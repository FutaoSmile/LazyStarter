package com.lazy.tools.log;

import ch.qos.logback.classic.pattern.MessageConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.helpers.MessageFormatter;

import java.util.stream.Stream;

/**
 * @author futao
 * Created on 2019/10/23.
 */
@Slf4j
public class LogBackToStringLayout extends MessageConverter {
    @Override
    public String convert(ILoggingEvent event) {
        try {
            Object[] argumentArray = event.getArgumentArray();
            if (ArrayUtils.isEmpty(argumentArray)) {
                return event.getMessage();
            }
            return MessageFormatter.arrayFormat(event.getMessage(), Stream.of(argumentArray).map(JSON::toJSONString).toArray()).getMessage();
        } catch (Exception e) {
            log.error("toString时发生异常", e);
            return event.getMessage();
        }
    }
}
