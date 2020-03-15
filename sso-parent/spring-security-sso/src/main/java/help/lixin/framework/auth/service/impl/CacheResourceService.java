package help.lixin.framework.auth.service.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import help.lixin.framework.auth.service.IResourceService;

public class CacheResourceService implements IResourceService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private IResourceService delegatorResourceService;

	private long resourceCacheExpireMinutes = 5;

	public void setResourceCacheExpireMinutes(long resourceCacheExpireMinutes) {
		this.resourceCacheExpireMinutes = resourceCacheExpireMinutes;
	}

	public long getResourceCacheExpireMinutes() {
		return resourceCacheExpireMinutes;
	}

	public void setDelegatorResourceService(IResourceService delegatorResourceService) {
		this.delegatorResourceService = delegatorResourceService;
	}

	public IResourceService getDelegatorResourceService() {
		return delegatorResourceService;
	}

	private LoadingCache<Map<String, Object>, Set<String>> cache = CacheBuilder.newBuilder() //
			.expireAfterAccess(resourceCacheExpireMinutes, TimeUnit.MINUTES) //
			.build(new CacheLoader<Map<String, Object>, Set<String>>() {
				public Set<String> load(Map<String, Object> params) throws Exception {
					logger.info("cache not found....ready db load user resources");
					return delegatorResourceService.loadResources(params);
				}
			});

	@Override
	public Set<String> loadResources(Map<String, Object> params) throws IllegalArgumentException {
		Set<String> resources = new HashSet<>();
		try {
			resources.addAll(cache.get(params));
		} catch (ExecutionException e) {
		}
		return resources;
	}
}
