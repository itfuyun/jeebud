package com.jeebud.module.upms.controller;

import com.jeebud.core.log.OpTypeEnum;
import com.jeebud.core.log.Operation;
import com.jeebud.core.web.RestEntity;
import com.jeebud.module.upms.model.entity.Param;
import com.jeebud.module.upms.model.param.ParamPageQuery;
import com.jeebud.module.upms.service.ParamService;
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
@RequestMapping("${jeebud.sys.serverCtx}/sys/param")
public class ParamController {
    /**
     * 模板前缀
     */
    private static final String TPL_PATH = "admin/modules/sys/param";
    @Autowired
	ParamService paramService;

    /**
     * 列表页面
     *
     * @return
     */
    @RequiresPermissions("p:sys:param:list")
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
    @RequiresPermissions("i:sys:param:list")
    @GetMapping("/list")
    @ResponseBody
    public RestEntity<Page<Param>> paramList(ParamPageQuery query) {
        Page<Param> paramPage = paramService.page(query);
        return RestEntity.ok().data(paramPage);
    }

    /**
     * 新增
     *
     * @param param
     * @return
     */
    @Operation(module = "参数模块", opType = OpTypeEnum.CREATE, info = "新增参数")
    @RequiresPermissions("i:sys:param:add")
    @PostMapping("/add")
    @ResponseBody
    public RestEntity<String> add(@Validated Param param) {
		paramService.insert(param);
        return RestEntity.ok();
    }

    /**
     * 修改
     *
     * @param param
     * @return
     */
    @Operation(module = "参数模块", opType = OpTypeEnum.UPDATE, info = "修改参数")
    @RequiresPermissions("i:sys:param:update")
    @PostMapping("/update")
    @ResponseBody
    public RestEntity<String> update(@Validated Param param) {
		paramService.update(param);
        return RestEntity.ok();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Operation(module = "参数模块", opType = OpTypeEnum.DELETE, info = "删除参数")
    @RequiresPermissions("i:sys:param:delete")
    @PostMapping("/delete")
    @ResponseBody
    public RestEntity<String> delete(Long id) {
		paramService.delete(id);
        return RestEntity.ok();
    }

}
