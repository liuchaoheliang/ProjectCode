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
 * 银行角色资源关系MongoVo
 * BankUserResourceVo
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class BankUserResourceVo implements org.apache.thrift.TBase<BankUserResourceVo, BankUserResourceVo._Fields>, java.io.Serializable, Cloneable, Comparable<BankUserResourceVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("BankUserResourceVo");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField RESOURCES_FIELD_DESC = new org.apache.thrift.protocol.TField("resources", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new BankUserResourceVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new BankUserResourceVoTupleSchemeFactory());
  }

  /**
   * 资源id 主键id
   */
  public String id; // optional
  /**
   * 客户端id
   */
  public List<ResourcesInfoVo> resources; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 资源id 主键id
     */
    ID((short)1, "id"),
    /**
     * 客户端id
     */
    RESOURCES((short)2, "resources");

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
        case 1: // ID
          return ID;
        case 2: // RESOURCES
          return RESOURCES;
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
  private static final _Fields optionals[] = {_Fields.ID,_Fields.RESOURCES};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.RESOURCES, new org.apache.thrift.meta_data.FieldMetaData("resources", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT            , "ResourcesInfoVo"))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(BankUserResourceVo.class, metaDataMap);
  }

  public BankUserResourceVo() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public BankUserResourceVo(BankUserResourceVo other) {
    if (other.isSetId()) {
      this.id = other.id;
    }
    if (other.isSetResources()) {
      List<ResourcesInfoVo> __this__resources = new ArrayList<ResourcesInfoVo>(other.resources.size());
      for (ResourcesInfoVo other_element : other.resources) {
        __this__resources.add(other_element);
      }
      this.resources = __this__resources;
    }
  }

  public BankUserResourceVo deepCopy() {
    return new BankUserResourceVo(this);
  }

  @Override
  public void clear() {
    this.id = null;
    this.resources = null;
  }

  /**
   * 资源id 主键id
   */
  public String getId() {
    return this.id;
  }

  /**
   * 资源id 主键id
   */
  public BankUserResourceVo setId(String id) {
    this.id = id;
    return this;
  }

  public void unsetId() {
    this.id = null;
  }

  /** Returns true if field id is set (has been assigned a value) and false otherwise */
  public boolean isSetId() {
    return this.id != null;
  }

  public void setIdIsSet(boolean value) {
    if (!value) {
      this.id = null;
    }
  }

  public int getResourcesSize() {
    return (this.resources == null) ? 0 : this.resources.size();
  }

  public java.util.Iterator<ResourcesInfoVo> getResourcesIterator() {
    return (this.resources == null) ? null : this.resources.iterator();
  }

  public void addToResources(ResourcesInfoVo elem) {
    if (this.resources == null) {
      this.resources = new ArrayList<ResourcesInfoVo>();
    }
    this.resources.add(elem);
  }

  /**
   * 客户端id
   */
  public List<ResourcesInfoVo> getResources() {
    return this.resources;
  }

  /**
   * 客户端id
   */
  public BankUserResourceVo setResources(List<ResourcesInfoVo> resources) {
    this.resources = resources;
    return this;
  }

  public void unsetResources() {
    this.resources = null;
  }

  /** Returns true if field resources is set (has been assigned a value) and false otherwise */
  public boolean isSetResources() {
    return this.resources != null;
  }

  public void setResourcesIsSet(boolean value) {
    if (!value) {
      this.resources = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((String)value);
      }
      break;

    case RESOURCES:
      if (value == null) {
        unsetResources();
      } else {
        setResources((List<ResourcesInfoVo>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return getId();

    case RESOURCES:
      return getResources();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ID:
      return isSetId();
    case RESOURCES:
      return isSetResources();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof BankUserResourceVo)
      return this.equals((BankUserResourceVo)that);
    return false;
  }

  public boolean equals(BankUserResourceVo that) {
    if (that == null)
      return false;

    boolean this_present_id = true && this.isSetId();
    boolean that_present_id = true && that.isSetId();
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (!this.id.equals(that.id))
        return false;
    }

    boolean this_present_resources = true && this.isSetResources();
    boolean that_present_resources = true && that.isSetResources();
    if (this_present_resources || that_present_resources) {
      if (!(this_present_resources && that_present_resources))
        return false;
      if (!this.resources.equals(that.resources))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_id = true && (isSetId());
    list.add(present_id);
    if (present_id)
      list.add(id);

    boolean present_resources = true && (isSetResources());
    list.add(present_resources);
    if (present_resources)
      list.add(resources);

    return list.hashCode();
  }

  @Override
  public int compareTo(BankUserResourceVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetId()).compareTo(other.isSetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id, other.id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetResources()).compareTo(other.isSetResources());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetResources()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.resources, other.resources);
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
    StringBuilder sb = new StringBuilder("BankUserResourceVo(");
    boolean first = true;

    if (isSetId()) {
      sb.append("id:");
      if (this.id == null) {
        sb.append("null");
      } else {
        sb.append(this.id);
      }
      first = false;
    }
    if (isSetResources()) {
      if (!first) sb.append(", ");
      sb.append("resources:");
      if (this.resources == null) {
        sb.append("null");
      } else {
        sb.append(this.resources);
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

  private static class BankUserResourceVoStandardSchemeFactory implements SchemeFactory {
    public BankUserResourceVoStandardScheme getScheme() {
      return new BankUserResourceVoStandardScheme();
    }
  }

  private static class BankUserResourceVoStandardScheme extends StandardScheme<BankUserResourceVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, BankUserResourceVo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.id = iprot.readString();
              struct.setIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // RESOURCES
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list24 = iprot.readListBegin();
                struct.resources = new ArrayList<ResourcesInfoVo>(_list24.size);
                ResourcesInfoVo _elem25;
                for (int _i26 = 0; _i26 < _list24.size; ++_i26)
                {
                  _elem25 = new ResourcesInfoVo();
                  _elem25.read(iprot);
                  struct.resources.add(_elem25);
                }
                iprot.readListEnd();
              }
              struct.setResourcesIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, BankUserResourceVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.id != null) {
        if (struct.isSetId()) {
          oprot.writeFieldBegin(ID_FIELD_DESC);
          oprot.writeString(struct.id);
          oprot.writeFieldEnd();
        }
      }
      if (struct.resources != null) {
        if (struct.isSetResources()) {
          oprot.writeFieldBegin(RESOURCES_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.resources.size()));
            for (ResourcesInfoVo _iter27 : struct.resources)
            {
              _iter27.write(oprot);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class BankUserResourceVoTupleSchemeFactory implements SchemeFactory {
    public BankUserResourceVoTupleScheme getScheme() {
      return new BankUserResourceVoTupleScheme();
    }
  }

  private static class BankUserResourceVoTupleScheme extends TupleScheme<BankUserResourceVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, BankUserResourceVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetResources()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetId()) {
        oprot.writeString(struct.id);
      }
      if (struct.isSetResources()) {
        {
          oprot.writeI32(struct.resources.size());
          for (ResourcesInfoVo _iter28 : struct.resources)
          {
            _iter28.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, BankUserResourceVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.id = iprot.readString();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list29 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.resources = new ArrayList<ResourcesInfoVo>(_list29.size);
          ResourcesInfoVo _elem30;
          for (int _i31 = 0; _i31 < _list29.size; ++_i31)
          {
            _elem30 = new ResourcesInfoVo();
            _elem30.read(iprot);
            struct.resources.add(_elem30);
          }
        }
        struct.setResourcesIsSet(true);
      }
    }
  }

}

