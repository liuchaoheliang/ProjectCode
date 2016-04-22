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
 * 金额指标信息
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class ReportAmountQuotaVo implements org.apache.thrift.TBase<ReportAmountQuotaVo, ReportAmountQuotaVo._Fields>, java.io.Serializable, Cloneable, Comparable<ReportAmountQuotaVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ReportAmountQuotaVo");

  private static final org.apache.thrift.protocol.TField ORDER_AMOUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("orderAmount", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField ORDER_TOTAL_PRICE_FIELD_DESC = new org.apache.thrift.protocol.TField("orderTotalPrice", org.apache.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.thrift.protocol.TField REFUND_AMOUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("refundAmount", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField REFUND_TOTAL_AMOUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("refundTotalAmount", org.apache.thrift.protocol.TType.I64, (short)4);
  private static final org.apache.thrift.protocol.TField AMOUNT_TURNOVER_FIELD_DESC = new org.apache.thrift.protocol.TField("amountTurnover", org.apache.thrift.protocol.TType.I64, (short)5);
  private static final org.apache.thrift.protocol.TField AMOUNT_CUMULATI_TURNOVER_FIELD_DESC = new org.apache.thrift.protocol.TField("amountCumulatiTurnover", org.apache.thrift.protocol.TType.I64, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ReportAmountQuotaVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ReportAmountQuotaVoTupleSchemeFactory());
  }

  /**
   * 金额*
   */
  public long orderAmount; // optional
  /**
   * 累积金额*
   */
  public long orderTotalPrice; // optional
  /**
   * 退款金额*
   */
  public long refundAmount; // optional
  /**
   * orderAmount*
   */
  public long refundTotalAmount; // optional
  /**
   * 成交金额=金额-退款金额*
   */
  public long amountTurnover; // optional
  /**
   * 累积成交金额=累积金额-累积退款金额*
   */
  public long amountCumulatiTurnover; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 金额*
     */
    ORDER_AMOUNT((short)1, "orderAmount"),
    /**
     * 累积金额*
     */
    ORDER_TOTAL_PRICE((short)2, "orderTotalPrice"),
    /**
     * 退款金额*
     */
    REFUND_AMOUNT((short)3, "refundAmount"),
    /**
     * orderAmount*
     */
    REFUND_TOTAL_AMOUNT((short)4, "refundTotalAmount"),
    /**
     * 成交金额=金额-退款金额*
     */
    AMOUNT_TURNOVER((short)5, "amountTurnover"),
    /**
     * 累积成交金额=累积金额-累积退款金额*
     */
    AMOUNT_CUMULATI_TURNOVER((short)6, "amountCumulatiTurnover");

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
        case 1: // ORDER_AMOUNT
          return ORDER_AMOUNT;
        case 2: // ORDER_TOTAL_PRICE
          return ORDER_TOTAL_PRICE;
        case 3: // REFUND_AMOUNT
          return REFUND_AMOUNT;
        case 4: // REFUND_TOTAL_AMOUNT
          return REFUND_TOTAL_AMOUNT;
        case 5: // AMOUNT_TURNOVER
          return AMOUNT_TURNOVER;
        case 6: // AMOUNT_CUMULATI_TURNOVER
          return AMOUNT_CUMULATI_TURNOVER;
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
  private static final int __ORDERAMOUNT_ISSET_ID = 0;
  private static final int __ORDERTOTALPRICE_ISSET_ID = 1;
  private static final int __REFUNDAMOUNT_ISSET_ID = 2;
  private static final int __REFUNDTOTALAMOUNT_ISSET_ID = 3;
  private static final int __AMOUNTTURNOVER_ISSET_ID = 4;
  private static final int __AMOUNTCUMULATITURNOVER_ISSET_ID = 5;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.ORDER_AMOUNT,_Fields.ORDER_TOTAL_PRICE,_Fields.REFUND_AMOUNT,_Fields.REFUND_TOTAL_AMOUNT,_Fields.AMOUNT_TURNOVER,_Fields.AMOUNT_CUMULATI_TURNOVER};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ORDER_AMOUNT, new org.apache.thrift.meta_data.FieldMetaData("orderAmount", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.ORDER_TOTAL_PRICE, new org.apache.thrift.meta_data.FieldMetaData("orderTotalPrice", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.REFUND_AMOUNT, new org.apache.thrift.meta_data.FieldMetaData("refundAmount", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.REFUND_TOTAL_AMOUNT, new org.apache.thrift.meta_data.FieldMetaData("refundTotalAmount", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.AMOUNT_TURNOVER, new org.apache.thrift.meta_data.FieldMetaData("amountTurnover", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.AMOUNT_CUMULATI_TURNOVER, new org.apache.thrift.meta_data.FieldMetaData("amountCumulatiTurnover", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ReportAmountQuotaVo.class, metaDataMap);
  }

  public ReportAmountQuotaVo() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ReportAmountQuotaVo(ReportAmountQuotaVo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.orderAmount = other.orderAmount;
    this.orderTotalPrice = other.orderTotalPrice;
    this.refundAmount = other.refundAmount;
    this.refundTotalAmount = other.refundTotalAmount;
    this.amountTurnover = other.amountTurnover;
    this.amountCumulatiTurnover = other.amountCumulatiTurnover;
  }

  public ReportAmountQuotaVo deepCopy() {
    return new ReportAmountQuotaVo(this);
  }

  @Override
  public void clear() {
    setOrderAmountIsSet(false);
    this.orderAmount = 0;
    setOrderTotalPriceIsSet(false);
    this.orderTotalPrice = 0;
    setRefundAmountIsSet(false);
    this.refundAmount = 0;
    setRefundTotalAmountIsSet(false);
    this.refundTotalAmount = 0;
    setAmountTurnoverIsSet(false);
    this.amountTurnover = 0;
    setAmountCumulatiTurnoverIsSet(false);
    this.amountCumulatiTurnover = 0;
  }

  /**
   * 金额*
   */
  public long getOrderAmount() {
    return this.orderAmount;
  }

  /**
   * 金额*
   */
  public ReportAmountQuotaVo setOrderAmount(long orderAmount) {
    this.orderAmount = orderAmount;
    setOrderAmountIsSet(true);
    return this;
  }

  public void unsetOrderAmount() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ORDERAMOUNT_ISSET_ID);
  }

  /** Returns true if field orderAmount is set (has been assigned a value) and false otherwise */
  public boolean isSetOrderAmount() {
    return EncodingUtils.testBit(__isset_bitfield, __ORDERAMOUNT_ISSET_ID);
  }

  public void setOrderAmountIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ORDERAMOUNT_ISSET_ID, value);
  }

  /**
   * 累积金额*
   */
  public long getOrderTotalPrice() {
    return this.orderTotalPrice;
  }

  /**
   * 累积金额*
   */
  public ReportAmountQuotaVo setOrderTotalPrice(long orderTotalPrice) {
    this.orderTotalPrice = orderTotalPrice;
    setOrderTotalPriceIsSet(true);
    return this;
  }

  public void unsetOrderTotalPrice() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ORDERTOTALPRICE_ISSET_ID);
  }

  /** Returns true if field orderTotalPrice is set (has been assigned a value) and false otherwise */
  public boolean isSetOrderTotalPrice() {
    return EncodingUtils.testBit(__isset_bitfield, __ORDERTOTALPRICE_ISSET_ID);
  }

  public void setOrderTotalPriceIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ORDERTOTALPRICE_ISSET_ID, value);
  }

  /**
   * 退款金额*
   */
  public long getRefundAmount() {
    return this.refundAmount;
  }

  /**
   * 退款金额*
   */
  public ReportAmountQuotaVo setRefundAmount(long refundAmount) {
    this.refundAmount = refundAmount;
    setRefundAmountIsSet(true);
    return this;
  }

  public void unsetRefundAmount() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __REFUNDAMOUNT_ISSET_ID);
  }

  /** Returns true if field refundAmount is set (has been assigned a value) and false otherwise */
  public boolean isSetRefundAmount() {
    return EncodingUtils.testBit(__isset_bitfield, __REFUNDAMOUNT_ISSET_ID);
  }

  public void setRefundAmountIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __REFUNDAMOUNT_ISSET_ID, value);
  }

  /**
   * orderAmount*
   */
  public long getRefundTotalAmount() {
    return this.refundTotalAmount;
  }

  /**
   * orderAmount*
   */
  public ReportAmountQuotaVo setRefundTotalAmount(long refundTotalAmount) {
    this.refundTotalAmount = refundTotalAmount;
    setRefundTotalAmountIsSet(true);
    return this;
  }

  public void unsetRefundTotalAmount() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __REFUNDTOTALAMOUNT_ISSET_ID);
  }

  /** Returns true if field refundTotalAmount is set (has been assigned a value) and false otherwise */
  public boolean isSetRefundTotalAmount() {
    return EncodingUtils.testBit(__isset_bitfield, __REFUNDTOTALAMOUNT_ISSET_ID);
  }

  public void setRefundTotalAmountIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __REFUNDTOTALAMOUNT_ISSET_ID, value);
  }

  /**
   * 成交金额=金额-退款金额*
   */
  public long getAmountTurnover() {
    return this.amountTurnover;
  }

  /**
   * 成交金额=金额-退款金额*
   */
  public ReportAmountQuotaVo setAmountTurnover(long amountTurnover) {
    this.amountTurnover = amountTurnover;
    setAmountTurnoverIsSet(true);
    return this;
  }

  public void unsetAmountTurnover() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __AMOUNTTURNOVER_ISSET_ID);
  }

  /** Returns true if field amountTurnover is set (has been assigned a value) and false otherwise */
  public boolean isSetAmountTurnover() {
    return EncodingUtils.testBit(__isset_bitfield, __AMOUNTTURNOVER_ISSET_ID);
  }

  public void setAmountTurnoverIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __AMOUNTTURNOVER_ISSET_ID, value);
  }

  /**
   * 累积成交金额=累积金额-累积退款金额*
   */
  public long getAmountCumulatiTurnover() {
    return this.amountCumulatiTurnover;
  }

  /**
   * 累积成交金额=累积金额-累积退款金额*
   */
  public ReportAmountQuotaVo setAmountCumulatiTurnover(long amountCumulatiTurnover) {
    this.amountCumulatiTurnover = amountCumulatiTurnover;
    setAmountCumulatiTurnoverIsSet(true);
    return this;
  }

  public void unsetAmountCumulatiTurnover() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __AMOUNTCUMULATITURNOVER_ISSET_ID);
  }

  /** Returns true if field amountCumulatiTurnover is set (has been assigned a value) and false otherwise */
  public boolean isSetAmountCumulatiTurnover() {
    return EncodingUtils.testBit(__isset_bitfield, __AMOUNTCUMULATITURNOVER_ISSET_ID);
  }

  public void setAmountCumulatiTurnoverIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __AMOUNTCUMULATITURNOVER_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ORDER_AMOUNT:
      if (value == null) {
        unsetOrderAmount();
      } else {
        setOrderAmount((Long)value);
      }
      break;

    case ORDER_TOTAL_PRICE:
      if (value == null) {
        unsetOrderTotalPrice();
      } else {
        setOrderTotalPrice((Long)value);
      }
      break;

    case REFUND_AMOUNT:
      if (value == null) {
        unsetRefundAmount();
      } else {
        setRefundAmount((Long)value);
      }
      break;

    case REFUND_TOTAL_AMOUNT:
      if (value == null) {
        unsetRefundTotalAmount();
      } else {
        setRefundTotalAmount((Long)value);
      }
      break;

    case AMOUNT_TURNOVER:
      if (value == null) {
        unsetAmountTurnover();
      } else {
        setAmountTurnover((Long)value);
      }
      break;

    case AMOUNT_CUMULATI_TURNOVER:
      if (value == null) {
        unsetAmountCumulatiTurnover();
      } else {
        setAmountCumulatiTurnover((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ORDER_AMOUNT:
      return Long.valueOf(getOrderAmount());

    case ORDER_TOTAL_PRICE:
      return Long.valueOf(getOrderTotalPrice());

    case REFUND_AMOUNT:
      return Long.valueOf(getRefundAmount());

    case REFUND_TOTAL_AMOUNT:
      return Long.valueOf(getRefundTotalAmount());

    case AMOUNT_TURNOVER:
      return Long.valueOf(getAmountTurnover());

    case AMOUNT_CUMULATI_TURNOVER:
      return Long.valueOf(getAmountCumulatiTurnover());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ORDER_AMOUNT:
      return isSetOrderAmount();
    case ORDER_TOTAL_PRICE:
      return isSetOrderTotalPrice();
    case REFUND_AMOUNT:
      return isSetRefundAmount();
    case REFUND_TOTAL_AMOUNT:
      return isSetRefundTotalAmount();
    case AMOUNT_TURNOVER:
      return isSetAmountTurnover();
    case AMOUNT_CUMULATI_TURNOVER:
      return isSetAmountCumulatiTurnover();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ReportAmountQuotaVo)
      return this.equals((ReportAmountQuotaVo)that);
    return false;
  }

  public boolean equals(ReportAmountQuotaVo that) {
    if (that == null)
      return false;

    boolean this_present_orderAmount = true && this.isSetOrderAmount();
    boolean that_present_orderAmount = true && that.isSetOrderAmount();
    if (this_present_orderAmount || that_present_orderAmount) {
      if (!(this_present_orderAmount && that_present_orderAmount))
        return false;
      if (this.orderAmount != that.orderAmount)
        return false;
    }

    boolean this_present_orderTotalPrice = true && this.isSetOrderTotalPrice();
    boolean that_present_orderTotalPrice = true && that.isSetOrderTotalPrice();
    if (this_present_orderTotalPrice || that_present_orderTotalPrice) {
      if (!(this_present_orderTotalPrice && that_present_orderTotalPrice))
        return false;
      if (this.orderTotalPrice != that.orderTotalPrice)
        return false;
    }

    boolean this_present_refundAmount = true && this.isSetRefundAmount();
    boolean that_present_refundAmount = true && that.isSetRefundAmount();
    if (this_present_refundAmount || that_present_refundAmount) {
      if (!(this_present_refundAmount && that_present_refundAmount))
        return false;
      if (this.refundAmount != that.refundAmount)
        return false;
    }

    boolean this_present_refundTotalAmount = true && this.isSetRefundTotalAmount();
    boolean that_present_refundTotalAmount = true && that.isSetRefundTotalAmount();
    if (this_present_refundTotalAmount || that_present_refundTotalAmount) {
      if (!(this_present_refundTotalAmount && that_present_refundTotalAmount))
        return false;
      if (this.refundTotalAmount != that.refundTotalAmount)
        return false;
    }

    boolean this_present_amountTurnover = true && this.isSetAmountTurnover();
    boolean that_present_amountTurnover = true && that.isSetAmountTurnover();
    if (this_present_amountTurnover || that_present_amountTurnover) {
      if (!(this_present_amountTurnover && that_present_amountTurnover))
        return false;
      if (this.amountTurnover != that.amountTurnover)
        return false;
    }

    boolean this_present_amountCumulatiTurnover = true && this.isSetAmountCumulatiTurnover();
    boolean that_present_amountCumulatiTurnover = true && that.isSetAmountCumulatiTurnover();
    if (this_present_amountCumulatiTurnover || that_present_amountCumulatiTurnover) {
      if (!(this_present_amountCumulatiTurnover && that_present_amountCumulatiTurnover))
        return false;
      if (this.amountCumulatiTurnover != that.amountCumulatiTurnover)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_orderAmount = true && (isSetOrderAmount());
    list.add(present_orderAmount);
    if (present_orderAmount)
      list.add(orderAmount);

    boolean present_orderTotalPrice = true && (isSetOrderTotalPrice());
    list.add(present_orderTotalPrice);
    if (present_orderTotalPrice)
      list.add(orderTotalPrice);

    boolean present_refundAmount = true && (isSetRefundAmount());
    list.add(present_refundAmount);
    if (present_refundAmount)
      list.add(refundAmount);

    boolean present_refundTotalAmount = true && (isSetRefundTotalAmount());
    list.add(present_refundTotalAmount);
    if (present_refundTotalAmount)
      list.add(refundTotalAmount);

    boolean present_amountTurnover = true && (isSetAmountTurnover());
    list.add(present_amountTurnover);
    if (present_amountTurnover)
      list.add(amountTurnover);

    boolean present_amountCumulatiTurnover = true && (isSetAmountCumulatiTurnover());
    list.add(present_amountCumulatiTurnover);
    if (present_amountCumulatiTurnover)
      list.add(amountCumulatiTurnover);

    return list.hashCode();
  }

  @Override
  public int compareTo(ReportAmountQuotaVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetOrderAmount()).compareTo(other.isSetOrderAmount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOrderAmount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.orderAmount, other.orderAmount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOrderTotalPrice()).compareTo(other.isSetOrderTotalPrice());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOrderTotalPrice()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.orderTotalPrice, other.orderTotalPrice);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRefundAmount()).compareTo(other.isSetRefundAmount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRefundAmount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.refundAmount, other.refundAmount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRefundTotalAmount()).compareTo(other.isSetRefundTotalAmount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRefundTotalAmount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.refundTotalAmount, other.refundTotalAmount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAmountTurnover()).compareTo(other.isSetAmountTurnover());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAmountTurnover()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.amountTurnover, other.amountTurnover);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAmountCumulatiTurnover()).compareTo(other.isSetAmountCumulatiTurnover());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAmountCumulatiTurnover()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.amountCumulatiTurnover, other.amountCumulatiTurnover);
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
    StringBuilder sb = new StringBuilder("ReportAmountQuotaVo(");
    boolean first = true;

    if (isSetOrderAmount()) {
      sb.append("orderAmount:");
      sb.append(this.orderAmount);
      first = false;
    }
    if (isSetOrderTotalPrice()) {
      if (!first) sb.append(", ");
      sb.append("orderTotalPrice:");
      sb.append(this.orderTotalPrice);
      first = false;
    }
    if (isSetRefundAmount()) {
      if (!first) sb.append(", ");
      sb.append("refundAmount:");
      sb.append(this.refundAmount);
      first = false;
    }
    if (isSetRefundTotalAmount()) {
      if (!first) sb.append(", ");
      sb.append("refundTotalAmount:");
      sb.append(this.refundTotalAmount);
      first = false;
    }
    if (isSetAmountTurnover()) {
      if (!first) sb.append(", ");
      sb.append("amountTurnover:");
      sb.append(this.amountTurnover);
      first = false;
    }
    if (isSetAmountCumulatiTurnover()) {
      if (!first) sb.append(", ");
      sb.append("amountCumulatiTurnover:");
      sb.append(this.amountCumulatiTurnover);
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

  private static class ReportAmountQuotaVoStandardSchemeFactory implements SchemeFactory {
    public ReportAmountQuotaVoStandardScheme getScheme() {
      return new ReportAmountQuotaVoStandardScheme();
    }
  }

  private static class ReportAmountQuotaVoStandardScheme extends StandardScheme<ReportAmountQuotaVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ReportAmountQuotaVo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ORDER_AMOUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.orderAmount = iprot.readI64();
              struct.setOrderAmountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // ORDER_TOTAL_PRICE
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.orderTotalPrice = iprot.readI64();
              struct.setOrderTotalPriceIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // REFUND_AMOUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.refundAmount = iprot.readI64();
              struct.setRefundAmountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // REFUND_TOTAL_AMOUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.refundTotalAmount = iprot.readI64();
              struct.setRefundTotalAmountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // AMOUNT_TURNOVER
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.amountTurnover = iprot.readI64();
              struct.setAmountTurnoverIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // AMOUNT_CUMULATI_TURNOVER
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.amountCumulatiTurnover = iprot.readI64();
              struct.setAmountCumulatiTurnoverIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ReportAmountQuotaVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetOrderAmount()) {
        oprot.writeFieldBegin(ORDER_AMOUNT_FIELD_DESC);
        oprot.writeI64(struct.orderAmount);
        oprot.writeFieldEnd();
      }
      if (struct.isSetOrderTotalPrice()) {
        oprot.writeFieldBegin(ORDER_TOTAL_PRICE_FIELD_DESC);
        oprot.writeI64(struct.orderTotalPrice);
        oprot.writeFieldEnd();
      }
      if (struct.isSetRefundAmount()) {
        oprot.writeFieldBegin(REFUND_AMOUNT_FIELD_DESC);
        oprot.writeI64(struct.refundAmount);
        oprot.writeFieldEnd();
      }
      if (struct.isSetRefundTotalAmount()) {
        oprot.writeFieldBegin(REFUND_TOTAL_AMOUNT_FIELD_DESC);
        oprot.writeI64(struct.refundTotalAmount);
        oprot.writeFieldEnd();
      }
      if (struct.isSetAmountTurnover()) {
        oprot.writeFieldBegin(AMOUNT_TURNOVER_FIELD_DESC);
        oprot.writeI64(struct.amountTurnover);
        oprot.writeFieldEnd();
      }
      if (struct.isSetAmountCumulatiTurnover()) {
        oprot.writeFieldBegin(AMOUNT_CUMULATI_TURNOVER_FIELD_DESC);
        oprot.writeI64(struct.amountCumulatiTurnover);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ReportAmountQuotaVoTupleSchemeFactory implements SchemeFactory {
    public ReportAmountQuotaVoTupleScheme getScheme() {
      return new ReportAmountQuotaVoTupleScheme();
    }
  }

  private static class ReportAmountQuotaVoTupleScheme extends TupleScheme<ReportAmountQuotaVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ReportAmountQuotaVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetOrderAmount()) {
        optionals.set(0);
      }
      if (struct.isSetOrderTotalPrice()) {
        optionals.set(1);
      }
      if (struct.isSetRefundAmount()) {
        optionals.set(2);
      }
      if (struct.isSetRefundTotalAmount()) {
        optionals.set(3);
      }
      if (struct.isSetAmountTurnover()) {
        optionals.set(4);
      }
      if (struct.isSetAmountCumulatiTurnover()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetOrderAmount()) {
        oprot.writeI64(struct.orderAmount);
      }
      if (struct.isSetOrderTotalPrice()) {
        oprot.writeI64(struct.orderTotalPrice);
      }
      if (struct.isSetRefundAmount()) {
        oprot.writeI64(struct.refundAmount);
      }
      if (struct.isSetRefundTotalAmount()) {
        oprot.writeI64(struct.refundTotalAmount);
      }
      if (struct.isSetAmountTurnover()) {
        oprot.writeI64(struct.amountTurnover);
      }
      if (struct.isSetAmountCumulatiTurnover()) {
        oprot.writeI64(struct.amountCumulatiTurnover);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ReportAmountQuotaVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.orderAmount = iprot.readI64();
        struct.setOrderAmountIsSet(true);
      }
      if (incoming.get(1)) {
        struct.orderTotalPrice = iprot.readI64();
        struct.setOrderTotalPriceIsSet(true);
      }
      if (incoming.get(2)) {
        struct.refundAmount = iprot.readI64();
        struct.setRefundAmountIsSet(true);
      }
      if (incoming.get(3)) {
        struct.refundTotalAmount = iprot.readI64();
        struct.setRefundTotalAmountIsSet(true);
      }
      if (incoming.get(4)) {
        struct.amountTurnover = iprot.readI64();
        struct.setAmountTurnoverIsSet(true);
      }
      if (incoming.get(5)) {
        struct.amountCumulatiTurnover = iprot.readI64();
        struct.setAmountCumulatiTurnoverIsSet(true);
      }
    }
  }

}

