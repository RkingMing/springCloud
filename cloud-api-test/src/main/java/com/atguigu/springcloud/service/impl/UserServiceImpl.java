package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.service.UserService;
import org.springframework.stereotype.Service;

/**
 * description:
 *
 * @author xiaoming.li04@hand-china.com
 * @datatime 2020/10/28 20:02
 */
@Service
public class UserServiceImpl implements UserService {


    @Override
    public void hello() {
        System.out.println("hello.........");
    }
}
