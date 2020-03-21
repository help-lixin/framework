package help.lixin.framework.auth.mapper;

import java.util.Set;

/**
 * 用户拥有哪些资源
 * 
 * @author lixin
 *
 */
public interface UserResourceMapper {
	
	/**
	 * 根据用户ID获得所有的资源.
	 * 
	 * @param userId
	 * @return
	 */
	Set<String> resources(Long userId);
}
