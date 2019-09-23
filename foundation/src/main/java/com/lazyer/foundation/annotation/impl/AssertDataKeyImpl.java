package com.lazyer.foundation.annotation.impl;

import com.lazyer.foundation.annotation.AssertDataKey;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author futao
 * Created on 2019/9/23.
 */
public class AssertDataKeyImpl implements ConstraintValidator<AssertDataKey, Object> {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        AssertDataKey assertDataKey = (AssertDataKey) ((ConstraintValidatorContextImpl) context).getConstraintDescriptor().getAnnotation();
        if (assertDataKey.nullable() == true && value == null) {
            return true;
        }
        String tableName = assertDataKey.tableName();
        String keyColumn = assertDataKey.keyColumn();
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("data", value);
        AtomicInteger result = new AtomicInteger(0);
        String sql = "select " + keyColumn + " from " + tableName + " where " + keyColumn + " = :data";
        jdbcTemplate.query(sql, sqlParameterSource, (var1) -> {
            result.set(var1.getRow());
        });
        return result.intValue() > 0;
    }
}
