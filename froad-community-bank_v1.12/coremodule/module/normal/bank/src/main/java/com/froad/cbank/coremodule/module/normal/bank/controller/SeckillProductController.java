package com.froad.cbank.coremodule.module.normal.bank.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.froad.cbank.coremodule.module.normal.bank.enums.AuditFlagEnum;
import com.froad.cbank.coremodule.module.normal.bank.enums.ProductTypeEnum;
import com.froad.cbank.coremodule.module.normal.bank.service.LoginService;
import com.froad.cbank.coremodule.module.normal.bank.service.SeckillProductService;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.util.QueryResult;
import com.froad.cbank.coremodule.module.normal.bank.vo.MsgVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.OrdinaryProductVoReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.OrdinaryProductVoRes;
import com.froad.cbank.coremodule.module.normal.bank.vo.SeckillProductDetil;
import com.froad.cbank.coremodule.module.normal.bank.vo.SeckillProductSetVoReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.SeckillProductVoReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.SeckillProductVoRes;

/**
 * 秒杀商品管理
 * 
 * @author liuyanyun
 * @date 2015-4-21 下午03:29:06
 */
@Controller
@RequestMapping(value = "/seckillProduct")
public class SeckillProductController extends BasicSpringController {

	@Resource
	private SeckillProductService seckillProductService;

	@Resource
	private LoginService loginService;

	/**
	 * @Title: 查询非秒杀商品列表
	 * @author wangzhangxu
	 * @date 2015-5-6 下午01:15:58
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"add_seckill","seckill_product_add"})
	@RequestMapping(value = "/nslt", method = RequestMethod.POST)
	public void QueryProductNotSeckill(ModelMap model, HttpServletRequest req, 
			@RequestBody OrdinaryProductVoReq voReq) {
		try {
			// 分页数据
			Integer pageNumber = voReq.getPageNumber();
			Integer pageSize = voReq.getPageSize();
			if (pageNumber == null && pageSize == null) {
				pageNumber = 1;
				pageSize = 10;
			}
			voReq.setClientId((String)req.getAttribute(Constants.CLIENT_ID));
			
			// 条件过滤
			Map<String, String> filter = new HashMap<String, String>();
			filter.put("name", voReq.getProductName());
			if (StringUtils.isEmpty(voReq.getProductType())) {
				// 只有“团购”，“预售”和“名优特惠”的商品才能设置秒杀
				filter.put("types", ProductTypeEnum.GROUP.getCode() + "," + ProductTypeEnum.PRESALE.getCode() + "," + ProductTypeEnum.PREFERENTIAL.getCode()); // 所有商品
			} else {
				filter.put("type", voReq.getProductType());
			}
			
			filter.put("orgCode", voReq.getOrgCode());
			filter.put("endTimeStart",String.valueOf(new Date().getTime()));
			filter.put("auditState", AuditFlagEnum.ACCEPTED.getCode()); // 审核通过的商品
			filter.put("isSeckill", "0"); // 非秒杀商品
			QueryResult<OrdinaryProductVoRes> queryVo = seckillProductService.findProductPageByConditions(voReq.getClientId(),
							JSON.toJSONString(filter), pageNumber, pageSize,voReq.getLastPageNumber(),
							voReq.getFirstRecordTime(),voReq.getLastRecordTime());
			model.put("productList", queryVo.getResult());// 非秒杀商品列表
			model.put("totalCount", queryVo.getTotalCount());// 总记录数
			model.put("pageCount", queryVo.getPageCount());// 总页数
			model.put("pageNumber", queryVo.getPageNumber());// 总页
			
			model.put("lastPageNumber", queryVo.getLastPageNumber());
			model.put("firstRecordTime", queryVo.getFirstRecordTime());
			model.put("lastRecordTime", queryVo.getLastRecordTime());
			
			model.put("message", "非秒杀商品列表信息查询成功");
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}

	}
	/**
	 * @Title: 查询秒杀商品列表
	 * @author liuyanyun
	 * @date 2015-4-21 下午04:15:58
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"to_audit_seckill_list_menu","to_audit_seckill_list_select_bind","seckill_product_list_menu","seckill_product_select_bind","add_seckill_lt"})
	@RequestMapping(value = "/lt", method = RequestMethod.POST)
	public void QuerySeckillProduct(ModelMap model, HttpServletRequest req,
			@RequestBody SeckillProductVoReq voReq) {
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			// 分页数据
			Integer pageNumber = voReq.getPageNumber();
			Integer pageSize = voReq.getPageSize();
			if (pageNumber == null && pageSize == null) {
				pageNumber = 1;
				pageSize = 10;
			}
			// 条件过滤
			Map<String, String> filter = new HashMap<String, String>();
			filter.put("parentOrgCode", voReq.getParentOrgCode());
			filter.put("orgCode", voReq.getOrgCode());
			filter.put("type", voReq.getProductType());
			filter.put("name", voReq.getProductName());
			filter.put("startTime", voReq.getStartDate());
			filter.put("endTime", voReq.getEndDate());
			filter.put("auditState", voReq.getAuditState());
			QueryResult<SeckillProductVoRes> queryVo = seckillProductService
					.findPageByConditions(voReq.getClientId(),
							JSON.toJSONString(filter), pageNumber, pageSize);
			model.put("seckillProductList", queryVo.getResult());// 秒杀商品列表
			model.put("totalCount", queryVo.getTotalCount());// 总记录数
			model.put("pageCount", queryVo.getPageCount());// 总页数
			model.put("pageNumber", queryVo.getPageNumber());// 总页
			
			model.put("lastPageNumber", queryVo.getLastPageNumber());
			model.put("firstRecordTime", queryVo.getFirstRecordTime());
			model.put("lastRecordTime", queryVo.getLastRecordTime());
			
			model.put("message", "秒杀商品列表信息查询成功");
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}

	}

	/**
	 * @Title: 秒杀商品批量设置
	 * @author liuyanyun
	 * @date 2015-4-22 下午02:45:58
	 * @param model
	 * @param req
	 */

