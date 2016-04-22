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
 * 2.获取订单概要请求
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class GetOrderSummaryVoReq implements org.apache.thrift.TBase<GetOrderSummaryVoReq, GetOrderSummaryVoReq._Fields>, java.io.Serializable, Cloneable, Comparable<GetOrderSummaryVoReq> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("GetOrderSummaryVoReq");

  private static final org.apache.thrift.protocol.TField CLIENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("clientId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField MEMBER_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("memberCode", org.apache.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.thrift.protocol.TField ORDER_STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("orderStatus", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField START_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("startTime", org.apache.thrift.protocol.TType.I64, (short)4);
  private static final org.apache.thrift.protocol.TField END_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("endTime", org.apache.thrift.protocol.TType.I64, (short)5);
  private static final org.apache.thrift.protocol.TField PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("page", org.apache.thrift.protocol.TType.STRUCT, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new GetOrderSummaryVoReqStandardSchemeFactory());
    schemes.put(TupleScheme.class, new GetOrderSummaryVoReqTupleSchemeFactory());
  }

  /**
   * 客户端ID
   */
  public String clientId; // required
  /**
   * 会员ID
   */
  public long memberCode; // required
  /**
   * 订单状态
   */
  public String orderStatus; // optional
  /**
   * 开始时间
   */
  public long startTime; // optional
  /**
   * 结束时间
   */
  public long endTime; // optional
  /**
   * 分页信息
   */
  public com.froad.thrift.vo.PageVo page; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 客户端ID
     */
    CLIENT_ID((short)1, "clientId"),
    /**
     * 会员ID
     */
    MEMBER_CODE((short)2, "memberCode"),
    /**
     * 订单状态
     */
    ORDER_STATUS((short)3, "orderStatus"),
    /**
     * 开始时间
     */
    START_TIME((short)4, "startTime"),
    /**
     * 结束时间
     */
    END_TIME((short)5, "endTime"),
    /**
     * 分页信息
     */
    PAGE((short)6, "page");

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
        case 2: // MEMBER_CODE
          return MEMBER_CODE;
        case 3: // ORDER_STATUS
          return ORDER_STATUS;
        case 4: // START_TIME
          return START_TIME;
        case 5: // END_TIME
          return END_TIME;
        case 6: // PAGE
          return PAGE;
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
  private static final int __MEMBERCODE_ISSET_ID = 0;
  private static final int __STARTTIME_ISSET_ID = 1;
  private static final int __ENDTIME_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.ORDER_STATUS,_Fields.START_TIME,_Fields.END_TIME};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.CLIENT_ID, new org.apache.thrift.meta_data.FieldMetaData("clientId", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.MEMBER_CODE, new org.apache.thrift.meta_data.FieldMetaData("memberCode", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.ORDER_STATUS, new org.apache.thrift.meta_data.FieldMetaData("orderStatus", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.START_TIME, new org.apache.thrift.meta_data.FieldMetaData("startTime", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.END_TIME, new org.apache.thrift.meta_data.FieldMetaData("endTime", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.PAGE, new org.apache.thrift.meta_data.FieldMetaData("page", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.PageVo.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(GetOrderSummaryVoReq.class, metaDataMap);
  }

  public GetOrderSummaryVoReq() {
  }

  public GetOrderSummaryVoReq(
    String clientId,
    long memberCode,
    com.froad.thrift.vo.PageVo page)
  {
    this();
    this.clientId = clientId;
    this.memberCode = memberCode;
    setMemberCodeIsSet(true);
    this.page = page;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public GetOrderSummaryVoReq(GetOrderSummaryVoReq other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetClientId()) {
      this.clientId = other.clientId;
    }
    this.memberCode = other.memberCode;
    if (other.isSetOrderStatus()) {
      this.orderStatus = other.orderStatus;
    }
    this.startTime = other.startTime;
    this.endTime = other.endTime;
    if (other.isSetPage()) {
      this.page = new com.froad.thrift.vo.PageVo(other.page);
    }
  }

  public GetOrderSummaryVoReq deepCopy() {
    return new GetOrderSummaryVoReq(this);
  }

  @Override
  public void clear() {
    this.clientId = null;
    setMemberCodeIsSet(false);
    this.memberCode = 0;
    this.orderStatus = null;
    setStartTimeIsSet(false);
    this.startTime = 0;
    setEndTimeIsSet(false);
    this.endTime = 0;
    this.page = null;
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
  public GetOrderSummaryVoReq setClientId(String clientId) {
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
   * 会员ID
   */
  public long getMemberCode() {
    return this.memberCode;
  }

  /**
   * 会员ID
   */
  public GetOrderSummaryVoReq setMemberCode(long memberCode) {
    this.memberCode = memberCode;
    setMemberCodeIsSet(true);
    return this;
  }

  public void unsetMemberCode() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __MEMBERCODE_ISSET_ID);
  }

  /** Returns true if field memberCode is set (has been assigned a value) and false otherwise */
  public boolean isSetMemberCode() {
    return EncodingUtils.testBit(__isset_bitfield, __MEMBERCODE_ISSET_ID);
  }

  public void setMemberCodeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __MEMBERCODE_ISSET_ID, value);
  }

  /**
   * 订单状态
   */
  public String getOrderStatus() {
    return this.orderStatus;
  }

  /**
   * 订单状态
   */
  public GetOrderSummaryVoReq setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
    return this;
  }

  public void unsetOrderStatus() {
    this.orderStatus = null;
  }

  /** Returns true if field orderStatus is set (has been assigned a value) and false otherwise */
  public boolean isSetOrderStatus() {
    return this.orderStatus != null;
  }

  public void setOrderStatusIsSet(boolean value) {
    if (!value) {
      this.orderStatus = null;
    }
  }

  /**
   * 开始时间
   */
  public long getStartTime() {
    return this.startTime;
  }

  /**
   * 开始时间
   */
  public GetOrderSummaryVoReq setStartTime(long startTime) {
    this.startTime = startTime;
    setStartTimeIsSet(true);
    return this;
  }

  public void unsetStartTime() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __STARTTIME_ISSET_ID);
  }

  /** Returns true if field startTime is set (has been assigned a value) and false otherwise */
  public boolean isSetStartTime() {
    return EncodingUtils.testBit(__isset_bitfield, __STARTTIME_ISSET_ID);
  }

  public void setStartTimeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __STARTTIME_ISSET_ID, value);
  }

  /**
   * 结束时间
   */
  public long getEndTime() {
    return this.endTime;
  }

  /**
   * 结束时间
   */
  public GetOrderSummaryVoReq setEndTime(long endTime) {
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

  /**
   * 分页信息
   */
  public com.froad.thrift.vo.PageVo getPage() {
    return this.page;
  }

  /**
   * 分页信息
   */
  public GetOrderSummaryVoReq setPage(com.froad.thrift.vo.PageVo page) {
    this.page = page;
    return this;
  }

  public void unsetPage() {
    this.page = null;
  }

  /** Returns true if field page is set (has been assigned a value) and false otherwise */
  public boolean isSetPage() {
    return this.page != null;
  }

  public void setPageIsSet(boolean value) {
    if (!value) {
      this.page = null;
    }
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

    case MEMBER_CODE:
      if (value == null) {
        unsetMemberCode();
      } else {
        setMemberCode((Long)value);
      }
      break;

    case ORDER_STATUS:
      if (value == null) {
        unsetOrderStatus();
      } else {
        setOrderStatus((String)value);
      }
      break;

    case START_TIME:
      if (value == null) {
        unsetStartTime();
      } else {
        setStartTime((Long)value);
      }
      break;

    case END_TIME:
      if (value == null) {
        unsetEndTime();
      } else {
        setEndTime((Long)value);
      }
      break;

    case PAGE:
      if (value == null) {
        unsetPage();
      } else {
        setPage((com.froad.thrift.vo.PageVo)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case CLIENT_ID:
      return getClientId();

    case MEMBER_CODE:
      return Long.valueOf(getMemberCode());

    case ORDER_STATUS:
      return getOrderStatus();

    case START_TIME:
      return Long.valueOf(getStartTime());

    case END_TIME:
      return Long.valueOf(getEndTime());

    case PAGE:
      return getPage();

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
    case MEMBER_CODE:
      return isSetMemberCode();
    case ORDER_STATUS:
      return isSetOrderStatus();
    case START_TIME:
      return isSetStartTime();
    case END_TIME:
      return isSetEndTime();
    case PAGE:
      return isSetPage();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof GetOrderSummaryVoReq)
      return this.equals((GetOrderSummaryVoReq)that);
    return false;
  }

  public boolean equals(GetOrderSummaryVoReq that) {
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

    boolean this_present_memberCode = true;
    boolean that_present_memberCode = true;
    if (this_present_memberCode || that_present_memberCode) {
      if (!(this_present_memberCode && that_present_memberCode))
        return false;
      if (this.memberCode != that.memberCode)
        return false;
    }

    boolean this_present_orderStatus = true && this.isSetOrderStatus();
    boolean that_present_orderStatus = true && that.isSetOrderStatus();
    if (this_present_orderStatus || that_present_orderStatus) {
      if (!(this_present_orderStatus && that_present_orderStatus))
        return false;
      if (!this.orderStatus.equals(that.orderStatus))
        return false;
    }

    boolean this_present_startTime = true && this.isSetStartTime();
    boolean that_present_startTime = true && that.isSetStartTime();
    if (this_present_startTime || that_present_startTime) {
      if (!(this_present_startTime && that_present_startTime))
        return false;
      if (this.startTime != that.startTime)
        return false;
    }

    boolean this_present_endTime = true && this.isSetEndTime();
    boolean that_present_endTime = true && that.isSetEndTime();
    if (this_present_endTime || that_present_endTime) {
      if (!(this_present_endTime && that_present_endTime))
        return false;
      if (this.endTime != that.endTime)
        return false;
    }

    boolean this_present_page = true && this.isSetPage();
    boolean that_present_page = true && that.isSetPage();
    if (this_present_page || that_present_page) {
      if (!(this_present_page && that_present_page))
        return false;
      if (!this.page.equals(that.page))
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

    boolean present_memberCode = true;
    list.add(present_memberCode);
    if (present_memberCode)
      list.add(memberCode);

    boolean present_orderStatus = true && (isSetOrderStatus());
    list.add(present_orderStatus);
    if (present_orderStatus)
      list.add(orderStatus);

    boolean present_startTime = true && (isSetStartTime());
    list.add(present_startTime);
    if (present_startTime)
      list.add(startTime);

    boolean present_endTime = true && (isSetEndTime());
    list.add(present_endTime);
    if (present_endTime)
      list.add(endTime);

    boolean present_page = true && (isSetPage());
    list.add(present_page);
    if (present_page)
      list.add(page);

    return list.hashCode();
  }

  @Override
  public int compareTo(GetOrderSummaryVoReq other) {
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
    lastComparison = Boolean.valueOf(isSetMemberCode()).compareTo(other.isSetMemberCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMemberCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.memberCode, other.memberCode);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOrderStatus()).compareTo(other.isSetOrderStatus());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOrderStatus()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.orderStatus, other.orderStatus);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetStartTime()).compareTo(other.isSetStartTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStartTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.startTime, other.startTime);
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
    lastComparison = Boolean.valueOf(isSetPage()).compareTo(other.isSetPage());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPage()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.page, other.page);
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
    StringBuilder sb = new StringBuilder("GetOrderSummaryVoReq(");
    boolean first = true;

    sb.append("clientId:");
    if (this.clientId == null) {
      sb.append("null");
    } else {
      sb.append(this.clientId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("memberCode:");
    sb.append(this.memberCode);
    first = false;
    if (isSetOrderStatus()) {
      if (!first) sb.append(", ");
      sb.append("orderStatus:");
      if (this.orderStatus == null) {
        sb.append("null");
      } else {
        sb.append(this.orderStatus);
      }
      first = false;
    }
    if (isSetStartTime()) {
      if (!first) sb.append(", ");
      sb.append("startTime:");
      sb.append(this.startTime);
      first = false;
    }
    if (isSetEndTime()) {
      if (!first) sb.append(", ");
      sb.append("endTime:");
      sb.append(this.endTime);
      first = false;
    }
    if (!first) sb.append(", ");
    sb.append("page:");
    if (this.page == null) {
      sb.append("null");
    } else {
      sb.append(this.page);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (clientId == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'clientId' was not present! Struct: " + toString());
    }
    // alas, we cannot check 'memberCode' because it's a primitive and you chose the non-beans generator.
    if (page == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'page' was not present! Struct: " + toString());
    }
    // check for sub-struct validity
    if (page != null) {
      page.validate();
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

  private static class GetOrderSummaryVoReqStandardSchemeFactory implements SchemeFactory {
    public GetOrderSummaryVoReqStandardScheme getScheme() {
      return new GetOrderSummaryVoReqStandardScheme();
    }
  }

  private static class GetOrderSummaryVoReqStandardScheme extends StandardScheme<GetOrderSummaryVoReq> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, GetOrderSummaryVoReq struct) throws org.apache.thrift.TException {
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
          case 2: // MEMBER_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.memberCode = iprot.readI64();
              struct.setMemberCodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // ORDER_STATUS
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.orderStatus = iprot.readString();
              struct.setOrderStatusIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // START_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.startTime = iprot.readI64();
              struct.setStartTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // END_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.endTime = iprot.readI64();
              struct.setEndTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // PAGE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.page = new com.froad.thrift.vo.PageVo();
              struct.page.read(iprot);
              struct.setPageIsSet(true);
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
      if (!struct.isSetMemberCode()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'memberCode' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, GetOrderSummaryVoReq struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.clientId != null) {
        oprot.writeFieldBegin(CLIENT_ID_FIELD_DESC);
        oprot.writeString(struct.clientId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(MEMBER_CODE_FIELD_DESC);
      oprot.writeI64(struct.memberCode);
      oprot.writeFieldEnd();
      if (struct.orderStatus != null) {
        if (struct.isSetOrderStatus()) {
          oprot.writeFieldBegin(ORDER_STATUS_FIELD_DESC);
          oprot.writeString(struct.orderStatus);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetStartTime()) {
        oprot.writeFieldBegin(START_TIME_FIELD_DESC);
        oprot.writeI64(struct.startTime);
        oprot.writeFieldEnd();
      }
      if (struct.isSetEndTime()) {
        oprot.writeFieldBegin(END_TIME_FIELD_DESC);
        oprot.writeI64(struct.endTime);
        oprot.writeFieldEnd();
      }
      if (struct.page != null) {
        oprot.writeFieldBegin(PAGE_FIELD_DESC);
        struct.page.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class GetOrderSummaryVoReqTupleSchemeFactory implements SchemeFactory {
    public GetOrderSummaryVoReqTupleScheme getScheme() {
      return new GetOrderSummaryVoReqTupleScheme();
    }
  }

  private static class GetOrderSummaryVoReqTupleScheme extends TupleScheme<GetOrderSummaryVoReq> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, GetOrderSummaryVoReq struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.clientId);
      oprot.writeI64(struct.memberCode);
      struct.page.write(oprot);
      BitSet optionals = new BitSet();
      if (struct.isSetOrderStatus()) {
        optionals.set(0);
      }
      if (struct.isSetStartTime()) {
        optionals.set(1);
      }
      if (struct.isSetEndTime()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetOrderStatus()) {
        oprot.writeString(struct.orderStatus);
      }
      if (struct.isSetStartTime()) {
        oprot.writeI64(struct.startTime);
      }
      if (struct.isSetEndTime()) {
        oprot.writeI64(struct.endTime);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, GetOrderSummaryVoReq struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.clientId = iprot.readString();
      struct.setClientIdIsSet(true);
      struct.memberCode = iprot.readI64();
      struct.setMemberCodeIsSet(true);
      struct.page = new com.froad.thrift.vo.PageVo();
      struct.page.read(iprot);
      struct.setPageIsSet(true);
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.orderStatus = iprot.readString();
        struct.setOrderStatusIsSet(true);
      }
      if (incoming.get(1)) {
        struct.startTime = iprot.readI64();
        struct.setStartTimeIsSet(true);
      }
      if (incoming.get(2)) {
        struct.endTime = iprot.readI64();
        struct.setEndTimeIsSet(true);
      }
    }
  }

}

