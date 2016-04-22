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
 * VIP规则列表vo
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class VipProductPageVoRes implements org.apache.thrift.TBase<VipProductPageVoRes, VipProductPageVoRes._Fields>, java.io.Serializable, Cloneable, Comparable<VipProductPageVoRes> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("VipProductPageVoRes");

  private static final org.apache.thrift.protocol.TField RESULT_VO_FIELD_DESC = new org.apache.thrift.protocol.TField("resultVo", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("page", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField VIP_PRODUCT_VOS_FIELD_DESC = new org.apache.thrift.protocol.TField("vipProductVos", org.apache.thrift.protocol.TType.LIST, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new VipProductPageVoResStandardSchemeFactory());
    schemes.put(TupleScheme.class, new VipProductPageVoResTupleSchemeFactory());
  }

  public com.froad.thrift.vo.ResultVo resultVo; // required
  public com.froad.thrift.vo.PageVo page; // required
  public List<VipProductVo> vipProductVos; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    RESULT_VO((short)1, "resultVo"),
    PAGE((short)2, "page"),
    VIP_PRODUCT_VOS((short)3, "vipProductVos");

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
        case 2: // PAGE
          return PAGE;
        case 3: // VIP_PRODUCT_VOS
          return VIP_PRODUCT_VOS;
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
    tmpMap.put(_Fields.PAGE, new org.apache.thrift.meta_data.FieldMetaData("page", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.PageVo.class)));
    tmpMap.put(_Fields.VIP_PRODUCT_VOS, new org.apache.thrift.meta_data.FieldMetaData("vipProductVos", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT            , "VipProductVo"))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(VipProductPageVoRes.class, metaDataMap);
  }

  public VipProductPageVoRes() {
  }

  public VipProductPageVoRes(
    com.froad.thrift.vo.ResultVo resultVo,
    com.froad.thrift.vo.PageVo page,
    List<VipProductVo> vipProductVos)
  {
    this();
    this.resultVo = resultVo;
    this.page = page;
    this.vipProductVos = vipProductVos;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public VipProductPageVoRes(VipProductPageVoRes other) {
    if (other.isSetResultVo()) {
      this.resultVo = new com.froad.thrift.vo.ResultVo(other.resultVo);
    }
    if (other.isSetPage()) {
      this.page = new com.froad.thrift.vo.PageVo(other.page);
    }
    if (other.isSetVipProductVos()) {
      List<VipProductVo> __this__vipProductVos = new ArrayList<VipProductVo>(other.vipProductVos.size());
      for (VipProductVo other_element : other.vipProductVos) {
        __this__vipProductVos.add(other_element);
      }
      this.vipProductVos = __this__vipProductVos;
    }
  }

  public VipProductPageVoRes deepCopy() {
    return new VipProductPageVoRes(this);
  }

  @Override
  public void clear() {
    this.resultVo = null;
    this.page = null;
    this.vipProductVos = null;
  }

  public com.froad.thrift.vo.ResultVo getResultVo() {
    return this.resultVo;
  }

  public VipProductPageVoRes setResultVo(com.froad.thrift.vo.ResultVo resultVo) {
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

  public com.froad.thrift.vo.PageVo getPage() {
    return this.page;
  }

  public VipProductPageVoRes setPage(com.froad.thrift.vo.PageVo page) {
    this.page = page;
    return this;
  }

  public void unsetPage() {
    this.page = null;
  }

  /** Returns true if field page is set (has been assigned a value) and false otherwise */
  public boolean isSetPage() {
    return this.page != null;
  }

  public void setPageIsSet(boolean value) {
    if (!value) {
      this.page = null;
    }
  }

  public int getVipProductVosSize() {
    return (this.vipProductVos == null) ? 0 : this.vipProductVos.size();
  }

  public java.util.Iterator<VipProductVo> getVipProductVosIterator() {
    return (this.vipProductVos == null) ? null : this.vipProductVos.iterator();
  }

  public void addToVipProductVos(VipProductVo elem) {
    if (this.vipProductVos == null) {
      this.vipProductVos = new ArrayList<VipProductVo>();
    }
    this.vipProductVos.add(elem);
  }

  public List<VipProductVo> getVipProductVos() {
    return this.vipProductVos;
  }

  public VipProductPageVoRes setVipProductVos(List<VipProductVo> vipProductVos) {
    this.vipProductVos = vipProductVos;
    return this;
  }

  public void unsetVipProductVos() {
    this.vipProductVos = null;
  }

  /** Returns true if field vipProductVos is set (has been assigned a value) and false otherwise */
  public boolean isSetVipProductVos() {
    return this.vipProductVos != null;
  }

  public void setVipProductVosIsSet(boolean value) {
    if (!value) {
      this.vipProductVos = null;
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

    case PAGE:
      if (value == null) {
        unsetPage();
      } else {
        setPage((com.froad.thrift.vo.PageVo)value);
      }
      break;

    case VIP_PRODUCT_VOS:
      if (value == null) {
        unsetVipProductVos();
      } else {
        setVipProductVos((List<VipProductVo>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RESULT_VO:
      return getResultVo();

    case PAGE:
      return getPage();

    case VIP_PRODUCT_VOS:
      return getVipProductVos();

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
    case PAGE:
      return isSetPage();
    case VIP_PRODUCT_VOS:
      return isSetVipProductVos();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof VipProductPageVoRes)
      return this.equals((VipProductPageVoRes)that);
    return false;
  }

  public boolean equals(VipProductPageVoRes that) {
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

    boolean this_present_page = true && this.isSetPage();
    boolean that_present_page = true && that.isSetPage();
    if (this_present_page || that_present_page) {
      if (!(this_present_page && that_present_page))
        return false;
      if (!this.page.equals(that.page))
        return false;
    }

    boolean this_present_vipProductVos = true && this.isSetVipProductVos();
    boolean that_present_vipProductVos = true && that.isSetVipProductVos();
    if (this_present_vipProductVos || that_present_vipProductVos) {
      if (!(this_present_vipProductVos && that_present_vipProductVos))
        return false;
      if (!this.vipProductVos.equals(that.vipProductVos))
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

    boolean present_page = true && (isSetPage());
    list.add(present_page);
    if (present_page)
      list.add(page);

    boolean present_vipProductVos = true && (isSetVipProductVos());
    list.add(present_vipProductVos);
    if (present_vipProductVos)
      list.add(vipProductVos);

    return list.hashCode();
  }

  @Override
  public int compareTo(VipProductPageVoRes other) {
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
    lastComparison = Boolean.valueOf(isSetPage()).compareTo(other.isSetPage());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPage()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.page, other.page);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetVipProductVos()).compareTo(other.isSetVipProductVos());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVipProductVos()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.vipProductVos, other.vipProductVos);
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
    StringBuilder sb = new StringBuilder("VipProductPageVoRes(");
    boolean first = true;

    sb.append("resultVo:");
    if (this.resultVo == null) {
      sb.append("null");
    } else {
      sb.append(this.resultVo);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("page:");
    if (this.page == null) {
      sb.append("null");
    } else {
      sb.append(this.page);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("vipProductVos:");
    if (this.vipProductVos == null) {
      sb.append("null");
    } else {
      sb.append(this.vipProductVos);
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
    if (page != null) {
      page.validate();
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

  private static class VipProductPageVoResStandardSchemeFactory implements SchemeFactory {
    public VipProductPageVoResStandardScheme getScheme() {
      return new VipProductPageVoResStandardScheme();
    }
  }

  private static class VipProductPageVoResStandardScheme extends StandardScheme<VipProductPageVoRes> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, VipProductPageVoRes struct) throws org.apache.thrift.TException {
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
          case 2: // PAGE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.page = new com.froad.thrift.vo.PageVo();
              struct.page.read(iprot);
              struct.setPageIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // VIP_PRODUCT_VOS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list152 = iprot.readListBegin();
                struct.vipProductVos = new ArrayList<VipProductVo>(_list152.size);
                VipProductVo _elem153;
                for (int _i154 = 0; _i154 < _list152.size; ++_i154)
                {
                  _elem153 = new VipProductVo();
                  _elem153.read(iprot);
                  struct.vipProductVos.add(_elem153);
                }
                iprot.readListEnd();
              }
              struct.setVipProductVosIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, VipProductPageVoRes struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.resultVo != null) {
        oprot.writeFieldBegin(RESULT_VO_FIELD_DESC);
        struct.resultVo.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.page != null) {
        oprot.writeFieldBegin(PAGE_FIELD_DESC);
        struct.page.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.vipProductVos != null) {
        oprot.writeFieldBegin(VIP_PRODUCT_VOS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.vipProductVos.size()));
          for (VipProductVo _iter155 : struct.vipProductVos)
          {
            _iter155.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class VipProductPageVoResTupleSchemeFactory implements SchemeFactory {
    public VipProductPageVoResTupleScheme getScheme() {
      return new VipProductPageVoResTupleScheme();
    }
  }

  private static class VipProductPageVoResTupleScheme extends TupleScheme<VipProductPageVoRes> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, VipProductPageVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetResultVo()) {
        optionals.set(0);
      }
      if (struct.isSetPage()) {
        optionals.set(1);
      }
      if (struct.isSetVipProductVos()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetResultVo()) {
        struct.resultVo.write(oprot);
      }
      if (struct.isSetPage()) {
        struct.page.write(oprot);
      }
      if (struct.isSetVipProductVos()) {
        {
          oprot.writeI32(struct.vipProductVos.size());
          for (VipProductVo _iter156 : struct.vipProductVos)
          {
            _iter156.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, VipProductPageVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.resultVo = new com.froad.thrift.vo.ResultVo();
        struct.resultVo.read(iprot);
        struct.setResultVoIsSet(true);
      }
      if (incoming.get(1)) {
        struct.page = new com.froad.thrift.vo.PageVo();
        struct.page.read(iprot);
        struct.setPageIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TList _list157 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.vipProductVos = new ArrayList<VipProductVo>(_list157.size);
          VipProductVo _elem158;
          for (int _i159 = 0; _i159 < _list157.size; ++_i159)
          {
            _elem158 = new VipProductVo();
            _elem158.read(iprot);
            struct.vipProductVos.add(_elem158);
          }
        }
        struct.setVipProductVosIsSet(true);
      }
    }
  }

}
