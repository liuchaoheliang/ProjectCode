/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.froad.thrift.vo.provider;

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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2015-12-2")
public class ProviderListVo implements org.apache.thrift.TBase<ProviderListVo, ProviderListVo._Fields>, java.io.Serializable, Cloneable, Comparable<ProviderListVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ProviderListVo");

  private static final org.apache.thrift.protocol.TField RESULT_VO_FIELD_DESC = new org.apache.thrift.protocol.TField("resultVo", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField PROVIDER_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("providerList", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ProviderListVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ProviderListVoTupleSchemeFactory());
  }

  /**
   * 返回描述 *
   */
  public com.froad.thrift.vo.ResultVo resultVo; // required
  /**
   * 供应商集合 *
   */
  public List<ProviderVo> providerList; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 返回描述 *
     */
    RESULT_VO((short)1, "resultVo"),
    /**
     * 供应商集合 *
     */
    PROVIDER_LIST((short)2, "providerList");

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
        case 2: // PROVIDER_LIST
          return PROVIDER_LIST;
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
    tmpMap.put(_Fields.RESULT_VO, new org.apache.thrift.meta_data.FieldMetaData("resultVo", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.ResultVo.class)));
    tmpMap.put(_Fields.PROVIDER_LIST, new org.apache.thrift.meta_data.FieldMetaData("providerList", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ProviderVo.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ProviderListVo.class, metaDataMap);
  }

  public ProviderListVo() {
  }

  public ProviderListVo(
    com.froad.thrift.vo.ResultVo resultVo,
    List<ProviderVo> providerList)
  {
    this();
    this.resultVo = resultVo;
    this.providerList = providerList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ProviderListVo(ProviderListVo other) {
    if (other.isSetResultVo()) {
      this.resultVo = new com.froad.thrift.vo.ResultVo(other.resultVo);
    }
    if (other.isSetProviderList()) {
      List<ProviderVo> __this__providerList = new ArrayList<ProviderVo>(other.providerList.size());
      for (ProviderVo other_element : other.providerList) {
        __this__providerList.add(new ProviderVo(other_element));
      }
      this.providerList = __this__providerList;
    }
  }

  public ProviderListVo deepCopy() {
    return new ProviderListVo(this);
  }

  @Override
  public void clear() {
    this.resultVo = null;
    this.providerList = null;
  }

  /**
   * 返回描述 *
   */
  public com.froad.thrift.vo.ResultVo getResultVo() {
    return this.resultVo;
  }

  /**
   * 返回描述 *
   */
  public ProviderListVo setResultVo(com.froad.thrift.vo.ResultVo resultVo) {
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

  public int getProviderListSize() {
    return (this.providerList == null) ? 0 : this.providerList.size();
  }

  public java.util.Iterator<ProviderVo> getProviderListIterator() {
    return (this.providerList == null) ? null : this.providerList.iterator();
  }

  public void addToProviderList(ProviderVo elem) {
    if (this.providerList == null) {
      this.providerList = new ArrayList<ProviderVo>();
    }
    this.providerList.add(elem);
  }

  /**
   * 供应商集合 *
   */
  public List<ProviderVo> getProviderList() {
    return this.providerList;
  }

  /**
   * 供应商集合 *
   */
  public ProviderListVo setProviderList(List<ProviderVo> providerList) {
    this.providerList = providerList;
    return this;
  }

  public void unsetProviderList() {
    this.providerList = null;
  }

  /** Returns true if field providerList is set (has been assigned a value) and false otherwise */
  public boolean isSetProviderList() {
    return this.providerList != null;
  }

  public void setProviderListIsSet(boolean value) {
    if (!value) {
      this.providerList = null;
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

    case PROVIDER_LIST:
      if (value == null) {
        unsetProviderList();
      } else {
        setProviderList((List<ProviderVo>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RESULT_VO:
      return getResultVo();

    case PROVIDER_LIST:
      return getProviderList();

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
    case PROVIDER_LIST:
      return isSetProviderList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ProviderListVo)
      return this.equals((ProviderListVo)that);
    return false;
  }

  public boolean equals(ProviderListVo that) {
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

    boolean this_present_providerList = true && this.isSetProviderList();
    boolean that_present_providerList = true && that.isSetProviderList();
    if (this_present_providerList || that_present_providerList) {
      if (!(this_present_providerList && that_present_providerList))
        return false;
      if (!this.providerList.equals(that.providerList))
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

    boolean present_providerList = true && (isSetProviderList());
    list.add(present_providerList);
    if (present_providerList)
      list.add(providerList);

    return list.hashCode();
  }

  @Override
  public int compareTo(ProviderListVo other) {
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
    lastComparison = Boolean.valueOf(isSetProviderList()).compareTo(other.isSetProviderList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProviderList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.providerList, other.providerList);
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
    StringBuilder sb = new StringBuilder("ProviderListVo(");
    boolean first = true;

    sb.append("resultVo:");
    if (this.resultVo == null) {
      sb.append("null");
    } else {
      sb.append(this.resultVo);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("providerList:");
    if (this.providerList == null) {
      sb.append("null");
    } else {
      sb.append(this.providerList);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (resultVo == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'resultVo' was not present! Struct: " + toString());
    }
    if (providerList == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'providerList' was not present! Struct: " + toString());
    }
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

  private static class ProviderListVoStandardSchemeFactory implements SchemeFactory {
    public ProviderListVoStandardScheme getScheme() {
      return new ProviderListVoStandardScheme();
    }
  }

  private static class ProviderListVoStandardScheme extends StandardScheme<ProviderListVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ProviderListVo struct) throws org.apache.thrift.TException {
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
          case 2: // PROVIDER_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.providerList = new ArrayList<ProviderVo>(_list0.size);
                ProviderVo _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = new ProviderVo();
                  _elem1.read(iprot);
                  struct.providerList.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setProviderListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ProviderListVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.resultVo != null) {
        oprot.writeFieldBegin(RESULT_VO_FIELD_DESC);
        struct.resultVo.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.providerList != null) {
        oprot.writeFieldBegin(PROVIDER_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.providerList.size()));
          for (ProviderVo _iter3 : struct.providerList)
          {
            _iter3.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ProviderListVoTupleSchemeFactory implements SchemeFactory {
    public ProviderListVoTupleScheme getScheme() {
      return new ProviderListVoTupleScheme();
    }
  }

  private static class ProviderListVoTupleScheme extends TupleScheme<ProviderListVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ProviderListVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      struct.resultVo.write(oprot);
      {
        oprot.writeI32(struct.providerList.size());
        for (ProviderVo _iter4 : struct.providerList)
        {
          _iter4.write(oprot);
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ProviderListVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.resultVo = new com.froad.thrift.vo.ResultVo();
      struct.resultVo.read(iprot);
      struct.setResultVoIsSet(true);
      {
        org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
        struct.providerList = new ArrayList<ProviderVo>(_list5.size);
        ProviderVo _elem6;
        for (int _i7 = 0; _i7 < _list5.size; ++_i7)
        {
          _elem6 = new ProviderVo();
          _elem6.read(iprot);
          struct.providerList.add(_elem6);
        }
      }
      struct.setProviderListIsSet(true);
    }
  }

}

