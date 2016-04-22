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
 * 进入购物车 响应
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class ShoppingCartResVo implements org.apache.thrift.TBase<ShoppingCartResVo, ShoppingCartResVo._Fields>, java.io.Serializable, Cloneable, Comparable<ShoppingCartResVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ShoppingCartResVo");

  private static final org.apache.thrift.protocol.TField REQ_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("reqId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField CLIENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("clientId", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField MEMBER_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("memberCode", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField SHOPPING_CART_RES_PRODUCT_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("shoppingCartResProductList", org.apache.thrift.protocol.TType.LIST, (short)4);
  private static final org.apache.thrift.protocol.TField SHOPPING_CART_FULL_GIVE_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("shoppingCartFullGiveList", org.apache.thrift.protocol.TType.LIST, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ShoppingCartResVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ShoppingCartResVoTupleSchemeFactory());
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
   * 用户编号
   */
  public long memberCode; // required
  /**
   * 购物车响应商品 - 列表
   */
  public List<ShoppingCartResProductVo> shoppingCartResProductList; // required
  /**
   * 购物车满赠 - 列表
   */
  public List<ShoppingCartFullGiveVo> shoppingCartFullGiveList; // required

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
     * 用户编号
     */
    MEMBER_CODE((short)3, "memberCode"),
    /**
     * 购物车响应商品 - 列表
     */
    SHOPPING_CART_RES_PRODUCT_LIST((short)4, "shoppingCartResProductList"),
    /**
     * 购物车满赠 - 列表
     */
    SHOPPING_CART_FULL_GIVE_LIST((short)5, "shoppingCartFullGiveList");

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
        case 3: // MEMBER_CODE
          return MEMBER_CODE;
        case 4: // SHOPPING_CART_RES_PRODUCT_LIST
          return SHOPPING_CART_RES_PRODUCT_LIST;
        case 5: // SHOPPING_CART_FULL_GIVE_LIST
          return SHOPPING_CART_FULL_GIVE_LIST;
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
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.REQ_ID, new org.apache.thrift.meta_data.FieldMetaData("reqId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CLIENT_ID, new org.apache.thrift.meta_data.FieldMetaData("clientId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.MEMBER_CODE, new org.apache.thrift.meta_data.FieldMetaData("memberCode", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.SHOPPING_CART_RES_PRODUCT_LIST, new org.apache.thrift.meta_data.FieldMetaData("shoppingCartResProductList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT            , "ShoppingCartResProductVo"))));
    tmpMap.put(_Fields.SHOPPING_CART_FULL_GIVE_LIST, new org.apache.thrift.meta_data.FieldMetaData("shoppingCartFullGiveList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT            , "ShoppingCartFullGiveVo"))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ShoppingCartResVo.class, metaDataMap);
  }

  public ShoppingCartResVo() {
  }

  public ShoppingCartResVo(
    String reqId,
    String clientId,
    long memberCode,
    List<ShoppingCartResProductVo> shoppingCartResProductList,
    List<ShoppingCartFullGiveVo> shoppingCartFullGiveList)
  {
    this();
    this.reqId = reqId;
    this.clientId = clientId;
    this.memberCode = memberCode;
    setMemberCodeIsSet(true);
    this.shoppingCartResProductList = shoppingCartResProductList;
    this.shoppingCartFullGiveList = shoppingCartFullGiveList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ShoppingCartResVo(ShoppingCartResVo other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetReqId()) {
      this.reqId = other.reqId;
    }
    if (other.isSetClientId()) {
      this.clientId = other.clientId;
    }
    this.memberCode = other.memberCode;
    if (other.isSetShoppingCartResProductList()) {
      List<ShoppingCartResProductVo> __this__shoppingCartResProductList = new ArrayList<ShoppingCartResProductVo>(other.shoppingCartResProductList.size());
      for (ShoppingCartResProductVo other_element : other.shoppingCartResProductList) {
        __this__shoppingCartResProductList.add(other_element);
      }
      this.shoppingCartResProductList = __this__shoppingCartResProductList;
    }
    if (other.isSetShoppingCartFullGiveList()) {
      List<ShoppingCartFullGiveVo> __this__shoppingCartFullGiveList = new ArrayList<ShoppingCartFullGiveVo>(other.shoppingCartFullGiveList.size());
      for (ShoppingCartFullGiveVo other_element : other.shoppingCartFullGiveList) {
        __this__shoppingCartFullGiveList.add(other_element);
      }
      this.shoppingCartFullGiveList = __this__shoppingCartFullGiveList;
    }
  }

  public ShoppingCartResVo deepCopy() {
    return new ShoppingCartResVo(this);
  }

  @Override
  public void clear() {
    this.reqId = null;
    this.clientId = null;
    setMemberCodeIsSet(false);
    this.memberCode = 0;
    this.shoppingCartResProductList = null;
    this.shoppingCartFullGiveList = null;
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
  public ShoppingCartResVo setReqId(String reqId) {
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
  public ShoppingCartResVo setClientId(String clientId) {
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
   * 用户编号
   */
  public long getMemberCode() {
    return this.memberCode;
  }

  /**
   * 用户编号
   */
  public ShoppingCartResVo setMemberCode(long memberCode) {
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

  public int getShoppingCartResProductListSize() {
    return (this.shoppingCartResProductList == null) ? 0 : this.shoppingCartResProductList.size();
  }

  public java.util.Iterator<ShoppingCartResProductVo> getShoppingCartResProductListIterator() {
    return (this.shoppingCartResProductList == null) ? null : this.shoppingCartResProductList.iterator();
  }

  public void addToShoppingCartResProductList(ShoppingCartResProductVo elem) {
    if (this.shoppingCartResProductList == null) {
      this.shoppingCartResProductList = new ArrayList<ShoppingCartResProductVo>();
    }
    this.shoppingCartResProductList.add(elem);
  }

  /**
   * 购物车响应商品 - 列表
   */
  public List<ShoppingCartResProductVo> getShoppingCartResProductList() {
    return this.shoppingCartResProductList;
  }

  /**
   * 购物车响应商品 - 列表
   */
  public ShoppingCartResVo setShoppingCartResProductList(List<ShoppingCartResProductVo> shoppingCartResProductList) {
    this.shoppingCartResProductList = shoppingCartResProductList;
    return this;
  }

  public void unsetShoppingCartResProductList() {
    this.shoppingCartResProductList = null;
  }

  /** Returns true if field shoppingCartResProductList is set (has been assigned a value) and false otherwise */
  public boolean isSetShoppingCartResProductList() {
    return this.shoppingCartResProductList != null;
  }

  public void setShoppingCartResProductListIsSet(boolean value) {
    if (!value) {
      this.shoppingCartResProductList = null;
    }
  }

  public int getShoppingCartFullGiveListSize() {
    return (this.shoppingCartFullGiveList == null) ? 0 : this.shoppingCartFullGiveList.size();
  }

  public java.util.Iterator<ShoppingCartFullGiveVo> getShoppingCartFullGiveListIterator() {
    return (this.shoppingCartFullGiveList == null) ? null : this.shoppingCartFullGiveList.iterator();
  }

  public void addToShoppingCartFullGiveList(ShoppingCartFullGiveVo elem) {
    if (this.shoppingCartFullGiveList == null) {
      this.shoppingCartFullGiveList = new ArrayList<ShoppingCartFullGiveVo>();
    }
    this.shoppingCartFullGiveList.add(elem);
  }

  /**
   * 购物车满赠 - 列表
   */
  public List<ShoppingCartFullGiveVo> getShoppingCartFullGiveList() {
    return this.shoppingCartFullGiveList;
  }

  /**
   * 购物车满赠 - 列表
   */
  public ShoppingCartResVo setShoppingCartFullGiveList(List<ShoppingCartFullGiveVo> shoppingCartFullGiveList) {
    this.shoppingCartFullGiveList = shoppingCartFullGiveList;
    return this;
  }

  public void unsetShoppingCartFullGiveList() {
    this.shoppingCartFullGiveList = null;
  }

  /** Returns true if field shoppingCartFullGiveList is set (has been assigned a value) and false otherwise */
  public boolean isSetShoppingCartFullGiveList() {
    return this.shoppingCartFullGiveList != null;
  }

  public void setShoppingCartFullGiveListIsSet(boolean value) {
    if (!value) {
      this.shoppingCartFullGiveList = null;
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

    case MEMBER_CODE:
      if (value == null) {
        unsetMemberCode();
      } else {
        setMemberCode((Long)value);
      }
      break;

    case SHOPPING_CART_RES_PRODUCT_LIST:
      if (value == null) {
        unsetShoppingCartResProductList();
      } else {
        setShoppingCartResProductList((List<ShoppingCartResProductVo>)value);
      }
      break;

    case SHOPPING_CART_FULL_GIVE_LIST:
      if (value == null) {
        unsetShoppingCartFullGiveList();
      } else {
        setShoppingCartFullGiveList((List<ShoppingCartFullGiveVo>)value);
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

    case MEMBER_CODE:
      return Long.valueOf(getMemberCode());

    case SHOPPING_CART_RES_PRODUCT_LIST:
      return getShoppingCartResProductList();

    case SHOPPING_CART_FULL_GIVE_LIST:
      return getShoppingCartFullGiveList();

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
    case MEMBER_CODE:
      return isSetMemberCode();
    case SHOPPING_CART_RES_PRODUCT_LIST:
      return isSetShoppingCartResProductList();
    case SHOPPING_CART_FULL_GIVE_LIST:
      return isSetShoppingCartFullGiveList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ShoppingCartResVo)
      return this.equals((ShoppingCartResVo)that);
    return false;
  }

  public boolean equals(ShoppingCartResVo that) {
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

    boolean this_present_memberCode = true;
    boolean that_present_memberCode = true;
    if (this_present_memberCode || that_present_memberCode) {
      if (!(this_present_memberCode && that_present_memberCode))
        return false;
      if (this.memberCode != that.memberCode)
        return false;
    }

    boolean this_present_shoppingCartResProductList = true && this.isSetShoppingCartResProductList();
    boolean that_present_shoppingCartResProductList = true && that.isSetShoppingCartResProductList();
    if (this_present_shoppingCartResProductList || that_present_shoppingCartResProductList) {
      if (!(this_present_shoppingCartResProductList && that_present_shoppingCartResProductList))
        return false;
      if (!this.shoppingCartResProductList.equals(that.shoppingCartResProductList))
        return false;
    }

    boolean this_present_shoppingCartFullGiveList = true && this.isSetShoppingCartFullGiveList();
    boolean that_present_shoppingCartFullGiveList = true && that.isSetShoppingCartFullGiveList();
    if (this_present_shoppingCartFullGiveList || that_present_shoppingCartFullGiveList) {
      if (!(this_present_shoppingCartFullGiveList && that_present_shoppingCartFullGiveList))
        return false;
      if (!this.shoppingCartFullGiveList.equals(that.shoppingCartFullGiveList))
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

    boolean present_memberCode = true;
    list.add(present_memberCode);
    if (present_memberCode)
      list.add(memberCode);

    boolean present_shoppingCartResProductList = true && (isSetShoppingCartResProductList());
    list.add(present_shoppingCartResProductList);
    if (present_shoppingCartResProductList)
      list.add(shoppingCartResProductList);

    boolean present_shoppingCartFullGiveList = true && (isSetShoppingCartFullGiveList());
    list.add(present_shoppingCartFullGiveList);
    if (present_shoppingCartFullGiveList)
      list.add(shoppingCartFullGiveList);

    return list.hashCode();
  }

  @Override
  public int compareTo(ShoppingCartResVo other) {
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
    lastComparison = Boolean.valueOf(isSetShoppingCartResProductList()).compareTo(other.isSetShoppingCartResProductList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetShoppingCartResProductList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.shoppingCartResProductList, other.shoppingCartResProductList);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetShoppingCartFullGiveList()).compareTo(other.isSetShoppingCartFullGiveList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetShoppingCartFullGiveList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.shoppingCartFullGiveList, other.shoppingCartFullGiveList);
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
    StringBuilder sb = new StringBuilder("ShoppingCartResVo(");
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
    sb.append("memberCode:");
    sb.append(this.memberCode);
    first = false;
    if (!first) sb.append(", ");
    sb.append("shoppingCartResProductList:");
    if (this.shoppingCartResProductList == null) {
      sb.append("null");
    } else {
      sb.append(this.shoppingCartResProductList);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("shoppingCartFullGiveList:");
    if (this.shoppingCartFullGiveList == null) {
      sb.append("null");
    } else {
      sb.append(this.shoppingCartFullGiveList);
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

  private static class ShoppingCartResVoStandardSchemeFactory implements SchemeFactory {
    public ShoppingCartResVoStandardScheme getScheme() {
      return new ShoppingCartResVoStandardScheme();
    }
  }

  private static class ShoppingCartResVoStandardScheme extends StandardScheme<ShoppingCartResVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ShoppingCartResVo struct) throws org.apache.thrift.TException {
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
          case 3: // MEMBER_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.memberCode = iprot.readI64();
              struct.setMemberCodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // SHOPPING_CART_RES_PRODUCT_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list24 = iprot.readListBegin();
                struct.shoppingCartResProductList = new ArrayList<ShoppingCartResProductVo>(_list24.size);
                ShoppingCartResProductVo _elem25;
                for (int _i26 = 0; _i26 < _list24.size; ++_i26)
                {
                  _elem25 = new ShoppingCartResProductVo();
                  _elem25.read(iprot);
                  struct.shoppingCartResProductList.add(_elem25);
                }
                iprot.readListEnd();
              }
              struct.setShoppingCartResProductListIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // SHOPPING_CART_FULL_GIVE_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list27 = iprot.readListBegin();
                struct.shoppingCartFullGiveList = new ArrayList<ShoppingCartFullGiveVo>(_list27.size);
                ShoppingCartFullGiveVo _elem28;
                for (int _i29 = 0; _i29 < _list27.size; ++_i29)
                {
                  _elem28 = new ShoppingCartFullGiveVo();
                  _elem28.read(iprot);
                  struct.shoppingCartFullGiveList.add(_elem28);
                }
                iprot.readListEnd();
              }
              struct.setShoppingCartFullGiveListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ShoppingCartResVo struct) throws org.apache.thrift.TException {
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
      oprot.writeFieldBegin(MEMBER_CODE_FIELD_DESC);
      oprot.writeI64(struct.memberCode);
      oprot.writeFieldEnd();
      if (struct.shoppingCartResProductList != null) {
        oprot.writeFieldBegin(SHOPPING_CART_RES_PRODUCT_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.shoppingCartResProductList.size()));
          for (ShoppingCartResProductVo _iter30 : struct.shoppingCartResProductList)
          {
            _iter30.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.shoppingCartFullGiveList != null) {
        oprot.writeFieldBegin(SHOPPING_CART_FULL_GIVE_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.shoppingCartFullGiveList.size()));
          for (ShoppingCartFullGiveVo _iter31 : struct.shoppingCartFullGiveList)
          {
            _iter31.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ShoppingCartResVoTupleSchemeFactory implements SchemeFactory {
    public ShoppingCartResVoTupleScheme getScheme() {
      return new ShoppingCartResVoTupleScheme();
    }
  }

  private static class ShoppingCartResVoTupleScheme extends TupleScheme<ShoppingCartResVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ShoppingCartResVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetReqId()) {
        optionals.set(0);
      }
      if (struct.isSetClientId()) {
        optionals.set(1);
      }
      if (struct.isSetMemberCode()) {
        optionals.set(2);
      }
      if (struct.isSetShoppingCartResProductList()) {
        optionals.set(3);
      }
      if (struct.isSetShoppingCartFullGiveList()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetReqId()) {
        oprot.writeString(struct.reqId);
      }
      if (struct.isSetClientId()) {
        oprot.writeString(struct.clientId);
      }
      if (struct.isSetMemberCode()) {
        oprot.writeI64(struct.memberCode);
      }
      if (struct.isSetShoppingCartResProductList()) {
        {
          oprot.writeI32(struct.shoppingCartResProductList.size());
          for (ShoppingCartResProductVo _iter32 : struct.shoppingCartResProductList)
          {
            _iter32.write(oprot);
          }
        }
      }
      if (struct.isSetShoppingCartFullGiveList()) {
        {
          oprot.writeI32(struct.shoppingCartFullGiveList.size());
          for (ShoppingCartFullGiveVo _iter33 : struct.shoppingCartFullGiveList)
          {
            _iter33.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ShoppingCartResVo struct) throws org.apache.thrift.TException {
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
        struct.memberCode = iprot.readI64();
        struct.setMemberCodeIsSet(true);
      }
      if (incoming.get(3)) {
        {
          org.apache.thrift.protocol.TList _list34 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.shoppingCartResProductList = new ArrayList<ShoppingCartResProductVo>(_list34.size);
          ShoppingCartResProductVo _elem35;
          for (int _i36 = 0; _i36 < _list34.size; ++_i36)
          {
            _elem35 = new ShoppingCartResProductVo();
            _elem35.read(iprot);
            struct.shoppingCartResProductList.add(_elem35);
          }
        }
        struct.setShoppingCartResProductListIsSet(true);
      }
      if (incoming.get(4)) {
        {
          org.apache.thrift.protocol.TList _list37 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.shoppingCartFullGiveList = new ArrayList<ShoppingCartFullGiveVo>(_list37.size);
          ShoppingCartFullGiveVo _elem38;
          for (int _i39 = 0; _i39 < _list37.size; ++_i39)
          {
            _elem38 = new ShoppingCartFullGiveVo();
            _elem38.read(iprot);
            struct.shoppingCartFullGiveList.add(_elem38);
          }
        }
        struct.setShoppingCartFullGiveListIsSet(true);
      }
    }
  }

}
