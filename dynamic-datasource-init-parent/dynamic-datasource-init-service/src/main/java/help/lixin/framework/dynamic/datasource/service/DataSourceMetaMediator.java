package help.lixin.framework.dynamic.datasource.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataSourceMetaMediator {
	private static final Map<String, DataSourceParams> DATA_SOURCE_METAS = new ConcurrentHashMap<>();

	public static void addMeta(String dataSourceName, DataSourceParams dataSourceFields) {
		synchronized (DATA_SOURCE_METAS) {
			if (DATA_SOURCE_METAS.get(dataSourceName) == null) {
				DATA_SOURCE_METAS.put(dataSourceName, dataSourceFields);
			}
		}
	}

	public static DataSourceParams getMeta(String dataSourceName) {
		return DATA_SOURCE_METAS.get(dataSourceName);
	}

	public static void remote(String dataSourceName) {
		synchronized (DATA_SOURCE_METAS) {
			DATA_SOURCE_METAS.remove(dataSourceName);
		}
	}
}
