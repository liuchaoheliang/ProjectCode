/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.froad.thrift.vo.payment;

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
 * 合并支付请求体
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2015-9-22")
public class CombineSettleReq implements org.apache.thrift.TBase<CombineSettleReq, CombineSettleReq._Fields>, java.io.Serializable, Cloneable, Comparable<CombineSettleReq> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CombineSettleReq");

  private static final org.apache.thrift.protocol.TField PAYMENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("paymentId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField PAY_VALUE_FIELD_DESC = new org.apache.thrift.protocol.TField("payValue", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField MERCHANT_AND_OUTLET_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("merchantAndOutletId", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField CLIENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("clientId", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField PAYMENT_ORG_NO_FIELD_DESC = new org.apache.thrift.protocol.TField("paymentOrgNo", org.apache.thrift.protocol.TType.STRING, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new CombineSettleReqStandardSchemeFactory());
    schemes.put(TupleScheme.class, new CombineSettleReqTupleSchemeFactory());
  }

  public String paymentId; // required
  public String payValue; // required
  public String merchantAndOutletId; // required
  public String clientId; // required
  public String paymentOrgNo; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PAYMENT_ID((short)1, "paymentId"),
    PAY_VALUE((short)2, "payValue"),
    MERCHANT_AND_OUTLET_ID((short)3, "merchantAndOutletId"),
    CLIENT_ID((short)4, "clientId"),
    PAYMENT_ORG_NO((short)5, "paymentOrgNo");

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
        case 2: // PAY_VALUE
          return PAY_VALUE;
        case 3: // MERCHANT_AND_OUTLET_ID
          return MERCHANT_AND_OUTLET_ID;
        case 4: // CLIENT_ID
          return CLIENT_ID;
        case 5: // PAYMENT_ORG_NO
          return PAYMENT_ORG_NO;
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
    tmpMap.put(_Fields.PAY_VALUE, new org.apache.thrift.meta_data.FieldMetaData("payValue", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.MERCHANT_AND_OUTLET_ID, new org.apache.thrift.meta_data.FieldMetaData("merchantAndOutletId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CLIENT_ID, new org.apache.thrift.meta_data.FieldMetaData("clientId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PAYMENT_ORG_NO, new org.apache.thrift.meta_data.FieldMetaData("paymentOrgNo", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CombineSettleReq.class, metaDataMap);
  }

  public CombineSettleReq() {
  }

  public CombineSettleReq(
    String paymentId,
    String payValue,
    String merchantAndOutletId,
    String clientId,
    String paymentOrgNo)
  {
    this();
    this.paymentId = paymentId;
    this.payValue = payValue;
    this.merchantAndOutletId = merchantAndOutletId;
    this.clientId = clientId;
    this.paymentOrgNo = paymentOrgNo;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CombineSettleReq(CombineSettleReq other) {
    if (other.isSetPaymentId()) {
      this.paymentId = other.paymentId;
    }
    if (other.isSetPayValue()) {
      this.payValue = other.payValue;
    }
    if (other.isSetMerchantAndOutletId()) {
      this.merchantAndOutletId = other.merchantAndOutletId;
    }
    if (other.isSetClientId()) {
      this.clientId = other.clientId;
    }
    if (other.isSetPaymentOrgNo()) {
      this.paymentOrgNo = other.paymentOrgNo;
    }
  }

  public CombineSettleReq deepCopy() {
    return new CombineSettleReq(this);
  }

  @Override
  public void clear() {
    this.paymentId = null;
    this.payValue = null;
    this.merchantAndOutletId = null;
    this.clientId = null;
    this.paymentOrgNo = null;
  }

  public String getPaymentId() {
    return this.paymentId;
  }

  public CombineSettleReq setPaymentId(String paymentId) {
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

  public String getPayValue() {
    return this.payValue;
  }

  public CombineSettleReq setPayValue(String payValue) {
    this.payValue = payValue;
    return this;
  }

  public void unsetPayValue() {
    this.payValue = null;
  }

  /** Returns true if field payValue is set (has been assigned a value) and false otherwise */
  public boolean isSetPayValue() {
    return this.payValue != null;
  }

  public void setPayValueIsSet(boolean value) {
    if (!value) {
      this.payValue = null;
    }
  }

  public String getMerchantAndOutletId() {
    return this.merchantAndOutletId;
  }

  public CombineSettleReq setMerchantAndOutletId(String merchantAndOutletId) {
    this.merchantAndOutletId = merchantAndOutletId;
    return this;
  }

  public void unsetMerchantAndOutletId() {
    this.merchantAndOutletId = null;
  }

  /** Returns true if field merchantAndOutletId is set (has been assigned a value) and false otherwise */
  public boolean isSetMerchantAndOutletId() {
    return this.merchantAndOutletId != null;
  }

  public void setMerchantAndOutletIdIsSet(boolean value) {
    if (!value) {
      this.merchantAndOutletId = null;
    }
  }

  public String getClientId() {
    return this.clientId;
  }

  public CombineSettleReq setClientId(String clientId) {
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

  public String getPaymentOrgNo() {
    return this.paymentOrgNo;
  }

  public CombineSettleReq setPaymentOrgNo(String paymentOrgNo) {
    this.paymentOrgNo = paymentOrgNo;
    return this;
  }

  public void unsetPaymentOrgNo() {
    this.paymentOrgNo = null;
  }

  /** Returns true if field paymentOrgNo is set (has been assigned a value) and false otherwise */
  public boolean isSetPaymentOrgNo() {
    return this.paymentOrgNo != null;
  }

  public void setPaymentOrgNoIsSet(boolean value) {
    if (!value) {
      this.paymentOrgNo = null;
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

    case PAY_VALUE:
      if (value == null) {
        unsetPayValue();
      } else {
        setPayValue((String)value);
      }
      break;

    case MERCHANT_AND_OUTLET_ID:
      if (value == null) {
        unsetMerchantAndOutletId();
      } else {
        setMerchantAndOutletId((String)value);
      }
      break;

    case CLIENT_ID:
      if (value == null) {
        unsetClientId();
      } else {
        setClientId((String)value);
      }
      break;

    case PAYMENT_ORG_NO:
      if (value == null) {
        unsetPaymentOrgNo();
      } else {
        setPaymentOrgNo((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PAYMENT_ID:
      return getPaymentId();

    case PAY_VALUE:
      return getPayValue();

    case MERCHANT_AND_OUTLET_ID:
      return getMerchantAndOutletId();

    case CLIENT_ID:
      return getClientId();

    case PAYMENT_ORG_NO:
      return getPaymentOrgNo();

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
    case PAY_VALUE:
      return isSetPayValue();
    case MERCHANT_AND_OUTLET_ID:
      return isSetMerchantAndOutletId();
    case CLIENT_ID:
      return isSetClientId();
    case PAYMENT_ORG_NO:
      return isSetPaymentOrgNo();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof CombineSettleReq)
      return this.equals((CombineSettleReq)that);
    return false;
  }

  public boolean equals(CombineSettleReq that) {
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

    boolean this_present_payValue = true && this.isSetPayValue();
    boolean that_present_payValue = true && that.isSetPayValue();
    if (this_present_payValue || that_present_payValue) {
      if (!(this_present_payValue && that_present_payValue))
        return false;
      if (!this.payValue.equals(that.payValue))
        return false;
    }

    boolean this_present_merchantAndOutletId = true && this.isSetMerchantAndOutletId();
    boolean that_present_merchantAndOutletId = true && that.isSetMerchantAndOutletId();
    if (this_present_merchantAndOutletId || that_present_merchantAndOutletId) {
      if (!(this_present_merchantAndOutletId && that_present_merchantAndOutletId))
        return false;
      if (!this.merchantAndOutletId.equals(that.merchantAndOutletId))
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

    boolean this_present_paymentOrgNo = true && this.isSetPaymentOrgNo();
    boolean that_present_paymentOrgNo = true && that.isSetPaymentOrgNo();
    if (this_present_paymentOrgNo || that_present_paymentOrgNo) {
      if (!(this_present_paymentOrgNo && that_present_paymentOrgNo))
        return false;
      if (!this.paymentOrgNo.equals(that.paymentOrgNo))
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

    boolean present_payValue = true && (isSetPayValue());
    list.add(present_payValue);
    if (present_payValue)
      list.add(payValue);

    boolean present_merchantAndOutletId = true && (isSetMerchantAndOutletId());
    list.add(present_merchantAndOutletId);
    if (present_merchantAndOutletId)
      list.add(merchantAndOutletId);

    boolean present_clientId = true && (isSetClientId());
    list.add(present_clientId);
    if (present_clientId)
      list.add(clientId);

    boolean present_paymentOrgNo = true && (isSetPaymentOrgNo());
    list.add(present_paymentOrgNo);
    if (present_paymentOrgNo)
      list.add(paymentOrgNo);

    return list.hashCode();
  }

  @Override
  public int compareTo(CombineSettleReq other) {
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
    lastComparison = Boolean.valueOf(isSetPayValue()).compareTo(other.isSetPayValue());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPayValue()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.payValue, other.payValue);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMerchantAndOutletId()).compareTo(other.isSetMerchantAndOutletId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMerchantAndOutletId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.merchantAndOutletId, other.merchantAndOutletId);
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
    lastComparison = Boolean.valueOf(isSetPaymentOrgNo()).compareTo(other.isSetPaymentOrgNo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPaymentOrgNo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.paymentOrgNo, other.paymentOrgNo);
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
    StringBuilder sb = new StringBuilder("CombineSettleReq(");
    boolean first = true;

    sb.append("paymentId:");
    if (this.paymentId == null) {
      sb.append("null");
    } else {
      sb.append(this.paymentId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("payValue:");
    if (this.payValue == null) {
      sb.append("null");
    } else {
      sb.append(this.payValue);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("merchantAndOutletId:");
    if (this.merchantAndOutletId == null) {
      sb.append("null");
    } else {
      sb.append(this.merchantAndOutletId);
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
    sb.append("paymentOrgNo:");
    if (this.paymentOrgNo == null) {
      sb.append("null");
    } else {
      sb.append(this.paymentOrgNo);
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

  private static class CombineSettleReqStandardSchemeFactory implements SchemeFactory {
    public CombineSettleReqStandardScheme getScheme() {
      return new CombineSettleReqStandardScheme();
    }
  }

  private static class CombineSettleReqStandardScheme extends StandardScheme<CombineSettleReq> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, CombineSettleReq struct) throws org.apache.thrift.TException {
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
          case 2: // PAY_VALUE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.payValue = iprot.readString();
              struct.setPayValueIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // MERCHANT_AND_OUTLET_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.merchantAndOutletId = iprot.readString();
              struct.setMerchantAndOutletIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // CLIENT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.clientId = iprot.readString();
              struct.setClientIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // PAYMENT_ORG_NO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.paymentOrgNo = iprot.readString();
              struct.setPaymentOrgNoIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, CombineSettleReq struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.paymentId != null) {
        oprot.writeFieldBegin(PAYMENT_ID_FIELD_DESC);
        oprot.writeString(struct.paymentId);
        oprot.writeFieldEnd();
      }
      if (struct.payValue != null) {
        oprot.writeFieldBegin(PAY_VALUE_FIELD_DESC);
        oprot.writeString(struct.payValue);
        oprot.writeFieldEnd();
      }
      if (struct.merchantAndOutletId != null) {
        oprot.writeFieldBegin(MERCHANT_AND_OUTLET_ID_FIELD_DESC);
        oprot.writeString(struct.merchantAndOutletId);
        oprot.writeFieldEnd();
      }
      if (struct.clientId != null) {
        oprot.writeFieldBegin(CLIENT_ID_FIELD_DESC);
        oprot.writeString(struct.clientId);
        oprot.writeFieldEnd();
      }
      if (struct.paymentOrgNo != null) {
        oprot.writeFieldBegin(PAYMENT_ORG_NO_FIELD_DESC);
        oprot.writeString(struct.paymentOrgNo);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CombineSettleReqTupleSchemeFactory implements SchemeFactory {
    public CombineSettleReqTupleScheme getScheme() {
      return new CombineSettleReqTupleScheme();
    }
  }

  private static class CombineSettleReqTupleScheme extends TupleScheme<CombineSettleReq> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, CombineSettleReq struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPaymentId()) {
        optionals.set(0);
      }
      if (struct.isSetPayValue()) {
        optionals.set(1);
      }
      if (struct.isSetMerchantAndOutletId()) {
        optionals.set(2);
      }
      if (struct.isSetClientId()) {
        optionals.set(3);
      }
      if (struct.isSetPaymentOrgNo()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetPaymentId()) {
        oprot.writeString(struct.paymentId);
      }
      if (struct.isSetPayValue()) {
        oprot.writeString(struct.payValue);
      }
      if (struct.isSetMerchantAndOutletId()) {
        oprot.writeString(struct.merchantAndOutletId);
      }
      if (struct.isSetClientId()) {
        oprot.writeString(struct.clientId);
      }
      if (struct.isSetPaymentOrgNo()) {
        oprot.writeString(struct.paymentOrgNo);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, CombineSettleReq struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.paymentId = iprot.readString();
        struct.setPaymentIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.payValue = iprot.readString();
        struct.setPayValueIsSet(true);
      }
      if (incoming.get(2)) {
        struct.merchantAndOutletId = iprot.readString();
        struct.setMerchantAndOutletIdIsSet(true);
      }
      if (incoming.get(3)) {
        struct.clientId = iprot.readString();
        struct.setClientIdIsSet(true);
      }
      if (incoming.get(4)) {
        struct.paymentOrgNo = iprot.readString();
        struct.setPaymentOrgNoIsSet(true);
      }
    }
  }

}
