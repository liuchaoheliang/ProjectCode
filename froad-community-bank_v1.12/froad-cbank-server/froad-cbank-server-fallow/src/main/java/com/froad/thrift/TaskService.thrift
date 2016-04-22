/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入TaskVo.thrift文件 */
include "TaskVo.thrift"

/* 引入TaskServiceVo.thrift文件 */
include "TaskServiceVo.thrift"

namespace java com.froad.thrift.service


/**
 * TaskService
 */
service TaskService extends BizMonitor.BizMonitorService {

    /**
     * 增加 Task
     * @param originVo 源信息对象
     * @param taskVo
     * @return long    主键ID
     */
    i64 addTask(1:Common.OriginVo originVo, 2:TaskVo.TaskVo taskVo),

    /**
     * 删除 Task
     * @param originVo 源信息对象
     * @param taskVo
     * @return boolean    
     */
    bool deleteTask(1:Common.OriginVo originVo, 2:TaskVo.TaskVo taskVo);

    /**
     * 根据id删除单个 Task
     * @param originVo 源信息对象
     * @param id
     * @return TaskVo
     */
    bool deleteTaskById(1:Common.OriginVo originVo, 2:i64 id),

    /**
     * 根据taskId删除单个 Task
     * @param originVo 源信息对象
     * @param taskId
     * @return TaskVo
     */
    bool deleteTaskByTaskId(1:Common.OriginVo originVo, 2:string taskId),

    /**
     * 修改 Task
     * @param originVo 源信息对象
     * @param taskVo
     * @return boolean    
     */
    bool updateTask(1:Common.OriginVo originVo, 2:TaskVo.TaskVo taskVo),

    /**
     * 根据id修改单个 Task
     * @param originVo 源信息对象
     * @param id
     * @return TaskVo
     */
    bool updateTaskById(1:Common.OriginVo originVo, 2:TaskVo.TaskVo taskVo),

    /**
     * 根据id修改单个 Task
     * @param originVo 源信息对象
     * @param taskId
     * @return TaskVo
     */
    bool updateTaskByTaskId(1:Common.OriginVo originVo, 2:TaskVo.TaskVo taskVo),

    /**
     * 根据id查询单个 Task
     * @param id
     * @return TaskVo
     */
    TaskVo.TaskVo getTaskById(1:i64 id),

    /**
     * 根据taskId查询单个 Task
     * @param taskId
     * @return TaskVo
     */
    TaskVo.TaskVo getTaskByTaskId(1:string taskId),

    /**
     * 根据条件查询一个 Task
     * @param taskVo
     * @return TaskVo
     */
    TaskVo.TaskVo getOneTask(1:TaskVo.TaskVo taskVo),

    /**
     * 根据条件统计 Task
     * @param taskVo
     * @return int
     */
    i32 countTask(1:TaskVo.TaskVo taskVo),

    /**
     * 查询 Task
     * @param taskVo
     * @return List<TaskVo>
     */
    list<TaskVo.TaskVo> getTask(1:TaskVo.TaskVo taskVo),

    /**
     * 分页查询 Task
     * @param taskVo
     * @return TaskPageVoRes
     */
    TaskServiceVo.TaskPageVoRes getTaskByPage(1:Common.PageVo page, 2:TaskVo.TaskVo taskVo);
}
