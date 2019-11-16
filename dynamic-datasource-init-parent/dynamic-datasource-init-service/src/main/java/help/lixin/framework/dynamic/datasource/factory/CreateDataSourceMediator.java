package help.lixin.framework.dynamic.datasource.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import help.lixin.framework.dynamic.datasource.service.DataSourceType;

public class CreateDataSourceMediator {
	private static final Map<String, ICreateDataSource> DATA_SOURCE_FACTORY = new ConcurrentHashMap<>();

	static {
		DATA_SOURCE_FACTORY.put(DataSourceType.DRUID.name(), new DruidCreateDataSource());
	}

	public static void register(String dbType, ICreateDataSource createDataSourceImpl) {
		synchronized (DATA_SOURCE_FACTORY) {
			if (null == DATA_SOURCE_FACTORY.get(dbType)) {
				DATA_SOURCE_FACTORY.put(dbType, createDataSourceImpl);
			}
		}
	}

	public static ICreateDataSource getCreateDataSource(String dbType) {
		return DATA_SOURCE_FACTORY.get(dbType);
	}

}
