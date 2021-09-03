package com.calm.gen.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import com.calm.common.exception.CalmException;
import com.calm.gen.config.ColumnEntity;
import com.calm.gen.config.GenConfig;
import com.calm.gen.config.TableEntity;
import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器 工具类
 */
@Slf4j
@UtilityClass
public class CodeGenUtils {

    private final String ENTITY_JAVA_VM = "Entity.java.vm";

    private final String MAPPER_JAVA_VM = "Mapper.java.vm";

    private final String SERVICE_JAVA_VM = "Service.java.vm";

    private final String SERVICE_IMPL_JAVA_VM = "ServiceImpl.java.vm";

    private final String CONTROLLER_JAVA_VM = "Controller.java.vm";

    private final String MAPPER_XML_VM = "Mapper.xml.vm";

    /**
     * 模板配置
     */
    private List<String> getTemplates(String specialTemplate) {
        List<String> templates = new LinkedList<>();
        templates.add("template/Entity.java.vm");
        templates.add("template/Mapper.java.vm");
        templates.add("template/Mapper.xml.vm");
        templates.add("template/Service.java.vm");
        templates.add("template/ServiceImpl.java.vm");
        templates.add("template/Controller.java.vm");
        if (StrUtil.isNotBlank(specialTemplate)) {
            templates = templates.stream().map(template -> {
                template = template.replace("template/", "template/" + specialTemplate + "/");
                return template;
            }).collect(Collectors.toList());
            return templates;
        }
        return templates;
    }

    /**
     * 生成代码
     */
    @SneakyThrows
    public Map<String, String> generatorCode(GenConfig genConfig, Map<String, String> table,
                                             List<Map<String, String>> columns, ZipOutputStream zip) {
        // 配置信息
        Configuration config = getConfig();
        boolean hasBigDecimal = false;
        // 表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(table.get("tableName"));

        if (StrUtil.isNotBlank(genConfig.getComments())) {
            tableEntity.setComments(genConfig.getComments());
        } else {
            tableEntity.setComments(table.get("tableComment"));
        }

        String tablePrefix = genConfig.getTablePrefix();

        // 表名转换成Java类名
        String className = tableToJava(tableEntity.getTableName(), tablePrefix);
        log.info("生成的Java类名...{}", className);
        tableEntity.setCaseClassName(className);
        tableEntity.setLowerClassName(StringUtils.uncapitalize(className));

        // 列信息
        List<ColumnEntity> columnList = new ArrayList<>();
        for (Map<String, String> column : columns) {
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.get("columnName"));
            columnEntity.setDataType(column.get("dataType"));
            columnEntity.setComments(column.get("columnComment"));
            columnEntity.setExtra(column.get("extra"));
            columnEntity.setNullable("NO".equals(column.get("isNullable")));
            columnEntity.setColumnType(column.get("columnType"));
            columnEntity.setHidden(Boolean.FALSE);
            // 列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            columnEntity.setCaseAttrName(attrName);
            columnEntity.setLowerAttrName(StringUtils.uncapitalize(attrName));

            // 列的数据类型，转换成Java类型
            String attrType = config.getString(columnEntity.getDataType(), "unknowType");
            columnEntity.setAttrType(attrType);
            if (!hasBigDecimal && "BigDecimal".equals(attrType)) {
                hasBigDecimal = true;
            }
            // 是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getPk() == null) {
                tableEntity.setPk(columnEntity);
            }

