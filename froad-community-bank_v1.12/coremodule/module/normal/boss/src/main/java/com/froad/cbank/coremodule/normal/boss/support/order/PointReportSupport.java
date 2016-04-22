package com.froad.cbank.coremodule.normal.boss.support.order;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.common.ClientRes;
import com.froad.cbank.coremodule.normal.boss.pojo.order.PointReportListRes;
import com.froad.cbank.coremodule.normal.boss.pojo.order.PointReportMerchantListRes;
import com.froad.cbank.coremodule.normal.boss.pojo.order.PointReportReq;
import com.froad.cbank.coremodule.normal.boss.support.common.ClientSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.PramasUtil;
import com.froad.thrift.service.PointSettlementService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.pointsettlement.PointSettlementDetailResVo;
import com.froad.thrift.vo.pointsettlement.PointSettlementMerchantDetailResVo;
import com.froad.thrift.vo.pointsettlement.PointSettlementMerchantResVo;
import com.froad.thrift.vo.pointsettlement.PointSettlementReqVo;
import com.froad.thrift.vo.pointsettlement.PointSettlementResVo;
/**
 * 积分报表相关support
 * @author liaopeixin
 *	@date 2016年1月21日 下午6:17:25
 */
@Service
public class PointReportSupport {

	@Resource
	private PointSettlementService.Iface pointSettlementService;
	@Resource
	private ClientSupport clientSupport;
	/**
	 * 总 汇总信息
	 * @param req
	 * @return
	 * @author liaopeixin
	 * @throws TException 
	 * @throws BossException 
	 * @throws ParseException 
	 *	@date 2016年1月22日 上午9:59:25
	 */
	public Map<String,Object> pointCount(PointReportReq voreq) throws TException, BossException, ParseException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		PointSettlementReqVo req=new PointSettlementReqVo();
		//将值拷贝
		BeanUtils.copyProperties(req, voreq);
		
		//时间转换
		long beginTime=PramasUtil.DateFormat(voreq.getStartTime());
		long endTime=PramasUtil.DateFormatEnd(voreq.getEndTime());
		req.setStartTime(beginTime);
		req.setEndTime(endTime);
		
