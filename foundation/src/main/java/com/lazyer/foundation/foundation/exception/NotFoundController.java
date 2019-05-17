package com.lazyer.foundation.foundation.exception;

import com.lazyer.foundation.model.RestResult;
import com.lazyer.foundation.utils.I18nTools;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 针对404
 *
 * @author futao
 * Created on 2019-03-25.
 */
@RestController
public class NotFoundController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping(path = "error", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Object error() {
        String message = I18nTools.getMessage("system.not.found");
        return new RestResult<>(false, String.valueOf(HttpServletResponse.SC_NOT_FOUND), message, message);
    }
}
