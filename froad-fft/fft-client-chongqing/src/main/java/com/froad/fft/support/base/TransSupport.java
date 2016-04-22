
	 /**
  * 文件名：TransSupport.java
  * 版本信息：Version 1.0
  * 日期：2014年4月5日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.support.base;

import com.froad.fft.bean.Result;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.trans.TransDto;
import com.froad.fft.dto.TransQueryDto;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月5日 下午12:03:55 
 */
public interface TransSupport {
	
	
	/**
	  * 方法描述：分页查询预售商品交易
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月5日 下午12:04:45
	  */
	public PageDto getPresellTransByPager(PageDto page);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>创建交易</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月5日 下午12:24:39 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Result createTrans(TransDto transDto);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>支付订单</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月5日 下午5:38:08 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Result doPayTrans(String transSn);
	
	
	/**
	  * 方法描述：根据Id查询Trans
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月7日 下午3:51:31
	  */
	public TransQueryDto getByTransId(Long id);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过交易Sn查询交易</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月8日 上午11:08:53 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public TransQueryDto getTransByTransSn(String transSn);
	
	/**
	*<p>预售交易状态</p>
	* @author larry
	* @datetime 2014-4-15下午02:04:56
	* @return String
	* @throws 
	* @example<br/>
	 */
	public String getPresellState(String transSn);
	
	/**
	*<p>购买数量验证</p>
	* @author larry
	* @datetime 2014-4-25上午10:03:26
	* @return Result
	* @throws 
	* @example<br/>
	*
	 */
	public Result validateQuantity(Long memberCode,Long productId,
			Integer min,Integer max,Integer quantity);
}
