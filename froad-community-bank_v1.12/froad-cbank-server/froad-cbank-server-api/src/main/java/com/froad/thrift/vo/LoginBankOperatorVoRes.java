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
 * 登录返回对象VoRes
 * LoginBankOperatorVoRes
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class LoginBankOperatorVoRes implements org.apache.thrift.TBase<LoginBankOperatorVoRes, LoginBankOperatorVoRes._Fields>, java.io.Serializable, Cloneable, Comparable<LoginBankOperatorVoRes> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("LoginBankOperatorVoRes");

  private static final org.apache.thrift.protocol.TField RESULT_FIELD_DESC = new org.apache.thrift.protocol.TField("result", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField LOGIN_FAILURE_COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("loginFailureCount", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField TOKEN_FIELD_DESC = new org.apache.thrift.protocol.TField("token", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField BANK_OPERATOR_FIELD_DESC = new org.apache.thrift.protocol.TField("bankOperator", org.apache.thrift.protocol.TType.STRUCT, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new LoginBankOperatorVoResStandardSchemeFactory());
    schemes.put(TupleScheme.class, new LoginBankOperatorVoResTupleSchemeFactory());
  }

  /**
   * 结果集vo
   */
  public com.froad.thrift.vo.ResultVo result; // required
  /**
   * 登录失败次数
   */
  public int loginFailureCount; // required
  /**
   * token值
   */
  public String token; // required
  /**
   * 银行用户vo
   */
  public BankOperatorVo bankOperator; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 结果集vo
     */
    RESULT((short)1, "result"),
    /**
     * 登录失败次数
     */
    LOGIN_FAILURE_COUNT((short)2, "loginFailureCount"),
    /**
     * token值
     */
    TOKEN((short)3, "token"),
    /**
     * 银行用户vo
     */
    BANK_OPERATOR((short)4, "bankOperator");

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
        case 2: // LOGIN_FAILURE_COUNT
          return LOGIN_FAILURE_COUNT;
        case 3: // TOKEN
          return TOKEN;
        case 4: // BANK_OPERATOR
          return BANK_OPERATOR;
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
  private static final int __LOGINFAILURECOUNT_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.RESULT, new org.apache.thrift.meta_data.FieldMetaData("result", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.ResultVo.class)));
    tmpMap.put(_Fields.LOGIN_FAILURE_COUNT, new org.apache.thrift.meta_data.FieldMetaData("loginFailureCount", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.TOKEN, new org.apache.thrift.meta_data.FieldMetaData("token", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.BANK_OPERATOR, new org.apache.thrift.meta_data.FieldMetaData("bankOperator", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT        , "BankOperatorVo")));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(LoginBankOperatorVoRes.class, metaDataMap);
  }

  public LoginBankOperatorVoRes() {
  }

  public LoginBankOperatorVoRes(
    com.froad.thrift.vo.ResultVo result,
    int loginFailureCount,
    String token,
    BankOperatorVo bankOperator)
  {
    this();
    this.result = result;
    this.loginFailureCount = loginFailureCount;
    setLoginFailureCountIsSet(true);
    this.token = token;
    this.bankOperator = bankOperator;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public LoginBankOperatorVoRes(LoginBankOperatorVoRes other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetResult()) {
      this.result = new com.froad.thrift.vo.ResultVo(other.result);
    }
    this.loginFailureCount = other.loginFailureCount;
    if (other.isSetToken()) {
      this.token = other.token;
    }
    if (other.isSetBankOperator()) {
      this.bankOperator = other.bankOperator;
    }
  }

  public LoginBankOperatorVoRes deepCopy() {
    return new LoginBankOperatorVoRes(this);
  }

  @Override
  public void clear() {
    this.result = null;
    setLoginFailureCountIsSet(false);
    this.loginFailureCount = 0;
    this.token = null;
    this.bankOperator = null;
  }

  /**
   * 结果集vo
   */
  public com.froad.thrift.vo.ResultVo getResult() {
    return this.result;
  }

  /**
   * 结果集vo
   */
  public LoginBankOperatorVoRes setResult(com.froad.thrift.vo.ResultVo result) {
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
   * 登录失败次数
   */
  public int getLoginFailureCount() {
    return this.loginFailureCount;
  }

  /**
   * 登录失败次数
   */
  public LoginBankOperatorVoRes setLoginFailureCount(int loginFailureCount) {
    this.loginFailureCount = loginFailureCount;
    setLoginFailureCountIsSet(true);
    return this;
  }

  public void unsetLoginFailureCount() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __LOGINFAILURECOUNT_ISSET_ID);
  }

  /** Returns true if field loginFailureCount is set (has been assigned a value) and false otherwise */
  public boolean isSetLoginFailureCount() {
    return EncodingUtils.testBit(__isset_bitfield, __LOGINFAILURECOUNT_ISSET_ID);
  }

  public void setLoginFailureCountIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __LOGINFAILURECOUNT_ISSET_ID, value);
  }

  /**
   * token值
   */
  public String getToken() {
    return this.token;
  }

  /**
   * token值
   */
  public LoginBankOperatorVoRes setToken(String token) {
    this.token = token;
    return this;
  }

  public void unsetToken() {
    this.token = null;
  }

  /** Returns true if field token is set (has been assigned a value) and false otherwise */
  public boolean isSetToken() {
    return this.token != null;
  }

  public void setTokenIsSet(boolean value) {
    if (!value) {
      this.token = null;
    }
  }

  /**
   * 银行用户vo
   */
  public BankOperatorVo getBankOperator() {
    return this.bankOperator;
  }

  /**
   * 银行用户vo
   */
  public LoginBankOperatorVoRes setBankOperator(BankOperatorVo bankOperator) {
    this.bankOperator = bankOperator;
    return this;
  }

  public void unsetBankOperator() {
    this.bankOperator = null;
  }

  /** Returns true if field bankOperator is set (has been assigned a value) and false otherwise */
  public boolean isSetBankOperator() {
    return this.bankOperator != null;
  }

  public void setBankOperatorIsSet(boolean value) {
    if (!value) {
      this.bankOperator = null;
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

    case LOGIN_FAILURE_COUNT:
      if (value == null) {
        unsetLoginFailureCount();
      } else {
        setLoginFailureCount((Integer)value);
      }
      break;

    case TOKEN:
      if (value == null) {
        unsetToken();
      } else {
        setToken((String)value);
      }
      break;

    case BANK_OPERATOR:
      if (value == null) {
        unsetBankOperator();
      } else {
        setBankOperator((BankOperatorVo)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RESULT:
      return getResult();

    case LOGIN_FAILURE_COUNT:
      return Integer.valueOf(getLoginFailureCount());

    case TOKEN:
      return getToken();

    case BANK_OPERATOR:
      return getBankOperator();

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
    case LOGIN_FAILURE_COUNT:
      return isSetLoginFailureCount();
    case TOKEN:
      return isSetToken();
    case BANK_OPERATOR:
      return isSetBankOperator();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof LoginBankOperatorVoRes)
      return this.equals((LoginBankOperatorVoRes)that);
    return false;
  }

  public boolean equals(LoginBankOperatorVoRes that) {
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

    boolean this_present_loginFailureCount = true;
    boolean that_present_loginFailureCount = true;
    if (this_present_loginFailureCount || that_present_loginFailureCount) {
      if (!(this_present_loginFailureCount && that_present_loginFailureCount))
        return false;
      if (this.loginFailureCount != that.loginFailureCount)
        return false;
    }

    boolean this_present_token = true && this.isSetToken();
    boolean that_present_token = true && that.isSetToken();
    if (this_present_token || that_present_token) {
      if (!(this_present_token && that_present_token))
        return false;
      if (!this.token.equals(that.token))
        return false;
    }

    boolean this_present_bankOperator = true && this.isSetBankOperator();
    boolean that_present_bankOperator = true && that.isSetBankOperator();
    if (this_present_bankOperator || that_present_bankOperator) {
      if (!(this_present_bankOperator && that_present_bankOperator))
        return false;
      if (!this.bankOperator.equals(that.bankOperator))
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

    boolean present_loginFailureCount = true;
    list.add(present_loginFailureCount);
    if (present_loginFailureCount)
      list.add(loginFailureCount);

    boolean present_token = true && (isSetToken());
    list.add(present_token);
    if (present_token)
      list.add(token);

    boolean present_bankOperator = true && (isSetBankOperator());
    list.add(present_bankOperator);
    if (present_bankOperator)
      list.add(bankOperator);

    return list.hashCode();
  }

  @Override
  public int compareTo(LoginBankOperatorVoRes other) {
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
    lastComparison = Boolean.valueOf(isSetLoginFailureCount()).compareTo(other.isSetLoginFailureCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLoginFailureCount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.loginFailureCount, other.loginFailureCount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetToken()).compareTo(other.isSetToken());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetToken()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.token, other.token);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBankOperator()).compareTo(other.isSetBankOperator());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBankOperator()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.bankOperator, other.bankOperator);
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
    StringBuilder sb = new StringBuilder("LoginBankOperatorVoRes(");
    boolean first = true;

    sb.append("result:");
    if (this.result == null) {
      sb.append("null");
    } else {
      sb.append(this.result);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("loginFailureCount:");
    sb.append(this.loginFailureCount);
    first = false;
    if (!first) sb.append(", ");
    sb.append("token:");
    if (this.token == null) {
      sb.append("null");
    } else {
      sb.append(this.token);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("bankOperator:");
    if (this.bankOperator == null) {
      sb.append("null");
    } else {
      sb.append(this.bankOperator);
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class LoginBankOperatorVoResStandardSchemeFactory implements SchemeFactory {
    public LoginBankOperatorVoResStandardScheme getScheme() {
      return new LoginBankOperatorVoResStandardScheme();
    }
  }

  private static class LoginBankOperatorVoResStandardScheme extends StandardScheme<LoginBankOperatorVoRes> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, LoginBankOperatorVoRes struct) throws org.apache.thrift.TException {
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
          case 2: // LOGIN_FAILURE_COUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.loginFailureCount = iprot.readI32();
              struct.setLoginFailureCountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TOKEN
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.token = iprot.readString();
              struct.setTokenIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // BANK_OPERATOR
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.bankOperator = new BankOperatorVo();
              struct.bankOperator.read(iprot);
              struct.setBankOperatorIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, LoginBankOperatorVoRes struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.result != null) {
        oprot.writeFieldBegin(RESULT_FIELD_DESC);
        struct.result.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(LOGIN_FAILURE_COUNT_FIELD_DESC);
      oprot.writeI32(struct.loginFailureCount);
      oprot.writeFieldEnd();
      if (struct.token != null) {
        oprot.writeFieldBegin(TOKEN_FIELD_DESC);
        oprot.writeString(struct.token);
        oprot.writeFieldEnd();
      }
      if (struct.bankOperator != null) {
        oprot.writeFieldBegin(BANK_OPERATOR_FIELD_DESC);
        struct.bankOperator.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class LoginBankOperatorVoResTupleSchemeFactory implements SchemeFactory {
    public LoginBankOperatorVoResTupleScheme getScheme() {
      return new LoginBankOperatorVoResTupleScheme();
    }
  }

  private static class LoginBankOperatorVoResTupleScheme extends TupleScheme<LoginBankOperatorVoRes> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, LoginBankOperatorVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetResult()) {
        optionals.set(0);
      }
      if (struct.isSetLoginFailureCount()) {
        optionals.set(1);
      }
      if (struct.isSetToken()) {
        optionals.set(2);
      }
      if (struct.isSetBankOperator()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetResult()) {
        struct.result.write(oprot);
      }
      if (struct.isSetLoginFailureCount()) {
        oprot.writeI32(struct.loginFailureCount);
      }
      if (struct.isSetToken()) {
        oprot.writeString(struct.token);
      }
      if (struct.isSetBankOperator()) {
        struct.bankOperator.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, LoginBankOperatorVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.result = new com.froad.thrift.vo.ResultVo();
        struct.result.read(iprot);
        struct.setResultIsSet(true);
      }
      if (incoming.get(1)) {
        struct.loginFailureCount = iprot.readI32();
        struct.setLoginFailureCountIsSet(true);
      }
      if (incoming.get(2)) {
        struct.token = iprot.readString();
        struct.setTokenIsSet(true);
      }
      if (incoming.get(3)) {
        struct.bankOperator = new BankOperatorVo();
        struct.bankOperator.read(iprot);
        struct.setBankOperatorIsSet(true);
      }
    }
  }

}
