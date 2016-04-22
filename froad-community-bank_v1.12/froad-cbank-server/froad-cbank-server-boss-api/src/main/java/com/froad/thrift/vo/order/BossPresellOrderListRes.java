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
 * 预售交易查询res
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2015-8-7")
public class BossPresellOrderListRes implements org.apache.thrift.TBase<BossPresellOrderListRes, BossPresellOrderListRes._Fields>, java.io.Serializable, Cloneable, Comparable<BossPresellOrderListRes> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("BossPresellOrderListRes");

  private static final org.apache.thrift.protocol.TField ORDERS_FIELD_DESC = new org.apache.thrift.protocol.TField("orders", org.apache.thrift.protocol.TType.LIST, (short)1);
  private static final org.apache.thrift.protocol.TField RESULT_VO_FIELD_DESC = new org.apache.thrift.protocol.TField("resultVo", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField PAGE_VO_FIELD_DESC = new org.apache.thrift.protocol.TField("pageVo", org.apache.thrift.protocol.TType.STRUCT, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new BossPresellOrderListResStandardSchemeFactory());
    schemes.put(TupleScheme.class, new BossPresellOrderListResTupleSchemeFactory());
  }

  /**
   * 订单数据
   */
  public List<BossPresellOrderVo> orders; // required
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
     * 订单数据
     */
    ORDERS((short)1, "orders"),
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
        case 1: // ORDERS
          return ORDERS;
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
    tmpMap.put(_Fields.ORDERS, new org.apache.thrift.meta_data.FieldMetaData("orders", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT            , "BossPresellOrderVo"))));
    tmpMap.put(_Fields.RESULT_VO, new org.apache.thrift.meta_data.FieldMetaData("resultVo", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.ResultVo.class)));
    tmpMap.put(_Fields.PAGE_VO, new org.apache.thrift.meta_data.FieldMetaData("pageVo", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.PageVo.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(BossPresellOrderListRes.class, metaDataMap);
  }

  public BossPresellOrderListRes() {
  }

  public BossPresellOrderListRes(
    List<BossPresellOrderVo> orders,
    com.froad.thrift.vo.ResultVo resultVo,
    com.froad.thrift.vo.PageVo pageVo)
  {
    this();
    this.orders = orders;
    this.resultVo = resultVo;
    this.pageVo = pageVo;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public BossPresellOrderListRes(BossPresellOrderListRes other) {
    if (other.isSetOrders()) {
      List<BossPresellOrderVo> __this__orders = new ArrayList<BossPresellOrderVo>(other.orders.size());
      for (BossPresellOrderVo other_element : other.orders) {
        __this__orders.add(other_element);
      }
      this.orders = __this__orders;
    }
    if (other.isSetResultVo()) {
      this.resultVo = new com.froad.thrift.vo.ResultVo(other.resultVo);
    }
    if (other.isSetPageVo()) {
      this.pageVo = new com.froad.thrift.vo.PageVo(other.pageVo);
    }
  }

  public BossPresellOrderListRes deepCopy() {
    return new BossPresellOrderListRes(this);
  }

  @Override
  public void clear() {
    this.orders = null;
    this.resultVo = null;
    this.pageVo = null;
  }

  public int getOrdersSize() {
    return (this.orders == null) ? 0 : this.orders.size();
  }

  public java.util.Iterator<BossPresellOrderVo> getOrdersIterator() {
    return (this.orders == null) ? null : this.orders.iterator();
  }

  public void addToOrders(BossPresellOrderVo elem) {
    if (this.orders == null) {
      this.orders = new ArrayList<BossPresellOrderVo>();
    }
    this.orders.add(elem);
  }

  /**
   * 订单数据
   */
  public List<BossPresellOrderVo> getOrders() {
    return this.orders;
  }

  /**
   * 订单数据
   */
  public BossPresellOrderListRes setOrders(List<BossPresellOrderVo> orders) {
    this.orders = orders;
    return this;
  }

  public void unsetOrders() {
    this.orders = null;
  }

  /** Returns true if field orders is set (has been assigned a value) and false otherwise */
  public boolean isSetOrders() {
    return this.orders != null;
  }

  public void setOrdersIsSet(boolean value) {
    if (!value) {
      this.orders = null;
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
  public BossPresellOrderListRes setResultVo(com.froad.thrift.vo.ResultVo resultVo) {
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
  public BossPresellOrderListRes setPageVo(com.froad.thrift.vo.PageVo pageVo) {
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
    case ORDERS:
      if (value == null) {
        unsetOrders();
      } else {
        setOrders((List<BossPresellOrderVo>)value);
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
    case ORDERS:
      return getOrders();

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
    case ORDERS:
      return isSetOrders();
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
    if (that instanceof BossPresellOrderListRes)
      return this.equals((BossPresellOrderListRes)that);
    return false;
  }

  public boolean equals(BossPresellOrderListRes that) {
    if (that == null)
      return false;

    boolean this_present_orders = true && this.isSetOrders();
    boolean that_present_orders = true && that.isSetOrders();
    if (this_present_orders || that_present_orders) {
      if (!(this_present_orders && that_present_orders))
        return false;
      if (!this.orders.equals(that.orders))
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

    boolean present_orders = true && (isSetOrders());
    list.add(present_orders);
    if (present_orders)
      list.add(orders);

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
  public int compareTo(BossPresellOrderListRes other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetOrders()).compareTo(other.isSetOrders());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOrders()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.orders, other.orders);
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
    StringBuilder sb = new StringBuilder("BossPresellOrderListRes(");
    boolean first = true;

    sb.append("orders:");
    if (this.orders == null) {
      sb.append("null");
    } else {
      sb.append(this.orders);
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

  private static class BossPresellOrderListResStandardSchemeFactory implements SchemeFactory {
    public BossPresellOrderListResStandardScheme getScheme() {
      return new BossPresellOrderListResStandardScheme();
    }
  }

  private static class BossPresellOrderListResStandardScheme extends StandardScheme<BossPresellOrderListRes> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, BossPresellOrderListRes struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ORDERS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list8 = iprot.readListBegin();
                struct.orders = new ArrayList<BossPresellOrderVo>(_list8.size);
                BossPresellOrderVo _elem9;
                for (int _i10 = 0; _i10 < _list8.size; ++_i10)
                {
                  _elem9 = new BossPresellOrderVo();
                  _elem9.read(iprot);
                  struct.orders.add(_elem9);
                }
                iprot.readListEnd();
              }
              struct.setOrdersIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, BossPresellOrderListRes struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.orders != null) {
        oprot.writeFieldBegin(ORDERS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.orders.size()));
          for (BossPresellOrderVo _iter11 : struct.orders)
          {
            _iter11.write(oprot);
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

  private static class BossPresellOrderListResTupleSchemeFactory implements SchemeFactory {
    public BossPresellOrderListResTupleScheme getScheme() {
      return new BossPresellOrderListResTupleScheme();
    }
  }

  private static class BossPresellOrderListResTupleScheme extends TupleScheme<BossPresellOrderListRes> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, BossPresellOrderListRes struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetOrders()) {
        optionals.set(0);
      }
      if (struct.isSetResultVo()) {
        optionals.set(1);
      }
      if (struct.isSetPageVo()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetOrders()) {
        {
          oprot.writeI32(struct.orders.size());
          for (BossPresellOrderVo _iter12 : struct.orders)
          {
            _iter12.write(oprot);
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
    public void read(org.apache.thrift.protocol.TProtocol prot, BossPresellOrderListRes struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list13 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.orders = new ArrayList<BossPresellOrderVo>(_list13.size);
          BossPresellOrderVo _elem14;
          for (int _i15 = 0; _i15 < _list13.size; ++_i15)
          {
            _elem14 = new BossPresellOrderVo();
            _elem14.read(iprot);
            struct.orders.add(_elem14);
          }
        }
        struct.setOrdersIsSet(true);
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

