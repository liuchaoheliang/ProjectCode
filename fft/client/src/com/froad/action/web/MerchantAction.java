package com.froad.action.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.froad.action.support.AdvertActionSupport;
import com.froad.action.support.GoodsExchangeRackActionSupport;
import com.froad.action.support.GoodsGroupRackActionSupport;
import com.froad.action.support.GoodsRebateRackActionSupport;
import com.froad.action.support.MemberCollectActionSupport;
import com.froad.action.support.MerchantActionSupport;
import com.froad.action.support.MerchantPhotoActionSupport;
import com.froad.action.support.MerchantPreferentialActionSupport;
import com.froad.action.support.MerchantPresentActionSupport;
import com.froad.action.support.MerchantProductActionSupport;
import com.froad.action.support.MerchantTrafficMAPActionSupport;
import com.froad.action.support.MerchantTrainActionSupport;
import com.froad.action.support.SearchesActionSupport;
import com.froad.action.support.SellersActionSupport;
import com.froad.action.support.StoreActionSupport;
import com.froad.action.support.TagActionSupport;
import com.froad.action.support.merchantUserSet.MerchantUserSellerActionSupport;
import com.froad.action.support.merchantUserSet.MerchantUserSetActionSupport;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.Store.Store;
import com.froad.client.advert.Advert;
import com.froad.client.goodsExchangeRack.GoodsExchangeRack;
import com.froad.client.goodsGroupRack.GoodsGroupRack;
import com.froad.client.goodsRebateRack.GoodsRebateRack;
import com.froad.client.memberCollect.MemberCollect;
import com.froad.client.merchant.Merchant;
import com.froad.client.merchantPhoto.MerchantPhoto;
import com.froad.client.merchantPreferential.MerchantPreferential;
import com.froad.client.merchantPresent.MerchantPresent;
import com.froad.client.merchantProduct.MerchantProduct;
import com.froad.client.merchantProduct.OrderType;
import com.froad.client.merchantTrafficMAP.MerchantTrafficMAP;
import com.froad.client.merchantTrain.MerchantTrain;
import com.froad.client.merchantUserSeller.MerchantUserSeller;
import com.froad.client.merchantUserSet.MerchantUserSet;
import com.froad.client.searches.Searches;
import com.froad.client.sellers.Seller;
import com.froad.client.tag.TagClassifyA;
import com.froad.client.tag.TagClassifyB;
import com.froad.client.tag.TagDistrictB;
import com.froad.client.tag.TagMAP;
import com.froad.client.user.AppException_Exception;
import com.froad.client.user.User;
import com.froad.util.Assert;
import com.froad.util.Command;
import com.froad.util.JsonUtil;
import com.froad.util.Validate;
import com.froad.util.command.MallCommand;
import com.froad.util.command.TransType;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-21  
 * @version 1.0
 */
public class MerchantAction extends BaseActionSupport {	
	/**
	 * UID
	 */
	private static final long serialVersionUID = -5536663963185404110L;
	private MerchantActionSupport merchantActionSupport;
	private MerchantTrainActionSupport merchantTrainActionSupport;
	private MerchantPresentActionSupport merchantPresentActionSupport;
	private MerchantUserSellerActionSupport merchantUserSellerActionSupport;
	private GoodsRebateRackActionSupport goodsRebateRackActionSupport;
	private MerchantProductActionSupport merchantProductActionSupport;	
	private MerchantPreferentialActionSupport merchantPreferentialActionSupport;
	private MerchantPhotoActionSupport merchantPhotoActionSupport;
	private MerchantTrafficMAPActionSupport merchantTrafficMAPActionSupport;
	private MerchantUserSetActionSupport merchantUserSetActionSupport;
	private SearchesActionSupport searchesActionSupport;
	private TagActionSupport tagActionSupport;
	private SellersActionSupport sellersActionSupport;
	private MerchantProduct hotPushMerchantProductPager=new MerchantProduct();//指定商户 热推商品
	private MerchantProduct newMerchantProductPager=new MerchantProduct();//最新商品
	private GoodsRebateRack hotPushGoodsRebateRackPager = new GoodsRebateRack();
	private GoodsRebateRack newGoodsRebateRackPager = new GoodsRebateRack();
	private MerchantPreferential merchantPreferentialPager=new MerchantPreferential();//商家优惠活动
	private List<MerchantPhoto> merchantPhotoList = new ArrayList<MerchantPhoto>(); //商户相册
	private MerchantTrafficMAP merchantTrafficMAP;//商户地图
	private MerchantTrain merchantTrain;
	private MerchantPresent merchantPresent;//商户介绍
	private Merchant merchant;//商户
	private Merchant pager;
	private List<TagDistrictB> allTagDistrictBList;//所有商圈
	private List<TagClassifyB> allTagClassifyBList;//所有二级分类
	private List<TagClassifyA> allTagClassifyAList;//所有一级分类
	
	private AdvertActionSupport advertActionSupport;//广告
	private List<Advert> advertList;//广告
	private MerchantUserSet merchantUserSet; 

	//商户修改密码相关参数
	private String password;
	private String password1;
	private String oldpassword;
	
	
	private String merchantId = ""; //用于记忆用户搜索商圈	
	private String sortId = "";//用于记忆用户搜索的分类
	
	//登录用户收藏商家集合
	private MemberCollectActionSupport memberCollectActionSupport;
	private MemberCollect memberCollectPager = new MemberCollect();

	
	//操作员selerType
	String[] clerkSellerType = null;
	private String StoreId;
	//操作员所拥有权限
	private List<String> clerkAuthority;
	
	
	
	//========================商户展示页面改版 begin
	private GoodsGroupRackActionSupport goodsGroupRackActionSupport;
	private GoodsExchangeRackActionSupport goodsExchangeRackActionSupport;
	private List<GoodsGroupRack> goodsGroupRackList;
	private List<GoodsExchangeRack> goodsExchangeRackList;
	private StoreActionSupport storeActionSupport;
	private List<Store> storeList;
	
	private String sytleCssName = "imglist";
	//========================商户展示页面改版 over
	
	
	/**
	 * 首页菜单主搜索
	 * 商户列表名称，地址，标签（分类，商圈）          
	 */
	public String search(){
		merchantId = pager.getTagDistrictBId();
		sortId = pager.getTagClassifyAId();
		//查询返利模块广告
//		queryAdvertList("1");
		
		String tagValue = "";
		if(pager == null){
			return "failse";
		}else if(!validateSearch(pager)){
			return "failse";
		}
		if(Assert.empty(pager.getTagValue())
				&& Assert.empty(pager.getTagClassifyAId())
				&& Assert.empty(pager.getTagDistrictBId())){
			pager.setTagDistrictAId("100001001");
		}
		if(!Assert.empty(pager.getTagValue())){
			//tagValue = urlDecode(urlDecode(pager.getTagValue(),"utf-8"), "utf-8");
			tagValue = pager.getTagValue().replace("\\", "\\\\").replace("_", "\\_").replace("%", "\\%");	
			pager.setTagValue(tagValue);
		}
		pager.setPageSize(9);
		updSearchHis(pager);
		pager=merchantActionSupport.getMerchantList(pager);
		//全部一级二级分类、二级商圈
		allTagDistrictBList=tagActionSupport.findAllDistrictB();
		allTagClassifyAList = tagActionSupport.findAllClassifyA();
		allTagClassifyBList = tagActionSupport.findAllClassifyB();
		return "index";
	}
	
