package com.froad.cbank.coremodule.module.normal.merchant.auto;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.ImageDel_Req;
import com.froad.cbank.coremodule.module.normal.merchant.service.Image_Service;
import com.froad.cbank.coremodule.module.normal.merchant.utils.RoleConstant;
import com.froad.cbank.expand.redis.RedisCommon;

/**
 * 定时任务
 * @ClassName AutoBusinessService
 * @author zxl
 * @date 2015年4月29日 下午2:25:15
 */
@Component
public class AutoBusinessService {
	@Resource
	Image_Service image_Service;
	
	/**
	 * 自动更新权限
	 * @tilte autoReloadRole
	 * @author zxl
	 * @date 2015年4月29日 下午2:24:58
	 */
	@Scheduled(cron="0 0 0/1 * * ?")
	public void autoReloadRole(){
		RoleConstant.reload();
	}
	
	/**
	 * 说明   定时删除redis的图片数据
	 * 每天凌晨两点
	 * 创建日期  2015年12月8日  下午3:35:15
	 * 作者  artPing
	 * 参数
	 */
	 @Scheduled(cron="0/5 * * * * ?") 
	public void imgDel(){
		String key="cbbank:undelete:images";
		//获取redis的图片
		try {
			RedisCommon redis=new RedisCommon();
			Set<String> set=redis.getSet(key);
			
			//删除七牛图片
			if(set!=null&&set.size()>0){
				List<ImageDel_Req> imgList=new ArrayList<ImageDel_Req>();
				for(String source:set){
					ImageDel_Req req=new ImageDel_Req();
					req.setFileKey(source);
					req.setFileHash("");
					imgList.add(req);
				}
				String json=image_Service.imgDel(imgList);
				LogCvt.info("删除redis缓存的图片------》"+json);

				//删除redis缓存
				String[] strArray = new String[set.toArray().length];
				String[] toArray = (String[]) set.toArray(strArray);
				redis.srem(key,toArray);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogCvt.error("获取redis缓存的图片------》"+e);
		}
	}

}
