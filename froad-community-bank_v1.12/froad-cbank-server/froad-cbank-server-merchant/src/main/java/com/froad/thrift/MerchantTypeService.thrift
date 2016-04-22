/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入MerchantTypeVo.thrift文件 */
include "MerchantTypeVo.thrift"

/* 引入MerchantTypeServiceVo.thrift文件 */
include "MerchantTypeServiceVo.thrift"

namespace java com.froad.thrift.service


/**
 * MerchantTypeService
 */
service MerchantTypeService extends BizMonitor.BizMonitorService {

    /**
     * 增加 MerchantType
     * @return MerchantTypeAddVoRes 
     */
    MerchantTypeServiceVo.MerchantTypeAddVoRes addMerchantType(1:Common.OriginVo originVo,2:MerchantTypeVo.MerchantTypeVo merchantTypeVo);

    /**
     * 删除 MerchantType
     * @return ResultVo    
     */
    Common.ResultVo deleteMerchantType(1:Common.OriginVo originVo,2:MerchantTypeVo.MerchantTypeVo merchantTypeVo);

    /**
     * 修改 MerchantType
     * @return ResultVo    
     */
    Common.ResultVo updateMerchantType(1:Common.OriginVo originVo,2:MerchantTypeVo.MerchantTypeVo merchantTypeVo);

	/**
     * 根据id查询 MerchantType
     * @param id
     * @return MerchantTypeVo
     */
	MerchantTypeVo.MerchantTypeVo getMerchantTypeById(1:i64 id,2:string clientId);

    /**
     * 查询 MerchantType
     * @param merchantType
     * @return List<MerchantTypeVo>
     */
    list<MerchantTypeVo.MerchantTypeVo> getMerchantType(1:MerchantTypeVo.MerchantTypeVo merchantTypeVo);

    /**
     * 分页查询 MerchantType
     * @param merchantType
     * @return List<MerchantTypeVo>
     */
    MerchantTypeServiceVo.MerchantTypePageVoRes getMerchantTypeByPage(1:Common.PageVo page, 2:MerchantTypeVo.MerchantTypeVo merchantTypeVo);
    
    
    /**
     * 查询 MerchantTypeVo
     * @param merchantType
     * @return List<MerchantTypeVo>
     */
    list<MerchantTypeVo.MerchantTypeVo> getMerchantTypeVoByMerchantTypeIdList(1:string clientId,2:list<i64> merchantTypeIdList);
    
    
}
