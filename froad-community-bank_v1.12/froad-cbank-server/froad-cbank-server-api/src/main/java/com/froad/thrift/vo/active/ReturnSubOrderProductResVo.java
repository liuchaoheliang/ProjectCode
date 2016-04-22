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
 * 退款子订单商品响应
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class ReturnSubOrderProductResVo implements org.apache.thrift.TBase<ReturnSubOrderProductResVo, ReturnSubOrderProductResVo._Fields>, java.io.Serializable, Cloneable, Comparable<ReturnSubOrderProductResVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ReturnSubOrderProductResVo");

  private static final org.apache.thrift.protocol.TField PRODUCT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("productId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField PRICE_FIELD_DESC = new org.apache.thrift.protocol.TField("price", org.apache.thrift.protocol.TType.DOUBLE, (short)2);
  private static final org.apache.thrift.protocol.TField VIP_PRICE_FIELD_DESC = new org.apache.thrift.protocol.TField("vipPrice", org.apache.thrift.protocol.TType.DOUBLE, (short)3);
  private static final org.apache.thrift.protocol.TField VOU_PRICE_FIELD_DESC = new org.apache.thrift.protocol.TField("vouPrice", org.apache.thrift.protocol.TType.DOUBLE, (short)4);
  private static final org.apache.thrift.protocol.TField VIP_VOU_PRICE_FIELD_DESC = new org.apache.thrift.protocol.TField("vipVouPrice", org.apache.thrift.protocol.TType.DOUBLE, (short)5);
  private static final org.apache.thrift.protocol.TField VIP_COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("vipCount", org.apache.thrift.protocol.TType.I32, (short)6);
  private static final org.apache.thrift.protocol.TField NORMAL_COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("normalCount", org.apache.thrift.protocol.TType.I32, (short)7);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ReturnSubOrderProductResVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ReturnSubOrderProductResVoTupleSchemeFactory());
  }

  /**
   * 商品id
   */
  public String productId; // required
  /**
   * 普通满减优惠总价
   */
  public double price; // required
  /**
   * Vip满减优惠总价
   */
  public double vipPrice; // required
  /**
   * 普通红包优惠总价
   */
  public double vouPrice; // required
  /**
   * Vip红包优惠总价
   */
  public double vipVouPrice; // required
  /**
   * vip数量
   */
  public int vipCount; // required
  /**
   * 普通数量
   */
  public int normalCount; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 商品id
     */
    PRODUCT_ID((short)1, "productId"),
    /**
     * 普通满减优惠总价
     */
    PRICE((short)2, "price"),
    /**
     * Vip满减优惠总价
     */
    VIP_PRICE((short)3, "vipPrice"),
    /**
     * 普通红包优惠总价
     */
    VOU_PRICE((short)4, "vouPrice"),
    /**
     * Vip红包优惠总价
     */
    VIP_VOU_PRICE((short)5, "vipVouPrice"),
    /**
     * vip数量
     */
    VIP_COUNT((short)6, "vipCount"),
    /**
     * 普通数量
     */
    NORMAL_COUNT((short)7, "normalCount");

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
        case 2: // PRICE
          return PRICE;
        case 3: // VIP_PRICE
          return VIP_PRICE;
        case 4: // VOU_PRICE
          return VOU_PRICE;
        case 5: // VIP_VOU_PRICE
          return VIP_VOU_PRICE;
        case 6: // VIP_COUNT
          return VIP_COUNT;
        case 7: // NORMAL_COUNT
          return NORMAL_COUNT;
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
  private static final int __PRICE_ISSET_ID = 0;
  private static final int __VIPPRICE_ISSET_ID = 1;
  private static final int __VOUPRICE_ISSET_ID = 2;
  private static final int __VIPVOUPRICE_ISSET_ID = 3;
  private static final int __VIPCOUNT_ISSET_ID = 4;
  private static final int __NORMALCOUNT_ISSET_ID = 5;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PRODUCT_ID, new org.apache.thrift.meta_data.FieldMetaData("productId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PRICE, new org.apache.thrift.meta_data.FieldMetaData("price", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.VIP_PRICE, new org.apache.thrift.meta_data.FieldMetaData("vipPrice", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.VOU_PRICE, new org.apache.thrift.meta_data.FieldMetaData("vouPrice", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.VIP_VOU_PRICE, new org.apache.thrift.meta_data.FieldMetaData("vipVouPrice", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.VIP_COUNT, new org.apache.thrift.meta_data.FieldMetaData("vipCount", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.NORMAL_COUNT, new org.apache.thrift.meta_data.FieldMetaData("normalCount", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ReturnSubOrderProductResVo.class, metaDataMap);
  }

  public ReturnSubOrderProductResVo() {
  }

  public ReturnSubOrderProductResVo(
    String productId,
    double price,
    double vipPrice,
    double vouPrice,
    double vipVouPrice,
    int vipCount,
    int normalCount)
  {
    this();
    this.productId = productId;
    this.price = price;
    setPriceIsSet(true);
    this.vipPrice = vipPrice;
    setVipPriceIsSet(true);
    this.vouPrice = vouPrice;
    setVouPriceIsSet(true);
    this.vipVouPrice = vipVouPrice;
    setVipVouPriceIsSet(true);
    this.vipCount = vipCount;
    setVipCountIsSet(true);
    this.normalCount = normalCount;
    setNormalCountIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ReturnSubOrderProductResVo(ReturnSubOrderProductResVo other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetProductId()) {
      this.productId = other.productId;
    }
    this.price = other.price;
    this.vipPrice = other.vipPrice;
    this.vouPrice = other.vouPrice;
    this.vipVouPrice = other.vipVouPrice;
    this.vipCount = other.vipCount;
    this.normalCount = other.normalCount;
  }

  public ReturnSubOrderProductResVo deepCopy() {
    return new ReturnSubOrderProductResVo(this);
  }

  @Override
  public void clear() {
    this.productId = null;
    setPriceIsSet(false);
    this.price = 0.0;
    setVipPriceIsSet(false);
    this.vipPrice = 0.0;
    setVouPriceIsSet(false);
    this.vouPrice = 0.0;
    setVipVouPriceIsSet(false);
    this.vipVouPrice = 0.0;
    setVipCountIsSet(false);
    this.vipCount = 0;
    setNormalCountIsSet(false);
    this.normalCount = 0;
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
  public ReturnSubOrderProductResVo setProductId(String productId) {
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
   * 普通满减优惠总价
   */
  public double getPrice() {
    return this.price;
  }

  /**
   * 普通满减优惠总价
   */
  public ReturnSubOrderProductResVo setPrice(double price) {
    this.price = price;
    setPriceIsSet(true);
    return this;
  }

  public void unsetPrice() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PRICE_ISSET_ID);
  }

  /** Returns true if field price is set (has been assigned a value) and false otherwise */
  public boolean isSetPrice() {
    return EncodingUtils.testBit(__isset_bitfield, __PRICE_ISSET_ID);
  }

  public void setPriceIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PRICE_ISSET_ID, value);
  }

  /**
   * Vip满减优惠总价
   */
  public double getVipPrice() {
    return this.vipPrice;
  }

  /**
   * Vip满减优惠总价
   */
  public ReturnSubOrderProductResVo setVipPrice(double vipPrice) {
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
   * 普通红包优惠总价
   */
  public double getVouPrice() {
    return this.vouPrice;
  }

  /**
   * 普通红包优惠总价
   */
  public ReturnSubOrderProductResVo setVouPrice(double vouPrice) {
    this.vouPrice = vouPrice;
    setVouPriceIsSet(true);
    return this;
  }

  public void unsetVouPrice() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __VOUPRICE_ISSET_ID);
  }

  /** Returns true if field vouPrice is set (has been assigned a value) and false otherwise */
  public boolean isSetVouPrice() {
    return EncodingUtils.testBit(__isset_bitfield, __VOUPRICE_ISSET_ID);
  }

  public void setVouPriceIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __VOUPRICE_ISSET_ID, value);
  }

  /**
   * Vip红包优惠总价
   */
  public double getVipVouPrice() {
    return this.vipVouPrice;
  }

  /**
   * Vip红包优惠总价
   */
  public ReturnSubOrderProductResVo setVipVouPrice(double vipVouPrice) {
    this.vipVouPrice = vipVouPrice;
    setVipVouPriceIsSet(true);
    return this;
  }

  public void unsetVipVouPrice() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __VIPVOUPRICE_ISSET_ID);
  }

  /** Returns true if field vipVouPrice is set (has been assigned a value) and false otherwise */
  public boolean isSetVipVouPrice() {
    return EncodingUtils.testBit(__isset_bitfield, __VIPVOUPRICE_ISSET_ID);
  }

  public void setVipVouPriceIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __VIPVOUPRICE_ISSET_ID, value);
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
  public ReturnSubOrderProductResVo setVipCount(int vipCount) {
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
   * 普通数量
   */
  public int getNormalCount() {
    return this.normalCount;
  }

  /**
   * 普通数量
   */
  public ReturnSubOrderProductResVo setNormalCount(int normalCount) {
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

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PRODUCT_ID:
      if (value == null) {
        unsetProductId();
      } else {
        setProductId((String)value);
      }
      break;

    case PRICE:
      if (value == null) {
        unsetPrice();
      } else {
        setPrice((Double)value);
      }
      break;

    case VIP_PRICE:
      if (value == null) {
        unsetVipPrice();
      } else {
        setVipPrice((Double)value);
      }
      break;

    case VOU_PRICE:
      if (value == null) {
        unsetVouPrice();
      } else {
        setVouPrice((Double)value);
      }
      break;

    case VIP_VOU_PRICE:
      if (value == null) {
        unsetVipVouPrice();
      } else {
        setVipVouPrice((Double)value);
      }
      break;

    case VIP_COUNT:
      if (value == null) {
        unsetVipCount();
      } else {
        setVipCount((Integer)value);
      }
      break;

    case NORMAL_COUNT:
      if (value == null) {
        unsetNormalCount();
      } else {
        setNormalCount((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PRODUCT_ID:
      return getProductId();

    case PRICE:
      return Double.valueOf(getPrice());

    case VIP_PRICE:
      return Double.valueOf(getVipPrice());

    case VOU_PRICE:
      return Double.valueOf(getVouPrice());

    case VIP_VOU_PRICE:
      return Double.valueOf(getVipVouPrice());

    case VIP_COUNT:
      return Integer.valueOf(getVipCount());

    case NORMAL_COUNT:
      return Integer.valueOf(getNormalCount());

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
    case PRICE:
      return isSetPrice();
    case VIP_PRICE:
      return isSetVipPrice();
    case VOU_PRICE:
      return isSetVouPrice();
    case VIP_VOU_PRICE:
      return isSetVipVouPrice();
    case VIP_COUNT:
      return isSetVipCount();
    case NORMAL_COUNT:
      return isSetNormalCount();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ReturnSubOrderProductResVo)
      return this.equals((ReturnSubOrderProductResVo)that);
    return false;
  }

  public boolean equals(ReturnSubOrderProductResVo that) {
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

    boolean this_present_price = true;
    boolean that_present_price = true;
    if (this_present_price || that_present_price) {
      if (!(this_present_price && that_present_price))
        return false;
      if (this.price != that.price)
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

    boolean this_present_vouPrice = true;
    boolean that_present_vouPrice = true;
    if (this_present_vouPrice || that_present_vouPrice) {
      if (!(this_present_vouPrice && that_present_vouPrice))
        return false;
      if (this.vouPrice != that.vouPrice)
        return false;
    }

    boolean this_present_vipVouPrice = true;
    boolean that_present_vipVouPrice = true;
    if (this_present_vipVouPrice || that_present_vipVouPrice) {
      if (!(this_present_vipVouPrice && that_present_vipVouPrice))
        return false;
      if (this.vipVouPrice != that.vipVouPrice)
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

    boolean this_present_normalCount = true;
    boolean that_present_normalCount = true;
    if (this_present_normalCount || that_present_normalCount) {
      if (!(this_present_normalCount && that_present_normalCount))
        return false;
      if (this.normalCount != that.normalCount)
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

    boolean present_price = true;
    list.add(present_price);
    if (present_price)
      list.add(price);

    boolean present_vipPrice = true;
    list.add(present_vipPrice);
    if (present_vipPrice)
      list.add(vipPrice);

    boolean present_vouPrice = true;
    list.add(present_vouPrice);
    if (present_vouPrice)
      list.add(vouPrice);

    boolean present_vipVouPrice = true;
    list.add(present_vipVouPrice);
    if (present_vipVouPrice)
      list.add(vipVouPrice);

    boolean present_vipCount = true;
    list.add(present_vipCount);
    if (present_vipCount)
      list.add(vipCount);

    boolean present_normalCount = true;
    list.add(present_normalCount);
    if (present_normalCount)
      list.add(normalCount);

    return list.hashCode();
  }

  @Override
  public int compareTo(ReturnSubOrderProductResVo other) {
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
    lastComparison = Boolean.valueOf(isSetPrice()).compareTo(other.isSetPrice());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPrice()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.price, other.price);
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
    lastComparison = Boolean.valueOf(isSetVouPrice()).compareTo(other.isSetVouPrice());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVouPrice()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.vouPrice, other.vouPrice);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetVipVouPrice()).compareTo(other.isSetVipVouPrice());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVipVouPrice()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.vipVouPrice, other.vipVouPrice);
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
    StringBuilder sb = new StringBuilder("ReturnSubOrderProductResVo(");
    boolean first = true;

    sb.append("productId:");
    if (this.productId == null) {
      sb.append("null");
    } else {
      sb.append(this.productId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("price:");
    sb.append(this.price);
    first = false;
    if (!first) sb.append(", ");
    sb.append("vipPrice:");
    sb.append(this.vipPrice);
    first = false;
    if (!first) sb.append(", ");
    sb.append("vouPrice:");
    sb.append(this.vouPrice);
    first = false;
    if (!first) sb.append(", ");
    sb.append("vipVouPrice:");
    sb.append(this.vipVouPrice);
    first = false;
    if (!first) sb.append(", ");
    sb.append("vipCount:");
    sb.append(this.vipCount);
    first = false;
    if (!first) sb.append(", ");
    sb.append("normalCount:");
    sb.append(this.normalCount);
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

  private static class ReturnSubOrderProductResVoStandardSchemeFactory implements SchemeFactory {
    public ReturnSubOrderProductResVoStandardScheme getScheme() {
      return new ReturnSubOrderProductResVoStandardScheme();
    }
  }

  private static class ReturnSubOrderProductResVoStandardScheme extends StandardScheme<ReturnSubOrderProductResVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ReturnSubOrderProductResVo struct) throws org.apache.thrift.TException {
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
          case 2: // PRICE
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.price = iprot.readDouble();
              struct.setPriceIsSet(true);
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
          case 4: // VOU_PRICE
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.vouPrice = iprot.readDouble();
              struct.setVouPriceIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // VIP_VOU_PRICE
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.vipVouPrice = iprot.readDouble();
              struct.setVipVouPriceIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // VIP_COUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.vipCount = iprot.readI32();
              struct.setVipCountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // NORMAL_COUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.normalCount = iprot.readI32();
              struct.setNormalCountIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ReturnSubOrderProductResVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.productId != null) {
        oprot.writeFieldBegin(PRODUCT_ID_FIELD_DESC);
        oprot.writeString(struct.productId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(PRICE_FIELD_DESC);
      oprot.writeDouble(struct.price);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(VIP_PRICE_FIELD_DESC);
      oprot.writeDouble(struct.vipPrice);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(VOU_PRICE_FIELD_DESC);
      oprot.writeDouble(struct.vouPrice);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(VIP_VOU_PRICE_FIELD_DESC);
      oprot.writeDouble(struct.vipVouPrice);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(VIP_COUNT_FIELD_DESC);
      oprot.writeI32(struct.vipCount);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(NORMAL_COUNT_FIELD_DESC);
      oprot.writeI32(struct.normalCount);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ReturnSubOrderProductResVoTupleSchemeFactory implements SchemeFactory {
    public ReturnSubOrderProductResVoTupleScheme getScheme() {
      return new ReturnSubOrderProductResVoTupleScheme();
    }
  }

  private static class ReturnSubOrderProductResVoTupleScheme extends TupleScheme<ReturnSubOrderProductResVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ReturnSubOrderProductResVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetProductId()) {
        optionals.set(0);
      }
      if (struct.isSetPrice()) {
        optionals.set(1);
      }
      if (struct.isSetVipPrice()) {
        optionals.set(2);
      }
      if (struct.isSetVouPrice()) {
        optionals.set(3);
      }
      if (struct.isSetVipVouPrice()) {
        optionals.set(4);
      }
      if (struct.isSetVipCount()) {
        optionals.set(5);
      }
      if (struct.isSetNormalCount()) {
        optionals.set(6);
      }
      oprot.writeBitSet(optionals, 7);
      if (struct.isSetProductId()) {
        oprot.writeString(struct.productId);
      }
      if (struct.isSetPrice()) {
        oprot.writeDouble(struct.price);
      }
      if (struct.isSetVipPrice()) {
        oprot.writeDouble(struct.vipPrice);
      }
      if (struct.isSetVouPrice()) {
        oprot.writeDouble(struct.vouPrice);
      }
      if (struct.isSetVipVouPrice()) {
        oprot.writeDouble(struct.vipVouPrice);
      }
      if (struct.isSetVipCount()) {
        oprot.writeI32(struct.vipCount);
      }
      if (struct.isSetNormalCount()) {
        oprot.writeI32(struct.normalCount);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ReturnSubOrderProductResVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(7);
      if (incoming.get(0)) {
        struct.productId = iprot.readString();
        struct.setProductIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.price = iprot.readDouble();
        struct.setPriceIsSet(true);
      }
      if (incoming.get(2)) {
        struct.vipPrice = iprot.readDouble();
        struct.setVipPriceIsSet(true);
      }
      if (incoming.get(3)) {
        struct.vouPrice = iprot.readDouble();
        struct.setVouPriceIsSet(true);
      }
      if (incoming.get(4)) {
        struct.vipVouPrice = iprot.readDouble();
        struct.setVipVouPriceIsSet(true);
      }
      if (incoming.get(5)) {
        struct.vipCount = iprot.readI32();
        struct.setVipCountIsSet(true);
      }
      if (incoming.get(6)) {
        struct.normalCount = iprot.readI32();
        struct.setNormalCountIsSet(true);
      }
    }
  }

}
