/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.froad.thrift.vo.member;

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
public class UserEnginePageVoRes implements org.apache.thrift.TBase<UserEnginePageVoRes, UserEnginePageVoRes._Fields>, java.io.Serializable, Cloneable, Comparable<UserEnginePageVoRes> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("UserEnginePageVoRes");

  private static final org.apache.thrift.protocol.TField PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("page", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField POINTS_RECORD_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("pointsRecordList", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new UserEnginePageVoResStandardSchemeFactory());
    schemes.put(TupleScheme.class, new UserEnginePageVoResTupleSchemeFactory());
  }

  public com.froad.thrift.vo.PageVo page; // required
  public List<UserEnginePointsRecordVo> pointsRecordList; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PAGE((short)1, "page"),
    POINTS_RECORD_LIST((short)2, "pointsRecordList");

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
        case 2: // POINTS_RECORD_LIST
          return POINTS_RECORD_LIST;
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
    tmpMap.put(_Fields.POINTS_RECORD_LIST, new org.apache.thrift.meta_data.FieldMetaData("pointsRecordList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT            , "UserEnginePointsRecordVo"))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(UserEnginePageVoRes.class, metaDataMap);
  }

  public UserEnginePageVoRes() {
  }

  public UserEnginePageVoRes(
    com.froad.thrift.vo.PageVo page,
    List<UserEnginePointsRecordVo> pointsRecordList)
  {
    this();
    this.page = page;
    this.pointsRecordList = pointsRecordList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public UserEnginePageVoRes(UserEnginePageVoRes other) {
    if (other.isSetPage()) {
      this.page = new com.froad.thrift.vo.PageVo(other.page);
    }
    if (other.isSetPointsRecordList()) {
      List<UserEnginePointsRecordVo> __this__pointsRecordList = new ArrayList<UserEnginePointsRecordVo>(other.pointsRecordList.size());
      for (UserEnginePointsRecordVo other_element : other.pointsRecordList) {
        __this__pointsRecordList.add(other_element);
      }
      this.pointsRecordList = __this__pointsRecordList;
    }
  }

  public UserEnginePageVoRes deepCopy() {
    return new UserEnginePageVoRes(this);
  }

  @Override
  public void clear() {
    this.page = null;
    this.pointsRecordList = null;
  }

  public com.froad.thrift.vo.PageVo getPage() {
    return this.page;
  }

  public UserEnginePageVoRes setPage(com.froad.thrift.vo.PageVo page) {
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

  public int getPointsRecordListSize() {
    return (this.pointsRecordList == null) ? 0 : this.pointsRecordList.size();
  }

  public java.util.Iterator<UserEnginePointsRecordVo> getPointsRecordListIterator() {
    return (this.pointsRecordList == null) ? null : this.pointsRecordList.iterator();
  }

  public void addToPointsRecordList(UserEnginePointsRecordVo elem) {
    if (this.pointsRecordList == null) {
      this.pointsRecordList = new ArrayList<UserEnginePointsRecordVo>();
    }
    this.pointsRecordList.add(elem);
  }

  public List<UserEnginePointsRecordVo> getPointsRecordList() {
    return this.pointsRecordList;
  }

  public UserEnginePageVoRes setPointsRecordList(List<UserEnginePointsRecordVo> pointsRecordList) {
    this.pointsRecordList = pointsRecordList;
    return this;
  }

  public void unsetPointsRecordList() {
    this.pointsRecordList = null;
  }

  /** Returns true if field pointsRecordList is set (has been assigned a value) and false otherwise */
  public boolean isSetPointsRecordList() {
    return this.pointsRecordList != null;
  }

  public void setPointsRecordListIsSet(boolean value) {
    if (!value) {
      this.pointsRecordList = null;
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

    case POINTS_RECORD_LIST:
      if (value == null) {
        unsetPointsRecordList();
      } else {
        setPointsRecordList((List<UserEnginePointsRecordVo>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PAGE:
      return getPage();

    case POINTS_RECORD_LIST:
      return getPointsRecordList();

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
    case POINTS_RECORD_LIST:
      return isSetPointsRecordList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof UserEnginePageVoRes)
      return this.equals((UserEnginePageVoRes)that);
    return false;
  }

  public boolean equals(UserEnginePageVoRes that) {
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

    boolean this_present_pointsRecordList = true && this.isSetPointsRecordList();
    boolean that_present_pointsRecordList = true && that.isSetPointsRecordList();
    if (this_present_pointsRecordList || that_present_pointsRecordList) {
      if (!(this_present_pointsRecordList && that_present_pointsRecordList))
        return false;
      if (!this.pointsRecordList.equals(that.pointsRecordList))
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

    boolean present_pointsRecordList = true && (isSetPointsRecordList());
    list.add(present_pointsRecordList);
    if (present_pointsRecordList)
      list.add(pointsRecordList);

    return list.hashCode();
  }

  @Override
  public int compareTo(UserEnginePageVoRes other) {
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
    lastComparison = Boolean.valueOf(isSetPointsRecordList()).compareTo(other.isSetPointsRecordList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPointsRecordList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.pointsRecordList, other.pointsRecordList);
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
    StringBuilder sb = new StringBuilder("UserEnginePageVoRes(");
    boolean first = true;

    sb.append("page:");
    if (this.page == null) {
      sb.append("null");
    } else {
      sb.append(this.page);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("pointsRecordList:");
    if (this.pointsRecordList == null) {
      sb.append("null");
    } else {
      sb.append(this.pointsRecordList);
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

  private static class UserEnginePageVoResStandardSchemeFactory implements SchemeFactory {
    public UserEnginePageVoResStandardScheme getScheme() {
      return new UserEnginePageVoResStandardScheme();
    }
  }

  private static class UserEnginePageVoResStandardScheme extends StandardScheme<UserEnginePageVoRes> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, UserEnginePageVoRes struct) throws org.apache.thrift.TException {
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
          case 2: // POINTS_RECORD_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list16 = iprot.readListBegin();
                struct.pointsRecordList = new ArrayList<UserEnginePointsRecordVo>(_list16.size);
                UserEnginePointsRecordVo _elem17;
                for (int _i18 = 0; _i18 < _list16.size; ++_i18)
                {
                  _elem17 = new UserEnginePointsRecordVo();
                  _elem17.read(iprot);
                  struct.pointsRecordList.add(_elem17);
                }
                iprot.readListEnd();
              }
              struct.setPointsRecordListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, UserEnginePageVoRes struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.page != null) {
        oprot.writeFieldBegin(PAGE_FIELD_DESC);
        struct.page.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.pointsRecordList != null) {
        oprot.writeFieldBegin(POINTS_RECORD_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.pointsRecordList.size()));
          for (UserEnginePointsRecordVo _iter19 : struct.pointsRecordList)
          {
            _iter19.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class UserEnginePageVoResTupleSchemeFactory implements SchemeFactory {
    public UserEnginePageVoResTupleScheme getScheme() {
      return new UserEnginePageVoResTupleScheme();
    }
  }

  private static class UserEnginePageVoResTupleScheme extends TupleScheme<UserEnginePageVoRes> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, UserEnginePageVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPage()) {
        optionals.set(0);
      }
      if (struct.isSetPointsRecordList()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetPage()) {
        struct.page.write(oprot);
      }
      if (struct.isSetPointsRecordList()) {
        {
          oprot.writeI32(struct.pointsRecordList.size());
          for (UserEnginePointsRecordVo _iter20 : struct.pointsRecordList)
          {
            _iter20.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, UserEnginePageVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.page = new com.froad.thrift.vo.PageVo();
        struct.page.read(iprot);
        struct.setPageIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list21 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.pointsRecordList = new ArrayList<UserEnginePointsRecordVo>(_list21.size);
          UserEnginePointsRecordVo _elem22;
          for (int _i23 = 0; _i23 < _list21.size; ++_i23)
          {
            _elem22 = new UserEnginePointsRecordVo();
            _elem22.read(iprot);
            struct.pointsRecordList.add(_elem22);
          }
        }
        struct.setPointsRecordListIsSet(true);
      }
    }
  }

}

