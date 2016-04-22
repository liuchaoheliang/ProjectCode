/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入MerchantCategoryVo.thrift文件 */
include "MerchantCategoryVo.thrift"

/* 引入MerchantCategoryServiceVo.thrift文件 */
include "MerchantCategoryServiceVo.thrift"

namespace java com.froad.thrift.service


/**
 * MerchantCategoryService
 */
service MerchantCategoryService extends BizMonitor.BizMonitorService{

    /**
     * 增加 MerchantCategory
     * @return MerchantCategoryAddVoRes
     */
    MerchantCategoryServiceVo.MerchantCategoryAddVoRes addMerchantCategory(1:Common.OriginVo originVo ,2:MerchantCategoryVo.MerchantCategoryVo merchantCategoryVo);

    /**
     * 删除 MerchantCategory
     * @return ResultVo    
     */
    Common.ResultVo deleteMerchantCategory(1:Common.OriginVo originVo ,2:MerchantCategoryVo.MerchantCategoryVo merchantCategoryVo);

    /**
     * 修改 MerchantCategory
     * @return ResultVo    
     */
    Common.ResultVo updateMerchantCategory(1:Common.OriginVo originVo ,2:MerchantCategoryVo.MerchantCategoryVo merchantCategoryVo);

	 /**
     * 根据id查询 MerchantCategory
     * @param clientId 客户端id
     * @param id 分类id
     * @return MerchantCategoryVo
     */
	MerchantCategoryVo.MerchantCategoryVo getMerchantCategoryById (1:string clientId, 2:i64 id);

    /**
     * 查询 MerchantCategory
     * @param merchantCategory
     * @return List<MerchantCategoryVo>
     */
    list<MerchantCategoryVo.MerchantCategoryVo> getMerchantCategory(1:MerchantCategoryVo.MerchantCategoryVo merchantCategoryVo);

    /**
     * 分页查询 MerchantCategory
     * @param merchantCategory
     * @return List<MerchantCategoryVo>
     */
    MerchantCategoryServiceVo.MerchantCategoryPageVoRes getMerchantCategoryByPage(1:Common.PageVo page, 2:MerchantCategoryVo.MerchantCategoryVo merchantCategoryVo);
    
    
    /**
     * 查询 MerchantCategoryVo
     * @param merchantCategory
     * @return List<MerchantCategoryVo>
     */
    list<MerchantCategoryVo.MerchantCategoryVo> getMerchantCategoryByCategoryIdList(1:string clientId,2:list<i64> categoryIdList);
    
    
    /**
     * 获取商户分类
     * @param merchantCategory
     * @return List<MerchantCategoryVo>
     */
    list<MerchantCategoryVo.MerchantCategoryVo> getMerchantCategoryByH5(1:MerchantCategoryVo.MerchantCategoryVo merchantCategoryVo);
    
    
    
}
