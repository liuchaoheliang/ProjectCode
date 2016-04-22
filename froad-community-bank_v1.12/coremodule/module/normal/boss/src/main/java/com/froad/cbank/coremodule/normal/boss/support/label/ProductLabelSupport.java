package com.froad.cbank.coremodule.normal.boss.support.label;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.BossUser;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.common.ClientRes;
import com.froad.cbank.coremodule.normal.boss.pojo.label.ExportRelateActivityVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.label.ProductAddReq;
import com.froad.cbank.coremodule.normal.boss.pojo.label.ProductDelReq;
import com.froad.cbank.coremodule.normal.boss.pojo.label.ProductLabelAddReq;
import com.froad.cbank.coremodule.normal.boss.pojo.label.ProductLabelReq;
import com.froad.cbank.coremodule.normal.boss.pojo.label.ProductLabelRes;
import com.froad.cbank.coremodule.normal.boss.pojo.label.ProductListReq;
import com.froad.cbank.coremodule.normal.boss.pojo.label.ProductListRes;
import com.froad.cbank.coremodule.normal.boss.pojo.label.ProductWeightReq;
import com.froad.cbank.coremodule.normal.boss.support.common.ClientSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.thrift.service.ProductActivityTagService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.recommendactivitytag.AdjustProductWeightReqVo;
import com.froad.thrift.vo.recommendactivitytag.DeleteRelateProductReqVo;
import com.froad.thrift.vo.recommendactivitytag.EnableProductActivityReqVo;
import com.froad.thrift.vo.recommendactivitytag.ExportRelateActivityTagReqVo;
import com.froad.thrift.vo.recommendactivitytag.InputRelateProductActivityReqVo;
import com.froad.thrift.vo.recommendactivitytag.InputRelateProductActivityVo;
import com.froad.thrift.vo.recommendactivitytag.ProductActivityTagDetailReqVo;
import com.froad.thrift.vo.recommendactivitytag.ProductActivityTagDetailResVo;
import com.froad.thrift.vo.recommendactivitytag.ProductNameResVo;
import com.froad.thrift.vo.recommendactivitytag.ProductWeightActivityTagPageReqVo;
import com.froad.thrift.vo.recommendactivitytag.ProductWeightActivityTagPageVo;
import com.froad.thrift.vo.recommendactivitytag.ProductWeightActivityTagVo;
import com.froad.thrift.vo.recommendactivitytag.RecommendActivityTagPageVo;
import com.froad.thrift.vo.recommendactivitytag.RecommendActivityTagVo;
import com.froad.thrift.vo.recommendactivitytag.RelateProductActivityVo;

/**
 * 商品推荐活动标签
 * @ClassName ProductLabelSupport
 * @author zxl
 * @date 2015年10月28日 下午2:33:41
 */
@Service
public class ProductLabelSupport {
	
	@Resource
	ProductActivityTagService.Iface productActivityTagService;
	
	@Resource
	ClientSupport clientSupport;
	
