namespace java com.froad.thrift.vo


/**
 * ProcessNodeVo
 */
struct ProcessNodeVo {
    /** id */
    1: optional i64 id,

    /** createTime */
    2: optional i64 createTime,

    /** updateTime */
    3: optional i64 updateTime,

    /** clientId */
    4: optional string clientId,

    /** processId */
    5: optional string processId,

    /** nodeId */
    6: optional string nodeId,

    /** name */
    7: optional string name,

    /** preNodeId */
    8: optional string preNodeId,

    /** nextNodeId */
    9: optional string nextNodeId,

    /** type */
    10: optional string type,

    /** nodeLogic */
    11: optional string nodeLogic,

    /** runnerFlag */
    12: optional string runnerFlag,

    /** nextRunnerOrg */
    13: optional string nextRunnerOrg,

    /** status */
    14: optional string status,

    /** runnerUserId */
    15: optional i64 runnerUserId,

    /** runnerPostId */
    16: optional i64 runnerPostId,

    /** runnerDepartId */
    17: optional i64 runnerDepartId,

    /** runnerUsergroupId */
    18: optional i64 runnerUsergroupId,

    /** runnerOrgLevel */
    19: optional string runnerOrgLevel,

    /** isMultiselect */
    20: optional bool isMultiselect,

    /** isOtherMan */
    21: optional bool isOtherMan,

    /** isAssignMan */
    22: optional bool isAssignMan,

    /** isRevoke */
    23: optional bool isRevoke,

    /** isFallback */
    24: optional bool isFallback

}
