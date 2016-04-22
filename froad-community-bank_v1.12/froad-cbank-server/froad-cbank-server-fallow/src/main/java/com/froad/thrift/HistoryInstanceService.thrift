/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入HistoryInstanceVo.thrift文件 */
include "HistoryInstanceVo.thrift"

/* 引入HistoryInstanceServiceVo.thrift文件 */
include "HistoryInstanceServiceVo.thrift"

namespace java com.froad.thrift.service


/**
 * HistoryInstanceService
 */
service HistoryInstanceService extends BizMonitor.BizMonitorService {

    /**
     * 增加 HistoryInstance
     * @param originVo 源信息对象
     * @param historyInstanceVo
     * @return long    主键ID
     */
    i64 addHistoryInstance(1:Common.OriginVo originVo, 2:HistoryInstanceVo.HistoryInstanceVo historyInstanceVo),

    /**
     * 删除 HistoryInstance
     * @param originVo 源信息对象
     * @param historyInstanceVo
     * @return boolean    
     */
    bool deleteHistoryInstance(1:Common.OriginVo originVo, 2:HistoryInstanceVo.HistoryInstanceVo historyInstanceVo);

    /**
     * 根据id删除单个 HistoryInstance
     * @param originVo 源信息对象
     * @param id
     * @return HistoryInstanceVo
     */
    bool deleteHistoryInstanceById(1:Common.OriginVo originVo, 2:i64 id),

    /**
     * 修改 HistoryInstance
     * @param originVo 源信息对象
     * @param historyInstanceVo
     * @return boolean    
     */
    bool updateHistoryInstance(1:Common.OriginVo originVo, 2:HistoryInstanceVo.HistoryInstanceVo historyInstanceVo),

    /**
     * 根据id修改单个 HistoryInstance
     * @param originVo 源信息对象
     * @param id
     * @return HistoryInstanceVo
     */
    bool updateHistoryInstanceById(1:Common.OriginVo originVo, 2:HistoryInstanceVo.HistoryInstanceVo historyInstanceVo),

    /**
     * 根据id查询单个 HistoryInstance
     * @param id
     * @return HistoryInstanceVo
     */
    HistoryInstanceVo.HistoryInstanceVo getHistoryInstanceById(1:i64 id),

    /**
     * 根据条件查询一个 HistoryInstance
     * @param historyInstanceVo
     * @return HistoryInstanceVo
     */
    HistoryInstanceVo.HistoryInstanceVo getOneHistoryInstance(1:HistoryInstanceVo.HistoryInstanceVo historyInstanceVo),

    /**
     * 根据条件统计 HistoryInstance
     * @param historyInstanceVo
     * @return int
     */
    i32 countHistoryInstance(1:HistoryInstanceVo.HistoryInstanceVo historyInstanceVo),

    /**
     * 查询 HistoryInstance
     * @param historyInstanceVo
     * @return List<HistoryInstanceVo>
     */
    list<HistoryInstanceVo.HistoryInstanceVo> getHistoryInstance(1:HistoryInstanceVo.HistoryInstanceVo historyInstanceVo),

    /**
     * 分页查询 HistoryInstance
     * @param historyInstanceVo
     * @return HistoryInstancePageVoRes
     */
    HistoryInstanceServiceVo.HistoryInstancePageVoRes getHistoryInstanceByPage(1:Common.PageVo page, 2:HistoryInstanceVo.HistoryInstanceVo historyInstanceVo);
}
