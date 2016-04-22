/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入OutletCommentVo.thrift文件 */
include "OutletCommentVo.thrift"

/* 引入OutletCommentServiceVo.thrift文件 */
include "OutletCommentServiceVo.thrift"

namespace java com.froad.thrift.service


/**
 * OutletCommentService
 */
service OutletCommentService extends BizMonitor.BizMonitorService {

    /**
     * 增加 OutletComment
     * @param OutletComment
     * @return OutletCommentAddVoRes
     */
    OutletCommentServiceVo.OutletCommentAddVoRes addOutletComment(1:Common.OriginVo originVo, 2:OutletCommentVo.OutletCommentVo outletCommentVo);

    /**
     * 删除 OutletComment
     * @param OutletComment
     * @return ResultVo    
     */
    Common.ResultVo deleteOutletComment(1:Common.OriginVo originVo, 2:string id);

    /**
     * 修改 OutletComment
     * @param OutletComment
     * @return ResultVo    
     */
    Common.ResultVo updateOutletComment(1:Common.OriginVo originVo, 2:OutletCommentVo.OutletCommentVo outletCommentVo);
    
    /**
     * 查询 OutletComment
     * @param string id
     * @return OutletCommentVo    
     */
    OutletCommentVo.OutletCommentVo getOutletCommentById(1:string id);

    /**
     * 查询 OutletComment
     * @param OutletComment
     * @return List<OutletCommentVo>
     */
    list<OutletCommentVo.OutletCommentVo> getOutletComment(1:OutletCommentVo.OutletCommentVo outletCommentVo);

    /**
     * 分页查询 OutletComment
     * @param OutletComment
     * @return List<OutletCommentVo>
     */
    OutletCommentServiceVo.OutletCommentPageVoRes getOutletCommentByPage(1:Common.PageVo page, 2:OutletCommentVo.OutletCommentVo outletCommentVo);
    
    /**
     * 增加 评论回复
     * @param OutletComment
     * @return ResultVo    
     */
    Common.ResultVo addOutletCommentOfRecomment(1:Common.OriginVo originVo, 2:OutletCommentVo.OutletCommentVo outletCommentVo);
    
    /**
     * 门店评论数量查询
     * @param outletCommentVo - clientId merchantId outletId
     * @return i32  
     */
    i32 getOutletCommentSum(1:OutletCommentVo.OutletCommentVo outletCommentVo);
    
    /**
     * 门店评论级别数量查询
     * @param outletCommentVo - merchantId outletId
     * @return OutletCommentLevelAmount  
     */
    OutletCommentServiceVo.OutletCommentLevelAmountVo getOutletCommentLevelAmount(1:OutletCommentVo.OutletCommentVo outletCommentVo);
    
    /**
     * 商户评论级别数量查询
     * @param outletCommentVo - merchantId
     * @return list<OutletCommentLevelAmount>  
     */
    list<OutletCommentServiceVo.OutletCommentLevelAmountVo> getMerchantCommentLevelAmount(1:string merchantId);

    /**
     * 分页查询 OutletComment
     * @param OutletComment(+orgCode)
     * @return Page
     */
    OutletCommentServiceVo.OutletCommentPageVoRes getOutletCommentPageByOrgCode(1:Common.PageVo page, 2:OutletCommentVo.OutletCommentVo outletCommentVo);
    
    /**
     * 是否存某会员在某天针对某门店的评论
     * @return bool
     */
    bool isExistComment(1:string memberCode, 2:string outletId, 3:i64 time);
    
    
    /**
    *是否存在某会员已经对门店进行了面对面的评论.
    *@return bool
    */
    bool isExitsFaceToFaceComment(1:string memberCode,2:string outletId,3:string orderId);
    
    
}