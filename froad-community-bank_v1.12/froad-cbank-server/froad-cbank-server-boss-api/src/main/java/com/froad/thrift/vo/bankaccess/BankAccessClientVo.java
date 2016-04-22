/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.froad.thrift.vo.bankaccess;

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
 * 客户端信息vo
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2015-9-15")
public class BankAccessClientVo implements org.apache.thrift.TBase<BankAccessClientVo, BankAccessClientVo._Fields>, java.io.Serializable, Cloneable, Comparable<BankAccessClientVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("BankAccessClientVo");

  private static final org.apache.thrift.protocol.TField CLIENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("clientId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField CLIENT_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("clientName", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField BANK_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("bankName", org.apache.thrift.protocol.TType.STRING, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new BankAccessClientVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new BankAccessClientVoTupleSchemeFactory());
  }

  /**
   * 客户端Id
   */
  public String clientId; // required
  /**
   * 客户端名称
   */
  public String clientName; // required
  /**
   * 银行名称
   */
  public String bankName; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 客户端Id
     */
    CLIENT_ID((short)1, "clientId"),
    /**
     * 客户端名称
     */
    CLIENT_NAME((short)2, "clientName"),
    /**
     * 银行名称
     */
    BANK_NAME((short)3, "bankName");

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
        case 2: // CLIENT_NAME
          return CLIENT_NAME;
        case 3: // BANK_NAME
          return BANK_NAME;
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
    tmpMap.put(_Fields.CLIENT_ID, new org.apache.thrift.meta_data.FieldMetaData("clientId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CLIENT_NAME, new org.apache.thrift.meta_data.FieldMetaData("clientName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.BANK_NAME, new org.apache.thrift.meta_data.FieldMetaData("bankName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(BankAccessClientVo.class, metaDataMap);
  }

  public BankAccessClientVo() {
  }

  public BankAccessClientVo(
    String clientId,
    String clientName,
    String bankName)
  {
    this();
    this.clientId = clientId;
    this.clientName = clientName;
    this.bankName = bankName;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public BankAccessClientVo(BankAccessClientVo other) {
    if (other.isSetClientId()) {
      this.clientId = other.clientId;
    }
    if (other.isSetClientName()) {
      this.clientName = other.clientName;
    }
    if (other.isSetBankName()) {
      this.bankName = other.bankName;
    }
  }

  public BankAccessClientVo deepCopy() {
    return new BankAccessClientVo(this);
  }

  @Override
  public void clear() {
    this.clientId = null;
    this.clientName = null;
    this.bankName = null;
  }

  /**
   * 客户端Id
   */
  public String getClientId() {
    return this.clientId;
  }

  /**
   * 客户端Id
   */
  public BankAccessClientVo setClientId(String clientId) {
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
   * 客户端名称
   */
  public String getClientName() {
    return this.clientName;
  }

  /**
   * 客户端名称
   */
  public BankAccessClientVo setClientName(String clientName) {
    this.clientName = clientName;
    return this;
  }

  public void unsetClientName() {
    this.clientName = null;
  }

  /** Returns true if field clientName is set (has been assigned a value) and false otherwise */
  public boolean isSetClientName() {
    return this.clientName != null;
  }

  public void setClientNameIsSet(boolean value) {
    if (!value) {
      this.clientName = null;
    }
  }

  /**
   * 银行名称
   */
  public String getBankName() {
    return this.bankName;
  }

  /**
   * 银行名称
   */
  public BankAccessClientVo setBankName(String bankName) {
    this.bankName = bankName;
    return this;
  }

  public void unsetBankName() {
    this.bankName = null;
  }

  /** Returns true if field bankName is set (has been assigned a value) and false otherwise */
  public boolean isSetBankName() {
    return this.bankName != null;
  }

  public void setBankNameIsSet(boolean value) {
    if (!value) {
      this.bankName = null;
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

    case CLIENT_NAME:
      if (value == null) {
        unsetClientName();
      } else {
        setClientName((String)value);
      }
      break;

    case BANK_NAME:
      if (value == null) {
        unsetBankName();
      } else {
        setBankName((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case CLIENT_ID:
      return getClientId();

    case CLIENT_NAME:
      return getClientName();

    case BANK_NAME:
      return getBankName();

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
    case CLIENT_NAME:
      return isSetClientName();
    case BANK_NAME:
      return isSetBankName();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof BankAccessClientVo)
      return this.equals((BankAccessClientVo)that);
    return false;
  }

  public boolean equals(BankAccessClientVo that) {
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

    boolean this_present_clientName = true && this.isSetClientName();
    boolean that_present_clientName = true && that.isSetClientName();
    if (this_present_clientName || that_present_clientName) {
      if (!(this_present_clientName && that_present_clientName))
        return false;
      if (!this.clientName.equals(that.clientName))
        return false;
    }

    boolean this_present_bankName = true && this.isSetBankName();
    boolean that_present_bankName = true && that.isSetBankName();
    if (this_present_bankName || that_present_bankName) {
      if (!(this_present_bankName && that_present_bankName))
        return false;
      if (!this.bankName.equals(that.bankName))
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

    boolean present_clientName = true && (isSetClientName());
    list.add(present_clientName);
    if (present_clientName)
      list.add(clientName);

    boolean present_bankName = true && (isSetBankName());
    list.add(present_bankName);
    if (present_bankName)
      list.add(bankName);

    return list.hashCode();
  }

  @Override
  public int compareTo(BankAccessClientVo other) {
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
    lastComparison = Boolean.valueOf(isSetClientName()).compareTo(other.isSetClientName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetClientName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.clientName, other.clientName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBankName()).compareTo(other.isSetBankName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBankName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.bankName, other.bankName);
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
    StringBuilder sb = new StringBuilder("BankAccessClientVo(");
    boolean first = true;

    sb.append("clientId:");
    if (this.clientId == null) {
      sb.append("null");
    } else {
      sb.append(this.clientId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("clientName:");
    if (this.clientName == null) {
      sb.append("null");
    } else {
      sb.append(this.clientName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("bankName:");
    if (this.bankName == null) {
      sb.append("null");
    } else {
      sb.append(this.bankName);
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

  private static class BankAccessClientVoStandardSchemeFactory implements SchemeFactory {
    public BankAccessClientVoStandardScheme getScheme() {
      return new BankAccessClientVoStandardScheme();
    }
  }

  private static class BankAccessClientVoStandardScheme extends StandardScheme<BankAccessClientVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, BankAccessClientVo struct) throws org.apache.thrift.TException {
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
          case 2: // CLIENT_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.clientName = iprot.readString();
              struct.setClientNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // BANK_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.bankName = iprot.readString();
              struct.setBankNameIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, BankAccessClientVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.clientId != null) {
        oprot.writeFieldBegin(CLIENT_ID_FIELD_DESC);
        oprot.writeString(struct.clientId);
        oprot.writeFieldEnd();
      }
      if (struct.clientName != null) {
        oprot.writeFieldBegin(CLIENT_NAME_FIELD_DESC);
        oprot.writeString(struct.clientName);
        oprot.writeFieldEnd();
      }
      if (struct.bankName != null) {
        oprot.writeFieldBegin(BANK_NAME_FIELD_DESC);
        oprot.writeString(struct.bankName);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class BankAccessClientVoTupleSchemeFactory implements SchemeFactory {
    public BankAccessClientVoTupleScheme getScheme() {
      return new BankAccessClientVoTupleScheme();
    }
  }

  private static class BankAccessClientVoTupleScheme extends TupleScheme<BankAccessClientVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, BankAccessClientVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetClientId()) {
        optionals.set(0);
      }
      if (struct.isSetClientName()) {
        optionals.set(1);
      }
      if (struct.isSetBankName()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetClientId()) {
        oprot.writeString(struct.clientId);
      }
      if (struct.isSetClientName()) {
        oprot.writeString(struct.clientName);
      }
      if (struct.isSetBankName()) {
        oprot.writeString(struct.bankName);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, BankAccessClientVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.clientId = iprot.readString();
        struct.setClientIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.clientName = iprot.readString();
        struct.setClientNameIsSet(true);
      }
      if (incoming.get(2)) {
        struct.bankName = iprot.readString();
        struct.setBankNameIsSet(true);
      }
    }
  }

}

