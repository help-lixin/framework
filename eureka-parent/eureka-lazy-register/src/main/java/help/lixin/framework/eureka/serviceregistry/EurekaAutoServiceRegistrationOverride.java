package help.lixin.framework.eureka.serviceregistry;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.cloud.client.discovery.event.InstanceRegisteredEvent;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaAutoServiceRegistration;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaRegistration;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaServiceRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ContextClosedEvent;

import help.lixin.framework.eureka.properties.EurekaLazyRegisterProperties;
import help.lixin.framework.event.RegisterServiceStartEvent;


public class EurekaAutoServiceRegistrationOverride extends EurekaAutoServiceRegistration {
	private Logger logger = LoggerFactory.getLogger(EurekaAutoServiceRegistrationOverride.class);

	private ApplicationContext context;

	private EurekaRegistration registration;

	private EurekaServiceRegistry serviceRegistry;

	private AtomicInteger port = new AtomicInteger(0);

	private AtomicBoolean running = new AtomicBoolean(false);

	private AtomicBoolean register = new AtomicBoolean(false);

	private Set<String> expectedEventTriggerRegister = new HashSet<>();

	public EurekaAutoServiceRegistrationOverride(ApplicationContext context, EurekaServiceRegistry serviceRegistry,
												 EurekaRegistration registration, EurekaLazyRegisterProperties eurekaLazyRegisterProperties) {
		super(context, serviceRegistry, registration);
		this.context = context;
		this.serviceRegistry = serviceRegistry;
		this.registration = registration;

		// clone
		Set<String> clone = new HashSet<>();
		clone.addAll(eurekaLazyRegisterProperties.getExpectedEventTriggerRegister());
		expectedEventTriggerRegister = clone;
	}

	@Override
	public void start() {
		this.running.set(true);
		logger.warn(
				"Enable EurekaAutoServiceRegistration Override,Wait RegisterServiceStartEvent , Delay Register Service...");
	}

	public boolean isRunning() {
		return running.get();
	}

	@Override
	public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
		return WebServerInitializedEvent.class.isAssignableFrom(eventType)
				|| ContextClosedEvent.class.isAssignableFrom(eventType)
				|| RegisterServiceStartEvent.class.isAssignableFrom(eventType);
	}

	public void onApplicationEvent(RegisterServiceStartEvent event) {
		logger.info("revice RegisterServiceStartEvent,ready Register Service.");
		// 获得事件源名称.
		String eventName = (String) event.getSource();
		// 移除事件
		expectedEventTriggerRegister.remove(eventName);
		// 移除事件之后,发现还存在要期待的事件,则返回,直到:expectedEventTriggerRegister里不存在事件,则进行注册.
		if (!expectedEventTriggerRegister.isEmpty()) {
			return;
		}

		if (this.port.get() != 0) {
			if (this.registration.getNonSecurePort() == 0) {
				this.registration.setNonSecurePort(this.port.get());
			}

			if (this.registration.getSecurePort() == 0 && this.registration.isSecure()) {
				this.registration.setSecurePort(this.port.get());
			}
		}

		if (this.running.get() && this.registration.getNonSecurePort() > 0) {
			this.serviceRegistry.register(this.registration);
			this.context.publishEvent(new InstanceRegisteredEvent<>(this, this.registration.getInstanceConfig()));
			this.running.set(true);
			// 标记注册完成
			this.register.set(true);
		}
	}

	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof WebServerInitializedEvent) {
			onApplicationEvent((WebServerInitializedEvent) event);
		} else if (event instanceof ContextClosedEvent) {
			onApplicationEvent((ContextClosedEvent) event);
		} else if (event instanceof RegisterServiceStartEvent) {
			onApplicationEvent((RegisterServiceStartEvent) event);
		}
	}

	public Set<String> getExpectedEventTriggerRegister() {
		return expectedEventTriggerRegister;
	}

	public AtomicBoolean getRegister() {
		return register;
	}
}
