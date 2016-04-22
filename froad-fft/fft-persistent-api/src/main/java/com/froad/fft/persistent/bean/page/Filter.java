package com.froad.fft.persistent.bean.page;

import java.io.Serializable;
import java.util.Date;

/**
 * 筛选
 * 
 * @author FQ
 *
 */
public class Filter implements Serializable {

	/**
	 * 运算符
	 */
	public enum Operator {

		/** 等于 */
		eq("="),

		/** 不等于 */
		ne("<>"),

		/** 大于 */
		gt(">"),

		/** 小于 */
		lt("<"),

		/** 大于等于 */
		ge(">="),

		/** 小于等于 */
		le("<="),

		/** 相似 */
		like("like"),

		/** 包含 */
		in("in"),

		/** 为Null */
		isNull("is null"),

		/** 不为Null */
		isNotNull("is not null"),
		
		/** 时间between */
		bt("between");
		
		private String sqlCode;

		private Operator(String sqlCode){
			this.sqlCode=sqlCode;
		}
		
		

		/**
		 * 从String中获取Operator
		 * 
		 * @param value
		 *            值
		 * @return String对应的operator
		 */
		public static Operator fromString(String value) {
			return Operator.valueOf(value.toLowerCase());
		}
		
		public String getSqlCode() {
			return sqlCode;
		}

	}
	

	/** 数据表 字段名称 */
	private String property;

	/** 运算符 */
	private String operator;
	
	/** sql关键字 */
	private String sqlCode;

	/** 值 */
	private Object value;
	
	/** 值 */
	private Object value2;

	
	/**
	 * 初始化一个新创建的Filter对象
	 */
	public Filter() {
	}

	/**
	 * 初始化一个新创建的Filter对象
	 * 
	 * @param property
	 *            数据表 字段名称
	 * @param operator
	 *            运算符
	 * @param value
	 *            值
	 */
	public Filter(String property, Operator operator, Object value) {
		this.property = property;
		this.operator = operator.toString();
		this.sqlCode = operator.getSqlCode();
		this.value = value;
	}
	
	public Filter(String property, Operator operator, Object value,Object value2) {
		this.property = property;
		this.operator = operator.toString();
		this.sqlCode = operator.getSqlCode();
		this.value = value;
		this.value2 = value2;
	}

	/**
	 * 返回等于筛选
	 * 
	 * @param property
	 *            数据表 字段名称
	 * @param value
	 *            值
	 * @return 等于筛选
	 */
	public static Filter eq(String property, Object value) {
		return new Filter(property, Operator.eq, value);
	}

	/**
	 * 返回不等于筛选
	 * 
	 * @param property
	 *            数据表 字段名称
	 * @param value
	 *            值
	 * @return 不等于筛选
	 */
	public static Filter ne(String property, Object value) {
		return new Filter(property, Operator.ne, value);
	}
	
	/**
	 * 返回大于筛选
	 * 
	 * @param property
	 *            数据表 字段名称
	 * @param value
	 *            值
	 * @return 大于筛选
	 */
	public static Filter gt(String property, Object value) {
		return new Filter(property, Operator.gt, value);
	}

	/**
	 * 返回小于筛选
	 * 
	 * @param property
	 *            数据表 字段名称
	 * @param value
	 *            值
	 * @return 小于筛选
	 */
	public static Filter lt(String property, Object value) {
		return new Filter(property, Operator.lt, value);
	}

	/**
	 * 返回大于等于筛选
	 * 
	 * @param property
	 *            数据表 字段名称
	 * @param value
	 *            值
	 * @return 大于等于筛选
	 */
	public static Filter ge(String property, Object value) {
		return new Filter(property, Operator.ge, value);
	}

	/**
	 * 返回小于等于筛选
	 * 
	 * @param property
	 *            数据表 字段名称
	 * @param value
	 *            值
	 * @return 小于等于筛选
	 */
	public static Filter le(String property, Object value) {
		return new Filter(property, Operator.le, value);
	}

	/**
	 * 返回相似筛选
	 * 
	 * @param property
	 *            数据表 字段名称
	 * @param value
	 *            值
	 * @return 相似筛选
	 */
	public static Filter like(String property, Object value) {
		return new Filter(property, Operator.like, value);
	}

	/**
	 * 返回包含筛选
	 * 
	 * @param property
	 *            数据表 字段名称
	 * @param value
	 *            值
	 * @return 包含筛选
	 */
	public static Filter in(String property, Object value) {
		return new Filter(property, Operator.in, value);
	}

	/**
	 * 返回为Null筛选
	 * 
	 * @param property
	 *            数据表 字段名称
	 * @return 为Null筛选
	 */
	public static Filter isNull(String property) {
		return new Filter(property, Operator.isNull, null);
	}

	/**
	 * 返回不为Null筛选
	 * 
	 * @param property
	 *            数据表 字段名称
	 * @return 不为Null筛选
	 */
	public static Filter isNotNull(String property) {
		return new Filter(property, Operator.isNotNull, null);
	}
	
	/**
	 * 返回不为Null筛选
	 * 
	 * @param property
	 *            数据表 字段名称
	 * @return 不为Null筛选
	 */
	public static Filter bt(String property,Date star,Date end) {
		return new Filter(property, Operator.bt,star,end);
	}


	/**
	 * 获取数据表 字段名称
	 * 
	 * @return 数据表 字段名称
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * 设置数据表 字段名称
	 * 
	 * @param property
	 *            数据表 字段名称
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/**
	 * 获取运算符
	 * 
	 * @return 运算符
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * 设置运算符
	 * 
	 * @param operator
	 *            运算符
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * 获取值
	 * 
	 * @return 值
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * 设置值
	 * 
	 * @param value
	 *            值
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	
	public Object getValue2() {
		return value2;
	}
	
	public void setValue2(Object value2) {
		this.value2 = value2;
	}
	
	public String getSqlCode() {
		return sqlCode;
	}
}
