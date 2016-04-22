package com.froad.logic;

import java.util.List;
import java.util.Map;

import com.froad.common.beans.ResultBean;
import com.froad.db.mysql.bean.Page;
import com.froad.po.mongo.DeliverInfo;
import com.froad.po.mongo.RecvInfo;
import com.froad.po.mongo.StoreOutletInfo;
public interface MerchantOutletFavoriteLogic {
	

	
	  /**
     * 分页查询 StoreOutletInfo
     * @param page
     * @param storeOutletInfo
     * @return Page<StoreOutletInfo>    结果集合 
     */
	public  Page<StoreOutletInfo>  findStoreOutletInfoByPage(Page<StoreOutletInfo> page, String clientId, long memberCode);
	
	/** 获取客户商品收藏数
	* @Title: countStoreProductInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @return
	* @return int
	* @throws 
	*/
//	public int countStoreProductInfo(String clientId, long memberCode);
	
	
	
	
	/** 是否收藏门店
	* @Title: countStoreOutletInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param merchantId
	* @param @return
	* @return int
	* @throws 
	*/
	public int isExitsStoreOutletInfo(String clientId, long memberCode,String outletId);
	
	/** 增加门店收藏
	* @Title: addStoreOutletInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param merchantId
	* @param @param storeOutletInfo
	* @param @return
	* @return int
	* @throws 
	*/
	public ResultBean addStoreOutletInfo(String clientId, long memberCode,StoreOutletInfo storeOutletInfo);
	
	/** 取消门店收藏
	* @Title: removeStoreOutletInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param merchantId
	* @param @return
	* @return int
	* @throws 
	*/
	public int removeStoreOutletInfo(String clientId, long memberCode, String outletId);
	
	/** 查询全部门店
	* @Title: queryStoreOutletInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @return
	* @return List<StoreOutletInfo>
	* @throws 
	*/
	public List<StoreOutletInfo> queryStoreOutletInfo(String clientId, long memberCode);
	
	/** 获取用户门店收藏数
	* @Title: countStoreOutletInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @return
	* @return int
	* @throws 
	*/
//	public int countStoreOutletInfo(String clientId, long memberCode);
	
	
	/** 
	* @Title: countProductStoreOutletInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param type
	* @param @return
	* @return Map<String,Integer>
	* @throws 
	*/
	public Map<String,Integer> countProductStoreOutletInfo(String clientId, long memberCode);
	
	/** 收货信息是否存在
	* @Title: isExitsRecvInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param recvId
	* @param @return
	* @return int
	* @throws 
	*/
	public int isExitsRecvInfo(String clientId, long memberCode,String recvId);
	
	/** 添加收货信息
	* @Title: addRecvInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param recvInfo
	* @param @return
	* @return int
	* @throws 
	*/
	public ResultBean addRecvInfo(String clientId, long memberCode,RecvInfo recvInfo);
	
	/** 删除收货信息
	* @Title: removeRecvInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param recvId
	* @param @return
	* @return int
	* @throws 
	*/
	public int removeRecvInfo(String clientId, long memberCode, String recvId);
	
	/** 更新收货信息
	* @Title: updateRecvInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param recvInfo
	* @param @return
	* @return int
	* @throws 
	*/
	public ResultBean updateRecvInfo(String clientId, long memberCode,RecvInfo recvInfo);
	
	/** 查询收货信息
	* @Title: queryRecvInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @return
	* @return List<RecvInfo>
	* @throws 
	*/
	public List<RecvInfo> queryRecvInfo(String clientId, long memberCode,String isDefault,String type);
	
	/** 查询收货信息
	* @Title: queryRecvInfoById 
	* @Description: 
	* @author lf
	* @param recvId
	* @return RecvInfo
	* @throws 
	*/
	public RecvInfo queryRecvInfoById(String clientId, long memberCode,String recvId);
	
	public int isExitsDeliverInfo(String clientId, long memberCode,String deliveryId);
	
	/** 增加提货信息
	* @Title: addDeliverInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param deliveryId
	* @param @param deliverInfo
	* @param @return
	* @return int
	* @throws 
	*/
	public ResultBean addDeliverInfo(String clientId, long memberCode,DeliverInfo deliverInfo) ;
	
	/** 删除提货信息
	* @Title: removeDeliverInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param deliveryId
	* @param @return
	* @return int
	* @throws 
	*/
	public int removeDeliverInfo(String clientId, long memberCode, String deliveryId);
	
	/** 修改提货信息
	* @Title: updateDeliverInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param deliverInfo
	* @param @return
	* @return int
	* @throws 
	*/
	public ResultBean updateDeliverInfo(String clientId, long memberCode,DeliverInfo deliverInfo);
	
	/** 查询提货信息
	* @Title: queryDeliverInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @return
	* @return List<DeliverInfo>
	* @throws 
	*/
	public List<DeliverInfo> queryDeliverInfo(String clientId, long memberCode,String isDefault);
	
	
	
	
	}
