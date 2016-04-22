package com.froad.cbank.coremodule.normal.boss.support.product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.enums.AuditFlagEnum;
import com.froad.cbank.coremodule.normal.boss.enums.IsMarketableEnum;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.BossUser;
import com.froad.cbank.coremodule.normal.boss.pojo.FileVo;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.product.AuditProductReq;
import com.froad.cbank.coremodule.normal.boss.pojo.product.BossOutletVoRes;
import com.froad.cbank.coremodule.normal.boss.pojo.product.BossProductInfoVoRes;
import com.froad.cbank.coremodule.normal.boss.pojo.product.BossProductVoRes;
import com.froad.cbank.coremodule.normal.boss.pojo.product.GiftProductVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.product.ProductAddReq;
import com.froad.cbank.coremodule.normal.boss.pojo.product.ProductInfoVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.product.ProductListVo;
import com.froad.cbank.coremodule.normal.boss.pojo.product.ProductVoRes;
import com.froad.cbank.coremodule.normal.boss.pojo.product.UpdownProductReq;
import com.froad.cbank.coremodule.normal.boss.support.merchant.MerchantOutletSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.DateUtil;
import com.froad.cbank.coremodule.normal.boss.utils.PramasUtil;
import com.froad.thrift.service.BossProductService;
import com.froad.thrift.service.ClientService;
import com.froad.thrift.service.OrgService;
import com.froad.thrift.service.OutletService;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.vo.AddProductVoRes;
import com.froad.thrift.vo.BossAuditProcessVo;
import com.froad.thrift.vo.BossProductDetailVo;
import com.froad.thrift.vo.BossProductFilterVo;
import com.froad.thrift.vo.BossProductInfoVo;
import com.froad.thrift.vo.BossProductListPageVo;
import com.froad.thrift.vo.BossProductListVo;
import com.froad.thrift.vo.BossProductStatusVoReq;
import com.froad.thrift.vo.BossProductVo;
import com.froad.thrift.vo.ClientVo;
import com.froad.thrift.vo.OrgVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.OutletVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.PlatType;
import com.froad.thrift.vo.ProductBriefInfoVo;
import com.froad.thrift.vo.ProductBriefPageVo;
import com.froad.thrift.vo.ProductBuyLimitVo;
import com.froad.thrift.vo.ProductCategoryVo;
import com.froad.thrift.vo.ProductFilterVoReq;
import com.froad.thrift.vo.ProductImageVo;
import com.froad.thrift.vo.ProductInfoVo;
import com.froad.thrift.vo.ProductOperateVoReq;
import com.froad.thrift.vo.ProductOutletVo;
import com.froad.thrift.vo.ProductStatusBatchVoReq;
import com.froad.thrift.vo.ProductVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.VipProductVo;

@Service
public class ProductSupport {
	
