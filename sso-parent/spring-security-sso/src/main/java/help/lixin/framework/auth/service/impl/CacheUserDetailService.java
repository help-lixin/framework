package help.lixin.framework.auth.service.impl;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import help.lixin.framework.auth.service.IUserDetailService;

public class CacheUserDetailService implements IUserDetailService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private IUserDetailService userDetailService;
	
	private LoadingCache<Map<String, Object>, UserDetails> cache = CacheBuilder.newBuilder() //
			.maximumSize(1000) //
			.build(new CacheLoader<Map<String, Object>, UserDetails>() {
				public UserDetails load(Map<String, Object> params) throws Exception {
					logger.info("cache not found....ready db load");
					return userDetailService.loadUser(params);
				}
			});

	public void setUserDetailService(IUserDetailService userDetailService) {
		this.userDetailService = userDetailService;
	}

	public IUserDetailService getUserDetailService() {
		return userDetailService;
	}

	@Override
	public UserDetails loadUser(Map<String, Object> params) throws UsernameNotFoundException {
		try {
			return cache.get(params);
		} catch (ExecutionException e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
	}
}
