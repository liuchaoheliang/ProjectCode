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
 * 查询注册(首单)活动规则信息 RegisteredRuleInfoVo 列表 结果
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class FindAllRegisteredRuleInfoVoResultVo implements org.apache.thrift.TBase<FindAllRegisteredRuleInfoVoResultVo, FindAllRegisteredRuleInfoVoResultVo._Fields>, java.io.Serializable, Cloneable, Comparable<FindAllRegisteredRuleInfoVoResultVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("FindAllRegisteredRuleInfoVoResultVo");

  private static final org.apache.thrift.protocol.TField RESULT_VO_FIELD_DESC = new org.apache.thrift.protocol.TField("resultVo", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField REGISTERED_RULE_INFO_VO_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("registeredRuleInfoVoList", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new FindAllRegisteredRuleInfoVoResultVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new FindAllRegisteredRuleInfoVoResultVoTupleSchemeFactory());
  }

  /**
   * ResultVo 结果
   */
  public com.froad.thrift.vo.ResultVo resultVo; // required
  /**
   * 结果集
   */
  public List<RegisteredRuleInfoVo> registeredRuleInfoVoList; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * ResultVo 结果
     */
    RESULT_VO((short)1, "resultVo"),
    /**
     * 结果集
     */
    REGISTERED_RULE_INFO_VO_LIST((short)2, "registeredRuleInfoVoList");

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
        case 2: // REGISTERED_RULE_INFO_VO_LIST
          return REGISTERED_RULE_INFO_VO_LIST;
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
    tmpMap.put(_Fields.REGISTERED_RULE_INFO_VO_LIST, new org.apache.thrift.meta_data.FieldMetaData("registeredRuleInfoVoList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, RegisteredRuleInfoVo.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(FindAllRegisteredRuleInfoVoResultVo.class, metaDataMap);
  }

  public FindAllRegisteredRuleInfoVoResultVo() {
  }

  public FindAllRegisteredRuleInfoVoResultVo(
    com.froad.thrift.vo.ResultVo resultVo,
    List<RegisteredRuleInfoVo> registeredRuleInfoVoList)
  {
    this();
    this.resultVo = resultVo;
    this.registeredRuleInfoVoList = registeredRuleInfoVoList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public FindAllRegisteredRuleInfoVoResultVo(FindAllRegisteredRuleInfoVoResultVo other) {
    if (other.isSetResultVo()) {
      this.resultVo = new com.froad.thrift.vo.ResultVo(other.resultVo);
    }
    if (other.isSetRegisteredRuleInfoVoList()) {
      List<RegisteredRuleInfoVo> __this__registeredRuleInfoVoList = new ArrayList<RegisteredRuleInfoVo>(other.registeredRuleInfoVoList.size());
      for (RegisteredRuleInfoVo other_element : other.registeredRuleInfoVoList) {
        __this__registeredRuleInfoVoList.add(new RegisteredRuleInfoVo(other_element));
      }
      this.registeredRuleInfoVoList = __this__registeredRuleInfoVoList;
    }
  }

  public FindAllRegisteredRuleInfoVoResultVo deepCopy() {
    return new FindAllRegisteredRuleInfoVoResultVo(this);
  }

  @Override
  public void clear() {
    this.resultVo = null;
    this.registeredRuleInfoVoList = null;
  }

  /**
   * ResultVo 结果
   */
  public com.froad.thrift.vo.ResultVo getResultVo() {
    return this.resultVo;
  }

  /**
   * ResultVo 结果
   */
  public FindAllRegisteredRuleInfoVoResultVo setResultVo(com.froad.thrift.vo.ResultVo resultVo) {
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

  public int getRegisteredRuleInfoVoListSize() {
    return (this.registeredRuleInfoVoList == null) ? 0 : this.registeredRuleInfoVoList.size();
  }

  public java.util.Iterator<RegisteredRuleInfoVo> getRegisteredRuleInfoVoListIterator() {
    return (this.registeredRuleInfoVoList == null) ? null : this.registeredRuleInfoVoList.iterator();
  }

  public void addToRegisteredRuleInfoVoList(RegisteredRuleInfoVo elem) {
    if (this.registeredRuleInfoVoList == null) {
      this.registeredRuleInfoVoList = new ArrayList<RegisteredRuleInfoVo>();
    }
    this.registeredRuleInfoVoList.add(elem);
  }

  /**
   * 结果集
   */
  public List<RegisteredRuleInfoVo> getRegisteredRuleInfoVoList() {
    return this.registeredRuleInfoVoList;
  }

  /**
   * 结果集
   */
  public FindAllRegisteredRuleInfoVoResultVo setRegisteredRuleInfoVoList(List<RegisteredRuleInfoVo> registeredRuleInfoVoList) {
    this.registeredRuleInfoVoList = registeredRuleInfoVoList;
    return this;
  }

  public void unsetRegisteredRuleInfoVoList() {
    this.registeredRuleInfoVoList = null;
  }

  /** Returns true if field registeredRuleInfoVoList is set (has been assigned a value) and false otherwise */
  public boolean isSetRegisteredRuleInfoVoList() {
    return this.registeredRuleInfoVoList != null;
  }

  public void setRegisteredRuleInfoVoListIsSet(boolean value) {
    if (!value) {
      this.registeredRuleInfoVoList = null;
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

    case REGISTERED_RULE_INFO_VO_LIST:
      if (value == null) {
        unsetRegisteredRuleInfoVoList();
      } else {
        setRegisteredRuleInfoVoList((List<RegisteredRuleInfoVo>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RESULT_VO:
      return getResultVo();

    case REGISTERED_RULE_INFO_VO_LIST:
      return getRegisteredRuleInfoVoList();

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
    case REGISTERED_RULE_INFO_VO_LIST:
      return isSetRegisteredRuleInfoVoList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof FindAllRegisteredRuleInfoVoResultVo)
      return this.equals((FindAllRegisteredRuleInfoVoResultVo)that);
    return false;
  }

  public boolean equals(FindAllRegisteredRuleInfoVoResultVo that) {
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

    boolean this_present_registeredRuleInfoVoList = true && this.isSetRegisteredRuleInfoVoList();
    boolean that_present_registeredRuleInfoVoList = true && that.isSetRegisteredRuleInfoVoList();
    if (this_present_registeredRuleInfoVoList || that_present_registeredRuleInfoVoList) {
      if (!(this_present_registeredRuleInfoVoList && that_present_registeredRuleInfoVoList))
        return false;
      if (!this.registeredRuleInfoVoList.equals(that.registeredRuleInfoVoList))
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

    boolean present_registeredRuleInfoVoList = true && (isSetRegisteredRuleInfoVoList());
    list.add(present_registeredRuleInfoVoList);
    if (present_registeredRuleInfoVoList)
      list.add(registeredRuleInfoVoList);

    return list.hashCode();
  }

  @Override
  public int compareTo(FindAllRegisteredRuleInfoVoResultVo other) {
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
    lastComparison = Boolean.valueOf(isSetRegisteredRuleInfoVoList()).compareTo(other.isSetRegisteredRuleInfoVoList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRegisteredRuleInfoVoList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.registeredRuleInfoVoList, other.registeredRuleInfoVoList);
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
    StringBuilder sb = new StringBuilder("FindAllRegisteredRuleInfoVoResultVo(");
    boolean first = true;

    sb.append("resultVo:");
    if (this.resultVo == null) {
      sb.append("null");
    } else {
      sb.append(this.resultVo);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("registeredRuleInfoVoList:");
    if (this.registeredRuleInfoVoList == null) {
      sb.append("null");
    } else {
      sb.append(this.registeredRuleInfoVoList);
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

  private static class FindAllRegisteredRuleInfoVoResultVoStandardSchemeFactory implements SchemeFactory {
    public FindAllRegisteredRuleInfoVoResultVoStandardScheme getScheme() {
      return new FindAllRegisteredRuleInfoVoResultVoStandardScheme();
    }
  }

  private static class FindAllRegisteredRuleInfoVoResultVoStandardScheme extends StandardScheme<FindAllRegisteredRuleInfoVoResultVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, FindAllRegisteredRuleInfoVoResultVo struct) throws org.apache.thrift.TException {
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
          case 2: // REGISTERED_RULE_INFO_VO_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list456 = iprot.readListBegin();
                struct.registeredRuleInfoVoList = new ArrayList<RegisteredRuleInfoVo>(_list456.size);
                RegisteredRuleInfoVo _elem457;
                for (int _i458 = 0; _i458 < _list456.size; ++_i458)
                {
                  _elem457 = new RegisteredRuleInfoVo();
                  _elem457.read(iprot);
                  struct.registeredRuleInfoVoList.add(_elem457);
                }
                iprot.readListEnd();
              }
              struct.setRegisteredRuleInfoVoListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, FindAllRegisteredRuleInfoVoResultVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.resultVo != null) {
        oprot.writeFieldBegin(RESULT_VO_FIELD_DESC);
        struct.resultVo.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.registeredRuleInfoVoList != null) {
        oprot.writeFieldBegin(REGISTERED_RULE_INFO_VO_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.registeredRuleInfoVoList.size()));
          for (RegisteredRuleInfoVo _iter459 : struct.registeredRuleInfoVoList)
          {
            _iter459.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class FindAllRegisteredRuleInfoVoResultVoTupleSchemeFactory implements SchemeFactory {
    public FindAllRegisteredRuleInfoVoResultVoTupleScheme getScheme() {
      return new FindAllRegisteredRuleInfoVoResultVoTupleScheme();
    }
  }

  private static class FindAllRegisteredRuleInfoVoResultVoTupleScheme extends TupleScheme<FindAllRegisteredRuleInfoVoResultVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, FindAllRegisteredRuleInfoVoResultVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetResultVo()) {
        optionals.set(0);
      }
      if (struct.isSetRegisteredRuleInfoVoList()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetResultVo()) {
        struct.resultVo.write(oprot);
      }
      if (struct.isSetRegisteredRuleInfoVoList()) {
        {
          oprot.writeI32(struct.registeredRuleInfoVoList.size());
          for (RegisteredRuleInfoVo _iter460 : struct.registeredRuleInfoVoList)
          {
            _iter460.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, FindAllRegisteredRuleInfoVoResultVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.resultVo = new com.froad.thrift.vo.ResultVo();
        struct.resultVo.read(iprot);
        struct.setResultVoIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list461 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.registeredRuleInfoVoList = new ArrayList<RegisteredRuleInfoVo>(_list461.size);
          RegisteredRuleInfoVo _elem462;
          for (int _i463 = 0; _i463 < _list461.size; ++_i463)
          {
            _elem462 = new RegisteredRuleInfoVo();
            _elem462.read(iprot);
            struct.registeredRuleInfoVoList.add(_elem462);
          }
        }
        struct.setRegisteredRuleInfoVoListIsSet(true);
      }
    }
  }

}

