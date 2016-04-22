package com.froad.fft.service;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.Merchant;

import java.util.List;
import java.util.Map;

public interface MerchantService {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>保存Merchant并返回数据主键（如果主键为null则插入失败）</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月7日 上午10:09:18 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Long saveMerchant(Merchant merchant);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键更新数据</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月7日 上午10:29:55 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Boolean updateMerchantById(Merchant merchant);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键查询对象</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月7日 上午11:14:02 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Merchant selectMerchantById(Long id);
	
	
	
	/**
	  * 方法描述：分页查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月28日 上午11:59:11
	  */
	public Page findMerchantByPage(Page page);

    /**
     * 获取所有分店的商户

     * @return
     */
    public List<Merchant> findAllOutletMerchant(Map<String,Object> paraMap);
}
