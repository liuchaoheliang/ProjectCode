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
 * 审核任务订单分页VoRes
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class AuditTaskPageVoRes implements org.apache.thrift.TBase<AuditTaskPageVoRes, AuditTaskPageVoRes._Fields>, java.io.Serializable, Cloneable, Comparable<AuditTaskPageVoRes> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("AuditTaskPageVoRes");

  private static final org.apache.thrift.protocol.TField PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("page", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField AUDIT_TASK_VO_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("auditTaskVoList", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new AuditTaskPageVoResStandardSchemeFactory());
    schemes.put(TupleScheme.class, new AuditTaskPageVoResTupleSchemeFactory());
  }

  /**
   * 分页page
   */
  public com.froad.thrift.vo.PageVo page; // optional
  /**
   * 结果list
   */
  public List<AuditTaskPageDetailVo> auditTaskVoList; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 分页page
     */
    PAGE((short)1, "page"),
    /**
     * 结果list
     */
    AUDIT_TASK_VO_LIST((short)2, "auditTaskVoList");

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
        case 2: // AUDIT_TASK_VO_LIST
          return AUDIT_TASK_VO_LIST;
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
  private static final _Fields optionals[] = {_Fields.PAGE,_Fields.AUDIT_TASK_VO_LIST};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PAGE, new org.apache.thrift.meta_data.FieldMetaData("page", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.PageVo.class)));
    tmpMap.put(_Fields.AUDIT_TASK_VO_LIST, new org.apache.thrift.meta_data.FieldMetaData("auditTaskVoList", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT            , "AuditTaskPageDetailVo"))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(AuditTaskPageVoRes.class, metaDataMap);
  }

  public AuditTaskPageVoRes() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public AuditTaskPageVoRes(AuditTaskPageVoRes other) {
    if (other.isSetPage()) {
      this.page = new com.froad.thrift.vo.PageVo(other.page);
    }
    if (other.isSetAuditTaskVoList()) {
      List<AuditTaskPageDetailVo> __this__auditTaskVoList = new ArrayList<AuditTaskPageDetailVo>(other.auditTaskVoList.size());
      for (AuditTaskPageDetailVo other_element : other.auditTaskVoList) {
        __this__auditTaskVoList.add(other_element);
      }
      this.auditTaskVoList = __this__auditTaskVoList;
    }
  }

  public AuditTaskPageVoRes deepCopy() {
    return new AuditTaskPageVoRes(this);
  }

  @Override
  public void clear() {
    this.page = null;
    this.auditTaskVoList = null;
  }

  /**
   * 分页page
   */
  public com.froad.thrift.vo.PageVo getPage() {
    return this.page;
  }

  /**
   * 分页page
   */
  public AuditTaskPageVoRes setPage(com.froad.thrift.vo.PageVo page) {
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

  public int getAuditTaskVoListSize() {
    return (this.auditTaskVoList == null) ? 0 : this.auditTaskVoList.size();
  }

  public java.util.Iterator<AuditTaskPageDetailVo> getAuditTaskVoListIterator() {
    return (this.auditTaskVoList == null) ? null : this.auditTaskVoList.iterator();
  }

  public void addToAuditTaskVoList(AuditTaskPageDetailVo elem) {
    if (this.auditTaskVoList == null) {
      this.auditTaskVoList = new ArrayList<AuditTaskPageDetailVo>();
    }
    this.auditTaskVoList.add(elem);
  }

  /**
   * 结果list
   */
  public List<AuditTaskPageDetailVo> getAuditTaskVoList() {
    return this.auditTaskVoList;
  }

  /**
   * 结果list
   */
  public AuditTaskPageVoRes setAuditTaskVoList(List<AuditTaskPageDetailVo> auditTaskVoList) {
    this.auditTaskVoList = auditTaskVoList;
    return this;
  }

  public void unsetAuditTaskVoList() {
    this.auditTaskVoList = null;
  }

  /** Returns true if field auditTaskVoList is set (has been assigned a value) and false otherwise */
  public boolean isSetAuditTaskVoList() {
    return this.auditTaskVoList != null;
  }

  public void setAuditTaskVoListIsSet(boolean value) {
    if (!value) {
      this.auditTaskVoList = null;
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

    case AUDIT_TASK_VO_LIST:
      if (value == null) {
        unsetAuditTaskVoList();
      } else {
        setAuditTaskVoList((List<AuditTaskPageDetailVo>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PAGE:
      return getPage();

    case AUDIT_TASK_VO_LIST:
      return getAuditTaskVoList();

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
    case AUDIT_TASK_VO_LIST:
      return isSetAuditTaskVoList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof AuditTaskPageVoRes)
      return this.equals((AuditTaskPageVoRes)that);
    return false;
  }

  public boolean equals(AuditTaskPageVoRes that) {
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

    boolean this_present_auditTaskVoList = true && this.isSetAuditTaskVoList();
    boolean that_present_auditTaskVoList = true && that.isSetAuditTaskVoList();
    if (this_present_auditTaskVoList || that_present_auditTaskVoList) {
      if (!(this_present_auditTaskVoList && that_present_auditTaskVoList))
        return false;
      if (!this.auditTaskVoList.equals(that.auditTaskVoList))
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

    boolean present_auditTaskVoList = true && (isSetAuditTaskVoList());
    list.add(present_auditTaskVoList);
    if (present_auditTaskVoList)
      list.add(auditTaskVoList);

    return list.hashCode();
  }

  @Override
  public int compareTo(AuditTaskPageVoRes other) {
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
    lastComparison = Boolean.valueOf(isSetAuditTaskVoList()).compareTo(other.isSetAuditTaskVoList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAuditTaskVoList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.auditTaskVoList, other.auditTaskVoList);
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
    StringBuilder sb = new StringBuilder("AuditTaskPageVoRes(");
    boolean first = true;

    if (isSetPage()) {
      sb.append("page:");
      if (this.page == null) {
        sb.append("null");
      } else {
        sb.append(this.page);
      }
      first = false;
    }
    if (isSetAuditTaskVoList()) {
      if (!first) sb.append(", ");
      sb.append("auditTaskVoList:");
      if (this.auditTaskVoList == null) {
        sb.append("null");
      } else {
        sb.append(this.auditTaskVoList);
      }
      first = false;
    }
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

  private static class AuditTaskPageVoResStandardSchemeFactory implements SchemeFactory {
    public AuditTaskPageVoResStandardScheme getScheme() {
      return new AuditTaskPageVoResStandardScheme();
    }
  }

  private static class AuditTaskPageVoResStandardScheme extends StandardScheme<AuditTaskPageVoRes> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, AuditTaskPageVoRes struct) throws org.apache.thrift.TException {
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
          case 2: // AUDIT_TASK_VO_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list96 = iprot.readListBegin();
                struct.auditTaskVoList = new ArrayList<AuditTaskPageDetailVo>(_list96.size);
                AuditTaskPageDetailVo _elem97;
                for (int _i98 = 0; _i98 < _list96.size; ++_i98)
                {
                  _elem97 = new AuditTaskPageDetailVo();
                  _elem97.read(iprot);
                  struct.auditTaskVoList.add(_elem97);
                }
                iprot.readListEnd();
              }
              struct.setAuditTaskVoListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, AuditTaskPageVoRes struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.page != null) {
        if (struct.isSetPage()) {
          oprot.writeFieldBegin(PAGE_FIELD_DESC);
          struct.page.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.auditTaskVoList != null) {
        if (struct.isSetAuditTaskVoList()) {
          oprot.writeFieldBegin(AUDIT_TASK_VO_LIST_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.auditTaskVoList.size()));
            for (AuditTaskPageDetailVo _iter99 : struct.auditTaskVoList)
            {
              _iter99.write(oprot);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class AuditTaskPageVoResTupleSchemeFactory implements SchemeFactory {
    public AuditTaskPageVoResTupleScheme getScheme() {
      return new AuditTaskPageVoResTupleScheme();
    }
  }

  private static class AuditTaskPageVoResTupleScheme extends TupleScheme<AuditTaskPageVoRes> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, AuditTaskPageVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPage()) {
        optionals.set(0);
      }
      if (struct.isSetAuditTaskVoList()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetPage()) {
        struct.page.write(oprot);
      }
      if (struct.isSetAuditTaskVoList()) {
        {
          oprot.writeI32(struct.auditTaskVoList.size());
          for (AuditTaskPageDetailVo _iter100 : struct.auditTaskVoList)
          {
            _iter100.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, AuditTaskPageVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.page = new com.froad.thrift.vo.PageVo();
        struct.page.read(iprot);
        struct.setPageIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list101 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.auditTaskVoList = new ArrayList<AuditTaskPageDetailVo>(_list101.size);
          AuditTaskPageDetailVo _elem102;
          for (int _i103 = 0; _i103 < _list101.size; ++_i103)
          {
            _elem102 = new AuditTaskPageDetailVo();
            _elem102.read(iprot);
            struct.auditTaskVoList.add(_elem102);
          }
        }
        struct.setAuditTaskVoListIsSet(true);
      }
    }
  }

}
