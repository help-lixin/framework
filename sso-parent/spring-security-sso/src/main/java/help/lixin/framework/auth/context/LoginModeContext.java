package help.lixin.framework.auth.context;

/**
 * 用户的登录方式(用户名/邮箱/手机号码/第三方登录)
 * 
 * @author lixin
 *
 */
public abstract class LoginModeContext {
	private static final ThreadLocal<String> LOGIN_MODE_CONTEXT = new InheritableThreadLocal<String>();

	public static void setMode(String mode) {
		LOGIN_MODE_CONTEXT.set(mode);
	}

	public static String getMode() {
		String mode = LOGIN_MODE_CONTEXT.get();
		return mode;
	}

	public static void clean() {
		LOGIN_MODE_CONTEXT.remove();
	}
}
