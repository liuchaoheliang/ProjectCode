package com.froad.thirdparty.util;

import java.lang.reflect.InvocationTargetException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.froad.logback.LogCvt;
import com.froad.thirdparty.common.OpenApiCommand;
import com.froad.thirdparty.common.PointsCommand;
import com.froad.thirdparty.common.SystemCommand;
import com.froad.thirdparty.dto.request.openapi.CreateMobileTokenApiReq;
import com.froad.thirdparty.dto.request.openapi.QueryParamApiReq;
import com.froad.thirdparty.dto.request.openapi.SendSmsApiReq;
import com.froad.thirdparty.dto.request.openapi.SignApiReq;
import com.froad.thirdparty.dto.request.openapi.SignCancelApiReq;
import com.froad.thirdparty.dto.request.openapi.SystemBean;
import com.froad.thirdparty.dto.request.points.System;
import com.froad.thirdparty.dto.request.openapi.LimitReq;

import org.apache.commons.beanutils.BeanUtils;

public class SecretUtil {


     /**
     * 响应验签
     * 
     * @param bodys
     * @param responseParAccountPointsApi
     * @param pkey
     * @return
     */
     public static boolean responseVerifyRSA(List<String> bodys,
               Object resObject, String pkey) {
          try {
               PublicKey pk = RsaUtil.initPublicKey(pkey);
               String body = getTreeMap(bodys, resObject);
               Class<?> clazz = resObject.getClass();
               String signMsg;
               try {
                    Object obj = clazz.getMethod("getSystem").invoke(resObject);
                    signMsg = (String) obj.getClass().getMethod("getSignMsg")
                              .invoke(obj);
               } catch (Exception e) {
                    signMsg = (String) clazz.getMethod("getSignMsg").invoke(
                              resObject);
               }
               LogCvt.info("treeMap body：" + body);
               LogCvt.info("signMsg:" + signMsg);
               LogCvt.info("publicKey:" + pkey);
               boolean flag = RsaUtil.verifyPublicKey(body, signMsg, pk);
               return flag;
          } catch (Exception e) {
               e.printStackTrace();
          }
          return false;
     }

     /**
     * 得到加签体
     * 
     * @param list
     * @param requestFroadApi
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
     public static String getTreeMap(List<String> list, Object resObject)
               throws IllegalAccessException, InvocationTargetException,
               NoSuchMethodException {
          Map tree = new TreeMap();
          for (String data : list) {
               String obj = BeanUtils.getProperty(resObject, data);
               int idx = data.lastIndexOf(".");
               data = data.substring(idx + 1, data.length());
               tree.put(data, obj == null ? "" : obj);
          }
          return tree.toString();
     }

     // ==============================================积分平台加签

     /**
     * 查询积分加签
     * 
     * @param accountMarked
     * @param accountMarkedType
     * @param partnerNo
     * @return
     */
     public static String parAccountPoints(String accountMarked,
               String accountMarkedType, String partnerNo, String signType) {
          String signMsg = "";
          try {
               Map map = new TreeMap();
               map.put("accountMarked", accountMarked == null ? "" : accountMarked);
               map.put("accountMarkedType", accountMarkedType == null ? ""
                         : accountMarkedType);
               map.put("partnerNo", partnerNo == null ? "" : partnerNo);

               if (signType.equals(SystemCommand.SIGNTYPE_RSA)) {
                    PrivateKey prikey = RsaUtil.initPrivateKey(
                              PointsCommand.POINTS_RSA_PRIVATE_KEY,
                              PointsCommand.POINTS_RSA_PRIVATE_PWD);
                    signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
               }

          } catch (Exception e) {
        	  LogCvt.error("查询积分加签异常", e);
          }
          return signMsg;
     }

     public static String consumeOrRefundPointsPoints(String objectNo,
               String accountId, String partnerNo,String requestNo, String signType) {
          String signMsg = null;
          try {
               Map map = new TreeMap();
               map.put("objectNo", objectNo == null ? "" : objectNo);
               map.put("accountId", accountId == null ? "" : accountId);
               map.put("partnerNo", partnerNo == null ? "" : partnerNo);
               map.put("requestNo", requestNo == null ? "" : requestNo);
               LogCvt.info(map.toString());
               if (signType.equals(SystemCommand.SIGNTYPE_RSA)) {
                    PrivateKey prikey = RsaUtil.initPrivateKey(
                              PointsCommand.POINTS_RSA_PRIVATE_KEY,
                              PointsCommand.POINTS_RSA_PRIVATE_PWD);
                    signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
               }
               
          } catch (Exception e) {
        	  LogCvt.error("消费/退还积分加签异常", e);
          }
          return signMsg;
     }
     
//     public static String payPointsByMobile(String objectNo,
//             String accountId, String partnerNo, String signType) {
//        String signMsg = null;
//        try {
//             Map map = new TreeMap();
//             map.put("objectNo", objectNo == null ? "" : objectNo);
//             map.put("accountId", accountId == null ? "" : accountId);
//             map.put("partnerNo", partnerNo == null ? "" : partnerNo);
//             if (signType.equals(SystemCommand.SIGNTYPE_RSA)) {
//                  PrivateKey prikey = RsaUtil.initPrivateKey(
//                            PointsCommand.POINTS_RSA_PRIVATE_KEY,
//                            PointsCommand.POINTS_RSA_PRIVATE_PWD);
//                  signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
//             }
//        } catch (Exception e) {
//      	  LogCvt.error("消费/退还积分加签异常", e);
//        }
//        return signMsg;
//   }
     

