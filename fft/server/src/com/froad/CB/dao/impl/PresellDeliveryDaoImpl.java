package com.froad.CB.dao.impl;

import java.util.*;

import com.froad.CB.po.*;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.PresellDeliveryDao;

public class PresellDeliveryDaoImpl implements PresellDeliveryDao
{

    private SqlMapClientTemplate sqlMapClientTemplate;

    @Override
    public Integer add(PresellDelivery presellDelivery)
    {
        return (Integer) sqlMapClientTemplate.insert("presellDelivery.insert", presellDelivery);
    }

    @Override
    public Integer updateById(PresellDelivery presellDelivery)
    {
        return sqlMapClientTemplate.update("presellDelivery.updateById", presellDelivery);
    }

    @Override
    public PresellDelivery getById(Integer id)
    {
        return (PresellDelivery) sqlMapClientTemplate.queryForObject("presellDelivery.getById", id);
    }

    @Override
    public List<PresellDelivery> getByConditions(PresellDelivery presellDelivery)
    {
        return sqlMapClientTemplate.queryForList("presellDelivery.getByConditions", presellDelivery);
    }

    public SqlMapClientTemplate getSqlMapClientTemplate()
    {
        return sqlMapClientTemplate;
    }

    public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate)
    {
        this.sqlMapClientTemplate = sqlMapClientTemplate;
    }

    @Override
    public PresellDelivery getByPager(PresellDelivery presellDelivery)
    {
        int count = (Integer) sqlMapClientTemplate.queryForObject("presellDelivery.getByPagerCount", presellDelivery);
        List list = sqlMapClientTemplate.queryForList("presellDelivery.getByPager", presellDelivery);
        presellDelivery.setTotalCount(count);
        presellDelivery.setList(list);
        return presellDelivery;
    }

    @Override
    public List<PresellDelivery> getByRackId(Integer rackId)
    {
        return sqlMapClientTemplate.queryForList("presellDelivery.getByRackId", rackId);
    }

    @Override
    public PresellDelivery queryByTransId(Integer transId)
    {
        return (PresellDelivery) sqlMapClientTemplate.queryForObject("presellDelivery.queryByTransId", transId);
    }



}
