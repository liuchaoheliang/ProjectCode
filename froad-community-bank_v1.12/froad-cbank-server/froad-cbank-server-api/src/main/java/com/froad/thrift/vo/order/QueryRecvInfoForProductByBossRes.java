/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.froad.thrift.vo.order;

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
 * 查询配送商品所有相关订单的发货人信息-响应
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class QueryRecvInfoForProductByBossRes implements org.apache.thrift.TBase<QueryRecvInfoForProductByBossRes, QueryRecvInfoForProductByBossRes._Fields>, java.io.Serializable, Cloneable, Comparable<QueryRecvInfoForProductByBossRes> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("QueryRecvInfoForProductByBossRes");

  private static final org.apache.thrift.protocol.TField RECV_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("recvList", org.apache.thrift.protocol.TType.LIST, (short)1);
  private static final org.apache.thrift.protocol.TField RESULT_VO_FIELD_DESC = new org.apache.thrift.protocol.TField("resultVo", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField PAGE_VO_FIELD_DESC = new org.apache.thrift.protocol.TField("pageVo", org.apache.thrift.protocol.TType.STRUCT, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new QueryRecvInfoForProductByBossResStandardSchemeFactory());
    schemes.put(TupleScheme.class, new QueryRecvInfoForProductByBossResTupleSchemeFactory());
  }

  /**
   * 子订单列表
   */
  public List<RecvInfoForProductVo> recvList; // required
  /**
   * 查询结果
   */
  public com.froad.thrift.vo.ResultVo resultVo; // required
  /**
   * 分页数据
   */
  public com.froad.thrift.vo.PageVo pageVo; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 子订单列表
     */
    RECV_LIST((short)1, "recvList"),
    /**
     * 查询结果
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
        case 1: // RECV_LIST
          return RECV_LIST;
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
    tmpMap.put(_Fields.RECV_LIST, new org.apache.thrift.meta_data.FieldMetaData("recvList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, RecvInfoForProductVo.class))));
    tmpMap.put(_Fields.RESULT_VO, new org.apache.thrift.meta_data.FieldMetaData("resultVo", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.ResultVo.class)));
    tmpMap.put(_Fields.PAGE_VO, new org.apache.thrift.meta_data.FieldMetaData("pageVo", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.PageVo.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(QueryRecvInfoForProductByBossRes.class, metaDataMap);
  }

  public QueryRecvInfoForProductByBossRes() {
  }

  public QueryRecvInfoForProductByBossRes(
    List<RecvInfoForProductVo> recvList,
    com.froad.thrift.vo.ResultVo resultVo,
    com.froad.thrift.vo.PageVo pageVo)
  {
    this();
    this.recvList = recvList;
    this.resultVo = resultVo;
    this.pageVo = pageVo;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public QueryRecvInfoForProductByBossRes(QueryRecvInfoForProductByBossRes other) {
    if (other.isSetRecvList()) {
      List<RecvInfoForProductVo> __this__recvList = new ArrayList<RecvInfoForProductVo>(other.recvList.size());
      for (RecvInfoForProductVo other_element : other.recvList) {
        __this__recvList.add(new RecvInfoForProductVo(other_element));
      }
      this.recvList = __this__recvList;
    }
    if (other.isSetResultVo()) {
      this.resultVo = new com.froad.thrift.vo.ResultVo(other.resultVo);
    }
    if (other.isSetPageVo()) {
      this.pageVo = new com.froad.thrift.vo.PageVo(other.pageVo);
    }
  }

  public QueryRecvInfoForProductByBossRes deepCopy() {
    return new QueryRecvInfoForProductByBossRes(this);
  }

  @Override
  public void clear() {
    this.recvList = null;
    this.resultVo = null;
    this.pageVo = null;
  }

  public int getRecvListSize() {
    return (this.recvList == null) ? 0 : this.recvList.size();
  }

  public java.util.Iterator<RecvInfoForProductVo> getRecvListIterator() {
    return (this.recvList == null) ? null : this.recvList.iterator();
  }

  public void addToRecvList(RecvInfoForProductVo elem) {
    if (this.recvList == null) {
      this.recvList = new ArrayList<RecvInfoForProductVo>();
    }
    this.recvList.add(elem);
  }

  /**
   * 子订单列表
   */
  public List<RecvInfoForProductVo> getRecvList() {
    return this.recvList;
  }

  /**
   * 子订单列表
   */
  public QueryRecvInfoForProductByBossRes setRecvList(List<RecvInfoForProductVo> recvList) {
    this.recvList = recvList;
    return this;
  }

  public void unsetRecvList() {
    this.recvList = null;
  }

  /** Returns true if field recvList is set (has been assigned a value) and false otherwise */
  public boolean isSetRecvList() {
    return this.recvList != null;
  }

  public void setRecvListIsSet(boolean value) {
    if (!value) {
      this.recvList = null;
    }
  }

  /**
   * 查询结果
   */
  public com.froad.thrift.vo.ResultVo getResultVo() {
    return this.resultVo;
  }

  /**
   * 查询结果
   */
  public QueryRecvInfoForProductByBossRes setResultVo(com.froad.thrift.vo.ResultVo resultVo) {
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
  public QueryRecvInfoForProductByBossRes setPageVo(com.froad.thrift.vo.PageVo pageVo) {
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
    case RECV_LIST:
      if (value == null) {
        unsetRecvList();
      } else {
        setRecvList((List<RecvInfoForProductVo>)value);
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
    case RECV_LIST:
      return getRecvList();

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
    case RECV_LIST:
      return isSetRecvList();
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
    if (that instanceof QueryRecvInfoForProductByBossRes)
      return this.equals((QueryRecvInfoForProductByBossRes)that);
    return false;
  }

  public boolean equals(QueryRecvInfoForProductByBossRes that) {
    if (that == null)
      return false;

    boolean this_present_recvList = true && this.isSetRecvList();
    boolean that_present_recvList = true && that.isSetRecvList();
    if (this_present_recvList || that_present_recvList) {
      if (!(this_present_recvList && that_present_recvList))
        return false;
      if (!this.recvList.equals(that.recvList))
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

    boolean present_recvList = true && (isSetRecvList());
    list.add(present_recvList);
    if (present_recvList)
      list.add(recvList);

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
  public int compareTo(QueryRecvInfoForProductByBossRes other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetRecvList()).compareTo(other.isSetRecvList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRecvList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.recvList, other.recvList);
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
    StringBuilder sb = new StringBuilder("QueryRecvInfoForProductByBossRes(");
    boolean first = true;

    sb.append("recvList:");
    if (this.recvList == null) {
      sb.append("null");
    } else {
      sb.append(this.recvList);
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

  private static class QueryRecvInfoForProductByBossResStandardSchemeFactory implements SchemeFactory {
    public QueryRecvInfoForProductByBossResStandardScheme getScheme() {
      return new QueryRecvInfoForProductByBossResStandardScheme();
    }
  }

  private static class QueryRecvInfoForProductByBossResStandardScheme extends StandardScheme<QueryRecvInfoForProductByBossRes> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, QueryRecvInfoForProductByBossRes struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // RECV_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list144 = iprot.readListBegin();
                struct.recvList = new ArrayList<RecvInfoForProductVo>(_list144.size);
                RecvInfoForProductVo _elem145;
                for (int _i146 = 0; _i146 < _list144.size; ++_i146)
                {
                  _elem145 = new RecvInfoForProductVo();
                  _elem145.read(iprot);
                  struct.recvList.add(_elem145);
                }
                iprot.readListEnd();
              }
              struct.setRecvListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, QueryRecvInfoForProductByBossRes struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.recvList != null) {
        oprot.writeFieldBegin(RECV_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.recvList.size()));
          for (RecvInfoForProductVo _iter147 : struct.recvList)
          {
            _iter147.write(oprot);
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

  private static class QueryRecvInfoForProductByBossResTupleSchemeFactory implements SchemeFactory {
    public QueryRecvInfoForProductByBossResTupleScheme getScheme() {
      return new QueryRecvInfoForProductByBossResTupleScheme();
    }
  }

  private static class QueryRecvInfoForProductByBossResTupleScheme extends TupleScheme<QueryRecvInfoForProductByBossRes> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, QueryRecvInfoForProductByBossRes struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetRecvList()) {
        optionals.set(0);
      }
      if (struct.isSetResultVo()) {
        optionals.set(1);
      }
      if (struct.isSetPageVo()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetRecvList()) {
        {
          oprot.writeI32(struct.recvList.size());
          for (RecvInfoForProductVo _iter148 : struct.recvList)
          {
            _iter148.write(oprot);
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
    public void read(org.apache.thrift.protocol.TProtocol prot, QueryRecvInfoForProductByBossRes struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list149 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.recvList = new ArrayList<RecvInfoForProductVo>(_list149.size);
          RecvInfoForProductVo _elem150;
          for (int _i151 = 0; _i151 < _list149.size; ++_i151)
          {
            _elem150 = new RecvInfoForProductVo();
            _elem150.read(iprot);
            struct.recvList.add(_elem150);
          }
        }
        struct.setRecvListIsSet(true);
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

