package help.lixin.framework.cache.interceptor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.AbstractCacheResolver;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.util.StringUtils;

import help.lixin.framework.cache.annotation.Cacheable;

public class CustomCacheResolver extends AbstractCacheResolver implements ApplicationContextAware {

	private Logger logger = LoggerFactory.getLogger(CustomCacheResolver.class);

	private ApplicationContext applicationContext;

	/**
	 * Construct a new {@code CustomCacheResolver}.
	 * 
	 * @see #setCacheManager
	 */
	public CustomCacheResolver() {
	}

	public CustomCacheResolver(CacheManager cacheManager) {
		super(cacheManager);
	}

	@Override
	public void afterPropertiesSet() {
		CacheManager cacheManager = applicationContext.getBean(CacheManager.class);
		setCacheManager(cacheManager);
		super.afterPropertiesSet();
	}

	@Override
	public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {
		Collection<String> cacheNames = getCacheNames(context);
		if (cacheNames == null) {
			return Collections.emptyList();
		}
		Collection<Cache> result = new ArrayList<>(cacheNames.size());

		// 只能通过反射拿到内容,不懂为何要这样设计.
		// *******************************************************
		// 获得方法上的注解@Cacheable
		Duration ttl = parseTTL(context);
		// *******************************************************

		for (String cacheName : cacheNames) {
			Cache cache = getCacheManager().getCache(cacheName);
			if (cache == null) {
				throw new IllegalArgumentException(
						"Cannot find cache named '" + cacheName + "' for " + context.getOperation());
			}
			// *******************************************************
			inovkeRedisCacheConfigurationTTL(cache, ttl);
			// *******************************************************
			result.add(cache);
		}
		return result;
	}

	protected void inovkeRedisCacheConfigurationTTL(Cache cache, Duration ttl) throws RuntimeException {
		if (cache instanceof RedisCache) {
			RedisCache redisCache = (RedisCache) cache;
			try {
				Field cacheConfigField = RedisCache.class.getDeclaredField("cacheConfig");
				cacheConfigField.setAccessible(true);
				RedisCacheConfiguration redisCacheConfiguration = (RedisCacheConfiguration) cacheConfigField
						.get(redisCache);
				Field ttlField = redisCacheConfiguration.getClass().getDeclaredField("ttl");
				ttlField.setAccessible(true);
				ttlField.set(redisCacheConfiguration, ttl);
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				logger.error(e.getMessage());
				throw new RuntimeException(e.getMessage());
			}
		}
	}

	protected Duration parseTTL(CacheOperationInvocationContext<?> context) throws RuntimeException {
		Duration ttl = Duration.parse("PT0s");
		try {
			Field metadataField = context.getClass().getDeclaredField("metadata");
			metadataField.setAccessible(true);
			Object metadataObj = metadataField.get(context);

			Field targetMethodField = metadataObj.getClass().getDeclaredField("targetMethod");
			targetMethodField.setAccessible(true);
			Method method = (Method) targetMethodField.get(metadataObj);
			method.setAccessible(true);

			Cacheable cacheable = method.getDeclaredAnnotation(Cacheable.class);
			if (!StringUtils.isEmpty(cacheable.ttl())) {
				ttl = Duration.parse(cacheable.ttl());
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			logger.error("parse ttl error:{}", e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return ttl;
	}

	@Override
	protected Collection<String> getCacheNames(CacheOperationInvocationContext<?> context) {
		return context.getOperation().getCacheNames();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
