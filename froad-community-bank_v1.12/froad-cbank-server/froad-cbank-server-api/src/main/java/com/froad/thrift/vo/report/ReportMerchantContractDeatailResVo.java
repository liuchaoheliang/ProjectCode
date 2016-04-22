/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.froad.thrift.vo.report;

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
 * 商户签约人统计详情返回参数
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class ReportMerchantContractDeatailResVo implements org.apache.thrift.TBase<ReportMerchantContractDeatailResVo, ReportMerchantContractDeatailResVo._Fields>, java.io.Serializable, Cloneable, Comparable<ReportMerchantContractDeatailResVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ReportMerchantContractDeatailResVo");

  private static final org.apache.thrift.protocol.TField RESULT_VO_FIELD_DESC = new org.apache.thrift.protocol.TField("resultVo", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField MERCHANT_CONTRACT_DEATAIL_VOS_FIELD_DESC = new org.apache.thrift.protocol.TField("merchantContractDeatailVos", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ReportMerchantContractDeatailResVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ReportMerchantContractDeatailResVoTupleSchemeFactory());
  }

  /**
   * 返回结果 *
   */
  public com.froad.thrift.vo.ResultVo resultVo; // required
  /**
   * 商户签约人排行集合 *
   */
  public List<ReportMerchantContractDeatailVo> merchantContractDeatailVos; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 返回结果 *
     */
    RESULT_VO((short)1, "resultVo"),
    /**
     * 商户签约人排行集合 *
     */
    MERCHANT_CONTRACT_DEATAIL_VOS((short)2, "merchantContractDeatailVos");

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
        case 2: // MERCHANT_CONTRACT_DEATAIL_VOS
          return MERCHANT_CONTRACT_DEATAIL_VOS;
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
    tmpMap.put(_Fields.MERCHANT_CONTRACT_DEATAIL_VOS, new org.apache.thrift.meta_data.FieldMetaData("merchantContractDeatailVos", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ReportMerchantContractDeatailVo.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ReportMerchantContractDeatailResVo.class, metaDataMap);
  }

  public ReportMerchantContractDeatailResVo() {
  }

  public ReportMerchantContractDeatailResVo(
    com.froad.thrift.vo.ResultVo resultVo,
    List<ReportMerchantContractDeatailVo> merchantContractDeatailVos)
  {
    this();
    this.resultVo = resultVo;
    this.merchantContractDeatailVos = merchantContractDeatailVos;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ReportMerchantContractDeatailResVo(ReportMerchantContractDeatailResVo other) {
    if (other.isSetResultVo()) {
      this.resultVo = new com.froad.thrift.vo.ResultVo(other.resultVo);
    }
    if (other.isSetMerchantContractDeatailVos()) {
      List<ReportMerchantContractDeatailVo> __this__merchantContractDeatailVos = new ArrayList<ReportMerchantContractDeatailVo>(other.merchantContractDeatailVos.size());
      for (ReportMerchantContractDeatailVo other_element : other.merchantContractDeatailVos) {
        __this__merchantContractDeatailVos.add(new ReportMerchantContractDeatailVo(other_element));
      }
      this.merchantContractDeatailVos = __this__merchantContractDeatailVos;
    }
  }

  public ReportMerchantContractDeatailResVo deepCopy() {
    return new ReportMerchantContractDeatailResVo(this);
  }

  @Override
  public void clear() {
    this.resultVo = null;
    this.merchantContractDeatailVos = null;
  }

  /**
   * 返回结果 *
   */
  public com.froad.thrift.vo.ResultVo getResultVo() {
    return this.resultVo;
  }

  /**
   * 返回结果 *
   */
  public ReportMerchantContractDeatailResVo setResultVo(com.froad.thrift.vo.ResultVo resultVo) {
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

  public int getMerchantContractDeatailVosSize() {
    return (this.merchantContractDeatailVos == null) ? 0 : this.merchantContractDeatailVos.size();
  }

  public java.util.Iterator<ReportMerchantContractDeatailVo> getMerchantContractDeatailVosIterator() {
    return (this.merchantContractDeatailVos == null) ? null : this.merchantContractDeatailVos.iterator();
  }

  public void addToMerchantContractDeatailVos(ReportMerchantContractDeatailVo elem) {
    if (this.merchantContractDeatailVos == null) {
      this.merchantContractDeatailVos = new ArrayList<ReportMerchantContractDeatailVo>();
    }
    this.merchantContractDeatailVos.add(elem);
  }

  /**
   * 商户签约人排行集合 *
   */
  public List<ReportMerchantContractDeatailVo> getMerchantContractDeatailVos() {
    return this.merchantContractDeatailVos;
  }

  /**
   * 商户签约人排行集合 *
   */
  public ReportMerchantContractDeatailResVo setMerchantContractDeatailVos(List<ReportMerchantContractDeatailVo> merchantContractDeatailVos) {
    this.merchantContractDeatailVos = merchantContractDeatailVos;
    return this;
  }

  public void unsetMerchantContractDeatailVos() {
    this.merchantContractDeatailVos = null;
  }

  /** Returns true if field merchantContractDeatailVos is set (has been assigned a value) and false otherwise */
  public boolean isSetMerchantContractDeatailVos() {
    return this.merchantContractDeatailVos != null;
  }

  public void setMerchantContractDeatailVosIsSet(boolean value) {
    if (!value) {
      this.merchantContractDeatailVos = null;
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

    case MERCHANT_CONTRACT_DEATAIL_VOS:
      if (value == null) {
        unsetMerchantContractDeatailVos();
      } else {
        setMerchantContractDeatailVos((List<ReportMerchantContractDeatailVo>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RESULT_VO:
      return getResultVo();

    case MERCHANT_CONTRACT_DEATAIL_VOS:
      return getMerchantContractDeatailVos();

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
    case MERCHANT_CONTRACT_DEATAIL_VOS:
      return isSetMerchantContractDeatailVos();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ReportMerchantContractDeatailResVo)
      return this.equals((ReportMerchantContractDeatailResVo)that);
    return false;
  }

  public boolean equals(ReportMerchantContractDeatailResVo that) {
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

    boolean this_present_merchantContractDeatailVos = true && this.isSetMerchantContractDeatailVos();
    boolean that_present_merchantContractDeatailVos = true && that.isSetMerchantContractDeatailVos();
    if (this_present_merchantContractDeatailVos || that_present_merchantContractDeatailVos) {
      if (!(this_present_merchantContractDeatailVos && that_present_merchantContractDeatailVos))
        return false;
      if (!this.merchantContractDeatailVos.equals(that.merchantContractDeatailVos))
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

    boolean present_merchantContractDeatailVos = true && (isSetMerchantContractDeatailVos());
    list.add(present_merchantContractDeatailVos);
    if (present_merchantContractDeatailVos)
      list.add(merchantContractDeatailVos);

    return list.hashCode();
  }

  @Override
  public int compareTo(ReportMerchantContractDeatailResVo other) {
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
    lastComparison = Boolean.valueOf(isSetMerchantContractDeatailVos()).compareTo(other.isSetMerchantContractDeatailVos());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMerchantContractDeatailVos()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.merchantContractDeatailVos, other.merchantContractDeatailVos);
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
    StringBuilder sb = new StringBuilder("ReportMerchantContractDeatailResVo(");
    boolean first = true;

    sb.append("resultVo:");
    if (this.resultVo == null) {
      sb.append("null");
    } else {
      sb.append(this.resultVo);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("merchantContractDeatailVos:");
    if (this.merchantContractDeatailVos == null) {
      sb.append("null");
    } else {
      sb.append(this.merchantContractDeatailVos);
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
    if (merchantContractDeatailVos == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'merchantContractDeatailVos' was not present! Struct: " + toString());
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

  private static class ReportMerchantContractDeatailResVoStandardSchemeFactory implements SchemeFactory {
    public ReportMerchantContractDeatailResVoStandardScheme getScheme() {
      return new ReportMerchantContractDeatailResVoStandardScheme();
    }
  }

  private static class ReportMerchantContractDeatailResVoStandardScheme extends StandardScheme<ReportMerchantContractDeatailResVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ReportMerchantContractDeatailResVo struct) throws org.apache.thrift.TException {
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
          case 2: // MERCHANT_CONTRACT_DEATAIL_VOS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list56 = iprot.readListBegin();
                struct.merchantContractDeatailVos = new ArrayList<ReportMerchantContractDeatailVo>(_list56.size);
                ReportMerchantContractDeatailVo _elem57;
                for (int _i58 = 0; _i58 < _list56.size; ++_i58)
                {
                  _elem57 = new ReportMerchantContractDeatailVo();
                  _elem57.read(iprot);
                  struct.merchantContractDeatailVos.add(_elem57);
                }
                iprot.readListEnd();
              }
              struct.setMerchantContractDeatailVosIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ReportMerchantContractDeatailResVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.resultVo != null) {
        oprot.writeFieldBegin(RESULT_VO_FIELD_DESC);
        struct.resultVo.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.merchantContractDeatailVos != null) {
        oprot.writeFieldBegin(MERCHANT_CONTRACT_DEATAIL_VOS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.merchantContractDeatailVos.size()));
          for (ReportMerchantContractDeatailVo _iter59 : struct.merchantContractDeatailVos)
          {
            _iter59.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ReportMerchantContractDeatailResVoTupleSchemeFactory implements SchemeFactory {
    public ReportMerchantContractDeatailResVoTupleScheme getScheme() {
      return new ReportMerchantContractDeatailResVoTupleScheme();
    }
  }

  private static class ReportMerchantContractDeatailResVoTupleScheme extends TupleScheme<ReportMerchantContractDeatailResVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ReportMerchantContractDeatailResVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      struct.resultVo.write(oprot);
      {
        oprot.writeI32(struct.merchantContractDeatailVos.size());
        for (ReportMerchantContractDeatailVo _iter60 : struct.merchantContractDeatailVos)
        {
          _iter60.write(oprot);
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ReportMerchantContractDeatailResVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.resultVo = new com.froad.thrift.vo.ResultVo();
      struct.resultVo.read(iprot);
      struct.setResultVoIsSet(true);
      {
        org.apache.thrift.protocol.TList _list61 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
        struct.merchantContractDeatailVos = new ArrayList<ReportMerchantContractDeatailVo>(_list61.size);
        ReportMerchantContractDeatailVo _elem62;
        for (int _i63 = 0; _i63 < _list61.size; ++_i63)
        {
          _elem62 = new ReportMerchantContractDeatailVo();
          _elem62.read(iprot);
          struct.merchantContractDeatailVos.add(_elem62);
        }
      }
      struct.setMerchantContractDeatailVosIsSet(true);
    }
  }

}
