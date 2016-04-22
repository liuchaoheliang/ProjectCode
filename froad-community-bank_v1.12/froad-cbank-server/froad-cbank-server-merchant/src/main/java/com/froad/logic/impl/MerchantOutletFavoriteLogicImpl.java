package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.froad.common.beans.ResultBean;
import com.froad.db.mongo.MerchantOutletFavoriteMongo;
import com.froad.db.mongo.OutletDetailMongo;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.OrderBy;
import com.froad.db.mongo.page.Sort;
import com.froad.db.mysql.bean.Page;
import com.froad.enums.ModuleID;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.MerchantOutletFavoriteLogic;
import com.froad.po.mongo.DeliverInfo;
import com.froad.po.mongo.MerchantOutletFavorite;
import com.froad.po.mongo.OutletDetail;
import com.froad.po.mongo.RecvInfo;
import com.froad.po.mongo.StoreOutletInfo;
import com.froad.util.PageModel;
import com.froad.util.SimpleID;

public class MerchantOutletFavoriteLogicImpl implements MerchantOutletFavoriteLogic {
	MerchantOutletFavoriteMongo mongo = new MerchantOutletFavoriteMongo();
	MongoManager manager = new MongoManager();
	private static Sort sort = new Sort("_id", OrderBy.ASC);
	
    private static SimpleID simpleID = new SimpleID(ModuleID.recvInfo);
    private static SimpleID simpledelID = new SimpleID(ModuleID.deliverInfo);
	
	//收藏上限
	private static int FAVORITELIMIT = 100;
	
	//收货地址限
	private static int RECVLIMIT = 10;
	
	//提货信息上限
	private static int DELIVERYLIMIT = 10;
	