	/**
	 * 积分返利商家首页
	 * @return
	 */
	public String index(){
		
		if(pager == null){
			pager = new Merchant();
		}
		if(Assert.empty(pager.getMerchantPriority()) 
				&& Assert.empty(pager.getTagClassifyAId())
				&& Assert.empty(pager.getTagDistrictBId())){
			pager.setTagDistrictAId("100001001");
		}
		//全部商圈
		allTagDistrictBList=tagActionSupport.findAllDistrictB();		
		//全部分类
		allTagClassifyAList = tagActionSupport.findAllClassifyA();
		allTagClassifyBList = tagActionSupport.findAllClassifyB();
		
		//积分返利  的商户
		pager.setPageSize(9);
		pager.setState(Command.FBU_USERED_STATE);
		pager.setPreferentialType("2");
		pager.setOrderBy("merchant_priority*1");//优先级
		pager.setOrderType(com.froad.client.merchant.OrderType.DESC);
		pager = merchantActionSupport.getMerchantListIndex(pager);

		if(pager == null){
			pager = new Merchant();
		}else{
			//根据商家是否有分店设置地址的显示文字
			List<?> list=pager.getList();
			for(int i=0;i<list.size();i++){
				Merchant m=(Merchant) list.get(i);
				log.info("===============merchantId======="+m.getId());
				log.info("===============storeList======="+m.getStoreList());
				//商家的分店数量 
				List<Store> slist=storeActionSupport.getStoresByMerchantId(m.getId());
//				int  count=2;
				if(slist.size()>1){
					m.setMstoreAddress("<a style='text-decoration:none;color:#660000;' target='_blank' href=\"merchant_annex_info.action?pager.merchantId="+m.getId()+"&pager.pageNumber=1\">查看全部"+slist.size()+"家门店地址</a>");
				}else{
					if(m.getMstoreAddress() != null){
						String s=m.getMstoreAddress().replaceAll("<", "&lt;");
						m.setMstoreAddress(s.replaceAll(">", "&gt;"));
					}
				}
			}
		}
		
		return "index";
	}

//	/**
//	 * 模块
//	 * @param module 模块 1-积分返利 3-直接优惠
//	 */
//	private void queryAdvertList(String module) {
//		Advert advert=new Advert();
//		advert.setModule(module);
//		advert.setType("3");
//		advert.setState("30");
//		advertList=advertActionSupport.getAdvertById(advert);
//	}
	
	/**
	 * 搜索返利商家列表
	 */
	public String searchRebateMerchants(){
		merchantId = pager.getTagDistrictBId();
		sortId = pager.getTagClassifyAId();
		//查询返利模块广告
//		queryAdvertList("1");
		
		if(pager == null){
			pager = new Merchant();
		}
		//if(Assert.empty(pager.getPreferentialType())){
			pager.setPreferentialType("2");
		//}
		if(Assert.empty(pager.getTagClassifyAId())
				&& Assert.empty(pager.getTagDistrictAId())
				&& Assert.empty(pager.getTagDistrictBId())){
			pager.setTagDistrictAId("100001001");
		}
		pager.setPageSize(9);
		pager.setOrderBy("merchant_priority*1");//优先级
		pager.setOrderType(com.froad.client.merchant.OrderType.DESC);
		updSearchHis(pager);
		pager=merchantActionSupport.getMerchantListIndex(pager);
		if(pager == null){
			pager = new Merchant();
		}else{
			//根据商家是否有分店设置地址的显示文字
			List<?> list=pager.getList();
			for(int i=0;i<list.size();i++){
				Merchant m=(Merchant) list.get(i);
				//商家的分店数量 
				 int count=storeActionSupport.getStoresByMerchantId(m.getId()).size();
//				int  count=2;
				if(count>1){
					m.setMstoreAddress("<a style='text-decoration:none;color:#660000;' target='_blank' href=\"merchant_annex_info.action?pager.merchantId="+m.getId()+"&pager.pageNumber=1\">查看全部"+count+"家门店地址</a>");
				}else{
					if(m.getMstoreAddress() != null){
						String s=m.getMstoreAddress().replaceAll("<", "&lt;");
						m.setMstoreAddress(s.replaceAll(">", "&gt;"));
					}
				}
			}
		}
		//全部一级分类、二级商圈
		allTagDistrictBList=tagActionSupport.findAllDistrictB();
		allTagClassifyAList = tagActionSupport.findAllClassifyA();
		allTagClassifyBList = tagActionSupport.findAllClassifyB();
		return "index";
	}
	
	/**
	 * 搜索优惠商家列表
	 */
	public String searchPreferentialMerchants(){
		merchantId = pager.getTagDistrictBId();
		sortId = pager.getTagClassifyAId();
		//查询返利模块广告
//		queryAdvertList("3");
		
		if(pager == null){
			pager = new Merchant();
		}
		//if(Assert.empty(pager.getPreferentialType())){
			pager.setPreferentialType("1");
		//}
		if(Assert.empty(pager.getTagClassifyAId())
				&& Assert.empty(pager.getTagDistrictAId())
				&& Assert.empty(pager.getTagDistrictBId())){
			pager.setTagDistrictAId("100001001");
		}
		pager.setPageSize(9);
		pager.setOrderBy("merchant_priority*1");//优先级
		pager.setOrderType(com.froad.client.merchant.OrderType.DESC);
		updSearchHis(pager);
		pager=merchantActionSupport.getMerchantListIndex(pager);
		if(pager == null){
			pager = new Merchant();
		}else{
			//根据商家是否有分店设置地址的显示文字
			List<?> list=pager.getList();
			for(int i=0;i<list.size();i++){
				Merchant m=(Merchant) list.get(i);
				//商家的分店数量 
				 int count=storeActionSupport.getStoresByMerchantId(m.getId()).size();
//				int  count=2;
				if(count>0){
					m.setMstoreAddress("<a style='text-decoration:none;color:#660000;' target='_blank' href=\"merchant_annex_info.action?pager.merchantId="+m.getId()+"&pager.pageNumber=1\">查看全部"+count+"家门店地址</a>");
				}else{
					if(m.getMstoreAddress() != null){
						String s=m.getMstoreAddress().replaceAll("<", "&lt;");
						m.setMstoreAddress(s.replaceAll(">", "&gt;"));
					}
				}
			}
		}
		//全部一级分类、二级商圈
		allTagDistrictBList=tagActionSupport.findAllDistrictB();
		allTagClassifyAList = tagActionSupport.findAllClassifyA();
		allTagClassifyBList = tagActionSupport.findAllClassifyB();
		return "index";
	}
	
	/**
	 * 直接优惠商家首页
	 * @return
	 */
	public String preferentialIndex(){
		
		//查询直接优惠模块广告
//		queryAdvertList("3");
		
		if(pager == null){
			pager = new Merchant();
		}
		if(Assert.empty(pager.getTagClassifyAId())
				&& Assert.empty(pager.getTagDistrictBId())){
			pager.setTagDistrictAId("100001001");
		}
		//全部商圈
		allTagDistrictBList=tagActionSupport.findAllDistrictB();		
		//全部分类
		allTagClassifyAList = tagActionSupport.findAllClassifyA();
		allTagClassifyBList = tagActionSupport.findAllClassifyB();
		
		//积分返利  的商户
		pager.setPageSize(9);
		pager.setState(Command.FBU_USERED_STATE);
		pager.setPreferentialType("1");
		pager.setOrderBy("merchant_priority*1");//优先级
		pager.setOrderType(com.froad.client.merchant.OrderType.DESC);
		pager = merchantActionSupport.getMerchantListIndex(pager);

		
		if(pager == null){
			pager = new Merchant();
		}else{
			//根据商家是否有分店设置地址的显示文字
			List<?> list=pager.getList();
			for(int i=0;i<list.size();i++){
				Merchant m=(Merchant) list.get(i);
				//商家的分店数量
				 int count=storeActionSupport.getStoresByMerchantId(m.getId()).size();
//				int  count=2;
				if(count>0){
					m.setMstoreAddress("<a style='text-decoration:none;color:#660000;'  target='_blank' href=\"merchant_annex_info.action?pager.merchantId="+m.getId()+"&pager.pageNumber=1\">查看全部"+count+"家门店地址</a>");
				}else{
					if(m.getMstoreAddress() != null){
							String s=m.getMstoreAddress().replaceAll("<", "&lt;");
							m.setMstoreAddress(s.replaceAll(">", "&gt;"));
					}
				}
			}
		}
		
		return "index";
	}
	
