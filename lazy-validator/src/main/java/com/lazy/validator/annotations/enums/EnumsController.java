package com.lazy.validator.annotations.enums;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lazy.constant.RedisPrefixConstant;
import com.lazy.rest.exception.LogicException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author futao
 * Created on 2019/9/6.
 */
@ConfigurationProperties(prefix = "lazy.validator")
@Profile("dev")
@Slf4j
@RequestMapping
@RestController
public class EnumsController implements ApplicationListener<ContextRefreshedEvent> {

    @GetMapping
    public void index(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }

    @GetMapping("/enums")
    public ArrayList<EnumDescription> enums() {
        String enumsCache = redisTemplate.opsForValue().get(RedisPrefixConstant.VALIDATOR + "enums");
        if (StringUtils.isNotBlank(enumsCache)) {
            return JSON.parseObject(enumsCache, new TypeReference<ArrayList<EnumDescription>>() {
            });
        } else {
            ArrayList<EnumDescription> enumDescriptions = this.loadEnums();
            if (enumDescriptions == null) {
                throw LogicException.le("enums功能未正确加载，请设置枚举类路径lazy.validator.enumPackage");
            }
            return enumDescriptions;
        }
    }

    private String enumPackage;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent applicationEvent) {
        this.loadEnums();
    }

    private ArrayList<EnumDescription> loadEnums() {
        if (StringUtils.isNotBlank(enumPackage)) {
            Reflections reflections = new Reflections(enumPackage);
            Set<Class<? extends IEnum>> subTypesOf = reflections.getSubTypesOf(IEnum.class);
            ArrayList<EnumDescription> enumDescriptions = new ArrayList<>(subTypesOf.size());
            for (Class<? extends IEnum> aClass : subTypesOf) {
                EnumDesc annotation = aClass.getAnnotation(EnumDesc.class);
                String enumDesc = StringUtils.EMPTY;
                if (annotation != null) {
                    enumDesc = annotation.value();
                } else {
                    log.warn("请为枚举类[{}]标记@EnumDesc注解，提示枚举类的用途", aClass.getName());
                }
                EnumDescription e = new EnumDescription();
                enumDescriptions.add(e);
                e.setEnumDesc(enumDesc);
                e.setEnumName(aClass.getName());
                IEnum[] enumConstants = aClass.getEnumConstants();
                ArrayList<NV> nvs = new ArrayList<>(enumConstants.length);
                e.setNvs(nvs);
                for (IEnum enumConstant : enumConstants) {
                    nvs.add(new NV(enumConstant.getType(), enumConstant.getDescription()));
                }
            }
            redisTemplate.opsForValue().set(RedisPrefixConstant.VALIDATOR + "enums", JSON.toJSONString(enumDescriptions));
            log.info("{}枚举值成功加载到redis，请访问/enums查看,{}", StringUtils.repeat("=", 30), StringUtils.repeat("=", 30));
            return enumDescriptions;
        } else {
            log.warn("enums功能未正确加载，请设置枚举类路径lazy.validator.enumPackage");
            return null;
        }
    }

    public String getEnumPackage() {
        return enumPackage;
    }

    public void setEnumPackage(String enumPackage) {
        this.enumPackage = enumPackage;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EnumDescription {
        private String enumName;
        private String enumDesc;
        private List<NV> nvs;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NV {
        private Object status;
        private String description;
    }

}
