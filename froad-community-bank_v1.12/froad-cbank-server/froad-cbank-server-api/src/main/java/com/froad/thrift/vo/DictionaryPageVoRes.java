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
 * 字典分页VoRes
 * DictionaryPageVoRes
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class DictionaryPageVoRes implements org.apache.thrift.TBase<DictionaryPageVoRes, DictionaryPageVoRes._Fields>, java.io.Serializable, Cloneable, Comparable<DictionaryPageVoRes> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("DictionaryPageVoRes");

  private static final org.apache.thrift.protocol.TField PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("page", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField RESULT_VO_FIELD_DESC = new org.apache.thrift.protocol.TField("resultVo", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField DICTIONARY_VO_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("dictionaryVoList", org.apache.thrift.protocol.TType.LIST, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new DictionaryPageVoResStandardSchemeFactory());
    schemes.put(TupleScheme.class, new DictionaryPageVoResTupleSchemeFactory());
  }

  /**
   * 分页page
   */
  public com.froad.thrift.vo.PageVo page; // required
  /**
   * 返回结果
   */
  public com.froad.thrift.vo.ResultVo resultVo; // required
  /**
   * 返回结果list
   */
  public List<DictionaryVo> dictionaryVoList; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 分页page
     */
    PAGE((short)1, "page"),
    /**
     * 返回结果
     */
    RESULT_VO((short)2, "resultVo"),
    /**
     * 返回结果list
     */
    DICTIONARY_VO_LIST((short)3, "dictionaryVoList");

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
        case 2: // RESULT_VO
          return RESULT_VO;
        case 3: // DICTIONARY_VO_LIST
          return DICTIONARY_VO_LIST;
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
    tmpMap.put(_Fields.RESULT_VO, new org.apache.thrift.meta_data.FieldMetaData("resultVo", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.ResultVo.class)));
    tmpMap.put(_Fields.DICTIONARY_VO_LIST, new org.apache.thrift.meta_data.FieldMetaData("dictionaryVoList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, DictionaryVo.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(DictionaryPageVoRes.class, metaDataMap);
  }

  public DictionaryPageVoRes() {
  }

  public DictionaryPageVoRes(
    com.froad.thrift.vo.PageVo page,
    com.froad.thrift.vo.ResultVo resultVo,
    List<DictionaryVo> dictionaryVoList)
  {
    this();
    this.page = page;
    this.resultVo = resultVo;
    this.dictionaryVoList = dictionaryVoList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public DictionaryPageVoRes(DictionaryPageVoRes other) {
    if (other.isSetPage()) {
      this.page = new com.froad.thrift.vo.PageVo(other.page);
    }
    if (other.isSetResultVo()) {
      this.resultVo = new com.froad.thrift.vo.ResultVo(other.resultVo);
    }
    if (other.isSetDictionaryVoList()) {
      List<DictionaryVo> __this__dictionaryVoList = new ArrayList<DictionaryVo>(other.dictionaryVoList.size());
      for (DictionaryVo other_element : other.dictionaryVoList) {
        __this__dictionaryVoList.add(new DictionaryVo(other_element));
      }
      this.dictionaryVoList = __this__dictionaryVoList;
    }
  }

  public DictionaryPageVoRes deepCopy() {
    return new DictionaryPageVoRes(this);
  }

  @Override
  public void clear() {
    this.page = null;
    this.resultVo = null;
    this.dictionaryVoList = null;
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
  public DictionaryPageVoRes setPage(com.froad.thrift.vo.PageVo page) {
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

  /**
   * 返回结果
   */
  public com.froad.thrift.vo.ResultVo getResultVo() {
    return this.resultVo;
  }

  /**
   * 返回结果
   */
  public DictionaryPageVoRes setResultVo(com.froad.thrift.vo.ResultVo resultVo) {
    this.resultVo = resultVo;
    return this;
  }

  public void unsetResultVo() {
    this.resultVo = null;
  }

  /** Returns true if field resultVo is set (has been assigned a value) and false otherwise */
  public boolean isSetResultVo() {
    return this.resultVo != null;
  }

  public void setResultVoIsSet(boolean value) {
    if (!value) {
      this.resultVo = null;
    }
  }

  public int getDictionaryVoListSize() {
    return (this.dictionaryVoList == null) ? 0 : this.dictionaryVoList.size();
  }

  public java.util.Iterator<DictionaryVo> getDictionaryVoListIterator() {
    return (this.dictionaryVoList == null) ? null : this.dictionaryVoList.iterator();
  }

  public void addToDictionaryVoList(DictionaryVo elem) {
    if (this.dictionaryVoList == null) {
      this.dictionaryVoList = new ArrayList<DictionaryVo>();
    }
    this.dictionaryVoList.add(elem);
  }

  /**
   * 返回结果list
   */
  public List<DictionaryVo> getDictionaryVoList() {
    return this.dictionaryVoList;
  }

  /**
   * 返回结果list
   */
  public DictionaryPageVoRes setDictionaryVoList(List<DictionaryVo> dictionaryVoList) {
    this.dictionaryVoList = dictionaryVoList;
    return this;
  }

  public void unsetDictionaryVoList() {
    this.dictionaryVoList = null;
  }

  /** Returns true if field dictionaryVoList is set (has been assigned a value) and false otherwise */
  public boolean isSetDictionaryVoList() {
    return this.dictionaryVoList != null;
  }

  public void setDictionaryVoListIsSet(boolean value) {
    if (!value) {
      this.dictionaryVoList = null;
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

    case RESULT_VO:
      if (value == null) {
        unsetResultVo();
      } else {
        setResultVo((com.froad.thrift.vo.ResultVo)value);
      }
      break;

    case DICTIONARY_VO_LIST:
      if (value == null) {
        unsetDictionaryVoList();
      } else {
        setDictionaryVoList((List<DictionaryVo>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PAGE:
      return getPage();

    case RESULT_VO:
      return getResultVo();

    case DICTIONARY_VO_LIST:
      return getDictionaryVoList();

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
    case RESULT_VO:
      return isSetResultVo();
    case DICTIONARY_VO_LIST:
      return isSetDictionaryVoList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof DictionaryPageVoRes)
      return this.equals((DictionaryPageVoRes)that);
    return false;
  }

  public boolean equals(DictionaryPageVoRes that) {
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

    boolean this_present_resultVo = true && this.isSetResultVo();
    boolean that_present_resultVo = true && that.isSetResultVo();
    if (this_present_resultVo || that_present_resultVo) {
      if (!(this_present_resultVo && that_present_resultVo))
        return false;
      if (!this.resultVo.equals(that.resultVo))
        return false;
    }

    boolean this_present_dictionaryVoList = true && this.isSetDictionaryVoList();
    boolean that_present_dictionaryVoList = true && that.isSetDictionaryVoList();
    if (this_present_dictionaryVoList || that_present_dictionaryVoList) {
      if (!(this_present_dictionaryVoList && that_present_dictionaryVoList))
        return false;
      if (!this.dictionaryVoList.equals(that.dictionaryVoList))
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

    boolean present_resultVo = true && (isSetResultVo());
    list.add(present_resultVo);
    if (present_resultVo)
      list.add(resultVo);

    boolean present_dictionaryVoList = true && (isSetDictionaryVoList());
    list.add(present_dictionaryVoList);
    if (present_dictionaryVoList)
      list.add(dictionaryVoList);

    return list.hashCode();
  }

  @Override
  public int compareTo(DictionaryPageVoRes other) {
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
    lastComparison = Boolean.valueOf(isSetResultVo()).compareTo(other.isSetResultVo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetResultVo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.resultVo, other.resultVo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDictionaryVoList()).compareTo(other.isSetDictionaryVoList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDictionaryVoList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.dictionaryVoList, other.dictionaryVoList);
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
    StringBuilder sb = new StringBuilder("DictionaryPageVoRes(");
    boolean first = true;

    sb.append("page:");
    if (this.page == null) {
      sb.append("null");
    } else {
      sb.append(this.page);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("resultVo:");
    if (this.resultVo == null) {
      sb.append("null");
    } else {
      sb.append(this.resultVo);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("dictionaryVoList:");
    if (this.dictionaryVoList == null) {
      sb.append("null");
    } else {
      sb.append(this.dictionaryVoList);
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
    if (resultVo != null) {
      resultVo.validate();
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

  private static class DictionaryPageVoResStandardSchemeFactory implements SchemeFactory {
    public DictionaryPageVoResStandardScheme getScheme() {
      return new DictionaryPageVoResStandardScheme();
    }
  }

  private static class DictionaryPageVoResStandardScheme extends StandardScheme<DictionaryPageVoRes> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, DictionaryPageVoRes struct) throws org.apache.thrift.TException {
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
          case 2: // RESULT_VO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.resultVo = new com.froad.thrift.vo.ResultVo();
              struct.resultVo.read(iprot);
              struct.setResultVoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // DICTIONARY_VO_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list16 = iprot.readListBegin();
                struct.dictionaryVoList = new ArrayList<DictionaryVo>(_list16.size);
                DictionaryVo _elem17;
                for (int _i18 = 0; _i18 < _list16.size; ++_i18)
                {
                  _elem17 = new DictionaryVo();
                  _elem17.read(iprot);
                  struct.dictionaryVoList.add(_elem17);
                }
                iprot.readListEnd();
              }
              struct.setDictionaryVoListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, DictionaryPageVoRes struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.page != null) {
        oprot.writeFieldBegin(PAGE_FIELD_DESC);
        struct.page.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.resultVo != null) {
        oprot.writeFieldBegin(RESULT_VO_FIELD_DESC);
        struct.resultVo.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.dictionaryVoList != null) {
        oprot.writeFieldBegin(DICTIONARY_VO_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.dictionaryVoList.size()));
          for (DictionaryVo _iter19 : struct.dictionaryVoList)
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

  private static class DictionaryPageVoResTupleSchemeFactory implements SchemeFactory {
    public DictionaryPageVoResTupleScheme getScheme() {
      return new DictionaryPageVoResTupleScheme();
    }
  }

  private static class DictionaryPageVoResTupleScheme extends TupleScheme<DictionaryPageVoRes> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, DictionaryPageVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPage()) {
        optionals.set(0);
      }
      if (struct.isSetResultVo()) {
        optionals.set(1);
      }
      if (struct.isSetDictionaryVoList()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetPage()) {
        struct.page.write(oprot);
      }
      if (struct.isSetResultVo()) {
        struct.resultVo.write(oprot);
      }
      if (struct.isSetDictionaryVoList()) {
        {
          oprot.writeI32(struct.dictionaryVoList.size());
          for (DictionaryVo _iter20 : struct.dictionaryVoList)
          {
            _iter20.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, DictionaryPageVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.page = new com.froad.thrift.vo.PageVo();
        struct.page.read(iprot);
        struct.setPageIsSet(true);
      }
      if (incoming.get(1)) {
        struct.resultVo = new com.froad.thrift.vo.ResultVo();
        struct.resultVo.read(iprot);
        struct.setResultVoIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TList _list21 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.dictionaryVoList = new ArrayList<DictionaryVo>(_list21.size);
          DictionaryVo _elem22;
          for (int _i23 = 0; _i23 < _list21.size; ++_i23)
          {
            _elem22 = new DictionaryVo();
            _elem22.read(iprot);
            struct.dictionaryVoList.add(_elem22);
          }
        }
        struct.setDictionaryVoListIsSet(true);
      }
    }
  }

}

