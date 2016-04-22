package com.froad.cbank.coremodule.normal.boss.support.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.normal.boss.pojo.prodiver.ProviderListRes;
import com.froad.cbank.coremodule.normal.boss.utils.DateUtil;
import com.froad.thrift.service.ProviderService;
import com.froad.thrift.vo.provider.ProviderListVo;
import com.froad.thrift.vo.provider.ProviderVo;

/**
 * 
 * @ClassName: ProviderSupport
 * @author chenzhangwei@f-road.com.cn
 * @createTime 2015年12月2日 下午5:18:54 
 * @desc <p>供应商support</p>
 */
@Service
public class ProviderSupport {
	
	@Resource
	ProviderService.Iface providerService;
	
	/**
	 * 
	 * <p>Title:供应商列表查询 </p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2015年12月2日 下午5:25:40 
	 * @return
	 * @throws TException 
	 */
	public HashMap<String,Object> findAllProvider() throws TException{
		
		HashMap<String,Object> map =new HashMap<String,Object>();
		
		ProviderListRes res;
		List<ProviderListRes> ls=new ArrayList<ProviderListRes>();
		//执行查询
		ProviderListVo temp=providerService.findAllProviders();
		if(temp.providerList.size()>0){
			for (ProviderVo vo : temp.getProviderList()) {
				//实体拷贝
				res=new ProviderListRes();
				BeanUtils.copyProperties(res, vo);
				res.setCreateTime(DateUtil.longToString(vo.getCreateTime()));
				res.setUpdateTime(DateUtil.longToString(vo.getUpdateTime()));
				ls.add(res);
			}
		}
		map.put("list", ls);
		return map;
	}
}
