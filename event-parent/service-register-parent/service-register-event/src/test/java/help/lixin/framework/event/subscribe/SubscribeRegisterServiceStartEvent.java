package help.lixin.framework.event.subscribe;

import help.lixin.framework.event.RegisterServiceStartEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;

public class SubscribeRegisterServiceStartEvent implements SmartApplicationListener {

    private Logger logger = LoggerFactory.getLogger(SubscribeRegisterServiceStartEvent.class);

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return RegisterServiceStartEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if(event instanceof  RegisterServiceStartEvent){
            RegisterServiceStartEvent registerServiceStartEvent = (RegisterServiceStartEvent)event;
            logger.debug("subscribe RegisterServiceStartEvent SUCCESS.");
        }
    }
}
