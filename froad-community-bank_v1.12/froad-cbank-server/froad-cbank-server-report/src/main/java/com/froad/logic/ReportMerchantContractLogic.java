package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.exceptions.FroadBusinessException;
import com.froad.po.CommonParam;
import com.froad.po.MerchantContractDeatailRes;
import com.froad.po.MerchantContractRankRes;
import com.froad.po.TypePercentInfo;

public interface ReportMerchantContractLogic {
	
	/**
	 * 签约人商户数排行榜
	 * @Title: merchantContractRank 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年6月8日
	 * @modify: froad-huangyihao 2015年6月8日
	 * @param clientId
	 * @param orgCode
	 * @return
	 * @throws FroadBusinessException
	 * @throws Exception
	 * @throws
	 */
	List<MerchantContractRankRes> merchantContractRank(CommonParam param) throws FroadBusinessException, Exception;
	
	/**
	 * 商户新增类型占比
	 * @Title: merchantTypeAddPercent 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年6月8日
	 * @modify: froad-huangyihao 2015年6月8日
	 * @param param
	 * @return
	 * @throws FroadBusinessException
	 * @throws Exception
	 * @throws
	 */
	List<TypePercentInfo> merchantTypeAddPercent(CommonParam param) throws FroadBusinessException, Exception;
	
	/**
	 * 签约人新增商户数排行
	 * @Title: merchantContractAddRank 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年6月8日
	 * @modify: froad-huangyihao 2015年6月8日
	 * @param param
	 * @return
	 * @throws FroadBusinessException
	 * @throws Exception
	 * @throws
	 */
	List<MerchantContractRankRes> merchantContractAddRank(CommonParam param) throws FroadBusinessException, Exception;
	
	/**
	 * 签约人商户统计详细列表
	 * @Title: merchantContractDetailList 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年6月8日
	 * @modify: froad-huangyihao 2015年6月8日
	 * @param param
	 * @return
	 * @throws FroadBusinessException
	 * @throws Exception
	 * @throws
	 */
	List<MerchantContractDeatailRes> merchantContractDetailList(CommonParam param) throws FroadBusinessException, Exception;
	
	/**
	 * 签约人商户统计详细列表分页查询
	 * @Title: merchantContractDetailListByPage 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年6月8日
	 * @modify: froad-huangyihao 2015年6月8日
	 * @param param
	 * @return
	 * @throws FroadBusinessException
	 * @throws Exception
	 * @throws
	 */
	Page<MerchantContractDeatailRes> merchantContractDetailListByPage(Page<MerchantContractDeatailRes> page, CommonParam param) throws FroadBusinessException, Exception;
}
