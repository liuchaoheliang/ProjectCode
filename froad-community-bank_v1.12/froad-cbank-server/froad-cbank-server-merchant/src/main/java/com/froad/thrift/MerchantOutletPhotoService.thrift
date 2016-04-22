/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入MerchantOutletPhotoVo.thrift文件 */
include "MerchantOutletPhotoVo.thrift"

/* 引入MerchantOutletPhotoServiceVo.thrift文件 */
include "MerchantOutletPhotoServiceVo.thrift"

namespace java com.froad.thrift.service


/**
 * MerchantOutletPhotoService
 */
service MerchantOutletPhotoService extends BizMonitor.BizMonitorService{

    /**
     * 增加 MerchantOutletPhoto
     * @return MerchantOutletPhotoAddVoRes
     */
    MerchantOutletPhotoServiceVo.MerchantOutletPhotoAddVoRes addMerchantOutletPhoto(1:Common.OriginVo originVo,2:MerchantOutletPhotoVo.MerchantOutletPhotoVo merchantOutletPhotoVo);

	/**
     * 批量增加 MerchantOutletPhoto
     * @param list<merchantOutletPhoto>
     * @return list<Common.ResultVo>    结果集
     */
    list<Common.ResultVo> addMerchantOutletPhotoByBatch(1:Common.OriginVo originVo,2:list<MerchantOutletPhotoVo.MerchantOutletPhotoVo> merchantOutletPhotoVoList);
    
    /**
     * 保存 MerchantOutletPhoto,以上送的集合为准
     * @param list<merchantOutletPhoto>
     * @return Common.ResultVo    结果集
     */
    Common.ResultVo saveMerchantOutletPhoto(1:Common.OriginVo originVo, 2:list<MerchantOutletPhotoVo.MerchantOutletPhotoVo> merchantOutletPhotoVoList);

    /**
     * 删除 MerchantOutletPhoto
     * @return ResultVo    
     */
    Common.ResultVo deleteMerchantOutletPhoto(1:Common.OriginVo originVo,2:MerchantOutletPhotoVo.MerchantOutletPhotoVo merchantOutletPhotoVo);

    /**
     * 修改 MerchantOutletPhoto
     * @return ResultVo    
     */
    Common.ResultVo updateMerchantOutletPhoto(1:Common.OriginVo originVo,2:MerchantOutletPhotoVo.MerchantOutletPhotoVo merchantOutletPhotoVo);

    /**
     * 查询 MerchantOutletPhoto
     * @param merchantOutletPhoto
     * @return List<MerchantOutletPhotoVo>
     */
    list<MerchantOutletPhotoVo.MerchantOutletPhotoVo> getMerchantOutletPhoto(1:MerchantOutletPhotoVo.MerchantOutletPhotoVo merchantOutletPhotoVo);

    /**
     * 分页查询 MerchantOutletPhoto
     * @param merchantOutletPhoto
     * @return List<MerchantOutletPhotoVo>
     */
    MerchantOutletPhotoServiceVo.MerchantOutletPhotoPageVoRes getMerchantOutletPhotoByPage(1:Common.PageVo page, 2:MerchantOutletPhotoVo.MerchantOutletPhotoVo merchantOutletPhotoVo);
}
