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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class TaskPageVoRes implements org.apache.thrift.TBase<TaskPageVoRes, TaskPageVoRes._Fields>, java.io.Serializable, Cloneable, Comparable<TaskPageVoRes> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TaskPageVoRes");

  private static final org.apache.thrift.protocol.TField PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("page", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField TASK_VO_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("taskVoList", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TaskPageVoResStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TaskPageVoResTupleSchemeFactory());
  }

  public com.froad.thrift.vo.PageVo page; // optional
  public List<com.froad.thrift.vo.TaskVo> taskVoList; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PAGE((short)1, "page"),
    TASK_VO_LIST((short)2, "taskVoList");

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
        case 2: // TASK_VO_LIST
          return TASK_VO_LIST;
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
  private static final _Fields optionals[] = {_Fields.PAGE,_Fields.TASK_VO_LIST};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PAGE, new org.apache.thrift.meta_data.FieldMetaData("page", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.PageVo.class)));
    tmpMap.put(_Fields.TASK_VO_LIST, new org.apache.thrift.meta_data.FieldMetaData("taskVoList", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.froad.thrift.vo.TaskVo.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TaskPageVoRes.class, metaDataMap);
  }

  public TaskPageVoRes() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TaskPageVoRes(TaskPageVoRes other) {
    if (other.isSetPage()) {
      this.page = new com.froad.thrift.vo.PageVo(other.page);
    }
    if (other.isSetTaskVoList()) {
      List<com.froad.thrift.vo.TaskVo> __this__taskVoList = new ArrayList<com.froad.thrift.vo.TaskVo>(other.taskVoList.size());
      for (com.froad.thrift.vo.TaskVo other_element : other.taskVoList) {
        __this__taskVoList.add(new com.froad.thrift.vo.TaskVo(other_element));
      }
      this.taskVoList = __this__taskVoList;
    }
  }

  public TaskPageVoRes deepCopy() {
    return new TaskPageVoRes(this);
  }

  @Override
  public void clear() {
    this.page = null;
    this.taskVoList = null;
  }

  public com.froad.thrift.vo.PageVo getPage() {
    return this.page;
  }

  public TaskPageVoRes setPage(com.froad.thrift.vo.PageVo page) {
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

  public int getTaskVoListSize() {
    return (this.taskVoList == null) ? 0 : this.taskVoList.size();
  }

  public java.util.Iterator<com.froad.thrift.vo.TaskVo> getTaskVoListIterator() {
    return (this.taskVoList == null) ? null : this.taskVoList.iterator();
  }

  public void addToTaskVoList(com.froad.thrift.vo.TaskVo elem) {
    if (this.taskVoList == null) {
      this.taskVoList = new ArrayList<com.froad.thrift.vo.TaskVo>();
    }
    this.taskVoList.add(elem);
  }

  public List<com.froad.thrift.vo.TaskVo> getTaskVoList() {
    return this.taskVoList;
  }

  public TaskPageVoRes setTaskVoList(List<com.froad.thrift.vo.TaskVo> taskVoList) {
    this.taskVoList = taskVoList;
    return this;
  }

  public void unsetTaskVoList() {
    this.taskVoList = null;
  }

  /** Returns true if field taskVoList is set (has been assigned a value) and false otherwise */
  public boolean isSetTaskVoList() {
    return this.taskVoList != null;
  }

  public void setTaskVoListIsSet(boolean value) {
    if (!value) {
      this.taskVoList = null;
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

    case TASK_VO_LIST:
      if (value == null) {
        unsetTaskVoList();
      } else {
        setTaskVoList((List<com.froad.thrift.vo.TaskVo>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PAGE:
      return getPage();

    case TASK_VO_LIST:
      return getTaskVoList();

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
    case TASK_VO_LIST:
      return isSetTaskVoList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TaskPageVoRes)
      return this.equals((TaskPageVoRes)that);
    return false;
  }

  public boolean equals(TaskPageVoRes that) {
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

    boolean this_present_taskVoList = true && this.isSetTaskVoList();
    boolean that_present_taskVoList = true && that.isSetTaskVoList();
    if (this_present_taskVoList || that_present_taskVoList) {
      if (!(this_present_taskVoList && that_present_taskVoList))
        return false;
      if (!this.taskVoList.equals(that.taskVoList))
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

    boolean present_taskVoList = true && (isSetTaskVoList());
    list.add(present_taskVoList);
    if (present_taskVoList)
      list.add(taskVoList);

    return list.hashCode();
  }

  @Override
  public int compareTo(TaskPageVoRes other) {
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
    lastComparison = Boolean.valueOf(isSetTaskVoList()).compareTo(other.isSetTaskVoList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTaskVoList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.taskVoList, other.taskVoList);
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
    StringBuilder sb = new StringBuilder("TaskPageVoRes(");
    boolean first = true;

    if (isSetPage()) {
      sb.append("page:");
      if (this.page == null) {
        sb.append("null");
      } else {
        sb.append(this.page);
      }
      first = false;
    }
    if (isSetTaskVoList()) {
      if (!first) sb.append(", ");
      sb.append("taskVoList:");
      if (this.taskVoList == null) {
        sb.append("null");
      } else {
        sb.append(this.taskVoList);
      }
      first = false;
    }
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

  private static class TaskPageVoResStandardSchemeFactory implements SchemeFactory {
    public TaskPageVoResStandardScheme getScheme() {
      return new TaskPageVoResStandardScheme();
    }
  }

  private static class TaskPageVoResStandardScheme extends StandardScheme<TaskPageVoRes> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TaskPageVoRes struct) throws org.apache.thrift.TException {
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
          case 2: // TASK_VO_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.taskVoList = new ArrayList<com.froad.thrift.vo.TaskVo>(_list0.size);
                com.froad.thrift.vo.TaskVo _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = new com.froad.thrift.vo.TaskVo();
                  _elem1.read(iprot);
                  struct.taskVoList.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setTaskVoListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TaskPageVoRes struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.page != null) {
        if (struct.isSetPage()) {
          oprot.writeFieldBegin(PAGE_FIELD_DESC);
          struct.page.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.taskVoList != null) {
        if (struct.isSetTaskVoList()) {
          oprot.writeFieldBegin(TASK_VO_LIST_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.taskVoList.size()));
            for (com.froad.thrift.vo.TaskVo _iter3 : struct.taskVoList)
            {
              _iter3.write(oprot);
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

  private static class TaskPageVoResTupleSchemeFactory implements SchemeFactory {
    public TaskPageVoResTupleScheme getScheme() {
      return new TaskPageVoResTupleScheme();
    }
  }

  private static class TaskPageVoResTupleScheme extends TupleScheme<TaskPageVoRes> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TaskPageVoRes struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPage()) {
        optionals.set(0);
      }
      if (struct.isSetTaskVoList()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetPage()) {
        struct.page.write(oprot);
      }
      if (struct.isSetTaskVoList()) {
        {
          oprot.writeI32(struct.taskVoList.size());
          for (com.froad.thrift.vo.TaskVo _iter4 : struct.taskVoList)
          {
            _iter4.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TaskPageVoRes struct) throws org.apache.thrift.TException {
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
          struct.taskVoList = new ArrayList<com.froad.thrift.vo.TaskVo>(_list5.size);
          com.froad.thrift.vo.TaskVo _elem6;
          for (int _i7 = 0; _i7 < _list5.size; ++_i7)
          {
            _elem6 = new com.froad.thrift.vo.TaskVo();
            _elem6.read(iprot);
            struct.taskVoList.add(_elem6);
          }
        }
        struct.setTaskVoListIsSet(true);
      }
    }
  }

}

