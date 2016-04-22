/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.froad.thrift.vo.active;

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
 * 退款子订单商品回退
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class ReturnSubOrderProductBackReqVo implements org.apache.thrift.TBase<ReturnSubOrderProductBackReqVo, ReturnSubOrderProductBackReqVo._Fields>, java.io.Serializable, Cloneable, Comparable<ReturnSubOrderProductBackReqVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ReturnSubOrderProductBackReqVo");

  private static final org.apache.thrift.protocol.TField PRODUCT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("productId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField VIP_COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("vipCount", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField VIP_PRICE_FIELD_DESC = new org.apache.thrift.protocol.TField("vipPrice", org.apache.thrift.protocol.TType.DOUBLE, (short)3);
  private static final org.apache.thrift.protocol.TField NORMAL_COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("normalCount", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField NORMAL_PRICE_FIELD_DESC = new org.apache.thrift.protocol.TField("normalPrice", org.apache.thrift.protocol.TType.DOUBLE, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ReturnSubOrderProductBackReqVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ReturnSubOrderProductBackReqVoTupleSchemeFactory());
  }

  /**
   * 商品id
   */
  public String productId; // required
  /**
   * vip数量
   */
  public int vipCount; // required
  /**
   * Vip回退总金额
   */
  public double vipPrice; // required
  /**
   * 普通数量
   */
  public int normalCount; // required
  /**
   * 普通回退总金额
   */
  public double normalPrice; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 商品id
     */
    PRODUCT_ID((short)1, "productId"),
    /**
     * vip数量
     */
    VIP_COUNT((short)2, "vipCount"),
    /**
     * Vip回退总金额
     */
    VIP_PRICE((short)3, "vipPrice"),
    /**
     * 普通数量
     */
    NORMAL_COUNT((short)4, "normalCount"),
    /**
     * 普通回退总金额
     */
    NORMAL_PRICE((short)5, "normalPrice");

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
        case 2: // VIP_COUNT
          return VIP_COUNT;
        case 3: // VIP_PRICE
          return VIP_PRICE;
        case 4: // NORMAL_COUNT
          return NORMAL_COUNT;
        case 5: // NORMAL_PRICE
          return NORMAL_PRICE;
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
  private static final int __VIPCOUNT_ISSET_ID = 0;
  private static final int __VIPPRICE_ISSET_ID = 1;
  private static final int __NORMALCOUNT_ISSET_ID = 2;
  private static final int __NORMALPRICE_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PRODUCT_ID, new org.apache.thrift.meta_data.FieldMetaData("productId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.VIP_COUNT, new org.apache.thrift.meta_data.FieldMetaData("vipCount", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.VIP_PRICE, new org.apache.thrift.meta_data.FieldMetaData("vipPrice", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.NORMAL_COUNT, new org.apache.thrift.meta_data.FieldMetaData("normalCount", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.NORMAL_PRICE, new org.apache.thrift.meta_data.FieldMetaData("normalPrice", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ReturnSubOrderProductBackReqVo.class, metaDataMap);
  }

  public ReturnSubOrderProductBackReqVo() {
  }

  public ReturnSubOrderProductBackReqVo(
    String productId,
    int vipCount,
    double vipPrice,
    int normalCount,
    double normalPrice)
  {
    this();
    this.productId = productId;
    this.vipCount = vipCount;
    setVipCountIsSet(true);
    this.vipPrice = vipPrice;
    setVipPriceIsSet(true);
    this.normalCount = normalCount;
    setNormalCountIsSet(true);
    this.normalPrice = normalPrice;
    setNormalPriceIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ReturnSubOrderProductBackReqVo(ReturnSubOrderProductBackReqVo other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetProductId()) {
      this.productId = other.productId;
    }
    this.vipCount = other.vipCount;
    this.vipPrice = other.vipPrice;
    this.normalCount = other.normalCount;
    this.normalPrice = other.normalPrice;
  }

  public ReturnSubOrderProductBackReqVo deepCopy() {
    return new ReturnSubOrderProductBackReqVo(this);
  }

  @Override
  public void clear() {
    this.productId = null;
    setVipCountIsSet(false);
    this.vipCount = 0;
    setVipPriceIsSet(false);
    this.vipPrice = 0.0;
    setNormalCountIsSet(false);
    this.normalCount = 0;
    setNormalPriceIsSet(false);
    this.normalPrice = 0.0;
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
  public ReturnSubOrderProductBackReqVo setProductId(String productId) {
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
   * vip数量
   */
  public int getVipCount() {
    return this.vipCount;
  }

  /**
   * vip数量
   */
  public ReturnSubOrderProductBackReqVo setVipCount(int vipCount) {
    this.vipCount = vipCount;
    setVipCountIsSet(true);
    return this;
  }

  public void unsetVipCount() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __VIPCOUNT_ISSET_ID);
  }

  /** Returns true if field vipCount is set (has been assigned a value) and false otherwise */
  public boolean isSetVipCount() {
    return EncodingUtils.testBit(__isset_bitfield, __VIPCOUNT_ISSET_ID);
  }

  public void setVipCountIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __VIPCOUNT_ISSET_ID, value);
  }

  /**
   * Vip回退总金额
   */
  public double getVipPrice() {
    return this.vipPrice;
  }

  /**
   * Vip回退总金额
   */
  public ReturnSubOrderProductBackReqVo setVipPrice(double vipPrice) {
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
   * 普通数量
   */
  public int getNormalCount() {
    return this.normalCount;
  }

  /**
   * 普通数量
   */
  public ReturnSubOrderProductBackReqVo setNormalCount(int normalCount) {
    this.normalCount = normalCount;
    setNormalCountIsSet(true);
    return this;
  }

  public void unsetNormalCount() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __NORMALCOUNT_ISSET_ID);
  }

  /** Returns true if field normalCount is set (has been assigned a value) and false otherwise */
  public boolean isSetNormalCount() {
    return EncodingUtils.testBit(__isset_bitfield, __NORMALCOUNT_ISSET_ID);
  }

  public void setNormalCountIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __NORMALCOUNT_ISSET_ID, value);
  }

  /**
   * 普通回退总金额
   */
  public double getNormalPrice() {
    return this.normalPrice;
  }

  /**
   * 普通回退总金额
   */
  public ReturnSubOrderProductBackReqVo setNormalPrice(double normalPrice) {
    this.normalPrice = normalPrice;
    setNormalPriceIsSet(true);
    return this;
  }

  public void unsetNormalPrice() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __NORMALPRICE_ISSET_ID);
  }

  /** Returns true if field normalPrice is set (has been assigned a value) and false otherwise */
  public boolean isSetNormalPrice() {
    return EncodingUtils.testBit(__isset_bitfield, __NORMALPRICE_ISSET_ID);
  }

  public void setNormalPriceIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __NORMALPRICE_ISSET_ID, value);
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

    case VIP_COUNT:
      if (value == null) {
        unsetVipCount();
      } else {
        setVipCount((Integer)value);
      }
      break;

    case VIP_PRICE:
      if (value == null) {
        unsetVipPrice();
      } else {
        setVipPrice((Double)value);
      }
      break;

    case NORMAL_COUNT:
      if (value == null) {
        unsetNormalCount();
      } else {
        setNormalCount((Integer)value);
      }
      break;

    case NORMAL_PRICE:
      if (value == null) {
        unsetNormalPrice();
      } else {
        setNormalPrice((Double)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PRODUCT_ID:
      return getProductId();

    case VIP_COUNT:
      return Integer.valueOf(getVipCount());

    case VIP_PRICE:
      return Double.valueOf(getVipPrice());

    case NORMAL_COUNT:
      return Integer.valueOf(getNormalCount());

    case NORMAL_PRICE:
      return Double.valueOf(getNormalPrice());

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
    case VIP_COUNT:
      return isSetVipCount();
    case VIP_PRICE:
      return isSetVipPrice();
    case NORMAL_COUNT:
      return isSetNormalCount();
    case NORMAL_PRICE:
      return isSetNormalPrice();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ReturnSubOrderProductBackReqVo)
      return this.equals((ReturnSubOrderProductBackReqVo)that);
    return false;
  }

  public boolean equals(ReturnSubOrderProductBackReqVo that) {
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

    boolean this_present_vipCount = true;
    boolean that_present_vipCount = true;
    if (this_present_vipCount || that_present_vipCount) {
      if (!(this_present_vipCount && that_present_vipCount))
        return false;
      if (this.vipCount != that.vipCount)
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

    boolean this_present_normalCount = true;
    boolean that_present_normalCount = true;
    if (this_present_normalCount || that_present_normalCount) {
      if (!(this_present_normalCount && that_present_normalCount))
        return false;
      if (this.normalCount != that.normalCount)
        return false;
    }

    boolean this_present_normalPrice = true;
    boolean that_present_normalPrice = true;
    if (this_present_normalPrice || that_present_normalPrice) {
      if (!(this_present_normalPrice && that_present_normalPrice))
        return false;
      if (this.normalPrice != that.normalPrice)
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

    boolean present_vipCount = true;
    list.add(present_vipCount);
    if (present_vipCount)
      list.add(vipCount);

    boolean present_vipPrice = true;
    list.add(present_vipPrice);
    if (present_vipPrice)
      list.add(vipPrice);

    boolean present_normalCount = true;
    list.add(present_normalCount);
    if (present_normalCount)
      list.add(normalCount);

    boolean present_normalPrice = true;
    list.add(present_normalPrice);
    if (present_normalPrice)
      list.add(normalPrice);

    return list.hashCode();
  }

  @Override
  public int compareTo(ReturnSubOrderProductBackReqVo other) {
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
    lastComparison = Boolean.valueOf(isSetVipCount()).compareTo(other.isSetVipCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVipCount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.vipCount, other.vipCount);
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
    lastComparison = Boolean.valueOf(isSetNormalCount()).compareTo(other.isSetNormalCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNormalCount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.normalCount, other.normalCount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetNormalPrice()).compareTo(other.isSetNormalPrice());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNormalPrice()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.normalPrice, other.normalPrice);
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
    StringBuilder sb = new StringBuilder("ReturnSubOrderProductBackReqVo(");
    boolean first = true;

    sb.append("productId:");
    if (this.productId == null) {
      sb.append("null");
    } else {
      sb.append(this.productId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("vipCount:");
    sb.append(this.vipCount);
    first = false;
    if (!first) sb.append(", ");
    sb.append("vipPrice:");
    sb.append(this.vipPrice);
    first = false;
    if (!first) sb.append(", ");
    sb.append("normalCount:");
    sb.append(this.normalCount);
    first = false;
    if (!first) sb.append(", ");
    sb.append("normalPrice:");
    sb.append(this.normalPrice);
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

  private static class ReturnSubOrderProductBackReqVoStandardSchemeFactory implements SchemeFactory {
    public ReturnSubOrderProductBackReqVoStandardScheme getScheme() {
      return new ReturnSubOrderProductBackReqVoStandardScheme();
    }
  }

  private static class ReturnSubOrderProductBackReqVoStandardScheme extends StandardScheme<ReturnSubOrderProductBackReqVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ReturnSubOrderProductBackReqVo struct) throws org.apache.thrift.TException {
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
          case 2: // VIP_COUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.vipCount = iprot.readI32();
              struct.setVipCountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // VIP_PRICE
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.vipPrice = iprot.readDouble();
              struct.setVipPriceIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // NORMAL_COUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.normalCount = iprot.readI32();
              struct.setNormalCountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // NORMAL_PRICE
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.normalPrice = iprot.readDouble();
              struct.setNormalPriceIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ReturnSubOrderProductBackReqVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.productId != null) {
        oprot.writeFieldBegin(PRODUCT_ID_FIELD_DESC);
        oprot.writeString(struct.productId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(VIP_COUNT_FIELD_DESC);
      oprot.writeI32(struct.vipCount);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(VIP_PRICE_FIELD_DESC);
      oprot.writeDouble(struct.vipPrice);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(NORMAL_COUNT_FIELD_DESC);
      oprot.writeI32(struct.normalCount);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(NORMAL_PRICE_FIELD_DESC);
      oprot.writeDouble(struct.normalPrice);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ReturnSubOrderProductBackReqVoTupleSchemeFactory implements SchemeFactory {
    public ReturnSubOrderProductBackReqVoTupleScheme getScheme() {
      return new ReturnSubOrderProductBackReqVoTupleScheme();
    }
  }

  private static class ReturnSubOrderProductBackReqVoTupleScheme extends TupleScheme<ReturnSubOrderProductBackReqVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ReturnSubOrderProductBackReqVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetProductId()) {
        optionals.set(0);
      }
      if (struct.isSetVipCount()) {
        optionals.set(1);
      }
      if (struct.isSetVipPrice()) {
        optionals.set(2);
      }
      if (struct.isSetNormalCount()) {
        optionals.set(3);
      }
      if (struct.isSetNormalPrice()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetProductId()) {
        oprot.writeString(struct.productId);
      }
      if (struct.isSetVipCount()) {
        oprot.writeI32(struct.vipCount);
      }
      if (struct.isSetVipPrice()) {
        oprot.writeDouble(struct.vipPrice);
      }
      if (struct.isSetNormalCount()) {
        oprot.writeI32(struct.normalCount);
      }
      if (struct.isSetNormalPrice()) {
        oprot.writeDouble(struct.normalPrice);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ReturnSubOrderProductBackReqVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.productId = iprot.readString();
        struct.setProductIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.vipCount = iprot.readI32();
        struct.setVipCountIsSet(true);
      }
      if (incoming.get(2)) {
        struct.vipPrice = iprot.readDouble();
        struct.setVipPriceIsSet(true);
      }
      if (incoming.get(3)) {
        struct.normalCount = iprot.readI32();
        struct.setNormalCountIsSet(true);
      }
      if (incoming.get(4)) {
        struct.normalPrice = iprot.readDouble();
        struct.setNormalPriceIsSet(true);
      }
    }
  }

}
