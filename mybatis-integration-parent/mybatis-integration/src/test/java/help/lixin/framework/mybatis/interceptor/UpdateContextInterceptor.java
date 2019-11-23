package help.lixin.framework.mybatis.interceptor;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;

@Intercepts({ @Signature(type = ParameterHandler.class, method = "setParameters", args = { PreparedStatement.class }) })
public class UpdateContextInterceptor implements Interceptor {
	public Object intercept(Invocation invocation) throws Throwable {
		Object target = invocation.getTarget();
		// 仅针对参数处理类
		if (target instanceof DefaultParameterHandler) {
			DefaultParameterHandler defaultParameterHandler = (DefaultParameterHandler) target;
			Field boundSqlField = DefaultParameterHandler.class.getDeclaredField("boundSql");
			boundSqlField.setAccessible(Boolean.TRUE);
			BoundSql boundSql = (BoundSql)boundSqlField.get(defaultParameterHandler);
			boundSql.setAdditionalParameter("_db", "fw");
			boundSql.setAdditionalParameter("_tablePrefix", "fw_");
			
			System.out.println(boundSql);
		}
		return invocation.proceed();
	}
}
