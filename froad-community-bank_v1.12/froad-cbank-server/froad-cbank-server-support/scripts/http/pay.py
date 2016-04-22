#!/usr/bin/env python

#-*-coding:utf-8-*-

import sys
reload(sys)

import pika
import redis
import signal
import json
import logging
import time

import tornado.options
import tornado.ioloop
import tornado.web
import tornado.httpserver
from tornado.options import define, options
from concurrent.futures import ThreadPoolExecutor
from tornado.concurrent import run_on_executor

from paythrift.Payment import PaymentService
from merchantthrift.MerchantAccountService import MerchantAccountService

from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.protocol import TMultiplexedProtocol

define("port", default=15901, help="tornado listen port", type=int)

#for read redis
r = None
#for write redis
rw = None
#for rabbit
#rb = None

#messageMax = 0
#messageItemCount = 0
paythrifthost = ""
paythriftport = 0
merchantthrifthost = ""
merchantthriftport = 0

class RedisHelper():
    def __init__(self, hostName, portNum):
        self.pool = redis.ConnectionPool(host=hostName, port=portNum)
        self.r = redis.Redis(connection_pool=self.pool)

    def addRegister(self, queueName, urlName):
        self.r.set(queueName, urlName)

    def getRegister(self):
        return self.r.get("master")

class RabbitHelper():
    def __init__(self, user, passwd, hostName, portNum, vHostName):
        self.credentials = pika.PlainCredentials(user, passwd)
        self.parameters = pika.ConnectionParameters(hostName, portNum, vHostName, self.credentials)
        self.connection = pika.BlockingConnection(self.parameters)
        self.channel = self.connection.channel()

    def createQueue(self, queueName):
        queue_args = {'x-ha-policy' : 'all'}
        self.channel.queue_declare(queue="master", arguments = queue_args)

    def send(self, queueName, message):
        self.channel.basic_publish(exchange = '', routing_key=queueName, body = message)

    def close(self):
        self.connection.close()

class SendHandler(tornado.web.RequestHandler):
    def post(self):
        messageJson = None
        returnMsg = None
        try:
            messageBody = self.get_argument('messageContent')
            messageJson = json.loads(messageBody)
        except Exception as e:
            messageJson = None
	    logging.info("message recv error")
        if messageJson != None:
	    type = messageJson["type"]
	    data = messageJson["data"]
	    messageLen = len(data)
	    logging.info("type %s length %d" %(type, messageLen))
            if messageLen > messageMax:
                returnMsg = {"resultCode":"0002", "msg":"message too much"}
            else:
                i = 0
                echoMessage = {}
                if messageLen < messageItemCount:
                    message = data[0:messageLen]
                    echoMessage["type"] = type
                    echoMessage["data"] = message
                    rb.send("master", json.dumps(echoMessage))
                else:
                    for i in range(0, messageLen / 100):
                        message = data[100 * i:100 * (i + 1)]
                        echoMessage["type"] = type
                        echoMessage["data"] = message
                        rb.send("master", json.dumps(echoMessage))
                    if 100 * (i + 1) < messageLen:
                        message = data[100 * (i + 1):messageLen]
                        echoMessage["type"] = type
                        echoMessage["data"] = message
                        rb.send("master", json.dumps(echoMessage))
            returnMsg = {"resultCode":"0000", "msg":"success send"}
        else:
            returnMsg = {"resultCode":"0003", "msg":"failed send"}
        self.write(json.dumps(returnMsg))

class RegisterListenerHandler(tornado.web.RequestHandler):
    def get(self):
        returnUrl = ""
        try:
            returnUrl = self.get_argument('returnUrl')
        except Exception as e:
            returnUrl = ""
        returnMsg = None
        if returnUrl != "":
            logging.info("returnUrl %s"%(returnUrl))
            r.addRegister("master", returnUrl)
            rb.createQueue("master")
            returnMsg = {"resultCode":"0000", "msg":"success register"}
        else:
            returnMsg = {"resultCode":"0001", "msg":"failed register"}
        self.write(json.dumps(returnMsg))

class MerchantThriftClient():
    def __init__(self, hostName, portNum):
        try:
            self.transport = TSocket.TSocket(hostName, portNum)
            self.transport.open()
            self.protocol = TBinaryProtocol.TBinaryProtocol(self.transport);
            self.protocol = TMultiplexedProtocol.TMultiplexedProtocol(self.protocol, "MerchantAccountService")
            self.client = MerchantAccountService.Client(self.protocol)
        except Thrift.TException, tx:
            print '%s' % (tx.message)

    def getMerchantAccount(self, key, merchant_id):
        resAccount = ""
        try:
            resAccount = self.client.getMerchantAccountByKeyAndOutletId(key, merchant_id)
        except Exception, e:
            print "merchant thrift exception"
        return resAccount

    def close(self):
        self.transport.close()

