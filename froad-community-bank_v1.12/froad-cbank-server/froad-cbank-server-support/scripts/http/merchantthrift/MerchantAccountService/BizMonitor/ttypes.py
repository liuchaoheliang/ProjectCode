#
# Autogenerated by Thrift Compiler (0.9.2)
#
# DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
#
#  options string: py
#

from thrift.Thrift import TType, TMessageType, TException, TApplicationException

from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol, TProtocol
try:
  from thrift.protocol import fastbinary
except:
  fastbinary = None



class BizMethodInfo:
  """
  Attributes:
   - name
   - argsNum
   - argsType
  """

  thrift_spec = (
    None, # 0
    (1, TType.STRING, 'name', None, None, ), # 1
    (2, TType.BYTE, 'argsNum', None, None, ), # 2
    (3, TType.LIST, 'argsType', (TType.STRING,None), None, ), # 3
  )

  def __init__(self, name=None, argsNum=None, argsType=None,):
    self.name = name
    self.argsNum = argsNum
    self.argsType = argsType

  def read(self, iprot):
    if iprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None and fastbinary is not None:
      fastbinary.decode_binary(self, iprot.trans, (self.__class__, self.thrift_spec))
      return
    iprot.readStructBegin()
    while True:
      (fname, ftype, fid) = iprot.readFieldBegin()
      if ftype == TType.STOP:
        break
      if fid == 1:
        if ftype == TType.STRING:
          self.name = iprot.readString();
        else:
          iprot.skip(ftype)
      elif fid == 2:
        if ftype == TType.BYTE:
          self.argsNum = iprot.readByte();
        else:
          iprot.skip(ftype)
      elif fid == 3:
        if ftype == TType.LIST:
          self.argsType = []
          (_etype3, _size0) = iprot.readListBegin()
          for _i4 in xrange(_size0):
            _elem5 = iprot.readString();
            self.argsType.append(_elem5)
          iprot.readListEnd()
        else:
          iprot.skip(ftype)
      else:
        iprot.skip(ftype)
      iprot.readFieldEnd()
    iprot.readStructEnd()

  def write(self, oprot):
    if oprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and self.thrift_spec is not None and fastbinary is not None:
      oprot.trans.write(fastbinary.encode_binary(self, (self.__class__, self.thrift_spec)))
      return
    oprot.writeStructBegin('BizMethodInfo')
    if self.name is not None:
      oprot.writeFieldBegin('name', TType.STRING, 1)
      oprot.writeString(self.name)
      oprot.writeFieldEnd()
    if self.argsNum is not None:
      oprot.writeFieldBegin('argsNum', TType.BYTE, 2)
      oprot.writeByte(self.argsNum)
      oprot.writeFieldEnd()
    if self.argsType is not None:
      oprot.writeFieldBegin('argsType', TType.LIST, 3)
      oprot.writeListBegin(TType.STRING, len(self.argsType))
      for iter6 in self.argsType:
        oprot.writeString(iter6)
      oprot.writeListEnd()
      oprot.writeFieldEnd()
    oprot.writeFieldStop()
    oprot.writeStructEnd()

  def validate(self):
    return


  def __hash__(self):
    value = 17
    value = (value * 31) ^ hash(self.name)
    value = (value * 31) ^ hash(self.argsNum)
    value = (value * 31) ^ hash(self.argsType)
    return value

  def __repr__(self):
    L = ['%s=%r' % (key, value)
      for key, value in self.__dict__.iteritems()]
    return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

  def __eq__(self, other):
    return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

  def __ne__(self, other):
    return not (self == other)

