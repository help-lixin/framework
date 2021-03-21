package help.lixin.framework.module.parse;

import help.lixin.framework.module.PluginDesc;
import help.lixin.framework.module.exception.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class ModuleXMLParse {
    private final String xmlFilePath;

    public ModuleXMLParse(String xmlFilePath) {
        this.xmlFilePath = xmlFilePath;
    }

    public PluginDesc parse() {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        try {
            PluginDesc pluginDesc = new PluginDesc();
            //获取解析器
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            //将内存中的XML解析为Document对象
            Document dom = builder.parse(xmlFilePath);
            Element root = dom.getDocumentElement();
            String tag = root.getTagName();
            switch (tag) {
                case "module":
                    processPlugin(pluginDesc, root);
                    break;
                default:
                    break;
            }
            return pluginDesc;
        } catch (ParserConfigurationException e) {
            throw new ParseException(e.getMessage());
        } catch (IOException e) {
            throw new ParseException(e.getMessage());
        } catch (SAXException e) {
            throw new ParseException(e.getMessage());
        }
    }

    public void processPlugin(PluginDesc context, Element element) {
        String name = element.getAttribute("name");
        String version = element.getAttribute("version");
        String desc = element.getAttribute("desc");
        String enabled = element.getAttribute("enabled");
        String clazz = element.getAttribute("class");
        String propertiesFile = element.getAttribute("properties-file");
        context.setModule(name);
        context.setVersion(version);
        context.setDesc(desc);
        if(null != enabled){
            context.setEnabled(Boolean.parseBoolean(enabled));
        }
        context.setPluginClass(clazz);
        context.setPropertiesFile(propertiesFile);
    }
}

