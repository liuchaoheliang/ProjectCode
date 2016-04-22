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
public class BankAccessModuleService {

  /**
   * ---------------------------------------------------------多银行配置服务--------------------------------------
   */
  public interface Iface extends com.froad.thrift.monitor.service.BizMonitorService.Iface {

    /**
     * 获取客户端功能列表
     * 
     * @param clientId
     */
    public com.froad.thrift.vo.BankAccessModuleListRes getBankAccessModuleList(String clientId) throws org.apache.thrift.TException;

  }

  public interface AsyncIface extends com.froad.thrift.monitor.service.BizMonitorService .AsyncIface {

    public void getBankAccessModuleList(String clientId, org.apache.thrift.async.AsyncMethodCallback resultHandler) throws org.apache.thrift.TException;

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

    public com.froad.thrift.vo.BankAccessModuleListRes getBankAccessModuleList(String clientId) throws org.apache.thrift.TException
    {
      send_getBankAccessModuleList(clientId);
      return recv_getBankAccessModuleList();
    }

    public void send_getBankAccessModuleList(String clientId) throws org.apache.thrift.TException
    {
      getBankAccessModuleList_args args = new getBankAccessModuleList_args();
      args.setClientId(clientId);
      sendBase("getBankAccessModuleList", args);
    }

    public com.froad.thrift.vo.BankAccessModuleListRes recv_getBankAccessModuleList() throws org.apache.thrift.TException
    {
      getBankAccessModuleList_result result = new getBankAccessModuleList_result();
      receiveBase(result, "getBankAccessModuleList");
      if (result.isSetSuccess()) {
        return result.success;
      }
      throw new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.MISSING_RESULT, "getBankAccessModuleList failed: unknown result");
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

    public void getBankAccessModuleList(String clientId, org.apache.thrift.async.AsyncMethodCallback resultHandler) throws org.apache.thrift.TException {
      checkReady();
      getBankAccessModuleList_call method_call = new getBankAccessModuleList_call(clientId, resultHandler, this, ___protocolFactory, ___transport);
      this.___currentMethod = method_call;
      ___manager.call(method_call);
    }

    public static class getBankAccessModuleList_call extends org.apache.thrift.async.TAsyncMethodCall {
      private String clientId;
      public getBankAccessModuleList_call(String clientId, org.apache.thrift.async.AsyncMethodCallback resultHandler, org.apache.thrift.async.TAsyncClient client, org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.transport.TNonblockingTransport transport) throws org.apache.thrift.TException {
        super(client, protocolFactory, transport, resultHandler, false);
        this.clientId = clientId;
      }

      public void write_args(org.apache.thrift.protocol.TProtocol prot) throws org.apache.thrift.TException {
        prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage("getBankAccessModuleList", org.apache.thrift.protocol.TMessageType.CALL, 0));
        getBankAccessModuleList_args args = new getBankAccessModuleList_args();
        args.setClientId(clientId);
        args.write(prot);
        prot.writeMessageEnd();
      }

      public com.froad.thrift.vo.BankAccessModuleListRes getResult() throws org.apache.thrift.TException {
        if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
          throw new IllegalStateException("Method call not finished!");
        }
        org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(getFrameBuffer().array());
        org.apache.thrift.protocol.TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
        return (new Client(prot)).recv_getBankAccessModuleList();
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
      processMap.put("getBankAccessModuleList", new getBankAccessModuleList());
      return processMap;
    }

    public static class getBankAccessModuleList<I extends Iface> extends org.apache.thrift.ProcessFunction<I, getBankAccessModuleList_args> {
      public getBankAccessModuleList() {
        super("getBankAccessModuleList");
      }

      public getBankAccessModuleList_args getEmptyArgsInstance() {
        return new getBankAccessModuleList_args();
      }

      protected boolean isOneway() {
        return false;
      }

      public getBankAccessModuleList_result getResult(I iface, getBankAccessModuleList_args args) throws org.apache.thrift.TException {
        getBankAccessModuleList_result result = new getBankAccessModuleList_result();
        result.success = iface.getBankAccessModuleList(args.clientId);
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
      processMap.put("getBankAccessModuleList", new getBankAccessModuleList());
      return processMap;
    }

    public static class getBankAccessModuleList<I extends AsyncIface> extends org.apache.thrift.AsyncProcessFunction<I, getBankAccessModuleList_args, com.froad.thrift.vo.BankAccessModuleListRes> {
      public getBankAccessModuleList() {
        super("getBankAccessModuleList");
      }

      public getBankAccessModuleList_args getEmptyArgsInstance() {
        return new getBankAccessModuleList_args();
      }