	//商品详情mongo操作
	OutletDetailMongo outletDetailMongo = new OutletDetailMongo();
	
	
	   /**
     * 分页查询 StoreOutletInfo
     * @param page
     * @param storeOutletInfo
     * @return Page<StoreOutletInfo>    结果集合 
     */
	public Page<StoreOutletInfo> findStoreOutletInfoByPage(Page<StoreOutletInfo> page, String clientId, long memberCode)
	{
		List<StoreOutletInfo> storeOutletInfoList = new ArrayList<StoreOutletInfo>();
		List<StoreOutletInfo> result = new ArrayList<StoreOutletInfo>();
		try {
			storeOutletInfoList = mongo.queryStoreOutletInfo(clientId, memberCode);
			if(storeOutletInfoList!=null || storeOutletInfoList.size()!=0){
				PageModel pm=new PageModel(storeOutletInfoList,page.getPageSize());
				result=pm.getObjects(page.getPageNumber());
				page.setResultsContent(result);
				page.setTotalCount(storeOutletInfoList.size());
				page.setPageCount(pm.getTotalPages());
			}
		} catch (Exception e) {
			// TODO: handle exception
			LogCvt.error("获取StoreOutletInfo分页失败，原因:" + e.getMessage(), e);
		}
		return page;
	}
		
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
	public int isExitsStoreOutletInfo(String clientId, long memberCode,String outletId){
		int result = 0;
		try {
			result = mongo.isExitsStoreOutletInfo(clientId, memberCode, outletId);
		} catch (Exception e) {
			// TODO: handle exception
			LogCvt.error("查询StoreOutletInfo失败，原因:" + e.getMessage(), e);
		}
		return result;
	}
	
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
	public ResultBean addStoreOutletInfo(String clientId, long memberCode,StoreOutletInfo storeOutletInfo){
		ResultBean result = new ResultBean(ResultCode.success,"门店收藏成功");
		OutletDetail outletDetail = null;
		int store_count=1;
		String id=clientId+"_"+memberCode;
		String outletId = storeOutletInfo.getOutletId();
		try {
			outletDetail = outletDetailMongo.findOutletDetailByoutletId(outletId);
			if(outletDetail == null){
				LogCvt.error("添加失败，门店详情表中不存在该门店");
				return new ResultBean(ResultCode.outlet_detail_no_exits);
			}else{
				Integer storeCount = outletDetail.getStoreCount();
				if(storeCount == null){
					mongo.addStoreOutletStoreCount(outletId);
//					outletDetail = outletDetailMongo.findOutletDetailByoutletId(outletId);
					storeCount =  0;
				}
				store_count =storeCount+store_count;
				MerchantOutletFavorite m= mongo.findMerchantOutletFavoriteById(id);
				
				if(m != null){
					int storlutletSize = 0;
					List<StoreOutletInfo> stoList = mongo.queryStoreOutletInfo(clientId, memberCode);
					if(stoList == null){
						storlutletSize = 0;
					}else{
						storlutletSize = stoList.size();
					}
					if(storlutletSize >= FAVORITELIMIT)
					{
						LogCvt.error("添加失败，添加门店收藏数已达上限"+FAVORITELIMIT);
						return new ResultBean(ResultCode.outlet_count_limit);
					}
					int count = isExitsStoreOutletInfo(clientId,memberCode,storeOutletInfo.getOutletId());
					if(count == 0){
						mongo.updateStoreOutletInfo(clientId, memberCode, storeOutletInfo);
						mongo.updateoutletDetail(clientId, memberCode, outletId, store_count);
					}else{
						LogCvt.error("门店已收藏，不能再次收藏");
						return new ResultBean(ResultCode.outlet_exits);
					}
				}else{
					mongo.addStoreOutletInfos(clientId, memberCode, storeOutletInfo);
					mongo.updateoutletDetail(clientId, memberCode, outletId, store_count);
				}
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			result = new ResultBean(ResultCode.failed,"Mongo门店收藏失败");
			LogCvt.error("插入StoreOutletInfo失败，原因:" + e.getMessage(), e);
		}
		return result;
	}
	
	/** 取消门店收藏
	* @Title: removeStoreOutletInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCodeu
	* @param @param merchantId
	* @param @return
	* @return int
	* @throws 
	*/
	public int removeStoreOutletInfo(String clientId, long memberCode, String outletId){
		int result = -1;
		OutletDetail outletDetail = null;
		int store_count=-1;
//		String id=clientId+"_"+memberCode;
		try {
			outletDetail = outletDetailMongo.findOutletDetailByoutletId(outletId);
			Integer storeCount = outletDetail.getStoreCount();
			store_count =storeCount+store_count;
			result = mongo.removeStoreOutletInfo(clientId, memberCode, outletId);
			mongo.updateoutletDetail(clientId, memberCode, outletId, store_count);
		} catch (Exception e) {
			// TODO: handle exception
			LogCvt.error("移除StoreOutletInfo失败，原因:" + e.getMessage(), e);
		}
		return result;
	}
	
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
	public List<StoreOutletInfo> queryStoreOutletInfo(String clientId, long memberCode){
		List<StoreOutletInfo> storeOutletInfo = null;
		try {
			storeOutletInfo = mongo.queryStoreOutletInfo(clientId, memberCode);
		} catch (Exception e) {
			// TODO: handle exception
			LogCvt.error("获取StoreProductInfo失败，原因:" + e.getMessage(), e);
		}
		return storeOutletInfo;
	}
	
	/** 获取用户商品、门店收藏数
	* @Title: countStoreOutletInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @return
	* @return int
	* @throws 
	*/
	public  Map<String,Integer> countProductStoreOutletInfo(String clientId, long memberCode){
		Map<String,Integer> result = new HashMap<String, Integer>();
		try {
			result = mongo.countProductStoreOutletInfo(clientId, memberCode);
		} catch (Exception e) {
			// TODO: handle exception
			LogCvt.error("统计ProductStoreOutletInfo失败，原因:" + e.getMessage(), e);
		}
		return result;
	}
	
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
	public int isExitsRecvInfo(String clientId, long memberCode,String recvId){
		int result = 0;
		try {
			result = mongo.isExitsRecvInfo(clientId, memberCode, recvId);
		} catch (Exception e) {
			// TODO: handle exception
			LogCvt.error("查询RecvInfo失败，原因:" + e.getMessage(), e);
		}
		return result;
	}
	
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
	public ResultBean addRecvInfo(String clientId, long memberCode,RecvInfo recvInfo){
		recvInfo.setCreatTime(new Date().getTime());
		int result =0;
		recvInfo.setRecvId(simpleID.nextId());
		ResultBean resultBean = new ResultBean(ResultCode.success,"添加收货信息成功",recvInfo.getRecvId());
		try {
			
			recvInfo.setIsEnable("1");
			String id = clientId+"_"+memberCode;
			MerchantOutletFavorite m= mongo.findMerchantOutletFavoriteById(id);
			if(m!= null){
				int limitresult=RecvisLimit(m,recvInfo);
				if(limitresult==0) 
				{
					LogCvt.error("添加失败，添加收货信息数已达上限"+RECVLIMIT);
					resultBean = new ResultBean(ResultCode.recvInfo_limit,"添加失败，添加收货信息数已达上限"+RECVLIMIT);
					return resultBean;
				}else if(limitresult==1){
					LogCvt.error("添加失败，添加提货信息数已达上限"+DELIVERYLIMIT);
					resultBean = new ResultBean(ResultCode.recvInfo_limit,"添加失败，添加提货信息数已达上限"+DELIVERYLIMIT);
					return resultBean;
				}else if(limitresult==2)
				{
					//如果是添加提货人查询提货人是否已经存在，提货人处理
					LogCvt.error("添加失败，提货人已经添加过,name="+recvInfo.getConsignee()+",phone="+recvInfo.getPhone());
					resultBean = new ResultBean(ResultCode.failed,"添加失败，提货人已经添加过,name="+recvInfo.getConsignee()+",phone="+recvInfo.getPhone());
					return resultBean;
				}
				//常用地址处理,如果是默认地址则先将原来的默认地址修改 1代表常用 2代表提货地址
					if((recvInfo.getIsDefault()!=null&&"1".equals(recvInfo.getIsDefault()))||(recvInfo.getIsDeliverDefault()!=null&&recvInfo.getIsDeliverDefault().equals("1"))){
						updateDefaultRecvInfo(clientId,memberCode,recvInfo,m);
					}
					else{//不为默认地址则正常加入
						result =mongo.appedRecvInfo(clientId, memberCode,  recvInfo);
					}
				
			}else{
				List<RecvInfo> stoList = new ArrayList<RecvInfo>();
				stoList.add(recvInfo);
				result = mongo.addRecvInfo2(clientId, memberCode, stoList);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			resultBean = new ResultBean(ResultCode.failed,"Mongo收货信息增加失败");
			LogCvt.error("插入RecvInfo失败，原因:" + e.getMessage(), e);
		}
		return resultBean;
	}
	/**
	 * 
	 * @Title: RecvisLimit 
	 * @Description: TODO
	 * @author: Yaolong Liang 2015年9月17日
	 * @modify: Yaolong Liang 2015年9月17日
	 * @param m
	 * @return
	 * @return int
	 * @throws
	 */
	public int RecvisLimit(MerchantOutletFavorite m,RecvInfo recvInfo){
		List<RecvInfo> list=m.getRecvInfo();
		/**
		 * 取出常用地址以及提货地址中的地址部分,判断是否大于限制数量
		 * 返回0为常用地址大于限制数量
		 * 返回1为提货地址大于限制数量
		 * 返回2为此提货地址已经添加
		 * 返回3为正常
		 */
		if(list!=null&&list.size()>0){
			List<RecvInfo> recvinfos=new ArrayList<RecvInfo>();
			List<RecvInfo> deliverinfos=new ArrayList<RecvInfo>();
			for(RecvInfo recv:list){
				if(recv.getType()!=null&&recv.getType().equals("1")&&recv.getIsEnable().equals("1")){
					recvinfos.add(recv);
				}else if(recv.getType()!=null&&recv.getType().equals("2")&&recv.getIsEnable().equals("1")){
					deliverinfos.add(recv);
				}
			}
			if(recvinfos !=null && recvinfos.size() >= RECVLIMIT&&recvInfo.getType().equals("1")) 
			{
				return 0;
			}
			if(deliverinfos !=null && deliverinfos.size() >= DELIVERYLIMIT&&recvInfo.getType().equals("2")){
				return 1;
			}
		}
//		if(recvInfo.getType().equals("2")){
//			for(RecvInfo recv:deliverinfos){
//				if(recv.getIsEnable().equals("1")&&recv.getConsignee().equals(recvInfo.getConsignee())
//						&&recv.getPhone().equals(recvInfo.getPhone())){
//					return 2;
//				}
//			}
//		}
		return 3;
	}
	public void updateDefaultRecvInfo(String clientId,Long memberCode,RecvInfo recvInfo,MerchantOutletFavorite mer){
		   List<RecvInfo> recvInfos=mer.getRecvInfo();
		   List<RecvInfo> newrecvInfos=new ArrayList<RecvInfo>();
		   if(recvInfos!=null&&recvInfos.size()>0){
			   for(RecvInfo recv:recvInfos){
				   //修改常用地址默认地址,同时如果提货
				   if("1".equals(recvInfo.getIsDefault())&&recvInfo.getType().equals("1")){
					   if(recv.getIsDefault()!=null&&recv.getIsDefault().equals("1")&&recv.getIsEnable().equals("1")){
						   recv.setIsDefault("0");
					   }
				   }
				   if("1".equals(recvInfo.getIsDeliverDefault())&&recvInfo.getType().equals("2")){
					   if(recv.getIsDeliverDefault()!=null&&recv.getIsDeliverDefault().equals("1")&&recv.getIsEnable().equals("1")){
						   recv.setIsDeliverDefault("0");
					   }
				   }
				   if("1".equals(recvInfo.getIsDeliverDefault())&&recvInfo.getType().equals("1")){
					   if(recv.getIsDeliverDefault()!=null&&recv.getIsDeliverDefault().equals("1")&&recv.getIsEnable().equals("1")){
						   recv.setIsDeliverDefault("0");
					   }
				   }
				   
				   newrecvInfos.add(recv);
			   }
		   }
		   newrecvInfos.add(recvInfo);
		   mongo.addRecvInfo3(clientId, memberCode, newrecvInfos);
	}
	public boolean isExitsDeliverInfo(String clientId,Long memberCode,RecvInfo recvInfo){
		String id=clientId+"_"+memberCode;
		try{
			int result=mongo.isExitsDeliverInfo(id,recvInfo);
			if(result>0){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			LogCvt.error("查询提货地址是否已经存在失败，原因:" + e.getMessage(), e);
		}
		return false;
	}
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
	public int removeRecvInfo(String clientId, long memberCode, String recvId){
		int result = 0;
		try {
			result = mongo.removeRecvInfo(clientId, memberCode, recvId);
		} catch (Exception e) {
			// TODO: handle exception
			LogCvt.error("移除RecvInfo失败，原因:" + e.getMessage(), e);
		}
		return result;
	}
	
	/** 更新收货信息
	 * 
	 * TODO luofan 20150522 
	 * 现在改为 : 传递进来需要更新的地址变为逻辑删除 再新建一个地址
	 * 二期改为 : 传递进来需要更新的地址如果和订单不相关就更新信息 如果和订单相关就不能更新
	 * 
	 * 
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
	public ResultBean updateRecvInfo(String clientId, long memberCode,RecvInfo recvInfo){
		ResultBean resultBean=new ResultBean(ResultCode.failed);
		try {
//				String id = clientId+"_"+memberCode;
//				MerchantOutletFavorite m= mongo.findMerchantOutletFavoriteById(id);
//				if(m != null){
//					if(mongo.isExitsRecvInfo(clientId, memberCode, recvInfo.getRecvId())>0){
//						if("1".equals(recvInfo.getIsDefault())){
//							mongo.updateDefaultRecvInfo(clientId,memberCode,"0","1");
//						}
//						mongo.updateDefaultRecvInfo(clientId, memberCode, recvInfo);
//						return new ResultBean(ResultCode.success,"更新收货信息成功");
//					}
//				}
			int rr = this.removeRecvInfo(clientId, memberCode, recvInfo.getRecvId());
			if( rr > -1 ){
				ResultBean rb = this.addRecvInfo(clientId, memberCode, recvInfo);
				if( ResultCode.success.getCode().equals(rb.getCode()) ){
					resultBean = new ResultBean(ResultCode.success,"更新收货信息成功",rb.getData());
				}else{
					resultBean = new ResultBean(ResultCode.failed,"更新收货信息不成功");
				}
			}else{
				resultBean = new ResultBean(ResultCode.failed,"更新的收货信息不存在");
			}
		} catch (Exception e) {
			// TODO: handle exception
			// TODO: handle exception
			resultBean = new ResultBean(ResultCode.failed,"Mongo更新收货增加失败");
			LogCvt.error("更新RecvInfo失败，原因:" + e.getMessage(), e);
		}
		return resultBean;
	}
	
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
	public List<RecvInfo> queryRecvInfo(String clientId, long memberCode,String isDefault,String type){
		List<RecvInfo> recvInfo = new ArrayList<RecvInfo>();
		try {
//			if(isDefault!=null && isDefault.equals("1")){
//				recvInfo = mongo.queryDefaultRecvInfo(clientId, memberCode, isDefault);
//			}else{
//				recvInfo = mongo.queryRecvInfo(clientId, memberCode);
//			}
			recvInfo = mongo.queryDefaultRecvInfo(clientId, memberCode, isDefault, type);
		} catch (Exception e) {
			// TODO: handle exception
			LogCvt.error("获取RecvInfo失败，原因:" + e.getMessage(), e);
		}
		return recvInfo;
	}
	
	/** 查询收货信息
	* @Title: queryRecvInfoById 
	* @Description: 
	* @author lf
	* @param recvId
	* @return RecvInfo
	* @throws 
	*/
	@Override
	public RecvInfo queryRecvInfoById(String clientId, long memberCode,
			String recvId) {
		// TODO Auto-generated method stub
		RecvInfo recvInfo = null;
		try{
			recvInfo = mongo.queryRecvInfoById(clientId, memberCode, recvId);
		} catch (Exception e) {
			// TODO: handle exception
			LogCvt.error("获取RecvInfo失败，原因:" + e.getMessage(), e);
		}
		return recvInfo;
	}

	public int isExitsDeliverInfo(String clientId, long memberCode,String deliveryId){
		int result = 0;
		try {
			result = mongo.isExitsDeliverInfo(clientId, memberCode, deliveryId);
		} catch (Exception e) {
			// TODO: handle exception
			LogCvt.error("查询DeliverInfo失败，原因:" + e.getMessage(), e);
		}
		return result;
	}
	
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
	public ResultBean addDeliverInfo(String clientId, long memberCode,DeliverInfo deliverInfo) {
		ResultBean resultBean = new ResultBean(ResultCode.success,"添加提货信息成功");
		int result =0;
		try {
			deliverInfo.setDeliverId(simpledelID.nextId());
			String id = clientId+"_"+memberCode;
			MerchantOutletFavorite m= mongo.findMerchantOutletFavoriteById(id);
			if(m != null){
				
				List<DeliverInfo> list=mongo.queryDeliverInfo(clientId, memberCode);
				
				if(list !=null && list.size() >= DELIVERYLIMIT) 
				{
					LogCvt.error("添加失败，添加提货信息数已达上限"+DELIVERYLIMIT);
					resultBean = new ResultBean(ResultCode.deliveryInfo_limit,"添加失败，添加提货信息数已达上限"+DELIVERYLIMIT);
					return resultBean;
				}
//				//查询是否存在
				int count = isExitsDeliverInfo(clientId,memberCode,deliverInfo.getDeliveryId());
				if(count == 0){  //不存在
					if("1".equals(deliverInfo.getIsDefault())){
						mongo.updateDefaultDeliverInfo(clientId,memberCode,"0","1");
					}
					result =mongo.appedDeliverInfo(clientId, memberCode,  deliverInfo);
				}
			}else{
				List<DeliverInfo> stoList = new ArrayList<DeliverInfo>();
				stoList.add(deliverInfo);
				result = mongo.addDeliverInfo2(clientId, memberCode, stoList);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			resultBean = new ResultBean(ResultCode.failed,"Mongo提货信息增加失败");
			LogCvt.error("插入DeliverInfo失败，原因:" + e.getMessage(), e);
		}
		return resultBean;
	}
	
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
	public int removeDeliverInfo(String clientId, long memberCode, String deliveryId){
		int result = 0;
		try {
			result = mongo.removeDeliverInfo(clientId, memberCode, deliveryId);
		} catch (Exception e) {
			// TODO: handle exception
			LogCvt.error("移除DeliverInfo失败，原因:" + e.getMessage(), e);
		}
		return result;
	}
	
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
	public ResultBean updateDeliverInfo(String clientId, long memberCode,DeliverInfo deliverInfo){
		ResultBean resultBean=new ResultBean(ResultCode.failed);
		try {
				String id = clientId+"_"+memberCode;
				MerchantOutletFavorite m= mongo.findMerchantOutletFavoriteById(id);
				if(m != null){
					if(mongo.isExitsDeliverInfo(clientId, memberCode, deliverInfo.getDeliveryId())>0){
						if("1".equals(deliverInfo.getIsDefault())){
							mongo.updateDefaultDeliverInfo(clientId,memberCode,"0","1");
						}
						mongo.updateDefaultDeliverInfo(clientId, memberCode, deliverInfo);
						return new ResultBean(ResultCode.success,"更新提货信息成功");
					}
				}
				resultBean = new ResultBean(ResultCode.success,"更新提货信息成功");
		} catch (Exception e) {
			// TODO: handle exception
			// TODO: handle exception
			resultBean = new ResultBean(ResultCode.failed,"Mongo更新提货增加失败");
			LogCvt.error("更新DeliverInfo失败，原因:" + e.getMessage(), e);
		}
		return resultBean;
	}
	
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
	public List<DeliverInfo> queryDeliverInfo(String clientId, long memberCode,String isDefault){
		List<DeliverInfo> deliverInfo = new ArrayList<DeliverInfo>();
		try {
			if(isDefault!=null && isDefault.equals("1")){
				deliverInfo = mongo.queryDefaultDeliverInfo(clientId, memberCode, isDefault);
			}else{
				deliverInfo = mongo.queryDeliverInfo(clientId, memberCode);
			}
		} catch (Exception e) {
			// TODO: handle exception
			LogCvt.error("获取DeliverInfo失败，原因:" + e.getMessage(), e);
		}
		return deliverInfo;
	}

	
	
	


	
	
}
