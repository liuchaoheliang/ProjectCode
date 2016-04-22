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
 * getInstanceIdByAttr 根据业务(商户、门店、商品)ID查询审核流水号 接口返回对象
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class GetInstanceIdByAttrResVo implements org.apache.thrift.TBase<GetInstanceIdByAttrResVo, GetInstanceIdByAttrResVo._Fields>, java.io.Serializable, Cloneable, Comparable<GetInstanceIdByAttrResVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("GetInstanceIdByAttrResVo");

  private static final org.apache.thrift.protocol.TField RESULT_FIELD_DESC = new org.apache.thrift.protocol.TField("result", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField INSTANCE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("instanceId", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField PROCESS_TYPE_DETAIL_FIELD_DESC = new org.apache.thrift.protocol.TField("processTypeDetail", org.apache.thrift.protocol.TType.STRING, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new GetInstanceIdByAttrResVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new GetInstanceIdByAttrResVoTupleSchemeFactory());
  }

  public com.froad.thrift.vo.ResultVo result; // optional
  /**
   * 审核流水号
   */
  public String instanceId; // required
  /**
   * 审核类型
   */
  public String processTypeDetail; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    RESULT((short)1, "result"),
    /**
     * 审核流水号
     */
    INSTANCE_ID((short)2, "instanceId"),
    /**
     * 审核类型
     */
    PROCESS_TYPE_DETAIL((short)3, "processTypeDetail");

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
        case 1: // RESULT
          return RESULT;
        case 2: // INSTANCE_ID
          return INSTANCE_ID;
        case 3: // PROCESS_TYPE_DETAIL
          return PROCESS_TYPE_DETAIL;
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
  private static final _Fields optionals[] = {_Fields.RESULT};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.RESULT, new org.apache.thrift.meta_data.FieldMetaData("result", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.ResultVo.class)));
    tmpMap.put(_Fields.INSTANCE_ID, new org.apache.thrift.meta_data.FieldMetaData("instanceId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PROCESS_TYPE_DETAIL, new org.apache.thrift.meta_data.FieldMetaData("processTypeDetail", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(GetInstanceIdByAttrResVo.class, metaDataMap);
  }

  public GetInstanceIdByAttrResVo() {
  }

  public GetInstanceIdByAttrResVo(
    String instanceId,
    String processTypeDetail)
  {
    this();
    this.instanceId = instanceId;
    this.processTypeDetail = processTypeDetail;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public GetInstanceIdByAttrResVo(GetInstanceIdByAttrResVo other) {
    if (other.isSetResult()) {
      this.result = new com.froad.thrift.vo.ResultVo(other.result);
    }
    if (other.isSetInstanceId()) {
      this.instanceId = other.instanceId;
    }
    if (other.isSetProcessTypeDetail()) {
      this.processTypeDetail = other.processTypeDetail;
    }
  }

  public GetInstanceIdByAttrResVo deepCopy() {
    return new GetInstanceIdByAttrResVo(this);
  }

  @Override
  public void clear() {
    this.result = null;
    this.instanceId = null;
    this.processTypeDetail = null;
  }

  public com.froad.thrift.vo.ResultVo getResult() {
    return this.result;
  }

  public GetInstanceIdByAttrResVo setResult(com.froad.thrift.vo.ResultVo result) {
    this.result = result;
    return this;
  }

  public void unsetResult() {
    this.result = null;
  }

  /** Returns true if field result is set (has been assigned a value) and false otherwise */
  public boolean isSetResult() {
    return this.result != null;
  }

  public void setResultIsSet(boolean value) {
    if (!value) {
      this.result = null;
    }
  }

  /**
   * 审核流水号
   */
  public String getInstanceId() {
    return this.instanceId;
  }

  /**
   * 审核流水号
   */
  public GetInstanceIdByAttrResVo setInstanceId(String instanceId) {
    this.instanceId = instanceId;
    return this;
  }

  public void unsetInstanceId() {
    this.instanceId = null;
  }

  /** Returns true if field instanceId is set (has been assigned a value) and false otherwise */
  public boolean isSetInstanceId() {
    return this.instanceId != null;
  }

  public void setInstanceIdIsSet(boolean value) {
    if (!value) {
      this.instanceId = null;
    }
  }

  /**
   * 审核类型
   */
  public String getProcessTypeDetail() {
    return this.processTypeDetail;
  }

  /**
   * 审核类型
   */
  public GetInstanceIdByAttrResVo setProcessTypeDetail(String processTypeDetail) {
    this.processTypeDetail = processTypeDetail;
    return this;
  }

  public void unsetProcessTypeDetail() {
    this.processTypeDetail = null;
  }

  /** Returns true if field processTypeDetail is set (has been assigned a value) and false otherwise */
  public boolean isSetProcessTypeDetail() {
    return this.processTypeDetail != null;
  }

  public void setProcessTypeDetailIsSet(boolean value) {
    if (!value) {
      this.processTypeDetail = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case RESULT:
      if (value == null) {
        unsetResult();
      } else {
        setResult((com.froad.thrift.vo.ResultVo)value);
      }
      break;

    case INSTANCE_ID:
      if (value == null) {
        unsetInstanceId();
      } else {
        setInstanceId((String)value);
      }
      break;

    case PROCESS_TYPE_DETAIL:
      if (value == null) {
        unsetProcessTypeDetail();
      } else {
        setProcessTypeDetail((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RESULT:
      return getResult();

    case INSTANCE_ID:
      return getInstanceId();

    case PROCESS_TYPE_DETAIL:
      return getProcessTypeDetail();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case RESULT:
      return isSetResult();
    case INSTANCE_ID:
      return isSetInstanceId();
    case PROCESS_TYPE_DETAIL:
      return isSetProcessTypeDetail();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof GetInstanceIdByAttrResVo)
      return this.equals((GetInstanceIdByAttrResVo)that);
    return false;
  }

  public boolean equals(GetInstanceIdByAttrResVo that) {
    if (that == null)
      return false;

    boolean this_present_result = true && this.isSetResult();
    boolean that_present_result = true && that.isSetResult();
    if (this_present_result || that_present_result) {
      if (!(this_present_result && that_present_result))
        return false;
      if (!this.result.equals(that.result))
        return false;
    }

    boolean this_present_instanceId = true && this.isSetInstanceId();
    boolean that_present_instanceId = true && that.isSetInstanceId();
    if (this_present_instanceId || that_present_instanceId) {
      if (!(this_present_instanceId && that_present_instanceId))
        return false;
      if (!this.instanceId.equals(that.instanceId))
        return false;
    }

    boolean this_present_processTypeDetail = true && this.isSetProcessTypeDetail();
    boolean that_present_processTypeDetail = true && that.isSetProcessTypeDetail();
    if (this_present_processTypeDetail || that_present_processTypeDetail) {
      if (!(this_present_processTypeDetail && that_present_processTypeDetail))
        return false;
      if (!this.processTypeDetail.equals(that.processTypeDetail))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_result = true && (isSetResult());
    list.add(present_result);
    if (present_result)
      list.add(result);

    boolean present_instanceId = true && (isSetInstanceId());
    list.add(present_instanceId);
    if (present_instanceId)
      list.add(instanceId);

    boolean present_processTypeDetail = true && (isSetProcessTypeDetail());
    list.add(present_processTypeDetail);
    if (present_processTypeDetail)
      list.add(processTypeDetail);

    return list.hashCode();
  }

  @Override
  public int compareTo(GetInstanceIdByAttrResVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetResult()).compareTo(other.isSetResult());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetResult()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.result, other.result);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetInstanceId()).compareTo(other.isSetInstanceId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetInstanceId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.instanceId, other.instanceId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetProcessTypeDetail()).compareTo(other.isSetProcessTypeDetail());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProcessTypeDetail()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.processTypeDetail, other.processTypeDetail);
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
    StringBuilder sb = new StringBuilder("GetInstanceIdByAttrResVo(");
    boolean first = true;

    if (isSetResult()) {
      sb.append("result:");
      if (this.result == null) {
        sb.append("null");
      } else {
        sb.append(this.result);
      }
      first = false;
    }
    if (!first) sb.append(", ");
    sb.append("instanceId:");
    if (this.instanceId == null) {
      sb.append("null");
    } else {
      sb.append(this.instanceId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("processTypeDetail:");
    if (this.processTypeDetail == null) {
      sb.append("null");
    } else {
      sb.append(this.processTypeDetail);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (result != null) {
      result.validate();
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

  private static class GetInstanceIdByAttrResVoStandardSchemeFactory implements SchemeFactory {
    public GetInstanceIdByAttrResVoStandardScheme getScheme() {
      return new GetInstanceIdByAttrResVoStandardScheme();
    }
  }

  private static class GetInstanceIdByAttrResVoStandardScheme extends StandardScheme<GetInstanceIdByAttrResVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, GetInstanceIdByAttrResVo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // RESULT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.result = new com.froad.thrift.vo.ResultVo();
              struct.result.read(iprot);
              struct.setResultIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // INSTANCE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.instanceId = iprot.readString();
              struct.setInstanceIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // PROCESS_TYPE_DETAIL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.processTypeDetail = iprot.readString();
              struct.setProcessTypeDetailIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, GetInstanceIdByAttrResVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.result != null) {
        if (struct.isSetResult()) {
          oprot.writeFieldBegin(RESULT_FIELD_DESC);
          struct.result.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.instanceId != null) {
        oprot.writeFieldBegin(INSTANCE_ID_FIELD_DESC);
        oprot.writeString(struct.instanceId);
        oprot.writeFieldEnd();
      }
      if (struct.processTypeDetail != null) {
        oprot.writeFieldBegin(PROCESS_TYPE_DETAIL_FIELD_DESC);
        oprot.writeString(struct.processTypeDetail);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class GetInstanceIdByAttrResVoTupleSchemeFactory implements SchemeFactory {
    public GetInstanceIdByAttrResVoTupleScheme getScheme() {
      return new GetInstanceIdByAttrResVoTupleScheme();
    }
  }

  private static class GetInstanceIdByAttrResVoTupleScheme extends TupleScheme<GetInstanceIdByAttrResVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, GetInstanceIdByAttrResVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetResult()) {
        optionals.set(0);
      }
      if (struct.isSetInstanceId()) {
        optionals.set(1);
      }
      if (struct.isSetProcessTypeDetail()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetResult()) {
        struct.result.write(oprot);
      }
      if (struct.isSetInstanceId()) {
        oprot.writeString(struct.instanceId);
      }
      if (struct.isSetProcessTypeDetail()) {
        oprot.writeString(struct.processTypeDetail);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, GetInstanceIdByAttrResVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.result = new com.froad.thrift.vo.ResultVo();
        struct.result.read(iprot);
        struct.setResultIsSet(true);
      }
      if (incoming.get(1)) {
        struct.instanceId = iprot.readString();
        struct.setInstanceIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.processTypeDetail = iprot.readString();
        struct.setProcessTypeDetailIsSet(true);
      }
    }
  }

}
