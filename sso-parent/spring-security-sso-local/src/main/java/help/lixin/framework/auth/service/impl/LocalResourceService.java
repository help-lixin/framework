package help.lixin.framework.auth.service.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import help.lixin.framework.auth.constant.Constants;
import help.lixin.framework.auth.mapper.UserResourceMapper;
import help.lixin.framework.auth.service.IResourceService;

/**
 * 读取数据库获得用户对应的URL资源信息
 * 
 * @author lixin
 */
public class LocalResourceService implements IResourceService {

	@Autowired
	private UserResourceMapper userResourceMapper;

	@Override
	public Set<String> loadResources(Map<String, Object> params) throws IllegalArgumentException {
		Set<String> resourecs = new HashSet<>();
		if (null == params) {
			throw new IllegalArgumentException("参数不能为空");
		}
		if (!params.containsKey(Constants.USER_INFO_ID_KEY)) {
			throw new IllegalArgumentException("用户ID不能为空");
		}
		Long userInfoId = (Long) params.get(Constants.USER_INFO_ID_KEY);
		Set<String> fetchResources = userResourceMapper.resources(userInfoId);
		if (null != fetchResources && !fetchResources.isEmpty()) {
			resourecs.addAll(fetchResources);
		}
		return resourecs;
	}
}
