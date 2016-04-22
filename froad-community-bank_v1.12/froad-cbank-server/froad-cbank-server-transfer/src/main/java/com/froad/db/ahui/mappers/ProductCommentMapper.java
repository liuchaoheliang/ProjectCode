package com.froad.db.ahui.mappers;

import java.util.List;

import com.froad.fft.persistent.entity.ProductComment;


public interface ProductCommentMapper {
	
	public List<ProductComment> selectAllProductComment();
	
	
    public abstract ProductComment selectProductCommentById(Long paramLong);
	

    public abstract List<ProductComment> selectByCondition(ProductComment paramProductComment);
    
    public List<ProductComment> selectProductCommentByProductId(Long id);
}

