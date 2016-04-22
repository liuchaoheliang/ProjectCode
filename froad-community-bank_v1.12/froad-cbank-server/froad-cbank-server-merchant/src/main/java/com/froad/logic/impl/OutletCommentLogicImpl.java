package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.froad.Constants;
import com.froad.db.mongo.MerchantDetailMongo;
import com.froad.db.mongo.OutletCommentMongo;
import com.froad.db.mongo.OutletDetailMongo;
import com.froad.db.mysql.bean.Page;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.OutletCommentLogic;
import com.froad.po.OutletComment;
import com.froad.po.OutletCommentAddRes;
import com.froad.po.OutletCommentLevelAmount;
import com.froad.po.Result;
import com.froad.po.mongo.OutletDetail;
import com.froad.thrift.client.ThriftClientProxyFactory;
import com.froad.thrift.service.SenseWordsService;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.ThriftConfig;

/**
 * 
 * <p>@Title: OutletCommentLogicImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月25日
 */
public class OutletCommentLogicImpl implements OutletCommentLogic {
	
	/**
     * 增加 OutletComment
     * @param outletComment
     * @return OutletCommentAddRes
     */
	@Override
	public OutletCommentAddRes addOutletComment(OutletComment outletComment) {
		// TODO Auto-generated method stub
		
		OutletCommentAddRes outletCommentAddRes = new OutletCommentAddRes();
		
		try{
			// 检验
			String addResult = checkAdd(outletComment);
			init(outletComment);
			if( addResult != null ){
				LogCvt.info("添加OutletComment 校验不通过 "+addResult);
				Result result = new Result(ResultCode.add_outlet_connent_param_empty, addResult);
				outletCommentAddRes.setResult(result);
				return outletCommentAddRes;
			}
			OutletCommentMongo outletCommentMongo = new OutletCommentMongo();
			boolean addResultM = outletCommentMongo.addOutletComment(outletComment);
			LogCvt.info("添加OutletComment:"+(addResultM?"成功":"失败"));
			if( addResultM ){
				Result result = new Result(ResultCode.success);
				outletCommentAddRes.setResult(result);
				outletCommentAddRes.setId(outletComment.getId());
			}else{
				Result result = new Result(ResultCode.add_outlet_connent_mongo_lapse);
				outletCommentAddRes.setResult(result);
			}
		} catch (Exception e) { 
			LogCvt.error("添加OutletComment失败，原因:" + e.getMessage(), e); 
			Result result = new Result(ResultCode.add_outlet_connent_app_lapse);
			outletCommentAddRes.setResult(result); // mongo add 失败 后续不执行
		} 
		try{
			OutletDetailMongo outletDetailMongo = new OutletDetailMongo();
			String outletId = outletComment.getOutletId();
			int starLevel = outletComment.getStarLevel();
			boolean updateR = outletDetailMongo.updateOutletStarLevelByOutletId(outletId, starLevel);
			LogCvt.info("更新门店评价星级 : " + (updateR?"成功":"失败"));
		} catch (Exception e) { 
			LogCvt.error("更新门店评价星级失败，原因:" + e.getMessage(), e); 
		}
		try{
			MerchantDetailMongo merchantDetailMongo = new MerchantDetailMongo();
			String merchantId = outletComment.getMerchantId();
			boolean updateR = merchantDetailMongo.updateMerchantStarLevelByMerchantId(merchantId);
			LogCvt.info("更新商户评价星级 : " + (updateR?"成功":"失败"));
		} catch (Exception e) { 
			LogCvt.error("更新商户评价星级失败，原因:" + e.getMessage(), e); 
		}
		return outletCommentAddRes;
	}
	
