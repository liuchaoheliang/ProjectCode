package com.froad.db.ahui.mappers;

import java.util.List;
import java.util.Map;

import com.froad.po.Merchant;


public interface MerchantMapper
{

    /**
     * *******************************************************
     * <p> 描述: *-- <b>保存Merchant</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月7日 上午10:07:13 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Long saveMerchant(Merchant merchant);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键更新数据</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月7日 上午10:29:06 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Boolean updateMerchantById(Merchant merchant);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键查找对象</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月7日 上午11:13:32 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Merchant selectMerchantById(Long id);

    /**
     * 方法描述：分页查询
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014年3月28日 上午10:32:48
     */

    /**
     * 方法描述：分页总条数
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014年3月28日 上午10:32:50
     */

    /**
     * 方法描述：查询商户
     *
     * @param: clientId
     * @return: List<Merchant>
     * @version: 1.0
     * @author: 李金魁 lijinkui@f-road.com.cn
     * @time: 2014年4月1日 下午4:07:34
     */
    public List<Merchant> selectMerchantByClientId(Long clientId);

    /**
     * 获取所有outlet商户
     *

     * @return
     */
    public List<Merchant> selectAllOutletMerchant( Map<String,Object> paraMap);
    /**
     * 根据merchant_id查对应的org
      * @Title: selectOrgCodeOfPresell
      * @Description: TODO
      * @author: Yaren Liang 2015年5月2日
      * @modify: Yaren Liang 2015年5月2日
      * @param @param merchant
      * @param @return    
      * @return OutleBank    
      * @throws
     */
    
    
	/**
	 * 根据商户id查询Merchant对象
	 * @param merchantId
	 * @return
	 */
	public Merchant findMerchantByMerchantId(String merchantId);
}
