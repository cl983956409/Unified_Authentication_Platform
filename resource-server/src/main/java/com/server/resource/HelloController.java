package com.server.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @param
 * @author 程龙[chenglonghy]
 * @return
 * @date 2020/5/25 9:56
 * @history 2020/5/25 9:56 程龙[chenglonghy]  create.
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
    @GetMapping("/admin/hello")
    public String admin() {
        return "admin";
    }
}
