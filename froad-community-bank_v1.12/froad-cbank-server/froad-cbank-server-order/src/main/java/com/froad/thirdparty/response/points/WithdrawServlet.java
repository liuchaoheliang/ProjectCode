//package com.froad.thirdparty.response.points;
//
//import java.io.IOException;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import java.security.PublicKey;
//import java.security.SignatureException;
//import java.security.spec.InvalidKeySpecException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.TreeMap;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.log4j.Logger;
//
////import com.froad.cbank.db.service.impl.TransCoreServiceImpl;
//import com.froad.cbank.thirdparty.common.PointsCommand;
//import com.froad.cbank.thirdparty.dto.response.points.CashPointsInfo;
//import com.froad.cbank.thirdparty.dto.response.points.NoticeCashPointsApi;
//import com.froad.cbank.thirdparty.dto.response.points.System;
//import com.froad.cbank.thirdparty.util.HttpReqBodyToStrUtil;
//import com.froad.cbank.util.RsaUtil;
//import com.froad.cbank.util.SpringUtil;
//import com.froad.cbank.util.XmlBeanUtil;
//
//public class WithdrawServlet extends HttpServlet{
//
//	private static final long serialVersionUID = -9201050494382450432L;
//
//	private static Logger logger = Logger.getLogger(WithdrawServlet.class);
//	
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {	
//		logger.info("=========收到积分提现通知=========");
//		String noticeXML = HttpReqBodyToStrUtil.toXMLStr(req);			
//		logger.info("积分提现通知报文:" + noticeXML);
//		NoticeCashPointsApi notice = null;
//		try {
//			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
//			xmlToBeanMap.put("noticeCashPointsApi", NoticeCashPointsApi.class);
//			xmlToBeanMap.put("cashPointsInfo", CashPointsInfo.class);
//			xmlToBeanMap.put("system", System.class);
//			notice = (NoticeCashPointsApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, noticeXML);		
//			boolean flag=this.verify(notice);
//			if (flag){//校验报文通过
////				TransCoreServiceImpl transCoreService=(TransCoreServiceImpl)SpringUtil.getBean("transCoreServiceImpl");
////				transCoreService.doWithdrawNotice(notice);
//			}
//		} catch (Exception e) {
//			logger.error("解析积分提现通知报文时异常", e);
//		}finally{
//			resp.getWriter().write("success");
//		}
//	}
//	
//	private boolean verify(NoticeCashPointsApi notice) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, IOException{
//		Map<String,String> signMap = new TreeMap<String,String>();
//		CashPointsInfo cashPoints=notice.getCashPointsInfo();
//		System system=notice.getSystem();
//		signMap.put("partnerNo", system.getPartnerNo());
//		signMap.put("stateCode", cashPoints.getStateCode());
//		signMap.put("objectNo", cashPoints.getObjectNo());
//		signMap.put("points", cashPoints.getPoints());
//		
//		PublicKey pk = RsaUtil.initPublicKey(PointsCommand.POINTS_PUBLICKEY_WITHDRAW_NOTICE);
//		boolean flag = RsaUtil.verifyPublicKey(signMap.toString(),
//				system.getSignMsg(), pk);
//
//		logger.info("收到积分提现通知，报文校验结果:" +flag+" 响应码:" + 
//				system.getResultCode() + "积分提现编号:" +
//				cashPoints.getCashPointsNo()+" objectNo:"+cashPoints.getObjectNo()+
//						"stateCode:"+cashPoints.getStateCode());
//		return flag;
//	}
//	
//}
