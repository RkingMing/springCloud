package com.atguigu.springcloud.service;


import com.atguigu.springcloud.model.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ArticleRestService {

    public String saveArticle( Article article) {

        log.info("saveArticle：{}",article);

        return  "测试";
    }
}
