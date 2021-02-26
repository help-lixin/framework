package help.lixin.framework.sleuth.client;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CollectorToZipkinTest {
    @Test
    public void testCollectorToZipkin() throws Exception {
        String zipKinServer = "http://127.0.0.1:9411/api/v2/spans";
        String logFile = "/Users/lixin/FwRepository/framework/sleuth-parent/sleuth-example-parent/sleuth-example/target/zipkin.log";
        BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(logFile));
        String line = bufferedReader.readLine();

        RestTemplate template = new RestTemplate();
        while (line != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<String>(line, headers);
            ResponseEntity responseEntity = template.postForEntity(zipKinServer , request , String.class);
            Assert.assertEquals(202 , responseEntity.getStatusCodeValue());
            line = bufferedReader.readLine();
        }
    }
}