     public static String donatePoints(String orgNo, String points,
               String accountMarked, String partnerNo,String requestNo, String signType) {
          String signMsg = null;
          try {
               Map map = new TreeMap();
               map.put("orgNo", orgNo == null ? "" : orgNo);
               map.put("points", points == null ? "" : points);
               map.put("accountMarked", accountMarked == null ? "" : accountMarked);
               map.put("partnerNo", partnerNo == null ? "" : partnerNo);
               map.put("requestNo", requestNo == null ? "" : requestNo);
               if (signType.equals(SystemCommand.SIGNTYPE_RSA)) {
                    PrivateKey prikey = RsaUtil.initPrivateKey(
                              PointsCommand.POINTS_RSA_PRIVATE_KEY,
                              PointsCommand.POINTS_RSA_PRIVATE_PWD);
                    signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
               }
          } catch (Exception e) {
        	  LogCvt.error("赠送积分加签异常", e);
          }
          return signMsg;
     }

     public static String fillPoints(String orgNo, String pointsCateNo,
               String orgPoints, String phone, String partnerNo, String requestNo,
               String signType) {
          String signMsg = null;
          try {
               Map map = new TreeMap();
               map.put("orgNo", orgNo == null ? "" : orgNo);
               map.put("pointsCateNo", pointsCateNo == null ? "" : pointsCateNo);
               map.put("orgPoints", orgPoints == null ? "" : orgPoints);
               map.put("partnerNo", partnerNo == null ? "" : partnerNo);
               map.put("phone", phone == null ? "" : phone);
               map.put("requestNo", requestNo == null ? "" : requestNo);
               if (signType.equals(SystemCommand.SIGNTYPE_RSA)) {
                    PrivateKey prikey = RsaUtil.initPrivateKey(
                              PointsCommand.POINTS_RSA_PRIVATE_KEY,
                              PointsCommand.POINTS_RSA_PRIVATE_PWD);
                    signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
               }
          } catch (Exception e) {
        	  LogCvt.error("兑充积分加签异常", e);
          }
          return signMsg;
     }

     public static String applyForPointsToCash(String accountMarked,
               String realName, String bankId, String bankCard, String objectNo,
               String orgNo, String partnerNo, String signType) {
          String signMsg = null;
          try {
               Map map = new TreeMap();
               map.put("accountMarked", accountMarked == null ? "" : accountMarked);
               map.put("realName", realName == null ? "" : realName);
               map.put("bankId", bankId == null ? "" : bankId);
               map.put("bankCard", bankCard == null ? "" : bankCard);
               map.put("objectNo", objectNo == null ? "" : objectNo);
               map.put("orgNo", orgNo == null ? "" : orgNo);
               map.put("partnerNo", partnerNo == null ? "" : partnerNo);
               if (signType.equals(SystemCommand.SIGNTYPE_RSA)) {
                    PrivateKey prikey = RsaUtil.initPrivateKey(
                              PointsCommand.POINTS_RSA_PRIVATE_KEY,
                              PointsCommand.POINTS_RSA_PRIVATE_PWD);
                    signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
               }
          } catch (Exception e) {
        	  LogCvt.error("提现申请加签异常", e);
          }
          return signMsg;
     }

     public static String queryPointsExchageRate(String orgNo, String partnerNo,
               String signType) {
          String signMsg = null;
          try {
               Map map = new TreeMap();
               map.put("orgNo", orgNo == null ? "" : orgNo);
               map.put("partnerNo", partnerNo == null ? "" : partnerNo);
               if (signType.equals(SystemCommand.SIGNTYPE_RSA)) {
                    PrivateKey prikey = RsaUtil.initPrivateKey(
                              PointsCommand.POINTS_RSA_PRIVATE_KEY,
                              PointsCommand.POINTS_RSA_PRIVATE_PWD);
                    signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
               }
          } catch (Exception e) {
        	  LogCvt.error("查询积分比例加签异常", e);
          }
          return signMsg;
     }

     // ================================================OpenApi加签

     public static String refund(String refundOrderID, String orderID,
               String orderAmount, String refundAmount, String refundType,
               String orderSupplier, String noticeUrl, String reqID,
               String partnerID, String signType) {
          String signMsg = null;
          try {
               Map<String, String> map = new TreeMap<String, String>();
               map.put("refundOrderID", refundOrderID == null ? "" : refundOrderID);
               map.put("orderID", orderID == null ? "" : orderID);
               map.put("orderAmount", orderAmount == null ? "" : orderAmount);
               map.put("refundAmount", refundAmount == null ? "" : refundAmount);
               map.put("refundType", refundType == null ? "" : refundType);
               if (orderSupplier != null) {
                    map.put("orderSupplier", orderSupplier);
               }
               map.put("noticeUrl", noticeUrl == null ? "" : noticeUrl);
               map.put("reqID", reqID == null ? "" : reqID);
               map.put("partnerID", partnerID == null ? "" : partnerID);
               map.put("signType", signType == null ? "" : signType);
               PrivateKey prikey = RsaUtil.initPrivateKey(
                         OpenApiCommand.OPENAPI_RSA_PRIVATE_KEY,
                         OpenApiCommand.OPENAPI_RSA_PRIVATE_PWD);
               signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
               LogCvt.info("退款加签字段："+map.toString());
          } catch (Exception e) {
        	  LogCvt.error("退款申请加签异常", e);
          }
          return signMsg;
     }

