package com.froad.CB.common;

import org.apache.log4j.Logger;

public class AnnouncementCommand {
	public static Logger logger = Logger.getLogger(AnnouncementCommand.class);	
	public static final String TYPE_SINGLE = "1";//查询单个公告
	public static final String TYPE_BATCH = "2";//查询公告列表
	public static final String MALL_INDEX_NOTICE = "0";//商城首页公告
	public static final String FBU_INDEX_NOTICE = "1";//珠海商盟首页公告
	public static final String FBU_PREFERENTIAL_NOTICE = "2";//商盟打折优惠公告
	public static final String FBU_FFTONG_NOTICE = "3";//分分通公告
	public static final String FBU_TUANGOU_NOTICE = "4";//团购公告
	public static final String FBU_HOT_NOTICE = "5";//热卖公告

	//类型模块goodsID。其中高品质商品，农特产品 使用product_type表中goodsType字段。其中， 100 为农特产品，200为高品质；300为活动商品。首页type为 0
	
}
