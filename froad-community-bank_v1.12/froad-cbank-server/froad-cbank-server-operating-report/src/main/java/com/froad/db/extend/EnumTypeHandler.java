package com.froad.db.extend;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumSet;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * 自定义枚举类型转换器
 * 
 * @author FQ
 * 
 */
public class EnumTypeHandler extends BaseTypeHandler<Enum> implements
		TypeHandler<Enum> {

	private Class<Enum> clazz;

	public EnumTypeHandler(Class<Enum> clazz) {
		this.clazz = clazz;
	}
	
	/** 
     * 用于定义在Mybatis设置参数时该如何把Java类型的参数转换为对应的数据库类型 
     * @param ps 当前的PreparedStatement对象 
     * @param i 当前参数的位置 
     * @param parameter 当前参数的Java对象 
     * @param jdbcType 当前参数的数据库类型 
     * @throws SQLException 
     */  
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
			Enum parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, parameter.toString());
	}

	/** 
     * 用于在Mybatis获取数据结果集时如何把数据库类型转换为对应的Java类型 
     * @param rs 当前的结果集 
     * @param columnName 当前的字段名称 
     * @return 转换后的Java对象 
     * @throws SQLException 
     */  
	@Override
	public Enum getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		// 根据数据库存储类型决定获取类型
		String val = rs.getString(columnName);
		if (!rs.wasNull()) {
			return locateEnum(val);
		} else {
			return null;
		}
	}

	/** 
     * 用于在Mybatis通过字段位置获取字段数据时把数据库类型转换为对应的Java类型 
     * @param rs 当前的结果集 
     * @param columnIndex 当前字段的位置 
     * @return 转换后的Java对象 
     * @throws SQLException 
     */  
	@Override
	public Enum getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		// 根据数据库存储类型决定获取类型
		String val = rs.getString(columnIndex);
		if (!rs.wasNull()) {
			return locateEnum(val);
		} else {
			return null;
		}
	}

	/** 
     * 用于Mybatis在调用存储过程后把数据库类型的数据转换为对应的Java类型 
     * @param cs 当前的CallableStatement执行后的CallableStatement 
     * @param columnIndex 当前输出参数的位置 
     * @return 
     * @throws SQLException 
     */  
	@Override
	public Enum getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		// 根据数据库存储类型决定获取类型
		String val = cs.getString(columnIndex);
		if (!cs.wasNull()) {
			return locateEnum(val);
		} else {
			return null;
		}
	}

	private Enum locateEnum(String code) {
		EnumSet enumSet = EnumSet.allOf(clazz);
		for (Object object : enumSet) {
			if (object instanceof Enum) {
				Enum e = (Enum) object;
				if (e.toString().equals(code)) {
					return e;
				}
			}
		}
		throw new IllegalArgumentException("未知的枚举类型：" + code + ",请核对"
				+ clazz.getSimpleName());

	}

}
