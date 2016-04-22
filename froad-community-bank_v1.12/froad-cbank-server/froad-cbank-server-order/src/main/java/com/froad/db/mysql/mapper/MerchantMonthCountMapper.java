
package com.froad.db.mysql.mapper;

import com.froad.po.MerchantMonthCount;

public interface MerchantMonthCountMapper {

    /**
     * 插入新记录
     * @param mmc
     */
    public void insertNewRecord(MerchantMonthCount mmc);

    /**
     * 累加数量
     * @param mmc
     * @return
     */
    public boolean plusMerchantMonthCount(MerchantMonthCount mmc);
    
    /**
     * 减少数量
     * @param mmc
     * @return
     */
    public boolean minusMerchantMonthCount(MerchantMonthCount mmc);
    
    /**
     * 查询记录ID
     * @param mmc
     * @return
     */
    public Long selectRecordId(MerchantMonthCount mmc);
}
