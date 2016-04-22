/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.froad.thrift.vo.refund;

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
 * 退款支付流水
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class RefundPaymentVo implements org.apache.thrift.TBase<RefundPaymentVo, RefundPaymentVo._Fields>, java.io.Serializable, Cloneable, Comparable<RefundPaymentVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("RefundPaymentVo");

  private static final org.apache.thrift.protocol.TField PAYMENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("paymentId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField PAYMENT_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("paymentType", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField REFUND_VALUE_FIELD_DESC = new org.apache.thrift.protocol.TField("refundValue", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField PAYMENT_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("paymentTime", org.apache.thrift.protocol.TType.STRING, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new RefundPaymentVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new RefundPaymentVoTupleSchemeFactory());
  }

  /**
   * 支付流水
   */
  public String paymentId; // required
  /**
   * 支付类型
   */
  public String paymentType; // required
  /**
   * 退还金额或积分
   */
  public String refundValue; // required
  /**
   * 退款支付时间
   */
  public String paymentTime; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 支付流水
     */
    PAYMENT_ID((short)1, "paymentId"),
    /**
     * 支付类型
     */
    PAYMENT_TYPE((short)2, "paymentType"),
    /**
     * 退还金额或积分
     */
    REFUND_VALUE((short)3, "refundValue"),
    /**
     * 退款支付时间
     */
    PAYMENT_TIME((short)4, "paymentTime");

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
        case 1: // PAYMENT_ID
          return PAYMENT_ID;
        case 2: // PAYMENT_TYPE
          return PAYMENT_TYPE;
        case 3: // REFUND_VALUE
          return REFUND_VALUE;
        case 4: // PAYMENT_TIME
          return PAYMENT_TIME;
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
    tmpMap.put(_Fields.PAYMENT_ID, new org.apache.thrift.meta_data.FieldMetaData("paymentId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PAYMENT_TYPE, new org.apache.thrift.meta_data.FieldMetaData("paymentType", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.REFUND_VALUE, new org.apache.thrift.meta_data.FieldMetaData("refundValue", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PAYMENT_TIME, new org.apache.thrift.meta_data.FieldMetaData("paymentTime", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(RefundPaymentVo.class, metaDataMap);
  }

  public RefundPaymentVo() {
  }

  public RefundPaymentVo(
    String paymentId,
    String paymentType,
    String refundValue,
    String paymentTime)
  {
    this();
    this.paymentId = paymentId;
    this.paymentType = paymentType;
    this.refundValue = refundValue;
    this.paymentTime = paymentTime;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public RefundPaymentVo(RefundPaymentVo other) {
    if (other.isSetPaymentId()) {
      this.paymentId = other.paymentId;
    }
    if (other.isSetPaymentType()) {
      this.paymentType = other.paymentType;
    }
    if (other.isSetRefundValue()) {
      this.refundValue = other.refundValue;
    }
    if (other.isSetPaymentTime()) {
      this.paymentTime = other.paymentTime;
    }
  }

  public RefundPaymentVo deepCopy() {
    return new RefundPaymentVo(this);
  }

  @Override
  public void clear() {
    this.paymentId = null;
    this.paymentType = null;
    this.refundValue = null;
    this.paymentTime = null;
  }

  /**
   * 支付流水
   */
  public String getPaymentId() {
    return this.paymentId;
  }

  /**
   * 支付流水
   */
  public RefundPaymentVo setPaymentId(String paymentId) {
    this.paymentId = paymentId;
    return this;
  }

  public void unsetPaymentId() {
    this.paymentId = null;
  }

  /** Returns true if field paymentId is set (has been assigned a value) and false otherwise */
  public boolean isSetPaymentId() {
    return this.paymentId != null;
  }

  public void setPaymentIdIsSet(boolean value) {
    if (!value) {
      this.paymentId = null;
    }
  }

  /**
   * 支付类型
   */
  public String getPaymentType() {
    return this.paymentType;
  }

  /**
   * 支付类型
   */
  public RefundPaymentVo setPaymentType(String paymentType) {
    this.paymentType = paymentType;
    return this;
  }

  public void unsetPaymentType() {
    this.paymentType = null;
  }

  /** Returns true if field paymentType is set (has been assigned a value) and false otherwise */
  public boolean isSetPaymentType() {
    return this.paymentType != null;
  }

  public void setPaymentTypeIsSet(boolean value) {
    if (!value) {
      this.paymentType = null;
    }
  }

  /**
   * 退还金额或积分
   */
  public String getRefundValue() {
    return this.refundValue;
  }

  /**
   * 退还金额或积分
   */
  public RefundPaymentVo setRefundValue(String refundValue) {
    this.refundValue = refundValue;
    return this;
  }

  public void unsetRefundValue() {
    this.refundValue = null;
  }

  /** Returns true if field refundValue is set (has been assigned a value) and false otherwise */
  public boolean isSetRefundValue() {
    return this.refundValue != null;
  }

  public void setRefundValueIsSet(boolean value) {
    if (!value) {
      this.refundValue = null;
    }
  }

  /**
   * 退款支付时间
   */
  public String getPaymentTime() {
    return this.paymentTime;
  }

  /**
   * 退款支付时间
   */
  public RefundPaymentVo setPaymentTime(String paymentTime) {
    this.paymentTime = paymentTime;
    return this;
  }

  public void unsetPaymentTime() {
    this.paymentTime = null;
  }

  /** Returns true if field paymentTime is set (has been assigned a value) and false otherwise */
  public boolean isSetPaymentTime() {
    return this.paymentTime != null;
  }

  public void setPaymentTimeIsSet(boolean value) {
    if (!value) {
      this.paymentTime = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PAYMENT_ID:
      if (value == null) {
        unsetPaymentId();
      } else {
        setPaymentId((String)value);
      }
      break;

    case PAYMENT_TYPE:
      if (value == null) {
        unsetPaymentType();
      } else {
        setPaymentType((String)value);
      }
      break;

    case REFUND_VALUE:
      if (value == null) {
        unsetRefundValue();
      } else {
        setRefundValue((String)value);
      }
      break;

    case PAYMENT_TIME:
      if (value == null) {
        unsetPaymentTime();
      } else {
        setPaymentTime((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PAYMENT_ID:
      return getPaymentId();

    case PAYMENT_TYPE:
      return getPaymentType();

    case REFUND_VALUE:
      return getRefundValue();

    case PAYMENT_TIME:
      return getPaymentTime();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case PAYMENT_ID:
      return isSetPaymentId();
    case PAYMENT_TYPE:
      return isSetPaymentType();
    case REFUND_VALUE:
      return isSetRefundValue();
    case PAYMENT_TIME:
      return isSetPaymentTime();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof RefundPaymentVo)
      return this.equals((RefundPaymentVo)that);
    return false;
  }

  public boolean equals(RefundPaymentVo that) {
    if (that == null)
      return false;

    boolean this_present_paymentId = true && this.isSetPaymentId();
    boolean that_present_paymentId = true && that.isSetPaymentId();
    if (this_present_paymentId || that_present_paymentId) {
      if (!(this_present_paymentId && that_present_paymentId))
        return false;
      if (!this.paymentId.equals(that.paymentId))
        return false;
    }

    boolean this_present_paymentType = true && this.isSetPaymentType();
    boolean that_present_paymentType = true && that.isSetPaymentType();
    if (this_present_paymentType || that_present_paymentType) {
      if (!(this_present_paymentType && that_present_paymentType))
        return false;
      if (!this.paymentType.equals(that.paymentType))
        return false;
    }

    boolean this_present_refundValue = true && this.isSetRefundValue();
    boolean that_present_refundValue = true && that.isSetRefundValue();
    if (this_present_refundValue || that_present_refundValue) {
      if (!(this_present_refundValue && that_present_refundValue))
        return false;
      if (!this.refundValue.equals(that.refundValue))
        return false;
    }

    boolean this_present_paymentTime = true && this.isSetPaymentTime();
    boolean that_present_paymentTime = true && that.isSetPaymentTime();
    if (this_present_paymentTime || that_present_paymentTime) {
      if (!(this_present_paymentTime && that_present_paymentTime))
        return false;
      if (!this.paymentTime.equals(that.paymentTime))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_paymentId = true && (isSetPaymentId());
    list.add(present_paymentId);
    if (present_paymentId)
      list.add(paymentId);

    boolean present_paymentType = true && (isSetPaymentType());
    list.add(present_paymentType);
    if (present_paymentType)
      list.add(paymentType);

    boolean present_refundValue = true && (isSetRefundValue());
    list.add(present_refundValue);
    if (present_refundValue)
      list.add(refundValue);

    boolean present_paymentTime = true && (isSetPaymentTime());
    list.add(present_paymentTime);
    if (present_paymentTime)
      list.add(paymentTime);

    return list.hashCode();
  }

  @Override
  public int compareTo(RefundPaymentVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetPaymentId()).compareTo(other.isSetPaymentId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPaymentId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.paymentId, other.paymentId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPaymentType()).compareTo(other.isSetPaymentType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPaymentType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.paymentType, other.paymentType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRefundValue()).compareTo(other.isSetRefundValue());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRefundValue()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.refundValue, other.refundValue);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPaymentTime()).compareTo(other.isSetPaymentTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPaymentTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.paymentTime, other.paymentTime);
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
    StringBuilder sb = new StringBuilder("RefundPaymentVo(");
    boolean first = true;

    sb.append("paymentId:");
    if (this.paymentId == null) {
      sb.append("null");
    } else {
      sb.append(this.paymentId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("paymentType:");
    if (this.paymentType == null) {
      sb.append("null");
    } else {
      sb.append(this.paymentType);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("refundValue:");
    if (this.refundValue == null) {
      sb.append("null");
    } else {
      sb.append(this.refundValue);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("paymentTime:");
    if (this.paymentTime == null) {
      sb.append("null");
    } else {
      sb.append(this.paymentTime);
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

  private static class RefundPaymentVoStandardSchemeFactory implements SchemeFactory {
    public RefundPaymentVoStandardScheme getScheme() {
      return new RefundPaymentVoStandardScheme();
    }
  }

  private static class RefundPaymentVoStandardScheme extends StandardScheme<RefundPaymentVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, RefundPaymentVo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PAYMENT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.paymentId = iprot.readString();
              struct.setPaymentIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PAYMENT_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.paymentType = iprot.readString();
              struct.setPaymentTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // REFUND_VALUE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.refundValue = iprot.readString();
              struct.setRefundValueIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // PAYMENT_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.paymentTime = iprot.readString();
              struct.setPaymentTimeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, RefundPaymentVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.paymentId != null) {
        oprot.writeFieldBegin(PAYMENT_ID_FIELD_DESC);
        oprot.writeString(struct.paymentId);
        oprot.writeFieldEnd();
      }
      if (struct.paymentType != null) {
        oprot.writeFieldBegin(PAYMENT_TYPE_FIELD_DESC);
        oprot.writeString(struct.paymentType);
        oprot.writeFieldEnd();
      }
      if (struct.refundValue != null) {
        oprot.writeFieldBegin(REFUND_VALUE_FIELD_DESC);
        oprot.writeString(struct.refundValue);
        oprot.writeFieldEnd();
      }
      if (struct.paymentTime != null) {
        oprot.writeFieldBegin(PAYMENT_TIME_FIELD_DESC);
        oprot.writeString(struct.paymentTime);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class RefundPaymentVoTupleSchemeFactory implements SchemeFactory {
    public RefundPaymentVoTupleScheme getScheme() {
      return new RefundPaymentVoTupleScheme();
    }
  }

  private static class RefundPaymentVoTupleScheme extends TupleScheme<RefundPaymentVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, RefundPaymentVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPaymentId()) {
        optionals.set(0);
      }
      if (struct.isSetPaymentType()) {
        optionals.set(1);
      }
      if (struct.isSetRefundValue()) {
        optionals.set(2);
      }
      if (struct.isSetPaymentTime()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetPaymentId()) {
        oprot.writeString(struct.paymentId);
      }
      if (struct.isSetPaymentType()) {
        oprot.writeString(struct.paymentType);
      }
      if (struct.isSetRefundValue()) {
        oprot.writeString(struct.refundValue);
      }
      if (struct.isSetPaymentTime()) {
        oprot.writeString(struct.paymentTime);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, RefundPaymentVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.paymentId = iprot.readString();
        struct.setPaymentIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.paymentType = iprot.readString();
        struct.setPaymentTypeIsSet(true);
      }
      if (incoming.get(2)) {
        struct.refundValue = iprot.readString();
        struct.setRefundValueIsSet(true);
      }
      if (incoming.get(3)) {
        struct.paymentTime = iprot.readString();
        struct.setPaymentTimeIsSet(true);
      }
    }
  }

}
