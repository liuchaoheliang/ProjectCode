package com.froad.cbank.coremodule.normal.boss.support.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.product.ActivityProductDetailReq;
import com.froad.cbank.coremodule.normal.boss.pojo.product.ActivityProductDetailRes;
import com.froad.cbank.coremodule.normal.boss.pojo.product.ActivityProductPointsRes;
import com.froad.cbank.coremodule.normal.boss.pojo.product.ActivityProductVoListReq;
import com.froad.cbank.coremodule.normal.boss.pojo.product.ActivityProductVoRes;
import com.froad.cbank.coremodule.normal.boss.pojo.product.OperateActivityProductVo;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.DateUtil;
import com.froad.cbank.coremodule.normal.boss.utils.PramasUtil;
import com.froad.thrift.service.ClientService;
import com.froad.thrift.service.OrderQueryService;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.vo.ActivityProductPageVo;
import com.froad.thrift.vo.ActivityProductVo;
import com.froad.thrift.vo.ClientVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.PlatType;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.order.GivePointsOrderVo;
import com.froad.thrift.vo.order.GivePointsProductVo;
import com.froad.thrift.vo.order.QueryGivePointsProductByBossReq;
import com.froad.thrift.vo.order.QueryGivePointsProductByBossRes;

/**
 * 
 * <p>标题: 商品与活动相关的类</p>
 * <p>说明: </p>
 * <p>创建时间：2015年5月25日下午8:13:50</p>
 * <p>作者: 陈明灿</p>
 * <p>版本: 1.0</p>
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */
@Service
public class ActivityProductSupport {

	@Resource
	ProductService.Iface productService;
	
	@Resource
	OrderQueryService.Iface orderQueryService;
	
	@Resource
	ClientService.Iface clientService;
	
	/**
	 * 
	 * <p>功能简述：查询活动绑定的商品列表</p> 
	 * <p>使用说明：查询活动绑定的商品列表</p> 
	 * <p>创建时间：2015年5月25日下午8:35:26</p>
	 * <p>作者: 陈明灿</p>
	 * @param id
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws TException 
	 */
	public Map<String, Object> haveList(ActivityProductVoListReq listReq) throws TException {
		Map<String, Object> map = new HashMap<String, Object>();
		long longId = 0L;
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(listReq.getPageNumber());
		pageVo.setPageSize(listReq.getPageSize());
		if(StringUtil.isNotEmpty(listReq.getId())){
			longId = Long.parseLong(listReq.getId());
		}
		ActivityProductPageVo productsByPage = productService
				.findActivtyProductsByPage(longId, pageVo);
		LogCvt.info("查询活动绑定商品列表请求返回数据:", JSON.toJSONString(productsByPage));
		if(null!=productsByPage){
			PageVo page1 = productsByPage.getPage();
			Page page = new Page();
			BeanUtils.copyProperties(page, page1);
			map.put("page", page);
			ActivityProductVoRes productVoRes = null;
			List<ActivityProductVoRes> list = null;
			List<ActivityProductVo> list1 = productsByPage
					.getActivityProductVoList();
			if (null != list1 && list1.size() > 0) {
				list = new ArrayList<ActivityProductVoRes>();
				for (ActivityProductVo activityProductVo : list1) {
					productVoRes = new ActivityProductVoRes();
					BeanUtils.copyProperties(productVoRes,
							activityProductVo);
					// 时间格式转化(创建时间)
					if(StringUtil.isNotEmpty(activityProductVo.getCreateTime()+"")){
						productVoRes.setCreateTime(DateUtil
									.longToString(activityProductVo
											.getCreateTime()));
					}
					// 时间格式转化(上架时间)
					if(activityProductVo.getRackTime()!=0){
						productVoRes.setRackTime(DateUtil
							.longToString(activityProductVo.getRackTime()));
					}
					list.add(productVoRes);

				}
			}
			map.put("list", list);
		}
		return map;
	}
	
	/**
	 * 
	 * <p>功能简述：查询活动可以绑定的商品列表</p>
	 * <p>使用说明：查询活动可以绑定的商品列表</p>
	 * <p>创建时间：2015年5月25日下午8:35:26</p>
	 * <p>作者: 陈明灿</p>
	 * @param id
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws TException 
	 */
	public Map<String, Object> newList(ActivityProductVoListReq listReq) throws TException {
		Map<String, Object> map = new HashMap<String, Object>();
		long longId = Long.parseLong(listReq.getId());
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(listReq.getPageNumber());
		pageVo.setPageSize(listReq.getPageSize());
		ActivityProductPageVo productsByPage = productService
				.findProductsForActivtyByPage(longId, listReq.getName(),
						pageVo);
		if (null != productsByPage) {
			PageVo page1 = productsByPage.getPage();
			Page page = new Page();
			BeanUtils.copyProperties(page, page1);
			map.put("page", page);
			ActivityProductVoRes productVoRes = null;
			List<ActivityProductVoRes> list = null;
			List<ActivityProductVo> list1 = productsByPage
					.getActivityProductVoList();
			if (null != list1 && list1.size() > 0) {
				list = new ArrayList<ActivityProductVoRes>();
				for (ActivityProductVo activityProductVo : list1) {
					productVoRes = new ActivityProductVoRes();
					BeanUtils.copyProperties(productVoRes,
							activityProductVo);
					// 时间格式转化(创建时间)
					if(StringUtil.isNotEmpty(activityProductVo.getCreateTime()+"")){
						productVoRes.setCreateTime(DateUtil
									.longToString(activityProductVo.getCreateTime()));
					}
					// 时间格式转化(上架时间)
					if(StringUtil.isNotEmpty(activityProductVo.getRackTime()+"")){
						productVoRes.setRackTime(DateUtil
							.longToString(activityProductVo.getRackTime()));
					}
					list.add(productVoRes);
				}
			}
			map.put("list", list);
		}
		return map;
	}

