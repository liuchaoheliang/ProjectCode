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
 * ProductOutletVo
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class ProductOutletVo implements org.apache.thrift.TBase<ProductOutletVo, ProductOutletVo._Fields>, java.io.Serializable, Cloneable, Comparable<ProductOutletVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ProductOutletVo");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField CLIENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("clientId", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField MERCHANT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("merchantId", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField OUTLET_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("outletId", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField AREA_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("areaId", org.apache.thrift.protocol.TType.I64, (short)5);
  private static final org.apache.thrift.protocol.TField OUTLET_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("outletName", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField ADDRESS_FIELD_DESC = new org.apache.thrift.protocol.TField("address", org.apache.thrift.protocol.TType.STRING, (short)7);
  private static final org.apache.thrift.protocol.TField PHONE_FIELD_DESC = new org.apache.thrift.protocol.TField("phone", org.apache.thrift.protocol.TType.STRING, (short)8);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ProductOutletVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ProductOutletVoTupleSchemeFactory());
  }

  /**
   * id
   */
  public long id; // required
  /**
   * 客户端ID
   */
  public String clientId; // required
  /**
   * 商户ID
   */
  public String merchantId; // required
  /**
   * 门店ID
   */
  public String outletId; // required
  /**
   * 地区ID
   */
  public long areaId; // required
  /**
   * 店名
   */
  public String outletName; // required
  /**
   * 地址
   */
  public String address; // required
  /**
   * 电话
   */
  public String phone; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * id
     */
    ID((short)1, "id"),
    /**
     * 客户端ID
     */
    CLIENT_ID((short)2, "clientId"),
    /**
     * 商户ID
     */
    MERCHANT_ID((short)3, "merchantId"),
    /**
     * 门店ID
     */
    OUTLET_ID((short)4, "outletId"),
    /**
     * 地区ID
     */
    AREA_ID((short)5, "areaId"),
    /**
     * 店名
     */
    OUTLET_NAME((short)6, "outletName"),
    /**
     * 地址
     */
    ADDRESS((short)7, "address"),
    /**
     * 电话
     */
    PHONE((short)8, "phone");

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
        case 1: // ID
          return ID;
        case 2: // CLIENT_ID
          return CLIENT_ID;
        case 3: // MERCHANT_ID
          return MERCHANT_ID;
        case 4: // OUTLET_ID
          return OUTLET_ID;
        case 5: // AREA_ID
          return AREA_ID;
        case 6: // OUTLET_NAME
          return OUTLET_NAME;
        case 7: // ADDRESS
          return ADDRESS;
        case 8: // PHONE
          return PHONE;
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
  private static final int __ID_ISSET_ID = 0;
  private static final int __AREAID_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.CLIENT_ID, new org.apache.thrift.meta_data.FieldMetaData("clientId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.MERCHANT_ID, new org.apache.thrift.meta_data.FieldMetaData("merchantId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.OUTLET_ID, new org.apache.thrift.meta_data.FieldMetaData("outletId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.AREA_ID, new org.apache.thrift.meta_data.FieldMetaData("areaId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.OUTLET_NAME, new org.apache.thrift.meta_data.FieldMetaData("outletName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ADDRESS, new org.apache.thrift.meta_data.FieldMetaData("address", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PHONE, new org.apache.thrift.meta_data.FieldMetaData("phone", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ProductOutletVo.class, metaDataMap);
  }

  public ProductOutletVo() {
  }

  public ProductOutletVo(
    long id,
    String clientId,
    String merchantId,
    String outletId,
    long areaId,
    String outletName,
    String address,
    String phone)
  {
    this();
    this.id = id;
    setIdIsSet(true);
    this.clientId = clientId;
    this.merchantId = merchantId;
    this.outletId = outletId;
    this.areaId = areaId;
    setAreaIdIsSet(true);
    this.outletName = outletName;
    this.address = address;
    this.phone = phone;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ProductOutletVo(ProductOutletVo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    if (other.isSetClientId()) {
      this.clientId = other.clientId;
    }
    if (other.isSetMerchantId()) {
      this.merchantId = other.merchantId;
    }
    if (other.isSetOutletId()) {
      this.outletId = other.outletId;
    }
    this.areaId = other.areaId;
    if (other.isSetOutletName()) {
      this.outletName = other.outletName;
    }
    if (other.isSetAddress()) {
      this.address = other.address;
    }
    if (other.isSetPhone()) {
      this.phone = other.phone;
    }
  }

  public ProductOutletVo deepCopy() {
    return new ProductOutletVo(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    this.clientId = null;
    this.merchantId = null;
    this.outletId = null;
    setAreaIdIsSet(false);
    this.areaId = 0;
    this.outletName = null;
    this.address = null;
    this.phone = null;
  }

  /**
   * id
   */
  public long getId() {
    return this.id;
  }

  /**
   * id
   */
  public ProductOutletVo setId(long id) {
    this.id = id;
    setIdIsSet(true);
    return this;
  }

  public void unsetId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ID_ISSET_ID);
  }

  /** Returns true if field id is set (has been assigned a value) and false otherwise */
  public boolean isSetId() {
    return EncodingUtils.testBit(__isset_bitfield, __ID_ISSET_ID);
  }

  public void setIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ID_ISSET_ID, value);
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
  public ProductOutletVo setClientId(String clientId) {
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
   * 商户ID
   */
  public String getMerchantId() {
    return this.merchantId;
  }

  /**
   * 商户ID
   */
  public ProductOutletVo setMerchantId(String merchantId) {
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

  /**
   * 门店ID
   */
  public String getOutletId() {
    return this.outletId;
  }

  /**
   * 门店ID
   */
  public ProductOutletVo setOutletId(String outletId) {
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

  /**
   * 地区ID
   */
  public long getAreaId() {
    return this.areaId;
  }

  /**
   * 地区ID
   */
  public ProductOutletVo setAreaId(long areaId) {
    this.areaId = areaId;
    setAreaIdIsSet(true);
    return this;
  }

  public void unsetAreaId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __AREAID_ISSET_ID);
  }

  /** Returns true if field areaId is set (has been assigned a value) and false otherwise */
  public boolean isSetAreaId() {
    return EncodingUtils.testBit(__isset_bitfield, __AREAID_ISSET_ID);
  }

  public void setAreaIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __AREAID_ISSET_ID, value);
  }

  /**
   * 店名
   */
  public String getOutletName() {
    return this.outletName;
  }

  /**
   * 店名
   */
  public ProductOutletVo setOutletName(String outletName) {
    this.outletName = outletName;
    return this;
  }

  public void unsetOutletName() {
    this.outletName = null;
  }

  /** Returns true if field outletName is set (has been assigned a value) and false otherwise */
  public boolean isSetOutletName() {
    return this.outletName != null;
  }

  public void setOutletNameIsSet(boolean value) {
    if (!value) {
      this.outletName = null;
    }
  }

  /**
   * 地址
   */
  public String getAddress() {
    return this.address;
  }

  /**
   * 地址
   */
  public ProductOutletVo setAddress(String address) {
    this.address = address;
    return this;
  }

  public void unsetAddress() {
    this.address = null;
  }

  /** Returns true if field address is set (has been assigned a value) and false otherwise */
  public boolean isSetAddress() {
    return this.address != null;
  }

  public void setAddressIsSet(boolean value) {
    if (!value) {
      this.address = null;
    }
  }

  /**
   * 电话
   */
  public String getPhone() {
    return this.phone;
  }

  /**
   * 电话
   */
  public ProductOutletVo setPhone(String phone) {
    this.phone = phone;
    return this;
  }

  public void unsetPhone() {
    this.phone = null;
  }

  /** Returns true if field phone is set (has been assigned a value) and false otherwise */
  public boolean isSetPhone() {
    return this.phone != null;
  }

  public void setPhoneIsSet(boolean value) {
    if (!value) {
      this.phone = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((Long)value);
      }
      break;

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

    case OUTLET_ID:
      if (value == null) {
        unsetOutletId();
      } else {
        setOutletId((String)value);
      }
      break;

    case AREA_ID:
      if (value == null) {
        unsetAreaId();
      } else {
        setAreaId((Long)value);
      }
      break;

    case OUTLET_NAME:
      if (value == null) {
        unsetOutletName();
      } else {
        setOutletName((String)value);
      }
      break;

    case ADDRESS:
      if (value == null) {
        unsetAddress();
      } else {
        setAddress((String)value);
      }
      break;

    case PHONE:
      if (value == null) {
        unsetPhone();
      } else {
        setPhone((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return Long.valueOf(getId());

    case CLIENT_ID:
      return getClientId();

    case MERCHANT_ID:
      return getMerchantId();

    case OUTLET_ID:
      return getOutletId();

    case AREA_ID:
      return Long.valueOf(getAreaId());

    case OUTLET_NAME:
      return getOutletName();

    case ADDRESS:
      return getAddress();

    case PHONE:
      return getPhone();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ID:
      return isSetId();
    case CLIENT_ID:
      return isSetClientId();
    case MERCHANT_ID:
      return isSetMerchantId();
    case OUTLET_ID:
      return isSetOutletId();
    case AREA_ID:
      return isSetAreaId();
    case OUTLET_NAME:
      return isSetOutletName();
    case ADDRESS:
      return isSetAddress();
    case PHONE:
      return isSetPhone();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ProductOutletVo)
      return this.equals((ProductOutletVo)that);
    return false;
  }

  public boolean equals(ProductOutletVo that) {
    if (that == null)
      return false;

    boolean this_present_id = true;
    boolean that_present_id = true;
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (this.id != that.id)
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

    boolean this_present_merchantId = true && this.isSetMerchantId();
    boolean that_present_merchantId = true && that.isSetMerchantId();
    if (this_present_merchantId || that_present_merchantId) {
      if (!(this_present_merchantId && that_present_merchantId))
        return false;
      if (!this.merchantId.equals(that.merchantId))
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

    boolean this_present_areaId = true;
    boolean that_present_areaId = true;
    if (this_present_areaId || that_present_areaId) {
      if (!(this_present_areaId && that_present_areaId))
        return false;
      if (this.areaId != that.areaId)
        return false;
    }

    boolean this_present_outletName = true && this.isSetOutletName();
    boolean that_present_outletName = true && that.isSetOutletName();
    if (this_present_outletName || that_present_outletName) {
      if (!(this_present_outletName && that_present_outletName))
        return false;
      if (!this.outletName.equals(that.outletName))
        return false;
    }

    boolean this_present_address = true && this.isSetAddress();
    boolean that_present_address = true && that.isSetAddress();
    if (this_present_address || that_present_address) {
      if (!(this_present_address && that_present_address))
        return false;
      if (!this.address.equals(that.address))
        return false;
    }

    boolean this_present_phone = true && this.isSetPhone();
    boolean that_present_phone = true && that.isSetPhone();
    if (this_present_phone || that_present_phone) {
      if (!(this_present_phone && that_present_phone))
        return false;
      if (!this.phone.equals(that.phone))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_id = true;
    list.add(present_id);
    if (present_id)
      list.add(id);

    boolean present_clientId = true && (isSetClientId());
    list.add(present_clientId);
    if (present_clientId)
      list.add(clientId);

    boolean present_merchantId = true && (isSetMerchantId());
    list.add(present_merchantId);
    if (present_merchantId)
      list.add(merchantId);

    boolean present_outletId = true && (isSetOutletId());
    list.add(present_outletId);
    if (present_outletId)
      list.add(outletId);

    boolean present_areaId = true;
    list.add(present_areaId);
    if (present_areaId)
      list.add(areaId);

    boolean present_outletName = true && (isSetOutletName());
    list.add(present_outletName);
    if (present_outletName)
      list.add(outletName);

    boolean present_address = true && (isSetAddress());
    list.add(present_address);
    if (present_address)
      list.add(address);

    boolean present_phone = true && (isSetPhone());
    list.add(present_phone);
    if (present_phone)
      list.add(phone);

    return list.hashCode();
  }

  @Override
  public int compareTo(ProductOutletVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetId()).compareTo(other.isSetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id, other.id);
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
    lastComparison = Boolean.valueOf(isSetAreaId()).compareTo(other.isSetAreaId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAreaId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.areaId, other.areaId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOutletName()).compareTo(other.isSetOutletName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOutletName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.outletName, other.outletName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAddress()).compareTo(other.isSetAddress());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAddress()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.address, other.address);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPhone()).compareTo(other.isSetPhone());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPhone()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.phone, other.phone);
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
    StringBuilder sb = new StringBuilder("ProductOutletVo(");
    boolean first = true;

    sb.append("id:");
    sb.append(this.id);
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
    sb.append("merchantId:");
    if (this.merchantId == null) {
      sb.append("null");
    } else {
      sb.append(this.merchantId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("outletId:");
    if (this.outletId == null) {
      sb.append("null");
    } else {
      sb.append(this.outletId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("areaId:");
    sb.append(this.areaId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("outletName:");
    if (this.outletName == null) {
      sb.append("null");
    } else {
      sb.append(this.outletName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("address:");
    if (this.address == null) {
      sb.append("null");
    } else {
      sb.append(this.address);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("phone:");
    if (this.phone == null) {
      sb.append("null");
    } else {
      sb.append(this.phone);
    }
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

  private static class ProductOutletVoStandardSchemeFactory implements SchemeFactory {
    public ProductOutletVoStandardScheme getScheme() {
      return new ProductOutletVoStandardScheme();
    }
  }

  private static class ProductOutletVoStandardScheme extends StandardScheme<ProductOutletVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ProductOutletVo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.id = iprot.readI64();
              struct.setIdIsSet(true);
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
          case 3: // MERCHANT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.merchantId = iprot.readString();
              struct.setMerchantIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // OUTLET_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.outletId = iprot.readString();
              struct.setOutletIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // AREA_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.areaId = iprot.readI64();
              struct.setAreaIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // OUTLET_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.outletName = iprot.readString();
              struct.setOutletNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // ADDRESS
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.address = iprot.readString();
              struct.setAddressIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // PHONE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.phone = iprot.readString();
              struct.setPhoneIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ProductOutletVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(ID_FIELD_DESC);
      oprot.writeI64(struct.id);
      oprot.writeFieldEnd();
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
      if (struct.outletId != null) {
        oprot.writeFieldBegin(OUTLET_ID_FIELD_DESC);
        oprot.writeString(struct.outletId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(AREA_ID_FIELD_DESC);
      oprot.writeI64(struct.areaId);
      oprot.writeFieldEnd();
      if (struct.outletName != null) {
        oprot.writeFieldBegin(OUTLET_NAME_FIELD_DESC);
        oprot.writeString(struct.outletName);
        oprot.writeFieldEnd();
      }
      if (struct.address != null) {
        oprot.writeFieldBegin(ADDRESS_FIELD_DESC);
        oprot.writeString(struct.address);
        oprot.writeFieldEnd();
      }
      if (struct.phone != null) {
        oprot.writeFieldBegin(PHONE_FIELD_DESC);
        oprot.writeString(struct.phone);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ProductOutletVoTupleSchemeFactory implements SchemeFactory {
    public ProductOutletVoTupleScheme getScheme() {
      return new ProductOutletVoTupleScheme();
    }
  }

  private static class ProductOutletVoTupleScheme extends TupleScheme<ProductOutletVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ProductOutletVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetClientId()) {
        optionals.set(1);
      }
      if (struct.isSetMerchantId()) {
        optionals.set(2);
      }
      if (struct.isSetOutletId()) {
        optionals.set(3);
      }
      if (struct.isSetAreaId()) {
        optionals.set(4);
      }
      if (struct.isSetOutletName()) {
        optionals.set(5);
      }
      if (struct.isSetAddress()) {
        optionals.set(6);
      }
      if (struct.isSetPhone()) {
        optionals.set(7);
      }
      oprot.writeBitSet(optionals, 8);
      if (struct.isSetId()) {
        oprot.writeI64(struct.id);
      }
      if (struct.isSetClientId()) {
        oprot.writeString(struct.clientId);
      }
      if (struct.isSetMerchantId()) {
        oprot.writeString(struct.merchantId);
      }
      if (struct.isSetOutletId()) {
        oprot.writeString(struct.outletId);
      }
      if (struct.isSetAreaId()) {
        oprot.writeI64(struct.areaId);
      }
      if (struct.isSetOutletName()) {
        oprot.writeString(struct.outletName);
      }
      if (struct.isSetAddress()) {
        oprot.writeString(struct.address);
      }
      if (struct.isSetPhone()) {
        oprot.writeString(struct.phone);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ProductOutletVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(8);
      if (incoming.get(0)) {
        struct.id = iprot.readI64();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.clientId = iprot.readString();
        struct.setClientIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.merchantId = iprot.readString();
        struct.setMerchantIdIsSet(true);
      }
      if (incoming.get(3)) {
        struct.outletId = iprot.readString();
        struct.setOutletIdIsSet(true);
      }
      if (incoming.get(4)) {
        struct.areaId = iprot.readI64();
        struct.setAreaIdIsSet(true);
      }
      if (incoming.get(5)) {
        struct.outletName = iprot.readString();
        struct.setOutletNameIsSet(true);
      }
      if (incoming.get(6)) {
        struct.address = iprot.readString();
        struct.setAddressIsSet(true);
      }
      if (incoming.get(7)) {
        struct.phone = iprot.readString();
        struct.setPhoneIsSet(true);
      }
    }
  }

}