	private static final String CLIENT_ID_NOT_NULL = "客户端编号不能为空 ";
//	private static final String MERCHANT_ID_NOT_NULL = "新增商户门店评价 :商户编号merchantId不能为空 ";
	private static final String OUTLET_ID_NOT_NULL = "门店编号不能为空 ";
	private static final String MEMBER_CODE_NOT_NULL = "评论人编号不能为空 ";
	private static final String STAR_LEVEL_NOT_NULL = "评论级别必须取值1-5 ";
	private static final String COMMENT_DESC_NOT_NULL = "评论描述不能为空 ";
	private static final String COMMENT_DESC_EXIST_SENSE = "您的评论含有敏感词，请修改后再提交！ ";
//	private static final String CREATE_TIME_NOT_NULL = "新增商户门店评价 :评论创建时间createTime不能为空 ";
	private static final String ONE_ONE_ONE = "一个会员一天内只能针对一门店评论一次 ";
	// 检验 - 新增之前
	private String checkAdd(OutletComment outletComment){
		if( outletComment.getClientId() == null || outletComment.getClientId().length() <= 0 ){
			LogCvt.info(CLIENT_ID_NOT_NULL);
			return CLIENT_ID_NOT_NULL;
		}
		if( outletComment.getOutletId() == null || outletComment.getOutletId().length() <= 0 ){
			LogCvt.info(OUTLET_ID_NOT_NULL);
			return OUTLET_ID_NOT_NULL;
		}
		if( outletComment.getMemberCode() == null || outletComment.getMemberCode().length() <= 0 ){
			LogCvt.info(MEMBER_CODE_NOT_NULL);
			return MEMBER_CODE_NOT_NULL;
		}
		if( outletComment.getStarLevel() < 1 || outletComment.getStarLevel() > 5){
			LogCvt.info(STAR_LEVEL_NOT_NULL);
			return STAR_LEVEL_NOT_NULL;
		}
		String desc = outletComment.getCommentDescription();
		if( desc == null || desc.length() <= 0 ){
			LogCvt.info(COMMENT_DESC_NOT_NULL);
			return COMMENT_DESC_NOT_NULL;
		}
//		if( outletComment.getMerchantId() == null || outletComment.getMerchantId().length() <= 0 ){
//			LogCvt.info(MERCHANT_ID_NOT_NULL);
//			return MERCHANT_ID_NOT_NULL;
//		}
		try{
			SenseWordsService.Iface swService = 
				(SenseWordsService.Iface)ThriftClientProxyFactory.createIface(SenseWordsService.class.getName(), SenseWordsService.class.getSimpleName(), ThriftConfig.SUPPORT_SERVICE_HOST, ThriftConfig.SUPPORT_SERVICE_PORT);
			ResultVo isSense = swService.isContaintSensitiveWord(desc);
			
			if( isSense.getResultCode().equals(ResultCode.failed.getCode())){
				LogCvt.info(COMMENT_DESC_EXIST_SENSE + "敏感词:" +isSense.getResultDesc());
				return COMMENT_DESC_EXIST_SENSE + "敏感词:" +isSense.getResultDesc();
			}
			
		}catch(Exception e){
			LogCvt.error("调用外围模块判断评论描述是否有敏感词 出现异常 "+e.getMessage(), e);
		}
		outletComment.setCreateTime(System.currentTimeMillis());
//		if( outletComment.getCreateTime() <= 0 ){
//			LogCvt.info(CREATE_TIME_NOT_NULL);
//			return CREATE_TIME_NOT_NULL;
//		}
		
		//如果commentTyep=评论来源类型 为非面对面评论,就需要进行checkMemberCommentCount(:0-商户评论  默认为普通评论,1-面对面评论,2-其它).
		if(outletComment.getCommentType() != Constants.COMMENT_TYPE_FACETOFACE){
			OutletCommentMongo outletCommentMongo = new OutletCommentMongo();
			try {
				if( !outletCommentMongo.checkMemberCommentCount(outletComment) ){
					LogCvt.info(ONE_ONE_ONE);
					return ONE_ONE_ONE;
				}
			} catch (Exception e) {
				LogCvt.error(ONE_ONE_ONE+" 出现异常 "+e.getMessage(), e);
				return ONE_ONE_ONE+" 出现异常 "+e.getMessage();
			}			
		}	
		return null;
	}
	// 初始化评论内容 根据门店id查询门店详情
	// 1-门店名称outletName 2-商户IDmerchantId 3-商户名称merchantName 4-默认小图outletImage 5-门店电话outletPhone
	private void init(OutletComment outletComment){
		
		String outletId = outletComment.getOutletId();
		OutletDetailMongo outletDetailMongo = new OutletDetailMongo(); 
		try {
			OutletDetail outletDetail = outletDetailMongo.findOutletDetailByoutletId(outletId);
			if( outletDetail != null ){
				outletComment.setOutletName(outletDetail.getOutletName());
				outletComment.setMerchantId(outletDetail.getMerchantId());
				outletComment.setMerchantName(outletDetail.getMerchantName());
				outletComment.setOutletImage(outletDetail.getDefaultImage());
				outletComment.setOutletPhone(outletDetail.getPhone());
			}
		} catch (Exception e) {
			LogCvt.error("根据门店id查询门店详情 出现异常 "+e.getMessage(), e);
		}
	}
	
