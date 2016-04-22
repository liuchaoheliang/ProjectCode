package com.froad.fft.persistent.interceptor;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.apache.log4j.Logger;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.util.ReflectUtil;

/**
 * 分页 拦截器
 * 
 * @author FQ
 * 
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PageInterceptor implements Interceptor {

	private static Logger logger = Logger.getLogger(PageInterceptor.class);
	
	private static String database_Type;// 数据库类型，不同的数据库有不同的分页方法
	private static String pageSqlId_RegEx;// mapper.xml中需要拦截的ID(正则匹配)

	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		if (invocation.getTarget() instanceof RoutingStatementHandler) {
			RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation
					.getTarget();

			// 通过反射获取到当前RoutingStatementHandler对象的delegate属性
			StatementHandler delegate = (StatementHandler) ReflectUtil
					.getFieldValue(statementHandler, "delegate");

			// 通过反射获取delegate父类BaseStatementHandler的mappedStatement属性
			MappedStatement mappedStatement = (MappedStatement) ReflectUtil
					.getFieldValue(delegate, "mappedStatement");

			// 拦截需要分页的SQL
			if (mappedStatement.getId().matches(pageSqlId_RegEx)) {

				// 获取到当前StatementHandler的 boundSql
				BoundSql boundSql = delegate.getBoundSql();

				// 拿到当前绑定Sql的参数对象，就是我们在调用对应的Mapper映射语句时所传入的参数对象
				// 分页SQL<select>中parameterType属性对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为空
				Object parameterObject = boundSql.getParameterObject();

				if (parameterObject == null) {
					throw new NullPointerException("parameterObject 尚未实例化！");
				} else {

					// 拦截到的prepare方法参数是一个Connection对象
//					Connection connection = (Connection) invocation.getArgs()[0];

					// 获取当前要执行的Sql语句，也就是我们直接在Mapper映射语句中写的Sql语句
					String sql = boundSql.getSql();

					// 记录统计
//					String countSql = this.getCountSql(sql);
//					
//					if(logger.isDebugEnabled()){
//						countSql = countSql.replaceAll("\t","");
//						countSql = countSql.replaceAll("\n","");
//						logger.info("统计 sql:"+countSql);
//					}
//
//					// 通过connection建立一个countSql对应的PreparedStatement对象。
//					PreparedStatement countStmt = connection.prepareStatement(countSql);
//
//					BoundSql countBS = mappedStatement.getBoundSql(parameterObject);
//
//					setParameters(countStmt, mappedStatement, countBS,parameterObject);
//					
//					// 之后就是执行获取总记录数的Sql语句和获取结果了
//					ResultSet rs = countStmt.executeQuery();
					int count = 0;
//					if (rs.next()) {
//						count = rs.getInt(1);
//					}
//					rs.close();
//					countStmt.close();
//
					Page page = null;
					if (parameterObject instanceof Page) { // 参数就是Page实体
						page = (Page) parameterObject;
						
						//给当前的参数page对象设置总记录数 
						page.setTotalCount(count);
					} else {
						// 参数为某个实体，该实体拥有Page属性
						Field pageField = ReflectUtil.getField(parameterObject,"page");
						
						if (pageField != null) {
							page = (Page) ReflectUtil.getFieldValue(parameterObject, "page");
							if (page == null) {
								page = new Page();
							}
							page.setTotalCount(count);

							// 通过反射，对实体对象设置分页对象
							ReflectUtil.setFieldValue(parameterObject, "page",page);
						} else {
							throw new NoSuchFieldException(parameterObject
									.getClass().getName() + "不存在 page 属性！");
						}
					}

					// 获取分页Sql语句
					String pageSql = this.generatePageSql(page, sql);
					
					if(logger.isDebugEnabled()){
						pageSql = pageSql.replaceAll("\t","");
						pageSql = pageSql.replaceAll("\n","");
						logger.info("分页 sql:"+pageSql);
					}
					
					// 利用反射设置当前BoundSql对应的sql属性为我们建立好的分页Sql语句
					ReflectUtil.setFieldValue(boundSql, "sql", pageSql);
				}

			}
		}
		return invocation.proceed();
	}

	/**
	 * 拦截器对应的封装原始对象的方法
	 */
	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	/**
	 * 设置拦截器时设定的属性
	 */
	@Override
	public void setProperties(Properties properties) {
		this.database_Type = properties.getProperty("databaseType");
		this.pageSqlId_RegEx = properties.getProperty("pageSqlIdRegEx");
	}
	
	/** 
     * 根据page对象获取对应的分页查询Sql语句
     * 其它的数据库都 没有进行分页 
     * 
     * @param page 分页对象 
     * @param sql 原sql语句 
     * @return 
     */  
	private String generatePageSql(Page<?> page, String sql) {
		StringBuffer sqlBuffer = new StringBuffer(sql);
		if ("mysql".equalsIgnoreCase(database_Type)) {
			return getMysqlPageSql(page, sqlBuffer);
		} 
		else if ("oracle".equalsIgnoreCase(database_Type)) {
			return getOraclePageSql(page, sqlBuffer);
		}
		return sqlBuffer.toString();
	}
	
	/**
	 * 获取Mysql数据库的分页查询语句
	 * 
	 * @param page
	 *            分页对象
	 * @param sqlBuffer
	 *            包含原sql语句的StringBuffer对象
	 * @return Mysql数据库分页语句
	 */
	private String getMysqlPageSql(Page<?> page, StringBuffer sqlBuffer) {
		// 计算第一条记录的位置，Mysql中记录的位置是从0开始的。
		int offset = (page.getPageNumber() - 1) * page.getPageSize();
		sqlBuffer.append(" limit ").append(offset).append(",")
				.append(page.getPageSize());
		return sqlBuffer.toString();
	} 
     
    /** 
     * 获取Oracle数据库的分页查询语句 
     * @param page 分页对象 
     * @param sqlBuffer 包含原sql语句的StringBuffer对象 
     * @return Oracle数据库的分页查询语句 
     */  
    private String getOraclePageSql(Page<?> page, StringBuffer sqlBuffer) {  
       //计算第一条记录的位置，Oracle分页是通过rownum进行的，而rownum是从1开始的  
       int offset = (page.getPageNumber() - 1) * page.getPageSize() + 1;  
       sqlBuffer.insert(0, "select u.*, rownum r from (").append(") u where rownum < ").append(offset + page.getPageSize());  
       sqlBuffer.insert(0, "select * from (").append(") where r >= ").append(offset);  
       
       //上面的Sql语句拼接之后大概是这个样子：  
       //select * from (select u.*, rownum r from (select * from t_user) u where rownum < 31) where r >= 16  
       
       return sqlBuffer.toString();  
    }  

	/**
	 * 根据原Sql语句获取对应的查询总记录数的Sql语句
	 * 
	 * @param sql
	 * @return
	 */
	private String getCountSql(String sql) {
		int index = sql.indexOf("from");
		return "select count(0) " + sql.substring(index);
	}
	
	private void setParameters(PreparedStatement ps,
			MappedStatement mappedStatement, BoundSql boundSql,
			Object parameterObject) throws SQLException {

		ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
		
		//通过BoundSql获取对应的参数映射  
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		
		if (parameterMappings != null) {
			Configuration configuration = mappedStatement.getConfiguration();
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
			
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					PropertyTokenizer prop = new PropertyTokenizer(propertyName);
					if (parameterObject == null) {
						value = null;
					} 
					else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} 
					else if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} 
					else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX) && boundSql.hasAdditionalParameter(prop.getName())) {
						value = boundSql.getAdditionalParameter(prop.getName());
						if (value != null) {
							value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
						}
					} else {
						value = metaObject == null ? null : metaObject.getValue(propertyName);
					}
					TypeHandler typeHandler = parameterMapping.getTypeHandler();
					
					if (typeHandler == null) {
						throw new ExecutorException(
								"There was no TypeHandler found for parameter "
										+ propertyName + " of statement "
										+ mappedStatement.getId());
					}
					
					typeHandler.setParameter(ps, i + 1, value,parameterMapping.getJdbcType());
				}
			}
		}
	}

}
