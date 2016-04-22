package com.froad.cbank.coremodule.module.normal.bank.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.util.DateUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.util.QueryResult;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankOrgVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.CommentVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.CommentVoReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.CommentVoRes;
import com.froad.cbank.coremodule.module.normal.bank.vo.MsgVo;
import com.froad.thrift.service.OutletCommentService;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.OutletCommentPageVoRes;
import com.froad.thrift.vo.OutletCommentVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ProductCommentFilterReq;
import com.froad.thrift.vo.ProductCommentPageVo;
import com.froad.thrift.vo.ProductCommentVo;
import com.froad.thrift.vo.RecommentNotEmptyVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.StarLevelFilterVo;


/**
 * 商品评价
 * @author yfy
 *
 */
@Service
public class CommentService {

	@Resource
	OutletCommentService.Iface outletCommentService;
	@Resource
	ProductService.Iface productService;
	@Resource
	private BankOrgService bankOrgService;
	
	/**
	 * 商户评价分页列表条件查询
	 * @throws ParseException 
	 */
	public QueryResult<CommentVoRes> findPageByMerchantConditions(CommentVoReq voReq) throws ParseException{
		
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(voReq.getPageNumber());
		pageVo.setPageSize(voReq.getPageSize());
		pageVo.setLastPageNumber(voReq.getLastPageNumber());
		pageVo.setFirstRecordTime(voReq.getFirstRecordTime());
		pageVo.setLastRecordTime(voReq.getLastRecordTime());
		
		if(StringUtils.isNotEmpty(voReq.getStartDate())){
			pageVo.setBegDate(DateUtil.DateFormat(voReq.getStartDate()));//开始日期
		}
		if(StringUtils.isNotEmpty(voReq.getEndDate())){
			pageVo.setEndDate(DateUtil.DateFormatEnd(voReq.getEndDate()));//结束日期
		}
		
		OutletCommentVo outletComment = new OutletCommentVo();
		outletComment.setClientId(voReq.getClientId());
		if(StringUtils.isNotEmpty(voReq.getOrgCode())){
			outletComment.setOrgCode(voReq.getOrgCode());//机构号
		}
		if(StringUtils.isNotEmpty(voReq.getMerchantName())){
			outletComment.setOutletName(voReq.getMerchantName());//商户名称
		}
		if(StringUtils.isNotEmpty(voReq.getStartLevel())){//评价星级
			StarLevelFilterVo strLevel = new StarLevelFilterVo();
			strLevel.setStarLevel(Integer.valueOf(voReq.getStartLevel()));
			outletComment.setStarLevelFilter(strLevel);
		}
		if(StringUtils.isNotEmpty(voReq.getAnswerState())){
			RecommentNotEmptyVo recommentVo = new RecommentNotEmptyVo();
			if(voReq.getAnswerState().equals("true")){
				recommentVo.setNotEmpty(true);//已回复
			}else{
				recommentVo.setNotEmpty(false);//未回复
			}
			outletComment.setRecommentNotEmpty(recommentVo);//回复状态
		}
		
		QueryResult<CommentVoRes> queryVo = new QueryResult<CommentVoRes>();
		List<CommentVoRes> commentList = new ArrayList<CommentVoRes>();
		List<OutletCommentVo> outletCommentList = new ArrayList<OutletCommentVo>();
		OutletCommentPageVoRes outletCommentPage = null;
		try {
			List<String> orgCodeList = new ArrayList<String>();
			outletCommentPage = outletCommentService.getOutletCommentPageByOrgCode(pageVo,outletComment);
			outletCommentList = outletCommentPage.getOutletCommentVoList();
			if(outletCommentList != null && outletCommentList.size() > 0){
				for(OutletCommentVo outletCommentVo : outletCommentList){
					CommentVoRes commentVo = new CommentVoRes();
					commentVo.setCommentId(outletCommentVo.getId());//评价ID
					if(StringUtil.isNotEmpty(outletCommentVo.getLorgCode()) && !outletCommentVo.getLorgCode().equals("0")){
						commentVo.setOrgCode(outletCommentVo.getLorgCode());
						orgCodeList.add(outletCommentVo.getLorgCode());
					}else if(StringUtil.isNotEmpty(outletCommentVo.getTorgCode()) && !outletCommentVo.getTorgCode().equals("0")){
						commentVo.setOrgCode(outletCommentVo.getTorgCode());
						orgCodeList.add(outletCommentVo.getTorgCode());
					}else if(StringUtil.isNotEmpty(outletCommentVo.getSorgCode()) && !outletCommentVo.getSorgCode().equals("0")){
						commentVo.setOrgCode(outletCommentVo.getSorgCode());
						orgCodeList.add(outletCommentVo.getSorgCode());
					}else if(StringUtil.isNotEmpty(outletCommentVo.getForgCode()) && !outletCommentVo.getForgCode().equals("0")){
						commentVo.setOrgCode(outletCommentVo.getForgCode());
						orgCodeList.add(outletCommentVo.getForgCode());
					}else{
						commentVo.setOrgCode("");
					}
					if(StringUtil.isNotEmpty(outletCommentVo.getMerchantName())){
						commentVo.setMerchantName(outletCommentVo.getMerchantName());//商户名称
					}else{
						commentVo.setMerchantName("");//商户名称
					}
					if(StringUtil.isNotEmpty(outletCommentVo.getOutletName())){
						commentVo.setOutletName(outletCommentVo.getOutletName());//门店名称
					}else{
						commentVo.setOutletName("");//门店名称
					}
					commentVo.setComment(outletCommentVo.getCommentDescription());//评价内容
					commentVo.setCommentDate(DateUtil.formatDate(
							new Date(outletCommentVo.getCreateTime()), 
							DateUtil.DATE_TIME_FORMAT_01));//评论时间
					if(outletCommentVo.getStarLevel() > 0){
						commentVo.setStartLevel(String.valueOf(outletCommentVo.getStarLevel()));//评论星级
					}
					if(StringUtils.isNotEmpty(outletCommentVo.getRecomment())){
						commentVo.setAnswerState("已回复");//已回复
					}else{
						commentVo.setAnswerState("未回复");//未回复
					}
					commentList.add(commentVo);
				}
				//获取机构名称
				if(orgCodeList != null && orgCodeList.size() > 0){
					List<BankOrgVo> orgList = bankOrgService.getByOrgCode(voReq.getClientId(),orgCodeList);
					for(CommentVoRes voRes : commentList){
						for(BankOrgVo orgVo : orgList){
							if(StringUtil.isNotEmpty(voRes.getOrgCode())){
								if(voRes.getOrgCode().equals(orgVo.getOrgCode())){
									voRes.setOrgName(orgVo.getOrgName());
								}
							}
						}
					}
				}
			}
			if(outletCommentPage.getPage() != null){
				queryVo.setTotalCount(outletCommentPage.getPage().getTotalCount());
				queryVo.setPageCount(outletCommentPage.getPage().getPageCount());
				queryVo.setPageNumber(outletCommentPage.getPage().getPageNumber());
				queryVo.setLastPageNumber(outletCommentPage.getPage().getLastPageNumber());
				queryVo.setFirstRecordTime(outletCommentPage.getPage().getFirstRecordTime());
				queryVo.setLastRecordTime(outletCommentPage.getPage().getLastRecordTime());
			}
		} catch (TException e) {
			LogCvt.info("商户评价分页列表条件查询"+e.getMessage(), e);
		}
		queryVo.setResult(commentList);
		return queryVo;
	}
	
