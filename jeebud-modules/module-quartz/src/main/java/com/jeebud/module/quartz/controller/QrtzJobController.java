package com.jeebud.module.quartz.controller;

import com.jeebud.core.web.RestEntity;
import com.jeebud.core.log.OpTypeEnum;
import com.jeebud.core.log.Operation;
import com.jeebud.module.quartz.model.entity.QrtzJob;
import com.jeebud.module.quartz.exception.TaskException;
import com.jeebud.module.quartz.model.param.QrtzJobPageQuery;
import com.jeebud.module.quartz.service.QrtzJobService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.SchedulerException;
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
@RequestMapping("/admin/quartz/job")
public class QrtzJobController {
    /**
     * 模板前缀
     */
    private static final String TPL_PATH = "admin/modules/quartz/job";
    @Autowired
    QrtzJobService jobService;

    /**
     * 列表页面
     *
     * @return
     */
    @RequiresPermissions("p:quartz:job:list")
    @GetMapping("/pageList")
    public String pageList() {
        return TPL_PATH + "/list";
    }

    /**
     * 任务列表
     *
     * @param query
     * @return
     */
    @RequiresPermissions("i:quartz:job:list")
    @GetMapping("/list")
    @ResponseBody
    public RestEntity<Page<QrtzJob>> page(@Validated @ModelAttribute QrtzJobPageQuery query) {
        Page<QrtzJob> page = jobService.page(query);
        return RestEntity.ok().data(page);
    }

    /**
     * 新增任务
     *
     * @param job
     * @return
     * @throws SchedulerException
     * @throws TaskException
     */
    @RequiresPermissions("i:quartz:job:add")
    @Operation(module = "任务调度模块", opType = OpTypeEnum.CREATE, info = "创建任务调度")
    @PostMapping("add")
    @ResponseBody
    public RestEntity<Long> create(@Validated QrtzJob job) throws SchedulerException, TaskException {
        Long id = jobService.create(job);
        return RestEntity.ok().data(id);
    }

    /**
     * 修改任务
     *
     * @param job
     * @return
     * @throws SchedulerException
     * @throws TaskException
     */
    @RequiresPermissions("i:quartz:job:update")
    @Operation(module = "任务调度模块", opType = OpTypeEnum.UPDATE, info = "修改任务调度")
    @PostMapping("update")
    @ResponseBody
    public RestEntity<Long> update(@Validated QrtzJob job) throws SchedulerException, TaskException {
        jobService.update(job);
        return RestEntity.ok();
    }

    /**
     * 删除任务
     *
     * @param id
     * @return
     * @throws SchedulerException
     */
    @RequiresPermissions("i:quartz:job:delete")
    @Operation(module = "任务调度模块", opType = OpTypeEnum.DELETE, info = "删除任务调度")
    @PostMapping("delete")
    @ResponseBody
    public RestEntity<Long> delete(Long id) throws SchedulerException {
        jobService.delete(id);
        return RestEntity.ok();
    }

    /**
     * 修改任务状态
     *
     * @param id
     * @param status
     * @return
     * @throws SchedulerException
     * @throws TaskException
     */
    @RequiresPermissions("i:quartz:job:updateStatus")
    @Operation(module = "任务调度模块", opType = OpTypeEnum.UPDATE, info = "修改任务调度状态")
    @PostMapping("updateStatus")
    @ResponseBody
    public RestEntity<Long> updateStatus(Long id, String status) throws SchedulerException, TaskException {
        jobService.updateStatus(id, status);
        return RestEntity.ok();
    }

    /**
     * 立即执行一次任务
     *
     * @param id
     * @return
     * @throws SchedulerException
     */
    @RequiresPermissions("i:quartz:job:run")
    @Operation(module = "任务调度模块", opType = OpTypeEnum.OTHER, info = "立即运行任务调度")
    @PostMapping("run")
    @ResponseBody
    public RestEntity<Long> updateStatus(Long id) throws SchedulerException {
        jobService.run(id);
        return RestEntity.ok();
    }
}
