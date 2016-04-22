package com.froad.action.web;

import java.io.File;
import java.util.List;

import net.sf.json.JSONObject;

import com.froad.action.support.MerchantActionSupport;
import com.froad.action.support.MerchantPresentActionSupport;
import com.froad.action.support.PictureActionSupport;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.merchant.Merchant;
import com.froad.client.merchantPresent.MerchantPresent;
import com.froad.common.SessionKey;
import com.froad.util.Assert;
import com.froad.util.Command;
import com.froad.util.command.MallCommand;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-28  
 * @version 1.0
 */
public class MerchantPresentAction extends BaseActionSupport {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 2544924647014253609L;
	private MerchantPresentActionSupport merchantPresentActionSupport;
	private MerchantActionSupport merchantActionSupport;
	private PictureActionSupport pictureActionSupport;
	private List<MerchantPresent> merchantPresentList;
	private MerchantPresent merchantPresent;
	//private String merchantId;	
	private File imgFile;
	private String imgFileFileName; 
	private String picType;//商家图片类型作为图片存储的文件件    logo:商家logo  products:商家产品   photos:商家相册图片   preferential:商家优惠
//	public static final String ALLOWED_UPLOAD_IMAGE_EXTENSION = "jpg,jpge,bmp,gif,png";//允许上传的文件(为空表示不允许上传图片文件)
//	public static final String EXTENSION_SEPARATOR = ",";// 文件扩展名分隔符
//	public static final Integer UPLOAD_LIMIT = 1024;// 文件上传最大值,0表示无限制,单位KB
//	public static final String UPLOAD_IMAGE_DIR = "/upload/merchant/";// 图片文件上传目录
	private String message;
	
	public void getMerchantPresentInfo(){
		JSONObject json = new JSONObject();
		json.put("reno", Command.respCode_FAIL);
		MerchantPresent merReq = null;		
		if(merchantPresent == null){
			merchantPresent = new MerchantPresent();
		}
		if(Assert.empty(merchantPresent.getMerchantId())){
			Merchant m = new Merchant();
			String userId = (String)getSession(MallCommand.USER_ID);
			if(!Assert.empty(userId)){
				m = merchantActionSupport.getMerchantInfo(userId);
				merchantPresent.setMerchantId(m.getId()!=null?m.getId().toString():"");
			}
		}
		merReq = merchantPresentActionSupport.getMerchantPresentInfo(merchantPresent.getMerchantId(),null);
		if(merReq !=null){
			json.put("remark", merReq.getRemark());
			json.put("txt", merReq.getTxt());
			json.put("presentUrl", merReq.getPhotoUrl());
			json.put("reno", Command.respCode_SUCCESS);
		}
		sendMsg(json.toString());
	}
	
	public String toManage(){
		if("1".equals(getSession(SessionKey.MERCHANT_ROLE))){
			return "nopower";
		}
		log.info("信息管理");
		if(merchantPresent == null){
			merchantPresent = new MerchantPresent();
		}
		if(Assert.empty(merchantPresent.getMerchantId())){
			Merchant m = new Merchant();
			String userId = (String)getSession(MallCommand.USER_ID);
			if(!Assert.empty(userId)){
				m = merchantActionSupport.getMerchantInfo(userId);
				merchantPresent.setMerchantId(m.getId()!=null?m.getId().toString():"");
			}else{
				return "failse";
			}
		}
		merchantPresent = merchantPresentActionSupport.getMerchantPresentInfo(merchantPresent.getMerchantId(),null);
		if(merchantPresent == null){
			merchantPresent = new MerchantPresent();
		}
		return "message_manage";
	}
	
	public String updateByMerchantId(){
		MerchantPresent merReq = new MerchantPresent();		
		String userId = (String)getSession(MallCommand.USER_ID);
		if(merchantPresent != null){	
			if(Assert.empty(merchantPresent.getMerchantId())){
				Merchant m = new Merchant();
				if(!Assert.empty(userId)){
					m = merchantActionSupport.getMerchantInfo(userId);
					merchantPresent.setMerchantId(m.getId()!=null?m.getId().toString():"");
					merReq.setMerchantId(m.getId()!=null?m.getId().toString():"");
				}else{
					return "failse";
				}
			}else{
				merReq.setMerchantId(merchantPresent.getMerchantId());
			}
			if(!Assert.empty(merchantPresent.getTxt1())){
				//String txt1 = urlDecode(urlDecode(merchantPresent.getTxt1(),"utf-8"), "utf-8");
				merReq.setTxt1(merchantPresent.getTxt1());
			}
			if(!Assert.empty(merchantPresent.getTxt())){
				//String txt = urlDecode(urlDecode(merchantPresent.getTxt(),"utf-8"), "utf-8");
				merReq.setTxt(merchantPresent.getTxt());
			}
			if(!Assert.empty(merchantPresent.getRemark())){
				//String remark = urlDecode(urlDecode(merchantPresent.getRemark(),"utf-8"), "utf-8");
				merReq.setRemark(merchantPresent.getRemark());
			}
			String picUrl = "pic";
			if(imgFile !=null){
				picUrl = pictureActionSupport.uploadPicture(imgFile,imgFileFileName,merchantPresent.getMerchantId(),picType);
				merReq.setPhotoUrl(picUrl);
			}
			if(Assert.empty(picUrl)){
				message="上传图片格式错误或文件大小超过1MB";
				merchantPresent = merchantPresentActionSupport.getMerchantPresentInfo(merReq.getMerchantId(), userId);
			}else{
				merchantPresent = merchantPresentActionSupport.updateInfoByMerchantId(merReq);				
			}
			if(merchantPresent!=null && Assert.empty(message)){
				message="修改成功";
			}
			
		}
		return "input";
	}
	
	
	public MerchantPresentActionSupport getMerchantPresentActionSupport() {
		return merchantPresentActionSupport;
	}
	public void setMerchantPresentActionSupport(
			MerchantPresentActionSupport merchantPresentActionSupport) {
		this.merchantPresentActionSupport = merchantPresentActionSupport;
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

	public List<MerchantPresent> getMerchantPresentList() {
		return merchantPresentList;
	}

	public void setMerchantPresentList(List<MerchantPresent> merchantPresentList) {
		this.merchantPresentList = merchantPresentList;
	}

	public MerchantPresent getMerchantPresent() {
		return merchantPresent;
	}

	public void setMerchantPresent(MerchantPresent merchantPresent) {
		this.merchantPresent = merchantPresent;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
