package com.froad.cbank.coremodule.module.normal.merchant.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.merchant.exception.MerchantException;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.ImageDel_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.UploadCrop_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.UploadDel_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.UploadSaveCrop_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.UploadToken_Req;
import com.froad.cbank.coremodule.module.normal.merchant.utils.Constants;
import com.froad.cbank.coremodule.module.normal.merchant.utils.EnumTypes;
import com.froad.cbank.coremodule.module.normal.merchant.utils.HttpServer;
import com.froad.cbank.coremodule.module.normal.merchant.utils.HttpWeb;
import com.froad.pojo.ARountePojo;
import com.froad.thrift.service.MerchantOutletPhotoService;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.vo.MerchantOutletPhotoVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.ProductImageVo;
import com.froad.thrift.vo.ProductInfoVo;
import com.froad.thrift.vo.ProductOperateVoReq;


/**
 * pc 图片service
 * 
 * @author artPing
 * @date 23/03/2015
 */
@Service
public class Image_Service {
	
	@Resource
	MerchantOutletPhotoService.Iface merchantOutletPhotoService;

	@Resource
	ProductService.Iface productService;
	/**
	 * 申请上传图片
	 * 
	 * @param req
	 * @return
	 * @throws MerchantException
	 * @throws TException
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> uploadImage(UploadToken_Req req)
			throws MerchantException, TException {
		LogCvt.info("请求参数---->"+JSON.toJSONString(req));
		StringBuffer sb=new StringBuffer();
		sb.append("appId=").append(Integer.valueOf(Constants.get("merchant.appId")))
		.append("&appKey=").append(Constants.get("merchant.appKey"))
		.append("&fsizeLimitMax=").append(Integer.valueOf(req.getFsizeLimitMax()))
		.append("&fsizeLimitMin=").append(Integer.valueOf(req.getFsizeLimitMin()))
		.append("&mimeLimit=").append(req.getMimeLimit())
		.append("&image.widthLimitMin=").append(Integer.valueOf(req.getWidthLimitMin()))
		.append("&image.widthLimitMax=").append(Integer.valueOf(req.getWidthLimitMax()))
		.append("&image.heightLimitMin=").append(Integer.valueOf(req.getHeightLimitMin()))
		.append("&image.heightLimitMax=").append(Integer.valueOf(req.getHeightLimitMax()));
		ARountePojo route=HttpWeb.geturl("fis","token", "uploadToken");
		String url="http://"+route.getService_ip()+":"+route.getService_port()+route.getUrlInteface()+"?";
		long beginTime=System.currentTimeMillis();
		String json = HttpWeb.sendGetRequest(url,sb.toString());
		HttpWeb.setResult(route.getRoute_id(), StringUtils.isEmpty(json)?0:1, (System.currentTimeMillis()-beginTime));
		Map<String, Object> map=(Map<String, Object>)JSON.parse(json);
		map.put("code",map.get("code")+"");
		if((map.get("code")+"").equals("200")){
			map.put("code",EnumTypes.success.getCode());
		}
		LogCvt.info("返回参数---->"+(Map<String, Object>)JSON.parse(json));
		return map;
	}

	/**
	 * 裁剪图片上传
	 * 
	 * @param req
	 * @return
	 * @throws MerchantException
	 * @throws TException
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> crop(UploadCrop_Req req)
			throws MerchantException, TException {
		LogCvt.info("请求参数---->"+JSON.toJSONString(req));
		req.setAppId(Integer.valueOf(Constants.get("merchant.appId")));
		req.setAppKey(Constants.get("merchant.appKey"));
		ARountePojo route=HttpWeb.geturl("fis","resource", "crop");
		String url="http://"+route.getService_ip()+":"+route.getService_port()+route.getUrlInteface()+"?";
		long beginTime=System.currentTimeMillis();
		String json = HttpWeb.sendPostRequest(url, JSON.toJSONString(req));
		HttpWeb.setResult(route.getRoute_id(), StringUtils.isEmpty(json)?0:1, (System.currentTimeMillis()-beginTime));
		Map<String, Object> map=(Map<String, Object>)JSON.parse(json);
		map.put("code",map.get("code")+"");
		if((map.get("code")+"").equals("200")){
			map.put("code",EnumTypes.success.getCode());
		}
		LogCvt.info("返回参数---->"+JSON.toJSONString(json));
		return map;
	}

	/**
	 * 保存图片
	 * 
	 * @param req
	 * @return
	 * @throws MerchantException
	 * @throws TException
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> saveCrop(UploadSaveCrop_Req req)
			throws MerchantException, TException {
		LogCvt.info("请求参数---->"+JSON.toJSONString(req));
		req.setAppId(Integer.valueOf(Constants.get("merchant.appId")));
		req.setAppKey(Constants.get("merchant.appKey"));
		String uuid=UUID.randomUUID().toString();
		req.setDestKey(uuid);
		ARountePojo route=HttpWeb.geturl("fis","resource", "saveCrop");
		String url="http://"+route.getService_ip()+":"+route.getService_port()+route.getUrlInteface()+"?";
		long beginTime=System.currentTimeMillis();
		String json = HttpWeb.sendPostRequest(url, JSON.toJSONString(req));
		HttpWeb.setResult(route.getRoute_id(), StringUtils.isEmpty(json)?0:1, (System.currentTimeMillis()-beginTime));
		
		Map<String, Object> map=(Map<String, Object>)JSON.parse(json);
		map.put("code",map.get("code")+"");
		map.put("imageName", uuid);
		if((map.get("code")+"").equals("200")){
			map.put("code",EnumTypes.success.getCode());
		}
		LogCvt.info("返回参数---->"+JSON.toJSONString(json));
		return map;
	}
	
	
	/**
	 * 图片删除
	 * 
	 * @param req
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> del(UploadDel_Req req)
			throws Exception {
		LogCvt.info("请求参数---->"+JSON.toJSONString(req));
		Map<String, Object> map=new HashMap<String,Object>();
		//删除门店图片
		OriginVo ori = new OriginVo();
		ori.setOperatorId(req.getMerchantUser().getId());
		ori.setOperatorIp(req.getIp());
		ori.setPlatType(req.getPlatType());
		ori.setClientId(req.getClientId());
		boolean istrue=false;int isPro=0;//是否是商品标志
		MerchantOutletPhotoVo photo=new MerchantOutletPhotoVo();
		if(StringUtils.isNotEmpty(req.getOutletId())){
			photo.setOutletId(req.getOutletId());
			photo.setMerchantId(req.getMerchantUser().getMerchantId());
			//查询是否存在这个图片
			List<MerchantOutletPhotoVo>  merphoto=merchantOutletPhotoService.getMerchantOutletPhoto(photo);
			if(merphoto!=null&&merphoto.size()>0)istrue=true;
			
		}
		
		//商品删除图片
		if(StringUtils.isNotEmpty(req.getProductId())){
			ProductOperateVoReq productVoReq=new ProductOperateVoReq();
			productVoReq.setMerchantId(req.getMerchantUser().getMerchantId());
			productVoReq.setProductId(req.getProductId());
			productVoReq.setClientId(req.getClientId());
			productVoReq.setType(req.getType());
			//详情
			ProductInfoVo productInfoVo = productService.getMerchantProductDetail(productVoReq);
            List<ProductImageVo>  proimg=productInfoVo.getImage();
			//修改
			if(productInfoVo!=null&&proimg.size()>0){
				for(ProductImageVo img:proimg){
					if(img.getSource().equals(req.getFileKey())){
						istrue=true;break;
					}
				}
			}
			
			isPro=1;
		}
		
		//审核不通过的才会删除否不删除
		if(istrue){
			StringBuffer sb=new StringBuffer();
			sb.append("appId=").append(Integer.valueOf(Constants.get("merchant.appId")))
			.append("&appKey=").append(Constants.get("merchant.appKey"))
			.append("&fileKey=").append(req.getFileKey());
			if(req.getAuditState()!=1||isPro==1){
				ARountePojo route=HttpWeb.geturl("ffs","resource", "del");
				String url="http://"+route.getService_ip()+":"+route.getService_port()+route.getUrlInteface()+"?";
				long beginTime=System.currentTimeMillis();
				String json = HttpWeb.sendPostRequest(url,sb.toString());
				HttpWeb.setResult(route.getRoute_id(), StringUtils.isEmpty(json)?0:1, (System.currentTimeMillis()-beginTime));
				map=(Map<String, Object>)JSON.parse(json);
				map.put("code",map.get("code")+"");
				if((map.get("code")+"").equals("200")||(map.get("code")+"").equals("612")){
					map.put("code",EnumTypes.success.getCode());
				}
				LogCvt.info("返回参数---->"+JSON.toJSONString(json));
			}else{
				map.put("code",EnumTypes.success.getCode());
				map.put("msg","删除成功");
			}
		}else{
			map.put("code",EnumTypes.success.getCode());
			map.put("msg","没有权限删除");
		}
		
		return map;
	}

	/**
	 * 说明   server端删除图片接口
	 * 创建日期  2015年12月7日  下午4:08:31
	 * 作者  artPing
	 * 参数  @param req
	 * 参数  @return
	 */
	@SuppressWarnings("unchecked")
	public String imgDel(List<ImageDel_Req> req){
		LogCvt.info("请求参数---->"+JSON.toJSONString(req));
		List<Map<String,String>> maplist=new ArrayList<Map<String,String>>();
		try {
			for(ImageDel_Req up:req){
				StringBuffer sb=new StringBuffer();
				sb.append("appId=").append(Integer.valueOf(Constants.get("merchant.appId")))
				.append("&appKey=").append(Constants.get("merchant.appKey"))
				.append("&fileKey=").append(up.getFileKey());
				ARountePojo route=HttpWeb.geturl("ffs","resource", "del");
				String url="http://"+route.getService_ip()+":"+route.getService_port()+route.getUrlInteface()+"?";
				long beginTime=System.currentTimeMillis();
				String json = HttpWeb.sendPostRequest(url,sb.toString());
				HttpWeb.setResult(route.getRoute_id(), StringUtils.isEmpty(json)?0:1, (System.currentTimeMillis()-beginTime));
				Map<String,String> map=(Map<String, String>)JSON.parse(json);
				map.put("fileKey", up.getFileKey());
				maplist.add(map);
				LogCvt.info("返回参数---->"+JSON.toJSONString(json));
			}
			return JSON.toJSONString(maplist);
		} catch (NumberFormatException e) {
			LogCvt.error("数据类型转换异常---->"+e);
			return HttpServer.error("9999", "数据类型转换异常");
		}catch(Exception ex){
			LogCvt.error("系统异常---->"+ex);
			return HttpServer.error("500", "系统异常");
		}
		
	}

}
