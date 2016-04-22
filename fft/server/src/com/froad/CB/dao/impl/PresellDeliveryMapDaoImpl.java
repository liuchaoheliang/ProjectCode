package com.froad.CB.dao.impl;

import java.util.*;

import com.froad.CB.po.*;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.PresellDeliveryMapDao;

public class PresellDeliveryMapDaoImpl implements PresellDeliveryMapDao
{

    private SqlMapClientTemplate sqlMapClientTemplate;

    @Override
    public Integer add(PresellDeliveryMap presellDeliveryMap)
    {
        return (Integer) sqlMapClientTemplate.insert("presellDeliveryMap.insert", presellDeliveryMap);
    }

    @Override
    public Integer updateById(PresellDeliveryMap presellDeliveryMap)
    {
        return sqlMapClientTemplate.update("presellDeliveryMap.updateById", presellDeliveryMap);
    }

    @Override
    public PresellDeliveryMap getById(Integer id)
    {
        return (PresellDeliveryMap) sqlMapClientTemplate.queryForObject("presellDeliveryMap.getById", id);
    }

    @Override
    public List<PresellDeliveryMap> getByConditions(PresellDeliveryMap presellDeliveryMap)
    {
        return sqlMapClientTemplate.queryForList("presellDeliveryMap.getByConditions", presellDeliveryMap);
    }

    @Override
    public Integer deleteByRackId(Integer RackId)
    {
        return sqlMapClientTemplate.delete("presellDeliveryMap.deleteByRackId", RackId);
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
    public PresellDeliveryMap getBypager(PresellDeliveryMap presellDeliveryMap)
    {
        Integer count = (Integer) sqlMapClientTemplate.queryForObject("presellDeliveryMap.getByPagerCount", presellDeliveryMap);
        List<PresellDeliveryMap> list = sqlMapClientTemplate.queryForList("presellDeliveryMap.getByPager", presellDeliveryMap);
        presellDeliveryMap.setTotalCount(count);
        presellDeliveryMap.setList(list);
        return presellDeliveryMap;
    }

}
