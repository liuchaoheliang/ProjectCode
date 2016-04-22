
package com.froad.client.lottery;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.3.3
 * 2014-04-15T16:39:11.612+08:00
 * Generated source version: 2.3.3
 * 
 */
public final class LotteryService_LotteryServiceImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.service.CB.froad.com/", "LotteryServiceImplService");

    private LotteryService_LotteryServiceImplPort_Client() {
    }

    public static void main(String args[]) throws Exception {
        URL wsdlURL = LotteryServiceImplService.WSDL_LOCATION;
        if (args.length > 0) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        LotteryServiceImplService ss = new LotteryServiceImplService(wsdlURL, SERVICE_NAME);
        LotteryService port = ss.getLotteryServiceImplPort();  
        
        {
        System.out.println("Invoking queryRewardByTranID...");
        com.froad.client.lottery.Lottery _queryRewardByTranID_arg0 = null;
        try {
            com.froad.client.lottery.Lottery _queryRewardByTranID__return = port.queryRewardByTranID(_queryRewardByTranID_arg0);
            System.out.println("queryRewardByTranID.result=" + _queryRewardByTranID__return);

        } catch (AppException_Exception e) { 
            System.out.println("Expected exception: AppException has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking queryPeridListNow...");
        com.froad.client.lottery.Lottery _queryPeridListNow_arg0 = null;
        try {
            java.util.List<com.froad.client.lottery.Lottery> _queryPeridListNow__return = port.queryPeridListNow(_queryPeridListNow_arg0);
            System.out.println("queryPeridListNow.result=" + _queryPeridListNow__return);

        } catch (AppException_Exception e) { 
            System.out.println("Expected exception: AppException has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking calOrder...");
        com.froad.client.lottery.Lottery _calOrder_arg0 = null;
        try {
            com.froad.client.lottery.Lottery _calOrder__return = port.calOrder(_calOrder_arg0);
            System.out.println("calOrder.result=" + _calOrder__return);

        } catch (AppException_Exception e) { 
            System.out.println("Expected exception: AppException has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking createOrder...");
        com.froad.client.lottery.Lottery _createOrder_arg0 = null;
        try {
            com.froad.client.lottery.Lottery _createOrder__return = port.createOrder(_createOrder_arg0);
            System.out.println("createOrder.result=" + _createOrder__return);

        } catch (AppException_Exception e) { 
            System.out.println("Expected exception: AppException has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking queryLastPeridReward...");
        com.froad.client.lottery.Lottery _queryLastPeridReward_arg0 = null;
        try {
            java.util.List<com.froad.client.lottery.Lottery> _queryLastPeridReward__return = port.queryLastPeridReward(_queryLastPeridReward_arg0);
            System.out.println("queryLastPeridReward.result=" + _queryLastPeridReward__return);

        } catch (AppException_Exception e) { 
            System.out.println("Expected exception: AppException has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking queryZCinfos...");
        com.froad.client.lottery.Lottery _queryZCinfos_arg0 = null;
        try {
            java.util.List<com.froad.client.lottery.Lottery> _queryZCinfos__return = port.queryZCinfos(_queryZCinfos_arg0);
            System.out.println("queryZCinfos.result=" + _queryZCinfos__return);

        } catch (AppException_Exception e) { 
            System.out.println("Expected exception: AppException has occurred.");
            System.out.println(e.toString());
        }
            }

        System.exit(0);
    }

}