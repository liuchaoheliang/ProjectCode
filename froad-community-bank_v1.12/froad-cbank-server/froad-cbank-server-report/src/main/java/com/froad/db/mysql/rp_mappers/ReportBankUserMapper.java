package com.froad.db.mysql.rp_mappers;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.ReportBankUser;

public interface ReportBankUserMapper {
	
	/**
	 * 查询cb_report_bank_user有多少条数据
	 * @Title: selectAllCount 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年6月15日
	 * @modify: froad-huangyihao 2015年6月15日
	 * @return
	 * @throws
	 */
	Integer selectAllCount();
	
	/**
	 * 根据机构号和签约人查询
	 * @Title: selectIsExist 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年6月3日
	 * @modify: froad-huangyihao 2015年6月3日
	 * @param user
	 * @return
	 * @throws
	 */
	ReportBankUser selectIsExist(ReportBankUser user);
	
	/**
	 * 批量添加
	 * @Title: addByBatch 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年6月3日
	 * @modify: froad-huangyihao 2015年6月3日
	 * @param users
	 * @return
	 * @throws
	 */
	Boolean addByBatch(@Param("users")List<ReportBankUser> users);
	
	/**
	 * 查询所有
	 * @Title: findAll 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年6月3日
	 * @modify: froad-huangyihao 2015年6月3日
	 * @return
	 * @throws
	 */
	List<ReportBankUser> findAllByPage(@Param("endDate")Date endDate, Page<ReportBankUser> page);
	
	/**
	 * find total record count
	 * 
	 * @return
	 */
	Integer findTotalRecordCount(@Param("endDate")Date endDate);
}
