package com.jeebud.module.gen.service;

import com.jeebud.common.util.StringUtils;
import com.jeebud.module.gen.conf.GenConfig;
import com.jeebud.module.gen.entity.ColumnInfo;
import com.jeebud.module.gen.entity.TableInfo;
import com.jeebud.module.gen.util.GenUtils;
import com.jeebud.module.gen.util.VelocityInitializer;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Service
public class GenService {

    @PersistenceContext
    private EntityManager em;

    public TableInfo getTable(String name) {
        Page<TableInfo> page = this.getTables(name, 1, 1);
        if (page.isEmpty()) {
            return null;
        }
        return page.getContent().get(0);
    }

    public Page<TableInfo> getTables(String name, int page, int limit) {
        // 使用预编译防止sql注入
        String sql = "select table_name ,table_comment from information_schema.tables " +
                "where table_schema = (select database()) " +
                "and table_name like ? order by create_time desc" ;
        Query query = em.createNativeQuery(sql);
        query.setFirstResult((page - 1) * limit);
        query.setMaxResults(limit);
        query.setParameter(1, StringUtils.isNotBlank(name) ? ("%" + name + "%") : "%%");
        List<Object[]> result = query.getResultList();
        List<TableInfo> tableInfos = new ArrayList<>();
        for (Object[] obj : result) {
            TableInfo tableInfo = new TableInfo();
            tableInfo.setTableName(String.valueOf(obj[0]));
            tableInfo.setTableComment(String.valueOf(obj[1]));
            tableInfos.add(tableInfo);
        }
        Query query1 = em.createNativeQuery("SELECT COUNT(*) from information_schema.tables where table_schema = (select database())");
        BigInteger totalElements = (BigInteger) query1.getSingleResult();
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<TableInfo> tableInfoPage = new PageImpl<>(tableInfos, pageable, totalElements.longValue());
        return tableInfoPage;
    }

    /**
     * 根据表名获取字段信息
     *
     * @param name
     * @return
     */
    public List<ColumnInfo> getColumns(String name) {
        // 使用预编译防止sql注入
        String sql = "select column_name, data_type, column_comment from information_schema.columns " +
                "where table_name = ? and table_schema = (select database()) order by ordinal_position" ;
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, StringUtils.isNotBlank(name) ? name : null);
        List<Object[]> result = query.getResultList();
        List<ColumnInfo> columnInfos = new ArrayList<>();
        for (Object[] obj : result) {
            ColumnInfo columnInfo = new ColumnInfo();
            columnInfo.setColumnName(String.valueOf(obj[0]));
            columnInfo.setDataType(String.valueOf(obj[1]));
            columnInfo.setColumnComment(String.valueOf(obj[2]));
            columnInfos.add(columnInfo);
        }
        return columnInfos;
    }

    /**
     * 生成代码
     *
     * @param tableName 表名称
     * @return 数据
     */
    public byte[] generatorCode(String tableName) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        // 查询表信息
        TableInfo table = this.getTable(tableName);
        // 查询列信息
        List<ColumnInfo> columns = this.getColumns(tableName);
        // 生成代码
        generatorCode(table, columns, zip);
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    /**
     * 生成代码
     */
    public void generatorCode(TableInfo table, List<ColumnInfo> columns, ZipOutputStream zip) {
        // 表名转换成Java属性名
        String className = GenUtils.tableToJava(table.getTableName());
        table.setClassName(className);
        table.setClassname(StringUtils.uncapitalize(className));
        // 列信息
        table.setColumns(GenUtils.transColums(columns));
        // 设置主键
        table.setPrimaryKey(table.getColumnsLast());

        VelocityInitializer.initVelocity();

        String packageName = GenConfig.getPackageName();
        String moduleName = GenConfig.getModuleName();

        VelocityContext context = GenUtils.getVelocityContext(table);

        // 获取模板列表
        List<String> templates = GenUtils.getTemplates();
        for (String template : templates) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);
            try {
                // 添加到zip
                zip.putNextEntry(new ZipEntry(GenUtils.getFileName(template, table, moduleName)));
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
