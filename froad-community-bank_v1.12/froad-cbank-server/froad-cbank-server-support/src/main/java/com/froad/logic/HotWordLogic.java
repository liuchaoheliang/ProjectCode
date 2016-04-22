package com.froad.logic;

import com.froad.db.mysql.bean.Page;
import com.froad.po.HotWord;
import com.froad.po.HotWordDetaill;

public interface HotWordLogic {
    /**
     * 分页查询 HotWord
     * @param page
     * @param HotWordDetaill
     * @return Page<HotWord>    结果集合 
     */
	public Page<HotWord> findHotWordByPage(Page<HotWord> page, HotWord hotWord,String orderSort,int index);


}
