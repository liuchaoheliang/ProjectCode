package com.froad.action.web;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.froad.action.support.MerchantActionSupport;
import com.froad.action.support.MerchantProductActionSupport;
import com.froad.action.support.PictureActionSupport;
import com.froad.action.support.TagActionSupport;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.merchant.Merchant;
import com.froad.client.merchantProduct.MerchantProduct;
import com.froad.client.merchantProduct.OrderType;
import com.froad.client.tag.TagDistrictB;
import com.froad.common.SessionKey;
import com.froad.util.Assert;
import com.froad.util.Command;
import com.froad.util.command.MallCommand;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-22  
 * @version 1.0
 */
public class MerchantProductAction extends BaseActionSupport {
	/**
	 * UID
	 */
	private static final long serialVersionUID = 4068576866860680296L;
	private TagActionSupport tagActionSupport;
	public MerchantProductActionSupport merchantProductActionSupport;
	public MerchantActionSupport merchantActionSupport;
	private PictureActionSupport pictureActionSupport;
	private List<TagDistrictB> logoTagDistrictBList;//logo 商圈
	private MerchantProduct merchantProduct;//商品
	private MerchantProduct pager = new MerchantProduct();//商品
	private List<MerchantProduct> merchantProductList = new ArrayList<MerchantProduct>();//商户商品
	private String merchantId;	
	private File imgFile;
	private String imgFileFileName; 
	private String picType;//商家图片类型作为图片存储的文件件    logo:商家logo  products:商家产品   photos:商家相册图片   preferential:商家优惠
	public static final String ALLOWED_UPLOAD_IMAGE_EXTENSION = "jpg,jpge,bmp,gif,png";//允许上传的文件(为空表示不允许上传图片文件)
	public static final String EXTENSION_SEPARATOR = ",";// 文件扩展名分隔符
	public static final Integer UPLOAD_LIMIT = 1024;// 文件上传最大值,0表示无限制,单位KB
	public static final String UPLOAD_IMAGE_DIR = "/upload/merchant/";// 图片文件上传目录
	
	private String preferentialType;//优惠类型
	private Merchant merchant;
	private String message;
	

	/**
	 * 获取商品
	 * @return
	 */
	public String getMerchantProductInfo(){		
		merchantProduct=merchantProductActionSupport.getMerchantProductById(id);
//		if(Assert.empty(pager.getMerchantId())){//若商户ID为空则通过userId获取
//			Merchant m = new Merchant();
//			String userId = (String)getSession(MallCommand.USER_ID);
//			if(!Assert.empty(userId)){
//
//			}else{
//				return "failse";
//			}
//		}
		return "merchant_product_info";
	}
	
	/**
	 * 列表
	 * @return
	 */
	public String list(){
		if("1".equals(getSession(SessionKey.MERCHANT_ROLE))){
			return "nopower";
		}
		if(pager == null){
			pager = new MerchantProduct();
		}
		if(Assert.empty(pager.getMerchantId())){//若商户ID为空则通过userId获取
			Merchant m = new Merchant();
			String userId = (String)getSession(MallCommand.USER_ID);
			if(!Assert.empty(userId)){
				m = merchantActionSupport.getMerchantInfo(userId);
				pager.setMerchantId(m.getId()!=null?m.getId().toString():"");
			}else{
				return "failse";
			}
		}
		if(Assert.empty(pager.getState())){
			pager.setState(Command.FBU_USERED_STATE);			
		}
		pager.setPageSize(10);//每页10条	
		pager.setOrderBy("array*1");//优先级
		pager.setOrderType(com.froad.client.merchantProduct.OrderType.DESC);
		pager=merchantProductActionSupport.queryMerchantProductByPager(pager);		
		return "list";
	}
	
	/**
	 * 增加
	 * @return
	 */
	public String add(){
		preferentialType = (String)getSession("preferentialType");
		Merchant m = new Merchant();
		merchantProduct = new MerchantProduct();
		String userId = (String)getSession(MallCommand.USER_ID);
		if(!Assert.empty(userId)){
			m = merchantActionSupport.getMerchantInfo(userId);
			merchantProduct.setPointRate(Assert.empty(m.getPointRate())?"":m.getPointRate());
			merchantProduct.setPreferentialRate(Assert.empty(m.getPreferentialRate())?"":m.getPreferentialRate());
		}else{
			return "failse";
		}
		return "input";
	}
	
