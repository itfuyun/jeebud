package com.jeebud.core.web;

import com.jeebud.common.constant.ErrCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Slf4j
@Controller
public class WebErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";
    private static final int NOT_FOUND_CODE = 404;
    private ErrorAttributes errorAttributes;

    @Autowired
    public WebErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    /**
     * Web页面错误处理
     */
    @RequestMapping(value = ERROR_PATH, produces = "text/html")
    public String errorPageHandler(HttpServletRequest request, HttpServletResponse response) {
        int status = response.getStatus();
        switch (status) {
            case 403:
                return "redirect:/err/403";
            case 404:
                return "redirect:/err/404";
            default:
                return "redirect:/err/500";
        }
    }

    /**
     * 除Web页面外的错误处理，比如Json/XML等
     */
    @RequestMapping(value = ERROR_PATH)
    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public RestEntity errorApiHandler(HttpServletRequest request, final Exception ex, final WebRequest req) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        Map<String, Object> attr = this.errorAttributes.getErrorAttributes(req, false);
        int status = getStatus(request);
        if (status == NOT_FOUND_CODE) {
            return RestEntity.failed().code(ErrCodeEnum.NOT_FOUND.getCode()).message(ErrCodeEnum.NOT_FOUND.getMsg());
        }
        return RestEntity.failed().code(ErrCodeEnum.ERROR.getCode()).message(ErrCodeEnum.ERROR.getMsg());
    }

    private int getStatus(HttpServletRequest request) {
        Integer status = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (status != null) {
            return status;
        }
        return 500;
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

}
