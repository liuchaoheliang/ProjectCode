package com.froad.fft.persistent.api;

import com.froad.fft.persistent.entity.MerchantPhoto;

public interface MerchantPhotoMapper {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>保存MerchantPhoto</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月7日 下午1:42:04 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Long saveMerchantPhoto(MerchantPhoto merchantPhoto);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键更新数据</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月7日 下午2:20:09 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Boolean updateMerchantPhotoById(MerchantPhoto merchantPhoto);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键查找对象</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月7日 下午2:26:49 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public MerchantPhoto selectMerchantPhotoById(Long id);
}
