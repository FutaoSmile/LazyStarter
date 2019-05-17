package com.lazyer.foundation.foundation;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * ApplicationStartEventListener
 *
 * @author futao
 * Created on 2019-05-06.
 */
@Component
@ConfigurationProperties(prefix = "framework")
public class ApplicationStartEventListener implements ApplicationListener<ApplicationStartedEvent> {

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
                    "//              CommonFramework组件加载成功\n" +
                    "//");
        } else {
            System.out.println("CommonFramework组件加载成功\n");
        }

    }

    public boolean isShowBanner() {
        return showBanner;
    }

    public void setShowBanner(boolean showBanner) {
        this.showBanner = showBanner;
    }
}
