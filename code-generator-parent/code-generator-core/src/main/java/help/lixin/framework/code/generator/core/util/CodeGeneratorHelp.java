package help.lixin.framework.code.generator.core.util;

import help.lixin.framework.code.generator.core.CodeGeneratorController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger logger = LoggerFactory.getLogger(CodeGeneratorHelp.class);

    private static final String EXT_NAME = ".jar";

    /**
     * 根据路径加载配置项
     *
     * @param extPropertiesFile
     * @return
     */
    public static Properties loadExtProperties(String extPropertiesFile) {
        Properties properties = new Properties();
        boolean isException = false;
        try {
            URL resource = CodeGeneratorHelp.class.getClassLoader().getResource(extPropertiesFile);
            if (logger.isDebugEnabled()) {
                logger.debug("load resource:[{}]", resource);
            }
            if (null != resource) {
                InputStream inputStream = resource.openConnection()
                        .getInputStream();
                properties.load(inputStream);
            }
        } catch (MalformedURLException ignore) {
            isException = true;
            logger.error("load resource[{}] error:[{}]", extPropertiesFile, ignore);
        } catch (IOException e) {
            isException = true;
            logger.error("load resource[{}] error:[{}]", extPropertiesFile, e);
        }
        if (isException) {
            logger.warn("ignore load resource[{}]", extPropertiesFile);
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
        if (logger.isDebugEnabled()) {
            logger.debug("load third part jars:[{}]", tempJars);
        }
        return jars;
    }
}
