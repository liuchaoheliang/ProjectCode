namespace java com.froad.thrift.vo


/**
 * TaskVo
 */
struct TaskVo {
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

    /** operator */
    13: optional string operator,

    /** finishTime */
    14: optional i64 finishTime,

    /** orgCode */
    15: optional string orgCode

}
