#! /bin/bash

error_exit ()
{
    echo "ERROR: $1 !!"
    exit 1
}

[ ! -e "$JAVA_HOME/bin/java" ] && JAVA_HOME=$HOME/jdk/java
[ ! -e "$JAVA_HOME/bin/java" ] && JAVA_HOME=/usr/java
[ ! -e "$JAVA_HOME/bin/java" ] && error_exit "Please set the JAVA_HOME variable in your environment, We need java(x64)!"


# shell执行目录
BIN_DIR=`pwd`
# 可以能过Ant进行替换
MAIN_CLASS=help.lixin.framework.module.boot.ModuleStarter
# 应用程序目录
export APP_PATH=`dirname $(pwd)`
PID_FILE=${BIN_DIR}/pid
CLASSPATH=.:${APP_PATH}/conf:${APP_PATH}/lib/*:
JAVA_OPT="${JAVA_OPT} -server -Xms1g -Xmx1g -Xmn256m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m"
JAVA_OPT="${JAVA_OPT} -cp ${CLASSPATH}"

# 如果参数小于1个,则提示,并退出.
if [ $# -ne 1 ]; then
   echo "Usage: $0 {start|stop|restart}"
   exit 1;
fi


check(){
   # 如果pid文件存在,则不允许重新启动
   if [ -f ${PID_FILE} ]; then
       echo "应用程序已经启动,不允许重复启动!";
       exit 1;
   fi
}

start(){
	# 提示启动中
	echo "start..."
	nohup $JAVA_HOME/bin/java $JAVA_OPT $MAIN_CLASS "$@" > /dev/null 2>&1 &
	# 生成pid文件
    echo $! > ${PID_FILE}
}

stop(){
	# 文件存在的情况下,才能根据进程pid kill
	if [ -f ${PID_FILE} ]; then
	    echo "kill pid:`cat ${PID_FILE}`"
	    echo "stop..."
		kill -SIGTERM `cat ${PID_FILE}`
		rm -rf ${PID_FILE}
	fi
}

case "$1" in
  "start")
    check
    start
  ;;

  "stop")
    stop
  ;;

  "restart")
    stop
    start
  ;;
esac