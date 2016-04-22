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
 * 物流详情
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class ShippingDetailVo implements org.apache.thrift.TBase<ShippingDetailVo, ShippingDetailVo._Fields>, java.io.Serializable, Cloneable, Comparable<ShippingDetailVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ShippingDetailVo");

  private static final org.apache.thrift.protocol.TField DELIVERY_CORP_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("deliveryCorpId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField DELIVERY_CORP_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("deliveryCorpName", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField TRACKING_NO_FIELD_DESC = new org.apache.thrift.protocol.TField("trackingNo", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField SHIPPING_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("shippingTime", org.apache.thrift.protocol.TType.I64, (short)4);
  private static final org.apache.thrift.protocol.TField RECEIPT_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("receiptTime", org.apache.thrift.protocol.TType.I64, (short)5);
  private static final org.apache.thrift.protocol.TField SHIPPING_STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("shippingStatus", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField REMARK_FIELD_DESC = new org.apache.thrift.protocol.TField("remark", org.apache.thrift.protocol.TType.STRING, (short)7);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ShippingDetailVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ShippingDetailVoTupleSchemeFactory());
  }

  /**
   * 物流公司ID
   */
  public String deliveryCorpId; // required
  /**
   * 物流公司名称
   */
  public String deliveryCorpName; // required
  /**
   * 物流单号
   */
  public String trackingNo; // required
  /**
   * 发货时间
   */
  public long shippingTime; // required
  /**
   * 收货时间
   */
  public long receiptTime; // required
  /**
   * 收货状态
   */
  public String shippingStatus; // required
  /**
   * 备注
   */
  public String remark; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 物流公司ID
     */
    DELIVERY_CORP_ID((short)1, "deliveryCorpId"),
    /**
     * 物流公司名称
     */
    DELIVERY_CORP_NAME((short)2, "deliveryCorpName"),
    /**
     * 物流单号
     */
    TRACKING_NO((short)3, "trackingNo"),
    /**
     * 发货时间
     */
    SHIPPING_TIME((short)4, "shippingTime"),
    /**
     * 收货时间
     */
    RECEIPT_TIME((short)5, "receiptTime"),
    /**
     * 收货状态
     */
    SHIPPING_STATUS((short)6, "shippingStatus"),
    /**
     * 备注
     */
    REMARK((short)7, "remark");

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
        case 1: // DELIVERY_CORP_ID
          return DELIVERY_CORP_ID;
        case 2: // DELIVERY_CORP_NAME
          return DELIVERY_CORP_NAME;
        case 3: // TRACKING_NO
          return TRACKING_NO;
        case 4: // SHIPPING_TIME
          return SHIPPING_TIME;
        case 5: // RECEIPT_TIME
          return RECEIPT_TIME;
        case 6: // SHIPPING_STATUS
          return SHIPPING_STATUS;
        case 7: // REMARK
          return REMARK;
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
  private static final int __SHIPPINGTIME_ISSET_ID = 0;
  private static final int __RECEIPTTIME_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.DELIVERY_CORP_ID, new org.apache.thrift.meta_data.FieldMetaData("deliveryCorpId", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.DELIVERY_CORP_NAME, new org.apache.thrift.meta_data.FieldMetaData("deliveryCorpName", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TRACKING_NO, new org.apache.thrift.meta_data.FieldMetaData("trackingNo", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SHIPPING_TIME, new org.apache.thrift.meta_data.FieldMetaData("shippingTime", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.RECEIPT_TIME, new org.apache.thrift.meta_data.FieldMetaData("receiptTime", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.SHIPPING_STATUS, new org.apache.thrift.meta_data.FieldMetaData("shippingStatus", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.REMARK, new org.apache.thrift.meta_data.FieldMetaData("remark", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ShippingDetailVo.class, metaDataMap);
  }

  public ShippingDetailVo() {
  }

  public ShippingDetailVo(
    String deliveryCorpId,
    String deliveryCorpName,
    String trackingNo,
    long shippingTime,
    long receiptTime,
    String shippingStatus,
    String remark)
  {
    this();
    this.deliveryCorpId = deliveryCorpId;
    this.deliveryCorpName = deliveryCorpName;
    this.trackingNo = trackingNo;
    this.shippingTime = shippingTime;
    setShippingTimeIsSet(true);
    this.receiptTime = receiptTime;
    setReceiptTimeIsSet(true);
    this.shippingStatus = shippingStatus;
    this.remark = remark;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ShippingDetailVo(ShippingDetailVo other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetDeliveryCorpId()) {
      this.deliveryCorpId = other.deliveryCorpId;
    }
    if (other.isSetDeliveryCorpName()) {
      this.deliveryCorpName = other.deliveryCorpName;
    }
    if (other.isSetTrackingNo()) {
      this.trackingNo = other.trackingNo;
    }
    this.shippingTime = other.shippingTime;
    this.receiptTime = other.receiptTime;
    if (other.isSetShippingStatus()) {
      this.shippingStatus = other.shippingStatus;
    }
    if (other.isSetRemark()) {
      this.remark = other.remark;
    }
  }

  public ShippingDetailVo deepCopy() {
    return new ShippingDetailVo(this);
  }

  @Override
  public void clear() {
    this.deliveryCorpId = null;
    this.deliveryCorpName = null;
    this.trackingNo = null;
    setShippingTimeIsSet(false);
    this.shippingTime = 0;
    setReceiptTimeIsSet(false);
    this.receiptTime = 0;
    this.shippingStatus = null;
    this.remark = null;
  }

  /**
   * 物流公司ID
   */
  public String getDeliveryCorpId() {
    return this.deliveryCorpId;
  }

  /**
   * 物流公司ID
   */
  public ShippingDetailVo setDeliveryCorpId(String deliveryCorpId) {
    this.deliveryCorpId = deliveryCorpId;
    return this;
  }

  public void unsetDeliveryCorpId() {
    this.deliveryCorpId = null;
  }

  /** Returns true if field deliveryCorpId is set (has been assigned a value) and false otherwise */
  public boolean isSetDeliveryCorpId() {
    return this.deliveryCorpId != null;
  }

  public void setDeliveryCorpIdIsSet(boolean value) {
    if (!value) {
      this.deliveryCorpId = null;
    }
  }

  /**
   * 物流公司名称
   */
  public String getDeliveryCorpName() {
    return this.deliveryCorpName;
  }

  /**
   * 物流公司名称
   */
  public ShippingDetailVo setDeliveryCorpName(String deliveryCorpName) {
    this.deliveryCorpName = deliveryCorpName;
    return this;
  }

  public void unsetDeliveryCorpName() {
    this.deliveryCorpName = null;
  }

  /** Returns true if field deliveryCorpName is set (has been assigned a value) and false otherwise */
  public boolean isSetDeliveryCorpName() {
    return this.deliveryCorpName != null;
  }

  public void setDeliveryCorpNameIsSet(boolean value) {
    if (!value) {
      this.deliveryCorpName = null;
    }
  }

  /**
   * 物流单号
   */
  public String getTrackingNo() {
    return this.trackingNo;
  }

  /**
   * 物流单号
   */
  public ShippingDetailVo setTrackingNo(String trackingNo) {
    this.trackingNo = trackingNo;
    return this;
  }

  public void unsetTrackingNo() {
    this.trackingNo = null;
  }

  /** Returns true if field trackingNo is set (has been assigned a value) and false otherwise */
  public boolean isSetTrackingNo() {
    return this.trackingNo != null;
  }

  public void setTrackingNoIsSet(boolean value) {
    if (!value) {
      this.trackingNo = null;
    }
  }

  /**
   * 发货时间
   */
  public long getShippingTime() {
    return this.shippingTime;
  }

  /**
   * 发货时间
   */
  public ShippingDetailVo setShippingTime(long shippingTime) {
    this.shippingTime = shippingTime;
    setShippingTimeIsSet(true);
    return this;
  }

  public void unsetShippingTime() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SHIPPINGTIME_ISSET_ID);
  }

  /** Returns true if field shippingTime is set (has been assigned a value) and false otherwise */
  public boolean isSetShippingTime() {
    return EncodingUtils.testBit(__isset_bitfield, __SHIPPINGTIME_ISSET_ID);
  }

  public void setShippingTimeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SHIPPINGTIME_ISSET_ID, value);
  }

  /**
   * 收货时间
   */
  public long getReceiptTime() {
    return this.receiptTime;
  }

  /**
   * 收货时间
   */
  public ShippingDetailVo setReceiptTime(long receiptTime) {
    this.receiptTime = receiptTime;
    setReceiptTimeIsSet(true);
    return this;
  }

  public void unsetReceiptTime() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __RECEIPTTIME_ISSET_ID);
  }

  /** Returns true if field receiptTime is set (has been assigned a value) and false otherwise */
  public boolean isSetReceiptTime() {
    return EncodingUtils.testBit(__isset_bitfield, __RECEIPTTIME_ISSET_ID);
  }

  public void setReceiptTimeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __RECEIPTTIME_ISSET_ID, value);
  }

  /**
   * 收货状态
   */
  public String getShippingStatus() {
    return this.shippingStatus;
  }

  /**
   * 收货状态
   */
  public ShippingDetailVo setShippingStatus(String shippingStatus) {
    this.shippingStatus = shippingStatus;
    return this;
  }

  public void unsetShippingStatus() {
    this.shippingStatus = null;
  }

  /** Returns true if field shippingStatus is set (has been assigned a value) and false otherwise */
  public boolean isSetShippingStatus() {
    return this.shippingStatus != null;
  }

  public void setShippingStatusIsSet(boolean value) {
    if (!value) {
      this.shippingStatus = null;
    }
  }

  /**
   * 备注
   */
  public String getRemark() {
    return this.remark;
  }

  /**
   * 备注
   */
  public ShippingDetailVo setRemark(String remark) {
    this.remark = remark;
    return this;
  }

  public void unsetRemark() {
    this.remark = null;
  }

  /** Returns true if field remark is set (has been assigned a value) and false otherwise */
  public boolean isSetRemark() {
    return this.remark != null;
  }

  public void setRemarkIsSet(boolean value) {
    if (!value) {
      this.remark = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case DELIVERY_CORP_ID:
      if (value == null) {
        unsetDeliveryCorpId();
      } else {
        setDeliveryCorpId((String)value);
      }
      break;

    case DELIVERY_CORP_NAME:
      if (value == null) {
        unsetDeliveryCorpName();
      } else {
        setDeliveryCorpName((String)value);
      }
      break;

    case TRACKING_NO:
      if (value == null) {
        unsetTrackingNo();
      } else {
        setTrackingNo((String)value);
      }
      break;

    case SHIPPING_TIME:
      if (value == null) {
        unsetShippingTime();
      } else {
        setShippingTime((Long)value);
      }
      break;

    case RECEIPT_TIME:
      if (value == null) {
        unsetReceiptTime();
      } else {
        setReceiptTime((Long)value);
      }
      break;

    case SHIPPING_STATUS:
      if (value == null) {
        unsetShippingStatus();
      } else {
        setShippingStatus((String)value);
      }
      break;

    case REMARK:
      if (value == null) {
        unsetRemark();
      } else {
        setRemark((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case DELIVERY_CORP_ID:
      return getDeliveryCorpId();

    case DELIVERY_CORP_NAME:
      return getDeliveryCorpName();

    case TRACKING_NO:
      return getTrackingNo();

    case SHIPPING_TIME:
      return Long.valueOf(getShippingTime());

    case RECEIPT_TIME:
      return Long.valueOf(getReceiptTime());

    case SHIPPING_STATUS:
      return getShippingStatus();

    case REMARK:
      return getRemark();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case DELIVERY_CORP_ID:
      return isSetDeliveryCorpId();
    case DELIVERY_CORP_NAME:
      return isSetDeliveryCorpName();
    case TRACKING_NO:
      return isSetTrackingNo();
    case SHIPPING_TIME:
      return isSetShippingTime();
    case RECEIPT_TIME:
      return isSetReceiptTime();
    case SHIPPING_STATUS:
      return isSetShippingStatus();
    case REMARK:
      return isSetRemark();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ShippingDetailVo)
      return this.equals((ShippingDetailVo)that);
    return false;
  }

  public boolean equals(ShippingDetailVo that) {
    if (that == null)
      return false;

    boolean this_present_deliveryCorpId = true && this.isSetDeliveryCorpId();
    boolean that_present_deliveryCorpId = true && that.isSetDeliveryCorpId();
    if (this_present_deliveryCorpId || that_present_deliveryCorpId) {
      if (!(this_present_deliveryCorpId && that_present_deliveryCorpId))
        return false;
      if (!this.deliveryCorpId.equals(that.deliveryCorpId))
        return false;
    }

    boolean this_present_deliveryCorpName = true && this.isSetDeliveryCorpName();
    boolean that_present_deliveryCorpName = true && that.isSetDeliveryCorpName();
    if (this_present_deliveryCorpName || that_present_deliveryCorpName) {
      if (!(this_present_deliveryCorpName && that_present_deliveryCorpName))
        return false;
      if (!this.deliveryCorpName.equals(that.deliveryCorpName))
        return false;
    }

    boolean this_present_trackingNo = true && this.isSetTrackingNo();
    boolean that_present_trackingNo = true && that.isSetTrackingNo();
    if (this_present_trackingNo || that_present_trackingNo) {
      if (!(this_present_trackingNo && that_present_trackingNo))
        return false;
      if (!this.trackingNo.equals(that.trackingNo))
        return false;
    }

    boolean this_present_shippingTime = true;
    boolean that_present_shippingTime = true;
    if (this_present_shippingTime || that_present_shippingTime) {
      if (!(this_present_shippingTime && that_present_shippingTime))
        return false;
      if (this.shippingTime != that.shippingTime)
        return false;
    }

    boolean this_present_receiptTime = true;
    boolean that_present_receiptTime = true;
    if (this_present_receiptTime || that_present_receiptTime) {
      if (!(this_present_receiptTime && that_present_receiptTime))
        return false;
      if (this.receiptTime != that.receiptTime)
        return false;
    }

    boolean this_present_shippingStatus = true && this.isSetShippingStatus();
    boolean that_present_shippingStatus = true && that.isSetShippingStatus();
    if (this_present_shippingStatus || that_present_shippingStatus) {
      if (!(this_present_shippingStatus && that_present_shippingStatus))
        return false;
      if (!this.shippingStatus.equals(that.shippingStatus))
        return false;
    }

    boolean this_present_remark = true && this.isSetRemark();
    boolean that_present_remark = true && that.isSetRemark();
    if (this_present_remark || that_present_remark) {
      if (!(this_present_remark && that_present_remark))
        return false;
      if (!this.remark.equals(that.remark))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_deliveryCorpId = true && (isSetDeliveryCorpId());
    list.add(present_deliveryCorpId);
    if (present_deliveryCorpId)
      list.add(deliveryCorpId);

    boolean present_deliveryCorpName = true && (isSetDeliveryCorpName());
    list.add(present_deliveryCorpName);
    if (present_deliveryCorpName)
      list.add(deliveryCorpName);

    boolean present_trackingNo = true && (isSetTrackingNo());
    list.add(present_trackingNo);
    if (present_trackingNo)
      list.add(trackingNo);

    boolean present_shippingTime = true;
    list.add(present_shippingTime);
    if (present_shippingTime)
      list.add(shippingTime);

    boolean present_receiptTime = true;
    list.add(present_receiptTime);
    if (present_receiptTime)
      list.add(receiptTime);

    boolean present_shippingStatus = true && (isSetShippingStatus());
    list.add(present_shippingStatus);
    if (present_shippingStatus)
      list.add(shippingStatus);

    boolean present_remark = true && (isSetRemark());
    list.add(present_remark);
    if (present_remark)
      list.add(remark);

    return list.hashCode();
  }

  @Override
  public int compareTo(ShippingDetailVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetDeliveryCorpId()).compareTo(other.isSetDeliveryCorpId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDeliveryCorpId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.deliveryCorpId, other.deliveryCorpId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDeliveryCorpName()).compareTo(other.isSetDeliveryCorpName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDeliveryCorpName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.deliveryCorpName, other.deliveryCorpName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTrackingNo()).compareTo(other.isSetTrackingNo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTrackingNo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.trackingNo, other.trackingNo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetShippingTime()).compareTo(other.isSetShippingTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetShippingTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.shippingTime, other.shippingTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetReceiptTime()).compareTo(other.isSetReceiptTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetReceiptTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.receiptTime, other.receiptTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetShippingStatus()).compareTo(other.isSetShippingStatus());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetShippingStatus()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.shippingStatus, other.shippingStatus);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRemark()).compareTo(other.isSetRemark());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRemark()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.remark, other.remark);
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
    StringBuilder sb = new StringBuilder("ShippingDetailVo(");
    boolean first = true;

    sb.append("deliveryCorpId:");
    if (this.deliveryCorpId == null) {
      sb.append("null");
    } else {
      sb.append(this.deliveryCorpId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("deliveryCorpName:");
    if (this.deliveryCorpName == null) {
      sb.append("null");
    } else {
      sb.append(this.deliveryCorpName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("trackingNo:");
    if (this.trackingNo == null) {
      sb.append("null");
    } else {
      sb.append(this.trackingNo);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("shippingTime:");
    sb.append(this.shippingTime);
    first = false;
    if (!first) sb.append(", ");
    sb.append("receiptTime:");
    sb.append(this.receiptTime);
    first = false;
    if (!first) sb.append(", ");
    sb.append("shippingStatus:");
    if (this.shippingStatus == null) {
      sb.append("null");
    } else {
      sb.append(this.shippingStatus);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("remark:");
    if (this.remark == null) {
      sb.append("null");
    } else {
      sb.append(this.remark);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (deliveryCorpId == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'deliveryCorpId' was not present! Struct: " + toString());
    }
    if (deliveryCorpName == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'deliveryCorpName' was not present! Struct: " + toString());
    }
    if (trackingNo == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'trackingNo' was not present! Struct: " + toString());
    }
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

  private static class ShippingDetailVoStandardSchemeFactory implements SchemeFactory {
    public ShippingDetailVoStandardScheme getScheme() {
      return new ShippingDetailVoStandardScheme();
    }
  }

  private static class ShippingDetailVoStandardScheme extends StandardScheme<ShippingDetailVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ShippingDetailVo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // DELIVERY_CORP_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.deliveryCorpId = iprot.readString();
              struct.setDeliveryCorpIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // DELIVERY_CORP_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.deliveryCorpName = iprot.readString();
              struct.setDeliveryCorpNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TRACKING_NO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.trackingNo = iprot.readString();
              struct.setTrackingNoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // SHIPPING_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.shippingTime = iprot.readI64();
              struct.setShippingTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // RECEIPT_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.receiptTime = iprot.readI64();
              struct.setReceiptTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // SHIPPING_STATUS
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.shippingStatus = iprot.readString();
              struct.setShippingStatusIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // REMARK
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.remark = iprot.readString();
              struct.setRemarkIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ShippingDetailVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.deliveryCorpId != null) {
        oprot.writeFieldBegin(DELIVERY_CORP_ID_FIELD_DESC);
        oprot.writeString(struct.deliveryCorpId);
        oprot.writeFieldEnd();
      }
      if (struct.deliveryCorpName != null) {
        oprot.writeFieldBegin(DELIVERY_CORP_NAME_FIELD_DESC);
        oprot.writeString(struct.deliveryCorpName);
        oprot.writeFieldEnd();
      }
      if (struct.trackingNo != null) {
        oprot.writeFieldBegin(TRACKING_NO_FIELD_DESC);
        oprot.writeString(struct.trackingNo);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(SHIPPING_TIME_FIELD_DESC);
      oprot.writeI64(struct.shippingTime);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(RECEIPT_TIME_FIELD_DESC);
      oprot.writeI64(struct.receiptTime);
      oprot.writeFieldEnd();
      if (struct.shippingStatus != null) {
        oprot.writeFieldBegin(SHIPPING_STATUS_FIELD_DESC);
        oprot.writeString(struct.shippingStatus);
        oprot.writeFieldEnd();
      }
      if (struct.remark != null) {
        oprot.writeFieldBegin(REMARK_FIELD_DESC);
        oprot.writeString(struct.remark);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ShippingDetailVoTupleSchemeFactory implements SchemeFactory {
    public ShippingDetailVoTupleScheme getScheme() {
      return new ShippingDetailVoTupleScheme();
    }
  }

  private static class ShippingDetailVoTupleScheme extends TupleScheme<ShippingDetailVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ShippingDetailVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.deliveryCorpId);
      oprot.writeString(struct.deliveryCorpName);
      oprot.writeString(struct.trackingNo);
      BitSet optionals = new BitSet();
      if (struct.isSetShippingTime()) {
        optionals.set(0);
      }
      if (struct.isSetReceiptTime()) {
        optionals.set(1);
      }
      if (struct.isSetShippingStatus()) {
        optionals.set(2);
      }
      if (struct.isSetRemark()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetShippingTime()) {
        oprot.writeI64(struct.shippingTime);
      }
      if (struct.isSetReceiptTime()) {
        oprot.writeI64(struct.receiptTime);
      }
      if (struct.isSetShippingStatus()) {
        oprot.writeString(struct.shippingStatus);
      }
      if (struct.isSetRemark()) {
        oprot.writeString(struct.remark);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ShippingDetailVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.deliveryCorpId = iprot.readString();
      struct.setDeliveryCorpIdIsSet(true);
      struct.deliveryCorpName = iprot.readString();
      struct.setDeliveryCorpNameIsSet(true);
      struct.trackingNo = iprot.readString();
      struct.setTrackingNoIsSet(true);
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.shippingTime = iprot.readI64();
        struct.setShippingTimeIsSet(true);
      }
      if (incoming.get(1)) {
        struct.receiptTime = iprot.readI64();
        struct.setReceiptTimeIsSet(true);
      }
      if (incoming.get(2)) {
        struct.shippingStatus = iprot.readString();
        struct.setShippingStatusIsSet(true);
      }
      if (incoming.get(3)) {
        struct.remark = iprot.readString();
        struct.setRemarkIsSet(true);
      }
    }
  }

}
