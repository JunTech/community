package top.juntech.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * @author juntech
 * @version ${version}
 * @date 2020/6/1 21:13
 * @ClassName 类名
 * @Descripe 描述
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GithubUser {
    private String name;
    private long id;
    private String bio;
    private String avatar_url;
}
