package com.froad.action.api.merchantPreferential;

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
import com.froad.action.support.GoodsRebateRackActionSupport;
import com.froad.action.support.MerchantActionSupport;
import com.froad.action.support.MerchantPreferentialActionSupport;
import com.froad.client.merchant.Merchant;
import com.froad.client.merchant.MerchantPhoto;
import com.froad.client.merchant.MerchantProduct;
import com.froad.client.merchant.TagMAP;
import com.froad.util.command.Command;

public class MerchantPreferentialAction  extends BaseApiAction {
	private static Logger logger = Logger.getLogger(MerchantPreferentialAction.class);
	private MerchantPreferentialActionSupport merchantPreferentialActionSupport;
	private GoodsRebateRackActionSupport goodsRebateRackActionSupport;
	private MerchantActionSupport merchantActionSupport;
	private String reno;//操作结果码
	private String remsg;//响应信息
	private Map<String,Object> rebody=new HashMap<String,Object>();//主体
	private String recount;//返回请求标识号（原样返回）
	private String returnTime;//返回时间
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss"); 
	
	
	/**
	  * 方法描述：直接优惠商户详细信息查询（no：0402）
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 孙庆亚 sunqingya@f-road.com.cn
	  * @time: Feb 27, 2013 11:13:57 AM
	  */
	public String queryMerchantPreferentialInfoByMerchantId(){
		logger.info("[MerchantPreferentialAction- getMerchantPreferentialInfoByMerchantId]body:"+body);
		JSONObject jsonObject = JSONObject.fromObject(body);
		String merchantID = jsonObject.getString("merchantID");
		
		Merchant merchant = merchantActionSupport.getMerchantById(merchantID);
		//商家商品
		List<MerchantProduct> list = merchant.getProductList();
		JSONArray arr = new JSONArray();
		if(list!=null&&list.size()>0){
			String desc="";
			for(int i=0;i<list.size();i++){
				JSONObject one = new JSONObject();
				MerchantProduct metchantProduct =list.get(i);
				one.put("photoUrl",FroadAPICommand.makePicURL(metchantProduct.getPhotoUrl(),FroadAPICommand.PIC_SAMLL) );
				one.put("recommend","1");
				desc=metchantProduct.getTxt2();
				if(desc!=null){
					one.put("desc",desc.replaceAll("<br/>|<br />", "\r\n"));
				}else{
					one.put("desc","");
				}
				arr.add(one);
			}
		}
		
		
		
		//商家相册
		List<MerchantPhoto> list2 = merchant.getPhotoList();
		JSONArray arr2 = new JSONArray();
		if(list2!=null&&list2.size()>0){
			for(int i=0;i<list2.size();i++){
				JSONObject one = new JSONObject();
				MerchantPhoto merchantPhoto =list2.get(i);
				one.put("url",FroadAPICommand.makePicURL(merchantPhoto.getPhotoUrl(),FroadAPICommand.PIC_BIG ));
				arr2.add(one);
			}
		}	
		rebody.put("merchantID",merchant.getId());
		rebody.put("merchantName",merchant.getMstoreFullName());
		rebody.put("merchantIntce",merchant.getMerchantPresent().getTxt());//商家介绍
		rebody.put("merchantAddress",merchant.getMstoreAddress());
		rebody.put("merchantPictures",FroadAPICommand.makePicURL(merchant.getMstoreLogoUrl(),FroadAPICommand.PIC_BIG));
		rebody.put("merchantTel",merchant.getMstoreTel());
		rebody.put("preferentialInfo",merchant.getPreferentialRate());
		String tag = "";
		List<TagMAP> listA = merchant.getTagMapList();
		if(listA.size()>0){
			for(int j=0;j<listA.size();j++){
				tag = tag+"  "+listA.get(j).getTagClassifyA().getTagValue()+"  "+listA.get(j).getTagClassifyB().getTagValue()+"  "+listA.get(j).getTagDistrictA().getTagValue()+"  "+listA.get(j).getTagDistrictB().getTagValue();
			}
		}
		String collectNum="0";
		String clickNum="0";
		com.froad.client.merchant.MerchantTrain merchantTran=merchant.getMerchantTrain() ;
		if(merchantTran!=null){
			 collectNum= merchantTran.getCollectes()!=null?merchantTran.getCollectes():collectNum ;
			 clickNum= merchantTran.getClickes()!=null?merchantTran.getClickes():clickNum;
		}
		rebody.put("collectNum",collectNum);
		rebody.put("visitNum",clickNum);
		rebody.put("tagValue",tag);
		rebody.put("goodsList", arr);
		rebody.put("photos", arr2);
		reno="0000";
		return SUCCESS;
	}
	
	
	/**
	  * 方法描述：直接优惠商户列表查询（no：0401）
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 孙庆亚 sunqingya@f-road.com.cn
	  * @time: Feb 27, 2013 11:11:25 AM
	  */
	public String queryMerchantByType(){
		logger.info("[MerchantPreferentialAction- getMerchantByType]body:"+body);
		JSONArray arr = new JSONArray();
		try{
			Merchant merchant = new Merchant();
			merchant.setPreferentialType("1");
			merchant.setState(Command.STATE_START);
			merchant.setPageSize(500);
			merchant = merchantActionSupport.getMerchantByPager(merchant);
			//logger.info("size():"+merchant.getList().size());
			if(merchant.getList()!=null&&merchant.getList().size()>0){
				for(int i=0;i<merchant.getList().size();i++){
					JSONObject one = new JSONObject();
					Merchant mer = (Merchant) merchant.getList().get(i);
					one.put("merchantID",mer.getId());
					one.put("merchantName",mer.getMstoreFullName());
					one.put("merchantIntce",mer.getMerchantPresent().getTxt());//商家介绍
					one.put("merchantAddress",mer.getMstoreAddress());
					one.put("merchantPictures",FroadAPICommand.makePicURL(mer.getMstoreLogoUrl(),FroadAPICommand.PIC_THUMBNAIL));
					one.put("merchantTel",mer.getMstoreTel());
					one.put("preferentialInfo",mer.getPreferentialRate());
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
					one.put("hotLevel",mer.getHotLevel());
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
	public MerchantPreferentialActionSupport getMerchantPreferentialActionSupport() {
		return merchantPreferentialActionSupport;
	}

	public void setMerchantPreferentialActionSupport(
			MerchantPreferentialActionSupport merchantPreferentialActionSupport) {
		this.merchantPreferentialActionSupport = merchantPreferentialActionSupport;
	}
	@JSON(serialize=false)
	public MerchantActionSupport getMerchantActionSupport() {
		return merchantActionSupport;
	}

	public void setMerchantActionSupport(MerchantActionSupport merchantActionSupport) {
		this.merchantActionSupport = merchantActionSupport;
	}

	@JSON(serialize=false)
	public GoodsRebateRackActionSupport getGoodsRebateRackActionSupport() {
		return goodsRebateRackActionSupport;
	}


	public void setGoodsRebateRackActionSupport(
			GoodsRebateRackActionSupport goodsRebateRackActionSupport) {
		this.goodsRebateRackActionSupport = goodsRebateRackActionSupport;
	}
	public String getReturnTime() {
		
		return sdf.format(new Date());
	}
	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}
}
