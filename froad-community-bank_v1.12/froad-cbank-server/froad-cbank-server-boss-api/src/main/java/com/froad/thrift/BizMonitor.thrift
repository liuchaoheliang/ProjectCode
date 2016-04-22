include "BizMonitorVo.thrift"

namespace java com.froad.thrift.monitor.service

service BizMonitorService {
  /**
   * return service name
   */
  string getName()
  /**
   * return service version
   */
  string getVersion()
  /**
   *Get all the business  method of the service
   */
  list<BizMonitorVo.BizMethodInfo> getServiceBizMethods()
  /**
   *Get all the business  method invocation of services provide information
   */
  map<string,BizMonitorVo.BizMethodInvokeInfo> getBizMethodsInvokeInfo()
  /**
   *Get the business  method invocation of services provide information
   */
  BizMonitorVo.BizMethodInvokeInfo getBizMethodInvokeInfo(1:string methodName)
  /**
   *Access to the service call total number
   */
  i64 getServiceCount()
  /**
   *Access to services run duration unit of seconds
   */
  i64 aliveSince()
  /**
   * Tell the server to reload its configuration, reopen log files, etc
   */
  oneway void reinitialize()
  /**
   * Suggest a shutdown to the server
   */
  oneway void shutdown()
  /**
   * Sets an option
   */
  void setOption(1: string key, 2: string value)
  /**
   * Gets all options
   */
  map<string, string> getOptions()
}
