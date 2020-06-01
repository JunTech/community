package top.juntech.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * @author juntech
 * @version ${version}
 * @date 2020/6/1 20:35
 * @ClassName 类名
 * @Descripe 描述
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenDto {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
