/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.froad.thrift.vo.Business;

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
 * 运营订单列表查询结果
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2015-12-7")
public class BusinessOrderListRes implements org.apache.thrift.TBase<BusinessOrderListRes, BusinessOrderListRes._Fields>, java.io.Serializable, Cloneable, Comparable<BusinessOrderListRes> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("BusinessOrderListRes");

  private static final org.apache.thrift.protocol.TField VO_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("voList", org.apache.thrift.protocol.TType.LIST, (short)1);
  private static final org.apache.thrift.protocol.TField RESULT_VO_FIELD_DESC = new org.apache.thrift.protocol.TField("resultVo", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField PAGE_VO_FIELD_DESC = new org.apache.thrift.protocol.TField("pageVo", org.apache.thrift.protocol.TType.STRUCT, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new BusinessOrderListResStandardSchemeFactory());
    schemes.put(TupleScheme.class, new BusinessOrderListResTupleSchemeFactory());
  }

  /**
   * 运营订单集合
   */
  public List<BusinessOrderVo> voList; // required
  /**
   * 返回结果
   */
  public com.froad.thrift.vo.ResultVo resultVo; // required
  /**
   * 分页数据
   */
  public com.froad.thrift.vo.PageVo pageVo; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 运营订单集合
     */
    VO_LIST((short)1, "voList"),
    /**
     * 返回结果
     */
    RESULT_VO((short)2, "resultVo"),
    /**
     * 分页数据
     */
    PAGE_VO((short)3, "pageVo");

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
        case 1: // VO_LIST
          return VO_LIST;
        case 2: // RESULT_VO
          return RESULT_VO;
        case 3: // PAGE_VO
          return PAGE_VO;
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
    tmpMap.put(_Fields.VO_LIST, new org.apache.thrift.meta_data.FieldMetaData("voList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, BusinessOrderVo.class))));
    tmpMap.put(_Fields.RESULT_VO, new org.apache.thrift.meta_data.FieldMetaData("resultVo", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.ResultVo.class)));
    tmpMap.put(_Fields.PAGE_VO, new org.apache.thrift.meta_data.FieldMetaData("pageVo", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.PageVo.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(BusinessOrderListRes.class, metaDataMap);
  }

  public BusinessOrderListRes() {
  }

  public BusinessOrderListRes(
    List<BusinessOrderVo> voList,
    com.froad.thrift.vo.ResultVo resultVo,
    com.froad.thrift.vo.PageVo pageVo)
  {
    this();
    this.voList = voList;
    this.resultVo = resultVo;
    this.pageVo = pageVo;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public BusinessOrderListRes(BusinessOrderListRes other) {
    if (other.isSetVoList()) {
      List<BusinessOrderVo> __this__voList = new ArrayList<BusinessOrderVo>(other.voList.size());
      for (BusinessOrderVo other_element : other.voList) {
        __this__voList.add(new BusinessOrderVo(other_element));
      }
      this.voList = __this__voList;
    }
    if (other.isSetResultVo()) {
      this.resultVo = new com.froad.thrift.vo.ResultVo(other.resultVo);
    }
    if (other.isSetPageVo()) {
      this.pageVo = new com.froad.thrift.vo.PageVo(other.pageVo);
    }
  }

  public BusinessOrderListRes deepCopy() {
    return new BusinessOrderListRes(this);
  }

  @Override
  public void clear() {
    this.voList = null;
    this.resultVo = null;
    this.pageVo = null;
  }

  public int getVoListSize() {
    return (this.voList == null) ? 0 : this.voList.size();
  }

  public java.util.Iterator<BusinessOrderVo> getVoListIterator() {
    return (this.voList == null) ? null : this.voList.iterator();
  }

  public void addToVoList(BusinessOrderVo elem) {
    if (this.voList == null) {
      this.voList = new ArrayList<BusinessOrderVo>();
    }
    this.voList.add(elem);
  }

  /**
   * 运营订单集合
   */
  public List<BusinessOrderVo> getVoList() {
    return this.voList;
  }

  /**
   * 运营订单集合
   */
  public BusinessOrderListRes setVoList(List<BusinessOrderVo> voList) {
    this.voList = voList;
    return this;
  }

  public void unsetVoList() {
    this.voList = null;
  }

  /** Returns true if field voList is set (has been assigned a value) and false otherwise */
  public boolean isSetVoList() {
    return this.voList != null;
  }

  public void setVoListIsSet(boolean value) {
    if (!value) {
      this.voList = null;
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
  public BusinessOrderListRes setResultVo(com.froad.thrift.vo.ResultVo resultVo) {
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

  /**
   * 分页数据
   */
  public com.froad.thrift.vo.PageVo getPageVo() {
    return this.pageVo;
  }

  /**
   * 分页数据
   */
  public BusinessOrderListRes setPageVo(com.froad.thrift.vo.PageVo pageVo) {
    this.pageVo = pageVo;
    return this;
  }

  public void unsetPageVo() {
    this.pageVo = null;
  }

  /** Returns true if field pageVo is set (has been assigned a value) and false otherwise */
  public boolean isSetPageVo() {
    return this.pageVo != null;
  }

  public void setPageVoIsSet(boolean value) {
    if (!value) {
      this.pageVo = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case VO_LIST:
      if (value == null) {
        unsetVoList();
      } else {
        setVoList((List<BusinessOrderVo>)value);
      }
      break;

    case RESULT_VO:
      if (value == null) {
        unsetResultVo();
      } else {
        setResultVo((com.froad.thrift.vo.ResultVo)value);
      }
      break;

    case PAGE_VO:
      if (value == null) {
        unsetPageVo();
      } else {
        setPageVo((com.froad.thrift.vo.PageVo)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case VO_LIST:
      return getVoList();

    case RESULT_VO:
      return getResultVo();

    case PAGE_VO:
      return getPageVo();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case VO_LIST:
      return isSetVoList();
    case RESULT_VO:
      return isSetResultVo();
    case PAGE_VO:
      return isSetPageVo();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof BusinessOrderListRes)
      return this.equals((BusinessOrderListRes)that);
    return false;
  }

  public boolean equals(BusinessOrderListRes that) {
    if (that == null)
      return false;

    boolean this_present_voList = true && this.isSetVoList();
    boolean that_present_voList = true && that.isSetVoList();
    if (this_present_voList || that_present_voList) {
      if (!(this_present_voList && that_present_voList))
        return false;
      if (!this.voList.equals(that.voList))
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

    boolean this_present_pageVo = true && this.isSetPageVo();
    boolean that_present_pageVo = true && that.isSetPageVo();
    if (this_present_pageVo || that_present_pageVo) {
      if (!(this_present_pageVo && that_present_pageVo))
        return false;
      if (!this.pageVo.equals(that.pageVo))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_voList = true && (isSetVoList());
    list.add(present_voList);
    if (present_voList)
      list.add(voList);

    boolean present_resultVo = true && (isSetResultVo());
    list.add(present_resultVo);
    if (present_resultVo)
      list.add(resultVo);

    boolean present_pageVo = true && (isSetPageVo());
    list.add(present_pageVo);
    if (present_pageVo)
      list.add(pageVo);

    return list.hashCode();
  }

  @Override
  public int compareTo(BusinessOrderListRes other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetVoList()).compareTo(other.isSetVoList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVoList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.voList, other.voList);
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
    lastComparison = Boolean.valueOf(isSetPageVo()).compareTo(other.isSetPageVo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPageVo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.pageVo, other.pageVo);
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
    StringBuilder sb = new StringBuilder("BusinessOrderListRes(");
    boolean first = true;

    sb.append("voList:");
    if (this.voList == null) {
      sb.append("null");
    } else {
      sb.append(this.voList);
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
    sb.append("pageVo:");
    if (this.pageVo == null) {
      sb.append("null");
    } else {
      sb.append(this.pageVo);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (resultVo != null) {
      resultVo.validate();
    }
    if (pageVo != null) {
      pageVo.validate();
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

  private static class BusinessOrderListResStandardSchemeFactory implements SchemeFactory {
    public BusinessOrderListResStandardScheme getScheme() {
      return new BusinessOrderListResStandardScheme();
    }
  }

  private static class BusinessOrderListResStandardScheme extends StandardScheme<BusinessOrderListRes> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, BusinessOrderListRes struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // VO_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.voList = new ArrayList<BusinessOrderVo>(_list0.size);
                BusinessOrderVo _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = new BusinessOrderVo();
                  _elem1.read(iprot);
                  struct.voList.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setVoListIsSet(true);
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
          case 3: // PAGE_VO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.pageVo = new com.froad.thrift.vo.PageVo();
              struct.pageVo.read(iprot);
              struct.setPageVoIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, BusinessOrderListRes struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.voList != null) {
        oprot.writeFieldBegin(VO_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.voList.size()));
          for (BusinessOrderVo _iter3 : struct.voList)
          {
            _iter3.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.resultVo != null) {
        oprot.writeFieldBegin(RESULT_VO_FIELD_DESC);
        struct.resultVo.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.pageVo != null) {
        oprot.writeFieldBegin(PAGE_VO_FIELD_DESC);
        struct.pageVo.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class BusinessOrderListResTupleSchemeFactory implements SchemeFactory {
    public BusinessOrderListResTupleScheme getScheme() {
      return new BusinessOrderListResTupleScheme();
    }
  }

  private static class BusinessOrderListResTupleScheme extends TupleScheme<BusinessOrderListRes> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, BusinessOrderListRes struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetVoList()) {
        optionals.set(0);
      }
      if (struct.isSetResultVo()) {
        optionals.set(1);
      }
      if (struct.isSetPageVo()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetVoList()) {
        {
          oprot.writeI32(struct.voList.size());
          for (BusinessOrderVo _iter4 : struct.voList)
          {
            _iter4.write(oprot);
          }
        }
      }
      if (struct.isSetResultVo()) {
        struct.resultVo.write(oprot);
      }
      if (struct.isSetPageVo()) {
        struct.pageVo.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, BusinessOrderListRes struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.voList = new ArrayList<BusinessOrderVo>(_list5.size);
          BusinessOrderVo _elem6;
          for (int _i7 = 0; _i7 < _list5.size; ++_i7)
          {
            _elem6 = new BusinessOrderVo();
            _elem6.read(iprot);
            struct.voList.add(_elem6);
          }
        }
        struct.setVoListIsSet(true);
      }
      if (incoming.get(1)) {
        struct.resultVo = new com.froad.thrift.vo.ResultVo();
        struct.resultVo.read(iprot);
        struct.setResultVoIsSet(true);
      }
      if (incoming.get(2)) {
        struct.pageVo = new com.froad.thrift.vo.PageVo();
        struct.pageVo.read(iprot);
        struct.setPageVoIsSet(true);
      }
    }
  }

}

