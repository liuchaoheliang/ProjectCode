package com.froad.CB.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.AppException.AppException;
import com.froad.CB.common.Command;
import com.froad.CB.common.SysCommand;
import com.froad.CB.common.constant.TranCommand;
import com.froad.CB.dao.BuyerChannelDao;
import com.froad.CB.dao.BuyersDao;
import com.froad.CB.dao.FundsChannelDao;
import com.froad.CB.dao.UserCertificationDao;
import com.froad.CB.dao.user.UserDao;
import com.froad.CB.po.BuyerChannel;
import com.froad.CB.po.Buyers;
import com.froad.CB.po.FundsChannel;
import com.froad.CB.po.UserCertification;
import com.froad.CB.po.bill.AccountCheck;
import com.froad.CB.po.user.User;
import com.froad.CB.service.UserCertificationService;
import com.froad.CB.thirdparty.BillFunc;
import com.froad.util.DateUtil;

@WebService(endpointInterface="com.froad.CB.service.UserCertificationService")
public class UserCertificationServiceImpl implements UserCertificationService{

	private static final Logger logger=Logger.getLogger(UserCertificationServiceImpl.class);
	
	private UserCertificationDao userCertificationDao;
	
	private UserDao userDao;
	
	private BuyersDao buyersDao;
	
	private BuyerChannelDao buyerChannelDao;
	
	private FundsChannelDao fundsChannelDao;
	
	@Override
	public UserCertification getUserCertByUserId(String userId) {
		if(userId==null||"".equals(userId)){
			logger.error("用户编号为空");
		}
		return userCertificationDao.getUserCertByUserId(userId);
	}

	public void setUserCertificationDao(UserCertificationDao userCertificationDao) {
		this.userCertificationDao = userCertificationDao;
	}

	@Override
	public Integer add(UserCertification cert)throws AppException{
		if(cert==null){
			logger.error("传入的参数为空");
			return null;
		}
		String userId=cert.getUserId();
		if(userId==null||"".equals(userId)){
			logger.error("用户编号为空");
			return null;
		}
		User user=userDao.queryUserAllByUserID(userId);
		if(user==null){
			logger.error("用户不存在，用户编号： "+userId);
			return null;
		}
		if(cert.getChannelId()==null||"".equals(cert.getChannelId())){
			logger.info("资金渠道为空，账户认证失败");
			return null;
		}
		FundsChannel fundsChannel=fundsChannelDao.selectByPrimaryKey(Integer.parseInt(cert.getChannelId()));
		if(fundsChannel==null){
			logger.info("编号为："+cert.getChannelId()+"的资金渠道不存，账户认证失败");
			return null;
		}
		cert.setFundsChannel(fundsChannel);
		String time=DateUtil.formatDate2Str(new Date());
		cert.setCreateTime(time);
		cert.setUpdateTime(time);
		String certType=cert.getCertificationType();//认证类型
		AccountCheck check=new AccountCheck();
		check.setCheckType(certType);
		check.setCheckOrg(TranCommand.TRANSFER_ORG);
		//1手机贴膜卡认证，2信通卡认证
		if(TranCommand.CHECK_CARD.equals(certType)){//信通卡认证
			check.setCheckContent(cert.getAccountNo()+"|"+cert.getAccountName());
		}else if(TranCommand.CHECK_PHONE.equals(certType)){//手机贴膜卡认证
			check.setCheckContent(cert.getPhone());
		}else{
			throw new AppException("未知的认证类型: "+certType);
		}
		AccountCheck checkRes=null;
		try {
			checkRes=BillFunc.checkAccount(check);
		} catch (Exception e) {
			logger.error("账户验证异常",e);
			throw new AppException(e);
		}
		if(checkRes!=null&&TranCommand.SUCCESS.equals(checkRes.getCheckResultCode())){//账户校验通过
			if(TranCommand.CHECK_PHONE.equals(certType)){//手机贴膜卡认证
				String result=checkRes.getCheckResultContent();
				String account=result.split(",")[0];
				String[] tmp=account.split("\\|");
				cert.setAccountNo(tmp[0]);
				cert.setAccountName(tmp[1]);
			}
//			Buyers buyer=this.addOrUpdateBuyerChannel(cert);
			cert.setCertificationResult("1");
//			cert.setBuyer(buyer);
			return userCertificationDao.add(cert);
		}
		return null;
	}

