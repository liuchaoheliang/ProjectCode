package com.froad.singleton;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mappers.OrgMapper;
import com.froad.po.Org;

public class OrgSingleton {
	private static volatile List<Org> instance = null;
	
	private OrgSingleton(){
	}
	
	/**
	 * get org instance
	 * 
	 * @param session
	 * @return
	 */
	public static List<Org> getInstance(SqlSession session){
		OrgMapper mapper = null;
		List<Org> orgList = null;
		Page<Org> page = null;
		int pageNumber = 1;
		int pageSize = Page.MAX_PAGE_SIZE;
		
		if (null == instance){
			synchronized (OrgSingleton.class){
				if (null == instance){
					mapper = session.getMapper(OrgMapper.class);
					page = new Page<Org>();
					page.setPageNumber(pageNumber);
					page.setPageSize(pageSize);
					orgList = mapper.findAllOrgInfoByPage(page);
					instance = new ArrayList<Org>();
					while (null != orgList && orgList.size() > 0){
						instance.addAll(orgList);
						if (orgList.size() < pageSize){
							break;
						}
						pageNumber += 1;
						page.setPageNumber(pageNumber);
						
						session.commit(false);
						orgList = mapper.findAllOrgInfoByPage(page);
					}
				}
			}
		}
		
		return instance;
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
