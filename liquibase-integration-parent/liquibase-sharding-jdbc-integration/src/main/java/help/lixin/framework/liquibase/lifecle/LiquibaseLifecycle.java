package help.lixin.framework.liquibase.lifecle;

import help.lixin.framework.event.RegisterServiceStartEvent;
import liquibase.Contexts;
import liquibase.Liquibase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.SmartLifecycle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class LiquibaseLifecycle implements ApplicationContextAware, SmartLifecycle {
    private final Logger log = LoggerFactory.getLogger(LiquibaseLifecycle.class);

    private AtomicBoolean running = new AtomicBoolean(false);

    private Map<Liquibase, Contexts> liquibases;
    private ApplicationContext applicationContext;


    public LiquibaseLifecycle(Map<Liquibase, Contexts> liquibases) {
        this.liquibases = liquibases;
    }


    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable callback) {
        stop();
        callback.run();
    }

    @Override
    public int getPhase() {
        return 0;
    }

    @Override
    public void start() {
        if (running.compareAndSet(false, true)) {
            List<LiquibaseExecute> executes = new ArrayList<>(liquibases.size());
            CountDownLatch latch = new CountDownLatch(liquibases.size());

            // 把Liquibase集合转换成:Execute集合.
            Iterator<Map.Entry<Liquibase, Contexts>> iterator = liquibases.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Liquibase, Contexts> entry = iterator.next();
                Liquibase liquibase = entry.getKey();
                Contexts contexts = entry.getValue();
                // 转换成:LiquibaseExecute对象.
                LiquibaseExecute execute = new LiquibaseExecute(latch, liquibase, contexts);
                executes.add(execute);
            }

            for (int i = 0; i < executes.size(); i++) {
                //这种任务只执行一次,不需要建线程池
                // 为每一个Liquibase构建线程
                // TODO lixin 测试发现:Liquibase不支持并发创建表.
                Thread thread = new Thread(executes.get(i));
                thread.setDaemon(true);
                thread.setName("liquibase-generate-table-" + i);
                thread.run();
            }

            try {
                // 等待所有的count
                latch.await();

                // 是否所有的Executer的结果都是成功的
                boolean isAllSuccess = false;
                // 获取成功的数据
                long successCount = executes.stream().filter(execute -> execute.getIsSuccess().get()).count();
                if (successCount == executes.size()) {
                    isAllSuccess = true;
                }

                if (isAllSuccess) {
                    // 发布事件,触发向注册中心进行注册.
                    applicationContext.publishEvent(new RegisterServiceStartEvent("TriggerRegister"));
                    running.set(Boolean.TRUE);
                } else {
                    // 获取所有失败的:Execute
                    List<LiquibaseExecute> failExecutes = executes.stream().filter(execute -> execute.getIsSuccess().get() == false).collect(Collectors.toList());
                    for (LiquibaseExecute execute : failExecutes) {
                        log.error("execute liquibase FAIL,description:[{}]", execute);
                    }
                }
            } catch (InterruptedException ignore) {
            }
        }
    }

    @Override
    public void stop() {
    }

    @Override
    public boolean isRunning() {
        return running.get();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

class LiquibaseExecute implements Runnable {
    private final Logger log = LoggerFactory.getLogger(LiquibaseExecute.class);

    private Liquibase liquibase;
    private Contexts context;
    private AtomicBoolean isSuccess = new AtomicBoolean(false);
    private Throwable exception = null;
    private CountDownLatch latch;

    public LiquibaseExecute(CountDownLatch latch, Liquibase liquibase, Contexts context) {
        this.latch = latch;
        this.liquibase = liquibase;
        this.context = context;
    }

    public Contexts getContext() {
        return context;
    }

    public Liquibase getLiquibase() {
        return liquibase;
    }

    public AtomicBoolean getIsSuccess() {
        return isSuccess;
    }

    public Throwable getException() {
        return exception;
    }

    @Override
    public void run() {
        try {
            log.info("START execute generate table for liquibase:[{}]", liquibase);
            liquibase.update(context);
            isSuccess.compareAndSet(false, true);
            log.info("END execute generate table for liquibase:[{}]", liquibase);
        } catch (Throwable e) {
            exception = e;
            log.warn("FAIL execute generate table for liquibase:[{}],exception:[{}]", liquibase, e);
        }
        // 放在代码,最后面:latch只是用来记录线程是否执行完毕.不做成功或异常处理.
        latch.countDown();
    }

    @Override
    public String toString() {
        return "Execute{" +
                "liquibase=" + liquibase +
                ", context=" + context +
                '}';
    }
}