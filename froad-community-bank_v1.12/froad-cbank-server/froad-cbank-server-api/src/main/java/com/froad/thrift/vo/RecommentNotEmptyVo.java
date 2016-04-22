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
 * 回复是否为空
 * 
 * 查询使用
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class RecommentNotEmptyVo implements org.apache.thrift.TBase<RecommentNotEmptyVo, RecommentNotEmptyVo._Fields>, java.io.Serializable, Cloneable, Comparable<RecommentNotEmptyVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("RecommentNotEmptyVo");

  private static final org.apache.thrift.protocol.TField NOT_EMPTY_FIELD_DESC = new org.apache.thrift.protocol.TField("notEmpty", org.apache.thrift.protocol.TType.BOOL, (short)1);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new RecommentNotEmptyVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new RecommentNotEmptyVoTupleSchemeFactory());
  }

  /**
   * 是否空
   */
  public boolean notEmpty; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 是否空
     */
    NOT_EMPTY((short)1, "notEmpty");

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
        case 1: // NOT_EMPTY
          return NOT_EMPTY;
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
  private static final int __NOTEMPTY_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.NOT_EMPTY, new org.apache.thrift.meta_data.FieldMetaData("notEmpty", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(RecommentNotEmptyVo.class, metaDataMap);
  }

  public RecommentNotEmptyVo() {
  }

  public RecommentNotEmptyVo(
    boolean notEmpty)
  {
    this();
    this.notEmpty = notEmpty;
    setNotEmptyIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public RecommentNotEmptyVo(RecommentNotEmptyVo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.notEmpty = other.notEmpty;
  }

  public RecommentNotEmptyVo deepCopy() {
    return new RecommentNotEmptyVo(this);
  }

  @Override
  public void clear() {
    setNotEmptyIsSet(false);
    this.notEmpty = false;
  }

  /**
   * 是否空
   */
  public boolean isNotEmpty() {
    return this.notEmpty;
  }

  /**
   * 是否空
   */
  public RecommentNotEmptyVo setNotEmpty(boolean notEmpty) {
    this.notEmpty = notEmpty;
    setNotEmptyIsSet(true);
    return this;
  }

  public void unsetNotEmpty() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __NOTEMPTY_ISSET_ID);
  }

  /** Returns true if field notEmpty is set (has been assigned a value) and false otherwise */
  public boolean isSetNotEmpty() {
    return EncodingUtils.testBit(__isset_bitfield, __NOTEMPTY_ISSET_ID);
  }

  public void setNotEmptyIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __NOTEMPTY_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case NOT_EMPTY:
      if (value == null) {
        unsetNotEmpty();
      } else {
        setNotEmpty((Boolean)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case NOT_EMPTY:
      return Boolean.valueOf(isNotEmpty());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case NOT_EMPTY:
      return isSetNotEmpty();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof RecommentNotEmptyVo)
      return this.equals((RecommentNotEmptyVo)that);
    return false;
  }

  public boolean equals(RecommentNotEmptyVo that) {
    if (that == null)
      return false;

    boolean this_present_notEmpty = true;
    boolean that_present_notEmpty = true;
    if (this_present_notEmpty || that_present_notEmpty) {
      if (!(this_present_notEmpty && that_present_notEmpty))
        return false;
      if (this.notEmpty != that.notEmpty)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_notEmpty = true;
    list.add(present_notEmpty);
    if (present_notEmpty)
      list.add(notEmpty);

    return list.hashCode();
  }

  @Override
  public int compareTo(RecommentNotEmptyVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetNotEmpty()).compareTo(other.isSetNotEmpty());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNotEmpty()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.notEmpty, other.notEmpty);
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
    StringBuilder sb = new StringBuilder("RecommentNotEmptyVo(");
    boolean first = true;

    sb.append("notEmpty:");
    sb.append(this.notEmpty);
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

  private static class RecommentNotEmptyVoStandardSchemeFactory implements SchemeFactory {
    public RecommentNotEmptyVoStandardScheme getScheme() {
      return new RecommentNotEmptyVoStandardScheme();
    }
  }

  private static class RecommentNotEmptyVoStandardScheme extends StandardScheme<RecommentNotEmptyVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, RecommentNotEmptyVo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // NOT_EMPTY
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.notEmpty = iprot.readBool();
              struct.setNotEmptyIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, RecommentNotEmptyVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(NOT_EMPTY_FIELD_DESC);
      oprot.writeBool(struct.notEmpty);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class RecommentNotEmptyVoTupleSchemeFactory implements SchemeFactory {
    public RecommentNotEmptyVoTupleScheme getScheme() {
      return new RecommentNotEmptyVoTupleScheme();
    }
  }

  private static class RecommentNotEmptyVoTupleScheme extends TupleScheme<RecommentNotEmptyVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, RecommentNotEmptyVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetNotEmpty()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetNotEmpty()) {
        oprot.writeBool(struct.notEmpty);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, RecommentNotEmptyVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.notEmpty = iprot.readBool();
        struct.setNotEmptyIsSet(true);
      }
    }
  }

}
