package com.froad.action.support;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.froad.client.tag.TagClassifyA;
import com.froad.client.tag.TagClassifyAService;
import com.froad.client.tag.TagClassifyB;
import com.froad.client.tag.TagClassifyBService;
import com.froad.client.tag.TagDistrictA;
import com.froad.client.tag.TagDistrictAService;
import com.froad.client.tag.TagDistrictB;
import com.froad.client.tag.TagDistrictBService;
import com.froad.client.tag.TagMAP;
import com.froad.client.tag.TagMapService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-20  
 * @version 1.0
 * 标签 client service  ActionSupport
 */
public class TagActionSupport {
	private static Logger logger = Logger.getLogger(TagActionSupport.class);
	private TagClassifyAService tagClassifyAService;
	private TagClassifyBService tagClassifyBService;
	private TagDistrictAService tagDistrictAService;
	private TagDistrictBService tagDistrictBService;
	private TagDistrictB tagDistrictB;
	private TagMapService tagMapService;
	
	public List<TagMAP> getTagMapsByMerchantId(String merchantId){
		return tagMapService.getTagsByMerchantId(merchantId);
	}
	/**
	 * 查询所有启用的二级分类
	 * @return
	 */
	public List<TagClassifyB> getAllTagClassifyBByEnabled(){
		List<TagClassifyB> tagClassifyBList = new ArrayList<TagClassifyB>();
		try {
			tagClassifyBList = tagClassifyBService.getAllTagClassifyBByEnabled();
		} catch (Exception e) {
			logger.error("查询所有启用的二级分类异常", e);
			if(tagClassifyBList == null){
				tagClassifyBList = new ArrayList<TagClassifyB>();
			}
		}
		return tagClassifyBList;
	}
	
	
	/**
	 * 查询所有启用的二级商圈
	 * @return
	 */
	public List<TagDistrictB> getAllTagDistrictBByEnabled(){
		List<TagDistrictB> tagDistrictBList=new ArrayList<TagDistrictB>();
		try {
			tagDistrictBList = tagDistrictBService.getAllTagDistrictBByEnabled();
		} catch (Exception e) {
			logger.error("查询所有启用的二级商圈异常", e);
			if(tagDistrictBList == null){
				tagDistrictBList = new ArrayList<TagDistrictB>();
			}
		}
		return tagDistrictBList;
	}
	
	/**
	 * ID 查找 二级分类
	 * @param id
	 * @return
	 */
	public TagClassifyB getTagClassifyBById(String id){
		TagClassifyB tagClassifyB = new TagClassifyB();
		try{
			tagClassifyB = tagClassifyBService.getTagClassifyBById(Integer.parseInt(id));
		}
		catch(Exception e){
			logger.error("ID 查找二级分类异常", e);
			if(tagClassifyB == null){
				tagClassifyB = new TagClassifyB();
			}
		}
		return tagClassifyB;
	}
	
	/**
	 * ID 查找 二级商圈
	 * @param id
	 * @return
	 */
	public TagDistrictB getTagDistrictBById(String id){
		TagDistrictB tagDistrictB = new TagDistrictB();
		try{
			tagDistrictB = tagDistrictBService.getTagDistrictBById(Integer.parseInt(id));
		}
		catch(Exception e){
			logger.error("ID 查找二级商圈异常", e);
			if(tagDistrictB == null){
				tagDistrictB = new TagDistrictB();
			}
		}
		return tagDistrictB;
	}
	
	/**
	 * 查找 所有二级商圈
	 * @param 
	 * @return List<TagDistrictB>
	 */
	public List<TagDistrictB> findAllDistrictB(){
		List<TagDistrictB> list = new ArrayList<TagDistrictB>();
		try {
			list = tagDistrictBService.getAllTagDistrictB();
		} catch (Exception e) {
			logger.error("TagActionSupport.findAllDistrictB 查找二级商圈异常", e);
			if(list == null){
				list = new ArrayList<TagDistrictB>();
			}
		}
		return list;
	}
	
	/**
	 * 查找 所有一级分类
	 * @param 
	 * @return List<TagClassifyA>
	 */
	public List<TagClassifyA> findAllClassifyA(){
		List<TagClassifyA> list = new ArrayList<TagClassifyA>();
		try {
			list = tagClassifyAService.getAllTagClassifyA();
		} catch (Exception e) {
			logger.error("TagActionSupport.findAllClassifyA 查找一级分类异常", e);
			if(list == null){
				list = new ArrayList<TagClassifyA>();
			}
		}
		return list;
	}
	
	/**
	 * 查找 所有一级商圈
	 * @param 
	 * @return List<TagDistrictA>
	 */
	public List<TagDistrictA> findAllDistrictA(){
		List<TagDistrictA> list = new ArrayList<TagDistrictA>();
		try {
			list = tagDistrictAService.getAllTagDistrictA();
		} catch (Exception e) {
			logger.error("TagActionSupport.findAllDistrictA 查找一级商圈异常", e);
			if(list == null){
				list = new ArrayList<TagDistrictA>();
			}
		}
		return list;
	}
	
	/**
	 * 查找 所有二级分类
	 * @param 
	 * @return List<TagClassifyB>
	 */
	public List<TagClassifyB> findAllClassifyB(){
		List<TagClassifyB> list = new ArrayList<TagClassifyB>();
		try {
			list = tagClassifyBService.getAllTagClassifyB();
		} catch (Exception e) {
			logger.error("TagActionSupport.findAllClassifyB 查找二级分类异常", e);
			if(list == null){
				list = new ArrayList<TagClassifyB>();
			}
		}
		return list;
	}
	
	/**
	 * 分页查找 商圈
	 * @param pager
	 * @return
	 */
//	public Pager queryTagDistrictBByPager(Pager pager){
//		try{
//			pager=tagDistrictBService.queryTagDistrictBByPager(pager);
//		}
//		catch(Exception e){
//			logger.error("分页查找二级商圈异常", e);
//		}
//		return pager;
//	}
	
	public TagClassifyAService getTagClassifyAService() {
		return tagClassifyAService;
	}
	public void setTagClassifyAService(TagClassifyAService tagClassifyAService) {
		this.tagClassifyAService = tagClassifyAService;
	}
	public TagClassifyBService getTagClassifyBService() {
		return tagClassifyBService;
	}
	public void setTagClassifyBService(TagClassifyBService tagClassifyBService) {
		this.tagClassifyBService = tagClassifyBService;
	}
	public TagDistrictAService getTagDistrictAService() {
		return tagDistrictAService;
	}
	public void setTagDistrictAService(TagDistrictAService tagDistrictAService) {
		this.tagDistrictAService = tagDistrictAService;
	}
	public TagDistrictBService getTagDistrictBService() {
		return tagDistrictBService;
	}
	public void setTagDistrictBService(TagDistrictBService tagDistrictBService) {
		this.tagDistrictBService = tagDistrictBService;
	}
	public TagMapService getTagMapService() {
		return tagMapService;
	}
	public void setTagMapService(TagMapService tagMapService) {
		this.tagMapService = tagMapService;
	}


	public TagDistrictB getTagDistrictB() {
		return tagDistrictB;
	}


	public void setTagDistrictB(TagDistrictB tagDistrictB) {
		this.tagDistrictB = tagDistrictB;
	}
	
	
}
