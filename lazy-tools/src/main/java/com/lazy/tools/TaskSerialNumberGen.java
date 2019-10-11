package com.lazy.tools;

import com.lazy.rest.exception.LogicException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * 任务流水号生成器
 *
 * @author futao
 * Created on 2019/9/18.
 */
@Component
public class TaskSerialNumberGen {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * 获取下一流水号
     * 任务单里的预约单号生成规则 ，yyyyMMdd+五位流水号 例如：2019090900001   共13位数字字符串
     * 流水超过5位(99999)目前不进行约束
     *
     * @return
     */
    public String next(BusinessType businessType) {
        String dateString = formatter.format(LocalDate.now());
        switch (businessType) {
            case ORG_TASK: {
                String nextPK = dateString + "00001";
                String currentKey = "task-serial-number:org:" + dateString;
                if (stringRedisTemplate.opsForValue().setIfAbsent(currentKey, nextPK, 25L, TimeUnit.HOURS)) {
                    return nextPK;
                }
                Long increment = stringRedisTemplate.opsForValue().increment(currentKey, 1L);
                return String.valueOf(increment);
            }
            case MANAGER_TASK: {
                String nextPK = dateString + "00001";
                String currentKey = "task-serial-number:manager:" + dateString;
                if (stringRedisTemplate.opsForValue().setIfAbsent(currentKey, nextPK, 25L, TimeUnit.HOURS)) {
                    return nextPK;
                }
                Long increment = stringRedisTemplate.opsForValue().increment(currentKey, 1L);
                return String.valueOf(increment);
            }
            default:
                throw LogicException.le("不支持的任务序列");
        }
    }


    @Getter
    @AllArgsConstructor
    public enum BusinessType {
        /**
         * 机构端任务
         */
        ORG_TASK("机构端任务"),
        /**
         * 监管端任务
         */
        MANAGER_TASK("监管端任务");
        /**
         * 描述
         */
        private String description;
    }
}
