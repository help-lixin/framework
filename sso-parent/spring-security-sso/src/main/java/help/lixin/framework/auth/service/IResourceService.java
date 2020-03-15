package help.lixin.framework.auth.service;

import java.util.Map;
import java.util.Set;

public interface IResourceService {
	// keyType:String     valueType:Long
	// key:tenantId       value:12345678
	// key:userInfoId     value:12345678
	Set<String> loadResources(Map<String, Object> params) throws IllegalArgumentException;
}
