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
 * VIP规则绑定商品信息vo
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class BindVipInfoVo implements org.apache.thrift.TBase<BindVipInfoVo, BindVipInfoVo._Fields>, java.io.Serializable, Cloneable, Comparable<BindVipInfoVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("BindVipInfoVo");

  private static final org.apache.thrift.protocol.TField PRODUCT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("productId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField VIP_PRICE_FIELD_DESC = new org.apache.thrift.protocol.TField("vipPrice", org.apache.thrift.protocol.TType.DOUBLE, (short)2);
  private static final org.apache.thrift.protocol.TField VIP_LIMIT_FIELD_DESC = new org.apache.thrift.protocol.TField("vipLimit", org.apache.thrift.protocol.TType.I32, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new BindVipInfoVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new BindVipInfoVoTupleSchemeFactory());
  }

  /**
   * 商品id
   */
  public String productId; // required
  /**
   * VIP1价
   */
  public double vipPrice; // required
  /**
   * VIP1限购
   */
  public int vipLimit; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 商品id
     */
    PRODUCT_ID((short)1, "productId"),
    /**
     * VIP1价
     */
    VIP_PRICE((short)2, "vipPrice"),
    /**
     * VIP1限购
     */
    VIP_LIMIT((short)3, "vipLimit");

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
        case 2: // VIP_PRICE
          return VIP_PRICE;
        case 3: // VIP_LIMIT
          return VIP_LIMIT;
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
  private static final int __VIPPRICE_ISSET_ID = 0;
  private static final int __VIPLIMIT_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PRODUCT_ID, new org.apache.thrift.meta_data.FieldMetaData("productId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.VIP_PRICE, new org.apache.thrift.meta_data.FieldMetaData("vipPrice", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.VIP_LIMIT, new org.apache.thrift.meta_data.FieldMetaData("vipLimit", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(BindVipInfoVo.class, metaDataMap);
  }

  public BindVipInfoVo() {
  }

  public BindVipInfoVo(
    String productId,
    double vipPrice,
    int vipLimit)
  {
    this();
    this.productId = productId;
    this.vipPrice = vipPrice;
    setVipPriceIsSet(true);
    this.vipLimit = vipLimit;
    setVipLimitIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public BindVipInfoVo(BindVipInfoVo other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetProductId()) {
      this.productId = other.productId;
    }
    this.vipPrice = other.vipPrice;
    this.vipLimit = other.vipLimit;
  }

  public BindVipInfoVo deepCopy() {
    return new BindVipInfoVo(this);
  }

  @Override
  public void clear() {
    this.productId = null;
    setVipPriceIsSet(false);
    this.vipPrice = 0.0;
    setVipLimitIsSet(false);
    this.vipLimit = 0;
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
  public BindVipInfoVo setProductId(String productId) {
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
   * VIP1价
   */
  public double getVipPrice() {
    return this.vipPrice;
  }

  /**
   * VIP1价
   */
  public BindVipInfoVo setVipPrice(double vipPrice) {
    this.vipPrice = vipPrice;
    setVipPriceIsSet(true);
    return this;
  }

  public void unsetVipPrice() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __VIPPRICE_ISSET_ID);
  }

  /** Returns true if field vipPrice is set (has been assigned a value) and false otherwise */
  public boolean isSetVipPrice() {
    return EncodingUtils.testBit(__isset_bitfield, __VIPPRICE_ISSET_ID);
  }

  public void setVipPriceIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __VIPPRICE_ISSET_ID, value);
  }

  /**
   * VIP1限购
   */
  public int getVipLimit() {
    return this.vipLimit;
  }

  /**
   * VIP1限购
   */
  public BindVipInfoVo setVipLimit(int vipLimit) {
    this.vipLimit = vipLimit;
    setVipLimitIsSet(true);
    return this;
  }

  public void unsetVipLimit() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __VIPLIMIT_ISSET_ID);
  }

  /** Returns true if field vipLimit is set (has been assigned a value) and false otherwise */
  public boolean isSetVipLimit() {
    return EncodingUtils.testBit(__isset_bitfield, __VIPLIMIT_ISSET_ID);
  }

  public void setVipLimitIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __VIPLIMIT_ISSET_ID, value);
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

    case VIP_PRICE:
      if (value == null) {
        unsetVipPrice();
      } else {
        setVipPrice((Double)value);
      }
      break;

    case VIP_LIMIT:
      if (value == null) {
        unsetVipLimit();
      } else {
        setVipLimit((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PRODUCT_ID:
      return getProductId();

    case VIP_PRICE:
      return Double.valueOf(getVipPrice());

    case VIP_LIMIT:
      return Integer.valueOf(getVipLimit());

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
    case VIP_PRICE:
      return isSetVipPrice();
    case VIP_LIMIT:
      return isSetVipLimit();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof BindVipInfoVo)
      return this.equals((BindVipInfoVo)that);
    return false;
  }

  public boolean equals(BindVipInfoVo that) {
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

    boolean this_present_vipPrice = true;
    boolean that_present_vipPrice = true;
    if (this_present_vipPrice || that_present_vipPrice) {
      if (!(this_present_vipPrice && that_present_vipPrice))
        return false;
      if (this.vipPrice != that.vipPrice)
        return false;
    }

    boolean this_present_vipLimit = true;
    boolean that_present_vipLimit = true;
    if (this_present_vipLimit || that_present_vipLimit) {
      if (!(this_present_vipLimit && that_present_vipLimit))
        return false;
      if (this.vipLimit != that.vipLimit)
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

    boolean present_vipPrice = true;
    list.add(present_vipPrice);
    if (present_vipPrice)
      list.add(vipPrice);

    boolean present_vipLimit = true;
    list.add(present_vipLimit);
    if (present_vipLimit)
      list.add(vipLimit);

    return list.hashCode();
  }

  @Override
  public int compareTo(BindVipInfoVo other) {
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
    lastComparison = Boolean.valueOf(isSetVipPrice()).compareTo(other.isSetVipPrice());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVipPrice()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.vipPrice, other.vipPrice);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetVipLimit()).compareTo(other.isSetVipLimit());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVipLimit()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.vipLimit, other.vipLimit);
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
    StringBuilder sb = new StringBuilder("BindVipInfoVo(");
    boolean first = true;

    sb.append("productId:");
    if (this.productId == null) {
      sb.append("null");
    } else {
      sb.append(this.productId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("vipPrice:");
    sb.append(this.vipPrice);
    first = false;
    if (!first) sb.append(", ");
    sb.append("vipLimit:");
    sb.append(this.vipLimit);
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class BindVipInfoVoStandardSchemeFactory implements SchemeFactory {
    public BindVipInfoVoStandardScheme getScheme() {
      return new BindVipInfoVoStandardScheme();
    }
  }

  private static class BindVipInfoVoStandardScheme extends StandardScheme<BindVipInfoVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, BindVipInfoVo struct) throws org.apache.thrift.TException {
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
          case 2: // VIP_PRICE
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.vipPrice = iprot.readDouble();
              struct.setVipPriceIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // VIP_LIMIT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.vipLimit = iprot.readI32();
              struct.setVipLimitIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, BindVipInfoVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.productId != null) {
        oprot.writeFieldBegin(PRODUCT_ID_FIELD_DESC);
        oprot.writeString(struct.productId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(VIP_PRICE_FIELD_DESC);
      oprot.writeDouble(struct.vipPrice);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(VIP_LIMIT_FIELD_DESC);
      oprot.writeI32(struct.vipLimit);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class BindVipInfoVoTupleSchemeFactory implements SchemeFactory {
    public BindVipInfoVoTupleScheme getScheme() {
      return new BindVipInfoVoTupleScheme();
    }
  }

  private static class BindVipInfoVoTupleScheme extends TupleScheme<BindVipInfoVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, BindVipInfoVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetProductId()) {
        optionals.set(0);
      }
      if (struct.isSetVipPrice()) {
        optionals.set(1);
      }
      if (struct.isSetVipLimit()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetProductId()) {
        oprot.writeString(struct.productId);
      }
      if (struct.isSetVipPrice()) {
        oprot.writeDouble(struct.vipPrice);
      }
      if (struct.isSetVipLimit()) {
        oprot.writeI32(struct.vipLimit);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, BindVipInfoVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.productId = iprot.readString();
        struct.setProductIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.vipPrice = iprot.readDouble();
        struct.setVipPriceIsSet(true);
      }
      if (incoming.get(2)) {
        struct.vipLimit = iprot.readI32();
        struct.setVipLimitIsSet(true);
      }
    }
  }

}
