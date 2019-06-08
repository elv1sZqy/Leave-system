package com.tsu.xjq.dao;

import com.tsu.xjq.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TeacherDao {

    @Select("select s.sid,name,email,telephone,s.tid from student s ,vacate v where s.sid = v.sid and v.id = #{id};")
    Student getStudentByVid(String id);

    @Select("select * from teacher where tid = #{id}")
    List<Teacher> getTeacherById(String id);

    @Select("select * from student where tid = #{id}")
    List<Student> getStudentsByTid(String id);

    @Select("select * from vacate where sid = #{sid} order by id desc ")
    List<Vacate> getVacateBysid(int sid);

    @Select("select * from student s join vacate v on s.sid = v.sid where s.tid = #{tid} and v.status = 0 order by v.id desc")
    List<VacateInfo> getVacateIsZero(String tid);

    @Select("select * from student s join vacate v on s.sid = v.sid where s.tid = #{tid} and v.status = 1 or " +
            "v.status = 3 or " +
            "v.status = 5 or " +
            "v.status = 6 or " +
            "v.status = 7 order by v.id desc")
    List<VacateInfo> getVacateIsOne(String tid);

    @Select("select * from assistant where grade = #{grade}")
    Assistant getAssistant(String grade);
    @Update("update vacate " +
            "set status = #{status} , " +
            "agrees = #{name} " +
            "where id = #{id}")
    void adopt(String id, String status,String name);

    @Select("select * from vacate where id = #{id}")
    Vacate getVacateByVid(String id);

    @Select("select * from teacher t , student s where t.tid = s.tid and s.sid = #{sid}")
    Teacher getTeacherBySid(int sid);
}
