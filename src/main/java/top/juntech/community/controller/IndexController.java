package top.juntech.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.juntech.community.dto.AccessTokenDto;
import top.juntech.community.dto.GithubUser;
import top.juntech.community.mapper.UserMapper;
import top.juntech.community.model.User;
import top.juntech.community.provider.GithubProvider;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


/**
 * @author juntech
 * @version ${version}
 * @date 2020/6/1 14:50
 * @ClassName IndexController
 * @Descripe 论坛首页控制器
 */
@Controller
public class IndexController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserMapper userMapper;

    @Value("${github.client.id}")
    public String CLIENT_ID;
    @Value("${github.client.secret}")
    public  String CLIENT_SECRET;
    @Value("${github.redirect.uri}")
    public  String CALLBACK;

    @GetMapping(value = "/")
    public String index(HttpServletRequest request){
        /*
        * 获取token
        * */
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("token")){
                String token = cookie.getValue();
                /*
                * 根据token获取用户信息
                * */
                if(token!=null){
                    User user = userMapper.findByToken(token);
                    if(user!=null){
                        request.getSession().setAttribute("user",user);
                    }
                }
                break;
            }
        }
        return "index";
    }

    @GetMapping(value = "/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(value = "state")String state,
                           /*HttpServletRequest request,*/
                           HttpServletResponse response){
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(CALLBACK);
        accessTokenDto.setState(state);
        accessTokenDto.setClient_id(CLIENT_ID);
        accessTokenDto.setClient_secret(CLIENT_SECRET);
        String access_token = githubProvider.getAccessToken(accessTokenDto);
        GithubUser githubUser = githubProvider.getUser(access_token);
        if(githubUser!=null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setBio(githubUser.getBio());
            userMapper.insert(user);
            //登录成功，写入session和cookie,页面重定向
//            request.getSession().setAttribute("user",githubUser);
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else {
            //登录失败重新登录
            return "redirect:/";
        }

    }
}
