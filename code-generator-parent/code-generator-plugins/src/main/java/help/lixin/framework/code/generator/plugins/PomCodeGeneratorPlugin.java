package help.lixin.framework.code.generator.plugins;

import help.lixin.framework.code.generator.core.annotation.Property;
import help.lixin.framework.code.generator.core.annotation.Propertys;
import help.lixin.framework.code.generator.core.util.ParseContext;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.XmlFormatter;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.render.DocumentRenderer;
import org.mybatis.generator.config.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Propertys(propertys = {
        @Property(name = "targetProject", value = "${pom.targetProject}", desc = "生成pom.xml路径"),
        @Property(name = "groupId", value = "${pom.groupId}"),
        @Property(name = "artifactId", value = "${pom.artifactId}"),
        @Property(name = "version", value = "${pom.version}"),
        @Property(name = "pomXmlTemplateFile", value = "${pom.xml.templatePath}", desc = "pom文件模板(比如:/template/pom.xml)"),
        @Property(name = "generatorPomXmlFileName", value = "pom.xml", desc = "生成后的xml文件")
})
public class PomCodeGeneratorPlugin extends PluginAdapter {

    private final Logger logger = LoggerFactory.getLogger(PomCodeGeneratorPlugin.class);

    @Override
    public boolean validate(List<String> warnings) {
        Properties properties = ParseContext.parse(context, this.getClass());
        String pomXmlTemplateFile = properties.getProperty("pomXmlTemplateFile", null);
        URL resource = PomCodeGeneratorPlugin.class.getClassLoader().getResource(pomXmlTemplateFile);
        if (null == resource) {
            warnings.add("Not Found pom template:" + pomXmlTemplateFile);
            logger.warn("ignore Plugin PomCodeGeneratorPlugin,Not Found pom template:[{}]", pomXmlTemplateFile);
            return false;
        }
        return true;
    }

    @Override
    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles() {
        List<GeneratedXmlFile> answer = new ArrayList<>(1);
        Properties properties = ParseContext.parse(context, this.getClass());
        // 添加全局的配置项
        properties.putAll(getProperties());

        String targetProject = properties.getProperty("targetProject", null);
        String groupId = properties.getProperty("groupId", null);
        String artifactId = properties.getProperty("artifactId", null);
        String version = properties.getProperty("version", null);
        String pomXmlTemplateFile = properties.getProperty("pomXmlTemplateFile", null);
        String generatorPomXmlFileName = properties.getProperty("generatorPomXmlFileName");
        if (null == pomXmlTemplateFile || null == groupId || null == artifactId || null == version) {
            logger.warn("ignore Plugin PomCodeGeneratorPlugin");
            return answer;
        }

        PomDocument document = new PomDocument();
        document.setTemplateXmlFile(pomXmlTemplateFile);
        document.setProperties(properties);

        PomXmlFormatter formatter = new PomXmlFormatter();
        GeneratedXmlFile pomXmlFile = new GeneratedXmlFile(document, generatorPomXmlFileName, "", targetProject, true, formatter);
        answer.add(pomXmlFile);
        return answer;
    }
}

// 自定义Document
class PomDocument extends Document {
    private String templateXmlFile;
    private Properties properties;

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setTemplateXmlFile(String templateXmlFile) {
        this.templateXmlFile = templateXmlFile;
    }

    public String getTemplateXmlFile() {
        return templateXmlFile;
    }

    public String format() {
        String xmlConent = null;
        try {
            // 1. 解析XML
            xmlConent = readToString(templateXmlFile);
            // 2. 转换变量
            xmlConent = processVar(xmlConent);
            return xmlConent;
        } catch (Exception ignore) {
        }
        return null;
    }

    public String processVar(String xmlContent) {
        Pattern pattern = Pattern.compile("\\$\\{(?<varname>.*?)}");
        Matcher matcher = pattern.matcher(xmlContent);
        while (matcher.find()) {
            String varName = matcher.group("varname");
            String varValue = properties.getProperty(varName, null);
            if (null != varValue) {
                xmlContent = xmlContent.replaceAll("\\$\\{" + varName + "\\}", varValue);
            }
        }
        return xmlContent;
    }

    public String readToString(String relativeFileName) throws Exception {
        String encoding = "UTF-8";
        URL resource = PomCodeGeneratorPlugin.class.getClassLoader().getResource(relativeFileName);
        String fileFullPath = null;
        if (null != resource) {
            fileFullPath = resource.getPath();
        }
        File file = new File(fileFullPath);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        FileInputStream in = new FileInputStream(file);
        in.read(filecontent);
        in.close();
        return new String(filecontent, encoding);
    }

}

// 自定义格式化类
class PomXmlFormatter implements XmlFormatter {
    private Context context;

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public String getFormattedContent(Document document) {
        if (document instanceof PomDocument) {
            PomDocument pomDocument = (PomDocument) document;
            String text = pomDocument.format();
            return text;
        } else {
            return new DocumentRenderer().render(document);
        }
    }
}