package help.lixin.framework.eureka;

import help.lixin.framework.event.RegisterServiceStartEvent;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {App.class})
public class EurekaAutoServiceRegistrationTest {

    private Logger logger = LoggerFactory.getLogger(EurekaAutoServiceRegistrationTest.class);

    @Autowired
    private ApplicationContext applicationContext;


    @Test
    @Ignore
    public void testEurekaAutoServiceRegistration() throws  Exception {
        logger.debug("publish event HELLO");
        applicationContext.publishEvent(new RegisterServiceStartEvent("HELLO"));
        logger.debug("publish event WORLD");
        applicationContext.publishEvent(new RegisterServiceStartEvent("WORLD"));

        TimeUnit.SECONDS.sleep(60);
        logger.debug("publish event TEST");
        applicationContext.publishEvent(new RegisterServiceStartEvent("TEST"));

        CountDownLatch latch = new CountDownLatch(1);
        latch.await();
    }
}
