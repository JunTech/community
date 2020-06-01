package top.juntech.community.provider;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;
import top.juntech.community.dto.AccessTokenDto;
import top.juntech.community.dto.GithubUser;

import java.io.IOException;

/**
 * @author juntech
 * @version ${version}
 * @date 2020/6/1 20:33
 * @ClassName 类名
 * @Descripe 描述
 */
@Component
@Slf4j
public class GithubProvider {
    public String getAccessToken(AccessTokenDto accessTokenDto){
        log.debug("获取access_token:");
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDto));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str =  response.body().string();
            String[] split = str.split("&");
            String token = split[0].substring(13);
            System.out.println("token="+token);
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public GithubUser getUser(String access_token){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization","token "+access_token)
                .build();
        System.out.println(request.url());
        try {
            Response response = client.newCall(request).execute();
            String s = response.body().toString();
            GithubUser githubUser = JSON.parseObject(s, GithubUser.class);
            System.out.println(githubUser.toString());
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
