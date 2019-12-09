package com.jeebud.module.cms.controller;

import com.jeebud.common.util.ObjectUtils;
import com.jeebud.core.log.OpTypeEnum;
import com.jeebud.core.log.Operation;
import com.jeebud.core.web.RestEntity;
import com.jeebud.module.cms.model.entity.Article;
import com.jeebud.module.cms.model.param.ArticlePageQuery;
import com.jeebud.module.cms.service.ArticleService;
import com.jeebud.module.cms.service.ColumnService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@RequestMapping("${jeebud.sys.serverCtx}/cms/article")
public class ArticleController {
    /**
     * 模板前缀
     */
    private static final String TPL_PATH = "admin/modules/cms/article";
    @Autowired
	ArticleService articleService;
    @Autowired
    ColumnService columnService;

    /**
     * 列表页面
     *
     * @return
     */
    @RequiresPermissions("p:cms:article:list")
    @GetMapping("/pageList")
    public String pageList(Model model) {
        model.addAttribute("columnList",columnService.listAll());
        return TPL_PATH + "/list";
    }

    /**
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/modal")
    public String articleModal(Long id, Model model) {
        Article article = new Article();
        if (ObjectUtils.isNotNull(id)) {
            article = articleService.findById(id);
        }
        model.addAttribute("article", article);
        model.addAttribute("columnList",columnService.listAll());
        return TPL_PATH + "/modal";
    }

    /**
     * 获取列表
     *
     * @param query
     * @return
     */
    @RequiresPermissions("i:cms:article:list")
    @GetMapping("/list")
    @ResponseBody
    public RestEntity<Page<Article>> articleList(ArticlePageQuery query) {
        Page<Article> articlePage = articleService.page(query);
        return RestEntity.ok().data(articlePage);
    }

    /**
     * 新增
     *
     * @param article
     * @return
     */
    @Operation(module = "内容模块", opType = OpTypeEnum.CREATE, info = "新增内容")
    @RequiresPermissions("i:cms:article:add")
    @PostMapping("/add")
    @ResponseBody
    public RestEntity<String> add(@Validated Article article) {
		articleService.insert(article);
        return RestEntity.ok();
    }

    /**
     * 修改
     *
     * @param article
     * @return
     */
    @Operation(module = "内容模块", opType = OpTypeEnum.UPDATE, info = "修改内容")
    @RequiresPermissions("i:cms:article:update")
    @PostMapping("/update")
    @ResponseBody
    public RestEntity<String> update(@Validated Article article) {
		articleService.update(article);
        return RestEntity.ok();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Operation(module = "内容模块", opType = OpTypeEnum.DELETE, info = "删除内容")
    @RequiresPermissions("i:cms:article:delete")
    @PostMapping("/delete")
    @ResponseBody
    public RestEntity<String> delete(Long id) {
		articleService.delete(id);
        return RestEntity.ok();
    }

}
