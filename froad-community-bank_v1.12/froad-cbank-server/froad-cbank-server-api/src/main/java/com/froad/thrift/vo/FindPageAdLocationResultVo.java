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
 * 查询 AdLocation 分页 结果
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class FindPageAdLocationResultVo implements org.apache.thrift.TBase<FindPageAdLocationResultVo, FindPageAdLocationResultVo._Fields>, java.io.Serializable, Cloneable, Comparable<FindPageAdLocationResultVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("FindPageAdLocationResultVo");

  private static final org.apache.thrift.protocol.TField RESULT_VO_FIELD_DESC = new org.apache.thrift.protocol.TField("resultVo", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField AD_LOCATION_PAGE_VO_RES_FIELD_DESC = new org.apache.thrift.protocol.TField("adLocationPageVoRes", org.apache.thrift.protocol.TType.STRUCT, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new FindPageAdLocationResultVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new FindPageAdLocationResultVoTupleSchemeFactory());
  }

  /**
   * ResultVo 结果
   */
  public com.froad.thrift.vo.ResultVo resultVo; // required
  /**
   * 分页结果
   */
  public AdLocationPageVoRes adLocationPageVoRes; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * ResultVo 结果
     */
    RESULT_VO((short)1, "resultVo"),
    /**
     * 分页结果
     */
    AD_LOCATION_PAGE_VO_RES((short)2, "adLocationPageVoRes");

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
        case 2: // AD_LOCATION_PAGE_VO_RES
          return AD_LOCATION_PAGE_VO_RES;
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
    tmpMap.put(_Fields.AD_LOCATION_PAGE_VO_RES, new org.apache.thrift.meta_data.FieldMetaData("adLocationPageVoRes", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, AdLocationPageVoRes.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(FindPageAdLocationResultVo.class, metaDataMap);
  }

  public FindPageAdLocationResultVo() {
  }

  public FindPageAdLocationResultVo(
    com.froad.thrift.vo.ResultVo resultVo,
    AdLocationPageVoRes adLocationPageVoRes)
  {
    this();
    this.resultVo = resultVo;
    this.adLocationPageVoRes = adLocationPageVoRes;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public FindPageAdLocationResultVo(FindPageAdLocationResultVo other) {
    if (other.isSetResultVo()) {
      this.resultVo = new com.froad.thrift.vo.ResultVo(other.resultVo);
    }
    if (other.isSetAdLocationPageVoRes()) {
      this.adLocationPageVoRes = new AdLocationPageVoRes(other.adLocationPageVoRes);
    }
  }

  public FindPageAdLocationResultVo deepCopy() {
    return new FindPageAdLocationResultVo(this);
  }

  @Override
  public void clear() {
    this.resultVo = null;
    this.adLocationPageVoRes = null;
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
  public FindPageAdLocationResultVo setResultVo(com.froad.thrift.vo.ResultVo resultVo) {
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

  /**
   * 分页结果
   */
  public AdLocationPageVoRes getAdLocationPageVoRes() {
    return this.adLocationPageVoRes;
  }

  /**
   * 分页结果
   */
  public FindPageAdLocationResultVo setAdLocationPageVoRes(AdLocationPageVoRes adLocationPageVoRes) {
    this.adLocationPageVoRes = adLocationPageVoRes;
    return this;
  }

  public void unsetAdLocationPageVoRes() {
    this.adLocationPageVoRes = null;
  }

  /** Returns true if field adLocationPageVoRes is set (has been assigned a value) and false otherwise */
  public boolean isSetAdLocationPageVoRes() {
    return this.adLocationPageVoRes != null;
  }

  public void setAdLocationPageVoResIsSet(boolean value) {
    if (!value) {
      this.adLocationPageVoRes = null;
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

    case AD_LOCATION_PAGE_VO_RES:
      if (value == null) {
        unsetAdLocationPageVoRes();
      } else {
        setAdLocationPageVoRes((AdLocationPageVoRes)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RESULT_VO:
      return getResultVo();

    case AD_LOCATION_PAGE_VO_RES:
      return getAdLocationPageVoRes();

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
    case AD_LOCATION_PAGE_VO_RES:
      return isSetAdLocationPageVoRes();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof FindPageAdLocationResultVo)
      return this.equals((FindPageAdLocationResultVo)that);
    return false;
  }

  public boolean equals(FindPageAdLocationResultVo that) {
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

    boolean this_present_adLocationPageVoRes = true && this.isSetAdLocationPageVoRes();
    boolean that_present_adLocationPageVoRes = true && that.isSetAdLocationPageVoRes();
    if (this_present_adLocationPageVoRes || that_present_adLocationPageVoRes) {
      if (!(this_present_adLocationPageVoRes && that_present_adLocationPageVoRes))
        return false;
      if (!this.adLocationPageVoRes.equals(that.adLocationPageVoRes))
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

    boolean present_adLocationPageVoRes = true && (isSetAdLocationPageVoRes());
    list.add(present_adLocationPageVoRes);
    if (present_adLocationPageVoRes)
      list.add(adLocationPageVoRes);

    return list.hashCode();
  }

  @Override
  public int compareTo(FindPageAdLocationResultVo other) {
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
    lastComparison = Boolean.valueOf(isSetAdLocationPageVoRes()).compareTo(other.isSetAdLocationPageVoRes());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAdLocationPageVoRes()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.adLocationPageVoRes, other.adLocationPageVoRes);
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
    StringBuilder sb = new StringBuilder("FindPageAdLocationResultVo(");
    boolean first = true;

    sb.append("resultVo:");
    if (this.resultVo == null) {
      sb.append("null");
    } else {
      sb.append(this.resultVo);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("adLocationPageVoRes:");
    if (this.adLocationPageVoRes == null) {
      sb.append("null");
    } else {
      sb.append(this.adLocationPageVoRes);
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
    if (adLocationPageVoRes != null) {
      adLocationPageVoRes.validate();
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

  private static class FindPageAdLocationResultVoStandardSchemeFactory implements SchemeFactory {
    public FindPageAdLocationResultVoStandardScheme getScheme() {
      return new FindPageAdLocationResultVoStandardScheme();
    }
  }

  private static class FindPageAdLocationResultVoStandardScheme extends StandardScheme<FindPageAdLocationResultVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, FindPageAdLocationResultVo struct) throws org.apache.thrift.TException {
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
          case 2: // AD_LOCATION_PAGE_VO_RES
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.adLocationPageVoRes = new AdLocationPageVoRes();
              struct.adLocationPageVoRes.read(iprot);
              struct.setAdLocationPageVoResIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, FindPageAdLocationResultVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.resultVo != null) {
        oprot.writeFieldBegin(RESULT_VO_FIELD_DESC);
        struct.resultVo.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.adLocationPageVoRes != null) {
        oprot.writeFieldBegin(AD_LOCATION_PAGE_VO_RES_FIELD_DESC);
        struct.adLocationPageVoRes.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class FindPageAdLocationResultVoTupleSchemeFactory implements SchemeFactory {
    public FindPageAdLocationResultVoTupleScheme getScheme() {
      return new FindPageAdLocationResultVoTupleScheme();
    }
  }

  private static class FindPageAdLocationResultVoTupleScheme extends TupleScheme<FindPageAdLocationResultVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, FindPageAdLocationResultVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetResultVo()) {
        optionals.set(0);
      }
      if (struct.isSetAdLocationPageVoRes()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetResultVo()) {
        struct.resultVo.write(oprot);
      }
      if (struct.isSetAdLocationPageVoRes()) {
        struct.adLocationPageVoRes.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, FindPageAdLocationResultVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.resultVo = new com.froad.thrift.vo.ResultVo();
        struct.resultVo.read(iprot);
        struct.setResultVoIsSet(true);
      }
      if (incoming.get(1)) {
        struct.adLocationPageVoRes = new AdLocationPageVoRes();
        struct.adLocationPageVoRes.read(iprot);
        struct.setAdLocationPageVoResIsSet(true);
      }
    }
  }

}

