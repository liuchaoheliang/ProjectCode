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
 * Boss机构分页
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class BossOrgPageVoRes implements org.apache.thrift.TBase<BossOrgPageVoRes, BossOrgPageVoRes._Fields>, java.io.Serializable, Cloneable, Comparable<BossOrgPageVoRes> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("BossOrgPageVoRes");

  private static final org.apache.thrift.protocol.TField PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("page", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField BOSS_ORG_VO_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("bossOrgVoList", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new BossOrgPageVoResStandardSchemeFactory());
    schemes.put(TupleScheme.class, new BossOrgPageVoResTupleSchemeFactory());
  }

  public com.froad.thrift.vo.PageVo page; // optional
  public List<BossOrgVo> bossOrgVoList; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PAGE((short)1, "page"),
    BOSS_ORG_VO_LIST((short)2, "bossOrgVoList");

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
        case 2: // BOSS_ORG_VO_LIST
          return BOSS_ORG_VO_LIST;
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
  private static final _Fields optionals[] = {_Fields.PAGE,_Fields.BOSS_ORG_VO_LIST};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PAGE, new org.apache.thrift.meta_data.FieldMetaData("page", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.PageVo.class)));
    tmpMap.put(_Fields.BOSS_ORG_VO_LIST, new org.apache.thrift.meta_data.FieldMetaData("bossOrgVoList", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, BossOrgVo.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(BossOrgPageVoRes.class, metaDataMap);
  }

  public BossOrgPageVoRes() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public BossOrgPageVoRes(BossOrgPageVoRes other) {
    if (other.isSetPage()) {
      this.page = new com.froad.thrift.vo.PageVo(other.page);
    }
    if (other.isSetBossOrgVoList()) {
      List<BossOrgVo> __this__bossOrgVoList = new ArrayList<BossOrgVo>(other.bossOrgVoList.size());
      for (BossOrgVo other_element : other.bossOrgVoList) {
        __this__bossOrgVoList.add(new BossOrgVo(other_element));
      }
      this.bossOrgVoList = __this__bossOrgVoList;
    }
  }

  public BossOrgPageVoRes deepCopy() {
    return new BossOrgPageVoRes(this);
  }

  @Override
  public void clear() {
    this.page = null;
    this.bossOrgVoList = null;
  }

  public com.froad.thrift.vo.PageVo getPage() {
    return this.page;
  }

  public BossOrgPageVoRes setPage(com.froad.thrift.vo.PageVo page) {
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

  public int getBossOrgVoListSize() {
    return (this.bossOrgVoList == null) ? 0 : this.bossOrgVoList.size();
  }

  public java.util.Iterator<BossOrgVo> getBossOrgVoListIterator() {
    return (this.bossOrgVoList == null) ? null : this.bossOrgVoList.iterator();
  }

  public void addToBossOrgVoList(BossOrgVo elem) {
    if (this.bossOrgVoList == null) {
      this.bossOrgVoList = new ArrayList<BossOrgVo>();
    }
    this.bossOrgVoList.add(elem);
  }

  public List<BossOrgVo> getBossOrgVoList() {
    return this.bossOrgVoList;
  }

  public BossOrgPageVoRes setBossOrgVoList(List<BossOrgVo> bossOrgVoList) {
    this.bossOrgVoList = bossOrgVoList;
    return this;
  }

  public void unsetBossOrgVoList() {
    this.bossOrgVoList = null;
  }

  /** Returns true if field bossOrgVoList is set (has been assigned a value) and false otherwise */
  public boolean isSetBossOrgVoList() {
    return this.bossOrgVoList != null;
  }

  public void setBossOrgVoListIsSet(boolean value) {
    if (!value) {
      this.bossOrgVoList = null;
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

    case BOSS_ORG_VO_LIST:
      if (value == null) {
        unsetBossOrgVoList();
      } else {
        setBossOrgVoList((List<BossOrgVo>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PAGE:
      return getPage();

    case BOSS_ORG_VO_LIST:
      return getBossOrgVoList();

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
    case BOSS_ORG_VO_LIST:
      return isSetBossOrgVoList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof BossOrgPageVoRes)
      return this.equals((BossOrgPageVoRes)that);
    return false;
  }

  public boolean equals(BossOrgPageVoRes that) {
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

    boolean this_present_bossOrgVoList = true && this.isSetBossOrgVoList();
    boolean that_present_bossOrgVoList = true && that.isSetBossOrgVoList();
    if (this_present_bossOrgVoList || that_present_bossOrgVoList) {
      if (!(this_present_bossOrgVoList && that_present_bossOrgVoList))
        return false;
      if (!this.bossOrgVoList.equals(that.bossOrgVoList))
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

    boolean present_bossOrgVoList = true && (isSetBossOrgVoList());
    list.add(present_bossOrgVoList);
    if (present_bossOrgVoList)
      list.add(bossOrgVoList);

    return list.hashCode();
  }

  @Override
  public int compareTo(BossOrgPageVoRes other) {
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
    lastComparison = Boolean.valueOf(isSetBossOrgVoList()).compareTo(other.isSetBossOrgVoList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBossOrgVoList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.bossOrgVoList, other.bossOrgVoList);
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
    StringBuilder sb = new StringBuilder("BossOrgPageVoRes(");
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
    if (isSetBossOrgVoList()) {
      if (!first) sb.append(", ");
      sb.append("bossOrgVoList:");
      if (this.bossOrgVoList == null) {
        sb.append("null");
      } else {
        sb.append(this.bossOrgVoList);
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

  private static class BossOrgPageVoResStandardSchemeFactory implements SchemeFactory {
    public BossOrgPageVoResStandardScheme getScheme() {
      return new BossOrgPageVoResStandardScheme();
    }
  }

  private static class BossOrgPageVoResStandardScheme extends StandardScheme<BossOrgPageVoRes> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, BossOrgPageVoRes struct) throws org.apache.thrift.TException {
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
          case 2: // BOSS_ORG_VO_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list88 = iprot.readListBegin();
                struct.bossOrgVoList = new ArrayList<BossOrgVo>(_list88.size);
                BossOrgVo _elem89;
                for (int _i90 = 0; _i90 < _list88.size; ++_i90)
                {
                  _elem89 = new BossOrgVo();
                  _elem89.read(iprot);
                  struct.bossOrgVoList.add(_elem89);
                }
                iprot.readListEnd();
              }
              struct.setBossOrgVoListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, BossOrgPageVoRes struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.page != null) {
        if (struct.isSetPage()) {
          oprot.writeFieldBegin(PAGE_FIELD_DESC);
          struct.page.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.bossOrgVoList != null) {
        if (struct.isSetBossOrgVoList()) {
          oprot.writeFieldBegin(BOSS_ORG_VO_LIST_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.bossOrgVoList.size()));
            for (BossOrgVo _iter91 : struct.bossOrgVoList)
            {
              _iter91.write(oprot);
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

  private static class BossOrgPageVoResTupleSchemeFactory implements SchemeFactory {
    public BossOrgPageVoResTupleScheme getScheme() {
      return new BossOrgPageVoResTupleScheme();
    }
  }

  private static class BossOrgPageVoResTupleScheme extends TupleScheme<BossOrgPageVoRes> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, BossOrgPageVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPage()) {
        optionals.set(0);
      }
      if (struct.isSetBossOrgVoList()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetPage()) {
        struct.page.write(oprot);
      }
      if (struct.isSetBossOrgVoList()) {
        {
          oprot.writeI32(struct.bossOrgVoList.size());
          for (BossOrgVo _iter92 : struct.bossOrgVoList)
          {
            _iter92.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, BossOrgPageVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.page = new com.froad.thrift.vo.PageVo();
        struct.page.read(iprot);
        struct.setPageIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list93 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.bossOrgVoList = new ArrayList<BossOrgVo>(_list93.size);
          BossOrgVo _elem94;
          for (int _i95 = 0; _i95 < _list93.size; ++_i95)
          {
            _elem94 = new BossOrgVo();
            _elem94.read(iprot);
            struct.bossOrgVoList.add(_elem94);
          }
        }
        struct.setBossOrgVoListIsSet(true);
      }
    }
  }

}

