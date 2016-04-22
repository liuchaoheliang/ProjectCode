package com.froad.thrift.service.impl;

import org.apache.thrift.TException;

import com.alibaba.fastjson.JSON;
import com.froad.logback.LogCvt;
import com.froad.logic.ActiveCheckOrder;
import com.froad.logic.ActiveCloseOrderLogic;
import com.froad.logic.ActiveCreateMarketOrder;
import com.froad.logic.ActiveCreateOrderGoBackLogic;
import com.froad.logic.ActiveFindMarketOrderLogic;
import com.froad.logic.ActiveOrderStatusLogic;
import com.froad.logic.ActiveReturnGoodsLogic;
import com.froad.logic.ActiveRunLogic;
import com.froad.logic.ActiveSettlementMarkOrderLogic;
import com.froad.logic.ActiveShoppingCart;
import com.froad.logic.FullGiveCheckLogic;
import com.froad.logic.impl.AcitveOrderStatusLogicImpl;
import com.froad.logic.impl.ActiveCheckOrderImpl;
import com.froad.logic.impl.ActiveCloseOrderLogicImpl;
import com.froad.logic.impl.ActiveCreateMarketOrderImpl;
import com.froad.logic.impl.ActiveCreateOrderGoBackLogicImpl;
import com.froad.logic.impl.ActiveFindMarketOrderLogicImpl;
import com.froad.logic.impl.ActiveReturnGoodsLogicImpl;
import com.froad.logic.impl.ActiveRunLogicImpl;
import com.froad.logic.impl.ActiveSettlementMarkOrderLogicImpl;
import com.froad.logic.impl.ActiveShoppingCartImpl;
import com.froad.logic.impl.FullGiveCheckLogicImpl;
import com.froad.po.CheckOrderReq;
import com.froad.po.CheckOrderRes;
import com.froad.po.CloseMarketOrderReq;
import com.froad.po.CreateMarketOrderReq;
import com.froad.po.CreateResult;
import com.froad.po.FailureGoBackReq;
import com.froad.po.FindMarketOrderReq;
import com.froad.po.FindMarketOrderRes;
import com.froad.po.FullGiveCheckReq;
import com.froad.po.PutFullCutReq;
import com.froad.po.Result;
import com.froad.po.ReturnMarketOrderBackReq;
import com.froad.po.ReturnMarketOrderReq;
import com.froad.po.ReturnMarketOrderRes;
import com.froad.po.ShoppingCartReq;
import com.froad.po.ShoppingCartRes;
import com.froad.po.UpdateMarketOrderReq;
import com.froad.po.UpdateMarketOrderRes;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.ActiveRunService;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.active.CheckOrderReqVo;
import com.froad.thrift.vo.active.CheckOrderResVo;
import com.froad.thrift.vo.active.CloseMarketOrderReqVo;
import com.froad.thrift.vo.active.CreateMarketOrderReqVo;
import com.froad.thrift.vo.active.CreateResultVo;
import com.froad.thrift.vo.active.FailureGoBackReqVo;
import com.froad.thrift.vo.active.FindMarketOrderReqVo;
import com.froad.thrift.vo.active.FindMarketOrderResVo;
import com.froad.thrift.vo.active.FullGiveCheckReqVo;
import com.froad.thrift.vo.active.PutFullCutReqVo;
import com.froad.thrift.vo.active.ReturnMarketOrderBackReqVo;
import com.froad.thrift.vo.active.ReturnMarketOrderReqVo;
import com.froad.thrift.vo.active.ReturnMarketOrderResVo;
import com.froad.thrift.vo.active.SettlementMarkOrderReq;
import com.froad.thrift.vo.active.SettlementMarkOrderRes;
import com.froad.thrift.vo.active.ShoppingCartReqVo;
import com.froad.thrift.vo.active.ShoppingCartResVo;
import com.froad.thrift.vo.active.UpdateMarketOrderReqVo;
import com.froad.thrift.vo.active.UpdateMarketOrderResVo;
import com.froad.util.BeanUtil;

public class ActiveRunServiceImpl extends BizMonitorBaseService implements ActiveRunService.Iface {

