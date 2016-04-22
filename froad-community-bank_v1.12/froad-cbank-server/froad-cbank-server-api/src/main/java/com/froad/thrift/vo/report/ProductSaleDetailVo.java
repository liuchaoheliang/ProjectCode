/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.froad.thrift.vo.report;

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
 * 商品销售详情列表
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class ProductSaleDetailVo implements org.apache.thrift.TBase<ProductSaleDetailVo, ProductSaleDetailVo._Fields>, java.io.Serializable, Cloneable, Comparable<ProductSaleDetailVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ProductSaleDetailVo");

  private static final org.apache.thrift.protocol.TField ORG_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("orgCode", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField ORG_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("orgName", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField ADD_PRODUCT_COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("addProductCount", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField PRODUCT_COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("productCount", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField PRODUCT_SALE_COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("productSaleCount", org.apache.thrift.protocol.TType.I32, (short)5);
  private static final org.apache.thrift.protocol.TField PRODUCT_SALE_AMOUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("productSaleAmount", org.apache.thrift.protocol.TType.DOUBLE, (short)6);
  private static final org.apache.thrift.protocol.TField REFUND_AMOUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("refundAmount", org.apache.thrift.protocol.TType.DOUBLE, (short)7);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ProductSaleDetailVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ProductSaleDetailVoTupleSchemeFactory());
  }

  /**
   * 机构号 *
   */
  public String orgCode; // optional
  /**
   * 机构名称 *
   */
  public String orgName; // optional
  /**
   * 新增商品数 *
   */
  public int addProductCount; // optional
  /**
   * 商品总数 *
   */
  public int productCount; // optional
  /**
   * 商品销售数量 *
   */
  public int productSaleCount; // optional
  /**
   * 商品销售金额 *
   */
  public double productSaleAmount; // optional
  /**
   * 退款总金额 *
   */
  public double refundAmount; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 机构号 *
     */
    ORG_CODE((short)1, "orgCode"),
    /**
     * 机构名称 *
     */
    ORG_NAME((short)2, "orgName"),
    /**
     * 新增商品数 *
     */
    ADD_PRODUCT_COUNT((short)3, "addProductCount"),
    /**
     * 商品总数 *
     */
    PRODUCT_COUNT((short)4, "productCount"),
    /**
     * 商品销售数量 *
     */
    PRODUCT_SALE_COUNT((short)5, "productSaleCount"),
    /**
     * 商品销售金额 *
     */
    PRODUCT_SALE_AMOUNT((short)6, "productSaleAmount"),
    /**
     * 退款总金额 *
     */
    REFUND_AMOUNT((short)7, "refundAmount");

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
        case 1: // ORG_CODE
          return ORG_CODE;
        case 2: // ORG_NAME
          return ORG_NAME;
        case 3: // ADD_PRODUCT_COUNT
          return ADD_PRODUCT_COUNT;
        case 4: // PRODUCT_COUNT
          return PRODUCT_COUNT;
        case 5: // PRODUCT_SALE_COUNT
          return PRODUCT_SALE_COUNT;
        case 6: // PRODUCT_SALE_AMOUNT
          return PRODUCT_SALE_AMOUNT;
        case 7: // REFUND_AMOUNT
          return REFUND_AMOUNT;
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
  private static final int __ADDPRODUCTCOUNT_ISSET_ID = 0;
  private static final int __PRODUCTCOUNT_ISSET_ID = 1;
  private static final int __PRODUCTSALECOUNT_ISSET_ID = 2;
  private static final int __PRODUCTSALEAMOUNT_ISSET_ID = 3;
  private static final int __REFUNDAMOUNT_ISSET_ID = 4;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.ORG_CODE,_Fields.ORG_NAME,_Fields.ADD_PRODUCT_COUNT,_Fields.PRODUCT_COUNT,_Fields.PRODUCT_SALE_COUNT,_Fields.PRODUCT_SALE_AMOUNT,_Fields.REFUND_AMOUNT};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ORG_CODE, new org.apache.thrift.meta_data.FieldMetaData("orgCode", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ORG_NAME, new org.apache.thrift.meta_data.FieldMetaData("orgName", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ADD_PRODUCT_COUNT, new org.apache.thrift.meta_data.FieldMetaData("addProductCount", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PRODUCT_COUNT, new org.apache.thrift.meta_data.FieldMetaData("productCount", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PRODUCT_SALE_COUNT, new org.apache.thrift.meta_data.FieldMetaData("productSaleCount", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PRODUCT_SALE_AMOUNT, new org.apache.thrift.meta_data.FieldMetaData("productSaleAmount", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.REFUND_AMOUNT, new org.apache.thrift.meta_data.FieldMetaData("refundAmount", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ProductSaleDetailVo.class, metaDataMap);
  }

  public ProductSaleDetailVo() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ProductSaleDetailVo(ProductSaleDetailVo other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetOrgCode()) {
      this.orgCode = other.orgCode;
    }
    if (other.isSetOrgName()) {
      this.orgName = other.orgName;
    }
    this.addProductCount = other.addProductCount;
    this.productCount = other.productCount;
    this.productSaleCount = other.productSaleCount;
    this.productSaleAmount = other.productSaleAmount;
    this.refundAmount = other.refundAmount;
  }

  public ProductSaleDetailVo deepCopy() {
    return new ProductSaleDetailVo(this);
  }

  @Override
  public void clear() {
    this.orgCode = null;
    this.orgName = null;
    setAddProductCountIsSet(false);
    this.addProductCount = 0;
    setProductCountIsSet(false);
    this.productCount = 0;
    setProductSaleCountIsSet(false);
    this.productSaleCount = 0;
    setProductSaleAmountIsSet(false);
    this.productSaleAmount = 0.0;
    setRefundAmountIsSet(false);
    this.refundAmount = 0.0;
  }

  /**
   * 机构号 *
   */
  public String getOrgCode() {
    return this.orgCode;
  }

  /**
   * 机构号 *
   */
  public ProductSaleDetailVo setOrgCode(String orgCode) {
    this.orgCode = orgCode;
    return this;
  }

  public void unsetOrgCode() {
    this.orgCode = null;
  }

  /** Returns true if field orgCode is set (has been assigned a value) and false otherwise */
  public boolean isSetOrgCode() {
    return this.orgCode != null;
  }

  public void setOrgCodeIsSet(boolean value) {
    if (!value) {
      this.orgCode = null;
    }
  }

  /**
   * 机构名称 *
   */
  public String getOrgName() {
    return this.orgName;
  }

  /**
   * 机构名称 *
   */
  public ProductSaleDetailVo setOrgName(String orgName) {
    this.orgName = orgName;
    return this;
  }

  public void unsetOrgName() {
    this.orgName = null;
  }

  /** Returns true if field orgName is set (has been assigned a value) and false otherwise */
  public boolean isSetOrgName() {
    return this.orgName != null;
  }

  public void setOrgNameIsSet(boolean value) {
    if (!value) {
      this.orgName = null;
    }
  }

  /**
   * 新增商品数 *
   */
  public int getAddProductCount() {
    return this.addProductCount;
  }

  /**
   * 新增商品数 *
   */
  public ProductSaleDetailVo setAddProductCount(int addProductCount) {
    this.addProductCount = addProductCount;
    setAddProductCountIsSet(true);
    return this;
  }

  public void unsetAddProductCount() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ADDPRODUCTCOUNT_ISSET_ID);
  }

  /** Returns true if field addProductCount is set (has been assigned a value) and false otherwise */
  public boolean isSetAddProductCount() {
    return EncodingUtils.testBit(__isset_bitfield, __ADDPRODUCTCOUNT_ISSET_ID);
  }

  public void setAddProductCountIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ADDPRODUCTCOUNT_ISSET_ID, value);
  }

  /**
   * 商品总数 *
   */
  public int getProductCount() {
    return this.productCount;
  }

  /**
   * 商品总数 *
   */
  public ProductSaleDetailVo setProductCount(int productCount) {
    this.productCount = productCount;
    setProductCountIsSet(true);
    return this;
  }

  public void unsetProductCount() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PRODUCTCOUNT_ISSET_ID);
  }

  /** Returns true if field productCount is set (has been assigned a value) and false otherwise */
  public boolean isSetProductCount() {
    return EncodingUtils.testBit(__isset_bitfield, __PRODUCTCOUNT_ISSET_ID);
  }

  public void setProductCountIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PRODUCTCOUNT_ISSET_ID, value);
  }

  /**
   * 商品销售数量 *
   */
  public int getProductSaleCount() {
    return this.productSaleCount;
  }

  /**
   * 商品销售数量 *
   */
  public ProductSaleDetailVo setProductSaleCount(int productSaleCount) {
    this.productSaleCount = productSaleCount;
    setProductSaleCountIsSet(true);
    return this;
  }

  public void unsetProductSaleCount() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PRODUCTSALECOUNT_ISSET_ID);
  }

  /** Returns true if field productSaleCount is set (has been assigned a value) and false otherwise */
  public boolean isSetProductSaleCount() {
    return EncodingUtils.testBit(__isset_bitfield, __PRODUCTSALECOUNT_ISSET_ID);
  }

  public void setProductSaleCountIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PRODUCTSALECOUNT_ISSET_ID, value);
  }

  /**
   * 商品销售金额 *
   */
  public double getProductSaleAmount() {
    return this.productSaleAmount;
  }

  /**
   * 商品销售金额 *
   */
  public ProductSaleDetailVo setProductSaleAmount(double productSaleAmount) {
    this.productSaleAmount = productSaleAmount;
    setProductSaleAmountIsSet(true);
    return this;
  }

  public void unsetProductSaleAmount() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PRODUCTSALEAMOUNT_ISSET_ID);
  }

  /** Returns true if field productSaleAmount is set (has been assigned a value) and false otherwise */
  public boolean isSetProductSaleAmount() {
    return EncodingUtils.testBit(__isset_bitfield, __PRODUCTSALEAMOUNT_ISSET_ID);
  }

  public void setProductSaleAmountIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PRODUCTSALEAMOUNT_ISSET_ID, value);
  }

  /**
   * 退款总金额 *
   */
  public double getRefundAmount() {
    return this.refundAmount;
  }

  /**
   * 退款总金额 *
   */
  public ProductSaleDetailVo setRefundAmount(double refundAmount) {
    this.refundAmount = refundAmount;
    setRefundAmountIsSet(true);
    return this;
  }

  public void unsetRefundAmount() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __REFUNDAMOUNT_ISSET_ID);
  }

  /** Returns true if field refundAmount is set (has been assigned a value) and false otherwise */
  public boolean isSetRefundAmount() {
    return EncodingUtils.testBit(__isset_bitfield, __REFUNDAMOUNT_ISSET_ID);
  }

  public void setRefundAmountIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __REFUNDAMOUNT_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ORG_CODE:
      if (value == null) {
        unsetOrgCode();
      } else {
        setOrgCode((String)value);
      }
      break;

    case ORG_NAME:
      if (value == null) {
        unsetOrgName();
      } else {
        setOrgName((String)value);
      }
      break;

    case ADD_PRODUCT_COUNT:
      if (value == null) {
        unsetAddProductCount();
      } else {
        setAddProductCount((Integer)value);
      }
      break;

    case PRODUCT_COUNT:
      if (value == null) {
        unsetProductCount();
      } else {
        setProductCount((Integer)value);
      }
      break;

    case PRODUCT_SALE_COUNT:
      if (value == null) {
        unsetProductSaleCount();
      } else {
        setProductSaleCount((Integer)value);
      }
      break;

    case PRODUCT_SALE_AMOUNT:
      if (value == null) {
        unsetProductSaleAmount();
      } else {
        setProductSaleAmount((Double)value);
      }
      break;

    case REFUND_AMOUNT:
      if (value == null) {
        unsetRefundAmount();
      } else {
        setRefundAmount((Double)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ORG_CODE:
      return getOrgCode();

    case ORG_NAME:
      return getOrgName();

    case ADD_PRODUCT_COUNT:
      return Integer.valueOf(getAddProductCount());

    case PRODUCT_COUNT:
      return Integer.valueOf(getProductCount());

    case PRODUCT_SALE_COUNT:
      return Integer.valueOf(getProductSaleCount());

    case PRODUCT_SALE_AMOUNT:
      return Double.valueOf(getProductSaleAmount());

    case REFUND_AMOUNT:
      return Double.valueOf(getRefundAmount());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ORG_CODE:
      return isSetOrgCode();
    case ORG_NAME:
      return isSetOrgName();
    case ADD_PRODUCT_COUNT:
      return isSetAddProductCount();
    case PRODUCT_COUNT:
      return isSetProductCount();
    case PRODUCT_SALE_COUNT:
      return isSetProductSaleCount();
    case PRODUCT_SALE_AMOUNT:
      return isSetProductSaleAmount();
    case REFUND_AMOUNT:
      return isSetRefundAmount();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ProductSaleDetailVo)
      return this.equals((ProductSaleDetailVo)that);
    return false;
  }

  public boolean equals(ProductSaleDetailVo that) {
    if (that == null)
      return false;

    boolean this_present_orgCode = true && this.isSetOrgCode();
    boolean that_present_orgCode = true && that.isSetOrgCode();
    if (this_present_orgCode || that_present_orgCode) {
      if (!(this_present_orgCode && that_present_orgCode))
        return false;
      if (!this.orgCode.equals(that.orgCode))
        return false;
    }

    boolean this_present_orgName = true && this.isSetOrgName();
    boolean that_present_orgName = true && that.isSetOrgName();
    if (this_present_orgName || that_present_orgName) {
      if (!(this_present_orgName && that_present_orgName))
        return false;
      if (!this.orgName.equals(that.orgName))
        return false;
    }

    boolean this_present_addProductCount = true && this.isSetAddProductCount();
    boolean that_present_addProductCount = true && that.isSetAddProductCount();
    if (this_present_addProductCount || that_present_addProductCount) {
      if (!(this_present_addProductCount && that_present_addProductCount))
        return false;
      if (this.addProductCount != that.addProductCount)
        return false;
    }

    boolean this_present_productCount = true && this.isSetProductCount();
    boolean that_present_productCount = true && that.isSetProductCount();
    if (this_present_productCount || that_present_productCount) {
      if (!(this_present_productCount && that_present_productCount))
        return false;
      if (this.productCount != that.productCount)
        return false;
    }

    boolean this_present_productSaleCount = true && this.isSetProductSaleCount();
    boolean that_present_productSaleCount = true && that.isSetProductSaleCount();
    if (this_present_productSaleCount || that_present_productSaleCount) {
      if (!(this_present_productSaleCount && that_present_productSaleCount))
        return false;
      if (this.productSaleCount != that.productSaleCount)
        return false;
    }

    boolean this_present_productSaleAmount = true && this.isSetProductSaleAmount();
    boolean that_present_productSaleAmount = true && that.isSetProductSaleAmount();
    if (this_present_productSaleAmount || that_present_productSaleAmount) {
      if (!(this_present_productSaleAmount && that_present_productSaleAmount))
        return false;
      if (this.productSaleAmount != that.productSaleAmount)
        return false;
    }

    boolean this_present_refundAmount = true && this.isSetRefundAmount();
    boolean that_present_refundAmount = true && that.isSetRefundAmount();
    if (this_present_refundAmount || that_present_refundAmount) {
      if (!(this_present_refundAmount && that_present_refundAmount))
        return false;
      if (this.refundAmount != that.refundAmount)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_orgCode = true && (isSetOrgCode());
    list.add(present_orgCode);
    if (present_orgCode)
      list.add(orgCode);

    boolean present_orgName = true && (isSetOrgName());
    list.add(present_orgName);
    if (present_orgName)
      list.add(orgName);

    boolean present_addProductCount = true && (isSetAddProductCount());
    list.add(present_addProductCount);
    if (present_addProductCount)
      list.add(addProductCount);

    boolean present_productCount = true && (isSetProductCount());
    list.add(present_productCount);
    if (present_productCount)
      list.add(productCount);

    boolean present_productSaleCount = true && (isSetProductSaleCount());
    list.add(present_productSaleCount);
    if (present_productSaleCount)
      list.add(productSaleCount);

    boolean present_productSaleAmount = true && (isSetProductSaleAmount());
    list.add(present_productSaleAmount);
    if (present_productSaleAmount)
      list.add(productSaleAmount);

    boolean present_refundAmount = true && (isSetRefundAmount());
    list.add(present_refundAmount);
    if (present_refundAmount)
      list.add(refundAmount);

    return list.hashCode();
  }

  @Override
  public int compareTo(ProductSaleDetailVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetOrgCode()).compareTo(other.isSetOrgCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOrgCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.orgCode, other.orgCode);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOrgName()).compareTo(other.isSetOrgName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOrgName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.orgName, other.orgName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAddProductCount()).compareTo(other.isSetAddProductCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAddProductCount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.addProductCount, other.addProductCount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetProductCount()).compareTo(other.isSetProductCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProductCount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.productCount, other.productCount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetProductSaleCount()).compareTo(other.isSetProductSaleCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProductSaleCount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.productSaleCount, other.productSaleCount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetProductSaleAmount()).compareTo(other.isSetProductSaleAmount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProductSaleAmount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.productSaleAmount, other.productSaleAmount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRefundAmount()).compareTo(other.isSetRefundAmount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRefundAmount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.refundAmount, other.refundAmount);
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
    StringBuilder sb = new StringBuilder("ProductSaleDetailVo(");
    boolean first = true;

    if (isSetOrgCode()) {
      sb.append("orgCode:");
      if (this.orgCode == null) {
        sb.append("null");
      } else {
        sb.append(this.orgCode);
      }
      first = false;
    }
    if (isSetOrgName()) {
      if (!first) sb.append(", ");
      sb.append("orgName:");
      if (this.orgName == null) {
        sb.append("null");
      } else {
        sb.append(this.orgName);
      }
      first = false;
    }
    if (isSetAddProductCount()) {
      if (!first) sb.append(", ");
      sb.append("addProductCount:");
      sb.append(this.addProductCount);
      first = false;
    }
    if (isSetProductCount()) {
      if (!first) sb.append(", ");
      sb.append("productCount:");
      sb.append(this.productCount);
      first = false;
    }
    if (isSetProductSaleCount()) {
      if (!first) sb.append(", ");
      sb.append("productSaleCount:");
      sb.append(this.productSaleCount);
      first = false;
    }
    if (isSetProductSaleAmount()) {
      if (!first) sb.append(", ");
      sb.append("productSaleAmount:");
      sb.append(this.productSaleAmount);
      first = false;
    }
    if (isSetRefundAmount()) {
      if (!first) sb.append(", ");
      sb.append("refundAmount:");
      sb.append(this.refundAmount);
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class ProductSaleDetailVoStandardSchemeFactory implements SchemeFactory {
    public ProductSaleDetailVoStandardScheme getScheme() {
      return new ProductSaleDetailVoStandardScheme();
    }
  }

  private static class ProductSaleDetailVoStandardScheme extends StandardScheme<ProductSaleDetailVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ProductSaleDetailVo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ORG_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.orgCode = iprot.readString();
              struct.setOrgCodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // ORG_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.orgName = iprot.readString();
              struct.setOrgNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // ADD_PRODUCT_COUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.addProductCount = iprot.readI32();
              struct.setAddProductCountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // PRODUCT_COUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.productCount = iprot.readI32();
              struct.setProductCountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // PRODUCT_SALE_COUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.productSaleCount = iprot.readI32();
              struct.setProductSaleCountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // PRODUCT_SALE_AMOUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.productSaleAmount = iprot.readDouble();
              struct.setProductSaleAmountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // REFUND_AMOUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.refundAmount = iprot.readDouble();
              struct.setRefundAmountIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ProductSaleDetailVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.orgCode != null) {
        if (struct.isSetOrgCode()) {
          oprot.writeFieldBegin(ORG_CODE_FIELD_DESC);
          oprot.writeString(struct.orgCode);
          oprot.writeFieldEnd();
        }
      }
      if (struct.orgName != null) {
        if (struct.isSetOrgName()) {
          oprot.writeFieldBegin(ORG_NAME_FIELD_DESC);
          oprot.writeString(struct.orgName);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetAddProductCount()) {
        oprot.writeFieldBegin(ADD_PRODUCT_COUNT_FIELD_DESC);
        oprot.writeI32(struct.addProductCount);
        oprot.writeFieldEnd();
      }
      if (struct.isSetProductCount()) {
        oprot.writeFieldBegin(PRODUCT_COUNT_FIELD_DESC);
        oprot.writeI32(struct.productCount);
        oprot.writeFieldEnd();
      }
      if (struct.isSetProductSaleCount()) {
        oprot.writeFieldBegin(PRODUCT_SALE_COUNT_FIELD_DESC);
        oprot.writeI32(struct.productSaleCount);
        oprot.writeFieldEnd();
      }
      if (struct.isSetProductSaleAmount()) {
        oprot.writeFieldBegin(PRODUCT_SALE_AMOUNT_FIELD_DESC);
        oprot.writeDouble(struct.productSaleAmount);
        oprot.writeFieldEnd();
      }
      if (struct.isSetRefundAmount()) {
        oprot.writeFieldBegin(REFUND_AMOUNT_FIELD_DESC);
        oprot.writeDouble(struct.refundAmount);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ProductSaleDetailVoTupleSchemeFactory implements SchemeFactory {
    public ProductSaleDetailVoTupleScheme getScheme() {
      return new ProductSaleDetailVoTupleScheme();
    }
  }

  private static class ProductSaleDetailVoTupleScheme extends TupleScheme<ProductSaleDetailVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ProductSaleDetailVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetOrgCode()) {
        optionals.set(0);
      }
      if (struct.isSetOrgName()) {
        optionals.set(1);
      }
      if (struct.isSetAddProductCount()) {
        optionals.set(2);
      }
      if (struct.isSetProductCount()) {
        optionals.set(3);
      }
      if (struct.isSetProductSaleCount()) {
        optionals.set(4);
      }
      if (struct.isSetProductSaleAmount()) {
        optionals.set(5);
      }
      if (struct.isSetRefundAmount()) {
        optionals.set(6);
      }
      oprot.writeBitSet(optionals, 7);
      if (struct.isSetOrgCode()) {
        oprot.writeString(struct.orgCode);
      }
      if (struct.isSetOrgName()) {
        oprot.writeString(struct.orgName);
      }
      if (struct.isSetAddProductCount()) {
        oprot.writeI32(struct.addProductCount);
      }
      if (struct.isSetProductCount()) {
        oprot.writeI32(struct.productCount);
      }
      if (struct.isSetProductSaleCount()) {
        oprot.writeI32(struct.productSaleCount);
      }
      if (struct.isSetProductSaleAmount()) {
        oprot.writeDouble(struct.productSaleAmount);
      }
      if (struct.isSetRefundAmount()) {
        oprot.writeDouble(struct.refundAmount);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ProductSaleDetailVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(7);
      if (incoming.get(0)) {
        struct.orgCode = iprot.readString();
        struct.setOrgCodeIsSet(true);
      }
      if (incoming.get(1)) {
        struct.orgName = iprot.readString();
        struct.setOrgNameIsSet(true);
      }
      if (incoming.get(2)) {
        struct.addProductCount = iprot.readI32();
        struct.setAddProductCountIsSet(true);
      }
      if (incoming.get(3)) {
        struct.productCount = iprot.readI32();
        struct.setProductCountIsSet(true);
      }
      if (incoming.get(4)) {
        struct.productSaleCount = iprot.readI32();
        struct.setProductSaleCountIsSet(true);
      }
      if (incoming.get(5)) {
        struct.productSaleAmount = iprot.readDouble();
        struct.setProductSaleAmountIsSet(true);
      }
      if (incoming.get(6)) {
        struct.refundAmount = iprot.readDouble();
        struct.setRefundAmountIsSet(true);
      }
    }
  }

}

