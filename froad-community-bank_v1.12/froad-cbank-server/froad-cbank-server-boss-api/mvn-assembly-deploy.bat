@echo off
set MAVEN_OPTS= -Xms256M -Xmx1024M -XX:PermSize=64M -XX:MaxPermSize=128M
call mvn clean assembly:assembly
cd target
call mvn deploy:deploy-file  -DgroupId=com.froad.cbank  -DartifactId=froad-cbank-server-boss-api  -Dversion=1.2.0-SNAPSHOT -Dpackaging=jar  -Dfile=./froad-cbank-server-boss-api-1.2.0-SNAPSHOT.jar -Durl=http://172.18.2.13:8081/content/repositories/froad-snapshot -DrepositoryId=froad-snapshot
pause

