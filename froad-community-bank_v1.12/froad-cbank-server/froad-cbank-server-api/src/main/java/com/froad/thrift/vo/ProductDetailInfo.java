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
 * 商品信息详情
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class ProductDetailInfo implements org.apache.thrift.TBase<ProductDetailInfo, ProductDetailInfo._Fields>, java.io.Serializable, Cloneable, Comparable<ProductDetailInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ProductDetailInfo");

  private static final org.apache.thrift.protocol.TField PRODUCT_DETAIL_VO_FIELD_DESC = new org.apache.thrift.protocol.TField("productDetailVo", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField OLD_PRODUCT_DETAIL_VO_FIELD_DESC = new org.apache.thrift.protocol.TField("oldProductDetailVo", org.apache.thrift.protocol.TType.STRUCT, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ProductDetailInfoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ProductDetailInfoTupleSchemeFactory());
  }

  /**
   * 商品信息Vo，最新信息
   */
  public ProductDetailVo productDetailVo; // required
  /**
   * 原始商品信息Vo，修改前信息
   */
  public ProductDetailVo oldProductDetailVo; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 商品信息Vo，最新信息
     */
    PRODUCT_DETAIL_VO((short)1, "productDetailVo"),
    /**
     * 原始商品信息Vo，修改前信息
     */
    OLD_PRODUCT_DETAIL_VO((short)2, "oldProductDetailVo");

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
        case 1: // PRODUCT_DETAIL_VO
          return PRODUCT_DETAIL_VO;
        case 2: // OLD_PRODUCT_DETAIL_VO
          return OLD_PRODUCT_DETAIL_VO;
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
    tmpMap.put(_Fields.PRODUCT_DETAIL_VO, new org.apache.thrift.meta_data.FieldMetaData("productDetailVo", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT        , "ProductDetailVo")));
    tmpMap.put(_Fields.OLD_PRODUCT_DETAIL_VO, new org.apache.thrift.meta_data.FieldMetaData("oldProductDetailVo", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT        , "ProductDetailVo")));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ProductDetailInfo.class, metaDataMap);
  }

  public ProductDetailInfo() {
  }

  public ProductDetailInfo(
    ProductDetailVo productDetailVo,
    ProductDetailVo oldProductDetailVo)
  {
    this();
    this.productDetailVo = productDetailVo;
    this.oldProductDetailVo = oldProductDetailVo;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ProductDetailInfo(ProductDetailInfo other) {
    if (other.isSetProductDetailVo()) {
      this.productDetailVo = other.productDetailVo;
    }
    if (other.isSetOldProductDetailVo()) {
      this.oldProductDetailVo = other.oldProductDetailVo;
    }
  }

  public ProductDetailInfo deepCopy() {
    return new ProductDetailInfo(this);
  }

  @Override
  public void clear() {
    this.productDetailVo = null;
    this.oldProductDetailVo = null;
  }

  /**
   * 商品信息Vo，最新信息
   */
  public ProductDetailVo getProductDetailVo() {
    return this.productDetailVo;
  }

  /**
   * 商品信息Vo，最新信息
   */
  public ProductDetailInfo setProductDetailVo(ProductDetailVo productDetailVo) {
    this.productDetailVo = productDetailVo;
    return this;
  }

  public void unsetProductDetailVo() {
    this.productDetailVo = null;
  }

  /** Returns true if field productDetailVo is set (has been assigned a value) and false otherwise */
  public boolean isSetProductDetailVo() {
    return this.productDetailVo != null;
  }

  public void setProductDetailVoIsSet(boolean value) {
    if (!value) {
      this.productDetailVo = null;
    }
  }

  /**
   * 原始商品信息Vo，修改前信息
   */
  public ProductDetailVo getOldProductDetailVo() {
    return this.oldProductDetailVo;
  }

  /**
   * 原始商品信息Vo，修改前信息
   */
  public ProductDetailInfo setOldProductDetailVo(ProductDetailVo oldProductDetailVo) {
    this.oldProductDetailVo = oldProductDetailVo;
    return this;
  }

  public void unsetOldProductDetailVo() {
    this.oldProductDetailVo = null;
  }

  /** Returns true if field oldProductDetailVo is set (has been assigned a value) and false otherwise */
  public boolean isSetOldProductDetailVo() {
    return this.oldProductDetailVo != null;
  }

  public void setOldProductDetailVoIsSet(boolean value) {
    if (!value) {
      this.oldProductDetailVo = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PRODUCT_DETAIL_VO:
      if (value == null) {
        unsetProductDetailVo();
      } else {
        setProductDetailVo((ProductDetailVo)value);
      }
      break;

    case OLD_PRODUCT_DETAIL_VO:
      if (value == null) {
        unsetOldProductDetailVo();
      } else {
        setOldProductDetailVo((ProductDetailVo)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PRODUCT_DETAIL_VO:
      return getProductDetailVo();

    case OLD_PRODUCT_DETAIL_VO:
      return getOldProductDetailVo();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case PRODUCT_DETAIL_VO:
      return isSetProductDetailVo();
    case OLD_PRODUCT_DETAIL_VO:
      return isSetOldProductDetailVo();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ProductDetailInfo)
      return this.equals((ProductDetailInfo)that);
    return false;
  }

  public boolean equals(ProductDetailInfo that) {
    if (that == null)
      return false;

    boolean this_present_productDetailVo = true && this.isSetProductDetailVo();
    boolean that_present_productDetailVo = true && that.isSetProductDetailVo();
    if (this_present_productDetailVo || that_present_productDetailVo) {
      if (!(this_present_productDetailVo && that_present_productDetailVo))
        return false;
      if (!this.productDetailVo.equals(that.productDetailVo))
        return false;
    }

    boolean this_present_oldProductDetailVo = true && this.isSetOldProductDetailVo();
    boolean that_present_oldProductDetailVo = true && that.isSetOldProductDetailVo();
    if (this_present_oldProductDetailVo || that_present_oldProductDetailVo) {
      if (!(this_present_oldProductDetailVo && that_present_oldProductDetailVo))
        return false;
      if (!this.oldProductDetailVo.equals(that.oldProductDetailVo))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_productDetailVo = true && (isSetProductDetailVo());
    list.add(present_productDetailVo);
    if (present_productDetailVo)
      list.add(productDetailVo);

    boolean present_oldProductDetailVo = true && (isSetOldProductDetailVo());
    list.add(present_oldProductDetailVo);
    if (present_oldProductDetailVo)
      list.add(oldProductDetailVo);

    return list.hashCode();
  }

  @Override
  public int compareTo(ProductDetailInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetProductDetailVo()).compareTo(other.isSetProductDetailVo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProductDetailVo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.productDetailVo, other.productDetailVo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOldProductDetailVo()).compareTo(other.isSetOldProductDetailVo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOldProductDetailVo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.oldProductDetailVo, other.oldProductDetailVo);
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
    StringBuilder sb = new StringBuilder("ProductDetailInfo(");
    boolean first = true;

    sb.append("productDetailVo:");
    if (this.productDetailVo == null) {
      sb.append("null");
    } else {
      sb.append(this.productDetailVo);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("oldProductDetailVo:");
    if (this.oldProductDetailVo == null) {
      sb.append("null");
    } else {
      sb.append(this.oldProductDetailVo);
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

  private static class ProductDetailInfoStandardSchemeFactory implements SchemeFactory {
    public ProductDetailInfoStandardScheme getScheme() {
      return new ProductDetailInfoStandardScheme();
    }
  }

  private static class ProductDetailInfoStandardScheme extends StandardScheme<ProductDetailInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ProductDetailInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PRODUCT_DETAIL_VO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.productDetailVo = new ProductDetailVo();
              struct.productDetailVo.read(iprot);
              struct.setProductDetailVoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // OLD_PRODUCT_DETAIL_VO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.oldProductDetailVo = new ProductDetailVo();
              struct.oldProductDetailVo.read(iprot);
              struct.setOldProductDetailVoIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ProductDetailInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.productDetailVo != null) {
        oprot.writeFieldBegin(PRODUCT_DETAIL_VO_FIELD_DESC);
        struct.productDetailVo.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.oldProductDetailVo != null) {
        oprot.writeFieldBegin(OLD_PRODUCT_DETAIL_VO_FIELD_DESC);
        struct.oldProductDetailVo.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ProductDetailInfoTupleSchemeFactory implements SchemeFactory {
    public ProductDetailInfoTupleScheme getScheme() {
      return new ProductDetailInfoTupleScheme();
    }
  }

  private static class ProductDetailInfoTupleScheme extends TupleScheme<ProductDetailInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ProductDetailInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetProductDetailVo()) {
        optionals.set(0);
      }
      if (struct.isSetOldProductDetailVo()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetProductDetailVo()) {
        struct.productDetailVo.write(oprot);
      }
      if (struct.isSetOldProductDetailVo()) {
        struct.oldProductDetailVo.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ProductDetailInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.productDetailVo = new ProductDetailVo();
        struct.productDetailVo.read(iprot);
        struct.setProductDetailVoIsSet(true);
      }
      if (incoming.get(1)) {
        struct.oldProductDetailVo = new ProductDetailVo();
        struct.oldProductDetailVo.read(iprot);
        struct.setOldProductDetailVoIsSet(true);
      }
    }
  }

}

