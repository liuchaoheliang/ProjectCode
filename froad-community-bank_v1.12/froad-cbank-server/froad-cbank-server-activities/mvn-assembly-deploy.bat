@echo off
set MAVEN_OPTS= -Xms256M -Xmx1024M -XX:PermSize=64M -XX:MaxPermSize=128M
call mvn clean assembly:assembly
cd target
call mvn deploy:deploy-file  -DgroupId=com.froad.cbank  -DartifactId=froad-cbank-server-activities-client  -Dversion=0.0.1-SNAPSHOT -Dpackaging=jar  -Dfile=./froad-cbank-server-activities-0.0.1-SNAPSHOT.jar -Durl=http://172.18.2.13:8081/content/repositories/froad-snapshot -DrepositoryId=froad-snapshot
pause

