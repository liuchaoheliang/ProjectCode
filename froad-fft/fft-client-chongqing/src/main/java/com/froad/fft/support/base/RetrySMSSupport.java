package com.froad.fft.support.base;


import com.froad.fft.bean.Result;

public interface RetrySMSSupport {

	public Result retryPresell(Long transId,String ip);
}