	/**
     * 删除 OutletComment
     * @param outletComment
     * @return Result
     */
	@Override
	public Result deleteOutletComment(String id) {
		// TODO Auto-generated method stub
		
		Result result = null;
		
		try{
			// 检验
			if( id == null || id.length() <= 0 ){
				LogCvt.info("删除商户门店评价 :评论id不能为空 ");
				return result;
			}
			OutletCommentMongo outletCommentMongo = new OutletCommentMongo();
			boolean delResult = outletCommentMongo.deleteOutletComment(id);
			LogCvt.info("删除OutletComment:"+(delResult?"成功":"失败"));
			if( delResult ){
				result = new Result(ResultCode.success);
			}else{
				result = new Result(ResultCode.del_outlet_connent_mongo_lapse);
			}
		} catch (Exception e) { 
			LogCvt.error("删除OutletComment失败，原因:" + e.getMessage(), e); 
			result = new Result(ResultCode.del_outlet_connent_app_lapse);
		} 
		return result;
	}

	/**
     * 修改 OutletComment
     * @param outletComment
     * @return Result
     */
	@Override
	public Result updateOutletComment(OutletComment outletComment) {
		// TODO Auto-generated method stub
		
		Result result = null;
		try{
			// 检验
			if( outletComment.getId() == null || outletComment.getId().length() <= 0 ){
				LogCvt.info("修改商户门店评价 :评论id不能为空 ");
				return result;
			}
			OutletCommentMongo outletCommentMongo = new OutletCommentMongo();
			boolean updResult = outletCommentMongo.updateOutletComment(outletComment);
			LogCvt.info("更新OutletComment:"+(updResult?"成功":"失败"));
			if( updResult ){
				result = new Result(ResultCode.success);
			}else{
				result = new Result(ResultCode.upd_outlet_connent_mongo_lapse);
			}
		} catch (Exception e) { 
			LogCvt.error("修改OutletComment失败，原因:" + e.getMessage(), e); 
			result = new Result(ResultCode.upd_outlet_connent_app_lapse);
		} 
		return result;
	}

	/**
     * 查询 OutletComment
     * @param String
     * @return OutletComment
     */
	@Override
	public OutletComment findOutletCommentById(String id) {
		// TODO Auto-generated method stub
		
		OutletComment outletComment = null;
		try{
			OutletCommentMongo outletCommentMongo = new OutletCommentMongo();
			outletComment = outletCommentMongo.findOutletCommentById(id);
			LogCvt.info("查询OutletComment结果:"+JSON.toJSONString(outletComment, true));
		} catch (Exception e) { 
			LogCvt.error("查询OutletComment失败，原因:" + e.getMessage(), e); 
		} 
		return outletComment;
	}
	
	/**
     * 查询 OutletComment
     * @param outletComment
     * @return List<OutletComment>    结果集合 
     */
	@Override
	public List<OutletComment> findOutletComment(OutletComment outletComment) {
		// TODO Auto-generated method stub
		
		List<OutletComment> result = new ArrayList<OutletComment>();

		try{
			OutletCommentMongo outletCommentMongo = new OutletCommentMongo();
			result = outletCommentMongo.findOutletCommentList(outletComment);
			LogCvt.info("查询 OutletCommentList结果条数:"+result.size());
		} catch (Exception e) { 
			LogCvt.error("查询OutletComment失败，原因:" + e.getMessage(), e); 
		} 
		return result;
	}

