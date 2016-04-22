@echo off
set MAVEN_OPTS= -Xms256M -Xmx1024M -XX:PermSize=64M -XX:MaxPermSize=128M
call mvn clean assembly:assembly

mvn install:install-file -Dfile=./target/froad-cbank-server-common-1.1.0-SNAPSHOT.jar -DgroupId=com.froad.cbank -DartifactId=froad-cbank-server -Dversion=1.1.0-SNAPSHOT -Dpackaging=jar
pause
