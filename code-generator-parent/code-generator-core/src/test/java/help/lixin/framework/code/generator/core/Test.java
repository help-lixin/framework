package help.lixin.framework.code.generator.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Object.class);
        logger.debug("test hello world");

        String xml = "<project>artifactId>${artifactId}</artifactId><version>${version}</version></project>";
        Properties properties = new Properties();
        properties.put("artifactId", "test");
        properties.put("version", "1.0");

        Pattern pattern = Pattern.compile("\\$\\{(?<varname>.*?)}");
        Matcher matcher = pattern.matcher(xml);
        while (matcher.find()) {
            String varName = matcher.group("varname");
            String varValue = properties.getProperty(varName, null);
            if (null != varValue) {
                xml = xml.replaceAll("\\$\\{" + varName + "\\}", varValue);
            }
        }
        System.out.println(xml);
    }
}
