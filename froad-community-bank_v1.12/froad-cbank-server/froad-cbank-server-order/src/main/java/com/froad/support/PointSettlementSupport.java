package com.froad.support;

import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.pointsettlement.PointSettlementMerchantResVo;
import com.froad.thrift.vo.pointsettlement.PointSettlementReqVo;
import com.froad.thrift.vo.pointsettlement.PointSettlementResVo;

/**
 * 积分结算查询功能
 * Description : TODO<br/>
 * Project Name : froad-cbank-server-order<br/>
 * File Name : PointSettlementSupport.java<br/>
 * 
 * Date : 2015年12月25日 下午2:47:08 <br/> 
 * @author KaiweiXiang
 * @version
 */
public interface PointSettlementSupport {
	
	/**
	 * 查询积分汇总信息
	 * Function : queryPointCount<br/>
	 * 2015年12月25日 下午2:47:26 <br/>
	 *  
	 * @author KaiweiXiang
	 * @param req
	 * @return
	 */
	public abstract PointSettlementResVo queryPointCount(PointSettlementReqVo req) ;

	/**
	 * 积分结算明细查询
	 * Function : queryPointDetail<br/>
	 * 2015年12月25日 下午2:48:00 <br/>
	 *  
	 * @author KaiweiXiang
	 * @param req
	 * @return
	 */
	public abstract PointSettlementResVo queryPointDetail(PointSettlementReqVo req) ;

	/**
	 * 商户积分结算汇总
	 * Function : queryMerchantPointCount<br/>
	 * 2015年12月25日 下午2:48:46 <br/>
	 *  
	 * @author KaiweiXiang
	 * @param req
	 * @return
	 */
	public abstract PointSettlementMerchantResVo queryMerchantPointCount(PointSettlementReqVo req) ;

	/**
	 * 积分结算报表导出
	 * Function : exportPointSettlement<br/>
	 * 2015年12月25日 下午2:49:03 <br/>
	 *  
	 * @author KaiweiXiang
	 * @param req
	 * @return
	 */
	public abstract ExportResultRes exportPointSettlement(PointSettlementReqVo req);
}

