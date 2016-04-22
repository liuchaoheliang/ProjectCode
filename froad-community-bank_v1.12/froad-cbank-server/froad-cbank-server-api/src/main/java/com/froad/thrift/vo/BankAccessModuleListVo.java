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
 * 客户端功能列表vo
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class BankAccessModuleListVo implements org.apache.thrift.TBase<BankAccessModuleListVo, BankAccessModuleListVo._Fields>, java.io.Serializable, Cloneable, Comparable<BankAccessModuleListVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("BankAccessModuleListVo");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField CLIENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("clientId", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("type", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField MODULE_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("moduleName", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField MODULE_ALIAS_FIELD_DESC = new org.apache.thrift.protocol.TField("moduleAlias", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField ICON_URL_FIELD_DESC = new org.apache.thrift.protocol.TField("iconUrl", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField SORT_VALUE_FIELD_DESC = new org.apache.thrift.protocol.TField("sortValue", org.apache.thrift.protocol.TType.STRING, (short)7);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new BankAccessModuleListVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new BankAccessModuleListVoTupleSchemeFactory());
  }

  /**
   * id
   */
  public String id; // optional
  /**
   * 客户端Id
   */
  public String clientId; // optional
  /**
   * 功能模块类型1:特惠商户;2:特惠商品;3:精品预售;4:扫码支付；5:积分兑换；
   */
  public String type; // optional
  /**
   * 模块名称
   */
  public String moduleName; // optional
  /**
   * 模块别名
   */
  public String moduleAlias; // optional
  /**
   * 模块图标url
   */
  public String iconUrl; // optional
  /**
   * 排序值
   */
  public String sortValue; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * id
     */
    ID((short)1, "id"),
    /**
     * 客户端Id
     */
    CLIENT_ID((short)2, "clientId"),
    /**
     * 功能模块类型1:特惠商户;2:特惠商品;3:精品预售;4:扫码支付；5:积分兑换；
     */
    TYPE((short)3, "type"),
    /**
     * 模块名称
     */
    MODULE_NAME((short)4, "moduleName"),
    /**
     * 模块别名
     */
    MODULE_ALIAS((short)5, "moduleAlias"),
    /**
     * 模块图标url
     */
    ICON_URL((short)6, "iconUrl"),
    /**
     * 排序值
     */
    SORT_VALUE((short)7, "sortValue");

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
        case 3: // TYPE
          return TYPE;
        case 4: // MODULE_NAME
          return MODULE_NAME;
        case 5: // MODULE_ALIAS
          return MODULE_ALIAS;
        case 6: // ICON_URL
          return ICON_URL;
        case 7: // SORT_VALUE
          return SORT_VALUE;
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
  private static final _Fields optionals[] = {_Fields.ID,_Fields.CLIENT_ID,_Fields.TYPE,_Fields.MODULE_NAME,_Fields.MODULE_ALIAS,_Fields.ICON_URL,_Fields.SORT_VALUE};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CLIENT_ID, new org.apache.thrift.meta_data.FieldMetaData("clientId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TYPE, new org.apache.thrift.meta_data.FieldMetaData("type", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.MODULE_NAME, new org.apache.thrift.meta_data.FieldMetaData("moduleName", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.MODULE_ALIAS, new org.apache.thrift.meta_data.FieldMetaData("moduleAlias", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ICON_URL, new org.apache.thrift.meta_data.FieldMetaData("iconUrl", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SORT_VALUE, new org.apache.thrift.meta_data.FieldMetaData("sortValue", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(BankAccessModuleListVo.class, metaDataMap);
  }

  public BankAccessModuleListVo() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public BankAccessModuleListVo(BankAccessModuleListVo other) {
    if (other.isSetId()) {
      this.id = other.id;
    }
    if (other.isSetClientId()) {
      this.clientId = other.clientId;
    }
    if (other.isSetType()) {
      this.type = other.type;
    }
    if (other.isSetModuleName()) {
      this.moduleName = other.moduleName;
    }
    if (other.isSetModuleAlias()) {
      this.moduleAlias = other.moduleAlias;
    }
    if (other.isSetIconUrl()) {
      this.iconUrl = other.iconUrl;
    }
    if (other.isSetSortValue()) {
      this.sortValue = other.sortValue;
    }
  }

  public BankAccessModuleListVo deepCopy() {
    return new BankAccessModuleListVo(this);
  }

  @Override
  public void clear() {
    this.id = null;
    this.clientId = null;
    this.type = null;
    this.moduleName = null;
    this.moduleAlias = null;
    this.iconUrl = null;
    this.sortValue = null;
  }

  /**
   * id
   */
  public String getId() {
    return this.id;
  }

  /**
   * id
   */
  public BankAccessModuleListVo setId(String id) {
    this.id = id;
    return this;
  }

  public void unsetId() {
    this.id = null;
  }

  /** Returns true if field id is set (has been assigned a value) and false otherwise */
  public boolean isSetId() {
    return this.id != null;
  }

  public void setIdIsSet(boolean value) {
    if (!value) {
      this.id = null;
    }
  }

  /**
   * 客户端Id
   */
  public String getClientId() {
    return this.clientId;
  }

  /**
   * 客户端Id
   */
  public BankAccessModuleListVo setClientId(String clientId) {
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
   * 功能模块类型1:特惠商户;2:特惠商品;3:精品预售;4:扫码支付；5:积分兑换；
   */
  public String getType() {
    return this.type;
  }

  /**
   * 功能模块类型1:特惠商户;2:特惠商品;3:精品预售;4:扫码支付；5:积分兑换；
   */
  public BankAccessModuleListVo setType(String type) {
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

  /**
   * 模块名称
   */
  public String getModuleName() {
    return this.moduleName;
  }

  /**
   * 模块名称
   */
  public BankAccessModuleListVo setModuleName(String moduleName) {
    this.moduleName = moduleName;
    return this;
  }

  public void unsetModuleName() {
    this.moduleName = null;
  }

  /** Returns true if field moduleName is set (has been assigned a value) and false otherwise */
  public boolean isSetModuleName() {
    return this.moduleName != null;
  }

  public void setModuleNameIsSet(boolean value) {
    if (!value) {
      this.moduleName = null;
    }
  }

  /**
   * 模块别名
   */
  public String getModuleAlias() {
    return this.moduleAlias;
  }

  /**
   * 模块别名
   */
  public BankAccessModuleListVo setModuleAlias(String moduleAlias) {
    this.moduleAlias = moduleAlias;
    return this;
  }

  public void unsetModuleAlias() {
    this.moduleAlias = null;
  }

  /** Returns true if field moduleAlias is set (has been assigned a value) and false otherwise */
  public boolean isSetModuleAlias() {
    return this.moduleAlias != null;
  }

  public void setModuleAliasIsSet(boolean value) {
    if (!value) {
      this.moduleAlias = null;
    }
  }

  /**
   * 模块图标url
   */
  public String getIconUrl() {
    return this.iconUrl;
  }

  /**
   * 模块图标url
   */
  public BankAccessModuleListVo setIconUrl(String iconUrl) {
    this.iconUrl = iconUrl;
    return this;
  }

  public void unsetIconUrl() {
    this.iconUrl = null;
  }

  /** Returns true if field iconUrl is set (has been assigned a value) and false otherwise */
  public boolean isSetIconUrl() {
    return this.iconUrl != null;
  }

  public void setIconUrlIsSet(boolean value) {
    if (!value) {
      this.iconUrl = null;
    }
  }

  /**
   * 排序值
   */
  public String getSortValue() {
    return this.sortValue;
  }

  /**
   * 排序值
   */
  public BankAccessModuleListVo setSortValue(String sortValue) {
    this.sortValue = sortValue;
    return this;
  }

  public void unsetSortValue() {
    this.sortValue = null;
  }

  /** Returns true if field sortValue is set (has been assigned a value) and false otherwise */
  public boolean isSetSortValue() {
    return this.sortValue != null;
  }

  public void setSortValueIsSet(boolean value) {
    if (!value) {
      this.sortValue = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((String)value);
      }
      break;

    case CLIENT_ID:
      if (value == null) {
        unsetClientId();
      } else {
        setClientId((String)value);
      }
      break;

    case TYPE:
      if (value == null) {
        unsetType();
      } else {
        setType((String)value);
      }
      break;

    case MODULE_NAME:
      if (value == null) {
        unsetModuleName();
      } else {
        setModuleName((String)value);
      }
      break;

    case MODULE_ALIAS:
      if (value == null) {
        unsetModuleAlias();
      } else {
        setModuleAlias((String)value);
      }
      break;

    case ICON_URL:
      if (value == null) {
        unsetIconUrl();
      } else {
        setIconUrl((String)value);
      }
      break;

    case SORT_VALUE:
      if (value == null) {
        unsetSortValue();
      } else {
        setSortValue((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return getId();

    case CLIENT_ID:
      return getClientId();

    case TYPE:
      return getType();

    case MODULE_NAME:
      return getModuleName();

    case MODULE_ALIAS:
      return getModuleAlias();

    case ICON_URL:
      return getIconUrl();

    case SORT_VALUE:
      return getSortValue();

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
    case TYPE:
      return isSetType();
    case MODULE_NAME:
      return isSetModuleName();
    case MODULE_ALIAS:
      return isSetModuleAlias();
    case ICON_URL:
      return isSetIconUrl();
    case SORT_VALUE:
      return isSetSortValue();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof BankAccessModuleListVo)
      return this.equals((BankAccessModuleListVo)that);
    return false;
  }

  public boolean equals(BankAccessModuleListVo that) {
    if (that == null)
      return false;

    boolean this_present_id = true && this.isSetId();
    boolean that_present_id = true && that.isSetId();
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (!this.id.equals(that.id))
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

    boolean this_present_type = true && this.isSetType();
    boolean that_present_type = true && that.isSetType();
    if (this_present_type || that_present_type) {
      if (!(this_present_type && that_present_type))
        return false;
      if (!this.type.equals(that.type))
        return false;
    }

    boolean this_present_moduleName = true && this.isSetModuleName();
    boolean that_present_moduleName = true && that.isSetModuleName();
    if (this_present_moduleName || that_present_moduleName) {
      if (!(this_present_moduleName && that_present_moduleName))
        return false;
      if (!this.moduleName.equals(that.moduleName))
        return false;
    }

    boolean this_present_moduleAlias = true && this.isSetModuleAlias();
    boolean that_present_moduleAlias = true && that.isSetModuleAlias();
    if (this_present_moduleAlias || that_present_moduleAlias) {
      if (!(this_present_moduleAlias && that_present_moduleAlias))
        return false;
      if (!this.moduleAlias.equals(that.moduleAlias))
        return false;
    }

    boolean this_present_iconUrl = true && this.isSetIconUrl();
    boolean that_present_iconUrl = true && that.isSetIconUrl();
    if (this_present_iconUrl || that_present_iconUrl) {
      if (!(this_present_iconUrl && that_present_iconUrl))
        return false;
      if (!this.iconUrl.equals(that.iconUrl))
        return false;
    }

    boolean this_present_sortValue = true && this.isSetSortValue();
    boolean that_present_sortValue = true && that.isSetSortValue();
    if (this_present_sortValue || that_present_sortValue) {
      if (!(this_present_sortValue && that_present_sortValue))
        return false;
      if (!this.sortValue.equals(that.sortValue))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_id = true && (isSetId());
    list.add(present_id);
    if (present_id)
      list.add(id);

    boolean present_clientId = true && (isSetClientId());
    list.add(present_clientId);
    if (present_clientId)
      list.add(clientId);

    boolean present_type = true && (isSetType());
    list.add(present_type);
    if (present_type)
      list.add(type);

    boolean present_moduleName = true && (isSetModuleName());
    list.add(present_moduleName);
    if (present_moduleName)
      list.add(moduleName);

    boolean present_moduleAlias = true && (isSetModuleAlias());
    list.add(present_moduleAlias);
    if (present_moduleAlias)
      list.add(moduleAlias);

    boolean present_iconUrl = true && (isSetIconUrl());
    list.add(present_iconUrl);
    if (present_iconUrl)
      list.add(iconUrl);

    boolean present_sortValue = true && (isSetSortValue());
    list.add(present_sortValue);
    if (present_sortValue)
      list.add(sortValue);

    return list.hashCode();
  }

  @Override
  public int compareTo(BankAccessModuleListVo other) {
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
    lastComparison = Boolean.valueOf(isSetModuleName()).compareTo(other.isSetModuleName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetModuleName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.moduleName, other.moduleName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetModuleAlias()).compareTo(other.isSetModuleAlias());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetModuleAlias()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.moduleAlias, other.moduleAlias);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetIconUrl()).compareTo(other.isSetIconUrl());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIconUrl()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.iconUrl, other.iconUrl);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSortValue()).compareTo(other.isSetSortValue());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSortValue()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sortValue, other.sortValue);
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
    StringBuilder sb = new StringBuilder("BankAccessModuleListVo(");
    boolean first = true;

    if (isSetId()) {
      sb.append("id:");
      if (this.id == null) {
        sb.append("null");
      } else {
        sb.append(this.id);
      }
      first = false;
    }
    if (isSetClientId()) {
      if (!first) sb.append(", ");
      sb.append("clientId:");
      if (this.clientId == null) {
        sb.append("null");
      } else {
        sb.append(this.clientId);
      }
      first = false;
    }
    if (isSetType()) {
      if (!first) sb.append(", ");
      sb.append("type:");
      if (this.type == null) {
        sb.append("null");
      } else {
        sb.append(this.type);
      }
      first = false;
    }
    if (isSetModuleName()) {
      if (!first) sb.append(", ");
      sb.append("moduleName:");
      if (this.moduleName == null) {
        sb.append("null");
      } else {
        sb.append(this.moduleName);
      }
      first = false;
    }
    if (isSetModuleAlias()) {
      if (!first) sb.append(", ");
      sb.append("moduleAlias:");
      if (this.moduleAlias == null) {
        sb.append("null");
      } else {
        sb.append(this.moduleAlias);
      }
      first = false;
    }
    if (isSetIconUrl()) {
      if (!first) sb.append(", ");
      sb.append("iconUrl:");
      if (this.iconUrl == null) {
        sb.append("null");
      } else {
        sb.append(this.iconUrl);
      }
      first = false;
    }
    if (isSetSortValue()) {
      if (!first) sb.append(", ");
      sb.append("sortValue:");
      if (this.sortValue == null) {
        sb.append("null");
      } else {
        sb.append(this.sortValue);
      }
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class BankAccessModuleListVoStandardSchemeFactory implements SchemeFactory {
    public BankAccessModuleListVoStandardScheme getScheme() {
      return new BankAccessModuleListVoStandardScheme();
    }
  }

  private static class BankAccessModuleListVoStandardScheme extends StandardScheme<BankAccessModuleListVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, BankAccessModuleListVo struct) throws org.apache.thrift.TException {
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
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.id = iprot.readString();
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
          case 3: // TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.type = iprot.readString();
              struct.setTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // MODULE_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.moduleName = iprot.readString();
              struct.setModuleNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // MODULE_ALIAS
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.moduleAlias = iprot.readString();
              struct.setModuleAliasIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // ICON_URL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.iconUrl = iprot.readString();
              struct.setIconUrlIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // SORT_VALUE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.sortValue = iprot.readString();
              struct.setSortValueIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, BankAccessModuleListVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.id != null) {
        if (struct.isSetId()) {
          oprot.writeFieldBegin(ID_FIELD_DESC);
          oprot.writeString(struct.id);
          oprot.writeFieldEnd();
        }
      }
      if (struct.clientId != null) {
        if (struct.isSetClientId()) {
          oprot.writeFieldBegin(CLIENT_ID_FIELD_DESC);
          oprot.writeString(struct.clientId);
          oprot.writeFieldEnd();
        }
      }
      if (struct.type != null) {
        if (struct.isSetType()) {
          oprot.writeFieldBegin(TYPE_FIELD_DESC);
          oprot.writeString(struct.type);
          oprot.writeFieldEnd();
        }
      }
      if (struct.moduleName != null) {
        if (struct.isSetModuleName()) {
          oprot.writeFieldBegin(MODULE_NAME_FIELD_DESC);
          oprot.writeString(struct.moduleName);
          oprot.writeFieldEnd();
        }
      }
      if (struct.moduleAlias != null) {
        if (struct.isSetModuleAlias()) {
          oprot.writeFieldBegin(MODULE_ALIAS_FIELD_DESC);
          oprot.writeString(struct.moduleAlias);
          oprot.writeFieldEnd();
        }
      }
      if (struct.iconUrl != null) {
        if (struct.isSetIconUrl()) {
          oprot.writeFieldBegin(ICON_URL_FIELD_DESC);
          oprot.writeString(struct.iconUrl);
          oprot.writeFieldEnd();
        }
      }
      if (struct.sortValue != null) {
        if (struct.isSetSortValue()) {
          oprot.writeFieldBegin(SORT_VALUE_FIELD_DESC);
          oprot.writeString(struct.sortValue);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class BankAccessModuleListVoTupleSchemeFactory implements SchemeFactory {
    public BankAccessModuleListVoTupleScheme getScheme() {
      return new BankAccessModuleListVoTupleScheme();
    }
  }

  private static class BankAccessModuleListVoTupleScheme extends TupleScheme<BankAccessModuleListVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, BankAccessModuleListVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetClientId()) {
        optionals.set(1);
      }
      if (struct.isSetType()) {
        optionals.set(2);
      }
      if (struct.isSetModuleName()) {
        optionals.set(3);
      }
      if (struct.isSetModuleAlias()) {
        optionals.set(4);
      }
      if (struct.isSetIconUrl()) {
        optionals.set(5);
      }
      if (struct.isSetSortValue()) {
        optionals.set(6);
      }
      oprot.writeBitSet(optionals, 7);
      if (struct.isSetId()) {
        oprot.writeString(struct.id);
      }
      if (struct.isSetClientId()) {
        oprot.writeString(struct.clientId);
      }
      if (struct.isSetType()) {
        oprot.writeString(struct.type);
      }
      if (struct.isSetModuleName()) {
        oprot.writeString(struct.moduleName);
      }
      if (struct.isSetModuleAlias()) {
        oprot.writeString(struct.moduleAlias);
      }
      if (struct.isSetIconUrl()) {
        oprot.writeString(struct.iconUrl);
      }
      if (struct.isSetSortValue()) {
        oprot.writeString(struct.sortValue);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, BankAccessModuleListVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(7);
      if (incoming.get(0)) {
        struct.id = iprot.readString();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.clientId = iprot.readString();
        struct.setClientIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.type = iprot.readString();
        struct.setTypeIsSet(true);
      }
      if (incoming.get(3)) {
        struct.moduleName = iprot.readString();
        struct.setModuleNameIsSet(true);
      }
      if (incoming.get(4)) {
        struct.moduleAlias = iprot.readString();
        struct.setModuleAliasIsSet(true);
      }
      if (incoming.get(5)) {
        struct.iconUrl = iprot.readString();
        struct.setIconUrlIsSet(true);
      }
      if (incoming.get(6)) {
        struct.sortValue = iprot.readString();
        struct.setSortValueIsSet(true);
      }
    }
  }

}

