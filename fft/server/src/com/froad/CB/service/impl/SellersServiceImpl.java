package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.AppException.AppException;
import com.froad.CB.dao.SellerChannelDao;
import com.froad.CB.dao.SellerDao;
import com.froad.CB.dao.user.UserDao;
import com.froad.CB.po.Seller;
import com.froad.CB.po.SellerChannel;
import com.froad.CB.po.user.User;
import com.froad.CB.service.SellersService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-1-29  
 * @version 1.0
 */
@WebService(endpointInterface="com.froad.CB.service.SellersService")
public class SellersServiceImpl implements SellersService {
	private static final Logger logger=Logger.getLogger(BuyersServiceImpl.class);
	private SellerDao sellerDao;
	private SellerChannelDao sellerChannelDao;
	private UserDao userDao;
	
	private void setUserForSeller(Seller seller){
		if(seller!=null&&seller.getUserId()!=null){
			User user=userDao.queryUserAllByUserID(seller.getUserId());
			if(user==null){
				logger.error("卖家对应的用户不存在");
			}else{
				seller.setUser(user);
			}
		}
	}
	
	private void setUserForSellerList(List<Seller> sellerList){
		if(sellerList!=null&&sellerList.size()>0){
			Seller seller=null;
			for (int i = 0; i <sellerList.size(); i++) {
				seller=sellerList.get(i);
				User user=userDao.queryUserAllByUserID(seller.getUserId());
				if(user==null){
					logger.error("卖家对应的用户不存在");
				}else{
					seller.setUser(user);
				}

			}
		}
	}
	
	@Override
	public Integer addSeller(Seller seller) {
		if(seller==null){
			logger.info("增加卖家参数不能为空！");
			return null;
		}
		List<SellerChannel> list=seller.getSellerChannelList();
		Integer sellerId=sellerDao.insert(seller);
		if(list!=null&&list.size()>0){
			for (int i = 0; i <list.size(); i++) {
				list.get(i).setSellerId(sellerId+"");
			}
			sellerChannelDao.batchInsert(list);
		}
		return sellerId;
	}

	@Override
	public Integer updateById(Seller seller) {
		if(seller==null){
			logger.info("修改卖家参数不能为空！");
			return null;
		}
		return sellerDao.updateById(seller);
	}

	@Override
	public Seller selectById(Integer id) {
		if(id==null){
			logger.info("查询卖家参数id不能为空！");
			return null;
		}
		Seller seller=sellerDao.selectByPrimaryKey(id);
		setUserForSeller(seller);
		return seller;
	}

	@Override
	public Integer deleteById(String id) {
		if(id==null){
			logger.info("删除卖家参数id不能为空！");
			return null;
		}
		return sellerDao.deleteByPrimaryKey(id);
	}

	@Override
	public Integer deleteStateById(String id) {
		if(id==null){
			logger.info("删除卖家参数id不能为空！");
			return null;
		}
		return sellerDao.deleteStateByPrimaryKey(id);
	}

	@Override
	public List<Seller> getSellerByUserId(String userId) {
		if(userId==null){
			logger.info("查询卖家参数userId不能为空！");
			return null;
		}
		List<Seller> sellerList=sellerDao.getSellerByUserId(userId);
		setUserForSellerList(sellerList);
		return sellerList;
	}

	@Override
	public List<Seller> getSellerByMerchantId(String merchantId) {
		if(merchantId==null){
			logger.info("查询卖家参数merchantId不能为空！");
			return null;
		}
		List<Seller> sellerList=sellerDao.getSellerByMerchantId(merchantId);
		setUserForSellerList(sellerList);
		return sellerList;
	}

	public SellerDao getSellerDao() {
		return sellerDao;
	}

	public void setSellerDao(SellerDao sellerDao) {
		this.sellerDao = sellerDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public SellerChannelDao getSellerChannelDao() {
		return sellerChannelDao;
	}

	public void setSellerChannelDao(SellerChannelDao sellerChannelDao) {
		this.sellerChannelDao = sellerChannelDao;
	}

	@Override
	public boolean bindingAccount(Seller seller) throws AppException {
		if(seller==null||seller.getUserId()==null||"".equals(seller.getUserId())){
			logger.error("卖家的用户编号为空");
			return false;
		}
		List<SellerChannel> channelList=seller.getSellerChannelList();
		if(channelList==null||channelList.size()==0){
			logger.error("卖家账户信息为空");
			return false;
		}
		SellerChannel channel=channelList.get(0);
		if(channel.getAccountName()==null||"".equals(channel.getAccountName())||
				channel.getAccountNumber()==null||"".equals(channel.getAccountNumber())){
			logger.error("卖家账户信息为空");
			return false;
		}
		User user=userDao.queryUserAllByUserID(seller.getUserId());
		if(user==null){
			logger.error("卖家对应的用户不存在，userId=="+seller.getUserId());
			return false;
		}
		List<Seller> sellerList=this.getSellerByUserId(seller.getUserId());
		if(sellerList==null||sellerList.size()==0){
			logger.info("卖家记录不存在，开始创建卖家并绑定账户信息，userId=="+seller.getUserId());
			Integer id=this.addSeller(seller);
			if(id!=null){
				logger.info("卖家账户绑定成功");
				return true;
			}else{
				logger.error("卖家账户绑定失败");
				return false;
			}
		}else{
			logger.info("卖家记录已经存在。开始绑定账户信息");
			try {
				sellerChannelDao.batchInsert(channelList);
				logger.info("卖家账户绑定成功");
				return true;
			} catch (Exception e) {
				logger.error("卖家账户绑定时出现异常",e);
				return false;
			}
		}
	}

	@Override
	public List<Seller> getBySelective(Seller seller) {
		if(seller==null){
			logger.error("参数为空，查询失败");
			return null;
		}
		return sellerDao.getBySelective(seller);
	}
	
}
