package com.jeebud.module.cms.controller;

import com.jeebud.core.log.OpTypeEnum;
import com.jeebud.core.log.Operation;
import com.jeebud.core.web.RestEntity;
import com.jeebud.module.cms.model.entity.Column;
import com.jeebud.module.cms.model.param.ColumnPageQuery;
import com.jeebud.module.cms.service.ColumnService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Controller
@RequestMapping("${jeebud.sys.serverCtx}/cms/column")
public class ColumnController {
    /**
     * 模板前缀
     */
    private static final String TPL_PATH = "admin/modules/cms/column";
    @Autowired
	ColumnService columnService;

    /**
     * 列表页面
     *
     * @return
     */
    @RequiresPermissions("p:cms:column:list")
    @GetMapping("/pageList")
    public String pageList() {
        return TPL_PATH + "/list";
    }

    /**
     * 获取列表
     *
     * @param query
     * @return
     */
    @RequiresPermissions("i:cms:column:list")
    @GetMapping("/list")
    @ResponseBody
    public RestEntity<Page<Column>> columnList(ColumnPageQuery query) {
        Page<Column> columnPage = columnService.page(query);
        return RestEntity.ok().data(columnPage);
    }

    /**
     * 新增
     *
     * @param column
     * @return
     */
    @Operation(module = "栏目模块", opType = OpTypeEnum.CREATE, info = "新增栏目")
    @RequiresPermissions("i:cms:column:add")
    @PostMapping("/add")
    @ResponseBody
    public RestEntity<String> add(@Validated Column column) {
		columnService.insert(column);
        return RestEntity.ok();
    }

    /**
     * 修改
     *
     * @param column
     * @return
     */
    @Operation(module = "栏目模块", opType = OpTypeEnum.UPDATE, info = "修改栏目")
    @RequiresPermissions("i:cms:column:update")
    @PostMapping("/update")
    @ResponseBody
    public RestEntity<String> update(@Validated Column column) {
		columnService.update(column);
        return RestEntity.ok();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Operation(module = "栏目模块", opType = OpTypeEnum.DELETE, info = "删除栏目")
    @RequiresPermissions("i:cms:column:delete")
    @PostMapping("/delete")
    @ResponseBody
    public RestEntity<String> delete(Long id) {
		columnService.delete(id);
        return RestEntity.ok();
    }

}
