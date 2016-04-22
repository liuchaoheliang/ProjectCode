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
 * 商户详情分页的响应
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class MerchantDetailPageVoRes implements org.apache.thrift.TBase<MerchantDetailPageVoRes, MerchantDetailPageVoRes._Fields>, java.io.Serializable, Cloneable, Comparable<MerchantDetailPageVoRes> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("MerchantDetailPageVoRes");

  private static final org.apache.thrift.protocol.TField PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("page", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField MERCHANT_DETAIL_VO_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("merchantDetailVoList", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new MerchantDetailPageVoResStandardSchemeFactory());
    schemes.put(TupleScheme.class, new MerchantDetailPageVoResTupleSchemeFactory());
  }

  /**
   * 分页基础信息
   */
  public com.froad.thrift.vo.PageVo page; // required
  /**
   * 商户详情信息列表
   */
  public List<com.froad.thrift.vo.MerchantDetailVo> merchantDetailVoList; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 分页基础信息
     */
    PAGE((short)1, "page"),
    /**
     * 商户详情信息列表
     */
    MERCHANT_DETAIL_VO_LIST((short)2, "merchantDetailVoList");

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
        case 2: // MERCHANT_DETAIL_VO_LIST
          return MERCHANT_DETAIL_VO_LIST;
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
    tmpMap.put(_Fields.MERCHANT_DETAIL_VO_LIST, new org.apache.thrift.meta_data.FieldMetaData("merchantDetailVoList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.MerchantDetailVo.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(MerchantDetailPageVoRes.class, metaDataMap);
  }

  public MerchantDetailPageVoRes() {
  }

  public MerchantDetailPageVoRes(
    com.froad.thrift.vo.PageVo page,
    List<com.froad.thrift.vo.MerchantDetailVo> merchantDetailVoList)
  {
    this();
    this.page = page;
    this.merchantDetailVoList = merchantDetailVoList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public MerchantDetailPageVoRes(MerchantDetailPageVoRes other) {
    if (other.isSetPage()) {
      this.page = new com.froad.thrift.vo.PageVo(other.page);
    }
    if (other.isSetMerchantDetailVoList()) {
      List<com.froad.thrift.vo.MerchantDetailVo> __this__merchantDetailVoList = new ArrayList<com.froad.thrift.vo.MerchantDetailVo>(other.merchantDetailVoList.size());
      for (com.froad.thrift.vo.MerchantDetailVo other_element : other.merchantDetailVoList) {
        __this__merchantDetailVoList.add(new com.froad.thrift.vo.MerchantDetailVo(other_element));
      }
      this.merchantDetailVoList = __this__merchantDetailVoList;
    }
  }

  public MerchantDetailPageVoRes deepCopy() {
    return new MerchantDetailPageVoRes(this);
  }

  @Override
  public void clear() {
    this.page = null;
    this.merchantDetailVoList = null;
  }

  /**
   * 分页基础信息
   */
  public com.froad.thrift.vo.PageVo getPage() {
    return this.page;
  }

  /**
   * 分页基础信息
   */
  public MerchantDetailPageVoRes setPage(com.froad.thrift.vo.PageVo page) {
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

  public int getMerchantDetailVoListSize() {
    return (this.merchantDetailVoList == null) ? 0 : this.merchantDetailVoList.size();
  }

  public java.util.Iterator<com.froad.thrift.vo.MerchantDetailVo> getMerchantDetailVoListIterator() {
    return (this.merchantDetailVoList == null) ? null : this.merchantDetailVoList.iterator();
  }

  public void addToMerchantDetailVoList(com.froad.thrift.vo.MerchantDetailVo elem) {
    if (this.merchantDetailVoList == null) {
      this.merchantDetailVoList = new ArrayList<com.froad.thrift.vo.MerchantDetailVo>();
    }
    this.merchantDetailVoList.add(elem);
  }

  /**
   * 商户详情信息列表
   */
  public List<com.froad.thrift.vo.MerchantDetailVo> getMerchantDetailVoList() {
    return this.merchantDetailVoList;
  }

  /**
   * 商户详情信息列表
   */
  public MerchantDetailPageVoRes setMerchantDetailVoList(List<com.froad.thrift.vo.MerchantDetailVo> merchantDetailVoList) {
    this.merchantDetailVoList = merchantDetailVoList;
    return this;
  }

  public void unsetMerchantDetailVoList() {
    this.merchantDetailVoList = null;
  }

  /** Returns true if field merchantDetailVoList is set (has been assigned a value) and false otherwise */
  public boolean isSetMerchantDetailVoList() {
    return this.merchantDetailVoList != null;
  }

  public void setMerchantDetailVoListIsSet(boolean value) {
    if (!value) {
      this.merchantDetailVoList = null;
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

    case MERCHANT_DETAIL_VO_LIST:
      if (value == null) {
        unsetMerchantDetailVoList();
      } else {
        setMerchantDetailVoList((List<com.froad.thrift.vo.MerchantDetailVo>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PAGE:
      return getPage();

    case MERCHANT_DETAIL_VO_LIST:
      return getMerchantDetailVoList();

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
    case MERCHANT_DETAIL_VO_LIST:
      return isSetMerchantDetailVoList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof MerchantDetailPageVoRes)
      return this.equals((MerchantDetailPageVoRes)that);
    return false;
  }

  public boolean equals(MerchantDetailPageVoRes that) {
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

    boolean this_present_merchantDetailVoList = true && this.isSetMerchantDetailVoList();
    boolean that_present_merchantDetailVoList = true && that.isSetMerchantDetailVoList();
    if (this_present_merchantDetailVoList || that_present_merchantDetailVoList) {
      if (!(this_present_merchantDetailVoList && that_present_merchantDetailVoList))
        return false;
      if (!this.merchantDetailVoList.equals(that.merchantDetailVoList))
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

    boolean present_merchantDetailVoList = true && (isSetMerchantDetailVoList());
    list.add(present_merchantDetailVoList);
    if (present_merchantDetailVoList)
      list.add(merchantDetailVoList);

    return list.hashCode();
  }

  @Override
  public int compareTo(MerchantDetailPageVoRes other) {
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
    lastComparison = Boolean.valueOf(isSetMerchantDetailVoList()).compareTo(other.isSetMerchantDetailVoList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMerchantDetailVoList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.merchantDetailVoList, other.merchantDetailVoList);
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
    StringBuilder sb = new StringBuilder("MerchantDetailPageVoRes(");
    boolean first = true;

    sb.append("page:");
    if (this.page == null) {
      sb.append("null");
    } else {
      sb.append(this.page);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("merchantDetailVoList:");
    if (this.merchantDetailVoList == null) {
      sb.append("null");
    } else {
      sb.append(this.merchantDetailVoList);
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

  private static class MerchantDetailPageVoResStandardSchemeFactory implements SchemeFactory {
    public MerchantDetailPageVoResStandardScheme getScheme() {
      return new MerchantDetailPageVoResStandardScheme();
    }
  }

  private static class MerchantDetailPageVoResStandardScheme extends StandardScheme<MerchantDetailPageVoRes> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, MerchantDetailPageVoRes struct) throws org.apache.thrift.TException {
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
          case 2: // MERCHANT_DETAIL_VO_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list8 = iprot.readListBegin();
                struct.merchantDetailVoList = new ArrayList<com.froad.thrift.vo.MerchantDetailVo>(_list8.size);
                com.froad.thrift.vo.MerchantDetailVo _elem9;
                for (int _i10 = 0; _i10 < _list8.size; ++_i10)
                {
                  _elem9 = new com.froad.thrift.vo.MerchantDetailVo();
                  _elem9.read(iprot);
                  struct.merchantDetailVoList.add(_elem9);
                }
                iprot.readListEnd();
              }
              struct.setMerchantDetailVoListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, MerchantDetailPageVoRes struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.page != null) {
        oprot.writeFieldBegin(PAGE_FIELD_DESC);
        struct.page.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.merchantDetailVoList != null) {
        oprot.writeFieldBegin(MERCHANT_DETAIL_VO_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.merchantDetailVoList.size()));
          for (com.froad.thrift.vo.MerchantDetailVo _iter11 : struct.merchantDetailVoList)
          {
            _iter11.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class MerchantDetailPageVoResTupleSchemeFactory implements SchemeFactory {
    public MerchantDetailPageVoResTupleScheme getScheme() {
      return new MerchantDetailPageVoResTupleScheme();
    }
  }

  private static class MerchantDetailPageVoResTupleScheme extends TupleScheme<MerchantDetailPageVoRes> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, MerchantDetailPageVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPage()) {
        optionals.set(0);
      }
      if (struct.isSetMerchantDetailVoList()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetPage()) {
        struct.page.write(oprot);
      }
      if (struct.isSetMerchantDetailVoList()) {
        {
          oprot.writeI32(struct.merchantDetailVoList.size());
          for (com.froad.thrift.vo.MerchantDetailVo _iter12 : struct.merchantDetailVoList)
          {
            _iter12.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, MerchantDetailPageVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.page = new com.froad.thrift.vo.PageVo();
        struct.page.read(iprot);
        struct.setPageIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list13 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.merchantDetailVoList = new ArrayList<com.froad.thrift.vo.MerchantDetailVo>(_list13.size);
          com.froad.thrift.vo.MerchantDetailVo _elem14;
          for (int _i15 = 0; _i15 < _list13.size; ++_i15)
          {
            _elem14 = new com.froad.thrift.vo.MerchantDetailVo();
            _elem14.read(iprot);
            struct.merchantDetailVoList.add(_elem14);
          }
        }
        struct.setMerchantDetailVoListIsSet(true);
      }
    }
  }

}