            columnList.add(columnEntity);
        }
        tableEntity.setColumns(columnList);

        // 没主键，则第一个字段为主键
        if (tableEntity.getPk() == null) {
            tableEntity.setPk(tableEntity.getColumns().get(0));
        }
        // 封装模板数据
        Map<String, Object> map = new HashMap<>(16);
        map.put("tableName", tableEntity.getTableName());
        map.put("pk", tableEntity.getPk());
        map.put("className", tableEntity.getCaseClassName());
        map.put("classname", tableEntity.getLowerClassName());
        map.put("pathName", tableEntity.getLowerClassName().toLowerCase());
        map.put("columns", tableEntity.getColumns());
        map.put("hasBigDecimal", hasBigDecimal);
        map.put("datetime", DateUtil.now());

        map.put("entity", genConfig.getEntity());
        map.put("controller", genConfig.getController());
        map.put("service", genConfig.getService());
        map.put("serviceImpl", genConfig.getServiceImpl());
        map.put("mapper", genConfig.getMapper());

        map.put("comments", tableEntity.getComments());

        map.put("author", genConfig.getAuthor());

        // 渲染数据
        return renderData(genConfig, zip, tableEntity, map);
    }

    /**
     * 渲染数据
     *
     * @param zip         流 （为空，直接返回Map）
     * @param tableEntity 表基本信息
     * @param map         模板参数
     * @return map key-filename value-contents
     * @throws IOException
     */
    private Map<String, String> renderData(GenConfig genConfig, ZipOutputStream zip,
                                           TableEntity tableEntity, Map<String, Object> map) throws IOException {
        // 设置velocity资源加载器
        VelocityInitializer.initVelocity();
        VelocityContext context = new VelocityContext(map);
        // 获取模板列表
        String specialTemplate = genConfig.getSpecialTemplate();
        List<String> templates = getTemplates(specialTemplate);
        //将模板列表与路径匹配为map
        Map<String, String> templatePathMap = handlePath(templates, genConfig);
        Map<String, String> resultMap = new HashMap<>(8);
        for (String template : templates) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, CharsetUtil.UTF_8);
            tpl.merge(context, sw);
            String path = templatePathMap.get(template);
            // 添加到zip
            String fileName = getFileName(template, path, tableEntity.getCaseClassName());
            if (zip != null) {
                zip.putNextEntry(new ZipEntry(Objects.requireNonNull(fileName)));
                IoUtil.write(zip, StandardCharsets.UTF_8, false, sw.toString());
                IoUtil.close(sw);
                zip.closeEntry();
            }
            resultMap.put(template, sw.toString());
        }
        return resultMap;
    }

    private static Map<String, String> handlePath(List<String> templates, GenConfig genConfig) {
        Map<String, String> pathMap = Maps.newHashMap();
        for (String template : templates) {
            if (template.contains(ENTITY_JAVA_VM)) {
                pathMap.put(template, genConfig.getEntity());
            }
            if (template.contains(MAPPER_JAVA_VM)) {
                pathMap.put(template, genConfig.getMapper());
            }
            if (template.contains(SERVICE_JAVA_VM)) {
                pathMap.put(template, genConfig.getService());
            }
            if (template.contains(SERVICE_IMPL_JAVA_VM)) {
                pathMap.put(template, genConfig.getServiceImpl());
            }
            if (template.contains(CONTROLLER_JAVA_VM)) {
                pathMap.put(template, genConfig.getController());
            }
            if (template.contains(MAPPER_XML_VM)) {
                pathMap.put(template, genConfig.getXml());
            }
        }
        return pathMap;
    }

    /**
     * 列名转换成Java属性名
     */
    public String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * 表名转换成Java类名
     */
    private String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replaceFirst(tablePrefix, "");
        }
        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     */
    private Configuration getConfig() {
        try {
            return new PropertiesConfiguration("generator.properties");
        } catch (ConfigurationException e) {
            throw new CalmException("获取配置文件失败");
        }
    }

    /**
     * 获取文件名
     */
    private String getFileName(String template, String path, String className) {
        String packagePath = "src" + File.separator + "main"
                + File.separator + "java" + File.separator + path;

        packagePath = packagePath.replace(".", File.separator);

        if (template.contains(ENTITY_JAVA_VM)) {
            return packagePath + File.separator + className + ".java";
        }

        if (template.contains(MAPPER_JAVA_VM)) {
            return packagePath + File.separator + className + "Mapper.java";
        }

        if (template.contains(SERVICE_JAVA_VM)) {
            return packagePath + File.separator + className + "Service.java";
        }

        if (template.contains(SERVICE_IMPL_JAVA_VM)) {
            return packagePath + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains(CONTROLLER_JAVA_VM)) {
            return packagePath + File.separator + className + "Controller.java";
        }

        if (template.contains(MAPPER_XML_VM)) {
            return "src" + File.separator + "main" + File.separator
                    + "resources" + File.separator + path + File.separator + className + "Mapper.xml";
        }

        return null;
    }

}
