/**
 * 文件名：RefundsExportServiceImpl.java
 * 版本信息：Version 1.0
 * 日期：2014年3月27日
 * author: 刘超 liuchao@f-road.com.cn
 */
package com.froad.fft.api.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.bean.Result;
import com.froad.fft.dto.TransDetailDto;
import com.froad.fft.enums.ClusterState;
import com.froad.fft.enums.trans.TransType;
import com.froad.fft.persistent.entity.ProductPresell;
import com.froad.fft.persistent.entity.Trans;
import com.froad.fft.persistent.entity.TransDetails;
import com.froad.fft.service.ProductPresellService;
import com.froad.fft.service.TransCoreService;
import com.froad.fft.service.TransService;
import org.springframework.beans.BeanUtils;

import com.froad.fft.api.service.RefundsExportService;
import com.froad.fft.api.support.BeanToDtoSupport;
import com.froad.fft.api.support.DtoToBeanSupport;
import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.ProductDto;
import com.froad.fft.dto.RefundsDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.bean.page.Order;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.entity.Product;
import com.froad.fft.persistent.entity.Refunds;
import com.froad.fft.service.ProductService;
import com.froad.fft.service.RefundsService;
import com.froad.fft.util.SerialNumberUtil;

/**
 * 类描述：
 *
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月27日 下午5:51:28
 */
public class RefundsExportServiceImpl implements RefundsExportService
{

    @Resource(name = "refundsServiceImpl")
    private RefundsService refundsService;

    @Resource
    private TransCoreService transCoreService;

    @Resource
    private TransService transService;

    @Resource(name = "productPresellServiceImpl")
    private ProductPresellService productPresellService;

    @Override
    public Long saveRefunds(ClientAccessType clientAccessType, ClientVersion clientVersion, RefundsDto refundsDto) throws FroadException
    {
        refundsDto.setSn(SerialNumberUtil.buildRefundSn());//退款编号
        return refundsService.saveRefunds(DtoToBeanSupport.loadByRefunds(refundsDto));
    }

    @Override
    public Boolean updateRefundsById(ClientAccessType clientAccessType, ClientVersion clientVersion, RefundsDto refundsDto) throws FroadException
    {
        return refundsService.updateRefundsById(DtoToBeanSupport.loadByRefunds(refundsDto));
    }

