package top.juntech.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author juntech
 * @version ${version}
 * @date 2020/6/1 13:44
 * @ClassName 类名
 * @Descripe 测试项目环境
 */
@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello(@RequestParam(name = "name", required = true, defaultValue = "juntech") String name, Model model) {
        model.addAttribute("name",name);
        return "hello";
    }

}
