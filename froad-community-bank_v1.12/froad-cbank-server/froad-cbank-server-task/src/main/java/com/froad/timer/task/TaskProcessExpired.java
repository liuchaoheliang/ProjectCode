package com.froad.timer.task;

import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.ibatis.session.SqlSession;

import com.froad.Constants;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.MerchantMapper;
import com.froad.enums.MonitorPointEnum;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.monitor.MonitorService;
import com.froad.monitor.impl.MonitorManager;
import com.froad.po.Merchant;
import com.froad.thrift.client.ThriftClientProxyFactory;
import com.froad.thrift.service.MerchantService;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PlatType;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.AllkindsTimeConfig;
import com.froad.util.ThriftConfig;

/**
 * 
 * 处理过期 - 定时任务
 * 
 * 商户和门店
 * 
 * @author lf 2015.03.20
 * @modify lf 2015.03.20
 * 
 * */
public class TaskProcessExpired implements Runnable {

	private static Properties expiredTimeContrast = null;
	/**
	 * 监控服务
	 * */
	private MonitorService monitorService = new MonitorManager();
	static{
		String fileName = "expiredTimeContrast.properties";
		try{
			expiredTimeContrast = new Properties();
			String path = System.getProperty(Constants.CONFIG_PATH) + File.separatorChar + fileName;
			expiredTimeContrast.load(new FileInputStream(path));
		}catch(Exception e){
			LogCvt.error("读取过期时间对比配置文件 "+fileName+" 异常:"+e.getMessage(), e);
			expiredTimeContrast = null;
		}
	}
	
	@Override
	public void run() {
		LogCvt.debug("定时任务: 处理过期商户和门店------开始------");
		
		if( expiredTimeContrast == null ){
			LogCvt.info("定时任务: 处理过期商户和门店------完成(过期时间对比配置文件读取为空)------");
			return;
		}
		
		SqlSession sqlSession = null;
		try{
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			MerchantMapper merchantMapper = sqlSession.getMapper(MerchantMapper.class);
			
			// 查询可续约的商户
			List<Merchant> merchantList = merchantMapper.selectCanRenewal();
			
			if(CollectionUtils.isEmpty(merchantList)){
				LogCvt.info("定时任务: 处理过期商户和门店------完成(无可续约的商户)------");
				return;
			}
			
			LogCvt.debug("可续约的商户共有"+merchantList.size()+"个");
			
			// 操作日志参数
			String localhost = InetAddress.getLocalHost().getHostAddress();
			OriginVo originVo = new OriginVo();
			originVo.setPlatType(PlatType.auto_task);
			originVo.setOperatorId(0);
			originVo.setOperatorIp(localhost);
			originVo.setDescription("定时续约过期商户和门店");
			// 续约时间	
			long contractEndtime = 0;
			// 商户接口 - 操作续约
			MerchantService.Iface merchantClient = 
					(MerchantService.Iface)ThriftClientProxyFactory.createIface(MerchantService.class.getName(), MerchantService.class.getSimpleName(), ThriftConfig.MERCHANT_SERVICE_HOST, ThriftConfig.MERCHANT_SERVICE_PORT);
			
			// 商品接口 - 商户启用变化商品的审核状态
			ProductService.Iface productClient = 
					(ProductService.Iface)ThriftClientProxyFactory.createIface(ProductService.class.getName(), ProductService.class.getSimpleName(), ThriftConfig.GOODS_SERVICE_HOST, ThriftConfig.GOODS_SERVICE_PORT);
			
			for( Merchant merchant : merchantList ){
				LogCvt.info("定时任务: 处理过期商户和门店------商户"+merchant.getId()+" 处理 --- 开始------");
				// 判断商户签约到期时间是否快过期
				//if( isAboutExpired(merchant) ){
					
					// 到期时间 + 配置的年数
					contractEndtime = DateUtils.addYears(merchant.getContractEndtime(), AllkindsTimeConfig.getProcessExpiredRenewalYears()).getTime();
					contractEndtime=isExpired(contractEndtime);
					// 调用接口 - 续约商户
					LogCvt.debug("过期商户[merchantId="+merchant.getMerchantId()+"]开始进行续约处理");
					ResultVo result = merchantClient.extensionMerchant(originVo, merchant.getMerchantId(), contractEndtime);
					LogCvt.debug("过期商户[merchantId="+merchant.getMerchantId()+"]处理结果: 返回码[resultCode="+result.getResultCode()+"]|返回信息[resultDesc="+result.getResultDesc()+"]");
					if( !ResultCode.success.getCode().equals(result.getResultCode()) ){
						// TODO 续约商户处理不成功 - 发送监控
						monitorService.send(MonitorPointEnum.Timertask_Handle_Expired_Merchant_Failure);
					}else{
						// 调用接口 - 商户启用变化商品的审核状态为待审核
					//	result = productClient.validMerchantProductBatch(merchant.getMerchantId());
					//	LogCvt.debug("商户[merchantId="+merchant.getMerchantId()+"]续约后 商品的审核状态为待审核处理结果: 返回码[resultCode="+result.getResultCode()+"]|返回信息[resultDesc="+result.getResultDesc()+"]");
						
					}
			//	}
				LogCvt.info("定时任务: 处理过期商户和门店------商户"+merchant.getId()+" 处理 --- 结束------");
			}
			
		} catch(Exception e){
			LogCvt.error("定时任务: 处理过期商品和门店------系统异常------");
			LogCvt.error(e.getMessage(), e);
			// TODO 续约商户处理不成功 - 发送监控
			monitorService.send(MonitorPointEnum.Timertask_Handle_Expired_Merchant_Failure);
		} finally {
			if(sqlSession != null) sqlSession.close();
			LogCvt.debug("定时任务: 处理过期商品和门店------结束------");
		}
	}
	
	// 判断商户签约到期时间是否快过期(根据配置判断)
	private boolean isAboutExpired(Merchant merchant){
		try{
			String clientId = merchant.getClientId();
			String proOrgCode = merchant.getProOrgCode();
			String key = clientId+"_"+proOrgCode;
			String value = expiredTimeContrast.getProperty(key);
			if( value == null ){
				LogCvt.debug("过期时间对比配置文件读取 "+ key +" 为空");
				return false;
			}
			Date contractEndtime = merchant.getContractEndtime();
			Date now = new Date();
			int days = Integer.parseInt(value);
			if( days > 180 ){
				days = 180;
			}
			Date after = DateUtils.addDays(now, days);
			// 到期时间 大于 现在 （去掉）&& 到期时间 小于 配置的几天后时间
			if( contractEndtime.getTime() < after.getTime() ){
				LogCvt.info("定时任务: 处理过期商户和门店------商户"+merchant.getId()+" 快过期------");
				return true;
			}
			LogCvt.info("定时任务: 处理过期商户和门店------商户"+merchant.getId()+" 暂不会过期------");
			return false;
		}catch(Exception e){
			LogCvt.error("判断商户 "+merchant.getMerchantId()+" 签约到期时间是否快过期 出现异常:"+e.getMessage(), e);
			return false;
		}
	}
	//判断日期是否大于今天,如2014年续约小于今天日期，加多一年
	public Long isExpired(long time){
		Date date=new Date();
		long today=date.getTime();
		if(today>time){
			return DateUtils.addYears(new Date(time), AllkindsTimeConfig.getProcessExpiredRenewalYears()).getTime();
		}
		return time;
	}
}
