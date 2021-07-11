package help.lixin.framework.cache.service;

import java.util.List;

import help.lixin.framework.cache.entity.User;

public interface IHelloService {
	List<User> findByTenantId(Long tenantId);

	Boolean add(Long tenantId, User user);
}
