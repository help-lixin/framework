package help.lixin.framework.code.generator.core.formatter;

import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.CompilationUnitVisitor;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Set;

public class FreemarkerCompilationUnit implements CompilationUnit {
    private final Logger logger = LoggerFactory.getLogger(FreemarkerCompilationUnit.class);

    private String templateFile;

    private FullyQualifiedJavaType type;

    public void setTemplateFile(String templateFile) {
        this.templateFile = templateFile;
    }

    public String getTemplateFile() {
        return templateFile;
    }

    public FreemarkerCompilationUnit(FullyQualifiedJavaType type) {
        this.type = type;
    }

    public String formatter() {
        URL resource = FreemarkerCompilationUnit.class.getClassLoader().getResource(templateFile);
        if (null == resource) {
            logger.warn("resource:[{}] not found.", resource);
        } else {
            String resourcePath = resource.getPath();
            File file = new File(resourcePath);
            String parentDir = file.getParent();

        }
        return null;
    }

    @Override
    public Set<FullyQualifiedJavaType> getImportedTypes() {
        return null;
    }

    @Override
    public Set<String> getStaticImports() {
        return null;
    }

    @Override
    public FullyQualifiedJavaType getType() {
        return type;
    }

    @Override
    public void addImportedType(FullyQualifiedJavaType importedType) {

    }

    @Override
    public void addImportedTypes(Set<FullyQualifiedJavaType> importedTypes) {

    }

    @Override
    public void addStaticImport(String staticImport) {

    }

    @Override
    public void addStaticImports(Set<String> staticImports) {

    }

    @Override
    public void addFileCommentLine(String commentLine) {

    }

    @Override
    public List<String> getFileCommentLines() {
        return null;
    }

    @Override
    public <R> R accept(CompilationUnitVisitor<R> visitor) {
        return null;
    }
}
