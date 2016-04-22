package com.froad.action.support;

import java.util.List;

import org.apache.log4j.Logger;
import com.froad.client.authTicket.AuthTicket;
import com.froad.client.authTicket.AuthTicketService;

/**
 * @author Qiaopeng.Lee
 * @version 1.0
 *          认证(团购、兑换) actionSupport
 * @date 2013-3-4
 */
public class AuthTicketActionSupport
{
    private static Logger logger = Logger.getLogger(AuthTicketActionSupport.class);
    private AuthTicketService authTicketService;

    /**
     * 根据商户ID、认证类型type、券号NO查询认证记录信息
     * SecuritiesNo唯一  实际返回一条记录
     *
     * @param authTicket
     * @return List<AuthTicket>
     */
    public List<AuthTicket> getAuthTickList(AuthTicket authTicket)
    {
        List<AuthTicket> list = null;
        try
        {
            list = authTicketService.getAuthTickBySelective(authTicket);
        }
        catch (Exception e)
        {
            logger.error("AuthTicketActionSupport.getAuthTickBySelective查询商户认证信息记录出错! 类型(type):" + authTicket.getType() + " merchantId:" +
                    authTicket.getMerchantId() + " 劵号：" + authTicket.getSecuritiesNo(), e);
        }
        return list;
    }

    /**
     * 团购、兑换券号认证
     * 认证结束返回此条认证信息记录
     *
     * @param authTicket
     * @return AuthTicket
     */
    public boolean updateAuthTicketState(AuthTicket authTicket)
    {
        boolean flag = false;
        try
        {
            authTicketService.updateById(authTicket);
            flag = true;
            logger.info("认证操作结束。");
        }
        catch (Exception e)
        {
            logger.error("AuthTicketActionSupport.updateAuthTicketState.updateStateById更新商户认证信息记录出错! id:" + authTicket.getId() + " state:" +
                    authTicket.getState() + " isConsume：" + authTicket.getIsConsume(), e);
        }

        return flag;
    }

    public List<AuthTicket> getAuthTickByTransId(Integer transId)
    {
        List<AuthTicket> result = null;
        try
        {
            result = authTicketService.getAuthTickByTransId(transId);
            logger.info("读取交易验证码信息记录操作结束。");
        }
        catch (Exception e)
        {
            logger.error("AuthTicketActionSupport.getAuthTickByTransId 读取交易验证码信息记录出错! id:");
        }

        return result;
    }

    public AuthTicketService getAuthTicketService()
    {
        return authTicketService;
    }

    public void setAuthTicketService(AuthTicketService authTicketService)
    {
        this.authTicketService = authTicketService;
    }

}
