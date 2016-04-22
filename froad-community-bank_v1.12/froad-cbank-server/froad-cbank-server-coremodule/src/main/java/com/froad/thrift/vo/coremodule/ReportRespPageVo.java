/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.froad.thrift.vo.coremodule;

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
 * 报表信息分页的响应
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2015-12-25")
public class ReportRespPageVo implements org.apache.thrift.TBase<ReportRespPageVo, ReportRespPageVo._Fields>, java.io.Serializable, Cloneable, Comparable<ReportRespPageVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ReportRespPageVo");

  private static final org.apache.thrift.protocol.TField PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("page", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField REPORT_RESP_FIELD_DESC = new org.apache.thrift.protocol.TField("reportResp", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ReportRespPageVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ReportRespPageVoTupleSchemeFactory());
  }

  /**
   * 分页基础信息
   */
  public com.froad.thrift.vo.PageVo page; // required
  /**
   * 信息列表
   */
  public List<ReportRespVo> reportResp; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 分页基础信息
     */
    PAGE((short)1, "page"),
    /**
     * 信息列表
     */
    REPORT_RESP((short)2, "reportResp");

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
        case 1: // PAGE
          return PAGE;
        case 2: // REPORT_RESP
          return REPORT_RESP;
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
    tmpMap.put(_Fields.PAGE, new org.apache.thrift.meta_data.FieldMetaData("page", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.PageVo.class)));
    tmpMap.put(_Fields.REPORT_RESP, new org.apache.thrift.meta_data.FieldMetaData("reportResp", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ReportRespVo.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ReportRespPageVo.class, metaDataMap);
  }

  public ReportRespPageVo() {
  }

  public ReportRespPageVo(
    com.froad.thrift.vo.PageVo page,
    List<ReportRespVo> reportResp)
  {
    this();
    this.page = page;
    this.reportResp = reportResp;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ReportRespPageVo(ReportRespPageVo other) {
    if (other.isSetPage()) {
      this.page = new com.froad.thrift.vo.PageVo(other.page);
    }
    if (other.isSetReportResp()) {
      List<ReportRespVo> __this__reportResp = new ArrayList<ReportRespVo>(other.reportResp.size());
      for (ReportRespVo other_element : other.reportResp) {
        __this__reportResp.add(new ReportRespVo(other_element));
      }
      this.reportResp = __this__reportResp;
    }
  }

  public ReportRespPageVo deepCopy() {
    return new ReportRespPageVo(this);
  }

  @Override
  public void clear() {
    this.page = null;
    this.reportResp = null;
  }

  /**
   * 分页基础信息
   */
  public com.froad.thrift.vo.PageVo getPage() {
    return this.page;
  }

  /**
   * 分页基础信息
   */
  public ReportRespPageVo setPage(com.froad.thrift.vo.PageVo page) {
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

  public int getReportRespSize() {
    return (this.reportResp == null) ? 0 : this.reportResp.size();
  }

  public java.util.Iterator<ReportRespVo> getReportRespIterator() {
    return (this.reportResp == null) ? null : this.reportResp.iterator();
  }

  public void addToReportResp(ReportRespVo elem) {
    if (this.reportResp == null) {
      this.reportResp = new ArrayList<ReportRespVo>();
    }
    this.reportResp.add(elem);
  }

  /**
   * 信息列表
   */
  public List<ReportRespVo> getReportResp() {
    return this.reportResp;
  }

  /**
   * 信息列表
   */
  public ReportRespPageVo setReportResp(List<ReportRespVo> reportResp) {
    this.reportResp = reportResp;
    return this;
  }

  public void unsetReportResp() {
    this.reportResp = null;
  }

  /** Returns true if field reportResp is set (has been assigned a value) and false otherwise */
  public boolean isSetReportResp() {
    return this.reportResp != null;
  }

  public void setReportRespIsSet(boolean value) {
    if (!value) {
      this.reportResp = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PAGE:
      if (value == null) {
        unsetPage();
      } else {
        setPage((com.froad.thrift.vo.PageVo)value);
      }
      break;

    case REPORT_RESP:
      if (value == null) {
        unsetReportResp();
      } else {
        setReportResp((List<ReportRespVo>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PAGE:
      return getPage();

    case REPORT_RESP:
      return getReportResp();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case PAGE:
      return isSetPage();
    case REPORT_RESP:
      return isSetReportResp();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ReportRespPageVo)
      return this.equals((ReportRespPageVo)that);
    return false;
  }

  public boolean equals(ReportRespPageVo that) {
    if (that == null)
      return false;

    boolean this_present_page = true && this.isSetPage();
    boolean that_present_page = true && that.isSetPage();
    if (this_present_page || that_present_page) {
      if (!(this_present_page && that_present_page))
        return false;
      if (!this.page.equals(that.page))
        return false;
    }

    boolean this_present_reportResp = true && this.isSetReportResp();
    boolean that_present_reportResp = true && that.isSetReportResp();
    if (this_present_reportResp || that_present_reportResp) {
      if (!(this_present_reportResp && that_present_reportResp))
        return false;
      if (!this.reportResp.equals(that.reportResp))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_page = true && (isSetPage());
    list.add(present_page);
    if (present_page)
      list.add(page);

    boolean present_reportResp = true && (isSetReportResp());
    list.add(present_reportResp);
    if (present_reportResp)
      list.add(reportResp);

    return list.hashCode();
  }

  @Override
  public int compareTo(ReportRespPageVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

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
    lastComparison = Boolean.valueOf(isSetReportResp()).compareTo(other.isSetReportResp());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetReportResp()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.reportResp, other.reportResp);
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
    StringBuilder sb = new StringBuilder("ReportRespPageVo(");
    boolean first = true;

    sb.append("page:");
    if (this.page == null) {
      sb.append("null");
    } else {
      sb.append(this.page);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("reportResp:");
    if (this.reportResp == null) {
      sb.append("null");
    } else {
      sb.append(this.reportResp);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
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

  private static class ReportRespPageVoStandardSchemeFactory implements SchemeFactory {
    public ReportRespPageVoStandardScheme getScheme() {
      return new ReportRespPageVoStandardScheme();
    }
  }

  private static class ReportRespPageVoStandardScheme extends StandardScheme<ReportRespPageVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ReportRespPageVo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PAGE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.page = new com.froad.thrift.vo.PageVo();
              struct.page.read(iprot);
              struct.setPageIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // REPORT_RESP
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.reportResp = new ArrayList<ReportRespVo>(_list0.size);
                ReportRespVo _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = new ReportRespVo();
                  _elem1.read(iprot);
                  struct.reportResp.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setReportRespIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ReportRespPageVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.page != null) {
        oprot.writeFieldBegin(PAGE_FIELD_DESC);
        struct.page.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.reportResp != null) {
        oprot.writeFieldBegin(REPORT_RESP_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.reportResp.size()));
          for (ReportRespVo _iter3 : struct.reportResp)
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

  private static class ReportRespPageVoTupleSchemeFactory implements SchemeFactory {
    public ReportRespPageVoTupleScheme getScheme() {
      return new ReportRespPageVoTupleScheme();
    }
  }

  private static class ReportRespPageVoTupleScheme extends TupleScheme<ReportRespPageVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ReportRespPageVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPage()) {
        optionals.set(0);
      }
      if (struct.isSetReportResp()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetPage()) {
        struct.page.write(oprot);
      }
      if (struct.isSetReportResp()) {
        {
          oprot.writeI32(struct.reportResp.size());
          for (ReportRespVo _iter4 : struct.reportResp)
          {
            _iter4.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ReportRespPageVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.page = new com.froad.thrift.vo.PageVo();
        struct.page.read(iprot);
        struct.setPageIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.reportResp = new ArrayList<ReportRespVo>(_list5.size);
          ReportRespVo _elem6;
          for (int _i7 = 0; _i7 < _list5.size; ++_i7)
          {
            _elem6 = new ReportRespVo();
            _elem6.read(iprot);
            struct.reportResp.add(_elem6);
          }
        }
        struct.setReportRespIsSet(true);
      }
    }
  }

}

