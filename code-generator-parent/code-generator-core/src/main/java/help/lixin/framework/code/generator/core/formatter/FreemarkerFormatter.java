package help.lixin.framework.code.generator.core.formatter;

import org.mybatis.generator.api.JavaFormatter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.Context;

public class FreemarkerFormatter implements JavaFormatter, CompilationUnitVisitor<String> {
    private Context context;

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public String getFormattedContent(CompilationUnit compilationUnit) {
        if (compilationUnit instanceof FreemarkerCompilationUnit) {
            FreemarkerCompilationUnit freemarkerCompilationUnit = (FreemarkerCompilationUnit) compilationUnit;
            return freemarkerCompilationUnit.formatter();
        } else {
            return compilationUnit.accept(this);
        }
    }

    @Override
    public String visit(TopLevelClass topLevelClass) {
        return null;
    }

    @Override
    public String visit(TopLevelEnumeration topLevelEnumeration) {
        return null;
    }

    @Override
    public String visit(Interface topLevelInterface) {
        return null;
    }
}
