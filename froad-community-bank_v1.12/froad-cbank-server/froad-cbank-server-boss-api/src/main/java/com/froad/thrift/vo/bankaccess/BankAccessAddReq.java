/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.froad.thrift.vo.bankaccess;

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
/**
 * 新增银行信息req
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2015-9-15")
public class BankAccessAddReq implements org.apache.thrift.TBase<BankAccessAddReq, BankAccessAddReq._Fields>, java.io.Serializable, Cloneable, Comparable<BankAccessAddReq> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("BankAccessAddReq");

  private static final org.apache.thrift.protocol.TField CLIENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("clientId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField FUNCTION_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("functionList", org.apache.thrift.protocol.TType.LIST, (short)2);
  private static final org.apache.thrift.protocol.TField PAYMENT_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("paymentList", org.apache.thrift.protocol.TType.LIST, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new BankAccessAddReqStandardSchemeFactory());
    schemes.put(TupleScheme.class, new BankAccessAddReqTupleSchemeFactory());
  }

  /**
   * 客户端Id
   */
  public String clientId; // required
  /**
   * 功能模块集合
   */
  public List<FunctionModuleVo> functionList; // required
  /**
   * 支付方式集合：
   */
  public List<PaymentMethodVo> paymentList; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 客户端Id
     */
    CLIENT_ID((short)1, "clientId"),
    /**
     * 功能模块集合
     */
    FUNCTION_LIST((short)2, "functionList"),
    /**
     * 支付方式集合：
     */
    PAYMENT_LIST((short)3, "paymentList");

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
        case 2: // FUNCTION_LIST
          return FUNCTION_LIST;
        case 3: // PAYMENT_LIST
          return PAYMENT_LIST;
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
    tmpMap.put(_Fields.FUNCTION_LIST, new org.apache.thrift.meta_data.FieldMetaData("functionList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT            , "FunctionModuleVo"))));
    tmpMap.put(_Fields.PAYMENT_LIST, new org.apache.thrift.meta_data.FieldMetaData("paymentList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT            , "PaymentMethodVo"))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(BankAccessAddReq.class, metaDataMap);
  }

  public BankAccessAddReq() {
  }

  public BankAccessAddReq(
    String clientId,
    List<FunctionModuleVo> functionList,
    List<PaymentMethodVo> paymentList)
  {
    this();
    this.clientId = clientId;
    this.functionList = functionList;
    this.paymentList = paymentList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public BankAccessAddReq(BankAccessAddReq other) {
    if (other.isSetClientId()) {
      this.clientId = other.clientId;
    }
    if (other.isSetFunctionList()) {
      List<FunctionModuleVo> __this__functionList = new ArrayList<FunctionModuleVo>(other.functionList.size());
      for (FunctionModuleVo other_element : other.functionList) {
        __this__functionList.add(other_element);
      }
      this.functionList = __this__functionList;
    }
    if (other.isSetPaymentList()) {
      List<PaymentMethodVo> __this__paymentList = new ArrayList<PaymentMethodVo>(other.paymentList.size());
      for (PaymentMethodVo other_element : other.paymentList) {
        __this__paymentList.add(other_element);
      }
      this.paymentList = __this__paymentList;
    }
  }

  public BankAccessAddReq deepCopy() {
    return new BankAccessAddReq(this);
  }

  @Override
  public void clear() {
    this.clientId = null;
    this.functionList = null;
    this.paymentList = null;
  }

  /**
   * 客户端Id
   */
  public String getClientId() {
    return this.clientId;
  }

  /**
   * 客户端Id
   */
  public BankAccessAddReq setClientId(String clientId) {
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

  public int getFunctionListSize() {
    return (this.functionList == null) ? 0 : this.functionList.size();
  }

  public java.util.Iterator<FunctionModuleVo> getFunctionListIterator() {
    return (this.functionList == null) ? null : this.functionList.iterator();
  }

  public void addToFunctionList(FunctionModuleVo elem) {
    if (this.functionList == null) {
      this.functionList = new ArrayList<FunctionModuleVo>();
    }
    this.functionList.add(elem);
  }

  /**
   * 功能模块集合
   */
  public List<FunctionModuleVo> getFunctionList() {
    return this.functionList;
  }

  /**
   * 功能模块集合
   */
  public BankAccessAddReq setFunctionList(List<FunctionModuleVo> functionList) {
    this.functionList = functionList;
    return this;
  }

  public void unsetFunctionList() {
    this.functionList = null;
  }

  /** Returns true if field functionList is set (has been assigned a value) and false otherwise */
  public boolean isSetFunctionList() {
    return this.functionList != null;
  }

  public void setFunctionListIsSet(boolean value) {
    if (!value) {
      this.functionList = null;
    }
  }

  public int getPaymentListSize() {
    return (this.paymentList == null) ? 0 : this.paymentList.size();
  }

  public java.util.Iterator<PaymentMethodVo> getPaymentListIterator() {
    return (this.paymentList == null) ? null : this.paymentList.iterator();
  }

  public void addToPaymentList(PaymentMethodVo elem) {
    if (this.paymentList == null) {
      this.paymentList = new ArrayList<PaymentMethodVo>();
    }
    this.paymentList.add(elem);
  }

  /**
   * 支付方式集合：
   */
  public List<PaymentMethodVo> getPaymentList() {
    return this.paymentList;
  }

  /**
   * 支付方式集合：
   */
  public BankAccessAddReq setPaymentList(List<PaymentMethodVo> paymentList) {
    this.paymentList = paymentList;
    return this;
  }

  public void unsetPaymentList() {
    this.paymentList = null;
  }

  /** Returns true if field paymentList is set (has been assigned a value) and false otherwise */
  public boolean isSetPaymentList() {
    return this.paymentList != null;
  }

  public void setPaymentListIsSet(boolean value) {
    if (!value) {
      this.paymentList = null;
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

    case FUNCTION_LIST:
      if (value == null) {
        unsetFunctionList();
      } else {
        setFunctionList((List<FunctionModuleVo>)value);
      }
      break;

    case PAYMENT_LIST:
      if (value == null) {
        unsetPaymentList();
      } else {
        setPaymentList((List<PaymentMethodVo>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case CLIENT_ID:
      return getClientId();

    case FUNCTION_LIST:
      return getFunctionList();

    case PAYMENT_LIST:
      return getPaymentList();

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
    case FUNCTION_LIST:
      return isSetFunctionList();
    case PAYMENT_LIST:
      return isSetPaymentList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof BankAccessAddReq)
      return this.equals((BankAccessAddReq)that);
    return false;
  }

  public boolean equals(BankAccessAddReq that) {
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

    boolean this_present_functionList = true && this.isSetFunctionList();
    boolean that_present_functionList = true && that.isSetFunctionList();
    if (this_present_functionList || that_present_functionList) {
      if (!(this_present_functionList && that_present_functionList))
        return false;
      if (!this.functionList.equals(that.functionList))
        return false;
    }

    boolean this_present_paymentList = true && this.isSetPaymentList();
    boolean that_present_paymentList = true && that.isSetPaymentList();
    if (this_present_paymentList || that_present_paymentList) {
      if (!(this_present_paymentList && that_present_paymentList))
        return false;
      if (!this.paymentList.equals(that.paymentList))
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

    boolean present_functionList = true && (isSetFunctionList());
    list.add(present_functionList);
    if (present_functionList)
      list.add(functionList);

    boolean present_paymentList = true && (isSetPaymentList());
    list.add(present_paymentList);
    if (present_paymentList)
      list.add(paymentList);

    return list.hashCode();
  }

  @Override
  public int compareTo(BankAccessAddReq other) {
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
    lastComparison = Boolean.valueOf(isSetFunctionList()).compareTo(other.isSetFunctionList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFunctionList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.functionList, other.functionList);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPaymentList()).compareTo(other.isSetPaymentList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPaymentList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.paymentList, other.paymentList);
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
    StringBuilder sb = new StringBuilder("BankAccessAddReq(");
    boolean first = true;

    sb.append("clientId:");
    if (this.clientId == null) {
      sb.append("null");
    } else {
      sb.append(this.clientId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("functionList:");
    if (this.functionList == null) {
      sb.append("null");
    } else {
      sb.append(this.functionList);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("paymentList:");
    if (this.paymentList == null) {
      sb.append("null");
    } else {
      sb.append(this.paymentList);
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

  private static class BankAccessAddReqStandardSchemeFactory implements SchemeFactory {
    public BankAccessAddReqStandardScheme getScheme() {
      return new BankAccessAddReqStandardScheme();
    }
  }

  private static class BankAccessAddReqStandardScheme extends StandardScheme<BankAccessAddReq> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, BankAccessAddReq struct) throws org.apache.thrift.TException {
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
          case 2: // FUNCTION_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list24 = iprot.readListBegin();
                struct.functionList = new ArrayList<FunctionModuleVo>(_list24.size);
                FunctionModuleVo _elem25;
                for (int _i26 = 0; _i26 < _list24.size; ++_i26)
                {
                  _elem25 = new FunctionModuleVo();
                  _elem25.read(iprot);
                  struct.functionList.add(_elem25);
                }
                iprot.readListEnd();
              }
              struct.setFunctionListIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // PAYMENT_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list27 = iprot.readListBegin();
                struct.paymentList = new ArrayList<PaymentMethodVo>(_list27.size);
                PaymentMethodVo _elem28;
                for (int _i29 = 0; _i29 < _list27.size; ++_i29)
                {
                  _elem28 = new PaymentMethodVo();
                  _elem28.read(iprot);
                  struct.paymentList.add(_elem28);
                }
                iprot.readListEnd();
              }
              struct.setPaymentListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, BankAccessAddReq struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.clientId != null) {
        oprot.writeFieldBegin(CLIENT_ID_FIELD_DESC);
        oprot.writeString(struct.clientId);
        oprot.writeFieldEnd();
      }
      if (struct.functionList != null) {
        oprot.writeFieldBegin(FUNCTION_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.functionList.size()));
          for (FunctionModuleVo _iter30 : struct.functionList)
          {
            _iter30.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.paymentList != null) {
        oprot.writeFieldBegin(PAYMENT_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.paymentList.size()));
          for (PaymentMethodVo _iter31 : struct.paymentList)
          {
            _iter31.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class BankAccessAddReqTupleSchemeFactory implements SchemeFactory {
    public BankAccessAddReqTupleScheme getScheme() {
      return new BankAccessAddReqTupleScheme();
    }
  }

  private static class BankAccessAddReqTupleScheme extends TupleScheme<BankAccessAddReq> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, BankAccessAddReq struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetClientId()) {
        optionals.set(0);
      }
      if (struct.isSetFunctionList()) {
        optionals.set(1);
      }
      if (struct.isSetPaymentList()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetClientId()) {
        oprot.writeString(struct.clientId);
      }
      if (struct.isSetFunctionList()) {
        {
          oprot.writeI32(struct.functionList.size());
          for (FunctionModuleVo _iter32 : struct.functionList)
          {
            _iter32.write(oprot);
          }
        }
      }
      if (struct.isSetPaymentList()) {
        {
          oprot.writeI32(struct.paymentList.size());
          for (PaymentMethodVo _iter33 : struct.paymentList)
          {
            _iter33.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, BankAccessAddReq struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.clientId = iprot.readString();
        struct.setClientIdIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list34 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.functionList = new ArrayList<FunctionModuleVo>(_list34.size);
          FunctionModuleVo _elem35;
          for (int _i36 = 0; _i36 < _list34.size; ++_i36)
          {
            _elem35 = new FunctionModuleVo();
            _elem35.read(iprot);
            struct.functionList.add(_elem35);
          }
        }
        struct.setFunctionListIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TList _list37 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.paymentList = new ArrayList<PaymentMethodVo>(_list37.size);
          PaymentMethodVo _elem38;
          for (int _i39 = 0; _i39 < _list37.size; ++_i39)
          {
            _elem38 = new PaymentMethodVo();
            _elem38.read(iprot);
            struct.paymentList.add(_elem38);
          }
        }
        struct.setPaymentListIsSet(true);
      }
    }
  }

}
