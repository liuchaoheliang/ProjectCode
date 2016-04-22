package com.froad.fft.service.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.bean.page.Page;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.persistent.api.TagMapper;
import com.froad.fft.persistent.entity.Tag;
import com.froad.fft.service.TagService;

@Service("tagServiceImpl")
public class TagServiceImpl implements TagService
{

    private static Logger logger = Logger.getLogger(TagServiceImpl.class);

    @Resource
    private TagMapper tagMapper;

    @Override
    public Long saveTag(Tag tag)
    {
        return tagMapper.saveTag(tag);
    }

    @Override
    public Boolean updateTagById(Tag tag)
    {
        if (tag.getId() == null)
        {
            logger.error("更新对象缺少必要Id字段值");
            return false;
        }
        return tagMapper.updateTagById(tag);
    }

    @Override
    public Tag selectTagById(Long id)
    {
        if (id == null)
        {
            logger.error("查询数据id为空");
            return null;
        }
        return tagMapper.selectTagById(id);
    }

    public Page findTagByPage(Page page)
    {
        if (page == null)
        {
            page = new Page();
        }
        page.setResultsContent(tagMapper.selectTagByPage(page));
        page.setTotalCount(tagMapper.selectTagByPageCount(page));
        return page;
    }

}
