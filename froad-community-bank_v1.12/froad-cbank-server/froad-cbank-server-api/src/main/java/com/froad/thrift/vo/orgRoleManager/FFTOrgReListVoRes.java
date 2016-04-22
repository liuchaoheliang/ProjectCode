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
 * 权限组织查询响应VoRes
 * FFTOrgReListVoRes
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class FFTOrgReListVoRes implements org.apache.thrift.TBase<FFTOrgReListVoRes, FFTOrgReListVoRes._Fields>, java.io.Serializable, Cloneable, Comparable<FFTOrgReListVoRes> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("FFTOrgReListVoRes");

  private static final org.apache.thrift.protocol.TField RESULT_VO_FIELD_DESC = new org.apache.thrift.protocol.TField("resultVo", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField VO_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("voList", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new FFTOrgReListVoResStandardSchemeFactory());
    schemes.put(TupleScheme.class, new FFTOrgReListVoResTupleSchemeFactory());
  }

  /**
   * 返回结果
   */
  public com.froad.thrift.vo.ResultVo resultVo; // required
  /**
   * 权限组织信息集合
   */
  public List<FFTOrgReVo> voList; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 返回结果
     */
    RESULT_VO((short)1, "resultVo"),
    /**
     * 权限组织信息集合
     */
    VO_LIST((short)2, "voList");

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
        case 2: // VO_LIST
          return VO_LIST;
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
    tmpMap.put(_Fields.VO_LIST, new org.apache.thrift.meta_data.FieldMetaData("voList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, FFTOrgReVo.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(FFTOrgReListVoRes.class, metaDataMap);
  }

  public FFTOrgReListVoRes() {
  }

  public FFTOrgReListVoRes(
    com.froad.thrift.vo.ResultVo resultVo,
    List<FFTOrgReVo> voList)
  {
    this();
    this.resultVo = resultVo;
    this.voList = voList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public FFTOrgReListVoRes(FFTOrgReListVoRes other) {
    if (other.isSetResultVo()) {
      this.resultVo = new com.froad.thrift.vo.ResultVo(other.resultVo);
    }
    if (other.isSetVoList()) {
      List<FFTOrgReVo> __this__voList = new ArrayList<FFTOrgReVo>(other.voList.size());
      for (FFTOrgReVo other_element : other.voList) {
        __this__voList.add(new FFTOrgReVo(other_element));
      }
      this.voList = __this__voList;
    }
  }

  public FFTOrgReListVoRes deepCopy() {
    return new FFTOrgReListVoRes(this);
  }

  @Override
  public void clear() {
    this.resultVo = null;
    this.voList = null;
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
  public FFTOrgReListVoRes setResultVo(com.froad.thrift.vo.ResultVo resultVo) {
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

  public int getVoListSize() {
    return (this.voList == null) ? 0 : this.voList.size();
  }

  public java.util.Iterator<FFTOrgReVo> getVoListIterator() {
    return (this.voList == null) ? null : this.voList.iterator();
  }

  public void addToVoList(FFTOrgReVo elem) {
    if (this.voList == null) {
      this.voList = new ArrayList<FFTOrgReVo>();
    }
    this.voList.add(elem);
  }

  /**
   * 权限组织信息集合
   */
  public List<FFTOrgReVo> getVoList() {
    return this.voList;
  }

  /**
   * 权限组织信息集合
   */
  public FFTOrgReListVoRes setVoList(List<FFTOrgReVo> voList) {
    this.voList = voList;
    return this;
  }

  public void unsetVoList() {
    this.voList = null;
  }

  /** Returns true if field voList is set (has been assigned a value) and false otherwise */
  public boolean isSetVoList() {
    return this.voList != null;
  }

  public void setVoListIsSet(boolean value) {
    if (!value) {
      this.voList = null;
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

    case VO_LIST:
      if (value == null) {
        unsetVoList();
      } else {
        setVoList((List<FFTOrgReVo>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RESULT_VO:
      return getResultVo();

    case VO_LIST:
      return getVoList();

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
    case VO_LIST:
      return isSetVoList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof FFTOrgReListVoRes)
      return this.equals((FFTOrgReListVoRes)that);
    return false;
  }

  public boolean equals(FFTOrgReListVoRes that) {
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

    boolean this_present_voList = true && this.isSetVoList();
    boolean that_present_voList = true && that.isSetVoList();
    if (this_present_voList || that_present_voList) {
      if (!(this_present_voList && that_present_voList))
        return false;
      if (!this.voList.equals(that.voList))
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

    boolean present_voList = true && (isSetVoList());
    list.add(present_voList);
    if (present_voList)
      list.add(voList);

    return list.hashCode();
  }

  @Override
  public int compareTo(FFTOrgReListVoRes other) {
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
    lastComparison = Boolean.valueOf(isSetVoList()).compareTo(other.isSetVoList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVoList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.voList, other.voList);
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
    StringBuilder sb = new StringBuilder("FFTOrgReListVoRes(");
    boolean first = true;

    sb.append("resultVo:");
    if (this.resultVo == null) {
      sb.append("null");
    } else {
      sb.append(this.resultVo);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("voList:");
    if (this.voList == null) {
      sb.append("null");
    } else {
      sb.append(this.voList);
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

  private static class FFTOrgReListVoResStandardSchemeFactory implements SchemeFactory {
    public FFTOrgReListVoResStandardScheme getScheme() {
      return new FFTOrgReListVoResStandardScheme();
    }
  }

  private static class FFTOrgReListVoResStandardScheme extends StandardScheme<FFTOrgReListVoRes> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, FFTOrgReListVoRes struct) throws org.apache.thrift.TException {
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
          case 2: // VO_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list24 = iprot.readListBegin();
                struct.voList = new ArrayList<FFTOrgReVo>(_list24.size);
                FFTOrgReVo _elem25;
                for (int _i26 = 0; _i26 < _list24.size; ++_i26)
                {
                  _elem25 = new FFTOrgReVo();
                  _elem25.read(iprot);
                  struct.voList.add(_elem25);
                }
                iprot.readListEnd();
              }
              struct.setVoListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, FFTOrgReListVoRes struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.resultVo != null) {
        oprot.writeFieldBegin(RESULT_VO_FIELD_DESC);
        struct.resultVo.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.voList != null) {
        oprot.writeFieldBegin(VO_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.voList.size()));
          for (FFTOrgReVo _iter27 : struct.voList)
          {
            _iter27.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class FFTOrgReListVoResTupleSchemeFactory implements SchemeFactory {
    public FFTOrgReListVoResTupleScheme getScheme() {
      return new FFTOrgReListVoResTupleScheme();
    }
  }

  private static class FFTOrgReListVoResTupleScheme extends TupleScheme<FFTOrgReListVoRes> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, FFTOrgReListVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetResultVo()) {
        optionals.set(0);
      }
      if (struct.isSetVoList()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetResultVo()) {
        struct.resultVo.write(oprot);
      }
      if (struct.isSetVoList()) {
        {
          oprot.writeI32(struct.voList.size());
          for (FFTOrgReVo _iter28 : struct.voList)
          {
            _iter28.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, FFTOrgReListVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.resultVo = new com.froad.thrift.vo.ResultVo();
        struct.resultVo.read(iprot);
        struct.setResultVoIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list29 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.voList = new ArrayList<FFTOrgReVo>(_list29.size);
          FFTOrgReVo _elem30;
          for (int _i31 = 0; _i31 < _list29.size; ++_i31)
          {
            _elem30 = new FFTOrgReVo();
            _elem30.read(iprot);
            struct.voList.add(_elem30);
          }
        }
        struct.setVoListIsSet(true);
      }
    }
  }

}
