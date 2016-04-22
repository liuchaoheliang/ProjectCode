package com.froad.handler;

import java.util.List;
import java.util.Set;

import com.froad.db.mongo.page.MongoPage;
import com.froad.po.ActiveResultInfo;
import com.froad.po.AreaInfo;
import com.froad.po.MinatoSingleParamInfo;
import com.froad.po.MinatoSingleProductResultInfo;
import com.froad.po.OutletDetail;
import com.froad.po.mongo.Location;
import com.froad.po.mongo.MerchantDetail;

 /**
  * @ClassName: MinatoSingleProductHandler
  * @Description: 商品凑单数据逻辑处理接口.
  * @author froad-shenshaocheng 2015年11月9日
  * @modify froad-shenshaocheng 2015年11月9日
 */
public interface MinatoSingleProductHandler{
	
	 /**
	  * @Title: queryVipPresellProducts
	  * @Description: 获取凑单商品分页信息
	  * @author: shenshaocheng 2015年11月10日
	  * @modify: shenshaocheng 2015年11月10日
	  * @param mongoPage
	  * @param activeInfo
	  * @return
	 */	
	public MongoPage queryMinatoSingleProduct(
			MongoPage mongoPage, ActiveResultInfo activeInfo);
	
	 /**
	  * @Title: findareaList
	  * @Description: 查询区域信息
	  * @author: shenshaocheng 2015年11月12日
	  * @modify: shenshaocheng 2015年11月12日
	  * @param clientId
	  * @param areaId
	  * @return 返回区域信息
	 */
	public List<AreaInfo> findareaList (String clientId, String areaId);
	
	 /**
	  * @Title: findAreaLocation
	  * @Description: 查找区域坐标信息
	  * @author: shenshaocheng 2015年11月17日
	  * @modify: shenshaocheng 2015年11月17日
	  * @param areadId
	  * @return 返回区域坐标信息
	 */	
	public Location findAreaLocation(String areadId);
	
	 /**
	  * @Title: queryGroupProductsByPage
	  * @Description: 查找商品详情
	  * @author: shenshaocheng 2015年11月17日
	  * @modify: shenshaocheng 2015年11月17日
	  * @param minatoSingleParamInfo 前端条件
	  * @param productIdSet 商品ID集合
	  * @return 返回商品详情
	 */	
	public List<MinatoSingleProductResultInfo> queryGroupProductsByPage(
			MinatoSingleParamInfo minatoSingleParamInfo,
			Set<String> productIdSet);
	
	 /**
	  * @Title: getOutletDetail
	  * @Description: 查询门店详情
	  * @author: shenshaocheng 2015年11月17日
	  * @modify: shenshaocheng 2015年11月17日
	  * @param minatoSingleParamInfo 前端条件
	  * @return 返回门店详情
	 */	
	public  List<OutletDetail> getOutletDetail(MinatoSingleParamInfo minatoSingleParamInfo);
	
	 /**
	  * @Title: getMerchantDetail
	  * @Description: 查询商户详情
	  * @author: shenshaocheng 2015年11月23日
	  * @modify: shenshaocheng 2015年11月23日
	  * @param minatoSingleParamInfo
	  * @return
	 */	
	public  List<MerchantDetail> getMerchantDetail(MinatoSingleParamInfo minatoSingleParamInfo);
	
	 /**
	  * @Title: findFirstProduct
	  * @Description: 查找第一个商品
	  * @author: shenshaocheng 2015年11月18日
	  * @modify: shenshaocheng 2015年11月18日
	  * @param clientId 客户端ID
	  * @param productId 商品ID
	  * @return 返回商品信息
	 */	
	public MinatoSingleProductResultInfo findFirstProduct(String clientId, String productId);
}
