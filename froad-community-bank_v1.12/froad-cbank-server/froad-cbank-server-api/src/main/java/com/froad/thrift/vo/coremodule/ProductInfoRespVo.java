/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.froad.thrift.vo.coremodule;

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
 * 商品指标信息
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class ProductInfoRespVo implements org.apache.thrift.TBase<ProductInfoRespVo, ProductInfoRespVo._Fields>, java.io.Serializable, Cloneable, Comparable<ProductInfoRespVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ProductInfoRespVo");

  private static final org.apache.thrift.protocol.TField PRODUCT_COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("productCount", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField PRODUCT_CUMULATION_FIELD_DESC = new org.apache.thrift.protocol.TField("productCumulation", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField PRODUCT_DOWN_SUM_FIELD_DESC = new org.apache.thrift.protocol.TField("productDownSum", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField PRODUCT_DOWN_COMULATION_FIELD_DESC = new org.apache.thrift.protocol.TField("productDownComulation", org.apache.thrift.protocol.TType.I32, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ProductInfoRespVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ProductInfoRespVoTupleSchemeFactory());
  }

  /**
   * 商品数量*
   */
  public int productCount; // optional
  /**
   * 累计商品数量*
   */
  public int productCumulation; // optional
  /**
   * 下架商品数量*
   */
  public int productDownSum; // optional
  /**
   * 累计下架商品数量*
   */
  public int productDownComulation; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 商品数量*
     */
    PRODUCT_COUNT((short)1, "productCount"),
    /**
     * 累计商品数量*
     */
    PRODUCT_CUMULATION((short)2, "productCumulation"),
    /**
     * 下架商品数量*
     */
    PRODUCT_DOWN_SUM((short)3, "productDownSum"),
    /**
     * 累计下架商品数量*
     */
    PRODUCT_DOWN_COMULATION((short)4, "productDownComulation");

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
        case 1: // PRODUCT_COUNT
          return PRODUCT_COUNT;
        case 2: // PRODUCT_CUMULATION
          return PRODUCT_CUMULATION;
        case 3: // PRODUCT_DOWN_SUM
          return PRODUCT_DOWN_SUM;
        case 4: // PRODUCT_DOWN_COMULATION
          return PRODUCT_DOWN_COMULATION;
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
  private static final int __PRODUCTCOUNT_ISSET_ID = 0;
  private static final int __PRODUCTCUMULATION_ISSET_ID = 1;
  private static final int __PRODUCTDOWNSUM_ISSET_ID = 2;
  private static final int __PRODUCTDOWNCOMULATION_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.PRODUCT_COUNT,_Fields.PRODUCT_CUMULATION,_Fields.PRODUCT_DOWN_SUM,_Fields.PRODUCT_DOWN_COMULATION};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PRODUCT_COUNT, new org.apache.thrift.meta_data.FieldMetaData("productCount", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PRODUCT_CUMULATION, new org.apache.thrift.meta_data.FieldMetaData("productCumulation", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PRODUCT_DOWN_SUM, new org.apache.thrift.meta_data.FieldMetaData("productDownSum", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PRODUCT_DOWN_COMULATION, new org.apache.thrift.meta_data.FieldMetaData("productDownComulation", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ProductInfoRespVo.class, metaDataMap);
  }

  public ProductInfoRespVo() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ProductInfoRespVo(ProductInfoRespVo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.productCount = other.productCount;
    this.productCumulation = other.productCumulation;
    this.productDownSum = other.productDownSum;
    this.productDownComulation = other.productDownComulation;
  }

  public ProductInfoRespVo deepCopy() {
    return new ProductInfoRespVo(this);
  }

  @Override
  public void clear() {
    setProductCountIsSet(false);
    this.productCount = 0;
    setProductCumulationIsSet(false);
    this.productCumulation = 0;
    setProductDownSumIsSet(false);
    this.productDownSum = 0;
    setProductDownComulationIsSet(false);
    this.productDownComulation = 0;
  }

  /**
   * 商品数量*
   */
  public int getProductCount() {
    return this.productCount;
  }

  /**
   * 商品数量*
   */
  public ProductInfoRespVo setProductCount(int productCount) {
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
   * 累计商品数量*
   */
  public int getProductCumulation() {
    return this.productCumulation;
  }

  /**
   * 累计商品数量*
   */
  public ProductInfoRespVo setProductCumulation(int productCumulation) {
    this.productCumulation = productCumulation;
    setProductCumulationIsSet(true);
    return this;
  }

  public void unsetProductCumulation() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PRODUCTCUMULATION_ISSET_ID);
  }

  /** Returns true if field productCumulation is set (has been assigned a value) and false otherwise */
  public boolean isSetProductCumulation() {
    return EncodingUtils.testBit(__isset_bitfield, __PRODUCTCUMULATION_ISSET_ID);
  }

  public void setProductCumulationIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PRODUCTCUMULATION_ISSET_ID, value);
  }

  /**
   * 下架商品数量*
   */
  public int getProductDownSum() {
    return this.productDownSum;
  }

  /**
   * 下架商品数量*
   */
  public ProductInfoRespVo setProductDownSum(int productDownSum) {
    this.productDownSum = productDownSum;
    setProductDownSumIsSet(true);
    return this;
  }

  public void unsetProductDownSum() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PRODUCTDOWNSUM_ISSET_ID);
  }

  /** Returns true if field productDownSum is set (has been assigned a value) and false otherwise */
  public boolean isSetProductDownSum() {
    return EncodingUtils.testBit(__isset_bitfield, __PRODUCTDOWNSUM_ISSET_ID);
  }

  public void setProductDownSumIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PRODUCTDOWNSUM_ISSET_ID, value);
  }

  /**
   * 累计下架商品数量*
   */
  public int getProductDownComulation() {
    return this.productDownComulation;
  }

  /**
   * 累计下架商品数量*
   */
  public ProductInfoRespVo setProductDownComulation(int productDownComulation) {
    this.productDownComulation = productDownComulation;
    setProductDownComulationIsSet(true);
    return this;
  }

  public void unsetProductDownComulation() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PRODUCTDOWNCOMULATION_ISSET_ID);
  }

  /** Returns true if field productDownComulation is set (has been assigned a value) and false otherwise */
  public boolean isSetProductDownComulation() {
    return EncodingUtils.testBit(__isset_bitfield, __PRODUCTDOWNCOMULATION_ISSET_ID);
  }

  public void setProductDownComulationIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PRODUCTDOWNCOMULATION_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PRODUCT_COUNT:
      if (value == null) {
        unsetProductCount();
      } else {
        setProductCount((Integer)value);
      }
      break;

    case PRODUCT_CUMULATION:
      if (value == null) {
        unsetProductCumulation();
      } else {
        setProductCumulation((Integer)value);
      }
      break;

    case PRODUCT_DOWN_SUM:
      if (value == null) {
        unsetProductDownSum();
      } else {
        setProductDownSum((Integer)value);
      }
      break;

    case PRODUCT_DOWN_COMULATION:
      if (value == null) {
        unsetProductDownComulation();
      } else {
        setProductDownComulation((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PRODUCT_COUNT:
      return Integer.valueOf(getProductCount());

    case PRODUCT_CUMULATION:
      return Integer.valueOf(getProductCumulation());

    case PRODUCT_DOWN_SUM:
      return Integer.valueOf(getProductDownSum());

    case PRODUCT_DOWN_COMULATION:
      return Integer.valueOf(getProductDownComulation());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case PRODUCT_COUNT:
      return isSetProductCount();
    case PRODUCT_CUMULATION:
      return isSetProductCumulation();
    case PRODUCT_DOWN_SUM:
      return isSetProductDownSum();
    case PRODUCT_DOWN_COMULATION:
      return isSetProductDownComulation();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ProductInfoRespVo)
      return this.equals((ProductInfoRespVo)that);
    return false;
  }

  public boolean equals(ProductInfoRespVo that) {
    if (that == null)
      return false;

    boolean this_present_productCount = true && this.isSetProductCount();
    boolean that_present_productCount = true && that.isSetProductCount();
    if (this_present_productCount || that_present_productCount) {
      if (!(this_present_productCount && that_present_productCount))
        return false;
      if (this.productCount != that.productCount)
        return false;
    }

    boolean this_present_productCumulation = true && this.isSetProductCumulation();
    boolean that_present_productCumulation = true && that.isSetProductCumulation();
    if (this_present_productCumulation || that_present_productCumulation) {
      if (!(this_present_productCumulation && that_present_productCumulation))
        return false;
      if (this.productCumulation != that.productCumulation)
        return false;
    }

    boolean this_present_productDownSum = true && this.isSetProductDownSum();
    boolean that_present_productDownSum = true && that.isSetProductDownSum();
    if (this_present_productDownSum || that_present_productDownSum) {
      if (!(this_present_productDownSum && that_present_productDownSum))
        return false;
      if (this.productDownSum != that.productDownSum)
        return false;
    }

    boolean this_present_productDownComulation = true && this.isSetProductDownComulation();
    boolean that_present_productDownComulation = true && that.isSetProductDownComulation();
    if (this_present_productDownComulation || that_present_productDownComulation) {
      if (!(this_present_productDownComulation && that_present_productDownComulation))
        return false;
      if (this.productDownComulation != that.productDownComulation)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_productCount = true && (isSetProductCount());
    list.add(present_productCount);
    if (present_productCount)
      list.add(productCount);

    boolean present_productCumulation = true && (isSetProductCumulation());
    list.add(present_productCumulation);
    if (present_productCumulation)
      list.add(productCumulation);

    boolean present_productDownSum = true && (isSetProductDownSum());
    list.add(present_productDownSum);
    if (present_productDownSum)
      list.add(productDownSum);

    boolean present_productDownComulation = true && (isSetProductDownComulation());
    list.add(present_productDownComulation);
    if (present_productDownComulation)
      list.add(productDownComulation);

    return list.hashCode();
  }

  @Override
  public int compareTo(ProductInfoRespVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

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
    lastComparison = Boolean.valueOf(isSetProductCumulation()).compareTo(other.isSetProductCumulation());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProductCumulation()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.productCumulation, other.productCumulation);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetProductDownSum()).compareTo(other.isSetProductDownSum());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProductDownSum()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.productDownSum, other.productDownSum);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetProductDownComulation()).compareTo(other.isSetProductDownComulation());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProductDownComulation()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.productDownComulation, other.productDownComulation);
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
    StringBuilder sb = new StringBuilder("ProductInfoRespVo(");
    boolean first = true;

    if (isSetProductCount()) {
      sb.append("productCount:");
      sb.append(this.productCount);
      first = false;
    }
    if (isSetProductCumulation()) {
      if (!first) sb.append(", ");
      sb.append("productCumulation:");
      sb.append(this.productCumulation);
      first = false;
    }
    if (isSetProductDownSum()) {
      if (!first) sb.append(", ");
      sb.append("productDownSum:");
      sb.append(this.productDownSum);
      first = false;
    }
    if (isSetProductDownComulation()) {
      if (!first) sb.append(", ");
      sb.append("productDownComulation:");
      sb.append(this.productDownComulation);
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

  private static class ProductInfoRespVoStandardSchemeFactory implements SchemeFactory {
    public ProductInfoRespVoStandardScheme getScheme() {
      return new ProductInfoRespVoStandardScheme();
    }
  }

  private static class ProductInfoRespVoStandardScheme extends StandardScheme<ProductInfoRespVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ProductInfoRespVo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PRODUCT_COUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.productCount = iprot.readI32();
              struct.setProductCountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PRODUCT_CUMULATION
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.productCumulation = iprot.readI32();
              struct.setProductCumulationIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // PRODUCT_DOWN_SUM
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.productDownSum = iprot.readI32();
              struct.setProductDownSumIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // PRODUCT_DOWN_COMULATION
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.productDownComulation = iprot.readI32();
              struct.setProductDownComulationIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ProductInfoRespVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetProductCount()) {
        oprot.writeFieldBegin(PRODUCT_COUNT_FIELD_DESC);
        oprot.writeI32(struct.productCount);
        oprot.writeFieldEnd();
      }
      if (struct.isSetProductCumulation()) {
        oprot.writeFieldBegin(PRODUCT_CUMULATION_FIELD_DESC);
        oprot.writeI32(struct.productCumulation);
        oprot.writeFieldEnd();
      }
      if (struct.isSetProductDownSum()) {
        oprot.writeFieldBegin(PRODUCT_DOWN_SUM_FIELD_DESC);
        oprot.writeI32(struct.productDownSum);
        oprot.writeFieldEnd();
      }
      if (struct.isSetProductDownComulation()) {
        oprot.writeFieldBegin(PRODUCT_DOWN_COMULATION_FIELD_DESC);
        oprot.writeI32(struct.productDownComulation);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ProductInfoRespVoTupleSchemeFactory implements SchemeFactory {
    public ProductInfoRespVoTupleScheme getScheme() {
      return new ProductInfoRespVoTupleScheme();
    }
  }

  private static class ProductInfoRespVoTupleScheme extends TupleScheme<ProductInfoRespVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ProductInfoRespVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetProductCount()) {
        optionals.set(0);
      }
      if (struct.isSetProductCumulation()) {
        optionals.set(1);
      }
      if (struct.isSetProductDownSum()) {
        optionals.set(2);
      }
      if (struct.isSetProductDownComulation()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetProductCount()) {
        oprot.writeI32(struct.productCount);
      }
      if (struct.isSetProductCumulation()) {
        oprot.writeI32(struct.productCumulation);
      }
      if (struct.isSetProductDownSum()) {
        oprot.writeI32(struct.productDownSum);
      }
      if (struct.isSetProductDownComulation()) {
        oprot.writeI32(struct.productDownComulation);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ProductInfoRespVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.productCount = iprot.readI32();
        struct.setProductCountIsSet(true);
      }
      if (incoming.get(1)) {
        struct.productCumulation = iprot.readI32();
        struct.setProductCumulationIsSet(true);
      }
      if (incoming.get(2)) {
        struct.productDownSum = iprot.readI32();
        struct.setProductDownSumIsSet(true);
      }
      if (incoming.get(3)) {
        struct.productDownComulation = iprot.readI32();
        struct.setProductDownComulationIsSet(true);
      }
    }
  }

}

