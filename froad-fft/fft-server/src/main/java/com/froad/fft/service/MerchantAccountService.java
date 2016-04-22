/**
 * 文件名称:MerchantAccountService.java
 * 文件描述: 商户账户接口
 * 产品标识: fft
 * 单元描述: fft-server
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-1
 * 历史修改:  
 */
package com.froad.fft.service;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.MerchantAccount;

/**
 * 商户账户接口
 *
 * @author houguoquan_Aides
 * @version 1.0
 */
public interface MerchantAccountService
{
    /**
     * 商户账户信息保存
     *
     * @param merchantAccount 需要保存的商户账户信息
     * @return 保存结果Id
     */
    public Long saveMerchantAccount(MerchantAccount merchantAccount);

    /**
     * 根据Id修改商户账户信息
     *
     * @param merchantAccount 商户账户信息
     * @return 修改结果
     */
    public Boolean updateMerchantAccountById(MerchantAccount merchantAccount);

    /**
     * 根据Id获取商户账户信息
     *
     * @param id 商户分店Id
     * @return 商户账户实体
     */
    public MerchantAccount selectMerchantAccountById(Long id);

    /**
     * 商户账户分页查询
     *
     * @param page 分页查询page
     * @return 分页结果
     */
    public Page findMerchantAccountByPage(Page page);


}
