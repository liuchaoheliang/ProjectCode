package com.froad.cbank.coremodule.normal.boss.support.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.product.ProductCommentVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.product.ProductCommentVoRes;
import com.froad.cbank.coremodule.normal.boss.utils.DateUtil;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ProductCommentFilterReq;
import com.froad.thrift.vo.ProductCommentPageVo;
import com.froad.thrift.vo.ProductCommentVo;

/**
 * 
 * <p>标题: 商品评价</p>
 * <p>说明: 商品评价相关业务</p>
 * <p>创建时间：2015年4月28日上午10:27:56</p>
 * <p>作者: 陈明灿</p>
 * <p>版本: 1.0</p>
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */
@Service
public class ProductCommentSupport {
	
	@Resource
	ProductService.Iface productService;
	/**
	 * 
	 * <p>功能简述：商品评价</p> 
	 * <p>使用说明：根据clientId查询商品评价列表</p> 
	 * <p>创建时间：2015年4月28日上午10:32:52</p>
	 * <p>作者: 陈明灿</p>
	 * @param req
	 * @return
	 * @throws TException 
	 */
	public Map<String, Object> list(ProductCommentVoReq req) throws TException {
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<ProductCommentVoRes> list = null;
		ProductCommentFilterReq reqVo = new ProductCommentFilterReq();
		reqVo.setClientId(req.getClientId());
		Map<String, Object> mapVo = new HashMap<String, Object>();
		//封装请求的filter
		mapVo.put("starLevel", req.getStartLevel());
		mapVo.put("memberName", req.getUserName());
		mapVo.put("productName", req.getProductName());
	
		String filter = JSON.toJSONString(mapVo);
		reqVo.setFilter(filter);
		PageVo pageVo = new PageVo();
		pageVo.setPageSize(req.getPageSize());
		pageVo.setPageNumber(req.getPageNumber());
		ProductCommentPageVo commentPageVo = productService
				.getProductCommentList(reqVo, pageVo);
		LogCvt.info("查询商品评论列表--返回数据:" + JSON.toJSONString(commentPageVo));
		if(null!=commentPageVo){
			Page page = new Page();
			BeanUtils.copyProperties(page, commentPageVo.getPage());
			map.put("page", page);
		}
		if (null != commentPageVo) {
			List<ProductCommentVo> list2 = commentPageVo.getProductCommentVoList();
			if (null != list2 && list2.size() > 0) {
				ProductCommentVoRes res = null;
				// Date date = null;
				list = new ArrayList<ProductCommentVoRes>();
				for (ProductCommentVo productCommentVo : list2) {
					 res = new ProductCommentVoRes();
					// System.out.println(JSON.toJSONString(productCommentVo));
					// BeanUtils.copyProperties(res, productCommentVo);
					res.setClientId(productCommentVo.getClientId());
					res.setProductName(productCommentVo.getProductName());
					res.setCommentId(productCommentVo.getCommentId());
					res.setContent(productCommentVo.getCommentDescription());
					// res.setIsPass(); 没有审核机制
					res.setUserName(productCommentVo.getMemberName());
					res.setStartLevel(productCommentVo.getStarLevel() + "");
					// 转化时间格式
					// date = new Date(productCommentVo.getCreateTime());
					// Date date = new Date();
					// SimpleDateFormat sdf = new SimpleDateFormat(
					// "yyyy-MM-dd HH:mm:ss");
					//转化日期格式 yyyy-MM-dd HH:mm:ss
					if(StringUtil.isNotBlank(productCommentVo.getCreateTime()) 
							&& productCommentVo.getCreateTime() != 0){
						res.setCreateTime(DateUtil.longToString(productCommentVo
							.getCreateTime()));
					}
					list.add(res);
				}
				LogCvt.info("查询商品评论列表--封装数据:" + JSON.toJSONString(list));
				map.put("list", list);
			}
		}
		return map;
	}
	
	
}
