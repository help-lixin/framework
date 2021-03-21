package help.lixin.framework.message.source.strategy.impl;

import help.lixin.framework.message.source.strategy.AbstractMessageSourceStrategy;
import help.lixin.framework.message.source.strategy.MessageSourceStrategy;

import java.text.MessageFormat;
import java.util.Locale;

/**
 * 基于文件实现国际化
 */
public class FileMessageSourceStrategy extends AbstractMessageSourceStrategy implements MessageSourceStrategy {

    // 所在目录
    private String dirPath;

    // 项目名称(dirPath + projectName)
    private String projectName;


    public FileMessageSourceStrategy(String dirPath, String projectName) {
        this.dirPath = dirPath;
        this.projectName = projectName;
    }


    @Override
    public MessageFormat resolveCode(String code, Locale locale) {
        String language = locale.getLanguage();
        return null;
    }
}
