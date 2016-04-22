
package com.froad.test.payment;

import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.froad.thrift.service.BankCardService;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.bankcard.BankCardInfo;

public class BankCardServiceTest {

    public static void main(String[] args) throws Exception {
        // 调用Thrift服务
//        TSocket transport = new TSocket("10.24.248.189", 15501);
        TSocket transport = new TSocket("127.0.0.1", 15501);
        TBinaryProtocol protocol = new TBinaryProtocol(transport);

        TMultiplexedProtocol proxy = new TMultiplexedProtocol(protocol, "BankCardService");
        BankCardService.Client bankCardService = new BankCardService.Client(proxy);
        transport.open();

//        List<BankCardInfo> result = bankCardService.selectSignedBankCardByClientId(1000, 50000216676L);
//        System.out.println(JSONObject.toJSON(result));
//        
//        BankCardInfo bank = result.get(0);
//        
//        
//        
//        ResultVo vo = bankCardService.updateSignBankCardLimitCash(1000, "6228671149642168", "101", "101", "101");
//        System.out.println(JSONObject.toJSON(vo));
//        
//        result = bankCardService.selectSignedBankCardByClientId(1000, 50000216676L);
//        System.out.println(JSONObject.toJSON(result));
//        
//        
        
//        Object result=bankCardService.sendSignBankCardMobileToken(1000, "15221508888", "6228671149642168");
//        System.out.println(result);
        
//        bankCardService.signBankCardByClientId(1000, 50000216676L, bank.getCardNo(), bank.getCardHostName(), bank.getIdentifyNo(), bank.getMobile(), bank.getOrderLimit(), bank.getDayLimit(), bank.getMonthLimit(), )
        // ShoppingCartService.Client service1 = new ShoppingCartService.Client(mp1);
        // transport.open();
        //
        // System.out.println(service1.getCart(100000l, 1000000000l, 1));
        
        System.out.println(JSON.toJSONString(bankCardService.auditStatusQuery("chongqing", "zhangsan", "6222562145874158925"), true));
        
         transport.close();

    }
}
