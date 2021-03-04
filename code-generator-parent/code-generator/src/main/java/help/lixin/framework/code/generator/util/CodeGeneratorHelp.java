package help.lixin.framework.code.generator.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public abstract class CodeGeneratorHelp {
    private static final String EXT_NAME = ".jar";

    /**
     * 根据路径加载配置项
     *
     * @param extPropertiesFile
     * @return
     */
    public static Properties loadExtProperties(String extPropertiesFile) {
        Properties properties = new Properties();
        try {
            URL resourceUrl = new URL(extPropertiesFile);
            InputStream inputStream = resourceUrl.openConnection()
                    .getInputStream();
            properties.load(inputStream);
        } catch (MalformedURLException ignore) {
            // TODO logger
        } catch (IOException e) {
            // TODO logger
        }
        return properties;
    }

    /**
     * 根据路径,获取第三方的jar包
     *
     * @param thirdPartyJarPath
     * @return
     */
    public static List<String> loadThirdPartyJar(String thirdPartyJarPath) {
        List<String> jars = new ArrayList<String>(0);
        Path path = Paths.get(thirdPartyJarPath);
        File[] files = path.toFile().listFiles((dir, name) -> name.endsWith(EXT_NAME));
        List<String> tempJars = Arrays.asList(files).stream().map(t -> t.getPath()).collect(Collectors.toList());
        jars.addAll(tempJars);
        return jars;
    }
}
