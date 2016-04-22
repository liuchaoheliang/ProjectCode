/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入BizInstanceAttrValueVo.thrift文件 */
include "BizInstanceAttrValueVo.thrift"

/* 引入BizInstanceAttrValueServiceVo.thrift文件 */
include "BizInstanceAttrValueServiceVo.thrift"

namespace java com.froad.thrift.service


/**
 * BizInstanceAttrValueService
 */
service BizInstanceAttrValueService extends BizMonitor.BizMonitorService {

    /**
     * 增加 BizInstanceAttrValue
     * @param originVo 源信息对象
     * @param bizInstanceAttrValueVo
     * @return String    主键ID
     */
    string addBizInstanceAttrValue(1:Common.OriginVo originVo, 2:BizInstanceAttrValueVo.BizInstanceAttrValueVo bizInstanceAttrValueVo),

    /**
     * 删除 BizInstanceAttrValue
     * @param originVo 源信息对象
     * @param bizInstanceAttrValueVo
     * @return boolean    
     */
    bool deleteBizInstanceAttrValue(1:Common.OriginVo originVo, 2:BizInstanceAttrValueVo.BizInstanceAttrValueVo bizInstanceAttrValueVo);

    /**
     * 根据instanceId删除单个 BizInstanceAttrValue
     * @param originVo 源信息对象
     * @param instanceId
     * @return BizInstanceAttrValueVo
     */
    bool deleteBizInstanceAttrValueByInstanceId(1:Common.OriginVo originVo, 2:string instanceId),

    /**
     * 修改 BizInstanceAttrValue
     * @param originVo 源信息对象
     * @param bizInstanceAttrValueVo
     * @return boolean    
     */
    bool updateBizInstanceAttrValue(1:Common.OriginVo originVo, 2:BizInstanceAttrValueVo.BizInstanceAttrValueVo bizInstanceAttrValueVo),

    /**
     * 根据instanceId修改单个 BizInstanceAttrValue
     * @param originVo 源信息对象
     * @param instanceId
     * @return BizInstanceAttrValueVo
     */
    bool updateBizInstanceAttrValueByInstanceId(1:Common.OriginVo originVo, 2:BizInstanceAttrValueVo.BizInstanceAttrValueVo bizInstanceAttrValueVo),

    /**
     * 根据instanceId查询单个 BizInstanceAttrValue
     * @param instanceId
     * @return BizInstanceAttrValueVo
     */
    BizInstanceAttrValueVo.BizInstanceAttrValueVo getBizInstanceAttrValueByInstanceId(1:string instanceId),

    /**
     * 根据条件查询一个 BizInstanceAttrValue
     * @param bizInstanceAttrValueVo
     * @return BizInstanceAttrValueVo
     */
    BizInstanceAttrValueVo.BizInstanceAttrValueVo getOneBizInstanceAttrValue(1:BizInstanceAttrValueVo.BizInstanceAttrValueVo bizInstanceAttrValueVo),

    /**
     * 根据条件统计 BizInstanceAttrValue
     * @param bizInstanceAttrValueVo
     * @return int
     */
    i32 countBizInstanceAttrValue(1:BizInstanceAttrValueVo.BizInstanceAttrValueVo bizInstanceAttrValueVo),

    /**
     * 查询 BizInstanceAttrValue
     * @param bizInstanceAttrValueVo
     * @return List<BizInstanceAttrValueVo>
     */
    list<BizInstanceAttrValueVo.BizInstanceAttrValueVo> getBizInstanceAttrValue(1:BizInstanceAttrValueVo.BizInstanceAttrValueVo bizInstanceAttrValueVo),

    /**
     * 分页查询 BizInstanceAttrValue
     * @param bizInstanceAttrValueVo
     * @return BizInstanceAttrValuePageVoRes
     */
    BizInstanceAttrValueServiceVo.BizInstanceAttrValuePageVoRes getBizInstanceAttrValueByPage(1:Common.PageVo page, 2:BizInstanceAttrValueVo.BizInstanceAttrValueVo bizInstanceAttrValueVo);
}
