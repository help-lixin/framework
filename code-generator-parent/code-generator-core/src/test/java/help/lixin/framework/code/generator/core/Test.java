package help.lixin.framework.code.generator.core;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
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
        System.out.println();
//        Iterator<Map.Entry<Object, Object>> iterator = properties.entrySet().iterator();
//        while (iterator.hasNext()) {
//            Map.Entry<Object, Object> entry = iterator.next();
//            String key = entry.getKey().toString();
//            String value = entry.getValue().toString();
//            String pattern = "\\$\\{(?<" + key + ">.*?)}";
//            xml = xml.replaceAll(pattern, value);
//        }
    }
}
