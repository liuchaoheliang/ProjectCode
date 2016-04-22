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
 * 11.获取积分兑换订单详情（在线积分/网点礼品）响应
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class GetPointExchangeDetailVoRes implements org.apache.thrift.TBase<GetPointExchangeDetailVoRes, GetPointExchangeDetailVoRes._Fields>, java.io.Serializable, Cloneable, Comparable<GetPointExchangeDetailVoRes> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("GetPointExchangeDetailVoRes");

  private static final org.apache.thrift.protocol.TField RESULT_VO_FIELD_DESC = new org.apache.thrift.protocol.TField("resultVo", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField POINT_EXCHANGE_VO_FIELD_DESC = new org.apache.thrift.protocol.TField("pointExchangeVo", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField DELIVER_INFO_DETAIL_VO_FIELD_DESC = new org.apache.thrift.protocol.TField("deliverInfoDetailVo", org.apache.thrift.protocol.TType.STRUCT, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new GetPointExchangeDetailVoResStandardSchemeFactory());
    schemes.put(TupleScheme.class, new GetPointExchangeDetailVoResTupleSchemeFactory());
  }

  /**
   * 结果信息
   */
  public com.froad.thrift.vo.ResultVo resultVo; // required
  /**
   * 积分兑换详情
   */
  public PointExchangeVo pointExchangeVo; // required
  /**
   * 配送|提货信息
   */
  public DeliverInfoDetailVo deliverInfoDetailVo; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 结果信息
     */
    RESULT_VO((short)1, "resultVo"),
    /**
     * 积分兑换详情
     */
    POINT_EXCHANGE_VO((short)2, "pointExchangeVo"),
    /**
     * 配送|提货信息
     */
    DELIVER_INFO_DETAIL_VO((short)3, "deliverInfoDetailVo");

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
        case 1: // RESULT_VO
          return RESULT_VO;
        case 2: // POINT_EXCHANGE_VO
          return POINT_EXCHANGE_VO;
        case 3: // DELIVER_INFO_DETAIL_VO
          return DELIVER_INFO_DETAIL_VO;
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
    tmpMap.put(_Fields.RESULT_VO, new org.apache.thrift.meta_data.FieldMetaData("resultVo", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.ResultVo.class)));
    tmpMap.put(_Fields.POINT_EXCHANGE_VO, new org.apache.thrift.meta_data.FieldMetaData("pointExchangeVo", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, PointExchangeVo.class)));
    tmpMap.put(_Fields.DELIVER_INFO_DETAIL_VO, new org.apache.thrift.meta_data.FieldMetaData("deliverInfoDetailVo", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, DeliverInfoDetailVo.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(GetPointExchangeDetailVoRes.class, metaDataMap);
  }

  public GetPointExchangeDetailVoRes() {
  }

  public GetPointExchangeDetailVoRes(
    com.froad.thrift.vo.ResultVo resultVo,
    PointExchangeVo pointExchangeVo,
    DeliverInfoDetailVo deliverInfoDetailVo)
  {
    this();
    this.resultVo = resultVo;
    this.pointExchangeVo = pointExchangeVo;
    this.deliverInfoDetailVo = deliverInfoDetailVo;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public GetPointExchangeDetailVoRes(GetPointExchangeDetailVoRes other) {
    if (other.isSetResultVo()) {
      this.resultVo = new com.froad.thrift.vo.ResultVo(other.resultVo);
    }
    if (other.isSetPointExchangeVo()) {
      this.pointExchangeVo = new PointExchangeVo(other.pointExchangeVo);
    }
    if (other.isSetDeliverInfoDetailVo()) {
      this.deliverInfoDetailVo = new DeliverInfoDetailVo(other.deliverInfoDetailVo);
    }
  }

  public GetPointExchangeDetailVoRes deepCopy() {
    return new GetPointExchangeDetailVoRes(this);
  }

  @Override
  public void clear() {
    this.resultVo = null;
    this.pointExchangeVo = null;
    this.deliverInfoDetailVo = null;
  }

  /**
   * 结果信息
   */
  public com.froad.thrift.vo.ResultVo getResultVo() {
    return this.resultVo;
  }

  /**
   * 结果信息
   */
  public GetPointExchangeDetailVoRes setResultVo(com.froad.thrift.vo.ResultVo resultVo) {
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
   * 积分兑换详情
   */
  public PointExchangeVo getPointExchangeVo() {
    return this.pointExchangeVo;
  }

  /**
   * 积分兑换详情
   */
  public GetPointExchangeDetailVoRes setPointExchangeVo(PointExchangeVo pointExchangeVo) {
    this.pointExchangeVo = pointExchangeVo;
    return this;
  }

  public void unsetPointExchangeVo() {
    this.pointExchangeVo = null;
  }

  /** Returns true if field pointExchangeVo is set (has been assigned a value) and false otherwise */
  public boolean isSetPointExchangeVo() {
    return this.pointExchangeVo != null;
  }

  public void setPointExchangeVoIsSet(boolean value) {
    if (!value) {
      this.pointExchangeVo = null;
    }
  }

  /**
   * 配送|提货信息
   */
  public DeliverInfoDetailVo getDeliverInfoDetailVo() {
    return this.deliverInfoDetailVo;
  }

  /**
   * 配送|提货信息
   */
  public GetPointExchangeDetailVoRes setDeliverInfoDetailVo(DeliverInfoDetailVo deliverInfoDetailVo) {
    this.deliverInfoDetailVo = deliverInfoDetailVo;
    return this;
  }

  public void unsetDeliverInfoDetailVo() {
    this.deliverInfoDetailVo = null;
  }

  /** Returns true if field deliverInfoDetailVo is set (has been assigned a value) and false otherwise */
  public boolean isSetDeliverInfoDetailVo() {
    return this.deliverInfoDetailVo != null;
  }

  public void setDeliverInfoDetailVoIsSet(boolean value) {
    if (!value) {
      this.deliverInfoDetailVo = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case RESULT_VO:
      if (value == null) {
        unsetResultVo();
      } else {
        setResultVo((com.froad.thrift.vo.ResultVo)value);
      }
      break;

    case POINT_EXCHANGE_VO:
      if (value == null) {
        unsetPointExchangeVo();
      } else {
        setPointExchangeVo((PointExchangeVo)value);
      }
      break;

    case DELIVER_INFO_DETAIL_VO:
      if (value == null) {
        unsetDeliverInfoDetailVo();
      } else {
        setDeliverInfoDetailVo((DeliverInfoDetailVo)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RESULT_VO:
      return getResultVo();

    case POINT_EXCHANGE_VO:
      return getPointExchangeVo();

    case DELIVER_INFO_DETAIL_VO:
      return getDeliverInfoDetailVo();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case RESULT_VO:
      return isSetResultVo();
    case POINT_EXCHANGE_VO:
      return isSetPointExchangeVo();
    case DELIVER_INFO_DETAIL_VO:
      return isSetDeliverInfoDetailVo();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof GetPointExchangeDetailVoRes)
      return this.equals((GetPointExchangeDetailVoRes)that);
    return false;
  }

  public boolean equals(GetPointExchangeDetailVoRes that) {
    if (that == null)
      return false;

    boolean this_present_resultVo = true && this.isSetResultVo();
    boolean that_present_resultVo = true && that.isSetResultVo();
    if (this_present_resultVo || that_present_resultVo) {
      if (!(this_present_resultVo && that_present_resultVo))
        return false;
      if (!this.resultVo.equals(that.resultVo))
        return false;
    }

    boolean this_present_pointExchangeVo = true && this.isSetPointExchangeVo();
    boolean that_present_pointExchangeVo = true && that.isSetPointExchangeVo();
    if (this_present_pointExchangeVo || that_present_pointExchangeVo) {
      if (!(this_present_pointExchangeVo && that_present_pointExchangeVo))
        return false;
      if (!this.pointExchangeVo.equals(that.pointExchangeVo))
        return false;
    }

    boolean this_present_deliverInfoDetailVo = true && this.isSetDeliverInfoDetailVo();
    boolean that_present_deliverInfoDetailVo = true && that.isSetDeliverInfoDetailVo();
    if (this_present_deliverInfoDetailVo || that_present_deliverInfoDetailVo) {
      if (!(this_present_deliverInfoDetailVo && that_present_deliverInfoDetailVo))
        return false;
      if (!this.deliverInfoDetailVo.equals(that.deliverInfoDetailVo))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_resultVo = true && (isSetResultVo());
    list.add(present_resultVo);
    if (present_resultVo)
      list.add(resultVo);

    boolean present_pointExchangeVo = true && (isSetPointExchangeVo());
    list.add(present_pointExchangeVo);
    if (present_pointExchangeVo)
      list.add(pointExchangeVo);

    boolean present_deliverInfoDetailVo = true && (isSetDeliverInfoDetailVo());
    list.add(present_deliverInfoDetailVo);
    if (present_deliverInfoDetailVo)
      list.add(deliverInfoDetailVo);

    return list.hashCode();
  }

  @Override
  public int compareTo(GetPointExchangeDetailVoRes other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

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
    lastComparison = Boolean.valueOf(isSetPointExchangeVo()).compareTo(other.isSetPointExchangeVo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPointExchangeVo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.pointExchangeVo, other.pointExchangeVo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDeliverInfoDetailVo()).compareTo(other.isSetDeliverInfoDetailVo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDeliverInfoDetailVo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.deliverInfoDetailVo, other.deliverInfoDetailVo);
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
    StringBuilder sb = new StringBuilder("GetPointExchangeDetailVoRes(");
    boolean first = true;

    sb.append("resultVo:");
    if (this.resultVo == null) {
      sb.append("null");
    } else {
      sb.append(this.resultVo);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("pointExchangeVo:");
    if (this.pointExchangeVo == null) {
      sb.append("null");
    } else {
      sb.append(this.pointExchangeVo);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("deliverInfoDetailVo:");
    if (this.deliverInfoDetailVo == null) {
      sb.append("null");
    } else {
      sb.append(this.deliverInfoDetailVo);
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
    if (pointExchangeVo != null) {
      pointExchangeVo.validate();
    }
    if (deliverInfoDetailVo != null) {
      deliverInfoDetailVo.validate();
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

  private static class GetPointExchangeDetailVoResStandardSchemeFactory implements SchemeFactory {
    public GetPointExchangeDetailVoResStandardScheme getScheme() {
      return new GetPointExchangeDetailVoResStandardScheme();
    }
  }

  private static class GetPointExchangeDetailVoResStandardScheme extends StandardScheme<GetPointExchangeDetailVoRes> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, GetPointExchangeDetailVoRes struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // RESULT_VO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.resultVo = new com.froad.thrift.vo.ResultVo();
              struct.resultVo.read(iprot);
              struct.setResultVoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // POINT_EXCHANGE_VO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.pointExchangeVo = new PointExchangeVo();
              struct.pointExchangeVo.read(iprot);
              struct.setPointExchangeVoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // DELIVER_INFO_DETAIL_VO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.deliverInfoDetailVo = new DeliverInfoDetailVo();
              struct.deliverInfoDetailVo.read(iprot);
              struct.setDeliverInfoDetailVoIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, GetPointExchangeDetailVoRes struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.resultVo != null) {
        oprot.writeFieldBegin(RESULT_VO_FIELD_DESC);
        struct.resultVo.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.pointExchangeVo != null) {
        oprot.writeFieldBegin(POINT_EXCHANGE_VO_FIELD_DESC);
        struct.pointExchangeVo.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.deliverInfoDetailVo != null) {
        oprot.writeFieldBegin(DELIVER_INFO_DETAIL_VO_FIELD_DESC);
        struct.deliverInfoDetailVo.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class GetPointExchangeDetailVoResTupleSchemeFactory implements SchemeFactory {
    public GetPointExchangeDetailVoResTupleScheme getScheme() {
      return new GetPointExchangeDetailVoResTupleScheme();
    }
  }

  private static class GetPointExchangeDetailVoResTupleScheme extends TupleScheme<GetPointExchangeDetailVoRes> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, GetPointExchangeDetailVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetResultVo()) {
        optionals.set(0);
      }
      if (struct.isSetPointExchangeVo()) {
        optionals.set(1);
      }
      if (struct.isSetDeliverInfoDetailVo()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetResultVo()) {
        struct.resultVo.write(oprot);
      }
      if (struct.isSetPointExchangeVo()) {
        struct.pointExchangeVo.write(oprot);
      }
      if (struct.isSetDeliverInfoDetailVo()) {
        struct.deliverInfoDetailVo.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, GetPointExchangeDetailVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.resultVo = new com.froad.thrift.vo.ResultVo();
        struct.resultVo.read(iprot);
        struct.setResultVoIsSet(true);
      }
      if (incoming.get(1)) {
        struct.pointExchangeVo = new PointExchangeVo();
        struct.pointExchangeVo.read(iprot);
        struct.setPointExchangeVoIsSet(true);
      }
      if (incoming.get(2)) {
        struct.deliverInfoDetailVo = new DeliverInfoDetailVo();
        struct.deliverInfoDetailVo.read(iprot);
        struct.setDeliverInfoDetailVoIsSet(true);
      }
    }
  }

}