	@Resource
	ProductService.Iface productService;
	@Resource
	ClientService.Iface clientService;
	@Resource
	BossProductService.Iface bossProductService;
	@Resource
	MerchantOutletSupport merchantOutletSupport;
	@Resource
	OutletService.Iface outletService;
	@Resource
	OrgService.Iface orgService;
	/**
	 * 商品新增
	 * @tilte add
	 * @author zxl
	 * @date 2015年7月27日 上午9:50:43
	 * @param req
	 * @param request
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public HashMap<String, Object> add(ProductAddReq req,HttpServletRequest request) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		BossProductInfoVo vo = new BossProductInfoVo();
		
		//商品
		BossProductVo product = new BossProductVo();
		BeanUtils.copyProperties(product, req);
		product.setStartTime(PramasUtil.DateFormat(req.getStartTime()));
		product.setEndTime(PramasUtil.DateFormatEnd(req.getEndTime()));
		product.setDeliveryStartTime(PramasUtil.DateFormat(req.getDeliveryStartTime()));
		product.setDeliveryEndTime(PramasUtil.DateFormatEnd(req.getDeliveryEndTime()));
		//boss录入
		product.setPlatType("1");
		
		//查一级机构
		OrgVo orgVo = new OrgVo(); 
		orgVo.setClientId(req.getClientId());
		orgVo.setOrgLevel("1");
		orgVo.setIsEnable(true);
		List<OrgVo> orgList = orgService.getOrg(orgVo);
		if(orgList!=null&&orgList.size()>0){
			product.setOrgCode(orgList.get(0).getOrgCode());
		}
		
		vo.setProduct(product);
		
		//图片
		List<ProductImageVo> image = new ArrayList<ProductImageVo>();
		for(FileVo i : req.getImgList()){
			ProductImageVo v = new ProductImageVo();
			BeanUtils.copyProperties(v, i);
			image.add(v);
		}
		vo.setImage(image);
		
		//机构
		if(StringUtil.isNotBlank(req.getOrgCodes())){
			List<String> orgCodes = new ArrayList<String>();
			String[] orgcode = req.getOrgCodes().split(",");
			for(String s : orgcode){
				orgCodes.add(s);
			}
			vo.setOrgCodes(orgCodes);
		}
		
		
		//提货网点
		if(StringUtil.isNotBlank(req.getOutletIds())){
			List<String> outletIds = new ArrayList<String>();
			String[] outletid = req.getOutletIds().split(",");
			for(String s : outletid){
				outletIds.add(s);
			}
			vo.setOutletIds(outletIds);
		}
		
		BossUser user = (BossUser)request.getAttribute(Constants.BOSS_USER);
		OriginVo ori = new OriginVo();
		ori.setClientId(req.getClientId());
		ori.setPlatType(PlatType.boss);
		ori.setOperatorIp(Constants.getIpAddr(request));
		ori.setOperatorId(user.getId());
		
		AddProductVoRes res = bossProductService.addProduct(ori, vo);
		if(!Constants.RESULT_SUCCESS.equals(res.getResult().getResultCode())){
			throw new BossException(res.getResult().getResultCode(), res.getResult().getResultDesc());
		}
		
		return map;
	}
	
	
	/**
	 * 商品修改
	 * @tilte mdy
	 * @author zxl
	 * @date 2015年7月27日 上午9:50:43
	 * @param req
	 * @param request
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public HashMap<String, Object> mdy(ProductAddReq req,HttpServletRequest request) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		BossProductInfoVo vo = new BossProductInfoVo();
		
		//商品
		BossProductVo product = new BossProductVo();
		BeanUtils.copyProperties(product, req);
		product.setStartTime(PramasUtil.DateFormat(req.getStartTime()));
		product.setEndTime(PramasUtil.DateFormatEnd(req.getEndTime()));
		product.setDeliveryStartTime(PramasUtil.DateFormat(req.getDeliveryStartTime()));
		product.setDeliveryEndTime(PramasUtil.DateFormatEnd(req.getDeliveryEndTime()));
		
		//修改后状态为下架，待审核
		product.setMarketableStatus(IsMarketableEnum.NO.getCode());
		product.setAuditStatus(AuditFlagEnum.NEW.getCode());
		
		//没有vip规则
		if(StringUtils.isBlank(req.getVipId())){
			product.setIsRemoveVipId(true);
		}
		
		vo.setProduct(product);
		
		//图片
		List<ProductImageVo> image = new ArrayList<ProductImageVo>();
		for(FileVo i : req.getImgList()){
			ProductImageVo v = new ProductImageVo();
			BeanUtils.copyProperties(v, i);
			image.add(v);
		}
		vo.setImage(image);
		
		//配送清空机构及网点
		if("0".equals(req.getDeliveryOption())){
			vo.setOrgCodes(null);
			vo.setOutletIds(null);
		}else{
			//机构
			if(StringUtil.isNotBlank(req.getOrgCodes())){
				List<String> orgCodes = new ArrayList<String>();
				String[] orgcode = req.getOrgCodes().split(",");
				for(String s : orgcode){
					orgCodes.add(s);
				}
				vo.setOrgCodes(orgCodes);
			}
			
			//提货网点
			if(StringUtil.isNotBlank(req.getOutletIds())){
				List<String> outletIds = new ArrayList<String>();
				String[] outletid = req.getOutletIds().split(",");
				for(String s : outletid){
					outletIds.add(s);
				}
				vo.setOutletIds(outletIds);
			}
		}
		
		BossUser user = (BossUser)request.getAttribute(Constants.BOSS_USER);
		OriginVo ori = new OriginVo();
		ori.setClientId(req.getClientId());
		ori.setPlatType(PlatType.boss);
		ori.setOperatorIp(Constants.getIpAddr(request));
		ori.setOperatorId(user.getId());
		
		ResultVo res = bossProductService.updateProduct(ori, vo);
		if(!Constants.RESULT_SUCCESS.equals(res.getResultCode())){
			throw new BossException(res.getResultCode(), res.getResultDesc());
		}
		
		return map;
	}

	/**
	 * 商品删除
	 * @tilte deleteByIds
	 * @author zxl
	 * @date 2015年7月27日 上午10:18:39
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> deleteByIds(HttpServletRequest request,String productId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		BossUser user = (BossUser)request.getAttribute(Constants.BOSS_USER);
		OriginVo ori = new OriginVo();
//		ori.setClientId(req.getClientId());
		ori.setPlatType(PlatType.boss);
		ori.setOperatorIp(Constants.getIpAddr(request));
		ori.setOperatorId(user.getId());
		
		ResultVo res = bossProductService.deleteProduct(ori, productId);
		if(!Constants.RESULT_SUCCESS.equals(res.getResultCode())){
			throw new BossException(res.getResultCode(), res.getResultDesc());
		}
		
		return map;
	}
	
	/**
	 * 
	 * <p>功能简述：查询商品详情</p> 
	 * <p>使用说明：查询商品详情</p> 
	 * <p>创建时间：2015年4月29日下午6:00:41</p>
	 * <p>作者: 陈明灿</p>
	 * @param productId
	 * @return
	 * @throws TException 
	 */
	public Map<String, Object> detail(String productId) throws TException {
		Map<String, Object> map = new HashMap<String, Object>();
		ProductOperateVoReq req = new ProductOperateVoReq();
		req.setProductId(productId);
		ProductInfoVo productInfoVo = productService
				.getMerchantProductDetail(req);
		LogCvt.info("商品详情请求--返回结果:" + JSON.toJSONString(productInfoVo));
		if (null != productInfoVo) {
			//获取客户端集合
			List<ClientVo> client = clientService.getClient(new ClientVo());
			// Date date = null;
			// ProductVoRes res = new ProductVoRes();
			ProductInfoVoReq res = new ProductInfoVoReq();
			ProductVo product = productInfoVo.getProduct();
			BeanUtils.copyProperties(res, product);
			//开始时间和结束时间(将日期转成yyyy-MM-dd HH:mm:ss 格式)
			if(StringUtil.isNotBlank(product.getStartTime())
					&& product.getStartTime() != 0){
				res.setStartTime(DateUtil.longToString(product.getStartTime()));
			}
			if(StringUtil.isNotBlank(product.getEndTime())
					&& product.getEndTime() != 0){
				res.setEndTime(DateUtil.longToString(product.getEndTime()));
			}
			// 转化client中方名字
			for (ClientVo clientVo : client) {
				if (product.getClientId().equals(clientVo.getClientId())) {
					res.setClientName(clientVo.getName());
					break;
				}
			}
			res.setProductSn(product.getProductId());
			ProductCategoryVo productCategoryVo = productInfoVo
					.getProductCategory();
			// 最大限量购
			if (null != productInfoVo.getBuyLimit()) {
				res.setMax(productInfoVo.getBuyLimit().getMax());
			}
			//分类名称
			if (null != productCategoryVo) {
				res.setProductType(productCategoryVo.getName());
			}

			List<ProductOutletVo> orgNameVo = productInfoVo.getOutlet();
			List<String> orgNameList = new ArrayList<String>();
			if(orgNameVo != null && orgNameVo.size() > 0){
				for(ProductOutletVo outlet:orgNameVo){
					orgNameList.add(outlet.getOutletName());
				}
			}
			res.setOrgNameList(orgNameList);
			
			// 封装图片信息
			List<ProductImageVo> image = productInfoVo.getImage();
			FileVo file = null;
			//图片数据封装
			List<FileVo> piclist = new ArrayList<FileVo>();
			if (null != image && image.size() > 0) {
				for (ProductImageVo productImageVo : image) {
					file = new FileVo();
					file.setLarge(productImageVo.getLarge());
					file.setSource(productImageVo.getSource());
					file.setMedium(productImageVo.getMedium());
					file.setThumbnail(productImageVo.getThumbnail());
					piclist.add(file);
				}
			}
			res.setFiles(piclist);
			LogCvt.info("商品详情请求--封装结果:" + JSON.toJSONString(res));
			map.put("product", res);
		}
		return map;
	}
	/**
	 * 
	 * <p>功能简述：商品列表</p> 
	 * <p>使用说明：查询积分商品</p> 
	 * <p>创建时间：2015年5月1日上午11:52:44</p>
	 * <p>作者: 陈明灿</p>
	 * @throws TException 
	 */
	public Map<String, Object> list(ProductListVo req) throws TException {
		// 封装请求后台的Vo
		ProductFilterVoReq thriftReq = new ProductFilterVoReq();
		Map<String, Object> mapReq = new HashMap<String, Object>();
		if (StringUtil.isNotBlank(req.getName())) {
			mapReq.put("name", req.getName());// 商品名称
		}
		if (null != req.getIsMarketable()) {
			mapReq.put("isMarketable", req.getIsMarketable());// 上/下架状态
		}
		Integer scoreScope = req.getScoreScope();
		if (null != scoreScope && 1 == scoreScope) {
			mapReq.put("priceStart", 0);
			mapReq.put("priceEnd", 99);
		}
		if (null != scoreScope && 2 == scoreScope) {
			mapReq.put("priceStart", 100);
			mapReq.put("priceEnd", 499);
		}
		if (null != scoreScope && 3 == scoreScope) {
			mapReq.put("priceStart", 500);
			mapReq.put("priceEnd", 999);
		}
		if (null != scoreScope && 4 == scoreScope) {
			mapReq.put("priceStart", 1000);
		}
		mapReq.put("type", req.getType());// 商品类型(积分商品)
		// mapReq.put("clientId", req.getClientId());
		String filter = JSON.toJSONString(mapReq);
		thriftReq.setFilter(filter);
		thriftReq.setClientId(req.getClientId());
		// thriftReq.setClientId(req.getClientId());
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(req.getPageNumber());
		pageVo.setPageSize(req.getPageSize());
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<ProductVoRes> list = null;
		// 请求后台接口
		ProductBriefPageVo res = productService.findMerchantProductsByPage(
				thriftReq, pageVo);
		LogCvt.info("查询商品列表请求--返回结果:" + JSON.toJSONString(res));
		// 封装后台返回的数据
		if (null != res.getProductBriefInfoVoList()
				&& null != res.getPage()) {
			Page page = new Page();
			// 封装页码数据
			BeanUtils.copyProperties(page, res.getPage());
			map.put("page", page);
			List<ProductBriefInfoVo> voList = res
					.getProductBriefInfoVoList();
			list = new ArrayList<ProductVoRes>();
			// 封装Vo
			// Date date = null;
			for (ProductBriefInfoVo proVo : voList) {
				ProductVoRes resVo = new ProductVoRes();
				BeanUtils.copyProperties(resVo, proVo);
				if(StringUtil.isNotBlank(proVo.getCreateTime())
						&& proVo.getCreateTime() != 0){
					resVo.setCreateTime(DateUtil.longToString(proVo
						.getCreateTime()));
				}
				// 兑换开始和结束时间
				if(StringUtil.isNotBlank(proVo.getStartTime())
						&& proVo.getStartTime() != 0){
					resVo.setBeginTime(DateUtil.longToString(proVo
							.getStartTime()));
				}
				if(StringUtil.isNotBlank(proVo.getEndTime())
						&& proVo.getEndTime() != 0){
					resVo.setEndTime(DateUtil.longToString(proVo.getEndTime()));
				}
				resVo.setProductSn(proVo.getProductId());
				// 商品分类
				resVo.setProductType(proVo.getCategoryName());
				list.add(resVo);
			}
			LogCvt.info("查询商品列表请求--封装结果:" + JSON.toJSONString(list));
			map.put("list", list);
		}
		return map;
	}
	
