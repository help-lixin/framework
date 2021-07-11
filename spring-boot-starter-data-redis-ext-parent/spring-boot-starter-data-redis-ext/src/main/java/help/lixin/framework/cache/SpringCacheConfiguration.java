package help.lixin.framework.cache;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.RedisSerializer;

import help.lixin.framework.cache.interceptor.CustomCacheResolver;

@Configuration
@AutoConfigureAfter(RedisCacheConfiguration.class)
public class SpringCacheConfiguration {

	private Logger logger = LoggerFactory.getLogger(SpringCacheConfiguration.class);

	@Bean
	public CacheResolver customCacheResolver() {
		return new CustomCacheResolver();
	}

	// 针对Redis自定义CacheManager的Value序列化机制
	@Bean
	public CacheManagerCustomizer<CacheManager> cacheManagerCustomizer() {
		CacheManagerCustomizer<CacheManager> customer = (cacheManager) -> {
			if (cacheManager instanceof RedisCacheManager) {
				RedisCacheManager redisCacheManager = (RedisCacheManager) cacheManager;
				try {
					// *********************************************************
					// 由于:RedisCacheManager没有相关的方法可以改变value序列化行为,所以,只能通过反射或者自定义RedisCacheManager创建过程
					// 我选择了反射的方式
					// *********************************************************
					Field defaultCacheConfigField = RedisCacheManager.class.getDeclaredField("defaultCacheConfig");
					// 允许私有属性可以被访问
					defaultCacheConfigField.setAccessible(true);

					org.springframework.data.redis.cache.RedisCacheConfiguration oldDefaultCacheConfig = (org.springframework.data.redis.cache.RedisCacheConfiguration) defaultCacheConfigField
							.get(redisCacheManager);
					// 设置为json序列化
					org.springframework.data.redis.cache.RedisCacheConfiguration newDefaultCacheConfig = oldDefaultCacheConfig
							.serializeValuesWith(SerializationPair.fromSerializer(RedisSerializer.json()));

					// 设置RedisCacheManager属性
					defaultCacheConfigField.set(redisCacheManager, newDefaultCacheConfig);
				} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException
						| SecurityException e) {
					logger.error(e.getMessage());
				}
			}
		};
		return customer;
	}
}
