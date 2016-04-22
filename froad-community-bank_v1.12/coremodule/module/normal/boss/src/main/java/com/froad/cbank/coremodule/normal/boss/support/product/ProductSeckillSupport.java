package com.froad.cbank.coremodule.normal.boss.support.product;

import java.text.DecimalFormat;
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
import com.froad.cbank.coremodule.normal.boss.pojo.FileVo;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.product.OrdinaryProductVoRes;
import com.froad.cbank.coremodule.normal.boss.pojo.product.SeckillProductDetil;
import com.froad.cbank.coremodule.normal.boss.pojo.product.SeckillProductSetVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.product.SeckillProductVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.product.SeckillProductVoRes;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.thrift.service.ProductSeckillService;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.PlatType;
import com.froad.thrift.vo.ProductFilterVo;
import com.froad.thrift.vo.ProductFilterVoReq;
import com.froad.thrift.vo.ProductImageVo;
import com.froad.thrift.vo.ProductListPageVo;
import com.froad.thrift.vo.ProductListVo;
import com.froad.thrift.vo.ProductOutletVo;
import com.froad.thrift.vo.ProductSeckillInfoVo;
import com.froad.thrift.vo.ProductSeckillPageVo;
import com.froad.thrift.vo.ProductSeckillVo;
import com.froad.thrift.vo.ResultVo;

@Service
public class ProductSeckillSupport {
	
	@Resource
	ProductSeckillService.Iface productSeckillService;
	
	@Resource
	ProductService.Iface productService;
	
	/**
	 * 
	 * <p>功能简述：设置秒杀商品</p> 
	 * <p>使用说明：新增秒杀商品</p> 
	 * <p>创建时间：2015年5月13日下午2:07:45</p>
	 * <p>作者: chenhao</p>
	 * @param req
	 * @return
	 * @throws TException 
	 * @throws BossException 
	 */
	public Map<String, Object> add(SeckillProductSetVoReq req) throws TException, BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		OriginVo ov = new OriginVo();
		ov.setPlatType(PlatType.boss);
		List<ProductSeckillVo> productSeckillVoList = new ArrayList<ProductSeckillVo>();
		long now = System.currentTimeMillis();
		List<SeckillProductVoReq> productReqList = req.getProductList();
		for (SeckillProductVoReq productReq : productReqList) {
			ProductSeckillVo product = new ProductSeckillVo();
			product.setClientId(productReq.getClientId());
			product.setProductId(productReq.getProductId());
			if(StringUtil.isNotBlank(productReq.getSecPrice())){
				product.setSecPrice(Double.parseDouble(productReq.getSecPrice()));
			}
			if(StringUtil.isNotBlank(productReq.getVipSecPrice())){
				product.setVipSecPrice(Double.parseDouble(productReq.getVipSecPrice()));
			}
			product.setBuyLimit(productReq.getBuyLimit());
			product.setSecStore(productReq.getSecStore());
			
			product.setCreateTime(now);
			product.setStartTime(productReq.getStartDate());
			product.setEndTime(productReq.getEndDate());
			
			product.setIsMarketable("0"); // 未上架
			product.setAuditState("1");// 已审核
			product.setAuditOrgCode("0");
			product.setAuditStartOrgCode("0");
			product.setAuditEndOrgCode("0");
			product.setAuditStage("0");
			product.setAuditTime(now);
			product.setAuditComment("Boss无需审批");
			product.setOrgCode(req.getOrgCode());
			
			productSeckillVoList.add(product);
		}
		ResultVo res = productSeckillService.addProductSeckill(productSeckillVoList, ov);
		LogCvt.info("保存商品请求--返回结果:" + JSON.toJSONString(res));
		
