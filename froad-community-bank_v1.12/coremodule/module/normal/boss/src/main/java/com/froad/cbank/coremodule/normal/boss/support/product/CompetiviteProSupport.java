package com.froad.cbank.coremodule.normal.boss.support.product;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.FileVo;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.product.CompetiviteProAddReq;
import com.froad.cbank.coremodule.normal.boss.pojo.product.CompetiviteProdetailRes;
import com.froad.cbank.coremodule.normal.boss.pojo.product.CompetiviteProductListVoRes;
import com.froad.cbank.coremodule.normal.boss.pojo.product.ProductCategoryVoRes;
import com.froad.cbank.coremodule.normal.boss.pojo.product.ProductListVoReq;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.DateUtil;
import com.froad.cbank.coremodule.normal.boss.utils.PramasUtil;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.vo.AddProductVoRes;
import com.froad.thrift.vo.GoodsInfoVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ProductFilterVo;
import com.froad.thrift.vo.ProductImageVo;
import com.froad.thrift.vo.ProductListPageVo;
import com.froad.thrift.vo.ProductListVo;
import com.froad.thrift.vo.ResultVo;

/**
 * 
 * @ClassName: CompetiviteProSupport
 * @author chenzhangwei@f-road.com.cn
 * @createTime 2015年11月25日 上午9:48:29 
 * @desc <p>精品商城商品管理support</p>
 */
@Service
public class CompetiviteProSupport {
	
	@Resource
	ProductService.Iface productService;
	
	@Resource
	ProductCategorySupport productCategorySupport;
	
	/**
	 * 
	 * <p>Title: 精品商城商品列表分页查询</p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2015年11月26日 下午5:56:09 
	 * @param req
	 * @return
	 * @throws TException 
	 */
	public HashMap<String,Object> list(ProductListVoReq req) throws TException{
		HashMap<String, Object> map = new HashMap<String, Object>();
		ProductFilterVo productFilterVoReq=new ProductFilterVo();
		if (StringUtil.isNotBlank(req.getProductName())) {
			// 商品名称
			productFilterVoReq.setProductName(req.getProductName());
		}if (StringUtil.isNotBlank(req.getClientId())) {
			// 客户端id
			productFilterVoReq.setClientId(req.getClientId());
		}if (StringUtil.isNotBlank(req.getProductId())) {
			// 商品编号
			productFilterVoReq.setProductId(req.getProductId());
		}if (StringUtil.isNotBlank(req.getCategoryId())) {
			// 商品分类id
			productFilterVoReq.setCategoryId(req.getCategoryId());
		}if (StringUtil.isNotBlank(req.getSeoKeyWords())) {
			// 关键字
			productFilterVoReq.setSeoKeyWords(req.getSeoKeyWords());
		}
		if(StringUtil.isNotBlank(req.getIsSeckill())){
			//是否为秒杀
			productFilterVoReq.setIsSeckill(req.getIsSeckill());
		}
		
		PageVo pageVo=new PageVo();
		pageVo.setPageSize(req.getPageSize());
		pageVo.setPageNumber(req.getPageNumber());
		Page page=new Page();
		
		//精品商品列表返回实体
		CompetiviteProductListVoRes res=null;
		List<CompetiviteProductListVoRes> list = new ArrayList<CompetiviteProductListVoRes>();
		//执行查询
		LogCvt.info("精品商品列表查询条件:" + JSON.toJSONString(productFilterVoReq));
		ProductListPageVo temp=productService.findGoodsByPage(productFilterVoReq, pageVo);
		List<ProductListVo> ls=new ArrayList<ProductListVo>();
		ls=temp.getProductListVos();
		if(ls.size()>0){
			for (ProductListVo productListVo : ls) {
				//实体拷贝
				res=new CompetiviteProductListVoRes();
				BeanUtils.copyProperties(res, productListVo);
				list.add(res);
			}
		}
		if(temp.getPage()!=null){
			//返回分页信息
			BeanUtils.copyProperties(page, temp.getPage());
		}
		map.put("list", list);
		map.put("page", page);
		return map;
	}
	
