include "BizMonitor.thrift"

namespace java com.froad.thrift.vo 

struct UserVo{
	1:i32 id;
	2:string name;
	3:i32 age;
	4:bool sex;
	5:list<string> subNodeList;
	6:set<i32> subNodeSet;
	7:map<i32,string> subNodeMap;  
}

service UserService extends BizMonitor.BizMonitorService{
	
	void addUser(1:UserVo userVo)

	UserVo getById(1:i64 id)

	list<UserVo> getAll()
}
