package com.froad.process.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.ObjectUtils;

import com.froad.config.ProcessServiceConfig;
import com.froad.db.ahui.mappers.AreaMapper;
import com.froad.db.chonggou.entity.AreaCG;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.AreaMapperCG;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.db.chonggou.redis.SupportRedis;
import com.froad.enums.TransferTypeEnum;
import com.froad.fft.persistent.entity.Area;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Checker;
import com.froad.util.Constans;
import com.froad.util.JSonUtil;

public class AreaProcess extends AbstractProcess{

	private AreaMapper mapper;
	private AreaMapperCG cgMapper;
	private TransferMapper transferMapper;
	
	public AreaProcess(String name,ProcessServiceConfig config) {
		super(name,config);
	}

	@Override
	public void process() {
		boolean result = false;
		//安徽的实体类和Mapper
		mapper = ahSqlSession.getMapper(AreaMapper.class);
		List<Area> areas = new ArrayList<Area>();
		Area area = new Area();
		//重构的实体类和Mapper
		cgMapper =  sqlSession.getMapper(AreaMapperCG.class);
		//记录新旧id
		transferMapper = sqlSession.getMapper(TransferMapper.class);
		
		areas = mapper.findArea(area);
		if(areas ==null || areas.isEmpty()){
			LogCvt.error("未查询到地区信息");
			return;
		}
		LogCvt.info("原安徽表fft_area 共有"+areas.size()+"条数据需迁移");
		for(Area a : areas){
			AreaCG cgArea = new AreaCG();
			cgArea.setId(a.getId());
			cgArea.setClientId(Constans.clientId);
			cgArea.setName(a.getName());
			cgArea.setTreePath(a.getTreePath());
			cgArea.setTreePathName("");
			cgArea.setParentId(a.getParentId());
			
			cgArea.setAreaCode(a.getAreaCode());
			cgArea.setVagueLetter(a.getVagueLetter());
			cgArea.setIsEnable(true);
			result = addArea(cgArea);
			if(result){
				Transfer transfer = new Transfer();
				transfer.setOldId(String.valueOf(a.getId()));
				transfer.setNewId(String.valueOf(cgArea.getId()));
				transfer.setType(TransferTypeEnum.area_id);
				int i = transferMapper.insert(transfer);
				if(i!=-1){
					LogCvt.info("安徽表fft_area数据"+JSonUtil.toJSonString(a));
					LogCvt.info("重构表cb_area数据"+JSonUtil.toJSonString(cgArea));
					LogCvt.info("转移成功");
				}
			}else{
				LogCvt.info("转移失败");
				return;
			}
		}
		
		Map<Long, Long> areaInfo = getAreaInfo();
		
		for(Area a : areas){
			AreaCG cgArea = new AreaCG();
			cgArea.setId(areaInfo.get(a.getId()));
			cgArea.setName(a.getName());
			cgArea.setAreaCode(a.getAreaCode());
			cgArea.setVagueLetter(a.getVagueLetter());
			cgArea.setIsEnable(true);
			if(Checker.isEmpty(a.getParentId())){
				cgArea.setParentId(0l);
				cgArea.setTreePath(ObjectUtils.toString(cgArea.getId()));
				cgArea.setTreePathName(a.getName());
			}else{
				cgArea.setParentId(areaInfo.get(a.getParentId()));
				// 获取tree_path和tree_name	
				Map<Long, String> treePathInfo = combineTree(a, areaInfo);
				
				StringBuilder treeIds = new StringBuilder();
				StringBuilder treeName = new StringBuilder();
				Set<Long> set = treePathInfo.keySet();
				for(Long tree : set){
					treeIds.append(tree+",");
					treeName.append(treePathInfo.get(tree)+",");
				}
				cgArea.setTreePath(treeIds.toString().substring(0,treeIds.length()-1));
				cgArea.setTreePathName(treeName.toString().substring(0,treeName.length()-1));
			}
			
			result = cgMapper.updateArea(cgArea);
			result = SupportRedis.set_cbbank_area_client_id_area_id(cgArea);
			if(result){
				LogCvt.info("资源表cb_are数据更新结束");
			}
		}
	}
	
	
	private Map<Long, Long> getAreaInfo(){
		List<Transfer> list = transferMapper.queryGroupList(TransferTypeEnum.area_id);
		Map<Long, Long> info = new HashMap<Long, Long>();
		for (Transfer transfer : list) {
			info.put(Long.valueOf(transfer.getOldId()), Long.valueOf(transfer.getNewId()));
		}
		return info;
	}
	
	private Map<Long, String> combineTree(Area a, Map<Long, Long> areaInfo){
		Map<Long, String> info = new TreeMap<Long, String>();
		if(Checker.isNotEmpty(a)){
			if(Checker.isEmpty(a.getParentId())){
				Long newAreaId = areaInfo.get(a.getId());
				info.put(newAreaId, a.getName());
			}else{
				Area parent = mapper.findById(a.getParentId());
				info.putAll(combineTree(parent, areaInfo));
				info.put(areaInfo.get(a.getId()), a.getName());
			}
		}
		return info;
	}
	
	public boolean addArea(AreaCG cgArea){
		boolean result = false;
		long addresult = -1;
		AreaCG areacg = cgMapper.findAreaById(cgArea.getId());
		if(areacg != null){
			LogCvt.error("该地区信息已存在,ID:"+cgArea.getId()+",Name:"+cgArea.getName());
			return false;
		}
		
		//重构mysql数据库
		addresult = cgMapper.addArea(cgArea);
		if(addresult == 1){
			result = true;
		}
		//redis
//		result = SupportRedis.set_cbbank_area_client_id_area_id(cgArea);
		
		return result;
	}
	
	
}

