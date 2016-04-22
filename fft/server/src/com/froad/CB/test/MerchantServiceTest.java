package com.froad.CB.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.dao.merchant.impl.MerchantDAOImpl;
import com.froad.CB.po.ClientGoodsGroupRack;
import com.froad.CB.po.merchant.Merchant;
import com.froad.CB.service.ClientGoodsGroupRackService;
import com.froad.CB.service.impl.MerchantServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MerchantServiceTest {

	@Resource
	private MerchantServiceImpl merchantServiceImpl;
	
	@Resource
	private ClientGoodsGroupRackService clientGoodsGroupRackService;
	
	
	@Test
	public void getMerchantsPreferentialType(){
		Merchant rankMerchantPager=new Merchant();
		rankMerchantPager.setPageNumber(1);
		rankMerchantPager.setPageSize(8);
		rankMerchantPager.setState("30");
		rankMerchantPager.setIsHot("1");
		rankMerchantPager.setOrderBy("hot_level*1");//优先级  
		rankMerchantPager.setIsInternalAccount("0");
//		rankMerchantPager.setOrderType("desc");
		
		Merchant mer=merchantServiceImpl.getMerchantsPreferentialType(rankMerchantPager);
		System.out.println(mer);
	}
	
	@Test
	public void getByPager(){
		Merchant mer=new Merchant();
		mer.setCompanyFullName("(公司)KIGO凯歌量贩KTV桂花路店");
//		mer.setBeginTime("2012-10-10 10:10:10");
//		mer.setEndTime("2013-02-28 10:10:10");
//		List<String> stateList=new ArrayList<String>();
//		stateList.add("10");
//		stateList.add("20");
//		stateList.add("30");
//		stateList.add("40");
//		mer.setStateList(stateList);
		Merchant merchant=merchantServiceImpl.getMerchantByPager(mer);
		System.out.println("list.size=="+merchant.getList().size());
		Merchant merchant_1=null;
		for (int i = 0; i <merchant.getList().size(); i++) {
			merchant_1=(Merchant)merchant.getList().get(i);
			System.out.println("id: "+merchant_1.getId());
			System.out.println("storeList: "+merchant_1.getStoreList().size());
		}
	}
	
	@Test
	public void getmerchantAll(){
//		Integer id=100001004;
		Merchant merchant = new Merchant();
//		merchant.setState("30"); 
//		List<String> state = new ArrayList<String>();
//		state.add("30");
//		state.add("20");
		
//		merchant.setStateList(state);
		merchant.setMstoreShortName("眼科");
		List<Merchant> mlist = merchantServiceImpl.getAllMerchant(merchant);
		System.out.println("merchant: "+mlist.size());
	}
	
	@Test
	public void getClientGoods(){
		Integer id=1000000008;
		ClientGoodsGroupRack rack=clientGoodsGroupRackService.getGoodsGroupRackById(id);
		System.out.println("rack: "+rack.getGoods().getMerchant().getMerchantTrain());
	}
	
	@Test
	public void isMerchant(){
		String userId="5890dbcf-3fa8-40bb-8638-4266af28f561";
		System.out.println((merchantServiceImpl.isMerchant(userId))?"yes":"no");
	}
	
	@Test
	public void getMerchantInfo(){
		try{
			String userId="23b70ccc-0c66-49c0-9973-e8fde766df8a";
			merchantServiceImpl.getMerchantByUserId(userId);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
