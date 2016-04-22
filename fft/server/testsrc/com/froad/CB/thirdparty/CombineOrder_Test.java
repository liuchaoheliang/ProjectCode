package com.froad.CB.thirdparty;

import com.froad.CB.po.bill.*;
import junit.framework.*;

/**
 * 文件名称:CombineOrder_Test.java
 * 文件描述: 订单合并测试
 * 产品标识: 分分通服务
 * 单元描述: server
 * 编写人: gq.hou_Mimosa
 * 编写时间: 14-3-5
 * 历史修改:
 */
public class CombineOrder_Test extends TestCase
{
    public void testCombineOrder()
    {
        CombineRequest reqBean = new CombineRequest();
        String orderId = "20140303" + System.currentTimeMillis();
        String orderId2 = "20140303" + System.currentTimeMillis();
        orderId = orderId.substring( 0, 20 );
        orderId2 = orderId2.substring( 0, 20 );

        OrderDetailInfo detailInfo1 = new OrderDetailInfo();
        OrderDetailInfo detailInfo2 = new OrderDetailInfo();

        detailInfo1.setOrderID( orderId );
        detailInfo1.setOrderAmount( "100" );
        detailInfo1.setOrderSupplier( "10000004021" );
        detailInfo1.setOrderDisplay( "方付通|游戏点卡7" );

        detailInfo2.setOrderID( orderId2 );
        detailInfo2.setOrderAmount( "200" );
        detailInfo2.setOrderSupplier( "90000000010" );
        detailInfo2.setOrderDisplay( "方付通|游戏点卡8" );

        reqBean.getOrderDetailInfoList().add( detailInfo1 );
        reqBean.getOrderDetailInfoList().add( detailInfo2 );


        //订单数据
        reqBean.setOrderType( "5000" );
        reqBean.setTotalAmount( "300" );
        reqBean.setOrderRemark( "合并支付" );
        reqBean.setMobileToken( "112333" );

        //处理支付参数数据
        reqBean.setPayType( "53" );
        reqBean.setPayOrg( "700" );
        reqBean.setPayerMember( "13361889602" );
        reqBean.setPayDirect( "20" );


        //通知地址
//        reqBean.setNoticeUrl( "http://localhost:8085/CombineOrderNoticeServlet" );
//        reqBean.setReturnUrl( "http://localhost:8085/CombineOrderNoticeServlet" );

          reqBean.setNoticeUrl( "" );

          reqBean.setReturnUrl( "http://172.18.30.79:8085/CombineOrderNoticeServlet" );


        //系统参数区域

        reqBean.setClient("100");
        reqBean.setVersion("1.2.4");

        //测试调用
        try
        {
            BillFunc.combineOrder( reqBean );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }


    }

}
