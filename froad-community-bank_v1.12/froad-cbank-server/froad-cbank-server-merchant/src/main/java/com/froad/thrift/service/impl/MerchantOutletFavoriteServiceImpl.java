package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;

import com.alibaba.druid.util.StringUtils;
import com.froad.common.beans.ResultBean;
import com.froad.db.mysql.bean.Page;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.MerchantOutletFavoriteLogic;
import com.froad.logic.impl.MerchantOutletFavoriteLogicImpl;
import com.froad.po.mongo.DeliverInfo;
import com.froad.po.mongo.RecvInfo;
import com.froad.po.mongo.StoreOutletInfo;
import com.froad.support.Support;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.MerchantOutletFavoriteService;
import com.froad.thrift.vo.AreaVo;
import com.froad.thrift.vo.DeliverInfoVo;
import com.froad.thrift.vo.MerchantOutletFavoriteVORes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.RecvInfoVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.StoreOutletInfoPageVoRes;
import com.froad.thrift.vo.StoreOutletInfoVo;
import com.froad.util.BeanUtil;

public class MerchantOutletFavoriteServiceImpl extends BizMonitorBaseService implements MerchantOutletFavoriteService.Iface {
	
	
	private MerchantOutletFavoriteLogic merchantOutletFavoriteLogic = new MerchantOutletFavoriteLogicImpl();
	
	private Support support=new Support();
	
	public MerchantOutletFavoriteServiceImpl() {}
	public MerchantOutletFavoriteServiceImpl(String name, String version) {
		super(name, version);
	}
	
	
	
	/** 是否收藏门店
	* @Title: isExitsStoreOutletInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param outletId
	* @param @return
	* @param @throws TException
	* @return int
	* @throws 
	*/
	@Override
	public boolean isExitsStoreOutletInfo(String clientId, long memberCode,String outletId) 
			throws TException {
		// TODO Auto-generated method stub
		boolean result = false;
		LogCvt.info("查询StoreOutletInfoVo");
		int rint = merchantOutletFavoriteLogic.isExitsStoreOutletInfo(clientId, memberCode, outletId);
		result = rint > 0 ? true : false;
		return result;
	}
	

