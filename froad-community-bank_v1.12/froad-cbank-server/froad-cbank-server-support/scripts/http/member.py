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

define("port", default=15902, help="tornado listen port", type=int)

#for read redis
r = None
#for write redis
rw = None
#for rabbit
rb = None

messageMax = 0
messageItemCount = 0

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

    def send(self, queueName, message):
        self.connection = pika.BlockingConnection(self.parameters)
        self.channel = self.connection.channel()
        queue_args = {'x-ha-policy' : 'all'}
        self.channel.queue_declare(queue="master", arguments = queue_args)
        self.channel.basic_publish(exchange = '', routing_key=queueName, body = message)
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
            rw.addRegister("master", returnUrl)
            rb.createQueue("master")
            returnMsg = {"resultCode":"0000", "msg":"success register"}
        else:
            returnMsg = {"resultCode":"0001", "msg":"failed register"}
        self.write(json.dumps(returnMsg))

class TestHandler(tornado.web.RequestHandler):
    def get(self):
        returnMessage = self.get_argument('element')
	logging.info("test %s" %(returnMessage))
        self.write(returnMessage)

def sigint_handler(signum, frame):
    sys.exit(0)

logging.basicConfig(level=logging.DEBUG,
    format='%(asctime)s %(filename)s[line:%(lineno)d] %(levelname)s %(message)s',
    datefmt='%a, %d %b %Y %H:%M:%S',
    filename='./tornado.log',
    filemode='w')

if __name__ == "__main__":
    signal.signal(signal.SIGINT, sigint_handler)
    tornado.options.parse_command_line()
    with open("./mq.conf", "r") as f:
        conf = json.load(f)
        r = RedisHelper(str(conf["readRedisHost"]), int(conf["readRedisPort"]))
        rw = RedisHelper(str(conf["writeRedisHost"]), int(conf["writeRedisPort"]))
        rb = RabbitHelper(str(conf["rabbitUser"]), str(conf["rabbitPasswd"]), str(conf["rabbitHost"]), int(conf["rabbitPort"]), str(conf["rabbitVHost"]))
        messageMax = int(conf["contentLimit"])
        messageItemCount = int(conf["elementCount"])
    
    application = tornado.web.Application([
        (r"/send", SendHandler),
        (r"/register", RegisterListenerHandler),
        (r"/test", TestHandler),
    ])

    http_server = tornado.httpserver.HTTPServer(application)
    http_server.listen(options.port)
    tornado.ioloop.IOLoop.instance().start()
