package com.froad.fft.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Resource;

import com.froad.fft.api.service.MerchantExportService;
import com.froad.fft.api.support.BeanToDtoSupport;
import com.froad.fft.api.support.DtoToBeanSupport;
import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.Result;
import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.MerchantAccountDto;
import com.froad.fft.dto.MerchantDto;
import com.froad.fft.dto.RefundsDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.bean.page.Order;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.common.enums.AccountType;
import com.froad.fft.persistent.entity.Merchant;
import com.froad.fft.persistent.entity.MerchantAccount;
import com.froad.fft.persistent.entity.Product;
import com.froad.fft.persistent.entity.Refunds;
import com.froad.fft.service.MerchantAccountService;
import com.froad.fft.service.MerchantService;
import com.froad.fft.service.ProductService;
import com.froad.fft.service.RefundsService;

public class MerchantExportServiceImpl implements MerchantExportService
{
    org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(MerchantExportServiceImpl.class);

    @Resource(name = "merchantServiceImpl")
    private MerchantService merchantService;

    @Resource(name = "merchantAccountServiceImpl")
    private MerchantAccountService merchantAccountService;

    @Resource(name = "productServiceImpl")
    private ProductService productService;

    @Override
    public Long addMerchant(ClientAccessType clientAccessType, ClientVersion clientVersion, MerchantDto merchantDto) throws FroadException
    {
        return merchantService.saveMerchant(DtoToBeanSupport.loadByMerchant(merchantDto));
    }

    @Override
    public Boolean updateMerchantById(ClientAccessType clientAccessType, ClientVersion clientVersion, MerchantDto merchantDto)
    {
        return merchantService.updateMerchantById(DtoToBeanSupport.loadByMerchant(merchantDto));
    }