	/**
	 * 商户信息
	 * @return
	 */
	public String getMerchantInfo(){
		log.info("商户信息");
		
		//增加直通车点击数
		merchantTrain=merchantTrainActionSupport.getMerchantTrainByMerchantId(id,null);
		if(merchantTrain!=null){
			
			if(merchantTrain.getClickes()==null || "".equals(merchantTrain.getClickes())){
				merchantTrain.setClickes("0");
			}
			
			Integer oldClickes=Integer.parseInt(merchantTrain.getClickes());
			
			MerchantTrain newMerchantTrain=new MerchantTrain();
			Integer newClickes=oldClickes+1;
			newMerchantTrain.setId(merchantTrain.getId());
			newMerchantTrain.setClickes(newClickes+"");
			merchantTrainActionSupport.updMerchantTrain(newMerchantTrain);
		}
		
		
		Seller seller=new Seller();
		seller.setMerchantId(id);
		seller.setSellerType(TransType.Trans_Points.getValue());
		seller.setState(Command.FBU_USERED_STATE);
		List<Seller> sellerList=sellersActionSupport.getSellerListBySelective(seller);
				
		//商户介绍
		merchantPresent=merchantPresentActionSupport.getMerchantPresentInfo(id, null);

		/**
		//商户热推商品
		hotPushGoodsRebateRackPager.setPageNumber(1);
		hotPushGoodsRebateRackPager.setPageSize(3);
		hotPushGoodsRebateRackPager.setState(Command.FBU_USERED_STATE);
		hotPushGoodsRebateRackPager.setSellerId(sel.getId());//卖家ID
		//hotPushGoodsRebateRackPager.setOrderBy("product_priority*1");//优先级
		hotPushGoodsRebateRackPager.setOrderType(com.froad.client.goodsRebateRack.OrderType.DESC);
		hotPushGoodsRebateRackPager=goodsRebateRackActionSupport.getGoodsRebateRackByPager(hotPushGoodsRebateRackPager);
		
		最新商品
		newGoodsRebateRackPager.setPageNumber(1);
		newGoodsRebateRackPager.setPageSize(10);
		newGoodsRebateRackPager.setState(Command.FBU_USERED_STATE);
		newGoodsRebateRackPager.setSellerId(sel.getId());//卖家ID	
		newGoodsRebateRackPager=goodsRebateRackActionSupport.getGoodsRebateRackByPager(newGoodsRebateRackPager);
		*/
		
		//商户热推商品
		hotPushMerchantProductPager.setPageNumber(1);
		//hotPushMerchantProductPager.setPageSize(3);
		hotPushMerchantProductPager.setState(Command.FBU_USERED_STATE);
		hotPushMerchantProductPager.setMerchantId(id);//商户ID
		hotPushMerchantProductPager.setOrderBy("array*1");//优先级
		hotPushMerchantProductPager.setOrderType(com.froad.client.merchantProduct.OrderType.DESC);
		hotPushMerchantProductPager=merchantProductActionSupport.queryMerchantProductByPager(hotPushMerchantProductPager);
		
		//最新商品
		newMerchantProductPager.setPageNumber(1);
		newMerchantProductPager.setPageSize(8);
		newMerchantProductPager.setState(Command.FBU_USERED_STATE);
		newMerchantProductPager.setMerchantId(id);//商户ID	
		
		// 设置商户的商品按照优先级排序  Zxy
		newMerchantProductPager.setOrderBy("array*1");		 
		newMerchantProductPager.setOrderType(OrderType.DESC); 
		
		// 设置商户的商品按照优先级排序 Zxy
		
		newMerchantProductPager=merchantProductActionSupport.queryMerchantProductByPager(newMerchantProductPager);

		//商家优惠活动公告
		merchantPreferentialPager.setPageNumber(1);
		merchantPreferentialPager.setPageSize(8);
		merchantPreferentialPager.setState(Command.FBU_USERED_STATE);
		merchantPreferentialPager.setMerchantId(id);//商户ID
		merchantPreferentialPager.setOrderBy("array*1");//排列数值
		merchantPreferentialPager.setOrderType(com.froad.client.merchantPreferential.OrderType.DESC);
		merchantPreferentialPager=merchantPreferentialActionSupport.queryMerchantPreferentialByPager(merchantPreferentialPager);
		
		
		//商家相册
		merchantPhotoList=merchantPhotoActionSupport.findMerchantPhotoByMerchatId(id);
		if(merchantPhotoList==null){
			merchantPhotoList=new ArrayList<MerchantPhoto>();
		}
		
		
		
		//商户地图
		merchantTrafficMAP=new MerchantTrafficMAP();
		merchantTrafficMAP=merchantTrafficMAPActionSupport.getMerchantTrafficMapByMerchantId(id);
		
		merchant=merchantActionSupport.getMerchantById(id);
		
		//获得登录用户收藏的商家集合
		//当前用户的ID
		String userId=(String) getSession(MallCommand.USER_ID);
		
		if(userId!=null){			
			memberCollectPager.setUserid(userId);
			memberCollectPager.setMerchantId(id);
			memberCollectPager=memberCollectActionSupport.getMemberCollectByPager(memberCollectPager);
		}
		
		
		//==========================================   新版商户展示页面 begin
		
		
		
		//查询该商户下的团购商品  begin
		GoodsGroupRack ggr = new GoodsGroupRack();
		ggr.setMerchantId(id);
		ggr.setPageSize(2);		
		ggr.setState("30");
		ggr.setIsRack("1");
		List<Object> list = goodsGroupRackActionSupport.findGoodsGroupByPager(ggr).getList();
		goodsGroupRackList = new ArrayList<GoodsGroupRack>();
		for (Object object : list) {
			goodsGroupRackList.add((GoodsGroupRack)object);
		}		
		//查询该商户下的团购商品  over
		
		//查询该商户下的积分兑换商品 begin
		GoodsExchangeRack ger = new GoodsExchangeRack();
		ger.setMerchantId(id);
		ger.setPageSize(2);
		ger.setState("30");
		ger.setIsRack("1");
		List<Object> objList = goodsExchangeRackActionSupport.getGoodsExchangeRacks(ger).getList();
		goodsExchangeRackList = new ArrayList<GoodsExchangeRack>();
		for (Object object : objList) {
			goodsExchangeRackList.add((GoodsExchangeRack)object);
		}
		//查询该商户下的积分兑换商品 over
		
		
		//分店信息查询 begin
		Store store = new Store();
		try{
			store.setMerchantId(Integer.parseInt(id));
		}catch (Exception e) {
			log.error("查询Store信息时 传入参数转型Integer失败，参数为id="+id,e);
		}
		List<Object> storeObjList = storeActionSupport.getStoreByPager(store).getList();
		storeList = new ArrayList<Store>();
		for (Object object : storeObjList) {
			storeList.add((Store)object);
		}
		//分店信息查询 over
		
		//获取商户优惠信息 begin
		merchant = merchantActionSupport.getMerchantById(id);
		//获取商户优惠信息 over
		
		
		//==========================================   新版商户展示页面 over
		return "merchant_info";
	}
	
