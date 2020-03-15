package help.lixin.framework.auth.service.impl;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

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

	private IUserDetailService delegatorUserDetailService;

	private long userCacheExpireMinutes = 5;
	
	public void setUserCacheExpireMinutes(long userCacheExpireMinutes) {
		this.userCacheExpireMinutes = userCacheExpireMinutes;
	}
	
	public long getUserCacheExpireMinutes() {
		return userCacheExpireMinutes;
	}

	public void setDelegatorUserDetailService(IUserDetailService delegatorUserDetailService) {
		this.delegatorUserDetailService = delegatorUserDetailService;
	}

	public IUserDetailService getDelegatorUserDetailService() {
		return delegatorUserDetailService;
	}

	private LoadingCache<Map<String, Object>, UserDetails> cache = CacheBuilder.newBuilder() //
			// 2分钟没有再次使用,则重新失效.
			.expireAfterAccess(userCacheExpireMinutes, TimeUnit.MINUTES) //
			.build(new CacheLoader<Map<String, Object>, UserDetails>() {
				public UserDetails load(Map<String, Object> params) throws Exception {
					logger.info("cache not found....ready db load userDetail");
					return delegatorUserDetailService.loadUser(params);
				}
			});

	@Override
	public UserDetails loadUser(Map<String, Object> params) throws UsernameNotFoundException {
		try {
			return cache.get(params);
		} catch (ExecutionException e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
	}
}
