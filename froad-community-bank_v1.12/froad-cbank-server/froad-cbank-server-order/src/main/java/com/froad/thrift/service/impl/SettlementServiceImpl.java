package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.froad.common.beans.ResultBean;
import com.froad.db.mongo.page.MongoPage;
import com.froad.enums.SettlementStatus;
import com.froad.logback.LogCvt;
import com.froad.logic.SettlementLogic;
import com.froad.logic.impl.SettlementLogicImpl;
import com.froad.po.settlement.Settlement;
import com.froad.po.settlement.SettlementReq;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.SettlementService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.settlement.SettlementPage;
import com.froad.thrift.vo.settlement.SettlementVo;
import com.froad.thrift.vo.settlement.SettlementVoReq;
import com.froad.util.JSonUtil;

/**
 *  结算Thrift接口
  * @ClassName: SettlementServiceImpl
  * @Description: TODO
  * @author share 2015年3月28日
  * @modify share 2015年3月28日
 */
public class SettlementServiceImpl extends BizMonitorBaseService implements SettlementService.Iface{
	public SettlementServiceImpl() {}
	public SettlementServiceImpl(String name, String version) {
		super(name, version);
	}
	/**
	 *  业务结算逻辑接口
	 */
	private SettlementLogic logic = new SettlementLogicImpl();
	
