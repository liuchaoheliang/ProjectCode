/**
 * 文件名：RefundsService.java
 * 版本信息：Version 1.0
 * 日期：2014年3月27日
 * author: 刘超 liuchao@f-road.com.cn
 */
package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.Result;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.ProductImageDto;
import com.froad.fft.dto.RefundsDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * 类描述：
 *
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月27日 下午5:28:15
 */
public interface RefundsExportService
{

    /**
     * 方法描述：添加退款记录
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014年3月27日 下午5:39:30
     */
    public Long saveRefunds(ClientAccessType clientAccessType, ClientVersion clientVersion, RefundsDto refundsDto) throws FroadException;

    /**
     * 方法描述：根据Id更新数据
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014年3月27日 下午5:39:33
     */
    public Boolean updateRefundsById(ClientAccessType clientAccessType, ClientVersion clientVersion, RefundsDto refundsDto) throws FroadException;

    /**
     * 方法描述：根据Id查询数据
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014年3月27日 下午5:39:33
     */
    public RefundsDto findRefundsById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id) throws FroadException;

    /**
     * 方法描述：分页查询
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014年3月27日 下午5:39:35
     */
    public PageDto<RefundsDto> findRefundsByPage(ClientAccessType management, ClientVersion version, PageDto<RefundsDto> pageDto) throws FroadException;

    /**
     * 方法描述：交易退款退分
     *
     * @param clientAccessType 客户端类型
     * @param clientVersion    客户端版本号
     * @param: refundsDto 退款交易dto
     * @return: Result
     * @version: 1.0
     * @author: 侯国权 lijinkui@f-road.com.cn
     * @time: 2014年4月14日 上午9:25:51
     */
    public Result doRefund(ClientAccessType clientAccessType, ClientVersion clientVersion,RefundsDto refundsDto);
}
