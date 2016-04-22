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
 * 商品分类
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class ProductCategoryVo implements org.apache.thrift.TBase<ProductCategoryVo, ProductCategoryVo._Fields>, java.io.Serializable, Cloneable, Comparable<ProductCategoryVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ProductCategoryVo");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField CLIENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("clientId", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("name", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField TREE_PATH_FIELD_DESC = new org.apache.thrift.protocol.TField("treePath", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField PARENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("parentId", org.apache.thrift.protocol.TType.I64, (short)5);
  private static final org.apache.thrift.protocol.TField ICO_URL_FIELD_DESC = new org.apache.thrift.protocol.TField("icoUrl", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField ORDER_VALUE_FIELD_DESC = new org.apache.thrift.protocol.TField("orderValue", org.apache.thrift.protocol.TType.I16, (short)7);
  private static final org.apache.thrift.protocol.TField IS_HAVE_CHILD_FIELD_DESC = new org.apache.thrift.protocol.TField("isHaveChild", org.apache.thrift.protocol.TType.BOOL, (short)8);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ProductCategoryVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ProductCategoryVoTupleSchemeFactory());
  }

  /**
   * id
   */
  public long id; // required
  /**
   * 客户端id
   */
  public String clientId; // required
  /**
   * name
   */
  public String name; // required
  /**
   * 树路径 空格间隔
   */
  public String treePath; // required
  /**
   * 父节点id
   */
  public long parentId; // required
  /**
   * 小图标
   */
  public String icoUrl; // required
  /**
   * 排序
   */
  public short orderValue; // required
  /**
   * 是否有子分类
   */
  public boolean isHaveChild; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * id
     */
    ID((short)1, "id"),
    /**
     * 客户端id
     */
    CLIENT_ID((short)2, "clientId"),
    /**
     * name
     */
    NAME((short)3, "name"),
    /**
     * 树路径 空格间隔
     */
    TREE_PATH((short)4, "treePath"),
    /**
     * 父节点id
     */
    PARENT_ID((short)5, "parentId"),
    /**
     * 小图标
     */
    ICO_URL((short)6, "icoUrl"),
    /**
     * 排序
     */
    ORDER_VALUE((short)7, "orderValue"),
    /**
     * 是否有子分类
     */
    IS_HAVE_CHILD((short)8, "isHaveChild");

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
        case 3: // NAME
          return NAME;
        case 4: // TREE_PATH
          return TREE_PATH;
        case 5: // PARENT_ID
          return PARENT_ID;
        case 6: // ICO_URL
          return ICO_URL;
        case 7: // ORDER_VALUE
          return ORDER_VALUE;
        case 8: // IS_HAVE_CHILD
          return IS_HAVE_CHILD;
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
  private static final int __PARENTID_ISSET_ID = 1;
  private static final int __ORDERVALUE_ISSET_ID = 2;
  private static final int __ISHAVECHILD_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.CLIENT_ID, new org.apache.thrift.meta_data.FieldMetaData("clientId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.NAME, new org.apache.thrift.meta_data.FieldMetaData("name", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TREE_PATH, new org.apache.thrift.meta_data.FieldMetaData("treePath", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PARENT_ID, new org.apache.thrift.meta_data.FieldMetaData("parentId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.ICO_URL, new org.apache.thrift.meta_data.FieldMetaData("icoUrl", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ORDER_VALUE, new org.apache.thrift.meta_data.FieldMetaData("orderValue", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I16)));
    tmpMap.put(_Fields.IS_HAVE_CHILD, new org.apache.thrift.meta_data.FieldMetaData("isHaveChild", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ProductCategoryVo.class, metaDataMap);
  }

  public ProductCategoryVo() {
  }

  public ProductCategoryVo(
    long id,
    String clientId,
    String name,
    String treePath,
    long parentId,
    String icoUrl,
    short orderValue,
    boolean isHaveChild)
  {
    this();
    this.id = id;
    setIdIsSet(true);
    this.clientId = clientId;
    this.name = name;
    this.treePath = treePath;
    this.parentId = parentId;
    setParentIdIsSet(true);
    this.icoUrl = icoUrl;
    this.orderValue = orderValue;
    setOrderValueIsSet(true);
    this.isHaveChild = isHaveChild;
    setIsHaveChildIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ProductCategoryVo(ProductCategoryVo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    if (other.isSetClientId()) {
      this.clientId = other.clientId;
    }
    if (other.isSetName()) {
      this.name = other.name;
    }
    if (other.isSetTreePath()) {
      this.treePath = other.treePath;
    }
    this.parentId = other.parentId;
    if (other.isSetIcoUrl()) {
      this.icoUrl = other.icoUrl;
    }
    this.orderValue = other.orderValue;
    this.isHaveChild = other.isHaveChild;
  }

  public ProductCategoryVo deepCopy() {
    return new ProductCategoryVo(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    this.clientId = null;
    this.name = null;
    this.treePath = null;
    setParentIdIsSet(false);
    this.parentId = 0;
    this.icoUrl = null;
    setOrderValueIsSet(false);
    this.orderValue = 0;
    setIsHaveChildIsSet(false);
    this.isHaveChild = false;
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
  public ProductCategoryVo setId(long id) {
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
   * 客户端id
   */
  public String getClientId() {
    return this.clientId;
  }

  /**
   * 客户端id
   */
  public ProductCategoryVo setClientId(String clientId) {
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
   * name
   */
  public String getName() {
    return this.name;
  }

  /**
   * name
   */
  public ProductCategoryVo setName(String name) {
    this.name = name;
    return this;
  }

  public void unsetName() {
    this.name = null;
  }

  /** Returns true if field name is set (has been assigned a value) and false otherwise */
  public boolean isSetName() {
    return this.name != null;
  }

  public void setNameIsSet(boolean value) {
    if (!value) {
      this.name = null;
    }
  }

  /**
   * 树路径 空格间隔
   */
  public String getTreePath() {
    return this.treePath;
  }

  /**
   * 树路径 空格间隔
   */
  public ProductCategoryVo setTreePath(String treePath) {
    this.treePath = treePath;
    return this;
  }

  public void unsetTreePath() {
    this.treePath = null;
  }

  /** Returns true if field treePath is set (has been assigned a value) and false otherwise */
  public boolean isSetTreePath() {
    return this.treePath != null;
  }

  public void setTreePathIsSet(boolean value) {
    if (!value) {
      this.treePath = null;
    }
  }

  /**
   * 父节点id
   */
  public long getParentId() {
    return this.parentId;
  }

  /**
   * 父节点id
   */
  public ProductCategoryVo setParentId(long parentId) {
    this.parentId = parentId;
    setParentIdIsSet(true);
    return this;
  }

  public void unsetParentId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PARENTID_ISSET_ID);
  }

  /** Returns true if field parentId is set (has been assigned a value) and false otherwise */
  public boolean isSetParentId() {
    return EncodingUtils.testBit(__isset_bitfield, __PARENTID_ISSET_ID);
  }

  public void setParentIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PARENTID_ISSET_ID, value);
  }

  /**
   * 小图标
   */
  public String getIcoUrl() {
    return this.icoUrl;
  }

  /**
   * 小图标
   */
  public ProductCategoryVo setIcoUrl(String icoUrl) {
    this.icoUrl = icoUrl;
    return this;
  }

  public void unsetIcoUrl() {
    this.icoUrl = null;
  }

  /** Returns true if field icoUrl is set (has been assigned a value) and false otherwise */
  public boolean isSetIcoUrl() {
    return this.icoUrl != null;
  }

  public void setIcoUrlIsSet(boolean value) {
    if (!value) {
      this.icoUrl = null;
    }
  }

  /**
   * 排序
   */
  public short getOrderValue() {
    return this.orderValue;
  }

  /**
   * 排序
   */
  public ProductCategoryVo setOrderValue(short orderValue) {
    this.orderValue = orderValue;
    setOrderValueIsSet(true);
    return this;
  }

  public void unsetOrderValue() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ORDERVALUE_ISSET_ID);
  }

  /** Returns true if field orderValue is set (has been assigned a value) and false otherwise */
  public boolean isSetOrderValue() {
    return EncodingUtils.testBit(__isset_bitfield, __ORDERVALUE_ISSET_ID);
  }

  public void setOrderValueIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ORDERVALUE_ISSET_ID, value);
  }

  /**
   * 是否有子分类
   */
  public boolean isIsHaveChild() {
    return this.isHaveChild;
  }

  /**
   * 是否有子分类
   */
  public ProductCategoryVo setIsHaveChild(boolean isHaveChild) {
    this.isHaveChild = isHaveChild;
    setIsHaveChildIsSet(true);
    return this;
  }

  public void unsetIsHaveChild() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ISHAVECHILD_ISSET_ID);
  }

  /** Returns true if field isHaveChild is set (has been assigned a value) and false otherwise */
  public boolean isSetIsHaveChild() {
    return EncodingUtils.testBit(__isset_bitfield, __ISHAVECHILD_ISSET_ID);
  }

  public void setIsHaveChildIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ISHAVECHILD_ISSET_ID, value);
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

    case NAME:
      if (value == null) {
        unsetName();
      } else {
        setName((String)value);
      }
      break;

    case TREE_PATH:
      if (value == null) {
        unsetTreePath();
      } else {
        setTreePath((String)value);
      }
      break;

    case PARENT_ID:
      if (value == null) {
        unsetParentId();
      } else {
        setParentId((Long)value);
      }
      break;

    case ICO_URL:
      if (value == null) {
        unsetIcoUrl();
      } else {
        setIcoUrl((String)value);
      }
      break;

    case ORDER_VALUE:
      if (value == null) {
        unsetOrderValue();
      } else {
        setOrderValue((Short)value);
      }
      break;

    case IS_HAVE_CHILD:
      if (value == null) {
        unsetIsHaveChild();
      } else {
        setIsHaveChild((Boolean)value);
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

    case NAME:
      return getName();

    case TREE_PATH:
      return getTreePath();

    case PARENT_ID:
      return Long.valueOf(getParentId());

    case ICO_URL:
      return getIcoUrl();

    case ORDER_VALUE:
      return Short.valueOf(getOrderValue());

    case IS_HAVE_CHILD:
      return Boolean.valueOf(isIsHaveChild());

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
    case NAME:
      return isSetName();
    case TREE_PATH:
      return isSetTreePath();
    case PARENT_ID:
      return isSetParentId();
    case ICO_URL:
      return isSetIcoUrl();
    case ORDER_VALUE:
      return isSetOrderValue();
    case IS_HAVE_CHILD:
      return isSetIsHaveChild();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ProductCategoryVo)
      return this.equals((ProductCategoryVo)that);
    return false;
  }

  public boolean equals(ProductCategoryVo that) {
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

    boolean this_present_name = true && this.isSetName();
    boolean that_present_name = true && that.isSetName();
    if (this_present_name || that_present_name) {
      if (!(this_present_name && that_present_name))
        return false;
      if (!this.name.equals(that.name))
        return false;
    }

    boolean this_present_treePath = true && this.isSetTreePath();
    boolean that_present_treePath = true && that.isSetTreePath();
    if (this_present_treePath || that_present_treePath) {
      if (!(this_present_treePath && that_present_treePath))
        return false;
      if (!this.treePath.equals(that.treePath))
        return false;
    }

    boolean this_present_parentId = true;
    boolean that_present_parentId = true;
    if (this_present_parentId || that_present_parentId) {
      if (!(this_present_parentId && that_present_parentId))
        return false;
      if (this.parentId != that.parentId)
        return false;
    }

    boolean this_present_icoUrl = true && this.isSetIcoUrl();
    boolean that_present_icoUrl = true && that.isSetIcoUrl();
    if (this_present_icoUrl || that_present_icoUrl) {
      if (!(this_present_icoUrl && that_present_icoUrl))
        return false;
      if (!this.icoUrl.equals(that.icoUrl))
        return false;
    }

    boolean this_present_orderValue = true;
    boolean that_present_orderValue = true;
    if (this_present_orderValue || that_present_orderValue) {
      if (!(this_present_orderValue && that_present_orderValue))
        return false;
      if (this.orderValue != that.orderValue)
        return false;
    }

    boolean this_present_isHaveChild = true;
    boolean that_present_isHaveChild = true;
    if (this_present_isHaveChild || that_present_isHaveChild) {
      if (!(this_present_isHaveChild && that_present_isHaveChild))
        return false;
      if (this.isHaveChild != that.isHaveChild)
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

    boolean present_name = true && (isSetName());
    list.add(present_name);
    if (present_name)
      list.add(name);

    boolean present_treePath = true && (isSetTreePath());
    list.add(present_treePath);
    if (present_treePath)
      list.add(treePath);

    boolean present_parentId = true;
    list.add(present_parentId);
    if (present_parentId)
      list.add(parentId);

    boolean present_icoUrl = true && (isSetIcoUrl());
    list.add(present_icoUrl);
    if (present_icoUrl)
      list.add(icoUrl);

    boolean present_orderValue = true;
    list.add(present_orderValue);
    if (present_orderValue)
      list.add(orderValue);

    boolean present_isHaveChild = true;
    list.add(present_isHaveChild);
    if (present_isHaveChild)
      list.add(isHaveChild);

    return list.hashCode();
  }

  @Override
  public int compareTo(ProductCategoryVo other) {
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
    lastComparison = Boolean.valueOf(isSetName()).compareTo(other.isSetName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.name, other.name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTreePath()).compareTo(other.isSetTreePath());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTreePath()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.treePath, other.treePath);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetParentId()).compareTo(other.isSetParentId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetParentId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.parentId, other.parentId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetIcoUrl()).compareTo(other.isSetIcoUrl());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIcoUrl()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.icoUrl, other.icoUrl);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOrderValue()).compareTo(other.isSetOrderValue());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOrderValue()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.orderValue, other.orderValue);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetIsHaveChild()).compareTo(other.isSetIsHaveChild());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIsHaveChild()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.isHaveChild, other.isHaveChild);
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
    StringBuilder sb = new StringBuilder("ProductCategoryVo(");
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
    sb.append("name:");
    if (this.name == null) {
      sb.append("null");
    } else {
      sb.append(this.name);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("treePath:");
    if (this.treePath == null) {
      sb.append("null");
    } else {
      sb.append(this.treePath);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("parentId:");
    sb.append(this.parentId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("icoUrl:");
    if (this.icoUrl == null) {
      sb.append("null");
    } else {
      sb.append(this.icoUrl);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("orderValue:");
    sb.append(this.orderValue);
    first = false;
    if (!first) sb.append(", ");
    sb.append("isHaveChild:");
    sb.append(this.isHaveChild);
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

  private static class ProductCategoryVoStandardSchemeFactory implements SchemeFactory {
    public ProductCategoryVoStandardScheme getScheme() {
      return new ProductCategoryVoStandardScheme();
    }
  }

  private static class ProductCategoryVoStandardScheme extends StandardScheme<ProductCategoryVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ProductCategoryVo struct) throws org.apache.thrift.TException {
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
          case 3: // NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.name = iprot.readString();
              struct.setNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // TREE_PATH
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.treePath = iprot.readString();
              struct.setTreePathIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // PARENT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.parentId = iprot.readI64();
              struct.setParentIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // ICO_URL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.icoUrl = iprot.readString();
              struct.setIcoUrlIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // ORDER_VALUE
            if (schemeField.type == org.apache.thrift.protocol.TType.I16) {
              struct.orderValue = iprot.readI16();
              struct.setOrderValueIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // IS_HAVE_CHILD
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.isHaveChild = iprot.readBool();
              struct.setIsHaveChildIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ProductCategoryVo struct) throws org.apache.thrift.TException {
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
      if (struct.name != null) {
        oprot.writeFieldBegin(NAME_FIELD_DESC);
        oprot.writeString(struct.name);
        oprot.writeFieldEnd();
      }
      if (struct.treePath != null) {
        oprot.writeFieldBegin(TREE_PATH_FIELD_DESC);
        oprot.writeString(struct.treePath);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(PARENT_ID_FIELD_DESC);
      oprot.writeI64(struct.parentId);
      oprot.writeFieldEnd();
      if (struct.icoUrl != null) {
        oprot.writeFieldBegin(ICO_URL_FIELD_DESC);
        oprot.writeString(struct.icoUrl);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(ORDER_VALUE_FIELD_DESC);
      oprot.writeI16(struct.orderValue);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(IS_HAVE_CHILD_FIELD_DESC);
      oprot.writeBool(struct.isHaveChild);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ProductCategoryVoTupleSchemeFactory implements SchemeFactory {
    public ProductCategoryVoTupleScheme getScheme() {
      return new ProductCategoryVoTupleScheme();
    }
  }

  private static class ProductCategoryVoTupleScheme extends TupleScheme<ProductCategoryVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ProductCategoryVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetClientId()) {
        optionals.set(1);
      }
      if (struct.isSetName()) {
        optionals.set(2);
      }
      if (struct.isSetTreePath()) {
        optionals.set(3);
      }
      if (struct.isSetParentId()) {
        optionals.set(4);
      }
      if (struct.isSetIcoUrl()) {
        optionals.set(5);
      }
      if (struct.isSetOrderValue()) {
        optionals.set(6);
      }
      if (struct.isSetIsHaveChild()) {
        optionals.set(7);
      }
      oprot.writeBitSet(optionals, 8);
      if (struct.isSetId()) {
        oprot.writeI64(struct.id);
      }
      if (struct.isSetClientId()) {
        oprot.writeString(struct.clientId);
      }
      if (struct.isSetName()) {
        oprot.writeString(struct.name);
      }
      if (struct.isSetTreePath()) {
        oprot.writeString(struct.treePath);
      }
      if (struct.isSetParentId()) {
        oprot.writeI64(struct.parentId);
      }
      if (struct.isSetIcoUrl()) {
        oprot.writeString(struct.icoUrl);
      }
      if (struct.isSetOrderValue()) {
        oprot.writeI16(struct.orderValue);
      }
      if (struct.isSetIsHaveChild()) {
        oprot.writeBool(struct.isHaveChild);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ProductCategoryVo struct) throws org.apache.thrift.TException {
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
        struct.name = iprot.readString();
        struct.setNameIsSet(true);
      }
      if (incoming.get(3)) {
        struct.treePath = iprot.readString();
        struct.setTreePathIsSet(true);
      }
      if (incoming.get(4)) {
        struct.parentId = iprot.readI64();
        struct.setParentIdIsSet(true);
      }
      if (incoming.get(5)) {
        struct.icoUrl = iprot.readString();
        struct.setIcoUrlIsSet(true);
      }
      if (incoming.get(6)) {
        struct.orderValue = iprot.readI16();
        struct.setOrderValueIsSet(true);
      }
      if (incoming.get(7)) {
        struct.isHaveChild = iprot.readBool();
        struct.setIsHaveChildIsSet(true);
      }
    }
  }

}

