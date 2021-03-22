package help.lixin.framework.module;

import help.lixin.framework.module.PluginDesc;
import help.lixin.framework.module.parse.ModuleXMLParse;

public class PluginParseTest {
    public static void main(String[] args) {
        String xmlFilePath = "/Users/lixin/FwRepository/framework/module-parent/module-core/src/test/resources/module.xml";
        ModuleXMLParse parse = new ModuleXMLParse(xmlFilePath);
        PluginDesc pluginDesc = parse.parse();
        System.out.println(pluginDesc);
    }
}