	/**
	 * 修改
	 * @return
	 */
	public String edit(){
		if(id!=null){
			merchantProduct=merchantProductActionSupport.getMerchantProductById(id);		
		}else{
			merchantProduct=new MerchantProduct();
		}
		Merchant m = new Merchant();
		String userId = (String)getSession(MallCommand.USER_ID);
		if(Assert.empty(merchantProduct.getPointRate()) && Assert.empty(merchantProduct.getPreferentialRate()) && !Assert.empty(userId)){
			m = merchantActionSupport.getMerchantInfo(userId);
			merchantProduct.setPointRate(Assert.empty(m.getPointRate())?"":m.getPointRate());
			merchantProduct.setPreferentialRate(Assert.empty(m.getPreferentialRate())?"":m.getPreferentialRate());
		}else if(Assert.empty(userId)){
			return "failse";
		}
		return "input";
	}
	
	/**
	 * 保存
	 * @return
	 */
	public String save(){
		MerchantProduct mp = new MerchantProduct();
		MerchantProduct mpres = null;
		mp.setArray(merchantProduct.getArray());
		Integer count = null;
		try {
			if(merchantProduct != null && imgFile != null 
					&& !Assert.empty(merchantProduct.getTxt1())
					&& !Assert.empty(merchantProduct.getTxt2())){
				if(Assert.empty(merchantProduct.getMerchantId())){//若商户ID为空则通过userId获取
					Merchant m = new Merchant();
					String userId = (String)getSession(MallCommand.USER_ID);
					if(!Assert.empty(userId)){
						m = merchantActionSupport.getMerchantInfo(userId);
						merchantProduct.setMerchantId(m.getId()!=null?m.getId().toString():"");
						merchantId = m.getId()!=null?m.getId().toString():"";
					}else{
						return "failse";
					}
				}
				mp.setMerchantId(merchantProduct.getMerchantId());
//				if(!Assert.empty(merchantProduct.getTxt1())){
//					String txt1 = urlDecode(urlDecode(merchantProduct.getTxt1(),"utf-8"), "utf-8");		
//				}
//				if(!Assert.empty(merchantProduct.getTxt2())){
//					String txt2 = urlDecode(urlDecode(merchantProduct.getTxt2(),"utf-8"), "utf-8");		
//				}
				mp.setPrice(Assert.empty(merchantProduct.getPrice())?"":merchantProduct.getPrice());
				mp.setPointRate(merchantProduct.getPointRate());
				mp.setPreferentialRate(merchantProduct.getPreferentialRate());
				mp.setTxt1(merchantProduct.getTxt1());
				mp.setTxt2(merchantProduct.getTxt2());
				if(imgFileFileName !=null && merchantProduct.getMerchantId()!=null){
					String picurl = pictureActionSupport.uploadPicture(imgFile,imgFileFileName,merchantProduct.getMerchantId(),picType);
					if(Assert.empty(picurl)){//增加提示
						message = "上传图片格式错误或文件大小超过1MB";
					}
					mp.setPhotoUrl(picurl);//保存图片并返回图片路径				
				}
				if(Assert.empty(mp.getPhotoUrl())){
					return edit();
				}
				if(Assert.empty(merchantProduct.getRemark())){		
					mp.setRemark("");
				}
				//mp.setPrice();
				//mp.setRemark(value);
				//mp.setTxt1(merchantProduct.getTxt1());
				//mp.setTxt2(merchantProduct.getTxt1());
				mp.setState(Command.FBU_USERED_STATE);//默认启用
				count = merchantProductActionSupport.addMerchantProducts(mp);
				return list();
			}else{
				message = "商品信息填写完整";
				return edit();
			}
		} catch (Exception e) {
			log.error("MerchantAction.save出错");
			e.printStackTrace();
		}
		return list();
	}
	