	/**
	 *  分页查询
	  * @Title: queryByPage
	  * @Description: TODO
	  * @author: share 2015年3月30日
	  * @modify: share 2015年3月30日
	  * @param @param page
	  * @param @return
	  * @param @throws TException
	  * @throws
	  * @see com.froad.thrift.service.SettlementService.Iface#queryByPage(com.froad.thrift.vo.settlement.SettlementPage)
	 */
	@Override
	public SettlementPage queryByPage(SettlementPage page)throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("分页查询结算记录, 请求参数:"+JSonUtil.toJSonString(page));
		
//		SettlementReq req = buildQueryPageRequest(page); // 移动到SettlementLogicImpl了
		// 分页查询
		MongoPage mongoPage = logic.querySettlementByPage(page);
		// 组装响应信息
		page.getPage().setTotalCount(mongoPage.getTotalCount());
		page.getPage().setPageCount(mongoPage.getPageCount());
		page.getPage().setFirstRecordTime(mongoPage.getFirstRecordTime());
		page.getPage().setLastPageNumber(mongoPage.getPageNumber());
		page.getPage().setLastRecordTime(mongoPage.getLastRecordTime());
		page.setRespList(convertVo(mongoPage.getItems()));
		return page;
	}
	
	
	/**
	 * 
	 * exportSettlements:(这里用一句话描述这个方法的作用).
	 *
	 * @author vania
	 * 2015年9月2日 上午9:53:21
	 * @param page
	 * @return
	 * @throws TException
	 *
	 */
	public ExportResultRes exportSettlements(SettlementPage page)throws TException {
		LogCvt.info("分页导出结算记录, 请求参数:" + JSonUtil.toJSonString(page));
		
		ExportResultRes responseVo = logic.exportSettlementByPage(page);
		LogCvt.info(new StringBuffer("分页导出结算记录response：").append(responseVo).toString());
		return responseVo;
	}

	/**
	 *  导出记录查询
	  * @Title: queryList
	  * @Description: TODO
	  * @author: share 2015年3月30日
	  * @modify: share 2015年3月30日
	  * @param @param req
	  * @param @return
	  * @param @throws TException
	  * @throws
	  * @see com.froad.thrift.service.SettlementService.Iface#queryList(com.froad.thrift.vo.settlement.SettlementVoReq)
	 */
	@Override
	public List<SettlementVo> queryList(SettlementVoReq reqVo) throws TException {
		// TODO Auto-generated method stub
		/**
		 *  接口调用
		 */
		SettlementReq req = new SettlementReq();
		req.setBegDate(reqVo.getBegDate());
		req.setEndDate(reqVo.getEndDate());
		req.setClientId(reqVo.getClientId());
		if(reqVo.getMerchantName() != null){
			req.setMerchantName(reqVo.getMerchantName());
		}
		if(reqVo.getOutletName() != null){
			req.setOutletName(reqVo.getOutletName());
		}
		req.setSettleState(SettlementStatus.getSettlementByCode(reqVo.getSettleState()));
		req.setOrderId(reqVo.getOrderId());
		// 分页查询
		List<Settlement> items = logic.querySettlement(req);
		// 组装响应信息
		return convertVo(items);
	}

	/**
	 *  更新接口
	  * @Title: updateSettleState
	  * @Description: TODO
	  * @author: share 2015年3月30日
	  * @modify: share 2015年3月30日
	  * @param @param id
	  * @param @param type
	  * @param @param remark
	  * @param @return
	  * @param @throws TException
	  * @throws
	  * @see com.froad.thrift.service.SettlementService.Iface#updateSettleState(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ResultVo updateSettleState(String id, String type, String remark)throws TException {
		// TODO Auto-generated method stub
		ResultBean result = logic.updateSettlement(id, SettlementStatus.getSettlementByCode(type), remark);
		ResultVo rvo = new ResultVo();
		rvo.setResultCode(result.getCode());
		rvo.setResultDesc(result.getMsg());
		return rvo;
	}

	/**
	 *  根据主键ID查询详情记录
	  * @Title: queryById
	  * @Description: TODO
	  * @author: share 2015年3月30日
	  * @modify: share 2015年3月30日
	  * @param @param id
	  * @param @return
	  * @param @throws TException
	  * @throws
	  * @see com.froad.thrift.service.SettlementService.Iface#queryById(java.lang.String)
	 */
	@Override
	public SettlementVo queryById(String id) throws TException {
		// TODO Auto-generated method stub
		return convertVo(logic.querySettlementById(id));
	}
	
	/**
	 *  名优特惠确认收货结算
	  * @Title: specialSettle
	  * @Description: TODO
	  * @author: share 2015年4月9日
	  * @modify: share 2015年4月9日
	  * @param @param subOrderId
	  * @param @param source
	  * @param @return
	  * @param @throws TException
	  * @throws
	  * @see com.froad.thrift.service.SettlementService.Iface#specialSettle(java.lang.String, int)
	 */
	@Override
	public ResultVo specialSettle(String subOrderId, int source)throws TException {
		// TODO Auto-generated method stub
		ResultBean result = logic.specialSettlement(subOrderId, source);
		ResultVo rvo = new ResultVo();
		rvo.setResultCode(result.getCode());
		rvo.setResultDesc(result.getMsg());
		return rvo;
	}
	
	
	public List<SettlementVo> convertVo(List<?> list){
		List<SettlementVo> settlementList = new ArrayList<SettlementVo>();
		if(list == null){
			return settlementList;
		}
		for (Settlement settle : (List<Settlement>) list) {
			SettlementVo vo = null;
			if (null != settle) {
				vo = convertVo(settle);
			} else {
				vo = new SettlementVo();
			}
			settlementList.add(vo);
		}
		list = null;
		return settlementList;
	}
	
	public SettlementVo convertVo(Settlement settle){
		
		SettlementVo vo = new SettlementVo();
		vo.setClientId(settle.getClientId());
		vo.setCreateTime(settle.getCreateTime());
		vo.setId(settle.getId());
		vo.setSettlementId(settle.getSettlementId());
		vo.setOrderId(settle.getOrderId());
		vo.setSubOrderId(settle.getSubOrderId());
		vo.setMerchantId(settle.getMerchantId());
		vo.setOutletId(settle.getOutletId());
		vo.setType(Integer.parseInt(settle.getType()));
		vo.setPaymentId(settle.getPaymentId());
		vo.setSettleState(settle.getSettleState());
		vo.setMoney(settle.getMoneyd());
		vo.setRemark(settle.getRemark());
		vo.setPaymentId(settle.getProductId());
		vo.setProductName(settle.getProductName());
		vo.setProductCount(settle.getProductCount());
		vo.setTickets(settle.getTickets());
		vo.setMerchantName(settle.getMerchantName());
		vo.setOutletName(settle.getOutletName());
		return vo;
	}

	
	/**
	 * 根据券id集合查询券的结算状态
	 * @param ticketIdList
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.SettlementService.Iface#getTicketIdSettlementList(java.util.List)
	 */
	public List<SettlementVo> getTicketIdSettlementList(List<String> ticketIdList) throws TException {
		return convertVo(logic.getTicketIdSettlementList(ticketIdList));
	}

}

