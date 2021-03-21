package help.lixin.framework.message.source;

import help.lixin.framework.message.source.strategy.MessageSourceStrategy;
import org.springframework.context.support.AbstractMessageSource;

import java.text.MessageFormat;
import java.util.Locale;


public class FacdeMessageSource extends AbstractMessageSource {

    private MessageSourceStrategy messageSourceStrategy;

    public void setMessageSourceStrategy(MessageSourceStrategy messageSourceStrategy) {
        this.messageSourceStrategy = messageSourceStrategy;
    }

    public MessageSourceStrategy getMessageSourceStrategy() {
        return messageSourceStrategy;
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        return messageSourceStrategy.resolveCode(code, locale);
    }
}
