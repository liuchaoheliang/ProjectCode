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
 * 创建结果
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class CreateResultVo implements org.apache.thrift.TBase<CreateResultVo, CreateResultVo._Fields>, java.io.Serializable, Cloneable, Comparable<CreateResultVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CreateResultVo");

  private static final org.apache.thrift.protocol.TField RESULT_FIELD_DESC = new org.apache.thrift.protocol.TField("result", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField CUT_ORDER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("cutOrderId", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField VOUCHERS_ORDER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("vouchersOrderId", org.apache.thrift.protocol.TType.STRING, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new CreateResultVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new CreateResultVoTupleSchemeFactory());
  }

  /**
   * ResultVo结果
   */
  public com.froad.thrift.vo.ResultVo result; // required
  /**
   * 营销的满减订单id
   */
  public String cutOrderId; // required
  /**
   * 营销的红包订单id
   */
  public String vouchersOrderId; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * ResultVo结果
     */
    RESULT((short)1, "result"),
    /**
     * 营销的满减订单id
     */
    CUT_ORDER_ID((short)2, "cutOrderId"),
    /**
     * 营销的红包订单id
     */
    VOUCHERS_ORDER_ID((short)3, "vouchersOrderId");

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
        case 2: // CUT_ORDER_ID
          return CUT_ORDER_ID;
        case 3: // VOUCHERS_ORDER_ID
          return VOUCHERS_ORDER_ID;
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
    tmpMap.put(_Fields.RESULT, new org.apache.thrift.meta_data.FieldMetaData("result", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.ResultVo.class)));
    tmpMap.put(_Fields.CUT_ORDER_ID, new org.apache.thrift.meta_data.FieldMetaData("cutOrderId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.VOUCHERS_ORDER_ID, new org.apache.thrift.meta_data.FieldMetaData("vouchersOrderId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CreateResultVo.class, metaDataMap);
  }

  public CreateResultVo() {
  }

  public CreateResultVo(
    com.froad.thrift.vo.ResultVo result,
    String cutOrderId,
    String vouchersOrderId)
  {
    this();
    this.result = result;
    this.cutOrderId = cutOrderId;
    this.vouchersOrderId = vouchersOrderId;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CreateResultVo(CreateResultVo other) {
    if (other.isSetResult()) {
      this.result = new com.froad.thrift.vo.ResultVo(other.result);
    }
    if (other.isSetCutOrderId()) {
      this.cutOrderId = other.cutOrderId;
    }
    if (other.isSetVouchersOrderId()) {
      this.vouchersOrderId = other.vouchersOrderId;
    }
  }

  public CreateResultVo deepCopy() {
    return new CreateResultVo(this);
  }

  @Override
  public void clear() {
    this.result = null;
    this.cutOrderId = null;
    this.vouchersOrderId = null;
  }

  /**
   * ResultVo结果
   */
  public com.froad.thrift.vo.ResultVo getResult() {
    return this.result;
  }

  /**
   * ResultVo结果
   */
  public CreateResultVo setResult(com.froad.thrift.vo.ResultVo result) {
    this.result = result;
    return this;
  }

  public void unsetResult() {
    this.result = null;
  }

  /** Returns true if field result is set (has been assigned a value) and false otherwise */
  public boolean isSetResult() {
    return this.result != null;
  }

  public void setResultIsSet(boolean value) {
    if (!value) {
      this.result = null;
    }
  }

  /**
   * 营销的满减订单id
   */
  public String getCutOrderId() {
    return this.cutOrderId;
  }

  /**
   * 营销的满减订单id
   */
  public CreateResultVo setCutOrderId(String cutOrderId) {
    this.cutOrderId = cutOrderId;
    return this;
  }

  public void unsetCutOrderId() {
    this.cutOrderId = null;
  }

  /** Returns true if field cutOrderId is set (has been assigned a value) and false otherwise */
  public boolean isSetCutOrderId() {
    return this.cutOrderId != null;
  }

  public void setCutOrderIdIsSet(boolean value) {
    if (!value) {
      this.cutOrderId = null;
    }
  }

  /**
   * 营销的红包订单id
   */
  public String getVouchersOrderId() {
    return this.vouchersOrderId;
  }

  /**
   * 营销的红包订单id
   */
  public CreateResultVo setVouchersOrderId(String vouchersOrderId) {
    this.vouchersOrderId = vouchersOrderId;
    return this;
  }

  public void unsetVouchersOrderId() {
    this.vouchersOrderId = null;
  }

  /** Returns true if field vouchersOrderId is set (has been assigned a value) and false otherwise */
  public boolean isSetVouchersOrderId() {
    return this.vouchersOrderId != null;
  }

  public void setVouchersOrderIdIsSet(boolean value) {
    if (!value) {
      this.vouchersOrderId = null;
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

    case CUT_ORDER_ID:
      if (value == null) {
        unsetCutOrderId();
      } else {
        setCutOrderId((String)value);
      }
      break;

    case VOUCHERS_ORDER_ID:
      if (value == null) {
        unsetVouchersOrderId();
      } else {
        setVouchersOrderId((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RESULT:
      return getResult();

    case CUT_ORDER_ID:
      return getCutOrderId();

    case VOUCHERS_ORDER_ID:
      return getVouchersOrderId();

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
    case CUT_ORDER_ID:
      return isSetCutOrderId();
    case VOUCHERS_ORDER_ID:
      return isSetVouchersOrderId();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof CreateResultVo)
      return this.equals((CreateResultVo)that);
    return false;
  }

  public boolean equals(CreateResultVo that) {
    if (that == null)
      return false;

    boolean this_present_result = true && this.isSetResult();
    boolean that_present_result = true && that.isSetResult();
    if (this_present_result || that_present_result) {
      if (!(this_present_result && that_present_result))
        return false;
      if (!this.result.equals(that.result))
        return false;
    }

    boolean this_present_cutOrderId = true && this.isSetCutOrderId();
    boolean that_present_cutOrderId = true && that.isSetCutOrderId();
    if (this_present_cutOrderId || that_present_cutOrderId) {
      if (!(this_present_cutOrderId && that_present_cutOrderId))
        return false;
      if (!this.cutOrderId.equals(that.cutOrderId))
        return false;
    }

    boolean this_present_vouchersOrderId = true && this.isSetVouchersOrderId();
    boolean that_present_vouchersOrderId = true && that.isSetVouchersOrderId();
    if (this_present_vouchersOrderId || that_present_vouchersOrderId) {
      if (!(this_present_vouchersOrderId && that_present_vouchersOrderId))
        return false;
      if (!this.vouchersOrderId.equals(that.vouchersOrderId))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_result = true && (isSetResult());
    list.add(present_result);
    if (present_result)
      list.add(result);

    boolean present_cutOrderId = true && (isSetCutOrderId());
    list.add(present_cutOrderId);
    if (present_cutOrderId)
      list.add(cutOrderId);

    boolean present_vouchersOrderId = true && (isSetVouchersOrderId());
    list.add(present_vouchersOrderId);
    if (present_vouchersOrderId)
      list.add(vouchersOrderId);

    return list.hashCode();
  }

  @Override
  public int compareTo(CreateResultVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetResult()).compareTo(other.isSetResult());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetResult()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.result, other.result);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCutOrderId()).compareTo(other.isSetCutOrderId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCutOrderId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.cutOrderId, other.cutOrderId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetVouchersOrderId()).compareTo(other.isSetVouchersOrderId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVouchersOrderId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.vouchersOrderId, other.vouchersOrderId);
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
    StringBuilder sb = new StringBuilder("CreateResultVo(");
    boolean first = true;

    sb.append("result:");
    if (this.result == null) {
      sb.append("null");
    } else {
      sb.append(this.result);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("cutOrderId:");
    if (this.cutOrderId == null) {
      sb.append("null");
    } else {
      sb.append(this.cutOrderId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("vouchersOrderId:");
    if (this.vouchersOrderId == null) {
      sb.append("null");
    } else {
      sb.append(this.vouchersOrderId);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (result != null) {
      result.validate();
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

  private static class CreateResultVoStandardSchemeFactory implements SchemeFactory {
    public CreateResultVoStandardScheme getScheme() {
      return new CreateResultVoStandardScheme();
    }
  }

  private static class CreateResultVoStandardScheme extends StandardScheme<CreateResultVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, CreateResultVo struct) throws org.apache.thrift.TException {
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
              struct.result = new com.froad.thrift.vo.ResultVo();
              struct.result.read(iprot);
              struct.setResultIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // CUT_ORDER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.cutOrderId = iprot.readString();
              struct.setCutOrderIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // VOUCHERS_ORDER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.vouchersOrderId = iprot.readString();
              struct.setVouchersOrderIdIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, CreateResultVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.result != null) {
        oprot.writeFieldBegin(RESULT_FIELD_DESC);
        struct.result.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.cutOrderId != null) {
        oprot.writeFieldBegin(CUT_ORDER_ID_FIELD_DESC);
        oprot.writeString(struct.cutOrderId);
        oprot.writeFieldEnd();
      }
      if (struct.vouchersOrderId != null) {
        oprot.writeFieldBegin(VOUCHERS_ORDER_ID_FIELD_DESC);
        oprot.writeString(struct.vouchersOrderId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CreateResultVoTupleSchemeFactory implements SchemeFactory {
    public CreateResultVoTupleScheme getScheme() {
      return new CreateResultVoTupleScheme();
    }
  }

  private static class CreateResultVoTupleScheme extends TupleScheme<CreateResultVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, CreateResultVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetResult()) {
        optionals.set(0);
      }
      if (struct.isSetCutOrderId()) {
        optionals.set(1);
      }
      if (struct.isSetVouchersOrderId()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetResult()) {
        struct.result.write(oprot);
      }
      if (struct.isSetCutOrderId()) {
        oprot.writeString(struct.cutOrderId);
      }
      if (struct.isSetVouchersOrderId()) {
        oprot.writeString(struct.vouchersOrderId);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, CreateResultVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.result = new com.froad.thrift.vo.ResultVo();
        struct.result.read(iprot);
        struct.setResultIsSet(true);
      }
      if (incoming.get(1)) {
        struct.cutOrderId = iprot.readString();
        struct.setCutOrderIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.vouchersOrderId = iprot.readString();
        struct.setVouchersOrderIdIsSet(true);
      }
    }
  }

}

