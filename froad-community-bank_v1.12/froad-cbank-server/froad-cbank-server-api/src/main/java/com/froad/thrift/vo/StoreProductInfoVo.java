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
 * 商品收藏
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class StoreProductInfoVo implements org.apache.thrift.TBase<StoreProductInfoVo, StoreProductInfoVo._Fields>, java.io.Serializable, Cloneable, Comparable<StoreProductInfoVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("StoreProductInfoVo");

  private static final org.apache.thrift.protocol.TField PRODUCT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("productId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField PRODUCT_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("productName", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField PRODUCT_IMAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("productImage", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField MERCHANT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("merchantId", org.apache.thrift.protocol.TType.STRING, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new StoreProductInfoVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new StoreProductInfoVoTupleSchemeFactory());
  }

  /**
   * 商品id
   */
  public String productId; // optional
  /**
   * 商品名称
   */
  public String productName; // optional
  /**
   * 商品图片
   */
  public String productImage; // optional
  /**
   * 商户id
   */
  public String merchantId; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 商品id
     */
    PRODUCT_ID((short)1, "productId"),
    /**
     * 商品名称
     */
    PRODUCT_NAME((short)2, "productName"),
    /**
     * 商品图片
     */
    PRODUCT_IMAGE((short)3, "productImage"),
    /**
     * 商户id
     */
    MERCHANT_ID((short)4, "merchantId");

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
        case 1: // PRODUCT_ID
          return PRODUCT_ID;
        case 2: // PRODUCT_NAME
          return PRODUCT_NAME;
        case 3: // PRODUCT_IMAGE
          return PRODUCT_IMAGE;
        case 4: // MERCHANT_ID
          return MERCHANT_ID;
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
  private static final _Fields optionals[] = {_Fields.PRODUCT_ID,_Fields.PRODUCT_NAME,_Fields.PRODUCT_IMAGE,_Fields.MERCHANT_ID};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PRODUCT_ID, new org.apache.thrift.meta_data.FieldMetaData("productId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PRODUCT_NAME, new org.apache.thrift.meta_data.FieldMetaData("productName", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PRODUCT_IMAGE, new org.apache.thrift.meta_data.FieldMetaData("productImage", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.MERCHANT_ID, new org.apache.thrift.meta_data.FieldMetaData("merchantId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(StoreProductInfoVo.class, metaDataMap);
  }

  public StoreProductInfoVo() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public StoreProductInfoVo(StoreProductInfoVo other) {
    if (other.isSetProductId()) {
      this.productId = other.productId;
    }
    if (other.isSetProductName()) {
      this.productName = other.productName;
    }
    if (other.isSetProductImage()) {
      this.productImage = other.productImage;
    }
    if (other.isSetMerchantId()) {
      this.merchantId = other.merchantId;
    }
  }

  public StoreProductInfoVo deepCopy() {
    return new StoreProductInfoVo(this);
  }

  @Override
  public void clear() {
    this.productId = null;
    this.productName = null;
    this.productImage = null;
    this.merchantId = null;
  }

  /**
   * 商品id
   */
  public String getProductId() {
    return this.productId;
  }

  /**
   * 商品id
   */
  public StoreProductInfoVo setProductId(String productId) {
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
   * 商品名称
   */
  public String getProductName() {
    return this.productName;
  }

  /**
   * 商品名称
   */
  public StoreProductInfoVo setProductName(String productName) {
    this.productName = productName;
    return this;
  }

  public void unsetProductName() {
    this.productName = null;
  }

  /** Returns true if field productName is set (has been assigned a value) and false otherwise */
  public boolean isSetProductName() {
    return this.productName != null;
  }

  public void setProductNameIsSet(boolean value) {
    if (!value) {
      this.productName = null;
    }
  }

  /**
   * 商品图片
   */
  public String getProductImage() {
    return this.productImage;
  }

  /**
   * 商品图片
   */
  public StoreProductInfoVo setProductImage(String productImage) {
    this.productImage = productImage;
    return this;
  }

  public void unsetProductImage() {
    this.productImage = null;
  }

  /** Returns true if field productImage is set (has been assigned a value) and false otherwise */
  public boolean isSetProductImage() {
    return this.productImage != null;
  }

  public void setProductImageIsSet(boolean value) {
    if (!value) {
      this.productImage = null;
    }
  }

  /**
   * 商户id
   */
  public String getMerchantId() {
    return this.merchantId;
  }

  /**
   * 商户id
   */
  public StoreProductInfoVo setMerchantId(String merchantId) {
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

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PRODUCT_ID:
      if (value == null) {
        unsetProductId();
      } else {
        setProductId((String)value);
      }
      break;

    case PRODUCT_NAME:
      if (value == null) {
        unsetProductName();
      } else {
        setProductName((String)value);
      }
      break;

    case PRODUCT_IMAGE:
      if (value == null) {
        unsetProductImage();
      } else {
        setProductImage((String)value);
      }
      break;

    case MERCHANT_ID:
      if (value == null) {
        unsetMerchantId();
      } else {
        setMerchantId((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PRODUCT_ID:
      return getProductId();

    case PRODUCT_NAME:
      return getProductName();

    case PRODUCT_IMAGE:
      return getProductImage();

    case MERCHANT_ID:
      return getMerchantId();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case PRODUCT_ID:
      return isSetProductId();
    case PRODUCT_NAME:
      return isSetProductName();
    case PRODUCT_IMAGE:
      return isSetProductImage();
    case MERCHANT_ID:
      return isSetMerchantId();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof StoreProductInfoVo)
      return this.equals((StoreProductInfoVo)that);
    return false;
  }

  public boolean equals(StoreProductInfoVo that) {
    if (that == null)
      return false;

    boolean this_present_productId = true && this.isSetProductId();
    boolean that_present_productId = true && that.isSetProductId();
    if (this_present_productId || that_present_productId) {
      if (!(this_present_productId && that_present_productId))
        return false;
      if (!this.productId.equals(that.productId))
        return false;
    }

    boolean this_present_productName = true && this.isSetProductName();
    boolean that_present_productName = true && that.isSetProductName();
    if (this_present_productName || that_present_productName) {
      if (!(this_present_productName && that_present_productName))
        return false;
      if (!this.productName.equals(that.productName))
        return false;
    }

    boolean this_present_productImage = true && this.isSetProductImage();
    boolean that_present_productImage = true && that.isSetProductImage();
    if (this_present_productImage || that_present_productImage) {
      if (!(this_present_productImage && that_present_productImage))
        return false;
      if (!this.productImage.equals(that.productImage))
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

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_productId = true && (isSetProductId());
    list.add(present_productId);
    if (present_productId)
      list.add(productId);

    boolean present_productName = true && (isSetProductName());
    list.add(present_productName);
    if (present_productName)
      list.add(productName);

    boolean present_productImage = true && (isSetProductImage());
    list.add(present_productImage);
    if (present_productImage)
      list.add(productImage);

    boolean present_merchantId = true && (isSetMerchantId());
    list.add(present_merchantId);
    if (present_merchantId)
      list.add(merchantId);

    return list.hashCode();
  }

  @Override
  public int compareTo(StoreProductInfoVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

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
    lastComparison = Boolean.valueOf(isSetProductName()).compareTo(other.isSetProductName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProductName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.productName, other.productName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetProductImage()).compareTo(other.isSetProductImage());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProductImage()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.productImage, other.productImage);
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
    StringBuilder sb = new StringBuilder("StoreProductInfoVo(");
    boolean first = true;

    if (isSetProductId()) {
      sb.append("productId:");
      if (this.productId == null) {
        sb.append("null");
      } else {
        sb.append(this.productId);
      }
      first = false;
    }
    if (isSetProductName()) {
      if (!first) sb.append(", ");
      sb.append("productName:");
      if (this.productName == null) {
        sb.append("null");
      } else {
        sb.append(this.productName);
      }
      first = false;
    }
    if (isSetProductImage()) {
      if (!first) sb.append(", ");
      sb.append("productImage:");
      if (this.productImage == null) {
        sb.append("null");
      } else {
        sb.append(this.productImage);
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

  private static class StoreProductInfoVoStandardSchemeFactory implements SchemeFactory {
    public StoreProductInfoVoStandardScheme getScheme() {
      return new StoreProductInfoVoStandardScheme();
    }
  }

  private static class StoreProductInfoVoStandardScheme extends StandardScheme<StoreProductInfoVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, StoreProductInfoVo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PRODUCT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.productId = iprot.readString();
              struct.setProductIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PRODUCT_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.productName = iprot.readString();
              struct.setProductNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // PRODUCT_IMAGE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.productImage = iprot.readString();
              struct.setProductImageIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // MERCHANT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.merchantId = iprot.readString();
              struct.setMerchantIdIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, StoreProductInfoVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.productId != null) {
        if (struct.isSetProductId()) {
          oprot.writeFieldBegin(PRODUCT_ID_FIELD_DESC);
          oprot.writeString(struct.productId);
          oprot.writeFieldEnd();
        }
      }
      if (struct.productName != null) {
        if (struct.isSetProductName()) {
          oprot.writeFieldBegin(PRODUCT_NAME_FIELD_DESC);
          oprot.writeString(struct.productName);
          oprot.writeFieldEnd();
        }
      }
      if (struct.productImage != null) {
        if (struct.isSetProductImage()) {
          oprot.writeFieldBegin(PRODUCT_IMAGE_FIELD_DESC);
          oprot.writeString(struct.productImage);
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
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class StoreProductInfoVoTupleSchemeFactory implements SchemeFactory {
    public StoreProductInfoVoTupleScheme getScheme() {
      return new StoreProductInfoVoTupleScheme();
    }
  }

  private static class StoreProductInfoVoTupleScheme extends TupleScheme<StoreProductInfoVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, StoreProductInfoVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetProductId()) {
        optionals.set(0);
      }
      if (struct.isSetProductName()) {
        optionals.set(1);
      }
      if (struct.isSetProductImage()) {
        optionals.set(2);
      }
      if (struct.isSetMerchantId()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetProductId()) {
        oprot.writeString(struct.productId);
      }
      if (struct.isSetProductName()) {
        oprot.writeString(struct.productName);
      }
      if (struct.isSetProductImage()) {
        oprot.writeString(struct.productImage);
      }
      if (struct.isSetMerchantId()) {
        oprot.writeString(struct.merchantId);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, StoreProductInfoVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.productId = iprot.readString();
        struct.setProductIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.productName = iprot.readString();
        struct.setProductNameIsSet(true);
      }
      if (incoming.get(2)) {
        struct.productImage = iprot.readString();
        struct.setProductImageIsSet(true);
      }
      if (incoming.get(3)) {
        struct.merchantId = iprot.readString();
        struct.setMerchantIdIsSet(true);
      }
    }
  }

}

