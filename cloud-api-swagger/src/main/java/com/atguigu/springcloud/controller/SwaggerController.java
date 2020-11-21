package com.atguigu.springcloud.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.swagger2markup.*;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.builder.Swagger2MarkupExtensionRegistryBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import io.github.swagger2markup.spi.SwaggerModelExtension;
import io.swagger.models.Model;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import io.swagger.models.Tag;
import io.swagger.parser.util.DeserializationUtils;
import io.swagger.parser.util.SwaggerDeserializationResult;
import io.swagger.parser.util.SwaggerDeserializer;
import io.swagger.util.Json;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * description:
 *
 * Swagger在线文档导出
 *
 * @author xiaoming.li04@hand-china.com
 * @datatime 2020/09/14 9:34
 */
@RestController
@RequestMapping("/swagger")
public class SwaggerController {
    /**
     * 需要合并的微服务接口信息
     */
    private final static  List<String> URL_LIST= Arrays.asList(
            "http://localhost:8810/v2/api-docs",
            "http://localhost:8889/v2/api-docs"
            );
    /**
     * 单个微服务接口信息
     * 如果是多个微服务接口合并，请填写自定义接口信息，如：http://localhost:8888/swagger/json
     */
    //private final static String URL = "http://localhost:8050/docs/cms?version=1.3.0-SNAPSHOT";
    private final static String URL = "http://localhost:8810/swagger/json";
    //private final static String URL = "http://localhost:8050/docs/o2ord?version=1.3.0-SNAPSHOT";

    /**
     * 输出文件的位置，最后一个路径代表文件的名称
     */
    private final static String OUT_PATH = "src/main/resources/doc/api";
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private SwaggerModelExtension swaggerModelExtension;

    /**
     * 获取多个微服务合并后的Swagger对象
     * @return
     * @throws IOException
     */
    @GetMapping("/json")
    public Swagger getSwaggerJson() throws IOException {
        String swaggerStr = restTemplate.getForObject(URL_LIST.get(0), String.class);
        Swagger swaggerTemplate=convertToSwagger(swaggerStr);
        Map<String, Path> pathsTemplate = swaggerTemplate.getPaths();
        Map<String, Model> defTemplate = swaggerTemplate.getDefinitions();
        List<Tag> tagsTemplate = swaggerTemplate.getTags();
        for (int i = 1; i < URL_LIST.size(); i++) {
            String swaggerString = restTemplate.getForObject(URL_LIST.get(i), String.class);
            Swagger swagger=convertToSwagger(swaggerString);
            //添加path
            Map<String, Path> paths = swagger.getPaths();
            Set<Map.Entry<String, Path>> entries = paths.entrySet();
            for (Map.Entry<String, Path> entry : entries) {
                pathsTemplate.put(entry.getKey(),entry.getValue());
            }
            //添加defined
            Map<String, Model> definitions = swagger.getDefinitions();
            Set<Map.Entry<String, Model>> entries1 = definitions.entrySet();
            for (Map.Entry<String, Model> entry : entries1) {
                defTemplate.put(entry.getKey(),entry.getValue());
            }
            //添加tags
            List<Tag> tags = swagger.getTags();
            tagsTemplate.addAll(tags);
        }
        swaggerTemplate.setPaths(pathsTemplate);
        swaggerTemplate.setDefinitions(defTemplate);
        swaggerTemplate.setTags(tagsTemplate);
        return swaggerTemplate;
    }

    /**
     * 将json字符串转换为Swagger对象
     * @param data json字符串
     * @return Swagger对象
     * @throws IOException
     */
    private Swagger convertToSwagger(String data) throws IOException {
        if (data != null) {
            JsonNode rootNode;
            if (data.trim().startsWith("{")) {
                ObjectMapper mapper = Json.mapper();
                rootNode = mapper.readTree(data);
            } else {
                rootNode = DeserializationUtils.readYamlTree(data);
            }
            if (rootNode == null) {
                return null;
            } else {
                JsonNode swaggerNode = rootNode.get("swagger");
                if (swaggerNode == null) {
                    return null;
                } else {
                    SwaggerDeserializationResult result = (new SwaggerDeserializer()).deserialize(rootNode);
                    Swagger convertValue = result.getSwagger();
                    return convertValue;
                }
            }
        } else {
            return null;
        }
    }

    /**
     * 生成Swagger部分离线文档
     * @throws MalformedURLException
     */
    @GetMapping("/selectDocs")
    public org.o2.swagger.entity.CommonResult getSwaggerDoc()  {
        //    输出Ascii到单文件配置
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();
//        Swagger扩展模块
//        SwaggerModelExtension ext = new MySwaggerModelExtension();
        Swagger2MarkupExtensionRegistry registry = new Swagger2MarkupExtensionRegistryBuilder()
                .withSwaggerModelExtension(swaggerModelExtension)
                .build();
        /**
         * 生成文档
         * URL：获取json字符串的URL路径，可以是单个微服务，也可以是多个微服务
         */
        try {
            Swagger2MarkupConverter.from(new URL(URL))
                    .withConfig(config)
                    .withExtensionRegistry(registry).build()
                    .toFile(Paths.get(OUT_PATH));
        } catch (MalformedURLException e) {
            return new org.o2.swagger.entity.CommonResult(HttpStatus.INTERNAL_SERVER_ERROR,"获取服务接口信息失败");
        }
        return  new org.o2.swagger.entity.CommonResult(HttpStatus.OK,"生成文件成功！");
    }

    /**
     * 生成全部离线文档
     * @return
     */
    @GetMapping("/allDocs")
    public org.o2.swagger.entity.CommonResult getSwaggerAllDocs(){
        //    输出Ascii到单文件
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        try {
            Swagger2MarkupConverter.from(new URL(URL))
                    .withConfig(config)
                    .build()
                    .toFile(Paths.get(OUT_PATH));
        } catch (MalformedURLException e) {
            return new org.o2.swagger.entity.CommonResult(HttpStatus.INTERNAL_SERVER_ERROR,"获取服务接口信息失败");
        }
        return  new org.o2.swagger.entity.CommonResult(HttpStatus.OK,"生成文件成功！");
    }
    @GetMapping("/test")
    public Swagger testSwagger() throws IOException {
        String swaggerStr1 = restTemplate.getForObject("http://localhost:8800/v2/api-docs", String.class);
        Swagger swagger1=convertToSwagger(swaggerStr1);
        String swaggerStr2 = restTemplate.getForObject("http://localhost:8889/v2/api-docs", String.class);
        Swagger swagger2=convertToSwagger(swaggerStr2);
//        添加path
        Map<String, Path> paths1 = swagger1.getPaths();
        Map<String, Path> paths2 = swagger2.getPaths();
        Set<Map.Entry<String, Path>> entries = paths2.entrySet();
        for (Map.Entry<String, Path> entry : entries) {
            paths1.put(entry.getKey(),entry.getValue());
        }
        swagger1.setPaths(paths1);
//        添加defined
        Map<String, Model> definitions1 = swagger1.getDefinitions();
        Map<String, Model> definitions2 = swagger2.getDefinitions();
        Set<Map.Entry<String, Model>> entries1 = definitions2.entrySet();
        for (Map.Entry<String, Model> entry : entries1) {
            definitions1.put(entry.getKey(),entry.getValue());
        }
        swagger1.setDefinitions(definitions1);
//        添加tags
        List<Tag> tags1 = swagger1.getTags();
        List<Tag> tags2 = swagger2.getTags();
        tags1.addAll(tags2);
        swagger1.setTags(tags1);
        return  swagger1;
    }
}
