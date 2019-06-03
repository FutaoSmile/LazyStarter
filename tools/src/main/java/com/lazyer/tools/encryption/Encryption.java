package com.lazyer.tools.encryption;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 加密算法
 *
 * @author futao
 * Created on 2019-05-29.
 */
public class Encryption {
    /**
     * md5  消息摘要算法
     *
     * @param data 要进行摘要的信息
     * @return
     */
    public String md5(String data) {
        return DigestUtils.md5Hex(data);
    }

}
