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
 * 营销活动规则信息 ActiveRuleInfoVo 分页
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class ActiveRuleInfoPageVoRes implements org.apache.thrift.TBase<ActiveRuleInfoPageVoRes, ActiveRuleInfoPageVoRes._Fields>, java.io.Serializable, Cloneable, Comparable<ActiveRuleInfoPageVoRes> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ActiveRuleInfoPageVoRes");

  private static final org.apache.thrift.protocol.TField PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("page", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField ACTIVE_RULE_INFO_VO_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("activeRuleInfoVoList", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ActiveRuleInfoPageVoResStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ActiveRuleInfoPageVoResTupleSchemeFactory());
  }

  public com.froad.thrift.vo.PageVo page; // required
  public List<ActiveRuleInfoVo> activeRuleInfoVoList; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PAGE((short)1, "page"),
    ACTIVE_RULE_INFO_VO_LIST((short)2, "activeRuleInfoVoList");

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
        case 2: // ACTIVE_RULE_INFO_VO_LIST
          return ACTIVE_RULE_INFO_VO_LIST;
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
    tmpMap.put(_Fields.ACTIVE_RULE_INFO_VO_LIST, new org.apache.thrift.meta_data.FieldMetaData("activeRuleInfoVoList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ActiveRuleInfoVo.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ActiveRuleInfoPageVoRes.class, metaDataMap);
  }

  public ActiveRuleInfoPageVoRes() {
  }

  public ActiveRuleInfoPageVoRes(
    com.froad.thrift.vo.PageVo page,
    List<ActiveRuleInfoVo> activeRuleInfoVoList)
  {
    this();
    this.page = page;
    this.activeRuleInfoVoList = activeRuleInfoVoList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ActiveRuleInfoPageVoRes(ActiveRuleInfoPageVoRes other) {
    if (other.isSetPage()) {
      this.page = new com.froad.thrift.vo.PageVo(other.page);
    }
    if (other.isSetActiveRuleInfoVoList()) {
      List<ActiveRuleInfoVo> __this__activeRuleInfoVoList = new ArrayList<ActiveRuleInfoVo>(other.activeRuleInfoVoList.size());
      for (ActiveRuleInfoVo other_element : other.activeRuleInfoVoList) {
        __this__activeRuleInfoVoList.add(new ActiveRuleInfoVo(other_element));
      }
      this.activeRuleInfoVoList = __this__activeRuleInfoVoList;
    }
  }

  public ActiveRuleInfoPageVoRes deepCopy() {
    return new ActiveRuleInfoPageVoRes(this);
  }

  @Override
  public void clear() {
    this.page = null;
    this.activeRuleInfoVoList = null;
  }

  public com.froad.thrift.vo.PageVo getPage() {
    return this.page;
  }

  public ActiveRuleInfoPageVoRes setPage(com.froad.thrift.vo.PageVo page) {
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

  public int getActiveRuleInfoVoListSize() {
    return (this.activeRuleInfoVoList == null) ? 0 : this.activeRuleInfoVoList.size();
  }

  public java.util.Iterator<ActiveRuleInfoVo> getActiveRuleInfoVoListIterator() {
    return (this.activeRuleInfoVoList == null) ? null : this.activeRuleInfoVoList.iterator();
  }

  public void addToActiveRuleInfoVoList(ActiveRuleInfoVo elem) {
    if (this.activeRuleInfoVoList == null) {
      this.activeRuleInfoVoList = new ArrayList<ActiveRuleInfoVo>();
    }
    this.activeRuleInfoVoList.add(elem);
  }

  public List<ActiveRuleInfoVo> getActiveRuleInfoVoList() {
    return this.activeRuleInfoVoList;
  }

  public ActiveRuleInfoPageVoRes setActiveRuleInfoVoList(List<ActiveRuleInfoVo> activeRuleInfoVoList) {
    this.activeRuleInfoVoList = activeRuleInfoVoList;
    return this;
  }

  public void unsetActiveRuleInfoVoList() {
    this.activeRuleInfoVoList = null;
  }

  /** Returns true if field activeRuleInfoVoList is set (has been assigned a value) and false otherwise */
  public boolean isSetActiveRuleInfoVoList() {
    return this.activeRuleInfoVoList != null;
  }

  public void setActiveRuleInfoVoListIsSet(boolean value) {
    if (!value) {
      this.activeRuleInfoVoList = null;
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

    case ACTIVE_RULE_INFO_VO_LIST:
      if (value == null) {
        unsetActiveRuleInfoVoList();
      } else {
        setActiveRuleInfoVoList((List<ActiveRuleInfoVo>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PAGE:
      return getPage();

    case ACTIVE_RULE_INFO_VO_LIST:
      return getActiveRuleInfoVoList();

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
    case ACTIVE_RULE_INFO_VO_LIST:
      return isSetActiveRuleInfoVoList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ActiveRuleInfoPageVoRes)
      return this.equals((ActiveRuleInfoPageVoRes)that);
    return false;
  }

  public boolean equals(ActiveRuleInfoPageVoRes that) {
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

    boolean this_present_activeRuleInfoVoList = true && this.isSetActiveRuleInfoVoList();
    boolean that_present_activeRuleInfoVoList = true && that.isSetActiveRuleInfoVoList();
    if (this_present_activeRuleInfoVoList || that_present_activeRuleInfoVoList) {
      if (!(this_present_activeRuleInfoVoList && that_present_activeRuleInfoVoList))
        return false;
      if (!this.activeRuleInfoVoList.equals(that.activeRuleInfoVoList))
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

    boolean present_activeRuleInfoVoList = true && (isSetActiveRuleInfoVoList());
    list.add(present_activeRuleInfoVoList);
    if (present_activeRuleInfoVoList)
      list.add(activeRuleInfoVoList);

    return list.hashCode();
  }

  @Override
  public int compareTo(ActiveRuleInfoPageVoRes other) {
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
    lastComparison = Boolean.valueOf(isSetActiveRuleInfoVoList()).compareTo(other.isSetActiveRuleInfoVoList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetActiveRuleInfoVoList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.activeRuleInfoVoList, other.activeRuleInfoVoList);
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
    StringBuilder sb = new StringBuilder("ActiveRuleInfoPageVoRes(");
    boolean first = true;

    sb.append("page:");
    if (this.page == null) {
      sb.append("null");
    } else {
      sb.append(this.page);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("activeRuleInfoVoList:");
    if (this.activeRuleInfoVoList == null) {
      sb.append("null");
    } else {
      sb.append(this.activeRuleInfoVoList);
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

  private static class ActiveRuleInfoPageVoResStandardSchemeFactory implements SchemeFactory {
    public ActiveRuleInfoPageVoResStandardScheme getScheme() {
      return new ActiveRuleInfoPageVoResStandardScheme();
    }
  }

  private static class ActiveRuleInfoPageVoResStandardScheme extends StandardScheme<ActiveRuleInfoPageVoRes> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ActiveRuleInfoPageVoRes struct) throws org.apache.thrift.TException {
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
          case 2: // ACTIVE_RULE_INFO_VO_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.activeRuleInfoVoList = new ArrayList<ActiveRuleInfoVo>(_list0.size);
                ActiveRuleInfoVo _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = new ActiveRuleInfoVo();
                  _elem1.read(iprot);
                  struct.activeRuleInfoVoList.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setActiveRuleInfoVoListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ActiveRuleInfoPageVoRes struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.page != null) {
        oprot.writeFieldBegin(PAGE_FIELD_DESC);
        struct.page.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.activeRuleInfoVoList != null) {
        oprot.writeFieldBegin(ACTIVE_RULE_INFO_VO_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.activeRuleInfoVoList.size()));
          for (ActiveRuleInfoVo _iter3 : struct.activeRuleInfoVoList)
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

  private static class ActiveRuleInfoPageVoResTupleSchemeFactory implements SchemeFactory {
    public ActiveRuleInfoPageVoResTupleScheme getScheme() {
      return new ActiveRuleInfoPageVoResTupleScheme();
    }
  }

  private static class ActiveRuleInfoPageVoResTupleScheme extends TupleScheme<ActiveRuleInfoPageVoRes> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ActiveRuleInfoPageVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPage()) {
        optionals.set(0);
      }
      if (struct.isSetActiveRuleInfoVoList()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetPage()) {
        struct.page.write(oprot);
      }
      if (struct.isSetActiveRuleInfoVoList()) {
        {
          oprot.writeI32(struct.activeRuleInfoVoList.size());
          for (ActiveRuleInfoVo _iter4 : struct.activeRuleInfoVoList)
          {
            _iter4.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ActiveRuleInfoPageVoRes struct) throws org.apache.thrift.TException {
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
          struct.activeRuleInfoVoList = new ArrayList<ActiveRuleInfoVo>(_list5.size);
          ActiveRuleInfoVo _elem6;
          for (int _i7 = 0; _i7 < _list5.size; ++_i7)
          {
            _elem6 = new ActiveRuleInfoVo();
            _elem6.read(iprot);
            struct.activeRuleInfoVoList.add(_elem6);
          }
        }
        struct.setActiveRuleInfoVoListIsSet(true);
      }
    }
  }

}
