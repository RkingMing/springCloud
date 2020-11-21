/*
 * Copyright 2017 Robert Winkler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.atguigu.springcloud;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.github.swagger2markup.spi.SwaggerModelExtension;
import io.swagger.models.*;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;
import org.springframework.beans.BeanUtils;

// tag::MySwaggerModelExtension[]
public class MySwaggerModelExtension extends SwaggerModelExtension {
    @Override
    public void apply(Swagger swagger) {

        swagger.setHost("newHostName"); //<1>
        swagger.basePath("newBasePath");

        Map<String, Path> paths = swagger.getPaths(); //<2>
        Map<String, Model> definitions = swagger.getDefinitions();
        Set<Map.Entry<String, Path>> entries = paths.entrySet();
        for (Map.Entry<String, Path> entry : entries) {
            if (entry.getKey().equals("/v1/{organizationId}/components")){
                Path value = entry.getValue();
//                post请求
                Operation post = value.getPost();
                definitions=delDefined(post,definitions);
//                delete请求
                Operation delete = value.getDelete();
                definitions=delDefined(delete,definitions);
                Operation put = value.getPut();
                definitions=delDefined(put,definitions);
                Operation patch = value.getPatch();
                definitions=delDefined(patch,definitions);
                swagger.setDefinitions(definitions);
//                删除paths
                paths.remove(entry.getKey());
                swagger.setPaths(paths);
            }
        }
        //        删除tags
        List<Tag> tags = swagger.getTags();
        List<Tag> tags1 = new ArrayList<>();
        for (Tag tag : tags) {
            if (!tag.getName().equals("basic-error-controller")){
                tags1.add(tag);
            }
        }
        swagger.setTags(tags1);
    }


    /**
     * 删除类的定义
     * @param method 请求方式
     * @param definitions 类定义集合
     * @return 类定义集合
     */
    public Map<String, Model>  delDefined(Operation method,Map<String, Model> definitions ){
        List<Parameter> parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            BodyParameter bodyParameter=new BodyParameter();
            BeanUtils.copyProperties(parameter,bodyParameter);
            Model schema = bodyParameter.getSchema();
            if (schema!=null){
                // #/definitions/页面组件表
                String reference = schema.getReference();
                String[] strings=reference.split("/");
                String definedName=strings[strings.length-1];
//                        删除definitions
                definitions.remove(definedName);
            }
        }
        return definitions;
    }
}
// end::MySwaggerModelExtension[]