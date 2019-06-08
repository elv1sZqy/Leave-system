package com.tsu.xjq.dao;

import com.tsu.xjq.pojo.Student;
import com.tsu.xjq.pojo.Teacher;
import com.tsu.xjq.pojo.Vacate;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @ClassName StudentDao
 * @Author Elv1s
 * @Date 2019/1/14 13:30
 * @Description:
 */
@Mapper
public interface StudentDao {

    @Select("select * from student where sid = #{id}")
    List<Student> getStu(String id);

    @Select("select * from student")
    List<Student> getAllStu();

    @Delete("delete from student where sid = #{id}")
    void deleteStu(String id);

    /**
     * 获得sid下所有请假记录
     * @param sid
     * @return
     */
    @Select("select * from vacate where sid = #{sid} order by id desc")
    List<Vacate> getVacateList(String sid);

    /**
     * 学生申请请假
     * @param reason
     * @param range
     * @param sid
     * @param time
     * @param days
     */
    @Insert("insert into vacate(cycle,time,reason,sid,days) " +
            "values(#{range},#{time},#{reason},#{sid},#{days})")
    void addVacate(String reason, String range, int sid, String time,String days);

    @Select("select * from teacher t, student s " +
            " where t.tid = s.tid and sid = #{sid}")
    Teacher getTeacherBySid(int sid);


    /**
     * 学生修改联系方式
     * @param sid
     * @param email
     * @param telephone
     */
    @Update("update student " +
            "set " +
            "email = #{email}, " +
            "telephone = #{telephone} " +
            "where sid = #{sid}"
    )
    void saveStudent(int sid,String email, String telephone);
    @Select("select max(id) from vacate ")
    int getMaxId();
}
