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
 * 查询营销活动规则 - 根据商户
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class FindActiveRuleByMerchantVo implements org.apache.thrift.TBase<FindActiveRuleByMerchantVo, FindActiveRuleByMerchantVo._Fields>, java.io.Serializable, Cloneable, Comparable<FindActiveRuleByMerchantVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("FindActiveRuleByMerchantVo");

  private static final org.apache.thrift.protocol.TField REQ_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("reqId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField CLIENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("clientId", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField MEMBER_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("memberCode", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField MERCHANT_ID_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("merchantIdList", org.apache.thrift.protocol.TType.LIST, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new FindActiveRuleByMerchantVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new FindActiveRuleByMerchantVoTupleSchemeFactory());
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
   * 客户号 *
   */
  public String memberCode; // required
  /**
   * 商户id - 列表
   */
  public List<String> merchantIdList; // required

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
     * 客户号 *
     */
    MEMBER_CODE((short)3, "memberCode"),
    /**
     * 商户id - 列表
     */
    MERCHANT_ID_LIST((short)4, "merchantIdList");

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
        case 4: // MERCHANT_ID_LIST
          return MERCHANT_ID_LIST;
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
    tmpMap.put(_Fields.REQ_ID, new org.apache.thrift.meta_data.FieldMetaData("reqId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CLIENT_ID, new org.apache.thrift.meta_data.FieldMetaData("clientId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.MEMBER_CODE, new org.apache.thrift.meta_data.FieldMetaData("memberCode", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.MERCHANT_ID_LIST, new org.apache.thrift.meta_data.FieldMetaData("merchantIdList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(FindActiveRuleByMerchantVo.class, metaDataMap);
  }

  public FindActiveRuleByMerchantVo() {
  }

  public FindActiveRuleByMerchantVo(
    String reqId,
    String clientId,
    String memberCode,
    List<String> merchantIdList)
  {
    this();
    this.reqId = reqId;
    this.clientId = clientId;
    this.memberCode = memberCode;
    this.merchantIdList = merchantIdList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public FindActiveRuleByMerchantVo(FindActiveRuleByMerchantVo other) {
    if (other.isSetReqId()) {
      this.reqId = other.reqId;
    }
    if (other.isSetClientId()) {
      this.clientId = other.clientId;
    }
    if (other.isSetMemberCode()) {
      this.memberCode = other.memberCode;
    }
    if (other.isSetMerchantIdList()) {
      List<String> __this__merchantIdList = new ArrayList<String>(other.merchantIdList);
      this.merchantIdList = __this__merchantIdList;
    }
  }

  public FindActiveRuleByMerchantVo deepCopy() {
    return new FindActiveRuleByMerchantVo(this);
  }

  @Override
  public void clear() {
    this.reqId = null;
    this.clientId = null;
    this.memberCode = null;
    this.merchantIdList = null;
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
  public FindActiveRuleByMerchantVo setReqId(String reqId) {
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
  public FindActiveRuleByMerchantVo setClientId(String clientId) {
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
   * 客户号 *
   */
  public String getMemberCode() {
    return this.memberCode;
  }

  /**
   * 客户号 *
   */
  public FindActiveRuleByMerchantVo setMemberCode(String memberCode) {
    this.memberCode = memberCode;
    return this;
  }

  public void unsetMemberCode() {
    this.memberCode = null;
  }

  /** Returns true if field memberCode is set (has been assigned a value) and false otherwise */
  public boolean isSetMemberCode() {
    return this.memberCode != null;
  }

  public void setMemberCodeIsSet(boolean value) {
    if (!value) {
      this.memberCode = null;
    }
  }

  public int getMerchantIdListSize() {
    return (this.merchantIdList == null) ? 0 : this.merchantIdList.size();
  }

  public java.util.Iterator<String> getMerchantIdListIterator() {
    return (this.merchantIdList == null) ? null : this.merchantIdList.iterator();
  }

  public void addToMerchantIdList(String elem) {
    if (this.merchantIdList == null) {
      this.merchantIdList = new ArrayList<String>();
    }
    this.merchantIdList.add(elem);
  }

  /**
   * 商户id - 列表
   */
  public List<String> getMerchantIdList() {
    return this.merchantIdList;
  }

  /**
   * 商户id - 列表
   */
  public FindActiveRuleByMerchantVo setMerchantIdList(List<String> merchantIdList) {
    this.merchantIdList = merchantIdList;
    return this;
  }

  public void unsetMerchantIdList() {
    this.merchantIdList = null;
  }

  /** Returns true if field merchantIdList is set (has been assigned a value) and false otherwise */
  public boolean isSetMerchantIdList() {
    return this.merchantIdList != null;
  }

  public void setMerchantIdListIsSet(boolean value) {
    if (!value) {
      this.merchantIdList = null;
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
        setMemberCode((String)value);
      }
      break;

    case MERCHANT_ID_LIST:
      if (value == null) {
        unsetMerchantIdList();
      } else {
        setMerchantIdList((List<String>)value);
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
      return getMemberCode();

    case MERCHANT_ID_LIST:
      return getMerchantIdList();

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
    case MERCHANT_ID_LIST:
      return isSetMerchantIdList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof FindActiveRuleByMerchantVo)
      return this.equals((FindActiveRuleByMerchantVo)that);
    return false;
  }

  public boolean equals(FindActiveRuleByMerchantVo that) {
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

    boolean this_present_memberCode = true && this.isSetMemberCode();
    boolean that_present_memberCode = true && that.isSetMemberCode();
    if (this_present_memberCode || that_present_memberCode) {
      if (!(this_present_memberCode && that_present_memberCode))
        return false;
      if (!this.memberCode.equals(that.memberCode))
        return false;
    }

    boolean this_present_merchantIdList = true && this.isSetMerchantIdList();
    boolean that_present_merchantIdList = true && that.isSetMerchantIdList();
    if (this_present_merchantIdList || that_present_merchantIdList) {
      if (!(this_present_merchantIdList && that_present_merchantIdList))
        return false;
      if (!this.merchantIdList.equals(that.merchantIdList))
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

    boolean present_memberCode = true && (isSetMemberCode());
    list.add(present_memberCode);
    if (present_memberCode)
      list.add(memberCode);

    boolean present_merchantIdList = true && (isSetMerchantIdList());
    list.add(present_merchantIdList);
    if (present_merchantIdList)
      list.add(merchantIdList);

    return list.hashCode();
  }

  @Override
  public int compareTo(FindActiveRuleByMerchantVo other) {
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
    lastComparison = Boolean.valueOf(isSetMerchantIdList()).compareTo(other.isSetMerchantIdList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMerchantIdList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.merchantIdList, other.merchantIdList);
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
    StringBuilder sb = new StringBuilder("FindActiveRuleByMerchantVo(");
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
    if (this.memberCode == null) {
      sb.append("null");
    } else {
      sb.append(this.memberCode);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("merchantIdList:");
    if (this.merchantIdList == null) {
      sb.append("null");
    } else {
      sb.append(this.merchantIdList);
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

  private static class FindActiveRuleByMerchantVoStandardSchemeFactory implements SchemeFactory {
    public FindActiveRuleByMerchantVoStandardScheme getScheme() {
      return new FindActiveRuleByMerchantVoStandardScheme();
    }
  }

  private static class FindActiveRuleByMerchantVoStandardScheme extends StandardScheme<FindActiveRuleByMerchantVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, FindActiveRuleByMerchantVo struct) throws org.apache.thrift.TException {
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
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.memberCode = iprot.readString();
              struct.setMemberCodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // MERCHANT_ID_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list208 = iprot.readListBegin();
                struct.merchantIdList = new ArrayList<String>(_list208.size);
                String _elem209;
                for (int _i210 = 0; _i210 < _list208.size; ++_i210)
                {
                  _elem209 = iprot.readString();
                  struct.merchantIdList.add(_elem209);
                }
                iprot.readListEnd();
              }
              struct.setMerchantIdListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, FindActiveRuleByMerchantVo struct) throws org.apache.thrift.TException {
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
      if (struct.memberCode != null) {
        oprot.writeFieldBegin(MEMBER_CODE_FIELD_DESC);
        oprot.writeString(struct.memberCode);
        oprot.writeFieldEnd();
      }
      if (struct.merchantIdList != null) {
        oprot.writeFieldBegin(MERCHANT_ID_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.merchantIdList.size()));
          for (String _iter211 : struct.merchantIdList)
          {
            oprot.writeString(_iter211);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class FindActiveRuleByMerchantVoTupleSchemeFactory implements SchemeFactory {
    public FindActiveRuleByMerchantVoTupleScheme getScheme() {
      return new FindActiveRuleByMerchantVoTupleScheme();
    }
  }

  private static class FindActiveRuleByMerchantVoTupleScheme extends TupleScheme<FindActiveRuleByMerchantVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, FindActiveRuleByMerchantVo struct) throws org.apache.thrift.TException {
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
      if (struct.isSetMerchantIdList()) {
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
        oprot.writeString(struct.memberCode);
      }
      if (struct.isSetMerchantIdList()) {
        {
          oprot.writeI32(struct.merchantIdList.size());
          for (String _iter212 : struct.merchantIdList)
          {
            oprot.writeString(_iter212);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, FindActiveRuleByMerchantVo struct) throws org.apache.thrift.TException {
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
        struct.memberCode = iprot.readString();
        struct.setMemberCodeIsSet(true);
      }
      if (incoming.get(3)) {
        {
          org.apache.thrift.protocol.TList _list213 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.merchantIdList = new ArrayList<String>(_list213.size);
          String _elem214;
          for (int _i215 = 0; _i215 < _list213.size; ++_i215)
          {
            _elem214 = iprot.readString();
            struct.merchantIdList.add(_elem214);
          }
        }
        struct.setMerchantIdListIsSet(true);
      }
    }
  }

}

