/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.froad.thrift.vo.outletActivity;

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
 * 根据门店id查询商户信息
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2015-10-28")
public class MerchantNameResVo implements org.apache.thrift.TBase<MerchantNameResVo, MerchantNameResVo._Fields>, java.io.Serializable, Cloneable, Comparable<MerchantNameResVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("MerchantNameResVo");

  private static final org.apache.thrift.protocol.TField RESULT_VO_FIELD_DESC = new org.apache.thrift.protocol.TField("resultVo", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField MERCHANT_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("merchantName", org.apache.thrift.protocol.TType.STRING, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new MerchantNameResVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new MerchantNameResVoTupleSchemeFactory());
  }

  /**
   * 返回描述 *
   */
  public com.froad.thrift.vo.ResultVo resultVo; // required
  /**
   * 商户名称 *
   */
  public String merchantName; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 返回描述 *
     */
    RESULT_VO((short)1, "resultVo"),
    /**
     * 商户名称 *
     */
    MERCHANT_NAME((short)2, "merchantName");

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
        case 2: // MERCHANT_NAME
          return MERCHANT_NAME;
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
    tmpMap.put(_Fields.MERCHANT_NAME, new org.apache.thrift.meta_data.FieldMetaData("merchantName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(MerchantNameResVo.class, metaDataMap);
  }

  public MerchantNameResVo() {
  }

  public MerchantNameResVo(
    com.froad.thrift.vo.ResultVo resultVo,
    String merchantName)
  {
    this();
    this.resultVo = resultVo;
    this.merchantName = merchantName;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public MerchantNameResVo(MerchantNameResVo other) {
    if (other.isSetResultVo()) {
      this.resultVo = new com.froad.thrift.vo.ResultVo(other.resultVo);
    }
    if (other.isSetMerchantName()) {
      this.merchantName = other.merchantName;
    }
  }

  public MerchantNameResVo deepCopy() {
    return new MerchantNameResVo(this);
  }

  @Override
  public void clear() {
    this.resultVo = null;
    this.merchantName = null;
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
  public MerchantNameResVo setResultVo(com.froad.thrift.vo.ResultVo resultVo) {
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
   * 商户名称 *
   */
  public String getMerchantName() {
    return this.merchantName;
  }

  /**
   * 商户名称 *
   */
  public MerchantNameResVo setMerchantName(String merchantName) {
    this.merchantName = merchantName;
    return this;
  }

  public void unsetMerchantName() {
    this.merchantName = null;
  }

  /** Returns true if field merchantName is set (has been assigned a value) and false otherwise */
  public boolean isSetMerchantName() {
    return this.merchantName != null;
  }

  public void setMerchantNameIsSet(boolean value) {
    if (!value) {
      this.merchantName = null;
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

    case MERCHANT_NAME:
      if (value == null) {
        unsetMerchantName();
      } else {
        setMerchantName((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RESULT_VO:
      return getResultVo();

    case MERCHANT_NAME:
      return getMerchantName();

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
    case MERCHANT_NAME:
      return isSetMerchantName();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof MerchantNameResVo)
      return this.equals((MerchantNameResVo)that);
    return false;
  }

  public boolean equals(MerchantNameResVo that) {
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

    boolean this_present_merchantName = true && this.isSetMerchantName();
    boolean that_present_merchantName = true && that.isSetMerchantName();
    if (this_present_merchantName || that_present_merchantName) {
      if (!(this_present_merchantName && that_present_merchantName))
        return false;
      if (!this.merchantName.equals(that.merchantName))
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

    boolean present_merchantName = true && (isSetMerchantName());
    list.add(present_merchantName);
    if (present_merchantName)
      list.add(merchantName);

    return list.hashCode();
  }

  @Override
  public int compareTo(MerchantNameResVo other) {
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
    lastComparison = Boolean.valueOf(isSetMerchantName()).compareTo(other.isSetMerchantName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMerchantName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.merchantName, other.merchantName);
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
    StringBuilder sb = new StringBuilder("MerchantNameResVo(");
    boolean first = true;

    sb.append("resultVo:");
    if (this.resultVo == null) {
      sb.append("null");
    } else {
      sb.append(this.resultVo);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("merchantName:");
    if (this.merchantName == null) {
      sb.append("null");
    } else {
      sb.append(this.merchantName);
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

  private static class MerchantNameResVoStandardSchemeFactory implements SchemeFactory {
    public MerchantNameResVoStandardScheme getScheme() {
      return new MerchantNameResVoStandardScheme();
    }
  }

  private static class MerchantNameResVoStandardScheme extends StandardScheme<MerchantNameResVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, MerchantNameResVo struct) throws org.apache.thrift.TException {
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
          case 2: // MERCHANT_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.merchantName = iprot.readString();
              struct.setMerchantNameIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, MerchantNameResVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.resultVo != null) {
        oprot.writeFieldBegin(RESULT_VO_FIELD_DESC);
        struct.resultVo.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.merchantName != null) {
        oprot.writeFieldBegin(MERCHANT_NAME_FIELD_DESC);
        oprot.writeString(struct.merchantName);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class MerchantNameResVoTupleSchemeFactory implements SchemeFactory {
    public MerchantNameResVoTupleScheme getScheme() {
      return new MerchantNameResVoTupleScheme();
    }
  }

  private static class MerchantNameResVoTupleScheme extends TupleScheme<MerchantNameResVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, MerchantNameResVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      struct.resultVo.write(oprot);
      BitSet optionals = new BitSet();
      if (struct.isSetMerchantName()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetMerchantName()) {
        oprot.writeString(struct.merchantName);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, MerchantNameResVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.resultVo = new com.froad.thrift.vo.ResultVo();
      struct.resultVo.read(iprot);
      struct.setResultVoIsSet(true);
      BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.merchantName = iprot.readString();
        struct.setMerchantNameIsSet(true);
      }
    }
  }

}