	/**
	 * 
	 * <p>Title:精品商城商品新增 </p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2015年11月26日 下午2:41:47 
	 * @param request
	 * @return
	 * @throws TException 
	 * @throws BossException 
	 * @throws ParseException 
	 */
	public HashMap<String,Object> add(HttpServletRequest request,CompetiviteProAddReq req) throws TException, BossException, ParseException{
		HashMap<String, Object> map = new HashMap<String, Object>();
		GoodsInfoVo goods=new GoodsInfoVo();
		goods.setDeliveryTime(PramasUtil.DateFormat(req.getDeliveryTime()));
		OriginVo originVo=(OriginVo) request.getAttribute(Constants.ORIGIN);
		//图片
		List<ProductImageVo> images=new ArrayList<ProductImageVo>();
		//参数转换
		BeanUtils.copyProperties(goods, req);
		//设置图片
		if(req.getImages()!=null){
			for (FileVo vo : req.getImages()) {
				ProductImageVo temp=new ProductImageVo();
				BeanUtils.copyProperties(temp, vo);
				images.add(temp);
			}
		}
		goods.setImages(images);
		
		//执行新增
		LogCvt.info("精品商品商品新增条件 thrift:" + JSON.toJSONString(goods));
		AddProductVoRes res=productService.addGoods(originVo, goods);
		if(Constants.RESULT_SUCCESS.equals(res.getResult().getResultCode())){
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", "精品商品新增成功");
		}else{
			throw new BossException(res.getResult().getResultCode(), res.getResult().getResultDesc());
		}
		return map;
	}
	
