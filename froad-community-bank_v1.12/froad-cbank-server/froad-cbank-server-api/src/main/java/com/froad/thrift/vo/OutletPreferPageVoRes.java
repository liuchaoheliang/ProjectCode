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
 * 门店惠付分页的响应
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class OutletPreferPageVoRes implements org.apache.thrift.TBase<OutletPreferPageVoRes, OutletPreferPageVoRes._Fields>, java.io.Serializable, Cloneable, Comparable<OutletPreferPageVoRes> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("OutletPreferPageVoRes");

  private static final org.apache.thrift.protocol.TField PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("page", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField OUTLET_PREFER_VO_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("outletPreferVoList", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new OutletPreferPageVoResStandardSchemeFactory());
    schemes.put(TupleScheme.class, new OutletPreferPageVoResTupleSchemeFactory());
  }

  /**
   * 分页基础信息
   */
  public com.froad.thrift.vo.PageVo page; // required
  /**
   * 门店详情信息列表
   */
  public List<com.froad.thrift.vo.OutletPreferVo> outletPreferVoList; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 分页基础信息
     */
    PAGE((short)1, "page"),
    /**
     * 门店详情信息列表
     */
    OUTLET_PREFER_VO_LIST((short)2, "outletPreferVoList");

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
        case 2: // OUTLET_PREFER_VO_LIST
          return OUTLET_PREFER_VO_LIST;
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
    tmpMap.put(_Fields.OUTLET_PREFER_VO_LIST, new org.apache.thrift.meta_data.FieldMetaData("outletPreferVoList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.OutletPreferVo.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(OutletPreferPageVoRes.class, metaDataMap);
  }

  public OutletPreferPageVoRes() {
  }

  public OutletPreferPageVoRes(
    com.froad.thrift.vo.PageVo page,
    List<com.froad.thrift.vo.OutletPreferVo> outletPreferVoList)
  {
    this();
    this.page = page;
    this.outletPreferVoList = outletPreferVoList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public OutletPreferPageVoRes(OutletPreferPageVoRes other) {
    if (other.isSetPage()) {
      this.page = new com.froad.thrift.vo.PageVo(other.page);
    }
    if (other.isSetOutletPreferVoList()) {
      List<com.froad.thrift.vo.OutletPreferVo> __this__outletPreferVoList = new ArrayList<com.froad.thrift.vo.OutletPreferVo>(other.outletPreferVoList.size());
      for (com.froad.thrift.vo.OutletPreferVo other_element : other.outletPreferVoList) {
        __this__outletPreferVoList.add(new com.froad.thrift.vo.OutletPreferVo(other_element));
      }
      this.outletPreferVoList = __this__outletPreferVoList;
    }
  }

  public OutletPreferPageVoRes deepCopy() {
    return new OutletPreferPageVoRes(this);
  }

  @Override
  public void clear() {
    this.page = null;
    this.outletPreferVoList = null;
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
  public OutletPreferPageVoRes setPage(com.froad.thrift.vo.PageVo page) {
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

  public int getOutletPreferVoListSize() {
    return (this.outletPreferVoList == null) ? 0 : this.outletPreferVoList.size();
  }

  public java.util.Iterator<com.froad.thrift.vo.OutletPreferVo> getOutletPreferVoListIterator() {
    return (this.outletPreferVoList == null) ? null : this.outletPreferVoList.iterator();
  }

  public void addToOutletPreferVoList(com.froad.thrift.vo.OutletPreferVo elem) {
    if (this.outletPreferVoList == null) {
      this.outletPreferVoList = new ArrayList<com.froad.thrift.vo.OutletPreferVo>();
    }
    this.outletPreferVoList.add(elem);
  }

  /**
   * 门店详情信息列表
   */
  public List<com.froad.thrift.vo.OutletPreferVo> getOutletPreferVoList() {
    return this.outletPreferVoList;
  }

  /**
   * 门店详情信息列表
   */
  public OutletPreferPageVoRes setOutletPreferVoList(List<com.froad.thrift.vo.OutletPreferVo> outletPreferVoList) {
    this.outletPreferVoList = outletPreferVoList;
    return this;
  }

  public void unsetOutletPreferVoList() {
    this.outletPreferVoList = null;
  }

  /** Returns true if field outletPreferVoList is set (has been assigned a value) and false otherwise */
  public boolean isSetOutletPreferVoList() {
    return this.outletPreferVoList != null;
  }

  public void setOutletPreferVoListIsSet(boolean value) {
    if (!value) {
      this.outletPreferVoList = null;
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

    case OUTLET_PREFER_VO_LIST:
      if (value == null) {
        unsetOutletPreferVoList();
      } else {
        setOutletPreferVoList((List<com.froad.thrift.vo.OutletPreferVo>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PAGE:
      return getPage();

    case OUTLET_PREFER_VO_LIST:
      return getOutletPreferVoList();

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
    case OUTLET_PREFER_VO_LIST:
      return isSetOutletPreferVoList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof OutletPreferPageVoRes)
      return this.equals((OutletPreferPageVoRes)that);
    return false;
  }

  public boolean equals(OutletPreferPageVoRes that) {
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

    boolean this_present_outletPreferVoList = true && this.isSetOutletPreferVoList();
    boolean that_present_outletPreferVoList = true && that.isSetOutletPreferVoList();
    if (this_present_outletPreferVoList || that_present_outletPreferVoList) {
      if (!(this_present_outletPreferVoList && that_present_outletPreferVoList))
        return false;
      if (!this.outletPreferVoList.equals(that.outletPreferVoList))
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

    boolean present_outletPreferVoList = true && (isSetOutletPreferVoList());
    list.add(present_outletPreferVoList);
    if (present_outletPreferVoList)
      list.add(outletPreferVoList);

    return list.hashCode();
  }

  @Override
  public int compareTo(OutletPreferPageVoRes other) {
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
    lastComparison = Boolean.valueOf(isSetOutletPreferVoList()).compareTo(other.isSetOutletPreferVoList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOutletPreferVoList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.outletPreferVoList, other.outletPreferVoList);
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
    StringBuilder sb = new StringBuilder("OutletPreferPageVoRes(");
    boolean first = true;

    sb.append("page:");
    if (this.page == null) {
      sb.append("null");
    } else {
      sb.append(this.page);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("outletPreferVoList:");
    if (this.outletPreferVoList == null) {
      sb.append("null");
    } else {
      sb.append(this.outletPreferVoList);
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

  private static class OutletPreferPageVoResStandardSchemeFactory implements SchemeFactory {
    public OutletPreferPageVoResStandardScheme getScheme() {
      return new OutletPreferPageVoResStandardScheme();
    }
  }

  private static class OutletPreferPageVoResStandardScheme extends StandardScheme<OutletPreferPageVoRes> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, OutletPreferPageVoRes struct) throws org.apache.thrift.TException {
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
          case 2: // OUTLET_PREFER_VO_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list40 = iprot.readListBegin();
                struct.outletPreferVoList = new ArrayList<com.froad.thrift.vo.OutletPreferVo>(_list40.size);
                com.froad.thrift.vo.OutletPreferVo _elem41;
                for (int _i42 = 0; _i42 < _list40.size; ++_i42)
                {
                  _elem41 = new com.froad.thrift.vo.OutletPreferVo();
                  _elem41.read(iprot);
                  struct.outletPreferVoList.add(_elem41);
                }
                iprot.readListEnd();
              }
              struct.setOutletPreferVoListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, OutletPreferPageVoRes struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.page != null) {
        oprot.writeFieldBegin(PAGE_FIELD_DESC);
        struct.page.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.outletPreferVoList != null) {
        oprot.writeFieldBegin(OUTLET_PREFER_VO_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.outletPreferVoList.size()));
          for (com.froad.thrift.vo.OutletPreferVo _iter43 : struct.outletPreferVoList)
          {
            _iter43.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class OutletPreferPageVoResTupleSchemeFactory implements SchemeFactory {
    public OutletPreferPageVoResTupleScheme getScheme() {
      return new OutletPreferPageVoResTupleScheme();
    }
  }

  private static class OutletPreferPageVoResTupleScheme extends TupleScheme<OutletPreferPageVoRes> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, OutletPreferPageVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPage()) {
        optionals.set(0);
      }
      if (struct.isSetOutletPreferVoList()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetPage()) {
        struct.page.write(oprot);
      }
      if (struct.isSetOutletPreferVoList()) {
        {
          oprot.writeI32(struct.outletPreferVoList.size());
          for (com.froad.thrift.vo.OutletPreferVo _iter44 : struct.outletPreferVoList)
          {
            _iter44.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, OutletPreferPageVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.page = new com.froad.thrift.vo.PageVo();
        struct.page.read(iprot);
        struct.setPageIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list45 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.outletPreferVoList = new ArrayList<com.froad.thrift.vo.OutletPreferVo>(_list45.size);
          com.froad.thrift.vo.OutletPreferVo _elem46;
          for (int _i47 = 0; _i47 < _list45.size; ++_i47)
          {
            _elem46 = new com.froad.thrift.vo.OutletPreferVo();
            _elem46.read(iprot);
            struct.outletPreferVoList.add(_elem46);
          }
        }
        struct.setOutletPreferVoListIsSet(true);
      }
    }
  }

}
