package com.froad.handler.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mongo.page.OrderBy;
import com.froad.db.mongo.page.Sort;
import com.froad.enums.FieldMapping;
import com.froad.enums.OrderType;
import com.froad.enums.OrgLevelEnum;
import com.froad.enums.ResultCode;
import com.froad.enums.SettlementStatus;
import com.froad.enums.SubOrderType;
import com.froad.enums.TicketStatus;
import com.froad.exceptions.FroadBusinessException;
import com.froad.ftp.ExcelWriter;
import com.froad.handler.TicketQueryHandler;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.po.Org;
import com.froad.po.Ticket;
import com.froad.support.SettlementSupport;
import com.froad.support.TicketSupport;
import com.froad.support.impl.SettlementSupportImpl;
import com.froad.support.impl.TicketSupportImpl;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.ticket.TicketDetailRequestVo;
import com.froad.thrift.vo.ticket.TicketDetailResponseVo;
import com.froad.thrift.vo.ticket.TicketDetailVo;
import com.froad.thrift.vo.ticket.TicketListRequestVo;
import com.froad.thrift.vo.ticket.TicketListResponseVo;
import com.froad.thrift.vo.ticket.TicketProductResponseVo;
import com.froad.thrift.vo.ticket.TicketProductVo;
import com.froad.thrift.vo.ticket.TicketRelatedRequestVo;
import com.froad.thrift.vo.ticket.VerifyTicketListByPageResponseVo;
import com.froad.util.Arith;
import com.froad.util.DateUtil;
import com.froad.util.EmptyChecker;
import com.froad.util.JSonUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

public class TicketQueryHandlerImpl implements TicketQueryHandler {

	/**
	 * 券数据库访问
	 */
	private TicketSupport ticketSupport = null;
	private CommonLogic commonLogic = null;
	private SettlementSupport settlementSupport = null;

	public TicketQueryHandlerImpl() {
		ticketSupport = new TicketSupportImpl();
	}

	@Override
	public TicketDetailResponseVo findTicketDetails(TicketDetailRequestVo requestVo) {
		TicketDetailResponseVo responseVo = null;
		TicketDetailVo ticketDetail = null;
		ResultVo resultVo = null;
		Ticket ticket = null;
		String option = null;
		String status = null;
		List<Ticket> ticketList = null;
		boolean isConsumed = false;
		boolean isExpired = false;
		boolean isDiffMerchant = false;
		Map<String, String> productMap = null;

		option = requestVo.getOption();

		responseVo = new TicketDetailResponseVo();
		ticketDetail = new TicketDetailVo();
		resultVo = new ResultVo();

		try {
			if (option.equals("3")) {
				// 根据ticketId + status查找
				status = requestVo.getStatus();

				ticket = ticketSupport.getUniqueTicket(requestVo.getTicketId(), status);

				if (ticket != null) {
					ticketDetail = convertPoToVo(ticket);

					responseVo.setTicketDetail(ticketDetail);
					resultVo.setResultCode(ResultCode.success.getCode());
				} else {
					resultVo.setResultCode(ResultCode.failed.getCode());
					resultVo.setResultDesc("Ticket not found");
				}
			} else if (option.equals("1")) {
				ticketList = ticketSupport.getTicketByTicketId(requestVo.getTicketId());

				if (null == ticketList || ticketList.size() == 0) {
					resultVo.setResultCode(ResultCode.failed.getCode());
					resultVo.setResultDesc("提货码不存在");
				} else {
					for (int i = 0; i < ticketList.size(); i++) {
						ticket = ticketList.get(i);
						if (ticket.getStatus().equals(TicketStatus.sent.getCode())) {
							break;
						}
					}

					productMap = getRedisProductMap(ticket);
					if (ticket.getType().equals(SubOrderType.group_merchant.getCode())) {
						// 团购取expire_start_time
						if (null != productMap) {
							ticket.setCreateTime(Long.valueOf(productMap.get("expire_start_time")));
						}
					} else {
						// 预售取delivery_start_time
						if (null != productMap) {
							ticket.setCreateTime(Long.valueOf(productMap.get("delivery_start_time")));
						}
					}
					ticketDetail = convertPoToVo(ticket);

					responseVo.setTicketDetail(ticketDetail);
					resultVo.setResultCode(ResultCode.success.getCode());
				}
			} else if (option.equals("2")) {
				// 根据merchantId + ticketId查找券
				ticketList = ticketSupport.getTicketByTicketId(requestVo.getTicketId());

				if (null == ticketList || ticketList.size() == 0) {
					resultVo.setResultCode(ResultCode.failed.getCode());
					resultVo.setResultDesc("提货码不存在");
				} else {
					for (int i = 0; i < ticketList.size(); i++) {
						ticket = ticketList.get(i);
						if (!ticket.getMerchantId().equals(requestVo.getMerchantId())) {
							isDiffMerchant = true;
							break;
						}

						// 如果是团购类型，直接返回；否则返回未消费类型
						if (ticket.getType().equals(SubOrderType.group_merchant.getCode()) || ticket.getStatus().equals(TicketStatus.sent.getCode())) {
							ticketDetail = convertPoToVo(ticket);
							break;
						} else if (ticket.getStatus().equals(TicketStatus.consumed.getCode())) {
							isConsumed = true;
						} else if (ticket.getStatus().equals(TicketStatus.expired.getCode())) {
							isExpired = false;
						}
					}

					// 银行端只能查找预售券
					if (requestVo.getResource().equals("1") && !ticket.getType().equals(SubOrderType.presell_org.getCode())) {
						resultVo.setResultCode(ResultCode.failed.getCode());
						resultVo.setResultDesc("机构网点只能查找预售提货码");
					} else if (requestVo.getResource().equals("2") && !ticket.getType().equals(SubOrderType.group_merchant.getCode())) {
						resultVo.setResultCode(ResultCode.failed.getCode());
						resultVo.setResultDesc("商户只能查找团购提货码");
					} else if (isDiffMerchant) {
						resultVo.setResultCode(ResultCode.failed.getCode());
						resultVo.setResultDesc("非本商户提货码");
					} else if (isConsumed) {
						resultVo.setResultCode(ResultCode.failed.getCode());
						resultVo.setResultDesc("提货码已使用");
					} else if (isExpired) {
						resultVo.setResultCode(ResultCode.failed.getCode());
						resultVo.setResultDesc("提货码已过期");
					} else {
						responseVo.setTicketDetail(ticketDetail);
						resultVo.setResultCode(ResultCode.success.getCode());
					}
				}
			} else {
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("非法查找选项");
			}
		} catch (Exception e) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("获取提货码详情失败");
			LogCvt.error(new StringBuffer("获取提货码详情失败：").append(requestVo.toString()).toString(), e);
		}

		responseVo.setResultVo(resultVo);

