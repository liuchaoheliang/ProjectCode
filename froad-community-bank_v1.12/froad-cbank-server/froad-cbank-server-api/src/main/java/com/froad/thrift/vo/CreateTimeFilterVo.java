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
 * 创建时间过滤 - 查询使用
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class CreateTimeFilterVo implements org.apache.thrift.TBase<CreateTimeFilterVo, CreateTimeFilterVo._Fields>, java.io.Serializable, Cloneable, Comparable<CreateTimeFilterVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CreateTimeFilterVo");

  private static final org.apache.thrift.protocol.TField BEG_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("begTime", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField END_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("endTime", org.apache.thrift.protocol.TType.I64, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new CreateTimeFilterVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new CreateTimeFilterVoTupleSchemeFactory());
  }

  /**
   * 开始时间
   */
  public long begTime; // required
  /**
   * 接收时间
   */
  public long endTime; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 开始时间
     */
    BEG_TIME((short)1, "begTime"),
    /**
     * 接收时间
     */
    END_TIME((short)2, "endTime");

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
        case 1: // BEG_TIME
          return BEG_TIME;
        case 2: // END_TIME
          return END_TIME;
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
  private static final int __BEGTIME_ISSET_ID = 0;
  private static final int __ENDTIME_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.BEG_TIME, new org.apache.thrift.meta_data.FieldMetaData("begTime", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.END_TIME, new org.apache.thrift.meta_data.FieldMetaData("endTime", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CreateTimeFilterVo.class, metaDataMap);
  }

  public CreateTimeFilterVo() {
  }

  public CreateTimeFilterVo(
    long begTime,
    long endTime)
  {
    this();
    this.begTime = begTime;
    setBegTimeIsSet(true);
    this.endTime = endTime;
    setEndTimeIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CreateTimeFilterVo(CreateTimeFilterVo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.begTime = other.begTime;
    this.endTime = other.endTime;
  }

  public CreateTimeFilterVo deepCopy() {
    return new CreateTimeFilterVo(this);
  }

  @Override
  public void clear() {
    setBegTimeIsSet(false);
    this.begTime = 0;
    setEndTimeIsSet(false);
    this.endTime = 0;
  }

  /**
   * 开始时间
   */
  public long getBegTime() {
    return this.begTime;
  }

  /**
   * 开始时间
   */
  public CreateTimeFilterVo setBegTime(long begTime) {
    this.begTime = begTime;
    setBegTimeIsSet(true);
    return this;
  }

  public void unsetBegTime() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __BEGTIME_ISSET_ID);
  }

  /** Returns true if field begTime is set (has been assigned a value) and false otherwise */
  public boolean isSetBegTime() {
    return EncodingUtils.testBit(__isset_bitfield, __BEGTIME_ISSET_ID);
  }

  public void setBegTimeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __BEGTIME_ISSET_ID, value);
  }

  /**
   * 接收时间
   */
  public long getEndTime() {
    return this.endTime;
  }

  /**
   * 接收时间
   */
  public CreateTimeFilterVo setEndTime(long endTime) {
    this.endTime = endTime;
    setEndTimeIsSet(true);
    return this;
  }

  public void unsetEndTime() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ENDTIME_ISSET_ID);
  }

  /** Returns true if field endTime is set (has been assigned a value) and false otherwise */
  public boolean isSetEndTime() {
    return EncodingUtils.testBit(__isset_bitfield, __ENDTIME_ISSET_ID);
  }

  public void setEndTimeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ENDTIME_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case BEG_TIME:
      if (value == null) {
        unsetBegTime();
      } else {
        setBegTime((Long)value);
      }
      break;

    case END_TIME:
      if (value == null) {
        unsetEndTime();
      } else {
        setEndTime((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case BEG_TIME:
      return Long.valueOf(getBegTime());

    case END_TIME:
      return Long.valueOf(getEndTime());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case BEG_TIME:
      return isSetBegTime();
    case END_TIME:
      return isSetEndTime();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof CreateTimeFilterVo)
      return this.equals((CreateTimeFilterVo)that);
    return false;
  }

  public boolean equals(CreateTimeFilterVo that) {
    if (that == null)
      return false;

    boolean this_present_begTime = true;
    boolean that_present_begTime = true;
    if (this_present_begTime || that_present_begTime) {
      if (!(this_present_begTime && that_present_begTime))
        return false;
      if (this.begTime != that.begTime)
        return false;
    }

    boolean this_present_endTime = true;
    boolean that_present_endTime = true;
    if (this_present_endTime || that_present_endTime) {
      if (!(this_present_endTime && that_present_endTime))
        return false;
      if (this.endTime != that.endTime)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_begTime = true;
    list.add(present_begTime);
    if (present_begTime)
      list.add(begTime);

    boolean present_endTime = true;
    list.add(present_endTime);
    if (present_endTime)
      list.add(endTime);

    return list.hashCode();
  }

  @Override
  public int compareTo(CreateTimeFilterVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetBegTime()).compareTo(other.isSetBegTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBegTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.begTime, other.begTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetEndTime()).compareTo(other.isSetEndTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEndTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.endTime, other.endTime);
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
    StringBuilder sb = new StringBuilder("CreateTimeFilterVo(");
    boolean first = true;

    sb.append("begTime:");
    sb.append(this.begTime);
    first = false;
    if (!first) sb.append(", ");
    sb.append("endTime:");
    sb.append(this.endTime);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class CreateTimeFilterVoStandardSchemeFactory implements SchemeFactory {
    public CreateTimeFilterVoStandardScheme getScheme() {
      return new CreateTimeFilterVoStandardScheme();
    }
  }

  private static class CreateTimeFilterVoStandardScheme extends StandardScheme<CreateTimeFilterVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, CreateTimeFilterVo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // BEG_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.begTime = iprot.readI64();
              struct.setBegTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // END_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.endTime = iprot.readI64();
              struct.setEndTimeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, CreateTimeFilterVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(BEG_TIME_FIELD_DESC);
      oprot.writeI64(struct.begTime);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(END_TIME_FIELD_DESC);
      oprot.writeI64(struct.endTime);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CreateTimeFilterVoTupleSchemeFactory implements SchemeFactory {
    public CreateTimeFilterVoTupleScheme getScheme() {
      return new CreateTimeFilterVoTupleScheme();
    }
  }

  private static class CreateTimeFilterVoTupleScheme extends TupleScheme<CreateTimeFilterVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, CreateTimeFilterVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetBegTime()) {
        optionals.set(0);
      }
      if (struct.isSetEndTime()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetBegTime()) {
        oprot.writeI64(struct.begTime);
      }
      if (struct.isSetEndTime()) {
        oprot.writeI64(struct.endTime);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, CreateTimeFilterVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.begTime = iprot.readI64();
        struct.setBegTimeIsSet(true);
      }
      if (incoming.get(1)) {
        struct.endTime = iprot.readI64();
        struct.setEndTimeIsSet(true);
      }
    }
  }

}
