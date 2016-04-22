package com.froad.CB.service.impl;

import java.util.List;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import com.froad.CB.dao.tag.TagClassifyADAO;
import com.froad.CB.dao.tag.TagClassifyBDAO;
import com.froad.CB.dao.tag.TagDistrictADAO;
import com.froad.CB.dao.tag.TagDistrictBDAO;
import com.froad.CB.dao.tag.TagMAPDAO;
import com.froad.CB.po.tag.TagMAP;
import com.froad.CB.service.TagMapService;
 
@WebService(endpointInterface="com.froad.CB.service.TagMapService")
public class TagMapServiceImpl implements TagMapService {
	private Logger logger=Logger.getLogger(TagMapServiceImpl.class);
	private TagClassifyADAO tagClassifyADAO;
	private TagClassifyBDAO tagClassifyBDAO;
	private TagDistrictADAO tagDistrictADAO;
	private TagDistrictBDAO tagDistrictBDAO;
	private TagMAPDAO tagMAPDAO;

	@Override
	public List<TagMAP> getTagMapsByMerchantId(String merchantId) {
		if(merchantId==null||"".equals(merchantId)){
			logger.error("查询失败，商户编号为空！");
			return null;
		}
		return tagMAPDAO.getTagMapsByMerchantId(merchantId);
	}

	@Override
	public List<TagMAP> getTagMapsByMerchantName(String merchantName) {
		if(merchantName==null||"".equals(merchantName)){
			return tagMAPDAO.getTopTagMaps();
		}
		return tagMAPDAO.getTagMapsByMerchantName(merchantName);
	}

	public void setTagMAPDAO(TagMAPDAO tagMAPDAO) {
		this.tagMAPDAO = tagMAPDAO;
	}

	public TagMAPDAO getTagMAPDAO() {
		return tagMAPDAO;
	}

	public TagClassifyADAO getTagClassifyADAO() {
		return tagClassifyADAO;
	}

	public void setTagClassifyADAO(TagClassifyADAO tagClassifyADAO) {
		this.tagClassifyADAO = tagClassifyADAO;
	}

	public TagClassifyBDAO getTagClassifyBDAO() {
		return tagClassifyBDAO;
	}

	public void setTagClassifyBDAO(TagClassifyBDAO tagClassifyBDAO) {
		this.tagClassifyBDAO = tagClassifyBDAO;
	}

	public TagDistrictADAO getTagDistrictADAO() {
		return tagDistrictADAO;
	}

	public void setTagDistrictADAO(TagDistrictADAO tagDistrictADAO) {
		this.tagDistrictADAO = tagDistrictADAO;
	}

	public TagDistrictBDAO getTagDistrictBDAO() {
		return tagDistrictBDAO;
	}

	public void setTagDistrictBDAO(TagDistrictBDAO tagDistrictBDAO) {
		this.tagDistrictBDAO = tagDistrictBDAO;
	}

	@Override
	public List<TagMAP> getTagsByMerchantId(String merchantId) {
		if(merchantId==null||"".equals(merchantId)){
			logger.error("商户编号为空，查询失败");
			return null;
		}
		return tagMAPDAO.selectTagsByMerchantId(merchantId);
	}

	@Override
	public Integer add(TagMAP tagMap) {
		if(tagMap==null){
			logger.error("参数为空");
			return null;
		}
		return tagMAPDAO.insert(tagMap);
	}

	@Override
	public boolean updateById(TagMAP tagMap) {
		if(tagMap==null){
			logger.error("参数为空");
			return false;
		}
		try {
			tagMAPDAO.updateById(tagMap);
			return true;
		} catch (Exception e) {
			logger.error("更新TagMAP时出现异常",e);
			return false;
		}
		
	}

	@Override
	public void insertBatch(List tagMAP) {
		if(tagMAP==null){
			logger.error("参数为空");
		}
		tagMAPDAO.insertBatch(tagMAP);
	}

	
}
