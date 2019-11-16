package help.lixin.framework.config;

import java.net.URL;

public class AppTest {
	public static void main(String[] args) {
		URL url = AppTest.class.getResource("/test.ini");
		Config ini = Config.fromResourcePath(url.getPath());
		String userNamePwd = ini.getSectionProperty("roles", "name");
		System.out.println(userNamePwd);
	}
}