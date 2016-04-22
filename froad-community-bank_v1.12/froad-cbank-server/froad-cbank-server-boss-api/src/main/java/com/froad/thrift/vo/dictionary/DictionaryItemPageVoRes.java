/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.froad.thrift.vo.dictionary;

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
 * 字典条目分页VoRes
 * DictionaryItemPageVoRes
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2015-11-26")
public class DictionaryItemPageVoRes implements org.apache.thrift.TBase<DictionaryItemPageVoRes, DictionaryItemPageVoRes._Fields>, java.io.Serializable, Cloneable, Comparable<DictionaryItemPageVoRes> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("DictionaryItemPageVoRes");

  private static final org.apache.thrift.protocol.TField PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("page", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField RESULT_VO_FIELD_DESC = new org.apache.thrift.protocol.TField("resultVo", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField DICTIONARY_ITEM_VO_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("dictionaryItemVoList", org.apache.thrift.protocol.TType.LIST, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new DictionaryItemPageVoResStandardSchemeFactory());
    schemes.put(TupleScheme.class, new DictionaryItemPageVoResTupleSchemeFactory());
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
  public List<DictionaryItemVo> dictionaryItemVoList; // required

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
    DICTIONARY_ITEM_VO_LIST((short)3, "dictionaryItemVoList");

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
        case 3: // DICTIONARY_ITEM_VO_LIST
          return DICTIONARY_ITEM_VO_LIST;
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
    tmpMap.put(_Fields.DICTIONARY_ITEM_VO_LIST, new org.apache.thrift.meta_data.FieldMetaData("dictionaryItemVoList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, DictionaryItemVo.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(DictionaryItemPageVoRes.class, metaDataMap);
  }

  public DictionaryItemPageVoRes() {
  }

  public DictionaryItemPageVoRes(
    com.froad.thrift.vo.PageVo page,
    com.froad.thrift.vo.ResultVo resultVo,
    List<DictionaryItemVo> dictionaryItemVoList)
  {
    this();
    this.page = page;
    this.resultVo = resultVo;
    this.dictionaryItemVoList = dictionaryItemVoList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public DictionaryItemPageVoRes(DictionaryItemPageVoRes other) {
    if (other.isSetPage()) {
      this.page = new com.froad.thrift.vo.PageVo(other.page);
    }
    if (other.isSetResultVo()) {
      this.resultVo = new com.froad.thrift.vo.ResultVo(other.resultVo);
    }
    if (other.isSetDictionaryItemVoList()) {
      List<DictionaryItemVo> __this__dictionaryItemVoList = new ArrayList<DictionaryItemVo>(other.dictionaryItemVoList.size());
      for (DictionaryItemVo other_element : other.dictionaryItemVoList) {
        __this__dictionaryItemVoList.add(new DictionaryItemVo(other_element));
      }
      this.dictionaryItemVoList = __this__dictionaryItemVoList;
    }
  }

  public DictionaryItemPageVoRes deepCopy() {
    return new DictionaryItemPageVoRes(this);
  }

  @Override
  public void clear() {
    this.page = null;
    this.resultVo = null;
    this.dictionaryItemVoList = null;
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
  public DictionaryItemPageVoRes setPage(com.froad.thrift.vo.PageVo page) {
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
  public DictionaryItemPageVoRes setResultVo(com.froad.thrift.vo.ResultVo resultVo) {
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

  public int getDictionaryItemVoListSize() {
    return (this.dictionaryItemVoList == null) ? 0 : this.dictionaryItemVoList.size();
  }

  public java.util.Iterator<DictionaryItemVo> getDictionaryItemVoListIterator() {
    return (this.dictionaryItemVoList == null) ? null : this.dictionaryItemVoList.iterator();
  }

  public void addToDictionaryItemVoList(DictionaryItemVo elem) {
    if (this.dictionaryItemVoList == null) {
      this.dictionaryItemVoList = new ArrayList<DictionaryItemVo>();
    }
    this.dictionaryItemVoList.add(elem);
  }

  /**
   * 返回结果list
   */
  public List<DictionaryItemVo> getDictionaryItemVoList() {
    return this.dictionaryItemVoList;
  }

  /**
   * 返回结果list
   */
  public DictionaryItemPageVoRes setDictionaryItemVoList(List<DictionaryItemVo> dictionaryItemVoList) {
    this.dictionaryItemVoList = dictionaryItemVoList;
    return this;
  }

  public void unsetDictionaryItemVoList() {
    this.dictionaryItemVoList = null;
  }

  /** Returns true if field dictionaryItemVoList is set (has been assigned a value) and false otherwise */
  public boolean isSetDictionaryItemVoList() {
    return this.dictionaryItemVoList != null;
  }

  public void setDictionaryItemVoListIsSet(boolean value) {
    if (!value) {
      this.dictionaryItemVoList = null;
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

    case DICTIONARY_ITEM_VO_LIST:
      if (value == null) {
        unsetDictionaryItemVoList();
      } else {
        setDictionaryItemVoList((List<DictionaryItemVo>)value);
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

    case DICTIONARY_ITEM_VO_LIST:
      return getDictionaryItemVoList();

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
    case DICTIONARY_ITEM_VO_LIST:
      return isSetDictionaryItemVoList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof DictionaryItemPageVoRes)
      return this.equals((DictionaryItemPageVoRes)that);
    return false;
  }

  public boolean equals(DictionaryItemPageVoRes that) {
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

    boolean this_present_dictionaryItemVoList = true && this.isSetDictionaryItemVoList();
    boolean that_present_dictionaryItemVoList = true && that.isSetDictionaryItemVoList();
    if (this_present_dictionaryItemVoList || that_present_dictionaryItemVoList) {
      if (!(this_present_dictionaryItemVoList && that_present_dictionaryItemVoList))
        return false;
      if (!this.dictionaryItemVoList.equals(that.dictionaryItemVoList))
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

    boolean present_dictionaryItemVoList = true && (isSetDictionaryItemVoList());
    list.add(present_dictionaryItemVoList);
    if (present_dictionaryItemVoList)
      list.add(dictionaryItemVoList);

    return list.hashCode();
  }

  @Override
  public int compareTo(DictionaryItemPageVoRes other) {
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
    lastComparison = Boolean.valueOf(isSetDictionaryItemVoList()).compareTo(other.isSetDictionaryItemVoList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDictionaryItemVoList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.dictionaryItemVoList, other.dictionaryItemVoList);
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
    StringBuilder sb = new StringBuilder("DictionaryItemPageVoRes(");
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
    sb.append("dictionaryItemVoList:");
    if (this.dictionaryItemVoList == null) {
      sb.append("null");
    } else {
      sb.append(this.dictionaryItemVoList);
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

  private static class DictionaryItemPageVoResStandardSchemeFactory implements SchemeFactory {
    public DictionaryItemPageVoResStandardScheme getScheme() {
      return new DictionaryItemPageVoResStandardScheme();
    }
  }

  private static class DictionaryItemPageVoResStandardScheme extends StandardScheme<DictionaryItemPageVoRes> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, DictionaryItemPageVoRes struct) throws org.apache.thrift.TException {
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
          case 3: // DICTIONARY_ITEM_VO_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.dictionaryItemVoList = new ArrayList<DictionaryItemVo>(_list0.size);
                DictionaryItemVo _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = new DictionaryItemVo();
                  _elem1.read(iprot);
                  struct.dictionaryItemVoList.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setDictionaryItemVoListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, DictionaryItemPageVoRes struct) throws org.apache.thrift.TException {
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
      if (struct.dictionaryItemVoList != null) {
        oprot.writeFieldBegin(DICTIONARY_ITEM_VO_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.dictionaryItemVoList.size()));
          for (DictionaryItemVo _iter3 : struct.dictionaryItemVoList)
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

  private static class DictionaryItemPageVoResTupleSchemeFactory implements SchemeFactory {
    public DictionaryItemPageVoResTupleScheme getScheme() {
      return new DictionaryItemPageVoResTupleScheme();
    }
  }

  private static class DictionaryItemPageVoResTupleScheme extends TupleScheme<DictionaryItemPageVoRes> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, DictionaryItemPageVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPage()) {
        optionals.set(0);
      }
      if (struct.isSetResultVo()) {
        optionals.set(1);
      }
      if (struct.isSetDictionaryItemVoList()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetPage()) {
        struct.page.write(oprot);
      }
      if (struct.isSetResultVo()) {
        struct.resultVo.write(oprot);
      }
      if (struct.isSetDictionaryItemVoList()) {
        {
          oprot.writeI32(struct.dictionaryItemVoList.size());
          for (DictionaryItemVo _iter4 : struct.dictionaryItemVoList)
          {
            _iter4.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, DictionaryItemPageVoRes struct) throws org.apache.thrift.TException {
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
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.dictionaryItemVoList = new ArrayList<DictionaryItemVo>(_list5.size);
          DictionaryItemVo _elem6;
          for (int _i7 = 0; _i7 < _list5.size; ++_i7)
          {
            _elem6 = new DictionaryItemVo();
            _elem6.read(iprot);
            struct.dictionaryItemVoList.add(_elem6);
          }
        }
        struct.setDictionaryItemVoListIsSet(true);
      }
    }
  }

}
