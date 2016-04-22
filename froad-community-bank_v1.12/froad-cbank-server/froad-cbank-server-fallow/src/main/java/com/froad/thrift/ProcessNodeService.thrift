/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入ProcessNodeVo.thrift文件 */
include "ProcessNodeVo.thrift"

/* 引入ProcessNodeServiceVo.thrift文件 */
include "ProcessNodeServiceVo.thrift"

namespace java com.froad.thrift.service


/**
 * ProcessNodeService
 */
service ProcessNodeService extends BizMonitor.BizMonitorService {

    /**
     * 增加 ProcessNode
     * @param originVo 源信息对象
     * @param processNodeVo
     * @return long    主键ID
     */
    i64 addProcessNode(1:Common.OriginVo originVo, 2:ProcessNodeVo.ProcessNodeVo processNodeVo),

    /**
     * 删除 ProcessNode
     * @param originVo 源信息对象
     * @param processNodeVo
     * @return boolean    
     */
    bool deleteProcessNode(1:Common.OriginVo originVo, 2:ProcessNodeVo.ProcessNodeVo processNodeVo);

    /**
     * 根据id删除单个 ProcessNode
     * @param originVo 源信息对象
     * @param id
     * @return ProcessNodeVo
     */
    bool deleteProcessNodeById(1:Common.OriginVo originVo, 2:i64 id),

    /**
     * 修改 ProcessNode
     * @param originVo 源信息对象
     * @param processNodeVo
     * @return boolean    
     */
    bool updateProcessNode(1:Common.OriginVo originVo, 2:ProcessNodeVo.ProcessNodeVo processNodeVo),

    /**
     * 根据id修改单个 ProcessNode
     * @param originVo 源信息对象
     * @param id
     * @return ProcessNodeVo
     */
    bool updateProcessNodeById(1:Common.OriginVo originVo, 2:ProcessNodeVo.ProcessNodeVo processNodeVo),

    /**
     * 根据id查询单个 ProcessNode
     * @param id
     * @return ProcessNodeVo
     */
    ProcessNodeVo.ProcessNodeVo getProcessNodeById(1:i64 id),

    /**
     * 根据条件查询一个 ProcessNode
     * @param processNodeVo
     * @return ProcessNodeVo
     */
    ProcessNodeVo.ProcessNodeVo getOneProcessNode(1:ProcessNodeVo.ProcessNodeVo processNodeVo),

    /**
     * 根据条件统计 ProcessNode
     * @param processNodeVo
     * @return int
     */
    i32 countProcessNode(1:ProcessNodeVo.ProcessNodeVo processNodeVo),

    /**
     * 查询 ProcessNode
     * @param processNodeVo
     * @return List<ProcessNodeVo>
     */
    list<ProcessNodeVo.ProcessNodeVo> getProcessNode(1:ProcessNodeVo.ProcessNodeVo processNodeVo),

    /**
     * 分页查询 ProcessNode
     * @param processNodeVo
     * @return ProcessNodePageVoRes
     */
    ProcessNodeServiceVo.ProcessNodePageVoRes getProcessNodeByPage(1:Common.PageVo page, 2:ProcessNodeVo.ProcessNodeVo processNodeVo);
}
