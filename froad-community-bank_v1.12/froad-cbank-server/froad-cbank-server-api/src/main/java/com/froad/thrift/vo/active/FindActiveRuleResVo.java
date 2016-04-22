/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.froad.thrift.vo.active;

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
 * 查询营销活动规则结果
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class FindActiveRuleResVo implements org.apache.thrift.TBase<FindActiveRuleResVo, FindActiveRuleResVo._Fields>, java.io.Serializable, Cloneable, Comparable<FindActiveRuleResVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("FindActiveRuleResVo");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField FIND_ACTIVE_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("findActiveList", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new FindActiveRuleResVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new FindActiveRuleResVoTupleSchemeFactory());
  }

  /**
   * 查询条件id - merchantId/productId
   */
  public String id; // required
  public List<FindActiveVo> findActiveList; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 查询条件id - merchantId/productId
     */
    ID((short)1, "id"),
    FIND_ACTIVE_LIST((short)2, "findActiveList");

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
        case 2: // FIND_ACTIVE_LIST
          return FIND_ACTIVE_LIST;
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
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.FIND_ACTIVE_LIST, new org.apache.thrift.meta_data.FieldMetaData("findActiveList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT            , "FindActiveVo"))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(FindActiveRuleResVo.class, metaDataMap);
  }

  public FindActiveRuleResVo() {
  }

  public FindActiveRuleResVo(
    String id,
    List<FindActiveVo> findActiveList)
  {
    this();
    this.id = id;
    this.findActiveList = findActiveList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public FindActiveRuleResVo(FindActiveRuleResVo other) {
    if (other.isSetId()) {
      this.id = other.id;
    }
    if (other.isSetFindActiveList()) {
      List<FindActiveVo> __this__findActiveList = new ArrayList<FindActiveVo>(other.findActiveList.size());
      for (FindActiveVo other_element : other.findActiveList) {
        __this__findActiveList.add(other_element);
      }
      this.findActiveList = __this__findActiveList;
    }
  }

  public FindActiveRuleResVo deepCopy() {
    return new FindActiveRuleResVo(this);
  }

  @Override
  public void clear() {
    this.id = null;
    this.findActiveList = null;
  }

  /**
   * 查询条件id - merchantId/productId
   */
  public String getId() {
    return this.id;
  }

  /**
   * 查询条件id - merchantId/productId
   */
  public FindActiveRuleResVo setId(String id) {
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

  public int getFindActiveListSize() {
    return (this.findActiveList == null) ? 0 : this.findActiveList.size();
  }

  public java.util.Iterator<FindActiveVo> getFindActiveListIterator() {
    return (this.findActiveList == null) ? null : this.findActiveList.iterator();
  }

  public void addToFindActiveList(FindActiveVo elem) {
    if (this.findActiveList == null) {
      this.findActiveList = new ArrayList<FindActiveVo>();
    }
    this.findActiveList.add(elem);
  }

  public List<FindActiveVo> getFindActiveList() {
    return this.findActiveList;
  }

  public FindActiveRuleResVo setFindActiveList(List<FindActiveVo> findActiveList) {
    this.findActiveList = findActiveList;
    return this;
  }

  public void unsetFindActiveList() {
    this.findActiveList = null;
  }

  /** Returns true if field findActiveList is set (has been assigned a value) and false otherwise */
  public boolean isSetFindActiveList() {
    return this.findActiveList != null;
  }

  public void setFindActiveListIsSet(boolean value) {
    if (!value) {
      this.findActiveList = null;
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

    case FIND_ACTIVE_LIST:
      if (value == null) {
        unsetFindActiveList();
      } else {
        setFindActiveList((List<FindActiveVo>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return getId();

    case FIND_ACTIVE_LIST:
      return getFindActiveList();

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
    case FIND_ACTIVE_LIST:
      return isSetFindActiveList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof FindActiveRuleResVo)
      return this.equals((FindActiveRuleResVo)that);
    return false;
  }

  public boolean equals(FindActiveRuleResVo that) {
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

    boolean this_present_findActiveList = true && this.isSetFindActiveList();
    boolean that_present_findActiveList = true && that.isSetFindActiveList();
    if (this_present_findActiveList || that_present_findActiveList) {
      if (!(this_present_findActiveList && that_present_findActiveList))
        return false;
      if (!this.findActiveList.equals(that.findActiveList))
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

    boolean present_findActiveList = true && (isSetFindActiveList());
    list.add(present_findActiveList);
    if (present_findActiveList)
      list.add(findActiveList);

    return list.hashCode();
  }

  @Override
  public int compareTo(FindActiveRuleResVo other) {
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
    lastComparison = Boolean.valueOf(isSetFindActiveList()).compareTo(other.isSetFindActiveList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFindActiveList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.findActiveList, other.findActiveList);
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
    StringBuilder sb = new StringBuilder("FindActiveRuleResVo(");
    boolean first = true;

    sb.append("id:");
    if (this.id == null) {
      sb.append("null");
    } else {
      sb.append(this.id);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("findActiveList:");
    if (this.findActiveList == null) {
      sb.append("null");
    } else {
      sb.append(this.findActiveList);
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

  private static class FindActiveRuleResVoStandardSchemeFactory implements SchemeFactory {
    public FindActiveRuleResVoStandardScheme getScheme() {
      return new FindActiveRuleResVoStandardScheme();
    }
  }

  private static class FindActiveRuleResVoStandardScheme extends StandardScheme<FindActiveRuleResVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, FindActiveRuleResVo struct) throws org.apache.thrift.TException {
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
          case 2: // FIND_ACTIVE_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list232 = iprot.readListBegin();
                struct.findActiveList = new ArrayList<FindActiveVo>(_list232.size);
                FindActiveVo _elem233;
                for (int _i234 = 0; _i234 < _list232.size; ++_i234)
                {
                  _elem233 = new FindActiveVo();
                  _elem233.read(iprot);
                  struct.findActiveList.add(_elem233);
                }
                iprot.readListEnd();
              }
              struct.setFindActiveListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, FindActiveRuleResVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.id != null) {
        oprot.writeFieldBegin(ID_FIELD_DESC);
        oprot.writeString(struct.id);
        oprot.writeFieldEnd();
      }
      if (struct.findActiveList != null) {
        oprot.writeFieldBegin(FIND_ACTIVE_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.findActiveList.size()));
          for (FindActiveVo _iter235 : struct.findActiveList)
          {
            _iter235.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class FindActiveRuleResVoTupleSchemeFactory implements SchemeFactory {
    public FindActiveRuleResVoTupleScheme getScheme() {
      return new FindActiveRuleResVoTupleScheme();
    }
  }

  private static class FindActiveRuleResVoTupleScheme extends TupleScheme<FindActiveRuleResVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, FindActiveRuleResVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetFindActiveList()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetId()) {
        oprot.writeString(struct.id);
      }
      if (struct.isSetFindActiveList()) {
        {
          oprot.writeI32(struct.findActiveList.size());
          for (FindActiveVo _iter236 : struct.findActiveList)
          {
            _iter236.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, FindActiveRuleResVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.id = iprot.readString();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list237 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.findActiveList = new ArrayList<FindActiveVo>(_list237.size);
          FindActiveVo _elem238;
          for (int _i239 = 0; _i239 < _list237.size; ++_i239)
          {
            _elem238 = new FindActiveVo();
            _elem238.read(iprot);
            struct.findActiveList.add(_elem238);
          }
        }
        struct.setFindActiveListIsSet(true);
      }
    }
  }

}

