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
 * 启动页详情
 * TerminalStartVo
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class TerminalStartVo implements org.apache.thrift.TBase<TerminalStartVo, TerminalStartVo._Fields>, java.io.Serializable, Cloneable, Comparable<TerminalStartVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TerminalStartVo");

  private static final org.apache.thrift.protocol.TField CLIENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("clientId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField APP_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("appType", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField TERMINAL_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("terminalType", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField IMAGE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("imageId", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField IMAGE_PATH_FIELD_DESC = new org.apache.thrift.protocol.TField("imagePath", org.apache.thrift.protocol.TType.STRING, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TerminalStartVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TerminalStartVoTupleSchemeFactory());
  }

  /**
   * 客户端Id
   */
  public String clientId; // optional
  /**
   * app类型  1-个人 2-商户
   */
  public String appType; // optional
  /**
   * 终端类型 1-pc 2-andriod 3-ios
   */
  public String terminalType; // optional
  /**
   * 图片标识Id
   */
  public String imageId; // optional
  /**
   * 图片路径url
   */
  public String imagePath; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 客户端Id
     */
    CLIENT_ID((short)1, "clientId"),
    /**
     * app类型  1-个人 2-商户
     */
    APP_TYPE((short)2, "appType"),
    /**
     * 终端类型 1-pc 2-andriod 3-ios
     */
    TERMINAL_TYPE((short)3, "terminalType"),
    /**
     * 图片标识Id
     */
    IMAGE_ID((short)4, "imageId"),
    /**
     * 图片路径url
     */
    IMAGE_PATH((short)5, "imagePath");

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
        case 2: // APP_TYPE
          return APP_TYPE;
        case 3: // TERMINAL_TYPE
          return TERMINAL_TYPE;
        case 4: // IMAGE_ID
          return IMAGE_ID;
        case 5: // IMAGE_PATH
          return IMAGE_PATH;
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
  private static final _Fields optionals[] = {_Fields.CLIENT_ID,_Fields.APP_TYPE,_Fields.TERMINAL_TYPE,_Fields.IMAGE_ID,_Fields.IMAGE_PATH};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.CLIENT_ID, new org.apache.thrift.meta_data.FieldMetaData("clientId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.APP_TYPE, new org.apache.thrift.meta_data.FieldMetaData("appType", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TERMINAL_TYPE, new org.apache.thrift.meta_data.FieldMetaData("terminalType", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.IMAGE_ID, new org.apache.thrift.meta_data.FieldMetaData("imageId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.IMAGE_PATH, new org.apache.thrift.meta_data.FieldMetaData("imagePath", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TerminalStartVo.class, metaDataMap);
  }

  public TerminalStartVo() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TerminalStartVo(TerminalStartVo other) {
    if (other.isSetClientId()) {
      this.clientId = other.clientId;
    }
    if (other.isSetAppType()) {
      this.appType = other.appType;
    }
    if (other.isSetTerminalType()) {
      this.terminalType = other.terminalType;
    }
    if (other.isSetImageId()) {
      this.imageId = other.imageId;
    }
    if (other.isSetImagePath()) {
      this.imagePath = other.imagePath;
    }
  }

  public TerminalStartVo deepCopy() {
    return new TerminalStartVo(this);
  }

  @Override
  public void clear() {
    this.clientId = null;
    this.appType = null;
    this.terminalType = null;
    this.imageId = null;
    this.imagePath = null;
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
  public TerminalStartVo setClientId(String clientId) {
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
   * app类型  1-个人 2-商户
   */
  public String getAppType() {
    return this.appType;
  }

  /**
   * app类型  1-个人 2-商户
   */
  public TerminalStartVo setAppType(String appType) {
    this.appType = appType;
    return this;
  }

  public void unsetAppType() {
    this.appType = null;
  }

  /** Returns true if field appType is set (has been assigned a value) and false otherwise */
  public boolean isSetAppType() {
    return this.appType != null;
  }

  public void setAppTypeIsSet(boolean value) {
    if (!value) {
      this.appType = null;
    }
  }

  /**
   * 终端类型 1-pc 2-andriod 3-ios
   */
  public String getTerminalType() {
    return this.terminalType;
  }

  /**
   * 终端类型 1-pc 2-andriod 3-ios
   */
  public TerminalStartVo setTerminalType(String terminalType) {
    this.terminalType = terminalType;
    return this;
  }

  public void unsetTerminalType() {
    this.terminalType = null;
  }

  /** Returns true if field terminalType is set (has been assigned a value) and false otherwise */
  public boolean isSetTerminalType() {
    return this.terminalType != null;
  }

  public void setTerminalTypeIsSet(boolean value) {
    if (!value) {
      this.terminalType = null;
    }
  }

  /**
   * 图片标识Id
   */
  public String getImageId() {
    return this.imageId;
  }

  /**
   * 图片标识Id
   */
  public TerminalStartVo setImageId(String imageId) {
    this.imageId = imageId;
    return this;
  }

  public void unsetImageId() {
    this.imageId = null;
  }

  /** Returns true if field imageId is set (has been assigned a value) and false otherwise */
  public boolean isSetImageId() {
    return this.imageId != null;
  }

  public void setImageIdIsSet(boolean value) {
    if (!value) {
      this.imageId = null;
    }
  }

  /**
   * 图片路径url
   */
  public String getImagePath() {
    return this.imagePath;
  }

  /**
   * 图片路径url
   */
  public TerminalStartVo setImagePath(String imagePath) {
    this.imagePath = imagePath;
    return this;
  }

  public void unsetImagePath() {
    this.imagePath = null;
  }

  /** Returns true if field imagePath is set (has been assigned a value) and false otherwise */
  public boolean isSetImagePath() {
    return this.imagePath != null;
  }

  public void setImagePathIsSet(boolean value) {
    if (!value) {
      this.imagePath = null;
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

    case APP_TYPE:
      if (value == null) {
        unsetAppType();
      } else {
        setAppType((String)value);
      }
      break;

    case TERMINAL_TYPE:
      if (value == null) {
        unsetTerminalType();
      } else {
        setTerminalType((String)value);
      }
      break;

    case IMAGE_ID:
      if (value == null) {
        unsetImageId();
      } else {
        setImageId((String)value);
      }
      break;

    case IMAGE_PATH:
      if (value == null) {
        unsetImagePath();
      } else {
        setImagePath((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case CLIENT_ID:
      return getClientId();

    case APP_TYPE:
      return getAppType();

    case TERMINAL_TYPE:
      return getTerminalType();

    case IMAGE_ID:
      return getImageId();

    case IMAGE_PATH:
      return getImagePath();

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
    case APP_TYPE:
      return isSetAppType();
    case TERMINAL_TYPE:
      return isSetTerminalType();
    case IMAGE_ID:
      return isSetImageId();
    case IMAGE_PATH:
      return isSetImagePath();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TerminalStartVo)
      return this.equals((TerminalStartVo)that);
    return false;
  }

  public boolean equals(TerminalStartVo that) {
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

    boolean this_present_appType = true && this.isSetAppType();
    boolean that_present_appType = true && that.isSetAppType();
    if (this_present_appType || that_present_appType) {
      if (!(this_present_appType && that_present_appType))
        return false;
      if (!this.appType.equals(that.appType))
        return false;
    }

    boolean this_present_terminalType = true && this.isSetTerminalType();
    boolean that_present_terminalType = true && that.isSetTerminalType();
    if (this_present_terminalType || that_present_terminalType) {
      if (!(this_present_terminalType && that_present_terminalType))
        return false;
      if (!this.terminalType.equals(that.terminalType))
        return false;
    }

    boolean this_present_imageId = true && this.isSetImageId();
    boolean that_present_imageId = true && that.isSetImageId();
    if (this_present_imageId || that_present_imageId) {
      if (!(this_present_imageId && that_present_imageId))
        return false;
      if (!this.imageId.equals(that.imageId))
        return false;
    }

    boolean this_present_imagePath = true && this.isSetImagePath();
    boolean that_present_imagePath = true && that.isSetImagePath();
    if (this_present_imagePath || that_present_imagePath) {
      if (!(this_present_imagePath && that_present_imagePath))
        return false;
      if (!this.imagePath.equals(that.imagePath))
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

    boolean present_appType = true && (isSetAppType());
    list.add(present_appType);
    if (present_appType)
      list.add(appType);

    boolean present_terminalType = true && (isSetTerminalType());
    list.add(present_terminalType);
    if (present_terminalType)
      list.add(terminalType);

    boolean present_imageId = true && (isSetImageId());
    list.add(present_imageId);
    if (present_imageId)
      list.add(imageId);

    boolean present_imagePath = true && (isSetImagePath());
    list.add(present_imagePath);
    if (present_imagePath)
      list.add(imagePath);

    return list.hashCode();
  }

  @Override
  public int compareTo(TerminalStartVo other) {
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
    lastComparison = Boolean.valueOf(isSetAppType()).compareTo(other.isSetAppType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAppType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.appType, other.appType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTerminalType()).compareTo(other.isSetTerminalType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTerminalType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.terminalType, other.terminalType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetImageId()).compareTo(other.isSetImageId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetImageId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.imageId, other.imageId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetImagePath()).compareTo(other.isSetImagePath());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetImagePath()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.imagePath, other.imagePath);
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
    StringBuilder sb = new StringBuilder("TerminalStartVo(");
    boolean first = true;

    if (isSetClientId()) {
      sb.append("clientId:");
      if (this.clientId == null) {
        sb.append("null");
      } else {
        sb.append(this.clientId);
      }
      first = false;
    }
    if (isSetAppType()) {
      if (!first) sb.append(", ");
      sb.append("appType:");
      if (this.appType == null) {
        sb.append("null");
      } else {
        sb.append(this.appType);
      }
      first = false;
    }
    if (isSetTerminalType()) {
      if (!first) sb.append(", ");
      sb.append("terminalType:");
      if (this.terminalType == null) {
        sb.append("null");
      } else {
        sb.append(this.terminalType);
      }
      first = false;
    }
    if (isSetImageId()) {
      if (!first) sb.append(", ");
      sb.append("imageId:");
      if (this.imageId == null) {
        sb.append("null");
      } else {
        sb.append(this.imageId);
      }
      first = false;
    }
    if (isSetImagePath()) {
      if (!first) sb.append(", ");
      sb.append("imagePath:");
      if (this.imagePath == null) {
        sb.append("null");
      } else {
        sb.append(this.imagePath);
      }
      first = false;
    }
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

  private static class TerminalStartVoStandardSchemeFactory implements SchemeFactory {
    public TerminalStartVoStandardScheme getScheme() {
      return new TerminalStartVoStandardScheme();
    }
  }

  private static class TerminalStartVoStandardScheme extends StandardScheme<TerminalStartVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TerminalStartVo struct) throws org.apache.thrift.TException {
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
          case 2: // APP_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.appType = iprot.readString();
              struct.setAppTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TERMINAL_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.terminalType = iprot.readString();
              struct.setTerminalTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // IMAGE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.imageId = iprot.readString();
              struct.setImageIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // IMAGE_PATH
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.imagePath = iprot.readString();
              struct.setImagePathIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TerminalStartVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.clientId != null) {
        if (struct.isSetClientId()) {
          oprot.writeFieldBegin(CLIENT_ID_FIELD_DESC);
          oprot.writeString(struct.clientId);
          oprot.writeFieldEnd();
        }
      }
      if (struct.appType != null) {
        if (struct.isSetAppType()) {
          oprot.writeFieldBegin(APP_TYPE_FIELD_DESC);
          oprot.writeString(struct.appType);
          oprot.writeFieldEnd();
        }
      }
      if (struct.terminalType != null) {
        if (struct.isSetTerminalType()) {
          oprot.writeFieldBegin(TERMINAL_TYPE_FIELD_DESC);
          oprot.writeString(struct.terminalType);
          oprot.writeFieldEnd();
        }
      }
      if (struct.imageId != null) {
        if (struct.isSetImageId()) {
          oprot.writeFieldBegin(IMAGE_ID_FIELD_DESC);
          oprot.writeString(struct.imageId);
          oprot.writeFieldEnd();
        }
      }
      if (struct.imagePath != null) {
        if (struct.isSetImagePath()) {
          oprot.writeFieldBegin(IMAGE_PATH_FIELD_DESC);
          oprot.writeString(struct.imagePath);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TerminalStartVoTupleSchemeFactory implements SchemeFactory {
    public TerminalStartVoTupleScheme getScheme() {
      return new TerminalStartVoTupleScheme();
    }
  }

  private static class TerminalStartVoTupleScheme extends TupleScheme<TerminalStartVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TerminalStartVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetClientId()) {
        optionals.set(0);
      }
      if (struct.isSetAppType()) {
        optionals.set(1);
      }
      if (struct.isSetTerminalType()) {
        optionals.set(2);
      }
      if (struct.isSetImageId()) {
        optionals.set(3);
      }
      if (struct.isSetImagePath()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetClientId()) {
        oprot.writeString(struct.clientId);
      }
      if (struct.isSetAppType()) {
        oprot.writeString(struct.appType);
      }
      if (struct.isSetTerminalType()) {
        oprot.writeString(struct.terminalType);
      }
      if (struct.isSetImageId()) {
        oprot.writeString(struct.imageId);
      }
      if (struct.isSetImagePath()) {
        oprot.writeString(struct.imagePath);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TerminalStartVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.clientId = iprot.readString();
        struct.setClientIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.appType = iprot.readString();
        struct.setAppTypeIsSet(true);
      }
      if (incoming.get(2)) {
        struct.terminalType = iprot.readString();
        struct.setTerminalTypeIsSet(true);
      }
      if (incoming.get(3)) {
        struct.imageId = iprot.readString();
        struct.setImageIdIsSet(true);
      }
      if (incoming.get(4)) {
        struct.imagePath = iprot.readString();
        struct.setImagePathIsSet(true);
      }
    }
  }

}