      public AsyncMethodCallback<com.froad.thrift.vo.BankAccessModuleListRes> getResultHandler(final AsyncFrameBuffer fb, final int seqid) {
        final org.apache.thrift.AsyncProcessFunction fcall = this;
        return new AsyncMethodCallback<com.froad.thrift.vo.BankAccessModuleListRes>() { 
          public void onComplete(com.froad.thrift.vo.BankAccessModuleListRes o) {
            getBankAccessModuleList_result result = new getBankAccessModuleList_result();
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
            getBankAccessModuleList_result result = new getBankAccessModuleList_result();
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

      public void start(I iface, getBankAccessModuleList_args args, org.apache.thrift.async.AsyncMethodCallback<com.froad.thrift.vo.BankAccessModuleListRes> resultHandler) throws TException {
        iface.getBankAccessModuleList(args.clientId,resultHandler);
      }
    }

  }

  public static class getBankAccessModuleList_args implements org.apache.thrift.TBase<getBankAccessModuleList_args, getBankAccessModuleList_args._Fields>, java.io.Serializable, Cloneable, Comparable<getBankAccessModuleList_args>   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("getBankAccessModuleList_args");

    private static final org.apache.thrift.protocol.TField CLIENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("clientId", org.apache.thrift.protocol.TType.STRING, (short)1);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new getBankAccessModuleList_argsStandardSchemeFactory());
      schemes.put(TupleScheme.class, new getBankAccessModuleList_argsTupleSchemeFactory());
    }

    public String clientId; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      CLIENT_ID((short)1, "clientId");

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
          case 1: // CLIENT_ID
            return CLIENT_ID;
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
      tmpMap.put(_Fields.CLIENT_ID, new org.apache.thrift.meta_data.FieldMetaData("clientId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(getBankAccessModuleList_args.class, metaDataMap);
    }

    public getBankAccessModuleList_args() {
    }

    public getBankAccessModuleList_args(
      String clientId)
    {
      this();
      this.clientId = clientId;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public getBankAccessModuleList_args(getBankAccessModuleList_args other) {
      if (other.isSetClientId()) {
        this.clientId = other.clientId;
      }
    }

    public getBankAccessModuleList_args deepCopy() {
      return new getBankAccessModuleList_args(this);
    }

    @Override
    public void clear() {
      this.clientId = null;
    }

    public String getClientId() {
      return this.clientId;
    }

    public getBankAccessModuleList_args setClientId(String clientId) {
      this.clientId = clientId;
      return this;
    }

    public void unsetClientId() {
      this.clientId = null;
    }

    /** Returns true if field clientId is set (has been assigned a value) and false otherwise */
    public boolean isSetClientId() {
      return this.clientId != null;
    }

    public void setClientIdIsSet(boolean value) {
      if (!value) {
        this.clientId = null;
      }
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case CLIENT_ID:
        if (value == null) {
          unsetClientId();
        } else {
          setClientId((String)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case CLIENT_ID:
        return getClientId();

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case CLIENT_ID:
        return isSetClientId();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof getBankAccessModuleList_args)
        return this.equals((getBankAccessModuleList_args)that);
      return false;
    }

    public boolean equals(getBankAccessModuleList_args that) {
      if (that == null)
        return false;

      boolean this_present_clientId = true && this.isSetClientId();
      boolean that_present_clientId = true && that.isSetClientId();
      if (this_present_clientId || that_present_clientId) {
        if (!(this_present_clientId && that_present_clientId))
          return false;
        if (!this.clientId.equals(that.clientId))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      List<Object> list = new ArrayList<Object>();

      boolean present_clientId = true && (isSetClientId());
      list.add(present_clientId);
      if (present_clientId)
        list.add(clientId);

      return list.hashCode();
    }

    @Override
    public int compareTo(getBankAccessModuleList_args other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;

      lastComparison = Boolean.valueOf(isSetClientId()).compareTo(other.isSetClientId());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetClientId()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.clientId, other.clientId);
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
      StringBuilder sb = new StringBuilder("getBankAccessModuleList_args(");
      boolean first = true;

      sb.append("clientId:");
      if (this.clientId == null) {
        sb.append("null");
      } else {
        sb.append(this.clientId);
      }
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
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class getBankAccessModuleList_argsStandardSchemeFactory implements SchemeFactory {
      public getBankAccessModuleList_argsStandardScheme getScheme() {
        return new getBankAccessModuleList_argsStandardScheme();
      }
    }

    private static class getBankAccessModuleList_argsStandardScheme extends StandardScheme<getBankAccessModuleList_args> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, getBankAccessModuleList_args struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 1: // CLIENT_ID
              if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                struct.clientId = iprot.readString();
                struct.setClientIdIsSet(true);
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

      public void write(org.apache.thrift.protocol.TProtocol oprot, getBankAccessModuleList_args struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.clientId != null) {
          oprot.writeFieldBegin(CLIENT_ID_FIELD_DESC);
          oprot.writeString(struct.clientId);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class getBankAccessModuleList_argsTupleSchemeFactory implements SchemeFactory {
      public getBankAccessModuleList_argsTupleScheme getScheme() {
        return new getBankAccessModuleList_argsTupleScheme();
      }
    }

    private static class getBankAccessModuleList_argsTupleScheme extends TupleScheme<getBankAccessModuleList_args> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, getBankAccessModuleList_args struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        BitSet optionals = new BitSet();
        if (struct.isSetClientId()) {
          optionals.set(0);
        }
        oprot.writeBitSet(optionals, 1);
        if (struct.isSetClientId()) {
          oprot.writeString(struct.clientId);
        }
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, getBankAccessModuleList_args struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        BitSet incoming = iprot.readBitSet(1);
        if (incoming.get(0)) {
          struct.clientId = iprot.readString();
          struct.setClientIdIsSet(true);
        }
      }
    }

  }

  public static class getBankAccessModuleList_result implements org.apache.thrift.TBase<getBankAccessModuleList_result, getBankAccessModuleList_result._Fields>, java.io.Serializable, Cloneable, Comparable<getBankAccessModuleList_result>   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("getBankAccessModuleList_result");

    private static final org.apache.thrift.protocol.TField SUCCESS_FIELD_DESC = new org.apache.thrift.protocol.TField("success", org.apache.thrift.protocol.TType.STRUCT, (short)0);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new getBankAccessModuleList_resultStandardSchemeFactory());
      schemes.put(TupleScheme.class, new getBankAccessModuleList_resultTupleSchemeFactory());
    }

