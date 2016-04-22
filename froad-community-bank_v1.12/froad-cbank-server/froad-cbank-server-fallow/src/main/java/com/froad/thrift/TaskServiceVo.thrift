/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入TaskVo.thrift文件 */
include "TaskVo.thrift"

namespace java com.froad.thrift.vo


struct TaskPageVoRes {
	1: optional Common.PageVo page;
	2: optional list<TaskVo.TaskVo> taskVoList,
}