	private ActiveRunLogic activeRunLogic = new ActiveRunLogicImpl();
	private ActiveOrderStatusLogic activeOrderStatusLogic =new AcitveOrderStatusLogicImpl();
	private ActiveCloseOrderLogic activeCloseOrderLogic =new ActiveCloseOrderLogicImpl();
	private ActiveReturnGoodsLogic activeReturnGoodsLogic=new ActiveReturnGoodsLogicImpl();
	private ActiveCreateOrderGoBackLogic activeCreateOrderGoBackLogic=new ActiveCreateOrderGoBackLogicImpl();
	private ActiveFindMarketOrderLogic  activeFindMarketOrderLogic=new  ActiveFindMarketOrderLogicImpl();
	private ActiveSettlementMarkOrderLogic activeSettlementMarkOrderLogic= new ActiveSettlementMarkOrderLogicImpl();
	private FullGiveCheckLogic fullGiveCheckLogic = new FullGiveCheckLogicImpl();
	private ActiveShoppingCart activeShoppingCart = new ActiveShoppingCartImpl();
	private ActiveCheckOrder activeCheckOrder = new ActiveCheckOrderImpl();
	private ActiveCreateMarketOrder activeCreateMarketOrder = new ActiveCreateMarketOrderImpl();
	
	public ActiveRunServiceImpl(){}
	public ActiveRunServiceImpl(String n, String v){
		super(n, v);
	}
	
	/**
     * 订单校验
     * <br>
     * CheckOrderRes.Result.resultCode = 0000 成功
     * CheckOrderRes.orderResList[n] 订单的各种满减金额
     * <br>
     * CheckOrderRes.Result.resultCode = 6666 没有传递活动
     * CheckOrderRes.orderResList = null
     * <br>
     * CheckOrderRes.Result.resultCode = 9999 校验失败
     * CheckOrderRes.Result.resultDesc 失败信息
     * <br>
     */
	@Override
	public CheckOrderResVo checkOrder(CheckOrderReqVo checkOrderReqVo) throws TException {
		LogCvt.info("检查订单 checkOrder 参数:");
		LogCvt.info(JSON.toJSONString(checkOrderReqVo));
		
		// 入参转换
		CheckOrderReq checkOrderReq = (CheckOrderReq)BeanUtil.copyProperties(CheckOrderReq.class, checkOrderReqVo);
		// 逻辑层方法调用
		CheckOrderRes checkOrderRes = activeCheckOrder.checkOrder(checkOrderReq);
		
		// 出参转换
		CheckOrderResVo CheckOrderResVo = (CheckOrderResVo)BeanUtil.copyProperties(CheckOrderResVo.class, checkOrderRes);
		return CheckOrderResVo;
	}

	/**
	  * @Title: closeMarketOrder
	  * @Description: 关闭订单（0-系统关单/取消订单 1-全部退货完毕）
	  * @author: zengfanting 2015年11月9日
	  * @modify: zengfanting 2015年11月9日
	  * @param arg0
	  * @return
	  * @throws TException
	  * @see com.froad.thrift.service.ActiveRunService.Iface#closeMarketOrder(com.froad.thrift.vo.active.CloseMarketOrderReqVo)
	 */
	@Override
	public ResultVo closeMarketOrder(CloseMarketOrderReqVo closeMarketOrderReqVo)throws TException {
		Result result = null;
		LogCvt.info("-----关闭订单交易---start--");
		LogCvt.info("---in---"+JSON.toJSONString(closeMarketOrderReqVo));
		
		CloseMarketOrderReq closeMarketOrderReq = (CloseMarketOrderReq)BeanUtil.copyProperties(CloseMarketOrderReq.class, closeMarketOrderReqVo);
		
		if(closeMarketOrderReqVo.getReason()==0){
			LogCvt.info("-----关闭订单交易---系统关单/取消订单--");
			result= activeCloseOrderLogic.closeOrderBySystem(closeMarketOrderReq);
		}else{
			LogCvt.info("-----关闭订单交易---全部退货--");
			result= activeCloseOrderLogic.closeOrderByReturn(closeMarketOrderReq);
		}
		
		ResultVo resultVo = (ResultVo)BeanUtil.copyProperties(ResultVo.class, result);
		
		return resultVo;
	}
	