	/**
	 * 
	 * <p>Title:精品商城商品上架 </p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2015年11月27日 下午4:22:46 
	 * @param request
	 * @param proId
	 * @return
	 * @throws TException 
	 * @throws BossException 
	 */
	public HashMap<String,Object> updateGoodsStatusOn(HttpServletRequest request,String proId) throws TException, BossException{
		HashMap<String, Object> map = new HashMap<String, Object>();
		OriginVo originVo=(OriginVo) request.getAttribute(Constants.ORIGIN);
		ResultVo resultVo=productService.updateGoodsStatusOn(originVo, proId);
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", "精品商品上架成功");
		}else{
			throw new BossException(resultVo.getResultCode(), resultVo.getResultDesc());
		}
		return map;
	}
	/**
	 * 
	 * <p>Title:精品商品下架 </p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2015年11月27日 下午4:27:38 
	 * @param request
	 * @param proId
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public HashMap<String,Object> updateGoodsStatusOff(HttpServletRequest request,String proId) throws TException, BossException{
		HashMap<String, Object> map = new HashMap<String, Object>();
		OriginVo originVo=(OriginVo) request.getAttribute(Constants.ORIGIN);
		ResultVo resultVo=productService.updateGoodsStatusOff(originVo, proId);
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", "精品商品下架成功");
		}else{
			throw new BossException(resultVo.getResultCode(), resultVo.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 
	 * <p>Title: 精品商城商品详情查询</p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2015年11月30日 上午9:13:24 
	 * @param proId
	 * @return
	 * @throws TException 
	 */
	public HashMap<String,Object> detail(String proId) throws TException{
		HashMap<String, Object> map = new HashMap<String, Object>();
		//查询返回商品信息
		GoodsInfoVo goodsInfoVo=productService.getGoodsDetail(proId);
		//商品返回实体
		CompetiviteProdetailRes res=new CompetiviteProdetailRes();
		List<FileVo> ls =new ArrayList<FileVo>();
		BeanUtils.copyProperties(res, goodsInfoVo);
		//设置时间
		res.setCreateTime(transLateDate(goodsInfoVo.getCreateTime(),true));
		res.setDeliveryTime(transLateDate(goodsInfoVo.getDeliveryTime(),false));
		//设置图片
		for (ProductImageVo vo : goodsInfoVo.getImages()) {
			FileVo temp=new FileVo();
			BeanUtils.copyProperties(temp, vo);
			ls.add(temp);
		}
		res.setImages(ls);
		map.put("oneImg", res.getImages().get((Integer)0));
		List<FileVo> imgs=new ArrayList<FileVo>();
		for(int i=0;i<res.getImages().size();i++){
			if(i!=0){
				imgs.add(res.getImages().get(i));
			}
		}
		map.put("images",imgs);
		map.put("res", res);
		LogCvt.info("精品商城商品详情返回res:" + JSON.toJSONString(res));
		return map;
	}
	
	/**
	 * 
	 * <p>Title: 精品商城商品修改</p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2015年11月30日 下午3:13:16 
	 * @param request
	 * @param req
	 * @return
	 * @throws TException
	 * @throws BossException
	 * @throws ParseException 
	 */
	public HashMap<String,Object> update(HttpServletRequest request,CompetiviteProAddReq req) throws TException, BossException, ParseException{
		HashMap<String, Object> map = new HashMap<String, Object>();
		GoodsInfoVo goods=new GoodsInfoVo();
		goods.setDeliveryTime(PramasUtil.DateFormat(req.getDeliveryTime()));
		OriginVo originVo=(OriginVo) request.getAttribute(Constants.ORIGIN);
				//图片
				List<ProductImageVo> images=new ArrayList<ProductImageVo>();
				//参数转换
				BeanUtils.copyProperties(goods, req);
				//设置图片
				if(req.getImages()!=null){
					for (FileVo vo : req.getImages()) {
						ProductImageVo temp=new ProductImageVo();
						BeanUtils.copyProperties(temp, vo);
						images.add(temp);
					}
				}
		goods.setImages(images);
		//执行修改
		LogCvt.info("精品商品商品修改条件 thrift:" + JSON.toJSONString(goods));
		ResultVo res=productService.updateGoods(originVo, goods);
		if(Constants.RESULT_SUCCESS.equals(res.getResultCode())){
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", "精品商品修改成功");
		}else{
			throw new BossException(res.getResultCode(), res.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 
	 * <p>Title:查询所有精品商品一级分类 </p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2015年12月1日 下午1:44:55 
	 * @param clientId
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public HashMap<String,Object> findAllProOneCategory(String clientId,HttpServletRequest request,boolean enable) throws Exception{
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		map=productCategorySupport.queryProCategoryList(clientId, (OriginVo)request.getAttribute(Constants.ORIGIN), true);
		List<ProductCategoryVoRes> temp=new ArrayList<ProductCategoryVoRes>();
		@SuppressWarnings("unchecked")
		List<ProductCategoryVoRes> list=(List<ProductCategoryVoRes>) map.get("categoryList");
		//LogCvt.info("精品商城商品一级分类list集合:   "+ JSON.toJSONString(list));
		LogCvt.info("精品商城商品一级分类list集合大小:   "+ list.size());
		if(enable){
			//如果enable为真，就是查询一级分类
			for (ProductCategoryVoRes res : list) {
				//得到所有的一级父节点
				if(res!=null){
					if(res.getParentId()!=null && res.isEnable()==true){
						if(res.getParentId()==0){
							temp.add(res);
						}
					}
				}
			}
		}else{
			//否则查询所有列表
			for (ProductCategoryVoRes res : list) {
				if(res!=null){
					if(res.isEnable()==true){
							temp.add(res);
					}
				}
			}
			temp.remove(temp.size()-1);
		}
		resMap.put("ls", temp);
		LogCvt.info("精品商城商品一级分类temp集合大小:   "+ temp.size());
		return resMap;
	}
	
	/**
	 * 
	 * <p>Title: 时间格式转化</p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2015年11月30日 上午11:48:39 
	 * @param time
	 * @param dateType
	 * @return
	 */
	private String transLateDate(long time,boolean dateType){
		String dateTmp="";
		if(time==0){
			return dateTmp;
		}else{
		Date dd =DateUtil.longToDate(Long.valueOf(time));
		 dateTmp = DateUtil.formatDate(dd, dateType);
		 return dateTmp;
		}
	}
}
