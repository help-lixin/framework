package help.lixin.framework.cache.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConnectionFactoryConfig {

	@Autowired
	private RedisProperties redisProperties;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		List<String> clusterNodes = redisProperties.getCluster().getNodes();
		String passwordAsString = redisProperties.getPassword();
		RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(clusterNodes);
		redisClusterConfiguration.setPassword(RedisPassword.of(passwordAsString));

		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		// 最大空闲连接数, 默认8个
		jedisPoolConfig.setMaxIdle(100);
		// 最大连接数, 默认8个
		jedisPoolConfig.setMaxTotal(500);
		// 最小空闲连接数, 默认0
		jedisPoolConfig.setMinIdle(0);
		// 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,
		// 默认-1
		jedisPoolConfig.setMaxWaitMillis(2000); // 设置2秒
		// 对拿到的connection进行validateObject校验
		jedisPoolConfig.setTestOnBorrow(true);
		return new JedisConnectionFactory(redisClusterConfiguration, jedisPoolConfig);
	}
}
