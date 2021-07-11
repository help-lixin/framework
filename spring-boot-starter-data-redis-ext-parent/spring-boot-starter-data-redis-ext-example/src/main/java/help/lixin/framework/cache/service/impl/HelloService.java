package help.lixin.framework.cache.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import help.lixin.framework.cache.annotation.CacheableExt;
import help.lixin.framework.cache.entity.User;
import help.lixin.framework.cache.service.IHelloService;

@Service
// 一定要加上自定义的:cacheResolver
@CacheConfig(cacheResolver = "customCacheResolver")
public class HelloService implements IHelloService {
	private Logger logger = LoggerFactory.getLogger(HelloService.class);

	private final Map<Long, List<User>> datas = new ConcurrentHashMap<Long, List<User>>();

	public HelloService() {
		init();
	}

	private void init() {
		Long tenantId = 1L;
		List<User> users = new ArrayList<User>();
		users.add(new User(1L, "张三"));
		users.add(new User(2L, "李四"));
		users.add(new User(3L, "王五"));
		users.add(new User(4L, "赵六"));
		datas.put(tenantId, users);
	}

	// 查询全部都入缓存,key=tenantId
	@help.lixin.framework.cache.annotation.Cacheable(cacheNames = "users" , ttl = "PT30s",exts = {
			@CacheableExt(key = "a",value = "aaaa"),
			@CacheableExt(key = "b",value = "bbbbb")
	})
	public List<User> findByTenantId(Long tenantId) {
		return datas.get(tenantId);
	}

	// 有添加操作时,仅清除:tenantId % 2 == 0的缓存信息
	@CacheEvict(cacheNames = "users", key="#tenantId" , condition = "#tenantId % 2 == 0", beforeInvocation = true)
	public Boolean add(Long tenantId, User user) {
		List<User> users = new ArrayList<User>();
		users.add(user);
		if (!datas.containsKey(tenantId)) {
			datas.put(tenantId, users);
		} else {
			datas.get(tenantId).addAll(users);
		}
		return true;
	}
}