     public static String accountCheck(String checkType, String checkContent,
               String checkTime, String reqID, String partnerID, String signType) {
          String signMsg = null;
          try {
               Map map = new TreeMap();
               map.put("checkType", checkType == null ? "" : checkType);
               map.put("checkContent", checkContent == null ? "" : checkContent);
               map.put("checkTime", checkTime == null ? "" : checkTime);
               map.put("reqID", reqID == null ? "" : reqID);
               map.put("partnerID", partnerID == null ? "" : partnerID);
               if (signType.equals(SystemCommand.SIGNTYPE_RSA)) {
                    PrivateKey prikey = RsaUtil.initPrivateKey(
                              OpenApiCommand.OPENAPI_RSA_PRIVATE_KEY,
                              OpenApiCommand.OPENAPI_RSA_PRIVATE_PWD);
                    signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
               }
          } catch (Exception e) {
        	  LogCvt.error("校验查询加签异常", e);
          }
          return signMsg;
     }

     public static String transferCurrency(String transferID,
               String transferAmount, String transferSubmitTime, String reqID,
               String partnerID, String signType) {
          String signMsg = null;
          try {
               Map map = new TreeMap();
               map.put("transferID", transferID == null ? "" : transferID);
               map.put("transferAmount", transferAmount == null ? ""
                         : transferAmount);
               map.put("transferSubmitTime", transferSubmitTime == null ? ""
                         : transferSubmitTime);
               map.put("reqID", reqID == null ? "" : reqID);
               map.put("partnerID", partnerID == null ? "" : partnerID);
               if (signType.equals(SystemCommand.SIGNTYPE_RSA)) {
                    PrivateKey prikey = RsaUtil.initPrivateKey(
                              OpenApiCommand.OPENAPI_RSA_PRIVATE_KEY,
                              OpenApiCommand.OPENAPI_RSA_PRIVATE_PWD);
                    signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
               }
          } catch (Exception e) {
        	  LogCvt.error("转账加签异常", e);
          }
          return signMsg;
     }

     public static String agencyCollectOrDeduct(String orderID,
               String orderAmount, String orderSubmitTime, String reqID,
               String partnerID, String signType) {
          String signMsg = null;
          try {
               Map map = new TreeMap();
               map.put("orderID", orderID == null ? "" : orderID);
               map.put("orderAmount", orderAmount == null ? "" : orderAmount);
               map.put("orderSubmitTime", orderSubmitTime == null ? ""
                         : orderSubmitTime);
               map.put("reqID", reqID == null ? "" : reqID);
               map.put("partnerID", partnerID == null ? "" : partnerID);
               if (signType.equals(SystemCommand.SIGNTYPE_RSA)) {
                    PrivateKey prikey = RsaUtil.initPrivateKey(
                              OpenApiCommand.OPENAPI_RSA_PRIVATE_KEY,
                              OpenApiCommand.OPENAPI_RSA_PRIVATE_PWD);
                    signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
               }
          } catch (Exception e) {
        	  LogCvt.error("代收代扣加签异常", e);
          }
          return signMsg;
     }

     public static String combineTransaction(String orderID, String orderAmount,
               String orderSupplier, String orderType, String totalAmount,
               String orderSubmitTime, String mobileToken, String fftSignNo,
               String payOrg, String payerMember, String payeeMember,
               String payDirect, String noticeUrl, String reqID, String partnerID,
               String charset, String signType) {
          String signMsg = null;
          try {
               Map map = new TreeMap();
               map.put("orderID", orderID == null ? "" : orderID);
               map.put("orderAmount", orderAmount == null ? "" : orderAmount);
               map.put("orderSupplier", orderSupplier == null ? "" : orderSupplier);
               map.put("orderType", orderType == null ? "" : orderType);
               map.put("totalAmount", totalAmount == null ? "" : totalAmount);
               map.put("orderSubmitTime", orderSubmitTime == null ? "" : orderSubmitTime);
               map.put("mobileToken", mobileToken == null ? "" : mobileToken);
               map.put("fftSignNo", fftSignNo == null ? "" : fftSignNo);
               map.put("payOrg", payOrg == null ? "" : payOrg);
               map.put("payerMember", payerMember == null ? "" : payerMember);
               map.put("payeeMember", payeeMember == null ? "" : payeeMember);
               map.put("payDirect", payDirect == null ? "" : payDirect);
               map.put("noticeUrl", noticeUrl == null ? "" : noticeUrl);
               map.put("reqID", reqID == null ? "" : reqID);
               map.put("partnerID", partnerID == null ? "" : partnerID);
               map.put("charset", charset == null ? "" : charset);
               map.put("signType", signType == null ? "" : signType);
               if (signType.equals(SystemCommand.SIGNTYPE_RSA)) {
                    PrivateKey prikey = RsaUtil.initPrivateKey(
                              OpenApiCommand.OPENAPI_RSA_PRIVATE_KEY,
                              OpenApiCommand.OPENAPI_RSA_PRIVATE_PWD);
                    signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
               }
          } catch (Exception e) {
        	  LogCvt.error("合并支付加签异常", e);
          }
          return signMsg;
     }
     
     
     public static String setWhiteList(String payOrg, String merchantID,
             String merchantName, String accountName, String accountNo,
             String optionType, String requestTime, String reqID,
             String partnerID,String charset, String signType) {
        String signMsg = null;
        try {
             Map map = new TreeMap();
             map.put("payOrg", payOrg == null ? "" : payOrg);
             map.put("merchantID", merchantID == null ? "" : merchantID);
             map.put("merchantName", merchantName == null ? "" : merchantName);
             map.put("accountName", accountName == null ? "" : accountName);
             map.put("accountNo", accountNo == null ? "" : accountNo);
             map.put("optionType", optionType == null ? "" : optionType);
             map.put("requestTime", requestTime == null ? "" : requestTime);
             map.put("reqID", reqID == null ? "" : reqID);
             map.put("partnerID", partnerID == null ? "" : partnerID);
             if (signType.equals(SystemCommand.SIGNTYPE_RSA)) {
                  PrivateKey prikey = RsaUtil.initPrivateKey(
                            OpenApiCommand.OPENAPI_RSA_PRIVATE_KEY,
                            OpenApiCommand.OPENAPI_RSA_PRIVATE_PWD);
                  signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
             }
        } catch (Exception e) {
      	  LogCvt.error("设置白名单加签异常", e);
        }
        return signMsg;
   }