	/**
     * 创建营销订单
     * <br>
     * CreateResult.Result.resultCode = 0000 成功
     * CreateResult.id = 营销订单编号
     * <br>
     * CreateResult.Result.resultCode != 不成功
     */
	@Override
	public CreateResultVo createMarketOrder(CreateMarketOrderReqVo createMarketOrderReqVo) throws TException {
		LogCvt.info("创建营销订单 createMarketOrder 参数:");
		LogCvt.info(JSON.toJSONString(createMarketOrderReqVo));
		
		// 入参转换
		CreateMarketOrderReq createMarketOrderReq = (CreateMarketOrderReq)BeanUtil.copyProperties(CreateMarketOrderReq.class, createMarketOrderReqVo);
		
		// 逻辑层方法调用
		CreateResult CreateResult = activeCreateMarketOrder.createMarketOrder(createMarketOrderReq);
		
		// 出参转换
		CreateResultVo createResultVo = (CreateResultVo)BeanUtil.copyProperties(CreateResultVo.class, CreateResult);
		
		return createResultVo;
	}

	/**
	  * @Title: findMarketOrder
	  * @Description: 根据订单/子订单/商品编号查询
	  * @author: zengfanting 2015年11月11日
	  * @modify: lf 2015年12月06日
	  * @param arg0
	  * @return
	  * @throws TException
	  * @see com.froad.thrift.service.ActiveRunService.Iface#findMarketOrder(com.froad.thrift.vo.active.FindMarketOrderReqVo)
	 */
	@Override
	public FindMarketOrderResVo findMarketOrder(FindMarketOrderReqVo arg0)throws TException {
		LogCvt.info("-----订单查询交易---start--");
		LogCvt.info("---in---"+JSON.toJSONString(arg0));
		
		FindMarketOrderReq findMarketOrderReq = (FindMarketOrderReq)BeanUtil.copyProperties(FindMarketOrderReq.class, arg0);
		
		FindMarketOrderRes findMarketOrderRes = activeFindMarketOrderLogic.findMarketOrder(findMarketOrderReq);
		
		FindMarketOrderResVo findMarketOrderResVo = (FindMarketOrderResVo)BeanUtil.copyProperties(FindMarketOrderResVo.class, findMarketOrderRes);
		return findMarketOrderResVo;
		
	}

	/**
     * 进入购物车
     */
	@Override
	public ShoppingCartResVo goShoppingCart(ShoppingCartReqVo shoppingCartReqVo) throws TException {
		LogCvt.info("进入 购物车 goShoppingCart 参数:");
		LogCvt.info(JSON.toJSONString(shoppingCartReqVo));
		
		// 入参转换
		ShoppingCartReq shoppingCartReq = (ShoppingCartReq)BeanUtil.copyProperties(ShoppingCartReq.class, shoppingCartReqVo);
		// 逻辑层方法调用
		ShoppingCartRes shoppingCartRes = activeShoppingCart.goShoppingCart(shoppingCartReq);
		
		// 出参转换
		ShoppingCartResVo shoppingCartResVo = (ShoppingCartResVo)BeanUtil.copyProperties(ShoppingCartResVo.class, shoppingCartRes);
		return shoppingCartResVo;
	}

