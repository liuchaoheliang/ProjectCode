package com.froad.worker;

import com.froad.worker.WorkTeam;

/**
 * 启动入口
 * 
 * @author wangzhangxu
 * @date 2015年4月23日 下午8:40:23
 * @version v1.0
 */
public class WorkMain {

	public static void main(String[] args) {
		WorkTeam team = new WorkTeam();
		team.work();
	}

}
