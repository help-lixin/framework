package help.lixin.framework.route.client;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Set;

public class RouteTest {

    // 测试未配置路由的情况下
    @Test
    public void testNoConfigRoute() {
        String expected1 = "consumer...Hello World!!! - Provider ONE ";
        String expected2 = "consumer...Hello World!!! - Provider TWO ";
        // 期望返回的内容集合
        Set<String> expecteds = new HashSet<>();
        expecteds.add(expected1);
        expecteds.add(expected2);

        // 发送的URL
        String url = "http://localhost:7070/consumer";
        RestTemplate template = new RestTemplate();
        for (int i = 0; i < 10; i++) { // 总共发送10次请求
            String result = template.getForEntity(url, String.class).getBody();
            // 如果返回的内容在:Set<String>,则代表通过
            Assert.assertEquals(true, expecteds.contains(result));
        }
    }

    // 测试配置路由的情况下
    @Test
    public void testConfigRoute() {
        String expected = "consumer...Hello World!!! - Provider TWO ";
        // 发送的URL
        String url = "http://localhost:7070/consumer";
        String headerKey = "x-route";
        String headerValue = "test-provider/127.0.0.1:8081";

        // 创建请求头信息
        HttpHeaders headers = new HttpHeaders();
        headers.add(headerKey,headerValue);

        RestTemplate template = new RestTemplate();
        for (int i = 0; i < 10; i++) { // 总共发送10次请求
            String result = template.exchange(url, HttpMethod.GET , new HttpEntity<>(headers), String.class).getBody();
            // 如果返回的内容在:Set<String>,则代表通过
             Assert.assertEquals(expected, result);
        }
    }

}
