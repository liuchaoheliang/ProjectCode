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
 * ProductImageVo
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2015-7-24")
public class ProductImageVo implements org.apache.thrift.TBase<ProductImageVo, ProductImageVo._Fields>, java.io.Serializable, Cloneable, Comparable<ProductImageVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ProductImageVo");

  private static final org.apache.thrift.protocol.TField TITLE_FIELD_DESC = new org.apache.thrift.protocol.TField("title", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField SOURCE_FIELD_DESC = new org.apache.thrift.protocol.TField("source", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField LARGE_FIELD_DESC = new org.apache.thrift.protocol.TField("large", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField MEDIUM_FIELD_DESC = new org.apache.thrift.protocol.TField("medium", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField THUMBNAIL_FIELD_DESC = new org.apache.thrift.protocol.TField("thumbnail", org.apache.thrift.protocol.TType.STRING, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ProductImageVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ProductImageVoTupleSchemeFactory());
  }

  /**
   * 图片标题
   */
  public String title; // required
  /**
   * 图片原图地址
   */
  public String source; // required
  /**
   * 图片大图地址
   */
  public String large; // required
  /**
   * 图片中图地址
   */
  public String medium; // required
  /**
   * 图片小图地址
   */
  public String thumbnail; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 图片标题
     */
    TITLE((short)1, "title"),
    /**
     * 图片原图地址
     */
    SOURCE((short)2, "source"),
    /**
     * 图片大图地址
     */
    LARGE((short)3, "large"),
    /**
     * 图片中图地址
     */
    MEDIUM((short)4, "medium"),
    /**
     * 图片小图地址
     */
    THUMBNAIL((short)5, "thumbnail");

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
        case 1: // TITLE
          return TITLE;
        case 2: // SOURCE
          return SOURCE;
        case 3: // LARGE
          return LARGE;
        case 4: // MEDIUM
          return MEDIUM;
        case 5: // THUMBNAIL
          return THUMBNAIL;
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
    tmpMap.put(_Fields.TITLE, new org.apache.thrift.meta_data.FieldMetaData("title", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SOURCE, new org.apache.thrift.meta_data.FieldMetaData("source", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.LARGE, new org.apache.thrift.meta_data.FieldMetaData("large", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.MEDIUM, new org.apache.thrift.meta_data.FieldMetaData("medium", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.THUMBNAIL, new org.apache.thrift.meta_data.FieldMetaData("thumbnail", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ProductImageVo.class, metaDataMap);
  }

  public ProductImageVo() {
  }

  public ProductImageVo(
    String title,
    String source,
    String large,
    String medium,
    String thumbnail)
  {
    this();
    this.title = title;
    this.source = source;
    this.large = large;
    this.medium = medium;
    this.thumbnail = thumbnail;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ProductImageVo(ProductImageVo other) {
    if (other.isSetTitle()) {
      this.title = other.title;
    }
    if (other.isSetSource()) {
      this.source = other.source;
    }
    if (other.isSetLarge()) {
      this.large = other.large;
    }
    if (other.isSetMedium()) {
      this.medium = other.medium;
    }
    if (other.isSetThumbnail()) {
      this.thumbnail = other.thumbnail;
    }
  }

  public ProductImageVo deepCopy() {
    return new ProductImageVo(this);
  }

  @Override
  public void clear() {
    this.title = null;
    this.source = null;
    this.large = null;
    this.medium = null;
    this.thumbnail = null;
  }

  /**
   * 图片标题
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * 图片标题
   */
  public ProductImageVo setTitle(String title) {
    this.title = title;
    return this;
  }

  public void unsetTitle() {
    this.title = null;
  }

  /** Returns true if field title is set (has been assigned a value) and false otherwise */
  public boolean isSetTitle() {
    return this.title != null;
  }

  public void setTitleIsSet(boolean value) {
    if (!value) {
      this.title = null;
    }
  }

  /**
   * 图片原图地址
   */
  public String getSource() {
    return this.source;
  }

  /**
   * 图片原图地址
   */
  public ProductImageVo setSource(String source) {
    this.source = source;
    return this;
  }

  public void unsetSource() {
    this.source = null;
  }

  /** Returns true if field source is set (has been assigned a value) and false otherwise */
  public boolean isSetSource() {
    return this.source != null;
  }

  public void setSourceIsSet(boolean value) {
    if (!value) {
      this.source = null;
    }
  }

  /**
   * 图片大图地址
   */
  public String getLarge() {
    return this.large;
  }

  /**
   * 图片大图地址
   */
  public ProductImageVo setLarge(String large) {
    this.large = large;
    return this;
  }

  public void unsetLarge() {
    this.large = null;
  }

  /** Returns true if field large is set (has been assigned a value) and false otherwise */
  public boolean isSetLarge() {
    return this.large != null;
  }

  public void setLargeIsSet(boolean value) {
    if (!value) {
      this.large = null;
    }
  }

  /**
   * 图片中图地址
   */
  public String getMedium() {
    return this.medium;
  }

  /**
   * 图片中图地址
   */
  public ProductImageVo setMedium(String medium) {
    this.medium = medium;
    return this;
  }

  public void unsetMedium() {
    this.medium = null;
  }

  /** Returns true if field medium is set (has been assigned a value) and false otherwise */
  public boolean isSetMedium() {
    return this.medium != null;
  }

  public void setMediumIsSet(boolean value) {
    if (!value) {
      this.medium = null;
    }
  }

  /**
   * 图片小图地址
   */
  public String getThumbnail() {
    return this.thumbnail;
  }

  /**
   * 图片小图地址
   */
  public ProductImageVo setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
    return this;
  }

  public void unsetThumbnail() {
    this.thumbnail = null;
  }

  /** Returns true if field thumbnail is set (has been assigned a value) and false otherwise */
  public boolean isSetThumbnail() {
    return this.thumbnail != null;
  }

  public void setThumbnailIsSet(boolean value) {
    if (!value) {
      this.thumbnail = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TITLE:
      if (value == null) {
        unsetTitle();
      } else {
        setTitle((String)value);
      }
      break;

    case SOURCE:
      if (value == null) {
        unsetSource();
      } else {
        setSource((String)value);
      }
      break;

    case LARGE:
      if (value == null) {
        unsetLarge();
      } else {
        setLarge((String)value);
      }
      break;

    case MEDIUM:
      if (value == null) {
        unsetMedium();
      } else {
        setMedium((String)value);
      }
      break;

    case THUMBNAIL:
      if (value == null) {
        unsetThumbnail();
      } else {
        setThumbnail((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TITLE:
      return getTitle();

    case SOURCE:
      return getSource();

    case LARGE:
      return getLarge();

    case MEDIUM:
      return getMedium();

    case THUMBNAIL:
      return getThumbnail();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case TITLE:
      return isSetTitle();
    case SOURCE:
      return isSetSource();
    case LARGE:
      return isSetLarge();
    case MEDIUM:
      return isSetMedium();
    case THUMBNAIL:
      return isSetThumbnail();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ProductImageVo)
      return this.equals((ProductImageVo)that);
    return false;
  }

  public boolean equals(ProductImageVo that) {
    if (that == null)
      return false;

    boolean this_present_title = true && this.isSetTitle();
    boolean that_present_title = true && that.isSetTitle();
    if (this_present_title || that_present_title) {
      if (!(this_present_title && that_present_title))
        return false;
      if (!this.title.equals(that.title))
        return false;
    }

    boolean this_present_source = true && this.isSetSource();
    boolean that_present_source = true && that.isSetSource();
    if (this_present_source || that_present_source) {
      if (!(this_present_source && that_present_source))
        return false;
      if (!this.source.equals(that.source))
        return false;
    }

    boolean this_present_large = true && this.isSetLarge();
    boolean that_present_large = true && that.isSetLarge();
    if (this_present_large || that_present_large) {
      if (!(this_present_large && that_present_large))
        return false;
      if (!this.large.equals(that.large))
        return false;
    }

    boolean this_present_medium = true && this.isSetMedium();
    boolean that_present_medium = true && that.isSetMedium();
    if (this_present_medium || that_present_medium) {
      if (!(this_present_medium && that_present_medium))
        return false;
      if (!this.medium.equals(that.medium))
        return false;
    }

    boolean this_present_thumbnail = true && this.isSetThumbnail();
    boolean that_present_thumbnail = true && that.isSetThumbnail();
    if (this_present_thumbnail || that_present_thumbnail) {
      if (!(this_present_thumbnail && that_present_thumbnail))
        return false;
      if (!this.thumbnail.equals(that.thumbnail))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_title = true && (isSetTitle());
    list.add(present_title);
    if (present_title)
      list.add(title);

    boolean present_source = true && (isSetSource());
    list.add(present_source);
    if (present_source)
      list.add(source);

    boolean present_large = true && (isSetLarge());
    list.add(present_large);
    if (present_large)
      list.add(large);

    boolean present_medium = true && (isSetMedium());
    list.add(present_medium);
    if (present_medium)
      list.add(medium);

    boolean present_thumbnail = true && (isSetThumbnail());
    list.add(present_thumbnail);
    if (present_thumbnail)
      list.add(thumbnail);

    return list.hashCode();
  }

  @Override
  public int compareTo(ProductImageVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetTitle()).compareTo(other.isSetTitle());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTitle()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.title, other.title);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSource()).compareTo(other.isSetSource());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSource()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.source, other.source);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLarge()).compareTo(other.isSetLarge());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLarge()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.large, other.large);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMedium()).compareTo(other.isSetMedium());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMedium()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.medium, other.medium);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetThumbnail()).compareTo(other.isSetThumbnail());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetThumbnail()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.thumbnail, other.thumbnail);
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
    StringBuilder sb = new StringBuilder("ProductImageVo(");
    boolean first = true;

    sb.append("title:");
    if (this.title == null) {
      sb.append("null");
    } else {
      sb.append(this.title);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("source:");
    if (this.source == null) {
      sb.append("null");
    } else {
      sb.append(this.source);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("large:");
    if (this.large == null) {
      sb.append("null");
    } else {
      sb.append(this.large);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("medium:");
    if (this.medium == null) {
      sb.append("null");
    } else {
      sb.append(this.medium);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("thumbnail:");
    if (this.thumbnail == null) {
      sb.append("null");
    } else {
      sb.append(this.thumbnail);
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

  private static class ProductImageVoStandardSchemeFactory implements SchemeFactory {
    public ProductImageVoStandardScheme getScheme() {
      return new ProductImageVoStandardScheme();
    }
  }

  private static class ProductImageVoStandardScheme extends StandardScheme<ProductImageVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ProductImageVo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TITLE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.title = iprot.readString();
              struct.setTitleIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // SOURCE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.source = iprot.readString();
              struct.setSourceIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // LARGE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.large = iprot.readString();
              struct.setLargeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // MEDIUM
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.medium = iprot.readString();
              struct.setMediumIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // THUMBNAIL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.thumbnail = iprot.readString();
              struct.setThumbnailIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ProductImageVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.title != null) {
        oprot.writeFieldBegin(TITLE_FIELD_DESC);
        oprot.writeString(struct.title);
        oprot.writeFieldEnd();
      }
      if (struct.source != null) {
        oprot.writeFieldBegin(SOURCE_FIELD_DESC);
        oprot.writeString(struct.source);
        oprot.writeFieldEnd();
      }
      if (struct.large != null) {
        oprot.writeFieldBegin(LARGE_FIELD_DESC);
        oprot.writeString(struct.large);
        oprot.writeFieldEnd();
      }
      if (struct.medium != null) {
        oprot.writeFieldBegin(MEDIUM_FIELD_DESC);
        oprot.writeString(struct.medium);
        oprot.writeFieldEnd();
      }
      if (struct.thumbnail != null) {
        oprot.writeFieldBegin(THUMBNAIL_FIELD_DESC);
        oprot.writeString(struct.thumbnail);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ProductImageVoTupleSchemeFactory implements SchemeFactory {
    public ProductImageVoTupleScheme getScheme() {
      return new ProductImageVoTupleScheme();
    }
  }

  private static class ProductImageVoTupleScheme extends TupleScheme<ProductImageVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ProductImageVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetTitle()) {
        optionals.set(0);
      }
      if (struct.isSetSource()) {
        optionals.set(1);
      }
      if (struct.isSetLarge()) {
        optionals.set(2);
      }
      if (struct.isSetMedium()) {
        optionals.set(3);
      }
      if (struct.isSetThumbnail()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetTitle()) {
        oprot.writeString(struct.title);
      }
      if (struct.isSetSource()) {
        oprot.writeString(struct.source);
      }
      if (struct.isSetLarge()) {
        oprot.writeString(struct.large);
      }
      if (struct.isSetMedium()) {
        oprot.writeString(struct.medium);
      }
      if (struct.isSetThumbnail()) {
        oprot.writeString(struct.thumbnail);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ProductImageVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.title = iprot.readString();
        struct.setTitleIsSet(true);
      }
      if (incoming.get(1)) {
        struct.source = iprot.readString();
        struct.setSourceIsSet(true);
      }
      if (incoming.get(2)) {
        struct.large = iprot.readString();
        struct.setLargeIsSet(true);
      }
      if (incoming.get(3)) {
        struct.medium = iprot.readString();
        struct.setMediumIsSet(true);
      }
      if (incoming.get(4)) {
        struct.thumbnail = iprot.readString();
        struct.setThumbnailIsSet(true);
      }
    }
  }

}

