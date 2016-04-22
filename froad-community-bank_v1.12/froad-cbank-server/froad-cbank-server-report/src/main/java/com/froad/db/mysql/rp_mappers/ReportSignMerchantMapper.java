package com.froad.db.mysql.rp_mappers;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.MerchantContractDeatailRes;
import com.froad.po.MerchantDetailRes;
import com.froad.po.ReportBankUser;
import com.froad.po.ReportSignMerchant;

public interface ReportSignMerchantMapper {
	
	Boolean addByBatch(@Param("signs")List<ReportSignMerchant> signs);
	
	List<ReportSignMerchant> selectMerchantBussinessAmount(@Param("begDate")Date begDate, @Param("endDate")Date endDate, @Param("user")ReportBankUser user, @Param("flag")Boolean flag);
	
	/**
	 * 查询商户信息统计列表
	 * @Title: selectMerchantDetailList 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年6月3日
	 * @modify: froad-huangyihao 2015年6月3日
	 * @param begDate
	 * @param endDate
	 * @param user
	 * @return
	 * @throws
	 */
	List<ReportSignMerchant> selectMerchantDetailList(@Param("begDate")Date begDate, @Param("endDate")Date endDate, @Param("user")ReportBankUser user, @Param("flag")Boolean flag);
	
	
	/**
	 * 查询商户信息统计列表(分页)
	 * @Title: selectMerchantDetailListByPage 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年6月8日
	 * @modify: froad-huangyihao 2015年6月8日
	 * @param begDate
	 * @param endDate
	 * @param user
	 * @param page
	 * @return
	 * @throws
	 */
	List<ReportSignMerchant> selectMerchantDetailListByPage(@Param("begDate")Date begDate, @Param("endDate")Date endDate, @Param("user")ReportBankUser user, Page<MerchantDetailRes> page, @Param("flag")Boolean flag);
	
	/**
	 * 根据类型查询机构下的新增和解约商户数
	 * @Title: selectNewCancelMerchants 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年6月3日
	 * @modify: froad-huangyihao 2015年6月3日
	 * @param begDate
	 * @param endDate
	 * @param user
	 * @param type
	 * @return
	 * @throws
	 */
	List<ReportSignMerchant> selectNewCancelMerchants(@Param("begDate")Date begDate, @Param("endDate")Date endDate, @Param("user")ReportBankUser user, @Param("flag")Boolean flag);
	
	
	/**
	 * 商户类型新增占比
	 * @Title: selecTypeAddPercent 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年6月4日
	 * @modify: froad-huangyihao 2015年6月4日
	 * @param begDate
	 * @param endDate
	 * @return
	 * @throws
	 */
	List<ReportSignMerchant> selecTypeAddPercent(@Param("begDate")Date begDate, @Param("endDate")Date endDate, @Param("user")ReportBankUser user, @Param("flag")Boolean flag);
	
	
	/**
	 * 签约人商户统计详细列表
	 * @Title: selectContractDetailList 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年6月5日
	 * @modify: froad-huangyihao 2015年6月5日
	 * @param begDate
	 * @param endDate
	 * @param user
	 * @return
	 * @throws
	 */
	List<ReportSignMerchant> selectContractDetailList(@Param("begDate")Date begDate, @Param("endDate")Date endDate, @Param("user")ReportBankUser user, @Param("flag")Boolean flag);
	
	
	/**
	 * 总新增商户数
	 * @Title: getTotalNewMerchants 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年6月8日
	 * @modify: froad-huangyihao 2015年6月8日
	 * @param begDate
	 * @param endDate
	 * @param user
	 * @return
	 * @throws
	 */
	Integer getTotalNewMerchants(@Param("begDate")Date begDate, @Param("endDate")Date endDate, @Param("user")ReportBankUser user, @Param("flag")Boolean flag);
	
	/**
	 * 签约人商户统计详细列表(分页)
	 * @Title: selectContractDetailListByPage 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年6月8日
	 * @modify: froad-huangyihao 2015年6月8日
	 * @param begDate
	 * @param endDate
	 * @param user
	 * @param page
	 * @return
	 * @throws
	 */
	List<ReportSignMerchant> selectContractDetailListByPage(@Param("begDate")Date begDate, @Param("endDate")Date endDate, @Param("user")ReportBankUser user, Page<MerchantContractDeatailRes> page, @Param("flag")Boolean flag);
	
	
	
}