	/**
	 * 商品管理
	 * @author yfy
	 * @date 2015年7月27日 上午10:12:28
	 * @param req
	 * @return
	 * @throws TException
	 */
	public Map<String, Object> bossList(ProductListVo req) throws TException {
		// 封装请求后台的Vo
		BossProductFilterVo bossProductReq = new BossProductFilterVo();
		if (StringUtil.isNotBlank(req.getClientId())) {
			bossProductReq.setClientId(req.getClientId());//所属行(客户端)
		}
		if (StringUtil.isNotBlank(req.getType())) {
			bossProductReq.setType(req.getType());//商品类型
		}
		if (StringUtil.isNotBlank(req.getProductCotegory())) {
			bossProductReq.setCategoryId(Long.valueOf(req.getProductCotegory()));//商品分类
		}
		if (StringUtil.isNotBlank(req.getIsMarketable())) {
			bossProductReq.setMarketableStatus(req.getIsMarketable()+"");// 上/下架状态
		}
		if (StringUtil.isNotBlank(req.getAuditState())) {
			bossProductReq.setAuditStatus(req.getAuditState()+"");//审核状态
		}
		if (StringUtil.isNotBlank(req.getName())) {
			bossProductReq.setName(req.getName());// 商品名称
		}
		if (StringUtil.isNotBlank(req.getPlatType())) {
			bossProductReq.setPlatType(req.getPlatType());//录入渠道
		}
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(req.getPageNumber());
		pageVo.setPageSize(req.getPageSize());
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<BossProductVoRes> list = new ArrayList<BossProductVoRes>();
		// 请求后台接口
		BossProductListPageVo res = bossProductService.findProductsByPage(bossProductReq, pageVo);
		LogCvt.info("查询商品列表请求--返回结果:" + JSON.toJSONString(res));
		// 封装后台返回的数据
		if (null != res.getProductListVos()&& null != res.getPage()) {
			Page page = new Page();
			BeanUtils.copyProperties(page, res.getPage());// 封装分页数据zl
			List<BossProductListVo> voList = res.getProductListVos();
			for (BossProductListVo proVo : voList) {
				BossProductVoRes resVo = new BossProductVoRes();
				BeanUtils.copyProperties(resVo, proVo);// 封装商品数据
				list.add(resVo);
			}
			map.put("list", list);
			map.put("page", page);
		}
		return map;
	}
	
