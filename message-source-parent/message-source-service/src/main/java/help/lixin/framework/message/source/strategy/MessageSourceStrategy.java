package help.lixin.framework.message.source.strategy;


import java.text.MessageFormat;
import java.util.Locale;

/**
 * 国际化策略
 */
public interface MessageSourceStrategy {
    MessageFormat resolveCode(String code, Locale locale);
}
