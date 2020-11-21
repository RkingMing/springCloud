package com.atguigu.springcloud.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.models.Model;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import io.swagger.models.Tag;
import io.swagger.parser.util.DeserializationUtils;
import io.swagger.parser.util.SwaggerDeserializationResult;
import io.swagger.parser.util.SwaggerDeserializer;
import io.swagger.util.Json;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * description:
 *
 * @author xiaoming.li04@hand-china.com
 * @datatime 2020/09/14 9:34
 */
@RestController
@RequestMapping("/swagger1")
public class SwaggerController1 {

    @Resource
    private RestTemplate restTemplate;


    @GetMapping("/json1")
    public Swagger  getSwaggerJson() throws IOException {

        String swaggerStr1 = restTemplate.getForObject("http://localhost:8888/v2/api-docs", String.class);
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
}