class BizMethodInvokeInfo:
  """
  Attributes:
   - totalCount
   - successCount
   - failureCount
   - successAverageTime
   - successMinTime
   - successMaxTime
  """

  thrift_spec = (
    None, # 0
    (1, TType.I64, 'totalCount', None, None, ), # 1
    (2, TType.I64, 'successCount', None, None, ), # 2
    (3, TType.I64, 'failureCount', None, None, ), # 3
    (4, TType.I64, 'successAverageTime', None, None, ), # 4
    (5, TType.I64, 'successMinTime', None, None, ), # 5
    (6, TType.I64, 'successMaxTime', None, None, ), # 6
  )

  def __init__(self, totalCount=None, successCount=None, failureCount=None, successAverageTime=None, successMinTime=None, successMaxTime=None,):
    self.totalCount = totalCount
    self.successCount = successCount
    self.failureCount = failureCount
    self.successAverageTime = successAverageTime
    self.successMinTime = successMinTime
    self.successMaxTime = successMaxTime

  def read(self, iprot):
    if iprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None and fastbinary is not None:
      fastbinary.decode_binary(self, iprot.trans, (self.__class__, self.thrift_spec))
      return
    iprot.readStructBegin()
    while True:
      (fname, ftype, fid) = iprot.readFieldBegin()
      if ftype == TType.STOP:
        break
      if fid == 1:
        if ftype == TType.I64:
          self.totalCount = iprot.readI64();
        else:
          iprot.skip(ftype)
      elif fid == 2:
        if ftype == TType.I64:
          self.successCount = iprot.readI64();
        else:
          iprot.skip(ftype)
      elif fid == 3:
        if ftype == TType.I64:
          self.failureCount = iprot.readI64();
        else:
          iprot.skip(ftype)
      elif fid == 4:
        if ftype == TType.I64:
          self.successAverageTime = iprot.readI64();
        else:
          iprot.skip(ftype)
      elif fid == 5:
        if ftype == TType.I64:
          self.successMinTime = iprot.readI64();
        else:
          iprot.skip(ftype)
      elif fid == 6:
        if ftype == TType.I64:
          self.successMaxTime = iprot.readI64();
        else:
          iprot.skip(ftype)
      else:
        iprot.skip(ftype)
      iprot.readFieldEnd()
    iprot.readStructEnd()

  def write(self, oprot):
    if oprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and self.thrift_spec is not None and fastbinary is not None:
      oprot.trans.write(fastbinary.encode_binary(self, (self.__class__, self.thrift_spec)))
      return
    oprot.writeStructBegin('BizMethodInvokeInfo')
    if self.totalCount is not None:
      oprot.writeFieldBegin('totalCount', TType.I64, 1)
      oprot.writeI64(self.totalCount)
      oprot.writeFieldEnd()
    if self.successCount is not None:
      oprot.writeFieldBegin('successCount', TType.I64, 2)
      oprot.writeI64(self.successCount)
      oprot.writeFieldEnd()
    if self.failureCount is not None:
      oprot.writeFieldBegin('failureCount', TType.I64, 3)
      oprot.writeI64(self.failureCount)
      oprot.writeFieldEnd()
    if self.successAverageTime is not None:
      oprot.writeFieldBegin('successAverageTime', TType.I64, 4)
      oprot.writeI64(self.successAverageTime)
      oprot.writeFieldEnd()
    if self.successMinTime is not None:
      oprot.writeFieldBegin('successMinTime', TType.I64, 5)
      oprot.writeI64(self.successMinTime)
      oprot.writeFieldEnd()
    if self.successMaxTime is not None:
      oprot.writeFieldBegin('successMaxTime', TType.I64, 6)
      oprot.writeI64(self.successMaxTime)
      oprot.writeFieldEnd()
    oprot.writeFieldStop()
    oprot.writeStructEnd()

  def validate(self):
    return


  def __hash__(self):
    value = 17
    value = (value * 31) ^ hash(self.totalCount)
    value = (value * 31) ^ hash(self.successCount)
    value = (value * 31) ^ hash(self.failureCount)
    value = (value * 31) ^ hash(self.successAverageTime)
    value = (value * 31) ^ hash(self.successMinTime)
    value = (value * 31) ^ hash(self.successMaxTime)
    return value

  def __repr__(self):
    L = ['%s=%r' % (key, value)
      for key, value in self.__dict__.iteritems()]
    return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

  def __eq__(self, other):
    return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

  def __ne__(self, other):
    return not (self == other)