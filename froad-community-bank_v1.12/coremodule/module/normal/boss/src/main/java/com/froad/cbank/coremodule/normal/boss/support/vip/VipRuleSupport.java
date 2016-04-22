package com.froad.cbank.coremodule.normal.boss.support.vip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.type.ArrayUtil;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.BindProductAddReq;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.BindProductDelReq;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.BindProductReq;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.BoundProductReq;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.VipProductAddReq;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.VipProductRes;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.VipRuleAddReq;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.VipRuleListReq;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.VipRuleRes;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.VipRuleStatReq;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.VipRuleUpdReq;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.thrift.service.VipProductService;
import com.froad.thrift.vo.AddProductVoRes;
import com.froad.thrift.vo.BindVipInfoVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ProductOfVipPageVo;
import com.froad.thrift.vo.ProductOfVipVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.VipProductPageVoRes;
import com.froad.thrift.vo.VipProductVo;

/**
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年6月17日 上午11:42:12
 * @version 1.0
 * @desc VIP规则支持类
 */
@Service
public class VipRuleSupport {

	@Resource
	private VipProductService.Iface vipProductService;
	
	/**
	 * @desc 获取VIP规则列表
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @throws Exception
	 * @date 创建时间：2015年6月16日 上午11:49:39
	 */
	public HashMap<String, Object> getRuleList(VipRuleListReq pojo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Page page = new Page();
		List<VipRuleRes> vipRuleList = new ArrayList<VipRuleRes>();
		VipRuleRes temp = null;
		//封装分页对象
		PageVo pageReq = new PageVo();
		pageReq.setPageNumber(pojo.getPageNumber());
		pageReq.setPageSize(pojo.getPageSize());
		//封装请求对象
		VipProductVo req = new VipProductVo();
		BeanUtils.copyProperties(req, pojo);
		//调用SERVER接口
		VipProductPageVoRes resp = vipProductService.getVipProductsByPage(pageReq, req);
		
		if(!Constants.RESULT_SUCCESS.equals(resp.getResultVo().getResultCode())) {
			throw new BossException(resp.getResultVo().getResultCode(), resp.getResultVo().getResultDesc());
		}
		BeanUtils.copyProperties(page, resp.getPage());
		if(!ArrayUtil.empty(resp.getVipProductVos())) {
			for(VipProductVo tmp : resp.getVipProductVos()) {
				temp = new VipRuleRes();
				BeanUtils.copyProperties(temp, tmp);
				vipRuleList.add(temp);
			}
		}
		map.put("vipRuleList", vipRuleList);
		map.put("page", page);
		return map;
	}
	
	/**
	 * @desc 获取VIP规则详情
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @throws Exception 
	 * @date 创建时间：2015年6月16日 下午1:44:01
	 */
	public HashMap<String, Object> getRuleDetail(String vipId) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		VipRuleRes vipRule = null;
		//调用SERVER接口
		VipProductVo resp = vipProductService.getVipProductDetail(vipId);
		
