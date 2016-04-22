package com.froad.singleton;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mappers.MerchantMapper;
import com.froad.po.Merchant;

public class MerchantSingleton {
	
	private static volatile List<Merchant> merchants = null;
	
	private MerchantSingleton(){}
	
	public static List<Merchant> getInstance(SqlSession session){
		MerchantMapper mapper = null;
		Page<Merchant> page = null;
		List<Merchant> list = null;
		List<Merchant> totalList = null;
		int pageNo = 1;
		int pageSize = Page.MAX_PAGE_SIZE;
		
		if (null == merchants){
			synchronized (MerchantSingleton.class) {
				mapper = session.getMapper(MerchantMapper.class);
				
				page = new Page<Merchant>();
				page.setPageNumber(pageNo);
				page.setPageSize(pageSize);
				list = mapper.selectAllAuditMerchantsByPage(page);
				
				totalList = new ArrayList<Merchant>();
				while (null != list && list.size() > 0){
					totalList.addAll(list);
					if (list.size() < pageSize){
						break;
					}
					pageNo += 1;
					page.setPageNumber(pageNo);
					
					session.commit(false);
					list = mapper.selectAllAuditMerchantsByPage(page);
				}
				
				merchants = totalList;
			}
		}
		return merchants;
	}
	
	public static void destroyInstance(){
		if(merchants != null){
			merchants.clear();
			merchants = null;
		}
		
	}
}