	  /**
     * 增加商户收藏 StoreOutletInfoVo
     * @param storeOutletInfoVo
     * @return long    主键ID
     * @param clientId
     * @param memberCode
     * @param storeOutletInfoVo
     */
	@Override
	public ResultVo addStoreOutletInfoVo(String clientId, long memberCode,
			StoreOutletInfoVo storeOutletInfoVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("添加StoreOutletInfoVo");
		ResultVo resultVo=new ResultVo();
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("添加门店收藏成功");
		StoreOutletInfo storeOutletInfo =null;
		storeOutletInfo = (StoreOutletInfo)BeanUtil.copyProperties(StoreOutletInfo.class, storeOutletInfoVo);
		ResultBean result = merchantOutletFavoriteLogic.addStoreOutletInfo(clientId, memberCode, storeOutletInfo);
		
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getCode())){
			LogCvt.error(result.getMsg());
			resultVo.setResultCode(result.getCode());
			resultVo.setResultDesc(result.getMsg());
		}
		return resultVo;
	}

    /**
     * 取消商户收藏 StoreOutletInfoVo
     * @param storeOutletInfoVo
     * @return boolean
     * @param clientId
     * @param memberCode
     * @param merchantId
     */
	@Override
	public boolean deleteStoreOutletInfoVo(String clientId, long memberCode,
			String outletId) throws TException {
		// TODO Auto-generated method stub
		boolean result = false;
		LogCvt.info("移除StoreOutletInfoVo");
		int rint = merchantOutletFavoriteLogic.removeStoreOutletInfo(clientId, memberCode, outletId);
		result = rint > -1 ? true : false;
		return result;
	}


	 /**
     * 商户收藏查询接口 StoreOutletInfoVo
     * @param storeOutletInfoVo
     * @return List<StoreOutletInfoVo>
     * @param clientId
     * @param memberCode
     */
	@Override
	public List<StoreOutletInfoVo> getStoreOutletInfoVo(String clientId,
			long memberCode) throws TException {
		// TODO Auto-generated method stub
		List<StoreOutletInfoVo> storeOutletInfoVoList = new ArrayList<StoreOutletInfoVo>();
		List<StoreOutletInfo> storeOutletInfoList = new ArrayList<StoreOutletInfo>();
		storeOutletInfoList = merchantOutletFavoriteLogic.queryStoreOutletInfo(clientId, memberCode);
		if( storeOutletInfoList !=null && storeOutletInfoList.size()>0 ){
			for(StoreOutletInfo po : storeOutletInfoList){
				StoreOutletInfoVo vo = (StoreOutletInfoVo)BeanUtil.copyProperties(StoreOutletInfo.class, po);
				storeOutletInfoVoList.add(vo);
			}
		}
		return storeOutletInfoVoList;
	}
	
	
	
	  /**
     * 分页查询 StoreOutletInfo
     * @param page
     * @param storeOutletInfo
     * @return Page<StoreOutletInfo>    结果集合 
     */
	public  StoreOutletInfoPageVoRes getStoreOutletInfoByPage(PageVo pageVo, String clientId, long memberCode)throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("分页查询StoreOutletInfo");
		StoreOutletInfoPageVoRes storeOutletInfoPageVoRes = new StoreOutletInfoPageVoRes();
		Page<StoreOutletInfo>  page = new Page<StoreOutletInfo>();
		page = (Page<StoreOutletInfo>)BeanUtil.copyProperties(Page.class, pageVo);
		List<StoreOutletInfoVo> storeOutletInfoVoList = new ArrayList<StoreOutletInfoVo>();
		page = merchantOutletFavoriteLogic.findStoreOutletInfoByPage(page,clientId, memberCode);
		if(page.getResultsContent() !=null){
			for(StoreOutletInfo po : page.getResultsContent()){
				StoreOutletInfoVo vo = (StoreOutletInfoVo)BeanUtil.copyProperties(StoreOutletInfoVo.class, po);
				storeOutletInfoVoList.add(vo);
			}
			pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
		}
		if(pageVo.getPageCount()>pageVo.getPageNumber()){
            pageVo.setHasNext(true);
        } else {
            pageVo.setHasNext(false);
        }
		storeOutletInfoPageVoRes.setPage(pageVo);
		storeOutletInfoPageVoRes.setStoreOutletInfoVoList(storeOutletInfoVoList);
		return storeOutletInfoPageVoRes;
	}
	
	
	
	
	
	
	/** 统计商品、门店
	* @Title: countProductStoreOutletInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param type
	* @param @return
	* @param @throws TException
	* @return Map<String,Integer>
	* @throws 
	*/
	@Override
	public Map<String,Integer>  countProductStoreOutletInfo(String clientId, long memberCode)
			throws TException {
		Map<String,Integer> map = new HashMap<String, Integer>();
		map = merchantOutletFavoriteLogic.countProductStoreOutletInfo(clientId, memberCode);
		return map;
	} 


	 /**
     * 收货信息增加 RecvInfoVo
     * @param recvInfoVo
     * @return long    主键ID
     * @param clientId
     * @param memberCode
     * @param recvInfoVo
     */
	@Override
	public ResultVo addRecvInfoVo(String clientId, long memberCode,RecvInfoVo recvInfoVo) throws TException {
    	RecvInfo recvInfo = null;
    	ResultVo resultVo=new ResultVo();
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("添加收货信息成功");
		recvInfo = (RecvInfo)BeanUtil.copyProperties(RecvInfo.class, recvInfoVo);
		ResultBean result = merchantOutletFavoriteLogic.addRecvInfo(clientId, memberCode, recvInfo);
		
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getCode())){
			LogCvt.error(result.getMsg());
			resultVo.setResultCode(result.getCode());
			resultVo.setResultDesc(result.getMsg());
		}
		return resultVo;
		
	}


    /**
     * 查询收货信息 RecvInfoVo
     * @param recvInfoVo
     * @return List<RecvInfoVo>
     * @param clientId
     * @param memberCode
     */
	@Override
	public List<RecvInfoVo> getRecvInfoVo(String clientId, long memberCode, String isDefault,String type)
			throws TException {
		// TODO Auto-generated method stub
		List<RecvInfoVo> recvInfoVoList =  new ArrayList<RecvInfoVo>();
		List<RecvInfo> recvInfoList = new ArrayList<RecvInfo>();
		try {
			recvInfoList = merchantOutletFavoriteLogic.queryRecvInfo(clientId, memberCode,isDefault,type);
			if( recvInfoList !=null && recvInfoList.size()>0 ){
				AreaVo areaVo=null;
				for(RecvInfo po : recvInfoList){
					RecvInfoVo vo = (RecvInfoVo)BeanUtil.copyProperties(RecvInfoVo.class, po);
					if( po.getAreaId()!=null){
						areaVo=support.getAreaById(po.getAreaId());
						if(areaVo !=null){
							vo.setTreePath(areaVo.getTreePath());
							vo.setTreePathName(areaVo.getTreePathName());
						}
					}
					recvInfoVoList.add(vo);
				}
			}
		} catch (Exception e) {
			LogCvt.error("查询收货信息失败, 原因:" + e.getMessage(), e);
		}
		return recvInfoVoList;
	}

	/**
     * 查询收货信息 RecvInfoVo 按主键
     * @param recvId
     * @return RecvInfoVo
     */
	@Override
	public RecvInfoVo getRecvInfoVoById(String clientId, long memberCode,
			String recvId) throws TException {
		// TODO Auto-generated method stub
		RecvInfoVo result = new RecvInfoVo();
		try{
			RecvInfo recv = merchantOutletFavoriteLogic.queryRecvInfoById(clientId, memberCode, recvId);
			if( recv != null ){
				result = (RecvInfoVo)BeanUtil.copyProperties(RecvInfoVo.class, recv);
				if( recv.getAreaId() != null ){
					AreaVo areaVo = support.getAreaById(recv.getAreaId());
					if(areaVo !=null){
						result.setTreePath(areaVo.getTreePath());
						result.setTreePathName(areaVo.getTreePathName());
					}
				}
			}
		} catch (Exception e) {
			LogCvt.error("查询收货信息失败, 原因:" + e.getMessage(), e);
			result = new RecvInfoVo();
		}
		return result;
	}
	   
	/**
     * 删除收货地址信息 RecvInfoVo
     * @param recvInfoVo
     * @return boolean
     * @param clientId
     * @param memberCode
     * @param recvId
     */
	@Override
	public boolean deleteRecvInfoVo(String clientId, long memberCode, String recvId)
			throws TException {
		boolean result = false;
		LogCvt.info("移除RecvInfoVo");
		int rint = merchantOutletFavoriteLogic.removeRecvInfo(clientId, memberCode, recvId);
		result = rint > -1 ? true : false;
		return result;
	}
    /**
     * 修改收货地址信息 RecvInfoVo
     * @param recvInfoVo
     * @return boolean
     * @param clientId
     * @param memberCode
     * @param recvInfoVo
     */
	@Override
	public MerchantOutletFavoriteVORes updateRecvInfoVo(String clientId, long memberCode,
			RecvInfoVo recvInfoVo) throws TException {
		MerchantOutletFavoriteVORes vORes=new MerchantOutletFavoriteVORes();
		ResultVo resultVo=new ResultVo();
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("修改收货地址信息成功");
		LogCvt.info("修改RecvInfoVo");
		RecvInfo recvInfo = null;
		recvInfo = (RecvInfo)BeanUtil.copyProperties(RecvInfo.class, recvInfoVo);
		ResultBean result = merchantOutletFavoriteLogic.updateRecvInfo(clientId, memberCode, recvInfo);
		
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getCode())){
			LogCvt.error(result.getMsg());
			resultVo.setResultCode(result.getCode());
			resultVo.setResultDesc(result.getMsg());
			vORes.setResult(resultVo);
			
		}else{
			vORes.setResult(resultVo);
			vORes.setId(result.getData().toString());
		}
		return vORes;
		
	}

	 /**
     * 增加提货地址信息 DeliverInfoVo
     * @param deliverInfoVo
     * @return long    主键ID
     * @param clientId
     * @param memberCode
     * @param deliverInfoVo
     */
	@Override
	public ResultVo addDeliverInfoVo(String clientId, long memberCode,
			DeliverInfoVo deliverInfoVo) throws TException {
		// TODO Auto-generated method stub
		DeliverInfo deliverInfo = null;
    	ResultVo resultVo=new ResultVo();
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("添加提货信息成功");
		deliverInfo = (DeliverInfo)BeanUtil.copyProperties(DeliverInfo.class, deliverInfoVo);
		ResultBean result = merchantOutletFavoriteLogic.addDeliverInfo(clientId, memberCode, deliverInfo);
		
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getCode())){
			LogCvt.error(result.getMsg());
			resultVo.setResultCode(result.getCode());
			resultVo.setResultDesc(result.getMsg());
		}
		return resultVo;
		
	}
	
	
	/** 查询提货信息
	* @Title: getDeliverInfoVo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @return
	* @param @throws TException
	* @return List<DeliverInfoVo>
	* @throws 
	*/
	@Override
	public List<DeliverInfoVo> getDeliverInfoVo(String clientId, long memberCode,String isDefault)
			throws TException {
		// TODO Auto-generated method stub
		List<DeliverInfoVo> deliverInfoVoList = new ArrayList<DeliverInfoVo> ();
		List<DeliverInfo> deliverInfoList = new ArrayList<DeliverInfo>();
		try {
			deliverInfoList = merchantOutletFavoriteLogic.queryDeliverInfo(clientId, memberCode,isDefault);
			if( deliverInfoList !=null && deliverInfoList.size()>0 ){
				AreaVo areaVo=null;
				List<AreaVo> arealist =null;
				for(DeliverInfo po : deliverInfoList){
					DeliverInfoVo vo = (DeliverInfoVo)BeanUtil.copyProperties(DeliverInfoVo.class, po);
					if(po.getAreaId()!=null && po.getAreaId()!=0){
						areaVo=support.getAreaById(po.getAreaId());
						if(areaVo !=null){
							vo.setTreePath(areaVo.getTreePath());
							vo.setTreePathName(areaVo.getTreePathName());
						}
					}
					deliverInfoVoList.add(vo);
				}
				
			}
		} catch (Exception e) {
			LogCvt.error("查询提货信息失败, 原因:" + e.getMessage(), e);
		}
		return deliverInfoVoList;
	}

	
	/** 删除提货信息
	* @Title: deleteDeliverInfoVo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param deliveryId
	* @param @return
	* @param @throws TException
	* @return int
	* @throws 
	*/
	@Override
	public boolean deleteDeliverInfoVo(String clientId, long memberCode, String deliveryId)
			throws TException {
		LogCvt.info("移除DeliverInfoVo");
		boolean result = false;
		int rint = merchantOutletFavoriteLogic.removeDeliverInfo(clientId, memberCode, deliveryId);
		result = rint > -1 ? true : false;
		return result;
	}
	
	
	/** 更新提货信息
	* @Title: updateDeliverInfoVo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param deliverInfoVo
	* @param @return
	* @param @throws TException
	* @return int
	* @throws 
	*/
	@Override
	public ResultVo updateDeliverInfoVo(String clientId, long memberCode,
			DeliverInfoVo deliverInfoVo) throws TException {
		// TODO Auto-generated method stub
		DeliverInfo deliverInfo = null;
		ResultVo resultVo=new ResultVo();
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("修改提货信息成功");
		deliverInfo = (DeliverInfo)BeanUtil.copyProperties(DeliverInfo.class, deliverInfoVo);
		ResultBean result = merchantOutletFavoriteLogic.updateDeliverInfo(clientId, memberCode, deliverInfo);
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getCode())){
			LogCvt.error(result.getMsg());
			resultVo.setResultCode(result.getCode());
			resultVo.setResultDesc(result.getMsg());
		}
		return resultVo;
	}
	
	
	
	
}
