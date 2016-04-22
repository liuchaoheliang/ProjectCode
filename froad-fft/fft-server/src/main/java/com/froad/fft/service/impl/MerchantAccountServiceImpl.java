/**
 * 文件名称:MerchantAccountServiceImpl.java
 * 文件描述: 商户账户接口实现
 * 产品标识: fft
 * 单元描述: fft-server
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-1
 * 历史修改:  
 */
package com.froad.fft.service.impl;

import com.froad.fft.api.service.MerchantAccountExportService;
import com.froad.fft.persistent.api.MerchantAccountMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.MerchantAccount;
import com.froad.fft.service.MerchantAccountService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
@Service("merchantAccountServiceImpl")
public class MerchantAccountServiceImpl implements MerchantAccountService
{
    private static Logger logger = Logger.getLogger(MerchantAccountServiceImpl.class);

    @Resource
    private MerchantAccountMapper merchantAccountMapper;

    @Override
    public Long saveMerchantAccount(MerchantAccount merchantAccount)
    {
        return merchantAccountMapper.saveMerchantAccount(merchantAccount);
    }

    @Override
    public Boolean updateMerchantAccountById(MerchantAccount merchantAccount)
    {
        if (merchantAccount.getId() == null)
        {
            logger.error("更新对象缺少必要Id字段值");
            return false;
        }
        return merchantAccountMapper.updateMerchantAccountById(merchantAccount);
    }

    @Override
    public MerchantAccount selectMerchantAccountById(Long id)
    {
        if (id == null)
        {
            logger.error("查询数据id为空");
            return null;
        }
        return merchantAccountMapper.selectMerchantAccountById(id);
    }

    public Page findMerchantAccountByPage(Page page)
    {
        if (page == null)
        {
            page = new Page();
        }
        page.setResultsContent(merchantAccountMapper.selectMerchantAccountByPage(page));
        page.setTotalCount(merchantAccountMapper.selectMerchantAccountByPageCount(page));
        return page;
    }

}
