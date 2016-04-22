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
 * 临时红包券码明细列表*
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-3-4")
public class TemporaryVouchersDetailInfoVO implements org.apache.thrift.TBase<TemporaryVouchersDetailInfoVO, TemporaryVouchersDetailInfoVO._Fields>, java.io.Serializable, Cloneable, Comparable<TemporaryVouchersDetailInfoVO> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TemporaryVouchersDetailInfoVO");

  private static final org.apache.thrift.protocol.TField ACTIVE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("activeId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField VOUCHERS_TOTAL_FIELD_DESC = new org.apache.thrift.protocol.TField("vouchersTotal", org.apache.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.thrift.protocol.TField VOUCHERS_COUPON_CODELIST_FIELD_DESC = new org.apache.thrift.protocol.TField("vouchersCouponCodelist", org.apache.thrift.protocol.TType.LIST, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TemporaryVouchersDetailInfoVOStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TemporaryVouchersDetailInfoVOTupleSchemeFactory());
  }

  /**
   * 临时券码活动ID *
   */
  public long activeId; // required
  /**
   * 券码总个数 *
   */
  public long vouchersTotal; // required
  /**
   * 红包券码明细列表 *
   */
  public List<VouchersCouponCodeVO> vouchersCouponCodelist; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 临时券码活动ID *
     */
    ACTIVE_ID((short)1, "activeId"),
    /**
     * 券码总个数 *
     */
    VOUCHERS_TOTAL((short)2, "vouchersTotal"),
    /**
     * 红包券码明细列表 *
     */
    VOUCHERS_COUPON_CODELIST((short)3, "vouchersCouponCodelist");

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
        case 1: // ACTIVE_ID
          return ACTIVE_ID;
        case 2: // VOUCHERS_TOTAL
          return VOUCHERS_TOTAL;
        case 3: // VOUCHERS_COUPON_CODELIST
          return VOUCHERS_COUPON_CODELIST;
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
  private static final int __ACTIVEID_ISSET_ID = 0;
  private static final int __VOUCHERSTOTAL_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ACTIVE_ID, new org.apache.thrift.meta_data.FieldMetaData("activeId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.VOUCHERS_TOTAL, new org.apache.thrift.meta_data.FieldMetaData("vouchersTotal", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.VOUCHERS_COUPON_CODELIST, new org.apache.thrift.meta_data.FieldMetaData("vouchersCouponCodelist", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT            , "VouchersCouponCodeVO"))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TemporaryVouchersDetailInfoVO.class, metaDataMap);
  }

  public TemporaryVouchersDetailInfoVO() {
  }

  public TemporaryVouchersDetailInfoVO(
    long activeId,
    long vouchersTotal,
    List<VouchersCouponCodeVO> vouchersCouponCodelist)
  {
    this();
    this.activeId = activeId;
    setActiveIdIsSet(true);
    this.vouchersTotal = vouchersTotal;
    setVouchersTotalIsSet(true);
    this.vouchersCouponCodelist = vouchersCouponCodelist;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TemporaryVouchersDetailInfoVO(TemporaryVouchersDetailInfoVO other) {
    __isset_bitfield = other.__isset_bitfield;
    this.activeId = other.activeId;
    this.vouchersTotal = other.vouchersTotal;
    if (other.isSetVouchersCouponCodelist()) {
      List<VouchersCouponCodeVO> __this__vouchersCouponCodelist = new ArrayList<VouchersCouponCodeVO>(other.vouchersCouponCodelist.size());
      for (VouchersCouponCodeVO other_element : other.vouchersCouponCodelist) {
        __this__vouchersCouponCodelist.add(other_element);
      }
      this.vouchersCouponCodelist = __this__vouchersCouponCodelist;
    }
  }

  public TemporaryVouchersDetailInfoVO deepCopy() {
    return new TemporaryVouchersDetailInfoVO(this);
  }

  @Override
  public void clear() {
    setActiveIdIsSet(false);
    this.activeId = 0;
    setVouchersTotalIsSet(false);
    this.vouchersTotal = 0;
    this.vouchersCouponCodelist = null;
  }

  /**
   * 临时券码活动ID *
   */
  public long getActiveId() {
    return this.activeId;
  }

  /**
   * 临时券码活动ID *
   */
  public TemporaryVouchersDetailInfoVO setActiveId(long activeId) {
    this.activeId = activeId;
    setActiveIdIsSet(true);
    return this;
  }

  public void unsetActiveId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ACTIVEID_ISSET_ID);
  }

  /** Returns true if field activeId is set (has been assigned a value) and false otherwise */
  public boolean isSetActiveId() {
    return EncodingUtils.testBit(__isset_bitfield, __ACTIVEID_ISSET_ID);
  }

  public void setActiveIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ACTIVEID_ISSET_ID, value);
  }

  /**
   * 券码总个数 *
   */
  public long getVouchersTotal() {
    return this.vouchersTotal;
  }

  /**
   * 券码总个数 *
   */
  public TemporaryVouchersDetailInfoVO setVouchersTotal(long vouchersTotal) {
    this.vouchersTotal = vouchersTotal;
    setVouchersTotalIsSet(true);
    return this;
  }

  public void unsetVouchersTotal() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __VOUCHERSTOTAL_ISSET_ID);
  }

  /** Returns true if field vouchersTotal is set (has been assigned a value) and false otherwise */
  public boolean isSetVouchersTotal() {
    return EncodingUtils.testBit(__isset_bitfield, __VOUCHERSTOTAL_ISSET_ID);
  }

  public void setVouchersTotalIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __VOUCHERSTOTAL_ISSET_ID, value);
  }

  public int getVouchersCouponCodelistSize() {
    return (this.vouchersCouponCodelist == null) ? 0 : this.vouchersCouponCodelist.size();
  }

  public java.util.Iterator<VouchersCouponCodeVO> getVouchersCouponCodelistIterator() {
    return (this.vouchersCouponCodelist == null) ? null : this.vouchersCouponCodelist.iterator();
  }

  public void addToVouchersCouponCodelist(VouchersCouponCodeVO elem) {
    if (this.vouchersCouponCodelist == null) {
      this.vouchersCouponCodelist = new ArrayList<VouchersCouponCodeVO>();
    }
    this.vouchersCouponCodelist.add(elem);
  }

  /**
   * 红包券码明细列表 *
   */
  public List<VouchersCouponCodeVO> getVouchersCouponCodelist() {
    return this.vouchersCouponCodelist;
  }

  /**
   * 红包券码明细列表 *
   */
  public TemporaryVouchersDetailInfoVO setVouchersCouponCodelist(List<VouchersCouponCodeVO> vouchersCouponCodelist) {
    this.vouchersCouponCodelist = vouchersCouponCodelist;
    return this;
  }

  public void unsetVouchersCouponCodelist() {
    this.vouchersCouponCodelist = null;
  }

  /** Returns true if field vouchersCouponCodelist is set (has been assigned a value) and false otherwise */
  public boolean isSetVouchersCouponCodelist() {
    return this.vouchersCouponCodelist != null;
  }

  public void setVouchersCouponCodelistIsSet(boolean value) {
    if (!value) {
      this.vouchersCouponCodelist = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ACTIVE_ID:
      if (value == null) {
        unsetActiveId();
      } else {
        setActiveId((Long)value);
      }
      break;

    case VOUCHERS_TOTAL:
      if (value == null) {
        unsetVouchersTotal();
      } else {
        setVouchersTotal((Long)value);
      }
      break;

    case VOUCHERS_COUPON_CODELIST:
      if (value == null) {
        unsetVouchersCouponCodelist();
      } else {
        setVouchersCouponCodelist((List<VouchersCouponCodeVO>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ACTIVE_ID:
      return Long.valueOf(getActiveId());

    case VOUCHERS_TOTAL:
      return Long.valueOf(getVouchersTotal());

    case VOUCHERS_COUPON_CODELIST:
      return getVouchersCouponCodelist();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ACTIVE_ID:
      return isSetActiveId();
    case VOUCHERS_TOTAL:
      return isSetVouchersTotal();
    case VOUCHERS_COUPON_CODELIST:
      return isSetVouchersCouponCodelist();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TemporaryVouchersDetailInfoVO)
      return this.equals((TemporaryVouchersDetailInfoVO)that);
    return false;
  }

  public boolean equals(TemporaryVouchersDetailInfoVO that) {
    if (that == null)
      return false;

    boolean this_present_activeId = true;
    boolean that_present_activeId = true;
    if (this_present_activeId || that_present_activeId) {
      if (!(this_present_activeId && that_present_activeId))
        return false;
      if (this.activeId != that.activeId)
        return false;
    }

    boolean this_present_vouchersTotal = true;
    boolean that_present_vouchersTotal = true;
    if (this_present_vouchersTotal || that_present_vouchersTotal) {
      if (!(this_present_vouchersTotal && that_present_vouchersTotal))
        return false;
      if (this.vouchersTotal != that.vouchersTotal)
        return false;
    }

    boolean this_present_vouchersCouponCodelist = true && this.isSetVouchersCouponCodelist();
    boolean that_present_vouchersCouponCodelist = true && that.isSetVouchersCouponCodelist();
    if (this_present_vouchersCouponCodelist || that_present_vouchersCouponCodelist) {
      if (!(this_present_vouchersCouponCodelist && that_present_vouchersCouponCodelist))
        return false;
      if (!this.vouchersCouponCodelist.equals(that.vouchersCouponCodelist))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_activeId = true;
    list.add(present_activeId);
    if (present_activeId)
      list.add(activeId);

    boolean present_vouchersTotal = true;
    list.add(present_vouchersTotal);
    if (present_vouchersTotal)
      list.add(vouchersTotal);

    boolean present_vouchersCouponCodelist = true && (isSetVouchersCouponCodelist());
    list.add(present_vouchersCouponCodelist);
    if (present_vouchersCouponCodelist)
      list.add(vouchersCouponCodelist);

    return list.hashCode();
  }

  @Override
  public int compareTo(TemporaryVouchersDetailInfoVO other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetActiveId()).compareTo(other.isSetActiveId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetActiveId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.activeId, other.activeId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetVouchersTotal()).compareTo(other.isSetVouchersTotal());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVouchersTotal()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.vouchersTotal, other.vouchersTotal);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetVouchersCouponCodelist()).compareTo(other.isSetVouchersCouponCodelist());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVouchersCouponCodelist()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.vouchersCouponCodelist, other.vouchersCouponCodelist);
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
    StringBuilder sb = new StringBuilder("TemporaryVouchersDetailInfoVO(");
    boolean first = true;

    sb.append("activeId:");
    sb.append(this.activeId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("vouchersTotal:");
    sb.append(this.vouchersTotal);
    first = false;
    if (!first) sb.append(", ");
    sb.append("vouchersCouponCodelist:");
    if (this.vouchersCouponCodelist == null) {
      sb.append("null");
    } else {
      sb.append(this.vouchersCouponCodelist);
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TemporaryVouchersDetailInfoVOStandardSchemeFactory implements SchemeFactory {
    public TemporaryVouchersDetailInfoVOStandardScheme getScheme() {
      return new TemporaryVouchersDetailInfoVOStandardScheme();
    }
  }

  private static class TemporaryVouchersDetailInfoVOStandardScheme extends StandardScheme<TemporaryVouchersDetailInfoVO> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TemporaryVouchersDetailInfoVO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ACTIVE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.activeId = iprot.readI64();
              struct.setActiveIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // VOUCHERS_TOTAL
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.vouchersTotal = iprot.readI64();
              struct.setVouchersTotalIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // VOUCHERS_COUPON_CODELIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list440 = iprot.readListBegin();
                struct.vouchersCouponCodelist = new ArrayList<VouchersCouponCodeVO>(_list440.size);
                VouchersCouponCodeVO _elem441;
                for (int _i442 = 0; _i442 < _list440.size; ++_i442)
                {
                  _elem441 = new VouchersCouponCodeVO();
                  _elem441.read(iprot);
                  struct.vouchersCouponCodelist.add(_elem441);
                }
                iprot.readListEnd();
              }
              struct.setVouchersCouponCodelistIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TemporaryVouchersDetailInfoVO struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(ACTIVE_ID_FIELD_DESC);
      oprot.writeI64(struct.activeId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(VOUCHERS_TOTAL_FIELD_DESC);
      oprot.writeI64(struct.vouchersTotal);
      oprot.writeFieldEnd();
      if (struct.vouchersCouponCodelist != null) {
        oprot.writeFieldBegin(VOUCHERS_COUPON_CODELIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.vouchersCouponCodelist.size()));
          for (VouchersCouponCodeVO _iter443 : struct.vouchersCouponCodelist)
          {
            _iter443.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TemporaryVouchersDetailInfoVOTupleSchemeFactory implements SchemeFactory {
    public TemporaryVouchersDetailInfoVOTupleScheme getScheme() {
      return new TemporaryVouchersDetailInfoVOTupleScheme();
    }
  }

  private static class TemporaryVouchersDetailInfoVOTupleScheme extends TupleScheme<TemporaryVouchersDetailInfoVO> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TemporaryVouchersDetailInfoVO struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetActiveId()) {
        optionals.set(0);
      }
      if (struct.isSetVouchersTotal()) {
        optionals.set(1);
      }
      if (struct.isSetVouchersCouponCodelist()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetActiveId()) {
        oprot.writeI64(struct.activeId);
      }
      if (struct.isSetVouchersTotal()) {
        oprot.writeI64(struct.vouchersTotal);
      }
      if (struct.isSetVouchersCouponCodelist()) {
        {
          oprot.writeI32(struct.vouchersCouponCodelist.size());
          for (VouchersCouponCodeVO _iter444 : struct.vouchersCouponCodelist)
          {
            _iter444.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TemporaryVouchersDetailInfoVO struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.activeId = iprot.readI64();
        struct.setActiveIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.vouchersTotal = iprot.readI64();
        struct.setVouchersTotalIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TList _list445 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.vouchersCouponCodelist = new ArrayList<VouchersCouponCodeVO>(_list445.size);
          VouchersCouponCodeVO _elem446;
          for (int _i447 = 0; _i447 < _list445.size; ++_i447)
          {
            _elem446 = new VouchersCouponCodeVO();
            _elem446.read(iprot);
            struct.vouchersCouponCodelist.add(_elem446);
          }
        }
        struct.setVouchersCouponCodelistIsSet(true);
      }
    }
  }

}
