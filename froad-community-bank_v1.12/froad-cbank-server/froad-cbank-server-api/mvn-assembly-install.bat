@echo off
set MAVEN_OPTS= -Xms256M -Xmx1024M -XX:PermSize=64M -XX:MaxPermSize=128M
call mvn clean assembly:assembly
cd target
call mvn install:install-file -Dfile=./froad-cbank-server-api-1.2.0-SNAPSHOT.jar -DgroupId=com.froad.cbank -DartifactId=froad-cbank-server-api -Dversion=1.2.0-SNAPSHOT -Dpackaging=jar
pause