	public String getAllMerchantInfo(){
		log.info("商户信息");
		
//		Seller seller=new Seller();
//		seller.setMerchantId(id);
//		seller.setSellerType(TransType.Trans_Points.getValue());
//		seller.setState(Command.FBU_USERED_STATE);
//		List<Seller> sellerList=sellersActionSupport.getSellerListBySelective(seller);
//		
		//商户热推商品
		hotPushMerchantProductPager.setPageNumber(1);
		hotPushMerchantProductPager.setState(Command.FBU_USERED_STATE);
		hotPushMerchantProductPager.setMerchantId(id);//商户ID
		hotPushMerchantProductPager.setOrderBy("product_priority*1");//优先级
		hotPushMerchantProductPager.setOrderType(com.froad.client.merchantProduct.OrderType.DESC);
		hotPushMerchantProductPager=merchantProductActionSupport.queryMerchantProductByPager(hotPushMerchantProductPager);
		
		return "merchant_goods_list";
	}
	
	/**
	 * 直接优惠商户信息
	 * @return
	 */
	public String getMerchantPreferentialInfo(){
		log.info("商户信息");
		
		//增加直通车点击数
		merchantTrain=merchantTrainActionSupport.getMerchantTrainByMerchantId(id,null);
		if(merchantTrain!=null){
			
			if(merchantTrain.getClickes()==null || "".equals(merchantTrain.getClickes())){
				merchantTrain.setClickes("0");
			}
			
			Integer oldClickes=Integer.parseInt(merchantTrain.getClickes());
			
			MerchantTrain newMerchantTrain=new MerchantTrain();
			Integer newClickes=oldClickes+1;
			newMerchantTrain.setId(merchantTrain.getId());
			newMerchantTrain.setClickes(newClickes+"");
			merchantTrainActionSupport.updMerchantTrain(newMerchantTrain);
		}
		
		//商户介绍
		merchantPresent=merchantPresentActionSupport.getMerchantPresentInfo(id, null);
		
		//商户热推商品
		hotPushMerchantProductPager.setPageNumber(1);
		hotPushMerchantProductPager.setState(Command.FBU_USERED_STATE);
		hotPushMerchantProductPager.setMerchantId(id);//商户ID
		hotPushMerchantProductPager.setOrderBy("array*1");//优先级
		hotPushMerchantProductPager.setOrderType(com.froad.client.merchantProduct.OrderType.DESC);
		hotPushMerchantProductPager=merchantProductActionSupport.queryMerchantProductByPager(hotPushMerchantProductPager);
		
		//最新商品
		newMerchantProductPager.setPageNumber(1);
		newMerchantProductPager.setPageSize(8);
		newMerchantProductPager.setState(Command.FBU_USERED_STATE);
		newMerchantProductPager.setMerchantId(id);//商户ID	
		newMerchantProductPager=merchantProductActionSupport.queryMerchantProductByPager(newMerchantProductPager);
		
		//商家优惠活动公告
		merchantPreferentialPager.setPageNumber(1);
		merchantPreferentialPager.setPageSize(8);
		merchantPreferentialPager.setState(Command.FBU_USERED_STATE);
		merchantPreferentialPager.setMerchantId(id);//商户ID
		merchantPreferentialPager.setOrderBy("array*1");//排列数值
		merchantPreferentialPager.setOrderType(com.froad.client.merchantPreferential.OrderType.DESC);
		merchantPreferentialPager=merchantPreferentialActionSupport.queryMerchantPreferentialByPager(merchantPreferentialPager);
		
		
		//商家相册
		merchantPhotoList=merchantPhotoActionSupport.findMerchantPhotoByMerchatId(id);
		if(merchantPhotoList==null){
			merchantPhotoList=new ArrayList<MerchantPhoto>();
		}
		
		
		
		//商户地图
		merchantTrafficMAP=new MerchantTrafficMAP();
		merchantTrafficMAP=merchantTrafficMAPActionSupport.getMerchantTrafficMapByMerchantId(id);
		
		merchant=merchantActionSupport.getMerchantById(id);
		
		
		//获得登录用户收藏的商家集合
		//当前用户的ID
		String userId=(String) getSession(MallCommand.USER_ID);
		
		if(userId!=null){			
			memberCollectPager.setUserid(userId);
			memberCollectPager.setMerchantId(id);
			memberCollectPager=memberCollectActionSupport.getMemberCollectByPager(memberCollectPager);
		}
				
		

		//==========================================   新版商户展示页面 begin
		
		
		
		//查询该商户下的团购商品  begin
		GoodsGroupRack ggr = new GoodsGroupRack();
		ggr.setMerchantId(id);
		ggr.setPageSize(2);		
		ggr.setState("30");
		ggr.setIsRack("1");
		List<Object> list = goodsGroupRackActionSupport.findGoodsGroupByPager(ggr).getList();
		goodsGroupRackList = new ArrayList<GoodsGroupRack>();
		for (Object object : list) {
			goodsGroupRackList.add((GoodsGroupRack)object);
		}		
		//查询该商户下的团购商品  over
		
		//查询该商户下的积分兑换商品 begin
		GoodsExchangeRack ger = new GoodsExchangeRack();
		ger.setMerchantId(id);
		ger.setPageSize(2);
		ger.setState("30");
		ger.setIsRack("1");
		List<Object> objList = goodsExchangeRackActionSupport.getGoodsExchangeRacks(ger).getList();
		goodsExchangeRackList = new ArrayList<GoodsExchangeRack>();
		for (Object object : objList) {
			goodsExchangeRackList.add((GoodsExchangeRack)object);
		}
		//查询该商户下的积分兑换商品 over
		
		
		//分店信息查询 begin
		Store store = new Store();
		try{
			store.setMerchantId(Integer.parseInt(id));
		}catch (Exception e) {
			log.error("查询Store信息时 传入参数转型Integer失败，参数为id="+id,e);
		}
		List<Object> storeObjList = storeActionSupport.getStoreByPager(store).getList();
		storeList = new ArrayList<Store>();
		for (Object object : storeObjList) {
			storeList.add((Store)object);
		}
		//分店信息查询 over
		
		//获取商户优惠信息 begin
		merchant = merchantActionSupport.getMerchantById(id);
		//获取商户优惠信息 over
		
		
		//==========================================   新版商户展示页面 over
		
		return "merchant_info";
	}
	
	
	/**
	  * 方法描述：查询商家排名
	  * @param: 
	  * @return: json
	  */
	public void findRankMerchantList(){
		log.info("查询商家排名开始！");
		JSONArray array = new JSONArray();
		JSONObject json = new JSONObject();
		//商家排名
		Merchant rankMerchantPager=new Merchant();
		rankMerchantPager.setPageNumber(1);
		rankMerchantPager.setPageSize(8);
		rankMerchantPager.setState(Command.FBU_USERED_STATE);
		rankMerchantPager.setIsHot("1");
		rankMerchantPager.setOrderBy("hot_level*1");//优先级  
		rankMerchantPager.setIsInternalAccount("0");
		rankMerchantPager.setOrderType(com.froad.client.merchant.OrderType.DESC);
		
		rankMerchantPager=merchantActionSupport.getMerchantsPreferentialType(rankMerchantPager);
		List rankMerchantPagerList=rankMerchantPager.getList();
		if(rankMerchantPagerList!=null && rankMerchantPagerList.size()>0){
			json.put("reno",Command.respCode_SUCCESS);
			Merchant m = null;
			for(Object merchant:rankMerchantPagerList){	
				m = (Merchant)merchant;
				JSONObject orgs = new JSONObject();
				if(Assert.empty(m.getTagClassifyAId())
						&& Assert.empty(m.getTagDistrictAId())
						&& Assert.empty(m.getTagDistrictBId())){
					m.setTagDistrictAId("100001001");
				}
				orgs.put("id",m.getId());
				/*
				 * orgs.put("companyFullName",m.getCompanyFullName());
				 * 替换为门店全名
				 */
				if(m.getMstoreFullName()==null){
					orgs.put("companyFullName", "");
				}else{
					orgs.put("companyFullName", m.getMstoreFullName());
				}		
				/*
				 * orgs.put("companyLogoUrl",m.getCompanyLogoUrl());
				 * 替换为门店logo地址
				 */
				orgs.put("companyLogoUrl",m.getMstoreLogoUrl());	
				/*
				 * 	orgs.put("companyTagValue", m.getTagValue());
				 * 替换为门店标签
				 */
				List<TagMAP> list=tagActionSupport.getTagMapsByMerchantId(String.valueOf(m.getId()));
				orgs.put("companyTagClassifyA", list.get(0).getTagClassifyA());
				orgs.put("companyTagClassifyB", list.get(0).getTagClassifyB());				
				orgs.put("companyTagDistrictA", list.get(0).getTagDistrictA());
				orgs.put("companyTagDistrictB", list.get(0).getTagDistrictB());
				
				array.add(orgs);
			}
			json.put("rankMerchantList", array);
		}else{
			json.put("reno",Command.respCode_FAIL);
			json.put("points","0");
		}
		ajaxJson(json.toString());
	}
	
