package com.froad.db.chongqing.mappers;

import java.util.List;

import com.froad.cbank.persistent.entity.ProductComment;
import com.froad.cbank.persistent.entity.base.PageEntity;

/**
 *  商品评价mybatis接口
  * @ClassName: ProductCommentMapper
  * @Description: TODO
  * @author share 2015年01月03日
  * @modify share 2015年01月03日
 */
public interface ProductCommentMapper {
	/**
	 * 
	  * @Title: selectAllProductComment
	  * @Description: TODO
	  * @author: Yaren Liang 2015年6月26日
	  * @modify: Yaren Liang 2015年6月26日
	  * @param @return    
	  * @return List<ProductComment>    
	  * @throws
	 */
public List<ProductComment> selectAllProductComment();
	
	
    public abstract ProductComment selectProductCommentById(Long paramLong);
	

    public abstract List<ProductComment> selectByCondition(ProductComment paramProductComment);
    
    public List<ProductComment> selectProductCommentByProductId(Long id);
    
    public Double selectProductCommentByProductIdOnInt(Long id);

}
