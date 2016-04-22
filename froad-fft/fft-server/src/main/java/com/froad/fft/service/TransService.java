package com.froad.fft.service;

import java.util.List;

import com.froad.fft.bean.Result;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.Trans;

/**
 * 类描述：交易相关接口声明
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014
 * @author: 李金魁 lijinkui@f-road.com.cn
 * @time: 2014年2月13日 上午9:48:29
 */
public interface TransService {
	
	
	
	/**
	 * 根据 sn查找
	 * @param sn
	 * @return
	 */
	public Trans findTransBySn(String sn);


    /**
   	 * 根据 id查找
   	 * @param id
   	 * @return
   	 */
   	public Trans findTransById(Long id);
	
	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	public Page findTransByPage(Page page);
	
	
	/**
	  * 方法描述：精品预售自然成团(由定时任务执行)
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 12, 2014 10:35:18 AM
	  */
	public void cluster();
	
	
	/**
	  * 方法描述：精品预售手动成团(由管理平台调用,做成团操作)
	  * @param: productId 商品编号
	  * @return: Result 成团操作的结果(success|fail)
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 12, 2014 1:46:50 PM
	  */
	public Result clusterByManager(Long productId);
	
	
	/**
	  * 方法描述：精品预售不成团(由管理平台调用,做成团失败操作)
	  * @param: productId 商品编号
	  * @return: Result 成团操作的结果(success|fail)
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 12, 2014 1:46:50 PM
	  */
	public Result clusterFailedByManager(Long productId);
	
	
	/**
	  * 方法描述：处理积分兑换、团购、精品预售的异常交易
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年4月9日 下午5:58:28
	  */
	public void doExceptionProductTrans();
	
	
	/**
	  * 方法描述：关闭超时交易(定时任务)
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年4月22日 下午4:19:57
	  */
	public void closeTimeoutTrans();
	
	
	/**
	  * 方法描述：查询用于页面展示的预售状态
	  * @param: sn 交易序号
	  * @return: String 状态值
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年4月14日 下午1:59:13
	  */
	public String queryPresellState(String sn);


	public List<Trans> selectGroupAndPresellByMemberCode(Long memberCode);
}