class PayThriftClient():
    def __init__(self, hostName, portNum):
        try:
            self.transport = TSocket.TSocket(hostName, portNum)
            self.transport.open()
            self.protocol = TBinaryProtocol.TBinaryProtocol(self.transport);
            self.protocol = TMultiplexedProtocol.TMultiplexedProtocol(self.protocol, "PaymentService")
            self.client = PaymentService.Client(self.protocol)
        except Thrift.TException, tx:
            print '%s' % (tx.message)

    def sendPayAndSettle(self, message):
	logging.info("start payandsettle thrift handle %f" %(time.time()))
        self.client.noticePaymentResult(message)
	logging.info("end payandsettle thrift handle %f" %(time.time()))

    def sendRefund(self, message):
	logging.info("start refund thrift handle %f" %(time.time()))
        self.client.noticeRefundResult(message)
	logging.info("end refund thrift handle %f" %(time.time()))

    def close(self):
        self.transport.close()

class PayAndSettlementHandler(tornado.web.RequestHandler):
    executor = ThreadPoolExecutor(10)

    @run_on_executor
    def paySettleCall(self, message):
        global paythrifthost
        global paythriftport
	logging.info("post payandsettle handle %s %d" %(paythrifthost, paythriftport))
        pt = PayThriftClient(paythrifthost, paythriftport)
        try:
            pt.sendPayAndSettle(self.request.body)
        except Exception, e:
	    logging.info("pay thrift client error")
        finally:
            pt.close()

    @tornado.web.asynchronous
    @tornado.gen.coroutine
    def post(self):
        yield self.paySettleCall(self.request.body)
        self.write("success")
        self.finish()

class RefundHandler(tornado.web.RequestHandler):
    executor = ThreadPoolExecutor(10)

    @run_on_executor
    def refundCall(self, message):
        global paythrifthost
        global paythriftport
	logging.info("post refund handle %s %d" %(paythrifthost, paythriftport))
        pt = PayThriftClient(paythrifthost, paythriftport)
        try:
            pt.sendRefund(message)
        except Exception, e:
	    logging.info("refund thrift client error")
        finally:
            pt.close()

    @tornado.web.asynchronous
    @tornado.gen.coroutine
    def post(self):
        yield self.refundCall(self.request.body)
        self.write("success")
        self.finish()
        
class PaySearchHandler(tornado.web.RequestHandler):
    def post(self):
        retMessage = ""
        message = self.request.body
        messagesplit = message.split("&")
        merchant_id = messagesplit[0].split("=")[1]
        key = messagesplit[1].split("=")[1]
        mt = None
        global merchantthrifthost
        global merchantthriftport
        try:
	    logging.info("post paysearch handle %s %d"%(merchantthrifthost, merchantthriftport))
            mt = MerchantThriftClient(merchantthrifthost, merchantthriftport)
            retMessage = mt.getMerchantAccount(key, merchant_id)
        except Exception,e:
	    logging.info("paysearch thrift error")
        finally:
            mt.close()
	logging.info("paysearch response body %s" %(retMessage))
        self.write(retMessage)

class TestHandler(tornado.web.RequestHandler):
    def get(self):
        returnMessage = self.get_argument('element')
	logging.info("test %s" %(returnMessage))
        self.write(returnMessage)

def sigint_handler(signum, frame):
    #rb.close()
    sys.exit(0)

logging.basicConfig(level=logging.DEBUG,
    format='%(asctime)s %(filename)s[line:%(lineno)d] %(levelname)s %(message)s',
    datefmt='%a, %d %b %Y %H:%M:%S',
    filename='./tornado.log',
    filemode='w')

if __name__ == "__main__":
    signal.signal(signal.SIGINT, sigint_handler)
    tornado.options.parse_command_line()
    with open("./pay.conf", "r") as f:
        conf = json.load(f)
        r = RedisHelper(str(conf["readRedisHost"]), int(conf["readRedisPort"]))
        rw = RedisHelper(str(conf["writeRedisHost"]), int(conf["writeRedisPort"]))
        #rb = RabbitHelper(str(conf["rabbitUser"]), str(conf["rabbitPasswd"]), str(conf["rabbitHost"]), int(conf["rabbitPort"]), str(conf["rabbitVHost"]))
        #messageMax = int(conf["contentLimit"])
        #messageItemCount = int(conf["elementCount"])
        paythrifthost = str(conf["payThriftHost"])
        paythriftport = int(conf["payThriftPort"])
        merchantthrifthost = str(conf["merchantThriftHost"])
        merchantthriftport = int(conf["merchantThriftPort"])

    '''
    (r"/send", SendHandler),
    (r"/register", RegisterListenerHandler),
    '''
    application = tornado.web.Application([
        (r"/pay", PayAndSettlementHandler),
        (r"/refund", RefundHandler),
        (r"/paysearch", PaySearchHandler),
        (r"/test", TestHandler),
    ])

    http_server = tornado.httpserver.HTTPServer(application)
    http_server.listen(options.port)
    tornado.ioloop.IOLoop.instance().start()