     public static String employeeCodeVerify(String verifyOrg, String employeeCode,
             String password, String verifyTime, String reqID,
             String partnerID,String signType) {
        String signMsg = null;
        try {
             Map map = new TreeMap();
             map.put("verifyOrg", verifyOrg == null ? "" : verifyOrg);
             map.put("employeeCode", employeeCode == null ? "" : employeeCode);
             map.put("password", password == null ? "" : password);
             map.put("verifyTime", verifyTime == null ? "" : verifyTime);
             map.put("reqID", reqID == null ? "" : reqID);
             map.put("partnerID", partnerID == null ? "" : partnerID);
             if (signType.equals(SystemCommand.SIGNTYPE_RSA)) {
                  PrivateKey prikey = RsaUtil.initPrivateKey(
                            OpenApiCommand.OPENAPI_RSA_PRIVATE_KEY,
                            OpenApiCommand.OPENAPI_RSA_PRIVATE_PWD);
                  signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
             }
        } catch (Exception e) {
      	  LogCvt.error("登录验密加签异常", e);
        }
        return signMsg;
   }

	public static String auditStatusQuery(String bankGroup, String accountName, String accountNo, String queryTime, String reqID, String partnerID, String charset, String signType) {
		String signMsg = null;
		try {
			Map map = new TreeMap();
			map.put("bankGroup", bankGroup);
			map.put("accountName", accountName);
			map.put("accountNo", accountNo);
			map.put("queryTime", queryTime);
			map.put("reqID", reqID);
			map.put("partnerID", partnerID);
			map.put("charset", charset);
			map.put("signType", signType);
			if (signType.equals(SystemCommand.SIGNTYPE_RSA)) {
				PrivateKey prikey = RsaUtil.initPrivateKey(OpenApiCommand.OPENAPI_RSA_PRIVATE_KEY, OpenApiCommand.OPENAPI_RSA_PRIVATE_PWD);
				signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
			}
		} catch (Exception e) {
			LogCvt.error("审核状态查询加签异常", e);
		}
		return signMsg;
	}
	
	public static String bankCardAccountCheck(String bankGroup, String accountName, String accountNo, String queryTime, String reqID, String partnerID, String charset, String signType) {
		String signMsg = null;
		try {
			Map map = new TreeMap();
			map.put("checkOrg", bankGroup);
			map.put("accountName", accountName);
			map.put("accountNo", accountNo);
			map.put("checkTime", queryTime);
			map.put("reqID", reqID);
			map.put("partnerID", partnerID);
			map.put("charset", charset);
			map.put("signType", signType);
			if (signType.equals(SystemCommand.SIGNTYPE_RSA)) {
				PrivateKey prikey = RsaUtil.initPrivateKey(OpenApiCommand.OPENAPI_RSA_PRIVATE_KEY, OpenApiCommand.OPENAPI_RSA_PRIVATE_PWD);
				signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
			}
		} catch (Exception e) {
			LogCvt.error("审核状态查询加签异常", e);
		}
		return signMsg;
	}
     // 快捷支付加签

