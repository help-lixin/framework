### 1.route-service集成步骤
> 1. 添加route-service依赖.     
> 2. 测试时,在协议头配置规则即可.  
> 3. 案例:curl --header "x-route:test-provider/127.0.0.1:8081"  http://localhost:7070/consumer
### 2.route-example测试流程
> 1. 启动EurekaServer.  
> 2. 启动ProviderOneApplication.  
> 3. 启动ProviderTwoApplication.  
> 4. 启动ConsumerApplication.  
> 5. 启动测试类(RouteTest). 
### 3.route-gateway-example测试流程
```
// 案例一(test-provider的请求固定分配在:127.0.0.1:8081)
curl --header "x-route:test-provider/127.0.0.1:8081"  http://localhost:9000/test-consumer/consumer


// 案例二(test-consumer的请求固定分配在:127.0.0.1:7071,并且,test-provider的请求固定分配在:127.0.0.1:8081)
// 通过日志去查看
curl --header "x-route:test-consumer/127.0.0.1:7071,test-provider/127.0.0.1:8081"  http://localhost:9000/test-consumer/consumer
```