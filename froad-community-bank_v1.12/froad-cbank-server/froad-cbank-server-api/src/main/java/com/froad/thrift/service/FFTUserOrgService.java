/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.froad.thrift.service;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class FFTUserOrgService {

  /**
   * 用户组织管理服务
   * FFTUserOrgService
   */
  public interface Iface extends com.froad.thrift.monitor.service.BizMonitorService.Iface {

    /**
     * 用户组织查询
     * @param userId 用户Id
     * @return FFTUserOrgListVoRes
     * 
     * @param userId
     */
    public com.froad.thrift.vo.orgRoleManager.FFTUserOrgListVoRes getFFTUserOrg(long userId) throws org.apache.thrift.TException;

  }

  public interface AsyncIface extends com.froad.thrift.monitor.service.BizMonitorService .AsyncIface {

    public void getFFTUserOrg(long userId, org.apache.thrift.async.AsyncMethodCallback resultHandler) throws org.apache.thrift.TException;

  }

  public static class Client extends com.froad.thrift.monitor.service.BizMonitorService.Client implements Iface {
    public static class Factory implements org.apache.thrift.TServiceClientFactory<Client> {
      public Factory() {}
      public Client getClient(org.apache.thrift.protocol.TProtocol prot) {
        return new Client(prot);
      }
      public Client getClient(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
        return new Client(iprot, oprot);
      }
    }

    public Client(org.apache.thrift.protocol.TProtocol prot)
    {
      super(prot, prot);
    }

    public Client(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
      super(iprot, oprot);
    }

    public com.froad.thrift.vo.orgRoleManager.FFTUserOrgListVoRes getFFTUserOrg(long userId) throws org.apache.thrift.TException
    {
      send_getFFTUserOrg(userId);
      return recv_getFFTUserOrg();
    }

    public void send_getFFTUserOrg(long userId) throws org.apache.thrift.TException
    {
      getFFTUserOrg_args args = new getFFTUserOrg_args();
      args.setUserId(userId);
      sendBase("getFFTUserOrg", args);
    }

    public com.froad.thrift.vo.orgRoleManager.FFTUserOrgListVoRes recv_getFFTUserOrg() throws org.apache.thrift.TException
    {
      getFFTUserOrg_result result = new getFFTUserOrg_result();
      receiveBase(result, "getFFTUserOrg");
      if (result.isSetSuccess()) {
        return result.success;
      }
      throw new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.MISSING_RESULT, "getFFTUserOrg failed: unknown result");
    }

  }
  public static class AsyncClient extends com.froad.thrift.monitor.service.BizMonitorService.AsyncClient implements AsyncIface {
    public static class Factory implements org.apache.thrift.async.TAsyncClientFactory<AsyncClient> {
      private org.apache.thrift.async.TAsyncClientManager clientManager;
      private org.apache.thrift.protocol.TProtocolFactory protocolFactory;
      public Factory(org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.protocol.TProtocolFactory protocolFactory) {
        this.clientManager = clientManager;
        this.protocolFactory = protocolFactory;
      }
      public AsyncClient getAsyncClient(org.apache.thrift.transport.TNonblockingTransport transport) {
        return new AsyncClient(protocolFactory, clientManager, transport);
      }
    }

    public AsyncClient(org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.transport.TNonblockingTransport transport) {
      super(protocolFactory, clientManager, transport);
    }

    public void getFFTUserOrg(long userId, org.apache.thrift.async.AsyncMethodCallback resultHandler) throws org.apache.thrift.TException {
      checkReady();
      getFFTUserOrg_call method_call = new getFFTUserOrg_call(userId, resultHandler, this, ___protocolFactory, ___transport);
      this.___currentMethod = method_call;
      ___manager.call(method_call);
    }

    public static class getFFTUserOrg_call extends org.apache.thrift.async.TAsyncMethodCall {
      private long userId;
      public getFFTUserOrg_call(long userId, org.apache.thrift.async.AsyncMethodCallback resultHandler, org.apache.thrift.async.TAsyncClient client, org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.transport.TNonblockingTransport transport) throws org.apache.thrift.TException {
        super(client, protocolFactory, transport, resultHandler, false);
        this.userId = userId;
      }

      public void write_args(org.apache.thrift.protocol.TProtocol prot) throws org.apache.thrift.TException {
        prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage("getFFTUserOrg", org.apache.thrift.protocol.TMessageType.CALL, 0));
        getFFTUserOrg_args args = new getFFTUserOrg_args();
        args.setUserId(userId);
        args.write(prot);
        prot.writeMessageEnd();
      }

      public com.froad.thrift.vo.orgRoleManager.FFTUserOrgListVoRes getResult() throws org.apache.thrift.TException {
        if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
          throw new IllegalStateException("Method call not finished!");
        }
        org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(getFrameBuffer().array());
        org.apache.thrift.protocol.TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
        return (new Client(prot)).recv_getFFTUserOrg();
      }
    }

  }

  public static class Processor<I extends Iface> extends com.froad.thrift.monitor.service.BizMonitorService.Processor<I> implements org.apache.thrift.TProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class.getName());
    public Processor(I iface) {
      super(iface, getProcessMap(new HashMap<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>>()));
    }

    protected Processor(I iface, Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> processMap) {
      super(iface, getProcessMap(processMap));
    }

    private static <I extends Iface> Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> getProcessMap(Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> processMap) {
      processMap.put("getFFTUserOrg", new getFFTUserOrg());
      return processMap;
    }

    public static class getFFTUserOrg<I extends Iface> extends org.apache.thrift.ProcessFunction<I, getFFTUserOrg_args> {
      public getFFTUserOrg() {
        super("getFFTUserOrg");
      }

      public getFFTUserOrg_args getEmptyArgsInstance() {
        return new getFFTUserOrg_args();
      }

      protected boolean isOneway() {
        return false;
      }

      public getFFTUserOrg_result getResult(I iface, getFFTUserOrg_args args) throws org.apache.thrift.TException {
        getFFTUserOrg_result result = new getFFTUserOrg_result();
        result.success = iface.getFFTUserOrg(args.userId);
        return result;
      }
    }

  }

  public static class AsyncProcessor<I extends AsyncIface> extends com.froad.thrift.monitor.service.BizMonitorService.AsyncProcessor<I> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncProcessor.class.getName());
    public AsyncProcessor(I iface) {
      super(iface, getProcessMap(new HashMap<String, org.apache.thrift.AsyncProcessFunction<I, ? extends org.apache.thrift.TBase, ?>>()));
    }

    protected AsyncProcessor(I iface, Map<String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase, ?>> processMap) {
      super(iface, getProcessMap(processMap));
    }

    private static <I extends AsyncIface> Map<String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase,?>> getProcessMap(Map<String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase, ?>> processMap) {
      processMap.put("getFFTUserOrg", new getFFTUserOrg());
      return processMap;
    }

    public static class getFFTUserOrg<I extends AsyncIface> extends org.apache.thrift.AsyncProcessFunction<I, getFFTUserOrg_args, com.froad.thrift.vo.orgRoleManager.FFTUserOrgListVoRes> {
      public getFFTUserOrg() {
        super("getFFTUserOrg");
      }

      public getFFTUserOrg_args getEmptyArgsInstance() {
        return new getFFTUserOrg_args();
      }

      public AsyncMethodCallback<com.froad.thrift.vo.orgRoleManager.FFTUserOrgListVoRes> getResultHandler(final AsyncFrameBuffer fb, final int seqid) {
        final org.apache.thrift.AsyncProcessFunction fcall = this;
        return new AsyncMethodCallback<com.froad.thrift.vo.orgRoleManager.FFTUserOrgListVoRes>() { 
          public void onComplete(com.froad.thrift.vo.orgRoleManager.FFTUserOrgListVoRes o) {
            getFFTUserOrg_result result = new getFFTUserOrg_result();
            result.success = o;
            try {
              fcall.sendResponse(fb,result, org.apache.thrift.protocol.TMessageType.REPLY,seqid);
              return;
            } catch (Exception e) {
              LOGGER.error("Exception writing to internal frame buffer", e);
            }
            fb.close();
          }
          public void onError(Exception e) {
            byte msgType = org.apache.thrift.protocol.TMessageType.REPLY;
            org.apache.thrift.TBase msg;
            getFFTUserOrg_result result = new getFFTUserOrg_result();
            {
              msgType = org.apache.thrift.protocol.TMessageType.EXCEPTION;
              msg = (org.apache.thrift.TBase)new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.INTERNAL_ERROR, e.getMessage());
            }
            try {
              fcall.sendResponse(fb,msg,msgType,seqid);
              return;
            } catch (Exception ex) {
              LOGGER.error("Exception writing to internal frame buffer", ex);
            }
            fb.close();
          }
        };
      }

      protected boolean isOneway() {
        return false;
      }

      public void start(I iface, getFFTUserOrg_args args, org.apache.thrift.async.AsyncMethodCallback<com.froad.thrift.vo.orgRoleManager.FFTUserOrgListVoRes> resultHandler) throws TException {
        iface.getFFTUserOrg(args.userId,resultHandler);
      }
    }

  }

  public static class getFFTUserOrg_args implements org.apache.thrift.TBase<getFFTUserOrg_args, getFFTUserOrg_args._Fields>, java.io.Serializable, Cloneable, Comparable<getFFTUserOrg_args>   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("getFFTUserOrg_args");

    private static final org.apache.thrift.protocol.TField USER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("userId", org.apache.thrift.protocol.TType.I64, (short)1);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new getFFTUserOrg_argsStandardSchemeFactory());
      schemes.put(TupleScheme.class, new getFFTUserOrg_argsTupleSchemeFactory());
    }

    public long userId; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      USER_ID((short)1, "userId");

      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

      static {
        for (_Fields field : EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 1: // USER_ID
            return USER_ID;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    private static final int __USERID_ISSET_ID = 0;
    private byte __isset_bitfield = 0;
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.USER_ID, new org.apache.thrift.meta_data.FieldMetaData("userId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(getFFTUserOrg_args.class, metaDataMap);
    }

    public getFFTUserOrg_args() {
    }

    public getFFTUserOrg_args(
      long userId)
    {
      this();
      this.userId = userId;
      setUserIdIsSet(true);
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public getFFTUserOrg_args(getFFTUserOrg_args other) {
      __isset_bitfield = other.__isset_bitfield;
      this.userId = other.userId;
    }

    public getFFTUserOrg_args deepCopy() {
      return new getFFTUserOrg_args(this);
    }

    @Override
    public void clear() {
      setUserIdIsSet(false);
      this.userId = 0;
    }

    public long getUserId() {
      return this.userId;
    }

    public getFFTUserOrg_args setUserId(long userId) {
      this.userId = userId;
      setUserIdIsSet(true);
      return this;
    }

    public void unsetUserId() {
      __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __USERID_ISSET_ID);
    }

    /** Returns true if field userId is set (has been assigned a value) and false otherwise */
    public boolean isSetUserId() {
      return EncodingUtils.testBit(__isset_bitfield, __USERID_ISSET_ID);
    }

    public void setUserIdIsSet(boolean value) {
      __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __USERID_ISSET_ID, value);
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case USER_ID:
        if (value == null) {
          unsetUserId();
        } else {
          setUserId((Long)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case USER_ID:
        return Long.valueOf(getUserId());

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case USER_ID:
        return isSetUserId();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof getFFTUserOrg_args)
        return this.equals((getFFTUserOrg_args)that);
      return false;
    }

    public boolean equals(getFFTUserOrg_args that) {
      if (that == null)
        return false;

      boolean this_present_userId = true;
      boolean that_present_userId = true;
      if (this_present_userId || that_present_userId) {
        if (!(this_present_userId && that_present_userId))
          return false;
        if (this.userId != that.userId)
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      List<Object> list = new ArrayList<Object>();

      boolean present_userId = true;
      list.add(present_userId);
      if (present_userId)
        list.add(userId);

      return list.hashCode();
    }

    @Override
    public int compareTo(getFFTUserOrg_args other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;

      lastComparison = Boolean.valueOf(isSetUserId()).compareTo(other.isSetUserId());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetUserId()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.userId, other.userId);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("getFFTUserOrg_args(");
      boolean first = true;

      sb.append("userId:");
      sb.append(this.userId);
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
      // check for sub-struct validity
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
        __isset_bitfield = 0;
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class getFFTUserOrg_argsStandardSchemeFactory implements SchemeFactory {
      public getFFTUserOrg_argsStandardScheme getScheme() {
        return new getFFTUserOrg_argsStandardScheme();
      }
    }

    private static class getFFTUserOrg_argsStandardScheme extends StandardScheme<getFFTUserOrg_args> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, getFFTUserOrg_args struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 1: // USER_ID
              if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
                struct.userId = iprot.readI64();
                struct.setUserIdIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, getFFTUserOrg_args struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        oprot.writeFieldBegin(USER_ID_FIELD_DESC);
        oprot.writeI64(struct.userId);
        oprot.writeFieldEnd();
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class getFFTUserOrg_argsTupleSchemeFactory implements SchemeFactory {
      public getFFTUserOrg_argsTupleScheme getScheme() {
        return new getFFTUserOrg_argsTupleScheme();
      }
    }

    private static class getFFTUserOrg_argsTupleScheme extends TupleScheme<getFFTUserOrg_args> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, getFFTUserOrg_args struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        BitSet optionals = new BitSet();
        if (struct.isSetUserId()) {
          optionals.set(0);
        }
        oprot.writeBitSet(optionals, 1);
        if (struct.isSetUserId()) {
          oprot.writeI64(struct.userId);
        }
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, getFFTUserOrg_args struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        BitSet incoming = iprot.readBitSet(1);
        if (incoming.get(0)) {
          struct.userId = iprot.readI64();
          struct.setUserIdIsSet(true);
        }
      }
    }

  }

  public static class getFFTUserOrg_result implements org.apache.thrift.TBase<getFFTUserOrg_result, getFFTUserOrg_result._Fields>, java.io.Serializable, Cloneable, Comparable<getFFTUserOrg_result>   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("getFFTUserOrg_result");

    private static final org.apache.thrift.protocol.TField SUCCESS_FIELD_DESC = new org.apache.thrift.protocol.TField("success", org.apache.thrift.protocol.TType.STRUCT, (short)0);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new getFFTUserOrg_resultStandardSchemeFactory());
      schemes.put(TupleScheme.class, new getFFTUserOrg_resultTupleSchemeFactory());
    }

    public com.froad.thrift.vo.orgRoleManager.FFTUserOrgListVoRes success; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      SUCCESS((short)0, "success");

      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

      static {
        for (_Fields field : EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 0: // SUCCESS
            return SUCCESS;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.SUCCESS, new org.apache.thrift.meta_data.FieldMetaData("success", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.orgRoleManager.FFTUserOrgListVoRes.class)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(getFFTUserOrg_result.class, metaDataMap);
    }

    public getFFTUserOrg_result() {
    }

    public getFFTUserOrg_result(
      com.froad.thrift.vo.orgRoleManager.FFTUserOrgListVoRes success)
    {
      this();
      this.success = success;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public getFFTUserOrg_result(getFFTUserOrg_result other) {
      if (other.isSetSuccess()) {
        this.success = new com.froad.thrift.vo.orgRoleManager.FFTUserOrgListVoRes(other.success);
      }
    }

    public getFFTUserOrg_result deepCopy() {
      return new getFFTUserOrg_result(this);
    }

    @Override
    public void clear() {
      this.success = null;
    }

    public com.froad.thrift.vo.orgRoleManager.FFTUserOrgListVoRes getSuccess() {
      return this.success;
    }

    public getFFTUserOrg_result setSuccess(com.froad.thrift.vo.orgRoleManager.FFTUserOrgListVoRes success) {
      this.success = success;
      return this;
    }

    public void unsetSuccess() {
      this.success = null;
    }

    /** Returns true if field success is set (has been assigned a value) and false otherwise */
    public boolean isSetSuccess() {
      return this.success != null;
    }

    public void setSuccessIsSet(boolean value) {
      if (!value) {
        this.success = null;
      }
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case SUCCESS:
        if (value == null) {
          unsetSuccess();
        } else {
          setSuccess((com.froad.thrift.vo.orgRoleManager.FFTUserOrgListVoRes)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case SUCCESS:
        return getSuccess();

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case SUCCESS:
        return isSetSuccess();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof getFFTUserOrg_result)
        return this.equals((getFFTUserOrg_result)that);
      return false;
    }

    public boolean equals(getFFTUserOrg_result that) {
      if (that == null)
        return false;

      boolean this_present_success = true && this.isSetSuccess();
      boolean that_present_success = true && that.isSetSuccess();
      if (this_present_success || that_present_success) {
        if (!(this_present_success && that_present_success))
          return false;
        if (!this.success.equals(that.success))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      List<Object> list = new ArrayList<Object>();

      boolean present_success = true && (isSetSuccess());
      list.add(present_success);
      if (present_success)
        list.add(success);

      return list.hashCode();
    }

    @Override
    public int compareTo(getFFTUserOrg_result other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;

      lastComparison = Boolean.valueOf(isSetSuccess()).compareTo(other.isSetSuccess());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetSuccess()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.success, other.success);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
      }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("getFFTUserOrg_result(");
      boolean first = true;

      sb.append("success:");
      if (this.success == null) {
        sb.append("null");
      } else {
        sb.append(this.success);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
      // check for sub-struct validity
      if (success != null) {
        success.validate();
      }
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class getFFTUserOrg_resultStandardSchemeFactory implements SchemeFactory {
      public getFFTUserOrg_resultStandardScheme getScheme() {
        return new getFFTUserOrg_resultStandardScheme();
      }
    }

    private static class getFFTUserOrg_resultStandardScheme extends StandardScheme<getFFTUserOrg_result> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, getFFTUserOrg_result struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 0: // SUCCESS
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.success = new com.froad.thrift.vo.orgRoleManager.FFTUserOrgListVoRes();
                struct.success.read(iprot);
                struct.setSuccessIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, getFFTUserOrg_result struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.success != null) {
          oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
          struct.success.write(oprot);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class getFFTUserOrg_resultTupleSchemeFactory implements SchemeFactory {
      public getFFTUserOrg_resultTupleScheme getScheme() {
        return new getFFTUserOrg_resultTupleScheme();
      }
    }

    private static class getFFTUserOrg_resultTupleScheme extends TupleScheme<getFFTUserOrg_result> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, getFFTUserOrg_result struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        BitSet optionals = new BitSet();
        if (struct.isSetSuccess()) {
          optionals.set(0);
        }
        oprot.writeBitSet(optionals, 1);
        if (struct.isSetSuccess()) {
          struct.success.write(oprot);
        }
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, getFFTUserOrg_result struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        BitSet incoming = iprot.readBitSet(1);
        if (incoming.get(0)) {
          struct.success = new com.froad.thrift.vo.orgRoleManager.FFTUserOrgListVoRes();
          struct.success.read(iprot);
          struct.setSuccessIsSet(true);
        }
      }
    }

  }

}
