namespace java com.froad.thrift.vo


/**
 * HistoryTaskVo
 */
struct HistoryTaskVo {
    /** id */
    1: optional i64 id,

    /** createTime */
    2: optional i64 createTime,

    /** updateTime */
    3: optional i64 updateTime,

    /** clientId */
    4: optional string clientId,

    /** instanceId */
    5: optional string instanceId,

    /** nodeId */
    6: optional string nodeId,

    /** taskId */
    7: optional string taskId,

    /** parentTaskId */
    8: optional string parentTaskId,

    /** name */
    9: optional string name,

    /** displayName */
    10: optional string displayName,

    /** performType */
    11: optional string performType,

    /** taskType */
    12: optional string taskType,

    /** taskState */
    13: optional string taskState,

    /** operator */
    14: optional string operator,

    /** finishTime */
    15: optional i64 finishTime,

    /** auditState */
    16: optional string auditState,

    /** remark */
    17: optional string remark,

    /** orgCode */
    18: optional string orgCode

}
