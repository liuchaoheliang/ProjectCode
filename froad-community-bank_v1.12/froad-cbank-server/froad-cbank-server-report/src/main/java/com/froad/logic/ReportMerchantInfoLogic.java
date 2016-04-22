package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.exceptions.FroadBusinessException;
import com.froad.po.CommonParam;
import com.froad.po.MerchantBussinessRes;
import com.froad.po.MerchantDetailRes;
import com.froad.po.MerchantTrend;
import com.froad.po.TypePercentInfo;
import com.froad.thrift.vo.report.ReportMerchantOutletVo;


public interface ReportMerchantInfoLogic {
	
	/**
	 * 商户走势
	 * @Title: getMerchantTrend 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年6月4日
	 * @modify: froad-huangyihao 2015年6月4日
	 * @param clientId
	 * @param orgCode
	 * @return
	 * @throws FroadBusinessException
	 * @throws Exception
	 */
	List<MerchantTrend> getMerchantTrend(CommonParam param) throws FroadBusinessException, Exception;
	
	/**
	 * 商户类型占比
	 * @Title: getMerchantTypePercent 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年6月4日
	 * @modify: froad-huangyihao 2015年6月4日
	 * @param param
	 * @return
	 * @throws FroadBusinessException
	 * @throws Exception
	 */
	List<TypePercentInfo> getMerchantTypePercent(CommonParam param) throws FroadBusinessException, Exception;
	
	/**
	 * 商户业务(销售金额)占比
	 * @Title: getMerchantBussinessPercent 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年6月4日
	 * @modify: froad-huangyihao 2015年6月4日
	 * @param param
	 * @return
	 * @throws FroadBusinessException
	 * @throws Exception
	 */
	List<TypePercentInfo> getMerchantBussinessPercent(CommonParam param) throws FroadBusinessException, Exception;
	
	/**
	 * 商户信息统计详情列表
	 * @Title: getMerchantDetailList 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年6月4日
	 * @modify: froad-huangyihao 2015年6月4日
	 * @param param
	 * @return
	 * @throws FroadBusinessException
	 * @throws Exception
	 */
	List<MerchantDetailRes> getMerchantDetailList(CommonParam param) throws FroadBusinessException, Exception;
	
	/**
	 * 商户业务统计信息列表
	 * @Title: getMerchantBussinessList 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年6月4日
	 * @modify: froad-huangyihao 2015年6月4日
	 * @param clientId
	 * @param orgCode
	 * @return
	 * @throws FroadBusinessException
	 * @throws Exception
	 */
	List<MerchantBussinessRes> getMerchantBussinessList(CommonParam param) throws FroadBusinessException, Exception;
	
	/**
	 * 商户信息统计详情列表(分页)
	 * @Title: getMerchantDetailListByPage 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年6月8日
	 * @modify: froad-huangyihao 2015年6月8日
	 * @param page
	 * @param param
	 * @return
	 * @throws
	 */
	Page<MerchantDetailRes> getMerchantDetailListByPage(Page<MerchantDetailRes> page, CommonParam param)throws FroadBusinessException, Exception;
	
	/**
	 * 商户门店信息导出
	 * 
	 * @param param
	 * @return
	 * @throws FroadBusinessException
	 * @throws Exception
	 */
	List<ReportMerchantOutletVo> getMerchantOutletList(CommonParam param) throws FroadBusinessException, Exception;
}