		return responseVo;
	}

	@Override
	public ResultVo extendEndDate(String ticketId, long endDate) {
		ResultVo resultVo = null;
		Ticket ticket = null;
		DBObject valueObj = null;

		resultVo = new ResultVo();

		try {
			ticket = ticketSupport.getUniqueTicket(ticketId, TicketStatus.sent.getCode());
			if (null == ticket) {
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("无效的提货码。");
			} else {
				ticket.setExpireTime(endDate);
				valueObj = new BasicDBObject(FieldMapping.EXPIRE_TIME.getMongoField(), endDate);
				ticketSupport.updateById(valueObj, JSonUtil.toObjectId(ticket.get_id()));
				resultVo.setResultCode(ResultCode.success.getCode());
			}
		} catch (Exception e) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("修改提货码有效期失败");
			LogCvt.error(new StringBuffer("修改提货码有效期失败：").append(ticketId).toString(), e);
		}

		return resultVo;
	}

	@Override
	public ResultVo extendEndDateByProductId(String productId, long endDate) {
		ResultVo resultVo = null;
		DBObject valueObj = null;
		DBObject where = null;

		resultVo = new ResultVo();

		try {
			valueObj = new BasicDBObject(FieldMapping.EXPIRE_TIME.getMongoField(), endDate);
			where = new BasicDBObject(FieldMapping.PRODUCT_ID.getMongoField(), productId);
			where.put(FieldMapping.STATUS.getMongoField(), TicketStatus.sent.getCode()); // 只修改为消费的券
			ticketSupport.updateMultiByDBObject(where, valueObj);
			resultVo.setResultCode(ResultCode.success.getCode());

		} catch (Exception e) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("修改提货码有效期失败");
			LogCvt.error(new StringBuffer("修改商品id=" + productId + "提货码有效期失败：").toString(), e);
		}

		return resultVo;
	}

	public MongoPage findListByExport(TicketListRequestVo requestVo) throws FroadBusinessException {
		// 分页查找
		return ticketSupport.exportTicketPageByDBObject(buildFindByPageCondition(requestVo), buidMongoPage(requestVo));
	}

	@SuppressWarnings("unchecked")
	@Override
	public TicketListResponseVo findListWithPage(TicketListRequestVo requestVo) {
		TicketListResponseVo responseVo = null;
		List<TicketDetailVo> ticketDetailList = null;
		ResultVo resultVo = null;
		PageVo pageVo = null;
		MongoPage mongoPage = null;
		String source = null;
		String resource = null;
		List<Ticket> ticketList = null;
		DBObject queryObj = null;
		OrgLevelEnum orgLevel = null;
		CommonLogic orgLogic = null;

		List<String> statusList = null;

		source = requestVo.getSource();
		resource = requestVo.getResource();
		responseVo = new TicketListResponseVo();
		resultVo = new ResultVo();
		ticketDetailList = new ArrayList<TicketDetailVo>();
		pageVo = new PageVo();

		try {
			if (null == source || (!source.equals("1") && !source.equals("2") && !source.equals("3") && !source.equals("4") && !source.equals("5"))) {
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("非法查找来源");
			} else {
				try {
					queryObj = buildFindByPageCondition(requestVo);
				} catch (FroadBusinessException e) {
					resultVo.setResultCode(ResultCode.failed.getCode());
					resultVo.setResultDesc(e.getMessage());
					return responseVo;
				} catch (Exception e) {
					LogCvt.error("获取提货码列表失败", e);
					resultVo.setResultCode(ResultCode.failed.getCode());
					resultVo.setResultDesc("获取提货码列表失败");
					return responseVo;
				}

				if (source.equals("3") && null == requestVo.getPageVo()) {
					pageVo.setTotalCount(ticketSupport.getTotalCount(queryObj));
				} else if (requestVo.getPageVo().getPageNumber() == 0 || requestVo.getPageVo().getPageSize() == 0) {
					// 不分页查找
					ticketList = ticketSupport.getListByDBObject(queryObj);
					ticketDetailList = convertToVoList(ticketList);
					pageVo.setPageCount(1);
					pageVo.setPageNumber(1);
					pageVo.setPageSize(1);
					pageVo.setTotalCount(ticketDetailList.size());

				} else {
					// 分页查找
					mongoPage = buidMongoPage(requestVo);
					String sortField = requestVo.getSortField();
					if (StringUtils.equals(sortField, "1")) { // 1-根据消费时间倒序
						mongoPage.setSort(new Sort(FieldMapping.CONSUME_TIME.getMongoField(), OrderBy.DESC).on(FieldMapping.CREATE_TIME.getMongoField(), OrderBy.DESC));
						mongoPage = ticketSupport.getTicketPageByConsumeTime(queryObj, mongoPage);
					} else { // 根据创建时间排序
						mongoPage = ticketSupport.getTicketPageByDBObject(queryObj, mongoPage);
					}
					ticketDetailList = convertToVoList((List<Ticket>) mongoPage.getItems());
					if (source.equals("5") && null != ticketDetailList && ticketDetailList.size() > 0 && ticketDetailList.get(0).getType().equals(SubOrderType.presell_org.getCode())) {
						ticketDetailList = filterDuplicateRecord(ticketDetailList);
						// LogCvt.info(new
						// StringBuffer("个人H5根据子订单查看预售提货码返回：").append(ticketDetailList).toString());
					}
					buidPageVo(pageVo, mongoPage);
					// if (null != ticketDetailList && ticketDetailList.size() >
					// 0){
					// pageVo.setFirstRecordTime(ticketDetailList.get(0).getCreateTime());
					// pageVo.setLastRecordTime(ticketDetailList.get(ticketDetailList.size()
					// - 1).getCreateTime());
					// }
				}

				resultVo.setResultCode(ResultCode.success.getCode());
				responseVo.setPageVo(pageVo);
				responseVo.setTicketDetailList(ticketDetailList);
				LogCvt.info(responseVo.toString());
			}

		} catch (Exception e) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("获取提货码列表失败");
			LogCvt.error(new StringBuffer("获取提货码列表失败：").append(requestVo.toString()).toString(), e);
		}

		responseVo.setResultVo(resultVo);

		return responseVo;
	}

	/**
	 * buidPageVo:(这里用一句话描述这个方法的作用).
	 * 
	 * @author vania 2015年9月16日 下午8:15:43
	 * @param pageVo
	 * @param mongoPage
	 * 
	 */
	private void buidPageVo(PageVo pageVo, MongoPage mongoPage) {
		pageVo.setPageCount(mongoPage.getPageCount());
		pageVo.setPageNumber(mongoPage.getPageNumber());
		pageVo.setPageSize(mongoPage.getPageSize());
		pageVo.setTotalCount(mongoPage.getTotalCount());
		pageVo.setHasNext(mongoPage.getHasNext());
		pageVo.setLastPageNumber(pageVo.getPageNumber());
		pageVo.setFirstRecordTime(mongoPage.getFirstRecordTime());
		pageVo.setLastRecordTime(mongoPage.getLastRecordTime());
	}

	/**
	 * buidMongoPage:(这里用一句话描述这个方法的作用).
	 * 
	 * @author vania 2015年9月16日 下午8:14:59
	 * @param requestVo
	 * @return
	 * 
	 */
	private MongoPage buidMongoPage(TicketListRequestVo requestVo) {
		MongoPage mongoPage;
		mongoPage = new MongoPage();
		mongoPage.setPageNumber(requestVo.getPageVo().getPageNumber());
		mongoPage.setLastPageNumber(requestVo.getPageVo().getLastPageNumber());
		if (requestVo.getPageVo().getFirstRecordTime() > 0) {
			mongoPage.setFirstRecordTime(requestVo.getPageVo().getFirstRecordTime());
		}
		if (requestVo.getPageVo().getLastRecordTime() > 0) {
			mongoPage.setLastRecordTime(requestVo.getPageVo().getLastRecordTime());
		}
		mongoPage.setPageSize(requestVo.getPageVo().getPageSize());
		if (requestVo.getPageVo().getTotalCount() > 0) {
			mongoPage.setTotalCount(requestVo.getPageVo().getTotalCount());
		}
		return mongoPage;
	}

	/**
	 * 
	 * buildFindByPageCondition:构建分页查询券码条件
	 * 
	 * @author vania 2015年9月16日 下午8:02:52
	 * @param requestVo
	 * @return
	 * @throws FroadBusinessException
	 * 
	 */
	private DBObject buildFindByPageCondition(TicketListRequestVo requestVo) throws FroadBusinessException {
		String source = requestVo.getSource();
		StringBuffer logInfo = new StringBuffer("");
		List<Ticket> ticketList;
		DBObject queryObj;
		OrgLevelEnum orgLevel;
		CommonLogic orgLogic;
		List<String> statusList;
		List<String> notStatus = new ArrayList<String>();
		notStatus.add(TicketStatus.init.getCode()); // 待发送
		notStatus.add(TicketStatus.init_refunded.getCode()); // 未发码退款
		queryObj = new BasicDBObject();
		LogCvt.info(logInfo.append("券列表查找：").append(requestVo.toString()).toString());
		if (!StringUtils.isBlank(requestVo.getClientId())) {
			queryObj.put(FieldMapping.CLIENT_ID.getMongoField(), requestVo.getClientId());
		}
		if (StringUtils.equals(source, "1")) {
			// 根据会员编号(memberCode)查找
			queryObj.put(FieldMapping.MEMBER_CODE.getMongoField(), requestVo.getMemberCode());
			if (null != requestVo.getStatus()) {
				queryObj.put(FieldMapping.STATUS.getMongoField(), requestVo.status);
			} else {
				// 不返回未发送券
				// queryObj.put(FieldMapping.STATUS.getMongoField(), new
				// BasicDBObject("$ne" , TicketStatus.init.getCode()));
				queryObj.put(FieldMapping.STATUS.getMongoField(), new BasicDBObject(QueryOperators.NIN, notStatus));
			}
			if (!EmptyChecker.isEmpty(requestVo.getType())) {
				queryObj.put(FieldMapping.TYPE.getMongoField(), requestVo.getType());
			}
			if (!EmptyChecker.isEmpty(requestVo.getStartDate()) && !EmptyChecker.isEmpty(requestVo.getEndDate())) {
				queryObj.put(FieldMapping.CREATE_TIME.getMongoField(), new BasicDBObject("$gte", Long.parseLong(requestVo.getStartDate())).append("$lte", Long.parseLong(requestVo.getEndDate())));
			} else if (!EmptyChecker.isEmpty(requestVo.getStartDate())) {
				queryObj.put(FieldMapping.CREATE_TIME.getMongoField(), new BasicDBObject("$gte", Long.parseLong(requestVo.getStartDate())));
			} else if (!EmptyChecker.isEmpty(requestVo.getEndDate())) {
				queryObj.put(FieldMapping.CREATE_TIME.getMongoField(), new BasicDBObject("$lte", Long.parseLong(requestVo.getEndDate())));
			}

		} else if (StringUtils.equals(source, "2")) {
			// 根据券有效期(startDate, endDate)时间范围查找
			if (null != requestVo.getResource() && requestVo.getResource().equals("4")) {
				// boss查找
				if (!EmptyChecker.isEmpty(requestVo.getStartDate()) && !EmptyChecker.isEmpty(requestVo.getEndDate())) {
					queryObj.put(FieldMapping.CREATE_TIME.getMongoField(), new BasicDBObject("$gte", Long.valueOf(requestVo.getStartDate())).append("$lte", Long.valueOf(requestVo.getEndDate())));
				} else if (!EmptyChecker.isEmpty(requestVo.getStartDate())) {
					queryObj.put(FieldMapping.CREATE_TIME.getMongoField(), new BasicDBObject("$gte", Long.valueOf(requestVo.getStartDate())));
				} else if (!EmptyChecker.isEmpty(requestVo.getEndDate())) {
					queryObj.put(FieldMapping.CREATE_TIME.getMongoField(), new BasicDBObject("$lte", Long.valueOf(requestVo.getEndDate())));
				}

				if (!EmptyChecker.isEmpty(requestVo.getPageVo().getBegDate()) && !EmptyChecker.isEmpty(requestVo.getPageVo().getEndDate())) {
					queryObj.put(FieldMapping.CREATE_TIME.getMongoField(), new BasicDBObject("$gte", Long.valueOf(requestVo.getPageVo().getBegDate())).append("$lte", Long.valueOf(requestVo.getPageVo().getEndDate())));
				} else if (!EmptyChecker.isEmpty(requestVo.getStartDate())) {
					queryObj.put(FieldMapping.CREATE_TIME.getMongoField(), new BasicDBObject("$gte", Long.valueOf(requestVo.getPageVo().getBegDate())));
				} else if (!EmptyChecker.isEmpty(requestVo.getEndDate())) {
					queryObj.put(FieldMapping.CREATE_TIME.getMongoField(), new BasicDBObject("$lte", Long.valueOf(requestVo.getPageVo().getEndDate())));
				}

				if (EmptyChecker.isNotEmpty(requestVo.getMemberCode())) {
					queryObj.put(FieldMapping.MEMBER_NAME.getMongoField(), requestVo.getMemberCode());
				}
			} else {
				if (!EmptyChecker.isEmpty(requestVo.getStartDate()) && !EmptyChecker.isEmpty(requestVo.getEndDate())) {
					queryObj.put(FieldMapping.EXPIRE_TIME.getMongoField(), new BasicDBObject("$gte", Long.parseLong(requestVo.getStartDate())).append("$lte", Long.parseLong(requestVo.getEndDate())));
				} else if (!EmptyChecker.isEmpty(requestVo.getStartDate())) {
					queryObj.put(FieldMapping.EXPIRE_TIME.getMongoField(), new BasicDBObject("$gte", Long.parseLong(requestVo.getStartDate())));
				} else if (!EmptyChecker.isEmpty(requestVo.getEndDate())) {
					queryObj.put(FieldMapping.EXPIRE_TIME.getMongoField(), new BasicDBObject("$lte", Long.parseLong(requestVo.getEndDate())));
				}
			}

			if (EmptyChecker.isNotEmpty(requestVo.getMerchantUserName())) {
				queryObj.put(FieldMapping.MERCHANT_USER_NAME.getMongoField(), requestVo.getMerchantUserName());
			}
			if (EmptyChecker.isNotEmpty(requestVo.getTicketId())) {
				queryObj.put(FieldMapping.TICKET_ID.getMongoField(), requestVo.getTicketId());
			}
			if (!EmptyChecker.isEmpty(requestVo.getStatus())) {
				statusList = new ArrayList<String>();
				statusList.add(requestVo.getStatus());
				if (requestVo.getStatus().equals(TicketStatus.sent.getCode())) {
					statusList.add(TicketStatus.refund_failed.getCode());
				} else if (requestVo.getStatus().equals(TicketStatus.refunded.getCode())) {
					statusList.add(TicketStatus.expired_refunded.getCode());
				}
				queryObj.put(FieldMapping.STATUS.getMongoField(), new BasicDBObject("$in", statusList));
			} else {
				// 不返回未发送券
				// queryObj.put(FieldMapping.STATUS.getMongoField(), new
				// BasicDBObject("$ne" , TicketStatus.init.getCode()));
				queryObj.put(FieldMapping.STATUS.getMongoField(), new BasicDBObject(QueryOperators.NIN, notStatus));
			}
			if (!EmptyChecker.isEmpty(requestVo.getType())) {
				queryObj.put(FieldMapping.TYPE.getMongoField(), requestVo.getType());
			}
			if (!EmptyChecker.isEmpty(requestVo.getOrderId())) {
				queryObj.put(FieldMapping.ORDER_ID.getMongoField(), requestVo.getOrderId());
			}
			if (!EmptyChecker.isEmpty(requestVo.getMobile())) {
				queryObj.put(FieldMapping.SMS_NUMBER.getMongoField(), requestVo.getMobile());
			}
		} else if (StringUtils.equals(source, "3")) {
			// 按商户、门店(merchantId/outletId)查找
			if (!EmptyChecker.isEmpty(requestVo.getMerchantId())) {
				queryObj.put(FieldMapping.MERCHANT_ID.getMongoField(), requestVo.getMerchantId());
			}
			if (!EmptyChecker.isEmpty(requestVo.getOutletId())) {
				queryObj.put(FieldMapping.OUTLET_ID.getMongoField(), requestVo.getOutletId());
			}
			if (!EmptyChecker.isEmpty(requestVo.getStartDate()) && !EmptyChecker.isEmpty(requestVo.getEndDate())) {
				queryObj.put(FieldMapping.CONSUME_TIME.getMongoField(), new BasicDBObject("$gte", Long.parseLong(requestVo.getStartDate())).append("$lte", Long.parseLong(requestVo.getEndDate())));
			} else if (!EmptyChecker.isEmpty(requestVo.getStartDate())) {
				queryObj.put(FieldMapping.CONSUME_TIME.getMongoField(), new BasicDBObject("$gte", Long.parseLong(requestVo.getStartDate())));
			} else if (!EmptyChecker.isEmpty(requestVo.getEndDate())) {
				queryObj.put(FieldMapping.CONSUME_TIME.getMongoField(), new BasicDBObject("$lte", Long.parseLong(requestVo.getEndDate())));
			}
			if (!EmptyChecker.isEmpty(requestVo.getStatus())) {
				queryObj.put(FieldMapping.STATUS.getMongoField(), requestVo.getStatus());
			} else {
				// 不返回未发送券
				// queryObj.put(FieldMapping.STATUS.getMongoField(), new
				// BasicDBObject("$ne" , TicketStatus.init.getCode()));
				queryObj.put(FieldMapping.STATUS.getMongoField(), new BasicDBObject(QueryOperators.NIN, notStatus));
			}
			if (!EmptyChecker.isEmpty(requestVo.getType())) {
				queryObj.put(FieldMapping.TYPE.getMongoField(), requestVo.getType());
			}
			if (!EmptyChecker.isEmpty(requestVo.getMerchantUserName())) {
				queryObj.put(FieldMapping.MERCHANT_USER_NAME.getMongoField(), requestVo.getMerchantUserName());
			}
			if (!EmptyChecker.isEmpty(requestVo.getTicketId())) {
				// 根据券号查找相关券
				ticketList = ticketSupport.getTicketByTicketId(requestVo.getTicketId());

				if (null == ticketList || ticketList.size() == 0) {
					throw new FroadBusinessException(ResultCode.failed.getCode(), "无效提货码");
					// resultVo.setResultCode(ResultCode.failed.getCode());
					// resultVo.setResultDesc("无效提货码");
					// responseVo.setResultVo(resultVo);
					// return responseVo;
				}
				// 查找该商户下会员子订单券表
				queryObj.put(FieldMapping.MEMBER_CODE.getMongoField(), ticketList.get(0).getMemberCode());
				queryObj.put(FieldMapping.SUB_ORDER_ID.getMongoField(), ticketList.get(0).getSubOrderId());
			}

		} else if (StringUtils.equals(source, "4")) {
			// 按机构码(orgCode)查找
			orgLogic = new CommonLogicImpl();
			orgLevel = orgLogic.getOrgLevelByOrgCode(requestVo.getOrgCode(), requestVo.getClientId());
			if (orgLevel.equals(OrgLevelEnum.orgLevel_one)) {
				queryObj.put(FieldMapping.FORG_CODE.getMongoField(), requestVo.getOrgCode());
			} else if (orgLevel.equals(OrgLevelEnum.orgLevel_two)) {
				queryObj.put(FieldMapping.SORG_CODE.getMongoField(), requestVo.getOrgCode());
			} else if (orgLevel.equals(OrgLevelEnum.orgLevel_three)) {
				queryObj.put(FieldMapping.TORG_CODE.getMongoField(), requestVo.getOrgCode());
			} else if (orgLevel.equals(OrgLevelEnum.orgLevel_four)) {
				queryObj.put(FieldMapping.LORG_CODE.getMongoField(), requestVo.getOrgCode());
			}
			if (!EmptyChecker.isEmpty(requestVo.getStartDate()) && !EmptyChecker.isEmpty(requestVo.getEndDate())) {
				queryObj.put(FieldMapping.CREATE_TIME.getMongoField(), new BasicDBObject("$gte", Long.parseLong(requestVo.getStartDate())).append("$lte", Long.parseLong(requestVo.getEndDate())));
			} else if (!EmptyChecker.isEmpty(requestVo.getStartDate())) {
				queryObj.put(FieldMapping.CREATE_TIME.getMongoField(), new BasicDBObject("$gte", Long.parseLong(requestVo.getStartDate())));
			} else if (!EmptyChecker.isEmpty(requestVo.getEndDate())) {
				queryObj.put(FieldMapping.CREATE_TIME.getMongoField(), new BasicDBObject("$lte", Long.parseLong(requestVo.getEndDate())));
			}
			if (!EmptyChecker.isEmpty(requestVo.getStatus())) {
				queryObj.put(FieldMapping.STATUS.getMongoField(), requestVo.getStatus());
			} else {
				// 不返回未发送券
				// queryObj.put(FieldMapping.STATUS.getMongoField(), new
				// BasicDBObject("$ne" , TicketStatus.init.getCode()));
				queryObj.put(FieldMapping.STATUS.getMongoField(), new BasicDBObject(QueryOperators.NIN, notStatus));
			}
			if (!EmptyChecker.isEmpty(requestVo.getType())) {
				queryObj.put(FieldMapping.TYPE.getMongoField(), requestVo.getType());
			}
			/** author by vania 20150824, 需求：V1.1.0 */
			if (!EmptyChecker.isEmpty(requestVo.getOrderId())) {
				queryObj.put(FieldMapping.ORDER_ID.getMongoField(), requestVo.getOrderId());
			}
			if (!EmptyChecker.isEmpty(requestVo.getSubOrderId())) {
				queryObj.put(FieldMapping.SUB_ORDER_ID.getMongoField(), requestVo.getSubOrderId());
			}
			/** author by vania 20150824, 需求：V1.1.0 */
		} else if (StringUtils.equals(source, "5")) {
			// 根据子订单号(+memberCode-验证查找)查找
			queryObj.put(FieldMapping.SUB_ORDER_ID.getMongoField(), requestVo.getSubOrderId());
			queryObj.put(FieldMapping.MEMBER_CODE.getMongoField(), requestVo.getMemberCode());
			if (!EmptyChecker.isEmpty(requestVo.getStatus())) {
				queryObj.put(FieldMapping.STATUS.getMongoField(), requestVo.getStatus());
			} else {
				// 不返回未发送券
				// queryObj.put(FieldMapping.STATUS.getMongoField(), new
				// BasicDBObject("$ne" , TicketStatus.init.getCode()));
				queryObj.put(FieldMapping.STATUS.getMongoField(), new BasicDBObject(QueryOperators.NIN, notStatus));
			}
		} else {
			throw new FroadBusinessException(ResultCode.failed.getCode(), "无效的source");
		}
		return queryObj;
	}

	public ExportResultRes exportListWithPage(TicketListRequestVo requestVo) {
		commonLogic = new CommonLogicImpl();
		settlementSupport = new SettlementSupportImpl();
		ExportResultRes res = new ExportResultRes();
		ResultVo resultVo = new ResultVo(ResultCode.success.getCode(), "");

		TicketListResponseVo responseVo = null;

		PageVo pageVo = requestVo.getPageVo();
		if (null == pageVo) {
			pageVo = new PageVo();
		}
		boolean hasNext = false; // 是否还有下一页

		pageVo.setPageNumber(1);
		pageVo.setPageSize(20000);
		requestVo.setPageVo(pageVo);

		ExcelWriter excelWriter = null;
		try {

			do {
				LogCvt.debug("分页导出第" + pageVo.getPageNumber() + "页【提货码】到Excel，每页显示" + pageVo.getPageSize() + "条，type=" + requestVo.getType() + "，hasNext=" + hasNext);
				// responseVo = this.findListWithPage(requestVo); // 分页查询数据
				MongoPage mongoPage = this.findListByExport(requestVo);
				if (pageVo.getPageNumber() == 1) {
					excelWriter = new ExcelWriter(pageVo.getPageSize());
				}

				generateExcel(requestVo, excelWriter, mongoPage, pageVo);// 生成excel

				// pageVo = responseVo.getPageVo();

				pageVo.setTotalCount(mongoPage.getTotalCount());
				pageVo.setPageCount(mongoPage.getPageCount());
				pageVo.setFirstRecordTime(mongoPage.getFirstRecordTime());
				pageVo.setLastPageNumber(mongoPage.getPageNumber());
				pageVo.setPageNumber(mongoPage.getPageNumber() + 1); // 翻到下一页
				pageVo.setLastRecordTime(mongoPage.getLastRecordTime());

				requestVo.setPageVo(pageVo);
				hasNext = mongoPage.getHasNext(); // 是否还有下一页
			} while (hasNext);
		} catch (FroadBusinessException e) {
			LogCvt.error("导出提货码到Excel失败，原因：" + e.getMessage(), e);
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("导出提货码到Excel失败，原因：" + e.getMessage(), e);
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("导出提货码失败");
		}
		res.setResultVo(resultVo);
		res.setUrl(excelWriter != null ? excelWriter.getUrl() : "");
		return res;
	}

	/**
	 * generateExcel:导出到Excel
	 * 
	 * @author vania 2015年9月11日 上午9:02:44
	 * @param requestVo
	 * @param excelWriter
	 * @param responseVo
	 * @param pageVo
	 * @throws FroadBusinessException
	 * @throws Exception
	 * 
	 */
	private void generateExcel(TicketListRequestVo requestVo, ExcelWriter excelWriter, MongoPage mongoPage, PageVo pageVo) throws FroadBusinessException, Exception {
		// ResultVo resultVo = new ResultVo(ResultCode.success.getCode(), "");
		// String url = ""; // 报表URL
		// List<TicketDetailVo> ticketList = responseVo.getTicketDetailList();
		List<Ticket> ticketList = (List<Ticket>) mongoPage.getItems();
		int count = null == ticketList ? 0 : ticketList.size();

		String[] header = null; // 表头
		List<List<String>> data = null; // 表数据
		String sheetName = ""; // sheetName

		String type = requestVo.getType(); // 类型
		OrderType orderType = OrderType.getByType(type);

		switch (orderType) {
		case group_merchant: // 1-团购
			header = new String[] { "序号", "订单编号", "提货时间", "提货码", "金额", "商品名称", "消费门店", "提货人信息", "结算状态", "操作人" };
			data = new ArrayList<List<String>>();

			sheetName = "团购提货码列表";
			if (count > 0) {
				List<String> tickets = new ArrayList<String>();
				for (int i = 0; i < count; i++) {
					Ticket ticketVo = ticketList.get(i);
					tickets.add(ticketVo.getTicketId());
				}
				Map<String, String> settMap = settlementSupport.querySettlementStatusMap(tickets); // 查询券对应的结算状态
				for (int i = 0; i < count; i++) {
					Ticket ticketVo = ticketList.get(i);
					String[] row = new String[header.length]; // 行数据
					row[0] = (i + 1) + "";
					row[1] = ticketVo.getOrderId(); // 这里显示大订单
					Long consumeTime = ticketVo.getConsumeTime();
					row[2] = consumeTime == null || consumeTime == 0 ? "" : DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, consumeTime);
					String ticketId = ticketVo.getTicketId();
					row[3] = ticketId;
					row[4] = Arith.getDoubleDecimalString(Arith.div(ticketVo.getPrice().doubleValue(), 1000, 2));
					// row[4] = ticketVo.getPrice();
					row[5] = ticketVo.getProductName();
					row[6] = ticketVo.getOutletName();
					row[7] = ticketVo.getMemberName() + ":" + ticketVo.getMobile();
					row[8] = settMap.containsKey(ticketId) ? SettlementStatus.getDescription(settMap.get(ticketId)) : "未结算";
					row[9] = ticketVo.getMerchantUserName();
					// LogCvt.debug("ROW:" + ArrayUtils.toString(row));
					data.add(Arrays.asList(row));
				}
			}
			break;
		case presell_org: // 2-预售
			header = new String[] { "序号", "订单编号", "提货码", "商品名称", "数量(份)", "下单时间", "所属机构", "提货人姓名", "提货人手机号码", "券码状态" };
			data = new ArrayList<List<String>>();
			sheetName = "预售提货查询" + pageVo.getPageNumber();
			if (count > 0) {
				Map<String, String> ticketStatus = new HashMap<String, String>(); // 提货码状态
				ticketStatus.put("0", "待发送");
				ticketStatus.put("1", "未提货"); // 这个跟我们内部的枚举不一样
				ticketStatus.put("2", "已提货"); // 这个跟我们内部的枚举不一样
				ticketStatus.put("3", "已过期");
				ticketStatus.put("4", "已退款");
				ticketStatus.put("5", "退款中");
				ticketStatus.put("6", "退款失败");
				ticketStatus.put("7", "已过期退款");
				ticketStatus.put("8", "未发码退款");
				ticketStatus.put("9", "已过期退款中");
				Map<String, String> orgMap = new HashMap<String, String>(); // 缓存机构用的
				for (int i = 0; i < count; i++) {
					Ticket ticketVo = ticketList.get(i);
					String[] row = new String[header.length]; // 行数据
					row[0] = (i + 1) + "";
					// row[1] = ticketVo.getOrderId();
					row[1] = ticketVo.getSubOrderId(); // 这里要求显示子订单
					row[2] = StringUtils.overlay(ticketVo.getTicketId(), "****", 4, 8);
					row[3] = ticketVo.getProductName();
					row[4] = ticketVo.getQuantity() + "";
					row[5] = DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, ticketVo.getCreateTime());
					String key = ticketVo.getClientId() + "_" + ticketVo.getSorgCode();
					if (!orgMap.containsKey(key)) {
						Org org = commonLogic.queryByOrgCode(ticketVo.getClientId(), ticketVo.getSorgCode()); // 查询二级机构
						if (null != org)
							orgMap.put(key, org.getOrgName());
					}
					row[6] = orgMap.get(key) + "-->" + ticketVo.getOrgName();
					row[7] = ticketVo.getMemberName();
					row[8] = ticketVo.getMobile();
					// row[9] =
					// TicketStatus.getDescription(ticketVo.getStatus());
					row[9] = ticketStatus.get(ticketVo.getStatus());
					// LogCvt.debug("ROW:" + ArrayUtils.toString(row));
					data.add(Arrays.asList(row));
				}
			}
			break;

		default:
			LogCvt.info("暂不支持的类型:" + type);
			throw new FroadBusinessException(ResultCode.failed.getCode(), "暂不支持的类型!");
			// resultVo = new ResultVo(ResultCode.failed.getCode(), "暂不支持的类型!");
			// break;
		}

		excelWriter.write(Arrays.asList(header), data, sheetName, "提货码");

	}

	/**
	 * 
	 * @param requestVo
	 * @return
	 * @see com.froad.handler.TicketQueryHandler#findVerifyTicketListByPage(com.froad.thrift.vo.ticket.TicketListRequestVo)
	 */
	@Override
	public VerifyTicketListByPageResponseVo findVerifyTicketListByPage(TicketListRequestVo requestVo) {
		VerifyTicketListByPageResponseVo responseVo = null;
		List<TicketDetailVo> ticketDetailList = null;
		ResultVo resultVo = null;
		PageVo pageVo = null;
		MongoPage mongoPage = null;
		String source = null;
		List<Ticket> ticketList = null;
		DBObject queryObj = null;
		// OrgLevelEnum orgLevel = null;
		// CommonLogic orgLogic = null;
		// StringBuffer logInfo = null;
		// List<String> statusList = null;

		source = requestVo.getSource();
		responseVo = new VerifyTicketListByPageResponseVo();
		resultVo = new ResultVo();
		ticketDetailList = new ArrayList<TicketDetailVo>();
		pageVo = new PageVo();
		// logInfo = new StringBuffer();

		if (EmptyChecker.isEmpty(requestVo.getMerchantId())) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("商户id不能为空");
			responseVo.setResultVo(resultVo);
			return responseVo;
		}
		// if (EmptyChecker.isEmpty(requestVo.getStatus())){
		// resultVo.setResultCode(ResultCode.failed.getCode());
		// resultVo.setResultDesc("券状态不能为空");
		// responseVo.setResultVo(resultVo);
		// return responseVo;
		// }
		requestVo.setStatus(TicketStatus.consumed.getCode());

		if (EmptyChecker.isEmpty(requestVo.getStartDate()) && EmptyChecker.isEmpty(requestVo.getEndDate())) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("开始时间和结束时间不能为空");
			responseVo.setResultVo(resultVo);
			return responseVo;
		}

		// List<String> notStatus = new ArrayList<String>();
		// notStatus.add(TicketStatus.init.getCode()); // 待发送
		// notStatus.add(TicketStatus.init_refunded.getCode()); // 未发码退款

		try {

			queryObj = new BasicDBObject();

			// 按商户、门店(merchantId/outletId)查找
			if (!EmptyChecker.isEmpty(requestVo.getMerchantId())) {
				queryObj.put(FieldMapping.MERCHANT_ID.getMongoField(), requestVo.getMerchantId());
			}
			if (!EmptyChecker.isEmpty(requestVo.getOutletId())) {
				queryObj.put(FieldMapping.OUTLET_ID.getMongoField(), requestVo.getOutletId());
			}
			if (!EmptyChecker.isEmpty(requestVo.getStartDate()) && !EmptyChecker.isEmpty(requestVo.getEndDate())) {
				queryObj.put(FieldMapping.CONSUME_TIME.getMongoField(), new BasicDBObject(QueryOperators.GTE, Long.parseLong(requestVo.getStartDate())).append(QueryOperators.LTE, Long.parseLong(requestVo.getEndDate())));
			} else if (!EmptyChecker.isEmpty(requestVo.getStartDate())) {
				queryObj.put(FieldMapping.CONSUME_TIME.getMongoField(), new BasicDBObject(QueryOperators.GTE, Long.parseLong(requestVo.getStartDate())));
			} else if (!EmptyChecker.isEmpty(requestVo.getEndDate())) {
				queryObj.put(FieldMapping.CONSUME_TIME.getMongoField(), new BasicDBObject(QueryOperators.LTE, Long.parseLong(requestVo.getEndDate())));
			}
			queryObj.put(FieldMapping.STATUS.getMongoField(), requestVo.getStatus());
			// if (!EmptyChecker.isEmpty(requestVo.getStatus())){
			// queryObj.put(FieldMapping.STATUS.getMongoField(),
			// requestVo.getStatus());
			// } else {
			// // 不返回未发送券
			// queryObj.put(FieldMapping.STATUS.getMongoField(), new
			// BasicDBObject(QueryOperators.NIN , notStatus));
			// }
			if (!EmptyChecker.isEmpty(requestVo.getType())) {
				queryObj.put(FieldMapping.TYPE.getMongoField(), requestVo.getType());
			}
			if (!EmptyChecker.isEmpty(requestVo.getMerchantUserName())) {
				queryObj.put(FieldMapping.MERCHANT_USER_NAME.getMongoField(), requestVo.getMerchantUserName());
			}
			if (!EmptyChecker.isEmpty(requestVo.getTicketId())) {
				// 根据券号查找相关券
				ticketList = ticketSupport.getTicketByTicketId(requestVo.getTicketId());

				if (null == ticketList || ticketList.size() == 0) {
					resultVo.setResultCode(ResultCode.failed.getCode());
					resultVo.setResultDesc("无效提货码");
					responseVo.setResultVo(resultVo);
					return responseVo;
				}
				// 查找该商户下会员子订单券表
				queryObj.put(FieldMapping.MEMBER_CODE.getMongoField(), ticketList.get(0).getMemberCode());
				queryObj.put(FieldMapping.SUB_ORDER_ID.getMongoField(), ticketList.get(0).getSubOrderId());
			}

			mongoPage = buidMongoPage(requestVo);
			// mongoPage = ticketSupport.getTicketPageByDBObject(queryObj,
			// mongoPage);
			Sort sort = new Sort(FieldMapping.CONSUME_TIME.getMongoField(), OrderBy.DESC) // 根据消费时间降序排列
					.on(FieldMapping.CREATE_TIME.getMongoField(), OrderBy.DESC) // 如果消费时间一样再根据创建时间降序排列
			;
			mongoPage.setSort(sort);
			mongoPage = ticketSupport.getTicketPageByDBObjectNoRedis(queryObj, mongoPage);
			ticketDetailList = convertToVoList((List<Ticket>) mongoPage.getItems());
			pageVo.setPageCount(mongoPage.getPageCount());
			pageVo.setPageNumber(mongoPage.getPageNumber());
			pageVo.setPageSize(mongoPage.getPageSize());
			pageVo.setTotalCount(mongoPage.getTotalCount());
			pageVo.setHasNext(mongoPage.getHasNext());
			pageVo.setLastPageNumber(pageVo.getPageNumber());
			if (null != ticketDetailList && ticketDetailList.size() > 0) {
				pageVo.setFirstRecordTime(ticketDetailList.get(0).getCreateTime());
				pageVo.setLastRecordTime(ticketDetailList.get(ticketDetailList.size() - 1).getCreateTime());
			}
		} catch (Exception e) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("获取提货码列表失败");
			LogCvt.error(new StringBuffer("获取提货码列表失败：").append(requestVo.toString()).toString(), e);
		}

		resultVo.setResultCode(ResultCode.success.getCode());

		// 查询总金额
		int totalAmount = ticketSupport.getTicketTotalAmountDBObject(queryObj);

		// Double.toString(Arith.div(Arith.mul(totalAmount, 1), 1000));
		responseVo.setTotalAmount(Double.toString(Arith.div(totalAmount, 1000)));
		responseVo.setResultVo(resultVo);
		responseVo.setPageVo(pageVo);
		responseVo.setTicketDetailList(ticketDetailList);
		return responseVo;

	}

	/**
	 * 转换数据库对象成VO
	 * 
	 * @param ticketList
	 * @return
	 */
	private List<TicketDetailVo> convertToVoList(List<Ticket> ticketList) {
		List<TicketDetailVo> voList = null;
		TicketDetailVo detailVo = null;
		Ticket ticket = null;

		voList = new ArrayList<TicketDetailVo>();
		if (null != ticketList) {
			for (int i = 0; i < ticketList.size(); i++) {
				ticket = ticketList.get(i);
				detailVo = convertPoToVo(ticket);
				voList.add(detailVo);
			}
		}

		return voList;
	}

	/**
	 * 把PO转换成VO
	 * 
	 * @param ticket
	 * @return
	 */
	private TicketDetailVo convertPoToVo(Ticket ticket) {
		TicketDetailVo detailVo = null;

		detailVo = new TicketDetailVo();
		detailVo = new TicketDetailVo();
		detailVo.setTicketId(ticket.getTicketId());
		detailVo.setMerchantId(ticket.getMerchantId());
		detailVo.setMerchantName(ticket.getMerchantName());
		detailVo.setProductId(ticket.getProductId());
		detailVo.setProductName(ticket.getProductName());
		detailVo.setQuantity(ticket.getQuantity());
		detailVo.setStatus(ticket.getStatus());
		detailVo.setStatusMsg(TicketStatus.getDescription(ticket.getStatus()));
		detailVo.setMobile(ticket.getMobile());
		detailVo.setMemberCode(ticket.getMemberCode());
		detailVo.setMemberName(ticket.getMemberName());
		if (null != ticket.getExpireTime())
			detailVo.setExpireTime(ticket.getExpireTime());
		if (null != ticket.getCreateTime())
			detailVo.setCreateTime(ticket.getCreateTime());
		detailVo.setOrderId(ticket.getOrderId());
		detailVo.setImageUrl(ticket.getImage());
		detailVo.setType(ticket.getType());
		detailVo.setSubOrderId(ticket.getSubOrderId()); // 设置子订单id
		if (null != ticket.getConsumeTime()) {
			detailVo.setConsumeTime(String.valueOf(ticket.getConsumeTime()));
		}
		if (null != ticket.getOutletId()) {
			detailVo.setOutletId(ticket.getOutletId());
		}
		if (null != ticket.getOutletName()) {
			detailVo.setOutletName(ticket.getOutletName());
		}
		if (null != ticket.getRefundTime()) {
			detailVo.setRefundTime(String.valueOf(ticket.getRefundTime()));
		}
		if (null != ticket.getOrgName()) {
			detailVo.setOrgName(ticket.getOrgName());
		}
		if (null != ticket.getMerchantUserId()) {
			detailVo.setMerchantUserId(ticket.getMerchantUserId());
		}
		if (null != ticket.getMerchantUserName()) {
			detailVo.setMerchantUserName(ticket.getMerchantUserName());
		}
		if (null != ticket.getPrice()) {
			// 商品小计
//			detailVo.setPrice(Double.toString(Arith.div(Arith.mul(ticket.getPrice(), ticket.getQuantity()), 1000)));
			detailVo.setPrice(Arith.getDoubleDecimalString(Arith.div(Arith.mul(ticket.getPrice(), ticket.getQuantity()), 1000, 2)));
		}
		if (null != ticket.getSorgCode()) {
			detailVo.setSorgCode(ticket.getSorgCode());
		}
		if (null != ticket.getClientId()) {
			detailVo.setClientId(ticket.getClientId());
		}
		return detailVo;
	}

	@Override
	public TicketListResponseVo findRelatedTickets(TicketRelatedRequestVo requestVo) {
		TicketListResponseVo responseVo = null;
		List<TicketDetailVo> ticketDetailList = null;
		ResultVo resultVo = null;
		List<Ticket> ticketList = null;
		Ticket ticket = null;
		DBObject queryObj = null;

		responseVo = new TicketListResponseVo();
		resultVo = new ResultVo();

		try {
			// ticketList =
			// ticketSupport.getTicketByTicketId(requestVo.getTicketId());
			// bug 3386
			queryObj = new BasicDBObject();
			queryObj.put(FieldMapping.TICKET_ID.getMongoField(), requestVo.getTicketId());
			queryObj.put(FieldMapping.STATUS.getMongoField(), TicketStatus.sent.getCode()); // 如果输入的券码
																							// 不可用
																							// 则不让查出数据
			ticketList = ticketSupport.getListByDBObject(queryObj);
			// bug 3386
			if (null == ticketList || ticketList.size() == 0) {
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("没有找到符合条件的记录");
				responseVo.setResultVo(resultVo);
				return responseVo;
			} else {
				ticket = ticketList.get(0);
				queryObj = new BasicDBObject();

				if (requestVo.getResource().equals("1")) {
					// 银行客户端获取有效预售券信息
					queryObj.put(FieldMapping.TYPE.getMongoField(), SubOrderType.presell_org.getCode());
					// bug 2304
					// queryObj.put(FieldMapping.TICKET_ID.getMongoField(),
					// requestVo.getTicketId());
					// bug 2302
					queryObj.put(FieldMapping.ORG_CODE.getMongoField(), requestVo.getOrgCode());
				} else if (requestVo.getResource().equals("2")) {
					// 商户端获取有效团购券信息
					queryObj.put(FieldMapping.TYPE.getMongoField(), SubOrderType.group_merchant.getCode());
				} else if (requestVo.getResource().equals("3")) {
					// 只根据会员编码获取有效券信息
				} else {
					resultVo.setResultCode(ResultCode.failed.getCode());
					resultVo.setResultDesc("非指定查找来源.");
					responseVo.setResultVo(resultVo);
					return responseVo;
				}

				// bug 2304 start
				// queryObj.put(FieldMapping.MEMBER_CODE.getMongoField(),
				// ticket.getMemberCode());
				queryObj.put(FieldMapping.MEMBER_NAME.getMongoField(), ticket.getMemberName());
				queryObj.put(FieldMapping.SMS_NUMBER.getMongoField(), ticket.getMobile());
				// bug 2304 end
				queryObj.put(FieldMapping.STATUS.getMongoField(), TicketStatus.sent.getCode());
				ticketList = ticketSupport.getListByDBObject(queryObj);
			}

			// 转换成VO对象
			ticketDetailList = convertToVoList(ticketList);

			resultVo.setResultCode(ResultCode.success.getCode());
			responseVo.setResultVo(resultVo);
			responseVo.setTicketDetailList(ticketDetailList);
		} catch (Exception e) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("获取提货码失败");
			responseVo.setResultVo(resultVo);
			LogCvt.error(new StringBuffer("获取会员相关提货码失败：").append(requestVo.toString()).toString(), e);
		}

		return responseVo;
	}

	@Override
	public TicketProductResponseVo findTicketProduct(TicketDetailRequestVo requestVo) {
		TicketProductResponseVo responseVo = null;
		ResultVo resultVo = null;
		Ticket ticket = null;
		String option = null;
		String status = null;
		List<Ticket> ticketList = null;
		List<TicketProductVo> ticketProList = null;
		TicketProductVo ticketProduct = null;
		boolean isSent = false;
		Map<String, String> productMap = null;
		long startTime = 0l;// 提货开始时间

		option = requestVo.getOption();

		responseVo = new TicketProductResponseVo();
		resultVo = new ResultVo();
		ticketProList = new ArrayList<TicketProductVo>();

		try {
			if (!option.equals("1") && !option.equals("2") && !option.equals("3")) {
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("非法查找选项");
				responseVo.setTicketProduct(ticketProList);
				responseVo.setResultVo(resultVo);
				return responseVo;
			}

			if (option.equals("3")) {
				// 根据ticketId + status查找
				status = requestVo.getStatus();

				ticket = ticketSupport.getUniqueTicket(requestVo.getTicketId(), status);

				if (ticket != null) {
					ticketProduct = new TicketProductVo();
					ticketProduct.setProductId(ticket.getProductId());
					ticketProduct.setProductName(ticket.getProductName());
					ticketProduct.setQuantity(ticket.getQuantity());
					ticketProduct.setStatus(ticket.getStatus());
					ticketProList.add(ticketProduct);
					if (ticket.getStatus().equals(TicketStatus.sent.getCode())) {
						isSent = true;
					}

					productMap = getRedisProductMap(ticket);
					if (ticket.getType().equals(SubOrderType.group_merchant.getCode())) {
						// 团购取expire_start_time
						// if (null != productMap){
						// startTime =
						// Long.valueOf(productMap.get("expire_start_time"));
						// }
						startTime = ticket.getCreateTime();

					} else {
						// 预售取delivery_start_time
						if (null != productMap) {
							startTime = Long.valueOf(productMap.get("delivery_start_time"));
						}
					}

					resultVo.setResultCode(ResultCode.success.getCode());
				} else {
					resultVo.setResultCode(ResultCode.failed.getCode());
					resultVo.setResultDesc("Ticket not found");
				}
			} else if (option.equals("1")) {
				ticketList = ticketSupport.getTicketByTicketId(requestVo.getTicketId());

				if (null == ticketList || ticketList.size() == 0) {
					resultVo.setResultCode(ResultCode.failed.getCode());
					resultVo.setResultDesc("提货码不存在");
				} else {
					for (int i = 0; i < ticketList.size(); i++) {
						ticket = ticketList.get(i);
						if (ticket.getStatus().equals(TicketStatus.init.getCode()) || ticket.getStatus().equals(TicketStatus.init_refunded.getCode())) {
							continue;
						}
						if (ticket.getStatus().equals(TicketStatus.sent.getCode())) {
							isSent = true;
						}
						ticketProduct = new TicketProductVo();
						ticketProduct.setProductId(ticket.getProductId());
						ticketProduct.setProductName(ticket.getProductName());
						ticketProduct.setQuantity(ticket.getQuantity());
						ticketProduct.setStatus(ticket.getStatus());
						ticketProList.add(ticketProduct);

						productMap = getRedisProductMap(ticket);
						if (ticket.getType().equals(SubOrderType.group_merchant.getCode())) {
							// 团购取expire_start_time
							// if (null != productMap){
							// // 如多商品，取最早提货期
							// if (startTime == 0 ||
							// Long.valueOf(productMap.get("expire_start_time"))
							// < startTime){
							// startTime =
							// Long.valueOf(productMap.get("expire_start_time"));
							// }
							// }
							startTime = ticket.getCreateTime();

						} else {
							// 预售取delivery_start_time
							if (null != productMap) {
								// 如多商品，取最早提货期
								if (startTime == 0 || Long.valueOf(productMap.get("delivery_start_time")) < startTime) {
									startTime = Long.valueOf(productMap.get("delivery_start_time"));
								}
							}
						}
					}

					resultVo.setResultCode(ResultCode.success.getCode());
				}
			} else if (option.equals("2")) {
				// 根据merchantId + ticketId查找券
				ticketList = ticketSupport.getTicketByTicketId(requestVo.getTicketId());

				if (null == ticketList || ticketList.size() == 0) {
					resultVo.setResultCode(ResultCode.failed.getCode());
					resultVo.setResultDesc("提货码不存在");
				} else {
					for (int i = 0; i < ticketList.size(); i++) {
						ticket = ticketList.get(i);
						if (!ticket.getMerchantId().equals(requestVo.getMerchantId())) {
							continue;
						}

						if (ticket.getStatus().equals(TicketStatus.init.getCode())) {
							continue;
						}

						if (ticket.getStatus().equals(TicketStatus.sent.getCode())) {
							isSent = true;
						}
						ticketProduct = new TicketProductVo();
						ticketProduct.setProductId(ticket.getProductId());
						ticketProduct.setProductName(ticket.getProductName());
						ticketProduct.setQuantity(ticket.getQuantity());
						ticketProduct.setStatus(ticket.getStatus());
						ticketProList.add(ticketProduct);

						productMap = getRedisProductMap(ticket);
						if (ticket.getType().equals(SubOrderType.group_merchant.getCode())) {
							// 团购取expire_start_time
							// if (null != productMap){
							// // 如多商品，取最早提货期
							// if (startTime == 0 ||
							// Long.valueOf(productMap.get("expire_start_time"))
							// < startTime){
							// startTime =
							// Long.valueOf(productMap.get("expire_start_time"));
							// }
							// }
							startTime = ticket.getCreateTime();

						} else {
							// 预售取delivery_start_time
							if (null != productMap) {
								// 如多商品，取最早提货期
								if (startTime == 0 || Long.valueOf(productMap.get("delivery_start_time")) < startTime) {
									startTime = Long.valueOf(productMap.get("delivery_start_time"));
								}
							}
						}
					}
				}

			}

			responseVo.setTicketId(ticket.getTicketId());
			if (isSent) {
				responseVo.setStatus(TicketStatus.sent.getCode());
			} else {
				responseVo.setStatus(ticket.getStatus());
			}
			responseVo.setMemberCode(ticket.getMemberCode());
			responseVo.setMemberName(ticket.getMemberName());
			responseVo.setOrderId(ticket.getOrderId());
			if (ticket.getOrgName() != null) {
				responseVo.setOrgName(ticket.getOrgName());
			} else {
				responseVo.setOrgName("");
			}
			if (ticket.getConsumeTime() != null) {
				responseVo.setConsumeTime(String.valueOf(ticket.getConsumeTime()));
			} else {
				responseVo.setConsumeTime("");
			}
			if (ticket.getOutletId() != null) {
				responseVo.setOutletId(ticket.getOutletId());
			} else {
				responseVo.setOutletId("");
			}
			if (ticket.getOutletName() != null) {
				responseVo.setOutletName(ticket.getOutletName());
			} else {
				responseVo.setOutletName("");
			}
			responseVo.setExpireTime(ticket.getExpireTime());
			if (ticket.getRefundTime() != null) {
				responseVo.setRefundTime(String.valueOf(ticket.getRefundTime()));
			} else {
				responseVo.setRefundTime("");
			}
			responseVo.setCreateTime(startTime);
			responseVo.setImageUrl(ticket.getImage());
			if (ticket.getMerchantUserId() != null) {
				responseVo.setMerchantUserId(ticket.getMerchantUserId());
			}
			if (ticket.getMerchantUserName() != null) {
				responseVo.setMerchantUserName(ticket.getMerchantUserName());
			} else {
				responseVo.setMerchantUserName("");
			}
			if (ticket.getMerchantId() != null) {
				responseVo.setMerchantId(ticket.getMerchantId());
			} else {
				responseVo.setMerchantId("");
			}
			if (ticket.getMerchantName() != null) {
				responseVo.setMerchantName(ticket.getMerchantName());
			} else {
				responseVo.setMerchantName("");
			}
			responseVo.setType(ticket.getType());
			responseVo.setTicketProduct(ticketProList);
		} catch (Exception e) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("获取提货码失败");
			LogCvt.error(new StringBuffer("获取提货码商品列表失败：").append(requestVo.toString()).toString(), e);
		}

		responseVo.setResultVo(resultVo);

		return responseVo;
	}

	/**
	 * 缓存获取商品信息
	 * 
	 * @param ticket
	 * @return
	 */
	private Map<String, String> getRedisProductMap(Ticket ticket) {
		Map<String, String> redisMap = null;
		CommonLogic commonLogic = null;

		commonLogic = new CommonLogicImpl();

		redisMap = commonLogic.getProductRedis(ticket.getClientId(), ticket.getMerchantId(), ticket.getProductId());

		return redisMap;
	}

	/**
	 * 去除子订单重复提货码数据
	 * 
	 * @param voList
	 * @return
	 */
	private List<TicketDetailVo> filterDuplicateRecord(List<TicketDetailVo> voList) {
		List<TicketDetailVo> newList = null;
		// TicketDetailVo ticketDetail = null;
		// String status = null;
		// TicketDetailVo sentDetail = null;
		// TicketDetailVo consumedDetail = null;
		// TicketDetailVo refundingDetail = null;
		// TicketDetailVo refundedDetail = null;
		// TicketDetailVo expiredDetail = null;

		/**************** 按照券码去分组 ****************/
		Map<String, List<TicketDetailVo>> map = new HashMap<String, List<TicketDetailVo>>(); // key
																								// 为券id-----------value
																								// 券id有多少个券
		for (int i = 0; i < voList.size(); i++) {
			TicketDetailVo ticketDetail = voList.get(i);

			String key = ticketDetail.getTicketId();
			List<TicketDetailVo> value = map.get(key);
			if (null == value)
				value = new ArrayList<TicketDetailVo>();
			value.add(ticketDetail);
			map.put(key, value);
		}

		newList = new ArrayList<TicketDetailVo>();

		/**************** 按照券码的状态去重复(优先顺序:已发送>退款中>退款>已过期) ****************/
		for (Map.Entry<String, List<TicketDetailVo>> kv : map.entrySet()) {
			TicketDetailVo sentDetail = null;
			TicketDetailVo consumedDetail = null;
			TicketDetailVo refundingDetail = null;
			TicketDetailVo refundedDetail = null;
			TicketDetailVo expiredDetail = null;
			// String key = kv.getKey();
			List<TicketDetailVo> value = kv.getValue();
			for (TicketDetailVo ticketDetail : value) {
				String status = ticketDetail.getStatus();
				if (status.equals(TicketStatus.sent.getCode())) {
					sentDetail = ticketDetail;
					break;
				} else if (status.equals(TicketStatus.consumed.getCode())) {
					consumedDetail = ticketDetail;
				} else if (status.equals(TicketStatus.refunding.getCode()) || status.equals(TicketStatus.expired_refunding.getCode())) {
					refundingDetail = ticketDetail;
				} else if (status.equals(TicketStatus.refunded.getCode())) {
					refundedDetail = ticketDetail;
				} else if (status.equals(TicketStatus.expired.getCode()) || status.equals(TicketStatus.expired_refunded.getCode())) {
					expiredDetail = ticketDetail;
				}
			}
			if (null != sentDetail) {
				newList.add(sentDetail);
			} else if (null != consumedDetail) {
				newList.add(consumedDetail);
			} else if (null != refundingDetail) {
				newList.add(refundingDetail);
			} else if (null != refundedDetail) {
				newList.add(refundedDetail);
			} else if (null != expiredDetail) {
				newList.add(expiredDetail);
			}
		}

		// for (int i = 0; i < voList.size(); i++){
		// ticketDetail = voList.get(i);
		// status = ticketDetail.getStatus();
		// if (status.equals(TicketStatus.sent.getCode())){
		// sentDetail = ticketDetail;
		// break;
		// } else if (status.equals(TicketStatus.consumed.getCode())){
		// consumedDetail = ticketDetail;
		// } else if (status.equals(TicketStatus.refunding.getCode())){
		// refundingDetail = ticketDetail;
		// } else if (status.equals(TicketStatus.refunded.getCode())){
		// refundedDetail = ticketDetail;
		// } else if (status.equals(TicketStatus.expired.getCode()) &&
		// status.equals(TicketStatus.expired_refunded.getCode())){
		// expiredDetail = ticketDetail;
		// }
		// }

		// newList = new ArrayList<TicketDetailVo>();
		// if (null != sentDetail){
		// newList.add(sentDetail);
		// } else if (null != consumedDetail){
		// newList.add(consumedDetail);
		// } else if (null != refundingDetail){
		// newList.add(refundingDetail);
		// } else if (null != refundedDetail){
		// newList.add(refundedDetail);
		// } else if (null != expiredDetail){
		// newList.add(expiredDetail);
		// }

		return newList;
	}
}
