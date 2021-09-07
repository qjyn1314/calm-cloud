package com.calm.gen.util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import com.calm.common.exception.CalmException;
import com.calm.gen.config.GenConfig;
import com.calm.gen.mapper.entity.ColumnEntity;
import com.calm.gen.mapper.entity.TableEntity;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.util.ResourceUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
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

    private final String MAPPER_XML_VM = "Mapper.xml.vm";

    private final String MAPPER_XML_SUFFIX = MAPPER_XML_VM.substring(0, MAPPER_XML_VM.lastIndexOf("." ));

    private final String JAVA_SUFFIX = ".java";

    private final String DEFAULT_TEMPLATE_PATH = File.separator + "template" + File.separator;

    private String finalClassPathTemplatePath = "";

    /**
     * 生成代码
     */
    @SneakyThrows
    public void generatorCode(GenConfig genConfig, Map<String, Object> table,
                              List<Map<String, Object>> columns, ZipOutputStream zip) {
        // 配置信息-数据库字段与java的的对应关系信息
        Configuration config = getConfig();
        // 表信息
        TableEntity tableEntity = new TableEntity();
        BeanUtils.populate(tableEntity, table);
        if (CharSequenceUtil.isNotBlank(genConfig.getComments())) {
            tableEntity.setComments(genConfig.getComments());
        }
        String className = tableToJava(tableEntity.getTableName(), genConfig.getTablePrefix());
        log.info("生成的Java类名...{}", className);
        tableEntity.setClassName(className);
        tableEntity.setLowerClassname(StringUtils.uncapitalize(className));
        tableEntity.setPathName(className.toLowerCase());
        // 列信息
        List<ColumnEntity> columnList = new ArrayList<>();
        int priCount = 1;
        for (Map<String, Object> column : columns) {
            ColumnEntity columnEntity = new ColumnEntity();
            BeanUtils.populate(columnEntity, column);
            // 列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            columnEntity.setCaseAttrName(attrName);
            columnEntity.setLowerAttrName(StringUtils.uncapitalize(attrName));
            columnEntity.setCapitalAttrName(columnEntity.getColumnName().toUpperCase());
            // 列的数据类型，转换成Java类型
            columnEntity.setAttrType(config.getString(columnEntity.getDataType(), "unknowType" ));
            // 是否主键
            if (priCount == 1 && "PRI".equalsIgnoreCase((String) column.get("columnKey" )) && tableEntity.getPk() == null) {
                tableEntity.setPk(columnEntity);
                ++priCount;
            }
            columnList.add(columnEntity);
        }
        tableEntity.setColumns(columnList);
        // 没主键，则第一个字段为主键
        if (tableEntity.getPk() == null) {
            tableEntity.setPk(tableEntity.getColumns().get(0));
        }
        // 封装模板中的数据
        Map<String, Object> map;
        map = transBean2Map(genConfig);
        //类名全小写
        map.put("classname", tableEntity.getLowerClassname().toLowerCase());
        map.putAll(transBean2Map(tableEntity));
        // 渲染数据
        renderData(genConfig, zip, tableEntity, map);
    }

    /**
     * 列名转换成Java属性名
     */
    public String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "" );
    }

    /**
     * 表名转换成Java类名
     */
    private String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replaceFirst(tablePrefix, "" );
        }
        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     */
    private Configuration getConfig() {
        try {
            return new PropertiesConfiguration("generator.properties" );
        } catch (ConfigurationException e) {
            throw new CalmException("获取配置文件失败" );
        }
    }

    /**
     * Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
     * 参考：https://blog.csdn.net/cuidiwhere/article/details/8130434
     *
     * @author wangjunming
     */
    public static Map<String, Object> transBean2Map(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!"class".equals(key)) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            log.error("转换错误--", e);
        }
        return map;
    }

    /**
     * 渲染数据
     *
     * @param zip         流 （为空，直接返回Map）
     * @param tableEntity 表基本信息
     * @param map         模板参数
     */
    private void renderData(GenConfig genConfig, ZipOutputStream zip,
                            TableEntity tableEntity, Map<String, Object> map) throws IOException {
        // 设置velocity资源加载器
        VelocityInitializer.initVelocity();
        VelocityContext context = new VelocityContext(map);
        // 特殊的模板文件夹
        String specialTemplate = genConfig.getSpecialTemplate();
        // 读取模板路径
        List<String> templates = getTemplates(specialTemplate);
        //将模板列表与路径匹配为map
        Map<String, String> templatePathMap = templates.stream().map(CodeGenUtils::getTemplatePathKey).collect(Collectors.toMap(Function.identity(), template -> String.valueOf(map.get(template))));
        for (String template : templates) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, CharsetUtil.UTF_8);
            tpl.merge(context, sw);
            String path = templatePathMap.get(getTemplatePathKey(template));
            // 添加到zip
            String fileName = getFileName(template, path, tableEntity.getClassName());
            if (zip != null) {
                zip.putNextEntry(new ZipEntry(Objects.requireNonNull(fileName)));
                IoUtil.write(zip, StandardCharsets.UTF_8, false, sw.toString());
                IoUtil.close(sw);
                zip.closeEntry();
            }
        }
    }

    /**
     * 模板配置
     */
    private List<String> getTemplates(String specialTemplate) {
        String classPathTemplatePath = CharSequenceUtil.isNotBlank(specialTemplate) ? DEFAULT_TEMPLATE_PATH + specialTemplate + File.separator : DEFAULT_TEMPLATE_PATH;
        finalClassPathTemplatePath = classPathTemplatePath;
        String path = null;
        try {
            path = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX).getPath();
        } catch (FileNotFoundException e) {
            log.error("读取文件失败。", e);
        }
        classPathTemplatePath = path + classPathTemplatePath;
        File file = new File(classPathTemplatePath);
        Assert.notNull(file, "读取文件目录失败。" );
        File[] files = file.listFiles();
        Assert.notNull(files, "读取文件目录失败。" );
        return Arrays.stream(files).filter(File::isFile).map(File::getAbsolutePath)
                .map(templatePath -> finalClassPathTemplatePath + templatePath.substring(templatePath.lastIndexOf("\\" ) + 1))
                .collect(Collectors.toList());
    }

    /**
     * 转换为 模板 key
     */
    public static String getTemplatePathKey(String str) {
        if ((finalClassPathTemplatePath + MAPPER_XML_VM).equals(str)) {
            return "xml";
        }
        return StringUtils.uncapitalize(str.substring(finalClassPathTemplatePath.length(), str.lastIndexOf(JAVA_SUFFIX)));
    }

    /**
     * 获取文件名
     */
    private String getFileName(String template, String path, String className) {
        String packagePath = "src" + File.separator + "main" + File.separator + "java" + File.separator + path;
        packagePath = packagePath.replace(".", File.separator);
        if (template.contains(ENTITY_JAVA_VM)) {
            return packagePath + File.separator + className + ".java";
        }
        if (template.contains(MAPPER_XML_VM)) {
            return "src" + File.separator + "main" + File.separator
                    + "resources" + File.separator + path + File.separator + className + MAPPER_XML_SUFFIX;
        }
        return packagePath + File.separator + className + template.substring(finalClassPathTemplatePath.length(), template.lastIndexOf(JAVA_SUFFIX)) + JAVA_SUFFIX;
    }

}
