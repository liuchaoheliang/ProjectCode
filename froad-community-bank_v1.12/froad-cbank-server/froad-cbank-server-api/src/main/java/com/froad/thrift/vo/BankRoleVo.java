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
 * 银行角色Vo
 * BankRoleVo
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class BankRoleVo implements org.apache.thrift.TBase<BankRoleVo, BankRoleVo._Fields>, java.io.Serializable, Cloneable, Comparable<BankRoleVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("BankRoleVo");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField CLIENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("clientId", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField ROLE_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("roleName", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("status", org.apache.thrift.protocol.TType.BOOL, (short)4);
  private static final org.apache.thrift.protocol.TField REMARK_FIELD_DESC = new org.apache.thrift.protocol.TField("remark", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField IS_DELETE_FIELD_DESC = new org.apache.thrift.protocol.TField("isDelete", org.apache.thrift.protocol.TType.BOOL, (short)6);
  private static final org.apache.thrift.protocol.TField TAG_FIELD_DESC = new org.apache.thrift.protocol.TField("tag", org.apache.thrift.protocol.TType.STRING, (short)7);
  private static final org.apache.thrift.protocol.TField ORG_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("orgCode", org.apache.thrift.protocol.TType.STRING, (short)8);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new BankRoleVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new BankRoleVoTupleSchemeFactory());
  }

  /**
   * 主键id 角色id
   */
  public long id; // optional
  /**
   * 客户端id
   */
  public String clientId; // optional
  /**
   * 角色名称
   */
  public String roleName; // optional
  /**
   * 0-不可用 1-可用
   */
  public boolean status; // optional
  /**
   * 备注
   */
  public String remark; // optional
  /**
   * 是否删除 0-未删除 1-删除
   */
  public boolean isDelete; // optional
  /**
   * 标明角色身份，身份比如管理员、操作员(管理员暂时定为0)
   */
  public String tag; // optional
  /**
   * 创建角色的机构号
   */
  public String orgCode; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 主键id 角色id
     */
    ID((short)1, "id"),
    /**
     * 客户端id
     */
    CLIENT_ID((short)2, "clientId"),
    /**
     * 角色名称
     */
    ROLE_NAME((short)3, "roleName"),
    /**
     * 0-不可用 1-可用
     */
    STATUS((short)4, "status"),
    /**
     * 备注
     */
    REMARK((short)5, "remark"),
    /**
     * 是否删除 0-未删除 1-删除
     */
    IS_DELETE((short)6, "isDelete"),
    /**
     * 标明角色身份，身份比如管理员、操作员(管理员暂时定为0)
     */
    TAG((short)7, "tag"),
    /**
     * 创建角色的机构号
     */
    ORG_CODE((short)8, "orgCode");

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
        case 3: // ROLE_NAME
          return ROLE_NAME;
        case 4: // STATUS
          return STATUS;
        case 5: // REMARK
          return REMARK;
        case 6: // IS_DELETE
          return IS_DELETE;
        case 7: // TAG
          return TAG;
        case 8: // ORG_CODE
          return ORG_CODE;
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
  private static final int __STATUS_ISSET_ID = 1;
  private static final int __ISDELETE_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.ID,_Fields.CLIENT_ID,_Fields.ROLE_NAME,_Fields.STATUS,_Fields.REMARK,_Fields.IS_DELETE,_Fields.TAG,_Fields.ORG_CODE};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.CLIENT_ID, new org.apache.thrift.meta_data.FieldMetaData("clientId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ROLE_NAME, new org.apache.thrift.meta_data.FieldMetaData("roleName", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.STATUS, new org.apache.thrift.meta_data.FieldMetaData("status", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.REMARK, new org.apache.thrift.meta_data.FieldMetaData("remark", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.IS_DELETE, new org.apache.thrift.meta_data.FieldMetaData("isDelete", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.TAG, new org.apache.thrift.meta_data.FieldMetaData("tag", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ORG_CODE, new org.apache.thrift.meta_data.FieldMetaData("orgCode", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(BankRoleVo.class, metaDataMap);
  }

  public BankRoleVo() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public BankRoleVo(BankRoleVo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    if (other.isSetClientId()) {
      this.clientId = other.clientId;
    }
    if (other.isSetRoleName()) {
      this.roleName = other.roleName;
    }
    this.status = other.status;
    if (other.isSetRemark()) {
      this.remark = other.remark;
    }
    this.isDelete = other.isDelete;
    if (other.isSetTag()) {
      this.tag = other.tag;
    }
    if (other.isSetOrgCode()) {
      this.orgCode = other.orgCode;
    }
  }

  public BankRoleVo deepCopy() {
    return new BankRoleVo(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    this.clientId = null;
    this.roleName = null;
    setStatusIsSet(false);
    this.status = false;
    this.remark = null;
    setIsDeleteIsSet(false);
    this.isDelete = false;
    this.tag = null;
    this.orgCode = null;
  }

  /**
   * 主键id 角色id
   */
  public long getId() {
    return this.id;
  }

  /**
   * 主键id 角色id
   */
  public BankRoleVo setId(long id) {
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
  public BankRoleVo setClientId(String clientId) {
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
   * 角色名称
   */
  public String getRoleName() {
    return this.roleName;
  }

  /**
   * 角色名称
   */
  public BankRoleVo setRoleName(String roleName) {
    this.roleName = roleName;
    return this;
  }

  public void unsetRoleName() {
    this.roleName = null;
  }

  /** Returns true if field roleName is set (has been assigned a value) and false otherwise */
  public boolean isSetRoleName() {
    return this.roleName != null;
  }

  public void setRoleNameIsSet(boolean value) {
    if (!value) {
      this.roleName = null;
    }
  }

  /**
   * 0-不可用 1-可用
   */
  public boolean isStatus() {
    return this.status;
  }

  /**
   * 0-不可用 1-可用
   */
  public BankRoleVo setStatus(boolean status) {
    this.status = status;
    setStatusIsSet(true);
    return this;
  }

  public void unsetStatus() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __STATUS_ISSET_ID);
  }

  /** Returns true if field status is set (has been assigned a value) and false otherwise */
  public boolean isSetStatus() {
    return EncodingUtils.testBit(__isset_bitfield, __STATUS_ISSET_ID);
  }

  public void setStatusIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __STATUS_ISSET_ID, value);
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
  public BankRoleVo setRemark(String remark) {
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

  /**
   * 是否删除 0-未删除 1-删除
   */
  public boolean isIsDelete() {
    return this.isDelete;
  }

  /**
   * 是否删除 0-未删除 1-删除
   */
  public BankRoleVo setIsDelete(boolean isDelete) {
    this.isDelete = isDelete;
    setIsDeleteIsSet(true);
    return this;
  }

  public void unsetIsDelete() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ISDELETE_ISSET_ID);
  }

  /** Returns true if field isDelete is set (has been assigned a value) and false otherwise */
  public boolean isSetIsDelete() {
    return EncodingUtils.testBit(__isset_bitfield, __ISDELETE_ISSET_ID);
  }

  public void setIsDeleteIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ISDELETE_ISSET_ID, value);
  }

  /**
   * 标明角色身份，身份比如管理员、操作员(管理员暂时定为0)
   */
  public String getTag() {
    return this.tag;
  }

  /**
   * 标明角色身份，身份比如管理员、操作员(管理员暂时定为0)
   */
  public BankRoleVo setTag(String tag) {
    this.tag = tag;
    return this;
  }

  public void unsetTag() {
    this.tag = null;
  }

  /** Returns true if field tag is set (has been assigned a value) and false otherwise */
  public boolean isSetTag() {
    return this.tag != null;
  }

  public void setTagIsSet(boolean value) {
    if (!value) {
      this.tag = null;
    }
  }

  /**
   * 创建角色的机构号
   */
  public String getOrgCode() {
    return this.orgCode;
  }

  /**
   * 创建角色的机构号
   */
  public BankRoleVo setOrgCode(String orgCode) {
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

    case ROLE_NAME:
      if (value == null) {
        unsetRoleName();
      } else {
        setRoleName((String)value);
      }
      break;

    case STATUS:
      if (value == null) {
        unsetStatus();
      } else {
        setStatus((Boolean)value);
      }
      break;

    case REMARK:
      if (value == null) {
        unsetRemark();
      } else {
        setRemark((String)value);
      }
      break;

    case IS_DELETE:
      if (value == null) {
        unsetIsDelete();
      } else {
        setIsDelete((Boolean)value);
      }
      break;

    case TAG:
      if (value == null) {
        unsetTag();
      } else {
        setTag((String)value);
      }
      break;

    case ORG_CODE:
      if (value == null) {
        unsetOrgCode();
      } else {
        setOrgCode((String)value);
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

    case ROLE_NAME:
      return getRoleName();

    case STATUS:
      return Boolean.valueOf(isStatus());

    case REMARK:
      return getRemark();

    case IS_DELETE:
      return Boolean.valueOf(isIsDelete());

    case TAG:
      return getTag();

    case ORG_CODE:
      return getOrgCode();

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
    case ROLE_NAME:
      return isSetRoleName();
    case STATUS:
      return isSetStatus();
    case REMARK:
      return isSetRemark();
    case IS_DELETE:
      return isSetIsDelete();
    case TAG:
      return isSetTag();
    case ORG_CODE:
      return isSetOrgCode();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof BankRoleVo)
      return this.equals((BankRoleVo)that);
    return false;
  }

  public boolean equals(BankRoleVo that) {
    if (that == null)
      return false;

    boolean this_present_id = true && this.isSetId();
    boolean that_present_id = true && that.isSetId();
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

    boolean this_present_roleName = true && this.isSetRoleName();
    boolean that_present_roleName = true && that.isSetRoleName();
    if (this_present_roleName || that_present_roleName) {
      if (!(this_present_roleName && that_present_roleName))
        return false;
      if (!this.roleName.equals(that.roleName))
        return false;
    }

    boolean this_present_status = true && this.isSetStatus();
    boolean that_present_status = true && that.isSetStatus();
    if (this_present_status || that_present_status) {
      if (!(this_present_status && that_present_status))
        return false;
      if (this.status != that.status)
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

    boolean this_present_isDelete = true && this.isSetIsDelete();
    boolean that_present_isDelete = true && that.isSetIsDelete();
    if (this_present_isDelete || that_present_isDelete) {
      if (!(this_present_isDelete && that_present_isDelete))
        return false;
      if (this.isDelete != that.isDelete)
        return false;
    }

    boolean this_present_tag = true && this.isSetTag();
    boolean that_present_tag = true && that.isSetTag();
    if (this_present_tag || that_present_tag) {
      if (!(this_present_tag && that_present_tag))
        return false;
      if (!this.tag.equals(that.tag))
        return false;
    }

    boolean this_present_orgCode = true && this.isSetOrgCode();
    boolean that_present_orgCode = true && that.isSetOrgCode();
    if (this_present_orgCode || that_present_orgCode) {
      if (!(this_present_orgCode && that_present_orgCode))
        return false;
      if (!this.orgCode.equals(that.orgCode))
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

    boolean present_roleName = true && (isSetRoleName());
    list.add(present_roleName);
    if (present_roleName)
      list.add(roleName);

    boolean present_status = true && (isSetStatus());
    list.add(present_status);
    if (present_status)
      list.add(status);

    boolean present_remark = true && (isSetRemark());
    list.add(present_remark);
    if (present_remark)
      list.add(remark);

    boolean present_isDelete = true && (isSetIsDelete());
    list.add(present_isDelete);
    if (present_isDelete)
      list.add(isDelete);

    boolean present_tag = true && (isSetTag());
    list.add(present_tag);
    if (present_tag)
      list.add(tag);

    boolean present_orgCode = true && (isSetOrgCode());
    list.add(present_orgCode);
    if (present_orgCode)
      list.add(orgCode);

    return list.hashCode();
  }

  @Override
  public int compareTo(BankRoleVo other) {
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
    lastComparison = Boolean.valueOf(isSetRoleName()).compareTo(other.isSetRoleName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRoleName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.roleName, other.roleName);
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
    lastComparison = Boolean.valueOf(isSetIsDelete()).compareTo(other.isSetIsDelete());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIsDelete()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.isDelete, other.isDelete);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTag()).compareTo(other.isSetTag());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTag()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.tag, other.tag);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
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
    StringBuilder sb = new StringBuilder("BankRoleVo(");
    boolean first = true;

    if (isSetId()) {
      sb.append("id:");
      sb.append(this.id);
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
    if (isSetRoleName()) {
      if (!first) sb.append(", ");
      sb.append("roleName:");
      if (this.roleName == null) {
        sb.append("null");
      } else {
        sb.append(this.roleName);
      }
      first = false;
    }
    if (isSetStatus()) {
      if (!first) sb.append(", ");
      sb.append("status:");
      sb.append(this.status);
      first = false;
    }
    if (isSetRemark()) {
      if (!first) sb.append(", ");
      sb.append("remark:");
      if (this.remark == null) {
        sb.append("null");
      } else {
        sb.append(this.remark);
      }
      first = false;
    }
    if (isSetIsDelete()) {
      if (!first) sb.append(", ");
      sb.append("isDelete:");
      sb.append(this.isDelete);
      first = false;
    }
    if (isSetTag()) {
      if (!first) sb.append(", ");
      sb.append("tag:");
      if (this.tag == null) {
        sb.append("null");
      } else {
        sb.append(this.tag);
      }
      first = false;
    }
    if (isSetOrgCode()) {
      if (!first) sb.append(", ");
      sb.append("orgCode:");
      if (this.orgCode == null) {
        sb.append("null");
      } else {
        sb.append(this.orgCode);
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class BankRoleVoStandardSchemeFactory implements SchemeFactory {
    public BankRoleVoStandardScheme getScheme() {
      return new BankRoleVoStandardScheme();
    }
  }

  private static class BankRoleVoStandardScheme extends StandardScheme<BankRoleVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, BankRoleVo struct) throws org.apache.thrift.TException {
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
          case 3: // ROLE_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.roleName = iprot.readString();
              struct.setRoleNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // STATUS
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.status = iprot.readBool();
              struct.setStatusIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // REMARK
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.remark = iprot.readString();
              struct.setRemarkIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // IS_DELETE
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.isDelete = iprot.readBool();
              struct.setIsDeleteIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // TAG
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.tag = iprot.readString();
              struct.setTagIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // ORG_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.orgCode = iprot.readString();
              struct.setOrgCodeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, BankRoleVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetId()) {
        oprot.writeFieldBegin(ID_FIELD_DESC);
        oprot.writeI64(struct.id);
        oprot.writeFieldEnd();
      }
      if (struct.clientId != null) {
        if (struct.isSetClientId()) {
          oprot.writeFieldBegin(CLIENT_ID_FIELD_DESC);
          oprot.writeString(struct.clientId);
          oprot.writeFieldEnd();
        }
      }
      if (struct.roleName != null) {
        if (struct.isSetRoleName()) {
          oprot.writeFieldBegin(ROLE_NAME_FIELD_DESC);
          oprot.writeString(struct.roleName);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetStatus()) {
        oprot.writeFieldBegin(STATUS_FIELD_DESC);
        oprot.writeBool(struct.status);
        oprot.writeFieldEnd();
      }
      if (struct.remark != null) {
        if (struct.isSetRemark()) {
          oprot.writeFieldBegin(REMARK_FIELD_DESC);
          oprot.writeString(struct.remark);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetIsDelete()) {
        oprot.writeFieldBegin(IS_DELETE_FIELD_DESC);
        oprot.writeBool(struct.isDelete);
        oprot.writeFieldEnd();
      }
      if (struct.tag != null) {
        if (struct.isSetTag()) {
          oprot.writeFieldBegin(TAG_FIELD_DESC);
          oprot.writeString(struct.tag);
          oprot.writeFieldEnd();
        }
      }
      if (struct.orgCode != null) {
        if (struct.isSetOrgCode()) {
          oprot.writeFieldBegin(ORG_CODE_FIELD_DESC);
          oprot.writeString(struct.orgCode);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class BankRoleVoTupleSchemeFactory implements SchemeFactory {
    public BankRoleVoTupleScheme getScheme() {
      return new BankRoleVoTupleScheme();
    }
  }

  private static class BankRoleVoTupleScheme extends TupleScheme<BankRoleVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, BankRoleVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetClientId()) {
        optionals.set(1);
      }
      if (struct.isSetRoleName()) {
        optionals.set(2);
      }
      if (struct.isSetStatus()) {
        optionals.set(3);
      }
      if (struct.isSetRemark()) {
        optionals.set(4);
      }
      if (struct.isSetIsDelete()) {
        optionals.set(5);
      }
      if (struct.isSetTag()) {
        optionals.set(6);
      }
      if (struct.isSetOrgCode()) {
        optionals.set(7);
      }
      oprot.writeBitSet(optionals, 8);
      if (struct.isSetId()) {
        oprot.writeI64(struct.id);
      }
      if (struct.isSetClientId()) {
        oprot.writeString(struct.clientId);
      }
      if (struct.isSetRoleName()) {
        oprot.writeString(struct.roleName);
      }
      if (struct.isSetStatus()) {
        oprot.writeBool(struct.status);
      }
      if (struct.isSetRemark()) {
        oprot.writeString(struct.remark);
      }
      if (struct.isSetIsDelete()) {
        oprot.writeBool(struct.isDelete);
      }
      if (struct.isSetTag()) {
        oprot.writeString(struct.tag);
      }
      if (struct.isSetOrgCode()) {
        oprot.writeString(struct.orgCode);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, BankRoleVo struct) throws org.apache.thrift.TException {
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
        struct.roleName = iprot.readString();
        struct.setRoleNameIsSet(true);
      }
      if (incoming.get(3)) {
        struct.status = iprot.readBool();
        struct.setStatusIsSet(true);
      }
      if (incoming.get(4)) {
        struct.remark = iprot.readString();
        struct.setRemarkIsSet(true);
      }
      if (incoming.get(5)) {
        struct.isDelete = iprot.readBool();
        struct.setIsDeleteIsSet(true);
      }
      if (incoming.get(6)) {
        struct.tag = iprot.readString();
        struct.setTagIsSet(true);
      }
      if (incoming.get(7)) {
        struct.orgCode = iprot.readString();
        struct.setOrgCodeIsSet(true);
      }
    }
  }

}
