package com.froad.CB.dao.user.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.froad.CB.common.Command;
import com.froad.CB.common.TableCommand;
import com.froad.CB.dao.user.ContactManagementDao;
import com.froad.CB.po.user.Contacter;

public class ContactManagementDaoImpl implements ContactManagementDao {
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	/**
	  * 方法描述：返回某表主键最大值+1
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2012-2-9 下午5:01:30
	  */
	public String getMaxOfDB(String sql,String tableCommand){
		String maxproductorderseq = null;
		synchronized(sqlMapClientTemplate){
			maxproductorderseq = (String) sqlMapClientTemplate.queryForObject("seq.queryMaxID", tableCommand);
		  	if (maxproductorderseq == null || "".equalsIgnoreCase(maxproductorderseq)) {
					String maxID = (String) sqlMapClientTemplate.queryForObject(sql);
					if (maxID == null || maxID.equals("")) {
						maxID = "1";
					} else {
						maxID = String.valueOf(Integer.parseInt(maxID) + 1);
					}
//					Seq seq = new Seq();
//					seq.setSeq(maxID);
//					seq.setType(tableCommand);
//					if(maxproductorderseq == null)
//						sqlMapClientTemplate.insert("addSeq", seq);
//					else
//						sqlMapClientTemplate.update("updMaxID", seq);
//					maxproductorderseq = maxID;
				} else {
					maxproductorderseq = String.valueOf(Integer.parseInt(maxproductorderseq) + 1);
//			        Seq seq = new Seq();
//			        seq.setSeq(maxproductorderseq);
//			        seq.setType(tableCommand);
//			        sqlMapClientTemplate.update("updMaxID", seq);
		      }
		}
		return maxproductorderseq;
	}
	
	public Contacter addContacter(Contacter con){
		con.setID(getMaxOfDB("contacter.queryMaxConID", TableCommand.seq_contacter));
		sqlMapClientTemplate.insert("contacter.addContacter",con);
		con.setRespCode(Command.respCode_SUCCESS);
		return con;
	}


	public Contacter delContacter(Contacter con) {
		int i = sqlMapClientTemplate.delete("contacter.delContacter",con);
		Contacter conRes = new Contacter();
		if(i>0){
			conRes.setRespCode(Command.respCode_SUCCESS);
			conRes.setRespMsg("更新常用联系人成功!");
		}else{
			conRes.setRespCode(Command.respCode_FAIL);
			conRes.setRespMsg("更新常用联系人失败!");
		}
		return conRes;
	}
	
	public Contacter updContacter(Contacter con) {
		int i = (Integer) sqlMapClientTemplate.update("contacter.UpdateContacter",con);
		Contacter conRes = new Contacter();
		if(i>0){
			conRes.setRespCode(Command.respCode_SUCCESS);
			conRes.setRespMsg("更新常用联系人成功!");
		}else{
			conRes.setRespCode(Command.respCode_FAIL);
			conRes.setRespMsg("更新常用联系人失败!");
		}
		System.out.println(i);
		return conRes;
	}

	public List<Contacter> getContacterList(Contacter con){
		List<Contacter> contacterResList = new ArrayList<Contacter>();
		contacterResList = (List<Contacter>) sqlMapClientTemplate.queryForList("contacter.QueryContacter",con);
		return contacterResList;
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

}
