/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.froad.thrift.vo.orgRoleManager;

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
 * 组织角色查询响应VoRes
 * OrgRoleIdListVoRes
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class OrgRoleIdListVoRes implements org.apache.thrift.TBase<OrgRoleIdListVoRes, OrgRoleIdListVoRes._Fields>, java.io.Serializable, Cloneable, Comparable<OrgRoleIdListVoRes> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("OrgRoleIdListVoRes");

  private static final org.apache.thrift.protocol.TField RESULT_VO_FIELD_DESC = new org.apache.thrift.protocol.TField("resultVo", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField ROLE_ID_VO_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("roleIdVoList", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new OrgRoleIdListVoResStandardSchemeFactory());
    schemes.put(TupleScheme.class, new OrgRoleIdListVoResTupleSchemeFactory());
  }

  /**
   * 返回结果
   */
  public com.froad.thrift.vo.ResultVo resultVo; // required
  /**
   * 组织角色id信息集合
   */
  public List<Long> roleIdVoList; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 返回结果
     */
    RESULT_VO((short)1, "resultVo"),
    /**
     * 组织角色id信息集合
     */
    ROLE_ID_VO_LIST((short)2, "roleIdVoList");

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
        case 1: // RESULT_VO
          return RESULT_VO;
        case 2: // ROLE_ID_VO_LIST
          return ROLE_ID_VO_LIST;
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
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.RESULT_VO, new org.apache.thrift.meta_data.FieldMetaData("resultVo", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.ResultVo.class)));
    tmpMap.put(_Fields.ROLE_ID_VO_LIST, new org.apache.thrift.meta_data.FieldMetaData("roleIdVoList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(OrgRoleIdListVoRes.class, metaDataMap);
  }

  public OrgRoleIdListVoRes() {
  }

  public OrgRoleIdListVoRes(
    com.froad.thrift.vo.ResultVo resultVo,
    List<Long> roleIdVoList)
  {
    this();
    this.resultVo = resultVo;
    this.roleIdVoList = roleIdVoList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public OrgRoleIdListVoRes(OrgRoleIdListVoRes other) {
    if (other.isSetResultVo()) {
      this.resultVo = new com.froad.thrift.vo.ResultVo(other.resultVo);
    }
    if (other.isSetRoleIdVoList()) {
      List<Long> __this__roleIdVoList = new ArrayList<Long>(other.roleIdVoList);
      this.roleIdVoList = __this__roleIdVoList;
    }
  }

  public OrgRoleIdListVoRes deepCopy() {
    return new OrgRoleIdListVoRes(this);
  }

  @Override
  public void clear() {
    this.resultVo = null;
    this.roleIdVoList = null;
  }

  /**
   * 返回结果
   */
  public com.froad.thrift.vo.ResultVo getResultVo() {
    return this.resultVo;
  }

  /**
   * 返回结果
   */
  public OrgRoleIdListVoRes setResultVo(com.froad.thrift.vo.ResultVo resultVo) {
    this.resultVo = resultVo;
    return this;
  }

  public void unsetResultVo() {
    this.resultVo = null;
  }

  /** Returns true if field resultVo is set (has been assigned a value) and false otherwise */
  public boolean isSetResultVo() {
    return this.resultVo != null;
  }

  public void setResultVoIsSet(boolean value) {
    if (!value) {
      this.resultVo = null;
    }
  }

  public int getRoleIdVoListSize() {
    return (this.roleIdVoList == null) ? 0 : this.roleIdVoList.size();
  }

  public java.util.Iterator<Long> getRoleIdVoListIterator() {
    return (this.roleIdVoList == null) ? null : this.roleIdVoList.iterator();
  }

  public void addToRoleIdVoList(long elem) {
    if (this.roleIdVoList == null) {
      this.roleIdVoList = new ArrayList<Long>();
    }
    this.roleIdVoList.add(elem);
  }

  /**
   * 组织角色id信息集合
   */
  public List<Long> getRoleIdVoList() {
    return this.roleIdVoList;
  }

  /**
   * 组织角色id信息集合
   */
  public OrgRoleIdListVoRes setRoleIdVoList(List<Long> roleIdVoList) {
    this.roleIdVoList = roleIdVoList;
    return this;
  }

  public void unsetRoleIdVoList() {
    this.roleIdVoList = null;
  }

  /** Returns true if field roleIdVoList is set (has been assigned a value) and false otherwise */
  public boolean isSetRoleIdVoList() {
    return this.roleIdVoList != null;
  }

  public void setRoleIdVoListIsSet(boolean value) {
    if (!value) {
      this.roleIdVoList = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case RESULT_VO:
      if (value == null) {
        unsetResultVo();
      } else {
        setResultVo((com.froad.thrift.vo.ResultVo)value);
      }
      break;

    case ROLE_ID_VO_LIST:
      if (value == null) {
        unsetRoleIdVoList();
      } else {
        setRoleIdVoList((List<Long>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RESULT_VO:
      return getResultVo();

    case ROLE_ID_VO_LIST:
      return getRoleIdVoList();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case RESULT_VO:
      return isSetResultVo();
    case ROLE_ID_VO_LIST:
      return isSetRoleIdVoList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof OrgRoleIdListVoRes)
      return this.equals((OrgRoleIdListVoRes)that);
    return false;
  }

  public boolean equals(OrgRoleIdListVoRes that) {
    if (that == null)
      return false;

    boolean this_present_resultVo = true && this.isSetResultVo();
    boolean that_present_resultVo = true && that.isSetResultVo();
    if (this_present_resultVo || that_present_resultVo) {
      if (!(this_present_resultVo && that_present_resultVo))
        return false;
      if (!this.resultVo.equals(that.resultVo))
        return false;
    }

    boolean this_present_roleIdVoList = true && this.isSetRoleIdVoList();
    boolean that_present_roleIdVoList = true && that.isSetRoleIdVoList();
    if (this_present_roleIdVoList || that_present_roleIdVoList) {
      if (!(this_present_roleIdVoList && that_present_roleIdVoList))
        return false;
      if (!this.roleIdVoList.equals(that.roleIdVoList))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_resultVo = true && (isSetResultVo());
    list.add(present_resultVo);
    if (present_resultVo)
      list.add(resultVo);

    boolean present_roleIdVoList = true && (isSetRoleIdVoList());
    list.add(present_roleIdVoList);
    if (present_roleIdVoList)
      list.add(roleIdVoList);

    return list.hashCode();
  }

  @Override
  public int compareTo(OrgRoleIdListVoRes other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetResultVo()).compareTo(other.isSetResultVo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetResultVo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.resultVo, other.resultVo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRoleIdVoList()).compareTo(other.isSetRoleIdVoList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRoleIdVoList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.roleIdVoList, other.roleIdVoList);
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
    StringBuilder sb = new StringBuilder("OrgRoleIdListVoRes(");
    boolean first = true;

    sb.append("resultVo:");
    if (this.resultVo == null) {
      sb.append("null");
    } else {
      sb.append(this.resultVo);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("roleIdVoList:");
    if (this.roleIdVoList == null) {
      sb.append("null");
    } else {
      sb.append(this.roleIdVoList);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (resultVo != null) {
      resultVo.validate();
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

  private static class OrgRoleIdListVoResStandardSchemeFactory implements SchemeFactory {
    public OrgRoleIdListVoResStandardScheme getScheme() {
      return new OrgRoleIdListVoResStandardScheme();
    }
  }

  private static class OrgRoleIdListVoResStandardScheme extends StandardScheme<OrgRoleIdListVoRes> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, OrgRoleIdListVoRes struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // RESULT_VO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.resultVo = new com.froad.thrift.vo.ResultVo();
              struct.resultVo.read(iprot);
              struct.setResultVoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // ROLE_ID_VO_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list40 = iprot.readListBegin();
                struct.roleIdVoList = new ArrayList<Long>(_list40.size);
                long _elem41;
                for (int _i42 = 0; _i42 < _list40.size; ++_i42)
                {
                  _elem41 = iprot.readI64();
                  struct.roleIdVoList.add(_elem41);
                }
                iprot.readListEnd();
              }
              struct.setRoleIdVoListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, OrgRoleIdListVoRes struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.resultVo != null) {
        oprot.writeFieldBegin(RESULT_VO_FIELD_DESC);
        struct.resultVo.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.roleIdVoList != null) {
        oprot.writeFieldBegin(ROLE_ID_VO_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I64, struct.roleIdVoList.size()));
          for (long _iter43 : struct.roleIdVoList)
          {
            oprot.writeI64(_iter43);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class OrgRoleIdListVoResTupleSchemeFactory implements SchemeFactory {
    public OrgRoleIdListVoResTupleScheme getScheme() {
      return new OrgRoleIdListVoResTupleScheme();
    }
  }

  private static class OrgRoleIdListVoResTupleScheme extends TupleScheme<OrgRoleIdListVoRes> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, OrgRoleIdListVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetResultVo()) {
        optionals.set(0);
      }
      if (struct.isSetRoleIdVoList()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetResultVo()) {
        struct.resultVo.write(oprot);
      }
      if (struct.isSetRoleIdVoList()) {
        {
          oprot.writeI32(struct.roleIdVoList.size());
          for (long _iter44 : struct.roleIdVoList)
          {
            oprot.writeI64(_iter44);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, OrgRoleIdListVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.resultVo = new com.froad.thrift.vo.ResultVo();
        struct.resultVo.read(iprot);
        struct.setResultVoIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list45 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I64, iprot.readI32());
          struct.roleIdVoList = new ArrayList<Long>(_list45.size);
          long _elem46;
          for (int _i47 = 0; _i47 < _list45.size; ++_i47)
          {
            _elem46 = iprot.readI64();
            struct.roleIdVoList.add(_elem46);
          }
        }
        struct.setRoleIdVoListIsSet(true);
      }
    }
  }

}

