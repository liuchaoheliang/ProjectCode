package com.froad.fft.service;

import com.froad.fft.persistent.entity.MerchantPhoto;

public interface MerchantPhotoService {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>保存MerchantPhoto并返回数据主键（如果主键为null则插入失败）</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月7日 下午1:43:43 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Long saveMerchantPhoto(MerchantPhoto merchantPhoto);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键更新数据</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月7日 下午2:21:05 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Boolean updateMerchantPhotoById(MerchantPhoto merchantPhoto);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键查找对象</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月7日 下午2:27:59 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public MerchantPhoto selectMerchantPhotoById(Long id);
}