	/**
	 * 商品评价分页列表条件查询
	 */
	public QueryResult<CommentVoRes> findPageByConditions(String clientId,String filter,
			int pageNumber,int pageSize,int lastPageNumber,Long firstRecordTime,Long lastRecordTime){
		
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(pageNumber);
		pageVo.setPageSize(pageSize);
		pageVo.setLastPageNumber(lastPageNumber);
		pageVo.setFirstRecordTime(firstRecordTime);
		pageVo.setLastRecordTime(lastRecordTime);
		
		ProductCommentFilterReq productCommentFilterReq = new ProductCommentFilterReq ();
		productCommentFilterReq.setClientId(clientId);
		productCommentFilterReq.setFilter(filter);
		
		QueryResult<CommentVoRes> queryVo = new QueryResult<CommentVoRes>();
		List<CommentVoRes> commentList = new ArrayList<CommentVoRes>();
		ProductCommentPageVo productCommentPage = null;
		try {
			List<String> orgCodeList = new ArrayList<String>();
			productCommentPage = productService.getProductCommentList(productCommentFilterReq, pageVo);
			List<ProductCommentVo> productCommentList = productCommentPage.getProductCommentVoList();
			if(productCommentList != null && productCommentList.size() > 0){
				for(ProductCommentVo productCommentVo : productCommentList){
					CommentVoRes commentVo = new CommentVoRes();
					commentVo.setCommentId(productCommentVo.getCommentId());//评价ID
					if(StringUtil.isNotEmpty(productCommentVo.getOrgCode())){
						commentVo.setOrgCode(productCommentVo.getOrgCode());
						orgCodeList.add(productCommentVo.getOrgCode());
					}else{
						commentVo.setOrgCode("");
					}
					commentVo.setProductName(productCommentVo.getProductName());//商品名称
					if(StringUtil.isNotEmpty(productCommentVo.getMerchantName())){
						commentVo.setMerchantName(productCommentVo.getMerchantName());//商户名称
					}else{
						commentVo.setMerchantName("");//商户名称
					}
					commentVo.setComment(productCommentVo.getCommentDescription());//评价内容
					commentVo.setType(productCommentVo.getType());//商品评价类型
					commentVo.setOrderType(productCommentVo.getOrderType());//交易类型
					commentVo.setCommentDate(DateUtil.formatDate(
							new Date(productCommentVo.getCreateTime()), 
							DateUtil.DATE_TIME_FORMAT_01));//评论时间
					if(productCommentVo.getStarLevel() > 0){
						commentVo.setStartLevel(String.valueOf(productCommentVo.getStarLevel()));//评论星级
					}
					if(StringUtils.isNotEmpty(productCommentVo.getRecomment())){//1未回复，2回复，-1全部
						commentVo.setAnswerState("已回复");//回复状态
					}else{
						commentVo.setAnswerState("未回复");//回复状态
					}
					commentList.add(commentVo);
				}
				//获取机构名称
				if(orgCodeList != null && orgCodeList.size() > 0){
					List<BankOrgVo> orgList = bankOrgService.getByOrgCode(clientId,orgCodeList);
					for(CommentVoRes voRes : commentList){
						for(BankOrgVo orgVo : orgList){
							if(StringUtil.isNotEmpty(voRes.getOrgCode())){
								if(voRes.getOrgCode().equals(orgVo.getOrgCode())){
									voRes.setOrgName(orgVo.getOrgName());
								}
							}
						}
					}
				}
			}
			if(productCommentPage.getPage() != null){
				queryVo.setTotalCount(productCommentPage.getPage().getTotalCount());
				queryVo.setPageCount(productCommentPage.getPage().getPageCount());
				queryVo.setPageNumber(productCommentPage.getPage().getPageNumber());
				queryVo.setLastPageNumber(productCommentPage.getPage().getLastPageNumber());
				queryVo.setFirstRecordTime(productCommentPage.getPage().getFirstRecordTime());
				queryVo.setLastRecordTime(productCommentPage.getPage().getLastRecordTime());
				
			}
		} catch (TException e) {
			LogCvt.info("商品评价分页列表条件查询"+e.getMessage(), e);
		}
		queryVo.setResult(commentList);
		return queryVo;
	}
	/**
	 * 商品评价详情
	 */
	public CommentVo productDetail(String clientId,String merchantId, String commentId){
		CommentVo comment = new CommentVo();
		ProductCommentVo productCommentVo = new ProductCommentVo();
		productCommentVo.setClientId(clientId);
		if(StringUtils.isNotEmpty(merchantId)){
			productCommentVo.setMerchantId(merchantId);
		}
		productCommentVo.setCommentId(commentId);
		try {
			ProductCommentVo commentVo = productService.getProductCommentDetail(productCommentVo);
			LogCvt.info("productDetail商品评价详情后端返回:"+JSON.toJSONString(commentVo));
			if(commentVo.getCommentId() != null){
				comment.setCommentId(commentVo.getCommentId());//评论ID
				if(!"".equals(commentVo.getCreateTime()))
				comment.setCommentDate(DateUtil.formatDate(
						new Date(commentVo.getCreateTime()), 
						DateUtil.DATE_TIME_FORMAT_01));//评论时间
				comment.setProductName(commentVo.getProductName());//商品名称
				comment.setStartLevel(String.valueOf(commentVo.getStarLevel()));//评价星级
				comment.setMerchantId(commentVo.getMerchantId());//商户ID
				comment.setMerchantName(commentVo.getMerchantName());//商户名称
				if(StringUtil.isNotEmpty(commentVo.getOrgCode())){
					comment.setOrgName(bankOrgService.getOrgNameByOrgCode(clientId, 
							commentVo.getOrgCode()).getOrgName());//所属网点
				}
				comment.setAnswer(commentVo.getRecomment());//回复
				comment.setComment(commentVo.getCommentDescription());//评价内容
				comment.setCommentPerson(commentVo.getMemberName());//评价人
			}
			
		} catch (TException e) {
			LogCvt.info("商品评价详情"+e.getMessage(), e);
		}
		return comment;
	}
	