	/**
     * 分页查询 OutletComment
     * @param page
     * @param outletComment
     * @return Page<OutletComment>    结果集合 
     */
	@Override
	public Page<OutletComment> findOutletCommentByPage(Page<OutletComment> page,
			OutletComment outletComment) {
		// TODO Auto-generated method stub
		
		try{
			OutletCommentMongo outletCommentMongo = new OutletCommentMongo();
			page = outletCommentMongo.findOutletCommentByPage(page, outletComment);
//			LogCvt.info("分页查询OutletComment结果:"+JSON.toJSONString(page, true));
		} catch (Exception e) { 
			LogCvt.error("分页查询OutletComment失败，原因:" + e.getMessage(), e); 
		} 
		return page;
	}
	
	/**
     * 分页查询 OutletComment
     * @param OutletComment(+orgCode)
     * @return Page
     */
	@Override
	public Page<OutletComment> findOutletCommentPageByOrgCode(
			Page<OutletComment> page, OutletComment outletComment) {
		// TODO Auto-generated method stub

		try{
			OutletCommentMongo outletCommentMongo = new OutletCommentMongo();
			page = outletCommentMongo.findOutletCommentPageByOrgCode(page, outletComment);
//			LogCvt.info("分页查询OutletComment结果:"+JSON.toJSONString(page, true));
		} catch (Exception e) { 
			LogCvt.error("分页查询OutletComment失败，原因:" + e.getMessage(), e); 
		} 
		return page;
	}
	
	/**
     * 增加 评论回复
     * @param OutletComment
     * @return Result
     * 
     * @param outletComment
     */
	@Override
	public Result addOutletCommentOfRecomment(OutletComment outletComment) {
		// TODO Auto-generated method stub
		
		Result result = null;

		try{
			// 检验
			String rr = checkAddRecomment(outletComment);
			if( rr != null ){
				LogCvt.info("添加评论回复 校验不通过 "+rr);
				result = new Result(ResultCode.failed, rr);
				return result;
			}
			OutletCommentMongo outletCommentMongo = new OutletCommentMongo();
			boolean recommentResult = outletCommentMongo.addOutletCommentOfRecomment(outletComment);
			LogCvt.info("增加回复:"+(recommentResult?"成功":"失败"));
			if( recommentResult ){
				result = new Result(ResultCode.success);
			}else{
				result = new Result(ResultCode.add_outlet_connent_recomment_mongo_lapse);
			}
		} catch (Exception e) { 
			LogCvt.error("增加回复失败，原因:" + e.getMessage(), e); 
			result = new Result(ResultCode.add_outlet_connent_recomment_app_lapse);
		} 
		return result;
	}
	
	private static final String RECOMMENT_NOT_NULL = "新增商户门店评价回复 :回复内容recomment不能为空 ";
	private static final String REPLY_NOT_NULL = "新增商户门店评价回复 :回复人merchantUserName不能为空 ";
	private static final String RECOMMENT_EXIST_SENSE = "您的评论含有敏感词，请修改后再提交！ ";
	// 检验 - 回复之前
	private String checkAddRecomment(OutletComment outletComment){
		String recomment = outletComment.getRecomment();
		if( recomment == null || recomment.length() <= 0 ){
			LogCvt.info(RECOMMENT_NOT_NULL);
			return RECOMMENT_NOT_NULL;
		}
		if( outletComment.getMerchantUserName() == null || outletComment.getMerchantUserName().length() <= 0 ){
			LogCvt.info(REPLY_NOT_NULL);
			return REPLY_NOT_NULL;
		}
		try{
			SenseWordsService.Iface swService = 
				(SenseWordsService.Iface)ThriftClientProxyFactory.createIface(SenseWordsService.class.getName(), SenseWordsService.class.getSimpleName(), ThriftConfig.SUPPORT_SERVICE_HOST, ThriftConfig.SUPPORT_SERVICE_PORT);
			ResultVo isSense = swService.isContaintSensitiveWord(recomment);
			
			if( isSense.getResultCode().equals(ResultCode.failed.getCode())){
				LogCvt.info(RECOMMENT_EXIST_SENSE + "敏感词:" +isSense.getResultDesc());
				return RECOMMENT_EXIST_SENSE + "敏感词:" +isSense.getResultDesc();
			}
			
		}catch(Exception e){
			LogCvt.error("调用外围模块判断评论描述是否有敏感词 出现异常 "+e.getMessage(), e);
		}
		return null;
	}
	
