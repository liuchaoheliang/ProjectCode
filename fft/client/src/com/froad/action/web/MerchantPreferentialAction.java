package com.froad.action.web;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.froad.action.support.MerchantActionSupport;
import com.froad.action.support.MerchantPreferentialActionSupport;
import com.froad.action.support.PictureActionSupport;
import com.froad.action.support.TagActionSupport;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.merchant.Merchant;
import com.froad.client.merchantPreferential.MerchantPreferential;
import com.froad.client.tag.TagDistrictB;
import com.froad.common.SessionKey;
import com.froad.util.Assert;
import com.froad.util.Command;
import com.froad.util.command.MallCommand;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-3-1  
 * @version 1.0
 */
public class MerchantPreferentialAction extends BaseActionSupport {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 1257322977780011378L;
	private TagActionSupport tagActionSupport;
	private MerchantPreferentialActionSupport merchantPreferentialActionSupport;
	private MerchantActionSupport merchantActionSupport;
	private PictureActionSupport pictureActionSupport;
	private List<MerchantPreferential> newMerchantPreferentialList;//最新优惠活动
	private List<TagDistrictB> logoTagDistrictBList;//logo 商圈
	private MerchantPreferential merchantPreferential;//优惠信息
	private MerchantPreferential pager = new MerchantPreferential();
	private String merchantId;
	private File imgFile;
	private String imgFileFileName; 
	private String picType;//商家图片类型作为图片存储的文件件    logo:商家logo  products:商家产品   photos:商家相册图片   preferential:商家优惠
	private String message;
	
	/**
	 *  获取优惠活动
	 * @return
	 */
	public String getMerchantPreferentialInfo(){
		log.info("优惠活动信息");		
		//logo处商圈
//		tagDistrictBList();
//		String userId = (String)getSession(MallCommand.USER_ID);
//		if(!Assert.empty(userId)){
//			
//		}else{
//			return "failse";
//		}
		if(id!=null){
			merchantPreferential=merchantPreferentialActionSupport.getMerchantPreferentialById(Integer.valueOf(id));						
		}else{
			merchantPreferential = new MerchantPreferential();
		}
		
		return "merchant_preferential_info";
	}

//	private void tagDistrictBList() {
//		//分页商圈
//		com.froad.client.tag.TagDistrictB tagDistrictBPager=new com.froad.client.tag.TagDistrictB();
//		tagDistrictBPager.setPageNumber(1);
//		tagDistrictBPager.setPageSize(8);
//		tagDistrictBPager=tagActionSupport.queryTagDistrictBByPager(tagDistrictBPager);
//		List tagDistrictBPagerList=tagDistrictBPager.getList();
//		logoTagDistrictBList=new ArrayList<TagDistrictB>();
//		if(tagDistrictBPagerList!=null){
//			for(Object tdb:tagDistrictBPagerList){
//				logoTagDistrictBList.add((TagDistrictB)tdb);
//			}
//		}
//	}
	
