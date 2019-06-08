package com.tsu.xjq.dao;

import com.tsu.xjq.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @ClassName AssistantDao
 * @Author Elv1s
 * @Date 2019/3/7 9:06
 * @Description:
 */
@Mapper
public interface AssistantDao {

    @Select("select * from assistant where aid = #{aid} and password = #{password}")
    Assistant getAssistant(Assistant assistant);

    @Select("select * from grade where grade = #{grade}")
    List<Grade> getGrades(String grade);
    @Select("select v.id,s.sid,s.name,v.cycle,v.reason,v.time,s.telephone  " +
            "from grade g ,student s ,vacate v " +
            "where g.gid = s.grade and " +
            "s.sid = v.sid and " +
            "g.grade = #{grade} and " +
            "g.name = #{name}")
    List<VacateInfo> getAllVacates(String name, String grade);

    @Select("select v.id,s.sid,s.name,v.cycle,v.reason,v.time,s.telephone  " +
            "from grade g ,student s ,vacate v " +
            "where g.gid = s.grade and " +
            "s.sid = v.sid and " +
            "g.grade = #{grade} and " +
            "v.status = 3 order by v.id desc")
    List<VacateInfo> getPendingVacates(String grade);

    @Update("update vacate " +
            "set status = #{status} , " +
            "agrees = #{agrees} " +
            "where id = #{vid}")
    void adoptVacate(String status, String vid,String agrees);

    @Select("select *from vacate where id = #{vid}")
    Vacate getVacateByVid(String vid);
    @Select("select s.name ,s.email,v.days,v.id from student s , vacate v where v.sid = s.sid and v.id = #{vid}")
    VacateInfo getVacateinfoByVid(String vid);

    @Update("update vacate set " +
            "status = #{s} " +
            "where id = #{vid}")
    void adoptVacate1(String s, String vid);
}
