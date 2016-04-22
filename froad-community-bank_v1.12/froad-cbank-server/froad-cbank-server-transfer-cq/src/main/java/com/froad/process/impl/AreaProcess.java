package com.froad.process.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.ObjectUtils;

import com.froad.cbank.persistent.entity.Area;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.chonggou.entity.AreaCG;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.AreaMapperCG;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.db.chonggou.redis.SupportRedis;
import com.froad.db.chongqing.mappers.AreaMapper;
import com.froad.enums.TransferTypeEnum;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Checker;
import com.froad.util.Constans;
import com.froad.util.JSonUtil;

public class AreaProcess extends AbstractProcess{

	private AreaMapper mapper;
	private AreaMapperCG cgMapper;
	private TransferMapper transferMapper;
	private Map<String,Object> areaCGMap=new HashMap<String,Object>();
	private boolean hasArea;
	private Map<String,String> AreaNameMap;
	//private String CLIENTID="chongqing";
	public AreaProcess(String name,ProcessServiceConfig config) {
		super(name,config);
	}
	@Override
	public void before() {
		LogCvt.info("删除Area开始.........");
		transferMapper = sqlSession.getMapper(TransferMapper.class);
		transferMapper.deleteFormType(TransferTypeEnum.area_id.getCode());
	}
	@Override
	public void process() {
		boolean result = false;
		//安徽的实体类和Mapper
		mapper = cqSqlSession.getMapper(AreaMapper.class);
		cgMapper =  sqlSession.getMapper(AreaMapperCG.class);
		List<Area> areas = new ArrayList<Area>();
		Area area = new Area();
		
		
		
		//由于生产已经有社区银行重庆的数据,所以只要中间表关系对应上就可以了
		areas = mapper.findArea(area);
		LogCvt.info("原重庆表cb_area 共有"+areas.size()+"条数据");
		if(areas ==null || areas.isEmpty()){
			LogCvt.error("未查询到重庆地区信息");
			return;
		}
		//内存加载
		LoadMap();
		if(hasArea){
			for(Area a : areas){
				AreaCG areacg=insertTransfer(a);
				//放入redis
				if(areacg!=null){
			     	SupportRedis.set_cbbank_area_client_id_area_id(areacg);
				}
				
			}
		}
		
		
		
//		for(Area a : areas){
//			AreaCG cgArea = new AreaCG();
//			cgArea.setClientId(Constans.clientId);
//			cgArea.setId(a.getId());
//			cgArea.setName(a.getName());
//			cgArea.setTreePath(a.getTreePath());
//			cgArea.setTreePathName("");
//			cgArea.setParentId(a.getParentId());
//			cgArea.setAreaCode(a.getAreaCode());
//			cgArea.setVagueLetter(a.getVagueLetter());
//			cgArea.setIsEnable(true);
//			result = addArea(cgArea);
//			if(result){
//				Transfer transfer = new Transfer();
//				transfer.setOldId(String.valueOf(a.getId()));
//				transfer.setNewId(String.valueOf(cgArea.getId()));
//				transfer.setType(TransferTypeEnum.area_id);
//				int i = transferMapper.insert(transfer);
//				if(i!=-1){
//					LogCvt.info("重庆cb_area数据"+JSonUtil.toJSonString(a));
//					LogCvt.info("重构表cb_area数据"+JSonUtil.toJSonString(cgArea));
//					LogCvt.info("转移成功");
//				}
//			}else{
//				LogCvt.info("转移失败");
//				return;
//			}
//		}
//		
//		Map<Long, Long> areaInfo = getAreaInfo();
//		
//		for(Area a : areas){
//			AreaCG cgArea = new AreaCG();
//			cgArea.setId(areaInfo.get(a.getId()));
//			cgArea.setName(a.getName());
//			cgArea.setAreaCode(a.getAreaCode());
//			cgArea.setVagueLetter(a.getVagueLetter());
//			cgArea.setIsEnable(true);
//			if(Checker.isEmpty(a.getParentId())){
//				cgArea.setParentId(0l);
//				cgArea.setTreePath(ObjectUtils.toString(cgArea.getId()));
//				cgArea.setTreePathName(a.getName());
//			}else{
//				cgArea.setParentId(areaInfo.get(a.getParentId()));
//				// 获取tree_path和tree_name	
//				Map<Long, String> treePathInfo = combineTree(a, areaInfo);
//				
//				StringBuilder treeIds = new StringBuilder();
//				StringBuilder treeName = new StringBuilder();
//				Set<Long> set = treePathInfo.keySet();
//				for(Long tree : set){
//					treeIds.append(tree+",");
//					treeName.append(treePathInfo.get(tree)+",");
//				}
//				cgArea.setTreePath(treeIds.toString().substring(0,treeIds.length()-1));
//				cgArea.setTreePathName(treeName.toString().substring(0,treeName.length()-1));
//			}
//			
//			result = cgMapper.updateArea(cgArea);
//			result = SupportRedis.set_cbbank_area_client_id_area_id(cgArea);
//			if(result){
//				LogCvt.info("资源表cb_are数据更新结束");
//			}
//		}
	}
	private void LoadMap(){
		List<AreaCG> areaCGs=new ArrayList<AreaCG>();
		hasArea=true;
		AreaCG areacg=new AreaCG();
		areacg.setClientId(Constans.clientId);
		areaCGs=cgMapper.findArea(areacg);
		LogCvt.info("新重庆表cb_area 共有"+areaCGs.size()+"条数据");
		if(areaCGs==null||areaCGs.isEmpty()){
			LogCvt.error("未查询到新社区地区信息");
			hasArea=false;
			return;
		}
       for(AreaCG area:areaCGs){
    	   areaCGMap.put(area.getName(),area);
       }
       //部分名称对应不上作兼容
       AreaNameMap=new HashMap<String,String>();
		String[] OrginName={"重庆市","石柱县","彭水县","酉阳县","秀山县","两江新区","西永镇","营业部","万盛区"};
		String[] newName={"重庆","石柱土家族自治县","彭水苗族土家族自治县","酉阳土家族苗族自治县","秀山土家族苗族自治县","两江新区","西永镇","营业部","万盛区"};
		for(int i=0;i<OrginName.length;i++){
			AreaNameMap.put(OrginName[i], newName[i]);
		}
		
	}
	private AreaCG insertTransfer(Area a){
		AreaCG areacg=checkArea(a);
		if(areacg!=null){
			Transfer transfer = new Transfer();
			transfer.setOldId(String.valueOf(a.getId()));
			transfer.setNewId(String.valueOf(areacg.getId()));
			transfer.setType(TransferTypeEnum.area_id);
			int i = transferMapper.insert(transfer);
			if(i!=-1){
				LogCvt.info("重庆cb_area数据"+JSonUtil.toJSonString(a));
				LogCvt.info("重构表cb_area数据"+JSonUtil.toJSonString(areacg));
				LogCvt.info("转移成功");
				return areacg;
			}
		}else{
			LogCvt.error("新重庆cb_area无数据"+a.getName()+",Id="+a.getId());
		}
		return null;
	}
	/**
	 * 做兼容
	  * @Title: checkArea
	  * @Description: TODO
	  * @author: Yaren Liang 2015年7月22日
	  * @modify: Yaren Liang 2015年7月22日
	  * @param @param area
	  * @param @return    
	  * @return AreaCG    
	  * @throws
	 */
	private AreaCG checkArea(Area area){
		AreaCG areaCG=null;
		if(AreaNameMap.containsKey(area.getName())){
			 areaCG=(AreaCG)areaCGMap.get(AreaNameMap.get(area.getName()));
		}else{
			 areaCG=(AreaCG)areaCGMap.get(area.getName());
		}
		return areaCG;
        		
		
	
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

