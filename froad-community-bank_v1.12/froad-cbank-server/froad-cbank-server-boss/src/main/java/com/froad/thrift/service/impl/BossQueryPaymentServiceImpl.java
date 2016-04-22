package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;

import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.MongoPage;
import com.froad.logback.LogCvt;
import com.froad.logic.BossQueryPaymentLogic;
import com.froad.logic.impl.BossQueryPaymentLogicImpl;
import com.froad.po.PaymentMongoEntity;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.BossQueryPaymentService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.payment.BossPaymentExceptionType;
import com.froad.thrift.vo.payment.BossPaymentQueryExceptionVo;
import com.froad.thrift.vo.payment.BossPaymentQueryPageVo;
import com.froad.thrift.vo.payment.BossPaymentQueryVo;
import com.froad.thrift.vo.payment.BossQueryPaymentListRes;
import com.froad.thrift.vo.payment.BossQueryPaymentVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;
import com.froad.util.MongoTableName;
import com.froad.util.payment.BaseSubassembly;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

/**
 * Boss支付记录查询
 * 
 * @author wangzhangxu
 * @date 2015年8月5日 上午11:46:28
 * @version v1.0
 */
public class BossQueryPaymentServiceImpl extends BizMonitorBaseService implements BossQueryPaymentService.Iface {
	private final String COLLECTION_NAME = MongoTableName.CB_PAYMENT;
	private MongoManager mongoManager = new MongoManager();

	BossQueryPaymentLogic logic = new BossQueryPaymentLogicImpl();
	
	private final int PAY_CASH_FAILD_AUTO_REFUND_POINT_FAILED = 10;
	private final int AUTO_PRESENT_FROAD_POINT = 11;
	private final int REFUND_CASH_SUCCESS_REFUND_POINT_FAILED = 12;
	private final int REFUND_PRESENT_FROAD_POINT = 13;
	private final int REFUNT_POINT_FAILED = 14;// 用户退款积分失败
	private final int ACCEPT_FAILED = 15;// 受理失败
	private final int RESPOND_FAILED = 16;// 响应失败

	public BossQueryPaymentServiceImpl() {
	}

	public BossQueryPaymentServiceImpl(String name, String version) {
		super(name, version);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BossPaymentQueryPageVo queryPaymentOfException(BossPaymentQueryExceptionVo queryVo) throws TException {
		LogCvt.debug("收到boss查询异常支付流水请求queryVo:" + queryVo);

		PageVo pageVo = queryVo.getPageVo();
		DBObject queryObj = new BasicDBObject();
		MongoPage mongoPage = (MongoPage) BeanUtil.copyProperties(MongoPage.class, pageVo);
		queryObj.put("is_dispose_exception", "0"); // 未处理过异常

		BossPaymentExceptionType exceptype = queryVo.getExceptionType(); // 获取调用方指定的异常类型
		BossPaymentQueryPageVo pageQueryVo = new BossPaymentQueryPageVo();
		if (Checker.isEmpty(exceptype)) {
			pageQueryVo.setResultVo(new ResultVo("9999", "未指明最小异常类型"));
			return pageQueryVo;
		}
		switch (exceptype.getValue()) {
		case PAY_CASH_FAILD_AUTO_REFUND_POINT_FAILED:
			queryObj.put("payment_reason", "2"); // 组合支付失败时产生的自动退款流水/按照业务逻辑只有积分支付才会涉及自动退款
			// queryObj.put("payment_type_detials", 0);//积分类型的流水
			// queryObj.put("is-enable", "true");//判断数据是否有效
			break;
		case AUTO_PRESENT_FROAD_POINT:
			queryObj.put("payment_reason", "3"); // 自动赠送积分产生的流水
			queryObj.put("payment_status", new BasicDBObject(QueryOperators.NE, "4"));
			break;
		case REFUND_CASH_SUCCESS_REFUND_POINT_FAILED:
			queryObj.put("payment_reason", "1"); // 用户组合退款产生的积分退款流水
			// queryObj.put("payment_type_detials", 0);//积分类型的流水
			// queryObj.put("is-enable", "true");//判断数据是否有效
			break;
		case REFUND_PRESENT_FROAD_POINT:
			queryObj.put("payment_reason", "4"); // 自动赠送积分退款流水（扣除已赠送的积分）
			break;
		case REFUNT_POINT_FAILED:
			queryObj.put("payment_reason", "1");// 用户退款积分的流水
			break;
		case ACCEPT_FAILED:
			queryObj.put("payment_reason", "0");// 结算受理失败流水
			queryObj.put("payment_status", new BasicDBObject(QueryOperators.NE, "4"));// 最终状态失败
			break;
		case RESPOND_FAILED:
			queryObj.put("payment_reason", "0");// 结算响应失败流水
			break;
		default:
			break;
		}
		queryObj.put("payment_status", "5");// 最终状态失败
		// queryObj.put("payment_type_detials", 0);//积分类型的流水
		// queryObj.put("is-enable", "true");//判断数据是否有效
		if (pageVo.getBegDate() > 0 && pageVo.getEndDate() > 0) {
			queryObj.put("create_time", new BasicDBObject(QueryOperators.GTE, pageVo.getBegDate()).append(QueryOperators.LTE, pageVo.getEndDate()));
		} else if (pageVo.getBegDate() > 0) {
			queryObj.put("create_time", new BasicDBObject(QueryOperators.GTE, pageVo.getBegDate()));
		} else if (pageVo.getEndDate() > 0) {
			queryObj.put("create_time", new BasicDBObject(QueryOperators.LTE, pageVo.getEndDate()));
		}

		if (!StringUtils.isEmpty(queryVo.getClientId())) {
			queryObj.put("client_id", queryVo.getClientId());
		}

		mongoPage = mongoManager.findByPageWithRedis(mongoPage, queryObj, COLLECTION_NAME, PaymentMongoEntity.class);

		List<PaymentMongoEntity> datas = mongoPage.getItems() == null ? null : (List<PaymentMongoEntity>) mongoPage.getItems();
		List<BossPaymentQueryVo> list = new ArrayList<BossPaymentQueryVo>();
		if (datas != null) {
			for (PaymentMongoEntity entity : datas) {
				list.add(BaseSubassembly.loadyPaymentQueryVo(entity));
			}
		}

		pageQueryVo.setPaymentQueryVos(list);
		pageQueryVo.setResultVo(new ResultVo("0000", "操作成功"));
		pageVo.setPageCount(mongoPage.getPageCount());
		pageVo.setTotalCount(mongoPage.getTotalCount());
		pageVo.setLastPageNumber(pageVo.getPageNumber());
		pageVo.setFirstRecordTime(mongoPage.getFirstRecordTime());
		pageVo.setLastRecordTime(mongoPage.getLastRecordTime());
		pageQueryVo.setPageVo(pageVo);

		return pageQueryVo;
	}

	public BossQueryPaymentListRes getPaymentList(BossQueryPaymentVo req) throws TException {
		return logic.getPaymentList(req, "0");
	}

	public ExportResultRes exportPaymentList(BossQueryPaymentVo req) throws TException {
		return logic.exportPaymentList(req);
	}
	
}
