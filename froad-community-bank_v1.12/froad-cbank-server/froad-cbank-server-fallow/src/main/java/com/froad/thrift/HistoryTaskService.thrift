/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入HistoryTaskVo.thrift文件 */
include "HistoryTaskVo.thrift"

/* 引入HistoryTaskServiceVo.thrift文件 */
include "HistoryTaskServiceVo.thrift"

namespace java com.froad.thrift.service


/**
 * HistoryTaskService
 */
service HistoryTaskService extends BizMonitor.BizMonitorService {

    /**
     * 增加 HistoryTask
     * @param originVo 源信息对象
     * @param historyTaskVo
     * @return long    主键ID
     */
    i64 addHistoryTask(1:Common.OriginVo originVo, 2:HistoryTaskVo.HistoryTaskVo historyTaskVo),

    /**
     * 删除 HistoryTask
     * @param originVo 源信息对象
     * @param historyTaskVo
     * @return boolean    
     */
    bool deleteHistoryTask(1:Common.OriginVo originVo, 2:HistoryTaskVo.HistoryTaskVo historyTaskVo);

    /**
     * 根据id删除单个 HistoryTask
     * @param originVo 源信息对象
     * @param id
     * @return HistoryTaskVo
     */
    bool deleteHistoryTaskById(1:Common.OriginVo originVo, 2:i64 id),

    /**
     * 修改 HistoryTask
     * @param originVo 源信息对象
     * @param historyTaskVo
     * @return boolean    
     */
    bool updateHistoryTask(1:Common.OriginVo originVo, 2:HistoryTaskVo.HistoryTaskVo historyTaskVo),

    /**
     * 根据id修改单个 HistoryTask
     * @param originVo 源信息对象
     * @param id
     * @return HistoryTaskVo
     */
    bool updateHistoryTaskById(1:Common.OriginVo originVo, 2:HistoryTaskVo.HistoryTaskVo historyTaskVo),

    /**
     * 根据id查询单个 HistoryTask
     * @param id
     * @return HistoryTaskVo
     */
    HistoryTaskVo.HistoryTaskVo getHistoryTaskById(1:i64 id),

    /**
     * 根据条件查询一个 HistoryTask
     * @param historyTaskVo
     * @return HistoryTaskVo
     */
    HistoryTaskVo.HistoryTaskVo getOneHistoryTask(1:HistoryTaskVo.HistoryTaskVo historyTaskVo),

    /**
     * 根据条件统计 HistoryTask
     * @param historyTaskVo
     * @return int
     */
    i32 countHistoryTask(1:HistoryTaskVo.HistoryTaskVo historyTaskVo),

    /**
     * 查询 HistoryTask
     * @param historyTaskVo
     * @return List<HistoryTaskVo>
     */
    list<HistoryTaskVo.HistoryTaskVo> getHistoryTask(1:HistoryTaskVo.HistoryTaskVo historyTaskVo),

    /**
     * 分页查询 HistoryTask
     * @param historyTaskVo
     * @return HistoryTaskPageVoRes
     */
    HistoryTaskServiceVo.HistoryTaskPageVoRes getHistoryTaskByPage(1:Common.PageVo page, 2:HistoryTaskVo.HistoryTaskVo historyTaskVo);
}