     /**
     * 快捷支付认证加签
     * 
     * @param openApiReq
     *            客户端请求参数
     * @param systemBean
     *            系统参数
     * @return 加签结果
     */
     public static String fastCardSignEnc(SignApiReq openApiReq,SystemBean systemBean) {
          String signMsg = null;
          try {
               Map map = new TreeMap();
               map.put("memberID", null == openApiReq.getMemberId() ? ""
                         : openApiReq.getMemberId());
               map.put("accountName", null == openApiReq.getAccountName() ? ""
                         : openApiReq.getAccountName());
               map.put("bankCardNo", null == openApiReq.getBankCardNo() ? ""
                         : openApiReq.getBankCardNo());
               map.put("bankCardType", null == openApiReq.getBankCardType() ? ""
                         : openApiReq.getBankCardType().getCode());
               map.put("certificateType",
                         null == openApiReq.getCertificateType() ? "" : openApiReq
                                   .getCertificateType().getCode());
               map.put("certificateNo", null == openApiReq.getCertificateNo() ? ""
                         : openApiReq.getCertificateNo());
               map.put("mobilePhone", null == openApiReq.getMobilePhone() ? ""
                         : openApiReq.getMobilePhone());
               map.put("mobileToken", null == openApiReq.getMobileToken() ? ""
                         : openApiReq.getMobileToken());
               map.put("payOrg",
                         null == openApiReq.getPayOrg() ? "" : openApiReq
                                   .getPayOrg());
               map.put("singlePenLimit",
                         null == openApiReq.getSinglePenLimit() ? "" : openApiReq
                                   .getSinglePenLimit());
               map.put("dailyLimit", null == openApiReq.getDailyLimit() ? ""
                         : openApiReq.getDailyLimit());
               map.put("monthlyLimit", null == openApiReq.getMonthlyLimit() ? ""
                         : openApiReq.getMonthlyLimit());

               map.put("reqID",
                         null == systemBean.getReqID() ? "" : systemBean.getReqID());
               map.put("partnerID", null == systemBean.getPartnerID() ? ""
                         : systemBean.getPartnerID());
               map.put("charset", null == systemBean.getCharset() ? ""
                         : systemBean.getCharset());
               map.put("signType", null == systemBean.getSignType() ? ""
                         : systemBean.getSignType());

               if (systemBean.getSignType().equals(SystemCommand.SIGNTYPE_RSA)) {
                    PrivateKey prikey = RsaUtil.initPrivateKey(
                              OpenApiCommand.OPENAPI_RSA_PRIVATE_KEY,
                              OpenApiCommand.OPENAPI_RSA_PRIVATE_PWD);
                    signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
               }
          } catch (Exception e) {
        	  LogCvt.error("快捷支付-认证签约加签异常", e);
          }
          return signMsg;
     }

     /**
     * 快捷支付认证取消加签
     * 
     * @param openApiReq
     *            请求参数
     * @param systemBean
     *            系统个参数
     * @return 加签内容
     */
     public static String fastCardSignCancelEnc(SignCancelApiReq openApiReq,
               SystemBean systemBean) {
          String signMsg = null;
          try {
               Map map = new TreeMap();
               map.put("memberID", null == openApiReq.getMemberId() ? ""
                         : openApiReq.getMemberId());
               map.put("bankCardNo", null == openApiReq.getBankCardNo() ? ""
                         : openApiReq.getBankCardNo());
               map.put("payOrg",
                         null == openApiReq.getPayOrg() ? "" : openApiReq
                                   .getPayOrg());

               map.put("reqID",
                         null == systemBean.getReqID() ? "" : systemBean.getReqID());
               map.put("partnerID", null == systemBean.getPartnerID() ? ""
                         : systemBean.getPartnerID());
               map.put("charset", null == systemBean.getCharset() ? ""
                         : systemBean.getCharset());
               map.put("signType", null == systemBean.getSignType() ? ""
                         : systemBean.getSignType());

               if (systemBean.getSignType().equals(SystemCommand.SIGNTYPE_RSA)) {
                    PrivateKey prikey = RsaUtil.initPrivateKey(
                              OpenApiCommand.OPENAPI_RSA_PRIVATE_KEY,
                              OpenApiCommand.OPENAPI_RSA_PRIVATE_PWD);
                    signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
               }
          } catch (Exception e) {
        	  LogCvt.error("快捷支付-认证签约取消加签异常", e);
          }
          return signMsg;
     }

     /**
     * 快捷支付认证取消加签
     * 
     * @param openApiReq
     *            请求参数
     * @param systemBean
     *            系统个参数
     * @return 加签内容
     */
     public static String fastCreateMobileTokenEnc(
               CreateMobileTokenApiReq openApiReq, SystemBean systemBean) {
          String signMsg = null;
          try {
               Map map = new TreeMap();
               map.put("bankCardNo", null == openApiReq.getBankCardNo() ? ""
                         : openApiReq.getBankCardNo());
               map.put("mobilePhone", null == openApiReq.getMobilePhone() ? ""
                         : openApiReq.getMobilePhone());
               map.put("type", null == openApiReq.getType() ? "" : openApiReq
                         .getType().getCode());
               map.put("remark",
                         null == openApiReq.getRemark() ? "" : openApiReq
                                   .getRemark());
               map.put("payOrg",
                         null == openApiReq.getPayOrg() ? "" : openApiReq
                                   .getPayOrg());

               map.put("reqID",
                         null == systemBean.getReqID() ? "" : systemBean.getReqID());
               map.put("partnerID", null == systemBean.getPartnerID() ? ""
                         : systemBean.getPartnerID());
               map.put("charset", null == systemBean.getCharset() ? ""
                         : systemBean.getCharset());
               map.put("signType", null == systemBean.getSignType() ? ""
                         : systemBean.getSignType());

               if (systemBean.getSignType().equals(SystemCommand.SIGNTYPE_RSA)) {
                    PrivateKey prikey = RsaUtil.initPrivateKey(
                              OpenApiCommand.OPENAPI_RSA_PRIVATE_KEY,
                              OpenApiCommand.OPENAPI_RSA_PRIVATE_PWD);
                    signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
               }
          } catch (Exception e) {
        	  LogCvt.error("快捷支付-认证签约取消加签异常", e);
          }
          return signMsg;
     }