	/**
	  * 方法描述：查询最新商户
	  * @param: 
	  * @return: json
	  */
	public void findNewMerchantList(){
		log.info("查询最新商户开始！");
		JSONArray array = new JSONArray();
		JSONObject json = new JSONObject();
		//最新商户
		Merchant newMerchantPager=new Merchant();
		newMerchantPager.setPageNumber(1);
		newMerchantPager.setPageSize(8);
		newMerchantPager.setIsInternalAccount("0");
		newMerchantPager.setState(Command.FBU_USERED_STATE);
		newMerchantPager=merchantActionSupport.getMerchantsPreferentialType(newMerchantPager);
		List newMerchantPagerList=newMerchantPager.getList();
		if(newMerchantPagerList!=null && newMerchantPagerList.size()>0){
			json.put("reno",Command.respCode_SUCCESS);
			Merchant m = null;
			for(Object merchant:newMerchantPagerList){	
				m = (Merchant)merchant;
				JSONObject orgs = new JSONObject();
				orgs.put("id",m.getId());
				if(Assert.empty(m.getTagClassifyAId())
						&& Assert.empty(m.getTagDistrictAId())
						&& Assert.empty(m.getTagDistrictBId())){
					m.setTagDistrictAId("100001001");
				}
				/*
				 * orgs.put("companyFullName",m.getCompanyFullName());
				 * 替换为门店全名
				 */
					if(m.getMstoreFullName()==null){
						orgs.put("companyFullName", "");
					}else{
						orgs.put("companyFullName", m.getMstoreFullName());
					}
					
		
				/*
				 * orgs.put("companyLogoUrl",m.getCompanyLogoUrl());
				 * 替换为门店logo地址
				 */
				orgs.put("companyLogoUrl",m.getMstoreLogoUrl());
				
				//添加商户的标签
				List<TagMAP> list=tagActionSupport.getTagMapsByMerchantId(String.valueOf(m.getId()));
				orgs.put("companyTagClassifyA", list.get(0).getTagClassifyA());
				orgs.put("companyTagClassifyB", list.get(0).getTagClassifyB());				
				orgs.put("companyTagDistrictA", list.get(0).getTagDistrictA());
				orgs.put("companyTagDistrictB", list.get(0).getTagDistrictB());
				
				orgs.put("introduction","商家简介");
				array.add(orgs);
			}
			json.put("newMerchantList", array);
		}else{
			json.put("reno",Command.respCode_FAIL);
			json.put("points","0");
		}
		ajaxJson(json.toString());
	}
	
	public boolean validateSearch(Merchant merchant){
		boolean flag = true;
		if(merchant.getTagValue()!=null && !"".equals(merchant.getTagValue())){
			String tagValue = urlDecode(merchant.getTagValue(),"utf-8");
			
			flag = true;
		}
		
		return flag;
	}
	
	/**
	 * 进入商户添加操作员页面
	 * @return
	 */
	public String toAddClerk(){
		User user = getLoginUser();
		
		if(!Assert.empty(user.getUserID())){
			Merchant mer = merchantActionSupport.getMerchantInfo(user.getUserID());
			if(merchantUserSet == null){
				merchantUserSet = new MerchantUserSet();
			}
			merchantUserSet.setLoginName(getSessionValue(MallCommand.MERCHANTUSERSET_LOGIN_NAME));
			merchantUserSet.setMerchantId(mer==null?"":String.valueOf(mer.getId()));
			log.info("查询商家的所有门店信息");
			storeList= storeActionSupport.getStoresByMerchantId(mer.getId());	
			return "success";
		}
		return "failse";
	}
	
	/**
	 * 进入商户添加财务员页面
	 * @return
	 */
	public String toAddTreasurer(){

		User user = getLoginUser();
		
		if(!Assert.empty(user.getUserID())){
			Merchant mer = merchantActionSupport.getMerchantInfo(user.getUserID());
			if(mer.getId().toString()=="" || mer.getId()==null){
				return "failse";
			}
			if(merchantUserSet == null){
				merchantUserSet = new MerchantUserSet();
			}
			merchantUserSet.setLoginName(getSessionValue(MallCommand.MERCHANTUSERSET_LOGIN_NAME));
			merchantUserSet.setMerchantId(mer==null?"":String.valueOf(mer.getId()));
			log.info("查询商家的所有门店信息");
			storeList= storeActionSupport.getStoresByMerchantId(mer.getId());			
			return "success";
		}
		return "failse";
	}
	
