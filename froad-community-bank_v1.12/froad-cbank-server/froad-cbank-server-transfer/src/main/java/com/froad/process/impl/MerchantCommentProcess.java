//package com.froad.process.impl;
//
//import java.util.List;
//
//import com.froad.config.ProcessServiceConfig;
//import com.froad.db.mongo.MerchantDetailMongo;
//import com.froad.db.mongo.OutletCommentMongo;
//import com.froad.db.mongo.OutletDetailMongo;
//import com.froad.enums.ResultCode;
//import com.froad.logback.LogCvt;
//import com.froad.po.OutletComment;
//import com.froad.po.OutletCommentAddRes;
//import com.froad.po.Result;
//import com.froad.process.AbstractProcess;
//
//public class MerchantCommentProcess extends AbstractProcess{
//
//	public MerchantCommentProcess(String name, ProcessServiceConfig config) {
//		super(name, config);
//	}
//
//	@Override
//	public void process() {
//		com.froad.db.ahui.mappers.MerchantCommentMapper mapper  = ahSqlSession.getMapper(com.froad.db.ahui.mappers.MerchantCommentMapper.class);
//		
//		List<com.froad.fft.persistent.entity.MerchantComment> list = mapper.selectMerchantCommentAll();
//		OutletComment oct = null;
//		for (com.froad.fft.persistent.entity.MerchantComment obj : list) {
//			oct = new OutletComment();
//			
//			oct.setCreateTime(obj.getCreateTime().getTime());
//			if(obj.getStarLevel().equals(com.froad.fft.persistent.common.enums.StarLevel.one_star)){
//				oct.setStarLevel(1);
//			}
//			
//			oct.setCommentDescription(obj.getCommentDescription());
////			oct.setmerchanti
//			
//		}
//		
//	}
//	
//	
//
//	/**
//     * 增加 OutletComment
//     * @param outletComment
//     * @return OutletCommentAddRes
//     */
//	public void addOutletComment(OutletComment outletComment) {
//		
//		OutletCommentAddRes outletCommentAddRes = new OutletCommentAddRes();
//		
//		try{
//			// 检验
//			String addResult = checkAdd(outletComment);
//			if( addResult != null ){
//				LogCvt.info("添加OutletComment 校验不通过 "+addResult);
//				throw new Exception("添加OutletComment 校验不通过");
//			}
//			OutletCommentMongo outletCommentMongo = new OutletCommentMongo();
//			boolean addResultM = outletCommentMongo.addOutletComment(outletComment);
//			LogCvt.info("添加OutletComment:"+(addResultM?"成功":"失败"));
//			
//			if( addResultM ){
//				Result result = new Result(ResultCode.success);
//				outletCommentAddRes.setResult(result);
//				outletCommentAddRes.setId(outletComment.getId());
//			}else{
//				Result result = new Result(ResultCode.add_outlet_connent_mongo_lapse);
//				outletCommentAddRes.setResult(result);
//			}
//		} catch (Exception e) { 
//			LogCvt.error("添加OutletComment失败，原因:" + e.getMessage(), e); 
//			Result result = new Result(ResultCode.add_outlet_connent_app_lapse, e.getMessage());
//			outletCommentAddRes.setResult(result); // mongo add 失败 后续不执行
//		} 
//		
//		
//		try{
//			OutletDetailMongo outletDetailMongo = new OutletDetailMongo();
//			String outletId = outletComment.getOutletId();
//			int starLevel = outletComment.getStarLevel();
//			boolean updateR = outletDetailMongo.updateOutletStarLevelByOutletId(outletId, starLevel);
//			LogCvt.info("更新门店评价星级 : " + (updateR?"成功":"失败"));
//		} catch (Exception e) { 
//			LogCvt.error("更新门店评价星级失败，原因:" + e.getMessage(), e); 
//		}
//		
//		
//		try{
//			MerchantDetailMongo merchantDetailMongo = new MerchantDetailMongo();
//			String merchantId = outletComment.getMerchantId();
//			boolean updateR = merchantDetailMongo.updateMerchantStarLevelByMerchantId(merchantId);
//			LogCvt.info("更新商户评价星级 : " + (updateR?"成功":"失败"));
//		} catch (Exception e) { 
//			LogCvt.error("更新商户评价星级失败，原因:" + e.getMessage(), e); 
//		}
//		
//		
//	}
//	
//	
//	// 检验 - 新增之前
//	private String checkAdd(OutletComment outletComment){
//		if( outletComment.getClientId() == null || outletComment.getClientId().length() <= 0 ){
//			LogCvt.info(CLIENT_ID_NOT_NULL);
//			return CLIENT_ID_NOT_NULL;
//		}
//		if( outletComment.getMerchantId() == null || outletComment.getMerchantId().length() <= 0 ){
//			LogCvt.info(MERCHANT_ID_NOT_NULL);
//			return MERCHANT_ID_NOT_NULL;
//		}
//		if( outletComment.getOutletId() == null || outletComment.getOutletId().length() <= 0 ){
//			LogCvt.info(OUTLET_ID_NOT_NULL);
//			return OUTLET_ID_NOT_NULL;
//		}
//		if( outletComment.getMemberCode() == null || outletComment.getMemberCode().length() <= 0 ){
//			LogCvt.info(MEMBER_CODE_NOT_NULL);
//			return MEMBER_CODE_NOT_NULL;
//		}
//		if( outletComment.getStarLevel() < 1 || outletComment.getStarLevel() > 5){
//			LogCvt.info(STAR_LEVEL_NOT_NULL);
//			return STAR_LEVEL_NOT_NULL;
//		}
//		if( outletComment.getCommentDescription() == null || outletComment.getCommentDescription().length() <= 0 ){
//			LogCvt.info(COMMENT_DESC_NOT_NULL);
//			return COMMENT_DESC_NOT_NULL;
//		}
//		if( outletComment.getCreateTime() <= 0 ){
//			LogCvt.info(CREATE_TIME_NOT_NULL);
//			return CREATE_TIME_NOT_NULL;
//		}
//		OutletCommentMongo outletCommentMongo = new OutletCommentMongo();
//		try {
//			if( !outletCommentMongo.checkMemberCommentCount(outletComment) ){
//				LogCvt.info(ONE_ONE_ONE);
//				return ONE_ONE_ONE;
//			}
//		} catch (Exception e) {
//			LogCvt.error(ONE_ONE_ONE+" 出现异常 "+e.getMessage(), e);
//			return ONE_ONE_ONE+" 出现异常 "+e.getMessage();
//		}
//		return null;
//	}
//	
//	private static final String CLIENT_ID_NOT_NULL = "新增商户门店评价 :客户端编号clientId不能为空 ";
//	private static final String MERCHANT_ID_NOT_NULL = "新增商户门店评价 :商户编号merchantId不能为空 ";
//	private static final String OUTLET_ID_NOT_NULL = "新增商户门店评价 :门店编号outletId不能为空 ";
//	private static final String MEMBER_CODE_NOT_NULL = "新增商户门店评价 :评论人编号memberCode不能为空 ";
//	private static final String STAR_LEVEL_NOT_NULL = "新增商户门店评价 :评论级别starLevel必须取值1-5 ";
//	private static final String COMMENT_DESC_NOT_NULL = "新增商户门店评价 :评论描述commentDescription不能为空 ";
//	private static final String CREATE_TIME_NOT_NULL = "新增商户门店评价 :评论创建时间createTime不能为空 ";
//	private static final String ONE_ONE_ONE = "新增商户门店评价 :一个会员一天内只能针对一门店评论一次 ";
//
//}

