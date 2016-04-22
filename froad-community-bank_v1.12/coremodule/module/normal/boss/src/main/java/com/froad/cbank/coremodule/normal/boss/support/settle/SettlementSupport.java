package com.froad.cbank.coremodule.normal.boss.support.settle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.ArrayUtil;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.settle.SettleInfoVo;
import com.froad.cbank.coremodule.normal.boss.pojo.settle.SettlementVoReq;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.PramasUtil;
import com.froad.thrift.service.BossSettlementService;
import com.froad.thrift.service.MerchantService;
import com.froad.thrift.service.OutletService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.MerchantPageVoRes;
import com.froad.thrift.vo.MerchantVo;
import com.froad.thrift.vo.OutletPageVoRes;
import com.froad.thrift.vo.OutletVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.settlement.BossSettlementPage;
import com.froad.thrift.vo.settlement.BossSettlementVo;

/**
 * 
 * 类描述:结算support
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author "chenzhangwei"
 * @time 2015年8月5日 下午4:17:37
 * @email "chenzhangwei@f-road.com.cn"
 */
@Service
public class SettlementSupport {
	
	@Resource
	BossSettlementService.Iface bossSettlementService;
	@Resource
	OutletService.Iface outletService;
	@Resource
	private MerchantService.Iface  merchantService;	
	
	/**
	 * 
	 * 方法描述:结算列表查询
	 * @author "chenzhangwei"
	 * @email "chenzhangwei@f-road.com.cn"
	 * @time 2015年8月5日 下午3:10:47
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> querySettlementByPage(SettlementVoReq req) throws Exception{
		PageVo pageVo=new PageVo();	
		BossSettlementPage settlementPage= new BossSettlementPage();
		if(StringUtil.isNotBlank(req.getOrderId())){
			settlementPage.setOrderId(req.getOrderId());
		}
		if(StringUtil.isNotBlank(req.getBeginTime())){
			pageVo.setBegDate(PramasUtil.DateFormat(req.getBeginTime()));
		}
		if(StringUtil.isNotBlank(req.getEndTime())){
			pageVo.setEndDate(PramasUtil.DateFormatEnd(req.getEndTime()));
		}
		if(StringUtil.isNotBlank(req.getMerchantName())){
			settlementPage.setMerchantName(req.getMerchantName());
		}
		if(StringUtil.isNotBlank(req.getOutletName())){
			settlementPage.setOutletName(req.getOutletName());
		}
		//商品名称
		if(StringUtil.isNotBlank(req.getProductName())){
			settlementPage.setProductName(req.getProductName());
		}
		if(StringUtil.isNotBlank(req.getSettleState())){
			settlementPage.setSettleState(req.getSettleState());
		}
		if(StringUtil.isNotBlank(req.getClientId())){
			settlementPage.setClientId(req.getClientId());
		}
		if(StringUtil.isNotBlank(req.getBillNo())){
			settlementPage.setBillNo(req.getBillNo());
		}
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		List<SettleInfoVo> list = null;
		SettleInfoVo res = null;
		//分页对象转换
	
		pageVo.setPageNumber(req.getPageNumber());
		pageVo.setPageSize(req.getPageSize());
		pageVo.setFirstRecordTime(req.getFirstRecordTime());
		pageVo.setLastPageNumber(req.getLastPageNumber());
		pageVo.setLastRecordTime(req.getLastRecordTime());
		Page page=new Page();
		settlementPage.setPage(pageVo);
			
		//所属组织
		List<String> orgs = new ArrayList<String>();
		for(String org:req.getOrgCodes().split(",")){
			orgs.add(org);
		}
		settlementPage.setOrgCodes(orgs);
		
		//查询
		LogCvt.info("settlementPage.......："+JSON.toJSONString(settlementPage));
		BossSettlementPage settlementPageRes= bossSettlementService.queryByPage(settlementPage);	
		//分页实体转换
		BeanUtils.copyProperties(page, settlementPageRes.getPage());
		if( !ArrayUtil.empty(settlementPageRes.getRespList())){
			list=new   ArrayList<SettleInfoVo>();
			for(BossSettlementVo temp:settlementPageRes.getRespList()){
				res=new SettleInfoVo();
				res.setClientId(temp.getClientId());
				res.setCreateTime(temp.getCreateTime());
				res.setId(temp.getId());
				res.setMerchantId(temp.getMerchantId());
				if(StringUtil.isNotBlank(temp.getMerchantId()) && StringUtil.isBlank(temp.getMerchantName())){
					MerchantVo  mm = merchantService.getMerchantByMerchantId(temp.getMerchantId());
					if(mm!=null){
						res.setMerchantName(mm.getMerchantName());
					}
				}else{
					res.setMerchantName(temp.getMerchantName());
				}
				res.setMoney(temp.getMoney());
				res.setOrderId(temp.getOrderId());
				res.setOutletId(temp.getOutletId());
				if(StringUtil.isNotBlank(temp.getOutletId())  && StringUtil.isBlank(temp.getOutletName())){
					OutletVo outVo = outletService.getOutletByOutletId(temp.getOutletId());
					if(outVo != null){
						res.setOutletName(outVo.getOutletName());								
					}
				}else{
					res.setOutletName(temp.getOutletName());
				}
				res.setPaymentId(temp.getPaymentId());
				res.setProductCount(temp.getProductCount());
				res.setProductId(temp.getProductId());
				res.setProductName(temp.getProductName());
				res.setRemark(temp.getRemark());
				res.setSettlementId(temp.getSettlementId());
				res.setSettleState(temp.getSettleState());
				res.setSubOrderId(temp.getSubOrderId());
				res.setTickets(temp.getTickets());
				res.setType(temp.getType());
				//返回账单编号
				res.setBillNo(temp.getBillNo());
				list.add(res);
			}
		}
		resMap.put("pageCount", page.getPageCount());
		resMap.put("totalCountExport", page.getTotalCount());
		resMap.put("pageNumber", page.getPageNumber());
		resMap.put("page", page);
		resMap.put("SettleOrderList", list);
		return  resMap;
	}
	
	/**
	 * 结算订单列表导出
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年9月2日 上午10:21:48
	 * @return map
	 * @throws Exception
	 */
	public HashMap<String, Object> settleListExport(SettlementVoReq req) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//封装请求对象
		PageVo pageVo=new PageVo();
		BossSettlementPage settlementPage= new BossSettlementPage();
		if(StringUtil.isNotBlank(req.getOrderId())){
			settlementPage.setOrderId(req.getOrderId());
		}
		if(StringUtil.isNotBlank(req.getBeginTime())){
			pageVo.setBegDate(PramasUtil.DateFormat(req.getBeginTime()));
		}
		if(StringUtil.isNotBlank(req.getEndTime())){
			pageVo.setEndDate(PramasUtil.DateFormatEnd(req.getEndTime()));
		}
		if(StringUtil.isNotBlank(req.getMerchantName())){
			settlementPage.setMerchantName(req.getMerchantName());
		}
		if(StringUtil.isNotBlank(req.getOutletName())){
			settlementPage.setOutletName(req.getOutletName());
		}
		//商品名称
		if(StringUtil.isNotBlank(req.getProductName())){
			settlementPage.setProductName(req.getProductName());
		}
		if(StringUtil.isNotBlank(req.getSettleState())){
			settlementPage.setSettleState(req.getSettleState());
		}
		if(StringUtil.isNotBlank(req.getClientId())){
			settlementPage.setClientId(req.getClientId());
		}
		if(StringUtil.isNotBlank(req.getBillNo())){
			settlementPage.setBillNo(req.getBillNo());
		}
		settlementPage.setPage(pageVo);
		
