/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.froad.thrift.vo.refund;

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
 * 券过期自动退款requestVo
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class RefundTicketsRequestVo implements org.apache.thrift.TBase<RefundTicketsRequestVo, RefundTicketsRequestVo._Fields>, java.io.Serializable, Cloneable, Comparable<RefundTicketsRequestVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("RefundTicketsRequestVo");

  private static final org.apache.thrift.protocol.TField TICKET_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("ticketList", org.apache.thrift.protocol.TType.LIST, (short)1);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new RefundTicketsRequestVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new RefundTicketsRequestVoTupleSchemeFactory());
  }

  /**
   * 过期券
   */
  public List<RefundTicketVo> ticketList; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 过期券
     */
    TICKET_LIST((short)1, "ticketList");

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
        case 1: // TICKET_LIST
          return TICKET_LIST;
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
    tmpMap.put(_Fields.TICKET_LIST, new org.apache.thrift.meta_data.FieldMetaData("ticketList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT            , "RefundTicketVo"))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(RefundTicketsRequestVo.class, metaDataMap);
  }

  public RefundTicketsRequestVo() {
  }

  public RefundTicketsRequestVo(
    List<RefundTicketVo> ticketList)
  {
    this();
    this.ticketList = ticketList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public RefundTicketsRequestVo(RefundTicketsRequestVo other) {
    if (other.isSetTicketList()) {
      List<RefundTicketVo> __this__ticketList = new ArrayList<RefundTicketVo>(other.ticketList.size());
      for (RefundTicketVo other_element : other.ticketList) {
        __this__ticketList.add(other_element);
      }
      this.ticketList = __this__ticketList;
    }
  }

  public RefundTicketsRequestVo deepCopy() {
    return new RefundTicketsRequestVo(this);
  }

  @Override
  public void clear() {
    this.ticketList = null;
  }

  public int getTicketListSize() {
    return (this.ticketList == null) ? 0 : this.ticketList.size();
  }

  public java.util.Iterator<RefundTicketVo> getTicketListIterator() {
    return (this.ticketList == null) ? null : this.ticketList.iterator();
  }

  public void addToTicketList(RefundTicketVo elem) {
    if (this.ticketList == null) {
      this.ticketList = new ArrayList<RefundTicketVo>();
    }
    this.ticketList.add(elem);
  }

  /**
   * 过期券
   */
  public List<RefundTicketVo> getTicketList() {
    return this.ticketList;
  }

  /**
   * 过期券
   */
  public RefundTicketsRequestVo setTicketList(List<RefundTicketVo> ticketList) {
    this.ticketList = ticketList;
    return this;
  }

  public void unsetTicketList() {
    this.ticketList = null;
  }

  /** Returns true if field ticketList is set (has been assigned a value) and false otherwise */
  public boolean isSetTicketList() {
    return this.ticketList != null;
  }

  public void setTicketListIsSet(boolean value) {
    if (!value) {
      this.ticketList = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TICKET_LIST:
      if (value == null) {
        unsetTicketList();
      } else {
        setTicketList((List<RefundTicketVo>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TICKET_LIST:
      return getTicketList();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case TICKET_LIST:
      return isSetTicketList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof RefundTicketsRequestVo)
      return this.equals((RefundTicketsRequestVo)that);
    return false;
  }

  public boolean equals(RefundTicketsRequestVo that) {
    if (that == null)
      return false;

    boolean this_present_ticketList = true && this.isSetTicketList();
    boolean that_present_ticketList = true && that.isSetTicketList();
    if (this_present_ticketList || that_present_ticketList) {
      if (!(this_present_ticketList && that_present_ticketList))
        return false;
      if (!this.ticketList.equals(that.ticketList))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_ticketList = true && (isSetTicketList());
    list.add(present_ticketList);
    if (present_ticketList)
      list.add(ticketList);

    return list.hashCode();
  }

  @Override
  public int compareTo(RefundTicketsRequestVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetTicketList()).compareTo(other.isSetTicketList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTicketList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ticketList, other.ticketList);
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
    StringBuilder sb = new StringBuilder("RefundTicketsRequestVo(");
    boolean first = true;

    sb.append("ticketList:");
    if (this.ticketList == null) {
      sb.append("null");
    } else {
      sb.append(this.ticketList);
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

  private static class RefundTicketsRequestVoStandardSchemeFactory implements SchemeFactory {
    public RefundTicketsRequestVoStandardScheme getScheme() {
      return new RefundTicketsRequestVoStandardScheme();
    }
  }

  private static class RefundTicketsRequestVoStandardScheme extends StandardScheme<RefundTicketsRequestVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, RefundTicketsRequestVo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TICKET_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list32 = iprot.readListBegin();
                struct.ticketList = new ArrayList<RefundTicketVo>(_list32.size);
                RefundTicketVo _elem33;
                for (int _i34 = 0; _i34 < _list32.size; ++_i34)
                {
                  _elem33 = new RefundTicketVo();
                  _elem33.read(iprot);
                  struct.ticketList.add(_elem33);
                }
                iprot.readListEnd();
              }
              struct.setTicketListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, RefundTicketsRequestVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.ticketList != null) {
        oprot.writeFieldBegin(TICKET_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.ticketList.size()));
          for (RefundTicketVo _iter35 : struct.ticketList)
          {
            _iter35.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class RefundTicketsRequestVoTupleSchemeFactory implements SchemeFactory {
    public RefundTicketsRequestVoTupleScheme getScheme() {
      return new RefundTicketsRequestVoTupleScheme();
    }
  }

  private static class RefundTicketsRequestVoTupleScheme extends TupleScheme<RefundTicketsRequestVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, RefundTicketsRequestVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetTicketList()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetTicketList()) {
        {
          oprot.writeI32(struct.ticketList.size());
          for (RefundTicketVo _iter36 : struct.ticketList)
          {
            _iter36.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, RefundTicketsRequestVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list37 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.ticketList = new ArrayList<RefundTicketVo>(_list37.size);
          RefundTicketVo _elem38;
          for (int _i39 = 0; _i39 < _list37.size; ++_i39)
          {
            _elem38 = new RefundTicketVo();
            _elem38.read(iprot);
            struct.ticketList.add(_elem38);
          }
        }
        struct.setTicketListIsSet(true);
      }
    }
  }

}

