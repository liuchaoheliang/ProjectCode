/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.froad.thrift.vo.coremodule;

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
 * 任务列表请求信息
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class DefineTaskReqVo implements org.apache.thrift.TBase<DefineTaskReqVo, DefineTaskReqVo._Fields>, java.io.Serializable, Cloneable, Comparable<DefineTaskReqVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("DefineTaskReqVo");

  private static final org.apache.thrift.protocol.TField CLIENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("clientId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField LOGIN_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("loginId", org.apache.thrift.protocol.TType.I64, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new DefineTaskReqVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new DefineTaskReqVoTupleSchemeFactory());
  }

  /**
   * 银行*
   */
  public String clientId; // optional
  /**
   * 登录人*
   */
  public long loginId; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 银行*
     */
    CLIENT_ID((short)1, "clientId"),
    /**
     * 登录人*
     */
    LOGIN_ID((short)2, "loginId");

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
        case 1: // CLIENT_ID
          return CLIENT_ID;
        case 2: // LOGIN_ID
          return LOGIN_ID;
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
  private static final int __LOGINID_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.CLIENT_ID,_Fields.LOGIN_ID};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.CLIENT_ID, new org.apache.thrift.meta_data.FieldMetaData("clientId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.LOGIN_ID, new org.apache.thrift.meta_data.FieldMetaData("loginId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(DefineTaskReqVo.class, metaDataMap);
  }

  public DefineTaskReqVo() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public DefineTaskReqVo(DefineTaskReqVo other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetClientId()) {
      this.clientId = other.clientId;
    }
    this.loginId = other.loginId;
  }

  public DefineTaskReqVo deepCopy() {
    return new DefineTaskReqVo(this);
  }

  @Override
  public void clear() {
    this.clientId = null;
    setLoginIdIsSet(false);
    this.loginId = 0;
  }

  /**
   * 银行*
   */
  public String getClientId() {
    return this.clientId;
  }

  /**
   * 银行*
   */
  public DefineTaskReqVo setClientId(String clientId) {
    this.clientId = clientId;
    return this;
  }

  public void unsetClientId() {
    this.clientId = null;
  }

  /** Returns true if field clientId is set (has been assigned a value) and false otherwise */
  public boolean isSetClientId() {
    return this.clientId != null;
  }

  public void setClientIdIsSet(boolean value) {
    if (!value) {
      this.clientId = null;
    }
  }

  /**
   * 登录人*
   */
  public long getLoginId() {
    return this.loginId;
  }

  /**
   * 登录人*
   */
  public DefineTaskReqVo setLoginId(long loginId) {
    this.loginId = loginId;
    setLoginIdIsSet(true);
    return this;
  }

  public void unsetLoginId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __LOGINID_ISSET_ID);
  }

  /** Returns true if field loginId is set (has been assigned a value) and false otherwise */
  public boolean isSetLoginId() {
    return EncodingUtils.testBit(__isset_bitfield, __LOGINID_ISSET_ID);
  }

  public void setLoginIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __LOGINID_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case CLIENT_ID:
      if (value == null) {
        unsetClientId();
      } else {
        setClientId((String)value);
      }
      break;

    case LOGIN_ID:
      if (value == null) {
        unsetLoginId();
      } else {
        setLoginId((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case CLIENT_ID:
      return getClientId();

    case LOGIN_ID:
      return Long.valueOf(getLoginId());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case CLIENT_ID:
      return isSetClientId();
    case LOGIN_ID:
      return isSetLoginId();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof DefineTaskReqVo)
      return this.equals((DefineTaskReqVo)that);
    return false;
  }

  public boolean equals(DefineTaskReqVo that) {
    if (that == null)
      return false;

    boolean this_present_clientId = true && this.isSetClientId();
    boolean that_present_clientId = true && that.isSetClientId();
    if (this_present_clientId || that_present_clientId) {
      if (!(this_present_clientId && that_present_clientId))
        return false;
      if (!this.clientId.equals(that.clientId))
        return false;
    }

    boolean this_present_loginId = true && this.isSetLoginId();
    boolean that_present_loginId = true && that.isSetLoginId();
    if (this_present_loginId || that_present_loginId) {
      if (!(this_present_loginId && that_present_loginId))
        return false;
      if (this.loginId != that.loginId)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_clientId = true && (isSetClientId());
    list.add(present_clientId);
    if (present_clientId)
      list.add(clientId);

    boolean present_loginId = true && (isSetLoginId());
    list.add(present_loginId);
    if (present_loginId)
      list.add(loginId);

    return list.hashCode();
  }

  @Override
  public int compareTo(DefineTaskReqVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetClientId()).compareTo(other.isSetClientId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetClientId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.clientId, other.clientId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLoginId()).compareTo(other.isSetLoginId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLoginId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.loginId, other.loginId);
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
    StringBuilder sb = new StringBuilder("DefineTaskReqVo(");
    boolean first = true;

    if (isSetClientId()) {
      sb.append("clientId:");
      if (this.clientId == null) {
        sb.append("null");
      } else {
        sb.append(this.clientId);
      }
      first = false;
    }
    if (isSetLoginId()) {
      if (!first) sb.append(", ");
      sb.append("loginId:");
      sb.append(this.loginId);
      first = false;
    }
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

  private static class DefineTaskReqVoStandardSchemeFactory implements SchemeFactory {
    public DefineTaskReqVoStandardScheme getScheme() {
      return new DefineTaskReqVoStandardScheme();
    }
  }

  private static class DefineTaskReqVoStandardScheme extends StandardScheme<DefineTaskReqVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, DefineTaskReqVo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // CLIENT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.clientId = iprot.readString();
              struct.setClientIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // LOGIN_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.loginId = iprot.readI64();
              struct.setLoginIdIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, DefineTaskReqVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.clientId != null) {
        if (struct.isSetClientId()) {
          oprot.writeFieldBegin(CLIENT_ID_FIELD_DESC);
          oprot.writeString(struct.clientId);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetLoginId()) {
        oprot.writeFieldBegin(LOGIN_ID_FIELD_DESC);
        oprot.writeI64(struct.loginId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class DefineTaskReqVoTupleSchemeFactory implements SchemeFactory {
    public DefineTaskReqVoTupleScheme getScheme() {
      return new DefineTaskReqVoTupleScheme();
    }
  }

  private static class DefineTaskReqVoTupleScheme extends TupleScheme<DefineTaskReqVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, DefineTaskReqVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetClientId()) {
        optionals.set(0);
      }
      if (struct.isSetLoginId()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetClientId()) {
        oprot.writeString(struct.clientId);
      }
      if (struct.isSetLoginId()) {
        oprot.writeI64(struct.loginId);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, DefineTaskReqVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.clientId = iprot.readString();
        struct.setClientIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.loginId = iprot.readI64();
        struct.setLoginIdIsSet(true);
      }
    }
  }

}