	/**
	  * @Title: returnMarketOrder
	  * @Description: 退货交易
	  * @author: zengfanting 2015年11月10日
	  * @modify: lf 2015年12月06日
	  * @param returnMarketOrderReqVo
	  * @return
	  * @throws TException
	  * @see com.froad.thrift.service.ActiveRunService.Iface#returnMarketOrder(com.froad.thrift.vo.active.ReturnMarketOrderReqVo)
	 */
	@Override
	public ReturnMarketOrderResVo returnMarketOrder(ReturnMarketOrderReqVo returnMarketOrderReqVo)throws TException {
		
		LogCvt.info("-----退货查询交易---start--");
		LogCvt.info("---in---"+JSON.toJSONString(returnMarketOrderReqVo));
		
		ReturnMarketOrderReq returnMarketOrderReq = (ReturnMarketOrderReq)BeanUtil.copyProperties(ReturnMarketOrderReq.class, returnMarketOrderReqVo);
		
		ReturnMarketOrderRes returnMarketOrderRes = activeReturnGoodsLogic.returnOrderGoods(returnMarketOrderReq);

		ReturnMarketOrderResVo returnMarketOrderResVo = (ReturnMarketOrderResVo)BeanUtil.copyProperties(ReturnMarketOrderResVo.class, returnMarketOrderRes);
		
		return returnMarketOrderResVo;
		
	}
	
	
	/**
	  * @Title: returnMarketOrderBack
	  * @Description: 退货回退
	  * @author: zengfanting 2015年11月12日
	  * @modify: lf 2015年12月06日
	  * @param returnMarketOrderBackReqVo
	  * @return
	  * @throws TException
	  * @see com.froad.thrift.service.ActiveRunService.Iface#returnMarketOrderBack(com.froad.thrift.vo.active.ReturnMarketOrderBackReqVo)
	 */
	@Override
	public ResultVo returnMarketOrderBack(ReturnMarketOrderBackReqVo returnMarketOrderBackReqVo)throws TException {
		LogCvt.info("-----退货回退交易---start--");
		LogCvt.info("---in---"+JSON.toJSONString(returnMarketOrderBackReqVo));
		
		ReturnMarketOrderBackReq returnMarketOrderBackReq = (ReturnMarketOrderBackReq)BeanUtil.copyProperties(ReturnMarketOrderBackReq.class, returnMarketOrderBackReqVo);
		
		Result result = activeReturnGoodsLogic.returnMarketOrderBack(returnMarketOrderBackReq);
		
		ResultVo resultVo = (ResultVo)BeanUtil.copyProperties(ResultVo.class, result);
		return resultVo;
	}

	/**
	  * @Title: updateMarketOrder
	  * @Description: 1.订单支付成功，更新数据库订单状态
	  *               2.订单支付失败，回退个人资格/全局资格
	  * @author: zengfanting 2015年11月8日
	  * @modify: lf 2015年12月04日
	  * @param arg0
	  * @return
	  * @throws TException
	  * @see com.froad.thrift.service.ActiveRunService.Iface#updateMarketOrder(com.froad.thrift.vo.active.UpdateMarketOrderReqVo)
	 */
	@Override
	public UpdateMarketOrderResVo updateMarketOrder(UpdateMarketOrderReqVo updateMarketOrderReqVo)throws TException {
		LogCvt.info("-----订单更新状态交易---start--");
		LogCvt.info("---in---"+JSON.toJSONString(updateMarketOrderReqVo));
		
		UpdateMarketOrderRes updateMarketOrderRes = null;
		
		UpdateMarketOrderReq updateMarketOrderReq = (UpdateMarketOrderReq)BeanUtil.copyProperties(UpdateMarketOrderReq.class, updateMarketOrderReqVo);
		
		if(updateMarketOrderReqVo.isStatus()){
			updateMarketOrderRes= activeOrderStatusLogic.updateOrderStatusSuccess(updateMarketOrderReq);
		}else{
			updateMarketOrderRes= activeOrderStatusLogic.updateOrderStatusFaile(updateMarketOrderReq);
		}
		
		UpdateMarketOrderResVo updateMarketOrderResVo = (UpdateMarketOrderResVo)BeanUtil.copyProperties(UpdateMarketOrderResVo.class, updateMarketOrderRes);
		return updateMarketOrderResVo;
	}
	
