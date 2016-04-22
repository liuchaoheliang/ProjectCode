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
 * 删除关联门店ReqVo
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2015-10-29")
public class DeleteRelateOutletReqVo implements org.apache.thrift.TBase<DeleteRelateOutletReqVo, DeleteRelateOutletReqVo._Fields>, java.io.Serializable, Cloneable, Comparable<DeleteRelateOutletReqVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("DeleteRelateOutletReqVo");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField CLIENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("clientId", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField ACTIVITY_NO_FIELD_DESC = new org.apache.thrift.protocol.TField("activityNo", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField OPERATOR_FIELD_DESC = new org.apache.thrift.protocol.TField("operator", org.apache.thrift.protocol.TType.STRING, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new DeleteRelateOutletReqVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new DeleteRelateOutletReqVoTupleSchemeFactory());
  }

  /**
   * 门店关联id *
   */
  public long id; // required
  /**
   * 客户端ID *
   */
  public String clientId; // required
  /**
   * 活动编号 *
   */
  public String activityNo; // required
  /**
   * 当前操作人 *
   */
  public String operator; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 门店关联id *
     */
    ID((short)1, "id"),
    /**
     * 客户端ID *
     */
    CLIENT_ID((short)2, "clientId"),
    /**
     * 活动编号 *
     */
    ACTIVITY_NO((short)3, "activityNo"),
    /**
     * 当前操作人 *
     */
    OPERATOR((short)4, "operator");

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
        case 2: // CLIENT_ID
          return CLIENT_ID;
        case 3: // ACTIVITY_NO
          return ACTIVITY_NO;
        case 4: // OPERATOR
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
  private static final int __ID_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.CLIENT_ID, new org.apache.thrift.meta_data.FieldMetaData("clientId", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ACTIVITY_NO, new org.apache.thrift.meta_data.FieldMetaData("activityNo", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.OPERATOR, new org.apache.thrift.meta_data.FieldMetaData("operator", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(DeleteRelateOutletReqVo.class, metaDataMap);
  }

  public DeleteRelateOutletReqVo() {
  }

  public DeleteRelateOutletReqVo(
    long id,
    String clientId,
    String activityNo,
    String operator)
  {
    this();
    this.id = id;
    setIdIsSet(true);
    this.clientId = clientId;
    this.activityNo = activityNo;
    this.operator = operator;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public DeleteRelateOutletReqVo(DeleteRelateOutletReqVo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    if (other.isSetClientId()) {
      this.clientId = other.clientId;
    }
    if (other.isSetActivityNo()) {
      this.activityNo = other.activityNo;
    }
    if (other.isSetOperator()) {
      this.operator = other.operator;
    }
  }

  public DeleteRelateOutletReqVo deepCopy() {
    return new DeleteRelateOutletReqVo(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    this.clientId = null;
    this.activityNo = null;
    this.operator = null;
  }

  /**
   * 门店关联id *
   */
  public long getId() {
    return this.id;
  }

  /**
   * 门店关联id *
   */
  public DeleteRelateOutletReqVo setId(long id) {
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
   * 客户端ID *
   */
  public String getClientId() {
    return this.clientId;
  }

  /**
   * 客户端ID *
   */
  public DeleteRelateOutletReqVo setClientId(String clientId) {
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
  public DeleteRelateOutletReqVo setActivityNo(String activityNo) {
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
   * 当前操作人 *
   */
  public String getOperator() {
    return this.operator;
  }

  /**
   * 当前操作人 *
   */
  public DeleteRelateOutletReqVo setOperator(String operator) {
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
    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((Long)value);
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
    case ID:
      return Long.valueOf(getId());

    case CLIENT_ID:
      return getClientId();

    case ACTIVITY_NO:
      return getActivityNo();

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
    case ID:
      return isSetId();
    case CLIENT_ID:
      return isSetClientId();
    case ACTIVITY_NO:
      return isSetActivityNo();
    case OPERATOR:
      return isSetOperator();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof DeleteRelateOutletReqVo)
      return this.equals((DeleteRelateOutletReqVo)that);
    return false;
  }

  public boolean equals(DeleteRelateOutletReqVo that) {
    if (that == null)
      return false;

    boolean this_present_id = true;
    boolean that_present_id = true;
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (this.id != that.id)
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

    boolean present_id = true;
    list.add(present_id);
    if (present_id)
      list.add(id);

    boolean present_clientId = true && (isSetClientId());
    list.add(present_clientId);
    if (present_clientId)
      list.add(clientId);

    boolean present_activityNo = true && (isSetActivityNo());
    list.add(present_activityNo);
    if (present_activityNo)
      list.add(activityNo);

    boolean present_operator = true && (isSetOperator());
    list.add(present_operator);
    if (present_operator)
      list.add(operator);

    return list.hashCode();
  }

  @Override
  public int compareTo(DeleteRelateOutletReqVo other) {
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
    StringBuilder sb = new StringBuilder("DeleteRelateOutletReqVo(");
    boolean first = true;

    sb.append("id:");
    sb.append(this.id);
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
    sb.append("activityNo:");
    if (this.activityNo == null) {
      sb.append("null");
    } else {
      sb.append(this.activityNo);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("operator:");
    if (this.operator == null) {
      sb.append("null");
    } else {
      sb.append(this.operator);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // alas, we cannot check 'id' because it's a primitive and you chose the non-beans generator.
    if (clientId == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'clientId' was not present! Struct: " + toString());
    }
    if (activityNo == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'activityNo' was not present! Struct: " + toString());
    }
    if (operator == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'operator' was not present! Struct: " + toString());
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

  private static class DeleteRelateOutletReqVoStandardSchemeFactory implements SchemeFactory {
    public DeleteRelateOutletReqVoStandardScheme getScheme() {
      return new DeleteRelateOutletReqVoStandardScheme();
    }
  }

  private static class DeleteRelateOutletReqVoStandardScheme extends StandardScheme<DeleteRelateOutletReqVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, DeleteRelateOutletReqVo struct) throws org.apache.thrift.TException {
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
          case 4: // OPERATOR
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
      if (!struct.isSetId()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'id' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, DeleteRelateOutletReqVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(ID_FIELD_DESC);
      oprot.writeI64(struct.id);
      oprot.writeFieldEnd();
      if (struct.clientId != null) {
        oprot.writeFieldBegin(CLIENT_ID_FIELD_DESC);
        oprot.writeString(struct.clientId);
        oprot.writeFieldEnd();
      }
      if (struct.activityNo != null) {
        oprot.writeFieldBegin(ACTIVITY_NO_FIELD_DESC);
        oprot.writeString(struct.activityNo);
        oprot.writeFieldEnd();
      }
      if (struct.operator != null) {
        oprot.writeFieldBegin(OPERATOR_FIELD_DESC);
        oprot.writeString(struct.operator);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class DeleteRelateOutletReqVoTupleSchemeFactory implements SchemeFactory {
    public DeleteRelateOutletReqVoTupleScheme getScheme() {
      return new DeleteRelateOutletReqVoTupleScheme();
    }
  }

  private static class DeleteRelateOutletReqVoTupleScheme extends TupleScheme<DeleteRelateOutletReqVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, DeleteRelateOutletReqVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeI64(struct.id);
      oprot.writeString(struct.clientId);
      oprot.writeString(struct.activityNo);
      oprot.writeString(struct.operator);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, DeleteRelateOutletReqVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.id = iprot.readI64();
      struct.setIdIsSet(true);
      struct.clientId = iprot.readString();
      struct.setClientIdIsSet(true);
      struct.activityNo = iprot.readString();
      struct.setActivityNoIsSet(true);
      struct.operator = iprot.readString();
      struct.setOperatorIsSet(true);
    }
  }

}

