@echo off
set MAVEN_OPTS= -Xms256M -Xmx1024M -XX:PermSize=64M -XX:MaxPermSize=128M
call mvn clean assembly:assembly
cd target
call mvn install:install-file -Dfile=./froad-cbank-server-boss-0.0.3-SNAPSHOT.jar -DgroupId=com.froad.cbank -DartifactId=froad-cbank-server-boss-client -Dversion=0.0.3-SNAPSHOT -Dpackaging=jar
pause