	/**
	 * 列表查询
	 * @tilte list
	 * @author zxl
	 * @date 2015年10月28日 下午2:37:06
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> list(ProductLabelReq req) throws Exception{
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(req.getPageNumber());
		pageVo.setPageSize(req.getPageSize());
		pageVo.setFirstRecordTime(req.getFirstRecordTime());
		pageVo.setLastPageNumber(req.getLastPageNumber());
		pageVo.setLastRecordTime(req.getLastRecordTime());
		
		RecommendActivityTagVo vo = new RecommendActivityTagVo();
		if(StringUtil.isNotEmpty(req.getClientId())){//客户端
			vo.setClientId(req.getClientId());
		}
		if(StringUtil.isNotEmpty(req.getStatus())){//状态
			vo.setStatus(req.getStatus());
		}
		if(StringUtil.isNotEmpty(req.getActivityName())){//名称
			vo.setActivityName(req.getActivityName());
		}
		
		RecommendActivityTagPageVo resp = productActivityTagService.findProductTagByPage(vo, pageVo);
		
		if(Constants.RESULT_SUCCESS.equals(resp.getResultVo().getResultCode())){
			List<ClientRes> client = clientSupport.getClient();
			List<ProductLabelRes> list = new ArrayList<ProductLabelRes>();
			for(RecommendActivityTagVo v : resp.getRecommendvos()){
				ProductLabelRes r = new ProductLabelRes();
				BeanUtils.copyProperties(r, v);
				for(ClientRes c : client){
					if(c.getClientId().equals(v.getClientId())){
						r.setClientName(c.getClientName());
						break;
					}
				}
				list.add(r);
			}
			Page page = new Page();
			BeanUtils.copyProperties(page, resp.getPageVo());
			map.put("page", page);
			map.put("list", list);
		}else{
			throw new BossException(resp.getResultVo().getResultCode(), resp.getResultVo().getResultDesc());
		}
		return map;
	}
	
	/**
	 * 详情
	 * @tilte detail
	 * @author zxl
	 * @date 2015年10月28日 下午2:40:55
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> detail(String id,String activityNo,String clientId,String operator) throws Exception{
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		ProductActivityTagDetailReqVo vo = new ProductActivityTagDetailReqVo();
		vo.setActivityId(Long.parseLong(id));
		vo.setActivityNo(activityNo);
		vo.setClientId(clientId);
		vo.setOperator(operator);
		
		ProductActivityTagDetailResVo resp = productActivityTagService.findProductTagDetail(vo);
		
		if(Constants.RESULT_SUCCESS.equals(resp.getResultVo().getResultCode())){
			List<ClientRes> client = clientSupport.getClient();
			ProductLabelRes r = new ProductLabelRes();
			BeanUtils.copyProperties(r, resp.getRecommendVo());
			for(ClientRes c : client){
				if(c.getClientId().equals(resp.getRecommendVo().getClientId())){
					r.setClientName(c.getClientName());
					break;
				}
			}
			map.put("detail", r);
		}else{
			throw new BossException(resp.getResultVo().getResultCode(), resp.getResultVo().getResultDesc());
		}
		
		return map;
	}
	
	/**
	 * 新增
	 * @tilte add
	 * @author zxl
	 * @date 2015年10月28日 下午2:41:01
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> add(ProductLabelAddReq req,BossUser user) throws Exception{
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		RecommendActivityTagVo vo = new RecommendActivityTagVo();
		vo.setActivityNo(req.getActivityNo());
		vo.setActivityName(req.getActivityName());
		vo.setClientId(req.getClientId());
		vo.setLogoUrl(req.getLogoUrl());
		vo.setActivityDesc(req.getActivityDesc());
		vo.setOperator(user.getName());
		
		ResultVo resp = productActivityTagService.addProductActivityTag(vo);
		if(!Constants.RESULT_SUCCESS.equals(resp.getResultCode())){
			throw new BossException(resp.getResultCode(), resp.getResultDesc());
		}
		
		return map;
	}
	
	/**
	 * 修改
	 * @tilte mdy
	 * @author zxl
	 * @date 2015年10月28日 下午2:41:08
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> mdy(ProductLabelAddReq req,BossUser user) throws Exception{
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		RecommendActivityTagVo vo = new RecommendActivityTagVo();
		vo.setId(req.getId());
		vo.setActivityNo(req.getActivityNo());
		vo.setActivityName(req.getActivityName());
		vo.setClientId(req.getClientId());
		vo.setLogoUrl(req.getLogoUrl());
		vo.setActivityDesc(req.getActivityDesc());
		vo.setOperator(user.getName());
		
		ResultVo resp = productActivityTagService.updateProductActivityTag(vo);
		if(!Constants.RESULT_SUCCESS.equals(resp.getResultCode())){
			throw new BossException(resp.getResultCode(), resp.getResultDesc());
		}
		
		return map;
	}
	
	/**
	 * 禁用
	 * @tilte del
	 * @author zxl
	 * @date 2015年10月28日 下午2:41:16
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> del(String id,String clientId,String operator) throws Exception{
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		EnableProductActivityReqVo vo = new EnableProductActivityReqVo();
		vo.setId(Long.parseLong(id));
		vo.setStatus("2");
		vo.setClientId(clientId);
		vo.setOperator(operator);
		
		ResultVo resp = productActivityTagService.enableProductRecommendActivityTag(vo);
		if(!Constants.RESULT_SUCCESS.equals(resp.getResultCode())){
			throw new BossException(resp.getResultCode(), resp.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 关联商品列表
	 * @tilte productList
	 * @author zxl
	 * @date 2015年10月29日 上午9:54:55
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> productList(ProductListReq req) throws Exception{
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(req.getPageNumber());
		pageVo.setPageSize(req.getPageSize());
		pageVo.setFirstRecordTime(req.getFirstRecordTime());
		pageVo.setLastPageNumber(req.getLastPageNumber());
		pageVo.setLastRecordTime(req.getLastRecordTime());
		
		ProductWeightActivityTagPageReqVo vo = new ProductWeightActivityTagPageReqVo();
		vo.setPageVo(pageVo);
		if(StringUtil.isNotBlank(req.getId())){//活动ID
			vo.setActivityId(Long.parseLong(req.getId()));
		}
		vo.setActivityNo(req.getActivityNo());
		vo.setClientId(req.getClientId());
		
		ProductWeightActivityTagPageVo resp = productActivityTagService.findRelateProductInfoByPage(vo);
		
		if(Constants.RESULT_SUCCESS.equals(resp.getResultVo().getResultCode())){
			List<ProductListRes> list = new ArrayList<ProductListRes>();
			for(ProductWeightActivityTagVo v : resp.getRelateProducts()){
				ProductListRes r = new ProductListRes();
				r.setProductId(v.getElementId());
				BeanUtils.copyProperties(r, v);
				//如果最后更改时间为0，那么返回创建时间
				if(v.getUpdateTime()==0){
					r.setUpdateTime(v.getCreateTime());
				}
				list.add(r);
			}
			Page page = new Page();
			BeanUtils.copyProperties(page, resp.getPageVo());
			map.put("page", page);
			map.put("list", list);
		}else{
			throw new BossException(resp.getResultVo().getResultCode(), resp.getResultVo().getResultDesc());
		}
		return map;
	}
	
	/**
	 * 关联商品
	 * @tilte productAdd
	 * @author zxl
	 * @date 2015年10月29日 上午10:15:31
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> productAdd(ProductAddReq req,BossUser user) throws Exception{
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		RelateProductActivityVo vo = new RelateProductActivityVo();
		BeanUtils.copyProperties(vo, req);
		vo.setOperator(user.getName());
		
		ResultVo resp = productActivityTagService.relateProductInfo(vo);
		
		if(!Constants.RESULT_SUCCESS.equals(resp.getResultCode())){
			throw new BossException(resp.getResultCode(), resp.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 商品删除
	 * @tilte productDel
	 * @author zxl
	 * @date 2015年10月29日 上午10:25:45
	 * @param req
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> productDel(ProductDelReq req,BossUser user) throws Exception{
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		DeleteRelateProductReqVo vo = new DeleteRelateProductReqVo();
		BeanUtils.copyProperties(vo, req);
		vo.setOperator(user.getName());
		
		ResultVo resp = productActivityTagService.deleteRelateProduct(vo);
		
		if(!Constants.RESULT_SUCCESS.equals(resp.getResultCode())){
			throw new BossException(resp.getResultCode(), resp.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 商品权重
	 * @tilte productWeight
	 * @author zxl
	 * @date 2015年10月29日 上午10:35:36
	 * @param req
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> productWeight(ProductWeightReq req,BossUser user) throws Exception{
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		AdjustProductWeightReqVo vo = new AdjustProductWeightReqVo();
		BeanUtils.copyProperties(vo, req);
		vo.setOperator(user.getName());
		
		ResultVo resp = productActivityTagService.adjustProductWeight(vo);
		
		if(!Constants.RESULT_SUCCESS.equals(resp.getResultCode())){
			throw new BossException(resp.getResultCode(), resp.getResultDesc());
		}
		return map;
	}
	
	/**
     * 根据商品ID获取商品名称
     * @param clientId
     * @param outletId
     * @return
     * @throws TException
	 * @throws BossException 
     */
	public Map<String, Object> getProductNameByProductId(String clientId, String productId) throws TException, BossException{
		Map<String, Object> map = new HashMap<String, Object>();
		//调用server后台接口获取数据
		ProductNameResVo productVo = productActivityTagService.queryProductNameByProductId(clientId, productId);
		if(Constants.RESULT_SUCCESS.equals(productVo.getResultVo().getResultCode())){
			map.put("productName", productVo.getProductName());
		}else{
			throw new BossException(productVo.getResultVo().getResultCode(), productVo.getResultVo().getResultDesc());
		}
		return map;
    }
	
