package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.alibaba.fastjson.JSON;
import com.froad.db.mysql.bean.Page;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.HotWordLogic;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.logic.impl.HotWordLogicImpl;
import com.froad.po.Area;
import com.froad.po.HotWord;
import com.froad.po.HotWordDetaill;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.HotWordService;
import com.froad.thrift.vo.HotWordPageRes;
import com.froad.thrift.vo.HotWordVo;
import com.froad.thrift.vo.PageVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;

public class HotWordServiceImpl extends BizMonitorBaseService implements
		HotWordService.Iface {
	private HotWordLogic hotWordLogic = new HotWordLogicImpl();

	public HotWordServiceImpl() {
	}

	public HotWordServiceImpl(String name, String version) {
		super(name, version);
	}

	@Override
	public HotWordPageRes searchHotWord(PageVo pageVo, HotWordVo hotWordVo)
			throws TException {
		LogCvt.info(JSON.toJSONString(hotWordVo));
		String orderSort=null;
		int index=1;
		Page<HotWord> page = (Page) BeanUtil.copyProperties(Page.class, pageVo);
		HotWord hot = new HotWord();
		if (hotWordVo != null) {
			// 根据查询条件传进来的areaid判断是市级地区id还是区级地区id
			Long provinceId=null;
			Long cityId = null;// 市级地区id
			Long countyId = null;// 区级地区id
			if (hotWordVo.getAreaId() > 0) {
				CommonLogic comLogic = new CommonLogicImpl();
				Area area = comLogic.findAreaById(hotWordVo.getAreaId());
				if (area != null) {
					String areaTreePath = area.getTreePath();
					if (Checker.isNotEmpty(areaTreePath)) {
						String[] treePtah = areaTreePath.split(",");
						if (treePtah.length == 1) {
							provinceId = hotWordVo.getAreaId();
							hot.setProvinceAreaId(provinceId);
						}else if(treePtah.length == 2) {// areaId为市
							cityId = hotWordVo.getAreaId();
							hot.setCityAreaId(cityId);
						}else if(treePtah.length == 3) {// areaId为市
							countyId = hotWordVo.getAreaId();
							hot.setCountyAreaId(countyId);
						}
					}
				}
			}
			hot.setAreaId(hotWordVo.getAreaId());
			hot.setType(hotWordVo.getType());
			hot.setCategoryType(hotWordVo.getCategoryType());
			hot.setClientId(hotWordVo.getClientId());
		}
		page = hotWordLogic.findHotWordByPage(page, hot, orderSort,index);
		HotWordPageRes hotWordPageVo = new HotWordPageRes();
		List<HotWordVo> hotWordVoList = new ArrayList<HotWordVo>();
		for (HotWord po : page.getResultsContent()) {
			HotWordVo vo = (HotWordVo) BeanUtil.copyProperties(HotWordVo.class,po);
			hotWordVoList.add(vo);
		}
		pageVo = (PageVo) BeanUtil.copyProperties(PageVo.class, page);
		hotWordPageVo.setPage(pageVo);
		hotWordPageVo.setHotList(hotWordVoList);
		return hotWordPageVo;
	}

}
