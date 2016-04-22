/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.froad.thrift.vo;

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
 * createInstance接口请求对象
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class CreateInstanceReqVo implements org.apache.thrift.TBase<CreateInstanceReqVo, CreateInstanceReqVo._Fields>, java.io.Serializable, Cloneable, Comparable<CreateInstanceReqVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CreateInstanceReqVo");

  private static final org.apache.thrift.protocol.TField ORIGIN_FIELD_DESC = new org.apache.thrift.protocol.TField("origin", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField BESS_DATA_FIELD_DESC = new org.apache.thrift.protocol.TField("bessData", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField BESS_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("bessId", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField ORG_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("orgCode", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField PROCESS_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("processType", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField PROCESS_TYPE_DETAIL_FIELD_DESC = new org.apache.thrift.protocol.TField("processTypeDetail", org.apache.thrift.protocol.TType.STRING, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new CreateInstanceReqVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new CreateInstanceReqVoTupleSchemeFactory());
  }

  public com.froad.thrift.vo.OriginVo origin; // required
  /**
   * 业务数据json对象
   */
  public String bessData; // required
  /**
   * 业务Id（门店、商品、商户）的Id
   */
  public String bessId; // required
  /**
   * 所属商户的发展机构
   */
  public String orgCode; // required
  /**
   * 流程类型:1-商户,2-门店,3-团购商品,4-预售商品,5-名优特惠,6-在线积分兑换,7-网点礼品
   */
  public String processType; // required
  /**
   * 类型详情:0-新增,1-更新
   */
  public String processTypeDetail; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ORIGIN((short)1, "origin"),
    /**
     * 业务数据json对象
     */
    BESS_DATA((short)2, "bessData"),
    /**
     * 业务Id（门店、商品、商户）的Id
     */
    BESS_ID((short)3, "bessId"),
    /**
     * 所属商户的发展机构
     */
    ORG_CODE((short)4, "orgCode"),
    /**
     * 流程类型:1-商户,2-门店,3-团购商品,4-预售商品,5-名优特惠,6-在线积分兑换,7-网点礼品
     */
    PROCESS_TYPE((short)5, "processType"),
    /**
     * 类型详情:0-新增,1-更新
     */
    PROCESS_TYPE_DETAIL((short)6, "processTypeDetail");

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
        case 1: // ORIGIN
          return ORIGIN;
        case 2: // BESS_DATA
          return BESS_DATA;
        case 3: // BESS_ID
          return BESS_ID;
        case 4: // ORG_CODE
          return ORG_CODE;
        case 5: // PROCESS_TYPE
          return PROCESS_TYPE;
        case 6: // PROCESS_TYPE_DETAIL
          return PROCESS_TYPE_DETAIL;
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
    tmpMap.put(_Fields.ORIGIN, new org.apache.thrift.meta_data.FieldMetaData("origin", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.OriginVo.class)));
    tmpMap.put(_Fields.BESS_DATA, new org.apache.thrift.meta_data.FieldMetaData("bessData", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.BESS_ID, new org.apache.thrift.meta_data.FieldMetaData("bessId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ORG_CODE, new org.apache.thrift.meta_data.FieldMetaData("orgCode", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PROCESS_TYPE, new org.apache.thrift.meta_data.FieldMetaData("processType", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PROCESS_TYPE_DETAIL, new org.apache.thrift.meta_data.FieldMetaData("processTypeDetail", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CreateInstanceReqVo.class, metaDataMap);
  }

  public CreateInstanceReqVo() {
  }

  public CreateInstanceReqVo(
    com.froad.thrift.vo.OriginVo origin,
    String bessData,
    String bessId,
    String orgCode,
    String processType,
    String processTypeDetail)
  {
    this();
    this.origin = origin;
    this.bessData = bessData;
    this.bessId = bessId;
    this.orgCode = orgCode;
    this.processType = processType;
    this.processTypeDetail = processTypeDetail;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CreateInstanceReqVo(CreateInstanceReqVo other) {
    if (other.isSetOrigin()) {
      this.origin = new com.froad.thrift.vo.OriginVo(other.origin);
    }
    if (other.isSetBessData()) {
      this.bessData = other.bessData;
    }
    if (other.isSetBessId()) {
      this.bessId = other.bessId;
    }
    if (other.isSetOrgCode()) {
      this.orgCode = other.orgCode;
    }
    if (other.isSetProcessType()) {
      this.processType = other.processType;
    }
    if (other.isSetProcessTypeDetail()) {
      this.processTypeDetail = other.processTypeDetail;
    }
  }

  public CreateInstanceReqVo deepCopy() {
    return new CreateInstanceReqVo(this);
  }

  @Override
  public void clear() {
    this.origin = null;
    this.bessData = null;
    this.bessId = null;
    this.orgCode = null;
    this.processType = null;
    this.processTypeDetail = null;
  }

  public com.froad.thrift.vo.OriginVo getOrigin() {
    return this.origin;
  }

  public CreateInstanceReqVo setOrigin(com.froad.thrift.vo.OriginVo origin) {
    this.origin = origin;
    return this;
  }

  public void unsetOrigin() {
    this.origin = null;
  }

  /** Returns true if field origin is set (has been assigned a value) and false otherwise */
  public boolean isSetOrigin() {
    return this.origin != null;
  }

  public void setOriginIsSet(boolean value) {
    if (!value) {
      this.origin = null;
    }
  }

  /**
   * 业务数据json对象
   */
  public String getBessData() {
    return this.bessData;
  }

  /**
   * 业务数据json对象
   */
  public CreateInstanceReqVo setBessData(String bessData) {
    this.bessData = bessData;
    return this;
  }

  public void unsetBessData() {
    this.bessData = null;
  }

  /** Returns true if field bessData is set (has been assigned a value) and false otherwise */
  public boolean isSetBessData() {
    return this.bessData != null;
  }

  public void setBessDataIsSet(boolean value) {
    if (!value) {
      this.bessData = null;
    }
  }

  /**
   * 业务Id（门店、商品、商户）的Id
   */
  public String getBessId() {
    return this.bessId;
  }

  /**
   * 业务Id（门店、商品、商户）的Id
   */
  public CreateInstanceReqVo setBessId(String bessId) {
    this.bessId = bessId;
    return this;
  }

  public void unsetBessId() {
    this.bessId = null;
  }

  /** Returns true if field bessId is set (has been assigned a value) and false otherwise */
  public boolean isSetBessId() {
    return this.bessId != null;
  }

  public void setBessIdIsSet(boolean value) {
    if (!value) {
      this.bessId = null;
    }
  }

  /**
   * 所属商户的发展机构
   */
  public String getOrgCode() {
    return this.orgCode;
  }

  /**
   * 所属商户的发展机构
   */
  public CreateInstanceReqVo setOrgCode(String orgCode) {
    this.orgCode = orgCode;
    return this;
  }

  public void unsetOrgCode() {
    this.orgCode = null;
  }

  /** Returns true if field orgCode is set (has been assigned a value) and false otherwise */
  public boolean isSetOrgCode() {
    return this.orgCode != null;
  }

  public void setOrgCodeIsSet(boolean value) {
    if (!value) {
      this.orgCode = null;
    }
  }

  /**
   * 流程类型:1-商户,2-门店,3-团购商品,4-预售商品,5-名优特惠,6-在线积分兑换,7-网点礼品
   */
  public String getProcessType() {
    return this.processType;
  }

  /**
   * 流程类型:1-商户,2-门店,3-团购商品,4-预售商品,5-名优特惠,6-在线积分兑换,7-网点礼品
   */
  public CreateInstanceReqVo setProcessType(String processType) {
    this.processType = processType;
    return this;
  }

  public void unsetProcessType() {
    this.processType = null;
  }

  /** Returns true if field processType is set (has been assigned a value) and false otherwise */
  public boolean isSetProcessType() {
    return this.processType != null;
  }

  public void setProcessTypeIsSet(boolean value) {
    if (!value) {
      this.processType = null;
    }
  }

  /**
   * 类型详情:0-新增,1-更新
   */
  public String getProcessTypeDetail() {
    return this.processTypeDetail;
  }

  /**
   * 类型详情:0-新增,1-更新
   */
  public CreateInstanceReqVo setProcessTypeDetail(String processTypeDetail) {
    this.processTypeDetail = processTypeDetail;
    return this;
  }

  public void unsetProcessTypeDetail() {
    this.processTypeDetail = null;
  }

  /** Returns true if field processTypeDetail is set (has been assigned a value) and false otherwise */
  public boolean isSetProcessTypeDetail() {
    return this.processTypeDetail != null;
  }

  public void setProcessTypeDetailIsSet(boolean value) {
    if (!value) {
      this.processTypeDetail = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ORIGIN:
      if (value == null) {
        unsetOrigin();
      } else {
        setOrigin((com.froad.thrift.vo.OriginVo)value);
      }
      break;

    case BESS_DATA:
      if (value == null) {
        unsetBessData();
      } else {
        setBessData((String)value);
      }
      break;

    case BESS_ID:
      if (value == null) {
        unsetBessId();
      } else {
        setBessId((String)value);
      }
      break;

    case ORG_CODE:
      if (value == null) {
        unsetOrgCode();
      } else {
        setOrgCode((String)value);
      }
      break;

    case PROCESS_TYPE:
      if (value == null) {
        unsetProcessType();
      } else {
        setProcessType((String)value);
      }
      break;

    case PROCESS_TYPE_DETAIL:
      if (value == null) {
        unsetProcessTypeDetail();
      } else {
        setProcessTypeDetail((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ORIGIN:
      return getOrigin();

    case BESS_DATA:
      return getBessData();

    case BESS_ID:
      return getBessId();

    case ORG_CODE:
      return getOrgCode();

    case PROCESS_TYPE:
      return getProcessType();

    case PROCESS_TYPE_DETAIL:
      return getProcessTypeDetail();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ORIGIN:
      return isSetOrigin();
    case BESS_DATA:
      return isSetBessData();
    case BESS_ID:
      return isSetBessId();
    case ORG_CODE:
      return isSetOrgCode();
    case PROCESS_TYPE:
      return isSetProcessType();
    case PROCESS_TYPE_DETAIL:
      return isSetProcessTypeDetail();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof CreateInstanceReqVo)
      return this.equals((CreateInstanceReqVo)that);
    return false;
  }

  public boolean equals(CreateInstanceReqVo that) {
    if (that == null)
      return false;

    boolean this_present_origin = true && this.isSetOrigin();
    boolean that_present_origin = true && that.isSetOrigin();
    if (this_present_origin || that_present_origin) {
      if (!(this_present_origin && that_present_origin))
        return false;
      if (!this.origin.equals(that.origin))
        return false;
    }

    boolean this_present_bessData = true && this.isSetBessData();
    boolean that_present_bessData = true && that.isSetBessData();
    if (this_present_bessData || that_present_bessData) {
      if (!(this_present_bessData && that_present_bessData))
        return false;
      if (!this.bessData.equals(that.bessData))
        return false;
    }

    boolean this_present_bessId = true && this.isSetBessId();
    boolean that_present_bessId = true && that.isSetBessId();
    if (this_present_bessId || that_present_bessId) {
      if (!(this_present_bessId && that_present_bessId))
        return false;
      if (!this.bessId.equals(that.bessId))
        return false;
    }

    boolean this_present_orgCode = true && this.isSetOrgCode();
    boolean that_present_orgCode = true && that.isSetOrgCode();
    if (this_present_orgCode || that_present_orgCode) {
      if (!(this_present_orgCode && that_present_orgCode))
        return false;
      if (!this.orgCode.equals(that.orgCode))
        return false;
    }

    boolean this_present_processType = true && this.isSetProcessType();
    boolean that_present_processType = true && that.isSetProcessType();
    if (this_present_processType || that_present_processType) {
      if (!(this_present_processType && that_present_processType))
        return false;
      if (!this.processType.equals(that.processType))
        return false;
    }

    boolean this_present_processTypeDetail = true && this.isSetProcessTypeDetail();
    boolean that_present_processTypeDetail = true && that.isSetProcessTypeDetail();
    if (this_present_processTypeDetail || that_present_processTypeDetail) {
      if (!(this_present_processTypeDetail && that_present_processTypeDetail))
        return false;
      if (!this.processTypeDetail.equals(that.processTypeDetail))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_origin = true && (isSetOrigin());
    list.add(present_origin);
    if (present_origin)
      list.add(origin);

    boolean present_bessData = true && (isSetBessData());
    list.add(present_bessData);
    if (present_bessData)
      list.add(bessData);

    boolean present_bessId = true && (isSetBessId());
    list.add(present_bessId);
    if (present_bessId)
      list.add(bessId);

    boolean present_orgCode = true && (isSetOrgCode());
    list.add(present_orgCode);
    if (present_orgCode)
      list.add(orgCode);

    boolean present_processType = true && (isSetProcessType());
    list.add(present_processType);
    if (present_processType)
      list.add(processType);

    boolean present_processTypeDetail = true && (isSetProcessTypeDetail());
    list.add(present_processTypeDetail);
    if (present_processTypeDetail)
      list.add(processTypeDetail);

    return list.hashCode();
  }

  @Override
  public int compareTo(CreateInstanceReqVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetOrigin()).compareTo(other.isSetOrigin());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOrigin()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.origin, other.origin);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBessData()).compareTo(other.isSetBessData());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBessData()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.bessData, other.bessData);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBessId()).compareTo(other.isSetBessId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBessId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.bessId, other.bessId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOrgCode()).compareTo(other.isSetOrgCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOrgCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.orgCode, other.orgCode);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetProcessType()).compareTo(other.isSetProcessType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProcessType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.processType, other.processType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetProcessTypeDetail()).compareTo(other.isSetProcessTypeDetail());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProcessTypeDetail()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.processTypeDetail, other.processTypeDetail);
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
    StringBuilder sb = new StringBuilder("CreateInstanceReqVo(");
    boolean first = true;

    sb.append("origin:");
    if (this.origin == null) {
      sb.append("null");
    } else {
      sb.append(this.origin);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("bessData:");
    if (this.bessData == null) {
      sb.append("null");
    } else {
      sb.append(this.bessData);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("bessId:");
    if (this.bessId == null) {
      sb.append("null");
    } else {
      sb.append(this.bessId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("orgCode:");
    if (this.orgCode == null) {
      sb.append("null");
    } else {
      sb.append(this.orgCode);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("processType:");
    if (this.processType == null) {
      sb.append("null");
    } else {
      sb.append(this.processType);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("processTypeDetail:");
    if (this.processTypeDetail == null) {
      sb.append("null");
    } else {
      sb.append(this.processTypeDetail);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (origin != null) {
      origin.validate();
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

  private static class CreateInstanceReqVoStandardSchemeFactory implements SchemeFactory {
    public CreateInstanceReqVoStandardScheme getScheme() {
      return new CreateInstanceReqVoStandardScheme();
    }
  }

  private static class CreateInstanceReqVoStandardScheme extends StandardScheme<CreateInstanceReqVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, CreateInstanceReqVo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ORIGIN
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.origin = new com.froad.thrift.vo.OriginVo();
              struct.origin.read(iprot);
              struct.setOriginIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // BESS_DATA
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.bessData = iprot.readString();
              struct.setBessDataIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // BESS_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.bessId = iprot.readString();
              struct.setBessIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // ORG_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.orgCode = iprot.readString();
              struct.setOrgCodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // PROCESS_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.processType = iprot.readString();
              struct.setProcessTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // PROCESS_TYPE_DETAIL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.processTypeDetail = iprot.readString();
              struct.setProcessTypeDetailIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, CreateInstanceReqVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.origin != null) {
        oprot.writeFieldBegin(ORIGIN_FIELD_DESC);
        struct.origin.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.bessData != null) {
        oprot.writeFieldBegin(BESS_DATA_FIELD_DESC);
        oprot.writeString(struct.bessData);
        oprot.writeFieldEnd();
      }
      if (struct.bessId != null) {
        oprot.writeFieldBegin(BESS_ID_FIELD_DESC);
        oprot.writeString(struct.bessId);
        oprot.writeFieldEnd();
      }
      if (struct.orgCode != null) {
        oprot.writeFieldBegin(ORG_CODE_FIELD_DESC);
        oprot.writeString(struct.orgCode);
        oprot.writeFieldEnd();
      }
      if (struct.processType != null) {
        oprot.writeFieldBegin(PROCESS_TYPE_FIELD_DESC);
        oprot.writeString(struct.processType);
        oprot.writeFieldEnd();
      }
      if (struct.processTypeDetail != null) {
        oprot.writeFieldBegin(PROCESS_TYPE_DETAIL_FIELD_DESC);
        oprot.writeString(struct.processTypeDetail);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CreateInstanceReqVoTupleSchemeFactory implements SchemeFactory {
    public CreateInstanceReqVoTupleScheme getScheme() {
      return new CreateInstanceReqVoTupleScheme();
    }
  }

  private static class CreateInstanceReqVoTupleScheme extends TupleScheme<CreateInstanceReqVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, CreateInstanceReqVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetOrigin()) {
        optionals.set(0);
      }
      if (struct.isSetBessData()) {
        optionals.set(1);
      }
      if (struct.isSetBessId()) {
        optionals.set(2);
      }
      if (struct.isSetOrgCode()) {
        optionals.set(3);
      }
      if (struct.isSetProcessType()) {
        optionals.set(4);
      }
      if (struct.isSetProcessTypeDetail()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetOrigin()) {
        struct.origin.write(oprot);
      }
      if (struct.isSetBessData()) {
        oprot.writeString(struct.bessData);
      }
      if (struct.isSetBessId()) {
        oprot.writeString(struct.bessId);
      }
      if (struct.isSetOrgCode()) {
        oprot.writeString(struct.orgCode);
      }
      if (struct.isSetProcessType()) {
        oprot.writeString(struct.processType);
      }
      if (struct.isSetProcessTypeDetail()) {
        oprot.writeString(struct.processTypeDetail);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, CreateInstanceReqVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.origin = new com.froad.thrift.vo.OriginVo();
        struct.origin.read(iprot);
        struct.setOriginIsSet(true);
      }
      if (incoming.get(1)) {
        struct.bessData = iprot.readString();
        struct.setBessDataIsSet(true);
      }
      if (incoming.get(2)) {
        struct.bessId = iprot.readString();
        struct.setBessIdIsSet(true);
      }
      if (incoming.get(3)) {
        struct.orgCode = iprot.readString();
        struct.setOrgCodeIsSet(true);
      }
      if (incoming.get(4)) {
        struct.processType = iprot.readString();
        struct.setProcessTypeIsSet(true);
      }
      if (incoming.get(5)) {
        struct.processTypeDetail = iprot.readString();
        struct.setProcessTypeDetailIsSet(true);
      }
    }
  }

}

