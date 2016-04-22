@echo off
set MAVEN_OPTS= -Xms256M -Xmx1024M -XX:PermSize=64M -XX:MaxPermSize=128M
call mvn clean assembly:assembly
cd target
call mvn install:install-file -Dfile=./froad-cbank-server-goods-0.0.1-SNAPSHOT.jar -DgroupId=com.froad.cbank -DartifactId=froad-cbank-server-goods-client -Dversion=0.0.1-SNAPSHOT -Dpackaging=jar
pause