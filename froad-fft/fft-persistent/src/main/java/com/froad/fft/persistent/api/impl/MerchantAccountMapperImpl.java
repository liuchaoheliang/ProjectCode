/**
 * 文件名：MerchantAccountMapperImpl.java
 * 版本信息：Version 1.0
 * 日期：2014年3月26日
 * author: 刘超 liuchao@f-road.com.cn
 */
package com.froad.fft.persistent.api.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.MerchantAccountMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.MerchantAccount;
import com.froad.fft.persistent.entity.Product;

import java.util.List;

/**
 * 类描述：
 *
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月26日 下午8:45:59
 */
public class MerchantAccountMapperImpl implements MerchantAccountMapper
{

    @Resource
    MerchantAccountMapper merchantAccountMapper;

    @Override
    public Long saveMerchantAccount(MerchantAccount merchantAccount)
    {
        merchantAccountMapper.saveMerchantAccount(merchantAccount);
        return merchantAccount.getId();
    }

    @Override
    public Boolean updateMerchantAccountById(MerchantAccount merchantAccount)
    {
        return merchantAccountMapper.updateMerchantAccountById(merchantAccount);
    }

    @Override
    public MerchantAccount selectMerchantAccountById(Long id)
    {
        return merchantAccountMapper.selectMerchantAccountById(id);
    }

    public List<MerchantAccount> selectMerchantAccountByPage(Page page)
    {
        return merchantAccountMapper.selectMerchantAccountByPage(page);
    }

    public Integer selectMerchantAccountByPageCount(Page page)
    {
        return merchantAccountMapper.selectMerchantAccountByPageCount(page);
    }

	@Override
	public List<MerchantAccount> selectByMerchantId(Long merchantId) {
		return merchantAccountMapper.selectByMerchantId(merchantId);
	}

}
