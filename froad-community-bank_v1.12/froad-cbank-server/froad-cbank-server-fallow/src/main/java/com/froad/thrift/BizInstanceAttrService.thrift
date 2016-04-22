/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入BizInstanceAttrVo.thrift文件 */
include "BizInstanceAttrVo.thrift"

/* 引入BizInstanceAttrServiceVo.thrift文件 */
include "BizInstanceAttrServiceVo.thrift"

namespace java com.froad.thrift.service


/**
 * BizInstanceAttrService
 */
service BizInstanceAttrService extends BizMonitor.BizMonitorService {

    /**
     * 增加 BizInstanceAttr
     * @param originVo 源信息对象
     * @param bizInstanceAttrVo
     * @return long    主键ID
     */
    i64 addBizInstanceAttr(1:Common.OriginVo originVo, 2:BizInstanceAttrVo.BizInstanceAttrVo bizInstanceAttrVo),

    /**
     * 删除 BizInstanceAttr
     * @param originVo 源信息对象
     * @param bizInstanceAttrVo
     * @return boolean    
     */
    bool deleteBizInstanceAttr(1:Common.OriginVo originVo, 2:BizInstanceAttrVo.BizInstanceAttrVo bizInstanceAttrVo);

    /**
     * 根据id删除单个 BizInstanceAttr
     * @param originVo 源信息对象
     * @param id
     * @return BizInstanceAttrVo
     */
    bool deleteBizInstanceAttrById(1:Common.OriginVo originVo, 2:i64 id),

    /**
     * 修改 BizInstanceAttr
     * @param originVo 源信息对象
     * @param bizInstanceAttrVo
     * @return boolean    
     */
    bool updateBizInstanceAttr(1:Common.OriginVo originVo, 2:BizInstanceAttrVo.BizInstanceAttrVo bizInstanceAttrVo),

    /**
     * 根据id修改单个 BizInstanceAttr
     * @param originVo 源信息对象
     * @param id
     * @return BizInstanceAttrVo
     */
    bool updateBizInstanceAttrById(1:Common.OriginVo originVo, 2:BizInstanceAttrVo.BizInstanceAttrVo bizInstanceAttrVo),

    /**
     * 根据id查询单个 BizInstanceAttr
     * @param id
     * @return BizInstanceAttrVo
     */
    BizInstanceAttrVo.BizInstanceAttrVo getBizInstanceAttrById(1:i64 id),

    /**
     * 根据条件查询一个 BizInstanceAttr
     * @param bizInstanceAttrVo
     * @return BizInstanceAttrVo
     */
    BizInstanceAttrVo.BizInstanceAttrVo getOneBizInstanceAttr(1:BizInstanceAttrVo.BizInstanceAttrVo bizInstanceAttrVo),

    /**
     * 根据条件统计 BizInstanceAttr
     * @param bizInstanceAttrVo
     * @return int
     */
    i32 countBizInstanceAttr(1:BizInstanceAttrVo.BizInstanceAttrVo bizInstanceAttrVo),

    /**
     * 查询 BizInstanceAttr
     * @param bizInstanceAttrVo
     * @return List<BizInstanceAttrVo>
     */
    list<BizInstanceAttrVo.BizInstanceAttrVo> getBizInstanceAttr(1:BizInstanceAttrVo.BizInstanceAttrVo bizInstanceAttrVo),

    /**
     * 分页查询 BizInstanceAttr
     * @param bizInstanceAttrVo
     * @return BizInstanceAttrPageVoRes
     */
    BizInstanceAttrServiceVo.BizInstanceAttrPageVoRes getBizInstanceAttrByPage(1:Common.PageVo page, 2:BizInstanceAttrVo.BizInstanceAttrVo bizInstanceAttrVo);
}
