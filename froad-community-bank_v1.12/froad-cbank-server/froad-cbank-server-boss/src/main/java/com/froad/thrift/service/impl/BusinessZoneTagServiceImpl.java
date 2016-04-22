/**
 * Project Name:froad-cbank-server-boss
 * File Name:BusinessZoneTagServiceImpl.java
 * Package Name:com.froad.thrift.service.impl
 * Date:2015年10月23日下午1:53:07
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.thrift.service.impl;

import org.apache.thrift.TException;

import com.froad.logic.BusinessZoneTagLogic;
import com.froad.logic.impl.BusinessZoneTagLogicImpl;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.BusinessZoneTagService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagAddReq;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagAddRes;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagDeleteReq;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagDeleteRes;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagDetailReq;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagDetailRes;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagExportOutletReq;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagExportOutletRes;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagListReq;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagListRes;
import com.froad.thrift.vo.businesszonetag.GenerateActivityNoReq;
import com.froad.thrift.vo.businesszonetag.GenerateActivityNoRes;
import com.froad.util.PropertiesUtil;

/**
 * ClassName:BusinessZoneTagServiceImpl
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月23日 下午1:53:07
 * @author   kevin
 * @version  
 * @see 	 
 */
public class BusinessZoneTagServiceImpl extends BizMonitorBaseService implements BusinessZoneTagService.Iface {

	private BusinessZoneTagLogic logic = new BusinessZoneTagLogicImpl();
	
	public BusinessZoneTagServiceImpl(){
		
	};
	
	public BusinessZoneTagServiceImpl(String name, String version) {
		super(name, version);
	}
	
	public static void main(String args[]) {
		PropertiesUtil.load();
		try {
			BusinessZoneTagServiceImpl b = new BusinessZoneTagServiceImpl();
			//查询test
			BusinessZoneTagListReq req = new BusinessZoneTagListReq();
			//req.setClientId("anhui");
			//req.setTagName("商圈");
			//req.setStatus("1");
			PageVo pageVo = new PageVo();
			pageVo.setTotalCount(0);
			pageVo.setPageSize(10);
			pageVo.setPageCount(1);
			pageVo.setPageNumber(1);
			req.setPageVo(pageVo);
			
			BusinessZoneTagListRes res =	b.getBusinessZoneTagList(req);
		//	res.getBusinessZoneTagList();
			
			//新增test
			BusinessZoneTagAddReq addReq = new BusinessZoneTagAddReq();
			addReq.setId("10");
			addReq.setClientId("chongqing");
			addReq.setDesc("描述修改");
			addReq.setFareadId("125");
			addReq.setSareadId("135");
			addReq.setTareadId("1110");
			addReq.setSortValue("10");
			addReq.setStatus("1");
			addReq.setFlag("2");
			addReq.setTagName("修改重庆商圈2_1");
			//BusinessZoneTagAddRes addRes = b.addBusinessZoneTag(addReq);
			//addRes.getResultVo();
			BusinessZoneTagDetailReq reqDetail = new BusinessZoneTagDetailReq();
			reqDetail.setId("10");
			b.getBusinessZoneTagDetail(reqDetail);
			
			BusinessZoneTagDeleteReq deleteReq = new BusinessZoneTagDeleteReq();
			deleteReq.setId("10");
			//b.deleteBusinessZoneTag(deleteReq);
			
			BusinessZoneTagExportOutletReq exportReq = new BusinessZoneTagExportOutletReq();
			exportReq.setId("10");
			//b.exportBusinessZoneTagOutlet(exportReq);
			
			GenerateActivityNoReq generateReq = new GenerateActivityNoReq();
			generateReq.setActivityType("2");
			generateReq.setClientId("anhui");
			GenerateActivityNoRes generateRes = b.generateActivityNo(generateReq);
			System.out.println(generateRes.getActiviyNo());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public BusinessZoneTagListRes getBusinessZoneTagList(BusinessZoneTagListReq req) throws TException {
		//校验入参
		
		return logic.getBusinessZoneTagList(req);
	}

	@Override
	public BusinessZoneTagDetailRes getBusinessZoneTagDetail(BusinessZoneTagDetailReq req) throws TException {
		// TODO Auto-generated method stub
		return logic.getBusinessZoneTag(req);
	}

	@Override
	public BusinessZoneTagAddRes addBusinessZoneTag(BusinessZoneTagAddReq req) throws TException {
		//入参校验
		if("1".equals(req.getFlag())) {
			return logic.addBusinessZoneTag(req);
		} else {
			return logic.editBusinessZoneTag(req);
		}
	}

	@Override
	public BusinessZoneTagDeleteRes deleteBusinessZoneTag(BusinessZoneTagDeleteReq req) throws TException {
		return logic.deleteBusinessZoneTag(req);
	}

	@Override
	public BusinessZoneTagExportOutletRes exportBusinessZoneTagOutlet(BusinessZoneTagExportOutletReq req)
			throws TException {
		return logic.exportBusinessZoneTag(req);
	}

	@Override
	public GenerateActivityNoRes generateActivityNo(GenerateActivityNoReq req) throws TException {
		// TODO Auto-generated method stub
		return logic.generateActivityNo(req);
	}
	 	
}
