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
 * 待审核任务记录详情vo
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class AuditProcessVo implements org.apache.thrift.TBase<AuditProcessVo, AuditProcessVo._Fields>, java.io.Serializable, Cloneable, Comparable<AuditProcessVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("AuditProcessVo");

  private static final org.apache.thrift.protocol.TField TASK_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("taskId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField CREATE_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("createTime", org.apache.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.thrift.protocol.TField AUDIT_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("auditTime", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField ORG_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("orgName", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField AUDIT_STAFF_FIELD_DESC = new org.apache.thrift.protocol.TField("auditStaff", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField AUDIT_STATE_FIELD_DESC = new org.apache.thrift.protocol.TField("auditState", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField AUDIT_COMMENT_FIELD_DESC = new org.apache.thrift.protocol.TField("auditComment", org.apache.thrift.protocol.TType.STRING, (short)7);
  private static final org.apache.thrift.protocol.TField CLIENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("clientId", org.apache.thrift.protocol.TType.STRING, (short)8);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new AuditProcessVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new AuditProcessVoTupleSchemeFactory());
  }

  /**
   * 任务流水号
   */
  public String taskId; // optional
  /**
   * 创建时间
   */
  public long createTime; // optional
  /**
   * 审核时间
   */
  public long auditTime; // optional
  /**
   * 审核机构
   */
  public String orgName; // optional
  /**
   * 审核人
   */
  public String auditStaff; // optional
  /**
   * 状态    0-待审核 1-审核通过 2-审核不通过
   */
  public String auditState; // optional
  /**
   * 备注
   */
  public String auditComment; // optional
  /**
   * 客户端id
   */
  public String clientId; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 任务流水号
     */
    TASK_ID((short)1, "taskId"),
    /**
     * 创建时间
     */
    CREATE_TIME((short)2, "createTime"),
    /**
     * 审核时间
     */
    AUDIT_TIME((short)3, "auditTime"),
    /**
     * 审核机构
     */
    ORG_NAME((short)4, "orgName"),
    /**
     * 审核人
     */
    AUDIT_STAFF((short)5, "auditStaff"),
    /**
     * 状态    0-待审核 1-审核通过 2-审核不通过
     */
    AUDIT_STATE((short)6, "auditState"),
    /**
     * 备注
     */
    AUDIT_COMMENT((short)7, "auditComment"),
    /**
     * 客户端id
     */
    CLIENT_ID((short)8, "clientId");

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
        case 1: // TASK_ID
          return TASK_ID;
        case 2: // CREATE_TIME
          return CREATE_TIME;
        case 3: // AUDIT_TIME
          return AUDIT_TIME;
        case 4: // ORG_NAME
          return ORG_NAME;
        case 5: // AUDIT_STAFF
          return AUDIT_STAFF;
        case 6: // AUDIT_STATE
          return AUDIT_STATE;
        case 7: // AUDIT_COMMENT
          return AUDIT_COMMENT;
        case 8: // CLIENT_ID
          return CLIENT_ID;
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
  private static final int __CREATETIME_ISSET_ID = 0;
  private static final int __AUDITTIME_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.TASK_ID,_Fields.CREATE_TIME,_Fields.AUDIT_TIME,_Fields.ORG_NAME,_Fields.AUDIT_STAFF,_Fields.AUDIT_STATE,_Fields.AUDIT_COMMENT,_Fields.CLIENT_ID};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TASK_ID, new org.apache.thrift.meta_data.FieldMetaData("taskId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CREATE_TIME, new org.apache.thrift.meta_data.FieldMetaData("createTime", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.AUDIT_TIME, new org.apache.thrift.meta_data.FieldMetaData("auditTime", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.ORG_NAME, new org.apache.thrift.meta_data.FieldMetaData("orgName", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.AUDIT_STAFF, new org.apache.thrift.meta_data.FieldMetaData("auditStaff", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.AUDIT_STATE, new org.apache.thrift.meta_data.FieldMetaData("auditState", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.AUDIT_COMMENT, new org.apache.thrift.meta_data.FieldMetaData("auditComment", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CLIENT_ID, new org.apache.thrift.meta_data.FieldMetaData("clientId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(AuditProcessVo.class, metaDataMap);
  }

  public AuditProcessVo() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public AuditProcessVo(AuditProcessVo other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetTaskId()) {
      this.taskId = other.taskId;
    }
    this.createTime = other.createTime;
    this.auditTime = other.auditTime;
    if (other.isSetOrgName()) {
      this.orgName = other.orgName;
    }
    if (other.isSetAuditStaff()) {
      this.auditStaff = other.auditStaff;
    }
    if (other.isSetAuditState()) {
      this.auditState = other.auditState;
    }
    if (other.isSetAuditComment()) {
      this.auditComment = other.auditComment;
    }
    if (other.isSetClientId()) {
      this.clientId = other.clientId;
    }
  }

  public AuditProcessVo deepCopy() {
    return new AuditProcessVo(this);
  }

  @Override
  public void clear() {
    this.taskId = null;
    setCreateTimeIsSet(false);
    this.createTime = 0;
    setAuditTimeIsSet(false);
    this.auditTime = 0;
    this.orgName = null;
    this.auditStaff = null;
    this.auditState = null;
    this.auditComment = null;
    this.clientId = null;
  }

  /**
   * 任务流水号
   */
  public String getTaskId() {
    return this.taskId;
  }

  /**
   * 任务流水号
   */
  public AuditProcessVo setTaskId(String taskId) {
    this.taskId = taskId;
    return this;
  }

  public void unsetTaskId() {
    this.taskId = null;
  }

  /** Returns true if field taskId is set (has been assigned a value) and false otherwise */
  public boolean isSetTaskId() {
    return this.taskId != null;
  }

  public void setTaskIdIsSet(boolean value) {
    if (!value) {
      this.taskId = null;
    }
  }

  /**
   * 创建时间
   */
  public long getCreateTime() {
    return this.createTime;
  }

  /**
   * 创建时间
   */
  public AuditProcessVo setCreateTime(long createTime) {
    this.createTime = createTime;
    setCreateTimeIsSet(true);
    return this;
  }

  public void unsetCreateTime() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __CREATETIME_ISSET_ID);
  }

  /** Returns true if field createTime is set (has been assigned a value) and false otherwise */
  public boolean isSetCreateTime() {
    return EncodingUtils.testBit(__isset_bitfield, __CREATETIME_ISSET_ID);
  }

  public void setCreateTimeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __CREATETIME_ISSET_ID, value);
  }

  /**
   * 审核时间
   */
  public long getAuditTime() {
    return this.auditTime;
  }

  /**
   * 审核时间
   */
  public AuditProcessVo setAuditTime(long auditTime) {
    this.auditTime = auditTime;
    setAuditTimeIsSet(true);
    return this;
  }

  public void unsetAuditTime() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __AUDITTIME_ISSET_ID);
  }

  /** Returns true if field auditTime is set (has been assigned a value) and false otherwise */
  public boolean isSetAuditTime() {
    return EncodingUtils.testBit(__isset_bitfield, __AUDITTIME_ISSET_ID);
  }

  public void setAuditTimeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __AUDITTIME_ISSET_ID, value);
  }

  /**
   * 审核机构
   */
  public String getOrgName() {
    return this.orgName;
  }

  /**
   * 审核机构
   */
  public AuditProcessVo setOrgName(String orgName) {
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
   * 审核人
   */
  public String getAuditStaff() {
    return this.auditStaff;
  }

  /**
   * 审核人
   */
  public AuditProcessVo setAuditStaff(String auditStaff) {
    this.auditStaff = auditStaff;
    return this;
  }

  public void unsetAuditStaff() {
    this.auditStaff = null;
  }

  /** Returns true if field auditStaff is set (has been assigned a value) and false otherwise */
  public boolean isSetAuditStaff() {
    return this.auditStaff != null;
  }

  public void setAuditStaffIsSet(boolean value) {
    if (!value) {
      this.auditStaff = null;
    }
  }

  /**
   * 状态    0-待审核 1-审核通过 2-审核不通过
   */
  public String getAuditState() {
    return this.auditState;
  }

  /**
   * 状态    0-待审核 1-审核通过 2-审核不通过
   */
  public AuditProcessVo setAuditState(String auditState) {
    this.auditState = auditState;
    return this;
  }

  public void unsetAuditState() {
    this.auditState = null;
  }

  /** Returns true if field auditState is set (has been assigned a value) and false otherwise */
  public boolean isSetAuditState() {
    return this.auditState != null;
  }

  public void setAuditStateIsSet(boolean value) {
    if (!value) {
      this.auditState = null;
    }
  }

  /**
   * 备注
   */
  public String getAuditComment() {
    return this.auditComment;
  }

  /**
   * 备注
   */
  public AuditProcessVo setAuditComment(String auditComment) {
    this.auditComment = auditComment;
    return this;
  }

  public void unsetAuditComment() {
    this.auditComment = null;
  }

  /** Returns true if field auditComment is set (has been assigned a value) and false otherwise */
  public boolean isSetAuditComment() {
    return this.auditComment != null;
  }

  public void setAuditCommentIsSet(boolean value) {
    if (!value) {
      this.auditComment = null;
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
  public AuditProcessVo setClientId(String clientId) {
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

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TASK_ID:
      if (value == null) {
        unsetTaskId();
      } else {
        setTaskId((String)value);
      }
      break;

    case CREATE_TIME:
      if (value == null) {
        unsetCreateTime();
      } else {
        setCreateTime((Long)value);
      }
      break;

    case AUDIT_TIME:
      if (value == null) {
        unsetAuditTime();
      } else {
        setAuditTime((Long)value);
      }
      break;

    case ORG_NAME:
      if (value == null) {
        unsetOrgName();
      } else {
        setOrgName((String)value);
      }
      break;

    case AUDIT_STAFF:
      if (value == null) {
        unsetAuditStaff();
      } else {
        setAuditStaff((String)value);
      }
      break;

    case AUDIT_STATE:
      if (value == null) {
        unsetAuditState();
      } else {
        setAuditState((String)value);
      }
      break;

    case AUDIT_COMMENT:
      if (value == null) {
        unsetAuditComment();
      } else {
        setAuditComment((String)value);
      }
      break;

    case CLIENT_ID:
      if (value == null) {
        unsetClientId();
      } else {
        setClientId((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TASK_ID:
      return getTaskId();

    case CREATE_TIME:
      return Long.valueOf(getCreateTime());

    case AUDIT_TIME:
      return Long.valueOf(getAuditTime());

    case ORG_NAME:
      return getOrgName();

    case AUDIT_STAFF:
      return getAuditStaff();

    case AUDIT_STATE:
      return getAuditState();

    case AUDIT_COMMENT:
      return getAuditComment();

    case CLIENT_ID:
      return getClientId();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case TASK_ID:
      return isSetTaskId();
    case CREATE_TIME:
      return isSetCreateTime();
    case AUDIT_TIME:
      return isSetAuditTime();
    case ORG_NAME:
      return isSetOrgName();
    case AUDIT_STAFF:
      return isSetAuditStaff();
    case AUDIT_STATE:
      return isSetAuditState();
    case AUDIT_COMMENT:
      return isSetAuditComment();
    case CLIENT_ID:
      return isSetClientId();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof AuditProcessVo)
      return this.equals((AuditProcessVo)that);
    return false;
  }

  public boolean equals(AuditProcessVo that) {
    if (that == null)
      return false;

    boolean this_present_taskId = true && this.isSetTaskId();
    boolean that_present_taskId = true && that.isSetTaskId();
    if (this_present_taskId || that_present_taskId) {
      if (!(this_present_taskId && that_present_taskId))
        return false;
      if (!this.taskId.equals(that.taskId))
        return false;
    }

    boolean this_present_createTime = true && this.isSetCreateTime();
    boolean that_present_createTime = true && that.isSetCreateTime();
    if (this_present_createTime || that_present_createTime) {
      if (!(this_present_createTime && that_present_createTime))
        return false;
      if (this.createTime != that.createTime)
        return false;
    }

    boolean this_present_auditTime = true && this.isSetAuditTime();
    boolean that_present_auditTime = true && that.isSetAuditTime();
    if (this_present_auditTime || that_present_auditTime) {
      if (!(this_present_auditTime && that_present_auditTime))
        return false;
      if (this.auditTime != that.auditTime)
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

    boolean this_present_auditStaff = true && this.isSetAuditStaff();
    boolean that_present_auditStaff = true && that.isSetAuditStaff();
    if (this_present_auditStaff || that_present_auditStaff) {
      if (!(this_present_auditStaff && that_present_auditStaff))
        return false;
      if (!this.auditStaff.equals(that.auditStaff))
        return false;
    }

    boolean this_present_auditState = true && this.isSetAuditState();
    boolean that_present_auditState = true && that.isSetAuditState();
    if (this_present_auditState || that_present_auditState) {
      if (!(this_present_auditState && that_present_auditState))
        return false;
      if (!this.auditState.equals(that.auditState))
        return false;
    }

    boolean this_present_auditComment = true && this.isSetAuditComment();
    boolean that_present_auditComment = true && that.isSetAuditComment();
    if (this_present_auditComment || that_present_auditComment) {
      if (!(this_present_auditComment && that_present_auditComment))
        return false;
      if (!this.auditComment.equals(that.auditComment))
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

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_taskId = true && (isSetTaskId());
    list.add(present_taskId);
    if (present_taskId)
      list.add(taskId);

    boolean present_createTime = true && (isSetCreateTime());
    list.add(present_createTime);
    if (present_createTime)
      list.add(createTime);

    boolean present_auditTime = true && (isSetAuditTime());
    list.add(present_auditTime);
    if (present_auditTime)
      list.add(auditTime);

    boolean present_orgName = true && (isSetOrgName());
    list.add(present_orgName);
    if (present_orgName)
      list.add(orgName);

    boolean present_auditStaff = true && (isSetAuditStaff());
    list.add(present_auditStaff);
    if (present_auditStaff)
      list.add(auditStaff);

    boolean present_auditState = true && (isSetAuditState());
    list.add(present_auditState);
    if (present_auditState)
      list.add(auditState);

    boolean present_auditComment = true && (isSetAuditComment());
    list.add(present_auditComment);
    if (present_auditComment)
      list.add(auditComment);

    boolean present_clientId = true && (isSetClientId());
    list.add(present_clientId);
    if (present_clientId)
      list.add(clientId);

    return list.hashCode();
  }

  @Override
  public int compareTo(AuditProcessVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetTaskId()).compareTo(other.isSetTaskId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTaskId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.taskId, other.taskId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCreateTime()).compareTo(other.isSetCreateTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCreateTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.createTime, other.createTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAuditTime()).compareTo(other.isSetAuditTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAuditTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.auditTime, other.auditTime);
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
    lastComparison = Boolean.valueOf(isSetAuditStaff()).compareTo(other.isSetAuditStaff());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAuditStaff()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.auditStaff, other.auditStaff);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAuditState()).compareTo(other.isSetAuditState());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAuditState()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.auditState, other.auditState);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAuditComment()).compareTo(other.isSetAuditComment());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAuditComment()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.auditComment, other.auditComment);
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
    StringBuilder sb = new StringBuilder("AuditProcessVo(");
    boolean first = true;

    if (isSetTaskId()) {
      sb.append("taskId:");
      if (this.taskId == null) {
        sb.append("null");
      } else {
        sb.append(this.taskId);
      }
      first = false;
    }
    if (isSetCreateTime()) {
      if (!first) sb.append(", ");
      sb.append("createTime:");
      sb.append(this.createTime);
      first = false;
    }
    if (isSetAuditTime()) {
      if (!first) sb.append(", ");
      sb.append("auditTime:");
      sb.append(this.auditTime);
      first = false;
    }
    if (isSetOrgName()) {
      if (!first) sb.append(", ");
      sb.append("orgName:");
      if (this.orgName == null) {
        sb.append("null");
      } else {
        sb.append(this.orgName);
      }
      first = false;
    }
    if (isSetAuditStaff()) {
      if (!first) sb.append(", ");
      sb.append("auditStaff:");
      if (this.auditStaff == null) {
        sb.append("null");
      } else {
        sb.append(this.auditStaff);
      }
      first = false;
    }
    if (isSetAuditState()) {
      if (!first) sb.append(", ");
      sb.append("auditState:");
      if (this.auditState == null) {
        sb.append("null");
      } else {
        sb.append(this.auditState);
      }
      first = false;
    }
    if (isSetAuditComment()) {
      if (!first) sb.append(", ");
      sb.append("auditComment:");
      if (this.auditComment == null) {
        sb.append("null");
      } else {
        sb.append(this.auditComment);
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

  private static class AuditProcessVoStandardSchemeFactory implements SchemeFactory {
    public AuditProcessVoStandardScheme getScheme() {
      return new AuditProcessVoStandardScheme();
    }
  }

  private static class AuditProcessVoStandardScheme extends StandardScheme<AuditProcessVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, AuditProcessVo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TASK_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.taskId = iprot.readString();
              struct.setTaskIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // CREATE_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.createTime = iprot.readI64();
              struct.setCreateTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // AUDIT_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.auditTime = iprot.readI64();
              struct.setAuditTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // ORG_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.orgName = iprot.readString();
              struct.setOrgNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // AUDIT_STAFF
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.auditStaff = iprot.readString();
              struct.setAuditStaffIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // AUDIT_STATE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.auditState = iprot.readString();
              struct.setAuditStateIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // AUDIT_COMMENT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.auditComment = iprot.readString();
              struct.setAuditCommentIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // CLIENT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.clientId = iprot.readString();
              struct.setClientIdIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, AuditProcessVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.taskId != null) {
        if (struct.isSetTaskId()) {
          oprot.writeFieldBegin(TASK_ID_FIELD_DESC);
          oprot.writeString(struct.taskId);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetCreateTime()) {
        oprot.writeFieldBegin(CREATE_TIME_FIELD_DESC);
        oprot.writeI64(struct.createTime);
        oprot.writeFieldEnd();
      }
      if (struct.isSetAuditTime()) {
        oprot.writeFieldBegin(AUDIT_TIME_FIELD_DESC);
        oprot.writeI64(struct.auditTime);
        oprot.writeFieldEnd();
      }
      if (struct.orgName != null) {
        if (struct.isSetOrgName()) {
          oprot.writeFieldBegin(ORG_NAME_FIELD_DESC);
          oprot.writeString(struct.orgName);
          oprot.writeFieldEnd();
        }
      }
      if (struct.auditStaff != null) {
        if (struct.isSetAuditStaff()) {
          oprot.writeFieldBegin(AUDIT_STAFF_FIELD_DESC);
          oprot.writeString(struct.auditStaff);
          oprot.writeFieldEnd();
        }
      }
      if (struct.auditState != null) {
        if (struct.isSetAuditState()) {
          oprot.writeFieldBegin(AUDIT_STATE_FIELD_DESC);
          oprot.writeString(struct.auditState);
          oprot.writeFieldEnd();
        }
      }
      if (struct.auditComment != null) {
        if (struct.isSetAuditComment()) {
          oprot.writeFieldBegin(AUDIT_COMMENT_FIELD_DESC);
          oprot.writeString(struct.auditComment);
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
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class AuditProcessVoTupleSchemeFactory implements SchemeFactory {
    public AuditProcessVoTupleScheme getScheme() {
      return new AuditProcessVoTupleScheme();
    }
  }

  private static class AuditProcessVoTupleScheme extends TupleScheme<AuditProcessVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, AuditProcessVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetTaskId()) {
        optionals.set(0);
      }
      if (struct.isSetCreateTime()) {
        optionals.set(1);
      }
      if (struct.isSetAuditTime()) {
        optionals.set(2);
      }
      if (struct.isSetOrgName()) {
        optionals.set(3);
      }
      if (struct.isSetAuditStaff()) {
        optionals.set(4);
      }
      if (struct.isSetAuditState()) {
        optionals.set(5);
      }
      if (struct.isSetAuditComment()) {
        optionals.set(6);
      }
      if (struct.isSetClientId()) {
        optionals.set(7);
      }
      oprot.writeBitSet(optionals, 8);
      if (struct.isSetTaskId()) {
        oprot.writeString(struct.taskId);
      }
      if (struct.isSetCreateTime()) {
        oprot.writeI64(struct.createTime);
      }
      if (struct.isSetAuditTime()) {
        oprot.writeI64(struct.auditTime);
      }
      if (struct.isSetOrgName()) {
        oprot.writeString(struct.orgName);
      }
      if (struct.isSetAuditStaff()) {
        oprot.writeString(struct.auditStaff);
      }
      if (struct.isSetAuditState()) {
        oprot.writeString(struct.auditState);
      }
      if (struct.isSetAuditComment()) {
        oprot.writeString(struct.auditComment);
      }
      if (struct.isSetClientId()) {
        oprot.writeString(struct.clientId);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, AuditProcessVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(8);
      if (incoming.get(0)) {
        struct.taskId = iprot.readString();
        struct.setTaskIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.createTime = iprot.readI64();
        struct.setCreateTimeIsSet(true);
      }
      if (incoming.get(2)) {
        struct.auditTime = iprot.readI64();
        struct.setAuditTimeIsSet(true);
      }
      if (incoming.get(3)) {
        struct.orgName = iprot.readString();
        struct.setOrgNameIsSet(true);
      }
      if (incoming.get(4)) {
        struct.auditStaff = iprot.readString();
        struct.setAuditStaffIsSet(true);
      }
      if (incoming.get(5)) {
        struct.auditState = iprot.readString();
        struct.setAuditStateIsSet(true);
      }
      if (incoming.get(6)) {
        struct.auditComment = iprot.readString();
        struct.setAuditCommentIsSet(true);
      }
      if (incoming.get(7)) {
        struct.clientId = iprot.readString();
        struct.setClientIdIsSet(true);
      }
    }
  }

}
