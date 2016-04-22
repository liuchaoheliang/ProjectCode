/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入HistoryTaskVo.thrift文件 */
include "HistoryTaskVo.thrift"

namespace java com.froad.thrift.vo


struct HistoryTaskPageVoRes {
	1: optional Common.PageVo page;
	2: optional list<HistoryTaskVo.HistoryTaskVo> historyTaskVoList,
}