    public com.froad.thrift.vo.BankAccessModuleListRes success; // required

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
          new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.BankAccessModuleListRes.class)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(getBankAccessModuleList_result.class, metaDataMap);
    }

    public getBankAccessModuleList_result() {
    }

    public getBankAccessModuleList_result(
      com.froad.thrift.vo.BankAccessModuleListRes success)
    {
      this();
      this.success = success;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public getBankAccessModuleList_result(getBankAccessModuleList_result other) {
      if (other.isSetSuccess()) {
        this.success = new com.froad.thrift.vo.BankAccessModuleListRes(other.success);
      }
    }

    public getBankAccessModuleList_result deepCopy() {
      return new getBankAccessModuleList_result(this);
    }

    @Override
    public void clear() {
      this.success = null;
    }

    public com.froad.thrift.vo.BankAccessModuleListRes getSuccess() {
      return this.success;
    }

    public getBankAccessModuleList_result setSuccess(com.froad.thrift.vo.BankAccessModuleListRes success) {
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
          setSuccess((com.froad.thrift.vo.BankAccessModuleListRes)value);
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
      if (that instanceof getBankAccessModuleList_result)
        return this.equals((getBankAccessModuleList_result)that);
      return false;
    }

    public boolean equals(getBankAccessModuleList_result that) {
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
    public int compareTo(getBankAccessModuleList_result other) {
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
      StringBuilder sb = new StringBuilder("getBankAccessModuleList_result(");
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

    private static class getBankAccessModuleList_resultStandardSchemeFactory implements SchemeFactory {
      public getBankAccessModuleList_resultStandardScheme getScheme() {
        return new getBankAccessModuleList_resultStandardScheme();
      }
    }

    private static class getBankAccessModuleList_resultStandardScheme extends StandardScheme<getBankAccessModuleList_result> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, getBankAccessModuleList_result struct) throws org.apache.thrift.TException {
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
                struct.success = new com.froad.thrift.vo.BankAccessModuleListRes();
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

      public void write(org.apache.thrift.protocol.TProtocol oprot, getBankAccessModuleList_result struct) throws org.apache.thrift.TException {
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

    private static class getBankAccessModuleList_resultTupleSchemeFactory implements SchemeFactory {
      public getBankAccessModuleList_resultTupleScheme getScheme() {
        return new getBankAccessModuleList_resultTupleScheme();
      }
    }

    private static class getBankAccessModuleList_resultTupleScheme extends TupleScheme<getBankAccessModuleList_result> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, getBankAccessModuleList_result struct) throws org.apache.thrift.TException {
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
      public void read(org.apache.thrift.protocol.TProtocol prot, getBankAccessModuleList_result struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        BitSet incoming = iprot.readBitSet(1);
        if (incoming.get(0)) {
          struct.success = new com.froad.thrift.vo.BankAccessModuleListRes();
          struct.success.read(iprot);
          struct.setSuccessIsSet(true);
        }
      }
    }

  }

}
