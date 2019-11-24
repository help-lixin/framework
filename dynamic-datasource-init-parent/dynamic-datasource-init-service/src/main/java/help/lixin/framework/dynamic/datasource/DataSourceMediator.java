package help.lixin.framework.dynamic.datasource;

import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

public class DataSourceMediator {
	private static final ConcurrentHashMap<String, DataSource> DATASOURCES = new ConcurrentHashMap<>();

	public static void addDataSource(String dataSourceName, DataSource dataSource) {
		synchronized (DATASOURCES) {
			DATASOURCES.put(dataSourceName, dataSource);
		}
	}

	public static DataSource getDataSource(String dataSourceName) {
		return DATASOURCES.get(dataSourceName);
	}

	public static synchronized void clean(String dataSourceName) {
		synchronized (DATASOURCES) {
			DataSource dataSource = DATASOURCES.remove(dataSourceName);
			// TODO 是否需要回收?
		}
	}
}
