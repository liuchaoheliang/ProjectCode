/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.froad.thrift.vo.payment;

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
 * Boss支付查询ResVo
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2015-8-5")
public class BossPaymentQueryPageVo implements org.apache.thrift.TBase<BossPaymentQueryPageVo, BossPaymentQueryPageVo._Fields>, java.io.Serializable, Cloneable, Comparable<BossPaymentQueryPageVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("BossPaymentQueryPageVo");

  private static final org.apache.thrift.protocol.TField PAGE_VO_FIELD_DESC = new org.apache.thrift.protocol.TField("pageVo", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField RESULT_VO_FIELD_DESC = new org.apache.thrift.protocol.TField("resultVo", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField PAYMENT_QUERY_VOS_FIELD_DESC = new org.apache.thrift.protocol.TField("paymentQueryVos", org.apache.thrift.protocol.TType.LIST, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new BossPaymentQueryPageVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new BossPaymentQueryPageVoTupleSchemeFactory());
  }

  public com.froad.thrift.vo.PageVo pageVo; // required
  public com.froad.thrift.vo.ResultVo resultVo; // required
  public List<BossPaymentQueryVo> paymentQueryVos; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PAGE_VO((short)1, "pageVo"),
    RESULT_VO((short)2, "resultVo"),
    PAYMENT_QUERY_VOS((short)3, "paymentQueryVos");

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
        case 1: // PAGE_VO
          return PAGE_VO;
        case 2: // RESULT_VO
          return RESULT_VO;
        case 3: // PAYMENT_QUERY_VOS
          return PAYMENT_QUERY_VOS;
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
    tmpMap.put(_Fields.PAGE_VO, new org.apache.thrift.meta_data.FieldMetaData("pageVo", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.PageVo.class)));
    tmpMap.put(_Fields.RESULT_VO, new org.apache.thrift.meta_data.FieldMetaData("resultVo", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.ResultVo.class)));
    tmpMap.put(_Fields.PAYMENT_QUERY_VOS, new org.apache.thrift.meta_data.FieldMetaData("paymentQueryVos", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT            , "BossPaymentQueryVo"))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(BossPaymentQueryPageVo.class, metaDataMap);
  }

  public BossPaymentQueryPageVo() {
  }

  public BossPaymentQueryPageVo(
    com.froad.thrift.vo.PageVo pageVo,
    com.froad.thrift.vo.ResultVo resultVo,
    List<BossPaymentQueryVo> paymentQueryVos)
  {
    this();
    this.pageVo = pageVo;
    this.resultVo = resultVo;
    this.paymentQueryVos = paymentQueryVos;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public BossPaymentQueryPageVo(BossPaymentQueryPageVo other) {
    if (other.isSetPageVo()) {
      this.pageVo = new com.froad.thrift.vo.PageVo(other.pageVo);
    }
    if (other.isSetResultVo()) {
      this.resultVo = new com.froad.thrift.vo.ResultVo(other.resultVo);
    }
    if (other.isSetPaymentQueryVos()) {
      List<BossPaymentQueryVo> __this__paymentQueryVos = new ArrayList<BossPaymentQueryVo>(other.paymentQueryVos.size());
      for (BossPaymentQueryVo other_element : other.paymentQueryVos) {
        __this__paymentQueryVos.add(other_element);
      }
      this.paymentQueryVos = __this__paymentQueryVos;
    }
  }

  public BossPaymentQueryPageVo deepCopy() {
    return new BossPaymentQueryPageVo(this);
  }

  @Override
  public void clear() {
    this.pageVo = null;
    this.resultVo = null;
    this.paymentQueryVos = null;
  }

  public com.froad.thrift.vo.PageVo getPageVo() {
    return this.pageVo;
  }

  public BossPaymentQueryPageVo setPageVo(com.froad.thrift.vo.PageVo pageVo) {
    this.pageVo = pageVo;
    return this;
  }

  public void unsetPageVo() {
    this.pageVo = null;
  }

  /** Returns true if field pageVo is set (has been assigned a value) and false otherwise */
  public boolean isSetPageVo() {
    return this.pageVo != null;
  }

  public void setPageVoIsSet(boolean value) {
    if (!value) {
      this.pageVo = null;
    }
  }

  public com.froad.thrift.vo.ResultVo getResultVo() {
    return this.resultVo;
  }

  public BossPaymentQueryPageVo setResultVo(com.froad.thrift.vo.ResultVo resultVo) {
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

  public int getPaymentQueryVosSize() {
    return (this.paymentQueryVos == null) ? 0 : this.paymentQueryVos.size();
  }

  public java.util.Iterator<BossPaymentQueryVo> getPaymentQueryVosIterator() {
    return (this.paymentQueryVos == null) ? null : this.paymentQueryVos.iterator();
  }

  public void addToPaymentQueryVos(BossPaymentQueryVo elem) {
    if (this.paymentQueryVos == null) {
      this.paymentQueryVos = new ArrayList<BossPaymentQueryVo>();
    }
    this.paymentQueryVos.add(elem);
  }

  public List<BossPaymentQueryVo> getPaymentQueryVos() {
    return this.paymentQueryVos;
  }

  public BossPaymentQueryPageVo setPaymentQueryVos(List<BossPaymentQueryVo> paymentQueryVos) {
    this.paymentQueryVos = paymentQueryVos;
    return this;
  }

  public void unsetPaymentQueryVos() {
    this.paymentQueryVos = null;
  }

  /** Returns true if field paymentQueryVos is set (has been assigned a value) and false otherwise */
  public boolean isSetPaymentQueryVos() {
    return this.paymentQueryVos != null;
  }

  public void setPaymentQueryVosIsSet(boolean value) {
    if (!value) {
      this.paymentQueryVos = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PAGE_VO:
      if (value == null) {
        unsetPageVo();
      } else {
        setPageVo((com.froad.thrift.vo.PageVo)value);
      }
      break;

    case RESULT_VO:
      if (value == null) {
        unsetResultVo();
      } else {
        setResultVo((com.froad.thrift.vo.ResultVo)value);
      }
      break;

    case PAYMENT_QUERY_VOS:
      if (value == null) {
        unsetPaymentQueryVos();
      } else {
        setPaymentQueryVos((List<BossPaymentQueryVo>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PAGE_VO:
      return getPageVo();

    case RESULT_VO:
      return getResultVo();

    case PAYMENT_QUERY_VOS:
      return getPaymentQueryVos();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case PAGE_VO:
      return isSetPageVo();
    case RESULT_VO:
      return isSetResultVo();
    case PAYMENT_QUERY_VOS:
      return isSetPaymentQueryVos();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof BossPaymentQueryPageVo)
      return this.equals((BossPaymentQueryPageVo)that);
    return false;
  }

  public boolean equals(BossPaymentQueryPageVo that) {
    if (that == null)
      return false;

    boolean this_present_pageVo = true && this.isSetPageVo();
    boolean that_present_pageVo = true && that.isSetPageVo();
    if (this_present_pageVo || that_present_pageVo) {
      if (!(this_present_pageVo && that_present_pageVo))
        return false;
      if (!this.pageVo.equals(that.pageVo))
        return false;
    }

    boolean this_present_resultVo = true && this.isSetResultVo();
    boolean that_present_resultVo = true && that.isSetResultVo();
    if (this_present_resultVo || that_present_resultVo) {
      if (!(this_present_resultVo && that_present_resultVo))
        return false;
      if (!this.resultVo.equals(that.resultVo))
        return false;
    }

    boolean this_present_paymentQueryVos = true && this.isSetPaymentQueryVos();
    boolean that_present_paymentQueryVos = true && that.isSetPaymentQueryVos();
    if (this_present_paymentQueryVos || that_present_paymentQueryVos) {
      if (!(this_present_paymentQueryVos && that_present_paymentQueryVos))
        return false;
      if (!this.paymentQueryVos.equals(that.paymentQueryVos))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_pageVo = true && (isSetPageVo());
    list.add(present_pageVo);
    if (present_pageVo)
      list.add(pageVo);

    boolean present_resultVo = true && (isSetResultVo());
    list.add(present_resultVo);
    if (present_resultVo)
      list.add(resultVo);

    boolean present_paymentQueryVos = true && (isSetPaymentQueryVos());
    list.add(present_paymentQueryVos);
    if (present_paymentQueryVos)
      list.add(paymentQueryVos);

    return list.hashCode();
  }

  @Override
  public int compareTo(BossPaymentQueryPageVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetPageVo()).compareTo(other.isSetPageVo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPageVo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.pageVo, other.pageVo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
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
    lastComparison = Boolean.valueOf(isSetPaymentQueryVos()).compareTo(other.isSetPaymentQueryVos());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPaymentQueryVos()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.paymentQueryVos, other.paymentQueryVos);
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
    StringBuilder sb = new StringBuilder("BossPaymentQueryPageVo(");
    boolean first = true;

    sb.append("pageVo:");
    if (this.pageVo == null) {
      sb.append("null");
    } else {
      sb.append(this.pageVo);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("resultVo:");
    if (this.resultVo == null) {
      sb.append("null");
    } else {
      sb.append(this.resultVo);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("paymentQueryVos:");
    if (this.paymentQueryVos == null) {
      sb.append("null");
    } else {
      sb.append(this.paymentQueryVos);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (pageVo != null) {
      pageVo.validate();
    }
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

  private static class BossPaymentQueryPageVoStandardSchemeFactory implements SchemeFactory {
    public BossPaymentQueryPageVoStandardScheme getScheme() {
      return new BossPaymentQueryPageVoStandardScheme();
    }
  }

  private static class BossPaymentQueryPageVoStandardScheme extends StandardScheme<BossPaymentQueryPageVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, BossPaymentQueryPageVo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PAGE_VO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.pageVo = new com.froad.thrift.vo.PageVo();
              struct.pageVo.read(iprot);
              struct.setPageVoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // RESULT_VO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.resultVo = new com.froad.thrift.vo.ResultVo();
              struct.resultVo.read(iprot);
              struct.setResultVoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // PAYMENT_QUERY_VOS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.paymentQueryVos = new ArrayList<BossPaymentQueryVo>(_list0.size);
                BossPaymentQueryVo _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = new BossPaymentQueryVo();
                  _elem1.read(iprot);
                  struct.paymentQueryVos.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setPaymentQueryVosIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, BossPaymentQueryPageVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.pageVo != null) {
        oprot.writeFieldBegin(PAGE_VO_FIELD_DESC);
        struct.pageVo.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.resultVo != null) {
        oprot.writeFieldBegin(RESULT_VO_FIELD_DESC);
        struct.resultVo.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.paymentQueryVos != null) {
        oprot.writeFieldBegin(PAYMENT_QUERY_VOS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.paymentQueryVos.size()));
          for (BossPaymentQueryVo _iter3 : struct.paymentQueryVos)
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

  private static class BossPaymentQueryPageVoTupleSchemeFactory implements SchemeFactory {
    public BossPaymentQueryPageVoTupleScheme getScheme() {
      return new BossPaymentQueryPageVoTupleScheme();
    }
  }

  private static class BossPaymentQueryPageVoTupleScheme extends TupleScheme<BossPaymentQueryPageVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, BossPaymentQueryPageVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPageVo()) {
        optionals.set(0);
      }
      if (struct.isSetResultVo()) {
        optionals.set(1);
      }
      if (struct.isSetPaymentQueryVos()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetPageVo()) {
        struct.pageVo.write(oprot);
      }
      if (struct.isSetResultVo()) {
        struct.resultVo.write(oprot);
      }
      if (struct.isSetPaymentQueryVos()) {
        {
          oprot.writeI32(struct.paymentQueryVos.size());
          for (BossPaymentQueryVo _iter4 : struct.paymentQueryVos)
          {
            _iter4.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, BossPaymentQueryPageVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.pageVo = new com.froad.thrift.vo.PageVo();
        struct.pageVo.read(iprot);
        struct.setPageVoIsSet(true);
      }
      if (incoming.get(1)) {
        struct.resultVo = new com.froad.thrift.vo.ResultVo();
        struct.resultVo.read(iprot);
        struct.setResultVoIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.paymentQueryVos = new ArrayList<BossPaymentQueryVo>(_list5.size);
          BossPaymentQueryVo _elem6;
          for (int _i7 = 0; _i7 < _list5.size; ++_i7)
          {
            _elem6 = new BossPaymentQueryVo();
            _elem6.read(iprot);
            struct.paymentQueryVos.add(_elem6);
          }
        }
        struct.setPaymentQueryVosIsSet(true);
      }
    }
  }

}

