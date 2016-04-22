package com.froad.cbank.coremodule.normal.boss.support.area;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.type.ArrayUtil;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.area.AreaPojo;
import com.froad.thrift.service.AreaService;
import com.froad.thrift.vo.AreaVo;


	/**
	 * 类描述：地区相关接口
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2015 
	 * @author: 刘超 liuchao@f-road.com.cn
	 * @time: 2015年5月12日 下午4:28:04 
	 */

@Service
public class AreaSupport {
	
	@Resource
	private AreaService.Iface areaService;
	
	/**
	  * 方法描述：预售商品获取绑定的区域
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	 * @throws TException 
	  * @time: 2015年4月3日 上午10:02:07
	  */
	public HashMap<String, Object> getAreaList(String clientId ,Long parentId) throws TException{
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		AreaVo areaVo =new AreaVo(); 
		areaVo.setClientId(clientId);
		if(parentId!=null){
			areaVo.setParentId(parentId);
		}
		List<AreaVo> list=null;
		AreaPojo areaPojo=null;
		List<AreaPojo> alist=null;
		list=areaService.getArea(areaVo);
		if(list != null && list.size() !=0 ){
			alist=new ArrayList<AreaPojo>();
			for(AreaVo  temp : list){
					areaPojo=new AreaPojo();
					BeanUtils.copyProperties(areaPojo, temp);
					alist.add(areaPojo);
			}
			resMap.put("resResult",alist);
		}
		return resMap;
	}
	
	/**
	 * 根据客户端ID联动获取地市级地区列表
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年10月10日 上午11:04:17
	 * @param clientId
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> getAreaListByClientId(String clientId) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<AreaPojo> areaList = new ArrayList<AreaPojo>();
		AreaPojo temp = null;
		AreaVo req = new AreaVo();
		AreaVo req2 = new AreaVo();
		//调用SERVER端接口
		req.setClientId(clientId);
		req.setParentId(0);
		List<AreaVo> resp = areaService.getArea(req);
		if(!ArrayUtil.empty(resp)) {
			req2.setParentId(resp.get(0).getId());
			List<AreaVo> res = areaService.getArea(req2);//查询地级市
			if(!ArrayUtil.empty(res)) {
				if("chongqing".equals(clientId) || "shanghai".equals(clientId) || "beijing".equals(clientId) || "tianjin".equals(clientId)) {
					req2.setParentId(res.get(0).getId());
					res = areaService.getArea(req2);
				}
				for(AreaVo tmp : res) {
					temp = new AreaPojo();
					BeanUtils.copyProperties(temp, tmp);
					areaList.add(temp);
				}
				map.put("areaList", areaList);
			} else {
				throw new BossException("暂无数据");
			}
		} else {
			throw new BossException("暂无数据");
		}
		return map;
	}
}
