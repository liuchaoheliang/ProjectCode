package com.froad.action.api.goodsRebateRack;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;

import com.froad.action.api.BaseApiAction;
import com.froad.action.api.command.FroadAPICommand;
import com.froad.action.support.ClientGoodsRebateRackActionSupport;
import com.froad.action.support.MerchantActionSupport;
import com.froad.action.support.SellersActionSupport;
import com.froad.action.support.StoreActionSupport;
import com.froad.client.Store.Store;
import com.froad.client.clientGoodsRebateRack.ClientGoodsRebateRack;
import com.froad.client.merchant.Merchant;
import com.froad.client.merchant.MerchantPhoto;
import com.froad.client.merchant.MerchantProduct;
import com.froad.client.merchant.MerchantTrain;
import com.froad.client.merchant.PreferType;
import com.froad.client.merchant.TagMAP;
import com.froad.client.sellers.Seller;
 
import com.froad.util.command.Command;
import com.froad.util.command.State;

/*
 * 积分返利
 */
public class GoodsRebateRackAction extends BaseApiAction{
	private static Logger logger = Logger.getLogger(GoodsRebateRackAction.class);
	private MerchantActionSupport merchantActionSupport;
	private ClientGoodsRebateRackActionSupport clientGoodsRebateRackActionSupport;
	private SellersActionSupport sellersActionSupport;	
	private String reno;//操作结果码
	private String remsg;//响应信息
	private Map<String,Object> rebody=new HashMap<String,Object>();//主体
	private String recount;//返回请求标识号（原样返回）
	private String returnTime;//返回时间
	
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss"); 
	
	/**
	  * 方法描述：积分返利，商户列表查询0601
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 孙庆亚 sunqingya@f-road.com.cn
	  * @time: Feb 26, 2013 2:48:58 PM
	  */
	public String queryMerchantByType_POINTS(){
		logger.info("[GoodsRebateRackAction- queryMerchantByType_POINTS]body:"+body);
		JSONArray arr = new JSONArray();
		try{
			Merchant merchant = new Merchant();
			merchant.setPreferentialType("2");
			merchant.setState(Command.STATE_START);
			merchant.setPageSize(500);
			merchant = merchantActionSupport.getMerchantByPager(merchant);
			//logger.info("size():"+merchant.getList().size());
			if(merchant.getList()!=null&&merchant.getList().size()>0){
				for(int i=0;i<merchant.getList().size();i++){
					JSONObject one = new JSONObject();
					Merchant mer =(Merchant) merchant.getList().get(i);
					one.put("merchantID",mer.getId());
					one.put("merchantName",mer.getMstoreFullName());
					one.put("merchantIntce",mer.getMerchantPresent().getTxt());//商家介绍					
					one.put("merchantAdress",mer.getMstoreAddress());
					one.put("merchantPictures",FroadAPICommand.makePicURL(mer.getMstoreLogoUrl(),FroadAPICommand.PIC_THUMBNAIL));
					one.put("merchantTel",mer.getMstoreTel());
					
					String tag = "";
					List<TagMAP> listA = mer.getTagMapList();
					if(listA.size()>0){
						for(int j=0;j<listA.size();j++){
							tag = tag+"  "+listA.get(j).getTagClassifyA().getTagValue()==null?"":listA.get(j).getTagClassifyA().getTagValue() + 
									  "  "+listA.get(j).getTagClassifyB().getTagValue()==null?"":listA.get(j).getTagClassifyB().getTagValue()+     
									 "  "+listA.get(j).getTagDistrictA().getTagValue()==null?"":listA.get(j).getTagDistrictA().getTagValue()+
									"  "+listA.get(j).getTagDistrictB().getTagValue()==null?"":listA.get(j).getTagDistrictB().getTagValue();
						}
					}
					one.put("tagValue",tag);
					one.put("rebateInfo",mer.getPointRate());
					one.put("hotLevel", mer.getHotLevel());
					arr.add(one);
				}
			}
		}catch (Exception e) {
			reno="9999"; //异常
			logger.error("login Exception",e);
		}
		reno="0000";
		rebody.put("merchantList",arr);
		logger.info("返回的主体："+rebody.toString());
		return SUCCESS;
	}
	
	
	 
