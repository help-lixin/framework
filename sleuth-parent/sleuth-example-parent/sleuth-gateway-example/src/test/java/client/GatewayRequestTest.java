package client;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Set;

public class GatewayRequestTest {

    @Test
    @Ignore
    public void testRequest() {
        String expected1 = "consumer...Hello World!!! - Provider ONE ";
        String expected2 = "consumer...Hello World!!! - Provider TWO ";
        Set<String> expecteds = new HashSet<>();
        expecteds.add(expected1);
        expecteds.add(expected2);

        // 发送的URL
        String url = "http://localhost:9000/test-consumer/consumer";
        RestTemplate template = new RestTemplate();
        for (int i = 0; i < 1000; i++) { // 总共发送10次请求
            String result = template.getForEntity(url, String.class).getBody();
            // 如果返回的内容在:Set<String>,则代表通过
            Assert.assertEquals(true, expecteds.contains(result));
        }
    }
}
