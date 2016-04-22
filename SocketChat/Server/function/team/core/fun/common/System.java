package team.core.fun.common;

import team.core.beans.conveying.UtilConveyingMsg;
import team.core.constant.SysConfig;

public class System {

	/**
	 * 获取服务器版本号
	 */
	public UtilConveyingMsg getServerVersion(Object data){
		return new UtilConveyingMsg("200", SysConfig.VERSION);
	}
}
