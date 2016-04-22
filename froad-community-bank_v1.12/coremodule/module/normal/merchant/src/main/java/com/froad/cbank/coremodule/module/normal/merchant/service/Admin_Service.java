package com.froad.cbank.coremodule.module.normal.merchant.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.module.normal.merchant.exception.MerchantException;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Admin_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Admin_Res;
import com.froad.cbank.coremodule.module.normal.merchant.utils.EnumTypes;
import com.froad.cbank.coremodule.module.normal.merchant.utils.PramasUtil;
import com.froad.cbank.coremodule.module.normal.merchant.utils.TargetObjectFormat;
import com.froad.thrift.service.MerchantMonthCountService;
import com.froad.thrift.service.MerchantService;
import com.froad.thrift.vo.MerchantMonthCountVo;
import com.froad.thrift.vo.MerchantVo;

/**
 * 管理员
 * @ClassName AdminService
 * @author zxl
 * @date 2015年3月21日 下午2:07:02
 */
@Service
public class Admin_Service {
	@Resource
	MerchantMonthCountService.Iface merchantMonthCountService;
	@Resource
	MerchantService.Iface merchantService;

	/**
	 * 管理员查询营业额
	 * @tilte queryAdminInfo
	 * @author zxl
	 * @date 2015年3月21日 下午2:12:25
	 * @param model
	 * @param map
	 * @throws TException 
	 */
	public HashMap<String,Object> query_admin(Query_Admin_Req req) 
			throws MerchantException, TException{
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		MerchantMonthCountVo resvo=new MerchantMonthCountVo();
        Map<String,String> map=PramasUtil.DateFormatTos(System.currentTimeMillis(),null);
		resvo.setYear(map.get("year"));
		resvo.setMonth(map.get("month"));
		resvo.setMerchantId(req.getMerchantUser().getMerchantId());
		resvo.setClientId(req.getClientId());
		MerchantMonthCountVo resv=merchantMonthCountService.getMerchantMonthCount(resvo);
		if(resv!=null){
			Query_Admin_Res res=new Query_Admin_Res();
			TargetObjectFormat.copyProperties(resv, res);
			//查询商户签约时间
			MerchantVo vo=merchantService.getMerchantByMerchantId(req.getMerchantUser().getMerchantId());
			int time=(int)((vo.getContractEndtime()+Long.valueOf(86400 * 1000)-1l-System.currentTimeMillis())/86400000);
			Integer monthCount=resv.getFaceOrderCount()+resv.getGroupOrderCount()+resv.getMaxCount();
			BigDecimal f = new BigDecimal(String.valueOf(resv.getFaceOrderMoney()));
			BigDecimal g = new BigDecimal(String.valueOf(resv.getGroupOrderMoney()));
			res.setMonthMoney(f.add(g).doubleValue());
			res.setOutTime(time<=0?0:time);
			res.setMonthCount(monthCount);
			res.setContractBegintime(vo.getContractBegintime());
			res.setContractEndtime(vo.getContractEndtime());
            reMap.put("countDetail",res);
		}else{
			throw new MerchantException(EnumTypes.fail.getCode(),EnumTypes.fail.getMsg());
		}
		return reMap;
	}
}
