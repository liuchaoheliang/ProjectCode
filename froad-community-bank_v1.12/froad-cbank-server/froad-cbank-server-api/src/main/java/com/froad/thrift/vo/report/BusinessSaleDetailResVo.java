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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class BusinessSaleDetailResVo implements org.apache.thrift.TBase<BusinessSaleDetailResVo, BusinessSaleDetailResVo._Fields>, java.io.Serializable, Cloneable, Comparable<BusinessSaleDetailResVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("BusinessSaleDetailResVo");

  private static final org.apache.thrift.protocol.TField RESULT_VO_FIELD_DESC = new org.apache.thrift.protocol.TField("resultVo", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField BUSINESS_SALE_DETAIL_VOS_FIELD_DESC = new org.apache.thrift.protocol.TField("businessSaleDetailVos", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new BusinessSaleDetailResVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new BusinessSaleDetailResVoTupleSchemeFactory());
  }

  /**
   * 返回结果 *
   */
  public com.froad.thrift.vo.ResultVo resultVo; // required
  /**
   * 业务类型销售统计详情集合 *
   */
  public List<BusinessSaleDetailVo> businessSaleDetailVos; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 返回结果 *
     */
    RESULT_VO((short)1, "resultVo"),
    /**
     * 业务类型销售统计详情集合 *
     */
    BUSINESS_SALE_DETAIL_VOS((short)2, "businessSaleDetailVos");

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
        case 2: // BUSINESS_SALE_DETAIL_VOS
          return BUSINESS_SALE_DETAIL_VOS;
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
    tmpMap.put(_Fields.BUSINESS_SALE_DETAIL_VOS, new org.apache.thrift.meta_data.FieldMetaData("businessSaleDetailVos", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, BusinessSaleDetailVo.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(BusinessSaleDetailResVo.class, metaDataMap);
  }

  public BusinessSaleDetailResVo() {
  }

  public BusinessSaleDetailResVo(
    com.froad.thrift.vo.ResultVo resultVo,
    List<BusinessSaleDetailVo> businessSaleDetailVos)
  {
    this();
    this.resultVo = resultVo;
    this.businessSaleDetailVos = businessSaleDetailVos;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public BusinessSaleDetailResVo(BusinessSaleDetailResVo other) {
    if (other.isSetResultVo()) {
      this.resultVo = new com.froad.thrift.vo.ResultVo(other.resultVo);
    }
    if (other.isSetBusinessSaleDetailVos()) {
      List<BusinessSaleDetailVo> __this__businessSaleDetailVos = new ArrayList<BusinessSaleDetailVo>(other.businessSaleDetailVos.size());
      for (BusinessSaleDetailVo other_element : other.businessSaleDetailVos) {
        __this__businessSaleDetailVos.add(new BusinessSaleDetailVo(other_element));
      }
      this.businessSaleDetailVos = __this__businessSaleDetailVos;
    }
  }

  public BusinessSaleDetailResVo deepCopy() {
    return new BusinessSaleDetailResVo(this);
  }

  @Override
  public void clear() {
    this.resultVo = null;
    this.businessSaleDetailVos = null;
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
  public BusinessSaleDetailResVo setResultVo(com.froad.thrift.vo.ResultVo resultVo) {
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

  public int getBusinessSaleDetailVosSize() {
    return (this.businessSaleDetailVos == null) ? 0 : this.businessSaleDetailVos.size();
  }

  public java.util.Iterator<BusinessSaleDetailVo> getBusinessSaleDetailVosIterator() {
    return (this.businessSaleDetailVos == null) ? null : this.businessSaleDetailVos.iterator();
  }

  public void addToBusinessSaleDetailVos(BusinessSaleDetailVo elem) {
    if (this.businessSaleDetailVos == null) {
      this.businessSaleDetailVos = new ArrayList<BusinessSaleDetailVo>();
    }
    this.businessSaleDetailVos.add(elem);
  }

  /**
   * 业务类型销售统计详情集合 *
   */
  public List<BusinessSaleDetailVo> getBusinessSaleDetailVos() {
    return this.businessSaleDetailVos;
  }

  /**
   * 业务类型销售统计详情集合 *
   */
  public BusinessSaleDetailResVo setBusinessSaleDetailVos(List<BusinessSaleDetailVo> businessSaleDetailVos) {
    this.businessSaleDetailVos = businessSaleDetailVos;
    return this;
  }

  public void unsetBusinessSaleDetailVos() {
    this.businessSaleDetailVos = null;
  }

  /** Returns true if field businessSaleDetailVos is set (has been assigned a value) and false otherwise */
  public boolean isSetBusinessSaleDetailVos() {
    return this.businessSaleDetailVos != null;
  }

  public void setBusinessSaleDetailVosIsSet(boolean value) {
    if (!value) {
      this.businessSaleDetailVos = null;
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

    case BUSINESS_SALE_DETAIL_VOS:
      if (value == null) {
        unsetBusinessSaleDetailVos();
      } else {
        setBusinessSaleDetailVos((List<BusinessSaleDetailVo>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RESULT_VO:
      return getResultVo();

    case BUSINESS_SALE_DETAIL_VOS:
      return getBusinessSaleDetailVos();

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
    case BUSINESS_SALE_DETAIL_VOS:
      return isSetBusinessSaleDetailVos();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof BusinessSaleDetailResVo)
      return this.equals((BusinessSaleDetailResVo)that);
    return false;
  }

  public boolean equals(BusinessSaleDetailResVo that) {
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

    boolean this_present_businessSaleDetailVos = true && this.isSetBusinessSaleDetailVos();
    boolean that_present_businessSaleDetailVos = true && that.isSetBusinessSaleDetailVos();
    if (this_present_businessSaleDetailVos || that_present_businessSaleDetailVos) {
      if (!(this_present_businessSaleDetailVos && that_present_businessSaleDetailVos))
        return false;
      if (!this.businessSaleDetailVos.equals(that.businessSaleDetailVos))
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

    boolean present_businessSaleDetailVos = true && (isSetBusinessSaleDetailVos());
    list.add(present_businessSaleDetailVos);
    if (present_businessSaleDetailVos)
      list.add(businessSaleDetailVos);

    return list.hashCode();
  }

  @Override
  public int compareTo(BusinessSaleDetailResVo other) {
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
    lastComparison = Boolean.valueOf(isSetBusinessSaleDetailVos()).compareTo(other.isSetBusinessSaleDetailVos());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBusinessSaleDetailVos()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.businessSaleDetailVos, other.businessSaleDetailVos);
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
    StringBuilder sb = new StringBuilder("BusinessSaleDetailResVo(");
    boolean first = true;

    sb.append("resultVo:");
    if (this.resultVo == null) {
      sb.append("null");
    } else {
      sb.append(this.resultVo);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("businessSaleDetailVos:");
    if (this.businessSaleDetailVos == null) {
      sb.append("null");
    } else {
      sb.append(this.businessSaleDetailVos);
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
    if (businessSaleDetailVos == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'businessSaleDetailVos' was not present! Struct: " + toString());
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

  private static class BusinessSaleDetailResVoStandardSchemeFactory implements SchemeFactory {
    public BusinessSaleDetailResVoStandardScheme getScheme() {
      return new BusinessSaleDetailResVoStandardScheme();
    }
  }

  private static class BusinessSaleDetailResVoStandardScheme extends StandardScheme<BusinessSaleDetailResVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, BusinessSaleDetailResVo struct) throws org.apache.thrift.TException {
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
          case 2: // BUSINESS_SALE_DETAIL_VOS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list144 = iprot.readListBegin();
                struct.businessSaleDetailVos = new ArrayList<BusinessSaleDetailVo>(_list144.size);
                BusinessSaleDetailVo _elem145;
                for (int _i146 = 0; _i146 < _list144.size; ++_i146)
                {
                  _elem145 = new BusinessSaleDetailVo();
                  _elem145.read(iprot);
                  struct.businessSaleDetailVos.add(_elem145);
                }
                iprot.readListEnd();
              }
              struct.setBusinessSaleDetailVosIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, BusinessSaleDetailResVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.resultVo != null) {
        oprot.writeFieldBegin(RESULT_VO_FIELD_DESC);
        struct.resultVo.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.businessSaleDetailVos != null) {
        oprot.writeFieldBegin(BUSINESS_SALE_DETAIL_VOS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.businessSaleDetailVos.size()));
          for (BusinessSaleDetailVo _iter147 : struct.businessSaleDetailVos)
          {
            _iter147.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class BusinessSaleDetailResVoTupleSchemeFactory implements SchemeFactory {
    public BusinessSaleDetailResVoTupleScheme getScheme() {
      return new BusinessSaleDetailResVoTupleScheme();
    }
  }

  private static class BusinessSaleDetailResVoTupleScheme extends TupleScheme<BusinessSaleDetailResVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, BusinessSaleDetailResVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      struct.resultVo.write(oprot);
      {
        oprot.writeI32(struct.businessSaleDetailVos.size());
        for (BusinessSaleDetailVo _iter148 : struct.businessSaleDetailVos)
        {
          _iter148.write(oprot);
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, BusinessSaleDetailResVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.resultVo = new com.froad.thrift.vo.ResultVo();
      struct.resultVo.read(iprot);
      struct.setResultVoIsSet(true);
      {
        org.apache.thrift.protocol.TList _list149 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
        struct.businessSaleDetailVos = new ArrayList<BusinessSaleDetailVo>(_list149.size);
        BusinessSaleDetailVo _elem150;
        for (int _i151 = 0; _i151 < _list149.size; ++_i151)
        {
          _elem150 = new BusinessSaleDetailVo();
          _elem150.read(iprot);
          struct.businessSaleDetailVos.add(_elem150);
        }
      }
      struct.setBusinessSaleDetailVosIsSet(true);
    }
  }

}
