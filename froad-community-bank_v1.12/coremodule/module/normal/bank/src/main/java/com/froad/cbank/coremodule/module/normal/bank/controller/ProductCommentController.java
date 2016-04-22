package com.froad.cbank.coremodule.module.normal.bank.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.common.valid.CheckPermission;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.bank.service.BankOrgService;
import com.froad.cbank.coremodule.module.normal.bank.service.CommentService;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.DateUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.util.QueryResult;
import com.froad.cbank.coremodule.module.normal.bank.vo.CommentVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.CommentVoReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.CommentVoRes;
import com.froad.cbank.coremodule.module.normal.bank.vo.MsgVo;

/**
 * 商品评价管理
 * 
 * @author yfy
 *
 */
@Controller
@RequestMapping(value = "/productComment")
public class ProductCommentController extends BasicSpringController{

	@Resource
	private CommentService commentService; 
	
	@Resource 
	private BankOrgService bankOrgService;
	
	/**
	 * @Title: 商品评价列表条件查询
	 * @author yfy
	 * @date 2015-3-23 下午13:20:32
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"product_comment_menu","product_comment_lt_bind"})
	@RequestMapping(value = "/lt",method = RequestMethod.POST)
    public void commentList(ModelMap model,HttpServletRequest req,@RequestBody CommentVoReq voReq) {
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			// 条件过滤
			Map<String,String> filter=new HashMap<String, String>();
			filter.put("orgCode", voReq.getOrgCode());// 机构号
			filter.put("isReply", voReq.getAnswerState());// 回复状态
			filter.put("starLevel", voReq.getStartLevel());// 评价星级
			if(StringUtil.isNotEmpty(voReq.getStartDate())){
				filter.put("pointStartTime",
						DateUtil.DateFormat(voReq.getStartDate()) + "");// 评价开始时间
			}
			if(StringUtil.isNotEmpty(voReq.getEndDate())){
				filter.put("pointEndTime",
						DateUtil.DateFormatEnd(voReq.getEndDate()) + "");// 评价结束时间
			}
			filter.put("merchantName", voReq.getMerchantName());// 商户名称
			filter.put("productName", voReq.getProductName());// 商品名称
			filter.put("type", voReq.getType());// 商品类型
			
			QueryResult<CommentVoRes> queryVo = commentService.findPageByConditions(
					voReq.getClientId(),JSON.toJSONString(filter), 
					voReq.getPageNumber(), voReq.getPageSize(),voReq.getLastPageNumber(),
					voReq.getFirstRecordTime(),voReq.getLastRecordTime());
			model.put("productCommentList", queryVo.getResult());
			model.put("totalCount", queryVo.getTotalCount());// 总记录数
			model.put("pageCount", queryVo.getPageCount());// 总页数
			model.put("pageNumber", queryVo.getPageNumber());// 总页
			model.put("lastPageNumber", queryVo.getLastPageNumber());
			model.put("firstRecordTime", queryVo.getFirstRecordTime());
			model.put("lastRecordTime", queryVo.getLastRecordTime());
			
			model.put("message", "商品评价列表条件查询成功");
			
		} catch (Exception e) {
			LogCvt.info("商品评价列表条件查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
		
	}
	
	/**
	 * @Title: 商品评价详情查询
	 * @author yfy
	 * @date 2015-3-23 下午13:43:10
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"product_comment_dl","product_comment_dl_bind"})
	@RequestMapping(value = "/dl",method = RequestMethod.GET)
    public void commentDetail(ModelMap model,HttpServletRequest req,String commentId,String merchantId) {
		
		try {
			String clientId = req.getAttribute(Constants.CLIENT_ID)+"";
			if(StringUtil.isNotEmpty(commentId)){
				CommentVo comment = commentService.productDetail(clientId,merchantId,commentId);
				if(comment != null){	
					model.put("productComment", comment);
				}
			}else{
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "评论ID不能为空");
			}
		} catch (Exception e) {
			LogCvt.info("商品评价详情查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
		
	}
	
	/**
	 * @Title: 商品评价回复
	 * @author yfy
	 * @date 2015-3-23 下午13:43:10
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "/ue",method = RequestMethod.PUT)
    public void commentAnswer(ModelMap model,HttpServletRequest req,@RequestBody CommentVoReq voReq) {
		
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			if(StringUtil.isNotEmpty(voReq.getCommentId())||StringUtil.isNotEmpty(voReq.getLoginName())){
				MsgVo msgVo = commentService.commentAnswer(voReq);
				if(msgVo.getResult()){
					model.put("code", EnumTypes.success.getCode());
					model.put("message", "商品评价回复成功");
				}else{
					model.put("code", EnumTypes.fail.getCode());
					model.put("message", "商品评价回复失败");
				}
			}else{
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "评价ID或用户名不能为空");
			}
		} catch (Exception e) {
			LogCvt.info("商品评价回复" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
		
	}
	
	/**
	 * @Title: 商品评价删除
	 * @author yfy
	 * @date 2015-3-23 下午15:10:30
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"product_comment_de"})
	@RequestMapping(value = "/de",method = RequestMethod.DELETE)
    public void commentDetele(ModelMap model,HttpServletRequest req,String commentId,String merchantId) {
		
		try {
			String clientId = req.getAttribute(Constants.CLIENT_ID)+"";
			if(StringUtil.isNotEmpty(commentId)){
				MsgVo msgVo = commentService.productCommentDelete(clientId,merchantId,commentId);
				if(msgVo.getResult()){
					model.put("code", EnumTypes.success.getCode());
					model.put("message", "删除商品评价成功");
				}else{
					model.put("code", EnumTypes.fail.getCode());
					model.put("message", "删除商品评价失败");
				}
			}else{
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "评价ID不能为空");
			}
		} catch (Exception e) {
			LogCvt.info("商品评价删除" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
		
	}
	
}
