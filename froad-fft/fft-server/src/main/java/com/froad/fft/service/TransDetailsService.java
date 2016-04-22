package com.froad.fft.service;

import java.util.List;

import com.froad.fft.persistent.entity.TransDetails;

/**
 * 交易详情
 *
 * @author FQ
 */
public interface TransDetailsService
{

    public Long saveTransDetails(TransDetails transDetails);

    public Boolean updateTransDetailsById(TransDetails transDetails);

    public TransDetails selectTransDetailsById(Long id);

    /**
     * 方法描述：批量添加
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014年3月31日 下午7:37:49
     */
    public void saveBatchTransDetails(List<TransDetails> list);

    /**
     * 根据交易Id查询交易明细数据
     *
     * @param transId 交易Id
     * @return 交易明细数据
     */
    public List<TransDetails>  findTransDetailsByTransId(Long transId);
}