     /**
     * 快捷支付认证取消加签
     * 
     * @param openApiReq
     *            请求参数
     * @param systemBean
     *            系统个参数
     * @return 加签内容
     */
     public static String fastSendSmsEnc(SendSmsApiReq openApiReq,
               SystemBean systemBean) {
          String signMsg = null;
          try {
               Map map = new TreeMap();
               map.put("mobilePhone", null == openApiReq.getMobilePhone() ? ""
                         : openApiReq.getMobilePhone());
               map.put("remark",
                         null == openApiReq.getRemark() ? "" : openApiReq
                                   .getRemark());
               map.put("payOrg",
                         null == openApiReq.getPayOrg() ? "" : openApiReq
                                   .getPayOrg());

               // map.put("reqID", null == systemBean.getReqID() ? "" :
               // systemBean.getReqID());
               // map.put("partnerID", null == systemBean.getPartnerID() ? "" :
               // systemBean.getPartnerID());
               // map.put("charset", null == systemBean.getCharset() ? "" :
               // systemBean.getCharset());
               // map.put("signType", null == systemBean.getSignType() ? "" :
               // systemBean.getSignType());

               if (systemBean.getSignType().equals(SystemCommand.SIGNTYPE_RSA)) {
                    PrivateKey prikey = RsaUtil.initPrivateKey(
                              OpenApiCommand.OPENAPI_RSA_PRIVATE_KEY,
                              OpenApiCommand.OPENAPI_RSA_PRIVATE_PWD);
                    signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
               }
          } catch (Exception e) {
        	  LogCvt.error("快捷支付-认证签约取消加签异常", e);
          }
          return signMsg;
     }

     public static String fastSetLimitEnc(LimitReq req, SystemBean systemBean) {
          String signMsg = null;
          try {
               Map map = new TreeMap();
               map.put("bankCardNo",
                         null == req.getBankCardNo() ? "" : req.getBankCardNo());
               map.put("singlePenLimit", null == req.getSinglePenLimit() ? ""
                         : req.getSinglePenLimit());
               map.put("dailyLimit",
                         null == req.getDailyLimit() ? "" : req.getDailyLimit());

               map.put("monthlyLimit",
                         null == req.getMonthlyLimit() ? "" : req.getMonthlyLimit());
               map.put("payOrg", null == req.getPayOrg() ? "" : req.getPayOrg());
               map.put("reqID",
                         null == systemBean.getReqID() ? "" : systemBean.getReqID());
               map.put("partnerID", null == systemBean.getPartnerID() ? ""
                         : systemBean.getPartnerID());
               map.put("charset", null == systemBean.getCharset() ? ""
                         : systemBean.getCharset());
               map.put("signType", null == systemBean.getSignType() ? ""
                         : systemBean.getSignType());

               if (systemBean.getSignType().equals(SystemCommand.SIGNTYPE_RSA)) {
                    PrivateKey prikey = RsaUtil.initPrivateKey(
                              OpenApiCommand.OPENAPI_RSA_PRIVATE_KEY,
                              OpenApiCommand.OPENAPI_RSA_PRIVATE_PWD);
                    signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
               }
          } catch (Exception e) {
               LogCvt.error("快捷支付-设置限额加签异常", e);
          }
          return signMsg;
     }

     // 以上为快捷支付加签

     /**
     * 方法描述：签约关系加签
     * 
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014年7月25日 下午12:48:06
     */
     public static String notifyAccountRelation(String accountMarked,
               String identityNo, String mobileNum, String realName,
               String bankId, String bankCard, String protocolNo,
               String partnerNo, String signType) {
          String signMsg = null;
          try {
               Map map = new TreeMap();
               map.put("accountMarked", null == accountMarked ? "" : accountMarked);
               map.put("identityNo", null == identityNo ? "" : identityNo);
               map.put("mobileNum", null == mobileNum ? "" : mobileNum);
               map.put("realName", null == realName ? "" : realName);
               map.put("bankId", null == bankId ? "" : bankId);
               map.put("bankCard", null == bankCard ? "" : bankCard);
               map.put("partnerNo", null == partnerNo ? "" : partnerNo);
               if (signType.equals(SystemCommand.SIGNTYPE_RSA)) {
                    PrivateKey prikey = RsaUtil.initPrivateKey(
                              PointsCommand.POINTS_RSA_PRIVATE_KEY,
                              PointsCommand.POINTS_RSA_PRIVATE_PWD);
                    signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
               }
          } catch (Exception e) {
        	  LogCvt.error("签约关系通知加签异常", e);
          }
          return signMsg;
     }

     /**
     * 方法描述：账户充值接口加签
     * 
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014年8月18日 上午11:42:03
     */
     public static String deposit(String accountMarked, String cardPassword,
               String partnerNo, String signType) {
          String signMsg = null;
          try {
               Map map = new TreeMap();
               map.put("accountMarked", null == accountMarked ? "" : accountMarked);
               map.put("cardPassword", null == cardPassword ? "" : cardPassword);
               map.put("partnerNo", null == partnerNo ? "" : partnerNo);
               if (signType.equals(SystemCommand.SIGNTYPE_RSA)) {
                    PrivateKey prikey = RsaUtil.initPrivateKey(
                              PointsCommand.POINTS_RSA_PRIVATE_KEY,
                              PointsCommand.POINTS_RSA_PRIVATE_PWD);
                    signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
               }
          } catch (Exception e) {
        	  LogCvt.error("账户充值接口加签异常", e);
          }
          return signMsg;
     }

