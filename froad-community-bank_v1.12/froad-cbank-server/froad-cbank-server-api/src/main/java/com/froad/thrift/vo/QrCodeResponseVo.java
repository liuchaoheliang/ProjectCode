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
 * 获取二维码响应对象
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class QrCodeResponseVo implements org.apache.thrift.TBase<QrCodeResponseVo, QrCodeResponseVo._Fields>, java.io.Serializable, Cloneable, Comparable<QrCodeResponseVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("QrCodeResponseVo");

  private static final org.apache.thrift.protocol.TField URL_FIELD_DESC = new org.apache.thrift.protocol.TField("url", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField RESULT_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("resultCode", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField RESULT_DESC_FIELD_DESC = new org.apache.thrift.protocol.TField("resultDesc", org.apache.thrift.protocol.TType.STRING, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new QrCodeResponseVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new QrCodeResponseVoTupleSchemeFactory());
  }

  public String url; // required
  public String resultCode; // required
  public String resultDesc; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    URL((short)1, "url"),
    RESULT_CODE((short)2, "resultCode"),
    RESULT_DESC((short)3, "resultDesc");

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
        case 1: // URL
          return URL;
        case 2: // RESULT_CODE
          return RESULT_CODE;
        case 3: // RESULT_DESC
          return RESULT_DESC;
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
    tmpMap.put(_Fields.URL, new org.apache.thrift.meta_data.FieldMetaData("url", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.RESULT_CODE, new org.apache.thrift.meta_data.FieldMetaData("resultCode", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.RESULT_DESC, new org.apache.thrift.meta_data.FieldMetaData("resultDesc", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(QrCodeResponseVo.class, metaDataMap);
  }

  public QrCodeResponseVo() {
  }

  public QrCodeResponseVo(
    String url,
    String resultCode,
    String resultDesc)
  {
    this();
    this.url = url;
    this.resultCode = resultCode;
    this.resultDesc = resultDesc;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public QrCodeResponseVo(QrCodeResponseVo other) {
    if (other.isSetUrl()) {
      this.url = other.url;
    }
    if (other.isSetResultCode()) {
      this.resultCode = other.resultCode;
    }
    if (other.isSetResultDesc()) {
      this.resultDesc = other.resultDesc;
    }
  }

  public QrCodeResponseVo deepCopy() {
    return new QrCodeResponseVo(this);
  }

  @Override
  public void clear() {
    this.url = null;
    this.resultCode = null;
    this.resultDesc = null;
  }

  public String getUrl() {
    return this.url;
  }

  public QrCodeResponseVo setUrl(String url) {
    this.url = url;
    return this;
  }

  public void unsetUrl() {
    this.url = null;
  }

  /** Returns true if field url is set (has been assigned a value) and false otherwise */
  public boolean isSetUrl() {
    return this.url != null;
  }

  public void setUrlIsSet(boolean value) {
    if (!value) {
      this.url = null;
    }
  }

  public String getResultCode() {
    return this.resultCode;
  }

  public QrCodeResponseVo setResultCode(String resultCode) {
    this.resultCode = resultCode;
    return this;
  }

  public void unsetResultCode() {
    this.resultCode = null;
  }

  /** Returns true if field resultCode is set (has been assigned a value) and false otherwise */
  public boolean isSetResultCode() {
    return this.resultCode != null;
  }

  public void setResultCodeIsSet(boolean value) {
    if (!value) {
      this.resultCode = null;
    }
  }

  public String getResultDesc() {
    return this.resultDesc;
  }

  public QrCodeResponseVo setResultDesc(String resultDesc) {
    this.resultDesc = resultDesc;
    return this;
  }

  public void unsetResultDesc() {
    this.resultDesc = null;
  }

  /** Returns true if field resultDesc is set (has been assigned a value) and false otherwise */
  public boolean isSetResultDesc() {
    return this.resultDesc != null;
  }

  public void setResultDescIsSet(boolean value) {
    if (!value) {
      this.resultDesc = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case URL:
      if (value == null) {
        unsetUrl();
      } else {
        setUrl((String)value);
      }
      break;

    case RESULT_CODE:
      if (value == null) {
        unsetResultCode();
      } else {
        setResultCode((String)value);
      }
      break;

    case RESULT_DESC:
      if (value == null) {
        unsetResultDesc();
      } else {
        setResultDesc((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case URL:
      return getUrl();

    case RESULT_CODE:
      return getResultCode();

    case RESULT_DESC:
      return getResultDesc();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case URL:
      return isSetUrl();
    case RESULT_CODE:
      return isSetResultCode();
    case RESULT_DESC:
      return isSetResultDesc();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof QrCodeResponseVo)
      return this.equals((QrCodeResponseVo)that);
    return false;
  }

  public boolean equals(QrCodeResponseVo that) {
    if (that == null)
      return false;

    boolean this_present_url = true && this.isSetUrl();
    boolean that_present_url = true && that.isSetUrl();
    if (this_present_url || that_present_url) {
      if (!(this_present_url && that_present_url))
        return false;
      if (!this.url.equals(that.url))
        return false;
    }

    boolean this_present_resultCode = true && this.isSetResultCode();
    boolean that_present_resultCode = true && that.isSetResultCode();
    if (this_present_resultCode || that_present_resultCode) {
      if (!(this_present_resultCode && that_present_resultCode))
        return false;
      if (!this.resultCode.equals(that.resultCode))
        return false;
    }

    boolean this_present_resultDesc = true && this.isSetResultDesc();
    boolean that_present_resultDesc = true && that.isSetResultDesc();
    if (this_present_resultDesc || that_present_resultDesc) {
      if (!(this_present_resultDesc && that_present_resultDesc))
        return false;
      if (!this.resultDesc.equals(that.resultDesc))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_url = true && (isSetUrl());
    list.add(present_url);
    if (present_url)
      list.add(url);

    boolean present_resultCode = true && (isSetResultCode());
    list.add(present_resultCode);
    if (present_resultCode)
      list.add(resultCode);

    boolean present_resultDesc = true && (isSetResultDesc());
    list.add(present_resultDesc);
    if (present_resultDesc)
      list.add(resultDesc);

    return list.hashCode();
  }

  @Override
  public int compareTo(QrCodeResponseVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetUrl()).compareTo(other.isSetUrl());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUrl()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.url, other.url);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetResultCode()).compareTo(other.isSetResultCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetResultCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.resultCode, other.resultCode);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetResultDesc()).compareTo(other.isSetResultDesc());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetResultDesc()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.resultDesc, other.resultDesc);
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
    StringBuilder sb = new StringBuilder("QrCodeResponseVo(");
    boolean first = true;

    sb.append("url:");
    if (this.url == null) {
      sb.append("null");
    } else {
      sb.append(this.url);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("resultCode:");
    if (this.resultCode == null) {
      sb.append("null");
    } else {
      sb.append(this.resultCode);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("resultDesc:");
    if (this.resultDesc == null) {
      sb.append("null");
    } else {
      sb.append(this.resultDesc);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
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

  private static class QrCodeResponseVoStandardSchemeFactory implements SchemeFactory {
    public QrCodeResponseVoStandardScheme getScheme() {
      return new QrCodeResponseVoStandardScheme();
    }
  }

  private static class QrCodeResponseVoStandardScheme extends StandardScheme<QrCodeResponseVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, QrCodeResponseVo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // URL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.url = iprot.readString();
              struct.setUrlIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // RESULT_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.resultCode = iprot.readString();
              struct.setResultCodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // RESULT_DESC
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.resultDesc = iprot.readString();
              struct.setResultDescIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, QrCodeResponseVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.url != null) {
        oprot.writeFieldBegin(URL_FIELD_DESC);
        oprot.writeString(struct.url);
        oprot.writeFieldEnd();
      }
      if (struct.resultCode != null) {
        oprot.writeFieldBegin(RESULT_CODE_FIELD_DESC);
        oprot.writeString(struct.resultCode);
        oprot.writeFieldEnd();
      }
      if (struct.resultDesc != null) {
        oprot.writeFieldBegin(RESULT_DESC_FIELD_DESC);
        oprot.writeString(struct.resultDesc);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class QrCodeResponseVoTupleSchemeFactory implements SchemeFactory {
    public QrCodeResponseVoTupleScheme getScheme() {
      return new QrCodeResponseVoTupleScheme();
    }
  }

  private static class QrCodeResponseVoTupleScheme extends TupleScheme<QrCodeResponseVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, QrCodeResponseVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetUrl()) {
        optionals.set(0);
      }
      if (struct.isSetResultCode()) {
        optionals.set(1);
      }
      if (struct.isSetResultDesc()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetUrl()) {
        oprot.writeString(struct.url);
      }
      if (struct.isSetResultCode()) {
        oprot.writeString(struct.resultCode);
      }
      if (struct.isSetResultDesc()) {
        oprot.writeString(struct.resultDesc);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, QrCodeResponseVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.url = iprot.readString();
        struct.setUrlIsSet(true);
      }
      if (incoming.get(1)) {
        struct.resultCode = iprot.readString();
        struct.setResultCodeIsSet(true);
      }
      if (incoming.get(2)) {
        struct.resultDesc = iprot.readString();
        struct.setResultDescIsSet(true);
      }
    }
  }

}

