/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.froad.thrift.vo.pointsettlement;

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
 * 积分结算商户汇总响应Vo
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class PointSettlementMerchantResVo implements org.apache.thrift.TBase<PointSettlementMerchantResVo, PointSettlementMerchantResVo._Fields>, java.io.Serializable, Cloneable, Comparable<PointSettlementMerchantResVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("PointSettlementMerchantResVo");

  private static final org.apache.thrift.protocol.TField RESULT_VO_FIELD_DESC = new org.apache.thrift.protocol.TField("resultVo", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField DETAIL_RES_VO_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("detailResVoList", org.apache.thrift.protocol.TType.LIST, (short)2);
  private static final org.apache.thrift.protocol.TField PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("page", org.apache.thrift.protocol.TType.STRUCT, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new PointSettlementMerchantResVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new PointSettlementMerchantResVoTupleSchemeFactory());
  }

  /**
   * 结果信息
   */
  public com.froad.thrift.vo.ResultVo resultVo; // required
  /**
   * 积分结算详情集合
   */
  public List<PointSettlementMerchantDetailResVo> detailResVoList; // required
  /**
   * 分页信息
   */
  public com.froad.thrift.vo.PageVo page; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 结果信息
     */
    RESULT_VO((short)1, "resultVo"),
    /**
     * 积分结算详情集合
     */
    DETAIL_RES_VO_LIST((short)2, "detailResVoList"),
    /**
     * 分页信息
     */
    PAGE((short)3, "page");

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
        case 2: // DETAIL_RES_VO_LIST
          return DETAIL_RES_VO_LIST;
        case 3: // PAGE
          return PAGE;
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
  private static final _Fields optionals[] = {_Fields.PAGE};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.RESULT_VO, new org.apache.thrift.meta_data.FieldMetaData("resultVo", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.ResultVo.class)));
    tmpMap.put(_Fields.DETAIL_RES_VO_LIST, new org.apache.thrift.meta_data.FieldMetaData("detailResVoList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT            , "PointSettlementMerchantDetailResVo"))));
    tmpMap.put(_Fields.PAGE, new org.apache.thrift.meta_data.FieldMetaData("page", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.PageVo.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(PointSettlementMerchantResVo.class, metaDataMap);
  }

  public PointSettlementMerchantResVo() {
  }

  public PointSettlementMerchantResVo(
    com.froad.thrift.vo.ResultVo resultVo,
    List<PointSettlementMerchantDetailResVo> detailResVoList)
  {
    this();
    this.resultVo = resultVo;
    this.detailResVoList = detailResVoList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public PointSettlementMerchantResVo(PointSettlementMerchantResVo other) {
    if (other.isSetResultVo()) {
      this.resultVo = new com.froad.thrift.vo.ResultVo(other.resultVo);
    }
    if (other.isSetDetailResVoList()) {
      List<PointSettlementMerchantDetailResVo> __this__detailResVoList = new ArrayList<PointSettlementMerchantDetailResVo>(other.detailResVoList.size());
      for (PointSettlementMerchantDetailResVo other_element : other.detailResVoList) {
        __this__detailResVoList.add(other_element);
      }
      this.detailResVoList = __this__detailResVoList;
    }
    if (other.isSetPage()) {
      this.page = new com.froad.thrift.vo.PageVo(other.page);
    }
  }

  public PointSettlementMerchantResVo deepCopy() {
    return new PointSettlementMerchantResVo(this);
  }

  @Override
  public void clear() {
    this.resultVo = null;
    this.detailResVoList = null;
    this.page = null;
  }

  /**
   * 结果信息
   */
  public com.froad.thrift.vo.ResultVo getResultVo() {
    return this.resultVo;
  }

  /**
   * 结果信息
   */
  public PointSettlementMerchantResVo setResultVo(com.froad.thrift.vo.ResultVo resultVo) {
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

  public int getDetailResVoListSize() {
    return (this.detailResVoList == null) ? 0 : this.detailResVoList.size();
  }

  public java.util.Iterator<PointSettlementMerchantDetailResVo> getDetailResVoListIterator() {
    return (this.detailResVoList == null) ? null : this.detailResVoList.iterator();
  }

  public void addToDetailResVoList(PointSettlementMerchantDetailResVo elem) {
    if (this.detailResVoList == null) {
      this.detailResVoList = new ArrayList<PointSettlementMerchantDetailResVo>();
    }
    this.detailResVoList.add(elem);
  }

  /**
   * 积分结算详情集合
   */
  public List<PointSettlementMerchantDetailResVo> getDetailResVoList() {
    return this.detailResVoList;
  }

  /**
   * 积分结算详情集合
   */
  public PointSettlementMerchantResVo setDetailResVoList(List<PointSettlementMerchantDetailResVo> detailResVoList) {
    this.detailResVoList = detailResVoList;
    return this;
  }

  public void unsetDetailResVoList() {
    this.detailResVoList = null;
  }

  /** Returns true if field detailResVoList is set (has been assigned a value) and false otherwise */
  public boolean isSetDetailResVoList() {
    return this.detailResVoList != null;
  }

  public void setDetailResVoListIsSet(boolean value) {
    if (!value) {
      this.detailResVoList = null;
    }
  }

  /**
   * 分页信息
   */
  public com.froad.thrift.vo.PageVo getPage() {
    return this.page;
  }

  /**
   * 分页信息
   */
  public PointSettlementMerchantResVo setPage(com.froad.thrift.vo.PageVo page) {
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

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case RESULT_VO:
      if (value == null) {
        unsetResultVo();
      } else {
        setResultVo((com.froad.thrift.vo.ResultVo)value);
      }
      break;

    case DETAIL_RES_VO_LIST:
      if (value == null) {
        unsetDetailResVoList();
      } else {
        setDetailResVoList((List<PointSettlementMerchantDetailResVo>)value);
      }
      break;

    case PAGE:
      if (value == null) {
        unsetPage();
      } else {
        setPage((com.froad.thrift.vo.PageVo)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RESULT_VO:
      return getResultVo();

    case DETAIL_RES_VO_LIST:
      return getDetailResVoList();

    case PAGE:
      return getPage();

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
    case DETAIL_RES_VO_LIST:
      return isSetDetailResVoList();
    case PAGE:
      return isSetPage();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof PointSettlementMerchantResVo)
      return this.equals((PointSettlementMerchantResVo)that);
    return false;
  }

  public boolean equals(PointSettlementMerchantResVo that) {
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

    boolean this_present_detailResVoList = true && this.isSetDetailResVoList();
    boolean that_present_detailResVoList = true && that.isSetDetailResVoList();
    if (this_present_detailResVoList || that_present_detailResVoList) {
      if (!(this_present_detailResVoList && that_present_detailResVoList))
        return false;
      if (!this.detailResVoList.equals(that.detailResVoList))
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

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_resultVo = true && (isSetResultVo());
    list.add(present_resultVo);
    if (present_resultVo)
      list.add(resultVo);

    boolean present_detailResVoList = true && (isSetDetailResVoList());
    list.add(present_detailResVoList);
    if (present_detailResVoList)
      list.add(detailResVoList);

    boolean present_page = true && (isSetPage());
    list.add(present_page);
    if (present_page)
      list.add(page);

    return list.hashCode();
  }

  @Override
  public int compareTo(PointSettlementMerchantResVo other) {
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
    lastComparison = Boolean.valueOf(isSetDetailResVoList()).compareTo(other.isSetDetailResVoList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDetailResVoList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.detailResVoList, other.detailResVoList);
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
    StringBuilder sb = new StringBuilder("PointSettlementMerchantResVo(");
    boolean first = true;

    sb.append("resultVo:");
    if (this.resultVo == null) {
      sb.append("null");
    } else {
      sb.append(this.resultVo);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("detailResVoList:");
    if (this.detailResVoList == null) {
      sb.append("null");
    } else {
      sb.append(this.detailResVoList);
    }
    first = false;
    if (isSetPage()) {
      if (!first) sb.append(", ");
      sb.append("page:");
      if (this.page == null) {
        sb.append("null");
      } else {
        sb.append(this.page);
      }
      first = false;
    }
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

  private static class PointSettlementMerchantResVoStandardSchemeFactory implements SchemeFactory {
    public PointSettlementMerchantResVoStandardScheme getScheme() {
      return new PointSettlementMerchantResVoStandardScheme();
    }
  }

  private static class PointSettlementMerchantResVoStandardScheme extends StandardScheme<PointSettlementMerchantResVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, PointSettlementMerchantResVo struct) throws org.apache.thrift.TException {
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
          case 2: // DETAIL_RES_VO_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list8 = iprot.readListBegin();
                struct.detailResVoList = new ArrayList<PointSettlementMerchantDetailResVo>(_list8.size);
                PointSettlementMerchantDetailResVo _elem9;
                for (int _i10 = 0; _i10 < _list8.size; ++_i10)
                {
                  _elem9 = new PointSettlementMerchantDetailResVo();
                  _elem9.read(iprot);
                  struct.detailResVoList.add(_elem9);
                }
                iprot.readListEnd();
              }
              struct.setDetailResVoListIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // PAGE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.page = new com.froad.thrift.vo.PageVo();
              struct.page.read(iprot);
              struct.setPageIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, PointSettlementMerchantResVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.resultVo != null) {
        oprot.writeFieldBegin(RESULT_VO_FIELD_DESC);
        struct.resultVo.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.detailResVoList != null) {
        oprot.writeFieldBegin(DETAIL_RES_VO_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.detailResVoList.size()));
          for (PointSettlementMerchantDetailResVo _iter11 : struct.detailResVoList)
          {
            _iter11.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.page != null) {
        if (struct.isSetPage()) {
          oprot.writeFieldBegin(PAGE_FIELD_DESC);
          struct.page.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class PointSettlementMerchantResVoTupleSchemeFactory implements SchemeFactory {
    public PointSettlementMerchantResVoTupleScheme getScheme() {
      return new PointSettlementMerchantResVoTupleScheme();
    }
  }

  private static class PointSettlementMerchantResVoTupleScheme extends TupleScheme<PointSettlementMerchantResVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, PointSettlementMerchantResVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetResultVo()) {
        optionals.set(0);
      }
      if (struct.isSetDetailResVoList()) {
        optionals.set(1);
      }
      if (struct.isSetPage()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetResultVo()) {
        struct.resultVo.write(oprot);
      }
      if (struct.isSetDetailResVoList()) {
        {
          oprot.writeI32(struct.detailResVoList.size());
          for (PointSettlementMerchantDetailResVo _iter12 : struct.detailResVoList)
          {
            _iter12.write(oprot);
          }
        }
      }
      if (struct.isSetPage()) {
        struct.page.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, PointSettlementMerchantResVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.resultVo = new com.froad.thrift.vo.ResultVo();
        struct.resultVo.read(iprot);
        struct.setResultVoIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list13 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.detailResVoList = new ArrayList<PointSettlementMerchantDetailResVo>(_list13.size);
          PointSettlementMerchantDetailResVo _elem14;
          for (int _i15 = 0; _i15 < _list13.size; ++_i15)
          {
            _elem14 = new PointSettlementMerchantDetailResVo();
            _elem14.read(iprot);
            struct.detailResVoList.add(_elem14);
          }
        }
        struct.setDetailResVoListIsSet(true);
      }
      if (incoming.get(2)) {
        struct.page = new com.froad.thrift.vo.PageVo();
        struct.page.read(iprot);
        struct.setPageIsSet(true);
      }
    }
  }

}