	/**
	  * 方法描述：查询最新优惠
	  * @param: 
	  * @return: json
	  */
	public void findNewMerchantPreferentialList(){
		log.info("查询最新优惠开始！");
		JSONArray array = new JSONArray();
		JSONObject json = new JSONObject();
		Date date = new Date();
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			//优惠活动
			MerchantPreferential MerchantPreferentialPager=new MerchantPreferential();
			MerchantPreferentialPager.setPageNumber(1);
			MerchantPreferentialPager.setPageSize(9);
			MerchantPreferentialPager.setState(Command.FBU_USERED_STATE);
			MerchantPreferentialPager=merchantPreferentialActionSupport.queryMerchantPreferentialByPager(MerchantPreferentialPager);
			List merchantPreferentialPagerList=MerchantPreferentialPager.getList();
			if(merchantPreferentialPagerList!=null && merchantPreferentialPagerList.size()>0){
				json.put("reno",Command.respCode_SUCCESS);
				MerchantPreferential mpreferential = null;
				for(Object m:merchantPreferentialPagerList){	
					mpreferential = (MerchantPreferential)m;
					JSONObject orgs = new JSONObject();
					orgs.put("id",mpreferential.getId());
					orgs.put("createTime",mpreferential.getCreateTime()==null || "".equals(mpreferential.getCreateTime())?df1.format(new Date()):df1.format(df1.parse(mpreferential.getCreateTime())));
					orgs.put("txt1",mpreferential.getTxt1());
					array.add(orgs);
				}
				json.put("newMerchantPreferentialList", array);
			}else{
				json.put("reno",Command.respCode_FAIL);
				json.put("points","0");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		sendMsg(json.toString());
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
			pager = new MerchantPreferential();
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
		pager=merchantPreferentialActionSupport.queryMerchantPreferentialByPager(pager);		
		return "list";
	}
	
	/**
	 * 增加
	 * @return
	 */
	public String add(){
		Merchant m = new Merchant();
		if(merchantPreferential == null){
			merchantPreferential = new MerchantPreferential();			
		}
		String userId = (String)getSession(MallCommand.USER_ID);
		if(!Assert.empty(userId)){
			m = merchantActionSupport.getMerchantInfo(userId);
			merchantPreferential.setPointRate(Assert.empty(m.getPointRate())?"":m.getPointRate());
			merchantPreferential.setPreferentialRate(Assert.empty(m.getPreferentialRate())?"":m.getPreferentialRate());
			//merchantPreferential.setPhotoUrl(m.getCompanyLogoUrl());
			merchantId = m.getId()!=null?m.getId().toString():"";
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
			merchantPreferential = merchantPreferentialActionSupport.getMerchantPreferentialById(Integer.valueOf(id));	
			merchantId = merchantPreferential.getMerchantId();
			if(merchantPreferential == null){
				merchantPreferential = new MerchantPreferential();			
			}
		}else{
			merchantPreferential = new MerchantPreferential();
		}
		return "input";
	}
	
	/**
	 * 保存
	 * @return
	 */
	public String save(){
		MerchantPreferential mp = new MerchantPreferential();
		MerchantPreferential mpres = null;
		mp.setArray(merchantPreferential.getArray());
		Integer count = null;
		if(merchantPreferential != null && imgFile !=null
				&& !Assert.empty(merchantPreferential.getTxt1())
				&& !Assert.empty(merchantPreferential.getTxt2())){
			if(Assert.empty(merchantPreferential.getMerchantId())){//若商户ID为空则通过userId获取
				Merchant m = new Merchant();
				String userId = (String)getSession(MallCommand.USER_ID);
				if(!Assert.empty(userId)){
					m = merchantActionSupport.getMerchantInfo(userId);
					merchantPreferential.setMerchantId(m.getId()!=null?m.getId().toString():"");
					merchantId = m.getId()!=null?m.getId().toString():"";
				}else{
					return "failse";
				}
			}
			mp.setMerchantId(merchantPreferential.getMerchantId());
//				if(!Assert.empty(merchantProduct.getTxt1())){
//					String txt1 = urlDecode(urlDecode(merchantProduct.getTxt1(),"utf-8"), "utf-8");		
//				}
//				if(!Assert.empty(merchantProduct.getTxt2())){
//					String txt2 = urlDecode(urlDecode(merchantProduct.getTxt2(),"utf-8"), "utf-8");		
//				}
			
//				mp.setPrice(Assert.empty(merchantProduct.getPrice())?"":merchantProduct.getPrice());
			mp.setPointRate(merchantPreferential.getPointRate());
			mp.setPreferentialRate(merchantPreferential.getPreferentialRate());
			mp.setTxt1(merchantPreferential.getTxt1());
			mp.setTxt2(merchantPreferential.getTxt2());
			//mp.setPhotoUrl(merchantPreferential.getPhotoUrl());	
			if(imgFileFileName !=null && merchantPreferential.getMerchantId()!=null){
				String picurl = pictureActionSupport.uploadPicture(imgFile,imgFileFileName,merchantPreferential.getMerchantId(),picType);
				if(Assert.empty(picurl)){//增加提示
					message = "上传图片格式错误或文件大小超过1MB";
				}
				mp.setPhotoUrl(picurl);//保存图片并返回图片路径					
			}		
			if(Assert.empty(mp.getPhotoUrl())){
				return edit();
			}
			if(Assert.empty(merchantPreferential.getRemark())){		
				mp.setRemark("");
			}
			//mp.setPrice();
			//mp.setRemark(value);
			//mp.setTxt1(merchantProduct.getTxt1());
			//mp.setTxt2(merchantProduct.getTxt1());
			mp.setState(Command.FBU_USERED_STATE);//默认启用
			count = merchantPreferentialActionSupport.addMerchantPreferentials(mp);
		}else{
			message = "优惠信息请填写完整";
			return edit();
		}
		return list();
	}
	
	/**
	 * 修改
	 * @return
	 */
	public String update(){
		MerchantPreferential mpp = new MerchantPreferential();
		MerchantPreferential mp = new MerchantPreferential();
		mp.setArray(merchantPreferential.getArray());
		MerchantPreferential mpres = null;
		Integer count = null;
		if(merchantPreferential != null &&!Assert.empty(merchantPreferential.getId())  && (imgFile!=null  
				|| !Assert.empty(merchantPreferential.getTxt1())
				|| !Assert.empty(merchantPreferential.getTxt2()))){
			if(Assert.empty(merchantPreferential.getMerchantId())){//若商户ID为空则通过userId获取
				Merchant m = new Merchant();
				String userId = (String)getSession(MallCommand.USER_ID);
				if(!Assert.empty(userId)){
					m = merchantActionSupport.getMerchantInfo(userId);
					merchantPreferential.setMerchantId(m.getId()!=null?m.getId().toString():"");
				}else{
					return "failse";
				}
			}
//				if(!Assert.empty(merchantPreferential.getTxt1())){
//					String txt1 = urlDecode(urlDecode(merchantPreferential.getTxt1(),"utf-8"), "utf-8");		
//					mp.setTxt1(txt1);
//				}
//				if(!Assert.empty(merchantProduct.getTxt1())){
//				String txt1 = urlDecode(urlDecode(merchantProduct.getTxt1(),"utf-8"), "utf-8");		
//			}
//			if(!Assert.empty(merchantProduct.getTxt2())){
//				String txt2 = urlDecode(urlDecode(merchantProduct.getTxt2(),"utf-8"), "utf-8");		
//			}
//				mp.setPrice(Assert.empty(merchantProduct.getPrice())?"":merchantProduct.getPrice());
			mp.setPointRate(merchantPreferential.getPointRate());
			mp.setPreferentialRate(merchantPreferential.getPreferentialRate());
			mp.setTxt1(merchantPreferential.getTxt1());
			mp.setTxt2(merchantPreferential.getTxt2());
			//mp.setPhotoUrl(merchantPreferential.getPhotoUrl());	
			//mp.setPrice();
			//mp.setRemark(value);
			//mp.setTxt1(merchantProduct.getTxt1());
			//mp.setTxt2(merchantProduct.getTxt1());
			String picurl = "pic";
			if(imgFile !=null && imgFileFileName !=null && merchantPreferential.getMerchantId()!=null){
				picurl = pictureActionSupport.uploadPicture(imgFile,imgFileFileName,merchantPreferential.getMerchantId(),picType);
				mp.setPhotoUrl(picurl);					
			}
			
			if(!Assert.empty(merchantPreferential.getState()) && Command.FBU_USERED_STATE.equals(merchantPreferential.getState())){
				//mp.setState(Command.FBU_VERIFY_STATE);		
			}
			mp.setId(merchantPreferential.getId());
			mpp = merchantPreferentialActionSupport.getMerchantPreferentialById(merchantPreferential.getId());
//			if(mpp !=null && Command.FBU_VERIFY_STATE.equals(mpp.getState())){
//				//审核中不能修改
//			}else{
			if(Assert.empty(picurl)){
				id = String.valueOf(merchantPreferential.getId());
				message = "上传图片格式错误或文件大小超过1MB";
				return edit();
			}else{
				mpres = merchantPreferentialActionSupport.updMerchantPreferentials(mp);		
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
	public void lineDownPreferentialJSON(){
		JSONObject json = new JSONObject();
		MerchantPreferential mpres = null;
		json.put("reno", Command.respCode_FAIL);
		try {
			if(merchantPreferential != null && !Assert.empty(merchantPreferential.getId())){
				merchantPreferential.setState(Command.FBU_STOP_STATE);
				mpres = merchantPreferentialActionSupport.updMerchantPreferentials(merchantPreferential);						
				if(mpres!=null && merchantPreferential.getId().equals(mpres.getId())){
					json.put("reno",Command.respCode_SUCCESS);
					json.put("stateValue", "停用");
				}else{
					json.put("reno",Command.respCode_FAIL);
				}
			}
		} catch (Exception e) {
			log.error("MerchantPreferentialAction.lineDownProductJSON出错");
			e.printStackTrace();
		}
		sendMsg(json.toString());
	}
	
	/**
	 * 启用 返回json
	 */
	public void onLinePreferentialJSON(){
		JSONObject json = new JSONObject();
		MerchantPreferential mpreq = new MerchantPreferential();
		MerchantPreferential mpres = null;
		String sateValue = "";
		List<MerchantPreferential> preList = new ArrayList<MerchantPreferential>();
		json.put("reno", Command.respCode_FAIL);
		try {
			if(merchantPreferential != null && !Assert.empty(merchantPreferential.getId())){
				mpreq.setState(Command.FBU_USERED_STATE);
				if(!Assert.empty(merchantPreferential.getMerchantId())){
					mpreq.setMerchantId(merchantPreferential.getMerchantId());
					preList = merchantPreferentialActionSupport.getMerchantPreferentialListByMerchantId(mpreq);					
				}else{
					Merchant m = new Merchant();
					String userId = (String)getSession(MallCommand.USER_ID);
					if(!Assert.empty(userId)){
						m = merchantActionSupport.getMerchantInfo(userId);
						merchantPreferential.setMerchantId(m.getId()!=null?m.getId().toString():"");
						mpreq.setMerchantId(merchantPreferential.getMerchantId());
						preList = merchantPreferentialActionSupport.getMerchantPreferentialListByMerchantId(mpreq);	
					}
				}
				if(preList != null && preList.size()>11){
					json.put("reno","2");
					json.put("msg", "启用商家优惠不能大于12个");
				}else{
					if(Assert.empty(merchantPreferential.getState()) || !Command.FBU_DELETE_STATE.equals(merchantPreferential.getState())){
						merchantPreferential.setState(Command.FBU_USERED_STATE);	
						sateValue = "启用";
					}else{
						//merchantPreferential.setState(Command.FBU_VERIFY_STATE);
						merchantPreferential.setState(Command.FBU_USERED_STATE);
						sateValue = "进入审核";
					}
					mpres = merchantPreferentialActionSupport.updMerchantPreferentials(merchantPreferential);											
					if(mpres!=null && merchantPreferential.getId().equals(mpres.getId())){
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
	
	//删除产品(返回产品列表)
	public String deletePreferential(){
		MerchantPreferential mpp = new MerchantPreferential();
		try {
			if(merchantPreferential != null && !Assert.empty(merchantPreferential.getId())){
				if(Assert.empty(merchantPreferential.getMerchantId())){//若商户ID为空则通过userId获取
					Merchant m = new Merchant();
					String userId = (String)getSession(MallCommand.USER_ID);
					if(!Assert.empty(userId)){
						m = merchantActionSupport.getMerchantInfo(userId);
						merchantPreferential.setMerchantId(m.getId()!=null?m.getId().toString():"");
					}else{
						return "failse";
					}
				}
//				mpp = merchantPreferentialActionSupport.getMerchantPreferentialById(merchantPreferential.getId());
//				if(mpp !=null && Command.FBU_VERIFY_STATE.equals(mpp.getState())){
//					//审核中不能删除
//				}else{
					int count = merchantPreferentialActionSupport.deletePreferentialInfo(merchantPreferential);							
//				}					
				return list();
			}else{
				return list();
			}
		} catch (Exception e) {
			log.error("MerchantPreferentialAction.deleteProduct出错,产品ID："+merchantPreferential.getId());
			e.printStackTrace();
		}
		return list();
	}
	
	
	public TagActionSupport getTagActionSupport() {
		return tagActionSupport;
	}
	public void setTagActionSupport(TagActionSupport tagActionSupport) {
		this.tagActionSupport = tagActionSupport;
	}
	public MerchantPreferentialActionSupport getMerchantPreferentialActionSupport() {
		return merchantPreferentialActionSupport;
	}
	public void setMerchantPreferentialActionSupport(
			MerchantPreferentialActionSupport merchantPreferentialActionSupport) {
		this.merchantPreferentialActionSupport = merchantPreferentialActionSupport;
	}
	public MerchantActionSupport getMerchantActionSupport() {
		return merchantActionSupport;
	}
	public void setMerchantActionSupport(MerchantActionSupport merchantActionSupport) {
		this.merchantActionSupport = merchantActionSupport;
	}


	public List<MerchantPreferential> getNewMerchantPreferentialList() {
		return newMerchantPreferentialList;
	}


	public void setNewMerchantPreferentialList(
			List<MerchantPreferential> newMerchantPreferentialList) {
		this.newMerchantPreferentialList = newMerchantPreferentialList;
	}


	public List<TagDistrictB> getLogoTagDistrictBList() {
		return logoTagDistrictBList;
	}


	public void setLogoTagDistrictBList(List<TagDistrictB> logoTagDistrictBList) {
		this.logoTagDistrictBList = logoTagDistrictBList;
	}


	public MerchantPreferential getMerchantPreferential() {
		return merchantPreferential;
	}


	public void setMerchantPreferential(MerchantPreferential merchantPreferential) {
		this.merchantPreferential = merchantPreferential;
	}


	public MerchantPreferential getPager() {
		return pager;
	}


	public void setPager(MerchantPreferential pager) {
		this.pager = pager;
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

	public PictureActionSupport getPictureActionSupport() {
		return pictureActionSupport;
	}

	public void setPictureActionSupport(PictureActionSupport pictureActionSupport) {
		this.pictureActionSupport = pictureActionSupport;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
}
