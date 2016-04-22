/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.froad.thrift.vo.order;

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
 * 14.通过二维码获取订单
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class GetOrderByQrcodeVoReq implements org.apache.thrift.TBase<GetOrderByQrcodeVoReq, GetOrderByQrcodeVoReq._Fields>, java.io.Serializable, Cloneable, Comparable<GetOrderByQrcodeVoReq> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("GetOrderByQrcodeVoReq");

  private static final org.apache.thrift.protocol.TField CLIENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("clientId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField QRCODE_FIELD_DESC = new org.apache.thrift.protocol.TField("qrcode", org.apache.thrift.protocol.TType.STRING, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new GetOrderByQrcodeVoReqStandardSchemeFactory());
    schemes.put(TupleScheme.class, new GetOrderByQrcodeVoReqTupleSchemeFactory());
  }

  /**
   * 客户端ID
   */
  public String clientId; // required
  /**
   * 二维码
   */
  public String qrcode; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 客户端ID
     */
    CLIENT_ID((short)1, "clientId"),
    /**
     * 二维码
     */
    QRCODE((short)2, "qrcode");

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
        case 1: // CLIENT_ID
          return CLIENT_ID;
        case 2: // QRCODE
          return QRCODE;
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
    tmpMap.put(_Fields.CLIENT_ID, new org.apache.thrift.meta_data.FieldMetaData("clientId", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.QRCODE, new org.apache.thrift.meta_data.FieldMetaData("qrcode", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(GetOrderByQrcodeVoReq.class, metaDataMap);
  }

  public GetOrderByQrcodeVoReq() {
  }

  public GetOrderByQrcodeVoReq(
    String clientId,
    String qrcode)
  {
    this();
    this.clientId = clientId;
    this.qrcode = qrcode;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public GetOrderByQrcodeVoReq(GetOrderByQrcodeVoReq other) {
    if (other.isSetClientId()) {
      this.clientId = other.clientId;
    }
    if (other.isSetQrcode()) {
      this.qrcode = other.qrcode;
    }
  }

  public GetOrderByQrcodeVoReq deepCopy() {
    return new GetOrderByQrcodeVoReq(this);
  }

  @Override
  public void clear() {
    this.clientId = null;
    this.qrcode = null;
  }

  /**
   * 客户端ID
   */
  public String getClientId() {
    return this.clientId;
  }

  /**
   * 客户端ID
   */
  public GetOrderByQrcodeVoReq setClientId(String clientId) {
    this.clientId = clientId;
    return this;
  }

  public void unsetClientId() {
    this.clientId = null;
  }

  /** Returns true if field clientId is set (has been assigned a value) and false otherwise */
  public boolean isSetClientId() {
    return this.clientId != null;
  }

  public void setClientIdIsSet(boolean value) {
    if (!value) {
      this.clientId = null;
    }
  }

  /**
   * 二维码
   */
  public String getQrcode() {
    return this.qrcode;
  }

  /**
   * 二维码
   */
  public GetOrderByQrcodeVoReq setQrcode(String qrcode) {
    this.qrcode = qrcode;
    return this;
  }

  public void unsetQrcode() {
    this.qrcode = null;
  }

  /** Returns true if field qrcode is set (has been assigned a value) and false otherwise */
  public boolean isSetQrcode() {
    return this.qrcode != null;
  }

  public void setQrcodeIsSet(boolean value) {
    if (!value) {
      this.qrcode = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case CLIENT_ID:
      if (value == null) {
        unsetClientId();
      } else {
        setClientId((String)value);
      }
      break;

    case QRCODE:
      if (value == null) {
        unsetQrcode();
      } else {
        setQrcode((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case CLIENT_ID:
      return getClientId();

    case QRCODE:
      return getQrcode();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case CLIENT_ID:
      return isSetClientId();
    case QRCODE:
      return isSetQrcode();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof GetOrderByQrcodeVoReq)
      return this.equals((GetOrderByQrcodeVoReq)that);
    return false;
  }

  public boolean equals(GetOrderByQrcodeVoReq that) {
    if (that == null)
      return false;

    boolean this_present_clientId = true && this.isSetClientId();
    boolean that_present_clientId = true && that.isSetClientId();
    if (this_present_clientId || that_present_clientId) {
      if (!(this_present_clientId && that_present_clientId))
        return false;
      if (!this.clientId.equals(that.clientId))
        return false;
    }

    boolean this_present_qrcode = true && this.isSetQrcode();
    boolean that_present_qrcode = true && that.isSetQrcode();
    if (this_present_qrcode || that_present_qrcode) {
      if (!(this_present_qrcode && that_present_qrcode))
        return false;
      if (!this.qrcode.equals(that.qrcode))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_clientId = true && (isSetClientId());
    list.add(present_clientId);
    if (present_clientId)
      list.add(clientId);

    boolean present_qrcode = true && (isSetQrcode());
    list.add(present_qrcode);
    if (present_qrcode)
      list.add(qrcode);

    return list.hashCode();
  }

  @Override
  public int compareTo(GetOrderByQrcodeVoReq other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetClientId()).compareTo(other.isSetClientId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetClientId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.clientId, other.clientId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetQrcode()).compareTo(other.isSetQrcode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetQrcode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.qrcode, other.qrcode);
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
    StringBuilder sb = new StringBuilder("GetOrderByQrcodeVoReq(");
    boolean first = true;

    sb.append("clientId:");
    if (this.clientId == null) {
      sb.append("null");
    } else {
      sb.append(this.clientId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("qrcode:");
    if (this.qrcode == null) {
      sb.append("null");
    } else {
      sb.append(this.qrcode);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (clientId == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'clientId' was not present! Struct: " + toString());
    }
    if (qrcode == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'qrcode' was not present! Struct: " + toString());
    }
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

  private static class GetOrderByQrcodeVoReqStandardSchemeFactory implements SchemeFactory {
    public GetOrderByQrcodeVoReqStandardScheme getScheme() {
      return new GetOrderByQrcodeVoReqStandardScheme();
    }
  }

  private static class GetOrderByQrcodeVoReqStandardScheme extends StandardScheme<GetOrderByQrcodeVoReq> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, GetOrderByQrcodeVoReq struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // CLIENT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.clientId = iprot.readString();
              struct.setClientIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // QRCODE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.qrcode = iprot.readString();
              struct.setQrcodeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, GetOrderByQrcodeVoReq struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.clientId != null) {
        oprot.writeFieldBegin(CLIENT_ID_FIELD_DESC);
        oprot.writeString(struct.clientId);
        oprot.writeFieldEnd();
      }
      if (struct.qrcode != null) {
        oprot.writeFieldBegin(QRCODE_FIELD_DESC);
        oprot.writeString(struct.qrcode);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class GetOrderByQrcodeVoReqTupleSchemeFactory implements SchemeFactory {
    public GetOrderByQrcodeVoReqTupleScheme getScheme() {
      return new GetOrderByQrcodeVoReqTupleScheme();
    }
  }

  private static class GetOrderByQrcodeVoReqTupleScheme extends TupleScheme<GetOrderByQrcodeVoReq> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, GetOrderByQrcodeVoReq struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.clientId);
      oprot.writeString(struct.qrcode);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, GetOrderByQrcodeVoReq struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.clientId = iprot.readString();
      struct.setClientIdIsSet(true);
      struct.qrcode = iprot.readString();
      struct.setQrcodeIsSet(true);
    }
  }

}

