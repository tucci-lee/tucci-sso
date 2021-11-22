package cn.tucci.sso.server.core.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author tucci.lee
 */
@Component
public class Message {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private final MessageSource messageSource;

    public Message(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String code, Object[] args, String defaultMessage) {
        return messageSource.getMessage(code, args, defaultMessage, LocaleContextHolder.getLocale());
    }

    public String getMessage(String code, Object[] args) {
        return getMessage(code, args, "no message");
    }

    public String getMessage(String code) {
        return getMessage(code, null);
    }
}
