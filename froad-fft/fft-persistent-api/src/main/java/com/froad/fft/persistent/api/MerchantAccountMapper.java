/**
 * 文件名：MerchantAccountMapper.java
 * 版本信息：Version 1.0
 * 日期：2014年3月26日
 * author: 刘超 liuchao@f-road.com.cn
 */
package com.froad.fft.persistent.api;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.Merchant;
import com.froad.fft.persistent.entity.MerchantAccount;
import com.froad.fft.persistent.entity.Product;

import java.util.List;

/**
 * 类描述：商户账户表
 *
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月26日 下午8:04:09
 */
public interface MerchantAccountMapper
{

    /**
     * 方法描述：保存数据
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014年3月26日 下午8:08:54
     */
    public Long saveMerchantAccount(MerchantAccount merchantAccount);

    /**
     * 方法描述：根据Id更新数据
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014年3月26日 下午8:08:58
     */
    public Boolean updateMerchantAccountById(MerchantAccount merchantAccount);

    /**
     * 方法描述：根据Id查询数据
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014年3月26日 下午8:09:00
     */
    public MerchantAccount selectMerchantAccountById(Long id);
    
    
    /**
      * 方法描述：查询商户账户号
      * @param: merchantId
      * @return: List<MerchantAccount>
      * @version: 1.0
      * @author: 李金魁 lijinkui@f-road.com.cn
      * @time: 2014年4月1日 下午2:48:54
      */
    public List<MerchantAccount> selectByMerchantId(Long merchantId);

    /**
     * 方法描述：分页查询
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 侯国权
     * @time: 2014年4月1日 苦逼写代码中，不过毫无心理压力^_^
     */
    public List<MerchantAccount> selectMerchantAccountByPage(Page page);

    /**
     * 方法描述：分页总条数
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 侯国权
     * @time: 2014年4月1日 苦逼写代码中，不过毫无心理压力^_^
     */
    public Integer selectMerchantAccountByPageCount(Page page);

}
