package com.froad.action.web;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.froad.action.support.MerchantActionSupport;
import com.froad.action.support.MerchantPhotoActionSupport;
import com.froad.action.support.PictureActionSupport;
import com.froad.action.support.TagActionSupport;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.merchant.Merchant;
import com.froad.client.merchantPhoto.MerchantPhoto;
import com.froad.client.tag.TagDistrictB;
import com.froad.common.SessionKey;
import com.froad.util.Assert;
import com.froad.util.Command;
import com.froad.util.command.MallCommand;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-28  
 * @version 1.0
 */
public class MerchantPhotoAction extends BaseActionSupport {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -6470997111906027472L;
	private TagActionSupport tagActionSupport;
	private MerchantPhotoActionSupport merchantPhotoActionSupport;
	private MerchantActionSupport merchantActionSupport;
	private PictureActionSupport pictureActionSupport;
	private String merchantId;	
	private List<TagDistrictB> logoTagDistrictBList;//logo 商圈
	private MerchantPhoto merchantPhoto;//相册
	private MerchantPhoto pager=new MerchantPhoto();//相册
	private List<MerchantPhoto> merchantPhotoList = new ArrayList<MerchantPhoto>();//商户相册
	private File imgFile;
	private String imgFileFileName; 
	private String picType;//商家图片类型作为图片存储的文件件    logo:商家logo  products:商家产品   photos:商家相册图片   preferential:商家优惠
	public static final String ALLOWED_UPLOAD_IMAGE_EXTENSION = "jpg,jpge,bmp,gif,png";//允许上传的文件(为空表示不允许上传图片文件)
	public static final String EXTENSION_SEPARATOR = ",";// 文件扩展名分隔符
	public static final Integer UPLOAD_LIMIT = 1024;// 文件上传最大值,0表示无限制,单位KB
	public static final String UPLOAD_IMAGE_DIR = "/upload/merchant/";// 图片文件上传目录
	private String message;
	
	/**
	 * 列表
	 * @return
	 */
	public String list(){
		if("1".equals(getSession(SessionKey.MERCHANT_ROLE))){
			return "nopower";
		}
		if(pager == null){
			pager = new MerchantPhoto();
		}
		if(Assert.empty(pager.getMerchantId())){//若商户ID为空则通过userId获取
			Merchant m = new Merchant();
			String userId = (String)getSession(MallCommand.USER_ID);
			if(!Assert.empty(userId)){
				m = merchantActionSupport.getMerchantInfo(userId);
				pager.setMerchantId(m.getId()==null?"":m.getId().toString());
			}else{
				return "failse";
			}
		}
		if(Assert.empty(pager.getState())){
			pager.setState(Command.FBU_USERED_STATE);			
		}
		pager.setPageSize(10);//每页10条
		pager.setOrderBy("array*1");//优先级
		pager.setOrderType(com.froad.client.merchantPhoto.OrderType.DESC);
		pager=merchantPhotoActionSupport.queryMerchantPhotoByPager(pager);
		
		return "list";
	}
	
	/**
	 * 获取图片
	 * @return
	 */
	public String getMerchantPhotoInfo(){
		merchantPhoto=merchantPhotoActionSupport.getMerchantPhotoById(id);
		String userId = (String)getSession(MallCommand.USER_ID);
		if(!Assert.empty(userId)){
			
		}else{
			return "failse";
		}
		return "merchant_photo_info";
	}
	
