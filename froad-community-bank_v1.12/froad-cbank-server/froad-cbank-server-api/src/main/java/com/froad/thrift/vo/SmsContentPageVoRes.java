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
 * 短信模板分页
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class SmsContentPageVoRes implements org.apache.thrift.TBase<SmsContentPageVoRes, SmsContentPageVoRes._Fields>, java.io.Serializable, Cloneable, Comparable<SmsContentPageVoRes> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("SmsContentPageVoRes");

  private static final org.apache.thrift.protocol.TField PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("page", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField SMS_CONTENT_VO_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("smsContentVoList", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new SmsContentPageVoResStandardSchemeFactory());
    schemes.put(TupleScheme.class, new SmsContentPageVoResTupleSchemeFactory());
  }

  public com.froad.thrift.vo.PageVo page; // required
  public List<SmsContentVo> smsContentVoList; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PAGE((short)1, "page"),
    SMS_CONTENT_VO_LIST((short)2, "smsContentVoList");

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
        case 2: // SMS_CONTENT_VO_LIST
          return SMS_CONTENT_VO_LIST;
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
    tmpMap.put(_Fields.SMS_CONTENT_VO_LIST, new org.apache.thrift.meta_data.FieldMetaData("smsContentVoList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, SmsContentVo.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(SmsContentPageVoRes.class, metaDataMap);
  }

  public SmsContentPageVoRes() {
  }

  public SmsContentPageVoRes(
    com.froad.thrift.vo.PageVo page,
    List<SmsContentVo> smsContentVoList)
  {
    this();
    this.page = page;
    this.smsContentVoList = smsContentVoList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public SmsContentPageVoRes(SmsContentPageVoRes other) {
    if (other.isSetPage()) {
      this.page = new com.froad.thrift.vo.PageVo(other.page);
    }
    if (other.isSetSmsContentVoList()) {
      List<SmsContentVo> __this__smsContentVoList = new ArrayList<SmsContentVo>(other.smsContentVoList.size());
      for (SmsContentVo other_element : other.smsContentVoList) {
        __this__smsContentVoList.add(new SmsContentVo(other_element));
      }
      this.smsContentVoList = __this__smsContentVoList;
    }
  }

  public SmsContentPageVoRes deepCopy() {
    return new SmsContentPageVoRes(this);
  }

  @Override
  public void clear() {
    this.page = null;
    this.smsContentVoList = null;
  }

  public com.froad.thrift.vo.PageVo getPage() {
    return this.page;
  }

  public SmsContentPageVoRes setPage(com.froad.thrift.vo.PageVo page) {
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

  public int getSmsContentVoListSize() {
    return (this.smsContentVoList == null) ? 0 : this.smsContentVoList.size();
  }

  public java.util.Iterator<SmsContentVo> getSmsContentVoListIterator() {
    return (this.smsContentVoList == null) ? null : this.smsContentVoList.iterator();
  }

  public void addToSmsContentVoList(SmsContentVo elem) {
    if (this.smsContentVoList == null) {
      this.smsContentVoList = new ArrayList<SmsContentVo>();
    }
    this.smsContentVoList.add(elem);
  }

  public List<SmsContentVo> getSmsContentVoList() {
    return this.smsContentVoList;
  }

  public SmsContentPageVoRes setSmsContentVoList(List<SmsContentVo> smsContentVoList) {
    this.smsContentVoList = smsContentVoList;
    return this;
  }

  public void unsetSmsContentVoList() {
    this.smsContentVoList = null;
  }

  /** Returns true if field smsContentVoList is set (has been assigned a value) and false otherwise */
  public boolean isSetSmsContentVoList() {
    return this.smsContentVoList != null;
  }

  public void setSmsContentVoListIsSet(boolean value) {
    if (!value) {
      this.smsContentVoList = null;
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

    case SMS_CONTENT_VO_LIST:
      if (value == null) {
        unsetSmsContentVoList();
      } else {
        setSmsContentVoList((List<SmsContentVo>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PAGE:
      return getPage();

    case SMS_CONTENT_VO_LIST:
      return getSmsContentVoList();

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
    case SMS_CONTENT_VO_LIST:
      return isSetSmsContentVoList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof SmsContentPageVoRes)
      return this.equals((SmsContentPageVoRes)that);
    return false;
  }

  public boolean equals(SmsContentPageVoRes that) {
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

    boolean this_present_smsContentVoList = true && this.isSetSmsContentVoList();
    boolean that_present_smsContentVoList = true && that.isSetSmsContentVoList();
    if (this_present_smsContentVoList || that_present_smsContentVoList) {
      if (!(this_present_smsContentVoList && that_present_smsContentVoList))
        return false;
      if (!this.smsContentVoList.equals(that.smsContentVoList))
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

    boolean present_smsContentVoList = true && (isSetSmsContentVoList());
    list.add(present_smsContentVoList);
    if (present_smsContentVoList)
      list.add(smsContentVoList);

    return list.hashCode();
  }

  @Override
  public int compareTo(SmsContentPageVoRes other) {
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
    lastComparison = Boolean.valueOf(isSetSmsContentVoList()).compareTo(other.isSetSmsContentVoList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSmsContentVoList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.smsContentVoList, other.smsContentVoList);
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
    StringBuilder sb = new StringBuilder("SmsContentPageVoRes(");
    boolean first = true;

    sb.append("page:");
    if (this.page == null) {
      sb.append("null");
    } else {
      sb.append(this.page);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("smsContentVoList:");
    if (this.smsContentVoList == null) {
      sb.append("null");
    } else {
      sb.append(this.smsContentVoList);
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

  private static class SmsContentPageVoResStandardSchemeFactory implements SchemeFactory {
    public SmsContentPageVoResStandardScheme getScheme() {
      return new SmsContentPageVoResStandardScheme();
    }
  }

  private static class SmsContentPageVoResStandardScheme extends StandardScheme<SmsContentPageVoRes> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, SmsContentPageVoRes struct) throws org.apache.thrift.TException {
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
          case 2: // SMS_CONTENT_VO_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list64 = iprot.readListBegin();
                struct.smsContentVoList = new ArrayList<SmsContentVo>(_list64.size);
                SmsContentVo _elem65;
                for (int _i66 = 0; _i66 < _list64.size; ++_i66)
                {
                  _elem65 = new SmsContentVo();
                  _elem65.read(iprot);
                  struct.smsContentVoList.add(_elem65);
                }
                iprot.readListEnd();
              }
              struct.setSmsContentVoListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, SmsContentPageVoRes struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.page != null) {
        oprot.writeFieldBegin(PAGE_FIELD_DESC);
        struct.page.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.smsContentVoList != null) {
        oprot.writeFieldBegin(SMS_CONTENT_VO_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.smsContentVoList.size()));
          for (SmsContentVo _iter67 : struct.smsContentVoList)
          {
            _iter67.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class SmsContentPageVoResTupleSchemeFactory implements SchemeFactory {
    public SmsContentPageVoResTupleScheme getScheme() {
      return new SmsContentPageVoResTupleScheme();
    }
  }

  private static class SmsContentPageVoResTupleScheme extends TupleScheme<SmsContentPageVoRes> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, SmsContentPageVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPage()) {
        optionals.set(0);
      }
      if (struct.isSetSmsContentVoList()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetPage()) {
        struct.page.write(oprot);
      }
      if (struct.isSetSmsContentVoList()) {
        {
          oprot.writeI32(struct.smsContentVoList.size());
          for (SmsContentVo _iter68 : struct.smsContentVoList)
          {
            _iter68.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, SmsContentPageVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.page = new com.froad.thrift.vo.PageVo();
        struct.page.read(iprot);
        struct.setPageIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list69 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.smsContentVoList = new ArrayList<SmsContentVo>(_list69.size);
          SmsContentVo _elem70;
          for (int _i71 = 0; _i71 < _list69.size; ++_i71)
          {
            _elem70 = new SmsContentVo();
            _elem70.read(iprot);
            struct.smsContentVoList.add(_elem70);
          }
        }
        struct.setSmsContentVoListIsSet(true);
      }
    }
  }

}

