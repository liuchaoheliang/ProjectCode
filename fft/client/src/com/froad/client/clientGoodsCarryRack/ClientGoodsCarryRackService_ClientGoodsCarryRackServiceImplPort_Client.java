
package com.froad.client.clientGoodsCarryRack;

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
 * 2014-04-15T16:37:43.671+08:00
 * Generated source version: 2.3.3
 * 
 */
public final class ClientGoodsCarryRackService_ClientGoodsCarryRackServiceImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.service.CB.froad.com/", "ClientGoodsCarryRackServiceImplService");

    private ClientGoodsCarryRackService_ClientGoodsCarryRackServiceImplPort_Client() {
    }

    public static void main(String args[]) throws Exception {
        URL wsdlURL = ClientGoodsCarryRackServiceImplService.WSDL_LOCATION;
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
      
        ClientGoodsCarryRackServiceImplService ss = new ClientGoodsCarryRackServiceImplService(wsdlURL, SERVICE_NAME);
        ClientGoodsCarryRackService port = ss.getClientGoodsCarryRackServiceImplPort();  
        
        {
        System.out.println("Invoking getClientGoodsCarryRackByGoodsId...");
        java.lang.String _getClientGoodsCarryRackByGoodsId_arg0 = "";
        java.util.List<com.froad.client.clientGoodsCarryRack.ClientGoodsCarryRack> _getClientGoodsCarryRackByGoodsId__return = port.getClientGoodsCarryRackByGoodsId(_getClientGoodsCarryRackByGoodsId_arg0);
        System.out.println("getClientGoodsCarryRackByGoodsId.result=" + _getClientGoodsCarryRackByGoodsId__return);


        }
        {
        System.out.println("Invoking deleteStateById...");
        java.lang.String _deleteStateById_arg0 = "";
        java.lang.Integer _deleteStateById__return = port.deleteStateById(_deleteStateById_arg0);
        System.out.println("deleteStateById.result=" + _deleteStateById__return);


        }
        {
        System.out.println("Invoking selectById...");
        java.lang.Integer _selectById_arg0 = null;
        com.froad.client.clientGoodsCarryRack.ClientGoodsCarryRack _selectById__return = port.selectById(_selectById_arg0);
        System.out.println("selectById.result=" + _selectById__return);


        }
        {
        System.out.println("Invoking getClientGoodsCarryRack...");
        com.froad.client.clientGoodsCarryRack.ClientGoodsCarryRack _getClientGoodsCarryRack_arg0 = null;
        java.util.List<com.froad.client.clientGoodsCarryRack.ClientGoodsCarryRack> _getClientGoodsCarryRack__return = port.getClientGoodsCarryRack(_getClientGoodsCarryRack_arg0);
        System.out.println("getClientGoodsCarryRack.result=" + _getClientGoodsCarryRack__return);


        }
        {
        System.out.println("Invoking updateById...");
        com.froad.client.clientGoodsCarryRack.ClientGoodsCarryRack _updateById_arg0 = null;
        java.lang.Integer _updateById__return = port.updateById(_updateById_arg0);
        System.out.println("updateById.result=" + _updateById__return);


        }
        {
        System.out.println("Invoking getClientGoodsCarryRackListByPager...");
        com.froad.client.clientGoodsCarryRack.ClientGoodsCarryRack _getClientGoodsCarryRackListByPager_arg0 = null;
        com.froad.client.clientGoodsCarryRack.ClientGoodsCarryRack _getClientGoodsCarryRackListByPager__return = port.getClientGoodsCarryRackListByPager(_getClientGoodsCarryRackListByPager_arg0);
        System.out.println("getClientGoodsCarryRackListByPager.result=" + _getClientGoodsCarryRackListByPager__return);


        }
        {
        System.out.println("Invoking addClientGoodsCarryRack...");
        com.froad.client.clientGoodsCarryRack.ClientGoodsCarryRack _addClientGoodsCarryRack_arg0 = null;
        java.lang.Integer _addClientGoodsCarryRack__return = port.addClientGoodsCarryRack(_addClientGoodsCarryRack_arg0);
        System.out.println("addClientGoodsCarryRack.result=" + _addClientGoodsCarryRack__return);


        }
        {
        System.out.println("Invoking deleteById...");
        java.lang.String _deleteById_arg0 = "";
        java.lang.Integer _deleteById__return = port.deleteById(_deleteById_arg0);
        System.out.println("deleteById.result=" + _deleteById__return);


        }

        System.exit(0);
    }

}