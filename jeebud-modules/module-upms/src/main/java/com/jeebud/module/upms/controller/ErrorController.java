package com.jeebud.module.upms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Controller
@RequestMapping("/err")
public class ErrorController {
    /**
     * 模板前缀
     */
    private static final String TPL_PATH = "admin/error";

    /**
     * 无权限页面
     *
     * @return
     */
    @GetMapping("/403")
    public String err403() {
        return TPL_PATH + "/403";
    }

    /**
     * 未找到页面
     *
     * @return
     */
    @GetMapping("/404")
    public String err404() {
        return TPL_PATH + "/404";
    }

    /**
     * 系统错误页面
     *
     * @return
     */
    @GetMapping("/500")
    public String err500() {
        return TPL_PATH + "/500";
    }
}
