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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class SubOrderByBossRes implements org.apache.thrift.TBase<SubOrderByBossRes, SubOrderByBossRes._Fields>, java.io.Serializable, Cloneable, Comparable<SubOrderByBossRes> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("SubOrderByBossRes");

  private static final org.apache.thrift.protocol.TField SUB_ORDERS_FIELD_DESC = new org.apache.thrift.protocol.TField("subOrders", org.apache.thrift.protocol.TType.LIST, (short)1);
  private static final org.apache.thrift.protocol.TField RESULT_VO_FIELD_DESC = new org.apache.thrift.protocol.TField("resultVo", org.apache.thrift.protocol.TType.STRUCT, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new SubOrderByBossResStandardSchemeFactory());
    schemes.put(TupleScheme.class, new SubOrderByBossResTupleSchemeFactory());
  }

  /**
   * 子订单列表
   */
  public List<SubOrderVo> subOrders; // required
  /**
   * 查询结果
   */
  public com.froad.thrift.vo.ResultVo resultVo; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 子订单列表
     */
    SUB_ORDERS((short)1, "subOrders"),
    /**
     * 查询结果
     */
    RESULT_VO((short)2, "resultVo");

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
        case 1: // SUB_ORDERS
          return SUB_ORDERS;
        case 2: // RESULT_VO
          return RESULT_VO;
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
    tmpMap.put(_Fields.SUB_ORDERS, new org.apache.thrift.meta_data.FieldMetaData("subOrders", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, SubOrderVo.class))));
    tmpMap.put(_Fields.RESULT_VO, new org.apache.thrift.meta_data.FieldMetaData("resultVo", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.ResultVo.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(SubOrderByBossRes.class, metaDataMap);
  }

  public SubOrderByBossRes() {
  }

  public SubOrderByBossRes(
    List<SubOrderVo> subOrders,
    com.froad.thrift.vo.ResultVo resultVo)
  {
    this();
    this.subOrders = subOrders;
    this.resultVo = resultVo;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public SubOrderByBossRes(SubOrderByBossRes other) {
    if (other.isSetSubOrders()) {
      List<SubOrderVo> __this__subOrders = new ArrayList<SubOrderVo>(other.subOrders.size());
      for (SubOrderVo other_element : other.subOrders) {
        __this__subOrders.add(new SubOrderVo(other_element));
      }
      this.subOrders = __this__subOrders;
    }
    if (other.isSetResultVo()) {
      this.resultVo = new com.froad.thrift.vo.ResultVo(other.resultVo);
    }
  }

  public SubOrderByBossRes deepCopy() {
    return new SubOrderByBossRes(this);
  }

  @Override
  public void clear() {
    this.subOrders = null;
    this.resultVo = null;
  }

  public int getSubOrdersSize() {
    return (this.subOrders == null) ? 0 : this.subOrders.size();
  }

  public java.util.Iterator<SubOrderVo> getSubOrdersIterator() {
    return (this.subOrders == null) ? null : this.subOrders.iterator();
  }

  public void addToSubOrders(SubOrderVo elem) {
    if (this.subOrders == null) {
      this.subOrders = new ArrayList<SubOrderVo>();
    }
    this.subOrders.add(elem);
  }

  /**
   * 子订单列表
   */
  public List<SubOrderVo> getSubOrders() {
    return this.subOrders;
  }

  /**
   * 子订单列表
   */
  public SubOrderByBossRes setSubOrders(List<SubOrderVo> subOrders) {
    this.subOrders = subOrders;
    return this;
  }

  public void unsetSubOrders() {
    this.subOrders = null;
  }

  /** Returns true if field subOrders is set (has been assigned a value) and false otherwise */
  public boolean isSetSubOrders() {
    return this.subOrders != null;
  }

  public void setSubOrdersIsSet(boolean value) {
    if (!value) {
      this.subOrders = null;
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
  public SubOrderByBossRes setResultVo(com.froad.thrift.vo.ResultVo resultVo) {
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

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case SUB_ORDERS:
      if (value == null) {
        unsetSubOrders();
      } else {
        setSubOrders((List<SubOrderVo>)value);
      }
      break;

    case RESULT_VO:
      if (value == null) {
        unsetResultVo();
      } else {
        setResultVo((com.froad.thrift.vo.ResultVo)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SUB_ORDERS:
      return getSubOrders();

    case RESULT_VO:
      return getResultVo();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case SUB_ORDERS:
      return isSetSubOrders();
    case RESULT_VO:
      return isSetResultVo();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof SubOrderByBossRes)
      return this.equals((SubOrderByBossRes)that);
    return false;
  }

  public boolean equals(SubOrderByBossRes that) {
    if (that == null)
      return false;

    boolean this_present_subOrders = true && this.isSetSubOrders();
    boolean that_present_subOrders = true && that.isSetSubOrders();
    if (this_present_subOrders || that_present_subOrders) {
      if (!(this_present_subOrders && that_present_subOrders))
        return false;
      if (!this.subOrders.equals(that.subOrders))
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

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_subOrders = true && (isSetSubOrders());
    list.add(present_subOrders);
    if (present_subOrders)
      list.add(subOrders);

    boolean present_resultVo = true && (isSetResultVo());
    list.add(present_resultVo);
    if (present_resultVo)
      list.add(resultVo);

    return list.hashCode();
  }

  @Override
  public int compareTo(SubOrderByBossRes other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetSubOrders()).compareTo(other.isSetSubOrders());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSubOrders()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.subOrders, other.subOrders);
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
    StringBuilder sb = new StringBuilder("SubOrderByBossRes(");
    boolean first = true;

    sb.append("subOrders:");
    if (this.subOrders == null) {
      sb.append("null");
    } else {
      sb.append(this.subOrders);
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
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
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

  private static class SubOrderByBossResStandardSchemeFactory implements SchemeFactory {
    public SubOrderByBossResStandardScheme getScheme() {
      return new SubOrderByBossResStandardScheme();
    }
  }

  private static class SubOrderByBossResStandardScheme extends StandardScheme<SubOrderByBossRes> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, SubOrderByBossRes struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // SUB_ORDERS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list136 = iprot.readListBegin();
                struct.subOrders = new ArrayList<SubOrderVo>(_list136.size);
                SubOrderVo _elem137;
                for (int _i138 = 0; _i138 < _list136.size; ++_i138)
                {
                  _elem137 = new SubOrderVo();
                  _elem137.read(iprot);
                  struct.subOrders.add(_elem137);
                }
                iprot.readListEnd();
              }
              struct.setSubOrdersIsSet(true);
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
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, SubOrderByBossRes struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.subOrders != null) {
        oprot.writeFieldBegin(SUB_ORDERS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.subOrders.size()));
          for (SubOrderVo _iter139 : struct.subOrders)
          {
            _iter139.write(oprot);
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
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class SubOrderByBossResTupleSchemeFactory implements SchemeFactory {
    public SubOrderByBossResTupleScheme getScheme() {
      return new SubOrderByBossResTupleScheme();
    }
  }

  private static class SubOrderByBossResTupleScheme extends TupleScheme<SubOrderByBossRes> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, SubOrderByBossRes struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetSubOrders()) {
        optionals.set(0);
      }
      if (struct.isSetResultVo()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetSubOrders()) {
        {
          oprot.writeI32(struct.subOrders.size());
          for (SubOrderVo _iter140 : struct.subOrders)
          {
            _iter140.write(oprot);
          }
        }
      }
      if (struct.isSetResultVo()) {
        struct.resultVo.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, SubOrderByBossRes struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list141 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.subOrders = new ArrayList<SubOrderVo>(_list141.size);
          SubOrderVo _elem142;
          for (int _i143 = 0; _i143 < _list141.size; ++_i143)
          {
            _elem142 = new SubOrderVo();
            _elem142.read(iprot);
            struct.subOrders.add(_elem142);
          }
        }
        struct.setSubOrdersIsSet(true);
      }
      if (incoming.get(1)) {
        struct.resultVo = new com.froad.thrift.vo.ResultVo();
        struct.resultVo.read(iprot);
        struct.setResultVoIsSet(true);
      }
    }
  }

}

