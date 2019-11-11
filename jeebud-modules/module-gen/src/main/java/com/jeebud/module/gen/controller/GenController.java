package com.jeebud.module.gen.controller;

import com.jeebud.core.web.RestEntity;
import com.jeebud.core.log.OpTypeEnum;
import com.jeebud.core.log.Operation;
import com.jeebud.module.gen.entity.TableInfo;
import com.jeebud.module.gen.service.GenService;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Controller
@RequestMapping("/admin/gen")
public class GenController {
    /**
     * 模板前缀
     */
    private static final String TPL_PATH = "admin/modules/gen";
    @Autowired
    GenService genService;

    /**
     * 列表页面
     *
     * @return
     */
    @RequiresPermissions("p:gen:table:list")
    @GetMapping("/pageList")
    public String pageList() {
        return TPL_PATH + "/list";
    }

    /**
     * 获取表列表
     *
     * @param name
     * @param page
     * @param limit
     * @return
     */
    @RequiresPermissions("i:gen:table:list")
    @GetMapping("/list")
    @ResponseBody
    public RestEntity<Page<TableInfo>> roleList(String name, int page, int limit) {
        Page<TableInfo> tableInfoPage = genService.getTables(name, page, limit);
        return RestEntity.ok().data(tableInfoPage);
    }

    /**
     * 代码生成
     *
     * @param response
     * @param tableName
     * @throws IOException
     */
    @Operation(module = "代码生成模块", opType = OpTypeEnum.CREATE, info = "生成代码")
    @RequiresPermissions("i:gen:table:create")
    @GetMapping("/create")
    @ResponseBody
    public void genCode(HttpServletResponse response, String tableName) throws IOException {
        String filename = tableName + ".zip";
        byte[] data = genService.generatorCode(tableName);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
    }
}