	private Buyers addOrUpdateBuyerChannel(UserCertification cert){
		String userId=cert.getUserId();
		String time=DateUtil.formatDate2Str(new Date());
		Buyers buyer=buyersDao.getBuyersByUserId(userId);
		if(buyer==null){//如果买家不存在，为该用户创建一个买家
			buyer=new Buyers();
			buyer.setUserId(userId);
			buyer.setState(Command.STATE_START);
			buyer.setCreateTime(time);
			buyer.setUpdateTime(time);
			Integer buyerId=buyersDao.insert(buyer);
			buyer.setId(buyerId);
		}
		BuyerChannel channel=new BuyerChannel();
		channel.setUserId(userId);
		channel.setBuyersId(buyer.getId()+"");
		if(TranCommand.CHECK_PHONE.equals(cert.getCertificationType())){//手机贴膜卡认证
			channel.setPhone(cert.getPhone());
		}else{
			channel.setChannelId(cert.getChannelId());
			channel.setAccountName(cert.getAccountName());
			channel.setAccountNumber(cert.getAccountNo());
		}
		channel.setState(Command.STATE_START);
		channel.setCreateTime(time);
		channel.setUpdateTime(time);
		int rows=buyerChannelDao.updateChannelByBuyerId(channel);
		if(rows<1){
			buyerChannelDao.insert(channel);
		}
		List<BuyerChannel> channelList=new ArrayList<BuyerChannel>();
		channelList.add(channel);
		buyer.setBuyerChannelList(channelList);
		return buyer;
	}
	
	@Override
	public List<UserCertification> getUserCertBySelective(UserCertification cert) {
		if(cert==null){
			logger.error("参数为空，查询失败");
			return null;
		}
		return userCertificationDao.getUserCertBySelective(cert);
	}

	@Override
	public boolean checkAccount(UserCertification cert) throws AppException {
		String certType=cert.getCertificationType();//认证类型
		AccountCheck check=new AccountCheck();
		check.setCheckOrg(TranCommand.TRANSFER_ORG);
		check.setCheckType(certType);
		if(TranCommand.CHECK_CARD.equals(certType)){//信通卡认证
			check.setCheckContent(cert.getAccountNo()+"|"+cert.getAccountName());
		}else if(TranCommand.CHECK_PHONE.equals(certType)){//手机贴膜卡认证
			check.setCheckContent(cert.getPhone());
		}else{
			throw new AppException("未知的认证类型: "+certType);
		}
		AccountCheck checkRes=null;
		try {
			checkRes=BillFunc.checkAccount(check);
			if(checkRes!=null&&TranCommand.SUCCESS.equals(checkRes.getCheckResultCode())){
				return true;
			}
			return false;
		} catch (Exception e) {
			logger.error("账户验证异常",e);
			throw new AppException(e);
		}
	}
	
	@Override
	public Integer addOrUpdateBindingNew(UserCertification cert) throws AppException {
		UserCertification certRes = null;
		Integer num = null;
		try {
			certRes = userCertificationDao.getUserCertByUserId(cert.getUserId());
			if(certRes == null){
				num = userCertificationDao.add(cert);//不存在认证成功的记录，就添加一条记录			
			}else{
				num = userCertificationDao.updateUserCertByUserId(cert);//如果存在已经认证的记录就更新操作
			}
		} catch (Exception e) {
			logger.error("账户验证异常",e);
			throw new AppException(e);
		}
		return num;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setBuyersDao(BuyersDao buyersDao) {
		this.buyersDao = buyersDao;
	}

	public void setBuyerChannelDao(BuyerChannelDao buyerChannelDao) {
		this.buyerChannelDao = buyerChannelDao;
	}

	public void setFundsChannelDao(FundsChannelDao fundsChannelDao) {
		this.fundsChannelDao = fundsChannelDao;
	}

}
