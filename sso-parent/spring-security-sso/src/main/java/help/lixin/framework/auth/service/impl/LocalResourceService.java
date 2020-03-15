package help.lixin.framework.auth.service.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import help.lixin.framework.auth.constant.Constants;
import help.lixin.framework.auth.service.IResourceService;

/**
 * 读取数据库获得用户对应的URL资源信息
 * 
 * @author lixin
 */
public class LocalResourceService implements IResourceService {

	@Override
	public Set<String> loadResources(Map<String, Object> params) throws IllegalArgumentException {
		Set<String> resourecs = new HashSet<>();
		if (null == params) {
			throw new IllegalArgumentException("参数不能为空");
		}
		if (!params.containsKey(Constants.USER_INFO_ID_KEY)) {
			throw new IllegalArgumentException("用户ID不能为空");
		}
		if (!params.containsKey(Constants.TENANT_ID_KEY)) {
			throw new IllegalArgumentException("租户ID不能为空");
		}
		Long userInfoId = (Long) params.get(Constants.USER_INFO_ID_KEY);
		Long tenantId = (Long) params.get(Constants.TENANT_ID_KEY);
		
		
		resourecs.add("/admin");
		resourecs.add("/user");
		
		return resourecs;
	}
}