		if (Constants.RESULT_SUCCESS.equals(res.getResultCode())) {
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", res.getResultDesc());
		} else {
			throw new BossException(res.getResultCode(),  res.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 
	 * <p>功能简述：修改秒杀商品</p> 
	 * <p>使用说明：修改秒杀商品</p> 
	 * <p>创建时间：2015年5月14日下午2:07:45</p>
	 * <p>作者: chenhao</p>
	 * @param req
	 * @return
	 * @throws TException 
	 * @throws BossException 
	 */
	public Map<String, Object> update(SeckillProductVoReq req) throws TException, BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		OriginVo ov = new OriginVo();
		ov.setPlatType(PlatType.boss);
		ProductSeckillVo product = new ProductSeckillVo();
		product.setClientId(req.getClientId());
		product.setProductId(req.getProductId());
		if(StringUtil.isNotBlank(req.getSecPrice())){
			product.setSecPrice(Double.valueOf(req.getSecPrice()));
		}
		if(StringUtil.isNotBlank(req.getVipSecPrice())){
			product.setVipSecPrice(Double.valueOf(req.getVipSecPrice()));
		}
		product.setSecStore(req.getSecStore());
		product.setBuyLimit(req.getBuyLimit());
		product.setStartTime(Long.valueOf(req.getStartDate()));
		product.setEndTime(Long.valueOf(req.getEndDate()));
		
		ResultVo res = productSeckillService.updateProductSeckill(product, ov);
		LogCvt.info("保存商品请求--返回结果:" + JSON.toJSONString(res));
		if (Constants.RESULT_SUCCESS.equals(res.getResultCode())) {
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", res.getResultDesc());
		} else {
			throw new BossException(res.getResultCode(),  res.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 
	 * <p>功能简述：删除秒杀商品</p> 
	 * <p>使用说明  根据商品ids删除商品</p> 
	 * <p>创建时间：2015年5月14日下午3:42:23</p>
	 * <p>作者: chenhao</p>
	 * @param ids
	 * @return
	 * @throws TException 
	 * @throws BossException 
	 */
	public Map<String, Object> delete(String productId, String clientId) throws TException, BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		OriginVo ov = new OriginVo();
		ov.setPlatType(PlatType.boss);
		ProductSeckillVo reqVo = new ProductSeckillVo();
		reqVo.setProductId(productId);
		reqVo.setClientId(clientId);
		
		ResultVo resultVo = productSeckillService.deleteProductSeckill(reqVo, ov);
		LogCvt.info("删除秒杀商品请求--返回结果:" + JSON.toJSONString(resultVo));
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
	 * <p>功能简述：修改秒杀商品上下架</p> 
	 * <p>使用说明：根据ids修改秒杀商品上下架</p> 
	 * <p>创建时间：2015年5月14日下午4:34:38</p>
	 * <p>作者: chenhao</p>
	 * @param ids
	 * @return
	 * @throws BossException 
	 * @throws TException 
	 */
	public Map<String, Object> updateStatus(String productId, String isMarketable,String clientId) throws BossException, TException {
		Map<String, Object> map = new HashMap<String, Object>();
		OriginVo ov = new OriginVo();
		ov.setPlatType(PlatType.boss);
		ProductSeckillVo reqVo = new ProductSeckillVo();
		reqVo.setProductId(productId);
		reqVo.setIsMarketable(isMarketable);// 0未上架1-已上架2-已下架
		reqVo.setClientId(clientId);
		ResultVo resultVo = productSeckillService.updateProductSeckillStatus(reqVo, ov);
		LogCvt.info("上下架秒杀商品请求--返回结果:" + JSON.toJSONString(resultVo));
		if (Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())) {
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", resultVo.getResultDesc());
		}else{
			throw new BossException(resultVo.getResultCode(),  resultVo.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 
	 * <p>功能简述：查询秒杀商品详情</p> 
	 * <p>使用说明：查询秒杀商品详情</p> 
	 * <p>创建时间：2015年5月14日下午6:00:41</p>
	 * <p>作者: chenhao</p>
	 * @param productId
	 * @return
	 * @throws TException 
	 */
	public Map<String, Object> detail(String productId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		ProductSeckillVo req = new ProductSeckillVo();
		req.setProductId(productId);
		ProductSeckillInfoVo productInfo = productSeckillService.getProductSeckillDetail(req);
		LogCvt.info("秒杀商品详情请求--返回结果:" + JSON.toJSONString(productInfo));
		
		if (productInfo != null) {
			SeckillProductDetil productDetil = new SeckillProductDetil();
			ProductSeckillVo product = productInfo.getProductSeckill();
			if (product != null) {
				DecimalFormat df = new DecimalFormat("#0.00");
				productDetil.setProductName(product.getName());// 秒杀商品名称
				productDetil.setProductType(product.getType());// 秒杀商品类型
				if(StringUtil.isNotBlank(product.getMarketPrice())){
					productDetil.setMarketPrice(String.valueOf(df.format(product.getMarketPrice())));// 市场价
				}
				if(StringUtil.isNotBlank(product.getSecPrice())){
					productDetil.setSecPrice(String.valueOf(df.format(product.getSecPrice())));// 秒杀价
				}
				if(StringUtil.isNotBlank(product.getVipSecPrice())){
					productDetil.setVipSecPrice(String.valueOf(df.format(product.getVipSecPrice())));// VIP秒杀价
				}
				if(StringUtil.isNotBlank(product.getSecStore())){
					productDetil.setSecStore(String.valueOf(product.getSecStore()));// 秒杀库存数量
				}
				if(StringUtil.isNotBlank(product.getBuyLimit())){
					productDetil.setBuyLimit(String.valueOf(product.getBuyLimit()));// 秒杀限购数量
				}
				if(StringUtil.isNotBlank(product.getStartTime())){
					productDetil.setStartDate(String.valueOf(product.getStartTime()));// 秒杀开始时间
				}
				if(StringUtil.isNotBlank(product.getEndTime())){
					productDetil.setEndDate(String.valueOf(product.getEndTime()));// 秒杀结束时间
				}
				productDetil.setDescription(product.getBriefIntroduction());// 商品简介
				productDetil.setDistributionType(product.getDeliveryOption());// 配送方式
				productDetil.setProductKnow(product.getBuyKnow());// 购买须知
				productDetil.setProductDetails(product.getIntroduction());// 商品详情
				productDetil.setAuditComment(product.getAuditComment());
			}
			
			List<String> bankOrgList = new ArrayList<String>();
			List<String> bankOrgNameList = new ArrayList<String>();
			if (productInfo.getOutlet() != null) {
				List<ProductOutletVo> productOutletList = productInfo.getOutlet();
				for (ProductOutletVo productOutletVo : productOutletList) {
					bankOrgList.add(productOutletVo.getOutletId());
					bankOrgNameList.add(productOutletVo.getOutletName());
				}
			}
			productDetil.setOrgCodeList(bankOrgList);// 网点
			productDetil.setOrgNameList(bankOrgNameList);// 网点名称
			productDetil.setParentOrgCodeList(productInfo.getOrgCodes());//发人行社
			
			// 商品图片
			ArrayList<FileVo> imageList = new ArrayList<FileVo>();
			List<ProductImageVo> productImageList = productInfo.getImage();
			if (productImageList != null && productImageList.size() > 0) {
				for (ProductImageVo productImage : productImageList) {
					FileVo fileImage = new FileVo();
//						fileImage.setTitle(productImage.getTitle());// 图片标题
					fileImage.setSource(productImage.getSource());// 图片原图地址
					fileImage.setLarge(productImage.getLarge());// 图片大图地址
					fileImage.setMedium(productImage.getMedium());// 图片中图地址
					fileImage.setThumbnail(productImage.getThumbnail());// 图片小图地址
					imageList.add(fileImage);
				}
			}
			productDetil.setFileList(imageList);// 商品图片
		
			LogCvt.info("秒杀商品详情请求--封装结果:" + JSON.toJSONString(productDetil));
			map.put("product", productDetil);
		}
		return map;
	}
	
	/**
	 * <p>功能简述：秒杀商品列表</p> 
	 * <p>使用说明：查询秒杀商品列表</p> 
	 * <p>创建时间：2015年5月14日上午11:52:44</p>
	 * <p>作者: chenhao</p>
	 * @throws TException 
	 */
	public Map<String, Object> list(SeckillProductVoReq req) throws Exception {
		ProductFilterVoReq thriftReq = new ProductFilterVoReq();
		Map<String, Object> mapReq = new HashMap<String, Object>();
		// 条件过滤
		mapReq.put("parentOrgCode", req.getParentOrgCode());
		mapReq.put("orgCode", req.getOrgCode());
		mapReq.put("type", req.getProductType());
		mapReq.put("name", req.getProductName());
		mapReq.put("startTime", req.getStartDate());
		mapReq.put("endTime", req.getEndDate());
		mapReq.put("auditState", req.getAuditState());
		
		String filter = JSON.toJSONString(mapReq);
		thriftReq.setFilter(filter);
		
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(req.getPageNumber());
		pageVo.setPageSize(req.getPageSize());
		
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<SeckillProductVoRes> list = new ArrayList<SeckillProductVoRes>();
		// 请求后台接口
		ProductSeckillPageVo res = productSeckillService.findSeckillByPage(thriftReq, pageVo);
		LogCvt.info("查询商品列表请求--返回结果:" + JSON.toJSONString(res));
		// 封装后台返回的数据
		if (null != res.getSeckillVoList() && null != res.getPage()) {
			List<ProductSeckillVo> productSeckillVoList = res.getSeckillVoList();
			
			if (productSeckillVoList != null && productSeckillVoList.size() > 0) {
				for (ProductSeckillVo productSeckillVo : productSeckillVoList) {
					SeckillProductVoRes seckillProduct = new SeckillProductVoRes();
					seckillProduct.setProductId(productSeckillVo.getProductId());// 商品ID
					seckillProduct.setClientId(productSeckillVo.getClientId());// 客户端ID
					seckillProduct.setProductName(productSeckillVo.getName());// 商品名称
					seckillProduct.setProductType(productSeckillVo.getType());
					seckillProduct.setMerchantId(productSeckillVo.getMerchantId());
					seckillProduct.setMerchantName(productSeckillVo.getMerchantName());
					seckillProduct.setOrgCode(productSeckillVo.getOrgCode());
					seckillProduct.setOrgName(productSeckillVo.getOrgName());
					seckillProduct.setPrice(productSeckillVo.getPrice()+"");// 优惠价格
					seckillProduct.setSecPrice(productSeckillVo.getSecPrice()+"");// 秒杀价格
					seckillProduct.setVipSecPrice(productSeckillVo.getVipSecPrice()+"");// VIP秒杀价格
					seckillProduct.setBuyLimit(productSeckillVo.getBuyLimit());// 秒杀限量
					seckillProduct.setStartDate(productSeckillVo.getStartTime() + "");// 秒杀开始时间
					seckillProduct.setEndDate(productSeckillVo.getEndTime() + "");// 秒杀结束时间
					seckillProduct.setStore(productSeckillVo.getStore());// 商品库存
					seckillProduct.setSecStore(productSeckillVo.getSecStore());// 秒杀库存
					seckillProduct.setAuditState(productSeckillVo.getAuditState());//审核状态
					seckillProduct.setIsMarketable(productSeckillVo.getIsMarketable());// 上下架
					
					list.add(seckillProduct);
				}
		
				LogCvt.info("查询商品列表请求--封装结果:" + JSON.toJSONString(list));
				map.put("list", list);
				
			}
			Page page = new Page();
			// 封装页码数据
			BeanUtils.copyProperties(page, res.getPage());
			map.put("page", page);
		}
		return map;
	}
	
	
	/**
	 * 
	 * <p>功能简述：非秒杀商品列表</p> 
	 * <p>使用说明：查询非秒杀商品列表查询,type为商品类型,可查不同类型商品</p> 
	 * <p>创建时间：2015年4月27日下午6:01:42</p>
	 * <p>作者: chenhao</p>
	 * @param req
	 * @return
	 * @throws TException 
	 */
	public Map<String, Object> nslist(SeckillProductVoReq req) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(req.getPageNumber());
		pageVo.setPageSize(req.getPageSize());
		
		List<OrdinaryProductVoRes> list = new ArrayList<OrdinaryProductVoRes>();
		
		ProductFilterVo vo = new ProductFilterVo();
		if(StringUtils.isNotBlank(req.getClientId())){
			vo.setClientId(req.getClientId());
		}
		if(StringUtils.isNotBlank(req.getProductName())){
			vo.setProductName(req.getProductName());
		}
		if (StringUtils.isEmpty(req.getProductType())) {
			//团购,预售,名优特惠,精品商城
			vo.setType("1,2,3,6");
		} else {
			vo.setType(req.getProductType());
		}
		//非秒杀
		vo.setIsSeckill("0");
		vo.setAuditState("1");
		
		ProductListPageVo res = productService.findGoodsByPage(vo, pageVo);
		// 封装后台返回的数据
		List<ProductListVo> proList = res.getProductListVos();
		if (proList != null && proList.size() > 0) {
			for (ProductListVo p : proList) {
				OrdinaryProductVoRes product = new OrdinaryProductVoRes();
				product.setClientId(p.getClientId());
				product.setMerchantId(p.getMerchantId());
				product.setMerchantName(p.getMerchantName());
				product.setProductId(p.getProductId());// 商品ID
				product.setProductName(p.getName());// 商品名称
				product.setProductType(p.getProductType());
				product.setPrice(p.getPrice() + "");
				product.setStore(p.getStore() + "");
				product.setIsMarketable(p.getMarketableStatus());// 未上架
				list.add(product);
			}
		}
		Page page = new Page();
		// 封装页码数据
		BeanUtils.copyProperties(page, res.getPage());
		map.put("page", page);
		map.put("list", list);
		return map;
	}
	
	/**
	 * 数据校验
	 * 
	 * @param bankOrg
	 * @return
	 */
	public void verify(SeckillProductVoReq voReq) throws Exception{
		if (StringUtils.isEmpty(voReq.getProductId())) {
			throw new BossException("商品ID不能为空!");
		}
		if (StringUtils.isEmpty(voReq.getProductType())) {
			throw new BossException("商品类型不能为空!");
		}
		if (StringUtils.isEmpty(voReq.getSecPrice())) {
			throw new BossException("价格不能为空!");
		}
		if (StringUtils.isEmpty(voReq.getVipSecPrice())) {
			throw new BossException("VIP价格不能为空!");
		}
		if (voReq.getSecStore() <= 0) {
			throw new BossException("总限购数不能小于等于0!");
		}
		if (voReq.getBuyLimit() < 0) {
			throw new BossException("限购数量不能小于0!");
		}
		if (voReq.getStartDate()==null) {
			throw new BossException("开始时间不能为空!");
		}
		if (voReq.getEndDate()==null) {
			throw new BossException("结束时间不能为空!");
		}
		long start = voReq.getStartDate();
		long end = voReq.getEndDate();
		if(end<=start){
			throw new BossException("结束时间不能小于等于开始时间!");
		}
		if(end < System.currentTimeMillis()){
			throw new BossException("结束时间不能小于当前时间!");
		}
	}

	/**
	 * 数据校验
	 */
	public void verify(SeckillProductSetVoReq voReq) throws Exception{
		if (voReq.getProductList() == null || voReq.getProductList().size() == 0) {
			throw new BossException("商品不能为空!");
		}
		if (StringUtil.isBlank(voReq.getStartDate())) {
			throw new BossException("开始时间不能为空!");
		}
		if (StringUtil.isBlank(voReq.getEndDate())) {
			throw new BossException("结束时间不能为空!");
		}
		long start = Long.valueOf(voReq.getStartDate());
		long end = Long.valueOf(voReq.getEndDate());
		for (SeckillProductVoReq p : voReq.getProductList()) {
			p.setStartDate(start);
			p.setEndDate(end);

			verify(p);
		}
	}

}
