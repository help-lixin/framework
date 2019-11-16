package help.lixin.framework.dynamic.datasource.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataSourceParams {
	private String dataSourceName;
	private DataSourceType dataSourceType = DataSourceType.DRUID;

	private String driverClassName;
	private String url;
	private String username;
	private String password;
	private String filters;

	private int maxActive = 20;
	private int initialSize = 1;
	private int maxWait = 60000;
	private int minIdle = 1;

	private boolean removeAbandoned = Boolean.TRUE;
	private long timeBetweenEvictionRunsMillis = 60000;
	private long minEvictableIdleTimeMillis = 300000;

	private boolean testWhileIdle = Boolean.TRUE;
	private boolean testOnBorrow = Boolean.FALSE;
	private boolean testOnReturn = Boolean.FALSE;

	private boolean poolPreparedStatements = Boolean.TRUE;
	private int maxOpenPreparedStatements = 20;

	private String validationQuery = "SELECT 1";

	private List<String> connectionInitSqls = new ArrayList<>();

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	private Map<String, Object> others = new HashMap<String, Object>();

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public long getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}

	public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	public long getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}

	public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	public boolean isTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public boolean isTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public boolean isPoolPreparedStatements() {
		return poolPreparedStatements;
	}

	public void setPoolPreparedStatements(boolean poolPreparedStatements) {
		this.poolPreparedStatements = poolPreparedStatements;
	}

	public int getMaxOpenPreparedStatements() {
		return maxOpenPreparedStatements;
	}

	public void setMaxOpenPreparedStatements(int maxOpenPreparedStatements) {
		this.maxOpenPreparedStatements = maxOpenPreparedStatements;
	}

	public String getValidationQuery() {
		return validationQuery;
	}

	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public DataSourceType getDataSourceType() {
		return dataSourceType;
	}

	public void setDataSourceType(DataSourceType dataSourceType) {
		this.dataSourceType = dataSourceType;
	}

	public Map<String, Object> getOthers() {
		return others;
	}

	public void setOthers(Map<String, Object> others) {
		if (null != others) {
			this.others = others;
		}
	}

	public void addOther(String key, Object value) {
		this.others.put(key, value);
	}

	public List<String> getConnectionInitSqls() {
		return connectionInitSqls;
	}

	public void setConnectionInitSqls(List<String> connectionInitSqls) {
		this.connectionInitSqls = connectionInitSqls;
	}

	public boolean isRemoveAbandoned() {
		return removeAbandoned;
	}

	public void setRemoveAbandoned(boolean removeAbandoned) {
		this.removeAbandoned = removeAbandoned;
	}
}