	/**
	 * 修改
	 * @return
	 */
	public String update(){
		MerchantProduct mpp = new MerchantProduct();
		MerchantProduct mp = new MerchantProduct();
		MerchantProduct mpres = null;
		mp.setArray(merchantProduct.getArray());
		Integer count = null;
		try {
			if(merchantProduct != null &&!Assert.empty(merchantProduct.getId())  && (!Assert.empty(merchantProduct.getPhotoUrl()) 
					|| !Assert.empty(merchantProduct.getTxt1())
					|| !Assert.empty(merchantProduct.getTxt2()))){
				if(Assert.empty(merchantProduct.getMerchantId())){//若商户ID为空则通过userId获取
					Merchant m = new Merchant();
					String userId = (String)getSession(MallCommand.USER_ID);
					if(!Assert.empty(userId)){
						m = merchantActionSupport.getMerchantInfo(userId);
						merchantProduct.setMerchantId(m.getId()!=null?m.getId().toString():"");
					}else{
						return "failse";
					}
				}
				if(!Assert.empty(merchantProduct.getTxt1())){
					String txt1 = urlDecode(urlDecode(merchantProduct.getTxt1(),"utf-8"), "utf-8");		
					mp.setTxt1(txt1);
				}
//				if(!Assert.empty(merchantProduct.getTxt1())){
//				String txt1 = urlDecode(urlDecode(merchantProduct.getTxt1(),"utf-8"), "utf-8");		
//			}
//			if(!Assert.empty(merchantProduct.getTxt2())){
//				String txt2 = urlDecode(urlDecode(merchantProduct.getTxt2(),"utf-8"), "utf-8");		
//			}
				mp.setPrice(Assert.empty(merchantProduct.getPrice())?"":merchantProduct.getPrice());
				mp.setPointRate(merchantProduct.getPointRate());
				mp.setPreferentialRate(merchantProduct.getPreferentialRate());
				mp.setTxt1(merchantProduct.getTxt1());
				mp.setTxt2(merchantProduct.getTxt2());
				String picurl = "pic";
				if(imgFile!=null && imgFileFileName !=null && merchantProduct.getMerchantId()!=null){
					picurl = pictureActionSupport.uploadPicture(imgFile,imgFileFileName,merchantProduct.getMerchantId(),picType);
					mp.setPhotoUrl(picurl);//保存图片并返回图片路径				
				}
				//mp.setPrice();
				//mp.setRemark(value);
				//mp.setTxt1(merchantProduct.getTxt1());
				//mp.setTxt2(merchantProduct.getTxt1());
				if(!Assert.empty(merchantProduct.getState()) && Command.FBU_USERED_STATE.equals(merchantProduct.getState())){
					//mp.setState(Command.FBU_VERIFY_STATE);		
				}
				
				mp.setId(merchantProduct.getId());
				

//				if(mpp !=null && Command.FBU_VERIFY_STATE.equals(mpp.getState())){
//					//审核中不能删除
//				}else{
				if(Assert.empty(picurl)){
					id = String.valueOf(merchantProduct.getId());
					message = "上传图片格式错误或文件大小超过1MB";
					return edit();
				}else{
					mpres = merchantProductActionSupport.updMerchantProducts(mp);	
				}	
//				}					
				return list();
			}else{
				return list();
			}
		} catch (Exception e) {
			log.error("MerchantAction.update出错"+merchantProduct.getId());
			e.printStackTrace();
		}
		return list();
	}
	
	
	/**
	 * 暂停  返回json
	 */
	public void lineDownProductJSON(){
		JSONObject json = new JSONObject();
		MerchantProduct mpres = null;
		json.put("reno", Command.respCode_FAIL);
		try {
			if(merchantProduct != null && !Assert.empty(merchantProduct.getId())){
				merchantProduct.setState(Command.FBU_STOP_STATE);
				mpres = merchantProductActionSupport.updMerchantProducts(merchantProduct);						
				if(mpres!=null && merchantProduct.getId().equals(mpres.getId())){
					json.put("reno",Command.respCode_SUCCESS);
					json.put("stateValue", "停用");
				}else{
					json.put("reno",Command.respCode_FAIL);
				}
			}
		} catch (Exception e) {
			log.error("MerchantAction.lineDownProductJSON出错");
			e.printStackTrace();
		}
		sendMsg(json.toString());
	}
	
