package com.froad.CB.thirdparty;

import com.froad.CB.AppException.AppException;
import com.froad.CB.common.SysCommand;
import com.froad.CB.common.constant.PayCommand;
import com.froad.CB.common.constant.TranCommand;
import com.froad.CB.po.bill.CombineResponse;
import com.froad.util.RsaUtil;
import com.froad.util.String2Xml;
import junit.framework.TestCase;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Administrator on 14-3-14.
 */
public class CombineNotice_Test extends TestCase {

    public void test00000000() {
        String2Xml bodyXml = new String2Xml("noticeFroadApi");
        Map<String, String> encMap = new TreeMap<String, String>();

        bodyXml.addElementName("order");

        bodyXml.append("orderID", "12345");
        bodyXml.append("orderAmount", "12222");
        bodyXml.append("orderCurrency", "4333");
        bodyXml.append("orderRemark", "aaaa");
        bodyXml.append("stateCode", "002");
        bodyXml.append("orderAcquiringTime", "20101218020101");
        bodyXml.append("orderCompleteTime", "20101218020101");

        encMap.put("orderID", "12345");
        encMap.put("orderAmount", "12222");
        encMap.put("stateCode", "002");
        encMap.put("orderAcquiringTime", "20101218020101");
        encMap.put("orderCompleteTime", "20101218020101");


        bodyXml.addElementName("pay");

        bodyXml.append("payType", "20");
        bodyXml.append("payOrg", "30");
        bodyXml.append("payOrgNo", "40");
        bodyXml.append("payAmount", "666");
        bodyXml.append("froadBillNo", "fjiwjfoie");

        //处理系统参数数据（除加签数据）-----------------------
        bodyXml.addElementName("system");

        bodyXml.append("resultCode", "1234567789");
        bodyXml.append("partnerID", TranCommand.PARTNER_ID);
        bodyXml.append("charset", PayCommand.CHARSET_UTF8);
        bodyXml.append("signType", PayCommand.SIGNTYPE_RSA);

        encMap.put("resultCode", "1234567789");
        encMap.put("partnerID", TranCommand.PARTNER_ID);


        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "http://172.18.30.79:8085/CombineOrderNoticeServlet";

        HttpPost post = new HttpPost(url);

        String requestXML = null;

        try {
            PrivateKey prikey = RsaUtil.initPrivateKey(TranCommand.RSA_PRIVATE_KEY, PayCommand.RSAPWD);

            //加签列表空指针转化为空字符串；
            for (Iterator iterator = encMap.entrySet().iterator(); iterator.hasNext(); ) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                if (org.apache.commons.lang.StringUtils.isBlank(value) || null == value) {
                    encMap.put(key, "");
                }
            }
            //加签数据
            String signMsg = RsaUtil.signPrivateKey(encMap.toString(), prikey);
            bodyXml.append("signMsg", signMsg);

            requestXML = bodyXml.toXMLString().replaceAll(">null<", "><");

            //报文发送
            post.setEntity(new StringEntity(requestXML, "UTF-8"));
            HttpResponse response = httpClient.execute(post);

            //报文接收
            if (response.getStatusLine().getStatusCode() == org.apache.commons.httpclient.HttpStatus.SC_OK) {
                System.out.println(response.getStatusLine().getStatusCode());
            } else {
                //发送支付请求失败
                throw new AppException("openapi响应失败，响应码:" + response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {//释放连接
            httpClient.getConnectionManager().shutdown();
        }

    }
}
