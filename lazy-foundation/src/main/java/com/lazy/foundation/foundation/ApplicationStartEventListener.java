package com.lazy.foundation.foundation;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ApplicationStartEventListener
 *
 * @author futao
 * Created on 2019-05-06.
 */
@Slf4j
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "lazyer.foundation")
public class ApplicationStartEventListener implements ApplicationListener<ApplicationStartedEvent> {
    /**
     * 是否显示banner
     */
    private boolean showBanner = true;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        if (showBanner) {
            System.out.println("//\n" +
                    "//                       _oo0oo_\n" +
                    "//                      o8888888o\n" +
                    "//                      88\" . \"88\n" +
                    "//                      (| -_- |)\n" +
                    "//                      0\\  =  /0\n" +
                    "//                    ___/`---'\\___\n" +
                    "//                  .' \\\\|     |// '.\n" +
                    "//                 / \\\\|||  :  |||// \\\n" +
                    "//                / _||||| -:- |||||- \\\n" +
                    "//               |   | \\\\\\  -  /// |   |\n" +
                    "//               | \\_|  ''\\---/''  |_/ |\n" +
                    "//               \\  .-\\__  '-'  ___/-. /\n" +
                    "//             ___'. .'  /--.--\\  `. .'___\n" +
                    "//          .\"\" '<  `.___\\_<|>_/___.' >' \"\".\n" +
                    "//         | | :  `- \\`.;`\\ _ /`;.`/ - ` : | |\n" +
                    "//         \\  \\ `_.   \\_ __\\ /__ _/   .-` /  /\n" +
                    "//     =====`-.____`.___ \\_____/___.-`___.-'=====\n" +
                    "//                       `=---='//\n" +
                    "//\n" +
                    "//     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                    "//\n" +
                    "//               佛祖保佑         永无BUG\n" +
                    "//              lazyer组件加载成功\n" +
                    "//");
        } else {
            log.info(StringUtils.repeat("=", 10) + "lazyer组件加载成功" + StringUtils.repeat("=", 10));
        }
    }

    /**
     * 核心线程数量
     */
    private int corePoolSize = 4;
    /**
     * 最大线程数量
     */
    private int maximumPoolSize = 10;
    /**
     * 非core线程存活时间
     */
    private int keepAliveTimeSecond;
    /**
     * 可以被阻塞的消息数量
     */
    private int blockingMsgSize = 1024;

    /**
     * 线程前缀
     */
    private String threadPrefix = "lazyer-ty-";

    @Primary
    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean(Executor.class)
    public ThreadPoolExecutor executor() {
        AtomicInteger threadNum = new AtomicInteger(0);
        return new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTimeSecond,
                TimeUnit.SECONDS,
                //可被阻塞的消息数量
                new LinkedBlockingQueue<>(blockingMsgSize),
                (r) -> new Thread(r, String.format(threadPrefix + "%s", threadNum.getAndIncrement())),
                (r, e) -> log.error("线程池ThreadPoolExecutor发生异常{}", e));
    }
}