	/**
	 * 
	 * <p>功能简述：绑定商品</p> 
	 * <p>使用说明：绑定商品</p> 
	 * <p>创建时间：2015年5月25日下午8:44:19</p>
	 * <p>作者: 陈明灿</p>
	 * @param activityProductVo
	 * @return
	 * @throws TException 
	 * @throws BossException 
	 */
	public Map<String, Object> addProduct(
			OperateActivityProductVo activityProductVo) throws TException, BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		OriginVo originVo = new OriginVo();
		originVo.setPlatType(PlatType.boss);
		long activityId = 0L;
		if(StringUtil.isNotEmpty(activityProductVo.getId())){
			activityId = Long.parseLong(activityProductVo.getId());
		}
		ResultVo resultVo = productService.addActivtyToProducts(originVo,
				activityId, activityProductVo.getProductIds());
		if (Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())) {
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", resultVo.getResultDesc());
		} else {
			throw new BossException(resultVo.getResultCode(),  resultVo.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 
	 * <p>功能简述：解绑商品</p> 
	 * <p>使用说明：解绑商品</p> 
	 * <p>创建时间：2015年5月25日下午9:14:27</p>
	 * <p>作者: 陈明灿</p>
	 * @param activityProductVo
	 * @return
	 * @throws TException 
	 * @throws BossException 
	 */
	public Map<String, Object> removeProduct(
			OperateActivityProductVo activityProductVo) throws TException, BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		OriginVo originVo = new OriginVo();
		originVo.setPlatType(PlatType.boss);
		long activityId = 0L;
		if(StringUtil.isNotEmpty(activityProductVo.getId())){
			activityId = Long.parseLong(activityProductVo.getId());
		}
		ResultVo resultVo = productService.removeActivtyFromProducts(
				originVo, activityId, activityProductVo.getProductId());
		LogCvt.info("解绑商品请求返回数据:", JSON.toJSONString(resultVo));
		if (Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())) {
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", resultVo.getResultDesc());
		} else {
			throw new BossException(resultVo.getResultCode(),  resultVo.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 商品送积分明细
	 * @tilte detail
	 * @author zxl
	 * @date 2015年6月15日 下午1:36:32
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> detail(ActivityProductDetailReq po) throws Exception{
		HashMap<String,Object> map = new HashMap<String, Object>();
		QueryGivePointsProductByBossReq req = new QueryGivePointsProductByBossReq();
		
		PageVo page = new PageVo();
		page.setPageSize(po.getPageSize());
		page.setPageNumber(po.getPageNumber());
		page.setFirstRecordTime(po.getFirstRecordTime());
		page.setLastRecordTime(po.getLastRecordTime());
		page.setLastPageNumber(po.getLastPageNumber());
		req.setPageVo(page);
		
		BeanUtils.copyProperties(req, po);
		
		if(StringUtils.isNotBlank(po.getStartTime())){
			req.setStartTime(PramasUtil.DateFormat(po.getStartTime()));
		}
		
		if(StringUtils.isNotBlank(po.getEndTime())){
			req.setEndTime(PramasUtil.DateFormatEnd(po.getEndTime()));
		}
		
		QueryGivePointsProductByBossRes res = orderQueryService.queryGivePointsProductByBoss(req);
		
		if(!Constants.RESULT_SUCCESS.equals(res.getResultVo().getResultCode())){
			throw new BossException(res.getResultVo().getResultCode(), res.getResultVo().getResultDesc());
		}
		
		List<ActivityProductDetailRes> list = new ArrayList<ActivityProductDetailRes>();
		
		//获取客户端集合
		List<ClientVo> client = clientService.getClient(new ClientVo());
		
		for(GivePointsOrderVo vo : res.getGivePointsOrderList()){
			ActivityProductDetailRes r = new ActivityProductDetailRes();
			BeanUtils.copyProperties(r, vo);
			
			// 转化client中方名字
			for (ClientVo clientVo : client) {
				if (vo.getClientId().equals(clientVo.getClientId())) {
					r.setClientId(clientVo.getBankName());
					break;
				}
			}
			
			List<ActivityProductPointsRes> p = new ArrayList<ActivityProductPointsRes>();
			for(GivePointsProductVo v : vo.getProductList()){
				ActivityProductPointsRes a = new ActivityProductPointsRes();
				BeanUtils.copyProperties(a, v);
				p.add(a);
			}
			r.setProduct(p);
			list.add(r);
		}
		Page pageRes = new Page();
		BeanUtils.copyProperties(pageRes, res.getPageVo());
		map.put("page", pageRes);
		map.put("list", list);
		return map;
	}

}
