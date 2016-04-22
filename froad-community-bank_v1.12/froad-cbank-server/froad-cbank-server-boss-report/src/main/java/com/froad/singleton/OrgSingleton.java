package com.froad.singleton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mappers.OrgMapper;
import com.froad.logback.LogCvt;
import com.froad.po.Org;

public class OrgSingleton {
	
	private static volatile Map<String, Org> instance = null;
	
	private OrgSingleton(){
	}
	
	/**
	 * get org instance
	 * 
	 * @param session
	 * @return
	 */
	public static Map<String, Org> getInstance(){
		OrgMapper mapper = null;
		
		if (null == instance){
			synchronized (OrgSingleton.class){
				if (null == instance){
					SqlSession session = null;
					try {
						session = MyBatisManager.getSqlSessionFactory().openSession();
						mapper = session.getMapper(OrgMapper.class);
						List<Org> list = mapper.findAllOrgInfo();
						instance = new HashMap<String, Org>();
						for (int i = 0; i < list.size(); i++) {
							instance.put(getOrgKey(list.get(i).getClientId(), list.get(i).getOrgCode()), list.get(i));
						}
					} catch (Exception e) {
						LogCvt.error("获取所有机构信息异常", e);
					} finally {
						if (session != null) {
							session.close();
						}
					}
					
				}
			}
		}
		
		return instance;
	}
	
	public static Org getOrgByCode(String clientId, String orgCode){
		return OrgSingleton.getInstance().get(getOrgKey(clientId, orgCode));
	}
	
	public static String getOrgKey(String clientId, String orgCode){
		return clientId + "_" + orgCode;
	}
	
	/**
	 * destroy instance
	 */
	public static void destroyInstance(){
		if (instance != null){
			instance.clear();
			instance = null;
		}
	}
}