	/**
	 * 商品上传
	 * @tilte productUpload
	 * @author zxl
	 * @date 2015年10月29日 上午10:39:31
	 * @param req
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> productUpload(HttpServletRequest request,String activityId,String activityNo,String clientId,List<List<String>> data) throws Exception{
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		InputRelateProductActivityReqVo vo = new InputRelateProductActivityReqVo();
		if(StringUtil.isNotBlank(activityId)){//活动ID
			vo.setActivityId(Long.parseLong(activityId));
		}
		vo.setActivityNo(activityNo);
		vo.setClientId(clientId);
		vo.setOperator(((BossUser)request.getAttribute(Constants.BOSS_USER)).getName());
		
		List<InputRelateProductActivityVo> proList = new ArrayList<InputRelateProductActivityVo>();
		
		for(int i = 1; i < data.size();i++){
			List<String> l = data.get(i);
			InputRelateProductActivityVo p = new InputRelateProductActivityVo();
			p.setId(i+1);
			p.setActivityNo(activityNo);
			p.setProductId(l.get(2));
			p.setProductName(l.get(1));
			p.setWeight((int)Double.parseDouble(l.get(3))+"");
			proList.add(p);
		}
		vo.setVos(proList);
		
		ResultVo resp = productActivityTagService.inputRelateProductInfo(vo);
		
		if(!Constants.RESULT_SUCCESS.equals(resp.getResultCode())){
			throw new BossException(resp.getResultCode(), resp.getResultDesc());
		}else{
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", "上传成功");
		}
		return map;
	}
	
	/**
	 * 
	 * <p>Title:关联商品信息导出 </p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2016年1月12日 下午4:26:11 
	 * @param req
	 * @return
	 * @throws TException 
	 * @throws BossException 
	 */
	public HashMap<String,Object> exportRelateActivity(ExportRelateActivityVoReq req) throws TException, BossException{
		HashMap<String,Object> map = new HashMap<String,Object>();
		ExportRelateActivityTagReqVo temp = new ExportRelateActivityTagReqVo();
		BeanUtils.copyProperties(temp, req);
		ExportResultRes res = productActivityTagService.exportProductRelateActivityTag(temp);
		if(!Constants.RESULT_SUCCESS.equals(res.getResultVo().getResultCode())){
			throw new BossException(res.getResultVo().getResultCode(), res.getResultVo().getResultDesc());
		}else{
			map.put("code", res.getResultVo().getResultCode());
			map.put("message","操作成功！");
			map.put("url", res.getUrl());
		}
		return map;
	}
}
