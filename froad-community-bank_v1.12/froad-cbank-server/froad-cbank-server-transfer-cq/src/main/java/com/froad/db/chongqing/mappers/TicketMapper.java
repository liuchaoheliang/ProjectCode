package com.froad.db.chongqing.mappers;

import java.util.List;

import com.froad.cbank.persistent.bean.TicketListCondition;
import com.froad.cbank.persistent.common.enums.TicketState;
import com.froad.cbank.persistent.entity.Ticket;
import com.froad.cbank.persistent.entity.base.PageEntity;


public interface TicketMapper {
    /**
     * 数据插入
    * <p>Function: insert</p>
    * <p>Description: </p>
    * @version 1.0
    * @param ticket
    * @return
     */
    public Long insert(Ticket ticket);

    /**
     * 基础分页查询
    * <p>Function: selectOfPage</p>
    * <p>Description: </p>
    * @version 1.0
    * @param page
    * @return
     */
    public List<Ticket> selectOfPage(PageEntity<Ticket> pageEntity);

    /**
     * 通过数据主键获取数据
    * <p>Function: selectById</p>
    * <p>Description: </p>
    * @version 1.0
    * @param id
    * @return
     */
    public Ticket selectById(Long id);
    /**
     * 通过订单编号获取数据
     * <p>Function: selectById</p>
     * <p>Description: </p>
     * @version 1.0
     * @param id
     * @return
     */
    public Ticket selectBySN(String sn);

    /**
     * 通过传入的JavaBean非空属性组合查询条件
    * <p>Function: selectByCondition</p>
    * <p>Description: </p>
    * @version 1.0
    * @param ticket
    * @return
     */
    public List<Ticket> selectByCondition(Ticket ticket);

    /**
     * 通过传入的JavaBean id 属性确定数据，将该JavaBean的其他非空属性执行更新命令
    * <p>Function: updateById</p>
    * <p>Description: </p>
    * @version 1.0
    * @param ticket
    * @return
     */
    public Boolean updateById(Ticket ticket);

    /**
     * 方法描述：查询订单相关虚拟劵是否有消费或者不可用的
     * @version: 1.0
     * @author: 蔡世璨 caishican@froad.com.cn
     * @time: 2014年12月29日 下午6:53:00
     * @param id
     * @return Boolean
     */
    public Boolean hashTicketConsumedOrDisabled(Long id);

    /**
     * 方法描述：根据订单ID禁用虚拟劵
     * @version: 1.0
     * @author: 蔡世璨 caishican@froad.com.cn
     * @time: 2014年12月29日 下午7:34:06
     * @param id
     * @return Object
     */
    public void disableTicketByOrderID(Long id);

    /**
     * 方法描述：校验劵号(团购)
     * @version: 1.0
     * @author: 蔡世璨 caishican@froad.com.cn
     * @time: 2015年1月7日 上午10:08:14
     * @param ticket
     * @return Integer
     */
    public Integer doValidateSecuritiesNoForGroup(Ticket ticket);

    /**
     * 方法描述：校验券号(预售)
     * @version: 1.0
     * @author: 蔡世璨 caishican@froad.com.cn
     * @time: 2015年1月10日 下午3:14:28
     * @param orgCode
     * @param ticket
     * @return int
     */
    public Integer doValidateSecuritiesNoForPresell(Ticket ticket);
    
    /**
     * 方法描述：通过券号查询券信息
     * @version: 1.0
     * @author: 蔡世璨 caishican@froad.com.cn
     * @time: 2015年1月7日 下午1:59:52
     * @param securitiesNo
     * @return Ticket
     */
    public Ticket selectBySecuritiesNO(String securitiesNo);
    /**
     * 方法描述：通过券号查询券信息-团购
     * @version: 1.0
     * @author: 蔡世璨 caishican@froad.com.cn
     * @time: 2015年1月7日 下午1:59:52
     * @param securitiesNo
     * @return Ticket
     */
    public Ticket selectBySecuritiesNoForGroup(Ticket ticket);
    
    /**
     * 方法描述：通过券号查询券信息-预售
     * @version: 1.0
     * @author: 蔡世璨 caishican@froad.com.cn
     * @time: 2015年1月7日 下午1:59:52
     * @param securitiesNo
     * @return Ticket
     */
    public Ticket selectBySecuritiesNoForPresell(Ticket ticket);

    /**
     * 方法描述：通过订单ID查询所有可用的券
     * @version: 1.0
     * @author: 蔡世璨 caishican@froad.com.cn
     * @time: 2015年1月8日 上午10:34:22
     * @param orderID
     * @return List<Ticket>
     */
    public List<Ticket> selectCanBeConsumedTicketByOrderID(Long orderID);

    /**
     * 方法描述：券分页查询-会员
     * @version: 1.0
     * @author: 蔡世璨 caishican@froad.com.cn
     * @time: 2015年1月11日 下午3:57:49
     * @param conditioin
     * @param pageEntity
     * @return List<Ticket>
     */
    public List<Ticket> selectOfPageForMember(TicketListCondition conditioin, PageEntity<Ticket> pageEntity);
   
    /**
     * 通过订单ID查询所有的券
      * @Title: selectByOrderID
      * @Description: TODO
      * @author: wuhelian 2015年1月19日
      * @modify: wuhelian 2015年1月19日
      * @param @param orderID
      * @param @return    
      * @return List<Ticket>    
      * @throws
     */
    public List<Ticket> selectByOrderID(Long orderID);
    
    /**
     * 方法描述：通过状态和订单号更新ticket
     * @version: 1.0
     * @author: 蔡世璨 caishican@froad.com.cn
     * @time: 2015年1月21日 上午10:38:31
     * @param ticket
     * @param oldState 
     * @return Integer
     */
    public Integer updateByStateAndOrderId(Ticket ticket, TicketState oldState);

    /**
     * 方法描述：检查是否能在当前门店消费
     * @version: 1.0
     * @author: 蔡世璨 caishican@froad.com.cn
     * @time: 2015年1月21日 下午3:57:32
     * @param securitiesNo
     * @param outletId
     * @return Boolean
     */
    public Boolean isSNCanConsumeForGroup(String securitiesNo, Long outletId);

    /**
     * 方法描述：检查是否能在当前门店消费
     * @version: 1.0
     * @author: 蔡世璨 caishican@froad.com.cn
     * @time: 2015年1月21日 下午3:57:35
     * @param securitiesNo
     * @param outletId
     * @return Boolean
     */
    public Boolean isSNCanConsumeForPresell(String securitiesNo, Long outletId);
    
    /**
     *  查询消费的团购卷信息
      * @Title: selectTicketByGroup
      * @Description: TODO
      * @author: share 2015年7月24日
      * @modify: share 2015年7月24日
      * @param @return    
      * @return List<Ticket>    
      * @throws
     */
    public List<Ticket> selectTicketByGroup();
}
