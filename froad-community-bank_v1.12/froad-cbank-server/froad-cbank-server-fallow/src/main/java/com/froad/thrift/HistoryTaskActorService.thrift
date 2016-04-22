/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入HistoryTaskActorVo.thrift文件 */
include "HistoryTaskActorVo.thrift"

/* 引入HistoryTaskActorServiceVo.thrift文件 */
include "HistoryTaskActorServiceVo.thrift"

namespace java com.froad.thrift.service


/**
 * HistoryTaskActorService
 */
service HistoryTaskActorService extends BizMonitor.BizMonitorService {

    /**
     * 增加 HistoryTaskActor
     * @param originVo 源信息对象
     * @param historyTaskActorVo
     * @return String    主键ID
     */
    string addHistoryTaskActor(1:Common.OriginVo originVo, 2:HistoryTaskActorVo.HistoryTaskActorVo historyTaskActorVo),

    /**
     * 删除 HistoryTaskActor
     * @param originVo 源信息对象
     * @param historyTaskActorVo
     * @return boolean    
     */
    bool deleteHistoryTaskActor(1:Common.OriginVo originVo, 2:HistoryTaskActorVo.HistoryTaskActorVo historyTaskActorVo);

    /**
     * 根据taskId删除单个 HistoryTaskActor
     * @param originVo 源信息对象
     * @param taskId
     * @return HistoryTaskActorVo
     */
    bool deleteHistoryTaskActorByTaskId(1:Common.OriginVo originVo, 2:string taskId),

    /**
     * 修改 HistoryTaskActor
     * @param originVo 源信息对象
     * @param historyTaskActorVo
     * @return boolean    
     */
    bool updateHistoryTaskActor(1:Common.OriginVo originVo, 2:HistoryTaskActorVo.HistoryTaskActorVo historyTaskActorVo),

    /**
     * 根据taskId修改单个 HistoryTaskActor
     * @param originVo 源信息对象
     * @param taskId
     * @return HistoryTaskActorVo
     */
    bool updateHistoryTaskActorByTaskId(1:Common.OriginVo originVo, 2:HistoryTaskActorVo.HistoryTaskActorVo historyTaskActorVo),

    /**
     * 根据taskId查询单个 HistoryTaskActor
     * @param taskId
     * @return HistoryTaskActorVo
     */
    HistoryTaskActorVo.HistoryTaskActorVo getHistoryTaskActorByTaskId(1:string taskId),

    /**
     * 根据条件查询一个 HistoryTaskActor
     * @param historyTaskActorVo
     * @return HistoryTaskActorVo
     */
    HistoryTaskActorVo.HistoryTaskActorVo getOneHistoryTaskActor(1:HistoryTaskActorVo.HistoryTaskActorVo historyTaskActorVo),

    /**
     * 根据条件统计 HistoryTaskActor
     * @param historyTaskActorVo
     * @return int
     */
    i32 countHistoryTaskActor(1:HistoryTaskActorVo.HistoryTaskActorVo historyTaskActorVo),

    /**
     * 查询 HistoryTaskActor
     * @param historyTaskActorVo
     * @return List<HistoryTaskActorVo>
     */
    list<HistoryTaskActorVo.HistoryTaskActorVo> getHistoryTaskActor(1:HistoryTaskActorVo.HistoryTaskActorVo historyTaskActorVo),

    /**
     * 分页查询 HistoryTaskActor
     * @param historyTaskActorVo
     * @return HistoryTaskActorPageVoRes
     */
    HistoryTaskActorServiceVo.HistoryTaskActorPageVoRes getHistoryTaskActorByPage(1:Common.PageVo page, 2:HistoryTaskActorVo.HistoryTaskActorVo historyTaskActorVo);
}
