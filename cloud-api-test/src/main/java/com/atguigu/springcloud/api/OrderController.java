package com.atguigu.springcloud.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description:
 *
 * @author xiaoming.li04@hand-china.com
 * @datatime 2020/09/14 9:27
 */
@RestController
@RequestMapping("/v1")
public class OrderController {

    @Value("${name}")
    private String name;
    @Value("${age}")
    private Integer age;
    @PostMapping("/orders")
    public String createOrder(){
        System.out.println("age:"+age);
        System.out.println("name:"+name);
        return "create order";
    }

}
