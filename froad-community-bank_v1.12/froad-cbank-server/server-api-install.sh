#!/bin/sh

# api打包的版本号
jar_version=0.0.1-SNAPSHOT

# 需要查找编译的thrift目录
srv='
froad-cbank-server-bank
froad-cbank-server-common
froad-cbank-server-goods
froad-cbank-server-merchant
froad-cbank-server-order
froad-cbank-server-qrcode
froad-cbank-server-support
froad-cbank-server-task
'
echo "开始 server模块thrift文件编译"

olddir=`pwd`
for srv_dir in $srv
	do
	cd $olddir/$srv_dir
	pwd
	find ./src -type 'f' -name '*.thrift' -exec thrift -r -gen java {} \;
	/bin/cp -avp gen-java/com $olddir/froad-cbank-server-api/src/main/java
	rm -rvf gen-java/com
done

echo "开始 server-api打包到私服"

cd $olddir/froad-cbank-server-api

echo `pwd`

mvn clean assembly:assembly
cd target
mvn install:install-file -Dfile=./froad-cbank-server-api-${jar_version}.jar -DgroupId=com.froad.cbank -DartifactId=froad-cbank-server-api -Dversion=${jar_version} -Dpackaging=jar
mvn deploy:deploy-file  -DgroupId=com.froad.cbank  -DartifactId=froad-cbank-server-api  -Dversion=${jar_version} -Dpackaging=jar  -Dfile=./froad-cbank-server-api-${jar_version}.jar -Durl=http://10.43.1.23:8081/content/repositories/froad-snapshot -DrepositoryId=froad-snapshot

echo "结束"

