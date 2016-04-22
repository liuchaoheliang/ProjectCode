package com.froad.process.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.persistent.entity.Merchant;
import com.froad.cbank.persistent.entity.MerchantOutletComment;
import com.froad.common.mongo.OutletDetailMongo;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.chonggou.entity.MemberInfo;
import com.froad.db.chonggou.entity.OutletComment;
import com.froad.db.chonggou.entity.OutletDetail;
import com.froad.db.chonggou.entity.PointInfo;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.db.chongqing.mappers.MerchantMapper;
import com.froad.db.chongqing.mappers.MerchantOutletCommentMapper;
import com.froad.enums.ModuleID;
import com.froad.enums.TransferTypeEnum;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Constans;
import com.froad.util.MongoTableName;
import com.froad.util.SimpleID;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class OutletCommentProcess extends AbstractProcess {

	private static SimpleID simpleID = new SimpleID(ModuleID.outletcomment);

	private static final String CB_OUTLET_COMMENT = "cb_outlet_comment";
	private static final String COMMAND_SET = "$set";

	public static final String MONGO_KEY_ID = "_id";
	public static final String MONGO_KEY_MERCHANT_ID = "merchant_id";
	public static final String MONGO_KEY_OUTLET_ID = "outlet_id";
	public static final String MONGO_KEY_CREATE_TIME = "create_time";
	public static final String MONGO_KEY_OUTLET_NAME = "outlet_name";
	public static final String MONGO_KEY_ORDER_VALUE = "order_value";
	public static final String MONGO_KEY_MEMBER_INFO = "member_info";
	public static final String MONGO_KEY_MEMBER_CODE = "member_code";
	public static final String MONGO_KEY_MEMBER_NAME = "member_name";
	public static final String MONGO_KEY_POINT_INFO = "point_info";
	public static final String MONGO_KEY_STAR_LEVEL = "star_level";
	public static final String MONGO_KEY_COMMENT_DESCRIPTION = "comment_description";
	public static final String MONGO_KEY_RECOMMENT = "recomment";
	public static final String MONGO_KEY_CLIENT_ID = "clientId";
	public static final String MONGO_KEY_FORG_CODE = "forg_code";
	public static final String MONGO_KEY_SORG_CODE = "sorg_code";
	public static final String MONGO_KEY_TORG_CODE = "torg_code";
	public static final String MONGO_KEY_LORG_CODE = "lorg_code";
	public static final String MONGO_KEY_MERCHANT_NAME = "merchant_name";
	public static final String MONGO_KEY_RECOMMENT_TIME = "recomment_time";
	public static final String MONGO_KEY_MERCHANT_USER_NAME = "merchant_user_name";
	public static final String MONGO_KEY_OUTLET_PHONE = "outlet_phone";
	public static final String MONGO_KEY_OUTLET_IMAGE = "outlet_image";
	public static final String MONGO_KEY_MEMBER_HEAD = "member_head";

	private OutletDetailMongo outletDetailMongo = null;

	private MerchantOutletCommentMapper mCommentMapper = null;
	private TransferMapper transferMapper = null;
	private MerchantMapper merchantMapper = null;

	// 门店
	private final Map<String, String> outletMap = new HashMap<String, String>();

	// 商户
	private final Map<String, String> merchantMap = new HashMap<String, String>();

	public OutletCommentProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}

	@Override
	public void before() {
		outletDetailMongo = new OutletDetailMongo(mongo);
		mCommentMapper = cqSqlSession
				.getMapper(MerchantOutletCommentMapper.class);
		transferMapper = sqlSession.getMapper(TransferMapper.class);
		merchantMapper = cqSqlSession.getMapper(MerchantMapper.class);

		List<Transfer> listOutlet = transferMapper
				.queryGroupList(TransferTypeEnum.outlet_id);
		for (Transfer t : listOutlet) {
			outletMap.put(t.getOldId(), t.getNewId());
		}

		List<Transfer> listMerchant = transferMapper
				.queryGroupList(TransferTypeEnum.merchant_id);
		for (Transfer t : listMerchant) {
			merchantMap.put(t.getOldId(), t.getNewId());
		}

		deleteOutletComment(Constans.clientId);
	}

	@Override
	public void process() {

		List<MerchantOutletComment> list = mCommentMapper.selectByCondition(new MerchantOutletComment());
		OutletComment oComment = null;
		MemberInfo mInfo = null;
		PointInfo pInfo = null;
		Merchant m = null;
		for (MerchantOutletComment obj : list) {
			oComment = new OutletComment();
			mInfo = new MemberInfo();
			pInfo = new PointInfo();

			oComment.setMerchantId(merchantMap.get(obj.getMerchantId() + ""));
			oComment.setOutletId(outletMap.get(obj.getMerchantOutletId() + ""));
			oComment.setCreateTime(obj.getCreateTime().getTime());

			mInfo.setMemberCode(String.valueOf(obj.getMemberCode()));
			mInfo.setMemberName(obj.getMemberName());
			mInfo.setMemberHead("");

			oComment.setMemberInfo(mInfo);

			pInfo.setStarLevel(obj.getStarLevel());
			pInfo.setCommentDescription(obj.getCommentDescription());

			oComment.setPointInfo(pInfo);
			oComment.setRecomment("");
			oComment.setRecommentTime(0l);
			oComment.setMerchantUserName("");
			oComment.setClientId(Constans.clientId);
			oComment.setOrderValue(obj.getOrderValue());

			oComment.setForgCode("000000");
			m = merchantMapper.selectById(obj.getMerchantId());

			if (StringUtils.isNotBlank(m.getTravelAgency())) {
				oComment.setSorgCode(Constans.filterOrg(m.getTravelAgency()));
			} else {
				oComment.setSorgCode("000000");
			}

			if (StringUtils.isNotBlank(m.getLatticePoint())) {
				oComment.setTorgCode(Constans.filterOrg(m.getLatticePoint()));
			} else {
				oComment.setTorgCode("000000");
			}
			oComment.setLorgCode("");

			addOutletComment(oComment);
		}

	}

	public boolean deleteOutletComment(String clientId) {

		DBObject where = new BasicDBObject();
		where.put("client_id", clientId);

		LogCvt.info(CB_OUTLET_COMMENT + "删除clientId为" + clientId + "的数据");
		int result = mongo.remove(where, CB_OUTLET_COMMENT);
		return result != -1;
	}

	public void addOutletComment(OutletComment outletComment) {

		init(outletComment);

		boolean addResultM = addOutletComments(outletComment);
		LogCvt.info("添加OutletComment:" + (addResultM ? "成功" : "失败"));

		String outletId = outletComment.getOutletId();
		int starLevel = outletComment.getStarLevel();
		boolean updateR = outletDetailMongo.updateOutletStarLevelByOutletId(
				outletId, starLevel);
		LogCvt.info("更新门店评价星级 : " + (updateR ? "成功" : "失败"));

		String merchantId = outletComment.getMerchantId();
		boolean updateR2 = updateMerchantStarLevelByMerchantId(merchantId);
		LogCvt.info("更新商户评价星级 : " + (updateR2 ? "成功" : "失败"));
	}

	private void init(OutletComment outletComment) {

		String outletId = outletComment.getOutletId();

		OutletDetail outletDetail = outletDetailMongo.findOutletDetailByoutletId(outletId);
		if (outletDetail != null) {
			outletComment.setOutletName(outletDetail.getOutletName());
			outletComment.setMerchantId(outletDetail.getMerchantId());
			outletComment.setMerchantName(outletDetail.getMerchantName());
			outletComment.setOutletImage(outletDetail.getDefaultImage());
			outletComment.setOutletPhone(outletDetail.getPhone());
		}
	}

	public boolean addOutletComments(OutletComment outletComment) {

		DBObject dbObject = changeToDBObjectOfAdd(outletComment);
		LogCvt.debug("添加 OutletComment:" + JSON.toJSONString(dbObject, true));
		int addCount = mongo.add(dbObject, CB_OUTLET_COMMENT);
		LogCvt.debug("添加 OutletComment,mongo返回:" + addCount);
		return addCount > -1;

	}

	// 得到门店评论主键
	private static String getOutletCommentId() {
		return simpleID.nextId();
	}

	private static DBObject changeToDBObjectOfAdd(OutletComment outletComment) {

		outletComment.setId(getOutletCommentId());

		DBObject dbA = new BasicDBObject();

		dbA.put(MONGO_KEY_ID, outletComment.getId());
		dbA.put(MONGO_KEY_MERCHANT_ID, outletComment.getMerchantId());
		dbA.put(MONGO_KEY_OUTLET_ID, outletComment.getOutletId());
		dbA.put(MONGO_KEY_CREATE_TIME, outletComment.getCreateTime());
		dbA.put(MONGO_KEY_OUTLET_NAME, outletComment.getOutletName());
		dbA.put(MONGO_KEY_ORDER_VALUE, outletComment.getOrderValue());
		DBObject memberDBO = new BasicDBObject();
		memberDBO.put(MONGO_KEY_MEMBER_CODE, outletComment.getMemberCode());
		memberDBO.put(MONGO_KEY_MEMBER_NAME, outletComment.getMemberName());
		memberDBO.put(MONGO_KEY_MEMBER_HEAD, outletComment.getMemberHead());
		dbA.put(MONGO_KEY_MEMBER_INFO, memberDBO);
		DBObject pointDBO = new BasicDBObject();
		pointDBO.put(MONGO_KEY_STAR_LEVEL, outletComment.getStarLevel());
		pointDBO.put(MONGO_KEY_COMMENT_DESCRIPTION,
				outletComment.getCommentDescription());
		dbA.put(MONGO_KEY_POINT_INFO, pointDBO);
		dbA.put(MONGO_KEY_RECOMMENT, "");
		dbA.put(MONGO_KEY_RECOMMENT_TIME, 0);
		dbA.put(MONGO_KEY_MERCHANT_USER_NAME, "");
		dbA.put(MONGO_KEY_CLIENT_ID, outletComment.getClientId());
		dbA.put(MONGO_KEY_FORG_CODE, outletComment.getForgCode());
		dbA.put(MONGO_KEY_SORG_CODE, outletComment.getSorgCode());
		dbA.put(MONGO_KEY_TORG_CODE, outletComment.getTorgCode());
		dbA.put(MONGO_KEY_LORG_CODE, outletComment.getLorgCode());
		dbA.put(MONGO_KEY_MERCHANT_NAME, outletComment.getMerchantName());
		dbA.put(MONGO_KEY_OUTLET_PHONE, outletComment.getOutletPhone());
		dbA.put(MONGO_KEY_OUTLET_IMAGE, outletComment.getOutletImage());

		return dbA;
	}

	public boolean updateMerchantStarLevelByMerchantId(String merchantId) {

		boolean result = false;

		if (merchantId != null && !"".equals(merchantId)) {

			// 查询所属门店
			List<OutletDetail> outletDetailList = outletDetailMongo
					.findOutletDetailListByMerchantId(merchantId);

			if (outletDetailList != null && outletDetailList.size() > 0) {

				int levelSum = 0, levelCount = 0, levelTemp = 0;
				// 循环所属门店 - 统计所有星级总和
				for (OutletDetail outletDetail : outletDetailList) {
					levelTemp = outletDetail.getStarLevel();
					if (levelTemp > 0) {
						levelSum += outletDetail.getStarLevel();
						levelCount++; // 统计有几个门店有评级数据
					}

				}
				// 小数点一位四省五入取整
				double average = Double.valueOf(levelSum) / levelCount;
				int level = new BigDecimal(average).setScale(0,
						BigDecimal.ROUND_HALF_UP).intValue();

				DBObject where = new BasicDBObject();
				where.put(MONGO_KEY_ID, merchantId);

				DBObject value = new BasicDBObject();
				value.put(MONGO_KEY_STAR_LEVEL, level);

				result = mongo.update(value, where,
						MongoTableName.CB_MERCHANT_DETAIL, COMMAND_SET) > -1 ? true
						: false;
			}
		}

		return result;
	}

}