	/**
	 * 增加
	 * @return
	 */
	public String add(){
		merchantPhoto = new MerchantPhoto();
		String userId = (String)getSession(MallCommand.USER_ID);
		if(!Assert.empty(userId)){
			
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
		String userId = (String)getSession(MallCommand.USER_ID);
		if(!Assert.empty(userId)){
			
		}else{
			return "failse";
		}
		if(id!=null){
			merchantPhoto = merchantPhotoActionSupport.getMerchantPhotoById(id);		
		}else{
			merchantPhoto = new MerchantPhoto();
		}
		return "input";
	}
	
	/**
	 * 保存
	 * @return
	 */
	public String save(){
		
		MerchantPhoto mp = new MerchantPhoto();
		mp.setArray(merchantPhoto.getArray());//设置优先级
		Integer mpsList = null;
		Integer count = null;
		if(merchantPhoto != null && imgFile !=null 
				&& !Assert.empty(merchantPhoto.getTxt1())
				&& !Assert.empty(merchantPhoto.getTxt2())){
			if(Assert.empty(merchantPhoto.getMerchantId())){//若商户ID为空则通过userId获取
				Merchant m = new Merchant();
				String userId = (String)getSession(MallCommand.USER_ID);
				if(!Assert.empty(userId)){
					m = merchantActionSupport.getMerchantInfo(userId);
					merchantPhoto.setMerchantId(m.getId()!=null?m.getId().toString():"");
					merchantId = m.getId()!=null?m.getId().toString():"";
				}else{
					return "failse";
				}
			}
			mp.setMerchantId(merchantPhoto.getMerchantId());
//				if(!Assert.empty(merchantPhoto.getTxt1())){
//					String txt1 = urlDecode(urlDecode(merchantPhoto.getTxt1(),"utf-8"), "utf-8");		
//				}
//				if(!Assert.empty(merchantPhoto.getTxt2())){
//					String txt2 = urlDecode(urlDecode(merchantPhoto.getTxt2(),"utf-8"), "utf-8");		
//				}
			mp.setTxt1(merchantPhoto.getTxt1());
			mp.setTxt2(merchantPhoto.getTxt2());
			if(Assert.empty(merchantPhoto.getRemark())){		
				mp.setRemark("");
			}
			//mp.setPrice();
			//mp.setRemark(value);
			//mp.setTxt1(merchantPhoto.getTxt1());
			//mp.setTxt2(merchantPhoto.getTxt1());
			mp.setState(Command.FBU_USERED_STATE);//（默认启用）
			if(imgFileFileName !=null && merchantPhoto.getMerchantId()!=null){
				String picurl = pictureActionSupport.uploadPicture(imgFile,imgFileFileName,merchantPhoto.getMerchantId(),picType);
				if(Assert.empty(picurl)){//增加提示
					message = "上传图片格式错误或文件大小超过1MB";
				}
				mp.setPhotoUrl(picurl);//保存图片并返回图片路径					
			}		
			if(Assert.empty(mp.getPhotoUrl())){
				return edit();
			}
			count = merchantPhotoActionSupport.addMerchantPhoto(mp);
		}else{
			return edit();
		}
		return list();
	}
	
	/**
	 * 修改
	 * @return
	 */
	public String update(){		
		MerchantPhoto mpp = new MerchantPhoto();
		MerchantPhoto mp = new MerchantPhoto();
		MerchantPhoto mpreq = new MerchantPhoto();
		mp.setArray(merchantPhoto.getArray());//设置优先级
		Integer count = null;
		if(merchantPhoto != null && !Assert.empty(merchantPhoto.getId()) && (imgFile!=null 
				|| !Assert.empty(merchantPhoto.getTxt1())
				|| !Assert.empty(merchantPhoto.getTxt2()))){
			if(Assert.empty(merchantPhoto.getMerchantId())){//若商户ID为空则通过userId获取
				Merchant m = new Merchant();
				String userId = (String)getSession(MallCommand.USER_ID);
				if(!Assert.empty(userId)){
					m = merchantActionSupport.getMerchantInfo(userId);
					merchantPhoto.setMerchantId(m.getId()!=null?m.getId().toString():"");
				}else{
					return "failse";
				}
			}
//				if(!Assert.empty(merchantPhoto.getTxt1())){
//				String txt1 = urlDecode(urlDecode(merchantPhoto.getTxt1(),"utf-8"), "utf-8");		
//			}
//			if(!Assert.empty(merchantPhoto.getTxt2())){
//				String txt2 = urlDecode(urlDecode(merchantPhoto.getTxt2(),"utf-8"), "utf-8");		
//			}
			mp.setTxt1(merchantPhoto.getTxt1());
			mp.setTxt2(merchantPhoto.getTxt2());
//				if(Assert.empty(merchantPhoto.getRemark())){		
//					mp.setRemark("");
//				}
			//mp.setPrice();
			//mp.setRemark(value);
			//mp.setTxt1(merchantPhoto.getTxt1());
			//mp.setTxt2(merchantPhoto.getTxt1());
			String picurl = "pic";
			if(imgFile !=null && imgFileFileName !=null && merchantPhoto.getMerchantId()!=null){
				picurl = pictureActionSupport.uploadPicture(imgFile,imgFileFileName,merchantPhoto.getMerchantId(),picType);
				mp.setPhotoUrl(picurl);					
			}
			if(!Assert.empty(merchantPhoto.getState()) && Command.FBU_USERED_STATE.equals(merchantPhoto.getState())){
				//mp.setState(Command.FBU_VERIFY_STATE);		
			}
			
			mp.setId(merchantPhoto.getId());
			mpp = merchantPhotoActionSupport.getMerchantPhotoById(String.valueOf(merchantPhoto.getId()));
//			if(mpp !=null && Command.FBU_VERIFY_STATE.equals(mpp.getState())){
//				//审核中不能删除
//			}else{
			if(Assert.empty(picurl)){
				id = String.valueOf(merchantPhoto.getId());
				message = "上传图片格式错误或文件大小超过1MB";
				return edit();
			}else{
				mpreq = merchantPhotoActionSupport.updMerchantPhotoInfo(mp);	
			}
												
//			}								
		}else{
			return list();
		}
		return list();
	}

	/**
	 * 暂停  返回json
	 */
	public void lineDownPhotoJSON(){
		JSONObject json = new JSONObject();
		MerchantPhoto mpres = null;
		json.put("reno", Command.respCode_FAIL);
		try {
			if(merchantPhoto != null && !Assert.empty(merchantPhoto.getId())){
				merchantPhoto.setState(Command.FBU_STOP_STATE);
				mpres = merchantPhotoActionSupport.updMerchantPhotoInfo(merchantPhoto);						
				if(mpres!=null && merchantPhoto.getId().equals(mpres.getId())){
					json.put("reno",Command.respCode_SUCCESS);
					json.put("stateValue", "停用");
				}else{
					json.put("reno",Command.respCode_FAIL);
				}
			}
		} catch (Exception e) {
			log.error("MerchantPhotoAction.lineDownProductJSON出错");
			e.printStackTrace();
		}
		sendMsg(json.toString());
	}
	
	/**
	 * 启用 返回json
	 */
	public void onLinePhotoJSON(){
		JSONObject json = new JSONObject();
		MerchantPhoto mpres = null;
		String sateValue = "";
		List<MerchantPhoto> photoList = new ArrayList<MerchantPhoto>();
		json.put("reno", Command.respCode_FAIL);
		try {
			if(merchantPhoto != null && !Assert.empty(merchantPhoto.getId())){
				if(!Assert.empty(merchantPhoto.getMerchantId())){
					photoList = merchantPhotoActionSupport.findMerchantPhotoByMerchatId(merchantPhoto.getMerchantId());					
				}else{
					Merchant m = new Merchant();
					String userId = (String)getSession(MallCommand.USER_ID);
					if(!Assert.empty(userId)){
						m = merchantActionSupport.getMerchantInfo(userId);
						merchantPhoto.setMerchantId(m.getId()!=null?m.getId().toString():"");
						photoList = merchantPhotoActionSupport.findMerchantPhotoByMerchatId(merchantPhoto.getMerchantId());	
					}
				}
				if(photoList != null && photoList.size()>11){
					json.put("reno","2");
					json.put("msg", "启用图片不能大于12个");
				}else{
					if(Assert.empty(merchantPhoto.getState()) || !Command.FBU_DELETE_STATE.equals(merchantPhoto.getState())){
						merchantPhoto.setState(Command.FBU_USERED_STATE);	
						sateValue = "启用";
					}else{
						//merchantPhoto.setState(Command.FBU_VERIFY_STATE);
						merchantPhoto.setState(Command.FBU_USERED_STATE);
						sateValue = "进入审核";
					}
					mpres = merchantPhotoActionSupport.updMerchantPhotoInfo(merchantPhoto);											
					if(mpres!=null && merchantPhoto.getId().equals(mpres.getId())){
						json.put("reno",Command.respCode_SUCCESS);
					}else{
						json.put("reno",Command.respCode_FAIL);
					}
					json.put("stateValue", sateValue);
				}
			}
		} catch (Exception e) {
			log.error("MerchantPhotoAction.onLinePhotoJSON出错");
			e.printStackTrace();
		}
		sendMsg(json.toString());
	}
	
	/**
	 * 查询单个图片   返回json
	 */
	public void getPhotoesInfoJSON(){
		JSONObject json = new JSONObject();
		json.put("reno", Command.respCode_FAIL);
		MerchantPhoto mp = null;
		try {
			if(merchantPhoto != null){
				merchantPhoto = new MerchantPhoto();
			}
			if(!Assert.empty(merchantPhoto.getId())){
				mp = merchantPhotoActionSupport.getMerchantPhotoById(String.valueOf(merchantPhoto.getId()));										
			}
			if(mp!=null){
				json.put("reno",Command.respCode_SUCCESS);	
				json.put("id",mp.getId());
				json.put("merchantId",mp.getMerchantId());
				json.put("photoUrl",mp.getPhotoUrl());
//				json.put("txt1",mp.getTxt1());
//				json.put("txt2",mp.getTxt2());
			}else{
				json.put("defaultImgUrl","/template/common/images/default_img.jpg");
				json.put("defaultImgUrl2","/template/common/images/3.jpg");
			}
		} catch (Exception e) {
			log.error("MerchantAction.getPhotoesInfoJSON出错");
			e.printStackTrace();
		}
		sendMsg(json.toString());
	}
	
	//商户相册信息 json
	public void queryMerchantPhotoesJSON(){
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		json.put("reno", Command.respCode_FAIL);
		try {
			if(Assert.empty(merchantPhoto.getMerchantId())){//若商户ID为空则通过userId获取
				Merchant m = new Merchant();
				String userId = (String)getSession(MallCommand.USER_ID);
				if(!Assert.empty(userId)){
					m = merchantActionSupport.getMerchantInfo(userId);
					merchantPhoto.setMerchantId(m.getId()!=null?m.getId().toString():"");
				}
			}
			merchantPhotoList=merchantPhotoActionSupport.getMerchantPhotoInfo(merchantPhoto.getMerchantId(), null);		
			if(merchantPhotoList!=null && merchantPhotoList.size()>0){
				json.put("reno",Command.respCode_SUCCESS);
				MerchantPhoto m = null;
				for(Object pic:merchantPhotoList){	
					m = (MerchantPhoto)pic;
					JSONObject orgs = new JSONObject();
					orgs.put("id",m.getId());
					orgs.put("merchantId",m.getMerchantId());
					orgs.put("photoUrl",m.getPhotoUrl());
					orgs.put("txt1",m.getTxt1());
					orgs.put("txt2",m.getTxt2());
					array.add(orgs);
				}
				json.put("merchantPhotoList", array);
			}else{
				json.put("reno",Command.respCode_FAIL);
			}
		} catch (Exception e) {
			log.error("MerchantAction.queryMerchantPhotoesJSON出错");
			e.printStackTrace();
		}
		sendMsg(json.toString());
	}
	
	//删除相册图片(返回相册列表)
	public String deletePhoto(){
		MerchantPhoto mpp = new MerchantPhoto();
		Integer count = null;
		if(merchantPhoto != null && !Assert.empty(merchantPhoto.getId())){
			if(Assert.empty(merchantPhoto.getMerchantId())){//若商户ID为空则通过userId获取
				Merchant m = new Merchant();
				String userId = (String)getSession(MallCommand.USER_ID);
				if(!Assert.empty(userId)){
					m = merchantActionSupport.getMerchantInfo(userId);
					merchantPhoto.setMerchantId(m.getId()!=null?m.getId().toString():"");
				}else{
					return "failse";
				}
			}
//			mpp = merchantPhotoActionSupport.getMerchantPhotoById(String.valueOf(merchantPhoto.getId()));
//			if(mpp !=null && Command.FBU_VERIFY_STATE.equals(mpp.getState())){
//				//审核中不能删除
//			}else{
				count = merchantPhotoActionSupport.deletePhotoInfo(merchantPhoto);							
//			}
		}else{
			return list();
		}
		return list();
	}

	public TagActionSupport getTagActionSupport() {
		return tagActionSupport;
	}

	public void setTagActionSupport(TagActionSupport tagActionSupport) {
		this.tagActionSupport = tagActionSupport;
	}

	public MerchantPhotoActionSupport getMerchantPhotoActionSupport() {
		return merchantPhotoActionSupport;
	}

	public void setMerchantPhotoActionSupport(
			MerchantPhotoActionSupport merchantPhotoActionSupport) {
		this.merchantPhotoActionSupport = merchantPhotoActionSupport;
	}

	public MerchantActionSupport getMerchantActionSupport() {
		return merchantActionSupport;
	}

	public void setMerchantActionSupport(MerchantActionSupport merchantActionSupport) {
		this.merchantActionSupport = merchantActionSupport;
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

	public List<TagDistrictB> getLogoTagDistrictBList() {
		return logoTagDistrictBList;
	}

	public void setLogoTagDistrictBList(List<TagDistrictB> logoTagDistrictBList) {
		this.logoTagDistrictBList = logoTagDistrictBList;
	}

	public MerchantPhoto getMerchantPhoto() {
		return merchantPhoto;
	}

	public void setMerchantPhoto(MerchantPhoto merchantPhoto) {
		this.merchantPhoto = merchantPhoto;
	}

	public MerchantPhoto getPager() {
		return pager;
	}

	public void setPager(MerchantPhoto pager) {
		this.pager = pager;
	}

	public List<MerchantPhoto> getMerchantPhotoList() {
		return merchantPhotoList;
	}

	public void setMerchantPhotoList(List<MerchantPhoto> merchantPhotoList) {
		this.merchantPhotoList = merchantPhotoList;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