	/**
	 * 商品管理详情查询
	 * @author yfy
	 * @date 2015年7月27日 上午10:37:31
	 * @param req
	 * @return
	 * @throws TException
	 */
	public Map<String, Object> bossDetail(String productId) throws TException {
		Map<String, Object> map = new HashMap<String, Object>();
		BossProductDetailVo bossProductDetailVo = bossProductService.getBossProductDetail(productId);
		LogCvt.info("商品详情请求--返回结果:" + JSON.toJSONString(bossProductDetailVo));
		if (null != bossProductDetailVo && bossProductDetailVo.getProduct() != null) {
			BossProductInfoVoRes productVoRes = new BossProductInfoVoRes();
			BossProductVo product = bossProductDetailVo.getProduct();
			BeanUtils.copyProperties(productVoRes, product);
			//提货网点的法人行社
			productVoRes.setOrgCodes(bossProductDetailVo.getOrgCodes());
			//提货网点ID和提货网点名称
			List<ProductOutletVo> outlets = bossProductDetailVo.getOutlets();
			List<BossOutletVoRes> outletVoList = null;
			if(outlets != null && outlets.size() > 0){
				outletVoList = new ArrayList<BossOutletVoRes>();
				for(ProductOutletVo productOutletVo : outlets){
					BossOutletVoRes bossOutletVoRes = new BossOutletVoRes();
					BeanUtils.copyProperties(bossOutletVoRes, productOutletVo);
					outletVoList.add(bossOutletVoRes);
				}
			}
			productVoRes.setOutletVoList(outletVoList);
			// 封装图片信息
			List<ProductImageVo> image = bossProductDetailVo.getImage();
			FileVo file = null;
			List<FileVo> piclist = new ArrayList<FileVo>();
			if (null != image && image.size() > 0) {
				for (ProductImageVo productImageVo : image) {
					file = new FileVo();
					file.setLarge(productImageVo.getLarge());
					file.setSource(productImageVo.getSource());
					file.setMedium(productImageVo.getMedium());
					file.setThumbnail(productImageVo.getThumbnail());
					piclist.add(file);
				}
			}
			productVoRes.setImgList(piclist);
			map.put("product", productVoRes);
		}
		return map;
	}
	
