package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.AuditFilter;
import com.froad.po.ProductFilter;
import com.froad.po.ProductOperateLog;
import com.froad.po.ProductTemp;

public interface ProductOperateLogMapper {
    /**
     * 新增商品日志
     * @param page
     * @param ProductFilter
     * @return List<ProductListInfo>
     */
    public Long addProductOperatorLog(ProductOperateLog log);
}
