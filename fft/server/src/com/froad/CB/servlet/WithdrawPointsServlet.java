package com.froad.CB.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.froad.CB.common.Command;
import com.froad.CB.common.SpringContextUtil;
import com.froad.CB.common.constant.PayCommand;
import com.froad.CB.common.constant.TranCommand;
import com.froad.CB.dao.impl.TransDaoImpl;
import com.froad.CB.po.Trans;
import com.froad.CB.po.bill.WithdrawNotice;
import com.froad.util.RsaUtil;


	/**
	 * 类描述：接收积分提现通知的servlet
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2014 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Mar 3, 2014 2:19:16 PM 
	 */
public class WithdrawPointsServlet extends HttpServlet{

	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;

	private static final Logger logger=Logger.getLogger(HFCZServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info("==========收到积分提现结果通知============");
		request.setCharacterEncoding("UTF-8");
		ServletInputStream inputStream = request.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputStream, "UTF-8"));
		String line = null;
		StringBuffer buffer = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		reader.close();
		String noticeXML = buffer.toString();
		logger.info("积分提现的通知报文:" + noticeXML);
		try {
			Document doc = DocumentHelper.parseText(noticeXML);
			Element root = doc.getRootElement();
			Element element = null;
			String name = null;
			Iterator<?> iter = root.elementIterator();
			WithdrawNotice notice=new WithdrawNotice();
			while(iter.hasNext()){
				element=(Element)iter.next();
				name=element.getName();
				if (name.equals("cashPointsInfo")){// 订单类元素
					Iterator<?> childIter = element.elementIterator();
					Element child=null;
					String childName=null;
					while(childIter.hasNext()) {
						child=(Element)childIter.next();
						childName=child.getName();
						if (childName.equals("cashPointsNo")) {
							notice.setCashPointsNo(child.getTextTrim());
						}else if (childName.equals("objectNo")) {
							notice.setObjectNo(child.getTextTrim());
						}else if (childName.equals("transferId")) {
							notice.setTransferId(child.getTextTrim());
						}else if(childName.equals("points")){
							notice.setPoints(child.getTextTrim());
						}else if (childName.equals("stateCode")) {
							notice.setStateCode(child.getTextTrim());
						}else if(childName.equals("remark")){
							notice.setRemark(child.getTextTrim());
						}
					}

				}else if (name.equals("system")) {// 系统类元素
					Iterator<?> childIter = element.elementIterator();
					Element child=null;
					String childName=null;
					while(childIter.hasNext()) {
						child=(Element)childIter.next();
						childName=child.getName();
						if (childName.equals("resultCode")) {
							notice.setResultCode(child.getTextTrim());
						}else if(childName.equals("receiveReqTime")){
							notice.setReceiveReqTime(child.getTextTrim());
						}else if (childName.equals("partnerNo")) {
							notice.setPartnerNo(child.getTextTrim());
						}else if (childName.equals("charset")) {
							notice.setCharset(child.getTextTrim());
						}else if (childName.equals("signType")) {
							notice.setSignType(child.getTextTrim());
						}else if (childName.equals("signMsg")) {
							notice.setSignMsg(child.getTextTrim());
						}
					}

				}

			}
			Map<String,String> signMap = new TreeMap<String,String>();
			signMap.put("partnerNo", notice.getPartnerNo());
			signMap.put("stateCode", notice.getStateCode());
			signMap.put("objectNo", notice.getObjectNo());
			signMap.put("points", notice.getPoints());
			
			PublicKey pk = RsaUtil.initPublicKey(PayCommand.PUBLIC_KEY_NOTICE);
			boolean flag = RsaUtil.verifyPublicKey(signMap.toString(),
					notice.getSignMsg(), pk);

			logger.info("校验结果:" +flag);
			logger.info("收到积分提现通知,响应码:" + notice.getResultCode() + "积分提现编号:"
					+ notice.getCashPointsNo()+" objectNo:"+notice.getObjectNo()+"stateCode:"+notice.getStateCode());
			if (flag){//校验报文通过
				if(Command.RESP_CODE_SUCCESS.equals(notice.getResultCode())){
					TransDaoImpl transDaoImpl=(TransDaoImpl)SpringContextUtil.getBean("transDaoImpl");
					String transId=notice.getObjectNo().substring(3);
					Trans trans=transDaoImpl.getTransById(Integer.parseInt(transId));
					if(trans!=null){
						if(TranCommand.TRAN_PROCESSING.equals(trans.getState())){
							String state="000".equals(notice.getStateCode())?TranCommand.TRAN_SUCCESS:TranCommand.TRAN_FAIL;
							transDaoImpl.updateStateById(trans.getId(), state);
						}else{
							logger.error("==========重复的积分提现通知=========");
						}
					}else{
						logger.error("该笔积分提现交易不存在，交易号："+transId);
					}
				}
			} else {//报文校验失败
				logger.info("积分提现通知校验失败,提现编号 " + notice.getCashPointsNo()+",订单号："+notice.getObjectNo());
			}
		} catch (DocumentException e) {
			logger.error("异常", e);
		} catch (InvalidKeyException e) {
			logger.error("异常", e);
		} catch (NoSuchAlgorithmException e) {
			logger.error("异常", e);
		} catch (InvalidKeySpecException e) {
			logger.error("异常", e);
		} catch (SignatureException e) {
			logger.error("异常", e);
		} catch(Exception e){
			logger.error("异常", e);
		}finally{
			response.getWriter().write("success");
		}
	}

}
