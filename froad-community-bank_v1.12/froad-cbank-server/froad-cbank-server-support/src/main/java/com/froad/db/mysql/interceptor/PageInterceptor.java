package com.froad.db.mysql.interceptor;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.xml.bind.PropertyException;

import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;

import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.dialet.Dialect;
import com.froad.db.mysql.util.ReflectUtil;
import com.froad.logback.LogCvt;

/**
 * @Description 分页插件
 * @author FQ
 * @date 2014年9月15日 下午3:52:11
 * @version 1.0
 */

@Intercepts({ @Signature(type = StatementHandler.class, 
							method = "prepare", args = { Connection.class }) })
public class PageInterceptor implements Interceptor {
	
	private static Dialect database_Dialect=null;// 数据库方言
	private static String pageSqlId_RegEx="";// mapper.xml中需要拦截的ID(正则匹配)

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
					throw new NullPointerException("parameterObject is null！");
				} else {
					
					// 拦截到的prepare方法参数是一个Connection对象
					Connection connection = (Connection) invocation.getArgs()[0];

					// 获取当前要执行的Sql语句，也就是我们直接在Mapper映射语句中写的Sql语句
					String sql = boundSql.getSql();

					// 记录统计
					String countSql = this.getCountSql(sql);
					
					countSql = countSql.replaceAll("\t"," ");
					countSql = countSql.replaceAll("\n"," ");
					LogCvt.debug("统计 sql:"+countSql);
					
					// 通过connection建立一个countSql对应的PreparedStatement对象。
					PreparedStatement countStmt = connection.prepareStatement(countSql);

					DefaultParameterHandler parameterHandler = new DefaultParameterHandler(
							mappedStatement, parameterObject, boundSql);
					parameterHandler.setParameters(countStmt);
					
					// 之后就是执行获取总记录数的Sql语句和获取结果了
					ResultSet rs = countStmt.executeQuery();
					int count = 0;
					if (rs.next()) {
						count = rs.getInt(1);
					}
					rs.close();
					countStmt.close();
					
					Page page = null;
					if (parameterObject instanceof Page) { // 参数就是Page实体
						page = (Page) parameterObject;
						
						//给当前的参数page对象设置总记录数 
						page.setTotalCount(count);
					}
					else if(parameterObject instanceof Map){
						for (Entry entry : (Set<Entry>) ((Map) parameterObject)
								.entrySet()) {
							if (entry.getValue() instanceof Page) {
								page = (Page) entry.getValue();
								break;
							}
						}
						page.setTotalCount(count);
					}
					else {
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
					
					pageSql = pageSql.replaceAll("\t","");
					pageSql = pageSql.replaceAll("\n","");
					LogCvt.debug("分页 sql:"+pageSql);
					
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
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	/**
	 * 设置拦截器时设定的属性
	 */
	public void setProperties(Properties properties) {

		String databaseDialect = ""; // 数据库方言
		databaseDialect = properties.getProperty("databaseDialect");
		if (null == databaseDialect || "".equals(databaseDialect)
				|| "".equals(databaseDialect.trim())
				|| "null".equalsIgnoreCase(databaseDialect)) {
			try {
				throw new PropertyException(
						"databaseDialect property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		} else {
			try {
				database_Dialect = (Dialect) Class.forName(databaseDialect)
						.getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				throw new RuntimeException(databaseDialect + ", init fail!\n"
						+ e);
			}
		}
		
		pageSqlId_RegEx = properties.getProperty("pageSqlIdRegEx");// 根据id来区分是否需要分页
		if (null == pageSqlId_RegEx || "".equals(pageSqlId_RegEx)
				|| "".equals(pageSqlId_RegEx.trim())
				|| "null".equalsIgnoreCase(pageSqlId_RegEx)) {
			try {
				throw new PropertyException(
						"pageSqlIdRegEx property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		}
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
	
	/**
	 * 根据数据库方言，生成特定的分页sql
	 * 
	 * @param page
	 * @param sql
	 * @return
	 */
	private String generatePageSql(Page page, String sql) {

		if (page != null && database_Dialect != null) {
			// pageNumber默认是从1，而已数据库是从0开始计算的．所以(page.getPageNumber()-1)
			int pageNumber = page.getPageNumber();
			return database_Dialect.getLimitString(sql, (pageNumber <= 0 ? 0
					: pageNumber - 1) * page.getPageSize(), page.getPageSize());
		}
		return sql;
	}

}


