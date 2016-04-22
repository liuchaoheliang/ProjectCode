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
 * 活动分页
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class ActivitiesPageVoRes implements org.apache.thrift.TBase<ActivitiesPageVoRes, ActivitiesPageVoRes._Fields>, java.io.Serializable, Cloneable, Comparable<ActivitiesPageVoRes> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ActivitiesPageVoRes");

  private static final org.apache.thrift.protocol.TField PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("page", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField ACTIVITIES_VO_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("activitiesVoList", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ActivitiesPageVoResStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ActivitiesPageVoResTupleSchemeFactory());
  }

  public com.froad.thrift.vo.PageVo page; // required
  public List<ActivitiesVo> activitiesVoList; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PAGE((short)1, "page"),
    ACTIVITIES_VO_LIST((short)2, "activitiesVoList");

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
        case 2: // ACTIVITIES_VO_LIST
          return ACTIVITIES_VO_LIST;
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
    tmpMap.put(_Fields.ACTIVITIES_VO_LIST, new org.apache.thrift.meta_data.FieldMetaData("activitiesVoList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ActivitiesVo.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ActivitiesPageVoRes.class, metaDataMap);
  }

  public ActivitiesPageVoRes() {
  }

  public ActivitiesPageVoRes(
    com.froad.thrift.vo.PageVo page,
    List<ActivitiesVo> activitiesVoList)
  {
    this();
    this.page = page;
    this.activitiesVoList = activitiesVoList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ActivitiesPageVoRes(ActivitiesPageVoRes other) {
    if (other.isSetPage()) {
      this.page = new com.froad.thrift.vo.PageVo(other.page);
    }
    if (other.isSetActivitiesVoList()) {
      List<ActivitiesVo> __this__activitiesVoList = new ArrayList<ActivitiesVo>(other.activitiesVoList.size());
      for (ActivitiesVo other_element : other.activitiesVoList) {
        __this__activitiesVoList.add(new ActivitiesVo(other_element));
      }
      this.activitiesVoList = __this__activitiesVoList;
    }
  }

  public ActivitiesPageVoRes deepCopy() {
    return new ActivitiesPageVoRes(this);
  }

  @Override
  public void clear() {
    this.page = null;
    this.activitiesVoList = null;
  }

  public com.froad.thrift.vo.PageVo getPage() {
    return this.page;
  }

  public ActivitiesPageVoRes setPage(com.froad.thrift.vo.PageVo page) {
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

  public int getActivitiesVoListSize() {
    return (this.activitiesVoList == null) ? 0 : this.activitiesVoList.size();
  }

  public java.util.Iterator<ActivitiesVo> getActivitiesVoListIterator() {
    return (this.activitiesVoList == null) ? null : this.activitiesVoList.iterator();
  }

  public void addToActivitiesVoList(ActivitiesVo elem) {
    if (this.activitiesVoList == null) {
      this.activitiesVoList = new ArrayList<ActivitiesVo>();
    }
    this.activitiesVoList.add(elem);
  }

  public List<ActivitiesVo> getActivitiesVoList() {
    return this.activitiesVoList;
  }

  public ActivitiesPageVoRes setActivitiesVoList(List<ActivitiesVo> activitiesVoList) {
    this.activitiesVoList = activitiesVoList;
    return this;
  }

  public void unsetActivitiesVoList() {
    this.activitiesVoList = null;
  }

  /** Returns true if field activitiesVoList is set (has been assigned a value) and false otherwise */
  public boolean isSetActivitiesVoList() {
    return this.activitiesVoList != null;
  }

  public void setActivitiesVoListIsSet(boolean value) {
    if (!value) {
      this.activitiesVoList = null;
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

    case ACTIVITIES_VO_LIST:
      if (value == null) {
        unsetActivitiesVoList();
      } else {
        setActivitiesVoList((List<ActivitiesVo>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PAGE:
      return getPage();

    case ACTIVITIES_VO_LIST:
      return getActivitiesVoList();

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
    case ACTIVITIES_VO_LIST:
      return isSetActivitiesVoList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ActivitiesPageVoRes)
      return this.equals((ActivitiesPageVoRes)that);
    return false;
  }

  public boolean equals(ActivitiesPageVoRes that) {
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

    boolean this_present_activitiesVoList = true && this.isSetActivitiesVoList();
    boolean that_present_activitiesVoList = true && that.isSetActivitiesVoList();
    if (this_present_activitiesVoList || that_present_activitiesVoList) {
      if (!(this_present_activitiesVoList && that_present_activitiesVoList))
        return false;
      if (!this.activitiesVoList.equals(that.activitiesVoList))
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

    boolean present_activitiesVoList = true && (isSetActivitiesVoList());
    list.add(present_activitiesVoList);
    if (present_activitiesVoList)
      list.add(activitiesVoList);

    return list.hashCode();
  }

  @Override
  public int compareTo(ActivitiesPageVoRes other) {
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
    lastComparison = Boolean.valueOf(isSetActivitiesVoList()).compareTo(other.isSetActivitiesVoList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetActivitiesVoList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.activitiesVoList, other.activitiesVoList);
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
    StringBuilder sb = new StringBuilder("ActivitiesPageVoRes(");
    boolean first = true;

    sb.append("page:");
    if (this.page == null) {
      sb.append("null");
    } else {
      sb.append(this.page);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("activitiesVoList:");
    if (this.activitiesVoList == null) {
      sb.append("null");
    } else {
      sb.append(this.activitiesVoList);
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

  private static class ActivitiesPageVoResStandardSchemeFactory implements SchemeFactory {
    public ActivitiesPageVoResStandardScheme getScheme() {
      return new ActivitiesPageVoResStandardScheme();
    }
  }

  private static class ActivitiesPageVoResStandardScheme extends StandardScheme<ActivitiesPageVoRes> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ActivitiesPageVoRes struct) throws org.apache.thrift.TException {
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
          case 2: // ACTIVITIES_VO_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list8 = iprot.readListBegin();
                struct.activitiesVoList = new ArrayList<ActivitiesVo>(_list8.size);
                ActivitiesVo _elem9;
                for (int _i10 = 0; _i10 < _list8.size; ++_i10)
                {
                  _elem9 = new ActivitiesVo();
                  _elem9.read(iprot);
                  struct.activitiesVoList.add(_elem9);
                }
                iprot.readListEnd();
              }
              struct.setActivitiesVoListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ActivitiesPageVoRes struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.page != null) {
        oprot.writeFieldBegin(PAGE_FIELD_DESC);
        struct.page.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.activitiesVoList != null) {
        oprot.writeFieldBegin(ACTIVITIES_VO_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.activitiesVoList.size()));
          for (ActivitiesVo _iter11 : struct.activitiesVoList)
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

  private static class ActivitiesPageVoResTupleSchemeFactory implements SchemeFactory {
    public ActivitiesPageVoResTupleScheme getScheme() {
      return new ActivitiesPageVoResTupleScheme();
    }
  }

  private static class ActivitiesPageVoResTupleScheme extends TupleScheme<ActivitiesPageVoRes> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ActivitiesPageVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPage()) {
        optionals.set(0);
      }
      if (struct.isSetActivitiesVoList()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetPage()) {
        struct.page.write(oprot);
      }
      if (struct.isSetActivitiesVoList()) {
        {
          oprot.writeI32(struct.activitiesVoList.size());
          for (ActivitiesVo _iter12 : struct.activitiesVoList)
          {
            _iter12.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ActivitiesPageVoRes struct) throws org.apache.thrift.TException {
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
          struct.activitiesVoList = new ArrayList<ActivitiesVo>(_list13.size);
          ActivitiesVo _elem14;
          for (int _i15 = 0; _i15 < _list13.size; ++_i15)
          {
            _elem14 = new ActivitiesVo();
            _elem14.read(iprot);
            struct.activitiesVoList.add(_elem14);
          }
        }
        struct.setActivitiesVoListIsSet(true);
      }
    }
  }

}
