package org.lf.community.community.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.lf.community.community.model.User;

import java.util.Optional;

@Mapper
public interface UserMapper {

    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,bio,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{bio},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    Optional<User> findByToken(@Param("token") String token);

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Insert("update user set name = #{name}, token = #{token}, gmt_modified = #{gmtModified}, bio = #{bio}, avatar_url = #{avatarUrl} where id = #{id}")
    void update(User user);
}
