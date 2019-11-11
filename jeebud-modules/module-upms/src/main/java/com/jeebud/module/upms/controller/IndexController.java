package com.jeebud.module.upms.controller;

import com.jeebud.core.shiro.util.ShiroUtils;
import com.jeebud.module.upms.model.entity.User;
import com.jeebud.module.upms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Controller
@RequestMapping("${jeebud.sys.serverCtx}")
public class IndexController {
    @Autowired
    UserService userService;

    /**
     * 首页
     *
     * @param model
     * @return
     */
    @GetMapping({"", "/index"})
    public String index(Model model) {
        User user = userService.findById(ShiroUtils.getUserId());
        model.addAttribute("user", user);
        return "admin/index";
    }

    /**
     * 介绍
     *
     * @return
     */
    @GetMapping("/introduction")
    public String introduction() {
        return "admin/introduction";
    }

    /**
     * 欢迎页
     *
     * @return
     */
    @GetMapping("/welcome")
    public String welcome() {
        return "admin/welcome";
    }

    /**
     * 主题页面
     *
     * @return
     */
    @GetMapping("/theme")
    public String theme() {
        return "admin/theme";
    }

    /**
     * websocket
     *
     * @return
     */
    @GetMapping("/websocket")
    public String websocket() {
        return "admin/websocket";
    }

    /**
     * markdown
     *
     * @return
     */
    @GetMapping("/markdown")
    public String markdown() {
        return "admin/markdown";
    }

    /**
     * 富文本
     *
     * @return
     */
    @GetMapping("/editor")
    public String editor() {
        return "admin/editor";
    }
}
