/*package test;

import com.froad.API.user.UserApi;
import com.froad.client.MUser.LoginManager;
import com.froad.client.MUser.LoginResult;
import com.froad.client.MUser.User;

public final class Client {
//    private Client() {
//    }

    public static void main(String args[]) throws Exception {

    	//注册
    	User user=new User();
    	user.setUsername("goujw");
    	user.setPassword("psss");
    	user=UserApi.addUser(user);
    	
    	System.out.println(user.getRespCode());
    	System.out.println(user.getRespMsg());
    	
    	//激活
    	user.setUsername("goujw");
    	user=UserApi.addedUser(user);
    	
    	//登录
    	user.setUsername("goujw");
    	user.setPassword("psss");
    	user.setUname("勾君伟");
    	LoginResult lr=UserApi.login(user);
    	System.out.println(lr.getRespCode());
    	System.out.println(lr.getRespMsg());
    	//退出
    	LoginManager loginManager=new LoginManager();
    	loginManager.setSessionID(lr.getSessionID());
    	loginManager.setUsername("goujw");
    	loginManager=UserApi.logout(loginManager);
    	System.out.println(loginManager.getRespCode());
    	System.out.println(loginManager.getRespMsg());
    	
//    	GoodsImpl.queryAllGoods();
    }
}*/