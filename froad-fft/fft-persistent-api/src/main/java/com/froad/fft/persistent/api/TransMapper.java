package com.froad.fft.persistent.api;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.Trans;

import java.util.List;

public interface TransMapper {

	public Long saveTrans(Trans trans);
	public Boolean updateTransById(Trans trans);
	public Trans selectTransById(Long id);
	
	/**
	  * 方法描述：查询交易
	  * @param: sn
	  * @return: Trans 
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年4月3日 上午11:48:47
	  */
	public Trans selectBySn(String sn);

    /**
   	  * 方法描述：分页查询
   	  * @param:
   	  * @return:
   	  * @version: 1.0
   	  * @author: 侯国权
   	  * @time: 2014年4月3日 上午14:32:48
   	  */
   	public List<Trans> selectTransByPage(Page page);


   	/**
   	  * 方法描述：分页总条数
   	  * @param:
   	  * @return:
   	  * @version: 1.0
   	  * @author: 刘超 liuchao@f-road.com.cn
   	  * @time: 2014年4月3日 上午14:32:50
   	  */
   	public Integer selectTransByPageCount(Page page);
   	
   	
   	/**
   	  * 方法描述：按商品id查询处理中且支付成功的预售交易
   	  * @param: productId
   	  * @return: List<Trans>
   	  * @version: 1.0
   	  * @author: 李金魁 lijinkui@f-road.com.cn
   	  * @time: 2014年4月8日 上午10:57:23
   	  */
   	public List<Trans> selectPresellTransByProductId(Long productId);
   	
   	
   	/**
   	  * 方法描述：查询所有处理中且支付成功的预售交易，已经到达预售结束时间，并且已经成团
   	  * @return: List<Trans>
   	  * @version: 1.0
   	  * @author: 李金魁 lijinkui@f-road.com.cn
   	  * @time: 2014年4月8日 上午10:58:45
   	  */
   	public List<Trans> selectPresellTrans();
   	
   	
   	/**
   	  * 方法描述：查询交易列表
   	  * @param: state 交易状态
   	  * @return: List<Trans>
   	  * @version: 1.0
   	  * @author: 李金魁 lijinkui@f-road.com.cn
   	  * @time: 2014年4月21日 下午6:15:33
   	  */
   	public List<Trans> selectByState(String state);
   	
   	
   	/**
   	  * 方法描述：查询团购和预售交易
   	  * @param: memberCode
   	  * @return: List<Trans>
   	  * @version: 1.0
   	  * @author: 李金魁 lijinkui@f-road.com.cn
   	  * @time: 2014年4月22日 上午10:54:29
   	  */
   	public List<Trans> selectGroupAndPresellByMemberCode(Long memberCode);
   	
   	
   	/**
   	  * 方法描述：将指定交易的状态更新为成功 
   	  * @param: List<Long>
   	  * @return: void
   	  * @version: 1.0
   	  * @author: 李金魁 lijinkui@f-road.com.cn
   	  * @time: 2014年4月8日 上午11:26:13
   	  */
   	public void updateStateToSuccessByIds(List<Long> ids);
   	
   	
   	/**
   	  * 方法描述：批量关闭交易，并将交易里的支付状态改为退款成功(用于部分支付执行完退分后关闭超时交易)
   	  * @param: List<Long> 交易id
   	  * @return: void
   	  * @version: 1.0
   	  * @author: 李金魁 lijinkui@f-road.com.cn
   	  * @time: 2014年4月23日 上午10:07:32
   	  */
   	public void updateStateToCloseByIds(List<Long> ids);
   	
   	
   	/**
   	  * 方法描述：关闭超过两小时没有支付的交易
   	  * @return: void
   	  * @version: 1.0
   	  * @author: 李金魁 lijinkui@f-road.com.cn
   	  * @time: 2014年4月22日 下午5:23:55
   	  */
   	public void updateTimeoutTransToClose();
   	
   	
   	/**
   	  * 方法描述：查询超过两小时仍然处于部分支付的交易
   	  * @return: List<Trans>
   	  * @version: 1.0
   	  * @author: 李金魁 lijinkui@f-road.com.cn
   	  * @time: 2014年4月22日 下午5:25:44
   	  */
   	public List<Trans> selectTimeoutTrans();
}
