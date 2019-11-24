package help.lixin.framework.dynamic.datasource;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import help.lixin.framework.dynamic.datasource.service.IDataSourceMetaService;

public class DataSourceMetaServiceFactoryBean
		implements ApplicationContextAware, FactoryBean<Collection<IDataSourceMetaService>> {

	private ApplicationContext applicationContext;

	@Override
	public Collection<IDataSourceMetaService> getObject() throws Exception {
		if (null != applicationContext) {
			Map<String, IDataSourceMetaService> dataSourceMetaServices = applicationContext
					.getBeansOfType(IDataSourceMetaService.class);
			if (null != dataSourceMetaServices && !dataSourceMetaServices.isEmpty()) {
				return dataSourceMetaServices.values();
			}
		}
		return null;
	}

	@Override
	public Class<?> getObjectType() {
		return Collection.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