		PointSettlementResVo res=pointSettlementService.queryPointCount(req);
		ResultVo resultVo=res.getResultVo();	
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			map.put("froadPointCount", res.getFroadPointCount());
			map.put("bankPointCount", res.getBankPointCount());
		}else{
			throw new BossException(resultVo.getResultCode(),resultVo.getResultDesc());
		}		
		return map;
	}

	/**
	 * 购物订单/面对面惠付订单 汇总与明细信息
	 * @param req
	 * @return
	 * @author liaopeixin
	 * @throws TException 
	 * @throws BossException 
	 * @throws ParseException 
	 *	@date 2016年1月22日 上午9:59:13
	 */
	public Map<String,Object> pointOrder(PointReportReq voreq) throws TException, BossException, ParseException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<PointReportListRes> list=new ArrayList<PointReportListRes>();
		PointReportListRes pr=null;
		List<ClientRes> clientList=clientSupport.getClient();
		String clientName="";
		for (ClientRes c : clientList) {
			if( voreq.getClientId().equals(c.getClientId())){
				clientName=c.getClientName();
			}
		}
		//给前端的分页对象
		Page page = new Page();
		//给后台的分页对象
		PageVo pageVo = new PageVo();
		// 分页对象转化
		pageVo.setPageNumber(voreq.getPageNumber());
		pageVo.setPageSize(voreq.getPageSize());
		pageVo.setFirstRecordTime(voreq.getFirstRecordTime());
		pageVo.setLastPageNumber(voreq.getLastPageNumber());
		pageVo.setLastRecordTime(voreq.getLastRecordTime());
		
		PointSettlementReqVo req=new PointSettlementReqVo();
		//将值拷贝
		BeanUtils.copyProperties(req, voreq);
		

		//时间转换
		long beginTime=PramasUtil.DateFormat(voreq.getStartTime());
		long endTime=PramasUtil.DateFormatEnd(voreq.getEndTime());
		req.setStartTime(beginTime);
		req.setEndTime(endTime);
		
		req.setPage(pageVo);
		PointSettlementResVo res=pointSettlementService.queryPointDetail(req);
		ResultVo resultVo=res.getResultVo();
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			map.put("froadPointCount", res.getFroadPointCount());
			map.put("bankPointCount", res.getBankPointCount());
			if(res.getDetailResVoListSize()>0){
				for (PointSettlementDetailResVo vo : res.getDetailResVoList()) {
					pr=new PointReportListRes();
					BeanUtils.copyProperties(pr,vo);
					pr.setClientName(clientName);
					if(StringUtils.isBlank(pr.getBankPointRate())){
						pr.setBankPointRate("-");
					}
					list.add(pr);
				}
				BeanUtils.copyProperties(page, res.getPage());
			}
		}else{
			throw new BossException(resultVo.getResultCode(),resultVo.getResultDesc());
		}						
		map.put("page", page);
		map.put("list", list);
		return map;
	}

	/**
	 * 商户汇总
	 * @param req
	 * @return
	 * @author liaopeixin
	 * @throws TException 
	 * @throws BossException 
	 * @throws ParseException 
	 *	@date 2016年1月22日 上午9:58:58
	 */
	public Map<String,Object> pointMerchantCount(PointReportReq voreq) throws TException, BossException, ParseException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<PointReportMerchantListRes> list=new ArrayList<PointReportMerchantListRes>();
		PointReportMerchantListRes pm=null;
		List<ClientRes> clientList=clientSupport.getClient();
		String clientName="";
		for (ClientRes c : clientList) {
			if( voreq.getClientId().equals(c.getClientId())){
				clientName=c.getClientName();
			}
		}
		//给前端的分页对象
		Page page = new Page();
		//给后台的分页对象
		PageVo pageVo = new PageVo();
		// 分页对象转化
		pageVo.setPageNumber(voreq.getPageNumber());
		pageVo.setPageSize(voreq.getPageSize());
		pageVo.setFirstRecordTime(voreq.getFirstRecordTime());
		pageVo.setLastPageNumber(voreq.getLastPageNumber());
		pageVo.setLastRecordTime(voreq.getLastRecordTime());
		
		PointSettlementReqVo req=new PointSettlementReqVo();
		
		//将值拷贝
		BeanUtils.copyProperties(req, voreq);

		//时间转换
		long beginTime=PramasUtil.DateFormat(voreq.getStartTime());
		long endTime=PramasUtil.DateFormatEnd(voreq.getEndTime());
		req.setStartTime(beginTime);
		req.setEndTime(endTime);
		
		req.setPage(pageVo);
		PointSettlementMerchantResVo res=pointSettlementService.queryMerchantPointCount(req);
		ResultVo resultVo=res.getResultVo();
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			if(res.getDetailResVoListSize()>0){
				for (PointSettlementMerchantDetailResVo vo : res.getDetailResVoList()) {
					pm=new PointReportMerchantListRes();
					BeanUtils.copyProperties(pm,vo);
					pm.setClientName(clientName);
					if(StringUtils.isBlank(pm.getBankPointRate())){
						pm.setBankPointRate("-");
					}
					list.add(pm);
				}
				BeanUtils.copyProperties(page, res.getPage());
			}
		}else{
			throw new BossException(resultVo.getResultCode(),resultVo.getResultDesc());
		}						
	
		map.put("clientName",clientName);
		map.put("page", page);
		map.put("list", list);
		return map;
	}
	/**
	 * 积分报表导出
	 * @param req
	 * @return
	 * @author liaopeixin
	 * @throws BossException 
	 * @throws TException 
	 * @throws ParseException 
	 *	@date 2016年1月22日 上午9:58:45
	 */
	public Map<String,Object> export(PointReportReq voreq) throws BossException, TException, ParseException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		PointSettlementReqVo req=new PointSettlementReqVo();
		BeanUtils.copyProperties(req, voreq);
		//时间转换
		long beginTime=PramasUtil.DateFormat(voreq.getStartTime());
		long endTime=PramasUtil.DateFormatEnd(voreq.getEndTime());
		req.setStartTime(beginTime);
		req.setEndTime(endTime);
		
		ExportResultRes res=pointSettlementService.exportPointSettlement(req);
		ResultVo resultVo=res.getResultVo();
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			map.put("code", resultVo.getResultCode());
			map.put("message",resultVo.getResultDesc());
			map.put("url", res.getUrl());
		}else{
			throw new BossException(resultVo.getResultCode(),resultVo.getResultDesc());
		}					
		return map;
	}

}
