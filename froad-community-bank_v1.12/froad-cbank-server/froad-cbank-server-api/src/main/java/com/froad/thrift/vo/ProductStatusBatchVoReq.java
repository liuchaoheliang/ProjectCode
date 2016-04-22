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
 * ProductStatusVoReq 商品上下架请求参数
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class ProductStatusBatchVoReq implements org.apache.thrift.TBase<ProductStatusBatchVoReq, ProductStatusBatchVoReq._Fields>, java.io.Serializable, Cloneable, Comparable<ProductStatusBatchVoReq> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ProductStatusBatchVoReq");

  private static final org.apache.thrift.protocol.TField CLIENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("clientId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("status", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField PRODUCT_IDS_FIELD_DESC = new org.apache.thrift.protocol.TField("productIds", org.apache.thrift.protocol.TType.LIST, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ProductStatusBatchVoReqStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ProductStatusBatchVoReqTupleSchemeFactory());
  }

  /**
   * 客户端id
   */
  public String clientId; // required
  /**
   * 商品状态
   */
  public String status; // required
  /**
   * 商品id
   */
  public List<String> productIds; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 客户端id
     */
    CLIENT_ID((short)1, "clientId"),
    /**
     * 商品状态
     */
    STATUS((short)2, "status"),
    /**
     * 商品id
     */
    PRODUCT_IDS((short)3, "productIds");

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
        case 2: // STATUS
          return STATUS;
        case 3: // PRODUCT_IDS
          return PRODUCT_IDS;
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
    tmpMap.put(_Fields.CLIENT_ID, new org.apache.thrift.meta_data.FieldMetaData("clientId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.STATUS, new org.apache.thrift.meta_data.FieldMetaData("status", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PRODUCT_IDS, new org.apache.thrift.meta_data.FieldMetaData("productIds", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ProductStatusBatchVoReq.class, metaDataMap);
  }

  public ProductStatusBatchVoReq() {
  }

  public ProductStatusBatchVoReq(
    String clientId,
    String status,
    List<String> productIds)
  {
    this();
    this.clientId = clientId;
    this.status = status;
    this.productIds = productIds;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ProductStatusBatchVoReq(ProductStatusBatchVoReq other) {
    if (other.isSetClientId()) {
      this.clientId = other.clientId;
    }
    if (other.isSetStatus()) {
      this.status = other.status;
    }
    if (other.isSetProductIds()) {
      List<String> __this__productIds = new ArrayList<String>(other.productIds);
      this.productIds = __this__productIds;
    }
  }

  public ProductStatusBatchVoReq deepCopy() {
    return new ProductStatusBatchVoReq(this);
  }

  @Override
  public void clear() {
    this.clientId = null;
    this.status = null;
    this.productIds = null;
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
  public ProductStatusBatchVoReq setClientId(String clientId) {
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
   * 商品状态
   */
  public String getStatus() {
    return this.status;
  }

  /**
   * 商品状态
   */
  public ProductStatusBatchVoReq setStatus(String status) {
    this.status = status;
    return this;
  }

  public void unsetStatus() {
    this.status = null;
  }

  /** Returns true if field status is set (has been assigned a value) and false otherwise */
  public boolean isSetStatus() {
    return this.status != null;
  }

  public void setStatusIsSet(boolean value) {
    if (!value) {
      this.status = null;
    }
  }

  public int getProductIdsSize() {
    return (this.productIds == null) ? 0 : this.productIds.size();
  }

  public java.util.Iterator<String> getProductIdsIterator() {
    return (this.productIds == null) ? null : this.productIds.iterator();
  }

  public void addToProductIds(String elem) {
    if (this.productIds == null) {
      this.productIds = new ArrayList<String>();
    }
    this.productIds.add(elem);
  }

  /**
   * 商品id
   */
  public List<String> getProductIds() {
    return this.productIds;
  }

  /**
   * 商品id
   */
  public ProductStatusBatchVoReq setProductIds(List<String> productIds) {
    this.productIds = productIds;
    return this;
  }

  public void unsetProductIds() {
    this.productIds = null;
  }

  /** Returns true if field productIds is set (has been assigned a value) and false otherwise */
  public boolean isSetProductIds() {
    return this.productIds != null;
  }

  public void setProductIdsIsSet(boolean value) {
    if (!value) {
      this.productIds = null;
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

    case STATUS:
      if (value == null) {
        unsetStatus();
      } else {
        setStatus((String)value);
      }
      break;

    case PRODUCT_IDS:
      if (value == null) {
        unsetProductIds();
      } else {
        setProductIds((List<String>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case CLIENT_ID:
      return getClientId();

    case STATUS:
      return getStatus();

    case PRODUCT_IDS:
      return getProductIds();

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
    case STATUS:
      return isSetStatus();
    case PRODUCT_IDS:
      return isSetProductIds();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ProductStatusBatchVoReq)
      return this.equals((ProductStatusBatchVoReq)that);
    return false;
  }

  public boolean equals(ProductStatusBatchVoReq that) {
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

    boolean this_present_status = true && this.isSetStatus();
    boolean that_present_status = true && that.isSetStatus();
    if (this_present_status || that_present_status) {
      if (!(this_present_status && that_present_status))
        return false;
      if (!this.status.equals(that.status))
        return false;
    }

    boolean this_present_productIds = true && this.isSetProductIds();
    boolean that_present_productIds = true && that.isSetProductIds();
    if (this_present_productIds || that_present_productIds) {
      if (!(this_present_productIds && that_present_productIds))
        return false;
      if (!this.productIds.equals(that.productIds))
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

    boolean present_status = true && (isSetStatus());
    list.add(present_status);
    if (present_status)
      list.add(status);

    boolean present_productIds = true && (isSetProductIds());
    list.add(present_productIds);
    if (present_productIds)
      list.add(productIds);

    return list.hashCode();
  }

  @Override
  public int compareTo(ProductStatusBatchVoReq other) {
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
    lastComparison = Boolean.valueOf(isSetStatus()).compareTo(other.isSetStatus());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStatus()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.status, other.status);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetProductIds()).compareTo(other.isSetProductIds());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProductIds()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.productIds, other.productIds);
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
    StringBuilder sb = new StringBuilder("ProductStatusBatchVoReq(");
    boolean first = true;

    sb.append("clientId:");
    if (this.clientId == null) {
      sb.append("null");
    } else {
      sb.append(this.clientId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("status:");
    if (this.status == null) {
      sb.append("null");
    } else {
      sb.append(this.status);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("productIds:");
    if (this.productIds == null) {
      sb.append("null");
    } else {
      sb.append(this.productIds);
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

  private static class ProductStatusBatchVoReqStandardSchemeFactory implements SchemeFactory {
    public ProductStatusBatchVoReqStandardScheme getScheme() {
      return new ProductStatusBatchVoReqStandardScheme();
    }
  }

  private static class ProductStatusBatchVoReqStandardScheme extends StandardScheme<ProductStatusBatchVoReq> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ProductStatusBatchVoReq struct) throws org.apache.thrift.TException {
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
          case 2: // STATUS
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.status = iprot.readString();
              struct.setStatusIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // PRODUCT_IDS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list64 = iprot.readListBegin();
                struct.productIds = new ArrayList<String>(_list64.size);
                String _elem65;
                for (int _i66 = 0; _i66 < _list64.size; ++_i66)
                {
                  _elem65 = iprot.readString();
                  struct.productIds.add(_elem65);
                }
                iprot.readListEnd();
              }
              struct.setProductIdsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ProductStatusBatchVoReq struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.clientId != null) {
        oprot.writeFieldBegin(CLIENT_ID_FIELD_DESC);
        oprot.writeString(struct.clientId);
        oprot.writeFieldEnd();
      }
      if (struct.status != null) {
        oprot.writeFieldBegin(STATUS_FIELD_DESC);
        oprot.writeString(struct.status);
        oprot.writeFieldEnd();
      }
      if (struct.productIds != null) {
        oprot.writeFieldBegin(PRODUCT_IDS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.productIds.size()));
          for (String _iter67 : struct.productIds)
          {
            oprot.writeString(_iter67);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ProductStatusBatchVoReqTupleSchemeFactory implements SchemeFactory {
    public ProductStatusBatchVoReqTupleScheme getScheme() {
      return new ProductStatusBatchVoReqTupleScheme();
    }
  }

  private static class ProductStatusBatchVoReqTupleScheme extends TupleScheme<ProductStatusBatchVoReq> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ProductStatusBatchVoReq struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetClientId()) {
        optionals.set(0);
      }
      if (struct.isSetStatus()) {
        optionals.set(1);
      }
      if (struct.isSetProductIds()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetClientId()) {
        oprot.writeString(struct.clientId);
      }
      if (struct.isSetStatus()) {
        oprot.writeString(struct.status);
      }
      if (struct.isSetProductIds()) {
        {
          oprot.writeI32(struct.productIds.size());
          for (String _iter68 : struct.productIds)
          {
            oprot.writeString(_iter68);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ProductStatusBatchVoReq struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.clientId = iprot.readString();
        struct.setClientIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.status = iprot.readString();
        struct.setStatusIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TList _list69 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.productIds = new ArrayList<String>(_list69.size);
          String _elem70;
          for (int _i71 = 0; _i71 < _list69.size; ++_i71)
          {
            _elem70 = iprot.readString();
            struct.productIds.add(_elem70);
          }
        }
        struct.setProductIdsIsSet(true);
      }
    }
  }

}

