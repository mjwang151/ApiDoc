#! /bin/bash

if [ -z "$JAVA_HOME" ]; then
	echo "Java home not configured!"
	exit 1
fi

if [ ! -z "$MAX_MEM" ]; then
	mem_size=`echo $MAX_MEM | tr -d '[a-zA-Z]'`
	mem_unit=`echo $MAX_MEM | tr -d '[0-9]'`
	#JAVA_OPTION="-Dfile.encoding=UTF-8 -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -Xss64M -XX:+UseG1GC -XX:+PrintGCDetails -Xloggc:./logs/gc.log -XX:+PrintGCTimeStamps"
	JAVA_OPTION="-Dfile.encoding=UTF-8 -Xmx$((mem_size/10*7))${mem_unit} -Xms$((mem_size/2))${mem_unit} -Xss1M -XX:MaxMetaspaceSize=200M -XX:+UseG1GC -XX:+PrintGCDetails -Xloggc:./logs/gc-%t.log -XX:+PrintGCDateStamps -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./logs/gc_java_heapdump.hprof -XX:+CrashOnOutOfMemoryError"
else
	JAVA_OPTION="-Dfile.encoding=UTF-8 -Xmx1024M -Xms1024M -Xss1M -XX:MaxMetaspaceSize=200M -XX:+UseG1GC -XX:+PrintGCDetails -Xloggc:./logs/gc.log -XX:+PrintGCDateStamps -XX:+CrashOnOutOfMemoryError"
fi

if [ -z "$SERVER_TYPE" ]; then
	$JAVA_HOME/bin/java $JAVA_OPTION -jar AmarPackTest.jar
elif [ "$SERVER_TYPE" == "test" ]; then
	$JAVA_HOME/bin/java $JAVA_OPTION -jar AmarPackTest.jar --spring.profiles.active=${SERVER_TYPE} --server.port=8080
elif [ "$SERVER_TYPE" == "product" ]; then
	$JAVA_HOME/bin/java $JAVA_OPTION -jar AmarPackTest.jar --spring.profiles.active=${SERVER_TYPE} --server.port=8080
fi