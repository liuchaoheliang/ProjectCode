package com.froad.CB.dao;

import java.util.List;

import com.froad.CB.po.Advert;

public interface AdvertDao {
    /**
     * 添加
     */
    Integer insert(Advert record);


    /**
     * 选择性更新
     */
    boolean updateByPrimaryKeySelective(Advert record);

    /**
     *通过ID查询
     */
    Advert selectByPrimaryKey(Integer id);

    /**
     *通过ID删除
     */
    boolean deleteByPrimaryKey(Integer id);
    
    /**
     *通过ID查询
     */
    List<Advert> selectAdverts(Advert queryCon);
    
    public Advert getAdvertByPager(Advert pager);
}