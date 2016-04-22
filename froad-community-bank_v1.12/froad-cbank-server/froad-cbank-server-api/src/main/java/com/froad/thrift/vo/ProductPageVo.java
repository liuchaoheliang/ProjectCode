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
 * 管理平台商品管理查询商品列表(商户管理平台和银行管理平台)
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class ProductPageVo implements org.apache.thrift.TBase<ProductPageVo, ProductPageVo._Fields>, java.io.Serializable, Cloneable, Comparable<ProductPageVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ProductPageVo");

  private static final org.apache.thrift.protocol.TField PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("page", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField PRODUCT_BRIEF_VO_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("productBriefVoList", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ProductPageVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ProductPageVoTupleSchemeFactory());
  }

  public com.froad.thrift.vo.PageVo page; // required
  public List<ProductBriefVo> productBriefVoList; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PAGE((short)1, "page"),
    PRODUCT_BRIEF_VO_LIST((short)2, "productBriefVoList");

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
        case 1: // PAGE
          return PAGE;
        case 2: // PRODUCT_BRIEF_VO_LIST
          return PRODUCT_BRIEF_VO_LIST;
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
    tmpMap.put(_Fields.PAGE, new org.apache.thrift.meta_data.FieldMetaData("page", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.PageVo.class)));
    tmpMap.put(_Fields.PRODUCT_BRIEF_VO_LIST, new org.apache.thrift.meta_data.FieldMetaData("productBriefVoList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT            , "ProductBriefVo"))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ProductPageVo.class, metaDataMap);
  }

  public ProductPageVo() {
  }

  public ProductPageVo(
    com.froad.thrift.vo.PageVo page,
    List<ProductBriefVo> productBriefVoList)
  {
    this();
    this.page = page;
    this.productBriefVoList = productBriefVoList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ProductPageVo(ProductPageVo other) {
    if (other.isSetPage()) {
      this.page = new com.froad.thrift.vo.PageVo(other.page);
    }
    if (other.isSetProductBriefVoList()) {
      List<ProductBriefVo> __this__productBriefVoList = new ArrayList<ProductBriefVo>(other.productBriefVoList.size());
      for (ProductBriefVo other_element : other.productBriefVoList) {
        __this__productBriefVoList.add(other_element);
      }
      this.productBriefVoList = __this__productBriefVoList;
    }
  }

  public ProductPageVo deepCopy() {
    return new ProductPageVo(this);
  }

  @Override
  public void clear() {
    this.page = null;
    this.productBriefVoList = null;
  }

  public com.froad.thrift.vo.PageVo getPage() {
    return this.page;
  }

  public ProductPageVo setPage(com.froad.thrift.vo.PageVo page) {
    this.page = page;
    return this;
  }

  public void unsetPage() {
    this.page = null;
  }

  /** Returns true if field page is set (has been assigned a value) and false otherwise */
  public boolean isSetPage() {
    return this.page != null;
  }

  public void setPageIsSet(boolean value) {
    if (!value) {
      this.page = null;
    }
  }

  public int getProductBriefVoListSize() {
    return (this.productBriefVoList == null) ? 0 : this.productBriefVoList.size();
  }

  public java.util.Iterator<ProductBriefVo> getProductBriefVoListIterator() {
    return (this.productBriefVoList == null) ? null : this.productBriefVoList.iterator();
  }

  public void addToProductBriefVoList(ProductBriefVo elem) {
    if (this.productBriefVoList == null) {
      this.productBriefVoList = new ArrayList<ProductBriefVo>();
    }
    this.productBriefVoList.add(elem);
  }

  public List<ProductBriefVo> getProductBriefVoList() {
    return this.productBriefVoList;
  }

  public ProductPageVo setProductBriefVoList(List<ProductBriefVo> productBriefVoList) {
    this.productBriefVoList = productBriefVoList;
    return this;
  }

  public void unsetProductBriefVoList() {
    this.productBriefVoList = null;
  }

  /** Returns true if field productBriefVoList is set (has been assigned a value) and false otherwise */
  public boolean isSetProductBriefVoList() {
    return this.productBriefVoList != null;
  }

  public void setProductBriefVoListIsSet(boolean value) {
    if (!value) {
      this.productBriefVoList = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PAGE:
      if (value == null) {
        unsetPage();
      } else {
        setPage((com.froad.thrift.vo.PageVo)value);
      }
      break;

    case PRODUCT_BRIEF_VO_LIST:
      if (value == null) {
        unsetProductBriefVoList();
      } else {
        setProductBriefVoList((List<ProductBriefVo>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PAGE:
      return getPage();

    case PRODUCT_BRIEF_VO_LIST:
      return getProductBriefVoList();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case PAGE:
      return isSetPage();
    case PRODUCT_BRIEF_VO_LIST:
      return isSetProductBriefVoList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ProductPageVo)
      return this.equals((ProductPageVo)that);
    return false;
  }

  public boolean equals(ProductPageVo that) {
    if (that == null)
      return false;

    boolean this_present_page = true && this.isSetPage();
    boolean that_present_page = true && that.isSetPage();
    if (this_present_page || that_present_page) {
      if (!(this_present_page && that_present_page))
        return false;
      if (!this.page.equals(that.page))
        return false;
    }

    boolean this_present_productBriefVoList = true && this.isSetProductBriefVoList();
    boolean that_present_productBriefVoList = true && that.isSetProductBriefVoList();
    if (this_present_productBriefVoList || that_present_productBriefVoList) {
      if (!(this_present_productBriefVoList && that_present_productBriefVoList))
        return false;
      if (!this.productBriefVoList.equals(that.productBriefVoList))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_page = true && (isSetPage());
    list.add(present_page);
    if (present_page)
      list.add(page);

    boolean present_productBriefVoList = true && (isSetProductBriefVoList());
    list.add(present_productBriefVoList);
    if (present_productBriefVoList)
      list.add(productBriefVoList);

    return list.hashCode();
  }

  @Override
  public int compareTo(ProductPageVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetPage()).compareTo(other.isSetPage());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPage()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.page, other.page);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetProductBriefVoList()).compareTo(other.isSetProductBriefVoList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProductBriefVoList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.productBriefVoList, other.productBriefVoList);
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
    StringBuilder sb = new StringBuilder("ProductPageVo(");
    boolean first = true;

    sb.append("page:");
    if (this.page == null) {
      sb.append("null");
    } else {
      sb.append(this.page);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("productBriefVoList:");
    if (this.productBriefVoList == null) {
      sb.append("null");
    } else {
      sb.append(this.productBriefVoList);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (page != null) {
      page.validate();
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

  private static class ProductPageVoStandardSchemeFactory implements SchemeFactory {
    public ProductPageVoStandardScheme getScheme() {
      return new ProductPageVoStandardScheme();
    }
  }

  private static class ProductPageVoStandardScheme extends StandardScheme<ProductPageVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ProductPageVo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PAGE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.page = new com.froad.thrift.vo.PageVo();
              struct.page.read(iprot);
              struct.setPageIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PRODUCT_BRIEF_VO_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list248 = iprot.readListBegin();
                struct.productBriefVoList = new ArrayList<ProductBriefVo>(_list248.size);
                ProductBriefVo _elem249;
                for (int _i250 = 0; _i250 < _list248.size; ++_i250)
                {
                  _elem249 = new ProductBriefVo();
                  _elem249.read(iprot);
                  struct.productBriefVoList.add(_elem249);
                }
                iprot.readListEnd();
              }
              struct.setProductBriefVoListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ProductPageVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.page != null) {
        oprot.writeFieldBegin(PAGE_FIELD_DESC);
        struct.page.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.productBriefVoList != null) {
        oprot.writeFieldBegin(PRODUCT_BRIEF_VO_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.productBriefVoList.size()));
          for (ProductBriefVo _iter251 : struct.productBriefVoList)
          {
            _iter251.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ProductPageVoTupleSchemeFactory implements SchemeFactory {
    public ProductPageVoTupleScheme getScheme() {
      return new ProductPageVoTupleScheme();
    }
  }

  private static class ProductPageVoTupleScheme extends TupleScheme<ProductPageVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ProductPageVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPage()) {
        optionals.set(0);
      }
      if (struct.isSetProductBriefVoList()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetPage()) {
        struct.page.write(oprot);
      }
      if (struct.isSetProductBriefVoList()) {
        {
          oprot.writeI32(struct.productBriefVoList.size());
          for (ProductBriefVo _iter252 : struct.productBriefVoList)
          {
            _iter252.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ProductPageVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.page = new com.froad.thrift.vo.PageVo();
        struct.page.read(iprot);
        struct.setPageIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list253 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.productBriefVoList = new ArrayList<ProductBriefVo>(_list253.size);
          ProductBriefVo _elem254;
          for (int _i255 = 0; _i255 < _list253.size; ++_i255)
          {
            _elem254 = new ProductBriefVo();
            _elem254.read(iprot);
            struct.productBriefVoList.add(_elem254);
          }
        }
        struct.setProductBriefVoListIsSet(true);
      }
    }
  }

}