	/**
     * 门店评论数量查询
     * @param outletComment - clientId merchantId outletId
     * @return int
     * 
     * @param outletComment
     */
	@Override
	public int getOutletCommentSum(OutletComment outletComment) {
		// TODO Auto-generated method stub
		
		int result = 0;
		
		try{
			OutletCommentMongo outletCommentMongo = new OutletCommentMongo();
			result = outletCommentMongo.getOutletCommentSum(outletComment);
			LogCvt.info("门店评论数量:"+result);
		} catch (Exception e) { 
			result = 0;
			LogCvt.error("门店评论数量查询失败，原因:" + e.getMessage(), e); 
		} 
		
		return result;
	}

	/**
     * 门店评论级别数量查询
     * @param outletComment - merchantId outletId
     * @return OutletCommentLevelAmount
     * 
     * @param outletComment
     */
	@Override
	public OutletCommentLevelAmount getOutletCommentLevelAmount(
			OutletComment outletComment) {
		// TODO Auto-generated method stub
		
		OutletCommentLevelAmount result = new OutletCommentLevelAmount();
		
		try{
			
			OutletCommentMongo outletCommentMongo = new OutletCommentMongo();
			result = outletCommentMongo.getOutletCommentLevelAmount(outletComment);
			LogCvt.info("门店评论级别数量查询结果:"+JSON.toJSONString(result));
			
		} catch (Exception e) { 
			result = null;
			LogCvt.error("门店评论数量查询失败，原因:" + e.getMessage(), e); 
		} 
		
		return result;
	}
	
	/**
     * 商户评论级别数量查询
     * @param merchantId
     * @return list<OutletCommentLevelAmount>
     * 
     * @param merchantId
     */
	@Override
	public List<OutletCommentLevelAmount> getMerchantCommentLevelAmount(
			String merchantId) {
		// TODO Auto-generated method stub
		
		List<OutletCommentLevelAmount> result = new ArrayList<OutletCommentLevelAmount>();
		
		try{
			
			OutletCommentMongo outletCommentMongo = new OutletCommentMongo();
			result = outletCommentMongo.getMerchantCommentLevelAmount(merchantId);
			LogCvt.info("商户评论级别数量查询结果:"+JSON.toJSONString(result));
			
		} catch (Exception e) { 
			result = null;
			LogCvt.error("商户评论数量查询失败，原因:" + e.getMessage(), e); 
		} 
		
		return result;
		
	}

	/**
     * 是否存某会员在某天针对某门店的评论
     * @return bool
     * 
     * @param memberCode
     * @param outletId
     * @param time
     */
	@Override
	public boolean isExistComment(String memberCode, String outletId, long time) {
		// TODO Auto-generated method stub
		OutletCommentMongo outletCommentMongo = new OutletCommentMongo();
		try {
			OutletComment outletComment = new OutletComment();
			outletComment.setMemberCode(memberCode);
			outletComment.setOutletId(outletId);
			outletComment.setCreateTime(time);
			if( !outletCommentMongo.checkMemberCommentCount(outletComment) ){
				return true;
			}
		} catch (Exception e) {
			LogCvt.error("判断是否存某会员在某天针对某门店的评论 出现异常 "+e.getMessage(), e);
		}
		return false;
	}
	
	/**
	 * 是否存在某会员已经对门店进行了面对面的评论.
	 */
	@Override
	public boolean isExitsFaceToFaceComment(String memberCode, String outletId, String orderId) {
		OutletCommentMongo outletCommentMongo = new OutletCommentMongo();
		try {
			OutletComment outletComment = new OutletComment();
			outletComment.setMemberCode(memberCode);
			outletComment.setOutletId(outletId);
			outletComment.setOrderId(orderId);
			if( !outletCommentMongo.checkFaceToFaceCommentCount(outletComment) ){
				return true;
			}
		} catch (Exception e) {
			LogCvt.error("判断是否存某会员针对某门店的面对面评论 出现异常 "+e.getMessage(), e);
		}
		return false;
	}	
}