#!/usr/bin/env python
import redis
import pika
import json
import signal
import urllib
import urllib2
import logging

#for read redis
r = None
#for rabbit
rb = None

class RabbitHelper():
    def __init__(self, user, passwd, hostName, portNum, vHostName):
        self.credentials = pika.PlainCredentials(user, passwd)
        self.parameters = pika.ConnectionParameters(hostName, portNum, vHostName, self.credentials)
        self.connection = pika.BlockingConnection(self.parameters)
        self.channel = self.connection.channel()

    def registerMasterHandler(self, queueName, callback):
        queue_args = {'x-ha-policy' : 'all'}
        self.channel.queue_declare(queue=queueName, arguments = queue_args)
        self.channel.basic_consume(callback, queue = queueName, no_ack = True)
        self.channel.start_consuming()

    def close(self):
        self.connection.close()

class RedisHelper():
    def __init__(self, hostName, portNum):
        self.pool = redis.ConnectionPool(host=hostName, port=portNum)

    def getRegister(self, queueName):
        self.r = redis.Redis(connection_pool=self.pool)
        return self.r.get(queueName)

def sigint_handler(signum, frame):
    rb.close()
    sys.exit(0)

if __name__ == "__main__":
    signal.signal(signal.SIGINT, sigint_handler)

    with open("./mq.conf", "r") as f:
        conf = json.load(f)
        r = RedisHelper(str(conf["readRedisHost"]), int(conf["readRedisPort"]))
        rb = RabbitHelper(str(conf["rabbitUser"]), str(conf["rabbitPasswd"]), str(conf["rabbitHost"]), int(conf["rabbitPort"]), str(conf["rabbitVHost"]))

    def masterCallback(ch, method, properties, body):
        returnUrl = r.getRegister("master")
        req = urllib2.Request(url = returnUrl, data = "element=" + body)
        resData = urllib2.urlopen(req)

    rb.registerMasterHandler("master", masterCallback)
