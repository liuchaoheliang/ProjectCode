package com.froad.CB.dao.activity.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.froad.CB.dao.activity.SpringFestivalCouponDao;
import com.froad.CB.dao.user.impl.UserDaoImpl;
import com.froad.CB.po.activity.SpringFestivalCoupon;

public class SpringFestivalCouponDaoImpl implements SpringFestivalCouponDao {
	private SqlMapClientTemplate sqlMapClientTemplate;
	private static Logger logger = Logger.getLogger(SpringFestivalCouponDaoImpl.class);
	
	
	@Override
	public SpringFestivalCoupon getById(String ID) {
			return (SpringFestivalCoupon) sqlMapClientTemplate.queryForObject("springFestivalCoupon.getById",ID);	
	}

	@Override
	public boolean add(SpringFestivalCoupon springFestivalCoupon) {	
		 if(sqlMapClientTemplate.insert("springFestivalCoupon.add", springFestivalCoupon) != null){
			 return true;
		 }else{
			 logger.info("插入数据失败");
			 return false;
		 }
	}

	@Override
	public boolean updateById(SpringFestivalCoupon springFestivalCoupon) {
		int n=0;
		n=sqlMapClientTemplate.update("springFestivalCoupon.updateById", springFestivalCoupon);
		if(n>0){
			return true;
		}else{			
			return false;
		}
	}

	@Override
	public List<SpringFestivalCoupon> getSpringFestivalCouponByCndition(
			SpringFestivalCoupon springFestivalCoupon) {
		return sqlMapClientTemplate.queryForList("springFestivalCoupon.getSpringFestivalCouponByCndition", springFestivalCoupon);
	}

	@Override
	public SpringFestivalCoupon getSpringFestivalCouponByPager(
			SpringFestivalCoupon springFestivalCoupon) {
			int PagerCount=(Integer) sqlMapClientTemplate.queryForObject("springFestivalCoupon.getSpringFestivalCouponByPagerCount", springFestivalCoupon);
			List<SpringFestivalCoupon> list=sqlMapClientTemplate.queryForList("springFestivalCoupon.getSpringFestivalCouponByPager", springFestivalCoupon);
			springFestivalCoupon.setPageCount(PagerCount);
			springFestivalCoupon.setList(list);
			return springFestivalCoupon;
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

}
