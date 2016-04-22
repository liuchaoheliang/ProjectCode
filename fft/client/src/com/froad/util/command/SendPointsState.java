package com.froad.util.command;

/**
  * 类描述：积分交易中赠送积分的状态
  * @version: 1.0
  * @Copyright www.f-road.com.cn Corporation 2013 
  * @author: 刘丽 liuli@f-road.com.cn
  * @time: 2013-1-10 上午10:46:40
 */
public class SendPointsState {
	public static final String to_pay="01";//待下推， 推给积分平台
	public static final String do_points_success="04";//处理积分成功
	public static final String do_points_fail="05";//处理积分失败
}
