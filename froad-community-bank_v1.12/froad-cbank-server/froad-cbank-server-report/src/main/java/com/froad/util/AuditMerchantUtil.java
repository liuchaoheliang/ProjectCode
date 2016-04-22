package com.froad.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mappers.MerchantMapper;
import com.froad.po.Merchant;

public class AuditMerchantUtil {
	private SqlSession sqlSession = null;
	private Map<String, Merchant> merchantMap = null;
	private Map<String, List<Merchant>> merchantListMap = null;
	
	// clientId + pro_org_code + city_org_code + county_org_code + contract_staff
	public static final char KEY_ORG_CODE_CONTRACT_STAFF = '1';
	
	public AuditMerchantUtil(SqlSession sqlSession){
		this.sqlSession = sqlSession;
		merchantMap = getAuditMerchantMap();
	}
	
	public AuditMerchantUtil(SqlSession sqlSession, char keyType){
		this.sqlSession = sqlSession;
		merchantListMap = getAuditMerchantListMap(keyType);
	}
	
	/**
	 * get merchant
	 * 
	 * @param clientId
	 * @param merchantId
	 * @return
	 */
	public Merchant getMerchant(String clientId, String merchantId){
		Merchant merchant = null;
		StringBuffer key = null;
		
		key = new StringBuffer();
		key.append(clientId);
		key.append(merchantId);
		merchant = merchantMap.get(key.toString());
		
		return merchant;
	}
	
	/**
	 * get merchant list
	 * 
	 * @param clientId
	 * @param pro_org_code
	 * @param city_org_code
	 * @param county_org_code
	 * @param contract_staff
	 * @return
	 */
	public List<Merchant> getMerchantList(String clientId, String pro_org_code,
			String city_org_code, String county_org_code, String contract_staff) {
		List<Merchant> merchantList = null;
		StringBuffer key = null;

		key = new StringBuffer();
		key.append(clientId);
		key.append(pro_org_code);
		key.append(city_org_code);
		key.append(county_org_code);
		key.append(contract_staff);
		merchantList = merchantListMap.get(key.toString());

		return merchantList;
	}
	
	/**
	 * get audit merchant map
	 * 
	 * @return
	 */
	private Map<String, Merchant> getAuditMerchantMap(){
		Map<String, Merchant> map = new HashMap<String, Merchant>();
		MerchantMapper mapper = null;
		Page<Merchant> page = null;
		List<Merchant> list = null;
		List<Merchant> merchants = null;
		int pageNo = 1;
		int pageSize = Page.MAX_PAGE_SIZE;
		StringBuffer key = null;
		
		mapper = sqlSession.getMapper(MerchantMapper.class);
		
		page = new Page<Merchant>();
		page.setPageNumber(pageNo);
		page.setPageSize(pageSize);
		list = mapper.selectAllAuditMerchantsByPage(page);
		
		merchants = new ArrayList<Merchant>();
		while (null != list && list.size() > 0){
			merchants.addAll(list);
			if (list.size() < pageSize){
				break;
			}
			pageNo += 1;
			page.setPageNumber(pageNo);
			
			sqlSession.commit(false);
			list = mapper.selectAllAuditMerchantsByPage(page);
		}
		
		for(Merchant m : merchants){
			key = new StringBuffer();
			key.append(m.getClientId());
			key.append(m.getMerchantId());
			map.put(key.toString(), m);
		}
		return map;
	}
	
	/**
	 * get audit merchant list map
	 * 
	 * @param keyType
	 * @return
	 */
	private Map<String, List<Merchant>> getAuditMerchantListMap(char keyType){
		Map<String, List<Merchant>> listMap = new HashMap<String, List<Merchant>>();
		MerchantMapper mapper = null;
		Page<Merchant> page = null;
		List<Merchant> list = null;
		List<Merchant> merchants = null;
		int pageNo = 1;
		int pageSize = Page.MAX_PAGE_SIZE;
		StringBuffer key = null;
		
		mapper = sqlSession.getMapper(MerchantMapper.class);
		
		page = new Page<Merchant>();
		page.setPageNumber(pageNo);
		page.setPageSize(pageSize);
		list = mapper.selectAllAuditMerchantsByPage(page);
		
		merchants = new ArrayList<Merchant>();
		while (null != list && list.size() > 0){
			merchants.addAll(list);
			if (list.size() < pageSize){
				break;
			}
			pageNo += 1;
			page.setPageNumber(pageNo);
			
			sqlSession.commit(false);
			list = mapper.selectAllAuditMerchantsByPage(page);
		}
		
		for(Merchant m : merchants){
			key = new StringBuffer();
			if (keyType == KEY_ORG_CODE_CONTRACT_STAFF){
				key.append(m.getClientId());
				key.append(m.getProOrgCode());
				key.append(m.getCityOrgCode());
				key.append(m.getCountyOrgCode());
				key.append(m.getContractStaff());
			}
			list = listMap.get(key.toString());
			if (Checker.isEmpty(list)){
				list = new ArrayList<Merchant>();
			}
			
			list.add(m);
			listMap.put(key.toString(), list);
		}
		return listMap;
	}
}