	/**
	 * 添加操作员
	 * @return
	 */
	public String addClerk(){
		if(merchantUserSet == null){
			return "failse";			
		}
		User user = getLoginUser();
		//String pwd = "";
		if(!Assert.empty(merchantUserSet.getLoginName()) 
				&& !Assert.empty(merchantUserSet.getIsAdmin())
				&& !Assert.empty(merchantUserSet.getBeName()) && !Assert.empty(merchantUserSet.getBeCodepwd())){
			MerchantUserSet clerkTmp = new MerchantUserSet();
			clerkTmp.setLoginName(merchantUserSet.getLoginName());
			clerkTmp.setUserId(user.getUserID());
			String maxBecode = merchantUserSetActionSupport.getMaxBeCode(clerkTmp);
			if(!Assert.empty(maxBecode)){
				Integer newBecode = Integer.valueOf(maxBecode)+1;//获取新的操作员最大工号				
				merchantUserSet.setBeCode(String.valueOf(newBecode));
				//对密码MD5加密
				String pwd = new Md5PasswordEncoder().encodePassword(merchantUserSet.getBeCodepwd(), merchantUserSet.getLoginName()+String.valueOf(newBecode));
				merchantUserSet.setBeCodepwd(pwd);
				//pwd = "888888";
			}else{
				return "failse";
			}
			merchantUserSet.setUserId(user.getUserID());
			
			//merchantUserSet.setBeCodepwd(pwd);
			if(Assert.empty(merchantUserSet.getMerchantId())){
				Merchant mer = merchantActionSupport.getMerchantInfo(user.getUserID());
				merchantUserSet.setMerchantId(mer==null?"":String.valueOf(mer.getId()));
			}
			merchantUserSet.setRoleType("0");
			merchantUserSet.setState(Command.FBU_USERED_STATE);
			merchantUserSet.setBelongStoreId(StoreId);
			Integer usersetId = merchantUserSetActionSupport.addMerchantClerk(merchantUserSet);
			List<Seller> sellerInfo = (List<Seller>)getSession(MallCommand.SELLER_LIST);
			
			Map<String, String> type = new HashMap<String, String>();
			for (Seller seller : sellerInfo) {
				if("03".equals(seller.getSellerType())){
					type.put("03", seller.getId()+"");
				}else if("06".equals(seller.getSellerType())){
					type.put("06", seller.getId()+"");
				}else if("01".equals(seller.getSellerType())){
					type.put("01", seller.getId()+"");
				}else if("02".equals(seller.getSellerType())){
					type.put("02", seller.getId()+"");
				}
			}
			
			if(usersetId != null){
				if(clerkSellerType != null && clerkSellerType.length > 0){
					//管理员为操作员选择了至少一个权限
					String merchantId = merchantUserSet.getMerchantId();//获取该商户的ID
					clerkAuthority=new ArrayList<String>();
					
					for (String str : clerkSellerType) {
						MerchantUserSeller mus = new MerchantUserSeller();
						mus.setMerchantId(merchantId);
						mus.setState("30");
						mus.setMerchantUserId(usersetId+"");
			
						if(str.indexOf("or") == -1){
							if("03".equals(str)){
								mus.setSellerId(type.get("03"));
								mus.setSellerType("03");
								merchantUserSellerActionSupport.addMerchantUserSeller(mus);
							}else if("06".equals(str)){
								mus.setSellerId(type.get("06"));
								mus.setSellerType("06");
								merchantUserSellerActionSupport.addMerchantUserSeller(mus);
							}
							
						}else{
							String[] strArray = str.split("or");
							if(!Assert.empty(type.get(strArray[0]))){
								mus.setSellerId(type.get(strArray[0]));		
								mus.setSellerType("01");
								merchantUserSellerActionSupport.addMerchantUserSeller(mus);
							}
							if(!Assert.empty(type.get(strArray[1]))){
								mus = new MerchantUserSeller();
								mus.setMerchantId(merchantId);
								mus.setState("30");
								mus.setSellerId(type.get(strArray[1]));
								mus.setMerchantUserId(usersetId+"");
								mus.setSellerType("02");
								merchantUserSellerActionSupport.addMerchantUserSeller(mus);	
							}												
						}	
						clerkAuthority.add(str);
					}
					
				}else{
					log.info("添加操作员失败，请至少为操作员赋予一个权限");			
					return "failse";
					
				}
			}else{
				log.error("插入操作员失败，后续程序无法执行");
				return "failse";				
			}			
			
		}else{
			return "failse";
		}
		//设置操作员所属门店
		String stroeName="";
		if(merchantUserSet.getBelongStoreId()==null || "".equals(merchantUserSet.getBelongStoreId())){
			stroeName="全部门店";
		}else{
			stroeName=storeActionSupport.getStoreById(merchantUserSet.getBelongStoreId()).getShortName();
		}
		merchantUserSet.setBelongStoreId(stroeName);
		log.info("添加操作员成功");
		return "success";
	}
	
	
	/**
	  * 方法描述：添加财务员
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2013-11-22 下午02:03:58
	  */
	public String addTreasurer(){
		if(merchantUserSet == null){
			return "failse";			
		}
		User user = getLoginUser();
		if(!Assert.empty(merchantUserSet.getLoginName()) 
				&& !Assert.empty(merchantUserSet.getIsAdmin())
				&& !Assert.empty(merchantUserSet.getBeName()) && !Assert.empty(merchantUserSet.getBeCodepwd())){
			MerchantUserSet Tmp = new MerchantUserSet();
			Tmp.setLoginName(merchantUserSet.getLoginName());
			Tmp.setUserId(user.getUserID());
			String maxBecode = merchantUserSetActionSupport.getMaxBeCode(Tmp);
			if(!Assert.empty(maxBecode)){
				Integer newBecode = Integer.valueOf(maxBecode)+1;//获取新的操作员最大工号				
				merchantUserSet.setBeCode(String.valueOf(newBecode));
				//对密码MD5加密
				String pwd = new Md5PasswordEncoder().encodePassword(merchantUserSet.getBeCodepwd(), merchantUserSet.getLoginName()+String.valueOf(newBecode));
				merchantUserSet.setBeCodepwd(pwd);				
				merchantUserSet.setRoleType("1");
			}else{
				return "failse";
			}
			merchantUserSet.setUserId(user.getUserID());
			if(Assert.empty(merchantUserSet.getMerchantId())){
				Merchant mer = merchantActionSupport.getMerchantInfo(user.getUserID());
				merchantUserSet.setMerchantId(mer==null?"":String.valueOf(mer.getId()));
			}
			merchantUserSet.setState(Command.FBU_USERED_STATE);
			merchantUserSet.setBelongStoreId(StoreId);
			Integer usersetId = merchantUserSetActionSupport.addMerchantClerk(merchantUserSet);
			if(usersetId==null){				
				return "failse";
			}
		}else{
			log.info("添加财务员失败，没有登录或不具有该权限");
			return "failse";
		}
		//设置操作员所属门店
		String stroeName="";
		if(merchantUserSet.getBelongStoreId()==null || "".equals(merchantUserSet.getBelongStoreId())){
			stroeName="全部门店";
		}else{
			stroeName=storeActionSupport.getStoreById(merchantUserSet.getBelongStoreId()).getShortName();
		}
		merchantUserSet.setBelongStoreId(stroeName);
		log.info("添加财务员");
		return "success";
			
	}
	
	
	/**
	 * 修改操作员密码
	 * @throws AppException_Exception
	 * @throws IOException
	 */
	public void changeClerkPassword() throws AppException_Exception, IOException{
		JSONObject jObject = new JSONObject();
		jObject.put("reno", "1");
		boolean falg=true;
		String userID = (String)getSession(MallCommand.LOGIN_USER_ID);
		String beCode = (String)getSession("beCode");
		MerchantUserSet clerk = new MerchantUserSet();
		clerk.setUserId(userID);
		clerk.setBeCode(beCode);
	    
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession(false);
		if(Assert.empty(userID)){
			jObject.put("desc", "请登录");
	    	falg = false;
		}else if(oldpassword == null || "".equals(oldpassword.trim())) {
	    	jObject.put("desc", "输入的原密码不能为空");
	    	falg = false;
	    }  else if(!Validate.validateOfPwd(password)) {
	    	jObject.put("desc", "为提高密码安全性，密码请选用6位及以上字符");
	    	falg = false;
	    } else if(!password.equals(password1)) {
	    	jObject.put("desc", "新密码和确认新密码输入不一致");
	    	falg = false;
	    }
	    if(falg){
		    //获得信息
		    List<MerchantUserSet> clerkList = merchantUserSetActionSupport.getMerchantUserSetList(clerk);
		    if(clerkList == null || clerkList.size() == 0){
		    	jObject.put("desc", "不存在操作员");
		    }else{
		    	clerk = clerkList.get(0);
		    	String pwdOld = new Md5PasswordEncoder().encodePassword(oldpassword, clerk.getLoginName()+clerk.getBeCode());
		    	if(!clerk.getBeCodepwd().equals(pwdOld)){
		    		jObject.put("desc", "原密码错误");
		    		log.info("用户=>>>> " + clerk.getLoginName()+",操作员:"+clerk.getBeCode() + " 修改密码失败"+",详情:"+"原密码错误");
		    	} else {	    
		    		MerchantUserSet clerkNew = new MerchantUserSet();
		    		String pwdNew = new Md5PasswordEncoder().encodePassword(password, clerk.getLoginName()+clerk.getBeCode());
		    		clerkNew.setBeCodepwd(pwdNew);	    	
		    		try {
		    			clerkNew.setUserId(clerk.getUserId());
		    			clerkNew.setBeCode(clerk.getBeCode());

		    			MerchantUserSet clerkRes = merchantUserSetActionSupport.updatePwdByUserIdAndBecode(clerkNew);
		    			if(clerkRes!=null){
		    				log.info("用户=>>>> " + clerkRes.getLoginName()+",操作员:"+clerkRes.getBeCode() + " 修改密码成功");
		    				//session.invalidate();
		    				jObject.clear();
		    				jObject.put("desc", "修改密码成功");
		    				jObject.put("reno", "0");
		    				Merchant m = merchantActionSupport.getMerchantInfo(userID);
		    				if(m!=null && userID.equals(m.getUserId())){
		    					session.invalidate();
		    					jObject.put("userType", "1");
		    				}else{
		    					jObject.put("userType", "2");
		    				}
		    			}else{
		    				log.info("用户=>>>> 修改密码失败");
		    				jObject.put("desc", "修改密码失败,请确认是否账号正确");
		    			}
		    			
		    		} catch (Exception e) {
		    			log.info("用户=>>>>  修改密码失败,发生异常!");
		    			jObject.put("desc", "修改密码失败");
		    		}
		    		
		    	}	    	
		    }
	    }
	    sendMsg(jObject.toString());   
	}
	
