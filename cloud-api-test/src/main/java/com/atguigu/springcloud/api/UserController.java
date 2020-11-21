package com.atguigu.springcloud.api;

import com.atguigu.springcloud.model.User;
import com.atguigu.springcloud.service.UserService;
import org.springframework.web.bind.annotation.*;

/**
 * description:
 *
 * @author xiaoming.li04@hand-china.com
 * @datatime 2020/09/14 9:15
 */
@RestController
@RequestMapping("/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public String  createUser(@RequestBody User user){
        userService.hello();
        return  "create user";
    }

    @GetMapping("/users/{id}")
    public  String getUser(@PathVariable("id")Integer id ){
        return "a user";
    }

}
