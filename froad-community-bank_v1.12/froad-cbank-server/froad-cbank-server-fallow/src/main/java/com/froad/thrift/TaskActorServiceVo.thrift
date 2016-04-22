/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入TaskActorVo.thrift文件 */
include "TaskActorVo.thrift"

namespace java com.froad.thrift.vo


struct TaskActorPageVoRes {
	1: optional Common.PageVo page;
	2: optional list<TaskActorVo.TaskActorVo> taskActorVoList,
}

