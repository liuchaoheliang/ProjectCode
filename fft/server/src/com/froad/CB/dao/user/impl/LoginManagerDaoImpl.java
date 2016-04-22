package com.froad.CB.dao.user.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.froad.CB.common.Command;
import com.froad.CB.common.TableCommand;
import com.froad.CB.dao.user.LoginManagerDao;
import com.froad.CB.po.user.LoginManager;
import com.froad.CB.po.user.Seq;

public class LoginManagerDaoImpl implements LoginManagerDao {
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	private static Logger logger = Logger.getLogger(LoginManagerDaoImpl.class);
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
		LoginManager loginManager=new LoginManager();		
		Object sessionID = sqlMapClientTemplate.queryForObject("selectSession", sessionid);
		
		loginManager.setRespCode("0");
		loginManager.setRespMsg("查询Session成功");
		
		if(sessionID == null ){
			loginManager.setSessionID("false");
		} else {
			loginManager.setSessionID("true");
		}
		return loginManager;
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
		LoginManager l=new LoginManager();
	    String sessionID = null;
	    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    	String left = df.format(new Date());	
		String right=null,sessionid = null;
		String maxsessionID = (String) sqlMapClientTemplate
		.queryForObject("queryMaxID", TableCommand.seq_loginManager);
	    if(maxsessionID == null) {
	    	sessionID = (String) sqlMapClientTemplate.queryForObject("queryMaxsessionID");
			if (sessionID == null || sessionID.equals("")) {
				right = "0000001";
				sessionID = left + right;
			} else {
				if (left.substring(0, 8).equalsIgnoreCase(
						sessionID.substring(0, 8))) {
					
					right =String
									.valueOf(
											Integer.parseInt(sessionID
													.substring(8)) + 10000001)
									.substring(1);
					sessionID = left + right;
				} else {
					right = "0000001";
					sessionID = left+right;
				}
			}
			Seq ss = new Seq();
			ss.setType(TableCommand.seq_loginManager);
			ss.setSeq(sessionID);
			sqlMapClientTemplate.insert("addSeq", ss);
			
			l.setID(sessionID);
			l.setSessionID(sessionID);
			l.setUserID(lm.getUserID());
			l.setUsername(lm.getUsername());
			l.setUname(lm.getUname());
			sqlMapClientTemplate.insert("createSession",l);
	    }else if("".equalsIgnoreCase(maxsessionID)){
	    	sessionID = (String) sqlMapClientTemplate.queryForObject("queryMaxsessionID");
			if (sessionID == null || sessionID.equals("")) {
				right = "0000001";
				sessionID = left+right;
			} else {
				if (left.substring(0, 8).equalsIgnoreCase(
						sessionID.substring(0, 8))) {
					
					right =String
									.valueOf(
											Integer.parseInt(sessionID
													.substring(8)) + 10000001)
									.substring(1);
					sessionID = left+right;
				} else {
					right = "0000001";
					sessionID = left+right;
				}
			}
			Seq seq = new Seq();
			seq.setSeq(sessionID);
			seq.setType(TableCommand.seq_loginManager);
			sqlMapClientTemplate.update("updMaxID", seq);
			
			l.setID(sessionID);
			l.setSessionID(sessionID);
			l.setUserID(lm.getUserID());
			l.setUsername(lm.getUsername());
			l.setUname(lm.getUname());
			sqlMapClientTemplate.insert("createSession",l);

	    }else{
	    
			if (left.substring(0, 8).equalsIgnoreCase(
					maxsessionID.substring(0, 8))) {
				right = String
						.valueOf(
								Integer
										.parseInt(maxsessionID
												.substring(8)) + 10000001)
						.substring(1);
				sessionID = left+right;
			} else {
				right = String.valueOf((10000001)).substring(1);
				sessionID = left+right;
			}
			
			Seq seq = new Seq();
			seq.setSeq(sessionID);
			seq.setType(TableCommand.seq_loginManager);
			sqlMapClientTemplate.update("updMaxID", seq);

	    	l.setID(sessionID);
			l.setSessionID(sessionID);
			l.setUserID(lm.getUserID());
			l.setUsername(lm.getUsername());
			l.setUname(lm.getUname());
			sqlMapClientTemplate.insert("createSession",l);
	    }
	    
		l.setRespCode("0");
		l.setRespMsg("创建Session成功");
		
		return l;
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
	public LoginManager delSessionID(String sessionID){
		LoginManager lm=new LoginManager();
		int i=0;
		i=sqlMapClientTemplate.delete("delSessionID", sessionID);
		if(i>0)
		{
			lm.setRespCode(Command.respCode_SUCCESS);
			lm.setRespMsg("删除sessionid成功");
		}else
		{
			lm.setRespCode(Command.respCode_FAIL);
			lm.setRespMsg("删除sessionid失败");
		}
		
		return lm;
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
	public LoginManager selectUserLoginInfoBYsessionID(String sessionID) {		
		LoginManager lm=new LoginManager();
		
		lm=(LoginManager) sqlMapClientTemplate.queryForObject("selectUserLoginInfoBYsessionID", sessionID);
		
		if(lm != null){
			//lm.getPassword()
		}
		
		lm.setRespCode("0");
		lm.setRespMsg("查询用户所有信息成功");
		
		return lm;
	}
	
	
	public LoginManager selectUserIDBYsessionID(LoginManager lm) {
//		try {
			lm = (LoginManager) sqlMapClientTemplate.queryForObject("selectUserIDBYsessionID", lm);
			lm.setRespCode("0");
			lm.setRespMsg("查询userID成功");
//			sqlMapClient.commitTransaction();
//		} catch (SQLException e) {
//			logger.error("异常", e);
//			lm.setRespCode("1");
//			lm.setRespMsg("根据SessionID查询userID失败:提交事务出现异常");
//		} finally {
//			try {
//				sqlMapClient.endTransaction();
//
//			} catch (SQLException e) {
//
//				lm.setRespCode("1");
//				lm.setRespMsg("根据SessionID查询userID失败:结束事务出现异常");
//				e.printStackTrace();
//			}
//		}

		return lm;
	}
	
	
	
   
	
	
	
	public LoginManager selectUserInfoBYsessionID(String sessionID){
		LoginManager lm=new LoginManager();
//		lm = (LoginManager) SqlMapClientFactory.getSqlMapClientTemplate().queryForObject("selectUserInfoBYsessionID", sessionID);
		lm=(LoginManager) sqlMapClientTemplate.queryForObject("selectUserInfoBYsessionID", sessionID);
		lm.setRespCode("0");
		lm.setRespMsg("查询用户所有信息成功");
		return lm;
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

}