	/**
	 * 修改搜索条件历史记录
	 * @param search
	 * @return Integer
	 */
	public Integer updSearchHis(Merchant merchant){
		Searches search = new Searches();
		search.setMPreferentialType(merchant.getMerchantPriority());
		search.setSearchKeywords(merchant.getTagValue());
		search.setTagClassifyAId(merchant.getTagClassifyAId());
		search.setTagDistrictAId(merchant.getTagDistrictAId());
		search.setTagClassifyBId(merchant.getTagClassifyBId());
		search.setTagDistrictBId(merchant.getTagDistrictBId());
		Integer flag =null;
		if((search.getMPreferentialType()==null || "".equals(search.getMPreferentialType())) 
				&& (search.getSearchKeywords()==null || "".equals(search.getSearchKeywords())) 
				&& (search.getTagClassifyAId()==null || "".equals(search.getTagClassifyAId()))
				&& (search.getTagDistrictAId()==null || "".equals(search.getTagDistrictAId()))
				&& (search.getTagClassifyBId()==null || "".equals(search.getTagClassifyBId()))
				&& (search.getTagDistrictBId()==null || "".equals(search.getTagDistrictBId()))){
			return 0;
		}
		List<Searches> searchList = searchesActionSupport.getSearchesByHis(search);
		if(searchList!=null && searchList.size()>0){
			Integer scount = Integer.valueOf(searchList.get(0).getSearchCount()==null?"0":searchList.get(0).getSearchCount());
			search.setSearchCount(String.valueOf(scount+1));
			search.setId(searchList.get(0).getId());
			flag = searchesActionSupport.updSearchesByHis(search);
		}else{
			search.setSearchCount("1");
			flag = searchesActionSupport.addSearches(search);
		}
		return flag;
	}
	
	
	//商户分店地址
	public void ajaxMerchantStoreList(){
		//查询
		Merchant merchant=merchantActionSupport.getMerchantById(id);
		
		List<com.froad.client.merchant.Store> storeObjList = merchant.getStoreList();
		
		Map<String,Object> merchantStoreMap=new HashMap<String,Object>();
		merchantStoreMap.put("merchantId", id);
		merchantStoreMap.put("storeTotalNumber", storeObjList.size());
		
		List list=new ArrayList();
		List storeList=new ArrayList();
		
		if(storeObjList.size()>0){
			for (com.froad.client.merchant.Store st : storeObjList) {
				Map<String,Object> storeMap=new HashMap<String,Object>();
				storeMap.put("fullName", st.getFullName());
				storeMap.put("address", st.getAddress());
				storeMap.put("telephone", st.getTelephone1());
				storeMap.put("coordinate", st.getCoordinate());
				
				storeList.add(storeMap);
			}
			merchantStoreMap.put("storeList", storeList);
		}
		else{
			merchantTrafficMAP=merchantTrafficMAPActionSupport.getMerchantTrafficMapByMerchantId(id);
			
			Map<String,Object> storeMap=new HashMap<String,Object>();
			storeMap.put("fullName", merchant.getMstoreFullName());
			storeMap.put("address", merchant.getMstoreAddress());
			storeMap.put("telephone", merchant.getMstoreTel());
			storeMap.put("coordinate", merchantTrafficMAP==null ? null : merchantTrafficMAP.getCoordinate());
			
			storeList.add(storeMap);
			merchantStoreMap.put("storeList", storeList);
		}
		String jsonStr=JsonUtil.getJsonString4Map(merchantStoreMap);
		//log.info("商户分店地址："+jsonStr);
		
		ajaxJson(jsonStr);
	}
	
	
	public MerchantActionSupport getMerchantActionSupport() {
		return merchantActionSupport;
	}
	public void setMerchantActionSupport(MerchantActionSupport merchantActionSupport) {
		this.merchantActionSupport = merchantActionSupport;
	}

	public MerchantTrainActionSupport getMerchantTrainActionSupport() {
		return merchantTrainActionSupport;
	}

	public void setMerchantTrainActionSupport(
			MerchantTrainActionSupport merchantTrainActionSupport) {
		this.merchantTrainActionSupport = merchantTrainActionSupport;
	}

	public MerchantPresentActionSupport getMerchantPresentActionSupport() {
		return merchantPresentActionSupport;
	}

	public void setMerchantPresentActionSupport(
			MerchantPresentActionSupport merchantPresentActionSupport) {
		this.merchantPresentActionSupport = merchantPresentActionSupport;
	}

	public MerchantProductActionSupport getMerchantProductActionSupport() {
		return merchantProductActionSupport;
	}

	public void setMerchantProductActionSupport(
			MerchantProductActionSupport merchantProductActionSupport) {
		this.merchantProductActionSupport = merchantProductActionSupport;
	}

	public MerchantPreferentialActionSupport getMerchantPreferentialActionSupport() {
		return merchantPreferentialActionSupport;
	}

	public void setMerchantPreferentialActionSupport(
			MerchantPreferentialActionSupport merchantPreferentialActionSupport) {
		this.merchantPreferentialActionSupport = merchantPreferentialActionSupport;
	}

	public MerchantPhotoActionSupport getMerchantPhotoActionSupport() {
		return merchantPhotoActionSupport;
	}

	public void setMerchantPhotoActionSupport(
			MerchantPhotoActionSupport merchantPhotoActionSupport) {
		this.merchantPhotoActionSupport = merchantPhotoActionSupport;
	}


	public MerchantPreferential getMerchantPreferentialPager() {
		return merchantPreferentialPager;
	}

	public void setMerchantPreferentialPager(
			MerchantPreferential merchantPreferentialPager) {
		this.merchantPreferentialPager = merchantPreferentialPager;
	}

	public List<MerchantPhoto> getMerchantPhotoList() {
		return merchantPhotoList;
	}

	public void setMerchantPhotoList(List<MerchantPhoto> merchantPhotoList) {
		this.merchantPhotoList = merchantPhotoList;
	}

	public MerchantTrafficMAP getMerchantTrafficMAP() {
		return merchantTrafficMAP;
	}

	public void setMerchantTrafficMAP(MerchantTrafficMAP merchantTrafficMAP) {
		this.merchantTrafficMAP = merchantTrafficMAP;
	}

	public MerchantTrain getMerchantTrain() {
		return merchantTrain;
	}

	public void setMerchantTrain(MerchantTrain merchantTrain) {
		this.merchantTrain = merchantTrain;
	}

	public MerchantPresent getMerchantPresent() {
		return merchantPresent;
	}

