package com.froad.logic.impl;

import com.froad.enums.ResultCode;
import com.froad.handler.ActiveOrderDetailHandler;
import com.froad.handler.impl.ActiveOrderDetailHandlerImpl;
import com.froad.logic.ActiveSettlementMarkOrderLogic;
import com.froad.po.ActiveOrderDetail;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.active.SettlementMarkOrderReq;
import com.froad.thrift.vo.active.SettlementMarkOrderRes;
import com.froad.util.ActiveUtils;


/**
  * @ClassName: ActiveSettlementMarkOrderLogicImpl
  * @Description: 清算程序
  * @author froad-zengfanting 2015年11月13日
  * @modify froad-zengfanting 2015年11月13日
 */
public class ActiveSettlementMarkOrderLogicImpl implements ActiveSettlementMarkOrderLogic {
	ActiveOrderDetailHandler activeOrderDetailHandler=new ActiveOrderDetailHandlerImpl();
	@Override
	public SettlementMarkOrderRes settlementMarkOrder(SettlementMarkOrderReq settlementMarkOrderReq) {
		SettlementMarkOrderRes res=new SettlementMarkOrderRes();
		ResultVo vo=new ResultVo();
		try{
			//String reqId= settlementMarkOrderReq.getReqId();
			String productId=settlementMarkOrderReq.getProductId();
			boolean isLast=settlementMarkOrderReq.isIsLast();
			String orderId=settlementMarkOrderReq.getOrderId();
			String subOrderId=settlementMarkOrderReq.getSubOrderId();
			int count=settlementMarkOrderReq.getCount();
			int vipCount=settlementMarkOrderReq.getVipCount();
			
			ActiveOrderDetail activeOrderDetail =new ActiveOrderDetail();
			activeOrderDetail.setOrderId(orderId);
			activeOrderDetail.setSubOrderId(subOrderId);
			activeOrderDetail.setProductId(productId);
			
			activeOrderDetail=activeOrderDetailHandler.findOneActiveOrderDetail(activeOrderDetail);
			
			int totalCount=activeOrderDetail.getOrgNormalPriceCount();
			double totalPrice=activeOrderDetail.getOrgNormalPriceMarket();
			
			int vipTotalCount=activeOrderDetail.getOrgVipPriceCount();
			double vipTotalPrice=activeOrderDetail.getOrgVipPriceMarket();
			
			double setMoney=ActiveUtils.settlement(isLast, totalPrice/1000, totalCount, count);
			
			double setVipMoney =ActiveUtils.settlement(isLast,vipTotalPrice/1000,vipTotalCount,vipCount);
			
			res.setMoney(setMoney);
			res.setVipMoney(setVipMoney);
			vo.setResultCode(ResultCode.success.getCode());
			vo.setResultDesc("清算成功");
			res.setResultVo(vo);
			
		}catch(Exception ex){
			vo.setResultCode(ResultCode.failed.getCode());
			vo.setResultDesc("系统异常，清算失败");
			res.setResultVo(vo);
		}
		return res;
	}

}
