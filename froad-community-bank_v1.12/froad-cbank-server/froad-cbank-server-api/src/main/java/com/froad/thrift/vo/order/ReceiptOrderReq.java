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
 * 7.1 订单收货
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class ReceiptOrderReq implements org.apache.thrift.TBase<ReceiptOrderReq, ReceiptOrderReq._Fields>, java.io.Serializable, Cloneable, Comparable<ReceiptOrderReq> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ReceiptOrderReq");

  private static final org.apache.thrift.protocol.TField ORDER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("orderId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField SUB_ORDER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("subOrderId", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField CLIENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("clientId", org.apache.thrift.protocol.TType.STRING, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ReceiptOrderReqStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ReceiptOrderReqTupleSchemeFactory());
  }

  /**
   * 订单号
   */
  public String orderId; // required
  /**
   * 子订单号
   */
  public String subOrderId; // required
  /**
   * 客户端号
   */
  public String clientId; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 订单号
     */
    ORDER_ID((short)1, "orderId"),
    /**
     * 子订单号
     */
    SUB_ORDER_ID((short)2, "subOrderId"),
    /**
     * 客户端号
     */
    CLIENT_ID((short)3, "clientId");

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
        case 1: // ORDER_ID
          return ORDER_ID;
        case 2: // SUB_ORDER_ID
          return SUB_ORDER_ID;
        case 3: // CLIENT_ID
          return CLIENT_ID;
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
    tmpMap.put(_Fields.ORDER_ID, new org.apache.thrift.meta_data.FieldMetaData("orderId", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SUB_ORDER_ID, new org.apache.thrift.meta_data.FieldMetaData("subOrderId", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CLIENT_ID, new org.apache.thrift.meta_data.FieldMetaData("clientId", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ReceiptOrderReq.class, metaDataMap);
  }

  public ReceiptOrderReq() {
  }

  public ReceiptOrderReq(
    String orderId,
    String subOrderId,
    String clientId)
  {
    this();
    this.orderId = orderId;
    this.subOrderId = subOrderId;
    this.clientId = clientId;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ReceiptOrderReq(ReceiptOrderReq other) {
    if (other.isSetOrderId()) {
      this.orderId = other.orderId;
    }
    if (other.isSetSubOrderId()) {
      this.subOrderId = other.subOrderId;
    }
    if (other.isSetClientId()) {
      this.clientId = other.clientId;
    }
  }

  public ReceiptOrderReq deepCopy() {
    return new ReceiptOrderReq(this);
  }

  @Override
  public void clear() {
    this.orderId = null;
    this.subOrderId = null;
    this.clientId = null;
  }

  /**
   * 订单号
   */
  public String getOrderId() {
    return this.orderId;
  }

  /**
   * 订单号
   */
  public ReceiptOrderReq setOrderId(String orderId) {
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

  /**
   * 子订单号
   */
  public String getSubOrderId() {
    return this.subOrderId;
  }

  /**
   * 子订单号
   */
  public ReceiptOrderReq setSubOrderId(String subOrderId) {
    this.subOrderId = subOrderId;
    return this;
  }

  public void unsetSubOrderId() {
    this.subOrderId = null;
  }

  /** Returns true if field subOrderId is set (has been assigned a value) and false otherwise */
  public boolean isSetSubOrderId() {
    return this.subOrderId != null;
  }

  public void setSubOrderIdIsSet(boolean value) {
    if (!value) {
      this.subOrderId = null;
    }
  }

  /**
   * 客户端号
   */
  public String getClientId() {
    return this.clientId;
  }

  /**
   * 客户端号
   */
  public ReceiptOrderReq setClientId(String clientId) {
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

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ORDER_ID:
      if (value == null) {
        unsetOrderId();
      } else {
        setOrderId((String)value);
      }
      break;

    case SUB_ORDER_ID:
      if (value == null) {
        unsetSubOrderId();
      } else {
        setSubOrderId((String)value);
      }
      break;

    case CLIENT_ID:
      if (value == null) {
        unsetClientId();
      } else {
        setClientId((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ORDER_ID:
      return getOrderId();

    case SUB_ORDER_ID:
      return getSubOrderId();

    case CLIENT_ID:
      return getClientId();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ORDER_ID:
      return isSetOrderId();
    case SUB_ORDER_ID:
      return isSetSubOrderId();
    case CLIENT_ID:
      return isSetClientId();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ReceiptOrderReq)
      return this.equals((ReceiptOrderReq)that);
    return false;
  }

  public boolean equals(ReceiptOrderReq that) {
    if (that == null)
      return false;

    boolean this_present_orderId = true && this.isSetOrderId();
    boolean that_present_orderId = true && that.isSetOrderId();
    if (this_present_orderId || that_present_orderId) {
      if (!(this_present_orderId && that_present_orderId))
        return false;
      if (!this.orderId.equals(that.orderId))
        return false;
    }

    boolean this_present_subOrderId = true && this.isSetSubOrderId();
    boolean that_present_subOrderId = true && that.isSetSubOrderId();
    if (this_present_subOrderId || that_present_subOrderId) {
      if (!(this_present_subOrderId && that_present_subOrderId))
        return false;
      if (!this.subOrderId.equals(that.subOrderId))
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

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_orderId = true && (isSetOrderId());
    list.add(present_orderId);
    if (present_orderId)
      list.add(orderId);

    boolean present_subOrderId = true && (isSetSubOrderId());
    list.add(present_subOrderId);
    if (present_subOrderId)
      list.add(subOrderId);

    boolean present_clientId = true && (isSetClientId());
    list.add(present_clientId);
    if (present_clientId)
      list.add(clientId);

    return list.hashCode();
  }

  @Override
  public int compareTo(ReceiptOrderReq other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

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
    lastComparison = Boolean.valueOf(isSetSubOrderId()).compareTo(other.isSetSubOrderId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSubOrderId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.subOrderId, other.subOrderId);
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
    StringBuilder sb = new StringBuilder("ReceiptOrderReq(");
    boolean first = true;

    sb.append("orderId:");
    if (this.orderId == null) {
      sb.append("null");
    } else {
      sb.append(this.orderId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("subOrderId:");
    if (this.subOrderId == null) {
      sb.append("null");
    } else {
      sb.append(this.subOrderId);
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
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (orderId == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'orderId' was not present! Struct: " + toString());
    }
    if (subOrderId == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'subOrderId' was not present! Struct: " + toString());
    }
    if (clientId == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'clientId' was not present! Struct: " + toString());
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class ReceiptOrderReqStandardSchemeFactory implements SchemeFactory {
    public ReceiptOrderReqStandardScheme getScheme() {
      return new ReceiptOrderReqStandardScheme();
    }
  }

  private static class ReceiptOrderReqStandardScheme extends StandardScheme<ReceiptOrderReq> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ReceiptOrderReq struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ORDER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.orderId = iprot.readString();
              struct.setOrderIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // SUB_ORDER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.subOrderId = iprot.readString();
              struct.setSubOrderIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // CLIENT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.clientId = iprot.readString();
              struct.setClientIdIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ReceiptOrderReq struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.orderId != null) {
        oprot.writeFieldBegin(ORDER_ID_FIELD_DESC);
        oprot.writeString(struct.orderId);
        oprot.writeFieldEnd();
      }
      if (struct.subOrderId != null) {
        oprot.writeFieldBegin(SUB_ORDER_ID_FIELD_DESC);
        oprot.writeString(struct.subOrderId);
        oprot.writeFieldEnd();
      }
      if (struct.clientId != null) {
        oprot.writeFieldBegin(CLIENT_ID_FIELD_DESC);
        oprot.writeString(struct.clientId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ReceiptOrderReqTupleSchemeFactory implements SchemeFactory {
    public ReceiptOrderReqTupleScheme getScheme() {
      return new ReceiptOrderReqTupleScheme();
    }
  }

  private static class ReceiptOrderReqTupleScheme extends TupleScheme<ReceiptOrderReq> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ReceiptOrderReq struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.orderId);
      oprot.writeString(struct.subOrderId);
      oprot.writeString(struct.clientId);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ReceiptOrderReq struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.orderId = iprot.readString();
      struct.setOrderIdIsSet(true);
      struct.subOrderId = iprot.readString();
      struct.setSubOrderIdIsSet(true);
      struct.clientId = iprot.readString();
      struct.setClientIdIsSet(true);
    }
  }

}
