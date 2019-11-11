package com.jeebud.module.quartz.controller;

import com.jeebud.core.web.RestEntity;
import com.jeebud.core.log.OpTypeEnum;
import com.jeebud.core.log.Operation;
import com.jeebud.module.quartz.model.entity.QrtzJobLog;
import com.jeebud.module.quartz.model.param.QrtzJobLogPageQuery;
import com.jeebud.module.quartz.service.QrtzJobLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Controller
@RequestMapping("/admin/quartz/log")
public class QrtzJobLogController {
    /**
     * 模板前缀
     */
    private static final String TPL_PATH = "admin/modules/quartz/log";
    @Autowired
    QrtzJobLogService jobLogService;

    /**
     * 列表页面
     *
     * @return
     */
    @RequiresPermissions("p:quartz:log:list")
    @GetMapping("/pageList")
    public String pageList() {
        return TPL_PATH + "/list";
    }

    /**
     * 任务日志列表
     *
     * @param query
     * @return
     */
    @RequiresPermissions("i:quartz:log:list")
    @GetMapping("/list")
    @ResponseBody
    public RestEntity<Page<QrtzJobLog>> list(@Validated @ModelAttribute QrtzJobLogPageQuery query) {
        Page<QrtzJobLog> page = jobLogService.page(query);
        return RestEntity.ok().data(page);
    }

    /**
     * 清空日志
     *
     * @return
     */
    @RequiresPermissions("i:quartz:log:clear")
    @Operation(module = "调度日志模块", opType = OpTypeEnum.DELETE, info = "清空调度日志")
    @PostMapping("/clear")
    @ResponseBody
    public RestEntity<Long> clear() {
        jobLogService.clear();
        return RestEntity.ok();
    }
}