	/**
	 * 查询提货网点
	 * @tilte outlet
	 * @author zxl
	 * @date 2015年7月28日 上午9:26:33
	 * @param request
	 * @param clientId
	 * @param orgCode
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> outlet(HttpServletRequest request,String clientId,String orgCode) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		OrgVo orgVo = new OrgVo(); 
		orgVo.setClientId(clientId);
		orgVo.setOrgLevel("2");
		orgVo.setIsEnable(true);
		List<OrgVo> orgList = orgService.getOrg(orgVo);
		if(orgList!=null&&orgList.size()>0){
			for(OrgVo org : orgList){
				List<OutletVo> list = outletService.getSubBankOutlet(clientId, org.getOrgCode());
				if(list!=null&&list.size()>0){
					List<HashMap<String,String>> l = new ArrayList<HashMap<String, String>>();
					for (OutletVo outletVo : list) {
						HashMap<String,String> h = new HashMap<String, String>();
						h.put("outletId", outletVo.getOutletId());
						h.put("outletName", outletVo.getOutletName());
						l.add(h);
					}
					map.put(org.getOrgCode(), l);
				}
			}
		}
		return map;
	}
	
	
	/**
	 * vip规则查询
	 * @tilte viprule
	 * @author zxl
	 * @date 2015年7月30日 上午10:58:38
	 * @param request
	 * @param clientId
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> viprule(HttpServletRequest request,String clientId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<HashMap<String,String>> l = new ArrayList<HashMap<String,String>>();
		
		List<VipProductVo> list = bossProductService.getVipProducts(clientId);
		
		if(list!=null&&list.size()>0){
			for(VipProductVo vo : list){
				HashMap<String,String> h = new HashMap<String, String>();
				h.put("vipId", vo.getVipId());
				h.put("activitiesName", vo.getActivitiesName());
				l.add(h);
			}
		}
		
		map.put("list", l);
		map.put("code", Constants.RESULT_SUCCESS);
		return map;
	}
	
	/**
	 * 审核商品
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年7月29日 下午4:53:48
	 * @param pojo
	 * @return
	 */
	public Map<String, Object> auditProduct(AuditProductReq pojo, OriginVo org) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		//封装请求参数
		BossAuditProcessVo req = new BossAuditProcessVo();
		BeanUtils.copyProperties(req, pojo);
		//调用SERVER端接口
		ResultVo resp = bossProductService.auditProduct(org, req);
		if(!Constants.RESULT_SUCCESS.equals(resp.getResultCode())) {
			throw new BossException(resp.getResultCode(), resp.getResultDesc());
		} else {
			map.put("code", resp.getResultCode());
			map.put("message", resp.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 上下架商品
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年7月29日 下午5:27:12
	 * @param pojo
	 * @return
	 */
	public Map<String, Object> upDown(UpdownProductReq pojo, OriginVo org) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		//封装请求参数
		BossProductStatusVoReq req = new BossProductStatusVoReq();
		BeanUtils.copyProperties(req, pojo);
		//调用SERVER端接口
		ResultVo resp = bossProductService.updateProductStatus(org, req);
		if(!Constants.RESULT_SUCCESS.equals(resp.getResultCode())) {
			throw new BossException(resp.getResultCode(), resp.getResultDesc());
		} else {
			map.put("code", resp.getResultCode());
			map.put("message", resp.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 
	 * <p>功能简述：保存商品</p> 
	 * <p>使用说明：新增或者修改商品</p> 
	 * <p>创建时间：2015年4月29日下午2:07:45</p>
	 * <p>作者: 陈明灿</p>
	 * @param req
	 * @return
	 * @throws TException 
	 * @throws BossException 
	 */
	public Map<String, Object> saveOrUpdate(GiftProductVoReq req) throws TException, BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		OriginVo ov = new OriginVo();
		ov.setPlatType(PlatType.boss);
		ProductInfoVo pio = new ProductInfoVo();
		ProductVo product = new ProductVo();
		product.setClientId(req.getClientId());
		product.setName(req.getName());// 礼品名称
		/**页面没有全名字段,默认给name值*/
		product.setFullName(req.getName());// 全名
		product.setType(req.getType());// 礼品类型
		product.setBriefIntroduction(req.getBriefIntroduction());// 副标题
		//数字类型转化
		if(StringUtil.isNotBlank(req.getPrice())){
			product.setPrice(Double.parseDouble(req.getPrice()));// 积分(价格)
		}
		if(StringUtil.isNotBlank(req.getStore())){
			product.setStore(Integer.parseInt(req.getStore()));// 库存
		}
		if(StringUtil.isNotBlank(req.getPrice())){
			product.setMarketPrice(Double.parseDouble(req.getPrice()));// 市场价
		}
		product.setBriefIntroduction(req.getBriefIntroduction());// 副标题
		product.setBuyKnow(req.getBuyKnow());// 须知
		product.setIntroduction(req.getIntroduction());// 商品详情
		product.setAuditEndOrgCode("0");// 审核状态
		product.setAuditOrgCode("0");
		product.setAuditStartOrgCode("0");
		product.setOrgCode(req.getOrgCode());// 所属机构
		// 兑换开始和结束时间
		product.setStartTime(req.getBeginTime());
		product.setEndTime(req.getEndTime());
		// 限购量
		if(req.getMax()>0){
			ProductBuyLimitVo limitVo = new ProductBuyLimitVo();
			limitVo.setMax(req.getMax());
			product.setIsLimit(true);
			pio.setBuyLimit(limitVo);
		}else{
			product.setIsLimit(false);
		}
		pio.setProduct(product);
		// 获取图片并注入
		List<ProductImageVo> voList = new ArrayList<ProductImageVo>();
		List<FileVo> files = req.getFiles();
		if (files != null && files.size() > 0) {
			for (FileVo fileVo : files) {
				ProductImageVo image = new ProductImageVo();
				image.setLarge(fileVo.getLarge());
				image.setMedium(fileVo.getMedium());
				image.setSource(fileVo.getSource());
				image.setThumbnail(fileVo.getThumbnail());
				voList.add(image);
			}
		}
		pio.setImage(voList);
		if (null != req.getProductId()) {
			// 设置商品Id,有商品id说明为修改商品数据....
			product.setProductId(req.getProductId());
			ResultVo res = productService.updateProduct(ov, pio);
			LogCvt.info("更新商品请求--返回结果:" + JSON.toJSONString(res));
			String resultVo = res.getResultCode();
			if (Constants.RESULT_SUCCESS.equals(resultVo)) {
				map.put("code", Constants.RESULT_SUCCESS);
				map.put("message", res.getResultDesc());
			} else {
				throw new BossException(res.getResultCode(),  res.getResultDesc());
			}

		} else {
			//无商品id,说明为新增商品操作....
			AddProductVoRes res2 = productService.addProduct(ov, pio);
			LogCvt.info("新增商品请求--返回结果:" + JSON.toJSONString(res2));
			if (Constants.RESULT_SUCCESS.equals(res2.getResult().getResultCode())) {
				map.put("code", Constants.RESULT_SUCCESS);
				map.put("message", res2.getResult().getResultDesc());
			} else {
				throw new BossException(res2.getResult().getResultCode(),  res2.getResult().getResultDesc());
			}
		}
		return map;
	}
	/**
	 * 
	 * <p>功能简述：删除商品</p> 
	 * <p>使用说明  根据商品ids删除商品</p> 
	 * <p>创建时间：2015年4月29日下午3:42:23</p>
	 * <p>作者: 陈明灿</p>
	 * @param ids
	 * @return
	 * @throws TException 
	 * @throws BossException 
	 */
	public Map<String, Object> deleteByIds(String[] ids) throws TException, BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		OriginVo ov = new OriginVo();
		ov.setPlatType(PlatType.boss);
		List<String> idList = Arrays.asList(ids);
		ProductStatusBatchVoReq reqVo = new ProductStatusBatchVoReq();
		reqVo.setProductIds(idList);
		ResultVo resultVo = productService.deleteProductBatch(ov, reqVo);
		LogCvt.info("删除商品请求--返回结果:" + JSON.toJSONString(resultVo));
		// String desc = resultVo.getResultDesc();
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
	 * <p>功能简述：商品上下架</p> 
	 * <p>使用说明：根据ids上下架商品</p> 
	 * <p>创建时间：2015年4月29日下午4:34:38</p>
	 * <p>作者: 陈明灿</p>
	 * @param ids
	 * @return
	 * @throws BossException 
	 * @throws TException 
	 */
	public Map<String, Object> sellOrNot(String[] ids, String flag) throws BossException, TException {
		Map<String, Object> map = new HashMap<String, Object>();
		OriginVo ov = new OriginVo();
		ov.setPlatType(PlatType.boss);
		ProductStatusBatchVoReq reqVo = new ProductStatusBatchVoReq();
		//将数组转成集合
		List<String> list = Arrays.asList(ids);
		reqVo.setProductIds(list);
		reqVo.setStatus(flag);
		ResultVo resultVo = productService.updateProductStatusBatch(ov,
				reqVo);
		LogCvt.info("上下架商品请求--返回结果:" + JSON.toJSONString(resultVo));
		if (Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())) {
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", resultVo.getResultDesc());
		}else{
			throw new BossException(resultVo.getResultCode(),  resultVo.getResultDesc());
		}
		return map;
	}
}