		if(StringUtil.isNotBlank(resp.getVipId())) {
			vipRule = new VipRuleRes();
			BeanUtils.copyProperties(vipRule, resp);
			map.put("vipRule", vipRule);
		} else {
			throw new BossException("VIP规则详情查询异常，返回null对象！");
		}
		return map;
	}
	
	/**
	 * @desc 新增VIP规则
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @throws Exception
	 * @date 创建时间：2015年6月16日 下午1:59:46
	 */
	public HashMap<String, Object> addRule(VipRuleAddReq pojo, OriginVo org) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//封装请求对象
		VipProductVo req = new VipProductVo();
		BeanUtils.copyProperties(req, pojo);
		//调用SERVER接口
		AddProductVoRes resp = vipProductService.addVipProduct(org, req);
		
		if(!Constants.RESULT_SUCCESS.equals(resp.getResult().getResultCode())) {
			throw new BossException(resp.getResult().getResultCode(), resp.getResult().getResultDesc());
		} else {
			map.put("code", resp.getResult().getResultCode());
			map.put("message", resp.getResult().getResultDesc());
		}
		return map;
	}
	
	/**
	 * @desc 修改VIP规则
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @throws Exception
	 * @date 创建时间：2015年6月16日 下午2:01:41
	 */
	public HashMap<String, Object> updateRule(VipRuleUpdReq pojo, OriginVo org) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//封装请求对象
		VipProductVo req = new VipProductVo();
		BeanUtils.copyProperties(req, pojo);
		//调用SERVER端接口
		ResultVo resp = vipProductService.updateVipProduct(org, req);
		
		if(!Constants.RESULT_SUCCESS.equals(resp.getResultCode())) {
			throw new BossException(resp.getResultCode(), resp.getResultDesc());
		} else {
			map.put("code", resp.getResultCode());
			map.put("message", resp.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 删除VIP规则
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年6月23日 上午11:33:35
	 * @param vipId
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> deleteRule(String vipId, OriginVo org) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//调用SERVER端接口
		ResultVo resp = vipProductService.deleteVipProduct(org, vipId);
		
		if(!Constants.RESULT_SUCCESS.equals(resp.getResultCode())) {
			throw new BossException(resp.getResultCode(), resp.getResultDesc());
		} else {
			map.put("code", resp.getResultCode());
			map.put("message", resp.getResultDesc());
		}
		return map;
	}
	
	/**
	 * @desc 修改VIP规则状态（"启用/作废"）
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年6月16日 下午2:06:19
	 * @throws Exception
	 */
	public HashMap<String, Object> changeStatus(VipRuleStatReq pojo, OriginVo org) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//调用SERVER端接口
		ResultVo resp = vipProductService.updateVipStatus(org, pojo.getVipId(), pojo.getStatus());
		
		if(!Constants.RESULT_SUCCESS.equals(resp.getResultCode())) {
			throw new BossException(resp.getResultCode(), resp.getResultDesc());
		} else {
			map.put("code", resp.getResultCode());
			map.put("message", resp.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 获取已绑定商品列表
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年6月23日 下午2:50:37
	 * @param pojo
	 * @return
	 */
	public HashMap<String, Object> getBoundProductList(BoundProductReq pojo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<VipProductRes> productList = new ArrayList<VipProductRes>();
		VipProductRes temp = null;
		Page page = new Page();
		//封装分页对象
		PageVo pageReq = new PageVo();
		pageReq.setPageNumber(pojo.getPageNumber());
		pageReq.setPageSize(pojo.getPageSize());
		//调用SERVER端接口
		ProductOfVipPageVo resp = vipProductService.getProductsOfVipByPage(pageReq, pojo.getVipId());
		
		if(!Constants.RESULT_SUCCESS.equals(resp.getResultVo().getResultCode())) {
			throw new BossException(resp.getResultVo().getResultCode(), resp.getResultVo().getResultDesc());
		}
		if(!ArrayUtil.empty(resp.getProductOfVipVoList())) {
			BeanUtils.copyProperties(page, resp.getPage());
			for(ProductOfVipVo tmp : resp.getProductOfVipVoList()) {
				temp = new VipProductRes();
				BeanUtils.copyProperties(temp, tmp);
				productList.add(temp);
			}
		}
		map.put("productList", productList);
		map.put("page", page);
		return map;
	}
	
	/**
	 * 获取可绑定商品列表
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年6月23日 下午2:51:00
	 * @param pojo
	 * @return
	 */
	public HashMap<String, Object> getBindProductList(BindProductReq pojo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<VipProductRes> productList = new ArrayList<VipProductRes>();
		VipProductRes temp = null;
		Page page = new Page();
		//封装分页对象
		PageVo pageReq = new PageVo();
		pageReq.setPageNumber(pojo.getPageNumber());
		pageReq.setPageSize(pojo.getPageSize());
		//调用SERVER端接口
		ProductOfVipPageVo resp = vipProductService.findProductsForVipByPage(pojo.getVipId(), pojo.getName(), (double) pojo.getPriceStart(), (double) pojo.getPriceEnd(), pageReq);
		
		if(!Constants.RESULT_SUCCESS.equals(resp.getResultVo().getResultCode())) {
			throw new BossException(resp.getResultVo().getResultCode(), resp.getResultVo().getResultDesc());
		}
		if(!ArrayUtil.empty(resp.getProductOfVipVoList())) {
			BeanUtils.copyProperties(page, resp.getPage());
			for(ProductOfVipVo tmp : resp.getProductOfVipVoList()) {
				temp = new VipProductRes();
				BeanUtils.copyProperties(temp, tmp);
				if(tmp.getVipLimit() == 0){
					temp.setVipLimit(null);
				}
				if(tmp.getVipPrice() == 0){
					temp.setVipPrice(null);
				}
				productList.add(temp);
			}
		}
		map.put("productList", productList);
		map.put("page", page);
		return map;
	}
	
	/**
	 * @desc 新增VIP商品
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @throws Exception
	 * @date 创建时间：2015年6月16日 下午2:56:11
	 */
	public HashMap<String, Object> addBindProduct(BindProductAddReq pojo, OriginVo org) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//封装请求参数
		List<BindVipInfoVo> req = new ArrayList<BindVipInfoVo>();
		BindVipInfoVo temp = null;
		if(!ArrayUtil.empty(pojo.getProductList())) {
			for(VipProductAddReq tmp : pojo.getProductList()) {
				if(tmp.getVipPrice() < 0) {
					throw new BossException("请传入有效的VIP价格");
				}
				if(tmp.getVipLimit() < 0) {
					throw new BossException("请传入有效的VIP限购");
				}
				temp = new BindVipInfoVo();
				BeanUtils.copyProperties(temp, tmp);
				req.add(temp);
			}
		} else {
			throw new BossException("新增的VIP商品实例为空");
		}
		//调用SERVER端接口
		ResultVo resp = vipProductService.addProductsToVipProduct(org, pojo.getVipId(), req);
		
		if(!Constants.RESULT_SUCCESS.equals(resp.getResultCode())) {
			throw new BossException(resp.getResultCode(), resp.getResultDesc());
		} else {
			map.put("code", resp.getResultCode());
			map.put("message", resp.getResultDesc());
		}
		return map;
	}
	
	/**
	 * @desc 删除VIP商品
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @throws Exception
	 * @date 创建时间：2015年6月16日 下午2:57:03
	 */
	public HashMap<String, Object> deleteBindProduct(BindProductDelReq pojo, OriginVo org) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//为空时删除全部
		List<String> productIdList = null;
		if(StringUtils.isNotBlank(pojo.getProductId())) {
			productIdList = new ArrayList<String>();
			productIdList.add(pojo.getProductId());
		}
		//调用SERVER端接口
		ResultVo resp = vipProductService.removeProductsFromVipProduct(org, pojo.getVipId(), productIdList);
		
		if(!Constants.RESULT_SUCCESS.equals(resp.getResultCode())) {
			throw new BossException(resp.getResultCode(), resp.getResultDesc());
		} else {
			map.put("code", resp.getResultCode());
			map.put("message", resp.getResultDesc());
		}
		return map;
	}
}