	@CheckPermission(keys={"add_seckill_ad","add_seckill_sure"})
	@RequestMapping(value = "/ad", method = RequestMethod.POST)
	public void seckillProductBatchSet(ModelMap model, HttpServletRequest req,
			@RequestBody SeckillProductSetVoReq voReq) {
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			if (seckillProductService.verify(voReq)) {
				MsgVo msgVo = seckillProductService.batchSet(voReq, loginService.getOriginVo(req));
				if (msgVo.getResult()) {
					model.put("code", EnumTypes.success.getCode());
					model.put("message", "秒杀商品批量设置成功");
				} else {
					model.put("code", EnumTypes.fail.getCode());
					model.put("message", msgVo.getMsg());
				}
			} else {
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "参数不全或有误");
			}
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}

	}
	
	/**
	 * @Title: 秒杀商品修改
	 * @author liuyanyun
	 * @date 2015-4-23 上午09:51:58
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"seckill_product_ue"})
	@RequestMapping(value = "/ue", method = RequestMethod.PUT)
	public void seckillProductUpdate(ModelMap model, HttpServletRequest req,
			@RequestBody SeckillProductVoReq voReq) {
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			if (seckillProductService.verify(voReq)) {
				MsgVo msgVo = seckillProductService.update(voReq, loginService.getOriginVo(req));
				if (msgVo.getResult()) {
					model.put("code", EnumTypes.success.getCode());
					model.put("message", "秒杀商品修改成功");
				} else {
					model.put("code", EnumTypes.fail.getCode());
					model.put("message", msgVo.getMsg());
				}
			}else{
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "参数不全或有误");
			}
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}

	}

	/**
	 * @Title:秒杀商品上/下架
	 * @author liuyanyun
	 * @date 2015-4-23 上午11:42:23
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"seckill_product_it_up","seckill_product_it_down"})
	@RequestMapping(value = "/it", method = RequestMethod.PUT)
	public void seckillProduckUpDown(ModelMap model, HttpServletRequest req,@RequestBody SeckillProductVoReq voReq) {
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			if (StringUtil.isNotEmpty(voReq.getProductId())
					&& StringUtil.isNotEmpty(voReq.getIsMarketable()) 
					&& ("1".equals(voReq.getIsMarketable()) || "2".equals(voReq.getIsMarketable()))) {
				MsgVo msgVo = seckillProductService.upDownProduct(
						voReq.getClientId(), 
						voReq.getProductId(), 
						voReq.getIsMarketable(), 
						loginService.getOriginVo(req));
				if (msgVo.getResult()) {
					model.put("code", EnumTypes.success.getCode());
					model.put("message", "秒杀商品"
							+ ("1".equals(voReq.getIsMarketable()) ? "上" : "下")
							+ "架成功");
				} else {
					model.put("code", EnumTypes.fail.getCode());
					model.put("message", "秒杀商品"
							+ ("1".equals(voReq.getIsMarketable()) ? "上" : "下")
							+ "架失败");
				}
			} else {
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "参数不全或有误");
			}
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * @Title:秒杀商品详情
	 * @author liuyanyun
	 * @date 2015-4-23 下午4:28:32
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"seckill_product_detail_bind","to_audit_seckilldetail_tg","to_audit_seckilldetail_jpys","to_audit_seckilldetail_myth","seckilldetail_jpys","seckilldetail_myth","seckilldetail_tg","to_audit_seckill_list_detail_bind"})
	@RequestMapping(value = "/dl", method = RequestMethod.GET)
	public void seckillProductDetil(ModelMap model, HttpServletRequest req,String productId) {
		try {
			String clientId = req.getAttribute(Constants.CLIENT_ID)+"";
			if(StringUtil.isNotEmpty(productId)){
				SeckillProductDetil productDetil = seckillProductService.detail(clientId, productId);
				if(productDetil != null){
					model.put("product", productDetil);
				}
			}else{
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "参数不全");
			}
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * @Title:秒杀商品删除
	 * @author liuyanyun
	 * @date 2015-4-23 下午5:33:25
	 * @param model
	 * @param req
	 * @param productId
	 */
	@CheckPermission(keys={"seckill_product_de"})
	@RequestMapping(value = "/de", method = RequestMethod.DELETE)
	public void seckillProductDelete(ModelMap model, HttpServletRequest req,String productId) {
		
		try {
			String clientId = req.getAttribute(Constants.CLIENT_ID)+"";
			if(StringUtil.isNotEmpty(productId)){
				MsgVo msgVo = seckillProductService.delete(clientId,productId,
						loginService.getOriginVo(req));
				if(msgVo.getResult()){
					model.put("code", EnumTypes.success.getCode());
					model.put("message", "秒杀商品删除成功");
				}else{
					model.put("code", EnumTypes.fail.getCode());
					model.put("message", "秒杀商品删除失败");
				}
			}else{
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "参数不全");
			}
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

}
