package help.lixin.framework.event;

import help.lixin.framework.event.config.TestEventConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestEventConfig.class})
public class EventPbulishTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testPublish() {
        applicationContext.publishEvent(new RegisterServiceStartEvent("hello world"));
    }
}
