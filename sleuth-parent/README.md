### 1. 集成测试
> 1. 配置logback.xml
> 2. 启动eureka.  
> 3. 启动provider.
> 4. 启动consumer.
> 5. 启动gateway.
> 6. 向gateway发起请求(GatewayRequestTest)
> 7. 查看日志是否产生了配置文件(注意:在生产上要开启缓存).  
> 8. 启动flume配置,收集zipkin.log内容,并向zipkin server汇报.

### 2. logback-spring.xml配置注意项
```
<appender name="ZipKinDailyRollAppender"
      class="ch.qos.logback.core.rolling.RollingFileAppender">
    <file>${ROOT_LOG_DIR}/zipkin.log</file>
    <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
        <fileNamePattern>${ROOT_LOG_DIR}/%d{yyyy/MM,aux}/zipkin.%d{yyyyMMdd}.%i.log.zip</fileNamePattern>
        <timeBasedFileNamingAndTriggeringPolicy
                class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
            <!-- or whenever the file size reaches 200MB -->
            <maxFileSize>200MB</maxFileSize>
        </timeBasedFileNamingAndTriggeringPolicy>
    </rollingPolicy>
    <encoder>
        <pattern>%msg%n</pattern>
        <immediateFlush>false</immediateFlush>
    </encoder>
</appender>

<logger name="help.lixin.framework.sleuth.reporter.LocalLogTraceReporter" level="INFO" addtivity="false">
    <appender-ref ref="ZipKinDailyRollAppender" />
</logger>
```
### 3. flume收集日志向zipkin汇报
>  /Users/lixin/Developer/apache-flume-1.8.0-bin/conf --conf-file /Users/lixin/FwRepository/framework/sleuth-parent/sleuth-example-parent/sleuth-example/src/test/resources/watch-dir-log-zipkin.conf是flume配置文件.

```
/Users/lixin/Developer/apache-flume-1.8.0-bin/bin/flume-ng agent --name a1 --conf /Users/lixin/Developer/apache-flume-1.8.0-bin/conf --conf-file /Users/lixin/FwRepository/framework/sleuth-parent/sleuth-example-parent/sleuth-example/src/test/resources/watch-dir-log-zipkin.conf  -Dflume.root.logger=INFO,console
```