	/**
	  * 方法描述：商户商品信息查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 孙庆亚 sunqingya@f-road.com.cn
	  * @time: Feb 26, 2013 2:49:08 PM
	  */
	public String queryMerchantProductByPager(){
		logger.info("[GoodsRebateRackAction- getGoodsRebateRackByPager]body:"+body);
		try{
			JSONObject jsonObject = JSONObject.fromObject(body);
			String merchantID = jsonObject.getString("merchantID");
//			Seller seller= new Seller();
//			seller.setMerchantId(merchantID);
//			seller.setSellerType(FroadAPICommand.trans_type_PointBack);
//			List<Seller> sellers = sellersActionSupport.getSellerListBySelective(seller);
//			if(sellers.size()==0){
//				reno="0601";
//				logger.info("merchantID:"+merchantID+",没有获得卖家信息。");
//				return SUCCESS;
//			}
//			ClientGoodsRebateRack clientGoodsRebateRack = new ClientGoodsRebateRack();
//			clientGoodsRebateRack.setPageSize(100);
//			clientGoodsRebateRack.setIsRack("1");
//			clientGoodsRebateRack.setSellerId(sellers.get(0).getId());
			 
			Merchant merchant = merchantActionSupport.getMerchantById(merchantID);
			rebody.put("merchantID",merchant.getId());
			rebody.put("merchantName",merchant.getMstoreFullName());
			rebody.put("merchantIntce",merchant.getMerchantPresent().getTxt());//商家介绍			
			 rebody.put("merchantAdress",merchant.getMstoreAddress()+" "+merchant.getMstoreShortName());		
			rebody.put("merchantPictures",  FroadAPICommand.makePicURL(merchant.getMstoreLogoUrl(),FroadAPICommand.PIC_SAMLL));
			rebody.put("merchantTel",merchant.getMstoreTel());
			String collectNum="0";
			String clickNum="0";
			MerchantTrain merchantTran=merchant.getMerchantTrain() ;
			if(merchantTran!=null){
				 collectNum= merchantTran.getCollectes()!=null?merchantTran.getCollectes():collectNum ;
				 clickNum= merchantTran.getClickes()!=null?merchantTran.getClickes():clickNum;
			}
			rebody.put("collectNum",collectNum);
			rebody.put("visitNum",clickNum);
			String tag = "";
			List<TagMAP> listA = merchant.getTagMapList();
			if(listA.size()>0){
				for(int j=0;j<listA.size();j++){
					tag = tag+"  "+listA.get(j).getTagClassifyA().getTagValue()==null?"":listA.get(j).getTagClassifyA().getTagValue() + 
							  "  "+listA.get(j).getTagClassifyB().getTagValue()==null?"":listA.get(j).getTagClassifyB().getTagValue()+     
							 "  "+listA.get(j).getTagDistrictA().getTagValue()==null?"":listA.get(j).getTagDistrictA().getTagValue()+
							"  "+listA.get(j).getTagDistrictB().getTagValue()==null?"":listA.get(j).getTagDistrictB().getTagValue();
				}
			}
			List<MerchantPhoto> list2 = merchant.getPhotoList();//商家相册
			JSONArray arr2 = new JSONArray();
			if(list2!=null&&list2.size()>0){
				for(int i=0;i<list2.size();i++){
					JSONObject one = new JSONObject();
					MerchantPhoto merchantPhoto =list2.get(i);
					one.put("url",FroadAPICommand.makePicURL(merchantPhoto.getPhotoUrl(),FroadAPICommand.PIC_BIG));
					arr2.add(one);
				}
			}
			rebody.put("photos",arr2);
			rebody.put("tagValue",tag);
			rebody.put("rebateInfo",merchant.getPointRate());
			
			//商家商品
			List<MerchantProduct> list = merchant.getProductList();
			JSONArray arr = new JSONArray();
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					JSONObject one = new JSONObject();
					MerchantProduct metchantProduct =list.get(i);
					one.put("sourceUrl",FroadAPICommand.makePicURL(metchantProduct.getPhotoUrl(),FroadAPICommand.PIC_SAMLL) );
					one.put("recommend","1");
					one.put("goodsDesc",metchantProduct.getTxt2().replaceAll("<br/>", "\r\n"));
					one.put("pointRate", metchantProduct.getPointRate());
					one.put("price", metchantProduct.getPrice());
					one.put("marketPrice", metchantProduct.getPrice());
					one.put("goodsName",metchantProduct.getTxt1());
					one.put("goodsId",metchantProduct.getId());
					arr.add(one);
				}
			}
			/*clientGoodsRebateRack = clientGoodsRebateRackActionSupport.getGoodsRebateRackByPager(clientGoodsRebateRack);
			JSONArray arr = new JSONArray();
			if(clientGoodsRebateRack.getList()!=null&&clientGoodsRebateRack.getList().size()>0){
				for(int i=0;i<clientGoodsRebateRack.getList().size();i++){
					ClientGoodsRebateRack clinetGoodsRebateRackOne = (ClientGoodsRebateRack) clientGoodsRebateRack.getList().get(i);
					JSONObject json = new JSONObject();
					json.put("goodsId", clinetGoodsRebateRackOne.getGoodsId()==null?"":clinetGoodsRebateRackOne.getGoodsId());
					json.put("goodsName", clinetGoodsRebateRackOne.getGoods().getGoodsName()==null?"":clinetGoodsRebateRackOne.getGoods().getGoodsName());
					json.put("price", clinetGoodsRebateRackOne.getGoods().getPrice()==null?"":clinetGoodsRebateRackOne.getGoods().getPrice());
					json.put("marketPrice", clinetGoodsRebateRackOne.getGoods().getMarketPrice()==null?"":clinetGoodsRebateRackOne.getGoods().getMarketPrice());
					json.put("recommend", clinetGoodsRebateRackOne.getIsRecommend()==null?"":clinetGoodsRebateRackOne.getIsRecommend());
					json.put("goodsDesc", clinetGoodsRebateRackOne.getGoods().getGoodsDesc()==null?"":clinetGoodsRebateRackOne.getGoods().getGoodsDesc());
					for(int img=0;img<clinetGoodsRebateRackOne.getClientGoodsRebateRackImagesList().size();img++){
						if(FroadAPICommand.pic_117x87.equals(clinetGoodsRebateRackOne.getClientGoodsRebateRackImagesList().get(img).getPixel()) ){
							json.put("sourceUrl", FroadAPICommand.makePicURL(clinetGoodsRebateRackOne.getClientGoodsRebateRackImagesList().get(img).getImagesUrl()));
						}
					}
					//json.put("sourceUrl", clinetGoodsRebateRackOne.getGoods().getSourceUrl()==null?"":FroadAPICommand.makePicURL(clinetGoodsRebateRackOne.getGoods().getSourceUrl()));
					json.put("pointRate", clinetGoodsRebateRackOne.getRebateValue()==null?"":clinetGoodsRebateRackOne.getRebateValue());
					arr.add(json);
				}
			}*/
			reno="0000";
			rebody.put("goodsList",arr);
			logger.info("返回的主体："+rebody.toString());
		}catch (Exception e) {
			reno="9999"; //异常
			logger.error("login Exception",e);
		}
		return SUCCESS;
		
	}
	
	
	
	@JSON(serialize=false)
	public MerchantActionSupport getMerchantActionSupport() {
		return merchantActionSupport;
	}
	public void setMerchantActionSupport(MerchantActionSupport merchantActionSupport) {
		this.merchantActionSupport = merchantActionSupport;
	}
	public String getReno() {
		logger.info("调用结束[UserAction]reno:"+reno+",remsg:"+getRemsg()+",rebody:"+rebody);
		return reno;
	}
	public void setReno(String reno) {
		this.reno = reno;
	}
	public String getRemsg() {
		return FroadAPICommand.getValidateMsg(reno);
	}
	public void setRemsg(String remsg) {
		this.remsg = remsg;
	}
	public Map<String, Object> getRebody() {
		return rebody;
	}
	public void setRebody(Map<String, Object> rebody) {
		this.rebody = rebody;
	}
	public String getRecount() {
		return count;
	}
	public void setRecount(String recount) {
		this.recount = recount;
	}
	@JSON(serialize=false)
	public ClientGoodsRebateRackActionSupport getClientGoodsRebateRackActionSupport() {
		return clientGoodsRebateRackActionSupport;
	}

	public void setClientGoodsRebateRackActionSupport(
			ClientGoodsRebateRackActionSupport clientGoodsRebateRackActionSupport) {
		this.clientGoodsRebateRackActionSupport = clientGoodsRebateRackActionSupport;
	}
	public String getReturnTime() {
		
		return sdf.format(new Date());
	}
	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}
 
	@JSON(serialize=false)
	public SellersActionSupport getSellersActionSupport() {
		return sellersActionSupport;
	} 

	public void setSellersActionSupport(SellersActionSupport sellersActionSupport) {
		this.sellersActionSupport = sellersActionSupport;
	}
	
}
