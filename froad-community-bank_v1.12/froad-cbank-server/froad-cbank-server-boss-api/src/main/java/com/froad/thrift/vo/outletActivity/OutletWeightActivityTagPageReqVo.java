/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.froad.thrift.vo.outletActivity;

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
 * 关联门店信息分页查询ReqVo
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2015-10-29")
public class OutletWeightActivityTagPageReqVo implements org.apache.thrift.TBase<OutletWeightActivityTagPageReqVo, OutletWeightActivityTagPageReqVo._Fields>, java.io.Serializable, Cloneable, Comparable<OutletWeightActivityTagPageReqVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("OutletWeightActivityTagPageReqVo");

  private static final org.apache.thrift.protocol.TField ACTIVITY_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("activityId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField CLIENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("clientId", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField ACTIVITY_NO_FIELD_DESC = new org.apache.thrift.protocol.TField("activityNo", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField PAGE_VO_FIELD_DESC = new org.apache.thrift.protocol.TField("pageVo", org.apache.thrift.protocol.TType.STRUCT, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new OutletWeightActivityTagPageReqVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new OutletWeightActivityTagPageReqVoTupleSchemeFactory());
  }

  /**
   * 活动标签id *
   */
  public long activityId; // optional
  /**
   * 客户端ID *
   */
  public String clientId; // optional
  /**
   * 活动编号 *
   */
  public String activityNo; // optional
  /**
   * 分页 *
   */
  public com.froad.thrift.vo.PageVo pageVo; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 活动标签id *
     */
    ACTIVITY_ID((short)1, "activityId"),
    /**
     * 客户端ID *
     */
    CLIENT_ID((short)2, "clientId"),
    /**
     * 活动编号 *
     */
    ACTIVITY_NO((short)3, "activityNo"),
    /**
     * 分页 *
     */
    PAGE_VO((short)4, "pageVo");

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
        case 1: // ACTIVITY_ID
          return ACTIVITY_ID;
        case 2: // CLIENT_ID
          return CLIENT_ID;
        case 3: // ACTIVITY_NO
          return ACTIVITY_NO;
        case 4: // PAGE_VO
          return PAGE_VO;
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
  private static final int __ACTIVITYID_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.ACTIVITY_ID,_Fields.CLIENT_ID,_Fields.ACTIVITY_NO};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ACTIVITY_ID, new org.apache.thrift.meta_data.FieldMetaData("activityId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.CLIENT_ID, new org.apache.thrift.meta_data.FieldMetaData("clientId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ACTIVITY_NO, new org.apache.thrift.meta_data.FieldMetaData("activityNo", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PAGE_VO, new org.apache.thrift.meta_data.FieldMetaData("pageVo", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.PageVo.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(OutletWeightActivityTagPageReqVo.class, metaDataMap);
  }

  public OutletWeightActivityTagPageReqVo() {
  }

  public OutletWeightActivityTagPageReqVo(
    com.froad.thrift.vo.PageVo pageVo)
  {
    this();
    this.pageVo = pageVo;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public OutletWeightActivityTagPageReqVo(OutletWeightActivityTagPageReqVo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.activityId = other.activityId;
    if (other.isSetClientId()) {
      this.clientId = other.clientId;
    }
    if (other.isSetActivityNo()) {
      this.activityNo = other.activityNo;
    }
    if (other.isSetPageVo()) {
      this.pageVo = new com.froad.thrift.vo.PageVo(other.pageVo);
    }
  }

  public OutletWeightActivityTagPageReqVo deepCopy() {
    return new OutletWeightActivityTagPageReqVo(this);
  }

  @Override
  public void clear() {
    setActivityIdIsSet(false);
    this.activityId = 0;
    this.clientId = null;
    this.activityNo = null;
    this.pageVo = null;
  }

  /**
   * 活动标签id *
   */
  public long getActivityId() {
    return this.activityId;
  }

  /**
   * 活动标签id *
   */
  public OutletWeightActivityTagPageReqVo setActivityId(long activityId) {
    this.activityId = activityId;
    setActivityIdIsSet(true);
    return this;
  }

  public void unsetActivityId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ACTIVITYID_ISSET_ID);
  }

  /** Returns true if field activityId is set (has been assigned a value) and false otherwise */
  public boolean isSetActivityId() {
    return EncodingUtils.testBit(__isset_bitfield, __ACTIVITYID_ISSET_ID);
  }

  public void setActivityIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ACTIVITYID_ISSET_ID, value);
  }

  /**
   * 客户端ID *
   */
  public String getClientId() {
    return this.clientId;
  }

  /**
   * 客户端ID *
   */
  public OutletWeightActivityTagPageReqVo setClientId(String clientId) {
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
   * 活动编号 *
   */
  public String getActivityNo() {
    return this.activityNo;
  }

  /**
   * 活动编号 *
   */
  public OutletWeightActivityTagPageReqVo setActivityNo(String activityNo) {
    this.activityNo = activityNo;
    return this;
  }

  public void unsetActivityNo() {
    this.activityNo = null;
  }

  /** Returns true if field activityNo is set (has been assigned a value) and false otherwise */
  public boolean isSetActivityNo() {
    return this.activityNo != null;
  }

  public void setActivityNoIsSet(boolean value) {
    if (!value) {
      this.activityNo = null;
    }
  }

  /**
   * 分页 *
   */
  public com.froad.thrift.vo.PageVo getPageVo() {
    return this.pageVo;
  }

  /**
   * 分页 *
   */
  public OutletWeightActivityTagPageReqVo setPageVo(com.froad.thrift.vo.PageVo pageVo) {
    this.pageVo = pageVo;
    return this;
  }

  public void unsetPageVo() {
    this.pageVo = null;
  }

  /** Returns true if field pageVo is set (has been assigned a value) and false otherwise */
  public boolean isSetPageVo() {
    return this.pageVo != null;
  }

  public void setPageVoIsSet(boolean value) {
    if (!value) {
      this.pageVo = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ACTIVITY_ID:
      if (value == null) {
        unsetActivityId();
      } else {
        setActivityId((Long)value);
      }
      break;

    case CLIENT_ID:
      if (value == null) {
        unsetClientId();
      } else {
        setClientId((String)value);
      }
      break;

    case ACTIVITY_NO:
      if (value == null) {
        unsetActivityNo();
      } else {
        setActivityNo((String)value);
      }
      break;

    case PAGE_VO:
      if (value == null) {
        unsetPageVo();
      } else {
        setPageVo((com.froad.thrift.vo.PageVo)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ACTIVITY_ID:
      return Long.valueOf(getActivityId());

    case CLIENT_ID:
      return getClientId();

    case ACTIVITY_NO:
      return getActivityNo();

    case PAGE_VO:
      return getPageVo();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ACTIVITY_ID:
      return isSetActivityId();
    case CLIENT_ID:
      return isSetClientId();
    case ACTIVITY_NO:
      return isSetActivityNo();
    case PAGE_VO:
      return isSetPageVo();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof OutletWeightActivityTagPageReqVo)
      return this.equals((OutletWeightActivityTagPageReqVo)that);
    return false;
  }

  public boolean equals(OutletWeightActivityTagPageReqVo that) {
    if (that == null)
      return false;

    boolean this_present_activityId = true && this.isSetActivityId();
    boolean that_present_activityId = true && that.isSetActivityId();
    if (this_present_activityId || that_present_activityId) {
      if (!(this_present_activityId && that_present_activityId))
        return false;
      if (this.activityId != that.activityId)
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

    boolean this_present_activityNo = true && this.isSetActivityNo();
    boolean that_present_activityNo = true && that.isSetActivityNo();
    if (this_present_activityNo || that_present_activityNo) {
      if (!(this_present_activityNo && that_present_activityNo))
        return false;
      if (!this.activityNo.equals(that.activityNo))
        return false;
    }

    boolean this_present_pageVo = true && this.isSetPageVo();
    boolean that_present_pageVo = true && that.isSetPageVo();
    if (this_present_pageVo || that_present_pageVo) {
      if (!(this_present_pageVo && that_present_pageVo))
        return false;
      if (!this.pageVo.equals(that.pageVo))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_activityId = true && (isSetActivityId());
    list.add(present_activityId);
    if (present_activityId)
      list.add(activityId);

    boolean present_clientId = true && (isSetClientId());
    list.add(present_clientId);
    if (present_clientId)
      list.add(clientId);

    boolean present_activityNo = true && (isSetActivityNo());
    list.add(present_activityNo);
    if (present_activityNo)
      list.add(activityNo);

    boolean present_pageVo = true && (isSetPageVo());
    list.add(present_pageVo);
    if (present_pageVo)
      list.add(pageVo);

    return list.hashCode();
  }

  @Override
  public int compareTo(OutletWeightActivityTagPageReqVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetActivityId()).compareTo(other.isSetActivityId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetActivityId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.activityId, other.activityId);
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
    lastComparison = Boolean.valueOf(isSetActivityNo()).compareTo(other.isSetActivityNo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetActivityNo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.activityNo, other.activityNo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPageVo()).compareTo(other.isSetPageVo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPageVo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.pageVo, other.pageVo);
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
    StringBuilder sb = new StringBuilder("OutletWeightActivityTagPageReqVo(");
    boolean first = true;

    if (isSetActivityId()) {
      sb.append("activityId:");
      sb.append(this.activityId);
      first = false;
    }
    if (isSetClientId()) {
      if (!first) sb.append(", ");
      sb.append("clientId:");
      if (this.clientId == null) {
        sb.append("null");
      } else {
        sb.append(this.clientId);
      }
      first = false;
    }
    if (isSetActivityNo()) {
      if (!first) sb.append(", ");
      sb.append("activityNo:");
      if (this.activityNo == null) {
        sb.append("null");
      } else {
        sb.append(this.activityNo);
      }
      first = false;
    }
    if (!first) sb.append(", ");
    sb.append("pageVo:");
    if (this.pageVo == null) {
      sb.append("null");
    } else {
      sb.append(this.pageVo);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (pageVo == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'pageVo' was not present! Struct: " + toString());
    }
    // check for sub-struct validity
    if (pageVo != null) {
      pageVo.validate();
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class OutletWeightActivityTagPageReqVoStandardSchemeFactory implements SchemeFactory {
    public OutletWeightActivityTagPageReqVoStandardScheme getScheme() {
      return new OutletWeightActivityTagPageReqVoStandardScheme();
    }
  }

  private static class OutletWeightActivityTagPageReqVoStandardScheme extends StandardScheme<OutletWeightActivityTagPageReqVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, OutletWeightActivityTagPageReqVo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ACTIVITY_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.activityId = iprot.readI64();
              struct.setActivityIdIsSet(true);
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
          case 3: // ACTIVITY_NO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.activityNo = iprot.readString();
              struct.setActivityNoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // PAGE_VO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.pageVo = new com.froad.thrift.vo.PageVo();
              struct.pageVo.read(iprot);
              struct.setPageVoIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, OutletWeightActivityTagPageReqVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetActivityId()) {
        oprot.writeFieldBegin(ACTIVITY_ID_FIELD_DESC);
        oprot.writeI64(struct.activityId);
        oprot.writeFieldEnd();
      }
      if (struct.clientId != null) {
        if (struct.isSetClientId()) {
          oprot.writeFieldBegin(CLIENT_ID_FIELD_DESC);
          oprot.writeString(struct.clientId);
          oprot.writeFieldEnd();
        }
      }
      if (struct.activityNo != null) {
        if (struct.isSetActivityNo()) {
          oprot.writeFieldBegin(ACTIVITY_NO_FIELD_DESC);
          oprot.writeString(struct.activityNo);
          oprot.writeFieldEnd();
        }
      }
      if (struct.pageVo != null) {
        oprot.writeFieldBegin(PAGE_VO_FIELD_DESC);
        struct.pageVo.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class OutletWeightActivityTagPageReqVoTupleSchemeFactory implements SchemeFactory {
    public OutletWeightActivityTagPageReqVoTupleScheme getScheme() {
      return new OutletWeightActivityTagPageReqVoTupleScheme();
    }
  }

  private static class OutletWeightActivityTagPageReqVoTupleScheme extends TupleScheme<OutletWeightActivityTagPageReqVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, OutletWeightActivityTagPageReqVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      struct.pageVo.write(oprot);
      BitSet optionals = new BitSet();
      if (struct.isSetActivityId()) {
        optionals.set(0);
      }
      if (struct.isSetClientId()) {
        optionals.set(1);
      }
      if (struct.isSetActivityNo()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetActivityId()) {
        oprot.writeI64(struct.activityId);
      }
      if (struct.isSetClientId()) {
        oprot.writeString(struct.clientId);
      }
      if (struct.isSetActivityNo()) {
        oprot.writeString(struct.activityNo);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, OutletWeightActivityTagPageReqVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.pageVo = new com.froad.thrift.vo.PageVo();
      struct.pageVo.read(iprot);
      struct.setPageVoIsSet(true);
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.activityId = iprot.readI64();
        struct.setActivityIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.clientId = iprot.readString();
        struct.setClientIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.activityNo = iprot.readString();
        struct.setActivityNoIsSet(true);
      }
    }
  }

}
