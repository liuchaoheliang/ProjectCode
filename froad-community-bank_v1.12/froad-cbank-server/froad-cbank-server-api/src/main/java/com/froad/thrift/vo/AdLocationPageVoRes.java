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
 * 广告位分页
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class AdLocationPageVoRes implements org.apache.thrift.TBase<AdLocationPageVoRes, AdLocationPageVoRes._Fields>, java.io.Serializable, Cloneable, Comparable<AdLocationPageVoRes> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("AdLocationPageVoRes");

  private static final org.apache.thrift.protocol.TField PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("page", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField AD_LOCATION_VO_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("adLocationVoList", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new AdLocationPageVoResStandardSchemeFactory());
    schemes.put(TupleScheme.class, new AdLocationPageVoResTupleSchemeFactory());
  }

  /**
   * 分页对象
   */
  public com.froad.thrift.vo.PageVo page; // required
  /**
   * 结果集
   */
  public List<AdLocationVo> adLocationVoList; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 分页对象
     */
    PAGE((short)1, "page"),
    /**
     * 结果集
     */
    AD_LOCATION_VO_LIST((short)2, "adLocationVoList");

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
        case 2: // AD_LOCATION_VO_LIST
          return AD_LOCATION_VO_LIST;
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
    tmpMap.put(_Fields.AD_LOCATION_VO_LIST, new org.apache.thrift.meta_data.FieldMetaData("adLocationVoList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, AdLocationVo.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(AdLocationPageVoRes.class, metaDataMap);
  }

  public AdLocationPageVoRes() {
  }

  public AdLocationPageVoRes(
    com.froad.thrift.vo.PageVo page,
    List<AdLocationVo> adLocationVoList)
  {
    this();
    this.page = page;
    this.adLocationVoList = adLocationVoList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public AdLocationPageVoRes(AdLocationPageVoRes other) {
    if (other.isSetPage()) {
      this.page = new com.froad.thrift.vo.PageVo(other.page);
    }
    if (other.isSetAdLocationVoList()) {
      List<AdLocationVo> __this__adLocationVoList = new ArrayList<AdLocationVo>(other.adLocationVoList.size());
      for (AdLocationVo other_element : other.adLocationVoList) {
        __this__adLocationVoList.add(new AdLocationVo(other_element));
      }
      this.adLocationVoList = __this__adLocationVoList;
    }
  }

  public AdLocationPageVoRes deepCopy() {
    return new AdLocationPageVoRes(this);
  }

  @Override
  public void clear() {
    this.page = null;
    this.adLocationVoList = null;
  }

  /**
   * 分页对象
   */
  public com.froad.thrift.vo.PageVo getPage() {
    return this.page;
  }

  /**
   * 分页对象
   */
  public AdLocationPageVoRes setPage(com.froad.thrift.vo.PageVo page) {
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

  public int getAdLocationVoListSize() {
    return (this.adLocationVoList == null) ? 0 : this.adLocationVoList.size();
  }

  public java.util.Iterator<AdLocationVo> getAdLocationVoListIterator() {
    return (this.adLocationVoList == null) ? null : this.adLocationVoList.iterator();
  }

  public void addToAdLocationVoList(AdLocationVo elem) {
    if (this.adLocationVoList == null) {
      this.adLocationVoList = new ArrayList<AdLocationVo>();
    }
    this.adLocationVoList.add(elem);
  }

  /**
   * 结果集
   */
  public List<AdLocationVo> getAdLocationVoList() {
    return this.adLocationVoList;
  }

  /**
   * 结果集
   */
  public AdLocationPageVoRes setAdLocationVoList(List<AdLocationVo> adLocationVoList) {
    this.adLocationVoList = adLocationVoList;
    return this;
  }

  public void unsetAdLocationVoList() {
    this.adLocationVoList = null;
  }

  /** Returns true if field adLocationVoList is set (has been assigned a value) and false otherwise */
  public boolean isSetAdLocationVoList() {
    return this.adLocationVoList != null;
  }

  public void setAdLocationVoListIsSet(boolean value) {
    if (!value) {
      this.adLocationVoList = null;
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

    case AD_LOCATION_VO_LIST:
      if (value == null) {
        unsetAdLocationVoList();
      } else {
        setAdLocationVoList((List<AdLocationVo>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PAGE:
      return getPage();

    case AD_LOCATION_VO_LIST:
      return getAdLocationVoList();

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
    case AD_LOCATION_VO_LIST:
      return isSetAdLocationVoList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof AdLocationPageVoRes)
      return this.equals((AdLocationPageVoRes)that);
    return false;
  }

  public boolean equals(AdLocationPageVoRes that) {
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

    boolean this_present_adLocationVoList = true && this.isSetAdLocationVoList();
    boolean that_present_adLocationVoList = true && that.isSetAdLocationVoList();
    if (this_present_adLocationVoList || that_present_adLocationVoList) {
      if (!(this_present_adLocationVoList && that_present_adLocationVoList))
        return false;
      if (!this.adLocationVoList.equals(that.adLocationVoList))
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

    boolean present_adLocationVoList = true && (isSetAdLocationVoList());
    list.add(present_adLocationVoList);
    if (present_adLocationVoList)
      list.add(adLocationVoList);

    return list.hashCode();
  }

  @Override
  public int compareTo(AdLocationPageVoRes other) {
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
    lastComparison = Boolean.valueOf(isSetAdLocationVoList()).compareTo(other.isSetAdLocationVoList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAdLocationVoList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.adLocationVoList, other.adLocationVoList);
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
    StringBuilder sb = new StringBuilder("AdLocationPageVoRes(");
    boolean first = true;

    sb.append("page:");
    if (this.page == null) {
      sb.append("null");
    } else {
      sb.append(this.page);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("adLocationVoList:");
    if (this.adLocationVoList == null) {
      sb.append("null");
    } else {
      sb.append(this.adLocationVoList);
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

  private static class AdLocationPageVoResStandardSchemeFactory implements SchemeFactory {
    public AdLocationPageVoResStandardScheme getScheme() {
      return new AdLocationPageVoResStandardScheme();
    }
  }

  private static class AdLocationPageVoResStandardScheme extends StandardScheme<AdLocationPageVoRes> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, AdLocationPageVoRes struct) throws org.apache.thrift.TException {
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
          case 2: // AD_LOCATION_VO_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list120 = iprot.readListBegin();
                struct.adLocationVoList = new ArrayList<AdLocationVo>(_list120.size);
                AdLocationVo _elem121;
                for (int _i122 = 0; _i122 < _list120.size; ++_i122)
                {
                  _elem121 = new AdLocationVo();
                  _elem121.read(iprot);
                  struct.adLocationVoList.add(_elem121);
                }
                iprot.readListEnd();
              }
              struct.setAdLocationVoListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, AdLocationPageVoRes struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.page != null) {
        oprot.writeFieldBegin(PAGE_FIELD_DESC);
        struct.page.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.adLocationVoList != null) {
        oprot.writeFieldBegin(AD_LOCATION_VO_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.adLocationVoList.size()));
          for (AdLocationVo _iter123 : struct.adLocationVoList)
          {
            _iter123.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class AdLocationPageVoResTupleSchemeFactory implements SchemeFactory {
    public AdLocationPageVoResTupleScheme getScheme() {
      return new AdLocationPageVoResTupleScheme();
    }
  }

  private static class AdLocationPageVoResTupleScheme extends TupleScheme<AdLocationPageVoRes> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, AdLocationPageVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPage()) {
        optionals.set(0);
      }
      if (struct.isSetAdLocationVoList()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetPage()) {
        struct.page.write(oprot);
      }
      if (struct.isSetAdLocationVoList()) {
        {
          oprot.writeI32(struct.adLocationVoList.size());
          for (AdLocationVo _iter124 : struct.adLocationVoList)
          {
            _iter124.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, AdLocationPageVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.page = new com.froad.thrift.vo.PageVo();
        struct.page.read(iprot);
        struct.setPageIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list125 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.adLocationVoList = new ArrayList<AdLocationVo>(_list125.size);
          AdLocationVo _elem126;
          for (int _i127 = 0; _i127 < _list125.size; ++_i127)
          {
            _elem126 = new AdLocationVo();
            _elem126.read(iprot);
            struct.adLocationVoList.add(_elem126);
          }
        }
        struct.setAdLocationVoListIsSet(true);
      }
    }
  }

}
