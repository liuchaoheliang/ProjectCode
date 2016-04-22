/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.froad.thrift.vo.payment;

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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2015-8-5")
public class BossPaymentQueryExceptionVo implements org.apache.thrift.TBase<BossPaymentQueryExceptionVo, BossPaymentQueryExceptionVo._Fields>, java.io.Serializable, Cloneable, Comparable<BossPaymentQueryExceptionVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("BossPaymentQueryExceptionVo");

  private static final org.apache.thrift.protocol.TField PAGE_VO_FIELD_DESC = new org.apache.thrift.protocol.TField("pageVo", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField CLIENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("clientId", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField EXCEPTION_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("exceptionType", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("type", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField CREATE_TIME_START_FIELD_DESC = new org.apache.thrift.protocol.TField("createTimeStart", org.apache.thrift.protocol.TType.I64, (short)5);
  private static final org.apache.thrift.protocol.TField CREATE_TIME_END_FIELD_DESC = new org.apache.thrift.protocol.TField("createTimeEnd", org.apache.thrift.protocol.TType.I64, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new BossPaymentQueryExceptionVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new BossPaymentQueryExceptionVoTupleSchemeFactory());
  }

  public com.froad.thrift.vo.PageVo pageVo; // required
  public String clientId; // required
  /**
   * 
   * @see BossPaymentExceptionType
   */
  public BossPaymentExceptionType exceptionType; // required
  /**
   * 
   * @see BossPaymentType
   */
  public BossPaymentType type; // required
  public long createTimeStart; // required
  public long createTimeEnd; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PAGE_VO((short)1, "pageVo"),
    CLIENT_ID((short)2, "clientId"),
    /**
     * 
     * @see BossPaymentExceptionType
     */
    EXCEPTION_TYPE((short)3, "exceptionType"),
    /**
     * 
     * @see BossPaymentType
     */
    TYPE((short)4, "type"),
    CREATE_TIME_START((short)5, "createTimeStart"),
    CREATE_TIME_END((short)6, "createTimeEnd");

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
        case 1: // PAGE_VO
          return PAGE_VO;
        case 2: // CLIENT_ID
          return CLIENT_ID;
        case 3: // EXCEPTION_TYPE
          return EXCEPTION_TYPE;
        case 4: // TYPE
          return TYPE;
        case 5: // CREATE_TIME_START
          return CREATE_TIME_START;
        case 6: // CREATE_TIME_END
          return CREATE_TIME_END;
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
  private static final int __CREATETIMESTART_ISSET_ID = 0;
  private static final int __CREATETIMEEND_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PAGE_VO, new org.apache.thrift.meta_data.FieldMetaData("pageVo", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.PageVo.class)));
    tmpMap.put(_Fields.CLIENT_ID, new org.apache.thrift.meta_data.FieldMetaData("clientId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.EXCEPTION_TYPE, new org.apache.thrift.meta_data.FieldMetaData("exceptionType", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, BossPaymentExceptionType.class)));
    tmpMap.put(_Fields.TYPE, new org.apache.thrift.meta_data.FieldMetaData("type", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, BossPaymentType.class)));
    tmpMap.put(_Fields.CREATE_TIME_START, new org.apache.thrift.meta_data.FieldMetaData("createTimeStart", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.CREATE_TIME_END, new org.apache.thrift.meta_data.FieldMetaData("createTimeEnd", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(BossPaymentQueryExceptionVo.class, metaDataMap);
  }

  public BossPaymentQueryExceptionVo() {
  }

  public BossPaymentQueryExceptionVo(
    com.froad.thrift.vo.PageVo pageVo,
    String clientId,
    BossPaymentExceptionType exceptionType,
    BossPaymentType type,
    long createTimeStart,
    long createTimeEnd)
  {
    this();
    this.pageVo = pageVo;
    this.clientId = clientId;
    this.exceptionType = exceptionType;
    this.type = type;
    this.createTimeStart = createTimeStart;
    setCreateTimeStartIsSet(true);
    this.createTimeEnd = createTimeEnd;
    setCreateTimeEndIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public BossPaymentQueryExceptionVo(BossPaymentQueryExceptionVo other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetPageVo()) {
      this.pageVo = new com.froad.thrift.vo.PageVo(other.pageVo);
    }
    if (other.isSetClientId()) {
      this.clientId = other.clientId;
    }
    if (other.isSetExceptionType()) {
      this.exceptionType = other.exceptionType;
    }
    if (other.isSetType()) {
      this.type = other.type;
    }
    this.createTimeStart = other.createTimeStart;
    this.createTimeEnd = other.createTimeEnd;
  }

  public BossPaymentQueryExceptionVo deepCopy() {
    return new BossPaymentQueryExceptionVo(this);
  }

  @Override
  public void clear() {
    this.pageVo = null;
    this.clientId = null;
    this.exceptionType = null;
    this.type = null;
    setCreateTimeStartIsSet(false);
    this.createTimeStart = 0;
    setCreateTimeEndIsSet(false);
    this.createTimeEnd = 0;
  }

  public com.froad.thrift.vo.PageVo getPageVo() {
    return this.pageVo;
  }

  public BossPaymentQueryExceptionVo setPageVo(com.froad.thrift.vo.PageVo pageVo) {
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

  public String getClientId() {
    return this.clientId;
  }

  public BossPaymentQueryExceptionVo setClientId(String clientId) {
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
   * 
   * @see BossPaymentExceptionType
   */
  public BossPaymentExceptionType getExceptionType() {
    return this.exceptionType;
  }

  /**
   * 
   * @see BossPaymentExceptionType
   */
  public BossPaymentQueryExceptionVo setExceptionType(BossPaymentExceptionType exceptionType) {
    this.exceptionType = exceptionType;
    return this;
  }

  public void unsetExceptionType() {
    this.exceptionType = null;
  }

  /** Returns true if field exceptionType is set (has been assigned a value) and false otherwise */
  public boolean isSetExceptionType() {
    return this.exceptionType != null;
  }

  public void setExceptionTypeIsSet(boolean value) {
    if (!value) {
      this.exceptionType = null;
    }
  }

  /**
   * 
   * @see BossPaymentType
   */
  public BossPaymentType getType() {
    return this.type;
  }

  /**
   * 
   * @see BossPaymentType
   */
  public BossPaymentQueryExceptionVo setType(BossPaymentType type) {
    this.type = type;
    return this;
  }

  public void unsetType() {
    this.type = null;
  }

  /** Returns true if field type is set (has been assigned a value) and false otherwise */
  public boolean isSetType() {
    return this.type != null;
  }

  public void setTypeIsSet(boolean value) {
    if (!value) {
      this.type = null;
    }
  }

  public long getCreateTimeStart() {
    return this.createTimeStart;
  }

  public BossPaymentQueryExceptionVo setCreateTimeStart(long createTimeStart) {
    this.createTimeStart = createTimeStart;
    setCreateTimeStartIsSet(true);
    return this;
  }

  public void unsetCreateTimeStart() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __CREATETIMESTART_ISSET_ID);
  }

  /** Returns true if field createTimeStart is set (has been assigned a value) and false otherwise */
  public boolean isSetCreateTimeStart() {
    return EncodingUtils.testBit(__isset_bitfield, __CREATETIMESTART_ISSET_ID);
  }

  public void setCreateTimeStartIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __CREATETIMESTART_ISSET_ID, value);
  }

  public long getCreateTimeEnd() {
    return this.createTimeEnd;
  }

  public BossPaymentQueryExceptionVo setCreateTimeEnd(long createTimeEnd) {
    this.createTimeEnd = createTimeEnd;
    setCreateTimeEndIsSet(true);
    return this;
  }

  public void unsetCreateTimeEnd() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __CREATETIMEEND_ISSET_ID);
  }

  /** Returns true if field createTimeEnd is set (has been assigned a value) and false otherwise */
  public boolean isSetCreateTimeEnd() {
    return EncodingUtils.testBit(__isset_bitfield, __CREATETIMEEND_ISSET_ID);
  }

  public void setCreateTimeEndIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __CREATETIMEEND_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PAGE_VO:
      if (value == null) {
        unsetPageVo();
      } else {
        setPageVo((com.froad.thrift.vo.PageVo)value);
      }
      break;

    case CLIENT_ID:
      if (value == null) {
        unsetClientId();
      } else {
        setClientId((String)value);
      }
      break;

    case EXCEPTION_TYPE:
      if (value == null) {
        unsetExceptionType();
      } else {
        setExceptionType((BossPaymentExceptionType)value);
      }
      break;

    case TYPE:
      if (value == null) {
        unsetType();
      } else {
        setType((BossPaymentType)value);
      }
      break;

    case CREATE_TIME_START:
      if (value == null) {
        unsetCreateTimeStart();
      } else {
        setCreateTimeStart((Long)value);
      }
      break;

    case CREATE_TIME_END:
      if (value == null) {
        unsetCreateTimeEnd();
      } else {
        setCreateTimeEnd((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PAGE_VO:
      return getPageVo();

    case CLIENT_ID:
      return getClientId();

    case EXCEPTION_TYPE:
      return getExceptionType();

    case TYPE:
      return getType();

    case CREATE_TIME_START:
      return Long.valueOf(getCreateTimeStart());

    case CREATE_TIME_END:
      return Long.valueOf(getCreateTimeEnd());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case PAGE_VO:
      return isSetPageVo();
    case CLIENT_ID:
      return isSetClientId();
    case EXCEPTION_TYPE:
      return isSetExceptionType();
    case TYPE:
      return isSetType();
    case CREATE_TIME_START:
      return isSetCreateTimeStart();
    case CREATE_TIME_END:
      return isSetCreateTimeEnd();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof BossPaymentQueryExceptionVo)
      return this.equals((BossPaymentQueryExceptionVo)that);
    return false;
  }

  public boolean equals(BossPaymentQueryExceptionVo that) {
    if (that == null)
      return false;

    boolean this_present_pageVo = true && this.isSetPageVo();
    boolean that_present_pageVo = true && that.isSetPageVo();
    if (this_present_pageVo || that_present_pageVo) {
      if (!(this_present_pageVo && that_present_pageVo))
        return false;
      if (!this.pageVo.equals(that.pageVo))
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

    boolean this_present_exceptionType = true && this.isSetExceptionType();
    boolean that_present_exceptionType = true && that.isSetExceptionType();
    if (this_present_exceptionType || that_present_exceptionType) {
      if (!(this_present_exceptionType && that_present_exceptionType))
        return false;
      if (!this.exceptionType.equals(that.exceptionType))
        return false;
    }

    boolean this_present_type = true && this.isSetType();
    boolean that_present_type = true && that.isSetType();
    if (this_present_type || that_present_type) {
      if (!(this_present_type && that_present_type))
        return false;
      if (!this.type.equals(that.type))
        return false;
    }

    boolean this_present_createTimeStart = true;
    boolean that_present_createTimeStart = true;
    if (this_present_createTimeStart || that_present_createTimeStart) {
      if (!(this_present_createTimeStart && that_present_createTimeStart))
        return false;
      if (this.createTimeStart != that.createTimeStart)
        return false;
    }

    boolean this_present_createTimeEnd = true;
    boolean that_present_createTimeEnd = true;
    if (this_present_createTimeEnd || that_present_createTimeEnd) {
      if (!(this_present_createTimeEnd && that_present_createTimeEnd))
        return false;
      if (this.createTimeEnd != that.createTimeEnd)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_pageVo = true && (isSetPageVo());
    list.add(present_pageVo);
    if (present_pageVo)
      list.add(pageVo);

    boolean present_clientId = true && (isSetClientId());
    list.add(present_clientId);
    if (present_clientId)
      list.add(clientId);

    boolean present_exceptionType = true && (isSetExceptionType());
    list.add(present_exceptionType);
    if (present_exceptionType)
      list.add(exceptionType.getValue());

    boolean present_type = true && (isSetType());
    list.add(present_type);
    if (present_type)
      list.add(type.getValue());

    boolean present_createTimeStart = true;
    list.add(present_createTimeStart);
    if (present_createTimeStart)
      list.add(createTimeStart);

    boolean present_createTimeEnd = true;
    list.add(present_createTimeEnd);
    if (present_createTimeEnd)
      list.add(createTimeEnd);

    return list.hashCode();
  }

  @Override
  public int compareTo(BossPaymentQueryExceptionVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

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
    lastComparison = Boolean.valueOf(isSetExceptionType()).compareTo(other.isSetExceptionType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetExceptionType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.exceptionType, other.exceptionType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetType()).compareTo(other.isSetType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.type, other.type);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCreateTimeStart()).compareTo(other.isSetCreateTimeStart());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCreateTimeStart()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.createTimeStart, other.createTimeStart);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCreateTimeEnd()).compareTo(other.isSetCreateTimeEnd());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCreateTimeEnd()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.createTimeEnd, other.createTimeEnd);
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
    StringBuilder sb = new StringBuilder("BossPaymentQueryExceptionVo(");
    boolean first = true;

    sb.append("pageVo:");
    if (this.pageVo == null) {
      sb.append("null");
    } else {
      sb.append(this.pageVo);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("clientId:");
    if (this.clientId == null) {
      sb.append("null");
    } else {
      sb.append(this.clientId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("exceptionType:");
    if (this.exceptionType == null) {
      sb.append("null");
    } else {
      sb.append(this.exceptionType);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("type:");
    if (this.type == null) {
      sb.append("null");
    } else {
      sb.append(this.type);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("createTimeStart:");
    sb.append(this.createTimeStart);
    first = false;
    if (!first) sb.append(", ");
    sb.append("createTimeEnd:");
    sb.append(this.createTimeEnd);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class BossPaymentQueryExceptionVoStandardSchemeFactory implements SchemeFactory {
    public BossPaymentQueryExceptionVoStandardScheme getScheme() {
      return new BossPaymentQueryExceptionVoStandardScheme();
    }
  }

  private static class BossPaymentQueryExceptionVoStandardScheme extends StandardScheme<BossPaymentQueryExceptionVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, BossPaymentQueryExceptionVo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PAGE_VO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.pageVo = new com.froad.thrift.vo.PageVo();
              struct.pageVo.read(iprot);
              struct.setPageVoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // CLIENT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.clientId = iprot.readString();
              struct.setClientIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // EXCEPTION_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.exceptionType = com.froad.thrift.vo.payment.BossPaymentExceptionType.findByValue(iprot.readI32());
              struct.setExceptionTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.type = com.froad.thrift.vo.payment.BossPaymentType.findByValue(iprot.readI32());
              struct.setTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // CREATE_TIME_START
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.createTimeStart = iprot.readI64();
              struct.setCreateTimeStartIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // CREATE_TIME_END
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.createTimeEnd = iprot.readI64();
              struct.setCreateTimeEndIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, BossPaymentQueryExceptionVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.pageVo != null) {
        oprot.writeFieldBegin(PAGE_VO_FIELD_DESC);
        struct.pageVo.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.clientId != null) {
        oprot.writeFieldBegin(CLIENT_ID_FIELD_DESC);
        oprot.writeString(struct.clientId);
        oprot.writeFieldEnd();
      }
      if (struct.exceptionType != null) {
        oprot.writeFieldBegin(EXCEPTION_TYPE_FIELD_DESC);
        oprot.writeI32(struct.exceptionType.getValue());
        oprot.writeFieldEnd();
      }
      if (struct.type != null) {
        oprot.writeFieldBegin(TYPE_FIELD_DESC);
        oprot.writeI32(struct.type.getValue());
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(CREATE_TIME_START_FIELD_DESC);
      oprot.writeI64(struct.createTimeStart);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(CREATE_TIME_END_FIELD_DESC);
      oprot.writeI64(struct.createTimeEnd);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class BossPaymentQueryExceptionVoTupleSchemeFactory implements SchemeFactory {
    public BossPaymentQueryExceptionVoTupleScheme getScheme() {
      return new BossPaymentQueryExceptionVoTupleScheme();
    }
  }

  private static class BossPaymentQueryExceptionVoTupleScheme extends TupleScheme<BossPaymentQueryExceptionVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, BossPaymentQueryExceptionVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPageVo()) {
        optionals.set(0);
      }
      if (struct.isSetClientId()) {
        optionals.set(1);
      }
      if (struct.isSetExceptionType()) {
        optionals.set(2);
      }
      if (struct.isSetType()) {
        optionals.set(3);
      }
      if (struct.isSetCreateTimeStart()) {
        optionals.set(4);
      }
      if (struct.isSetCreateTimeEnd()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetPageVo()) {
        struct.pageVo.write(oprot);
      }
      if (struct.isSetClientId()) {
        oprot.writeString(struct.clientId);
      }
      if (struct.isSetExceptionType()) {
        oprot.writeI32(struct.exceptionType.getValue());
      }
      if (struct.isSetType()) {
        oprot.writeI32(struct.type.getValue());
      }
      if (struct.isSetCreateTimeStart()) {
        oprot.writeI64(struct.createTimeStart);
      }
      if (struct.isSetCreateTimeEnd()) {
        oprot.writeI64(struct.createTimeEnd);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, BossPaymentQueryExceptionVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.pageVo = new com.froad.thrift.vo.PageVo();
        struct.pageVo.read(iprot);
        struct.setPageVoIsSet(true);
      }
      if (incoming.get(1)) {
        struct.clientId = iprot.readString();
        struct.setClientIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.exceptionType = com.froad.thrift.vo.payment.BossPaymentExceptionType.findByValue(iprot.readI32());
        struct.setExceptionTypeIsSet(true);
      }
      if (incoming.get(3)) {
        struct.type = com.froad.thrift.vo.payment.BossPaymentType.findByValue(iprot.readI32());
        struct.setTypeIsSet(true);
      }
      if (incoming.get(4)) {
        struct.createTimeStart = iprot.readI64();
        struct.setCreateTimeStartIsSet(true);
      }
      if (incoming.get(5)) {
        struct.createTimeEnd = iprot.readI64();
        struct.setCreateTimeEndIsSet(true);
      }
    }
  }

}

