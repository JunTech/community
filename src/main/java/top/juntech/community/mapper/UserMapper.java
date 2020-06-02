package top.juntech.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import top.juntech.community.model.User;

/**
 * @author juntech
 * @version ${version}
 * @date 2020/6/2 8:56
 * @ClassName 类名
 * @Descripe userMapper接口
 */
@Mapper
@Component
public interface UserMapper {

    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified,bio) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{bio})")
    public void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param(value = "token") String token);
}
