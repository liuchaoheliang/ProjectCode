package com.froad.CB.dao.tag.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.common.Pager;
import com.froad.CB.dao.tag.TagDistrictBDAO;
import com.froad.CB.po.tag.TagDistrictB;

public class TagDistrictBDAOImpl  implements TagDistrictBDAO {
	private SqlMapClientTemplate sqlMapClientTemplate;
    
    public void insert(TagDistrictB record) {
    	sqlMapClientTemplate.insert("tagDistrictB.insert", record);
    }

    public int updateByPrimaryKeySelective(TagDistrictB record) {
        int rows = sqlMapClientTemplate.update("tagDistrictB.updateByPrimaryKeySelective", record);
        return rows;
    }

    
    public TagDistrictB selectByPrimaryKey(Integer id) {
        TagDistrictB record = (TagDistrictB) sqlMapClientTemplate.queryForObject("tagDistrictB.selectByPrimaryKey", id);
        return record;
    }

    
    public int deleteByPrimaryKey(Integer id) {
        int rows = sqlMapClientTemplate.delete("tagDistrictB.deleteByPrimaryKey", id);
        return rows;
    }

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public List<TagDistrictB> selectTagDistrictB(TagDistrictB queryCon) {
		// TODO Auto-generated method stub
		return sqlMapClientTemplate.queryForList("tagDistrictB.selectTagDistrictB", queryCon);
	}
    
	/**
	 * 描述：查询商户的商圈二标签
	 * @param merchantId
	 * @return List<TagDistrictB>
	 */
	public List<TagDistrictB> getMerchantDistrictB(String merchantId){
		return sqlMapClientTemplate.queryForList("tagDistrictB.GET-DISTRICTB-TAGVALUE-BY-MERCHANTID",merchantId);
	}
	
	@Override
	public List<TagDistrictB> getAllTagDistrictB() {
		return sqlMapClientTemplate.queryForList("tagDistrictB.getAllTagDistrictB");
	}

	@Override
	public List<TagDistrictB> getAllTagDistrictBByEnabled() {
		return sqlMapClientTemplate.queryForList("tagDistrictB.getAllTagDistrictBByEnabled");
	}

	@Override
	public Pager getTagDistrictBByPager(Pager pager) {
		Map pagerMap=new HashMap();
		if(pager.getProperty()!=null){
			pagerMap.put("property", pager.getProperty());
			pagerMap.put("keyword", pager.getKeyword());
		}
		pagerMap.put("orderBy", pager.getOrderBy());
		pagerMap.put("orderType", pager.getOrderType());
		pagerMap.put("startRow", pager.getStartRow());
		pagerMap.put("pageSize", pager.getPageSize());
		
		Map pagerCountMap=new HashMap();
		if(pager.getProperty()!=null){
			pagerMap.put("property", pager.getProperty());
			pagerMap.put("keyword", pager.getKeyword());
		}
		
		Integer totalCount=(Integer) sqlMapClientTemplate.queryForObject("tagDistrictB.getTagDistrictBListByPagerCount",pagerCountMap);
		List<TagDistrictB> list=sqlMapClientTemplate.queryForList("tagDistrictB.getTagDistrictBListByPager", pagerMap);
		
		pager.setTotalCount(totalCount);
		pager.setList(list);
		
		return pager;
	}
}