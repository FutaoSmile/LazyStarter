package com.lazy.validator;

import com.lazy.rest.utils.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.SmartValidator;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.executable.ExecutableValidator;

/**
 * @author futao
 * Created on 2019/11/1.
 */
@ComponentScan("com.lazy")
@Configuration
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NormalTest.class)
public class NormalTest {

    public void method1(
            @NotEmpty(message = "不为空")
                    String name,
            @Positive(message = "必须大于0")
                    int age) {
        System.out.println("method run....");
    }

    @Test
    public void test1() {
        SmartValidator validator = SpringContextHolder.getBean(SmartValidator.class);
        ExecutableValidator bean = SpringContextHolder.getBean(ExecutableValidator.class);
        System.out.println(bean);

    }
}
