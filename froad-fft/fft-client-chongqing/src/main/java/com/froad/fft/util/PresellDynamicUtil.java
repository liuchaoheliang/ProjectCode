package com.froad.fft.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.froad.fft.common.Constants;

public class PresellDynamicUtil {

	//用户名
	private static final String []  entrystr =new String[]{"andy91310","anthony","cai9085","chen","chengzi911","elaine","fealer","feng","fred","fulong" +
		"","guoming","hewle","huangjing","jeremy_ljw","jianhuano1","jiaoshengan","jineliu","kevin","konglj","lilianzhi" +
		"","liqiaopeng","liuchaoheliang","liuwei","lvyanfei","mxzcluoli","nancyclf","rixk1943","sunsimin","tian666tian666" +
		"","tonghua","wangdonghu","wangruchuan","xuying","youngst2010","yunlong167167","zhangxuefeng","zhaojianguo","zhoujing"};

	/**
	 * <p>
	 * 生成预售动态
	 * </p>
	 * 
	 * @author larry
	 * @datetime 2014-4-16下午07:17:35
	 * @return Set<String[]>
	 * @throws
	 * @example<br/>
	 * 
	 */
	public static List<String[]> getPresellDynamic(Date startTime,
			Date endTime, Integer maxBuyNum) {
		Random random = new Random();
		List<String[]> entrys = new ArrayList<String[]>();
		Set<String> names = new HashSet<String>();
		// 预售动态数量
		Integer dynamicNum = Constants.PRESELL_DYNAMIC_NUM > entrystr.length ? entrystr
				.length : Constants.PRESELL_DYNAMIC_NUM;
		if (maxBuyNum == 0) {// 无预售
			return null;
		}
		while (true) {
			if (entrys.size() == dynamicNum) {
				break;
			}
			// 姓名
			String name =entrystr[random
					.nextInt(entrystr.length)];
			if(name.length()<=5){
				name=name.concat("***");
			}else{
				name=name.substring(0,name.length()-3);
				name=name.concat("***");
			}
			if (names.contains(name)) {// 名字重复，重新生成
				continue;
			}
			entrys.add(new String[] {name, "", "" });
			names.add(name);
		}
		names = null;//清空
		// 购买时间（指定天数内）
		SimpleDateFormat format = new SimpleDateFormat("MM月dd日 HH:mm");
		Date now = new Date();
		Long min = 60 * 1000L;// 随机时间精确到分。
		int dy = 0;
		if (!now.after(startTime)) {// 预售未开始，没有预售动态
			return null;
		} else if (now.after(startTime) && !now.after(endTime)) {// 预售期内
			// 预售期内,预售动态中的时间范围：当前时间-预售开始
			dy = Integer.parseInt(String.valueOf((now.getTime() - startTime
					.getTime()) / min));
		} else if (now.after(endTime)) {// 预售结束
			// 预售结束，预售动态中的时间范围：预售开始-预售结束
			dy = Integer.parseInt(String.valueOf((endTime.getTime() - startTime
					.getTime()) / min));
			now = endTime;
		}
		dy = dy == 0 ? 1 : dy;// dy=0 ，预售开始时间不足1分
		int size = entrys.size();
		// 生成时间
		String[] dates = new String[size];
		for (int i = 0; i < size; i++) {
			Long calTime = now.getTime() - min * random.nextInt(dy);// 0<=rand<dy
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date(calTime));
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			//时间在9:00-23:59:59
			if(hour<9){//如果小时是在九点之前，则补足到9点
				cal.set(Calendar.HOUR_OF_DAY, 9);
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(new Date());
				int minute = cal2.get(Calendar.MINUTE);
				if(minute<cal.get(Calendar.MINUTE)){//生成的时间中“分”，大于当前分。
					cal.set(Calendar.MINUTE, random.nextInt(minute));
				}
			}
			dates[i] = format.format(cal.getTime());
		}
		// 倒序显示
		Arrays.sort(dates);
		for (int i = 0; i < size; i++) {
			entrys.get(size - (i + 1))[1] = dates[i];
		}
		// // 假购买数量（理论上来说，相加应该等于预售数量，实际....）
		// for (int i = 0 ;i<size;i++) {
		// entrys.get(i)[2] = String.valueOf(random.nextInt(size)+1);
		// }
		return entrys;
	}
}
