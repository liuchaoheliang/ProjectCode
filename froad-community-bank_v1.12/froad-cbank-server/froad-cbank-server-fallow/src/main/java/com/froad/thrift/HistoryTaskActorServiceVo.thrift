/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入HistoryTaskActorVo.thrift文件 */
include "HistoryTaskActorVo.thrift"

namespace java com.froad.thrift.vo


struct HistoryTaskActorPageVoRes {
	1: optional Common.PageVo page;
	2: optional list<HistoryTaskActorVo.HistoryTaskActorVo> historyTaskActorVoList,
}

