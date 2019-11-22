package help.lixin.framework.dynamic.datasource.context;

public abstract class DataSourceContext {
	private static final ThreadLocal<String> DATA_SOURCE_CONTEXT = new InheritableThreadLocal<String>();

	public static void datasource(String dataSroueName) {
		DATA_SOURCE_CONTEXT.set(dataSroueName);
	}

	public static String datasource() {
		return DATA_SOURCE_CONTEXT.get();
	}

	public static void clean() {
		DATA_SOURCE_CONTEXT.remove();
	}
}
