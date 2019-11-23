package help.lixin.framework.mybatis.interceptor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
		RowBounds.class, ResultHandler.class }), })
public class QueryContextInjectInterceptor implements Interceptor {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object[] args = invocation.getArgs();
		Method method = invocation.getMethod();
		Object target = invocation.getTarget();
		// 首先判断是否代理了:Executor
		if(target instanceof Executor){
			// 判断是否为查询方法
			if (method.getName().equalsIgnoreCase("query")) {
				// Executor 查询方法(query)签名(第二个为参数)
				/**  <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey cacheKey, BoundSql boundSql) throws SQLException; **/
				/**  <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException; **/
				Object parameter = args[1];
				Map contextParams = null;
				if (null == parameter) {  //如果为空的情况下,需要注入上下文信息. 
					// 从上下文中拿取数据注入
					contextParams = new HashMap<>();
				} else if(parameter instanceof Map){   // 当多个参数或@Param注解时,会被识别为Map
					contextParams = (Map)parameter;
				} else {  //当只有一个参数时:返回的是只有一个参数
					// 判断是否为基本类型 
					// TODO
					// 模拟MyBatis构建参数
					contextParams = new HashMap<>();
					contextParams.put("arg0", parameter);
					contextParams.put("param1", parameter);
				}
				// 上下文中数据
				contextParams.put("_db", "fw");
				contextParams.put("_tablePrefix", "fw_");
				contextParams.put("id", "1");
				args[1] = contextParams;
			} else if ("update".equals(method.getName())) { // 判断是否为更新方法
				// Executor 更新方法(update)签名(第二个为参数)
				/** int update(MappedStatement ms, Object parameter) throws SQLException; **/
				System.out.println("==================================");
			}
		}
		return invocation.proceed();
	}
}
