package top.juntech.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author juntech
 * @version ${version}
 * @date 2020/6/1 14:50
 * @ClassName 类名
 * @Descripe 论坛首页控制器
 */
@Controller
public class IndexController {


    @GetMapping(value = "/")
    public String index(){
        return "index";
    }
}
