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
 * 查询订单列表请求参数
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class QueryOrderByMerchantPhoneVoReq implements org.apache.thrift.TBase<QueryOrderByMerchantPhoneVoReq, QueryOrderByMerchantPhoneVoReq._Fields>, java.io.Serializable, Cloneable, Comparable<QueryOrderByMerchantPhoneVoReq> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("QueryOrderByMerchantPhoneVoReq");

  private static final org.apache.thrift.protocol.TField CLIENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("clientId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField MERCHANT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("merchantId", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("type", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("status", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField OUTLET_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("outletId", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField DELIVERY_STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("deliveryStatus", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("page", org.apache.thrift.protocol.TType.STRUCT, (short)7);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new QueryOrderByMerchantPhoneVoReqStandardSchemeFactory());
    schemes.put(TupleScheme.class, new QueryOrderByMerchantPhoneVoReqTupleSchemeFactory());
  }

  public String clientId; // required
  public String merchantId; // required
  public String type; // required
  public String status; // required
  public String outletId; // optional
  public String deliveryStatus; // optional
  public com.froad.thrift.vo.PageVo page; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    CLIENT_ID((short)1, "clientId"),
    MERCHANT_ID((short)2, "merchantId"),
    TYPE((short)3, "type"),
    STATUS((short)4, "status"),
    OUTLET_ID((short)5, "outletId"),
    DELIVERY_STATUS((short)6, "deliveryStatus"),
    PAGE((short)7, "page");

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
        case 2: // MERCHANT_ID
          return MERCHANT_ID;
        case 3: // TYPE
          return TYPE;
        case 4: // STATUS
          return STATUS;
        case 5: // OUTLET_ID
          return OUTLET_ID;
        case 6: // DELIVERY_STATUS
          return DELIVERY_STATUS;
        case 7: // PAGE
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
  private static final _Fields optionals[] = {_Fields.OUTLET_ID,_Fields.DELIVERY_STATUS};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.CLIENT_ID, new org.apache.thrift.meta_data.FieldMetaData("clientId", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.MERCHANT_ID, new org.apache.thrift.meta_data.FieldMetaData("merchantId", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TYPE, new org.apache.thrift.meta_data.FieldMetaData("type", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.STATUS, new org.apache.thrift.meta_data.FieldMetaData("status", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.OUTLET_ID, new org.apache.thrift.meta_data.FieldMetaData("outletId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.DELIVERY_STATUS, new org.apache.thrift.meta_data.FieldMetaData("deliveryStatus", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PAGE, new org.apache.thrift.meta_data.FieldMetaData("page", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.PageVo.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(QueryOrderByMerchantPhoneVoReq.class, metaDataMap);
  }

  public QueryOrderByMerchantPhoneVoReq() {
  }

  public QueryOrderByMerchantPhoneVoReq(
    String clientId,
    String merchantId,
    String type,
    String status,
    com.froad.thrift.vo.PageVo page)
  {
    this();
    this.clientId = clientId;
    this.merchantId = merchantId;
    this.type = type;
    this.status = status;
    this.page = page;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public QueryOrderByMerchantPhoneVoReq(QueryOrderByMerchantPhoneVoReq other) {
    if (other.isSetClientId()) {
      this.clientId = other.clientId;
    }
    if (other.isSetMerchantId()) {
      this.merchantId = other.merchantId;
    }
    if (other.isSetType()) {
      this.type = other.type;
    }
    if (other.isSetStatus()) {
      this.status = other.status;
    }
    if (other.isSetOutletId()) {
      this.outletId = other.outletId;
    }
    if (other.isSetDeliveryStatus()) {
      this.deliveryStatus = other.deliveryStatus;
    }
    if (other.isSetPage()) {
      this.page = new com.froad.thrift.vo.PageVo(other.page);
    }
  }

  public QueryOrderByMerchantPhoneVoReq deepCopy() {
    return new QueryOrderByMerchantPhoneVoReq(this);
  }

  @Override
  public void clear() {
    this.clientId = null;
    this.merchantId = null;
    this.type = null;
    this.status = null;
    this.outletId = null;
    this.deliveryStatus = null;
    this.page = null;
  }

  public String getClientId() {
    return this.clientId;
  }

  public QueryOrderByMerchantPhoneVoReq setClientId(String clientId) {
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

  public String getMerchantId() {
    return this.merchantId;
  }

  public QueryOrderByMerchantPhoneVoReq setMerchantId(String merchantId) {
    this.merchantId = merchantId;
    return this;
  }

  public void unsetMerchantId() {
    this.merchantId = null;
  }

  /** Returns true if field merchantId is set (has been assigned a value) and false otherwise */
  public boolean isSetMerchantId() {
    return this.merchantId != null;
  }

  public void setMerchantIdIsSet(boolean value) {
    if (!value) {
      this.merchantId = null;
    }
  }

  public String getType() {
    return this.type;
  }

  public QueryOrderByMerchantPhoneVoReq setType(String type) {
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

  public String getStatus() {
    return this.status;
  }

  public QueryOrderByMerchantPhoneVoReq setStatus(String status) {
    this.status = status;
    return this;
  }

  public void unsetStatus() {
    this.status = null;
  }

  /** Returns true if field status is set (has been assigned a value) and false otherwise */
  public boolean isSetStatus() {
    return this.status != null;
  }

  public void setStatusIsSet(boolean value) {
    if (!value) {
      this.status = null;
    }
  }

  public String getOutletId() {
    return this.outletId;
  }

  public QueryOrderByMerchantPhoneVoReq setOutletId(String outletId) {
    this.outletId = outletId;
    return this;
  }

  public void unsetOutletId() {
    this.outletId = null;
  }

  /** Returns true if field outletId is set (has been assigned a value) and false otherwise */
  public boolean isSetOutletId() {
    return this.outletId != null;
  }

  public void setOutletIdIsSet(boolean value) {
    if (!value) {
      this.outletId = null;
    }
  }

  public String getDeliveryStatus() {
    return this.deliveryStatus;
  }

  public QueryOrderByMerchantPhoneVoReq setDeliveryStatus(String deliveryStatus) {
    this.deliveryStatus = deliveryStatus;
    return this;
  }

  public void unsetDeliveryStatus() {
    this.deliveryStatus = null;
  }

  /** Returns true if field deliveryStatus is set (has been assigned a value) and false otherwise */
  public boolean isSetDeliveryStatus() {
    return this.deliveryStatus != null;
  }

  public void setDeliveryStatusIsSet(boolean value) {
    if (!value) {
      this.deliveryStatus = null;
    }
  }

  public com.froad.thrift.vo.PageVo getPage() {
    return this.page;
  }

  public QueryOrderByMerchantPhoneVoReq setPage(com.froad.thrift.vo.PageVo page) {
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

    case MERCHANT_ID:
      if (value == null) {
        unsetMerchantId();
      } else {
        setMerchantId((String)value);
      }
      break;

    case TYPE:
      if (value == null) {
        unsetType();
      } else {
        setType((String)value);
      }
      break;

    case STATUS:
      if (value == null) {
        unsetStatus();
      } else {
        setStatus((String)value);
      }
      break;

    case OUTLET_ID:
      if (value == null) {
        unsetOutletId();
      } else {
        setOutletId((String)value);
      }
      break;

    case DELIVERY_STATUS:
      if (value == null) {
        unsetDeliveryStatus();
      } else {
        setDeliveryStatus((String)value);
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

    case MERCHANT_ID:
      return getMerchantId();

    case TYPE:
      return getType();

    case STATUS:
      return getStatus();

    case OUTLET_ID:
      return getOutletId();

    case DELIVERY_STATUS:
      return getDeliveryStatus();

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
    case MERCHANT_ID:
      return isSetMerchantId();
    case TYPE:
      return isSetType();
    case STATUS:
      return isSetStatus();
    case OUTLET_ID:
      return isSetOutletId();
    case DELIVERY_STATUS:
      return isSetDeliveryStatus();
    case PAGE:
      return isSetPage();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof QueryOrderByMerchantPhoneVoReq)
      return this.equals((QueryOrderByMerchantPhoneVoReq)that);
    return false;
  }

  public boolean equals(QueryOrderByMerchantPhoneVoReq that) {
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

    boolean this_present_merchantId = true && this.isSetMerchantId();
    boolean that_present_merchantId = true && that.isSetMerchantId();
    if (this_present_merchantId || that_present_merchantId) {
      if (!(this_present_merchantId && that_present_merchantId))
        return false;
      if (!this.merchantId.equals(that.merchantId))
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

    boolean this_present_status = true && this.isSetStatus();
    boolean that_present_status = true && that.isSetStatus();
    if (this_present_status || that_present_status) {
      if (!(this_present_status && that_present_status))
        return false;
      if (!this.status.equals(that.status))
        return false;
    }

    boolean this_present_outletId = true && this.isSetOutletId();
    boolean that_present_outletId = true && that.isSetOutletId();
    if (this_present_outletId || that_present_outletId) {
      if (!(this_present_outletId && that_present_outletId))
        return false;
      if (!this.outletId.equals(that.outletId))
        return false;
    }

    boolean this_present_deliveryStatus = true && this.isSetDeliveryStatus();
    boolean that_present_deliveryStatus = true && that.isSetDeliveryStatus();
    if (this_present_deliveryStatus || that_present_deliveryStatus) {
      if (!(this_present_deliveryStatus && that_present_deliveryStatus))
        return false;
      if (!this.deliveryStatus.equals(that.deliveryStatus))
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

    boolean present_merchantId = true && (isSetMerchantId());
    list.add(present_merchantId);
    if (present_merchantId)
      list.add(merchantId);

    boolean present_type = true && (isSetType());
    list.add(present_type);
    if (present_type)
      list.add(type);

    boolean present_status = true && (isSetStatus());
    list.add(present_status);
    if (present_status)
      list.add(status);

    boolean present_outletId = true && (isSetOutletId());
    list.add(present_outletId);
    if (present_outletId)
      list.add(outletId);

    boolean present_deliveryStatus = true && (isSetDeliveryStatus());
    list.add(present_deliveryStatus);
    if (present_deliveryStatus)
      list.add(deliveryStatus);

    boolean present_page = true && (isSetPage());
    list.add(present_page);
    if (present_page)
      list.add(page);

    return list.hashCode();
  }

  @Override
  public int compareTo(QueryOrderByMerchantPhoneVoReq other) {
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
    lastComparison = Boolean.valueOf(isSetMerchantId()).compareTo(other.isSetMerchantId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMerchantId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.merchantId, other.merchantId);
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
    lastComparison = Boolean.valueOf(isSetStatus()).compareTo(other.isSetStatus());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStatus()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.status, other.status);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOutletId()).compareTo(other.isSetOutletId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOutletId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.outletId, other.outletId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDeliveryStatus()).compareTo(other.isSetDeliveryStatus());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDeliveryStatus()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.deliveryStatus, other.deliveryStatus);
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
    StringBuilder sb = new StringBuilder("QueryOrderByMerchantPhoneVoReq(");
    boolean first = true;

    sb.append("clientId:");
    if (this.clientId == null) {
      sb.append("null");
    } else {
      sb.append(this.clientId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("merchantId:");
    if (this.merchantId == null) {
      sb.append("null");
    } else {
      sb.append(this.merchantId);
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
    sb.append("status:");
    if (this.status == null) {
      sb.append("null");
    } else {
      sb.append(this.status);
    }
    first = false;
    if (isSetOutletId()) {
      if (!first) sb.append(", ");
      sb.append("outletId:");
      if (this.outletId == null) {
        sb.append("null");
      } else {
        sb.append(this.outletId);
      }
      first = false;
    }
    if (isSetDeliveryStatus()) {
      if (!first) sb.append(", ");
      sb.append("deliveryStatus:");
      if (this.deliveryStatus == null) {
        sb.append("null");
      } else {
        sb.append(this.deliveryStatus);
      }
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
    if (merchantId == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'merchantId' was not present! Struct: " + toString());
    }
    if (type == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'type' was not present! Struct: " + toString());
    }
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class QueryOrderByMerchantPhoneVoReqStandardSchemeFactory implements SchemeFactory {
    public QueryOrderByMerchantPhoneVoReqStandardScheme getScheme() {
      return new QueryOrderByMerchantPhoneVoReqStandardScheme();
    }
  }

  private static class QueryOrderByMerchantPhoneVoReqStandardScheme extends StandardScheme<QueryOrderByMerchantPhoneVoReq> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, QueryOrderByMerchantPhoneVoReq struct) throws org.apache.thrift.TException {
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
          case 2: // MERCHANT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.merchantId = iprot.readString();
              struct.setMerchantIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.type = iprot.readString();
              struct.setTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // STATUS
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.status = iprot.readString();
              struct.setStatusIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // OUTLET_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.outletId = iprot.readString();
              struct.setOutletIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // DELIVERY_STATUS
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.deliveryStatus = iprot.readString();
              struct.setDeliveryStatusIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // PAGE
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
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, QueryOrderByMerchantPhoneVoReq struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.clientId != null) {
        oprot.writeFieldBegin(CLIENT_ID_FIELD_DESC);
        oprot.writeString(struct.clientId);
        oprot.writeFieldEnd();
      }
      if (struct.merchantId != null) {
        oprot.writeFieldBegin(MERCHANT_ID_FIELD_DESC);
        oprot.writeString(struct.merchantId);
        oprot.writeFieldEnd();
      }
      if (struct.type != null) {
        oprot.writeFieldBegin(TYPE_FIELD_DESC);
        oprot.writeString(struct.type);
        oprot.writeFieldEnd();
      }
      if (struct.status != null) {
        oprot.writeFieldBegin(STATUS_FIELD_DESC);
        oprot.writeString(struct.status);
        oprot.writeFieldEnd();
      }
      if (struct.outletId != null) {
        if (struct.isSetOutletId()) {
          oprot.writeFieldBegin(OUTLET_ID_FIELD_DESC);
          oprot.writeString(struct.outletId);
          oprot.writeFieldEnd();
        }
      }
      if (struct.deliveryStatus != null) {
        if (struct.isSetDeliveryStatus()) {
          oprot.writeFieldBegin(DELIVERY_STATUS_FIELD_DESC);
          oprot.writeString(struct.deliveryStatus);
          oprot.writeFieldEnd();
        }
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

  private static class QueryOrderByMerchantPhoneVoReqTupleSchemeFactory implements SchemeFactory {
    public QueryOrderByMerchantPhoneVoReqTupleScheme getScheme() {
      return new QueryOrderByMerchantPhoneVoReqTupleScheme();
    }
  }

  private static class QueryOrderByMerchantPhoneVoReqTupleScheme extends TupleScheme<QueryOrderByMerchantPhoneVoReq> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, QueryOrderByMerchantPhoneVoReq struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.clientId);
      oprot.writeString(struct.merchantId);
      oprot.writeString(struct.type);
      struct.page.write(oprot);
      BitSet optionals = new BitSet();
      if (struct.isSetStatus()) {
        optionals.set(0);
      }
      if (struct.isSetOutletId()) {
        optionals.set(1);
      }
      if (struct.isSetDeliveryStatus()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetStatus()) {
        oprot.writeString(struct.status);
      }
      if (struct.isSetOutletId()) {
        oprot.writeString(struct.outletId);
      }
      if (struct.isSetDeliveryStatus()) {
        oprot.writeString(struct.deliveryStatus);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, QueryOrderByMerchantPhoneVoReq struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.clientId = iprot.readString();
      struct.setClientIdIsSet(true);
      struct.merchantId = iprot.readString();
      struct.setMerchantIdIsSet(true);
      struct.type = iprot.readString();
      struct.setTypeIsSet(true);
      struct.page = new com.froad.thrift.vo.PageVo();
      struct.page.read(iprot);
      struct.setPageIsSet(true);
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.status = iprot.readString();
        struct.setStatusIsSet(true);
      }
      if (incoming.get(1)) {
        struct.outletId = iprot.readString();
        struct.setOutletIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.deliveryStatus = iprot.readString();
        struct.setDeliveryStatusIsSet(true);
      }
    }
  }

}
