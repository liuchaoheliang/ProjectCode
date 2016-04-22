package com.froad.db.mysql.mappers;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.Merchant;
import com.froad.po.MerchantCount;

public interface MerchantMapper {
	
	/**
	 * find merchant count by date
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Integer findRecordCountByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	List<MerchantCount> selectTotalMerchantByPage(@Param("begDate")Date begDate, @Param("endDate")Date endDate, Page<MerchantCount> page);
	
	List<Merchant> findByMerchantCount(@Param("date")Date date, @Param("merchantCount")MerchantCount merchantCount);
	
	/**
	 * 根据商户id查询Merchant对象
	 * @param merchantId
	 * @return
	 */
	public Merchant findMerchantByMerchantId(String merchantId);
	
	/**
	 * 分页查找
	 * 
	 * @param page
	 * @return
	 */
	public List<Merchant> findAllByPage(Page<Merchant> page, @Param("date")Date date); 
	
	/**
	 * 获取总记录数
	 * 
	 * @return
	 */
	public Integer findAllRecordCount(@Param("date")Date date);
	
	
	/**
	 * 查询审核通过的所有普通商户
	 * @return
	 */
	public List<Merchant> selectAllAuditMerchantsByPage(@Param("page")Page<Merchant> page);
	
	
	/**
	 * 查询机构签约人下的机构集合信息
	 * @return
	 */
	public List<MerchantCount> selectMerchantIdListInfoByPage(@Param("begDate")Date begDate, @Param("endDate") Date endDate, Page<MerchantCount> page);
	
	
	public Integer selectMerchantIdListInfoCount(@Param("begDate")Date begDate, @Param("endDate") Date endDate);
}
