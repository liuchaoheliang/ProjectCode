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
 * 关联门店
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2015-10-29")
public class RelateOutletActivityVo implements org.apache.thrift.TBase<RelateOutletActivityVo, RelateOutletActivityVo._Fields>, java.io.Serializable, Cloneable, Comparable<RelateOutletActivityVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("RelateOutletActivityVo");

  private static final org.apache.thrift.protocol.TField OUTLET_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("outletId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField WEIGHT_FIELD_DESC = new org.apache.thrift.protocol.TField("weight", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField ACTIVITY_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("activityId", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField ACTIVITY_NO_FIELD_DESC = new org.apache.thrift.protocol.TField("activityNo", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField CLIENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("clientId", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField OPERATOR_FIELD_DESC = new org.apache.thrift.protocol.TField("operator", org.apache.thrift.protocol.TType.STRING, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new RelateOutletActivityVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new RelateOutletActivityVoTupleSchemeFactory());
  }

  /**
   * 门店id *
   */
  public String outletId; // optional
  /**
   * 权重 *
   */
  public String weight; // optional
  /**
   * 活动标签id *
   */
  public long activityId; // optional
  /**
   * 活动编号 *
   */
  public String activityNo; // optional
  /**
   * 客户端号 *
   */
  public String clientId; // optional
  /**
   * 当前操作员 *
   */
  public String operator; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 门店id *
     */
    OUTLET_ID((short)1, "outletId"),
    /**
     * 权重 *
     */
    WEIGHT((short)2, "weight"),
    /**
     * 活动标签id *
     */
    ACTIVITY_ID((short)3, "activityId"),
    /**
     * 活动编号 *
     */
    ACTIVITY_NO((short)4, "activityNo"),
    /**
     * 客户端号 *
     */
    CLIENT_ID((short)5, "clientId"),
    /**
     * 当前操作员 *
     */
    OPERATOR((short)6, "operator");

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
        case 1: // OUTLET_ID
          return OUTLET_ID;
        case 2: // WEIGHT
          return WEIGHT;
        case 3: // ACTIVITY_ID
          return ACTIVITY_ID;
        case 4: // ACTIVITY_NO
          return ACTIVITY_NO;
        case 5: // CLIENT_ID
          return CLIENT_ID;
        case 6: // OPERATOR
          return OPERATOR;
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
  private static final _Fields optionals[] = {_Fields.OUTLET_ID,_Fields.WEIGHT,_Fields.ACTIVITY_ID,_Fields.ACTIVITY_NO,_Fields.CLIENT_ID,_Fields.OPERATOR};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.OUTLET_ID, new org.apache.thrift.meta_data.FieldMetaData("outletId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.WEIGHT, new org.apache.thrift.meta_data.FieldMetaData("weight", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ACTIVITY_ID, new org.apache.thrift.meta_data.FieldMetaData("activityId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.ACTIVITY_NO, new org.apache.thrift.meta_data.FieldMetaData("activityNo", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CLIENT_ID, new org.apache.thrift.meta_data.FieldMetaData("clientId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.OPERATOR, new org.apache.thrift.meta_data.FieldMetaData("operator", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(RelateOutletActivityVo.class, metaDataMap);
  }

  public RelateOutletActivityVo() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public RelateOutletActivityVo(RelateOutletActivityVo other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetOutletId()) {
      this.outletId = other.outletId;
    }
    if (other.isSetWeight()) {
      this.weight = other.weight;
    }
    this.activityId = other.activityId;
    if (other.isSetActivityNo()) {
      this.activityNo = other.activityNo;
    }
    if (other.isSetClientId()) {
      this.clientId = other.clientId;
    }
    if (other.isSetOperator()) {
      this.operator = other.operator;
    }
  }

  public RelateOutletActivityVo deepCopy() {
    return new RelateOutletActivityVo(this);
  }

  @Override
  public void clear() {
    this.outletId = null;
    this.weight = null;
    setActivityIdIsSet(false);
    this.activityId = 0;
    this.activityNo = null;
    this.clientId = null;
    this.operator = null;
  }

  /**
   * 门店id *
   */
  public String getOutletId() {
    return this.outletId;
  }

  /**
   * 门店id *
   */
  public RelateOutletActivityVo setOutletId(String outletId) {
    this.outletId = outletId;
    return this;
  }

  public void unsetOutletId() {
    this.outletId = null;
  }

  /** Returns true if field outletId is set (has been assigned a value) and false otherwise */
  public boolean isSetOutletId() {
    return this.outletId != null;
  }

  public void setOutletIdIsSet(boolean value) {
    if (!value) {
      this.outletId = null;
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
  public RelateOutletActivityVo setWeight(String weight) {
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
   * 活动标签id *
   */
  public long getActivityId() {
    return this.activityId;
  }

  /**
   * 活动标签id *
   */
  public RelateOutletActivityVo setActivityId(long activityId) {
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
   * 活动编号 *
   */
  public String getActivityNo() {
    return this.activityNo;
  }

  /**
   * 活动编号 *
   */
  public RelateOutletActivityVo setActivityNo(String activityNo) {
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
   * 客户端号 *
   */
  public String getClientId() {
    return this.clientId;
  }

  /**
   * 客户端号 *
   */
  public RelateOutletActivityVo setClientId(String clientId) {
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
   * 当前操作员 *
   */
  public String getOperator() {
    return this.operator;
  }

  /**
   * 当前操作员 *
   */
  public RelateOutletActivityVo setOperator(String operator) {
    this.operator = operator;
    return this;
  }

  public void unsetOperator() {
    this.operator = null;
  }

  /** Returns true if field operator is set (has been assigned a value) and false otherwise */
  public boolean isSetOperator() {
    return this.operator != null;
  }

  public void setOperatorIsSet(boolean value) {
    if (!value) {
      this.operator = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case OUTLET_ID:
      if (value == null) {
        unsetOutletId();
      } else {
        setOutletId((String)value);
      }
      break;

    case WEIGHT:
      if (value == null) {
        unsetWeight();
      } else {
        setWeight((String)value);
      }
      break;

    case ACTIVITY_ID:
      if (value == null) {
        unsetActivityId();
      } else {
        setActivityId((Long)value);
      }
      break;

    case ACTIVITY_NO:
      if (value == null) {
        unsetActivityNo();
      } else {
        setActivityNo((String)value);
      }
      break;

    case CLIENT_ID:
      if (value == null) {
        unsetClientId();
      } else {
        setClientId((String)value);
      }
      break;

    case OPERATOR:
      if (value == null) {
        unsetOperator();
      } else {
        setOperator((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case OUTLET_ID:
      return getOutletId();

    case WEIGHT:
      return getWeight();

    case ACTIVITY_ID:
      return Long.valueOf(getActivityId());

    case ACTIVITY_NO:
      return getActivityNo();

    case CLIENT_ID:
      return getClientId();

    case OPERATOR:
      return getOperator();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case OUTLET_ID:
      return isSetOutletId();
    case WEIGHT:
      return isSetWeight();
    case ACTIVITY_ID:
      return isSetActivityId();
    case ACTIVITY_NO:
      return isSetActivityNo();
    case CLIENT_ID:
      return isSetClientId();
    case OPERATOR:
      return isSetOperator();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof RelateOutletActivityVo)
      return this.equals((RelateOutletActivityVo)that);
    return false;
  }

  public boolean equals(RelateOutletActivityVo that) {
    if (that == null)
      return false;

    boolean this_present_outletId = true && this.isSetOutletId();
    boolean that_present_outletId = true && that.isSetOutletId();
    if (this_present_outletId || that_present_outletId) {
      if (!(this_present_outletId && that_present_outletId))
        return false;
      if (!this.outletId.equals(that.outletId))
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

    boolean this_present_activityId = true && this.isSetActivityId();
    boolean that_present_activityId = true && that.isSetActivityId();
    if (this_present_activityId || that_present_activityId) {
      if (!(this_present_activityId && that_present_activityId))
        return false;
      if (this.activityId != that.activityId)
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

    boolean this_present_clientId = true && this.isSetClientId();
    boolean that_present_clientId = true && that.isSetClientId();
    if (this_present_clientId || that_present_clientId) {
      if (!(this_present_clientId && that_present_clientId))
        return false;
      if (!this.clientId.equals(that.clientId))
        return false;
    }

    boolean this_present_operator = true && this.isSetOperator();
    boolean that_present_operator = true && that.isSetOperator();
    if (this_present_operator || that_present_operator) {
      if (!(this_present_operator && that_present_operator))
        return false;
      if (!this.operator.equals(that.operator))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_outletId = true && (isSetOutletId());
    list.add(present_outletId);
    if (present_outletId)
      list.add(outletId);

    boolean present_weight = true && (isSetWeight());
    list.add(present_weight);
    if (present_weight)
      list.add(weight);

    boolean present_activityId = true && (isSetActivityId());
    list.add(present_activityId);
    if (present_activityId)
      list.add(activityId);

    boolean present_activityNo = true && (isSetActivityNo());
    list.add(present_activityNo);
    if (present_activityNo)
      list.add(activityNo);

    boolean present_clientId = true && (isSetClientId());
    list.add(present_clientId);
    if (present_clientId)
      list.add(clientId);

    boolean present_operator = true && (isSetOperator());
    list.add(present_operator);
    if (present_operator)
      list.add(operator);

    return list.hashCode();
  }

  @Override
  public int compareTo(RelateOutletActivityVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetOutletId()).compareTo(other.isSetOutletId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOutletId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.outletId, other.outletId);
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
    lastComparison = Boolean.valueOf(isSetOperator()).compareTo(other.isSetOperator());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOperator()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.operator, other.operator);
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
    StringBuilder sb = new StringBuilder("RelateOutletActivityVo(");
    boolean first = true;

    if (isSetOutletId()) {
      sb.append("outletId:");
      if (this.outletId == null) {
        sb.append("null");
      } else {
        sb.append(this.outletId);
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
    if (isSetActivityId()) {
      if (!first) sb.append(", ");
      sb.append("activityId:");
      sb.append(this.activityId);
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
    if (isSetOperator()) {
      if (!first) sb.append(", ");
      sb.append("operator:");
      if (this.operator == null) {
        sb.append("null");
      } else {
        sb.append(this.operator);
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

  private static class RelateOutletActivityVoStandardSchemeFactory implements SchemeFactory {
    public RelateOutletActivityVoStandardScheme getScheme() {
      return new RelateOutletActivityVoStandardScheme();
    }
  }

  private static class RelateOutletActivityVoStandardScheme extends StandardScheme<RelateOutletActivityVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, RelateOutletActivityVo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // OUTLET_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.outletId = iprot.readString();
              struct.setOutletIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // WEIGHT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.weight = iprot.readString();
              struct.setWeightIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // ACTIVITY_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.activityId = iprot.readI64();
              struct.setActivityIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // ACTIVITY_NO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.activityNo = iprot.readString();
              struct.setActivityNoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // CLIENT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.clientId = iprot.readString();
              struct.setClientIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // OPERATOR
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.operator = iprot.readString();
              struct.setOperatorIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, RelateOutletActivityVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.outletId != null) {
        if (struct.isSetOutletId()) {
          oprot.writeFieldBegin(OUTLET_ID_FIELD_DESC);
          oprot.writeString(struct.outletId);
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
      if (struct.isSetActivityId()) {
        oprot.writeFieldBegin(ACTIVITY_ID_FIELD_DESC);
        oprot.writeI64(struct.activityId);
        oprot.writeFieldEnd();
      }
      if (struct.activityNo != null) {
        if (struct.isSetActivityNo()) {
          oprot.writeFieldBegin(ACTIVITY_NO_FIELD_DESC);
          oprot.writeString(struct.activityNo);
          oprot.writeFieldEnd();
        }
      }
      if (struct.clientId != null) {
        if (struct.isSetClientId()) {
          oprot.writeFieldBegin(CLIENT_ID_FIELD_DESC);
          oprot.writeString(struct.clientId);
          oprot.writeFieldEnd();
        }
      }
      if (struct.operator != null) {
        if (struct.isSetOperator()) {
          oprot.writeFieldBegin(OPERATOR_FIELD_DESC);
          oprot.writeString(struct.operator);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class RelateOutletActivityVoTupleSchemeFactory implements SchemeFactory {
    public RelateOutletActivityVoTupleScheme getScheme() {
      return new RelateOutletActivityVoTupleScheme();
    }
  }

  private static class RelateOutletActivityVoTupleScheme extends TupleScheme<RelateOutletActivityVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, RelateOutletActivityVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetOutletId()) {
        optionals.set(0);
      }
      if (struct.isSetWeight()) {
        optionals.set(1);
      }
      if (struct.isSetActivityId()) {
        optionals.set(2);
      }
      if (struct.isSetActivityNo()) {
        optionals.set(3);
      }
      if (struct.isSetClientId()) {
        optionals.set(4);
      }
      if (struct.isSetOperator()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetOutletId()) {
        oprot.writeString(struct.outletId);
      }
      if (struct.isSetWeight()) {
        oprot.writeString(struct.weight);
      }
      if (struct.isSetActivityId()) {
        oprot.writeI64(struct.activityId);
      }
      if (struct.isSetActivityNo()) {
        oprot.writeString(struct.activityNo);
      }
      if (struct.isSetClientId()) {
        oprot.writeString(struct.clientId);
      }
      if (struct.isSetOperator()) {
        oprot.writeString(struct.operator);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, RelateOutletActivityVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.outletId = iprot.readString();
        struct.setOutletIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.weight = iprot.readString();
        struct.setWeightIsSet(true);
      }
      if (incoming.get(2)) {
        struct.activityId = iprot.readI64();
        struct.setActivityIdIsSet(true);
      }
      if (incoming.get(3)) {
        struct.activityNo = iprot.readString();
        struct.setActivityNoIsSet(true);
      }
      if (incoming.get(4)) {
        struct.clientId = iprot.readString();
        struct.setClientIdIsSet(true);
      }
      if (incoming.get(5)) {
        struct.operator = iprot.readString();
        struct.setOperatorIsSet(true);
      }
    }
  }

}

