#
# Autogenerated by Thrift Compiler (0.9.2)
#
# DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
#
#  options string: py
#

from thrift.Thrift import TType, TMessageType, TException, TApplicationException
import Common.ttypes
import MerchantAccountVo.ttypes


from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol, TProtocol
try:
  from thrift.protocol import fastbinary
except:
  fastbinary = None



class MerchantAccountPageVoRes:
  """
  """

  thrift_spec = (
    None, # 0
    (1, TType.STRUCT, 'page', (Common.ttypes.PageVo, Common.ttypes.PageVo.thrift_spec), None, ), # 1
    (2, TType.LIST, 'merchantAccountVoList', (TType.STRUCT,(MerchantAccountVo.ttypes.MerchantAccountVo, MerchantAccountVo.ttypes.MerchantAccountVo.thrift_spec)), None, ), # 2
  )

  def __init__(self, page=None, merchantAccountVoList=None,):
    self.page = page
    self.merchantAccountVoList = merchantAccountVoList

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
        if ftype == TType.STRUCT:
          self.page = Common.ttypes.PageVo()
          self.page.read(iprot)
        else:
          iprot.skip(ftype)
      elif fid == 2:
        if ftype == TType.LIST:
          self.merchantAccountVoList = []
          (_etype3, _size0) = iprot.readListBegin()
          for _i4 in xrange(_size0):
            _elem5 = MerchantAccountVo.ttypes.MerchantAccountVo()
            _elem5.read(iprot)
            self.merchantAccountVoList.append(_elem5)
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
    oprot.writeStructBegin('MerchantAccountPageVoRes')
    if self.page is not None:
      oprot.writeFieldBegin('page', TType.STRUCT, 1)
      self.page.write(oprot)
      oprot.writeFieldEnd()
    if self.merchantAccountVoList is not None:
      oprot.writeFieldBegin('merchantAccountVoList', TType.LIST, 2)
      oprot.writeListBegin(TType.STRUCT, len(self.merchantAccountVoList))
      for iter6 in self.merchantAccountVoList:
        iter6.write(oprot)
      oprot.writeListEnd()
      oprot.writeFieldEnd()
    oprot.writeFieldStop()
    oprot.writeStructEnd()

  def validate(self):
    return


  def __hash__(self):
    value = 17
    value = (value * 31) ^ hash(self.page)
    value = (value * 31) ^ hash(self.merchantAccountVoList)
    return value

  def __repr__(self):
    L = ['%s=%r' % (key, value)
      for key, value in self.__dict__.iteritems()]
    return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

  def __eq__(self, other):
    return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

  def __ne__(self, other):
    return not (self == other)