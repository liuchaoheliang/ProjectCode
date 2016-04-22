package com.froad.logic.impl;

import com.alibaba.fastjson.JSON;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.MerchantLogic;
import com.froad.po.Area;
import com.froad.thrift.client.ThriftClientProxyFactory;
import com.froad.thrift.service.OutletService;
import com.froad.thrift.vo.LocationVo;
import com.froad.thrift.vo.OutletDetailSimplePageVoRes;
import com.froad.thrift.vo.OutletDetailVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.QueryProductFilterVo;
import com.froad.util.Checker;
import com.froad.util.GoodsConstants;

public class MerchantLogicImpl implements MerchantLogic {

	/**
	 * 
	 * queryMerchantOutlets:(根据区id 定位的经纬度查询 商户门店信息， 每个商户下只有一条最优的门店信息).
	 *
	 * @author wangyan
	 * 2015-8-7 下午4:10:03
	 * @param filterVo
	 * @param pageVo
	 * @param skip
	 * @return
	 * @throws Exception
	 *
	 */
	@Override
	public OutletDetailSimplePageVoRes queryMerchantOutlets(QueryProductFilterVo filterVo, PageVo pageVo, int skip)
			throws Exception {
		//商户门店分页
    	PageVo outletPage = new PageVo();
        outletPage.setPageNumber(pageVo.getPageNumber());
        outletPage.setPageSize(pageVo.getPageSize());
        
        //商户门店查询条件组装
        OutletDetailVo outletDetailVo = new OutletDetailVo();
        if(Checker.isNotEmpty(filterVo.getClientId())){
        	outletDetailVo.setClientId(filterVo.getClientId());//客户端ID
        }
        outletDetailVo.setMerchantId(filterVo.getMerchantId());//商户ID
        //判断传过来的areaid是几级
        if(filterVo.getAreaId()>0){
            CommonLogic com = new CommonLogicImpl();
            Area area = com.findAreaById(filterVo.getAreaId());
            if(area!=null){
              //最后的节点
                int level = 0;
                if(area.getTreePath()!=null){
                    String[] areas = area.getTreePath().split(",");
                    level = areas.length;
                }
                if(level==2){//两级代表市
                    outletDetailVo.setParentAreaId(filterVo.getAreaId());//市级地区ID
                } else if(level==3){//三级代表区
                    outletDetailVo.setAreaId(filterVo.getAreaId());//地区ID
                } else {
                    outletDetailVo.setAreaId(filterVo.getAreaId());//地区ID
                }
            } else {
                outletDetailVo.setAreaId(filterVo.getAreaId());//地区ID
            }
        }
        if(filterVo.getLongitude()>0.0 || filterVo.getLatitude()>0.0){
            LocationVo location = new LocationVo();
            location.setLongitude(filterVo.getLongitude());//经度
            location.setLatitude(filterVo.getLatitude());//纬度
            outletDetailVo.setLocation(location);
        }
        
        try {
        	OutletService.Iface client = (OutletService.Iface) ThriftClientProxyFactory.createIface(OutletService.class, 
                    GoodsConstants.THRIFT_MERCHANT_HOST, Integer.valueOf(GoodsConstants.THRIFT_MERCHANT_PORT), 60000);
            
            LogCvt.debug("根据区 经纬度 查询商户门店信息 每个商户下只有一条门店信息,查询参数:outletPage:"+JSON.toJSONString(outletPage)
                    +",outletDetailVo:"+JSON.toJSONString(outletDetailVo)+",distance:"+filterVo.getDistance()+",skip:"+skip);
            
            OutletDetailSimplePageVoRes outletPageVo = client.getNearbyOutletByPage(outletPage, outletDetailVo, filterVo.getDistance(), skip);//查询商户门店信息
             
            return outletPageVo;
        }catch (Exception e) {
            LogCvt.error("根据区 经纬度 查询商户门店信息 每个商户下只有一条门店信息异常：" +e,e);
        }
		return null;
	}

}
