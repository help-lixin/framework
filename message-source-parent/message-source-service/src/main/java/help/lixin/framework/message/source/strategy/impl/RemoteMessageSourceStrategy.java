package help.lixin.framework.message.source.strategy.impl;

import help.lixin.framework.message.source.strategy.AbstractMessageSourceStrategy;
import help.lixin.framework.message.source.strategy.MessageSourceStrategy;

import java.text.MessageFormat;
import java.util.Locale;

/**
 * 基于远程文件实现国际化
 */
public class RemoteMessageSourceStrategy extends AbstractMessageSourceStrategy implements MessageSourceStrategy {

    // 远程URL
    private String remoteUrl;
    // 项目名称( remoteUrl + projectName )
    private String projectName;


    @Override
    public MessageFormat resolveCode(String code, Locale locale) {
        return null;
    }
}
