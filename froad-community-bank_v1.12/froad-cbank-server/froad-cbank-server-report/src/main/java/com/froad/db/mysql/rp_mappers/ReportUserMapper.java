package com.froad.db.mysql.rp_mappers;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.ReportUser;

public interface ReportUserMapper {
	
	Integer findAllCount(@Param("endDate")Date endDate);
	
	Integer findAllCountByUserId(@Param("endDate")Date endDate);
	
	ReportUser findOneUser(ReportUser user);
	
	Boolean updateUser(ReportUser user);
	
	Boolean addUser(ReportUser user);
	
	Boolean addUserByBatch(@Param("users")List<ReportUser> users);
	
	Boolean truncateUniqueTable();
	
	Boolean insertUniqueUsers();
	
	List<ReportUser> findInfoByPage(@Param("endDate")Date endDate, @Param("page")Page<ReportUser> page);
	
	List<ReportUser> findUserCountByCardBin(@Param("endDate")Date endDate);
}
