package com.atguigu.springcloud.controller;


import com.atguigu.springcloud.model.Article;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.async.AsyncLoggerContextSelector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1")
public class HelloController {

    //private static final Logger log = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/hello")
    public Article hello() {
        /*ArticleRestService article = new ArticleRestService(1L,"zimug");

        article.setAuthor("字母哥");*/

        Article article1 = Article.builder().id(3L).author("莫言").build();
        for (int i = 0; i < 1000; i++) {
            log.debug("DEBUG:{},啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊", AsyncLoggerContextSelector.isSelected());
        }
        for (int i = 0; i < 1000; i++) {
            log.info("INFO:{},啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊", AsyncLoggerContextSelector.isSelected());
        }
        for (int i = 0; i < 1000; i++) {
            log.warn("WARN:{},啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊", AsyncLoggerContextSelector.isSelected());
        }
        for (int i = 0; i < 1000; i++) {
            log.error("ERROR:{},啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊", AsyncLoggerContextSelector.isSelected());
        }


        return article1;
    }

}
