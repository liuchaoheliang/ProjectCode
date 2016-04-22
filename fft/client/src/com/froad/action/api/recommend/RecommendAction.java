package com.froad.action.api.recommend;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;

import com.froad.action.api.BaseApiAction;
import com.froad.action.api.command.FroadAPICommand;
import com.froad.action.support.MerchantActionSupport;
import com.froad.client.merchant.Merchant;
import com.froad.client.merchant.OrderType;
import com.froad.util.command.Command;
 

public class RecommendAction  extends BaseApiAction {
	private static Logger logger = Logger.getLogger(RecommendAction.class);
	private MerchantActionSupport merchantActionSupport;
	
	

	private String reno;//操作结果码
	private String remsg;//响应信息
	private Map<String,Object> rebody=new HashMap<String,Object>();//主体
	private String recount;//返回请求标识号（原样返回）
	private String returnTime;//返回时间
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss"); 
	/**
	 * 
	  * 方法描述：查询最新推荐
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-2-27 下午02:51:29
	 */
	public String queryTypeRecommend(){
		logger.info("[RecommendAction- queryTypeRecommends]body:"+body);
		try {
			JSONArray array = new JSONArray();
			Merchant merchant = new Merchant();
			merchant.setPageSize(100);
		//	merchant.setIsHot("1");  //是否推荐
			merchant.setState(Command.STATE_START);
		//	merchant.setOrderBy("id");
		//	merchant.setOrderType(OrderType.DESC);
			merchant =merchantActionSupport.getMerchantsPreferentialType(merchant);
			if(merchant!=null&&merchant.getList()!=null){
				for(int i=0;i<merchant.getList().size()&&i<10;i++){
					JSONObject merchantjson= new JSONObject();
					Merchant merchant2 = (Merchant)merchant.getList().get(i);
					merchantjson.put("name",merchant2.getMstoreFullName());
					merchantjson.put("content",merchant2.getCompanyFullName());
					merchantjson.put("picUrl",FroadAPICommand.makePicURL(merchant2.getMstoreLogoUrl(),FroadAPICommand.PIC_THUMBNAIL ));
					merchantjson.put("hotLevel", merchant.getHotLevel());
					merchantjson.put("merchantId",merchant2.getId());
					merchantjson.put("remark",merchant2.getRemark() == null ? "":merchant2.getRemark());
					merchantjson.put("type","merchant");
					array.add(merchantjson);
				} 
			}
			String serverURL= getRequest().getScheme()+"://"+getRequest().getServerName()+":"+getRequest().getServerPort()+ getRequest().getContextPath();
			//logger.info("===========serverURL hfcz:"+serverURL+"/upload/clientPic/hfcz.jpg"); 
			JSONObject hfczjson= new JSONObject();
			hfczjson.put("name","话费");
			hfczjson.put("content", "话费充值");
			hfczjson.put("picUrl",serverURL+"/upload/clientPic/hfcz.jpg");
			hfczjson.put("hotLevel","5");
			hfczjson.put("type","hfcz");
			array.add(hfczjson);
			JSONObject  lotteryjson= new JSONObject();
			lotteryjson.put("name","彩票");
			lotteryjson.put("content", "彩票");
			lotteryjson.put("picUrl",serverURL+"/upload/clientPic/lotery2D.jpg");
			lotteryjson.put("hotLevel","5");
			lotteryjson.put("type","lottery");
			array.add(lotteryjson);
			JSONObject  pointsGoodsjson= new JSONObject();
			pointsGoodsjson.put("name","积分商品");
			pointsGoodsjson.put("content", "积分商品");
			pointsGoodsjson.put("picUrl",serverURL+"/upload/clientPic/pointsGoods.jpg");
			pointsGoodsjson.put("hotLevel","5");
			pointsGoodsjson.put("type","pointsGoods");
			array.add(pointsGoodsjson);
			
			rebody.put("list", array);
			reno="0000";
		}catch(Exception e){
			reno="9999";
			logger.error("queryRecommendList异常",e);
		}
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
	public MerchantActionSupport getMerchantActionSupport() {
		return merchantActionSupport;
	} 

	public void setMerchantActionSupport(MerchantActionSupport merchantActionSupport) {
		this.merchantActionSupport = merchantActionSupport;
	}
	public String getReturnTime() {
		
		return sdf.format(new Date());
	}
	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}
}
