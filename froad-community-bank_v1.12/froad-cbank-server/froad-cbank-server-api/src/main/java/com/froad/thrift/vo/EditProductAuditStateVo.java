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
 * 更新商品审核状态vo
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class EditProductAuditStateVo implements org.apache.thrift.TBase<EditProductAuditStateVo, EditProductAuditStateVo._Fields>, java.io.Serializable, Cloneable, Comparable<EditProductAuditStateVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("EditProductAuditStateVo");

  private static final org.apache.thrift.protocol.TField PRODUCT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("productId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField AUDIT_STATE_FIELD_DESC = new org.apache.thrift.protocol.TField("auditState", org.apache.thrift.protocol.TType.STRING, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new EditProductAuditStateVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new EditProductAuditStateVoTupleSchemeFactory());
  }

  /**
   * 商品id即商品编号
   */
  public String productId; // required
  /**
   * 商品审核状态 ("0待审核",3未提交)
   */
  public String auditState; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 商品id即商品编号
     */
    PRODUCT_ID((short)1, "productId"),
    /**
     * 商品审核状态 ("0待审核",3未提交)
     */
    AUDIT_STATE((short)2, "auditState");

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
        case 1: // PRODUCT_ID
          return PRODUCT_ID;
        case 2: // AUDIT_STATE
          return AUDIT_STATE;
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
    tmpMap.put(_Fields.PRODUCT_ID, new org.apache.thrift.meta_data.FieldMetaData("productId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.AUDIT_STATE, new org.apache.thrift.meta_data.FieldMetaData("auditState", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(EditProductAuditStateVo.class, metaDataMap);
  }

  public EditProductAuditStateVo() {
  }

  public EditProductAuditStateVo(
    String productId,
    String auditState)
  {
    this();
    this.productId = productId;
    this.auditState = auditState;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public EditProductAuditStateVo(EditProductAuditStateVo other) {
    if (other.isSetProductId()) {
      this.productId = other.productId;
    }
    if (other.isSetAuditState()) {
      this.auditState = other.auditState;
    }
  }

  public EditProductAuditStateVo deepCopy() {
    return new EditProductAuditStateVo(this);
  }

  @Override
  public void clear() {
    this.productId = null;
    this.auditState = null;
  }

  /**
   * 商品id即商品编号
   */
  public String getProductId() {
    return this.productId;
  }

  /**
   * 商品id即商品编号
   */
  public EditProductAuditStateVo setProductId(String productId) {
    this.productId = productId;
    return this;
  }

  public void unsetProductId() {
    this.productId = null;
  }

  /** Returns true if field productId is set (has been assigned a value) and false otherwise */
  public boolean isSetProductId() {
    return this.productId != null;
  }

  public void setProductIdIsSet(boolean value) {
    if (!value) {
      this.productId = null;
    }
  }

  /**
   * 商品审核状态 ("0待审核",3未提交)
   */
  public String getAuditState() {
    return this.auditState;
  }

  /**
   * 商品审核状态 ("0待审核",3未提交)
   */
  public EditProductAuditStateVo setAuditState(String auditState) {
    this.auditState = auditState;
    return this;
  }

  public void unsetAuditState() {
    this.auditState = null;
  }

  /** Returns true if field auditState is set (has been assigned a value) and false otherwise */
  public boolean isSetAuditState() {
    return this.auditState != null;
  }

  public void setAuditStateIsSet(boolean value) {
    if (!value) {
      this.auditState = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PRODUCT_ID:
      if (value == null) {
        unsetProductId();
      } else {
        setProductId((String)value);
      }
      break;

    case AUDIT_STATE:
      if (value == null) {
        unsetAuditState();
      } else {
        setAuditState((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PRODUCT_ID:
      return getProductId();

    case AUDIT_STATE:
      return getAuditState();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case PRODUCT_ID:
      return isSetProductId();
    case AUDIT_STATE:
      return isSetAuditState();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof EditProductAuditStateVo)
      return this.equals((EditProductAuditStateVo)that);
    return false;
  }

  public boolean equals(EditProductAuditStateVo that) {
    if (that == null)
      return false;

    boolean this_present_productId = true && this.isSetProductId();
    boolean that_present_productId = true && that.isSetProductId();
    if (this_present_productId || that_present_productId) {
      if (!(this_present_productId && that_present_productId))
        return false;
      if (!this.productId.equals(that.productId))
        return false;
    }

    boolean this_present_auditState = true && this.isSetAuditState();
    boolean that_present_auditState = true && that.isSetAuditState();
    if (this_present_auditState || that_present_auditState) {
      if (!(this_present_auditState && that_present_auditState))
        return false;
      if (!this.auditState.equals(that.auditState))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_productId = true && (isSetProductId());
    list.add(present_productId);
    if (present_productId)
      list.add(productId);

    boolean present_auditState = true && (isSetAuditState());
    list.add(present_auditState);
    if (present_auditState)
      list.add(auditState);

    return list.hashCode();
  }

  @Override
  public int compareTo(EditProductAuditStateVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetProductId()).compareTo(other.isSetProductId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProductId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.productId, other.productId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAuditState()).compareTo(other.isSetAuditState());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAuditState()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.auditState, other.auditState);
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
    StringBuilder sb = new StringBuilder("EditProductAuditStateVo(");
    boolean first = true;

    sb.append("productId:");
    if (this.productId == null) {
      sb.append("null");
    } else {
      sb.append(this.productId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("auditState:");
    if (this.auditState == null) {
      sb.append("null");
    } else {
      sb.append(this.auditState);
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

  private static class EditProductAuditStateVoStandardSchemeFactory implements SchemeFactory {
    public EditProductAuditStateVoStandardScheme getScheme() {
      return new EditProductAuditStateVoStandardScheme();
    }
  }

  private static class EditProductAuditStateVoStandardScheme extends StandardScheme<EditProductAuditStateVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, EditProductAuditStateVo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PRODUCT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.productId = iprot.readString();
              struct.setProductIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // AUDIT_STATE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.auditState = iprot.readString();
              struct.setAuditStateIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, EditProductAuditStateVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.productId != null) {
        oprot.writeFieldBegin(PRODUCT_ID_FIELD_DESC);
        oprot.writeString(struct.productId);
        oprot.writeFieldEnd();
      }
      if (struct.auditState != null) {
        oprot.writeFieldBegin(AUDIT_STATE_FIELD_DESC);
        oprot.writeString(struct.auditState);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class EditProductAuditStateVoTupleSchemeFactory implements SchemeFactory {
    public EditProductAuditStateVoTupleScheme getScheme() {
      return new EditProductAuditStateVoTupleScheme();
    }
  }

  private static class EditProductAuditStateVoTupleScheme extends TupleScheme<EditProductAuditStateVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, EditProductAuditStateVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetProductId()) {
        optionals.set(0);
      }
      if (struct.isSetAuditState()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetProductId()) {
        oprot.writeString(struct.productId);
      }
      if (struct.isSetAuditState()) {
        oprot.writeString(struct.auditState);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, EditProductAuditStateVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.productId = iprot.readString();
        struct.setProductIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.auditState = iprot.readString();
        struct.setAuditStateIsSet(true);
      }
    }
  }

}

