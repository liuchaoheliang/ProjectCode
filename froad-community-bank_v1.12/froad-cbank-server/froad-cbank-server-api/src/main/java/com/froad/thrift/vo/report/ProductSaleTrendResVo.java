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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class ProductSaleTrendResVo implements org.apache.thrift.TBase<ProductSaleTrendResVo, ProductSaleTrendResVo._Fields>, java.io.Serializable, Cloneable, Comparable<ProductSaleTrendResVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ProductSaleTrendResVo");

  private static final org.apache.thrift.protocol.TField RESULT_VO_FIELD_DESC = new org.apache.thrift.protocol.TField("resultVo", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField PRODUCT_SALE_TREND_VOS_FIELD_DESC = new org.apache.thrift.protocol.TField("productSaleTrendVos", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ProductSaleTrendResVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ProductSaleTrendResVoTupleSchemeFactory());
  }

  /**
   * 返回结果 *
   */
  public com.froad.thrift.vo.ResultVo resultVo; // required
  /**
   * 销售走势集合 *
   */
  public List<ProductSaleTrendVo> productSaleTrendVos; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 返回结果 *
     */
    RESULT_VO((short)1, "resultVo"),
    /**
     * 销售走势集合 *
     */
    PRODUCT_SALE_TREND_VOS((short)2, "productSaleTrendVos");

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
        case 1: // RESULT_VO
          return RESULT_VO;
        case 2: // PRODUCT_SALE_TREND_VOS
          return PRODUCT_SALE_TREND_VOS;
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
    tmpMap.put(_Fields.RESULT_VO, new org.apache.thrift.meta_data.FieldMetaData("resultVo", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.ResultVo.class)));
    tmpMap.put(_Fields.PRODUCT_SALE_TREND_VOS, new org.apache.thrift.meta_data.FieldMetaData("productSaleTrendVos", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ProductSaleTrendVo.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ProductSaleTrendResVo.class, metaDataMap);
  }

  public ProductSaleTrendResVo() {
  }

  public ProductSaleTrendResVo(
    com.froad.thrift.vo.ResultVo resultVo,
    List<ProductSaleTrendVo> productSaleTrendVos)
  {
    this();
    this.resultVo = resultVo;
    this.productSaleTrendVos = productSaleTrendVos;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ProductSaleTrendResVo(ProductSaleTrendResVo other) {
    if (other.isSetResultVo()) {
      this.resultVo = new com.froad.thrift.vo.ResultVo(other.resultVo);
    }
    if (other.isSetProductSaleTrendVos()) {
      List<ProductSaleTrendVo> __this__productSaleTrendVos = new ArrayList<ProductSaleTrendVo>(other.productSaleTrendVos.size());
      for (ProductSaleTrendVo other_element : other.productSaleTrendVos) {
        __this__productSaleTrendVos.add(new ProductSaleTrendVo(other_element));
      }
      this.productSaleTrendVos = __this__productSaleTrendVos;
    }
  }

  public ProductSaleTrendResVo deepCopy() {
    return new ProductSaleTrendResVo(this);
  }

  @Override
  public void clear() {
    this.resultVo = null;
    this.productSaleTrendVos = null;
  }

  /**
   * 返回结果 *
   */
  public com.froad.thrift.vo.ResultVo getResultVo() {
    return this.resultVo;
  }

  /**
   * 返回结果 *
   */
  public ProductSaleTrendResVo setResultVo(com.froad.thrift.vo.ResultVo resultVo) {
    this.resultVo = resultVo;
    return this;
  }

  public void unsetResultVo() {
    this.resultVo = null;
  }

  /** Returns true if field resultVo is set (has been assigned a value) and false otherwise */
  public boolean isSetResultVo() {
    return this.resultVo != null;
  }

  public void setResultVoIsSet(boolean value) {
    if (!value) {
      this.resultVo = null;
    }
  }

  public int getProductSaleTrendVosSize() {
    return (this.productSaleTrendVos == null) ? 0 : this.productSaleTrendVos.size();
  }

  public java.util.Iterator<ProductSaleTrendVo> getProductSaleTrendVosIterator() {
    return (this.productSaleTrendVos == null) ? null : this.productSaleTrendVos.iterator();
  }

  public void addToProductSaleTrendVos(ProductSaleTrendVo elem) {
    if (this.productSaleTrendVos == null) {
      this.productSaleTrendVos = new ArrayList<ProductSaleTrendVo>();
    }
    this.productSaleTrendVos.add(elem);
  }

  /**
   * 销售走势集合 *
   */
  public List<ProductSaleTrendVo> getProductSaleTrendVos() {
    return this.productSaleTrendVos;
  }

  /**
   * 销售走势集合 *
   */
  public ProductSaleTrendResVo setProductSaleTrendVos(List<ProductSaleTrendVo> productSaleTrendVos) {
    this.productSaleTrendVos = productSaleTrendVos;
    return this;
  }

  public void unsetProductSaleTrendVos() {
    this.productSaleTrendVos = null;
  }

  /** Returns true if field productSaleTrendVos is set (has been assigned a value) and false otherwise */
  public boolean isSetProductSaleTrendVos() {
    return this.productSaleTrendVos != null;
  }

  public void setProductSaleTrendVosIsSet(boolean value) {
    if (!value) {
      this.productSaleTrendVos = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case RESULT_VO:
      if (value == null) {
        unsetResultVo();
      } else {
        setResultVo((com.froad.thrift.vo.ResultVo)value);
      }
      break;

    case PRODUCT_SALE_TREND_VOS:
      if (value == null) {
        unsetProductSaleTrendVos();
      } else {
        setProductSaleTrendVos((List<ProductSaleTrendVo>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RESULT_VO:
      return getResultVo();

    case PRODUCT_SALE_TREND_VOS:
      return getProductSaleTrendVos();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case RESULT_VO:
      return isSetResultVo();
    case PRODUCT_SALE_TREND_VOS:
      return isSetProductSaleTrendVos();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ProductSaleTrendResVo)
      return this.equals((ProductSaleTrendResVo)that);
    return false;
  }

  public boolean equals(ProductSaleTrendResVo that) {
    if (that == null)
      return false;

    boolean this_present_resultVo = true && this.isSetResultVo();
    boolean that_present_resultVo = true && that.isSetResultVo();
    if (this_present_resultVo || that_present_resultVo) {
      if (!(this_present_resultVo && that_present_resultVo))
        return false;
      if (!this.resultVo.equals(that.resultVo))
        return false;
    }

    boolean this_present_productSaleTrendVos = true && this.isSetProductSaleTrendVos();
    boolean that_present_productSaleTrendVos = true && that.isSetProductSaleTrendVos();
    if (this_present_productSaleTrendVos || that_present_productSaleTrendVos) {
      if (!(this_present_productSaleTrendVos && that_present_productSaleTrendVos))
        return false;
      if (!this.productSaleTrendVos.equals(that.productSaleTrendVos))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_resultVo = true && (isSetResultVo());
    list.add(present_resultVo);
    if (present_resultVo)
      list.add(resultVo);

    boolean present_productSaleTrendVos = true && (isSetProductSaleTrendVos());
    list.add(present_productSaleTrendVos);
    if (present_productSaleTrendVos)
      list.add(productSaleTrendVos);

    return list.hashCode();
  }

  @Override
  public int compareTo(ProductSaleTrendResVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetResultVo()).compareTo(other.isSetResultVo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetResultVo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.resultVo, other.resultVo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetProductSaleTrendVos()).compareTo(other.isSetProductSaleTrendVos());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProductSaleTrendVos()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.productSaleTrendVos, other.productSaleTrendVos);
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
    StringBuilder sb = new StringBuilder("ProductSaleTrendResVo(");
    boolean first = true;

    sb.append("resultVo:");
    if (this.resultVo == null) {
      sb.append("null");
    } else {
      sb.append(this.resultVo);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("productSaleTrendVos:");
    if (this.productSaleTrendVos == null) {
      sb.append("null");
    } else {
      sb.append(this.productSaleTrendVos);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (resultVo == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'resultVo' was not present! Struct: " + toString());
    }
    if (productSaleTrendVos == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'productSaleTrendVos' was not present! Struct: " + toString());
    }
    // check for sub-struct validity
    if (resultVo != null) {
      resultVo.validate();
    }
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

  private static class ProductSaleTrendResVoStandardSchemeFactory implements SchemeFactory {
    public ProductSaleTrendResVoStandardScheme getScheme() {
      return new ProductSaleTrendResVoStandardScheme();
    }
  }

  private static class ProductSaleTrendResVoStandardScheme extends StandardScheme<ProductSaleTrendResVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ProductSaleTrendResVo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // RESULT_VO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.resultVo = new com.froad.thrift.vo.ResultVo();
              struct.resultVo.read(iprot);
              struct.setResultVoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PRODUCT_SALE_TREND_VOS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list152 = iprot.readListBegin();
                struct.productSaleTrendVos = new ArrayList<ProductSaleTrendVo>(_list152.size);
                ProductSaleTrendVo _elem153;
                for (int _i154 = 0; _i154 < _list152.size; ++_i154)
                {
                  _elem153 = new ProductSaleTrendVo();
                  _elem153.read(iprot);
                  struct.productSaleTrendVos.add(_elem153);
                }
                iprot.readListEnd();
              }
              struct.setProductSaleTrendVosIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ProductSaleTrendResVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.resultVo != null) {
        oprot.writeFieldBegin(RESULT_VO_FIELD_DESC);
        struct.resultVo.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.productSaleTrendVos != null) {
        oprot.writeFieldBegin(PRODUCT_SALE_TREND_VOS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.productSaleTrendVos.size()));
          for (ProductSaleTrendVo _iter155 : struct.productSaleTrendVos)
          {
            _iter155.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ProductSaleTrendResVoTupleSchemeFactory implements SchemeFactory {
    public ProductSaleTrendResVoTupleScheme getScheme() {
      return new ProductSaleTrendResVoTupleScheme();
    }
  }

  private static class ProductSaleTrendResVoTupleScheme extends TupleScheme<ProductSaleTrendResVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ProductSaleTrendResVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      struct.resultVo.write(oprot);
      {
        oprot.writeI32(struct.productSaleTrendVos.size());
        for (ProductSaleTrendVo _iter156 : struct.productSaleTrendVos)
        {
          _iter156.write(oprot);
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ProductSaleTrendResVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.resultVo = new com.froad.thrift.vo.ResultVo();
      struct.resultVo.read(iprot);
      struct.setResultVoIsSet(true);
      {
        org.apache.thrift.protocol.TList _list157 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
        struct.productSaleTrendVos = new ArrayList<ProductSaleTrendVo>(_list157.size);
        ProductSaleTrendVo _elem158;
        for (int _i159 = 0; _i159 < _list157.size; ++_i159)
        {
          _elem158 = new ProductSaleTrendVo();
          _elem158.read(iprot);
          struct.productSaleTrendVos.add(_elem158);
        }
      }
      struct.setProductSaleTrendVosIsSet(true);
    }
  }

}
