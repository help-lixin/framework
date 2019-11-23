每次执行SQL语句前,需要动态的注入一些属性,以供Mapper.xml使用.
最初想法是想使用过拦截器,但是针对insert/update无法做到,因为insert/update时,开发者传递的可能一个Java Bean.
查询能改造是因为:MyBatis在调用查询时,会把参数变成Map.
有两种解决方案:
1. 把java bean转换成map
2. MyBatis使用的是Ognl表达式解析,所以,要解释时从ThreadLocal获取,并交给Ognl
该方法使用第二种,仅仅改动了(org.apache.ibatis.scripting.xmltags.DynamicContext)

package org.apache.ibatis.scripting.xmltags;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringJoiner;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;

import help.lixin.framework.mybatis.integration.context.VarContext;
import ognl.OgnlContext;
import ognl.OgnlRuntime;
import ognl.PropertyAccessor;

public class DynamicContext {

  public static final String PARAMETER_OBJECT_KEY = "_parameter";
  public static final String DATABASE_ID_KEY = "_databaseId";

  static {
    OgnlRuntime.setPropertyAccessor(ContextMap.class, new ContextAccessor());
  }

  private final ContextMap bindings;
  private final StringJoiner sqlBuilder = new StringJoiner(" ");
  private int uniqueNumber = 0;

  public DynamicContext(Configuration configuration, Object parameterObject) {
    if (parameterObject != null && !(parameterObject instanceof Map)) {
      MetaObject metaObject = configuration.newMetaObject(parameterObject);
      boolean existsTypeHandler = configuration.getTypeHandlerRegistry().hasTypeHandler(parameterObject.getClass());
      bindings = new ContextMap(metaObject, existsTypeHandler);
    } else {
      bindings = new ContextMap(null, false);
    }
    bindings.put(PARAMETER_OBJECT_KEY, parameterObject);
    bindings.put(DATABASE_ID_KEY, configuration.getDatabaseId());
    // ================================以下代码为新增代码====================================
    try {
    	// TODO lixin  2019-11-23 此处使用ThreadLocal
    	// 尝试下去加载,如果都没有依赖,就跳过
		Class.forName("help.lixin.framework.mybatis.integration.context.VarContext");
		Map<String,String> vars = VarContext.vars();
		if(null != vars && !vars.isEmpty()){
			Iterator<Entry<String, String>> it = vars.entrySet().iterator();
			while(it.hasNext()){
				Entry<String, String> entry = it.next();
				bindings.put(entry.getKey(), entry.getValue());
			}
		}
	} catch (ClassNotFoundException ignore) {
	}
  }

  public Map<String, Object> getBindings() {
    return bindings;
  }

  public void bind(String name, Object value) {
    bindings.put(name, value);
  }

  public void appendSql(String sql) {
    sqlBuilder.add(sql);
  }

  public String getSql() {
    return sqlBuilder.toString().trim();
  }

  public int getUniqueNumber() {
    return uniqueNumber++;
  }

  static class ContextMap extends HashMap<String, Object> {
    private static final long serialVersionUID = 2977601501966151582L;
    private final MetaObject parameterMetaObject;
    private final boolean fallbackParameterObject;

    public ContextMap(MetaObject parameterMetaObject, boolean fallbackParameterObject) {
      this.parameterMetaObject = parameterMetaObject;
      this.fallbackParameterObject = fallbackParameterObject;
    }

    @Override
    public Object get(Object key) {
      String strKey = (String) key;
      if (super.containsKey(strKey)) {
        return super.get(strKey);
      }

      if (parameterMetaObject == null) {
        return null;
      }

      if (fallbackParameterObject && !parameterMetaObject.hasGetter(strKey)) {
        return parameterMetaObject.getOriginalObject();
      } else {
        // issue #61 do not modify the context when reading
        return parameterMetaObject.getValue(strKey);
      }
    }
  }

  static class ContextAccessor implements PropertyAccessor {

    @Override
    public Object getProperty(Map context, Object target, Object name) {
      Map map = (Map) target;

      Object result = map.get(name);
      if (map.containsKey(name) || result != null) {
        return result;
      }

      Object parameterObject = map.get(PARAMETER_OBJECT_KEY);
      if (parameterObject instanceof Map) {
        return ((Map)parameterObject).get(name);
      }

      return null;
    }

    @Override
    public void setProperty(Map context, Object target, Object name, Object value) {
      Map<Object, Object> map = (Map<Object, Object>) target;
      map.put(name, value);
    }

    @Override
    public String getSourceAccessor(OgnlContext arg0, Object arg1, Object arg2) {
      return null;
    }

    @Override
    public String getSourceSetter(OgnlContext arg0, Object arg1, Object arg2) {
      return null;
    }
  }
}
