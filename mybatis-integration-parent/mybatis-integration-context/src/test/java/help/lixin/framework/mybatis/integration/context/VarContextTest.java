package help.lixin.framework.mybatis.integration.context;

import help.lixin.framework.mybatis.integration.context.VarContext;

public class VarContextTest {
	public static void main(String[] args) {
		VarContext.Builder builder1 = new VarContext.Builder().var("a", "a");
		VarContext.Builder builder2 = new VarContext.Builder().var("b", "b");
		System.out.println();
	}
}
