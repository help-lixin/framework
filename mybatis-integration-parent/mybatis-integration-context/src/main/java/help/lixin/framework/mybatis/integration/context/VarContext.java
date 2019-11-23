package help.lixin.framework.mybatis.integration.context;

import java.util.HashMap;
import java.util.Map;

public abstract class VarContext {
	private static final ThreadLocal<Map<String, String>> VAR_CONTEXT = new InheritableThreadLocal<Map<String, String>>();

	public static class Builder {
		private Map<String, String> varsTemp = new HashMap<String, String>();

		public Builder var(String key, String value) {
			varsTemp.put(key, value);
			return this;
		}

		public Builder var(Map<String, String> vars) {
			varsTemp.putAll(vars);
			return this;
		}

		public Map<String, String> bind() {
			vars(varsTemp);
			return varsTemp;
		}
	}

	static void vars(Map<String, String> vars) {
		VAR_CONTEXT.set(vars);
	}

	public static Map<String, String> vars() {
		return VAR_CONTEXT.get();
	}

	public static void clean() {
		VAR_CONTEXT.remove();
	}
}
