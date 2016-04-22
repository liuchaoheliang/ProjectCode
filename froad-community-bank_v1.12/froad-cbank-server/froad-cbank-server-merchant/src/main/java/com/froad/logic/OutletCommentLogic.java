/*   
* Copyright © 2008 F-Road All Rights Reserved.
*  
* This software is the confidential and proprietary information of   
* Founder. You shall not disclose such Confidential Information   
* and shall use it only in accordance with the terms of the agreements   
* you entered into with Founder.   
*   
*/

/**
 * 
 * @Title: OutletCommentLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月25日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.po.OutletComment;
import com.froad.po.OutletCommentAddRes;
import com.froad.po.OutletCommentLevelAmount;
import com.froad.po.Result;

/**
 * 
 * <p>@Title: OutletCommentLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月25日
 */
public interface OutletCommentLogic {


    /**
     * 增加 OutletComment
     * @param outletComment
     * @return OutletCommentAddRes
     */
	public OutletCommentAddRes addOutletComment(OutletComment outletComment);



    /**
     * 删除 OutletComment
     * @param outletComment
     * @return Result
     */
	public Result deleteOutletComment(String id);



    /**
     * 修改 OutletComment
     * @param outletComment
     * @return Result
     */
	public Result updateOutletComment(OutletComment outletComment);

	/**
     * 查询 OutletComment
     * @param String
     * @return OutletComment
     */
	public OutletComment findOutletCommentById(String id);

    /**
     * 查询 OutletComment
     * @param outletComment
     * @return List<OutletComment>    结果集合 
     */
	public List<OutletComment> findOutletComment(OutletComment outletComment);



    /**
     * 分页查询 OutletComment
     * @param page
     * @param outletComment
     * @return Page<OutletComment>    结果集合 
     */
	public Page<OutletComment> findOutletCommentByPage(Page<OutletComment> page, OutletComment outletComment);

	/**
     * 分页查询 OutletComment
     * @param OutletComment(+orgCode)
     * @return Page
     */
	public Page<OutletComment> findOutletCommentPageByOrgCode(Page<OutletComment> page, OutletComment outletComment);

	/**
     * 增加 评论回复
     * @param OutletComment
     * @return Result
     * 
     * @param outletComment
     */
    public Result addOutletCommentOfRecomment(OutletComment outletComment);
    
    /**
     * 门店评论数量查询
     * @param outletComment - clientId merchantId outletId
     * @return int
     * 
     * @param outletComment
     */
    public int getOutletCommentSum(OutletComment outletComment);
    
    /**
     * 门店评论级别数量查询
     * @param outletComment - merchantId outletId
     * @return OutletCommentLevelAmount
     * 
     * @param outletComment
     */
    public OutletCommentLevelAmount getOutletCommentLevelAmount(OutletComment outletComment);

    /**
     * 商户评论级别数量查询
     * @param merchantId
     * @return list<OutletCommentLevelAmount>
     * 
     * @param merchantId
     */
    public List<OutletCommentLevelAmount> getMerchantCommentLevelAmount(String merchantId);
    
    /**
     * 是否存某会员在某天针对某门店的评论
     * @return bool
     * 
     * @param memberCode
     * @param outletId
     * @param time
     */
    public boolean isExistComment(String memberCode, String outletId, long time);
    
    /**
     * 是否存在某会员已经对门店进行了面对面的评论.
     * @param memberCode
     * @param outletId
     * @return
     */
    public boolean isExitsFaceToFaceComment(String memberCode,String outletId,String orderId);
}