1.Trunk版本号（各pom.xml -version）
	0.0.1-SNAPSHOT
	
	注意：分支以各版本迭代号为version
	
2.Thrift文件
	├── froad-cbank-server-模块
	│   ├── src
	│   │   ├── main
	│   │   │   ├── java
	│   │   │   │   └── com
	│   │   │   │       └── froad
	│   │   │   │           ├── thrift
	│   │   │   │           │   ├── service
	│   │   │   │           │   └── vo
	│   │   │   │           │   └── 文件名.thrift
	
	接口服务（service）.thrift		namespace java com.froad.thrift.service
	VO实例 （struct）.thrift		namespace java com.froad.thrift.vo
	
	注意：thrift文件namespace定义 (namespace server与vo分开)
	
3.分支版本sql文件（迭代中对数据库修改  如：version=1.4）
	├── froad-cbank-server-模块
	│   ├── sql
	│   │   └── 1.4-init.sql
	│   │   └── 1.4-rollback.sql
	
	init.sql     此版本增加脚本（结构+数据）
	rollback.sql 回滚执行脚本（结构+数据）
	
	注意：trunk版本上上面两个sql文件累加
	
4.server-api打包
	├── froad-cbank-server
	│   ├── server-api-install.sh
	│   ├── server-api-install.bat
	
	执行打包脚本（编译 ==> 复制 ==> 打包 ==> 上传） 
	thrift==>java==>server-api==>打包私服库
	
	目前以下模块thrift文件会进行编译
	froad-cbank-server-bank
	froad-cbank-server-common
	froad-cbank-server-goods
	froad-cbank-server-merchant
	froad-cbank-server-order
	froad-cbank-server-qrcode
	froad-cbank-server-support
	froad-cbank-server-task
	
	
	
	
	
	
	
	
	