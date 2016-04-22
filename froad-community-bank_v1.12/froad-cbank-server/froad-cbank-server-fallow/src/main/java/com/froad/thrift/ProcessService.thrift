/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入ProcessVo.thrift文件 */
include "ProcessVo.thrift"

/* 引入ProcessServiceVo.thrift文件 */
include "ProcessServiceVo.thrift"

namespace java com.froad.thrift.service


/**
 * ProcessService
 */
service ProcessService extends BizMonitor.BizMonitorService {

    /**
     * 增加 Process
     * @param originVo 源信息对象
     * @param processVo
     * @return long    主键ID
     */
    i64 addProcess(1:Common.OriginVo originVo, 2:ProcessVo.ProcessVo processVo),

    /**
     * 删除 Process
     * @param originVo 源信息对象
     * @param processVo
     * @return boolean    
     */
    bool deleteProcess(1:Common.OriginVo originVo, 2:ProcessVo.ProcessVo processVo);

    /**
     * 根据id删除单个 Process
     * @param originVo 源信息对象
     * @param id
     * @return ProcessVo
     */
    bool deleteProcessById(1:Common.OriginVo originVo, 2:i64 id),

    /**
     * 根据processId删除单个 Process
     * @param originVo 源信息对象
     * @param processId
     * @return ProcessVo
     */
    bool deleteProcessByProcessId(1:Common.OriginVo originVo, 2:string processId),

    /**
     * 修改 Process
     * @param originVo 源信息对象
     * @param processVo
     * @return boolean    
     */
    bool updateProcess(1:Common.OriginVo originVo, 2:ProcessVo.ProcessVo processVo),

    /**
     * 根据id修改单个 Process
     * @param originVo 源信息对象
     * @param id
     * @return ProcessVo
     */
    bool updateProcessById(1:Common.OriginVo originVo, 2:ProcessVo.ProcessVo processVo),

    /**
     * 根据id修改单个 Process
     * @param originVo 源信息对象
     * @param processId
     * @return ProcessVo
     */
    bool updateProcessByProcessId(1:Common.OriginVo originVo, 2:ProcessVo.ProcessVo processVo),

    /**
     * 根据id查询单个 Process
     * @param id
     * @return ProcessVo
     */
    ProcessVo.ProcessVo getProcessById(1:i64 id),

    /**
     * 根据processId查询单个 Process
     * @param processId
     * @return ProcessVo
     */
    ProcessVo.ProcessVo getProcessByProcessId(1:string processId),

    /**
     * 根据条件查询一个 Process
     * @param processVo
     * @return ProcessVo
     */
    ProcessVo.ProcessVo getOneProcess(1:ProcessVo.ProcessVo processVo),

    /**
     * 根据条件统计 Process
     * @param processVo
     * @return int
     */
    i32 countProcess(1:ProcessVo.ProcessVo processVo),

    /**
     * 查询 Process
     * @param processVo
     * @return List<ProcessVo>
     */
    list<ProcessVo.ProcessVo> getProcess(1:ProcessVo.ProcessVo processVo),

    /**
     * 分页查询 Process
     * @param processVo
     * @return ProcessPageVoRes
     */
    ProcessServiceVo.ProcessPageVoRes getProcessByPage(1:Common.PageVo page, 2:ProcessVo.ProcessVo processVo);
}
