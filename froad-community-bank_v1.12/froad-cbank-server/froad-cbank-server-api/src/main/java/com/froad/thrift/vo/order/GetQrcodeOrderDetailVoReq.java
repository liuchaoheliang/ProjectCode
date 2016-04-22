/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.froad.thrift.vo.order;

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
 * 5.获取面对面支付订单详情请求
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class GetQrcodeOrderDetailVoReq implements org.apache.thrift.TBase<GetQrcodeOrderDetailVoReq, GetQrcodeOrderDetailVoReq._Fields>, java.io.Serializable, Cloneable, Comparable<GetQrcodeOrderDetailVoReq> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("GetQrcodeOrderDetailVoReq");

  private static final org.apache.thrift.protocol.TField CLIENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("clientId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField MEMBER_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("memberCode", org.apache.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.thrift.protocol.TField ORDER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("orderId", org.apache.thrift.protocol.TType.STRING, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new GetQrcodeOrderDetailVoReqStandardSchemeFactory());
    schemes.put(TupleScheme.class, new GetQrcodeOrderDetailVoReqTupleSchemeFactory());
  }

  /**
   * 客户端ID
   */
  public String clientId; // required
  /**
   * 会员ID
   */
  public long memberCode; // required
  /**
   * 订单ID
   */
  public String orderId; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 客户端ID
     */
    CLIENT_ID((short)1, "clientId"),
    /**
     * 会员ID
     */
    MEMBER_CODE((short)2, "memberCode"),
    /**
     * 订单ID
     */
    ORDER_ID((short)3, "orderId");

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
        case 2: // MEMBER_CODE
          return MEMBER_CODE;
        case 3: // ORDER_ID
          return ORDER_ID;
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
  private static final int __MEMBERCODE_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.CLIENT_ID, new org.apache.thrift.meta_data.FieldMetaData("clientId", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.MEMBER_CODE, new org.apache.thrift.meta_data.FieldMetaData("memberCode", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.ORDER_ID, new org.apache.thrift.meta_data.FieldMetaData("orderId", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(GetQrcodeOrderDetailVoReq.class, metaDataMap);
  }

  public GetQrcodeOrderDetailVoReq() {
  }

  public GetQrcodeOrderDetailVoReq(
    String clientId,
    long memberCode,
    String orderId)
  {
    this();
    this.clientId = clientId;
    this.memberCode = memberCode;
    setMemberCodeIsSet(true);
    this.orderId = orderId;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public GetQrcodeOrderDetailVoReq(GetQrcodeOrderDetailVoReq other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetClientId()) {
      this.clientId = other.clientId;
    }
    this.memberCode = other.memberCode;
    if (other.isSetOrderId()) {
      this.orderId = other.orderId;
    }
  }

  public GetQrcodeOrderDetailVoReq deepCopy() {
    return new GetQrcodeOrderDetailVoReq(this);
  }

  @Override
  public void clear() {
    this.clientId = null;
    setMemberCodeIsSet(false);
    this.memberCode = 0;
    this.orderId = null;
  }

  /**
   * 客户端ID
   */
  public String getClientId() {
    return this.clientId;
  }

  /**
   * 客户端ID
   */
  public GetQrcodeOrderDetailVoReq setClientId(String clientId) {
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

  /**
   * 会员ID
   */
  public long getMemberCode() {
    return this.memberCode;
  }

  /**
   * 会员ID
   */
  public GetQrcodeOrderDetailVoReq setMemberCode(long memberCode) {
    this.memberCode = memberCode;
    setMemberCodeIsSet(true);
    return this;
  }

  public void unsetMemberCode() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __MEMBERCODE_ISSET_ID);
  }

  /** Returns true if field memberCode is set (has been assigned a value) and false otherwise */
  public boolean isSetMemberCode() {
    return EncodingUtils.testBit(__isset_bitfield, __MEMBERCODE_ISSET_ID);
  }

  public void setMemberCodeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __MEMBERCODE_ISSET_ID, value);
  }

  /**
   * 订单ID
   */
  public String getOrderId() {
    return this.orderId;
  }

  /**
   * 订单ID
   */
  public GetQrcodeOrderDetailVoReq setOrderId(String orderId) {
    this.orderId = orderId;
    return this;
  }

  public void unsetOrderId() {
    this.orderId = null;
  }

  /** Returns true if field orderId is set (has been assigned a value) and false otherwise */
  public boolean isSetOrderId() {
    return this.orderId != null;
  }

  public void setOrderIdIsSet(boolean value) {
    if (!value) {
      this.orderId = null;
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

    case MEMBER_CODE:
      if (value == null) {
        unsetMemberCode();
      } else {
        setMemberCode((Long)value);
      }
      break;

    case ORDER_ID:
      if (value == null) {
        unsetOrderId();
      } else {
        setOrderId((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case CLIENT_ID:
      return getClientId();

    case MEMBER_CODE:
      return Long.valueOf(getMemberCode());

    case ORDER_ID:
      return getOrderId();

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
    case MEMBER_CODE:
      return isSetMemberCode();
    case ORDER_ID:
      return isSetOrderId();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof GetQrcodeOrderDetailVoReq)
      return this.equals((GetQrcodeOrderDetailVoReq)that);
    return false;
  }

  public boolean equals(GetQrcodeOrderDetailVoReq that) {
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

    boolean this_present_memberCode = true;
    boolean that_present_memberCode = true;
    if (this_present_memberCode || that_present_memberCode) {
      if (!(this_present_memberCode && that_present_memberCode))
        return false;
      if (this.memberCode != that.memberCode)
        return false;
    }

    boolean this_present_orderId = true && this.isSetOrderId();
    boolean that_present_orderId = true && that.isSetOrderId();
    if (this_present_orderId || that_present_orderId) {
      if (!(this_present_orderId && that_present_orderId))
        return false;
      if (!this.orderId.equals(that.orderId))
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

    boolean present_memberCode = true;
    list.add(present_memberCode);
    if (present_memberCode)
      list.add(memberCode);

    boolean present_orderId = true && (isSetOrderId());
    list.add(present_orderId);
    if (present_orderId)
      list.add(orderId);

    return list.hashCode();
  }

  @Override
  public int compareTo(GetQrcodeOrderDetailVoReq other) {
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
    lastComparison = Boolean.valueOf(isSetMemberCode()).compareTo(other.isSetMemberCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMemberCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.memberCode, other.memberCode);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOrderId()).compareTo(other.isSetOrderId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOrderId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.orderId, other.orderId);
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
    StringBuilder sb = new StringBuilder("GetQrcodeOrderDetailVoReq(");
    boolean first = true;

    sb.append("clientId:");
    if (this.clientId == null) {
      sb.append("null");
    } else {
      sb.append(this.clientId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("memberCode:");
    sb.append(this.memberCode);
    first = false;
    if (!first) sb.append(", ");
    sb.append("orderId:");
    if (this.orderId == null) {
      sb.append("null");
    } else {
      sb.append(this.orderId);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (clientId == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'clientId' was not present! Struct: " + toString());
    }
    // alas, we cannot check 'memberCode' because it's a primitive and you chose the non-beans generator.
    if (orderId == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'orderId' was not present! Struct: " + toString());
    }
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

  private static class GetQrcodeOrderDetailVoReqStandardSchemeFactory implements SchemeFactory {
    public GetQrcodeOrderDetailVoReqStandardScheme getScheme() {
      return new GetQrcodeOrderDetailVoReqStandardScheme();
    }
  }

  private static class GetQrcodeOrderDetailVoReqStandardScheme extends StandardScheme<GetQrcodeOrderDetailVoReq> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, GetQrcodeOrderDetailVoReq struct) throws org.apache.thrift.TException {
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
          case 2: // MEMBER_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.memberCode = iprot.readI64();
              struct.setMemberCodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // ORDER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.orderId = iprot.readString();
              struct.setOrderIdIsSet(true);
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
      if (!struct.isSetMemberCode()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'memberCode' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, GetQrcodeOrderDetailVoReq struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.clientId != null) {
        oprot.writeFieldBegin(CLIENT_ID_FIELD_DESC);
        oprot.writeString(struct.clientId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(MEMBER_CODE_FIELD_DESC);
      oprot.writeI64(struct.memberCode);
      oprot.writeFieldEnd();
      if (struct.orderId != null) {
        oprot.writeFieldBegin(ORDER_ID_FIELD_DESC);
        oprot.writeString(struct.orderId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class GetQrcodeOrderDetailVoReqTupleSchemeFactory implements SchemeFactory {
    public GetQrcodeOrderDetailVoReqTupleScheme getScheme() {
      return new GetQrcodeOrderDetailVoReqTupleScheme();
    }
  }

  private static class GetQrcodeOrderDetailVoReqTupleScheme extends TupleScheme<GetQrcodeOrderDetailVoReq> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, GetQrcodeOrderDetailVoReq struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.clientId);
      oprot.writeI64(struct.memberCode);
      oprot.writeString(struct.orderId);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, GetQrcodeOrderDetailVoReq struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.clientId = iprot.readString();
      struct.setClientIdIsSet(true);
      struct.memberCode = iprot.readI64();
      struct.setMemberCodeIsSet(true);
      struct.orderId = iprot.readString();
      struct.setOrderIdIsSet(true);
    }
  }

}