	/**
	 * 启用 返回json
	 */
	public void onLineProductJSON(){
		JSONObject json = new JSONObject();
		String sateValue = "";
		MerchantProduct mpres = null;
		MerchantProduct mpreq = new MerchantProduct();;
		List<MerchantProduct> preList = new ArrayList<MerchantProduct>();
		json.put("reno", Command.respCode_FAIL);
		try {
			if(merchantProduct != null && !Assert.empty(merchantProduct.getId())){
				mpreq.setState(Command.FBU_USERED_STATE);
				if(!Assert.empty(merchantProduct.getMerchantId())){
					mpreq.setMerchantId(merchantProduct.getMerchantId());
					preList = merchantProductActionSupport.getMerchantProductListByMerchantId(mpreq);					
				}else{
					Merchant m = new Merchant();
					String userId = (String)getSession(MallCommand.USER_ID);
					if(!Assert.empty(userId)){
						m = merchantActionSupport.getMerchantInfo(userId);
						merchantProduct.setMerchantId(m.getId()!=null?m.getId().toString():"");
						mpreq.setMerchantId(merchantProduct.getMerchantId());
						preList = merchantProductActionSupport.getMerchantProductListByMerchantId(mpreq);	
					}
				}
				if(preList != null && preList.size()>11){
					json.put("reno","2");
					json.put("msg", "启用商家产品不能大于12个");
				}else{
					if(Assert.empty(merchantProduct.getState()) || !Command.FBU_DELETE_STATE.equals(merchantProduct.getState())){
						merchantProduct.setState(Command.FBU_USERED_STATE);	
						sateValue = "启用";
					}else{
						//merchantProduct.setState(Command.FBU_VERIFY_STATE);	
						merchantProduct.setState(Command.FBU_USERED_STATE);//删除直接变为启用
						sateValue = "进入审核";
					}
					mpres = merchantProductActionSupport.updMerchantProducts(merchantProduct);											
					if(mpres!=null && merchantProduct.getId().equals(mpres.getId())){
						json.put("reno",Command.respCode_SUCCESS);
					}else{
						json.put("reno",Command.respCode_FAIL);
//					if(!Assert.empty(merchantProduct.getState())){
//						json.put("stateValue", merchantProduct.getState());
//					}else{
//						json.put("stateValue", "");					
//					}
					}
					json.put("stateValue", sateValue);
				}				
			}
		} catch (Exception e) {
			log.error("MerchantAction.onLineProductJSON出错");
			e.printStackTrace();
		}
		sendMsg(json.toString());
	}
	
	/**
	 * 产品列表图片，描述，优惠  返回json
	 */
	public void queryMerchantProductInfoJSON(){
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		json.put("reno", Command.respCode_FAIL);
		try {
			if(merchantProduct != null){
				if(Assert.empty(merchantProduct.getMerchantId())){//若商户ID为空则通过userId获取
					Merchant m = new Merchant();
					String userId = (String)getSession(MallCommand.USER_ID);
					if(!Assert.empty(userId)){
						m = merchantActionSupport.getMerchantInfo(userId);
						merchantProduct.setMerchantId(m.getId()!=null?m.getId().toString():"");
					}
				}
				merchantProductList=merchantProductActionSupport.getMerchantProductInfo(merchantProduct.getMerchantId(), null);							
			}
			if(merchantProductList!=null && merchantProductList.size()>0){
				json.put("reno",Command.respCode_SUCCESS);
				MerchantProduct m = null;
				for(Object pro:merchantProductList){	
					m = (MerchantProduct)pro;
					JSONObject orgs = new JSONObject();
					orgs.put("id",m.getId());
					orgs.put("merchantId",m.getMerchantId());
					orgs.put("photoUrl",m.getPhotoUrl());
					orgs.put("txt1",m.getTxt1());
					orgs.put("txt2",m.getTxt2());
					array.add(orgs);
				}
				json.put("merchantProductList", array);
			}else{
				json.put("reno",Command.respCode_FAIL);
				json.put("defaultImgUrl","/template/common/images/default_img.jpg");
				json.put("defaultImgUrl2","/template/common/images/3.jpg");
			}
		} catch (Exception e) {
			log.error("MerchantAction.queryMerchantProductInfoJSON出错");
			e.printStackTrace();
		}
		sendMsg(json.toString());
	}
	
	/**
	 * 查询单个产品图片，描述，优惠  返回json
	 */
	public void getProductInfoJSON(){
		JSONObject json = new JSONObject();
		json.put("reno", Command.respCode_FAIL);
		MerchantProduct mp = null;
		try {
			if(merchantProduct == null){
				merchantProduct = new MerchantProduct();
			}
			if(Assert.empty(merchantProduct.getId())){//若商户ID为空则通过userId获取
				mp = merchantProductActionSupport.getMerchantProductById(String.valueOf(merchantProduct.getId()));										
			}
			if(mp!=null){
				json.put("reno",Command.respCode_SUCCESS);	
				json.put("id",mp.getId());
				json.put("merchantId",mp.getMerchantId());
				json.put("photoUrl",mp.getPhotoUrl());
				json.put("txt1",mp.getTxt1());
				json.put("txt2",mp.getTxt2());
			}else{
				json.put("defaultImgUrl","/template/common/images/default_img.jpg");
				json.put("defaultImgUrl2","/template/common/images/3.jpg");
			}
		} catch (Exception e) {
			log.error("MerchantAction.queryMerchantProductInfoJSON出错");
			e.printStackTrace();
		}
		sendMsg(json.toString());
	}	
	
