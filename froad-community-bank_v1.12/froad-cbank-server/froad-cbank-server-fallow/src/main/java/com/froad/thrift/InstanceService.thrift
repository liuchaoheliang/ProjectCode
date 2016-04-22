/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入InstanceVo.thrift文件 */
include "InstanceVo.thrift"

/* 引入InstanceServiceVo.thrift文件 */
include "InstanceServiceVo.thrift"

namespace java com.froad.thrift.service


/**
 * InstanceService
 */
service InstanceService extends BizMonitor.BizMonitorService {

    /**
     * 增加 Instance
     * @param originVo 源信息对象
     * @param instanceVo
     * @return long    主键ID
     */
    i64 addInstance(1:Common.OriginVo originVo, 2:InstanceVo.InstanceVo instanceVo),

    /**
     * 删除 Instance
     * @param originVo 源信息对象
     * @param instanceVo
     * @return boolean    
     */
    bool deleteInstance(1:Common.OriginVo originVo, 2:InstanceVo.InstanceVo instanceVo);

    /**
     * 根据id删除单个 Instance
     * @param originVo 源信息对象
     * @param id
     * @return InstanceVo
     */
    bool deleteInstanceById(1:Common.OriginVo originVo, 2:i64 id),

    /**
     * 根据instanceId删除单个 Instance
     * @param originVo 源信息对象
     * @param instanceId
     * @return InstanceVo
     */
    bool deleteInstanceByInstanceId(1:Common.OriginVo originVo, 2:string instanceId),

    /**
     * 修改 Instance
     * @param originVo 源信息对象
     * @param instanceVo
     * @return boolean    
     */
    bool updateInstance(1:Common.OriginVo originVo, 2:InstanceVo.InstanceVo instanceVo),

    /**
     * 根据id修改单个 Instance
     * @param originVo 源信息对象
     * @param id
     * @return InstanceVo
     */
    bool updateInstanceById(1:Common.OriginVo originVo, 2:InstanceVo.InstanceVo instanceVo),

    /**
     * 根据id修改单个 Instance
     * @param originVo 源信息对象
     * @param instanceId
     * @return InstanceVo
     */
    bool updateInstanceByInstanceId(1:Common.OriginVo originVo, 2:InstanceVo.InstanceVo instanceVo),

    /**
     * 根据id查询单个 Instance
     * @param id
     * @return InstanceVo
     */
    InstanceVo.InstanceVo getInstanceById(1:i64 id),

    /**
     * 根据instanceId查询单个 Instance
     * @param instanceId
     * @return InstanceVo
     */
    InstanceVo.InstanceVo getInstanceByInstanceId(1:string instanceId),

    /**
     * 根据条件查询一个 Instance
     * @param instanceVo
     * @return InstanceVo
     */
    InstanceVo.InstanceVo getOneInstance(1:InstanceVo.InstanceVo instanceVo),

    /**
     * 根据条件统计 Instance
     * @param instanceVo
     * @return int
     */
    i32 countInstance(1:InstanceVo.InstanceVo instanceVo),

    /**
     * 查询 Instance
     * @param instanceVo
     * @return List<InstanceVo>
     */
    list<InstanceVo.InstanceVo> getInstance(1:InstanceVo.InstanceVo instanceVo),

    /**
     * 分页查询 Instance
     * @param instanceVo
     * @return InstancePageVoRes
     */
    InstanceServiceVo.InstancePageVoRes getInstanceByPage(1:Common.PageVo page, 2:InstanceVo.InstanceVo instanceVo);
}
