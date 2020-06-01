package top.juntech.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.juntech.community.dto.AccessTokenDto;
import top.juntech.community.dto.GithubUser;
import top.juntech.community.provider.GithubProvider;

import javax.servlet.http.HttpServletRequest;


/**
 * @author juntech
 * @version ${version}
 * @date 2020/6/1 14:50
 * @ClassName 类名
 * @Descripe 论坛首页控制器
 */
@Controller
public class IndexController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    public String CLIENT_ID;
    @Value("${github.client.secret}")
    public  String CLIENT_SECRET;
    @Value("${github.redirect.uri}")
    public  String CALLBACK;

    @GetMapping(value = "/")
    public String index(){
        return "index";
    }

    @GetMapping(value = "/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(value = "state")String state,
                           HttpServletRequest request){
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(CALLBACK);
        accessTokenDto.setState(state);
        accessTokenDto.setClient_id(CLIENT_ID);
        accessTokenDto.setClient_secret(CLIENT_SECRET);
        String access_token = githubProvider.getAccessToken(accessTokenDto);
        GithubUser user = githubProvider.getUser(access_token);
        if(user!=null){
//            System.out.println(user.getName());
            request.getSession().setAttribute("user",user);
            //登录成功，页面重定向
            return "redirect:/";
        }else {
            //登录失败重新登录
            return "redirect:/";
        }

    }
}
