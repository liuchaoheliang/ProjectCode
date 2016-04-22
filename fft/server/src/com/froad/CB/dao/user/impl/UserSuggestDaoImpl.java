package com.froad.CB.dao.user.impl;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.froad.CB.dao.user.UserSuggestDao;
import com.froad.CB.po.user.Suggest;

public class UserSuggestDaoImpl implements UserSuggestDao {
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	private static Logger logger = Logger.getLogger(UserSuggestDaoImpl.class);
	/**
	  * 方法描述：向数据库中插入建议
	  * @param: Suggest suggest(userID,username,suggesttittle,suggestcontext,submittime,transactionID)
	  * @return: Suggest suggest
	  * @version: 1.0
	  * @author: zhuzhiwei zhuzhiwei@f-road.com.cn
	  * @time: Aug 14, 2011 4:52:18 PM
	  */	
	public  Suggest add(Suggest suggest){
		suggest.setRespCode("1");
		String ID=null;
		  String maxID=(String)sqlMapClientTemplate.queryForObject("queryMaxSuggestID");
		  if(maxID==null)
		  {
			  ID="1";
		  }else
		  {
			  ID=String.valueOf(Integer.parseInt(maxID)+1);
		  }
		  suggest.setID(ID);
		  sqlMapClientTemplate.insert("addsuggest", suggest);
			suggest.setRespCode("0");
			suggest.setRespMsg("提交成功！");
		return suggest;
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

}