	public void setMerchantPresent(MerchantPresent merchantPresent) {
		this.merchantPresent = merchantPresent;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public GoodsRebateRackActionSupport getGoodsRebateRackActionSupport() {
		return goodsRebateRackActionSupport;
	}

	public void setGoodsRebateRackActionSupport(
			GoodsRebateRackActionSupport goodsRebateRackActionSupport) {
		this.goodsRebateRackActionSupport = goodsRebateRackActionSupport;
	}

	public GoodsRebateRack getHotPushGoodsRebateRackPager() {
		return hotPushGoodsRebateRackPager;
	}

	public void setHotPushGoodsRebateRackPager(
			GoodsRebateRack hotPushGoodsRebateRackPager) {
		this.hotPushGoodsRebateRackPager = hotPushGoodsRebateRackPager;
	}

	public GoodsRebateRack getNewGoodsRebateRackPager() {
		return newGoodsRebateRackPager;
	}

	public void setNewGoodsRebateRackPager(GoodsRebateRack newGoodsRebateRackPager) {
		this.newGoodsRebateRackPager = newGoodsRebateRackPager;
	}

	public TagActionSupport getTagActionSupport() {
		return tagActionSupport;
	}

	public void setTagActionSupport(TagActionSupport tagActionSupport) {
		this.tagActionSupport = tagActionSupport;
	}

	public Merchant getPager() {
		return pager;
	}

	public void setPager(Merchant pager) {
		this.pager = pager;
	}

	public List<TagDistrictB> getAllTagDistrictBList() {
		return allTagDistrictBList;
	}

	public void setAllTagDistrictBList(List<TagDistrictB> allTagDistrictBList) {
		this.allTagDistrictBList = allTagDistrictBList;
	}

	public List<TagClassifyB> getAllTagClassifyBList() {
		return allTagClassifyBList;
	}

	public void setAllTagClassifyBList(List<TagClassifyB> allTagClassifyBList) {
		this.allTagClassifyBList = allTagClassifyBList;
	}

	public List<TagClassifyA> getAllTagClassifyAList() {
		return allTagClassifyAList;
	}

	public void setAllTagClassifyAList(List<TagClassifyA> allTagClassifyAList) {
		this.allTagClassifyAList = allTagClassifyAList;
	}

	public MerchantProduct getHotPushMerchantProductPager() {
		return hotPushMerchantProductPager;
	}

	public void setHotPushMerchantProductPager(
			MerchantProduct hotPushMerchantProductPager) {
		this.hotPushMerchantProductPager = hotPushMerchantProductPager;
	}

	public MerchantProduct getNewMerchantProductPager() {
		return newMerchantProductPager;
	}

	public void setNewMerchantProductPager(MerchantProduct newMerchantProductPager) {
		this.newMerchantProductPager = newMerchantProductPager;
	}

	public AdvertActionSupport getAdvertActionSupport() {
		return advertActionSupport;
	}

	public void setAdvertActionSupport(AdvertActionSupport advertActionSupport) {
		this.advertActionSupport = advertActionSupport;
	}
	
	public List<Advert> getAdvertList() {
		return advertList;
	}

	public void setAdvertList(List<Advert> advertList) {
		this.advertList = advertList;
	}
	
	public MerchantTrafficMAPActionSupport getMerchantTrafficMAPActionSupport() {
		return merchantTrafficMAPActionSupport;
	}

	public void setMerchantTrafficMAPActionSupport(
			MerchantTrafficMAPActionSupport merchantTrafficMAPActionSupport) {
		this.merchantTrafficMAPActionSupport = merchantTrafficMAPActionSupport;
	}

	public MerchantUserSetActionSupport getMerchantUserSetActionSupport() {
		return merchantUserSetActionSupport;
	}

	public void setMerchantUserSetActionSupport(
			MerchantUserSetActionSupport merchantUserSetActionSupport) {
		this.merchantUserSetActionSupport = merchantUserSetActionSupport;
	}

	public MerchantUserSet getMerchantUserSet() {
		return merchantUserSet;
	}

	public void setMerchantUserSet(MerchantUserSet merchantUserSet) {
		this.merchantUserSet = merchantUserSet;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getOldpassword() {
		return oldpassword;
	}

	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}
	
	public SellersActionSupport getSellersActionSupport() {
		return sellersActionSupport;
	}

	public void setSellersActionSupport(SellersActionSupport sellersActionSupport) {
		this.sellersActionSupport = sellersActionSupport;
	}
	
	public MemberCollect getMemberCollectPager() {
		return memberCollectPager;
	}
	
	public void setMemberCollectPager(MemberCollect memberCollectPager) {
		this.memberCollectPager = memberCollectPager;
	}
	
	public MemberCollectActionSupport getMemberCollectActionSupport() {
		return memberCollectActionSupport;
	}
	
	public void setMemberCollectActionSupport(
			MemberCollectActionSupport memberCollectActionSupport) {
		this.memberCollectActionSupport = memberCollectActionSupport;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getSortId() {
		return sortId;
	}

	public void setSortId(String sortId) {
		this.sortId = sortId;
	}

	public String[] getClerkSellerType() {
		return clerkSellerType;
	}

	public void setClerkSellerType(String[] clerkSellerType) {
		this.clerkSellerType = clerkSellerType;
	}

	public MerchantUserSellerActionSupport getMerchantUserSellerActionSupport() {
		return merchantUserSellerActionSupport;
	}

	public void setMerchantUserSellerActionSupport(
			MerchantUserSellerActionSupport merchantUserSellerActionSupport) {
		this.merchantUserSellerActionSupport = merchantUserSellerActionSupport;
	}

	public SearchesActionSupport getSearchesActionSupport() {
		return searchesActionSupport;
	}

	public void setSearchesActionSupport(SearchesActionSupport searchesActionSupport) {
		this.searchesActionSupport = searchesActionSupport;
	}

	
	public GoodsGroupRackActionSupport getGoodsGroupRackActionSupport() {
		return goodsGroupRackActionSupport;
	}

	public void setGoodsGroupRackActionSupport(
			GoodsGroupRackActionSupport goodsGroupRackActionSupport) {
		this.goodsGroupRackActionSupport = goodsGroupRackActionSupport;
	}

	public List<GoodsGroupRack> getGoodsGroupRackList() {
		return goodsGroupRackList;
	}

	public void setGoodsGroupRackList(List<GoodsGroupRack> goodsGroupRackList) {
		this.goodsGroupRackList = goodsGroupRackList;
	}

	public GoodsExchangeRackActionSupport getGoodsExchangeRackActionSupport() {
		return goodsExchangeRackActionSupport;
	}

	public void setGoodsExchangeRackActionSupport(
			GoodsExchangeRackActionSupport goodsExchangeRackActionSupport) {
		this.goodsExchangeRackActionSupport = goodsExchangeRackActionSupport;
	}

	public List<GoodsExchangeRack> getGoodsExchangeRackList() {
		return goodsExchangeRackList;
	}

	public void setGoodsExchangeRackList(
			List<GoodsExchangeRack> goodsExchangeRackList) {
		this.goodsExchangeRackList = goodsExchangeRackList;
	}

	public StoreActionSupport getStoreActionSupport() {
		return storeActionSupport;
	}

	public void setStoreActionSupport(StoreActionSupport storeActionSupport) {
		this.storeActionSupport = storeActionSupport;
	}

	public List<Store> getStoreList() {
		return storeList;
	}

	public void setStoreList(List<Store> storeList) {
		this.storeList = storeList;
	}

	public String getSytleCssName() {
		return sytleCssName;
	}

	public void setSytleCssName(String sytleCssName) {
		this.sytleCssName = sytleCssName;
	}

	public String getStoreId() {
		return StoreId;
	}

	public void setStoreId(String storeId) {
		StoreId = storeId;
	}

	public List<String> getClerkAuthority() {
		return clerkAuthority;
	}

	public void setClerkAuthority(List<String> clerkAuthority) {
		this.clerkAuthority = clerkAuthority;
	}
	
	
}
