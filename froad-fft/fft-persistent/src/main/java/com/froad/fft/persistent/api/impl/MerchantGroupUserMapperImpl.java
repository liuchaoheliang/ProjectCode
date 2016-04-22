package com.froad.fft.persistent.api.impl;

import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.MerchantGroupUserMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.MerchantGroupUser;

public class MerchantGroupUserMapperImpl implements MerchantGroupUserMapper {

	@Resource
	private MerchantGroupUserMapper merchantGroupUserMapper;
	
	@Override
	public Long saveMerchantGroupUser(MerchantGroupUser temp) {
		
		merchantGroupUserMapper.saveMerchantGroupUser(temp);
		return temp.getId();
	}

	@Override
	public Boolean updateMerchantGroupUserById(MerchantGroupUser temp) {
		return merchantGroupUserMapper.updateMerchantGroupUserById(temp);
	}

	@Override
	public MerchantGroupUser selectMerchantGroupUserById(Long id) {
		return merchantGroupUserMapper.selectMerchantGroupUserById(id);
	}

	@Override
	public List<MerchantGroupUser> selectMerchantGroupUserByPage(Page page) {
		return merchantGroupUserMapper.selectMerchantGroupUserByPage(page);
	}

	@Override
	public List<MerchantGroupUser> findAllMerchantGroupUser() {
		return merchantGroupUserMapper.findAllMerchantGroupUser();
	}

	@Override
	public MerchantGroupUser findMerchantGroupUserByUserId(Long userId) {
		return merchantGroupUserMapper.findMerchantGroupUserByUserId(userId);
	}


	@Override
	public List<MerchantGroupUser> selectByConditions(
			MerchantGroupUser merchantGroupUser) {
		return merchantGroupUserMapper.selectByConditions(merchantGroupUser);
	}

}
