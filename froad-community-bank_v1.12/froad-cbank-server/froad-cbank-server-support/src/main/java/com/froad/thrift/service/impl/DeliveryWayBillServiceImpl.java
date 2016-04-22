package com.froad.thrift.service.impl;

import org.apache.thrift.TException;

import com.froad.logback.LogCvt;
import com.froad.logic.DeliveryWayBillLogic;
import com.froad.logic.impl.DeliveryWayBillLogicImpl;
import com.froad.po.Waybill;
import com.froad.po.Result;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.DeliveryWayBillService.Iface;
import com.froad.thrift.vo.DeliveryWayBillVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;

public class DeliveryWayBillServiceImpl extends BizMonitorBaseService implements Iface {
     private DeliveryWayBillLogic deliveryWayBillLogic=new DeliveryWayBillLogicImpl();
     
     public DeliveryWayBillServiceImpl(String name, String version) {
 		super(name, version);
 	}
	@Override
	public DeliveryWayBillVo getDeliveryWayBillById(DeliveryWayBillVo billVo)
			throws TException {
		    LogCvt.info("根据运单参数查询,sub_order:"+billVo.getSubOrderId()+"shppingId:"+billVo.getShippingId());
		    Waybill	billpo=(Waybill)BeanUtil.copyProperties(Waybill.class, billVo);
			Waybill	bill=deliveryWayBillLogic.getDeliveryWayBill(billpo);
			if(bill!=null){
				billVo=(DeliveryWayBillVo) BeanUtil.copyProperties(DeliveryWayBillVo.class, bill);	
			}else{
				billVo=new DeliveryWayBillVo();
			}
			
		return billVo;
	}

	@Override
	public ResultVo updateDeliveryWayBill(DeliveryWayBillVo deliveryWayBillVo)
			throws TException {
		    LogCvt.info("更新物流表开始,Shippingid="+deliveryWayBillVo.getShippingId());
		    ResultVo resultVo=new ResultVo();
			Waybill	bill=(Waybill)BeanUtil.copyProperties(Waybill.class, deliveryWayBillVo);
		    Result result=deliveryWayBillLogic.updateDeliveryWayBill(bill);
		    resultVo.setResultCode(result.getResultCode());
		    resultVo.setResultDesc(result.getResultDesc());
		    return resultVo;
	}
	@Override
	public ResultVo updateDeliveryWayBillByCondition(DeliveryWayBillVo deliveryWayBillVo)
			throws TException {
	         	LogCvt.info("确认收货更新物流表开始,subOrderid="+deliveryWayBillVo.getSubOrderId());
		        ResultVo resultVo=new ResultVo();
				Waybill	bill=(Waybill)BeanUtil.copyProperties(Waybill.class, deliveryWayBillVo);
			    Result result=deliveryWayBillLogic.updateDeliveryWayBillByCondition(bill);
			    resultVo.setResultCode(result.getResultCode());
			    resultVo.setResultDesc(result.getResultDesc());
			    return resultVo;
	}
	


}
