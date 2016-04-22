/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.froad.thrift.vo.recommendactivitytag;

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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2015-10-28")
public class InputRelateMerchantActivityVo implements org.apache.thrift.TBase<InputRelateMerchantActivityVo, InputRelateMerchantActivityVo._Fields>, java.io.Serializable, Cloneable, Comparable<InputRelateMerchantActivityVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("InputRelateMerchantActivityVo");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField MERCHANT_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("merchantName", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField MERCHANT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("merchantId", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField WEIGHT_FIELD_DESC = new org.apache.thrift.protocol.TField("weight", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField ACTIVITY_NO_FIELD_DESC = new org.apache.thrift.protocol.TField("activityNo", org.apache.thrift.protocol.TType.STRING, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new InputRelateMerchantActivityVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new InputRelateMerchantActivityVoTupleSchemeFactory());
  }

  /**
   * 序号 *
   */
  public long id; // optional
  /**
   * 商户名称 *
   */
  public String merchantName; // optional
  /**
   * 商户id *
   */
  public String merchantId; // optional
  /**
   * 权重 *
   */
  public String weight; // optional
  /**
   * 活动编号 *
   */
  public String activityNo; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 序号 *
     */
    ID((short)1, "id"),
    /**
     * 商户名称 *
     */
    MERCHANT_NAME((short)2, "merchantName"),
    /**
     * 商户id *
     */
    MERCHANT_ID((short)3, "merchantId"),
    /**
     * 权重 *
     */
    WEIGHT((short)4, "weight"),
    /**
     * 活动编号 *
     */
    ACTIVITY_NO((short)5, "activityNo");

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
        case 1: // ID
          return ID;
        case 2: // MERCHANT_NAME
          return MERCHANT_NAME;
        case 3: // MERCHANT_ID
          return MERCHANT_ID;
        case 4: // WEIGHT
          return WEIGHT;
        case 5: // ACTIVITY_NO
          return ACTIVITY_NO;
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
  private static final int __ID_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.ID,_Fields.MERCHANT_NAME,_Fields.MERCHANT_ID,_Fields.WEIGHT,_Fields.ACTIVITY_NO};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.MERCHANT_NAME, new org.apache.thrift.meta_data.FieldMetaData("merchantName", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.MERCHANT_ID, new org.apache.thrift.meta_data.FieldMetaData("merchantId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.WEIGHT, new org.apache.thrift.meta_data.FieldMetaData("weight", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ACTIVITY_NO, new org.apache.thrift.meta_data.FieldMetaData("activityNo", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(InputRelateMerchantActivityVo.class, metaDataMap);
  }

  public InputRelateMerchantActivityVo() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public InputRelateMerchantActivityVo(InputRelateMerchantActivityVo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    if (other.isSetMerchantName()) {
      this.merchantName = other.merchantName;
    }
    if (other.isSetMerchantId()) {
      this.merchantId = other.merchantId;
    }
    if (other.isSetWeight()) {
      this.weight = other.weight;
    }
    if (other.isSetActivityNo()) {
      this.activityNo = other.activityNo;
    }
  }

  public InputRelateMerchantActivityVo deepCopy() {
    return new InputRelateMerchantActivityVo(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    this.merchantName = null;
    this.merchantId = null;
    this.weight = null;
    this.activityNo = null;
  }

  /**
   * 序号 *
   */
  public long getId() {
    return this.id;
  }

  /**
   * 序号 *
   */
  public InputRelateMerchantActivityVo setId(long id) {
    this.id = id;
    setIdIsSet(true);
    return this;
  }

  public void unsetId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ID_ISSET_ID);
  }

  /** Returns true if field id is set (has been assigned a value) and false otherwise */
  public boolean isSetId() {
    return EncodingUtils.testBit(__isset_bitfield, __ID_ISSET_ID);
  }

  public void setIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ID_ISSET_ID, value);
  }

  /**
   * 商户名称 *
   */
  public String getMerchantName() {
    return this.merchantName;
  }

  /**
   * 商户名称 *
   */
  public InputRelateMerchantActivityVo setMerchantName(String merchantName) {
    this.merchantName = merchantName;
    return this;
  }

  public void unsetMerchantName() {
    this.merchantName = null;
  }

  /** Returns true if field merchantName is set (has been assigned a value) and false otherwise */
  public boolean isSetMerchantName() {
    return this.merchantName != null;
  }

  public void setMerchantNameIsSet(boolean value) {
    if (!value) {
      this.merchantName = null;
    }
  }

  /**
   * 商户id *
   */
  public String getMerchantId() {
    return this.merchantId;
  }

  /**
   * 商户id *
   */
  public InputRelateMerchantActivityVo setMerchantId(String merchantId) {
    this.merchantId = merchantId;
    return this;
  }

  public void unsetMerchantId() {
    this.merchantId = null;
  }

  /** Returns true if field merchantId is set (has been assigned a value) and false otherwise */
  public boolean isSetMerchantId() {
    return this.merchantId != null;
  }

  public void setMerchantIdIsSet(boolean value) {
    if (!value) {
      this.merchantId = null;
    }
  }

  /**
   * 权重 *
   */
  public String getWeight() {
    return this.weight;
  }

  /**
   * 权重 *
   */
  public InputRelateMerchantActivityVo setWeight(String weight) {
    this.weight = weight;
    return this;
  }

  public void unsetWeight() {
    this.weight = null;
  }

  /** Returns true if field weight is set (has been assigned a value) and false otherwise */
  public boolean isSetWeight() {
    return this.weight != null;
  }

  public void setWeightIsSet(boolean value) {
    if (!value) {
      this.weight = null;
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
  public InputRelateMerchantActivityVo setActivityNo(String activityNo) {
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

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((Long)value);
      }
      break;

    case MERCHANT_NAME:
      if (value == null) {
        unsetMerchantName();
      } else {
        setMerchantName((String)value);
      }
      break;

    case MERCHANT_ID:
      if (value == null) {
        unsetMerchantId();
      } else {
        setMerchantId((String)value);
      }
      break;

    case WEIGHT:
      if (value == null) {
        unsetWeight();
      } else {
        setWeight((String)value);
      }
      break;

    case ACTIVITY_NO:
      if (value == null) {
        unsetActivityNo();
      } else {
        setActivityNo((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return Long.valueOf(getId());

    case MERCHANT_NAME:
      return getMerchantName();

    case MERCHANT_ID:
      return getMerchantId();

    case WEIGHT:
      return getWeight();

    case ACTIVITY_NO:
      return getActivityNo();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ID:
      return isSetId();
    case MERCHANT_NAME:
      return isSetMerchantName();
    case MERCHANT_ID:
      return isSetMerchantId();
    case WEIGHT:
      return isSetWeight();
    case ACTIVITY_NO:
      return isSetActivityNo();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof InputRelateMerchantActivityVo)
      return this.equals((InputRelateMerchantActivityVo)that);
    return false;
  }

  public boolean equals(InputRelateMerchantActivityVo that) {
    if (that == null)
      return false;

    boolean this_present_id = true && this.isSetId();
    boolean that_present_id = true && that.isSetId();
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (this.id != that.id)
        return false;
    }

    boolean this_present_merchantName = true && this.isSetMerchantName();
    boolean that_present_merchantName = true && that.isSetMerchantName();
    if (this_present_merchantName || that_present_merchantName) {
      if (!(this_present_merchantName && that_present_merchantName))
        return false;
      if (!this.merchantName.equals(that.merchantName))
        return false;
    }

    boolean this_present_merchantId = true && this.isSetMerchantId();
    boolean that_present_merchantId = true && that.isSetMerchantId();
    if (this_present_merchantId || that_present_merchantId) {
      if (!(this_present_merchantId && that_present_merchantId))
        return false;
      if (!this.merchantId.equals(that.merchantId))
        return false;
    }

    boolean this_present_weight = true && this.isSetWeight();
    boolean that_present_weight = true && that.isSetWeight();
    if (this_present_weight || that_present_weight) {
      if (!(this_present_weight && that_present_weight))
        return false;
      if (!this.weight.equals(that.weight))
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

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_id = true && (isSetId());
    list.add(present_id);
    if (present_id)
      list.add(id);

    boolean present_merchantName = true && (isSetMerchantName());
    list.add(present_merchantName);
    if (present_merchantName)
      list.add(merchantName);

    boolean present_merchantId = true && (isSetMerchantId());
    list.add(present_merchantId);
    if (present_merchantId)
      list.add(merchantId);

    boolean present_weight = true && (isSetWeight());
    list.add(present_weight);
    if (present_weight)
      list.add(weight);

    boolean present_activityNo = true && (isSetActivityNo());
    list.add(present_activityNo);
    if (present_activityNo)
      list.add(activityNo);

    return list.hashCode();
  }

  @Override
  public int compareTo(InputRelateMerchantActivityVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetId()).compareTo(other.isSetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id, other.id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMerchantName()).compareTo(other.isSetMerchantName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMerchantName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.merchantName, other.merchantName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMerchantId()).compareTo(other.isSetMerchantId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMerchantId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.merchantId, other.merchantId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetWeight()).compareTo(other.isSetWeight());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetWeight()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.weight, other.weight);
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
    StringBuilder sb = new StringBuilder("InputRelateMerchantActivityVo(");
    boolean first = true;

    if (isSetId()) {
      sb.append("id:");
      sb.append(this.id);
      first = false;
    }
    if (isSetMerchantName()) {
      if (!first) sb.append(", ");
      sb.append("merchantName:");
      if (this.merchantName == null) {
        sb.append("null");
      } else {
        sb.append(this.merchantName);
      }
      first = false;
    }
    if (isSetMerchantId()) {
      if (!first) sb.append(", ");
      sb.append("merchantId:");
      if (this.merchantId == null) {
        sb.append("null");
      } else {
        sb.append(this.merchantId);
      }
      first = false;
    }
    if (isSetWeight()) {
      if (!first) sb.append(", ");
      sb.append("weight:");
      if (this.weight == null) {
        sb.append("null");
      } else {
        sb.append(this.weight);
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

  private static class InputRelateMerchantActivityVoStandardSchemeFactory implements SchemeFactory {
    public InputRelateMerchantActivityVoStandardScheme getScheme() {
      return new InputRelateMerchantActivityVoStandardScheme();
    }
  }

  private static class InputRelateMerchantActivityVoStandardScheme extends StandardScheme<InputRelateMerchantActivityVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, InputRelateMerchantActivityVo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.id = iprot.readI64();
              struct.setIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // MERCHANT_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.merchantName = iprot.readString();
              struct.setMerchantNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // MERCHANT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.merchantId = iprot.readString();
              struct.setMerchantIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // WEIGHT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.weight = iprot.readString();
              struct.setWeightIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // ACTIVITY_NO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.activityNo = iprot.readString();
              struct.setActivityNoIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, InputRelateMerchantActivityVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetId()) {
        oprot.writeFieldBegin(ID_FIELD_DESC);
        oprot.writeI64(struct.id);
        oprot.writeFieldEnd();
      }
      if (struct.merchantName != null) {
        if (struct.isSetMerchantName()) {
          oprot.writeFieldBegin(MERCHANT_NAME_FIELD_DESC);
          oprot.writeString(struct.merchantName);
          oprot.writeFieldEnd();
        }
      }
      if (struct.merchantId != null) {
        if (struct.isSetMerchantId()) {
          oprot.writeFieldBegin(MERCHANT_ID_FIELD_DESC);
          oprot.writeString(struct.merchantId);
          oprot.writeFieldEnd();
        }
      }
      if (struct.weight != null) {
        if (struct.isSetWeight()) {
          oprot.writeFieldBegin(WEIGHT_FIELD_DESC);
          oprot.writeString(struct.weight);
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
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class InputRelateMerchantActivityVoTupleSchemeFactory implements SchemeFactory {
    public InputRelateMerchantActivityVoTupleScheme getScheme() {
      return new InputRelateMerchantActivityVoTupleScheme();
    }
  }

  private static class InputRelateMerchantActivityVoTupleScheme extends TupleScheme<InputRelateMerchantActivityVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, InputRelateMerchantActivityVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetMerchantName()) {
        optionals.set(1);
      }
      if (struct.isSetMerchantId()) {
        optionals.set(2);
      }
      if (struct.isSetWeight()) {
        optionals.set(3);
      }
      if (struct.isSetActivityNo()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetId()) {
        oprot.writeI64(struct.id);
      }
      if (struct.isSetMerchantName()) {
        oprot.writeString(struct.merchantName);
      }
      if (struct.isSetMerchantId()) {
        oprot.writeString(struct.merchantId);
      }
      if (struct.isSetWeight()) {
        oprot.writeString(struct.weight);
      }
      if (struct.isSetActivityNo()) {
        oprot.writeString(struct.activityNo);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, InputRelateMerchantActivityVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.id = iprot.readI64();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.merchantName = iprot.readString();
        struct.setMerchantNameIsSet(true);
      }
      if (incoming.get(2)) {
        struct.merchantId = iprot.readString();
        struct.setMerchantIdIsSet(true);
      }
      if (incoming.get(3)) {
        struct.weight = iprot.readString();
        struct.setWeightIsSet(true);
      }
      if (incoming.get(4)) {
        struct.activityNo = iprot.readString();
        struct.setActivityNoIsSet(true);
      }
    }
  }

}

