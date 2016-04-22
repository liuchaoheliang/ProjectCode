/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.froad.thrift.vo.active;

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
 * 注册(首单)活动规则信息 RegisteredRuleInfoVo 分页
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class RegisteredRuleInfoPageVoRes implements org.apache.thrift.TBase<RegisteredRuleInfoPageVoRes, RegisteredRuleInfoPageVoRes._Fields>, java.io.Serializable, Cloneable, Comparable<RegisteredRuleInfoPageVoRes> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("RegisteredRuleInfoPageVoRes");

  private static final org.apache.thrift.protocol.TField PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("page", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField REGISTERED_RULE_INFO_VO_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("registeredRuleInfoVoList", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new RegisteredRuleInfoPageVoResStandardSchemeFactory());
    schemes.put(TupleScheme.class, new RegisteredRuleInfoPageVoResTupleSchemeFactory());
  }

  public com.froad.thrift.vo.PageVo page; // required
  public List<RegisteredRuleInfoVo> registeredRuleInfoVoList; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PAGE((short)1, "page"),
    REGISTERED_RULE_INFO_VO_LIST((short)2, "registeredRuleInfoVoList");

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
        case 2: // REGISTERED_RULE_INFO_VO_LIST
          return REGISTERED_RULE_INFO_VO_LIST;
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
    tmpMap.put(_Fields.REGISTERED_RULE_INFO_VO_LIST, new org.apache.thrift.meta_data.FieldMetaData("registeredRuleInfoVoList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, RegisteredRuleInfoVo.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(RegisteredRuleInfoPageVoRes.class, metaDataMap);
  }

  public RegisteredRuleInfoPageVoRes() {
  }

  public RegisteredRuleInfoPageVoRes(
    com.froad.thrift.vo.PageVo page,
    List<RegisteredRuleInfoVo> registeredRuleInfoVoList)
  {
    this();
    this.page = page;
    this.registeredRuleInfoVoList = registeredRuleInfoVoList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public RegisteredRuleInfoPageVoRes(RegisteredRuleInfoPageVoRes other) {
    if (other.isSetPage()) {
      this.page = new com.froad.thrift.vo.PageVo(other.page);
    }
    if (other.isSetRegisteredRuleInfoVoList()) {
      List<RegisteredRuleInfoVo> __this__registeredRuleInfoVoList = new ArrayList<RegisteredRuleInfoVo>(other.registeredRuleInfoVoList.size());
      for (RegisteredRuleInfoVo other_element : other.registeredRuleInfoVoList) {
        __this__registeredRuleInfoVoList.add(new RegisteredRuleInfoVo(other_element));
      }
      this.registeredRuleInfoVoList = __this__registeredRuleInfoVoList;
    }
  }

  public RegisteredRuleInfoPageVoRes deepCopy() {
    return new RegisteredRuleInfoPageVoRes(this);
  }

  @Override
  public void clear() {
    this.page = null;
    this.registeredRuleInfoVoList = null;
  }

  public com.froad.thrift.vo.PageVo getPage() {
    return this.page;
  }

  public RegisteredRuleInfoPageVoRes setPage(com.froad.thrift.vo.PageVo page) {
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

  public int getRegisteredRuleInfoVoListSize() {
    return (this.registeredRuleInfoVoList == null) ? 0 : this.registeredRuleInfoVoList.size();
  }

  public java.util.Iterator<RegisteredRuleInfoVo> getRegisteredRuleInfoVoListIterator() {
    return (this.registeredRuleInfoVoList == null) ? null : this.registeredRuleInfoVoList.iterator();
  }

  public void addToRegisteredRuleInfoVoList(RegisteredRuleInfoVo elem) {
    if (this.registeredRuleInfoVoList == null) {
      this.registeredRuleInfoVoList = new ArrayList<RegisteredRuleInfoVo>();
    }
    this.registeredRuleInfoVoList.add(elem);
  }

  public List<RegisteredRuleInfoVo> getRegisteredRuleInfoVoList() {
    return this.registeredRuleInfoVoList;
  }

  public RegisteredRuleInfoPageVoRes setRegisteredRuleInfoVoList(List<RegisteredRuleInfoVo> registeredRuleInfoVoList) {
    this.registeredRuleInfoVoList = registeredRuleInfoVoList;
    return this;
  }

  public void unsetRegisteredRuleInfoVoList() {
    this.registeredRuleInfoVoList = null;
  }

  /** Returns true if field registeredRuleInfoVoList is set (has been assigned a value) and false otherwise */
  public boolean isSetRegisteredRuleInfoVoList() {
    return this.registeredRuleInfoVoList != null;
  }

  public void setRegisteredRuleInfoVoListIsSet(boolean value) {
    if (!value) {
      this.registeredRuleInfoVoList = null;
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

    case REGISTERED_RULE_INFO_VO_LIST:
      if (value == null) {
        unsetRegisteredRuleInfoVoList();
      } else {
        setRegisteredRuleInfoVoList((List<RegisteredRuleInfoVo>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PAGE:
      return getPage();

    case REGISTERED_RULE_INFO_VO_LIST:
      return getRegisteredRuleInfoVoList();

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
    case REGISTERED_RULE_INFO_VO_LIST:
      return isSetRegisteredRuleInfoVoList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof RegisteredRuleInfoPageVoRes)
      return this.equals((RegisteredRuleInfoPageVoRes)that);
    return false;
  }

  public boolean equals(RegisteredRuleInfoPageVoRes that) {
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

    boolean this_present_registeredRuleInfoVoList = true && this.isSetRegisteredRuleInfoVoList();
    boolean that_present_registeredRuleInfoVoList = true && that.isSetRegisteredRuleInfoVoList();
    if (this_present_registeredRuleInfoVoList || that_present_registeredRuleInfoVoList) {
      if (!(this_present_registeredRuleInfoVoList && that_present_registeredRuleInfoVoList))
        return false;
      if (!this.registeredRuleInfoVoList.equals(that.registeredRuleInfoVoList))
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

    boolean present_registeredRuleInfoVoList = true && (isSetRegisteredRuleInfoVoList());
    list.add(present_registeredRuleInfoVoList);
    if (present_registeredRuleInfoVoList)
      list.add(registeredRuleInfoVoList);

    return list.hashCode();
  }

  @Override
  public int compareTo(RegisteredRuleInfoPageVoRes other) {
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
    lastComparison = Boolean.valueOf(isSetRegisteredRuleInfoVoList()).compareTo(other.isSetRegisteredRuleInfoVoList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRegisteredRuleInfoVoList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.registeredRuleInfoVoList, other.registeredRuleInfoVoList);
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
    StringBuilder sb = new StringBuilder("RegisteredRuleInfoPageVoRes(");
    boolean first = true;

    sb.append("page:");
    if (this.page == null) {
      sb.append("null");
    } else {
      sb.append(this.page);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("registeredRuleInfoVoList:");
    if (this.registeredRuleInfoVoList == null) {
      sb.append("null");
    } else {
      sb.append(this.registeredRuleInfoVoList);
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

  private static class RegisteredRuleInfoPageVoResStandardSchemeFactory implements SchemeFactory {
    public RegisteredRuleInfoPageVoResStandardScheme getScheme() {
      return new RegisteredRuleInfoPageVoResStandardScheme();
    }
  }

  private static class RegisteredRuleInfoPageVoResStandardScheme extends StandardScheme<RegisteredRuleInfoPageVoRes> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, RegisteredRuleInfoPageVoRes struct) throws org.apache.thrift.TException {
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
          case 2: // REGISTERED_RULE_INFO_VO_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list448 = iprot.readListBegin();
                struct.registeredRuleInfoVoList = new ArrayList<RegisteredRuleInfoVo>(_list448.size);
                RegisteredRuleInfoVo _elem449;
                for (int _i450 = 0; _i450 < _list448.size; ++_i450)
                {
                  _elem449 = new RegisteredRuleInfoVo();
                  _elem449.read(iprot);
                  struct.registeredRuleInfoVoList.add(_elem449);
                }
                iprot.readListEnd();
              }
              struct.setRegisteredRuleInfoVoListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, RegisteredRuleInfoPageVoRes struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.page != null) {
        oprot.writeFieldBegin(PAGE_FIELD_DESC);
        struct.page.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.registeredRuleInfoVoList != null) {
        oprot.writeFieldBegin(REGISTERED_RULE_INFO_VO_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.registeredRuleInfoVoList.size()));
          for (RegisteredRuleInfoVo _iter451 : struct.registeredRuleInfoVoList)
          {
            _iter451.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class RegisteredRuleInfoPageVoResTupleSchemeFactory implements SchemeFactory {
    public RegisteredRuleInfoPageVoResTupleScheme getScheme() {
      return new RegisteredRuleInfoPageVoResTupleScheme();
    }
  }

  private static class RegisteredRuleInfoPageVoResTupleScheme extends TupleScheme<RegisteredRuleInfoPageVoRes> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, RegisteredRuleInfoPageVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPage()) {
        optionals.set(0);
      }
      if (struct.isSetRegisteredRuleInfoVoList()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetPage()) {
        struct.page.write(oprot);
      }
      if (struct.isSetRegisteredRuleInfoVoList()) {
        {
          oprot.writeI32(struct.registeredRuleInfoVoList.size());
          for (RegisteredRuleInfoVo _iter452 : struct.registeredRuleInfoVoList)
          {
            _iter452.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, RegisteredRuleInfoPageVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.page = new com.froad.thrift.vo.PageVo();
        struct.page.read(iprot);
        struct.setPageIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list453 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.registeredRuleInfoVoList = new ArrayList<RegisteredRuleInfoVo>(_list453.size);
          RegisteredRuleInfoVo _elem454;
          for (int _i455 = 0; _i455 < _list453.size; ++_i455)
          {
            _elem454 = new RegisteredRuleInfoVo();
            _elem454.read(iprot);
            struct.registeredRuleInfoVoList.add(_elem454);
          }
        }
        struct.setRegisteredRuleInfoVoListIsSet(true);
      }
    }
  }

}

