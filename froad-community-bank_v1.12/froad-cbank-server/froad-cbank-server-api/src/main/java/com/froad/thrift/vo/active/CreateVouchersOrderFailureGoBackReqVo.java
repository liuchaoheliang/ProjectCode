/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.froad.thrift.vo.active;

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
 * 订单创建失败回退 请求
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class CreateVouchersOrderFailureGoBackReqVo implements org.apache.thrift.TBase<CreateVouchersOrderFailureGoBackReqVo, CreateVouchersOrderFailureGoBackReqVo._Fields>, java.io.Serializable, Cloneable, Comparable<CreateVouchersOrderFailureGoBackReqVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CreateVouchersOrderFailureGoBackReqVo");

  private static final org.apache.thrift.protocol.TField REQ_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("reqId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField CLIENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("clientId", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField MEMBER_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("memberCode", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField VOUCHERS_IDS_FIELD_DESC = new org.apache.thrift.protocol.TField("vouchersIds", org.apache.thrift.protocol.TType.LIST, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new CreateVouchersOrderFailureGoBackReqVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new CreateVouchersOrderFailureGoBackReqVoTupleSchemeFactory());
  }

  /**
   * 请求id
   */
  public String reqId; // required
  /**
   * 客户端id
   */
  public String clientId; // required
  /**
   * 用户编号
   */
  public long memberCode; // required
  /**
   * 代金券id 列表
   */
  public List<String> vouchersIds; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 请求id
     */
    REQ_ID((short)1, "reqId"),
    /**
     * 客户端id
     */
    CLIENT_ID((short)2, "clientId"),
    /**
     * 用户编号
     */
    MEMBER_CODE((short)3, "memberCode"),
    /**
     * 代金券id 列表
     */
    VOUCHERS_IDS((short)4, "vouchersIds");

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
        case 1: // REQ_ID
          return REQ_ID;
        case 2: // CLIENT_ID
          return CLIENT_ID;
        case 3: // MEMBER_CODE
          return MEMBER_CODE;
        case 4: // VOUCHERS_IDS
          return VOUCHERS_IDS;
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
    tmpMap.put(_Fields.REQ_ID, new org.apache.thrift.meta_data.FieldMetaData("reqId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CLIENT_ID, new org.apache.thrift.meta_data.FieldMetaData("clientId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.MEMBER_CODE, new org.apache.thrift.meta_data.FieldMetaData("memberCode", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.VOUCHERS_IDS, new org.apache.thrift.meta_data.FieldMetaData("vouchersIds", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CreateVouchersOrderFailureGoBackReqVo.class, metaDataMap);
  }

  public CreateVouchersOrderFailureGoBackReqVo() {
  }

  public CreateVouchersOrderFailureGoBackReqVo(
    String reqId,
    String clientId,
    long memberCode,
    List<String> vouchersIds)
  {
    this();
    this.reqId = reqId;
    this.clientId = clientId;
    this.memberCode = memberCode;
    setMemberCodeIsSet(true);
    this.vouchersIds = vouchersIds;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CreateVouchersOrderFailureGoBackReqVo(CreateVouchersOrderFailureGoBackReqVo other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetReqId()) {
      this.reqId = other.reqId;
    }
    if (other.isSetClientId()) {
      this.clientId = other.clientId;
    }
    this.memberCode = other.memberCode;
    if (other.isSetVouchersIds()) {
      List<String> __this__vouchersIds = new ArrayList<String>(other.vouchersIds);
      this.vouchersIds = __this__vouchersIds;
    }
  }

  public CreateVouchersOrderFailureGoBackReqVo deepCopy() {
    return new CreateVouchersOrderFailureGoBackReqVo(this);
  }

  @Override
  public void clear() {
    this.reqId = null;
    this.clientId = null;
    setMemberCodeIsSet(false);
    this.memberCode = 0;
    this.vouchersIds = null;
  }

  /**
   * 请求id
   */
  public String getReqId() {
    return this.reqId;
  }

  /**
   * 请求id
   */
  public CreateVouchersOrderFailureGoBackReqVo setReqId(String reqId) {
    this.reqId = reqId;
    return this;
  }

  public void unsetReqId() {
    this.reqId = null;
  }

  /** Returns true if field reqId is set (has been assigned a value) and false otherwise */
  public boolean isSetReqId() {
    return this.reqId != null;
  }

  public void setReqIdIsSet(boolean value) {
    if (!value) {
      this.reqId = null;
    }
  }

  /**
   * 客户端id
   */
  public String getClientId() {
    return this.clientId;
  }

  /**
   * 客户端id
   */
  public CreateVouchersOrderFailureGoBackReqVo setClientId(String clientId) {
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
   * 用户编号
   */
  public long getMemberCode() {
    return this.memberCode;
  }

  /**
   * 用户编号
   */
  public CreateVouchersOrderFailureGoBackReqVo setMemberCode(long memberCode) {
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

  public int getVouchersIdsSize() {
    return (this.vouchersIds == null) ? 0 : this.vouchersIds.size();
  }

  public java.util.Iterator<String> getVouchersIdsIterator() {
    return (this.vouchersIds == null) ? null : this.vouchersIds.iterator();
  }

  public void addToVouchersIds(String elem) {
    if (this.vouchersIds == null) {
      this.vouchersIds = new ArrayList<String>();
    }
    this.vouchersIds.add(elem);
  }

  /**
   * 代金券id 列表
   */
  public List<String> getVouchersIds() {
    return this.vouchersIds;
  }

  /**
   * 代金券id 列表
   */
  public CreateVouchersOrderFailureGoBackReqVo setVouchersIds(List<String> vouchersIds) {
    this.vouchersIds = vouchersIds;
    return this;
  }

  public void unsetVouchersIds() {
    this.vouchersIds = null;
  }

  /** Returns true if field vouchersIds is set (has been assigned a value) and false otherwise */
  public boolean isSetVouchersIds() {
    return this.vouchersIds != null;
  }

  public void setVouchersIdsIsSet(boolean value) {
    if (!value) {
      this.vouchersIds = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case REQ_ID:
      if (value == null) {
        unsetReqId();
      } else {
        setReqId((String)value);
      }
      break;

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

    case VOUCHERS_IDS:
      if (value == null) {
        unsetVouchersIds();
      } else {
        setVouchersIds((List<String>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case REQ_ID:
      return getReqId();

    case CLIENT_ID:
      return getClientId();

    case MEMBER_CODE:
      return Long.valueOf(getMemberCode());

    case VOUCHERS_IDS:
      return getVouchersIds();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case REQ_ID:
      return isSetReqId();
    case CLIENT_ID:
      return isSetClientId();
    case MEMBER_CODE:
      return isSetMemberCode();
    case VOUCHERS_IDS:
      return isSetVouchersIds();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof CreateVouchersOrderFailureGoBackReqVo)
      return this.equals((CreateVouchersOrderFailureGoBackReqVo)that);
    return false;
  }

  public boolean equals(CreateVouchersOrderFailureGoBackReqVo that) {
    if (that == null)
      return false;

    boolean this_present_reqId = true && this.isSetReqId();
    boolean that_present_reqId = true && that.isSetReqId();
    if (this_present_reqId || that_present_reqId) {
      if (!(this_present_reqId && that_present_reqId))
        return false;
      if (!this.reqId.equals(that.reqId))
        return false;
    }

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

    boolean this_present_vouchersIds = true && this.isSetVouchersIds();
    boolean that_present_vouchersIds = true && that.isSetVouchersIds();
    if (this_present_vouchersIds || that_present_vouchersIds) {
      if (!(this_present_vouchersIds && that_present_vouchersIds))
        return false;
      if (!this.vouchersIds.equals(that.vouchersIds))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_reqId = true && (isSetReqId());
    list.add(present_reqId);
    if (present_reqId)
      list.add(reqId);

    boolean present_clientId = true && (isSetClientId());
    list.add(present_clientId);
    if (present_clientId)
      list.add(clientId);

    boolean present_memberCode = true;
    list.add(present_memberCode);
    if (present_memberCode)
      list.add(memberCode);

    boolean present_vouchersIds = true && (isSetVouchersIds());
    list.add(present_vouchersIds);
    if (present_vouchersIds)
      list.add(vouchersIds);

    return list.hashCode();
  }

  @Override
  public int compareTo(CreateVouchersOrderFailureGoBackReqVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetReqId()).compareTo(other.isSetReqId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetReqId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.reqId, other.reqId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
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
    lastComparison = Boolean.valueOf(isSetVouchersIds()).compareTo(other.isSetVouchersIds());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVouchersIds()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.vouchersIds, other.vouchersIds);
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
    StringBuilder sb = new StringBuilder("CreateVouchersOrderFailureGoBackReqVo(");
    boolean first = true;

    sb.append("reqId:");
    if (this.reqId == null) {
      sb.append("null");
    } else {
      sb.append(this.reqId);
    }
    first = false;
    if (!first) sb.append(", ");
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
    sb.append("vouchersIds:");
    if (this.vouchersIds == null) {
      sb.append("null");
    } else {
      sb.append(this.vouchersIds);
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class CreateVouchersOrderFailureGoBackReqVoStandardSchemeFactory implements SchemeFactory {
    public CreateVouchersOrderFailureGoBackReqVoStandardScheme getScheme() {
      return new CreateVouchersOrderFailureGoBackReqVoStandardScheme();
    }
  }

  private static class CreateVouchersOrderFailureGoBackReqVoStandardScheme extends StandardScheme<CreateVouchersOrderFailureGoBackReqVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, CreateVouchersOrderFailureGoBackReqVo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // REQ_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.reqId = iprot.readString();
              struct.setReqIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // CLIENT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.clientId = iprot.readString();
              struct.setClientIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // MEMBER_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.memberCode = iprot.readI64();
              struct.setMemberCodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // VOUCHERS_IDS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list400 = iprot.readListBegin();
                struct.vouchersIds = new ArrayList<String>(_list400.size);
                String _elem401;
                for (int _i402 = 0; _i402 < _list400.size; ++_i402)
                {
                  _elem401 = iprot.readString();
                  struct.vouchersIds.add(_elem401);
                }
                iprot.readListEnd();
              }
              struct.setVouchersIdsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, CreateVouchersOrderFailureGoBackReqVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.reqId != null) {
        oprot.writeFieldBegin(REQ_ID_FIELD_DESC);
        oprot.writeString(struct.reqId);
        oprot.writeFieldEnd();
      }
      if (struct.clientId != null) {
        oprot.writeFieldBegin(CLIENT_ID_FIELD_DESC);
        oprot.writeString(struct.clientId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(MEMBER_CODE_FIELD_DESC);
      oprot.writeI64(struct.memberCode);
      oprot.writeFieldEnd();
      if (struct.vouchersIds != null) {
        oprot.writeFieldBegin(VOUCHERS_IDS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.vouchersIds.size()));
          for (String _iter403 : struct.vouchersIds)
          {
            oprot.writeString(_iter403);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CreateVouchersOrderFailureGoBackReqVoTupleSchemeFactory implements SchemeFactory {
    public CreateVouchersOrderFailureGoBackReqVoTupleScheme getScheme() {
      return new CreateVouchersOrderFailureGoBackReqVoTupleScheme();
    }
  }

  private static class CreateVouchersOrderFailureGoBackReqVoTupleScheme extends TupleScheme<CreateVouchersOrderFailureGoBackReqVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, CreateVouchersOrderFailureGoBackReqVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetReqId()) {
        optionals.set(0);
      }
      if (struct.isSetClientId()) {
        optionals.set(1);
      }
      if (struct.isSetMemberCode()) {
        optionals.set(2);
      }
      if (struct.isSetVouchersIds()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetReqId()) {
        oprot.writeString(struct.reqId);
      }
      if (struct.isSetClientId()) {
        oprot.writeString(struct.clientId);
      }
      if (struct.isSetMemberCode()) {
        oprot.writeI64(struct.memberCode);
      }
      if (struct.isSetVouchersIds()) {
        {
          oprot.writeI32(struct.vouchersIds.size());
          for (String _iter404 : struct.vouchersIds)
          {
            oprot.writeString(_iter404);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, CreateVouchersOrderFailureGoBackReqVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.reqId = iprot.readString();
        struct.setReqIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.clientId = iprot.readString();
        struct.setClientIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.memberCode = iprot.readI64();
        struct.setMemberCodeIsSet(true);
      }
      if (incoming.get(3)) {
        {
          org.apache.thrift.protocol.TList _list405 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.vouchersIds = new ArrayList<String>(_list405.size);
          String _elem406;
          for (int _i407 = 0; _i407 < _list405.size; ++_i407)
          {
            _elem406 = iprot.readString();
            struct.vouchersIds.add(_elem406);
          }
        }
        struct.setVouchersIdsIsSet(true);
      }
    }
  }

}

