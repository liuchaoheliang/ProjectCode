package com.froad.thrift.service.impl;

import java.io.File;

import org.apache.ibatis.session.SqlSession;
import org.apache.thrift.TException;

import com.froad.QrCodeConstants;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.CbankClientMapper;
import com.froad.db.mysql.mapper.QrCodeMapper;
import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.MonitorPointEnum;
import com.froad.logback.LogCvt;
import com.froad.monitor.MonitorService;
import com.froad.monitor.impl.MonitorManager;
import com.froad.po.QrCode;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.QrCodeService;
import com.froad.thrift.vo.QrCodeRequestVo;
import com.froad.thrift.vo.QrCodeResponseVo;
import com.froad.util.PropertiesUtil;
import com.froad.util.QrCodeUtil;

/**
 * @author F-road
 *
 */
public class QrCodeServiceImpl extends BizMonitorBaseService implements QrCodeService.Iface {
	
	/**
	 * 业务监控
	 */
	private static MonitorService monitorService = new MonitorManager();
	
	public QrCodeServiceImpl(String name, String version) {
		super(name, version);
	}
	
	/**
	 * 从数据库中获取二维码URL
	 * 
	 * @param keyword
	 * @param bankCode
	 * @return
	 */
	private String getUrlFromDatabase(QrCodeRequestVo qrcodeRequestVo){
		QrCode qrCode = null;
		SqlSession sqlSession = null;
		QrCodeMapper qrCodeMapper = null;
		String url = null;
		
		sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
		qrCodeMapper = sqlSession.getMapper(QrCodeMapper.class);
		qrCode = qrCodeMapper.getQrCode(qrcodeRequestVo);
		sqlSession.close();
		
		if (null != qrCode) {
			url = qrCode.getUrl();
		}
		
		return url;
	}
	
	/**
	 * 从Redis获取银行logo URL
	 * 
	 * @param clientId
	 * @return
	 */
	private String retrieveUrlFromRedis(String clientId){
		RedisManager redisManager = null;
		String logoUrl = null;
		
		redisManager = new RedisManager();
		logoUrl = redisManager.getMapValue(new StringBuffer(
				QrCodeConstants.REDIS_CB_CLIENT_KEY_PREFIX).append(clientId)
				.toString(), QrCodeConstants.REDIS_CB_CLIENT_QR_LOGO);
		
		return logoUrl;
	}
	
	/**
	 * 从Mysql获取银行logo URL
	 * 
	 * @param clientId
	 * @return
	 */
	private String retrieveUrlFromMysql(String clientId){
		SqlSession sqlSession = null;
		CbankClientMapper mapper = null;
		String url = null;
		
		sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
		mapper = sqlSession.getMapper(CbankClientMapper.class);
		url = mapper.getLogoUrl(clientId);
		sqlSession.close();
		
		return url;
	}
	
	/**
	 * 生产二维码,并返回二维码URL
	 * 
	 * @param qrcodeRequestVo
	 * @return
	 */
	private String generateQrCode(QrCodeRequestVo qrcodeRequestVo) throws Exception{
		String keyword = null;
		String localLogoDir = null;
		String localLogoName = null;
		String localLogoPath = null;
		String logoUrl = null;
		String clientId = null;
		String qrCodeUrl = null;
		QrCodeUtil qrcodeUtil = null;
		File file = null;
		
		keyword = qrcodeRequestVo.getKeyword();
		clientId = qrcodeRequestVo.getClientId();
		
		/**
		 * 获取目录信息
		 */
		localLogoDir = PropertiesUtil.getProperty(
				QrCodeConstants.QR_PROPERTIES_NAME, QrCodeConstants.PROPERTY_LOGO_LOCAL_DIR).replace(
				QrCodeConstants.DOUBLE_BACKSLASH, QrCodeConstants.SLASH);
		
		qrcodeUtil = new QrCodeUtil();
		
		if (null != clientId){
			localLogoName = new StringBuffer(clientId).append("_logo.png").toString();
			localLogoPath = new StringBuffer(localLogoDir).append(localLogoName).toString();
			file = new File(localLogoPath);
			if (!file.exists()) {
				/**
				 * 获取银行logo URL 1) 先从Redis缓存中获取 2) 若Redis中获取不到, 则尝试从mysql获取 3)
				 * 若Redis跟mysql均获取不了, 则默认生成无logo二维码
				 */
				logoUrl = retrieveUrlFromRedis(clientId);
				if (null == logoUrl) {
					logoUrl = retrieveUrlFromMysql(clientId);
				}
				
				/**
				 * 如果生成本地logo失败,使用不带logo二维码
				 */
				if (null == logoUrl || !qrcodeUtil.getRemoteLogo(logoUrl, localLogoDir)) {
					localLogoPath = null;
				}
			}
		}
		
		qrCodeUrl = qrcodeUtil.generateQrCode(keyword, localLogoPath, clientId);
		
		return qrCodeUrl;
	}

	/**
	 * 获取二维码
	 *  1)如二维码信息已经存库，则返回相关信息
	 *  2)如二维码未生成，则生产二维码，并返回相关信息
	 * 
	 * @param qrcodeRequestVo
	 * @return
	 */
	public QrCodeResponseVo retrieveQrCode(QrCodeRequestVo qrcodeRequestVo)
			throws TException {
		QrCodeResponseVo responseVo = null;
		String url = null;
		String resultCode = null;
		String resultDesc = null;
		
		try {
			LogCvt.info(new StringBuffer("获取二维码 request: ").append(qrcodeRequestVo.toString()).toString());
			monitorService.send(MonitorPointEnum.Qrcode_Qrcode_Gen_Count);//秒触发次数
			
			// try to retrieve qrcode from DB
			url = getUrlFromDatabase(qrcodeRequestVo);
			LogCvt.info(new StringBuffer("QrCode URL from DB: ").append(url).toString());
			
			// if qrcode does not exist in DB, generate a new one
			if (null == url) {
				LogCvt.info(new StringBuffer("开始生成二维码: ").append(qrcodeRequestVo.toString()).toString());
				url = generateQrCode(qrcodeRequestVo);
				LogCvt.info(new StringBuffer("生成的二维码url: ").append(url).toString());
			}
			
			resultCode = QrCodeConstants.NORMAL_RESPONSE;
			resultDesc = QrCodeConstants.BLANK_STRING;
		} catch (Exception e){
			url = QrCodeConstants.BLANK_STRING;
			resultCode = QrCodeConstants.ABNORMAL_RESPONSE;
			resultDesc = e.getMessage();
			LogCvt.error(new StringBuffer("获取二维码异常：").append(qrcodeRequestVo.toString()).toString());
			monitorService.send(MonitorPointEnum.Qrcode_Qrcode_Gen_Failed_Count);//每秒失败次数
		}
		
		// set return value to response vo
		responseVo = new QrCodeResponseVo();
		responseVo.setUrl(url);
		responseVo.setResultCode(resultCode);
		responseVo.setResultDesc(resultDesc);
		
		LogCvt.info(new StringBuffer("获取二维码 response: ").append(responseVo.toString()).toString());
		
		return responseVo;
	}

	@Override
	public String generateWordImage(String content) throws TException {
		QrCodeUtil qrcodeUtil = null;
		
		qrcodeUtil = new QrCodeUtil();
		
		return qrcodeUtil.generateCheckCode(content);
	}
}
