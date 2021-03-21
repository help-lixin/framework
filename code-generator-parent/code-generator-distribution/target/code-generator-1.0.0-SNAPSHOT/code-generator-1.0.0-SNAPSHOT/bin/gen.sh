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
MAIN_CLASS=help.lixin.framework.code.generator.core.CodeGeneratorLaunch
# 应用程序目录
export APP_PATH=`dirname $(pwd)`
PID_FILE=${BIN_DIR}/pid
CLASSPATH=.:${APP_PATH}/conf:${APP_PATH}/lib/*:
JAVA_OPT="${JAVA_OPT} -server -Xms1g -Xmx1g -Xmn256m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m"
#JAVA_OPT="${JAVA_OPT} -Xdebug -Xrunjdwp:transport=dt_socket,address=9555,server=y,suspend=y"
JAVA_OPT="${JAVA_OPT} -cp ${CLASSPATH}"
echo "start code generator..."
nohup $JAVA_HOME/bin/java $JAVA_OPT $MAIN_CLASS "$@" > /dev/null 2>&1 &