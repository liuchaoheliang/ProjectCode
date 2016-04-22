/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入TaskActorVo.thrift文件 */
include "TaskActorVo.thrift"

/* 引入TaskActorServiceVo.thrift文件 */
include "TaskActorServiceVo.thrift"

namespace java com.froad.thrift.service


/**
 * TaskActorService
 */
service TaskActorService extends BizMonitor.BizMonitorService {

    /**
     * 增加 TaskActor
     * @param originVo 源信息对象
     * @param taskActorVo
     * @return String    主键ID
     */
    string addTaskActor(1:Common.OriginVo originVo, 2:TaskActorVo.TaskActorVo taskActorVo),

    /**
     * 删除 TaskActor
     * @param originVo 源信息对象
     * @param taskActorVo
     * @return boolean    
     */
    bool deleteTaskActor(1:Common.OriginVo originVo, 2:TaskActorVo.TaskActorVo taskActorVo);

    /**
     * 根据taskId删除单个 TaskActor
     * @param originVo 源信息对象
     * @param taskId
     * @return TaskActorVo
     */
    bool deleteTaskActorByTaskId(1:Common.OriginVo originVo, 2:string taskId),

    /**
     * 修改 TaskActor
     * @param originVo 源信息对象
     * @param taskActorVo
     * @return boolean    
     */
    bool updateTaskActor(1:Common.OriginVo originVo, 2:TaskActorVo.TaskActorVo taskActorVo),

    /**
     * 根据taskId修改单个 TaskActor
     * @param originVo 源信息对象
     * @param taskId
     * @return TaskActorVo
     */
    bool updateTaskActorByTaskId(1:Common.OriginVo originVo, 2:TaskActorVo.TaskActorVo taskActorVo),

    /**
     * 根据taskId查询单个 TaskActor
     * @param taskId
     * @return TaskActorVo
     */
    TaskActorVo.TaskActorVo getTaskActorByTaskId(1:string taskId),

    /**
     * 根据条件查询一个 TaskActor
     * @param taskActorVo
     * @return TaskActorVo
     */
    TaskActorVo.TaskActorVo getOneTaskActor(1:TaskActorVo.TaskActorVo taskActorVo),

    /**
     * 根据条件统计 TaskActor
     * @param taskActorVo
     * @return int
     */
    i32 countTaskActor(1:TaskActorVo.TaskActorVo taskActorVo),

    /**
     * 查询 TaskActor
     * @param taskActorVo
     * @return List<TaskActorVo>
     */
    list<TaskActorVo.TaskActorVo> getTaskActor(1:TaskActorVo.TaskActorVo taskActorVo),

    /**
     * 分页查询 TaskActor
     * @param taskActorVo
     * @return TaskActorPageVoRes
     */
    TaskActorServiceVo.TaskActorPageVoRes getTaskActorByPage(1:Common.PageVo page, 2:TaskActorVo.TaskActorVo taskActorVo);
}
