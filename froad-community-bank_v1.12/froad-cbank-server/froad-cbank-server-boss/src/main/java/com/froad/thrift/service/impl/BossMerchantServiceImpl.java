package com.froad.thrift.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.froad.logback.LogCvt;
import com.froad.logic.BossMerchantLogic;
import com.froad.logic.impl.BossMerchantLogicImpl;
import com.froad.po.Outlet;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.BossMerchantService.Iface;
import com.froad.thrift.vo.OutletVo;
import com.froad.util.BeanUtil;

public class BossMerchantServiceImpl extends  BizMonitorBaseService implements Iface  {

	private BossMerchantLogic bossMerchantLogic = new BossMerchantLogicImpl();
	
	
	public BossMerchantServiceImpl() {}
	public BossMerchantServiceImpl(String name, String version) {
		super(name, version);
	}
	
	@Override
	public List<OutletVo> getSubBankOutletToList(String client_id,
			String org_code) throws TException {
		
		LogCvt.info("查询提货网点(预售用到) Outlet");
		List<OutletVo> outletVoList = new ArrayList<OutletVo>();
		
		List<Outlet> outletList = bossMerchantLogic.findSubBankOutlet(client_id, org_code);		
		outletVoList = (List<OutletVo>)BeanUtil.copyProperties(OutletVo.class, outletList);
		return outletVoList;
	}

}
