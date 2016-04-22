package com.froad.thrift.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;

import com.alibaba.fastjson.JSON;
import com.froad.enums.ResultCode;
import com.froad.handler.TicketHandler;
import com.froad.handler.TicketQueryHandler;
import com.froad.handler.impl.TicketHandlerImpl;
import com.froad.handler.impl.TicketQueryHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.po.Ticket;
import com.froad.support.TicketSupport;
import com.froad.support.impl.TicketSupportImpl;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.TicketService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.ticket.TicketDetailRequestVo;
import com.froad.thrift.vo.ticket.TicketDetailResponseVo;
import com.froad.thrift.vo.ticket.TicketDetailVo;
import com.froad.thrift.vo.ticket.TicketListRequestVo;
import com.froad.thrift.vo.ticket.TicketListResponseVo;
import com.froad.thrift.vo.ticket.TicketProductResponseVo;
import com.froad.thrift.vo.ticket.TicketRelatedRequestVo;
import com.froad.thrift.vo.ticket.TicketVerifyOfMergerRequestVo;
import com.froad.thrift.vo.ticket.TicketVerifyOfMergerResponseVo;
import com.froad.thrift.vo.ticket.TicketVerifyRequestVo;
import com.froad.thrift.vo.ticket.TicketVerifyResponseVo;
import com.froad.thrift.vo.ticket.VerifyTicketListByPageResponseVo;
import com.froad.util.BeanUtil;

public class TicketServiceImpl extends BizMonitorBaseService implements TicketService.Iface {
	public TicketServiceImpl() {}
	public TicketServiceImpl(String name, String version) {
		super(name, version);
	}

	@Override
	public TicketVerifyResponseVo verifyTickets(TicketVerifyRequestVo requestVo)
			throws TException {
		TicketHandler ticketHandler = null;
		TicketVerifyResponseVo responseVo = null;
		
		LogCvt.info(new StringBuffer("提货验券request：").append(requestVo.toString()).toString());
		
		ticketHandler = new TicketHandlerImpl();
		// 提货验券
		responseVo = ticketHandler.verifyTickets(requestVo);
		
		LogCvt.info(new StringBuffer("提货验券response：").append(responseVo.toString()).toString());
		
		return responseVo;
	}
	
	/**    
	 * 验券(团购)多个商品合并成一个 
	 * @param requestVo
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.TicketService.Iface#verifyTicketsOfMerger(com.froad.thrift.vo.ticket.TicketVerifyOfMergerRequestVo)    
	 */
	
	@Override
	public TicketVerifyOfMergerResponseVo verifyGroupTicketsOfMerger(TicketVerifyOfMergerRequestVo requestVo) throws TException {
		OriginVo originVo = requestVo.getOriginVo();
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("商户合并验券");
		//添加操作日志记录
		if(null != originVo) {
			if(StringUtils.isBlank(originVo.getDescription()))
				originVo.setDescription("商户合并验券");
			com.froad.util.LogUtils.addLog(originVo);
		}
		TicketHandler ticketHandler = null;
		TicketVerifyOfMergerResponseVo responseVo = null;
		
		LogCvt.info(new StringBuffer("商户合并验券request：").append(requestVo.toString()).toString());
		
		ticketHandler = new TicketHandlerImpl();
		// 提货验券
		responseVo = ticketHandler.verifyTicketsOfMerger(requestVo);
		
		LogCvt.info(new StringBuffer("商户合并验券response：").append(responseVo.toString()).toString());
		
		return responseVo;
	}

	@Override
	public TicketDetailResponseVo getTicketDetails(
			TicketDetailRequestVo requestVo) throws TException {
		TicketQueryHandler queryHandler = null;
		TicketDetailResponseVo responseVo = null;
		
		LogCvt.info(new StringBuffer("获取券详情request：").append(requestVo.toString()).toString());
		
		queryHandler = new TicketQueryHandlerImpl();
		responseVo = queryHandler.findTicketDetails(requestVo);
		
		LogCvt.info(new StringBuffer("获取券详情response：").append(responseVo.toString()).toString());
		
		return responseVo;
	}

	@Override
	public ResultVo extendEndDate(String ticketId, long endDate)
			throws TException {
		TicketQueryHandler queryHandler = null;
		ResultVo resultVo = null;
		
		LogCvt.info(new StringBuffer(ticketId).append(" 延长券有效期：").append(endDate).toString());
		
		queryHandler = new TicketQueryHandlerImpl();
		resultVo = queryHandler.extendEndDate(ticketId, endDate);
		
		LogCvt.info(new StringBuffer(ticketId).append(" 延长券有效期：")
				.append(endDate).append(resultVo.toString())
				.toString());
		
		return resultVo;
	}
	
