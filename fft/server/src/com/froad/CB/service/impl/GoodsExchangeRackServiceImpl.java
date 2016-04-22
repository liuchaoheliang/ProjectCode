package com.froad.CB.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import org.apache.log4j.Logger;
import com.froad.CB.dao.GoodsExchangeRackDao;
import com.froad.CB.dao.PresentPointRuleDao;
import com.froad.CB.po.GoodsExchangeRack;
import com.froad.CB.po.PresentPointRule;
import com.froad.CB.service.GoodsExchangeRackService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-1  
 * @version 1.0
 */
@WebService(endpointInterface="com.froad.CB.service.GoodsExchangeRackService")
public class GoodsExchangeRackServiceImpl implements GoodsExchangeRackService {
	private static final Logger logger = Logger.getLogger(GoodsExchangeRackServiceImpl.class);
	private GoodsExchangeRackDao goodsExchangeRackDao;
	private PresentPointRuleDao presentPointRuleDao;
	private static final SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	

	
	@Override
	public Integer addGoodsExchangeRack(GoodsExchangeRack goodsExchangeRack) {
		if(goodsExchangeRack==null){
			logger.info("增加商品兑换架参数不能为空！");
			return null;
		}
		return goodsExchangeRackDao.insert(goodsExchangeRack);
	}

	@Override
	public Integer updateById(GoodsExchangeRack goodsExchangeRack) {
		if(goodsExchangeRack==null){
			logger.info("更新商品兑换架参数不能为空！");
			return null;
		}
		return goodsExchangeRackDao.updateById(goodsExchangeRack);
	}

	@Override
	public GoodsExchangeRack selectById(Integer id) {
		if(id==null){
			logger.info("查询商品兑换架参数id不能为空！");
			return null;
		}
		return goodsExchangeRackDao.selectByPrimaryKey(id);
	}

	@Override
	public Integer deleteById(String id) {
		if(id==null){
			logger.info("删除商品兑换架参数id不能为空！");
			return null;
		}
		return goodsExchangeRackDao.deleteByPrimaryKey(id);
	}

	@Override
	public Integer deleteStateById(String id) {
		if(id==null){
			logger.info("删除商品兑换架参数id不能为空！");
			return null;
		}
		return goodsExchangeRackDao.deleteStateByPrimaryKey(id);
	}

	@Override
	public List<GoodsExchangeRack> getGoodsExchangeRackByGoodsId(String goodsId) {
		if(goodsId==null){
			logger.info("查询商品兑换架参数goodsId不能为空！");
			return null;
		}
		return goodsExchangeRackDao.getGoodsExchangeRackByGoodsId(goodsId);
	}

	@Override
	public List<GoodsExchangeRack> getGoodsExchangeRack(
			GoodsExchangeRack goodsExchangeRack) {
		if(goodsExchangeRack==null){
			logger.info("查询商品兑换架参数不能为空！");
			return null;
		}
		return goodsExchangeRackDao.getGoodsExchangeRack(goodsExchangeRack);
	}

	@Override
	public GoodsExchangeRack getGoodsExchangeRackListByPager(
			GoodsExchangeRack goodsExchangeRack) {
		if(goodsExchangeRack==null){
			logger.info("分页查询商品兑换架参数不能为空！");
			return null;
		}
		GoodsExchangeRack exchange=goodsExchangeRackDao.getGoodsExchangeRackListByPager(goodsExchangeRack);
		List<?> list=exchange.getList();
		if(list!=null){
			GoodsExchangeRack tmp = null;
			for (int i = 0; i < list.size(); i++) {
				tmp=(GoodsExchangeRack)list.get(i);
				if(tmp.getIsPresentPoints()==1){
					List<PresentPointRule> ruleList=presentPointRuleDao.getByRackId(tmp.getId().toString());
					if(!hasRule(ruleList)){
						tmp.setIsPresentPoints(0);
					}
				}
			}
		}
		return exchange;
	}
	
	private boolean hasRule(List<PresentPointRule> ruleList){
		if(ruleList==null||ruleList.size()==0){
			return false;
		}
		PresentPointRule rule=null;
		for (int i = 0; i < ruleList.size(); i++) {
			rule=ruleList.get(i);
			if(isRange(rule.getActiveTime(), rule.getExpireTime())){
				return true;
			}
		}
		return false;
	}
	
	private boolean isRange(String begin,String end){
		try {
			Date beginDate=dateFormat.parse(begin);
			Date endDate=dateFormat.parse(end);
			Date now = new Date();
			if(now.before(beginDate)||now.after(endDate)){
				return false;
			}
			return true;
		} catch (ParseException e) {
			logger.error("日期格式不正确",e);
			return false;
		}
	}

	public GoodsExchangeRackDao getGoodsExchangeRackDao() {
		return goodsExchangeRackDao;
	}

	public void setGoodsExchangeRackDao(GoodsExchangeRackDao goodsExchangeRackDao) {
		this.goodsExchangeRackDao = goodsExchangeRackDao;
	}
	

	public PresentPointRuleDao getPresentPointRuleDao() {
		return presentPointRuleDao;
	}

	public void setPresentPointRuleDao(PresentPointRuleDao presentPointRuleDao) {
		this.presentPointRuleDao = presentPointRuleDao;
	}

	@Override
	public List<GoodsExchangeRack> getIndexGoodsRack() {
		return goodsExchangeRackDao.getIndexGoodsRack();
	}

	@Override
	public GoodsExchangeRack getByPagerForMgmt(
			GoodsExchangeRack goodsExchangeRack) {
		if(goodsExchangeRack==null){
			logger.info("分页查询商品兑换架参数不能为空！");
			return null;
		}
		return goodsExchangeRackDao.getGoodsExchangeRackListByPager(goodsExchangeRack);
	}

}
