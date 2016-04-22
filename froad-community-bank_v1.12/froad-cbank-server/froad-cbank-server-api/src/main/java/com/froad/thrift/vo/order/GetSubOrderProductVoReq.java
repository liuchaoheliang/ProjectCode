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
 * 13.获取子订单商品
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class GetSubOrderProductVoReq implements org.apache.thrift.TBase<GetSubOrderProductVoReq, GetSubOrderProductVoReq._Fields>, java.io.Serializable, Cloneable, Comparable<GetSubOrderProductVoReq> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("GetSubOrderProductVoReq");

  private static final org.apache.thrift.protocol.TField CLIENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("clientId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField SUB_ORDER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("subOrderId", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField PRODUCT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("productId", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField ORDER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("orderId", org.apache.thrift.protocol.TType.STRING, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new GetSubOrderProductVoReqStandardSchemeFactory());
    schemes.put(TupleScheme.class, new GetSubOrderProductVoReqTupleSchemeFactory());
  }

  /**
   * 客户端ID
   */
  public String clientId; // required
  /**
   * 子订单ID
   */
  public String subOrderId; // required
  /**
   * 商品ID
   */
  public String productId; // required
  /**
   * 订单ID
   */
  public String orderId; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 客户端ID
     */
    CLIENT_ID((short)1, "clientId"),
    /**
     * 子订单ID
     */
    SUB_ORDER_ID((short)2, "subOrderId"),
    /**
     * 商品ID
     */
    PRODUCT_ID((short)3, "productId"),
    /**
     * 订单ID
     */
    ORDER_ID((short)4, "orderId");

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
        case 1: // CLIENT_ID
          return CLIENT_ID;
        case 2: // SUB_ORDER_ID
          return SUB_ORDER_ID;
        case 3: // PRODUCT_ID
          return PRODUCT_ID;
        case 4: // ORDER_ID
          return ORDER_ID;
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
    tmpMap.put(_Fields.CLIENT_ID, new org.apache.thrift.meta_data.FieldMetaData("clientId", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SUB_ORDER_ID, new org.apache.thrift.meta_data.FieldMetaData("subOrderId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PRODUCT_ID, new org.apache.thrift.meta_data.FieldMetaData("productId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ORDER_ID, new org.apache.thrift.meta_data.FieldMetaData("orderId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(GetSubOrderProductVoReq.class, metaDataMap);
  }

  public GetSubOrderProductVoReq() {
  }

  public GetSubOrderProductVoReq(
    String clientId,
    String subOrderId,
    String productId,
    String orderId)
  {
    this();
    this.clientId = clientId;
    this.subOrderId = subOrderId;
    this.productId = productId;
    this.orderId = orderId;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public GetSubOrderProductVoReq(GetSubOrderProductVoReq other) {
    if (other.isSetClientId()) {
      this.clientId = other.clientId;
    }
    if (other.isSetSubOrderId()) {
      this.subOrderId = other.subOrderId;
    }
    if (other.isSetProductId()) {
      this.productId = other.productId;
    }
    if (other.isSetOrderId()) {
      this.orderId = other.orderId;
    }
  }

  public GetSubOrderProductVoReq deepCopy() {
    return new GetSubOrderProductVoReq(this);
  }

  @Override
  public void clear() {
    this.clientId = null;
    this.subOrderId = null;
    this.productId = null;
    this.orderId = null;
  }

  /**
   * 客户端ID
   */
  public String getClientId() {
    return this.clientId;
  }

  /**
   * 客户端ID
   */
  public GetSubOrderProductVoReq setClientId(String clientId) {
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
   * 子订单ID
   */
  public String getSubOrderId() {
    return this.subOrderId;
  }

  /**
   * 子订单ID
   */
  public GetSubOrderProductVoReq setSubOrderId(String subOrderId) {
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
   * 商品ID
   */
  public String getProductId() {
    return this.productId;
  }

  /**
   * 商品ID
   */
  public GetSubOrderProductVoReq setProductId(String productId) {
    this.productId = productId;
    return this;
  }

  public void unsetProductId() {
    this.productId = null;
  }

  /** Returns true if field productId is set (has been assigned a value) and false otherwise */
  public boolean isSetProductId() {
    return this.productId != null;
  }

  public void setProductIdIsSet(boolean value) {
    if (!value) {
      this.productId = null;
    }
  }

  /**
   * 订单ID
   */
  public String getOrderId() {
    return this.orderId;
  }

  /**
   * 订单ID
   */
  public GetSubOrderProductVoReq setOrderId(String orderId) {
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

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case CLIENT_ID:
      if (value == null) {
        unsetClientId();
      } else {
        setClientId((String)value);
      }
      break;

    case SUB_ORDER_ID:
      if (value == null) {
        unsetSubOrderId();
      } else {
        setSubOrderId((String)value);
      }
      break;

    case PRODUCT_ID:
      if (value == null) {
        unsetProductId();
      } else {
        setProductId((String)value);
      }
      break;

    case ORDER_ID:
      if (value == null) {
        unsetOrderId();
      } else {
        setOrderId((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case CLIENT_ID:
      return getClientId();

    case SUB_ORDER_ID:
      return getSubOrderId();

    case PRODUCT_ID:
      return getProductId();

    case ORDER_ID:
      return getOrderId();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case CLIENT_ID:
      return isSetClientId();
    case SUB_ORDER_ID:
      return isSetSubOrderId();
    case PRODUCT_ID:
      return isSetProductId();
    case ORDER_ID:
      return isSetOrderId();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof GetSubOrderProductVoReq)
      return this.equals((GetSubOrderProductVoReq)that);
    return false;
  }

  public boolean equals(GetSubOrderProductVoReq that) {
    if (that == null)
      return false;

    boolean this_present_clientId = true && this.isSetClientId();
    boolean that_present_clientId = true && that.isSetClientId();
    if (this_present_clientId || that_present_clientId) {
      if (!(this_present_clientId && that_present_clientId))
        return false;
      if (!this.clientId.equals(that.clientId))
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

    boolean this_present_productId = true && this.isSetProductId();
    boolean that_present_productId = true && that.isSetProductId();
    if (this_present_productId || that_present_productId) {
      if (!(this_present_productId && that_present_productId))
        return false;
      if (!this.productId.equals(that.productId))
        return false;
    }

    boolean this_present_orderId = true && this.isSetOrderId();
    boolean that_present_orderId = true && that.isSetOrderId();
    if (this_present_orderId || that_present_orderId) {
      if (!(this_present_orderId && that_present_orderId))
        return false;
      if (!this.orderId.equals(that.orderId))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_clientId = true && (isSetClientId());
    list.add(present_clientId);
    if (present_clientId)
      list.add(clientId);

    boolean present_subOrderId = true && (isSetSubOrderId());
    list.add(present_subOrderId);
    if (present_subOrderId)
      list.add(subOrderId);

    boolean present_productId = true && (isSetProductId());
    list.add(present_productId);
    if (present_productId)
      list.add(productId);

    boolean present_orderId = true && (isSetOrderId());
    list.add(present_orderId);
    if (present_orderId)
      list.add(orderId);

    return list.hashCode();
  }

  @Override
  public int compareTo(GetSubOrderProductVoReq other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

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
    lastComparison = Boolean.valueOf(isSetProductId()).compareTo(other.isSetProductId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProductId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.productId, other.productId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
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
    StringBuilder sb = new StringBuilder("GetSubOrderProductVoReq(");
    boolean first = true;

    sb.append("clientId:");
    if (this.clientId == null) {
      sb.append("null");
    } else {
      sb.append(this.clientId);
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
    sb.append("productId:");
    if (this.productId == null) {
      sb.append("null");
    } else {
      sb.append(this.productId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("orderId:");
    if (this.orderId == null) {
      sb.append("null");
    } else {
      sb.append(this.orderId);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
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

  private static class GetSubOrderProductVoReqStandardSchemeFactory implements SchemeFactory {
    public GetSubOrderProductVoReqStandardScheme getScheme() {
      return new GetSubOrderProductVoReqStandardScheme();
    }
  }

  private static class GetSubOrderProductVoReqStandardScheme extends StandardScheme<GetSubOrderProductVoReq> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, GetSubOrderProductVoReq struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // CLIENT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.clientId = iprot.readString();
              struct.setClientIdIsSet(true);
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
          case 3: // PRODUCT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.productId = iprot.readString();
              struct.setProductIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // ORDER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.orderId = iprot.readString();
              struct.setOrderIdIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, GetSubOrderProductVoReq struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.clientId != null) {
        oprot.writeFieldBegin(CLIENT_ID_FIELD_DESC);
        oprot.writeString(struct.clientId);
        oprot.writeFieldEnd();
      }
      if (struct.subOrderId != null) {
        oprot.writeFieldBegin(SUB_ORDER_ID_FIELD_DESC);
        oprot.writeString(struct.subOrderId);
        oprot.writeFieldEnd();
      }
      if (struct.productId != null) {
        oprot.writeFieldBegin(PRODUCT_ID_FIELD_DESC);
        oprot.writeString(struct.productId);
        oprot.writeFieldEnd();
      }
      if (struct.orderId != null) {
        oprot.writeFieldBegin(ORDER_ID_FIELD_DESC);
        oprot.writeString(struct.orderId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class GetSubOrderProductVoReqTupleSchemeFactory implements SchemeFactory {
    public GetSubOrderProductVoReqTupleScheme getScheme() {
      return new GetSubOrderProductVoReqTupleScheme();
    }
  }

  private static class GetSubOrderProductVoReqTupleScheme extends TupleScheme<GetSubOrderProductVoReq> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, GetSubOrderProductVoReq struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.clientId);
      BitSet optionals = new BitSet();
      if (struct.isSetSubOrderId()) {
        optionals.set(0);
      }
      if (struct.isSetProductId()) {
        optionals.set(1);
      }
      if (struct.isSetOrderId()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetSubOrderId()) {
        oprot.writeString(struct.subOrderId);
      }
      if (struct.isSetProductId()) {
        oprot.writeString(struct.productId);
      }
      if (struct.isSetOrderId()) {
        oprot.writeString(struct.orderId);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, GetSubOrderProductVoReq struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.clientId = iprot.readString();
      struct.setClientIdIsSet(true);
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.subOrderId = iprot.readString();
        struct.setSubOrderIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.productId = iprot.readString();
        struct.setProductIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.orderId = iprot.readString();
        struct.setOrderIdIsSet(true);
      }
    }
  }

}