     /**
     * 方法描述：账单平台交易查询接口加签
     * 
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014年9月3日 上午10:54:20
     */
     public static String query(QueryParamApiReq req, SystemBean systemBean) {
          String signMsg = null;
          String queryType = req.getQueryType();
          String queryOrderType = req.getQueryOrderType();
          String queryOrderID = req.getQueryOrderID();
          String queryOrderState = req.getQueryOrderState();
          String queryTime = req.getQueryTime();
          String reqID = systemBean.getReqID();
          String partnerID = systemBean.getPartnerID();
          String signType = systemBean.getSignType();
          try {
               Map map = new TreeMap();
               map.put("queryType", null == queryType ? "" : queryType);
               map.put("queryOrderType", null == queryOrderType ? ""
                         : queryOrderType);
               map.put("queryOrderID", null == queryOrderID ? "" : queryOrderID);
               map.put("queryOrderState", null == queryOrderState ? ""
                         : queryOrderState);
               map.put("queryTime", null == queryTime ? "" : queryTime);
               map.put("reqID", null == reqID ? "" : reqID);
               map.put("partnerID", null == partnerID ? "" : partnerID);
               if (signType.equals(SystemCommand.SIGNTYPE_RSA)) {
                    PrivateKey prikey = RsaUtil.initPrivateKey(
                              PointsCommand.POINTS_RSA_PRIVATE_KEY,
                              PointsCommand.POINTS_RSA_PRIVATE_PWD);
                    signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
               }
          } catch (Exception e) {
        	  LogCvt.error("openApi账单查询接口加签异常", e);
          }
          return signMsg;
     }

     public static String sendCheckCode(String accountMarked, String orgNo,
               String points, String partnerNo) {
          String signMsg = null;
          try {
               Map map = new TreeMap();
               map.put("accountMarked", accountMarked == null ? "" : accountMarked);
               map.put("orgNo", orgNo == null ? "" : orgNo);
               map.put("points", points == null ? "" : points);
               map.put("partnerNo", partnerNo == null ? "" : partnerNo);
               PrivateKey prikey = RsaUtil.initPrivateKey(
                         PointsCommand.POINTS_RSA_PRIVATE_KEY,
                         PointsCommand.POINTS_RSA_PRIVATE_PWD);
               signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
          } catch (Exception e) {
        	  LogCvt.error("发送验证码加签异常", e);
          }
          return signMsg;
     }

     public static String validateCheckCode(String accountMarked, String checkCode,
               String orgNo, String partnerNo) {
          String signMsg = null;
          try {
               Map map = new TreeMap();
               map.put("accountMarked", accountMarked == null ? "" : accountMarked);
               map.put("checkCode", checkCode == null ? "" : checkCode);
               map.put("orgNo", orgNo == null ? "" : orgNo);
               map.put("partnerNo", partnerNo == null ? "" : partnerNo);
               PrivateKey prikey = RsaUtil.initPrivateKey(
                         PointsCommand.POINTS_RSA_PRIVATE_KEY,
                         PointsCommand.POINTS_RSA_PRIVATE_PWD);
               signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
          } catch (Exception e) {
        	  LogCvt.error("校验验证码加签异常", e);
          }
          return signMsg;
     }

     public static String queryBankPointsByMobile(String mobilePhone,
               String orgNo, String partnerNo) {
          String signMsg = null;
          try {
               Map map = new TreeMap();
               map.put("mobilePhone", mobilePhone == null ? "" : mobilePhone);
               map.put("orgNo", orgNo == null ? "" : orgNo);
               map.put("partnerNo", partnerNo == null ? "" : partnerNo);
               PrivateKey prikey = RsaUtil.initPrivateKey(
                         PointsCommand.POINTS_RSA_PRIVATE_KEY,
                         PointsCommand.POINTS_RSA_PRIVATE_PWD);
               signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
          } catch (Exception e) {
        	  LogCvt.error("按手机号查询银行积分异常", e);
          }
          return signMsg;
     }

     public static String payPointsByMobile(String objectNo, String accountId,
               String partnerNo,String requestNo, String signType) {
          String signMsg = null;
          try {
               Map map = new TreeMap();
               map.put("objectNo", objectNo == null ? "" : objectNo);
               map.put("accountId", accountId == null ? "" : accountId);
               map.put("partnerNo", partnerNo == null ? "" : partnerNo);
               map.put("requestNo", requestNo == null ? "" : requestNo);
               if (signType.equals(SystemCommand.SIGNTYPE_RSA)) {
                    PrivateKey prikey = RsaUtil.initPrivateKey(
                              PointsCommand.POINTS_RSA_PRIVATE_KEY,
                              PointsCommand.POINTS_RSA_PRIVATE_PWD);
                    signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
               }
          } catch (Exception e) {
        	  LogCvt.error("消费/退还积分加签异常", e);
          }
          return signMsg;
     }
     
     
     
