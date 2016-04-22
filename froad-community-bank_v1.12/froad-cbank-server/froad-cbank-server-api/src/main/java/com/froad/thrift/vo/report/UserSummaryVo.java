/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.froad.thrift.vo.report;

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
 * 用户统计详情信息
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class UserSummaryVo implements org.apache.thrift.TBase<UserSummaryVo, UserSummaryVo._Fields>, java.io.Serializable, Cloneable, Comparable<UserSummaryVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("UserSummaryVo");

  private static final org.apache.thrift.protocol.TField ORG_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("orgCode", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField ORG_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("orgName", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField ADD_COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("addCount", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField CHANGE_COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("changeCount", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField TOTAL_COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("totalCount", org.apache.thrift.protocol.TType.I32, (short)5);
  private static final org.apache.thrift.protocol.TField PERCENT_FIELD_DESC = new org.apache.thrift.protocol.TField("percent", org.apache.thrift.protocol.TType.DOUBLE, (short)6);
  private static final org.apache.thrift.protocol.TField ORDER_COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("orderCount", org.apache.thrift.protocol.TType.I32, (short)7);
  private static final org.apache.thrift.protocol.TField TOTAL_AMOUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("totalAmount", org.apache.thrift.protocol.TType.DOUBLE, (short)8);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new UserSummaryVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new UserSummaryVoTupleSchemeFactory());
  }

  /**
   * 机构号 *
   */
  public String orgCode; // required
  /**
   * 机构名 *
   */
  public String orgName; // required
  /**
   * 新增用户数 *
   */
  public int addCount; // required
  /**
   * 动账用户数 *
   */
  public int changeCount; // required
  /**
   * 结余用户数 *
   */
  public int totalCount; // required
  /**
   * 新增商户占比 *
   */
  public double percent; // required
  /**
   * 订单数 *
   */
  public int orderCount; // required
  /**
   * 消费金额 *
   */
  public double totalAmount; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 机构号 *
     */
    ORG_CODE((short)1, "orgCode"),
    /**
     * 机构名 *
     */
    ORG_NAME((short)2, "orgName"),
    /**
     * 新增用户数 *
     */
    ADD_COUNT((short)3, "addCount"),
    /**
     * 动账用户数 *
     */
    CHANGE_COUNT((short)4, "changeCount"),
    /**
     * 结余用户数 *
     */
    TOTAL_COUNT((short)5, "totalCount"),
    /**
     * 新增商户占比 *
     */
    PERCENT((short)6, "percent"),
    /**
     * 订单数 *
     */
    ORDER_COUNT((short)7, "orderCount"),
    /**
     * 消费金额 *
     */
    TOTAL_AMOUNT((short)8, "totalAmount");

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
        case 1: // ORG_CODE
          return ORG_CODE;
        case 2: // ORG_NAME
          return ORG_NAME;
        case 3: // ADD_COUNT
          return ADD_COUNT;
        case 4: // CHANGE_COUNT
          return CHANGE_COUNT;
        case 5: // TOTAL_COUNT
          return TOTAL_COUNT;
        case 6: // PERCENT
          return PERCENT;
        case 7: // ORDER_COUNT
          return ORDER_COUNT;
        case 8: // TOTAL_AMOUNT
          return TOTAL_AMOUNT;
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
  private static final int __ADDCOUNT_ISSET_ID = 0;
  private static final int __CHANGECOUNT_ISSET_ID = 1;
  private static final int __TOTALCOUNT_ISSET_ID = 2;
  private static final int __PERCENT_ISSET_ID = 3;
  private static final int __ORDERCOUNT_ISSET_ID = 4;
  private static final int __TOTALAMOUNT_ISSET_ID = 5;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ORG_CODE, new org.apache.thrift.meta_data.FieldMetaData("orgCode", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ORG_NAME, new org.apache.thrift.meta_data.FieldMetaData("orgName", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ADD_COUNT, new org.apache.thrift.meta_data.FieldMetaData("addCount", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.CHANGE_COUNT, new org.apache.thrift.meta_data.FieldMetaData("changeCount", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.TOTAL_COUNT, new org.apache.thrift.meta_data.FieldMetaData("totalCount", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PERCENT, new org.apache.thrift.meta_data.FieldMetaData("percent", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.ORDER_COUNT, new org.apache.thrift.meta_data.FieldMetaData("orderCount", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.TOTAL_AMOUNT, new org.apache.thrift.meta_data.FieldMetaData("totalAmount", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(UserSummaryVo.class, metaDataMap);
  }

  public UserSummaryVo() {
  }

  public UserSummaryVo(
    String orgCode,
    String orgName,
    int addCount,
    int changeCount,
    int totalCount,
    double percent,
    int orderCount,
    double totalAmount)
  {
    this();
    this.orgCode = orgCode;
    this.orgName = orgName;
    this.addCount = addCount;
    setAddCountIsSet(true);
    this.changeCount = changeCount;
    setChangeCountIsSet(true);
    this.totalCount = totalCount;
    setTotalCountIsSet(true);
    this.percent = percent;
    setPercentIsSet(true);
    this.orderCount = orderCount;
    setOrderCountIsSet(true);
    this.totalAmount = totalAmount;
    setTotalAmountIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public UserSummaryVo(UserSummaryVo other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetOrgCode()) {
      this.orgCode = other.orgCode;
    }
    if (other.isSetOrgName()) {
      this.orgName = other.orgName;
    }
    this.addCount = other.addCount;
    this.changeCount = other.changeCount;
    this.totalCount = other.totalCount;
    this.percent = other.percent;
    this.orderCount = other.orderCount;
    this.totalAmount = other.totalAmount;
  }

  public UserSummaryVo deepCopy() {
    return new UserSummaryVo(this);
  }

  @Override
  public void clear() {
    this.orgCode = null;
    this.orgName = null;
    setAddCountIsSet(false);
    this.addCount = 0;
    setChangeCountIsSet(false);
    this.changeCount = 0;
    setTotalCountIsSet(false);
    this.totalCount = 0;
    setPercentIsSet(false);
    this.percent = 0.0;
    setOrderCountIsSet(false);
    this.orderCount = 0;
    setTotalAmountIsSet(false);
    this.totalAmount = 0.0;
  }

  /**
   * 机构号 *
   */
  public String getOrgCode() {
    return this.orgCode;
  }

  /**
   * 机构号 *
   */
  public UserSummaryVo setOrgCode(String orgCode) {
    this.orgCode = orgCode;
    return this;
  }

  public void unsetOrgCode() {
    this.orgCode = null;
  }

  /** Returns true if field orgCode is set (has been assigned a value) and false otherwise */
  public boolean isSetOrgCode() {
    return this.orgCode != null;
  }

  public void setOrgCodeIsSet(boolean value) {
    if (!value) {
      this.orgCode = null;
    }
  }

  /**
   * 机构名 *
   */
  public String getOrgName() {
    return this.orgName;
  }

  /**
   * 机构名 *
   */
  public UserSummaryVo setOrgName(String orgName) {
    this.orgName = orgName;
    return this;
  }

  public void unsetOrgName() {
    this.orgName = null;
  }

  /** Returns true if field orgName is set (has been assigned a value) and false otherwise */
  public boolean isSetOrgName() {
    return this.orgName != null;
  }

  public void setOrgNameIsSet(boolean value) {
    if (!value) {
      this.orgName = null;
    }
  }

  /**
   * 新增用户数 *
   */
  public int getAddCount() {
    return this.addCount;
  }

  /**
   * 新增用户数 *
   */
  public UserSummaryVo setAddCount(int addCount) {
    this.addCount = addCount;
    setAddCountIsSet(true);
    return this;
  }

  public void unsetAddCount() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ADDCOUNT_ISSET_ID);
  }

  /** Returns true if field addCount is set (has been assigned a value) and false otherwise */
  public boolean isSetAddCount() {
    return EncodingUtils.testBit(__isset_bitfield, __ADDCOUNT_ISSET_ID);
  }

  public void setAddCountIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ADDCOUNT_ISSET_ID, value);
  }

  /**
   * 动账用户数 *
   */
  public int getChangeCount() {
    return this.changeCount;
  }

  /**
   * 动账用户数 *
   */
  public UserSummaryVo setChangeCount(int changeCount) {
    this.changeCount = changeCount;
    setChangeCountIsSet(true);
    return this;
  }

  public void unsetChangeCount() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __CHANGECOUNT_ISSET_ID);
  }

  /** Returns true if field changeCount is set (has been assigned a value) and false otherwise */
  public boolean isSetChangeCount() {
    return EncodingUtils.testBit(__isset_bitfield, __CHANGECOUNT_ISSET_ID);
  }

  public void setChangeCountIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __CHANGECOUNT_ISSET_ID, value);
  }

  /**
   * 结余用户数 *
   */
  public int getTotalCount() {
    return this.totalCount;
  }

  /**
   * 结余用户数 *
   */
  public UserSummaryVo setTotalCount(int totalCount) {
    this.totalCount = totalCount;
    setTotalCountIsSet(true);
    return this;
  }

  public void unsetTotalCount() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TOTALCOUNT_ISSET_ID);
  }

  /** Returns true if field totalCount is set (has been assigned a value) and false otherwise */
  public boolean isSetTotalCount() {
    return EncodingUtils.testBit(__isset_bitfield, __TOTALCOUNT_ISSET_ID);
  }

  public void setTotalCountIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TOTALCOUNT_ISSET_ID, value);
  }

  /**
   * 新增商户占比 *
   */
  public double getPercent() {
    return this.percent;
  }

  /**
   * 新增商户占比 *
   */
  public UserSummaryVo setPercent(double percent) {
    this.percent = percent;
    setPercentIsSet(true);
    return this;
  }

  public void unsetPercent() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PERCENT_ISSET_ID);
  }

  /** Returns true if field percent is set (has been assigned a value) and false otherwise */
  public boolean isSetPercent() {
    return EncodingUtils.testBit(__isset_bitfield, __PERCENT_ISSET_ID);
  }

  public void setPercentIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PERCENT_ISSET_ID, value);
  }

  /**
   * 订单数 *
   */
  public int getOrderCount() {
    return this.orderCount;
  }

  /**
   * 订单数 *
   */
  public UserSummaryVo setOrderCount(int orderCount) {
    this.orderCount = orderCount;
    setOrderCountIsSet(true);
    return this;
  }

  public void unsetOrderCount() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ORDERCOUNT_ISSET_ID);
  }

  /** Returns true if field orderCount is set (has been assigned a value) and false otherwise */
  public boolean isSetOrderCount() {
    return EncodingUtils.testBit(__isset_bitfield, __ORDERCOUNT_ISSET_ID);
  }

  public void setOrderCountIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ORDERCOUNT_ISSET_ID, value);
  }

  /**
   * 消费金额 *
   */
  public double getTotalAmount() {
    return this.totalAmount;
  }

  /**
   * 消费金额 *
   */
  public UserSummaryVo setTotalAmount(double totalAmount) {
    this.totalAmount = totalAmount;
    setTotalAmountIsSet(true);
    return this;
  }

  public void unsetTotalAmount() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TOTALAMOUNT_ISSET_ID);
  }

  /** Returns true if field totalAmount is set (has been assigned a value) and false otherwise */
  public boolean isSetTotalAmount() {
    return EncodingUtils.testBit(__isset_bitfield, __TOTALAMOUNT_ISSET_ID);
  }

  public void setTotalAmountIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TOTALAMOUNT_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ORG_CODE:
      if (value == null) {
        unsetOrgCode();
      } else {
        setOrgCode((String)value);
      }
      break;

    case ORG_NAME:
      if (value == null) {
        unsetOrgName();
      } else {
        setOrgName((String)value);
      }
      break;

    case ADD_COUNT:
      if (value == null) {
        unsetAddCount();
      } else {
        setAddCount((Integer)value);
      }
      break;

    case CHANGE_COUNT:
      if (value == null) {
        unsetChangeCount();
      } else {
        setChangeCount((Integer)value);
      }
      break;

    case TOTAL_COUNT:
      if (value == null) {
        unsetTotalCount();
      } else {
        setTotalCount((Integer)value);
      }
      break;

    case PERCENT:
      if (value == null) {
        unsetPercent();
      } else {
        setPercent((Double)value);
      }
      break;

    case ORDER_COUNT:
      if (value == null) {
        unsetOrderCount();
      } else {
        setOrderCount((Integer)value);
      }
      break;

    case TOTAL_AMOUNT:
      if (value == null) {
        unsetTotalAmount();
      } else {
        setTotalAmount((Double)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ORG_CODE:
      return getOrgCode();

    case ORG_NAME:
      return getOrgName();

    case ADD_COUNT:
      return Integer.valueOf(getAddCount());

    case CHANGE_COUNT:
      return Integer.valueOf(getChangeCount());

    case TOTAL_COUNT:
      return Integer.valueOf(getTotalCount());

    case PERCENT:
      return Double.valueOf(getPercent());

    case ORDER_COUNT:
      return Integer.valueOf(getOrderCount());

    case TOTAL_AMOUNT:
      return Double.valueOf(getTotalAmount());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ORG_CODE:
      return isSetOrgCode();
    case ORG_NAME:
      return isSetOrgName();
    case ADD_COUNT:
      return isSetAddCount();
    case CHANGE_COUNT:
      return isSetChangeCount();
    case TOTAL_COUNT:
      return isSetTotalCount();
    case PERCENT:
      return isSetPercent();
    case ORDER_COUNT:
      return isSetOrderCount();
    case TOTAL_AMOUNT:
      return isSetTotalAmount();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof UserSummaryVo)
      return this.equals((UserSummaryVo)that);
    return false;
  }

  public boolean equals(UserSummaryVo that) {
    if (that == null)
      return false;

    boolean this_present_orgCode = true && this.isSetOrgCode();
    boolean that_present_orgCode = true && that.isSetOrgCode();
    if (this_present_orgCode || that_present_orgCode) {
      if (!(this_present_orgCode && that_present_orgCode))
        return false;
      if (!this.orgCode.equals(that.orgCode))
        return false;
    }

    boolean this_present_orgName = true && this.isSetOrgName();
    boolean that_present_orgName = true && that.isSetOrgName();
    if (this_present_orgName || that_present_orgName) {
      if (!(this_present_orgName && that_present_orgName))
        return false;
      if (!this.orgName.equals(that.orgName))
        return false;
    }

    boolean this_present_addCount = true;
    boolean that_present_addCount = true;
    if (this_present_addCount || that_present_addCount) {
      if (!(this_present_addCount && that_present_addCount))
        return false;
      if (this.addCount != that.addCount)
        return false;
    }

    boolean this_present_changeCount = true;
    boolean that_present_changeCount = true;
    if (this_present_changeCount || that_present_changeCount) {
      if (!(this_present_changeCount && that_present_changeCount))
        return false;
      if (this.changeCount != that.changeCount)
        return false;
    }

    boolean this_present_totalCount = true;
    boolean that_present_totalCount = true;
    if (this_present_totalCount || that_present_totalCount) {
      if (!(this_present_totalCount && that_present_totalCount))
        return false;
      if (this.totalCount != that.totalCount)
        return false;
    }

    boolean this_present_percent = true;
    boolean that_present_percent = true;
    if (this_present_percent || that_present_percent) {
      if (!(this_present_percent && that_present_percent))
        return false;
      if (this.percent != that.percent)
        return false;
    }

    boolean this_present_orderCount = true;
    boolean that_present_orderCount = true;
    if (this_present_orderCount || that_present_orderCount) {
      if (!(this_present_orderCount && that_present_orderCount))
        return false;
      if (this.orderCount != that.orderCount)
        return false;
    }

    boolean this_present_totalAmount = true;
    boolean that_present_totalAmount = true;
    if (this_present_totalAmount || that_present_totalAmount) {
      if (!(this_present_totalAmount && that_present_totalAmount))
        return false;
      if (this.totalAmount != that.totalAmount)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_orgCode = true && (isSetOrgCode());
    list.add(present_orgCode);
    if (present_orgCode)
      list.add(orgCode);

    boolean present_orgName = true && (isSetOrgName());
    list.add(present_orgName);
    if (present_orgName)
      list.add(orgName);

    boolean present_addCount = true;
    list.add(present_addCount);
    if (present_addCount)
      list.add(addCount);

    boolean present_changeCount = true;
    list.add(present_changeCount);
    if (present_changeCount)
      list.add(changeCount);

    boolean present_totalCount = true;
    list.add(present_totalCount);
    if (present_totalCount)
      list.add(totalCount);

    boolean present_percent = true;
    list.add(present_percent);
    if (present_percent)
      list.add(percent);

    boolean present_orderCount = true;
    list.add(present_orderCount);
    if (present_orderCount)
      list.add(orderCount);

    boolean present_totalAmount = true;
    list.add(present_totalAmount);
    if (present_totalAmount)
      list.add(totalAmount);

    return list.hashCode();
  }

  @Override
  public int compareTo(UserSummaryVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetOrgCode()).compareTo(other.isSetOrgCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOrgCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.orgCode, other.orgCode);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOrgName()).compareTo(other.isSetOrgName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOrgName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.orgName, other.orgName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAddCount()).compareTo(other.isSetAddCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAddCount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.addCount, other.addCount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetChangeCount()).compareTo(other.isSetChangeCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetChangeCount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.changeCount, other.changeCount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTotalCount()).compareTo(other.isSetTotalCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTotalCount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.totalCount, other.totalCount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPercent()).compareTo(other.isSetPercent());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPercent()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.percent, other.percent);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOrderCount()).compareTo(other.isSetOrderCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOrderCount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.orderCount, other.orderCount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTotalAmount()).compareTo(other.isSetTotalAmount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTotalAmount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.totalAmount, other.totalAmount);
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
    StringBuilder sb = new StringBuilder("UserSummaryVo(");
    boolean first = true;

    sb.append("orgCode:");
    if (this.orgCode == null) {
      sb.append("null");
    } else {
      sb.append(this.orgCode);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("orgName:");
    if (this.orgName == null) {
      sb.append("null");
    } else {
      sb.append(this.orgName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("addCount:");
    sb.append(this.addCount);
    first = false;
    if (!first) sb.append(", ");
    sb.append("changeCount:");
    sb.append(this.changeCount);
    first = false;
    if (!first) sb.append(", ");
    sb.append("totalCount:");
    sb.append(this.totalCount);
    first = false;
    if (!first) sb.append(", ");
    sb.append("percent:");
    sb.append(this.percent);
    first = false;
    if (!first) sb.append(", ");
    sb.append("orderCount:");
    sb.append(this.orderCount);
    first = false;
    if (!first) sb.append(", ");
    sb.append("totalAmount:");
    sb.append(this.totalAmount);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (orgCode == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'orgCode' was not present! Struct: " + toString());
    }
    if (orgName == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'orgName' was not present! Struct: " + toString());
    }
    // alas, we cannot check 'addCount' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'changeCount' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'totalCount' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'percent' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'orderCount' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'totalAmount' because it's a primitive and you chose the non-beans generator.
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

  private static class UserSummaryVoStandardSchemeFactory implements SchemeFactory {
    public UserSummaryVoStandardScheme getScheme() {
      return new UserSummaryVoStandardScheme();
    }
  }

  private static class UserSummaryVoStandardScheme extends StandardScheme<UserSummaryVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, UserSummaryVo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ORG_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.orgCode = iprot.readString();
              struct.setOrgCodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // ORG_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.orgName = iprot.readString();
              struct.setOrgNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // ADD_COUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.addCount = iprot.readI32();
              struct.setAddCountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // CHANGE_COUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.changeCount = iprot.readI32();
              struct.setChangeCountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // TOTAL_COUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.totalCount = iprot.readI32();
              struct.setTotalCountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // PERCENT
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.percent = iprot.readDouble();
              struct.setPercentIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // ORDER_COUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.orderCount = iprot.readI32();
              struct.setOrderCountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // TOTAL_AMOUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.totalAmount = iprot.readDouble();
              struct.setTotalAmountIsSet(true);
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
      if (!struct.isSetAddCount()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'addCount' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetChangeCount()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'changeCount' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetTotalCount()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'totalCount' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetPercent()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'percent' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetOrderCount()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'orderCount' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetTotalAmount()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'totalAmount' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, UserSummaryVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.orgCode != null) {
        oprot.writeFieldBegin(ORG_CODE_FIELD_DESC);
        oprot.writeString(struct.orgCode);
        oprot.writeFieldEnd();
      }
      if (struct.orgName != null) {
        oprot.writeFieldBegin(ORG_NAME_FIELD_DESC);
        oprot.writeString(struct.orgName);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(ADD_COUNT_FIELD_DESC);
      oprot.writeI32(struct.addCount);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(CHANGE_COUNT_FIELD_DESC);
      oprot.writeI32(struct.changeCount);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(TOTAL_COUNT_FIELD_DESC);
      oprot.writeI32(struct.totalCount);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(PERCENT_FIELD_DESC);
      oprot.writeDouble(struct.percent);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(ORDER_COUNT_FIELD_DESC);
      oprot.writeI32(struct.orderCount);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(TOTAL_AMOUNT_FIELD_DESC);
      oprot.writeDouble(struct.totalAmount);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class UserSummaryVoTupleSchemeFactory implements SchemeFactory {
    public UserSummaryVoTupleScheme getScheme() {
      return new UserSummaryVoTupleScheme();
    }
  }

  private static class UserSummaryVoTupleScheme extends TupleScheme<UserSummaryVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, UserSummaryVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.orgCode);
      oprot.writeString(struct.orgName);
      oprot.writeI32(struct.addCount);
      oprot.writeI32(struct.changeCount);
      oprot.writeI32(struct.totalCount);
      oprot.writeDouble(struct.percent);
      oprot.writeI32(struct.orderCount);
      oprot.writeDouble(struct.totalAmount);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, UserSummaryVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.orgCode = iprot.readString();
      struct.setOrgCodeIsSet(true);
      struct.orgName = iprot.readString();
      struct.setOrgNameIsSet(true);
      struct.addCount = iprot.readI32();
      struct.setAddCountIsSet(true);
      struct.changeCount = iprot.readI32();
      struct.setChangeCountIsSet(true);
      struct.totalCount = iprot.readI32();
      struct.setTotalCountIsSet(true);
      struct.percent = iprot.readDouble();
      struct.setPercentIsSet(true);
      struct.orderCount = iprot.readI32();
      struct.setOrderCountIsSet(true);
      struct.totalAmount = iprot.readDouble();
      struct.setTotalAmountIsSet(true);
    }
  }

}

