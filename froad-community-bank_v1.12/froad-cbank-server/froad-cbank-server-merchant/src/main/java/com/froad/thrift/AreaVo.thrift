namespace java com.froad.thrift.vo

/**
 * 地区表
 */
struct AreaVo {
    1: optional i64 id;    /** 自增主键ID（地区ID）*/
    /** 客户端id */
    2: optional string clientId;
    3: optional string name;    /** 名称 */
    4: optional string vagueLetter;    /** 简拼 */
    5: optional string treePath;    /** 树路径 */
    6: optional string treePathName;  /** 树路径名称 */
    7: optional i64 parentId;    /** 上级地区ID */
    8: optional bool isEnable;    /** 是否启用 */
    9: optional string areaCode;    /** 地图code,供前端交互地区时候使用 */
}