     /**
       * 方法描述：查询积分比例的加签
       * @param: orgNo
       * @param: partnerNo
       * @return: String
       * @version: 1.0
       * @author: 李金魁 lijinkui@f-road.com.cn
       * @time: 2014年10月19日 下午6:19:48
       */
     public static String queryRatio(String orgNo,String partnerNo) {
          try {
               Map map = new TreeMap();
               map.put("orgNo", orgNo);
               map.put("partnerNo", partnerNo);
               PrivateKey prikey = RsaUtil.initPrivateKey(
                         PointsCommand.POINTS_RSA_PRIVATE_KEY,
                         PointsCommand.POINTS_RSA_PRIVATE_PWD);
               return RsaUtil.signPrivateKey(map.toString(), prikey);
          } catch (Exception e) {
        	  LogCvt.error("查询积分比例加签异常", e);
          }
          return null;
     }
     
     
     /**
       * 方法描述：银行账户签约接口加签
       * @param: 
       * @return: 
       * @version: 1.0
       * @author: 刘超 liuchao@f-road.com.cn
       * @time: 2014年11月30日 下午12:18:13
       */
     public static String bindBankAccount(String accountMarked,String identityNo,String mobileNum,String realName,String bankId,String bankCard,String partnerNo,String signType){
          String signMsg = null;
          try {
               Map map = new TreeMap();
               map.put("accountMarked", accountMarked);
               map.put("identityNo", identityNo);
               map.put("mobileNum", mobileNum);
               map.put("realName", realName);
               map.put("bankId", bankId);
               map.put("bankCard", bankCard);
               map.put("partnerNo", partnerNo);
               if (signType.equals(SystemCommand.SIGNTYPE_RSA)) {
                    PrivateKey prikey = RsaUtil.initPrivateKey(
                              PointsCommand.POINTS_RSA_PRIVATE_KEY,
                              PointsCommand.POINTS_RSA_PRIVATE_PWD);
                    signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
               }
          } catch (Exception e) {
        	  LogCvt.error("银行账户签约接口加签异常", e);
          }
          return signMsg;
     }
     
     
     /**
       * 方法描述：银行账户解约接口加签
       * @param: 
       * @return: 
       * @version: 1.0
       * @author: 刘超 liuchao@f-road.com.cn
       * @time: 2014年11月30日 下午12:18:13
       */
     public static String unbindBankAccount(String accountMarked,String bankCard,String partnerNo,String signType){
          String signMsg = null;
          try {
               Map map = new TreeMap();
               map.put("accountMarked", accountMarked);
               map.put("bankCard", bankCard);
               map.put("partnerNo", partnerNo);
               if (signType.equals(SystemCommand.SIGNTYPE_RSA)) {
                    PrivateKey prikey = RsaUtil.initPrivateKey(
                              PointsCommand.POINTS_RSA_PRIVATE_KEY,
                              PointsCommand.POINTS_RSA_PRIVATE_PWD);
                    signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
               }
          } catch (Exception e) {
        	  LogCvt.error("银行账户解约接口加签异常", e);
          }
          return signMsg;
     }
     
     
     
     /**
       * 方法描述：用户消费积分分页查询
       * @param: 
       * @return: 
       * @version: 1.0
       * @author: 刘超 liuchao@f-road.com.cn
       * @time: 2014年12月1日 下午7:28:51
       */
     public static String queryMemberProtocols(String orgNo,String protocolType,String accountMarked,String partnerNo,String signType){
          String signMsg = null;
          try {
               Map map = new TreeMap();
               map.put("orgNo", orgNo);
               map.put("protocolType", protocolType);
               map.put("accountMarked", accountMarked);
               map.put("partnerNo", partnerNo);
               if (signType.equals(SystemCommand.SIGNTYPE_RSA)) {
                    PrivateKey prikey = RsaUtil.initPrivateKey(
                              PointsCommand.POINTS_RSA_PRIVATE_KEY,
                              PointsCommand.POINTS_RSA_PRIVATE_PWD);
                    signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
               }
          } catch (Exception e) {
        	  LogCvt.error("用户消费积分分页查询接口加签异常", e);
          }
          return signMsg;
     }
     public static String contractRelationshipQuery(String accountMarked,String signType){
    	 String signMsg = null;
         try {
              Map map = new TreeMap();
              map.put("accountMarked", accountMarked);
              if (signType.equals(SystemCommand.SIGNTYPE_RSA)) {
                   PrivateKey prikey = RsaUtil.initPrivateKey(
                             PointsCommand.POINTS_RSA_PRIVATE_KEY,
                             PointsCommand.POINTS_RSA_PRIVATE_PWD);
                   signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
              }
         } catch (Exception e) {
       	  LogCvt.error("查询用户积分签约关系", e);
         }
         return signMsg;
     }
     
     
     /**
      * 方法描述：主动查询积分
      * @param: 
      * @return: 
      * @version: 1.0
      * @author: 刘超 liuchao@f-road.com.cn
      * @time: 2014年12月1日 下午7:28:51
      */
     public static String queryOrderStatus(String queryOrderID,String queryOrderType,String partnerNo,String signType ){
    	 String signMsg = null;
    	 try {
    		 Map map = new TreeMap();
    		 map.put("queryOrderType", queryOrderType);
    		 map.put("queryOrderID", queryOrderID);
    		 map.put("partnerNo", partnerNo);
    		 if (signType.equals(SystemCommand.SIGNTYPE_RSA)) {
    			 PrivateKey prikey = RsaUtil.initPrivateKey(
    					 PointsCommand.POINTS_RSA_PRIVATE_KEY,
    					 PointsCommand.POINTS_RSA_PRIVATE_PWD);
    			 signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
    		 }
    	 } catch (Exception e) {
    		 LogCvt.error("主动查询积分加签异常", e);
    	 }
    	 return signMsg;
     }
     
     
     

}