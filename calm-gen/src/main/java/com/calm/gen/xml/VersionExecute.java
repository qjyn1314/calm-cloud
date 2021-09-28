package com.calm.gen.xml;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * 版本更改思路
 * 使用程序将工程中的version改为响应的版本号
 * <p>
 * 然后进行install到本地，
 * <p>
 * 然后打包
 *
 * @author wangjunming@zhichubao.com 2021/9/18 16:24
 */
@Slf4j
public class VersionExecute {

    private static final String PROJECT_PATH;
    private static final String zcbParent;
    private static final String parentInstall;
    private static final Map<String, Map<String, String>> PROJECT_VERSION_LIST = Maps.newHashMap();

    static {
        PROJECT_PATH = "D:\\workspace\\idea_workspace\\zhichubao_workspace\\dev_saas";
        zcbParent = "\\zcb-parent\\pom.xml";
        parentInstall = "\\parentInstall.cmd";
        Map<String, String> zcbParentVersionMap = Maps.newHashMap();
        zcbParentVersionMap.put("", "");
        PROJECT_VERSION_LIST.put(zcbParent, zcbParentVersionMap);

    }

    public static void main(String[] args) {
//        PROJECT_VERSION_LIST.forEach((k,v)->{
//            String filePath = PROJECT_PATH + k;
//            v.forEach((versionK,versionV)->{
//            });
//        });
        String xmlPath = PROJECT_PATH + zcbParent;
        String nodePath = "project.version";
        String sourceValue = "0.0.2-SNAPSHOT";
        parserXml(xmlPath, nodePath, sourceValue);
        //执行 mvn install
        runCmdCommand(PROJECT_PATH + parentInstall);

    }

    /**
     * 参考：https://blog.csdn.net/redarmy_chen/article/details/12969219
     * 将指定的xml文件中的某一节点，改为目标值
     *
     * @param fileName    指定的xml文件
     * @param path        某一节点
     * @param sourceValue 目标值
     */
    public static void parserXml(String fileName, String path, String sourceValue) {
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(fileName);
            Element rootElement = document.getRootElement();
            Element finalElement = getFinalElement(rootElement, path);
            finalElement.setText(sourceValue);
            try {
                OutputFormat format = new OutputFormat();
                format.setEncoding("UTF-8" );
                Writer fileWriter = new FileWriter(fileName);
                XMLWriter xmlWriter = new XMLWriter(fileWriter, format);
                xmlWriter.write(document);
                xmlWriter.close();
            } catch (Exception e) {
                log.error("写入xml文件：{}，节点：{}", fileName, path, e);
            }
        } catch (Exception e) {
            log.error("读取xml文件：{}，节点：{}", fileName, path, e);
        }
    }

    private static Element getFinalElement(Element children, String path) {
        path = path.replace("project.", "" );
        Element element = children.element(path);
        if (element == null) {
            return children;
        }
        return getFinalElement(element, element.getName());
    }

    /**
     * 执行一个cmd命令
     * @param cmdCommand cmd命令
     * @return 命令执行结果字符串，如出现异常返回null
     */
    public static void runCmdCommand(String cmdCommand)
    {
        try {
            Process process = Runtime.getRuntime().exec(cmdCommand);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
            String line = null;
            while((line=bufferedReader.readLine()) != null)
            {
                log.info(line);
            }
        } catch (Exception e) {
            log.error("执行CMD异常信息",e);
        }
    }

}