    @Override
    public RefundsDto findRefundsById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id) throws FroadException
    {
        return BeanToDtoSupport.loadByRefundsDto(refundsService.findRefundsById(id));
    }

    @Override
    public PageDto<RefundsDto> findRefundsByPage(ClientAccessType management, ClientVersion version, PageDto<RefundsDto> pageDto) throws FroadException
    {
        Page page = loadBy(pageDto);
        PageDto returnPageDto = loadBy(refundsService.findRefundsByPage(page));
        return returnPageDto;
    }

    public Result doRefund(ClientAccessType clientAccessType, ClientVersion clientVersion, RefundsDto refundsDto)
    {
        Result result = new Result();
        Refunds refunds = DtoToBeanSupport.loadByRefunds(refundsDto);
        if (RefundsDto.State.audit_pass.equals(refundsDto.getState()))
        {
            //如果是审核通过，执行退款
            Trans trans = transService.findTransById(refundsDto.getTransId());
            //检查预售成团状态
            if (com.froad.fft.persistent.common.enums.TransType.presell.equals(trans.getType()))
            {
                List<TransDetails> detailList = trans.getDetailsList();
                //目前只考虑涉及一条商品的交易，如果多条记录，则后期处理 todo
                TransDetails detail = detailList.get(0);
                ProductPresell presell = productPresellService.selectByProductId(detail.getProductId());
                if (com.froad.fft.persistent.common.enums.ClusterState.success.equals(presell.getClusterState()))
                {
                    //如果已成团，则不通过
                    result = new Result();
                    refunds.setState(Refunds.State.audit_no_pass);
                    refunds.setRemark("商品已成团，不允许退款。审核不通过！");
                    boolean flag = refundsService.updateRefundsById(refunds);
                    if (flag)
                    {
                        result.setMsg("商品已成团，不允许退款。审核不通过！");
                        result.setCode("9999");
                    }
                    else
                    {
                        result.setMsg("操作失败！");
                        result.setCode("9998");
                    }
                    return result;
                }
            }

            String sn = trans.getSn();
            String reason = refundsDto.getReason();
            result = transCoreService.doRefund(sn, reason);
            if (Result.FAIL.equals(result.getCode()))
            {
                //退款失败，直接返回,不再做任何操作
                return result;
            }
            //把预售的实际购买数量减去用户购买数量
            if (com.froad.fft.persistent.common.enums.TransType.presell.equals(trans.getType()))
            {
                List<TransDetails> detailList = trans.getDetailsList();
                for (TransDetails detail : detailList)
                {
                    ProductPresell presell = productPresellService.selectByProductId(detail.getProductId());
                    int trueBuyNum = presell.getTrueBuyerNumber();
                    trueBuyNum -= detail.getQuantity();
                    presell.setTrueBuyerNumber(trueBuyNum);
                    productPresellService.updateProductPresellById(presell);
                }
            }

        }
        boolean flag = refundsService.updateRefundsById(refunds);
        if (flag)
        {
            result.setCode(Result.SUCCESS);
            result.setMsg("审核退款操作成功");
        }
        else
        {
            result.setCode(Result.FAIL);
            result.setMsg("审核退款操作失败");
        }
        return result;
    }

    private Page loadBy(PageDto pageDto)
    {

        Page page = new Page();
        page.setPageNumber(pageDto.getPageNumber());
        page.setPageSize(pageDto.getPageSize());

        //排序
        if (pageDto.getOrderDto() != null)
        {
            Order order = new Order();
            order.setProperty(pageDto.getOrderDto().getProperty());
            order.setDirection(pageDto.getOrderDto().getDirection() != null ? com.froad.fft.persistent.bean.page.Order.Direction.valueOf(pageDto.getOrderDto().getDirection().toString()) : null);
            page.setOrder(order);
        }

        //过滤条件
        if (pageDto.getPageFilterDto() != null)
        {
            PageFilter pageFilter = new PageFilter();
            if (pageDto.getPageFilterDto().getFilterEntity() != null)
            {
                Refunds refunds = DtoToBeanSupport.loadByRefunds((RefundsDto) pageDto.getPageFilterDto().getFilterEntity());
                pageFilter.setFilterEntity(refunds);
            }
            pageFilter.setProperty(pageDto.getPageFilterDto().getProperty());


            SimpleDateFormat sdf_1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


            if (pageDto.getPageFilterDto().getStartTime() != null)
            {
                String start = sdf_1.format(pageDto.getPageFilterDto().getStartTime()) + " 00:00:00";
                try
                {
                    pageFilter.setStartTime(sdf_2.parse(start));
                }
                catch (ParseException e)
                {

                }
            }

            if (pageDto.getPageFilterDto().getEndTime() != null)
            {
                String end = sdf_1.format(pageDto.getPageFilterDto().getEndTime()) + " 23:59:59";
                try
                {
                    pageFilter.setEndTime(sdf_2.parse(end));
                }
                catch (ParseException e)
                {

                }
            }
            page.setPageFilter(pageFilter);
        }
        return page;
    }

    private PageDto loadBy(Page page)
    {

        PageDto pageDto = new PageDto();

        pageDto.setPageNumber(page.getPageNumber());
        pageDto.setPageSize(page.getPageSize());
        pageDto.setTotalCount(page.getTotalCount());
        pageDto.setPageCount(page.getPageCount());

        if (page.getOrder() != null)
        {
            OrderDto orderDto = new OrderDto();
            orderDto.setProperty(page.getOrder().getProperty());
            orderDto.setDirection(page.getOrder().getDirection() != null ? com.froad.fft.bean.page.OrderDto.Direction.valueOf(page.getOrder().getDirection().toString()) : null);
            pageDto.setOrderDto(orderDto);
        }

        if (page.getPageFilter() != null)
        {
            PageFilterDto pageFilterDto = new PageFilterDto();
            if (page.getPageFilter().getFilterEntity() != null)
            {
                RefundsDto refundsDto = BeanToDtoSupport.loadByRefundsDto((Refunds) page.getPageFilter().getFilterEntity());
                pageFilterDto.setFilterEntity(refundsDto);
            }
            pageFilterDto.setProperty(page.getPageFilter().getProperty());
            pageFilterDto.setStartTime(page.getPageFilter().getStartTime());
            pageFilterDto.setEndTime(page.getPageFilter().getEndTime());
            pageDto.setPageFilterDto(pageFilterDto);
        }

        List<Refunds> list = page.getResultsContent();
        List<RefundsDto> dlist = new ArrayList();
        for (Refunds refunds : list)
        {
            dlist.add(BeanToDtoSupport.loadByRefundsDto(refunds));
        }
        pageDto.setResultsContent(dlist);

        return pageDto;
    }


}