	/**
	  * @Title: createOrderFailureGoBack
	  * @Description: 订单平台创建订单失败，无条件回退资格
	  * @author: zengfanting 2015年11月10日
	  * @modify: lf 2015年12月03日
	  * @param paramFailureGoBackReqVo
	  * @return
	  * @throws TException
	  * @see com.froad.thrift.service.ActiveRunService.Iface#createOrderFailureGoBack(com.froad.thrift.vo.active.FailureGoBackReqVo)
	 */
	@Override
	public ResultVo createOrderFailureGoBack(FailureGoBackReqVo paramFailureGoBackReqVo) throws TException {
		LogCvt.info("订单创建失败回退交易---start--");
		LogCvt.info("--订单创建失败回退交易-in---"+JSON.toJSONString(paramFailureGoBackReqVo));
		
		FailureGoBackReq failureGoBackReq = (FailureGoBackReq)BeanUtil.copyProperties(FailureGoBackReq.class, paramFailureGoBackReqVo);
		
		Result result = activeCreateOrderGoBackLogic.createOrderfailGoBack(failureGoBackReq);
	
		ResultVo resultVo = (ResultVo)BeanUtil.copyProperties(ResultVo.class, result);
		
		return resultVo;
		
	}
	
	
	
	
	/**
	  * @Title: settlementMarkOrder
	  * @Description: 结算
	  * @author: zengfanting 2015年11月12日
	  * @modify: zengfanting 2015年11月12日
	  * @param settlementMarkOrderReq
	  * @return
	  * @throws TException
	  * @see com.froad.thrift.service.ActiveRunService.Iface#settlementMarkOrder(com.froad.thrift.vo.active.SettlementMarkOrderReq)
	 */
	@Override
	public SettlementMarkOrderRes settlementMarkOrder(SettlementMarkOrderReq settlementMarkOrderReq) throws TException {
		
		SettlementMarkOrderRes vo=new SettlementMarkOrderRes();
		LogCvt.info("-----结算交易---start--");
		LogCvt.info("--结算交易-in---"+JSON.toJSONString(settlementMarkOrderReq));
		vo=activeSettlementMarkOrderLogic.settlementMarkOrder(settlementMarkOrderReq);
		LogCvt.info("--结算交易-out---"+JSON.toJSONString(settlementMarkOrderReq));
		LogCvt.info("-----结算交易---start--");
		
		return vo;
	}

	 /**
	  * @Title: putFullCut
	  * @Description: TODO
	  * @author: Joker 2015年12月25日
	  * @modify: Joker 2015年12月25日
	  * @param putFullCutReqVo
	  * @return
	  * @throws TException
	  * @see com.froad.thrift.service.ActiveRunService.Iface#putFullCut(com.froad.thrift.vo.active.PutFullCutReqVo)
	  */
	
	
	@Override
	public ResultVo putFullCut(PutFullCutReqVo putFullCutReqVo) throws TException {
		LogCvt.info("-----[putFullCut]---start--");
		
		PutFullCutReq putFullCutReq = (PutFullCutReq)BeanUtil.copyProperties(PutFullCutReq.class, putFullCutReqVo);
		
		Result result = activeRunLogic.putFullCut(putFullCutReq);
		
		ResultVo resultVo = (ResultVo)BeanUtil.copyProperties(ResultVo.class, result);
		LogCvt.info("-----[putFullCut]---end--");
		return resultVo;
	}
	
	/**
	 * 满赠的支付前资格检查
	 * @author lf 2016年02月24日
	 * @param FullGiveCheckReq -------- fullGiveActiveIds 满赠活动id集合 -- orderProductList 参与满赠活动的订单商品集合
	 * @return Result ------- resultCode==0000:成功 -- resultCode!=0000失败 -- resultDesc:失败原因
	 * */
	@Override
	public ResultVo fullGiveCheck(FullGiveCheckReqVo arg0) throws TException {

		LogCvt.info("-----fullGiveCheck---start--");
		
		FullGiveCheckReq fullGiveCheckReq = (FullGiveCheckReq)BeanUtil.copyProperties(FullGiveCheckReq.class, arg0);
		
		Result result = fullGiveCheckLogic.fullGiveCheck(fullGiveCheckReq);
		
		ResultVo resultVo = (ResultVo)BeanUtil.copyProperties(ResultVo.class, result);
		LogCvt.info("-----fullGiveCheck---end--");
		
		return resultVo;
	}

	

}
