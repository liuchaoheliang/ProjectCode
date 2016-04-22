package com.froad.CB.service.user.impl;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import com.froad.CB.AppException.AppException;
import com.froad.CB.dao.user.LoginManagerDao;
import com.froad.CB.po.user.LoginManager;
import com.froad.CB.service.user.MLoginManagerService;

@WebService(endpointInterface="com.froad.CB.service.user.MLoginManagerService")
public class MLoginManagerServiceImpl implements MLoginManagerService {
	private LoginManagerDao loginManagerDao;
	
	private static Logger logger = Logger.getLogger(MLoginManagerServiceImpl.class);
    /**
     * 方法描述：查询SessionID是否存在
     * @param: sessionid
     * @return: 
     * @version: 1.0
     * @author: 何庆均 heqingjun@f-road.com.cn
     * @update:
     * @time: 2011-7-15 上午11:07:46
     */
	public LoginManager selectSessionID(String sessionid) {
		return loginManagerDao.selectSessionID(sessionid);
	}

    /**
     * 方法描述：创建sessionID 并返回
     * @param: 
     * @return: 
     * @version: 1.0
     * @author: 何庆均 heqingjun@f-road.com.cn
     * @update:
     * @time: 2011-7-15 上午11:07:46
     */
	public synchronized LoginManager createSession(LoginManager lm) {	
		return loginManagerDao.createSession(lm);
	}
	
	public static void main(String agrs[]){
//		MLoginManagerService m = new MLoginManagerServiceImpl();
//		LoginManager l=new LoginManager();
//		l.setUserID("fb1909e2-88fe-4f19-b21f-b8f846cea6f4");
//		l.setUsername("user12312388");
//		l.setUname("faircy");
//		try {
//			m.createSession(l);
//		} catch (AppException e) {
//			logger.error("异常", e);
//		}
	}

	
	 /**
     * 方法描述：删除sessionID
     * @param: 
     * @return: 
     * @version: 1.0
     * @author: 何庆均 heqingjun@f-road.com.cn
     * @update:
     * @time: 2011-7-15 上午11:07:46
     */ 
	public LoginManager delSessionID(String sessionID)  throws AppException {		
		return loginManagerDao.delSessionID(sessionID);
	}
	
	
    /**
     * 方法描述：根据sessionID 查询用户登陆信息
     * @param: 
     * @return: 
     * @version: 1.0
     * @author: 何庆均 heqingjun@f-road.com.cn
     * @update:
     * @time: 2011-7-15 上午11:07:46
     */
	public LoginManager selectUserLoginInfoBYsessionID(String sessionID) throws AppException {
		return loginManagerDao.selectUserLoginInfoBYsessionID(sessionID);
	}
	
	
	public LoginManager selectUserIDBYsessionID(LoginManager lm) {
		return loginManagerDao.selectUserIDBYsessionID(lm);
	}
	
	public LoginManager selectUserInfoBYsessionID(String sessionID) throws AppException {
		return loginManagerDao.selectUserInfoBYsessionID(sessionID);
	}

	public void setLoginManagerDao(LoginManagerDao loginManagerDao) {
		this.loginManagerDao = loginManagerDao;
	}
	
//	public static void main(String[] args) throws AppException {
//		
//		LoginManager lm=new LoginManager();
//		lm.setUserID("0c750db2-d213-475e-916f-8409e553c33f");
//		lm.setUsername("uuu");
//		lm.setUname("uuu");
//		new MLoginManagerImpl().selectSessionID("201107150000010");
//	}
	
}
