package org.lf.community.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.lf.community.community.model.Question;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    public void create(Question question);

    @Select("select * from question limit #{offset}, #{size} ")
    List<Question> list(@Value("offset") Integer offset, @Value("size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select count(1) from question where creator = #{accountId}")
    Integer countByAccounId(@Value("accountId") String accountId);

    @Select("select * from question  where creator = #{accountId} limit #{offset}, #{size} ")
    List<Question> findAllByAccount(@Value("accountId") String accountId, Integer offset, Integer size);
}
