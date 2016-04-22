package com.froad.fft.persistent.api.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.TagMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.Tag;

import java.util.List;

public class TagMapperImpl implements TagMapper {

    @Resource
    private TagMapper tagMapper;

    @Override
    public Long saveTag(Tag tag)
    {
        tagMapper.saveTag(tag);
        return tag.getId();
    }

    @Override
    public Boolean updateTagById(Tag tag)
    {

        return tagMapper.updateTagById(tag);
    }

    @Override
    public Tag selectTagById(Long id)
    {
        return tagMapper.selectTagById(id);
    }

    public List<Tag> selectTagByPage(Page page)
    {
        return tagMapper.selectTagByPage(page);
    }

    public Integer selectTagByPageCount(Page page)
    {
        return tagMapper.selectTagByPageCount(page);
    }

}