		//所属组织
		List<String> orgs = new ArrayList<String>();
		for(String org:req.getOrgCodes().split(",")){
			orgs.add(org);
		}
		settlementPage.setOrgCodes(orgs);
		
		//调用SERVER端接口
		ExportResultRes resp = bossSettlementService.exportSettlementQueryByPage(settlementPage);
		ResultVo result = resp.getResultVo();
		LogCvt.info("结算订单列表导出返回结果：" + JSON.toJSONString(result));
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
			HashMap<String, String> obj = new HashMap<String, String>();
			obj.put("url", resp.getUrl());
			map.put("result", obj);
		} else {
			throw new BossException(result.getResultCode(), result.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 根据门店名查询IDs
	 * @param name
	 * @return
	 * @throws TException
	 */
	public List<String> getOutletIds(String name) throws TException{
		List<String>  ids = new ArrayList<String>();
		
		PageVo pageVo=new PageVo();
		pageVo.setPageNumber(1);
		pageVo.setPageSize(200);
		pageVo.setFirstRecordTime(0);
		pageVo.setLastPageNumber(0);
		pageVo.setLastRecordTime(0);
		
		OutletVo outletVo = new OutletVo();
		outletVo.setOutletName(name);
		OutletPageVoRes res = outletService.getOutletByPage(pageVo, outletVo);
		List<OutletVo> list = null;
		if(res != null && res.getOutletVoList()!= null){
			list = res.getOutletVoList();			
		}
		for(OutletVo vo:list){
			ids.add(String.valueOf(vo.getOutletId()));
		}
		return ids;
	}
	
	/**
	 * 根据门店名查询IDs
	 * @param name
	 * @return
	 * @throws TException
	 */
	public List<String> getMerchantIds(String name) throws TException{
		List<String>  ids = new ArrayList<String>();
		
		PageVo pageVo=new PageVo();
		pageVo.setPageNumber(1);
		pageVo.setPageSize(200);
		pageVo.setFirstRecordTime(0);
		pageVo.setLastPageNumber(0);
		pageVo.setLastRecordTime(0);
		
		MerchantVo mVo = new MerchantVo();
		mVo.setMerchantName(name);
		mVo.setIsEnable(true);
		mVo.setDisableStatus("0");//正常商户
		MerchantPageVoRes  res = merchantService.getMerchantByPage(pageVo, mVo);
		List<MerchantVo> list = null;
		if(res != null && res.getMerchantVoList()!= null){
			list = res.getMerchantVoList();			
		}
		for(MerchantVo vo:list){
			ids.add(String.valueOf(vo.getMerchantId()));
		}
		return ids;
	}
	/**
	  * 方法描述：修改结算状态
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: f-road.com.cn
	 * @throws TException 
	  * @time: 2015年4月27日 下午4:48:56
	  */
	public HashMap<String, Object> modifySettlementState(String id, String type, String remark) throws TException{
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		ResultVo resultVo = new ResultVo();				
		resultVo = bossSettlementService.updateSettleState(id,type,remark);						
		resMap.put("resultVo", resultVo);
		return  resMap;
	}
	
}
