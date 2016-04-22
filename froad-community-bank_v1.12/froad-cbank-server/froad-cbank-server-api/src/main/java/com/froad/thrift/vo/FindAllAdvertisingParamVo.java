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
 * 页面优化查询 Advertising 列表的参数
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class FindAllAdvertisingParamVo implements org.apache.thrift.TBase<FindAllAdvertisingParamVo, FindAllAdvertisingParamVo._Fields>, java.io.Serializable, Cloneable, Comparable<FindAllAdvertisingParamVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("FindAllAdvertisingParamVo");

  private static final org.apache.thrift.protocol.TField TERMINAL_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("terminalType", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField POSITION_PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("positionPage", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField CLIENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("clientId", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField PARAM_ONE_VALUE_FIELD_DESC = new org.apache.thrift.protocol.TField("paramOneValue", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField PARAM_TWO_VALUE_FIELD_DESC = new org.apache.thrift.protocol.TField("paramTwoValue", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField PARAM_THREE_VALUE_FIELD_DESC = new org.apache.thrift.protocol.TField("paramThreeValue", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField AD_LOCATION_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("adLocationId", org.apache.thrift.protocol.TType.I64, (short)7);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new FindAllAdvertisingParamVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new FindAllAdvertisingParamVoTupleSchemeFactory());
  }

  /**
   * 终端类型
   */
  public String terminalType; // optional
  /**
   * 页面标识
   */
  public String positionPage; // optional
  /**
   * 客户端ID
   */
  public String clientId; // optional
  /**
   * 第一参数-内容
   */
  public String paramOneValue; // optional
  /**
   * 第二参数-内容
   */
  public String paramTwoValue; // optional
  /**
   * 第三参数-内容
   */
  public String paramThreeValue; // optional
  /**
   * 广告位ID
   */
  public long adLocationId; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 终端类型
     */
    TERMINAL_TYPE((short)1, "terminalType"),
    /**
     * 页面标识
     */
    POSITION_PAGE((short)2, "positionPage"),
    /**
     * 客户端ID
     */
    CLIENT_ID((short)3, "clientId"),
    /**
     * 第一参数-内容
     */
    PARAM_ONE_VALUE((short)4, "paramOneValue"),
    /**
     * 第二参数-内容
     */
    PARAM_TWO_VALUE((short)5, "paramTwoValue"),
    /**
     * 第三参数-内容
     */
    PARAM_THREE_VALUE((short)6, "paramThreeValue"),
    /**
     * 广告位ID
     */
    AD_LOCATION_ID((short)7, "adLocationId");

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
        case 1: // TERMINAL_TYPE
          return TERMINAL_TYPE;
        case 2: // POSITION_PAGE
          return POSITION_PAGE;
        case 3: // CLIENT_ID
          return CLIENT_ID;
        case 4: // PARAM_ONE_VALUE
          return PARAM_ONE_VALUE;
        case 5: // PARAM_TWO_VALUE
          return PARAM_TWO_VALUE;
        case 6: // PARAM_THREE_VALUE
          return PARAM_THREE_VALUE;
        case 7: // AD_LOCATION_ID
          return AD_LOCATION_ID;
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
  private static final int __ADLOCATIONID_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.TERMINAL_TYPE,_Fields.POSITION_PAGE,_Fields.CLIENT_ID,_Fields.PARAM_ONE_VALUE,_Fields.PARAM_TWO_VALUE,_Fields.PARAM_THREE_VALUE,_Fields.AD_LOCATION_ID};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TERMINAL_TYPE, new org.apache.thrift.meta_data.FieldMetaData("terminalType", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.POSITION_PAGE, new org.apache.thrift.meta_data.FieldMetaData("positionPage", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CLIENT_ID, new org.apache.thrift.meta_data.FieldMetaData("clientId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PARAM_ONE_VALUE, new org.apache.thrift.meta_data.FieldMetaData("paramOneValue", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PARAM_TWO_VALUE, new org.apache.thrift.meta_data.FieldMetaData("paramTwoValue", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PARAM_THREE_VALUE, new org.apache.thrift.meta_data.FieldMetaData("paramThreeValue", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.AD_LOCATION_ID, new org.apache.thrift.meta_data.FieldMetaData("adLocationId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(FindAllAdvertisingParamVo.class, metaDataMap);
  }

  public FindAllAdvertisingParamVo() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public FindAllAdvertisingParamVo(FindAllAdvertisingParamVo other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetTerminalType()) {
      this.terminalType = other.terminalType;
    }
    if (other.isSetPositionPage()) {
      this.positionPage = other.positionPage;
    }
    if (other.isSetClientId()) {
      this.clientId = other.clientId;
    }
    if (other.isSetParamOneValue()) {
      this.paramOneValue = other.paramOneValue;
    }
    if (other.isSetParamTwoValue()) {
      this.paramTwoValue = other.paramTwoValue;
    }
    if (other.isSetParamThreeValue()) {
      this.paramThreeValue = other.paramThreeValue;
    }
    this.adLocationId = other.adLocationId;
  }

  public FindAllAdvertisingParamVo deepCopy() {
    return new FindAllAdvertisingParamVo(this);
  }

  @Override
  public void clear() {
    this.terminalType = null;
    this.positionPage = null;
    this.clientId = null;
    this.paramOneValue = null;
    this.paramTwoValue = null;
    this.paramThreeValue = null;
    setAdLocationIdIsSet(false);
    this.adLocationId = 0;
  }

  /**
   * 终端类型
   */
  public String getTerminalType() {
    return this.terminalType;
  }

  /**
   * 终端类型
   */
  public FindAllAdvertisingParamVo setTerminalType(String terminalType) {
    this.terminalType = terminalType;
    return this;
  }

  public void unsetTerminalType() {
    this.terminalType = null;
  }

  /** Returns true if field terminalType is set (has been assigned a value) and false otherwise */
  public boolean isSetTerminalType() {
    return this.terminalType != null;
  }

  public void setTerminalTypeIsSet(boolean value) {
    if (!value) {
      this.terminalType = null;
    }
  }

  /**
   * 页面标识
   */
  public String getPositionPage() {
    return this.positionPage;
  }

  /**
   * 页面标识
   */
  public FindAllAdvertisingParamVo setPositionPage(String positionPage) {
    this.positionPage = positionPage;
    return this;
  }

  public void unsetPositionPage() {
    this.positionPage = null;
  }

  /** Returns true if field positionPage is set (has been assigned a value) and false otherwise */
  public boolean isSetPositionPage() {
    return this.positionPage != null;
  }

  public void setPositionPageIsSet(boolean value) {
    if (!value) {
      this.positionPage = null;
    }
  }

  /**
   * 客户端ID
   */
  public String getClientId() {
    return this.clientId;
  }

  /**
   * 客户端ID
   */
  public FindAllAdvertisingParamVo setClientId(String clientId) {
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
   * 第一参数-内容
   */
  public String getParamOneValue() {
    return this.paramOneValue;
  }

  /**
   * 第一参数-内容
   */
  public FindAllAdvertisingParamVo setParamOneValue(String paramOneValue) {
    this.paramOneValue = paramOneValue;
    return this;
  }

  public void unsetParamOneValue() {
    this.paramOneValue = null;
  }

  /** Returns true if field paramOneValue is set (has been assigned a value) and false otherwise */
  public boolean isSetParamOneValue() {
    return this.paramOneValue != null;
  }

  public void setParamOneValueIsSet(boolean value) {
    if (!value) {
      this.paramOneValue = null;
    }
  }

  /**
   * 第二参数-内容
   */
  public String getParamTwoValue() {
    return this.paramTwoValue;
  }

  /**
   * 第二参数-内容
   */
  public FindAllAdvertisingParamVo setParamTwoValue(String paramTwoValue) {
    this.paramTwoValue = paramTwoValue;
    return this;
  }

  public void unsetParamTwoValue() {
    this.paramTwoValue = null;
  }

  /** Returns true if field paramTwoValue is set (has been assigned a value) and false otherwise */
  public boolean isSetParamTwoValue() {
    return this.paramTwoValue != null;
  }

  public void setParamTwoValueIsSet(boolean value) {
    if (!value) {
      this.paramTwoValue = null;
    }
  }

  /**
   * 第三参数-内容
   */
  public String getParamThreeValue() {
    return this.paramThreeValue;
  }

  /**
   * 第三参数-内容
   */
  public FindAllAdvertisingParamVo setParamThreeValue(String paramThreeValue) {
    this.paramThreeValue = paramThreeValue;
    return this;
  }

  public void unsetParamThreeValue() {
    this.paramThreeValue = null;
  }

  /** Returns true if field paramThreeValue is set (has been assigned a value) and false otherwise */
  public boolean isSetParamThreeValue() {
    return this.paramThreeValue != null;
  }

  public void setParamThreeValueIsSet(boolean value) {
    if (!value) {
      this.paramThreeValue = null;
    }
  }

  /**
   * 广告位ID
   */
  public long getAdLocationId() {
    return this.adLocationId;
  }

  /**
   * 广告位ID
   */
  public FindAllAdvertisingParamVo setAdLocationId(long adLocationId) {
    this.adLocationId = adLocationId;
    setAdLocationIdIsSet(true);
    return this;
  }

  public void unsetAdLocationId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ADLOCATIONID_ISSET_ID);
  }

  /** Returns true if field adLocationId is set (has been assigned a value) and false otherwise */
  public boolean isSetAdLocationId() {
    return EncodingUtils.testBit(__isset_bitfield, __ADLOCATIONID_ISSET_ID);
  }

  public void setAdLocationIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ADLOCATIONID_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TERMINAL_TYPE:
      if (value == null) {
        unsetTerminalType();
      } else {
        setTerminalType((String)value);
      }
      break;

    case POSITION_PAGE:
      if (value == null) {
        unsetPositionPage();
      } else {
        setPositionPage((String)value);
      }
      break;

    case CLIENT_ID:
      if (value == null) {
        unsetClientId();
      } else {
        setClientId((String)value);
      }
      break;

    case PARAM_ONE_VALUE:
      if (value == null) {
        unsetParamOneValue();
      } else {
        setParamOneValue((String)value);
      }
      break;

    case PARAM_TWO_VALUE:
      if (value == null) {
        unsetParamTwoValue();
      } else {
        setParamTwoValue((String)value);
      }
      break;

    case PARAM_THREE_VALUE:
      if (value == null) {
        unsetParamThreeValue();
      } else {
        setParamThreeValue((String)value);
      }
      break;

    case AD_LOCATION_ID:
      if (value == null) {
        unsetAdLocationId();
      } else {
        setAdLocationId((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TERMINAL_TYPE:
      return getTerminalType();

    case POSITION_PAGE:
      return getPositionPage();

    case CLIENT_ID:
      return getClientId();

    case PARAM_ONE_VALUE:
      return getParamOneValue();

    case PARAM_TWO_VALUE:
      return getParamTwoValue();

    case PARAM_THREE_VALUE:
      return getParamThreeValue();

    case AD_LOCATION_ID:
      return Long.valueOf(getAdLocationId());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case TERMINAL_TYPE:
      return isSetTerminalType();
    case POSITION_PAGE:
      return isSetPositionPage();
    case CLIENT_ID:
      return isSetClientId();
    case PARAM_ONE_VALUE:
      return isSetParamOneValue();
    case PARAM_TWO_VALUE:
      return isSetParamTwoValue();
    case PARAM_THREE_VALUE:
      return isSetParamThreeValue();
    case AD_LOCATION_ID:
      return isSetAdLocationId();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof FindAllAdvertisingParamVo)
      return this.equals((FindAllAdvertisingParamVo)that);
    return false;
  }

  public boolean equals(FindAllAdvertisingParamVo that) {
    if (that == null)
      return false;

    boolean this_present_terminalType = true && this.isSetTerminalType();
    boolean that_present_terminalType = true && that.isSetTerminalType();
    if (this_present_terminalType || that_present_terminalType) {
      if (!(this_present_terminalType && that_present_terminalType))
        return false;
      if (!this.terminalType.equals(that.terminalType))
        return false;
    }

    boolean this_present_positionPage = true && this.isSetPositionPage();
    boolean that_present_positionPage = true && that.isSetPositionPage();
    if (this_present_positionPage || that_present_positionPage) {
      if (!(this_present_positionPage && that_present_positionPage))
        return false;
      if (!this.positionPage.equals(that.positionPage))
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

    boolean this_present_paramOneValue = true && this.isSetParamOneValue();
    boolean that_present_paramOneValue = true && that.isSetParamOneValue();
    if (this_present_paramOneValue || that_present_paramOneValue) {
      if (!(this_present_paramOneValue && that_present_paramOneValue))
        return false;
      if (!this.paramOneValue.equals(that.paramOneValue))
        return false;
    }

    boolean this_present_paramTwoValue = true && this.isSetParamTwoValue();
    boolean that_present_paramTwoValue = true && that.isSetParamTwoValue();
    if (this_present_paramTwoValue || that_present_paramTwoValue) {
      if (!(this_present_paramTwoValue && that_present_paramTwoValue))
        return false;
      if (!this.paramTwoValue.equals(that.paramTwoValue))
        return false;
    }

    boolean this_present_paramThreeValue = true && this.isSetParamThreeValue();
    boolean that_present_paramThreeValue = true && that.isSetParamThreeValue();
    if (this_present_paramThreeValue || that_present_paramThreeValue) {
      if (!(this_present_paramThreeValue && that_present_paramThreeValue))
        return false;
      if (!this.paramThreeValue.equals(that.paramThreeValue))
        return false;
    }

    boolean this_present_adLocationId = true && this.isSetAdLocationId();
    boolean that_present_adLocationId = true && that.isSetAdLocationId();
    if (this_present_adLocationId || that_present_adLocationId) {
      if (!(this_present_adLocationId && that_present_adLocationId))
        return false;
      if (this.adLocationId != that.adLocationId)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_terminalType = true && (isSetTerminalType());
    list.add(present_terminalType);
    if (present_terminalType)
      list.add(terminalType);

    boolean present_positionPage = true && (isSetPositionPage());
    list.add(present_positionPage);
    if (present_positionPage)
      list.add(positionPage);

    boolean present_clientId = true && (isSetClientId());
    list.add(present_clientId);
    if (present_clientId)
      list.add(clientId);

    boolean present_paramOneValue = true && (isSetParamOneValue());
    list.add(present_paramOneValue);
    if (present_paramOneValue)
      list.add(paramOneValue);

    boolean present_paramTwoValue = true && (isSetParamTwoValue());
    list.add(present_paramTwoValue);
    if (present_paramTwoValue)
      list.add(paramTwoValue);

    boolean present_paramThreeValue = true && (isSetParamThreeValue());
    list.add(present_paramThreeValue);
    if (present_paramThreeValue)
      list.add(paramThreeValue);

    boolean present_adLocationId = true && (isSetAdLocationId());
    list.add(present_adLocationId);
    if (present_adLocationId)
      list.add(adLocationId);

    return list.hashCode();
  }

  @Override
  public int compareTo(FindAllAdvertisingParamVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetTerminalType()).compareTo(other.isSetTerminalType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTerminalType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.terminalType, other.terminalType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPositionPage()).compareTo(other.isSetPositionPage());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPositionPage()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.positionPage, other.positionPage);
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
    lastComparison = Boolean.valueOf(isSetParamOneValue()).compareTo(other.isSetParamOneValue());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetParamOneValue()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.paramOneValue, other.paramOneValue);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetParamTwoValue()).compareTo(other.isSetParamTwoValue());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetParamTwoValue()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.paramTwoValue, other.paramTwoValue);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetParamThreeValue()).compareTo(other.isSetParamThreeValue());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetParamThreeValue()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.paramThreeValue, other.paramThreeValue);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAdLocationId()).compareTo(other.isSetAdLocationId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAdLocationId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.adLocationId, other.adLocationId);
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
    StringBuilder sb = new StringBuilder("FindAllAdvertisingParamVo(");
    boolean first = true;

    if (isSetTerminalType()) {
      sb.append("terminalType:");
      if (this.terminalType == null) {
        sb.append("null");
      } else {
        sb.append(this.terminalType);
      }
      first = false;
    }
    if (isSetPositionPage()) {
      if (!first) sb.append(", ");
      sb.append("positionPage:");
      if (this.positionPage == null) {
        sb.append("null");
      } else {
        sb.append(this.positionPage);
      }
      first = false;
    }
    if (isSetClientId()) {
      if (!first) sb.append(", ");
      sb.append("clientId:");
      if (this.clientId == null) {
        sb.append("null");
      } else {
        sb.append(this.clientId);
      }
      first = false;
    }
    if (isSetParamOneValue()) {
      if (!first) sb.append(", ");
      sb.append("paramOneValue:");
      if (this.paramOneValue == null) {
        sb.append("null");
      } else {
        sb.append(this.paramOneValue);
      }
      first = false;
    }
    if (isSetParamTwoValue()) {
      if (!first) sb.append(", ");
      sb.append("paramTwoValue:");
      if (this.paramTwoValue == null) {
        sb.append("null");
      } else {
        sb.append(this.paramTwoValue);
      }
      first = false;
    }
    if (isSetParamThreeValue()) {
      if (!first) sb.append(", ");
      sb.append("paramThreeValue:");
      if (this.paramThreeValue == null) {
        sb.append("null");
      } else {
        sb.append(this.paramThreeValue);
      }
      first = false;
    }
    if (isSetAdLocationId()) {
      if (!first) sb.append(", ");
      sb.append("adLocationId:");
      sb.append(this.adLocationId);
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

  private static class FindAllAdvertisingParamVoStandardSchemeFactory implements SchemeFactory {
    public FindAllAdvertisingParamVoStandardScheme getScheme() {
      return new FindAllAdvertisingParamVoStandardScheme();
    }
  }

  private static class FindAllAdvertisingParamVoStandardScheme extends StandardScheme<FindAllAdvertisingParamVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, FindAllAdvertisingParamVo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TERMINAL_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.terminalType = iprot.readString();
              struct.setTerminalTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // POSITION_PAGE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.positionPage = iprot.readString();
              struct.setPositionPageIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // CLIENT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.clientId = iprot.readString();
              struct.setClientIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // PARAM_ONE_VALUE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.paramOneValue = iprot.readString();
              struct.setParamOneValueIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // PARAM_TWO_VALUE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.paramTwoValue = iprot.readString();
              struct.setParamTwoValueIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // PARAM_THREE_VALUE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.paramThreeValue = iprot.readString();
              struct.setParamThreeValueIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // AD_LOCATION_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.adLocationId = iprot.readI64();
              struct.setAdLocationIdIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, FindAllAdvertisingParamVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.terminalType != null) {
        if (struct.isSetTerminalType()) {
          oprot.writeFieldBegin(TERMINAL_TYPE_FIELD_DESC);
          oprot.writeString(struct.terminalType);
          oprot.writeFieldEnd();
        }
      }
      if (struct.positionPage != null) {
        if (struct.isSetPositionPage()) {
          oprot.writeFieldBegin(POSITION_PAGE_FIELD_DESC);
          oprot.writeString(struct.positionPage);
          oprot.writeFieldEnd();
        }
      }
      if (struct.clientId != null) {
        if (struct.isSetClientId()) {
          oprot.writeFieldBegin(CLIENT_ID_FIELD_DESC);
          oprot.writeString(struct.clientId);
          oprot.writeFieldEnd();
        }
      }
      if (struct.paramOneValue != null) {
        if (struct.isSetParamOneValue()) {
          oprot.writeFieldBegin(PARAM_ONE_VALUE_FIELD_DESC);
          oprot.writeString(struct.paramOneValue);
          oprot.writeFieldEnd();
        }
      }
      if (struct.paramTwoValue != null) {
        if (struct.isSetParamTwoValue()) {
          oprot.writeFieldBegin(PARAM_TWO_VALUE_FIELD_DESC);
          oprot.writeString(struct.paramTwoValue);
          oprot.writeFieldEnd();
        }
      }
      if (struct.paramThreeValue != null) {
        if (struct.isSetParamThreeValue()) {
          oprot.writeFieldBegin(PARAM_THREE_VALUE_FIELD_DESC);
          oprot.writeString(struct.paramThreeValue);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetAdLocationId()) {
        oprot.writeFieldBegin(AD_LOCATION_ID_FIELD_DESC);
        oprot.writeI64(struct.adLocationId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class FindAllAdvertisingParamVoTupleSchemeFactory implements SchemeFactory {
    public FindAllAdvertisingParamVoTupleScheme getScheme() {
      return new FindAllAdvertisingParamVoTupleScheme();
    }
  }

  private static class FindAllAdvertisingParamVoTupleScheme extends TupleScheme<FindAllAdvertisingParamVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, FindAllAdvertisingParamVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetTerminalType()) {
        optionals.set(0);
      }
      if (struct.isSetPositionPage()) {
        optionals.set(1);
      }
      if (struct.isSetClientId()) {
        optionals.set(2);
      }
      if (struct.isSetParamOneValue()) {
        optionals.set(3);
      }
      if (struct.isSetParamTwoValue()) {
        optionals.set(4);
      }
      if (struct.isSetParamThreeValue()) {
        optionals.set(5);
      }
      if (struct.isSetAdLocationId()) {
        optionals.set(6);
      }
      oprot.writeBitSet(optionals, 7);
      if (struct.isSetTerminalType()) {
        oprot.writeString(struct.terminalType);
      }
      if (struct.isSetPositionPage()) {
        oprot.writeString(struct.positionPage);
      }
      if (struct.isSetClientId()) {
        oprot.writeString(struct.clientId);
      }
      if (struct.isSetParamOneValue()) {
        oprot.writeString(struct.paramOneValue);
      }
      if (struct.isSetParamTwoValue()) {
        oprot.writeString(struct.paramTwoValue);
      }
      if (struct.isSetParamThreeValue()) {
        oprot.writeString(struct.paramThreeValue);
      }
      if (struct.isSetAdLocationId()) {
        oprot.writeI64(struct.adLocationId);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, FindAllAdvertisingParamVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(7);
      if (incoming.get(0)) {
        struct.terminalType = iprot.readString();
        struct.setTerminalTypeIsSet(true);
      }
      if (incoming.get(1)) {
        struct.positionPage = iprot.readString();
        struct.setPositionPageIsSet(true);
      }
      if (incoming.get(2)) {
        struct.clientId = iprot.readString();
        struct.setClientIdIsSet(true);
      }
      if (incoming.get(3)) {
        struct.paramOneValue = iprot.readString();
        struct.setParamOneValueIsSet(true);
      }
      if (incoming.get(4)) {
        struct.paramTwoValue = iprot.readString();
        struct.setParamTwoValueIsSet(true);
      }
      if (incoming.get(5)) {
        struct.paramThreeValue = iprot.readString();
        struct.setParamThreeValueIsSet(true);
      }
      if (incoming.get(6)) {
        struct.adLocationId = iprot.readI64();
        struct.setAdLocationIdIsSet(true);
      }
    }
  }

}

