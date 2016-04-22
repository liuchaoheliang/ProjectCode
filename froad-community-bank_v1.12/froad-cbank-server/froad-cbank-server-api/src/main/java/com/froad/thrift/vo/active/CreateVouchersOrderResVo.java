/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.froad.thrift.vo.active;

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
 * 创建订单 响应
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class CreateVouchersOrderResVo implements org.apache.thrift.TBase<CreateVouchersOrderResVo, CreateVouchersOrderResVo._Fields>, java.io.Serializable, Cloneable, Comparable<CreateVouchersOrderResVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CreateVouchersOrderResVo");

  private static final org.apache.thrift.protocol.TField RESULT_FIELD_DESC = new org.apache.thrift.protocol.TField("Result", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField MARKET_ORDER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("marketOrderId", org.apache.thrift.protocol.TType.STRING, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new CreateVouchersOrderResVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new CreateVouchersOrderResVoTupleSchemeFactory());
  }

  /**
   * 结果
   */
  public com.froad.thrift.vo.ResultVo Result; // required
  /**
   * 促销平台订单编号
   */
  public String marketOrderId; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 结果
     */
    RESULT((short)1, "Result"),
    /**
     * 促销平台订单编号
     */
    MARKET_ORDER_ID((short)2, "marketOrderId");

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
        case 1: // RESULT
          return RESULT;
        case 2: // MARKET_ORDER_ID
          return MARKET_ORDER_ID;
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
    tmpMap.put(_Fields.RESULT, new org.apache.thrift.meta_data.FieldMetaData("Result", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.ResultVo.class)));
    tmpMap.put(_Fields.MARKET_ORDER_ID, new org.apache.thrift.meta_data.FieldMetaData("marketOrderId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CreateVouchersOrderResVo.class, metaDataMap);
  }

  public CreateVouchersOrderResVo() {
  }

  public CreateVouchersOrderResVo(
    com.froad.thrift.vo.ResultVo Result,
    String marketOrderId)
  {
    this();
    this.Result = Result;
    this.marketOrderId = marketOrderId;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CreateVouchersOrderResVo(CreateVouchersOrderResVo other) {
    if (other.isSetResult()) {
      this.Result = new com.froad.thrift.vo.ResultVo(other.Result);
    }
    if (other.isSetMarketOrderId()) {
      this.marketOrderId = other.marketOrderId;
    }
  }

  public CreateVouchersOrderResVo deepCopy() {
    return new CreateVouchersOrderResVo(this);
  }

  @Override
  public void clear() {
    this.Result = null;
    this.marketOrderId = null;
  }

  /**
   * 结果
   */
  public com.froad.thrift.vo.ResultVo getResult() {
    return this.Result;
  }

  /**
   * 结果
   */
  public CreateVouchersOrderResVo setResult(com.froad.thrift.vo.ResultVo Result) {
    this.Result = Result;
    return this;
  }

  public void unsetResult() {
    this.Result = null;
  }

  /** Returns true if field Result is set (has been assigned a value) and false otherwise */
  public boolean isSetResult() {
    return this.Result != null;
  }

  public void setResultIsSet(boolean value) {
    if (!value) {
      this.Result = null;
    }
  }

  /**
   * 促销平台订单编号
   */
  public String getMarketOrderId() {
    return this.marketOrderId;
  }

  /**
   * 促销平台订单编号
   */
  public CreateVouchersOrderResVo setMarketOrderId(String marketOrderId) {
    this.marketOrderId = marketOrderId;
    return this;
  }

  public void unsetMarketOrderId() {
    this.marketOrderId = null;
  }

  /** Returns true if field marketOrderId is set (has been assigned a value) and false otherwise */
  public boolean isSetMarketOrderId() {
    return this.marketOrderId != null;
  }

  public void setMarketOrderIdIsSet(boolean value) {
    if (!value) {
      this.marketOrderId = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case RESULT:
      if (value == null) {
        unsetResult();
      } else {
        setResult((com.froad.thrift.vo.ResultVo)value);
      }
      break;

    case MARKET_ORDER_ID:
      if (value == null) {
        unsetMarketOrderId();
      } else {
        setMarketOrderId((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RESULT:
      return getResult();

    case MARKET_ORDER_ID:
      return getMarketOrderId();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case RESULT:
      return isSetResult();
    case MARKET_ORDER_ID:
      return isSetMarketOrderId();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof CreateVouchersOrderResVo)
      return this.equals((CreateVouchersOrderResVo)that);
    return false;
  }

  public boolean equals(CreateVouchersOrderResVo that) {
    if (that == null)
      return false;

    boolean this_present_Result = true && this.isSetResult();
    boolean that_present_Result = true && that.isSetResult();
    if (this_present_Result || that_present_Result) {
      if (!(this_present_Result && that_present_Result))
        return false;
      if (!this.Result.equals(that.Result))
        return false;
    }

    boolean this_present_marketOrderId = true && this.isSetMarketOrderId();
    boolean that_present_marketOrderId = true && that.isSetMarketOrderId();
    if (this_present_marketOrderId || that_present_marketOrderId) {
      if (!(this_present_marketOrderId && that_present_marketOrderId))
        return false;
      if (!this.marketOrderId.equals(that.marketOrderId))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_Result = true && (isSetResult());
    list.add(present_Result);
    if (present_Result)
      list.add(Result);

    boolean present_marketOrderId = true && (isSetMarketOrderId());
    list.add(present_marketOrderId);
    if (present_marketOrderId)
      list.add(marketOrderId);

    return list.hashCode();
  }

  @Override
  public int compareTo(CreateVouchersOrderResVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetResult()).compareTo(other.isSetResult());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetResult()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.Result, other.Result);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMarketOrderId()).compareTo(other.isSetMarketOrderId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMarketOrderId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.marketOrderId, other.marketOrderId);
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
    StringBuilder sb = new StringBuilder("CreateVouchersOrderResVo(");
    boolean first = true;

    sb.append("Result:");
    if (this.Result == null) {
      sb.append("null");
    } else {
      sb.append(this.Result);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("marketOrderId:");
    if (this.marketOrderId == null) {
      sb.append("null");
    } else {
      sb.append(this.marketOrderId);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (Result != null) {
      Result.validate();
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

  private static class CreateVouchersOrderResVoStandardSchemeFactory implements SchemeFactory {
    public CreateVouchersOrderResVoStandardScheme getScheme() {
      return new CreateVouchersOrderResVoStandardScheme();
    }
  }

  private static class CreateVouchersOrderResVoStandardScheme extends StandardScheme<CreateVouchersOrderResVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, CreateVouchersOrderResVo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // RESULT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.Result = new com.froad.thrift.vo.ResultVo();
              struct.Result.read(iprot);
              struct.setResultIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // MARKET_ORDER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.marketOrderId = iprot.readString();
              struct.setMarketOrderIdIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, CreateVouchersOrderResVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.Result != null) {
        oprot.writeFieldBegin(RESULT_FIELD_DESC);
        struct.Result.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.marketOrderId != null) {
        oprot.writeFieldBegin(MARKET_ORDER_ID_FIELD_DESC);
        oprot.writeString(struct.marketOrderId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CreateVouchersOrderResVoTupleSchemeFactory implements SchemeFactory {
    public CreateVouchersOrderResVoTupleScheme getScheme() {
      return new CreateVouchersOrderResVoTupleScheme();
    }
  }

  private static class CreateVouchersOrderResVoTupleScheme extends TupleScheme<CreateVouchersOrderResVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, CreateVouchersOrderResVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetResult()) {
        optionals.set(0);
      }
      if (struct.isSetMarketOrderId()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetResult()) {
        struct.Result.write(oprot);
      }
      if (struct.isSetMarketOrderId()) {
        oprot.writeString(struct.marketOrderId);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, CreateVouchersOrderResVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.Result = new com.froad.thrift.vo.ResultVo();
        struct.Result.read(iprot);
        struct.setResultIsSet(true);
      }
      if (incoming.get(1)) {
        struct.marketOrderId = iprot.readString();
        struct.setMarketOrderIdIsSet(true);
      }
    }
  }

}
