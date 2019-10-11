package com.lazy.constant;

import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author futao
 * Created on 2018/9/19-14:50.
 */
public final class Constant {
    /**
     * session中存放登陆用户的id的session key
     */
    public static final String LOGIN_USER_SESSION_KEY = "userLoginUserId";

    /**
     * UTF-8编码
     */
    public static final String UTF8_ENCODE = "UTF-8";

    /**
     * rocket mq 发送邮件的 topic
     */
    public static final String ROCKET_MQ_TOPIC_MAIL = "topic_mail";

    /**
     * rocket mq 发送邮件-注册邮件的tag
     */
    public static final String ROCKET_MQ_TAG_MAIL_REGISTER = "tag_mail_register";


    public static class RequestConst {
        public static final int CONNECTION_REQUEST_TIMEOUT = 5000;
        public static final int CONNECT_TIMEOUT = 5000;
        public static final int SOCKET_TIMEOUT = 5000;
    }

    /**
     * fastJson配置
     */
    public static final SerializerFeature[] SERIALIZER_FEATURES = new SerializerFeature[]{
            SerializerFeature.PrettyFormat
            , SerializerFeature.SkipTransientField
            , SerializerFeature.WriteEnumUsingName
            , SerializerFeature.WriteDateUseDateFormat
            , SerializerFeature.WriteNullStringAsEmpty
            , SerializerFeature.WriteNullListAsEmpty
            , SerializerFeature.WriteMapNullValue
            , SerializerFeature.DisableCircularReferenceDetect
    };

}
