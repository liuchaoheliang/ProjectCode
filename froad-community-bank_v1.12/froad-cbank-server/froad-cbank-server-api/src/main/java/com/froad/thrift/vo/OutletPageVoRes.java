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
 * 门店分页的响应
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class OutletPageVoRes implements org.apache.thrift.TBase<OutletPageVoRes, OutletPageVoRes._Fields>, java.io.Serializable, Cloneable, Comparable<OutletPageVoRes> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("OutletPageVoRes");

  private static final org.apache.thrift.protocol.TField PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("page", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField OUTLET_VO_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("outletVoList", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new OutletPageVoResStandardSchemeFactory());
    schemes.put(TupleScheme.class, new OutletPageVoResTupleSchemeFactory());
  }

  /**
   * 分页基础信息
   */
  public com.froad.thrift.vo.PageVo page; // required
  /**
   * 门店信息列表
   */
  public List<com.froad.thrift.vo.OutletVo> outletVoList; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 分页基础信息
     */
    PAGE((short)1, "page"),
    /**
     * 门店信息列表
     */
    OUTLET_VO_LIST((short)2, "outletVoList");

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
        case 2: // OUTLET_VO_LIST
          return OUTLET_VO_LIST;
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
    tmpMap.put(_Fields.OUTLET_VO_LIST, new org.apache.thrift.meta_data.FieldMetaData("outletVoList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.OutletVo.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(OutletPageVoRes.class, metaDataMap);
  }

  public OutletPageVoRes() {
  }

  public OutletPageVoRes(
    com.froad.thrift.vo.PageVo page,
    List<com.froad.thrift.vo.OutletVo> outletVoList)
  {
    this();
    this.page = page;
    this.outletVoList = outletVoList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public OutletPageVoRes(OutletPageVoRes other) {
    if (other.isSetPage()) {
      this.page = new com.froad.thrift.vo.PageVo(other.page);
    }
    if (other.isSetOutletVoList()) {
      List<com.froad.thrift.vo.OutletVo> __this__outletVoList = new ArrayList<com.froad.thrift.vo.OutletVo>(other.outletVoList.size());
      for (com.froad.thrift.vo.OutletVo other_element : other.outletVoList) {
        __this__outletVoList.add(new com.froad.thrift.vo.OutletVo(other_element));
      }
      this.outletVoList = __this__outletVoList;
    }
  }

  public OutletPageVoRes deepCopy() {
    return new OutletPageVoRes(this);
  }

  @Override
  public void clear() {
    this.page = null;
    this.outletVoList = null;
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
  public OutletPageVoRes setPage(com.froad.thrift.vo.PageVo page) {
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

  public int getOutletVoListSize() {
    return (this.outletVoList == null) ? 0 : this.outletVoList.size();
  }

  public java.util.Iterator<com.froad.thrift.vo.OutletVo> getOutletVoListIterator() {
    return (this.outletVoList == null) ? null : this.outletVoList.iterator();
  }

  public void addToOutletVoList(com.froad.thrift.vo.OutletVo elem) {
    if (this.outletVoList == null) {
      this.outletVoList = new ArrayList<com.froad.thrift.vo.OutletVo>();
    }
    this.outletVoList.add(elem);
  }

  /**
   * 门店信息列表
   */
  public List<com.froad.thrift.vo.OutletVo> getOutletVoList() {
    return this.outletVoList;
  }

  /**
   * 门店信息列表
   */
  public OutletPageVoRes setOutletVoList(List<com.froad.thrift.vo.OutletVo> outletVoList) {
    this.outletVoList = outletVoList;
    return this;
  }

  public void unsetOutletVoList() {
    this.outletVoList = null;
  }

  /** Returns true if field outletVoList is set (has been assigned a value) and false otherwise */
  public boolean isSetOutletVoList() {
    return this.outletVoList != null;
  }

  public void setOutletVoListIsSet(boolean value) {
    if (!value) {
      this.outletVoList = null;
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

    case OUTLET_VO_LIST:
      if (value == null) {
        unsetOutletVoList();
      } else {
        setOutletVoList((List<com.froad.thrift.vo.OutletVo>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PAGE:
      return getPage();

    case OUTLET_VO_LIST:
      return getOutletVoList();

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
    case OUTLET_VO_LIST:
      return isSetOutletVoList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof OutletPageVoRes)
      return this.equals((OutletPageVoRes)that);
    return false;
  }

  public boolean equals(OutletPageVoRes that) {
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

    boolean this_present_outletVoList = true && this.isSetOutletVoList();
    boolean that_present_outletVoList = true && that.isSetOutletVoList();
    if (this_present_outletVoList || that_present_outletVoList) {
      if (!(this_present_outletVoList && that_present_outletVoList))
        return false;
      if (!this.outletVoList.equals(that.outletVoList))
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

    boolean present_outletVoList = true && (isSetOutletVoList());
    list.add(present_outletVoList);
    if (present_outletVoList)
      list.add(outletVoList);

    return list.hashCode();
  }

  @Override
  public int compareTo(OutletPageVoRes other) {
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
    lastComparison = Boolean.valueOf(isSetOutletVoList()).compareTo(other.isSetOutletVoList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOutletVoList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.outletVoList, other.outletVoList);
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
    StringBuilder sb = new StringBuilder("OutletPageVoRes(");
    boolean first = true;

    sb.append("page:");
    if (this.page == null) {
      sb.append("null");
    } else {
      sb.append(this.page);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("outletVoList:");
    if (this.outletVoList == null) {
      sb.append("null");
    } else {
      sb.append(this.outletVoList);
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

  private static class OutletPageVoResStandardSchemeFactory implements SchemeFactory {
    public OutletPageVoResStandardScheme getScheme() {
      return new OutletPageVoResStandardScheme();
    }
  }

  private static class OutletPageVoResStandardScheme extends StandardScheme<OutletPageVoRes> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, OutletPageVoRes struct) throws org.apache.thrift.TException {
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
          case 2: // OUTLET_VO_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.outletVoList = new ArrayList<com.froad.thrift.vo.OutletVo>(_list0.size);
                com.froad.thrift.vo.OutletVo _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = new com.froad.thrift.vo.OutletVo();
                  _elem1.read(iprot);
                  struct.outletVoList.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setOutletVoListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, OutletPageVoRes struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.page != null) {
        oprot.writeFieldBegin(PAGE_FIELD_DESC);
        struct.page.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.outletVoList != null) {
        oprot.writeFieldBegin(OUTLET_VO_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.outletVoList.size()));
          for (com.froad.thrift.vo.OutletVo _iter3 : struct.outletVoList)
          {
            _iter3.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class OutletPageVoResTupleSchemeFactory implements SchemeFactory {
    public OutletPageVoResTupleScheme getScheme() {
      return new OutletPageVoResTupleScheme();
    }
  }

  private static class OutletPageVoResTupleScheme extends TupleScheme<OutletPageVoRes> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, OutletPageVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPage()) {
        optionals.set(0);
      }
      if (struct.isSetOutletVoList()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetPage()) {
        struct.page.write(oprot);
      }
      if (struct.isSetOutletVoList()) {
        {
          oprot.writeI32(struct.outletVoList.size());
          for (com.froad.thrift.vo.OutletVo _iter4 : struct.outletVoList)
          {
            _iter4.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, OutletPageVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.page = new com.froad.thrift.vo.PageVo();
        struct.page.read(iprot);
        struct.setPageIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.outletVoList = new ArrayList<com.froad.thrift.vo.OutletVo>(_list5.size);
          com.froad.thrift.vo.OutletVo _elem6;
          for (int _i7 = 0; _i7 < _list5.size; ++_i7)
          {
            _elem6 = new com.froad.thrift.vo.OutletVo();
            _elem6.read(iprot);
            struct.outletVoList.add(_elem6);
          }
        }
        struct.setOutletVoListIsSet(true);
      }
    }
  }

}