    @Override
    public MerchantDto findMerchantById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id)
    {

        return BeanToDtoSupport.loadByMerchantDto(merchantService.selectMerchantById(id));
    }

    @Override
    public PageDto<MerchantDto> findMerchantByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto<MerchantDto> pageDto)
    {
        Page page = loadBy(pageDto);
        PageDto returnPageDto = loadBy(merchantService.findMerchantByPage(page));
        return returnPageDto;
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
                Merchant merchant = DtoToBeanSupport.loadByMerchant((MerchantDto) pageDto.getPageFilterDto().getFilterEntity());
                pageFilter.setFilterEntity(merchant);
            }
            pageFilter.setProperty(pageDto.getPageFilterDto().getProperty());


            if (pageDto.getPageFilterDto().getStartTime() != null)
            {
                pageFilter.setStartTime(pageDto.getPageFilterDto().getStartTime());
            }

            if (pageDto.getPageFilterDto().getEndTime() != null)
            {
                pageFilter.setEndTime(pageDto.getPageFilterDto().getEndTime());
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
                MerchantDto merchantDto = BeanToDtoSupport.loadByMerchantDto((Merchant) page.getPageFilter().getFilterEntity());
                pageFilterDto.setFilterEntity(merchantDto);
            }
            pageFilterDto.setProperty(page.getPageFilter().getProperty());
            pageFilterDto.setStartTime(page.getPageFilter().getStartTime());
            pageFilterDto.setEndTime(page.getPageFilter().getEndTime());
            pageDto.setPageFilterDto(pageFilterDto);
        }

        List<Merchant> list = page.getResultsContent();
        List<MerchantDto> dlist = new ArrayList();
        for (Merchant merchant : list)
        {
            dlist.add(BeanToDtoSupport.loadByMerchantDto(merchant));
        }
        pageDto.setResultsContent(dlist);

        return pageDto;
    }

    public List<MerchantDto> findAllOutletMerchant(ClientAccessType clientAccessType, ClientVersion clientVersion, Long clientId)
    {
        Map<String, Object> paraMap = new HashMap<String, Object>();
        if (null != clientId)
        {
            paraMap.put("clientId", clientId);
        }
        List<Merchant> merchants = merchantService.findAllOutletMerchant(paraMap);
        List<MerchantDto> result = new ArrayList<MerchantDto>();
        for (Merchant merchant : merchants)
        {
            result.add(BeanToDtoSupport.loadByMerchantDto(merchant));
        }
        return result;
    }

    public Result auditMerchant(ClientAccessType clientAccessType, ClientVersion clientVersion, Long merchantId, Boolean auditFlag, String userName)
    {
        Result result = new Result();
        String resultMsg;

        logger.info("开始处理商户审核！");
        logger.info("商户Id：" + merchantId);


        Merchant merchant = merchantService.selectMerchantById(merchantId);
        logger.info("商户名称：" + merchant.getName());
        if (auditFlag)
        {
            logger.info("开始审核通过操作！");
            logger.info("商户账户检查！");

            result = accountCheck(merchant, AccountType.deduct);
            if (Result.FAIL.equals(result.getCode()))
            {
                return result;
            }
            result = accountCheck(merchant, AccountType.collect);
            if (Result.FAIL.equals(result.getCode()))
            {
                return result;
            }
            merchant.setIsAudit(Boolean.TRUE);
            merchant.setReviewStaff(userName);
            merchantService.updateMerchantById(merchant);

            logger.info("审核通过操作处理成功！");
            result.setCode(Result.SUCCESS);
            result.setMsg("审核通过操作处理成功！");
        }
        else
        {
            logger.info("开始审核不通过操作！");
            boolean flag = disableAccount(merchant);
            if (!flag)
            {
                logger.info("禁用商户账户操作失败！");
                result.setCode(Result.FAIL);
                result.setMsg("禁用商户账户操作失败！");
                return result;
            }
            flag = takeOffProduct(merchant);
            if (!flag)
            {
                logger.info("商户商品下架操作失败！");
                result.setCode(Result.FAIL);
                result.setMsg("商户商品下架操作失败！");
                return result;
            }
            merchant.setIsAudit(Boolean.FALSE);
            merchant.setReviewStaff(userName);
            merchantService.updateMerchantById(merchant);

            logger.info("审核不通过操作处理成功！");
            result.setCode(Result.SUCCESS);
            result.setMsg("审核不通过操作处理成功！");
        }
        return result;

    }

    private boolean disableAccount(Merchant merchant)
    {
        logger.info("开始禁用ID为【" + merchant.getId() + "】名称为【" + merchant.getName() + "】的账户！");
        boolean result = true;
        MerchantAccount account = new MerchantAccount();
        Page page = new Page();
        account.setMerchantId(merchant.getId());
        account.setIsEnabled(Boolean.TRUE);


        PageFilter pageFilter = new PageFilter();
        pageFilter.setFilterEntity(account);
        page.setPageFilter(pageFilter);
        page = merchantAccountService.findMerchantAccountByPage(page);
        List<MerchantAccount> accountList = page.getResultsContent();
        if (null == accountList || 0 == accountList.size())
        {
            logger.info("ID为【" + merchant.getId() + "】名称为【" + merchant.getName() + "】的商户没有已启用的账户！");
        }

        for (MerchantAccount _account : accountList)
        {
            logger.info("禁用类型为【" + _account.getAcctType().getDescribe() + "】的【" + _account.getAcctName() + "】账户！");
            _account.setIsEnabled(Boolean.FALSE);
            result = merchantAccountService.updateMerchantAccountById(_account);
            if (!result)
            {
                break;
            }
        }
        logger.info("禁用ID为【" + merchant.getId() + "】名称为【" + merchant.getName() + "】的账户完成！");
        return result;
    }

    private boolean takeOffProduct(Merchant merchant)
    {
        logger.info("开始下架ID为【" + merchant.getId() + "】名称为【" + merchant.getName() + "】的所有商品！");
        boolean result = true;
        Product product = new Product();
        Page page = new Page();
        product.setMerchantId(merchant.getId());
        product.setIsMarketable(Boolean.TRUE);


        PageFilter pageFilter = new PageFilter();
        pageFilter.setFilterEntity(product);
        page.setPageFilter(pageFilter);
        page = productService.findProductByPage(page);
        List<Product> productList = page.getResultsContent();
        if (null == productList || 0 == productList.size())
        {
            logger.info("ID为【" + merchant.getId() + "】名称为【" + merchant.getName() + "】的商户没有上架商品！");
        }
        for (Product _product : productList)
        {
            logger.info("下架Id为【" + _product.getId() + "】商品名为【" + _product.getName() + "】的商品！");
            _product.setIsMarketable(Boolean.FALSE);
            result = productService.updateProductById(_product);
            if (!result)
            {
                break;
            }
        }
        logger.info("下架ID为【" + merchant.getId() + "】名称为【" + merchant.getName() + "】的所有商品完成！");
        return result;
    }

    private Result accountCheck(Merchant merchant, AccountType accountType)
    {
        logger.info("开始检查ID为【" + merchant.getId() + "】名称为【" + merchant.getName() + "】的【" + accountType.getDescribe() + "】类型账户！");
        Result result = new Result();
        String resultMsg;
        MerchantAccount account = new MerchantAccount();
        Page page = new Page();
        account.setMerchantId(merchant.getId());
        account.setIsEnabled(Boolean.TRUE);
        account.setAcctType(AccountType.deduct);

        PageFilter pageFilter = new PageFilter();
        pageFilter.setFilterEntity(account);
        page.setPageFilter(pageFilter);
        page = merchantAccountService.findMerchantAccountByPage(page);
        List<MerchantAccount> deductList = page.getResultsContent();

        if (null == deductList || 0 == deductList.size())
        {

            resultMsg = "未查找到该商户的" + AccountType.deduct.getDescribe() + "信息！";
            logger.warn(resultMsg);
            result.setCode(Result.FAIL);
            result.setMsg(resultMsg);
            return result;
        }
        else if (1 < deductList.size())
        {
            resultMsg = "该商户的" + AccountType.deduct.getDescribe() + "信息不唯一！";
            logger.warn(resultMsg);
            result.setCode(Result.FAIL);
            result.setMsg(resultMsg);
            return result;
        }
        logger.info("检查ID为【" + merchant.getId() + "】名称为【" + merchant.getName() + "】的【" + accountType.getDescribe() + "】类型账户完成！");

        result.setCode(Result.SUCCESS);
        return result;
    }

}