	/**
	 * 商户评价详情
	 */
	public CommentVo merchantDetail(String clientId,String commentId){
		CommentVo comment = new CommentVo();
		try {
			OutletCommentVo outletCommentVo = outletCommentService.getOutletCommentById(commentId);
			LogCvt.info("merchantDetail商户评价详情后端返回:"+JSON.toJSONString(outletCommentVo));
			if(outletCommentVo.getId() != null){
				comment.setCommentId(outletCommentVo.getId());//评论ID
				if(!"".equals(outletCommentVo.getCreateTime()))
				comment.setCommentDate(DateUtil.formatDate(
						new Date(outletCommentVo.getCreateTime()), 
						DateUtil.DATE_TIME_FORMAT_01));//评论时间
				comment.setMerchantId(outletCommentVo.getMerchantId());//商户ID
				comment.setMerchantName(outletCommentVo.getMerchantName());//商户名称
				if(StringUtil.isNotEmpty(outletCommentVo.getLorgCode()) && !outletCommentVo.getLorgCode().equals("0")){
					comment.setOrgName(bankOrgService.getOrgNameByOrgCode(clientId, 
							outletCommentVo.getLorgCode()).getOrgName());
				}else if(StringUtil.isNotEmpty(outletCommentVo.getTorgCode()) && !outletCommentVo.getTorgCode().equals("0")){
					comment.setOrgName(bankOrgService.getOrgNameByOrgCode(clientId, 
							outletCommentVo.getTorgCode()).getOrgName());
				}else if(StringUtil.isNotEmpty(outletCommentVo.getSorgCode()) && !outletCommentVo.getSorgCode().equals("0")){
					comment.setOrgName(bankOrgService.getOrgNameByOrgCode(clientId, 
							outletCommentVo.getSorgCode()).getOrgName());
				}else if(StringUtil.isNotEmpty(outletCommentVo.getForgCode()) && !outletCommentVo.getForgCode().equals("0")){
					comment.setOrgName(bankOrgService.getOrgNameByOrgCode(clientId, 
							outletCommentVo.getForgCode()).getOrgName());
				}else{
					comment.setOrgName("");
				}
				comment.setOutletId(outletCommentVo.getOutletId());//门店ID
				comment.setOutletName(outletCommentVo.getOutletName());//门店名称
				comment.setComment(outletCommentVo.getCommentDescription());//评价内容
				comment.setCommentDate(DateUtil.formatDate(
						new Date(outletCommentVo.getCreateTime()), 
						DateUtil.DATE_TIME_FORMAT_01));//评论时间
				comment.setStartLevel(String.valueOf(outletCommentVo.getStarLevel()));//评论星级
				if(StringUtils.isNotEmpty(outletCommentVo.getRecomment())){
					comment.setAnswerState(true);//已回复
				}else{
					comment.setAnswerState(false);//未回复
				}
				comment.setAnswer(outletCommentVo.getRecomment());//回复
				comment.setComment(outletCommentVo.getCommentDescription());//评价内容
				comment.setCommentPerson(outletCommentVo.getMemberName());//评价人
			}
			
		} catch (TException e) {
			LogCvt.info("商户评价详情"+e.getMessage(), e);
		}
		return comment;
	}
	/**
	 * 评价回复
	 */
	public MsgVo commentAnswer(CommentVoReq voReq){
		MsgVo msgVo = new MsgVo();
		try {
			ProductCommentVo productCommentVo = new ProductCommentVo();
			productCommentVo.setClientId(voReq.getClientId());
			productCommentVo.setMerchantId(voReq.getMerchantId());
			productCommentVo.setMerchantUserName(voReq.getLoginName());
			productCommentVo.setCommentId(voReq.getCommentId());
			productCommentVo.setRecomment(voReq.getAnswer());
			ResultVo resultVo = productService.replayProductComment(productCommentVo);
			LogCvt.info("commentAnswer商品评价回复后端返回:"+JSON.toJSONString(resultVo));
			if(resultVo.getResultCode().equals(EnumTypes.success.getCode())){
				msgVo.setResult(true);
				msgVo.setMsg(resultVo.getResultDesc());
			}else{
				msgVo.setResult(false);
				msgVo.setMsg(resultVo.getResultDesc());
			}
			
		} catch (Exception e) {
			LogCvt.info("评价回复"+e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("评价回复异常");
		}
		return msgVo;
	}
	
	/**
	 * 删除
	 * @param commentId
	 * @return
	 */
	public MsgVo productCommentDelete(String clientId,String merchantId, String commentId){
		MsgVo msgVo = new MsgVo();
		try {
			ProductCommentVo productCommentVo = new ProductCommentVo();
			productCommentVo.setClientId(clientId);
			productCommentVo.setMerchantId(merchantId);
			productCommentVo.setCommentId(commentId);
			ResultVo resultVo = productService.deleteProductComment(productCommentVo);
			LogCvt.info("productCommentDelete商品评价删除后端返回:"+JSON.toJSONString(resultVo));
			if(resultVo.getResultCode().equals(EnumTypes.success.getCode())){
				msgVo.setResult(true);
				msgVo.setMsg(resultVo.getResultDesc());
			}else{
				msgVo.setResult(false);
				msgVo.setMsg(resultVo.getResultDesc());
			}
			
		} catch (Exception e) {
			LogCvt.info("商品评价删除"+e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("商品评价删除异常");
		}
		return msgVo;
	}
	
	/**
	 * 删除
	 * @param operatorId
	 * @return
	 */
	public MsgVo merchantCommentDelete(OriginVo vo, String commentId){
		MsgVo msgVo = new MsgVo();
		try {
			
			ResultVo resultVo = outletCommentService.deleteOutletComment(vo, commentId);
			LogCvt.info("merchantCommentDelete商户评价删除后端返回:"+JSON.toJSONString(resultVo));
			if(resultVo.getResultCode().equals(EnumTypes.success.getCode())){
				msgVo.setResult(true);
				msgVo.setMsg(resultVo.getResultDesc());
			}else{
				msgVo.setResult(false);
				msgVo.setMsg(resultVo.getResultDesc());
			}
			
		} catch (Exception e) {
			LogCvt.info("商户评价删除"+e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("商户评价删除异常");
		}
		return msgVo;
	}
}
