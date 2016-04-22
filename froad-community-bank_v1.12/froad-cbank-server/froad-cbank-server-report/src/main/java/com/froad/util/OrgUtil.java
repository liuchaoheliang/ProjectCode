package com.froad.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mappers.OrgMapper;
import com.froad.po.Org;

public class OrgUtil {
	private SqlSession sqlSession = null;
	private Map<String, Org> orgMap = null;
	
	public OrgUtil (SqlSession sqlSession) {
		this.sqlSession = sqlSession;
		orgMap = getAllOrgMap();
	}
	
	/**
	 * get org entity
	 * 
	 * @param clientId
	 * @param orgCode
	 * @return
	 */
	public Org getOrg(String clientId, String orgCode){
		Org org = null;
		StringBuffer key = new StringBuffer();
		
		key = new StringBuffer();
		key.append(clientId);
		key.append(orgCode);
		
		org = orgMap.get(key.toString());
		
		return org;
	}
	
	/**
	 * contains key
	 * 
	 * @param clientId
	 * @param orgCode
	 * @return
	 */
	public boolean containsKey(String clientId, String orgCode){
		StringBuffer key = new StringBuffer();
		
		key = new StringBuffer();
		key.append(clientId);
		key.append(orgCode);
		
		return orgMap.containsKey(key.toString());
	}
	
	/**
	 * get org map
	 *  key = client_id + org_name
	 *  value = org entity
	 * 
	 * @return
	 */
	private Map<String, Org> getAllOrgMap(){
		Map<String, Org> orgMap = null;
		List<Org> orgList = null;
		Org org = null;
		StringBuffer key = null;
		
		orgMap = new HashMap<String, Org>();
		orgList = getOrgList();
		for (int i = 0; i < orgList.size(); i++){
			org = orgList.get(i);
			key = new StringBuffer();
			key.append(org.getClientId());
			key.append(org.getOrgCode());
			orgMap.put(key.toString(), org);
		}
		
		return orgMap;
	}
	
	/**
	 * get org map
	 * 
	 * @return
	 */
	private List<Org> getOrgList(){
		List<Org> orgs = null;
		OrgMapper orgMapper = null;
		List<Org> orgList = null;
		Page<Org> page = null;
		int pageNumber = 1;
		int pageSize = Page.MAX_PAGE_SIZE;
		
		orgMapper = sqlSession.getMapper(OrgMapper.class);
		page = new Page<Org>();
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		orgList = orgMapper.findAllOrgInfoByPage(page);
		orgs = new ArrayList<Org>();
		while (null != orgList && orgList.size() > 0){
			orgs.addAll(orgList);
			if (orgList.size() < pageSize){
				break;
			}
			pageNumber += 1;
			page.setPageNumber(pageNumber);
			
			sqlSession.commit(false);
			orgList = orgMapper.findAllOrgInfoByPage(page);
		}
		
		return orgs;
	}

	/**
	 * @return the orgMap
	 */
	public Map<String, Org> getOrgMap() {
		return orgMap;
	}

	/**
	 * @param orgMap the orgMap to set
	 */
	public void setOrgMap(Map<String, Org> orgMap) {
		this.orgMap = orgMap;
	}
}