	/**
	 * 延长指定商品id的券有效期
	 * @param productId
	 * @param endDate
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.TicketService.Iface#extendEndDateByProductId(java.lang.String, long)
	 */
	@Override
	public ResultVo extendEndDateByProductId(String productId, long endDate)
			throws TException {
		ResultVo resultVo = new ResultVo();
		if (StringUtils.isBlank(productId)) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("商品id不能为空");
		} else if (endDate < System.currentTimeMillis()) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("券有效期不能小于当前时间");
		} else {
			TicketQueryHandler queryHandler = null;

			LogCvt.info(new StringBuffer().append(" 延长商品id=" + productId + "下所有券的有效期：").append(endDate).toString());

			queryHandler = new TicketQueryHandlerImpl();
			resultVo = queryHandler.extendEndDateByProductId(productId, endDate);

			LogCvt.info(new StringBuffer().append(" 延长商品id=" + productId + "下所有券的有效期：").append(endDate).append(resultVo.toString()).toString());
		}
		return resultVo;
	}

	@Override
	public TicketListResponseVo getTicketList(
			TicketListRequestVo ticketListRequestVo) throws TException {
		TicketQueryHandler queryHandler = null;
		TicketListResponseVo responseVo = null;
		
		LogCvt.info(new StringBuffer("获取券列表request：").append(ticketListRequestVo.toString()).toString());
		
		queryHandler = new TicketQueryHandlerImpl();
		responseVo = queryHandler.findListWithPage(ticketListRequestVo);
		
		LogCvt.info(new StringBuffer("获取券列表response：").append(responseVo.getTicketDetailListSize()).toString());
		
		return responseVo;
	}
	
	/**
	 * 
	 * exportTickets:导出券
	 *
	 * @author vania
	 * 2015年8月28日 上午9:54:27
	 * @param ticketListRequestVo
	 * @return
	 * @throws TException
	 *
	 */
	@Override
	public ExportResultRes exportTickets(TicketListRequestVo ticketListRequestVo) throws TException {
		LogCvt.info(new StringBuffer("导出券列表request：").append(ticketListRequestVo.toString()).toString());
		TicketQueryHandler queryHandler = new TicketQueryHandlerImpl();
		ExportResultRes responseVo = queryHandler.exportListWithPage(ticketListRequestVo);
		LogCvt.info(new StringBuffer("导出券列表response：").append(responseVo).toString());
		return responseVo;
	}
	
	/**     
	 * 查询券(同一种商品合并)
	 * @param ticketListRequestVo
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.TicketService.Iface#getTicketListOfMerger(com.froad.thrift.vo.ticket.TicketListRequestVo)    
	 */	
	@Override
	public TicketListResponseVo getTicketListOfMerger(TicketListRequestVo ticketListRequestVo) throws TException {
		TicketQueryHandler queryHandler = null;
		TicketListResponseVo responseVo = new TicketListResponseVo();
		
		LogCvt.info(new StringBuffer("获取合并券列表request：").append(ticketListRequestVo.toString()).toString());
		if(StringUtils.isEmpty(ticketListRequestVo.getTicketId())){
			responseVo.setResultVo(new ResultVo(ResultCode.failed.getCode(),"券码不能为空!"));
			return responseVo;
		}
		queryHandler = new TicketQueryHandlerImpl();
		responseVo = queryHandler.findListWithPage(ticketListRequestVo);
		
		List<TicketDetailVo> ticketDetailVoList = responseVo.getTicketDetailList();
		
		String ticketId = ticketListRequestVo.getTicketId();
		String ticketIdProductId = null;
		
		if(CollectionUtils.isNotEmpty(ticketDetailVoList)) {
			Map<String, Integer> map = new HashMap<String, Integer>(); // key 为商品id-----------value 商品id有多少个券
//			for (TicketDetailVo ticketDetailVo : ticketDetailVoList) {
//				System.out.println("查出来的数据:"+JSON.toJSONString(ticketDetailVo));
//				String key = ticketDetailVo.getProductId();
//				Integer value = map.get(key);
//				map.put(key, null == value ? 1 : value + 1);
//			}
//			System.out.println("map=====:"+JSON.toJSONString(map));
			
			for (int i = 0; i < ticketDetailVoList.size() - 1; i++) {
				TicketDetailVo ti = ticketDetailVoList.get(i);
				for (int j = ticketDetailVoList.size() - 1; j > i; j--) {
					TicketDetailVo tj = ticketDetailVoList.get(j);
					if (ticketIdProductId == null && ticketId.equals(tj.getTicketId())) // 获取上送券id的商品id
						ticketIdProductId = tj.getProductId();
					if (tj.getProductId().equals(ti.getProductId())) { // 去除重复的
						String key = tj.getProductId();
						Integer value = map.get(key);
//						map.put(key, null == value ? 2 : value + 1);
						map.put(key, null == value ? 2 : value + (tj.getQuantity() < 1 ? 1 : tj.getQuantity()));
						
//						System.out.println("设置 key=====:"+key + "\t value=====" + map.get(key));
						ticketDetailVoList.remove(j);
					}
				}
//				System.out.println("key=====:"+ti.getProductId() + "\t value=====" + map.get(ti.getProductId()));
				Integer quantity = map.get(ti.getProductId());
				if (null != quantity) // 说明有重复的商品
					ti.setQuantity(map.get(ti.getProductId())); // 重新设置商品数量
				if(StringUtils.equals(ticketIdProductId, ti.getProductId()))// 如果是上送的券id则把券id改成上送的券id
					ti.setTicketId(ticketId); 
			} 
//			System.out.println("筛选后的数据---->:"+JSON.toJSONString(ticketDetailVoList, true));
		}
		LogCvt.info(new StringBuffer("获取合并券列表response：").append(responseVo.getTicketDetailListSize()).toString());
		
		return responseVo;
	}
	
	/**
	 * 商户验码记录
	 * @Title: getVerifyTicketListByPage 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param ticketListRequestVo
	 * @return
	 * @throws TException
	 * @return VerifyTicketListByPageResponseVo    返回类型 
	 * @throws
	 */
	public VerifyTicketListByPageResponseVo getVerifyTicketListByPage(
			TicketListRequestVo ticketListRequestVo) throws TException {
		TicketQueryHandler queryHandler = null;
		VerifyTicketListByPageResponseVo responseVo = null;
		
		LogCvt.info(new StringBuffer("获取商户验码记录列表request：").append(ticketListRequestVo.toString()).toString());
		
		queryHandler = new TicketQueryHandlerImpl();
//		responseVo = queryHandler.findListWithPage(ticketListRequestVo);
		responseVo = queryHandler.findVerifyTicketListByPage(ticketListRequestVo);
		
		LogCvt.info(new StringBuffer("获取商户验码记录列表response：").append(responseVo.getTicketDetailListSize()).toString());
		
		return responseVo;
	}

	@Override
	public TicketListResponseVo getRelatedTickets(TicketRelatedRequestVo requestVo)
			throws TException {
		TicketQueryHandler queryHandler = null;
		TicketListResponseVo responseVo = null;
		
		LogCvt.info(new StringBuffer("根据券获取用户相关有效券request：").append(requestVo.toString()).toString());
		
		queryHandler = new TicketQueryHandlerImpl();
		responseVo = queryHandler.findRelatedTickets(requestVo);
		
		LogCvt.info(new StringBuffer("根据券获取用户相关有效券response：").append(responseVo.getTicketDetailListSize()).toString());
		
		return responseVo;
	}

	@Override
	public TicketProductResponseVo getTicketProductDetails(
			TicketDetailRequestVo requestVo) throws TException {
		TicketProductResponseVo responseVo = null;
		TicketQueryHandler queryHandler = null;
		
		LogCvt.info(new StringBuffer("获取个人端有效券request：").append(requestVo.toString()).toString());
		
		queryHandler = new TicketQueryHandlerImpl();
		responseVo = queryHandler.findTicketProduct(requestVo);
		
		LogCvt.info(new StringBuffer("获取个人端有效券response：").append(responseVo.getTicketProductSize()).toString());
		
		return responseVo;
	}

	/**
	 * 获取券详细信息
	 * @param ticketDetailVo
	 * @return    
	 * @see com.froad.thrift.service.TicketService.Iface#getTicketDetail(com.froad.thrift.vo.ticket.TicketDetailVo)
	 */
	@Override
	public List<TicketDetailVo> getTicketDetail(TicketDetailVo ticketDetailVo){
		LogCvt.info(new StringBuffer("获取券详细信息request：").append(ticketDetailVo).toString());
		TicketSupport ticketSupport = new TicketSupportImpl();
		List<Ticket> ticketList = ticketSupport.getTicketList((Ticket) BeanUtil.copyProperties(Ticket.class, ticketDetailVo));
		List<TicketDetailVo> ticketVoList = (List<TicketDetailVo>) BeanUtil.copyProperties(TicketDetailVo.class, ticketList);
		LogCvt.info(new StringBuffer("获取券详细信息response：").append(ticketVoList).toString());
		return ticketVoList;
	}

	
	

}
