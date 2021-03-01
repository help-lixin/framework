package help.lixin.framework.eureka.serviceregistry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaAutoServiceRegistration;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 对EurekaAutoServiceRegistrationOverride进行监听
 */
public class EurekaAutoServiceRegistrationOverrideListener {
    private Logger logger = LoggerFactory.getLogger(EurekaAutoServiceRegistrationOverrideListener.class);
    private EurekaAutoServiceRegistration eurekaAutoServiceRegistration;
    // 间隔多少秒执行一次.
    private final long delay;
    private volatile boolean isRunning = false;

    public EurekaAutoServiceRegistrationOverrideListener(EurekaAutoServiceRegistration eurekaAutoServiceRegistration, long delay) {
        this.delay = delay;
        this.eurekaAutoServiceRegistration = eurekaAutoServiceRegistration;
        Thread checkThread = new Thread(runnable());
        checkThread.setDaemon(true);
        checkThread.setName("EurekaAutoServiceRegistrationOverrideListener");
        checkThread.start();
    }

    public Runnable runnable() {
        return () -> {
            while (!isRunning) {
                if (eurekaAutoServiceRegistration instanceof EurekaAutoServiceRegistrationOverride) {
                    EurekaAutoServiceRegistrationOverride eurekaAutoServiceRegistrationOverride = (EurekaAutoServiceRegistrationOverride) eurekaAutoServiceRegistration;
                    Set<String> expectedEvents = eurekaAutoServiceRegistrationOverride.getExpectedEventTriggerRegister();
                    if (!eurekaAutoServiceRegistrationOverride.getRegister().get()) {
                        logger.warn("EurekaAutoServiceRegistration Register Service FAIL,Wait Events:[{}]", expectedEvents);
                    } else {
                        // 注册成功的情况下,当前的线程不要再执行了,不要浪费线程做无意义的事情.
                        isRunning = true;
                    }
                }
                try {
                    TimeUnit.SECONDS.sleep(delay);
                } catch (InterruptedException ignore) {
                }
            }
        };
    }
}
