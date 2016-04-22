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
 * 营销活动订单退款 请求
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class ReturnMarketOrderReqVo implements org.apache.thrift.TBase<ReturnMarketOrderReqVo, ReturnMarketOrderReqVo._Fields>, java.io.Serializable, Cloneable, Comparable<ReturnMarketOrderReqVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ReturnMarketOrderReqVo");

  private static final org.apache.thrift.protocol.TField REQ_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("reqId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField CLIENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("clientId", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField ORDER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("orderId", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField IS_QUERY_FIELD_DESC = new org.apache.thrift.protocol.TField("isQuery", org.apache.thrift.protocol.TType.BOOL, (short)4);
  private static final org.apache.thrift.protocol.TField RETURN_SUB_ORDER_REQ_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("returnSubOrderReqList", org.apache.thrift.protocol.TType.LIST, (short)5);
  private static final org.apache.thrift.protocol.TField RETURN_BILL_NO_FIELD_DESC = new org.apache.thrift.protocol.TField("returnBillNo", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField RETURN_MONEY_FIELD_DESC = new org.apache.thrift.protocol.TField("returnMoney", org.apache.thrift.protocol.TType.DOUBLE, (short)7);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ReturnMarketOrderReqVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ReturnMarketOrderReqVoTupleSchemeFactory());
  }

  /**
   * 请求id
   */
  public String reqId; // required
  /**
   * 客户端id
   */
  public String clientId; // required
  /**
   * 订单id
   */
  public String orderId; // required
  /**
   * 是否是查询
   */
  public boolean isQuery; // required
  /**
   * 退款子订单 - 列表
   */
  public List<ReturnSubOrderReqVo> returnSubOrderReqList; // required
  /**
   * 退款编号
   */
  public String returnBillNo; // required
  /**
   * 退款金额
   */
  public double returnMoney; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 请求id
     */
    REQ_ID((short)1, "reqId"),
    /**
     * 客户端id
     */
    CLIENT_ID((short)2, "clientId"),
    /**
     * 订单id
     */
    ORDER_ID((short)3, "orderId"),
    /**
     * 是否是查询
     */
    IS_QUERY((short)4, "isQuery"),
    /**
     * 退款子订单 - 列表
     */
    RETURN_SUB_ORDER_REQ_LIST((short)5, "returnSubOrderReqList"),
    /**
     * 退款编号
     */
    RETURN_BILL_NO((short)6, "returnBillNo"),
    /**
     * 退款金额
     */
    RETURN_MONEY((short)7, "returnMoney");

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
        case 1: // REQ_ID
          return REQ_ID;
        case 2: // CLIENT_ID
          return CLIENT_ID;
        case 3: // ORDER_ID
          return ORDER_ID;
        case 4: // IS_QUERY
          return IS_QUERY;
        case 5: // RETURN_SUB_ORDER_REQ_LIST
          return RETURN_SUB_ORDER_REQ_LIST;
        case 6: // RETURN_BILL_NO
          return RETURN_BILL_NO;
        case 7: // RETURN_MONEY
          return RETURN_MONEY;
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
  private static final int __ISQUERY_ISSET_ID = 0;
  private static final int __RETURNMONEY_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.REQ_ID, new org.apache.thrift.meta_data.FieldMetaData("reqId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CLIENT_ID, new org.apache.thrift.meta_data.FieldMetaData("clientId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ORDER_ID, new org.apache.thrift.meta_data.FieldMetaData("orderId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.IS_QUERY, new org.apache.thrift.meta_data.FieldMetaData("isQuery", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.RETURN_SUB_ORDER_REQ_LIST, new org.apache.thrift.meta_data.FieldMetaData("returnSubOrderReqList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT            , "ReturnSubOrderReqVo"))));
    tmpMap.put(_Fields.RETURN_BILL_NO, new org.apache.thrift.meta_data.FieldMetaData("returnBillNo", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.RETURN_MONEY, new org.apache.thrift.meta_data.FieldMetaData("returnMoney", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ReturnMarketOrderReqVo.class, metaDataMap);
  }

  public ReturnMarketOrderReqVo() {
  }

  public ReturnMarketOrderReqVo(
    String reqId,
    String clientId,
    String orderId,
    boolean isQuery,
    List<ReturnSubOrderReqVo> returnSubOrderReqList,
    String returnBillNo,
    double returnMoney)
  {
    this();
    this.reqId = reqId;
    this.clientId = clientId;
    this.orderId = orderId;
    this.isQuery = isQuery;
    setIsQueryIsSet(true);
    this.returnSubOrderReqList = returnSubOrderReqList;
    this.returnBillNo = returnBillNo;
    this.returnMoney = returnMoney;
    setReturnMoneyIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ReturnMarketOrderReqVo(ReturnMarketOrderReqVo other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetReqId()) {
      this.reqId = other.reqId;
    }
    if (other.isSetClientId()) {
      this.clientId = other.clientId;
    }
    if (other.isSetOrderId()) {
      this.orderId = other.orderId;
    }
    this.isQuery = other.isQuery;
    if (other.isSetReturnSubOrderReqList()) {
      List<ReturnSubOrderReqVo> __this__returnSubOrderReqList = new ArrayList<ReturnSubOrderReqVo>(other.returnSubOrderReqList.size());
      for (ReturnSubOrderReqVo other_element : other.returnSubOrderReqList) {
        __this__returnSubOrderReqList.add(other_element);
      }
      this.returnSubOrderReqList = __this__returnSubOrderReqList;
    }
    if (other.isSetReturnBillNo()) {
      this.returnBillNo = other.returnBillNo;
    }
    this.returnMoney = other.returnMoney;
  }

  public ReturnMarketOrderReqVo deepCopy() {
    return new ReturnMarketOrderReqVo(this);
  }

  @Override
  public void clear() {
    this.reqId = null;
    this.clientId = null;
    this.orderId = null;
    setIsQueryIsSet(false);
    this.isQuery = false;
    this.returnSubOrderReqList = null;
    this.returnBillNo = null;
    setReturnMoneyIsSet(false);
    this.returnMoney = 0.0;
  }

  /**
   * 请求id
   */
  public String getReqId() {
    return this.reqId;
  }

  /**
   * 请求id
   */
  public ReturnMarketOrderReqVo setReqId(String reqId) {
    this.reqId = reqId;
    return this;
  }

  public void unsetReqId() {
    this.reqId = null;
  }

  /** Returns true if field reqId is set (has been assigned a value) and false otherwise */
  public boolean isSetReqId() {
    return this.reqId != null;
  }

  public void setReqIdIsSet(boolean value) {
    if (!value) {
      this.reqId = null;
    }
  }

  /**
   * 客户端id
   */
  public String getClientId() {
    return this.clientId;
  }

  /**
   * 客户端id
   */
  public ReturnMarketOrderReqVo setClientId(String clientId) {
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
   * 订单id
   */
  public String getOrderId() {
    return this.orderId;
  }

  /**
   * 订单id
   */
  public ReturnMarketOrderReqVo setOrderId(String orderId) {
    this.orderId = orderId;
    return this;
  }

  public void unsetOrderId() {
    this.orderId = null;
  }

  /** Returns true if field orderId is set (has been assigned a value) and false otherwise */
  public boolean isSetOrderId() {
    return this.orderId != null;
  }

  public void setOrderIdIsSet(boolean value) {
    if (!value) {
      this.orderId = null;
    }
  }

  /**
   * 是否是查询
   */
  public boolean isIsQuery() {
    return this.isQuery;
  }

  /**
   * 是否是查询
   */
  public ReturnMarketOrderReqVo setIsQuery(boolean isQuery) {
    this.isQuery = isQuery;
    setIsQueryIsSet(true);
    return this;
  }

  public void unsetIsQuery() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ISQUERY_ISSET_ID);
  }

  /** Returns true if field isQuery is set (has been assigned a value) and false otherwise */
  public boolean isSetIsQuery() {
    return EncodingUtils.testBit(__isset_bitfield, __ISQUERY_ISSET_ID);
  }

  public void setIsQueryIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ISQUERY_ISSET_ID, value);
  }

  public int getReturnSubOrderReqListSize() {
    return (this.returnSubOrderReqList == null) ? 0 : this.returnSubOrderReqList.size();
  }

  public java.util.Iterator<ReturnSubOrderReqVo> getReturnSubOrderReqListIterator() {
    return (this.returnSubOrderReqList == null) ? null : this.returnSubOrderReqList.iterator();
  }

  public void addToReturnSubOrderReqList(ReturnSubOrderReqVo elem) {
    if (this.returnSubOrderReqList == null) {
      this.returnSubOrderReqList = new ArrayList<ReturnSubOrderReqVo>();
    }
    this.returnSubOrderReqList.add(elem);
  }

  /**
   * 退款子订单 - 列表
   */
  public List<ReturnSubOrderReqVo> getReturnSubOrderReqList() {
    return this.returnSubOrderReqList;
  }

  /**
   * 退款子订单 - 列表
   */
  public ReturnMarketOrderReqVo setReturnSubOrderReqList(List<ReturnSubOrderReqVo> returnSubOrderReqList) {
    this.returnSubOrderReqList = returnSubOrderReqList;
    return this;
  }

  public void unsetReturnSubOrderReqList() {
    this.returnSubOrderReqList = null;
  }

  /** Returns true if field returnSubOrderReqList is set (has been assigned a value) and false otherwise */
  public boolean isSetReturnSubOrderReqList() {
    return this.returnSubOrderReqList != null;
  }

  public void setReturnSubOrderReqListIsSet(boolean value) {
    if (!value) {
      this.returnSubOrderReqList = null;
    }
  }

  /**
   * 退款编号
   */
  public String getReturnBillNo() {
    return this.returnBillNo;
  }

  /**
   * 退款编号
   */
  public ReturnMarketOrderReqVo setReturnBillNo(String returnBillNo) {
    this.returnBillNo = returnBillNo;
    return this;
  }

  public void unsetReturnBillNo() {
    this.returnBillNo = null;
  }

  /** Returns true if field returnBillNo is set (has been assigned a value) and false otherwise */
  public boolean isSetReturnBillNo() {
    return this.returnBillNo != null;
  }

  public void setReturnBillNoIsSet(boolean value) {
    if (!value) {
      this.returnBillNo = null;
    }
  }

  /**
   * 退款金额
   */
  public double getReturnMoney() {
    return this.returnMoney;
  }

  /**
   * 退款金额
   */
  public ReturnMarketOrderReqVo setReturnMoney(double returnMoney) {
    this.returnMoney = returnMoney;
    setReturnMoneyIsSet(true);
    return this;
  }

  public void unsetReturnMoney() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __RETURNMONEY_ISSET_ID);
  }

  /** Returns true if field returnMoney is set (has been assigned a value) and false otherwise */
  public boolean isSetReturnMoney() {
    return EncodingUtils.testBit(__isset_bitfield, __RETURNMONEY_ISSET_ID);
  }

  public void setReturnMoneyIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __RETURNMONEY_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case REQ_ID:
      if (value == null) {
        unsetReqId();
      } else {
        setReqId((String)value);
      }
      break;

    case CLIENT_ID:
      if (value == null) {
        unsetClientId();
      } else {
        setClientId((String)value);
      }
      break;

    case ORDER_ID:
      if (value == null) {
        unsetOrderId();
      } else {
        setOrderId((String)value);
      }
      break;

    case IS_QUERY:
      if (value == null) {
        unsetIsQuery();
      } else {
        setIsQuery((Boolean)value);
      }
      break;

    case RETURN_SUB_ORDER_REQ_LIST:
      if (value == null) {
        unsetReturnSubOrderReqList();
      } else {
        setReturnSubOrderReqList((List<ReturnSubOrderReqVo>)value);
      }
      break;

    case RETURN_BILL_NO:
      if (value == null) {
        unsetReturnBillNo();
      } else {
        setReturnBillNo((String)value);
      }
      break;

    case RETURN_MONEY:
      if (value == null) {
        unsetReturnMoney();
      } else {
        setReturnMoney((Double)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case REQ_ID:
      return getReqId();

    case CLIENT_ID:
      return getClientId();

    case ORDER_ID:
      return getOrderId();

    case IS_QUERY:
      return Boolean.valueOf(isIsQuery());

    case RETURN_SUB_ORDER_REQ_LIST:
      return getReturnSubOrderReqList();

    case RETURN_BILL_NO:
      return getReturnBillNo();

    case RETURN_MONEY:
      return Double.valueOf(getReturnMoney());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case REQ_ID:
      return isSetReqId();
    case CLIENT_ID:
      return isSetClientId();
    case ORDER_ID:
      return isSetOrderId();
    case IS_QUERY:
      return isSetIsQuery();
    case RETURN_SUB_ORDER_REQ_LIST:
      return isSetReturnSubOrderReqList();
    case RETURN_BILL_NO:
      return isSetReturnBillNo();
    case RETURN_MONEY:
      return isSetReturnMoney();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ReturnMarketOrderReqVo)
      return this.equals((ReturnMarketOrderReqVo)that);
    return false;
  }

  public boolean equals(ReturnMarketOrderReqVo that) {
    if (that == null)
      return false;

    boolean this_present_reqId = true && this.isSetReqId();
    boolean that_present_reqId = true && that.isSetReqId();
    if (this_present_reqId || that_present_reqId) {
      if (!(this_present_reqId && that_present_reqId))
        return false;
      if (!this.reqId.equals(that.reqId))
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

    boolean this_present_orderId = true && this.isSetOrderId();
    boolean that_present_orderId = true && that.isSetOrderId();
    if (this_present_orderId || that_present_orderId) {
      if (!(this_present_orderId && that_present_orderId))
        return false;
      if (!this.orderId.equals(that.orderId))
        return false;
    }

    boolean this_present_isQuery = true;
    boolean that_present_isQuery = true;
    if (this_present_isQuery || that_present_isQuery) {
      if (!(this_present_isQuery && that_present_isQuery))
        return false;
      if (this.isQuery != that.isQuery)
        return false;
    }

    boolean this_present_returnSubOrderReqList = true && this.isSetReturnSubOrderReqList();
    boolean that_present_returnSubOrderReqList = true && that.isSetReturnSubOrderReqList();
    if (this_present_returnSubOrderReqList || that_present_returnSubOrderReqList) {
      if (!(this_present_returnSubOrderReqList && that_present_returnSubOrderReqList))
        return false;
      if (!this.returnSubOrderReqList.equals(that.returnSubOrderReqList))
        return false;
    }

    boolean this_present_returnBillNo = true && this.isSetReturnBillNo();
    boolean that_present_returnBillNo = true && that.isSetReturnBillNo();
    if (this_present_returnBillNo || that_present_returnBillNo) {
      if (!(this_present_returnBillNo && that_present_returnBillNo))
        return false;
      if (!this.returnBillNo.equals(that.returnBillNo))
        return false;
    }

    boolean this_present_returnMoney = true;
    boolean that_present_returnMoney = true;
    if (this_present_returnMoney || that_present_returnMoney) {
      if (!(this_present_returnMoney && that_present_returnMoney))
        return false;
      if (this.returnMoney != that.returnMoney)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_reqId = true && (isSetReqId());
    list.add(present_reqId);
    if (present_reqId)
      list.add(reqId);

    boolean present_clientId = true && (isSetClientId());
    list.add(present_clientId);
    if (present_clientId)
      list.add(clientId);

    boolean present_orderId = true && (isSetOrderId());
    list.add(present_orderId);
    if (present_orderId)
      list.add(orderId);

    boolean present_isQuery = true;
    list.add(present_isQuery);
    if (present_isQuery)
      list.add(isQuery);

    boolean present_returnSubOrderReqList = true && (isSetReturnSubOrderReqList());
    list.add(present_returnSubOrderReqList);
    if (present_returnSubOrderReqList)
      list.add(returnSubOrderReqList);

    boolean present_returnBillNo = true && (isSetReturnBillNo());
    list.add(present_returnBillNo);
    if (present_returnBillNo)
      list.add(returnBillNo);

    boolean present_returnMoney = true;
    list.add(present_returnMoney);
    if (present_returnMoney)
      list.add(returnMoney);

    return list.hashCode();
  }

  @Override
  public int compareTo(ReturnMarketOrderReqVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetReqId()).compareTo(other.isSetReqId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetReqId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.reqId, other.reqId);
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
    lastComparison = Boolean.valueOf(isSetOrderId()).compareTo(other.isSetOrderId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOrderId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.orderId, other.orderId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetIsQuery()).compareTo(other.isSetIsQuery());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIsQuery()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.isQuery, other.isQuery);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetReturnSubOrderReqList()).compareTo(other.isSetReturnSubOrderReqList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetReturnSubOrderReqList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.returnSubOrderReqList, other.returnSubOrderReqList);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetReturnBillNo()).compareTo(other.isSetReturnBillNo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetReturnBillNo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.returnBillNo, other.returnBillNo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetReturnMoney()).compareTo(other.isSetReturnMoney());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetReturnMoney()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.returnMoney, other.returnMoney);
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
    StringBuilder sb = new StringBuilder("ReturnMarketOrderReqVo(");
    boolean first = true;

    sb.append("reqId:");
    if (this.reqId == null) {
      sb.append("null");
    } else {
      sb.append(this.reqId);
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
    sb.append("orderId:");
    if (this.orderId == null) {
      sb.append("null");
    } else {
      sb.append(this.orderId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("isQuery:");
    sb.append(this.isQuery);
    first = false;
    if (!first) sb.append(", ");
    sb.append("returnSubOrderReqList:");
    if (this.returnSubOrderReqList == null) {
      sb.append("null");
    } else {
      sb.append(this.returnSubOrderReqList);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("returnBillNo:");
    if (this.returnBillNo == null) {
      sb.append("null");
    } else {
      sb.append(this.returnBillNo);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("returnMoney:");
    sb.append(this.returnMoney);
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

  private static class ReturnMarketOrderReqVoStandardSchemeFactory implements SchemeFactory {
    public ReturnMarketOrderReqVoStandardScheme getScheme() {
      return new ReturnMarketOrderReqVoStandardScheme();
    }
  }

  private static class ReturnMarketOrderReqVoStandardScheme extends StandardScheme<ReturnMarketOrderReqVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ReturnMarketOrderReqVo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // REQ_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.reqId = iprot.readString();
              struct.setReqIdIsSet(true);
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
          case 3: // ORDER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.orderId = iprot.readString();
              struct.setOrderIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // IS_QUERY
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.isQuery = iprot.readBool();
              struct.setIsQueryIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // RETURN_SUB_ORDER_REQ_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list136 = iprot.readListBegin();
                struct.returnSubOrderReqList = new ArrayList<ReturnSubOrderReqVo>(_list136.size);
                ReturnSubOrderReqVo _elem137;
                for (int _i138 = 0; _i138 < _list136.size; ++_i138)
                {
                  _elem137 = new ReturnSubOrderReqVo();
                  _elem137.read(iprot);
                  struct.returnSubOrderReqList.add(_elem137);
                }
                iprot.readListEnd();
              }
              struct.setReturnSubOrderReqListIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // RETURN_BILL_NO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.returnBillNo = iprot.readString();
              struct.setReturnBillNoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // RETURN_MONEY
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.returnMoney = iprot.readDouble();
              struct.setReturnMoneyIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ReturnMarketOrderReqVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.reqId != null) {
        oprot.writeFieldBegin(REQ_ID_FIELD_DESC);
        oprot.writeString(struct.reqId);
        oprot.writeFieldEnd();
      }
      if (struct.clientId != null) {
        oprot.writeFieldBegin(CLIENT_ID_FIELD_DESC);
        oprot.writeString(struct.clientId);
        oprot.writeFieldEnd();
      }
      if (struct.orderId != null) {
        oprot.writeFieldBegin(ORDER_ID_FIELD_DESC);
        oprot.writeString(struct.orderId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(IS_QUERY_FIELD_DESC);
      oprot.writeBool(struct.isQuery);
      oprot.writeFieldEnd();
      if (struct.returnSubOrderReqList != null) {
        oprot.writeFieldBegin(RETURN_SUB_ORDER_REQ_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.returnSubOrderReqList.size()));
          for (ReturnSubOrderReqVo _iter139 : struct.returnSubOrderReqList)
          {
            _iter139.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.returnBillNo != null) {
        oprot.writeFieldBegin(RETURN_BILL_NO_FIELD_DESC);
        oprot.writeString(struct.returnBillNo);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(RETURN_MONEY_FIELD_DESC);
      oprot.writeDouble(struct.returnMoney);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ReturnMarketOrderReqVoTupleSchemeFactory implements SchemeFactory {
    public ReturnMarketOrderReqVoTupleScheme getScheme() {
      return new ReturnMarketOrderReqVoTupleScheme();
    }
  }

  private static class ReturnMarketOrderReqVoTupleScheme extends TupleScheme<ReturnMarketOrderReqVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ReturnMarketOrderReqVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetReqId()) {
        optionals.set(0);
      }
      if (struct.isSetClientId()) {
        optionals.set(1);
      }
      if (struct.isSetOrderId()) {
        optionals.set(2);
      }
      if (struct.isSetIsQuery()) {
        optionals.set(3);
      }
      if (struct.isSetReturnSubOrderReqList()) {
        optionals.set(4);
      }
      if (struct.isSetReturnBillNo()) {
        optionals.set(5);
      }
      if (struct.isSetReturnMoney()) {
        optionals.set(6);
      }
      oprot.writeBitSet(optionals, 7);
      if (struct.isSetReqId()) {
        oprot.writeString(struct.reqId);
      }
      if (struct.isSetClientId()) {
        oprot.writeString(struct.clientId);
      }
      if (struct.isSetOrderId()) {
        oprot.writeString(struct.orderId);
      }
      if (struct.isSetIsQuery()) {
        oprot.writeBool(struct.isQuery);
      }
      if (struct.isSetReturnSubOrderReqList()) {
        {
          oprot.writeI32(struct.returnSubOrderReqList.size());
          for (ReturnSubOrderReqVo _iter140 : struct.returnSubOrderReqList)
          {
            _iter140.write(oprot);
          }
        }
      }
      if (struct.isSetReturnBillNo()) {
        oprot.writeString(struct.returnBillNo);
      }
      if (struct.isSetReturnMoney()) {
        oprot.writeDouble(struct.returnMoney);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ReturnMarketOrderReqVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(7);
      if (incoming.get(0)) {
        struct.reqId = iprot.readString();
        struct.setReqIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.clientId = iprot.readString();
        struct.setClientIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.orderId = iprot.readString();
        struct.setOrderIdIsSet(true);
      }
      if (incoming.get(3)) {
        struct.isQuery = iprot.readBool();
        struct.setIsQueryIsSet(true);
      }
      if (incoming.get(4)) {
        {
          org.apache.thrift.protocol.TList _list141 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.returnSubOrderReqList = new ArrayList<ReturnSubOrderReqVo>(_list141.size);
          ReturnSubOrderReqVo _elem142;
          for (int _i143 = 0; _i143 < _list141.size; ++_i143)
          {
            _elem142 = new ReturnSubOrderReqVo();
            _elem142.read(iprot);
            struct.returnSubOrderReqList.add(_elem142);
          }
        }
        struct.setReturnSubOrderReqListIsSet(true);
      }
      if (incoming.get(5)) {
        struct.returnBillNo = iprot.readString();
        struct.setReturnBillNoIsSet(true);
      }
      if (incoming.get(6)) {
        struct.returnMoney = iprot.readDouble();
        struct.setReturnMoneyIsSet(true);
      }
    }
  }

}