	//删除产品(返回产品列表)
	public String deleteProduct(){
		MerchantProduct mpp = new MerchantProduct();
		try {
			if(merchantProduct != null && !Assert.empty(merchantProduct.getId())){
				if(Assert.empty(merchantProduct.getMerchantId())){//若商户ID为空则通过userId获取
					Merchant m = new Merchant();
					String userId = (String)getSession(MallCommand.USER_ID);
					if(!Assert.empty(userId)){
						m = merchantActionSupport.getMerchantInfo(userId);
						merchantProduct.setMerchantId(m.getId()!=null?m.getId().toString():"");
					}else{
						return "failse";
					}
				}
//				mpp = merchantProductActionSupport.getMerchantProductById(String.valueOf(merchantProduct.getId()));
//				if(mpp !=null && Command.FBU_VERIFY_STATE.equals(mpp.getState())){
//					//审核中不能删除
//				}else{
					merchantProduct.setState(Command.FBU_DELETE_STATE);
					int count = merchantProductActionSupport.deleteProductAndReListInfo(merchantProduct);							
//				}				
				return list();
			}else{
				return list();
			}
		} catch (Exception e) {
			log.error("MerchantAction.deleteProduct出错,产品ID："+merchantProduct.getId());
			e.printStackTrace();
		}
		return list();
	}
	
	public String merchantProuctListByMerchantId(){
		log.info("商户ID："+id);
		
		merchant=merchantActionSupport.getMerchantById(id);
		
		pager.setMerchantId(id);
		pager.setState("30");
		pager.setPageSize(9);
		pager.setOrderBy("array*1");
		pager.setOrderType(OrderType.DESC);
		pager=merchantProductActionSupport.queryMerchantProductByPager(pager);
		
		return "merchantProuct_by_merchantId_list";
	}
	
	
	public TagActionSupport getTagActionSupport() {
		return tagActionSupport;
	}

	public void setTagActionSupport(TagActionSupport tagActionSupport) {
		this.tagActionSupport = tagActionSupport;
	}

	public MerchantProductActionSupport getMerchantProductActionSupport() {
		return merchantProductActionSupport;
	}

	public void setMerchantProductActionSupport(
			MerchantProductActionSupport merchantProductActionSupport) {
		this.merchantProductActionSupport = merchantProductActionSupport;
	}

	public List<TagDistrictB> getLogoTagDistrictBList() {
		return logoTagDistrictBList;
	}

	public void setLogoTagDistrictBList(List<TagDistrictB> logoTagDistrictBList) {
		this.logoTagDistrictBList = logoTagDistrictBList;
	}

	public MerchantProduct getMerchantProduct() {
		return merchantProduct;
	}

	public void setMerchantProduct(MerchantProduct merchantProduct) {
		this.merchantProduct = merchantProduct;
	}

	public MerchantProduct getPager() {
		return pager;
	}

	public void setPager(MerchantProduct pager) {
		this.pager = pager;
	}

	public List<MerchantProduct> getMerchantProductList() {
		return merchantProductList;
	}

	public void setMerchantProductList(List<MerchantProduct> merchantProductList) {
		this.merchantProductList = merchantProductList;
	}

	public PictureActionSupport getPictureActionSupport() {
		return pictureActionSupport;
	}

	public void setPictureActionSupport(PictureActionSupport pictureActionSupport) {
		this.pictureActionSupport = pictureActionSupport;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public File getImgFile() {
		return imgFile;
	}

	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}

	public String getImgFileFileName() {
		return imgFileFileName;
	}

	public void setImgFileFileName(String imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}

	public String getPicType() {
		return picType;
	}

	public void setPicType(String picType) {
		this.picType = picType;
	}

	public static String getAllowedUploadImageExtension() {
		return ALLOWED_UPLOAD_IMAGE_EXTENSION;
	}

	public static String getExtensionSeparator() {
		return EXTENSION_SEPARATOR;
	}

	public static Integer getUploadLimit() {
		return UPLOAD_LIMIT;
	}

	public static String getUploadImageDir() {
		return UPLOAD_IMAGE_DIR;
	}

	public MerchantActionSupport getMerchantActionSupport() {
		return merchantActionSupport;
	}

	public void setMerchantActionSupport(MerchantActionSupport merchantActionSupport) {
		this.merchantActionSupport = merchantActionSupport;
	}
	
	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public String getPreferentialType() {
		return preferentialType;
	}

	public void setPreferentialType(String preferentialType) {
		this.preferentialType = preferentialType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
