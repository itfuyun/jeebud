package com.jeebud.module.upms.controller;

import com.jeebud.core.web.RestEntity;
import com.jeebud.core.log.OpTypeEnum;
import com.jeebud.core.log.Operation;
import com.jeebud.module.upms.model.entity.LoginLog;
import com.jeebud.module.upms.model.entity.OperationLog;
import com.jeebud.module.upms.model.param.log.LoginLogPageQuery;
import com.jeebud.module.upms.model.param.log.OperationLogPageQuery;
import com.jeebud.module.upms.service.LogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Controller
@RequestMapping("${jeebud.sys.serverCtx}/sys/log")
public class LogController {
    /**
     * 模板前缀
     */
    private static final String TPL_PATH = "admin/modules/sys/log";
    @Autowired
    LogService logService;

    /**
     * 操作日志列表页
     *
     * @return
     */
    @RequiresPermissions("p:sys:log:operation:list")
    @GetMapping("/operation/pageList")
    public String operationLogPageList() {
        return TPL_PATH + "/operationLogList";
    }

    /**
     * 操作日志列表
     *
     * @param query
     * @return
     */
    @RequiresPermissions("i:sys:log:operation:list")
    @GetMapping("/operation/list")
    @ResponseBody
    public RestEntity<Page<OperationLog>> operationLogList(OperationLogPageQuery query) {
        Page<OperationLog> operationLogPage = logService.operationLogPage(query);
        return RestEntity.ok().data(operationLogPage);
    }

    /**
     * 清空操作日志
     *
     * @return
     */
    @Operation(module = "日志模块", opType = OpTypeEnum.UPDATE, info = "清空操作日志")
    @RequiresPermissions("i:sys:log:operation:clear")
    @PostMapping("/operation/clear")
    @ResponseBody
    public RestEntity clearOperationLog() {
        logService.clearOperationLog();
        return RestEntity.ok();
    }

    /**
     * 登录日志列表页
     *
     * @return
     */
    @RequiresPermissions("p:sys:log:login:list")
    @GetMapping("/login/pageList")
    public String loginPageList() {
        return TPL_PATH + "/loginLogList";
    }

    /**
     * 登录日志列表
     *
     * @param query
     * @return
     */
    @RequiresPermissions("i:sys:log:login:list")
    @GetMapping("/login/list")
    @ResponseBody
    public RestEntity<Page<LoginLog>> loginLogList(LoginLogPageQuery query) {
        Page<LoginLog> loginLogPage = logService.loginLogPage(query);
        return RestEntity.ok().data(loginLogPage);
    }

    /**
     * 清空登录日志
     *
     * @return
     */
    @Operation(module = "日志模块", opType = OpTypeEnum.UPDATE, info = "清空登录日志")
    @RequiresPermissions("i:sys:log:login:clear")
    @PostMapping("/login/clear")
    @ResponseBody
    public RestEntity clearLoginLog() {
        logService.clearLoginLog();
        return RestEntity.ok();
    }
}
