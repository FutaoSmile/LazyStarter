package com.futao.framework.foundation.utils;

import com.alibaba.fastjson.JSON;
import com.futao.framework.foundation.configuration.HttpMessageConverterConfiguration;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

/**
 * 通用帮助类
 *
 * @author futao
 * Created on 2019-03-20.
 */
public class CommonTools {

    /**
     * 获取32位的UUID
     *
     * @return UUID
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成验证码
     *
     * @param size 长度
     * @return
     */
    public static String verifyNum(int size) {
        int i = 0;
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        while (i < size) {
            i++;
            sb.append(random.nextInt(9));
        }
        return sb.toString();
    }

    /**
     * md5加密
     *
     * @param words 内容
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String md5(String words) {
        if (StringUtils.isNotBlank(words)) {
            MessageDigest md5Digest = null;
            try {
                md5Digest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return new String(Hex.encodeHex(md5Digest.digest(words.getBytes(StandardCharsets.UTF_8))));
        } else {
            return null;
        }
    }

    public static String formatJsonString(String jsonString) {
        try {
            return StringUtils.isEmpty(jsonString) ? "{}" : JSON.toJSONString(JSON.parseObject(jsonString), HttpMessageConverterConfiguration.SERIALIZER_FEATURES);
        } catch (Exception e) {
            return jsonString;
        }
    }

}
