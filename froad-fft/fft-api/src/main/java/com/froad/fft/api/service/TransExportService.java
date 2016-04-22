package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.Result;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.trans.TransDto;
import com.froad.fft.dto.PayDto;
import com.froad.fft.dto.TransDetailDto;
import com.froad.fft.dto.TransQueryDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

import java.util.List;

/**
 * 交易
 *
 * @author FQ
 */
public interface TransExportService
{

    /**
     * 创建交易
     *
     * @param transDto
     * @return Result
     */
    public Result createTrans(TransDto transDto) throws FroadException;

    /**
     * 支付交易
     *
     * @param clientAccessType
     * @param clientVersion
     * @param transSn
     * @return Result
     */
    public Result doPay(ClientAccessType clientAccessType, ClientVersion clientVersion, String transSn) throws FroadException;

    /**
     * 根据id获取交易 add by 侯国权
     *
     * @param clientAccessType 客户端类型
     * @param clientVersion    客户端版本好
     * @param transId          交易id
     * @return 页面数据
     */
    public TransQueryDto findTransById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long transId);

    /**
     * 交易分页查询 add by 侯国权
     *
     * @param clientAccessType 客户端类型
     * @param clientVersion    客户端版本好
     * @param pageDto          分页
     * @return 页面数据
     */
    public PageDto<TransQueryDto> findTransByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto<TransQueryDto> pageDto) throws FroadException;

    /**
     * 根据交易Id获取支付信息 add by 侯国权
     *
     * @param clientAccessType 客户端编号
     * @param clientVersion    客户端版本号
     * @param transId          交易Id
     * @return 交易信息列表
     */
    public List<PayDto> findPaysByTransId(ClientAccessType clientAccessType, ClientVersion clientVersion, Long transId) throws FroadException;

    /**
     * 根据交易Id获取交易明细信息 add by 侯国权
     *
     * @param clientAccessType 客户端编号
     * @param clientVersion    客户端版本号
     * @param transId          交易Id
     * @return 交易信息列表
     */
    public List<TransDetailDto> findTransDetailsByTransId(ClientAccessType clientAccessType, ClientVersion clientVersion, Long transId) throws FroadException;

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过transSn查询TransDto说明</b> --* </p>
     * <p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
     * <p> 时间: 2014年4月8日 上午11:30:25 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public TransQueryDto findTransDtoBySn(ClientAccessType clientAccessType, ClientVersion clientVersion, String transSn);

    
    /**
	  * 方法描述：精品预售自然成团(由定时任务执行)
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 12, 2014 10:35:18 AM
	  */
    public void cluster(ClientAccessType clientAccessType,
			ClientVersion clientVersion);
    
    
    /**
     * 方法描述：精品预售手动成团(由管理平台调用,做成团操作)
     *
     * @param: productId 商品编号
     * @return: Result 成团操作的结果(success|fail)
     * @version: 1.0
     * @author: 侯国权
     * @time: Mar 12, 2014 1:46:50 PM
     */
    public Result clusterByManager(ClientAccessType clientAccessType, ClientVersion clientVersion,Long productId);

    /**
     * 方法描述：精品预售不成团(由管理平台调用,做成团失败操作)
     *
     * @param: productId 商品编号
     * @return: Result 成团操作的结果(success|fail)
     * @version: 1.0
     * @author: 侯国权
     * @time: Mar 12, 2014 1:46:50 PM
     */
    public Result clusterFailedByManager(ClientAccessType clientAccessType, ClientVersion clientVersion,Long productId);

    
    /**
	  * 方法描述：关闭超时交易(定时任务)
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年4月22日 下午4:19:57
	  */
	public void closeTimeoutTrans(ClientAccessType clientAccessType, ClientVersion clientVersion);

	
	 /**
	  * 方法描述：查询用于页面展示的预售状态
	  * @param: sn 交易序号
	  * @return: String 状态值
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年4月14日 下午1:59:13
	  */
	public String queryPresellState(ClientAccessType clientAccessType, ClientVersion clientVersion,String sn);

	
	public List<TransQueryDto> selectGroupAndPresellByMemberCode(Long memberCode);
    
}
