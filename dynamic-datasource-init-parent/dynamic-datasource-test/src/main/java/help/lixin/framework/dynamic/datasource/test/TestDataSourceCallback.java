package help.lixin.framework.dynamic.datasource.test;

import java.lang.reflect.Method;

import org.springframework.stereotype.Component;

import help.lixin.framework.dynamic.datasource.DataSourceCallback;

@Component
public class TestDataSourceCallback implements DataSourceCallback {
	@Override
	public String datasource(Class<?> targetClass, Method targetMethod) {
		// 业务自行处理
		return "test";
	}
}
