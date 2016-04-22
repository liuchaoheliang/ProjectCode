package team.core.constant;

import java.util.HashMap;
import java.util.Map;


/**
 * code|方法  映射 
 */
public class CodeFunInsinuate {

	public static Map<String, String> insinuate = new HashMap<String, String>();
	
	static{
		insinuate.put("0001", "team.core.fun.common.Login|login");
		insinuate.put("0002", "team.core.fun.common.Register|register");
		insinuate.put("0003", "team.core.fun.users.AboutFriend|addFriend");
		insinuate.put("0004", "team.core.fun.users.AboutFriend|removeFriend");
		insinuate.put("0005", "team.core.fun.users.AboutUser|getUserDetailInfo");
		insinuate.put("0006", "team.core.fun.users.AboutFriend|getFriendList");
		insinuate.put("0007", "team.core.fun.common.System|getServerVersion");
	}
}
