/*   
 * Copyright © 2008 F-Road All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */

/**  
 * @Title: ModifyLikeSqlInterceptor.java
 * @Package com.froad.db.mysql.interceptor
 * @Description: TODO
 * @author vania
 * @date 2015年3月25日
 */

package com.froad.db.mysql.interceptor;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.session.Configuration;

import com.froad.db.mysql.util.ReflectUtil;

/**
 * Mybatis SQL转义字符与like 查询
 * <p>Title: ModifyLikeSqlInterceptor.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年5月26日 下午4:20:45
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class ModifyLikeSqlInterceptor implements Interceptor {
	private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
	/**
	 * @param arg0
	 * @return
	 * @throws Throwable
	 * @see org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin.Invocation)
	 */

	@Override
	public Object intercept(Invocation  invocation) throws Throwable {
		
//		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
//        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY,DEFAULT_OBJECT_WRAPPER_FACTORY);
		MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
//		MetaObject metaMappedStatement = MetaObject.forObject(mappedStatement, DEFAULT_OBJECT_FACTORY2,DEFAULT_OBJECT_WRAPPER_FACTORY2);
		MetaObject metaMappedStatement = MetaObject.forObject(mappedStatement, DEFAULT_OBJECT_FACTORY,DEFAULT_OBJECT_WRAPPER_FACTORY);
		
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        if (SqlCommandType.SELECT == sqlCommandType){    	
        
	        BoundSql boundSql = statementHandler.getBoundSql();//原始sql对象
			// 修改参数值
	        try {
	        	SqlSource sqlSource = (SqlSource)metaMappedStatement.getValue("sqlSource");
	        	// String.class.isAssignableFrom(clazz)
	        	if(DynamicSqlSource.class.isAssignableFrom(sqlSource.getClass())){
		        	SqlNode sqlNode = (SqlNode) metaMappedStatement.getValue("sqlSource.rootSqlNode");
		        	
		        	BoundSql boundSqlConvert = getBoundSql(mappedStatement.getConfiguration(),  boundSql.getParameterObject(), sqlNode); 
//		        	boundSql = boundSqlConvert;
		        	ReflectUtil.setFieldValue(boundSql, "sql", boundSqlConvert.getSql());
//		        	System.out.println("boundSql.getSql()==>" + (boundSql.getSql()));
//		        	System.out.println("boundSqlConvert.getSql()==>" + (boundSql.getSql()));
	        	}
			} catch (Exception e) {
//				e.printStackTrace();
			}
        }
		
		// 将执行权交给下一个拦截器
		return invocation.proceed();
	}

	/**
	 * 拦截器对应的封装原始对象的方法
	 * 
	 * @param arg0
	 * @return
	 * @see org.apache.ibatis.plugin.Interceptor#plugin(java.lang.Object)
	 */

	@Override
	public Object plugin(Object target) {
//		return Plugin.wrap(target, this);
		// 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的次数
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }

	}

	/**
	 * @param arg0
	 * @see org.apache.ibatis.plugin.Interceptor#setProperties(java.util.Properties)
	 */

	@Override
	public void setProperties(Properties arg0) {
		// TODO Auto-generated method stub

	}
	
	public static BoundSql getBoundSql(Configuration configuration,Object parameterObject,SqlNode sqlNode) {
	    DynamicContext context = new DynamicContext(configuration, parameterObject);
		//DynamicContext context = new DynamicContext(mappedStatement.getConfiguration(), boundSql.getParameterObject());
		//mappedStatement.getSqlSource().
	
		sqlNode.apply(context);
		String countextSql=context.getSql();
//		System.out.println("context.getSql():"+countextSql);	    
	
	    SqlSourceBuilder sqlSourceParser = new SqlSourceBuilder(configuration);
	    Class<?> parameterType = parameterObject == null ? Object.class : parameterObject.getClass();
	    String sql = modifyLikeSql(countextSql, parameterObject);
	    SqlSource sqlSource = sqlSourceParser.parse(sql, parameterType, context.getBindings());
	    
	   
	    BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
	    for (Map.Entry<String, Object> entry : context.getBindings().entrySet()) {
	      boundSql.setAdditionalParameter(entry.getKey(), entry.getValue());
	    }
	    
	    return boundSql;
	  }
	
	
	public static String modifyLikeSql(String sql,Object parameterObject)
	{
//		if(Map.class.isAssignableFrom(parameterObject.getClass())) {
////		if(parameterObject instanceof HashMap){
//		}else{
//			return sql;			
//		}
		if(!sql.toLowerCase().contains("like"))
			return sql;
		 //sql=" and OPER_REMARK LIKE '%' || #{operRemark} || '%'  \n " +"and OPER_U_NAME LIKE #{operUName} || '%' ";
		//原始表达式：\s\w+\sLIKE\s('%'\s\|{2})?\s*(#\{\w+\})\s*(\|{2}\s*'%')
		// CONCAT('%',#{outletName},'%' )
		String reg = null;
//		reg="\\s\\w+\\sLIKE\\s*('%'\\s*\\|{2}\\s*)?(#\\{\\w+\\})(\\s*\\|{2}\\s*'%')?";//"order\\s+by\\s+.+"
//		reg="\\s\\w+\\sLIKE\\s*('%'\\s*\\|{2}\\s*)?(#\\{\\w+\\})(\\s*\\|{2}\\s*'%')?(CONCAT\\(('%'\\s*\\,\\s*)?(\\s*#\\{\\w+\\})(\\s*\\,\\s*'%')\\))?";//"order\\s+by\\s+.+"
		reg="\\s\\w+\\sLIKE\\s*(CONCAT\\((\\s*'%'\\s*\\,\\s*)?(\\s*#\\{(.+)\\}\\s*)(\\s*\\,\\s*'%'\\s*)\\))?";//"order\\s+by\\s+.+"
//		reg=".*LIKE\\s*(CONCAT\\(('%'\\s*\\,\\s*)?(\\s*#\\{\\w+\\})(\\s*\\,\\s*'%')\\))?";//"order\\s+by\\s+.+"
		Pattern pattern = Pattern.compile(reg,Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(sql);
//		System.out.println("sql====>" + sql);
		
		List<String> replaceEscape=new ArrayList<String>();
		List<String> replaceFiled=new ArrayList<String>();
		
		while(matcher.find()){
//			System.out.println("matcher.group()====>" + matcher.group());
			replaceEscape.add(matcher.group());
			 int n = matcher.groupCount();  
             for (int i = 0; i <= n; i++)
             {  
                String  output = matcher.group(i);  
//                System.out.println("i=====>"+i+"\t output====>" + output);
//                if(2==i&&output!=null)
                	if(3==i&&output!=null)
                {
                	replaceFiled.add(output.trim());
                }
             }  
	       }

		//sql = matcher.replaceAll(reg+" 1111");
		
		for(String s:replaceEscape)
		{
			sql=sql.replace(s, s+" ESCAPE '/' ");
		}
		//修改参数
//		Map<String,Object> paramMab = null;
//		if(Map.class.isAssignableFrom(parameterObject.getClass())) {
//			paramMab=(Map)parameterObject;
//		}
		MetaObject parameterObjectHandler = MetaObject.forObject(parameterObject, DEFAULT_OBJECT_FACTORY,DEFAULT_OBJECT_WRAPPER_FACTORY);
		
		for(String s:replaceFiled)
		{
			//sql=sql.replace(s, " ? ");
			// #{operUName} -->operUName
			String key=s.replace("#{", "").replace("}", "");
			
			Object val = null;
			val = parameterObjectHandler.getValue(key);
//			if(null != paramMab && !paramMab.isEmpty()) {
//				val = paramMab.get(key);
//			} else {				
//				val = ReflectUtil.getFieldValue(parameterObject, key);
//			}
			if(val!=null &&val instanceof String&&(val.toString().contains("%")||val.toString().contains("_")))
			{
				val=val.toString().replaceAll("%", "/%").replaceAll("_", "/_");
				parameterObjectHandler.setValue(key, val);
//				if(null != paramMab && !paramMab.isEmpty()) {
//					paramMab.put(key.toString(), val);
//				} else {
//					ReflectUtil.setFieldValue(parameterObject, key.toString(), val);
//				}
			}			
		}	
//		System.out.println("修改之后的sql====>" + sql);
		return sql;   
	}
}
