package com.froad.CB.service.user.impl;

import java.sql.SQLException;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.AppException.AppException;
import com.froad.CB.dao.user.UserDao;
import com.froad.CB.dao.user.UserSuggestDao;
import com.froad.CB.po.user.Suggest;
import com.froad.CB.service.user.MUserSuggestService;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * 类描述：建议impl主类
	 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2011 
 * @author: zhuzhiwei zhuzhiwei@f-road.com.cn
 * @time: Aug 14, 2011 4:51:57 PM 
 */
@WebService(endpointInterface="com.froad.CB.service.user.MUserSuggestService")
public class MUserSuggestServiceImpl implements MUserSuggestService{
	private UserSuggestDao userSuggestDao;
	/**
	  * 方法描述：向数据库中插入建议
	  * @param: Suggest suggest(userID,username,suggesttittle,suggestcontext,submittime,transactionID)
	  * @return: Suggest suggest
	  * @version: 1.0
	  * @author: zhuzhiwei zhuzhiwei@f-road.com.cn
	  * @time: Aug 14, 2011 4:52:18 PM
	  */	
	public  Suggest add(Suggest suggest) throws AppException{
		return userSuggestDao.add(suggest);
	}
	/**
	  * 方法描述：
	  * @param: 异常处理
	  * @return: AppException
	  * @version: 1.0
	  * @author: zhuzhiwei zhuzhiwei@f-road.com.cn
	  * @time: Aug 14, 2011 4:52:18 PM
	  */
	public void handleException(Exception e) throws AppException {
		throw new AppException("抛出 "+e.getClass()+" 异常");
	}
	
	/**
	  * 方法描述：后台测试main
	  * @param: String
	  * @return: String
	  * @version: 1.0
	  * @author: zhuzhiwei zhuzhiwei@f-road.com.cn
	  * @time: Aug 14, 2011 4:53:23 PM
	  */
	public static void main(String[] args) throws AppException {
		MUserSuggestServiceImpl musersuggest= new MUserSuggestServiceImpl();
		
		Suggest suggest=new Suggest();
		suggest.setSuggestcontext("111");
		suggest.setSuggesttittle("t1后台测试");
		suggest.setUserID("11");
		suggest.setUsername("zzw");
		
		musersuggest.add(suggest);
	}
	public void setUserSuggestDao(UserSuggestDao userSuggestDao) {
		this.userSuggestDao = userSuggestDao;
	}

}

