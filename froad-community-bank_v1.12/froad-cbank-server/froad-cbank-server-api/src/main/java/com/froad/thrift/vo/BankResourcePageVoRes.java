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
 * ---------------------------------------------------------银行资源服务--------------------------------------
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class BankResourcePageVoRes implements org.apache.thrift.TBase<BankResourcePageVoRes, BankResourcePageVoRes._Fields>, java.io.Serializable, Cloneable, Comparable<BankResourcePageVoRes> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("BankResourcePageVoRes");

  private static final org.apache.thrift.protocol.TField PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("page", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField BANK_RESOURCE_VO_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("bankResourceVoList", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new BankResourcePageVoResStandardSchemeFactory());
    schemes.put(TupleScheme.class, new BankResourcePageVoResTupleSchemeFactory());
  }

  /**
   * 分页page
   */
  public com.froad.thrift.vo.PageVo page; // required
  /**
   * 查询结果list
   */
  public List<BankResourceVo> bankResourceVoList; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 分页page
     */
    PAGE((short)1, "page"),
    /**
     * 查询结果list
     */
    BANK_RESOURCE_VO_LIST((short)2, "bankResourceVoList");

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
        case 2: // BANK_RESOURCE_VO_LIST
          return BANK_RESOURCE_VO_LIST;
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
    tmpMap.put(_Fields.BANK_RESOURCE_VO_LIST, new org.apache.thrift.meta_data.FieldMetaData("bankResourceVoList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT            , "BankResourceVo"))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(BankResourcePageVoRes.class, metaDataMap);
  }

  public BankResourcePageVoRes() {
  }

  public BankResourcePageVoRes(
    com.froad.thrift.vo.PageVo page,
    List<BankResourceVo> bankResourceVoList)
  {
    this();
    this.page = page;
    this.bankResourceVoList = bankResourceVoList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public BankResourcePageVoRes(BankResourcePageVoRes other) {
    if (other.isSetPage()) {
      this.page = new com.froad.thrift.vo.PageVo(other.page);
    }
    if (other.isSetBankResourceVoList()) {
      List<BankResourceVo> __this__bankResourceVoList = new ArrayList<BankResourceVo>(other.bankResourceVoList.size());
      for (BankResourceVo other_element : other.bankResourceVoList) {
        __this__bankResourceVoList.add(other_element);
      }
      this.bankResourceVoList = __this__bankResourceVoList;
    }
  }

  public BankResourcePageVoRes deepCopy() {
    return new BankResourcePageVoRes(this);
  }

  @Override
  public void clear() {
    this.page = null;
    this.bankResourceVoList = null;
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
  public BankResourcePageVoRes setPage(com.froad.thrift.vo.PageVo page) {
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

  public int getBankResourceVoListSize() {
    return (this.bankResourceVoList == null) ? 0 : this.bankResourceVoList.size();
  }

  public java.util.Iterator<BankResourceVo> getBankResourceVoListIterator() {
    return (this.bankResourceVoList == null) ? null : this.bankResourceVoList.iterator();
  }

  public void addToBankResourceVoList(BankResourceVo elem) {
    if (this.bankResourceVoList == null) {
      this.bankResourceVoList = new ArrayList<BankResourceVo>();
    }
    this.bankResourceVoList.add(elem);
  }

  /**
   * 查询结果list
   */
  public List<BankResourceVo> getBankResourceVoList() {
    return this.bankResourceVoList;
  }

  /**
   * 查询结果list
   */
  public BankResourcePageVoRes setBankResourceVoList(List<BankResourceVo> bankResourceVoList) {
    this.bankResourceVoList = bankResourceVoList;
    return this;
  }

  public void unsetBankResourceVoList() {
    this.bankResourceVoList = null;
  }

  /** Returns true if field bankResourceVoList is set (has been assigned a value) and false otherwise */
  public boolean isSetBankResourceVoList() {
    return this.bankResourceVoList != null;
  }

  public void setBankResourceVoListIsSet(boolean value) {
    if (!value) {
      this.bankResourceVoList = null;
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

    case BANK_RESOURCE_VO_LIST:
      if (value == null) {
        unsetBankResourceVoList();
      } else {
        setBankResourceVoList((List<BankResourceVo>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PAGE:
      return getPage();

    case BANK_RESOURCE_VO_LIST:
      return getBankResourceVoList();

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
    case BANK_RESOURCE_VO_LIST:
      return isSetBankResourceVoList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof BankResourcePageVoRes)
      return this.equals((BankResourcePageVoRes)that);
    return false;
  }

  public boolean equals(BankResourcePageVoRes that) {
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

    boolean this_present_bankResourceVoList = true && this.isSetBankResourceVoList();
    boolean that_present_bankResourceVoList = true && that.isSetBankResourceVoList();
    if (this_present_bankResourceVoList || that_present_bankResourceVoList) {
      if (!(this_present_bankResourceVoList && that_present_bankResourceVoList))
        return false;
      if (!this.bankResourceVoList.equals(that.bankResourceVoList))
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

    boolean present_bankResourceVoList = true && (isSetBankResourceVoList());
    list.add(present_bankResourceVoList);
    if (present_bankResourceVoList)
      list.add(bankResourceVoList);

    return list.hashCode();
  }

  @Override
  public int compareTo(BankResourcePageVoRes other) {
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
    lastComparison = Boolean.valueOf(isSetBankResourceVoList()).compareTo(other.isSetBankResourceVoList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBankResourceVoList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.bankResourceVoList, other.bankResourceVoList);
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
    StringBuilder sb = new StringBuilder("BankResourcePageVoRes(");
    boolean first = true;

    sb.append("page:");
    if (this.page == null) {
      sb.append("null");
    } else {
      sb.append(this.page);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("bankResourceVoList:");
    if (this.bankResourceVoList == null) {
      sb.append("null");
    } else {
      sb.append(this.bankResourceVoList);
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

  private static class BankResourcePageVoResStandardSchemeFactory implements SchemeFactory {
    public BankResourcePageVoResStandardScheme getScheme() {
      return new BankResourcePageVoResStandardScheme();
    }
  }

  private static class BankResourcePageVoResStandardScheme extends StandardScheme<BankResourcePageVoRes> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, BankResourcePageVoRes struct) throws org.apache.thrift.TException {
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
          case 2: // BANK_RESOURCE_VO_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list16 = iprot.readListBegin();
                struct.bankResourceVoList = new ArrayList<BankResourceVo>(_list16.size);
                BankResourceVo _elem17;
                for (int _i18 = 0; _i18 < _list16.size; ++_i18)
                {
                  _elem17 = new BankResourceVo();
                  _elem17.read(iprot);
                  struct.bankResourceVoList.add(_elem17);
                }
                iprot.readListEnd();
              }
              struct.setBankResourceVoListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, BankResourcePageVoRes struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.page != null) {
        oprot.writeFieldBegin(PAGE_FIELD_DESC);
        struct.page.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.bankResourceVoList != null) {
        oprot.writeFieldBegin(BANK_RESOURCE_VO_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.bankResourceVoList.size()));
          for (BankResourceVo _iter19 : struct.bankResourceVoList)
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

  private static class BankResourcePageVoResTupleSchemeFactory implements SchemeFactory {
    public BankResourcePageVoResTupleScheme getScheme() {
      return new BankResourcePageVoResTupleScheme();
    }
  }

  private static class BankResourcePageVoResTupleScheme extends TupleScheme<BankResourcePageVoRes> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, BankResourcePageVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPage()) {
        optionals.set(0);
      }
      if (struct.isSetBankResourceVoList()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetPage()) {
        struct.page.write(oprot);
      }
      if (struct.isSetBankResourceVoList()) {
        {
          oprot.writeI32(struct.bankResourceVoList.size());
          for (BankResourceVo _iter20 : struct.bankResourceVoList)
          {
            _iter20.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, BankResourcePageVoRes struct) throws org.apache.thrift.TException {
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
          struct.bankResourceVoList = new ArrayList<BankResourceVo>(_list21.size);
          BankResourceVo _elem22;
          for (int _i23 = 0; _i23 < _list21.size; ++_i23)
          {
            _elem22 = new BankResourceVo();
            _elem22.read(iprot);
            struct.bankResourceVoList.add(_elem22);
          }
        }
        struct.setBankResourceVoListIsSet(true);
      }
    }
  }

}

