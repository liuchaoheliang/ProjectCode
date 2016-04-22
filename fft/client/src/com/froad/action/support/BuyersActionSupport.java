package com.froad.action.support;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.froad.client.buyers.Buyers;
import com.froad.client.buyers.BuyersService;
import com.froad.client.user.MUserService;
import com.froad.client.user.User;
import com.froad.client.buyers.BuyerChannel;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 * 买家 client service  ActionSupport
 * @param <BuyerChannel>
 */
public class BuyersActionSupport {
	private BuyersService buyersService;
	private MUserService userService;
	private static final Logger logger=Logger.getLogger(BuyersActionSupport.class);
	
	public User getUserIdByPhone(String phone) {
		User user=null;
		try {
			user = userService.selectUserMobilephone(phone);
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.error("查询user调用服务失败！", e);
		}
		return user;
	}
	
	public List<BuyerChannel> getBuyerPayChannelByUserId(String userId){
		List<BuyerChannel> buyersDepositChannelList=new ArrayList();
		Buyers buyer=getBuyerByUserId(userId);
		buyersDepositChannelList=(List<BuyerChannel>) buyer.getBuyerChannelList();
		return buyersDepositChannelList;
	}
	
	public Buyers getBuyerByUserId(String userId) {
		Buyers buyer=null;
		try {
			buyer = buyersService.getBuyerByUserId(userId);
		} catch (Exception e2) {
			logger.error("调用：通过userID获取买家信息失败！",e2);
		}
		return buyer;
	}
	
	public Buyers getBuyerById(String buyerId) {
		Buyers buyer=null;
		try {
			buyer = buyersService.getById(Integer.parseInt(buyerId));
		} catch (Exception e2) {
			// TODO: handle exception
			logger.error("调用：通过userID获取买家信息失败！",e2);
		}
		
		return buyer;
	}
	
	public void changeToBuyers(Buyers buyers){
		try {
			buyersService.addBuyer(buyers);
		} catch (Exception e) {
			logger.error("BuyersActionSupport.changeToBuyers成为买家调用失败！",e);
		}
	}
	
	public boolean updateBuyerAndBuyerChannel(String userId,String mobile){
		return buyersService.updateBuyerAndBuyerChannel(userId, mobile);
	}
	
	public BuyersService getBuyersService() {
		return buyersService;
	}
	public void setBuyersService(BuyersService buyersService) {
		this.buyersService = buyersService;
	}

	public MUserService getUserService() {
		return userService;
	}

	public void setUserService(MUserService userService) {
		this.userService = userService;
	}
	 
	
}
