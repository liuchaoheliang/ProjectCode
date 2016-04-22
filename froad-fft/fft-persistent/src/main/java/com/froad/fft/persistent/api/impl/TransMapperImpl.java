package com.froad.fft.persistent.api.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.TransMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.Trans;

import java.util.List;

public class TransMapperImpl implements TransMapper
{

    @Resource
    private TransMapper transMapper;

    @Override
    public Long saveTrans(Trans trans)
    {
        transMapper.saveTrans(trans);
        return trans.getId();
    }

    @Override
    public Boolean updateTransById(Trans trans)
    {
        return transMapper.updateTransById(trans);
    }

    @Override
    public Trans selectTransById(Long id)
    {
        return transMapper.selectTransById(id);
    }

    @Override
    public Trans selectBySn(String sn)
    {
        return transMapper.selectBySn(sn);
    }

    public List<Trans> selectTransByPage(Page page)
    {
        return transMapper.selectTransByPage(page);
    }

    public Integer selectTransByPageCount(Page page)
    {
        return transMapper.selectTransByPageCount(page);
    }

	@Override
	public List<Trans> selectPresellTransByProductId(Long productId) {
		return transMapper.selectPresellTransByProductId(productId);
	}

	@Override
	public List<Trans> selectPresellTrans() {
		return transMapper.selectPresellTrans();
	}

	@Override
	public void updateStateToSuccessByIds(List<Long> ids) {
		if(ids==null||ids.size()==0){
			return;
		}
		transMapper.updateStateToSuccessByIds(ids);
	}
	
	@Override
	public List<Trans> selectByState(String state) {
		return transMapper.selectByState(state);
	}

	@Override
	public List<Trans> selectGroupAndPresellByMemberCode(Long memberCode) {
		return transMapper.selectGroupAndPresellByMemberCode(memberCode);
	}

	@Override
	public void updateTimeoutTransToClose() {
		transMapper.updateTimeoutTransToClose();
	}

	@Override
	public List<Trans> selectTimeoutTrans() {
		return transMapper.selectTimeoutTrans();
	}

	@Override
	public void updateStateToCloseByIds(List<Long> ids) {
		transMapper.updateStateToCloseByIds(ids);
	}

}
