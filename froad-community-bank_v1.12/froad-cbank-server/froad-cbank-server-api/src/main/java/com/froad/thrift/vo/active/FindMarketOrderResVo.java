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
 * 查询营销活动订单 响应
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class FindMarketOrderResVo implements org.apache.thrift.TBase<FindMarketOrderResVo, FindMarketOrderResVo._Fields>, java.io.Serializable, Cloneable, Comparable<FindMarketOrderResVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("FindMarketOrderResVo");

  private static final org.apache.thrift.protocol.TField REQ_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("reqId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField CLIENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("clientId", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField ORDER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("orderId", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField FIND_MARKET_SUB_ORDER_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("findMarketSubOrderList", org.apache.thrift.protocol.TType.LIST, (short)4);
  private static final org.apache.thrift.protocol.TField RESULT_FIELD_DESC = new org.apache.thrift.protocol.TField("result", org.apache.thrift.protocol.TType.STRUCT, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new FindMarketOrderResVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new FindMarketOrderResVoTupleSchemeFactory());
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
   * 查询营销活动子订单 - 列表
   */
  public List<FindMarketSubOrderVo> findMarketSubOrderList; // required
  /**
   * 响应结果
   */
  public com.froad.thrift.vo.ResultVo result; // required

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
     * 查询营销活动子订单 - 列表
     */
    FIND_MARKET_SUB_ORDER_LIST((short)4, "findMarketSubOrderList"),
    /**
     * 响应结果
     */
    RESULT((short)5, "result");

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
        case 4: // FIND_MARKET_SUB_ORDER_LIST
          return FIND_MARKET_SUB_ORDER_LIST;
        case 5: // RESULT
          return RESULT;
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
    tmpMap.put(_Fields.REQ_ID, new org.apache.thrift.meta_data.FieldMetaData("reqId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CLIENT_ID, new org.apache.thrift.meta_data.FieldMetaData("clientId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ORDER_ID, new org.apache.thrift.meta_data.FieldMetaData("orderId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.FIND_MARKET_SUB_ORDER_LIST, new org.apache.thrift.meta_data.FieldMetaData("findMarketSubOrderList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT            , "FindMarketSubOrderVo"))));
    tmpMap.put(_Fields.RESULT, new org.apache.thrift.meta_data.FieldMetaData("result", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.ResultVo.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(FindMarketOrderResVo.class, metaDataMap);
  }

  public FindMarketOrderResVo() {
  }

  public FindMarketOrderResVo(
    String reqId,
    String clientId,
    String orderId,
    List<FindMarketSubOrderVo> findMarketSubOrderList,
    com.froad.thrift.vo.ResultVo result)
  {
    this();
    this.reqId = reqId;
    this.clientId = clientId;
    this.orderId = orderId;
    this.findMarketSubOrderList = findMarketSubOrderList;
    this.result = result;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public FindMarketOrderResVo(FindMarketOrderResVo other) {
    if (other.isSetReqId()) {
      this.reqId = other.reqId;
    }
    if (other.isSetClientId()) {
      this.clientId = other.clientId;
    }
    if (other.isSetOrderId()) {
      this.orderId = other.orderId;
    }
    if (other.isSetFindMarketSubOrderList()) {
      List<FindMarketSubOrderVo> __this__findMarketSubOrderList = new ArrayList<FindMarketSubOrderVo>(other.findMarketSubOrderList.size());
      for (FindMarketSubOrderVo other_element : other.findMarketSubOrderList) {
        __this__findMarketSubOrderList.add(other_element);
      }
      this.findMarketSubOrderList = __this__findMarketSubOrderList;
    }
    if (other.isSetResult()) {
      this.result = new com.froad.thrift.vo.ResultVo(other.result);
    }
  }

  public FindMarketOrderResVo deepCopy() {
    return new FindMarketOrderResVo(this);
  }

  @Override
  public void clear() {
    this.reqId = null;
    this.clientId = null;
    this.orderId = null;
    this.findMarketSubOrderList = null;
    this.result = null;
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
  public FindMarketOrderResVo setReqId(String reqId) {
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
  public FindMarketOrderResVo setClientId(String clientId) {
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
  public FindMarketOrderResVo setOrderId(String orderId) {
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

  public int getFindMarketSubOrderListSize() {
    return (this.findMarketSubOrderList == null) ? 0 : this.findMarketSubOrderList.size();
  }

  public java.util.Iterator<FindMarketSubOrderVo> getFindMarketSubOrderListIterator() {
    return (this.findMarketSubOrderList == null) ? null : this.findMarketSubOrderList.iterator();
  }

  public void addToFindMarketSubOrderList(FindMarketSubOrderVo elem) {
    if (this.findMarketSubOrderList == null) {
      this.findMarketSubOrderList = new ArrayList<FindMarketSubOrderVo>();
    }
    this.findMarketSubOrderList.add(elem);
  }

  /**
   * 查询营销活动子订单 - 列表
   */
  public List<FindMarketSubOrderVo> getFindMarketSubOrderList() {
    return this.findMarketSubOrderList;
  }

  /**
   * 查询营销活动子订单 - 列表
   */
  public FindMarketOrderResVo setFindMarketSubOrderList(List<FindMarketSubOrderVo> findMarketSubOrderList) {
    this.findMarketSubOrderList = findMarketSubOrderList;
    return this;
  }

  public void unsetFindMarketSubOrderList() {
    this.findMarketSubOrderList = null;
  }

  /** Returns true if field findMarketSubOrderList is set (has been assigned a value) and false otherwise */
  public boolean isSetFindMarketSubOrderList() {
    return this.findMarketSubOrderList != null;
  }

  public void setFindMarketSubOrderListIsSet(boolean value) {
    if (!value) {
      this.findMarketSubOrderList = null;
    }
  }

  /**
   * 响应结果
   */
  public com.froad.thrift.vo.ResultVo getResult() {
    return this.result;
  }

  /**
   * 响应结果
   */
  public FindMarketOrderResVo setResult(com.froad.thrift.vo.ResultVo result) {
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

    case FIND_MARKET_SUB_ORDER_LIST:
      if (value == null) {
        unsetFindMarketSubOrderList();
      } else {
        setFindMarketSubOrderList((List<FindMarketSubOrderVo>)value);
      }
      break;

    case RESULT:
      if (value == null) {
        unsetResult();
      } else {
        setResult((com.froad.thrift.vo.ResultVo)value);
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

    case FIND_MARKET_SUB_ORDER_LIST:
      return getFindMarketSubOrderList();

    case RESULT:
      return getResult();

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
    case FIND_MARKET_SUB_ORDER_LIST:
      return isSetFindMarketSubOrderList();
    case RESULT:
      return isSetResult();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof FindMarketOrderResVo)
      return this.equals((FindMarketOrderResVo)that);
    return false;
  }

  public boolean equals(FindMarketOrderResVo that) {
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

    boolean this_present_findMarketSubOrderList = true && this.isSetFindMarketSubOrderList();
    boolean that_present_findMarketSubOrderList = true && that.isSetFindMarketSubOrderList();
    if (this_present_findMarketSubOrderList || that_present_findMarketSubOrderList) {
      if (!(this_present_findMarketSubOrderList && that_present_findMarketSubOrderList))
        return false;
      if (!this.findMarketSubOrderList.equals(that.findMarketSubOrderList))
        return false;
    }

    boolean this_present_result = true && this.isSetResult();
    boolean that_present_result = true && that.isSetResult();
    if (this_present_result || that_present_result) {
      if (!(this_present_result && that_present_result))
        return false;
      if (!this.result.equals(that.result))
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

    boolean present_findMarketSubOrderList = true && (isSetFindMarketSubOrderList());
    list.add(present_findMarketSubOrderList);
    if (present_findMarketSubOrderList)
      list.add(findMarketSubOrderList);

    boolean present_result = true && (isSetResult());
    list.add(present_result);
    if (present_result)
      list.add(result);

    return list.hashCode();
  }

  @Override
  public int compareTo(FindMarketOrderResVo other) {
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
    lastComparison = Boolean.valueOf(isSetFindMarketSubOrderList()).compareTo(other.isSetFindMarketSubOrderList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFindMarketSubOrderList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.findMarketSubOrderList, other.findMarketSubOrderList);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
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
    StringBuilder sb = new StringBuilder("FindMarketOrderResVo(");
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
    sb.append("findMarketSubOrderList:");
    if (this.findMarketSubOrderList == null) {
      sb.append("null");
    } else {
      sb.append(this.findMarketSubOrderList);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("result:");
    if (this.result == null) {
      sb.append("null");
    } else {
      sb.append(this.result);
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

  private static class FindMarketOrderResVoStandardSchemeFactory implements SchemeFactory {
    public FindMarketOrderResVoStandardScheme getScheme() {
      return new FindMarketOrderResVoStandardScheme();
    }
  }

  private static class FindMarketOrderResVoStandardScheme extends StandardScheme<FindMarketOrderResVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, FindMarketOrderResVo struct) throws org.apache.thrift.TException {
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
          case 4: // FIND_MARKET_SUB_ORDER_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list120 = iprot.readListBegin();
                struct.findMarketSubOrderList = new ArrayList<FindMarketSubOrderVo>(_list120.size);
                FindMarketSubOrderVo _elem121;
                for (int _i122 = 0; _i122 < _list120.size; ++_i122)
                {
                  _elem121 = new FindMarketSubOrderVo();
                  _elem121.read(iprot);
                  struct.findMarketSubOrderList.add(_elem121);
                }
                iprot.readListEnd();
              }
              struct.setFindMarketSubOrderListIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // RESULT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.result = new com.froad.thrift.vo.ResultVo();
              struct.result.read(iprot);
              struct.setResultIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, FindMarketOrderResVo struct) throws org.apache.thrift.TException {
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
      if (struct.findMarketSubOrderList != null) {
        oprot.writeFieldBegin(FIND_MARKET_SUB_ORDER_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.findMarketSubOrderList.size()));
          for (FindMarketSubOrderVo _iter123 : struct.findMarketSubOrderList)
          {
            _iter123.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.result != null) {
        oprot.writeFieldBegin(RESULT_FIELD_DESC);
        struct.result.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class FindMarketOrderResVoTupleSchemeFactory implements SchemeFactory {
    public FindMarketOrderResVoTupleScheme getScheme() {
      return new FindMarketOrderResVoTupleScheme();
    }
  }

  private static class FindMarketOrderResVoTupleScheme extends TupleScheme<FindMarketOrderResVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, FindMarketOrderResVo struct) throws org.apache.thrift.TException {
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
      if (struct.isSetFindMarketSubOrderList()) {
        optionals.set(3);
      }
      if (struct.isSetResult()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetReqId()) {
        oprot.writeString(struct.reqId);
      }
      if (struct.isSetClientId()) {
        oprot.writeString(struct.clientId);
      }
      if (struct.isSetOrderId()) {
        oprot.writeString(struct.orderId);
      }
      if (struct.isSetFindMarketSubOrderList()) {
        {
          oprot.writeI32(struct.findMarketSubOrderList.size());
          for (FindMarketSubOrderVo _iter124 : struct.findMarketSubOrderList)
          {
            _iter124.write(oprot);
          }
        }
      }
      if (struct.isSetResult()) {
        struct.result.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, FindMarketOrderResVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(5);
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
        {
          org.apache.thrift.protocol.TList _list125 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.findMarketSubOrderList = new ArrayList<FindMarketSubOrderVo>(_list125.size);
          FindMarketSubOrderVo _elem126;
          for (int _i127 = 0; _i127 < _list125.size; ++_i127)
          {
            _elem126 = new FindMarketSubOrderVo();
            _elem126.read(iprot);
            struct.findMarketSubOrderList.add(_elem126);
          }
        }
        struct.setFindMarketSubOrderListIsSet(true);
      }
      if (incoming.get(4)) {
        struct.result = new com.froad.thrift.vo.ResultVo();
        struct.result.read(iprot);
        struct.setResultIsSet(true);
      }
    }
  }

}

