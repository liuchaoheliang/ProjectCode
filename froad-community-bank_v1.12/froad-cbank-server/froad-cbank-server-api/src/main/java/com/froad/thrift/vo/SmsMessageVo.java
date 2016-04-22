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
 * 短信消息
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class SmsMessageVo implements org.apache.thrift.TBase<SmsMessageVo, SmsMessageVo._Fields>, java.io.Serializable, Cloneable, Comparable<SmsMessageVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("SmsMessageVo");

  private static final org.apache.thrift.protocol.TField SMS_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("smsType", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField MOBILE_FIELD_DESC = new org.apache.thrift.protocol.TField("mobile", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField VALUES_FIELD_DESC = new org.apache.thrift.protocol.TField("values", org.apache.thrift.protocol.TType.LIST, (short)3);
  private static final org.apache.thrift.protocol.TField SEND_IP_FIELD_DESC = new org.apache.thrift.protocol.TField("sendIp", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField SEND_USER_FIELD_DESC = new org.apache.thrift.protocol.TField("sendUser", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField CLIENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("clientId", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField VALID_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("validTime", org.apache.thrift.protocol.TType.I32, (short)7);
  private static final org.apache.thrift.protocol.TField CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("code", org.apache.thrift.protocol.TType.STRING, (short)8);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new SmsMessageVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new SmsMessageVoTupleSchemeFactory());
  }

  /**
   * 短信类型(图⽚片验证码为特殊类型-1)
   */
  public int smsType; // required
  /**
   * 手机号码(图片验证码无)
   */
  public String mobile; // optional
  /**
   * 可传参数
   */
  public List<String> values; // optional
  /**
   * 发送IP
   */
  public String sendIp; // optional
  /**
   * 发送短信方 NULL表示为系统发送
   */
  public String sendUser; // optional
  /**
   * 客户端编号
   */
  public String clientId; // required
  /**
   * 有效时间
   */
  public int validTime; // optional
  /**
   * 验证码
   */
  public String code; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 短信类型(图⽚片验证码为特殊类型-1)
     */
    SMS_TYPE((short)1, "smsType"),
    /**
     * 手机号码(图片验证码无)
     */
    MOBILE((short)2, "mobile"),
    /**
     * 可传参数
     */
    VALUES((short)3, "values"),
    /**
     * 发送IP
     */
    SEND_IP((short)4, "sendIp"),
    /**
     * 发送短信方 NULL表示为系统发送
     */
    SEND_USER((short)5, "sendUser"),
    /**
     * 客户端编号
     */
    CLIENT_ID((short)6, "clientId"),
    /**
     * 有效时间
     */
    VALID_TIME((short)7, "validTime"),
    /**
     * 验证码
     */
    CODE((short)8, "code");

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
        case 1: // SMS_TYPE
          return SMS_TYPE;
        case 2: // MOBILE
          return MOBILE;
        case 3: // VALUES
          return VALUES;
        case 4: // SEND_IP
          return SEND_IP;
        case 5: // SEND_USER
          return SEND_USER;
        case 6: // CLIENT_ID
          return CLIENT_ID;
        case 7: // VALID_TIME
          return VALID_TIME;
        case 8: // CODE
          return CODE;
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
  private static final int __SMSTYPE_ISSET_ID = 0;
  private static final int __VALIDTIME_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.MOBILE,_Fields.VALUES,_Fields.SEND_IP,_Fields.SEND_USER,_Fields.VALID_TIME,_Fields.CODE};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SMS_TYPE, new org.apache.thrift.meta_data.FieldMetaData("smsType", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.MOBILE, new org.apache.thrift.meta_data.FieldMetaData("mobile", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.VALUES, new org.apache.thrift.meta_data.FieldMetaData("values", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    tmpMap.put(_Fields.SEND_IP, new org.apache.thrift.meta_data.FieldMetaData("sendIp", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SEND_USER, new org.apache.thrift.meta_data.FieldMetaData("sendUser", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CLIENT_ID, new org.apache.thrift.meta_data.FieldMetaData("clientId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.VALID_TIME, new org.apache.thrift.meta_data.FieldMetaData("validTime", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.CODE, new org.apache.thrift.meta_data.FieldMetaData("code", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(SmsMessageVo.class, metaDataMap);
  }

  public SmsMessageVo() {
  }

  public SmsMessageVo(
    int smsType,
    String clientId)
  {
    this();
    this.smsType = smsType;
    setSmsTypeIsSet(true);
    this.clientId = clientId;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public SmsMessageVo(SmsMessageVo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.smsType = other.smsType;
    if (other.isSetMobile()) {
      this.mobile = other.mobile;
    }
    if (other.isSetValues()) {
      List<String> __this__values = new ArrayList<String>(other.values);
      this.values = __this__values;
    }
    if (other.isSetSendIp()) {
      this.sendIp = other.sendIp;
    }
    if (other.isSetSendUser()) {
      this.sendUser = other.sendUser;
    }
    if (other.isSetClientId()) {
      this.clientId = other.clientId;
    }
    this.validTime = other.validTime;
    if (other.isSetCode()) {
      this.code = other.code;
    }
  }

  public SmsMessageVo deepCopy() {
    return new SmsMessageVo(this);
  }

  @Override
  public void clear() {
    setSmsTypeIsSet(false);
    this.smsType = 0;
    this.mobile = null;
    this.values = null;
    this.sendIp = null;
    this.sendUser = null;
    this.clientId = null;
    setValidTimeIsSet(false);
    this.validTime = 0;
    this.code = null;
  }

  /**
   * 短信类型(图⽚片验证码为特殊类型-1)
   */
  public int getSmsType() {
    return this.smsType;
  }

  /**
   * 短信类型(图⽚片验证码为特殊类型-1)
   */
  public SmsMessageVo setSmsType(int smsType) {
    this.smsType = smsType;
    setSmsTypeIsSet(true);
    return this;
  }

  public void unsetSmsType() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SMSTYPE_ISSET_ID);
  }

  /** Returns true if field smsType is set (has been assigned a value) and false otherwise */
  public boolean isSetSmsType() {
    return EncodingUtils.testBit(__isset_bitfield, __SMSTYPE_ISSET_ID);
  }

  public void setSmsTypeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SMSTYPE_ISSET_ID, value);
  }

  /**
   * 手机号码(图片验证码无)
   */
  public String getMobile() {
    return this.mobile;
  }

  /**
   * 手机号码(图片验证码无)
   */
  public SmsMessageVo setMobile(String mobile) {
    this.mobile = mobile;
    return this;
  }

  public void unsetMobile() {
    this.mobile = null;
  }

  /** Returns true if field mobile is set (has been assigned a value) and false otherwise */
  public boolean isSetMobile() {
    return this.mobile != null;
  }

  public void setMobileIsSet(boolean value) {
    if (!value) {
      this.mobile = null;
    }
  }

  public int getValuesSize() {
    return (this.values == null) ? 0 : this.values.size();
  }

  public java.util.Iterator<String> getValuesIterator() {
    return (this.values == null) ? null : this.values.iterator();
  }

  public void addToValues(String elem) {
    if (this.values == null) {
      this.values = new ArrayList<String>();
    }
    this.values.add(elem);
  }

  /**
   * 可传参数
   */
  public List<String> getValues() {
    return this.values;
  }

  /**
   * 可传参数
   */
  public SmsMessageVo setValues(List<String> values) {
    this.values = values;
    return this;
  }

  public void unsetValues() {
    this.values = null;
  }

  /** Returns true if field values is set (has been assigned a value) and false otherwise */
  public boolean isSetValues() {
    return this.values != null;
  }

  public void setValuesIsSet(boolean value) {
    if (!value) {
      this.values = null;
    }
  }

  /**
   * 发送IP
   */
  public String getSendIp() {
    return this.sendIp;
  }

  /**
   * 发送IP
   */
  public SmsMessageVo setSendIp(String sendIp) {
    this.sendIp = sendIp;
    return this;
  }

  public void unsetSendIp() {
    this.sendIp = null;
  }

  /** Returns true if field sendIp is set (has been assigned a value) and false otherwise */
  public boolean isSetSendIp() {
    return this.sendIp != null;
  }

  public void setSendIpIsSet(boolean value) {
    if (!value) {
      this.sendIp = null;
    }
  }

  /**
   * 发送短信方 NULL表示为系统发送
   */
  public String getSendUser() {
    return this.sendUser;
  }

  /**
   * 发送短信方 NULL表示为系统发送
   */
  public SmsMessageVo setSendUser(String sendUser) {
    this.sendUser = sendUser;
    return this;
  }

  public void unsetSendUser() {
    this.sendUser = null;
  }

  /** Returns true if field sendUser is set (has been assigned a value) and false otherwise */
  public boolean isSetSendUser() {
    return this.sendUser != null;
  }

  public void setSendUserIsSet(boolean value) {
    if (!value) {
      this.sendUser = null;
    }
  }

  /**
   * 客户端编号
   */
  public String getClientId() {
    return this.clientId;
  }

  /**
   * 客户端编号
   */
  public SmsMessageVo setClientId(String clientId) {
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
   * 有效时间
   */
  public int getValidTime() {
    return this.validTime;
  }

  /**
   * 有效时间
   */
  public SmsMessageVo setValidTime(int validTime) {
    this.validTime = validTime;
    setValidTimeIsSet(true);
    return this;
  }

  public void unsetValidTime() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __VALIDTIME_ISSET_ID);
  }

  /** Returns true if field validTime is set (has been assigned a value) and false otherwise */
  public boolean isSetValidTime() {
    return EncodingUtils.testBit(__isset_bitfield, __VALIDTIME_ISSET_ID);
  }

  public void setValidTimeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __VALIDTIME_ISSET_ID, value);
  }

  /**
   * 验证码
   */
  public String getCode() {
    return this.code;
  }

  /**
   * 验证码
   */
  public SmsMessageVo setCode(String code) {
    this.code = code;
    return this;
  }

  public void unsetCode() {
    this.code = null;
  }

  /** Returns true if field code is set (has been assigned a value) and false otherwise */
  public boolean isSetCode() {
    return this.code != null;
  }

  public void setCodeIsSet(boolean value) {
    if (!value) {
      this.code = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case SMS_TYPE:
      if (value == null) {
        unsetSmsType();
      } else {
        setSmsType((Integer)value);
      }
      break;

    case MOBILE:
      if (value == null) {
        unsetMobile();
      } else {
        setMobile((String)value);
      }
      break;

    case VALUES:
      if (value == null) {
        unsetValues();
      } else {
        setValues((List<String>)value);
      }
      break;

    case SEND_IP:
      if (value == null) {
        unsetSendIp();
      } else {
        setSendIp((String)value);
      }
      break;

    case SEND_USER:
      if (value == null) {
        unsetSendUser();
      } else {
        setSendUser((String)value);
      }
      break;

    case CLIENT_ID:
      if (value == null) {
        unsetClientId();
      } else {
        setClientId((String)value);
      }
      break;

    case VALID_TIME:
      if (value == null) {
        unsetValidTime();
      } else {
        setValidTime((Integer)value);
      }
      break;

    case CODE:
      if (value == null) {
        unsetCode();
      } else {
        setCode((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SMS_TYPE:
      return Integer.valueOf(getSmsType());

    case MOBILE:
      return getMobile();

    case VALUES:
      return getValues();

    case SEND_IP:
      return getSendIp();

    case SEND_USER:
      return getSendUser();

    case CLIENT_ID:
      return getClientId();

    case VALID_TIME:
      return Integer.valueOf(getValidTime());

    case CODE:
      return getCode();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case SMS_TYPE:
      return isSetSmsType();
    case MOBILE:
      return isSetMobile();
    case VALUES:
      return isSetValues();
    case SEND_IP:
      return isSetSendIp();
    case SEND_USER:
      return isSetSendUser();
    case CLIENT_ID:
      return isSetClientId();
    case VALID_TIME:
      return isSetValidTime();
    case CODE:
      return isSetCode();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof SmsMessageVo)
      return this.equals((SmsMessageVo)that);
    return false;
  }

  public boolean equals(SmsMessageVo that) {
    if (that == null)
      return false;

    boolean this_present_smsType = true;
    boolean that_present_smsType = true;
    if (this_present_smsType || that_present_smsType) {
      if (!(this_present_smsType && that_present_smsType))
        return false;
      if (this.smsType != that.smsType)
        return false;
    }

    boolean this_present_mobile = true && this.isSetMobile();
    boolean that_present_mobile = true && that.isSetMobile();
    if (this_present_mobile || that_present_mobile) {
      if (!(this_present_mobile && that_present_mobile))
        return false;
      if (!this.mobile.equals(that.mobile))
        return false;
    }

    boolean this_present_values = true && this.isSetValues();
    boolean that_present_values = true && that.isSetValues();
    if (this_present_values || that_present_values) {
      if (!(this_present_values && that_present_values))
        return false;
      if (!this.values.equals(that.values))
        return false;
    }

    boolean this_present_sendIp = true && this.isSetSendIp();
    boolean that_present_sendIp = true && that.isSetSendIp();
    if (this_present_sendIp || that_present_sendIp) {
      if (!(this_present_sendIp && that_present_sendIp))
        return false;
      if (!this.sendIp.equals(that.sendIp))
        return false;
    }

    boolean this_present_sendUser = true && this.isSetSendUser();
    boolean that_present_sendUser = true && that.isSetSendUser();
    if (this_present_sendUser || that_present_sendUser) {
      if (!(this_present_sendUser && that_present_sendUser))
        return false;
      if (!this.sendUser.equals(that.sendUser))
        return false;
    }

    boolean this_present_clientId = true && this.isSetClientId();
    boolean that_present_clientId = true && that.isSetClientId();
    if (this_present_clientId || that_present_clientId) {
      if (!(this_present_clientId && that_present_clientId))
        return false;
      if (!this.clientId.equals(that.clientId))
        return false;
    }

    boolean this_present_validTime = true && this.isSetValidTime();
    boolean that_present_validTime = true && that.isSetValidTime();
    if (this_present_validTime || that_present_validTime) {
      if (!(this_present_validTime && that_present_validTime))
        return false;
      if (this.validTime != that.validTime)
        return false;
    }

    boolean this_present_code = true && this.isSetCode();
    boolean that_present_code = true && that.isSetCode();
    if (this_present_code || that_present_code) {
      if (!(this_present_code && that_present_code))
        return false;
      if (!this.code.equals(that.code))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_smsType = true;
    list.add(present_smsType);
    if (present_smsType)
      list.add(smsType);

    boolean present_mobile = true && (isSetMobile());
    list.add(present_mobile);
    if (present_mobile)
      list.add(mobile);

    boolean present_values = true && (isSetValues());
    list.add(present_values);
    if (present_values)
      list.add(values);

    boolean present_sendIp = true && (isSetSendIp());
    list.add(present_sendIp);
    if (present_sendIp)
      list.add(sendIp);

    boolean present_sendUser = true && (isSetSendUser());
    list.add(present_sendUser);
    if (present_sendUser)
      list.add(sendUser);

    boolean present_clientId = true && (isSetClientId());
    list.add(present_clientId);
    if (present_clientId)
      list.add(clientId);

    boolean present_validTime = true && (isSetValidTime());
    list.add(present_validTime);
    if (present_validTime)
      list.add(validTime);

    boolean present_code = true && (isSetCode());
    list.add(present_code);
    if (present_code)
      list.add(code);

    return list.hashCode();
  }

  @Override
  public int compareTo(SmsMessageVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetSmsType()).compareTo(other.isSetSmsType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSmsType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.smsType, other.smsType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMobile()).compareTo(other.isSetMobile());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMobile()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.mobile, other.mobile);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetValues()).compareTo(other.isSetValues());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetValues()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.values, other.values);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSendIp()).compareTo(other.isSetSendIp());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSendIp()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sendIp, other.sendIp);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSendUser()).compareTo(other.isSetSendUser());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSendUser()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sendUser, other.sendUser);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
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
    lastComparison = Boolean.valueOf(isSetValidTime()).compareTo(other.isSetValidTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetValidTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.validTime, other.validTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCode()).compareTo(other.isSetCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.code, other.code);
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
    StringBuilder sb = new StringBuilder("SmsMessageVo(");
    boolean first = true;

    sb.append("smsType:");
    sb.append(this.smsType);
    first = false;
    if (isSetMobile()) {
      if (!first) sb.append(", ");
      sb.append("mobile:");
      if (this.mobile == null) {
        sb.append("null");
      } else {
        sb.append(this.mobile);
      }
      first = false;
    }
    if (isSetValues()) {
      if (!first) sb.append(", ");
      sb.append("values:");
      if (this.values == null) {
        sb.append("null");
      } else {
        sb.append(this.values);
      }
      first = false;
    }
    if (isSetSendIp()) {
      if (!first) sb.append(", ");
      sb.append("sendIp:");
      if (this.sendIp == null) {
        sb.append("null");
      } else {
        sb.append(this.sendIp);
      }
      first = false;
    }
    if (isSetSendUser()) {
      if (!first) sb.append(", ");
      sb.append("sendUser:");
      if (this.sendUser == null) {
        sb.append("null");
      } else {
        sb.append(this.sendUser);
      }
      first = false;
    }
    if (!first) sb.append(", ");
    sb.append("clientId:");
    if (this.clientId == null) {
      sb.append("null");
    } else {
      sb.append(this.clientId);
    }
    first = false;
    if (isSetValidTime()) {
      if (!first) sb.append(", ");
      sb.append("validTime:");
      sb.append(this.validTime);
      first = false;
    }
    if (isSetCode()) {
      if (!first) sb.append(", ");
      sb.append("code:");
      if (this.code == null) {
        sb.append("null");
      } else {
        sb.append(this.code);
      }
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

  private static class SmsMessageVoStandardSchemeFactory implements SchemeFactory {
    public SmsMessageVoStandardScheme getScheme() {
      return new SmsMessageVoStandardScheme();
    }
  }

  private static class SmsMessageVoStandardScheme extends StandardScheme<SmsMessageVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, SmsMessageVo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // SMS_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.smsType = iprot.readI32();
              struct.setSmsTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // MOBILE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.mobile = iprot.readString();
              struct.setMobileIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // VALUES
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.values = new ArrayList<String>(_list0.size);
                String _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = iprot.readString();
                  struct.values.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setValuesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // SEND_IP
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.sendIp = iprot.readString();
              struct.setSendIpIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // SEND_USER
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.sendUser = iprot.readString();
              struct.setSendUserIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // CLIENT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.clientId = iprot.readString();
              struct.setClientIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // VALID_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.validTime = iprot.readI32();
              struct.setValidTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.code = iprot.readString();
              struct.setCodeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, SmsMessageVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(SMS_TYPE_FIELD_DESC);
      oprot.writeI32(struct.smsType);
      oprot.writeFieldEnd();
      if (struct.mobile != null) {
        if (struct.isSetMobile()) {
          oprot.writeFieldBegin(MOBILE_FIELD_DESC);
          oprot.writeString(struct.mobile);
          oprot.writeFieldEnd();
        }
      }
      if (struct.values != null) {
        if (struct.isSetValues()) {
          oprot.writeFieldBegin(VALUES_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.values.size()));
            for (String _iter3 : struct.values)
            {
              oprot.writeString(_iter3);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      if (struct.sendIp != null) {
        if (struct.isSetSendIp()) {
          oprot.writeFieldBegin(SEND_IP_FIELD_DESC);
          oprot.writeString(struct.sendIp);
          oprot.writeFieldEnd();
        }
      }
      if (struct.sendUser != null) {
        if (struct.isSetSendUser()) {
          oprot.writeFieldBegin(SEND_USER_FIELD_DESC);
          oprot.writeString(struct.sendUser);
          oprot.writeFieldEnd();
        }
      }
      if (struct.clientId != null) {
        oprot.writeFieldBegin(CLIENT_ID_FIELD_DESC);
        oprot.writeString(struct.clientId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetValidTime()) {
        oprot.writeFieldBegin(VALID_TIME_FIELD_DESC);
        oprot.writeI32(struct.validTime);
        oprot.writeFieldEnd();
      }
      if (struct.code != null) {
        if (struct.isSetCode()) {
          oprot.writeFieldBegin(CODE_FIELD_DESC);
          oprot.writeString(struct.code);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class SmsMessageVoTupleSchemeFactory implements SchemeFactory {
    public SmsMessageVoTupleScheme getScheme() {
      return new SmsMessageVoTupleScheme();
    }
  }

  private static class SmsMessageVoTupleScheme extends TupleScheme<SmsMessageVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, SmsMessageVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetSmsType()) {
        optionals.set(0);
      }
      if (struct.isSetMobile()) {
        optionals.set(1);
      }
      if (struct.isSetValues()) {
        optionals.set(2);
      }
      if (struct.isSetSendIp()) {
        optionals.set(3);
      }
      if (struct.isSetSendUser()) {
        optionals.set(4);
      }
      if (struct.isSetClientId()) {
        optionals.set(5);
      }
      if (struct.isSetValidTime()) {
        optionals.set(6);
      }
      if (struct.isSetCode()) {
        optionals.set(7);
      }
      oprot.writeBitSet(optionals, 8);
      if (struct.isSetSmsType()) {
        oprot.writeI32(struct.smsType);
      }
      if (struct.isSetMobile()) {
        oprot.writeString(struct.mobile);
      }
      if (struct.isSetValues()) {
        {
          oprot.writeI32(struct.values.size());
          for (String _iter4 : struct.values)
          {
            oprot.writeString(_iter4);
          }
        }
      }
      if (struct.isSetSendIp()) {
        oprot.writeString(struct.sendIp);
      }
      if (struct.isSetSendUser()) {
        oprot.writeString(struct.sendUser);
      }
      if (struct.isSetClientId()) {
        oprot.writeString(struct.clientId);
      }
      if (struct.isSetValidTime()) {
        oprot.writeI32(struct.validTime);
      }
      if (struct.isSetCode()) {
        oprot.writeString(struct.code);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, SmsMessageVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(8);
      if (incoming.get(0)) {
        struct.smsType = iprot.readI32();
        struct.setSmsTypeIsSet(true);
      }
      if (incoming.get(1)) {
        struct.mobile = iprot.readString();
        struct.setMobileIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.values = new ArrayList<String>(_list5.size);
          String _elem6;
          for (int _i7 = 0; _i7 < _list5.size; ++_i7)
          {
            _elem6 = iprot.readString();
            struct.values.add(_elem6);
          }
        }
        struct.setValuesIsSet(true);
      }
      if (incoming.get(3)) {
        struct.sendIp = iprot.readString();
        struct.setSendIpIsSet(true);
      }
      if (incoming.get(4)) {
        struct.sendUser = iprot.readString();
        struct.setSendUserIsSet(true);
      }
      if (incoming.get(5)) {
        struct.clientId = iprot.readString();
        struct.setClientIdIsSet(true);
      }
      if (incoming.get(6)) {
        struct.validTime = iprot.readI32();
        struct.setValidTimeIsSet(true);
      }
      if (incoming.get(7)) {
        struct.code = iprot.readString();
        struct.setCodeIsSet(true);
      }
    }
  }

}
