package com.tsu.xjq.dao;

import com.tsu.xjq.pojo.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @ClassName AdminDao
 * @Author Elv1s
 * @Date 2019/3/8 14:30
 * @Description:
 */
@Mapper
public interface AdminDao {


    /**
     * 查询admin待处理请求
     * @return
     */
    @Select("select v.id,s.sid,s.name,v.cycle,v.reason,v.time,s.telephone  " +
            "from student s ,vacate v " +
            "where  " +
            "s.sid = v.sid and " +
            "v.status = 6 order by v.id desc")
    List<VacateInfo> getPendingVacates();

    /**
     * 获得i年级下的班级
     * @param grade
     * @return
     */
    @Select("select gid, name from grade where grade = #{i}")
    List<Grade> getGrades(int grade);

    /**
     * 通过vid获得vacateinfo
     * @param vid
     * @return
     */
    @Select("select * from student s, vacate v where s.sid = v.sid and v.id = #{vid}")
    VacateInfo getVacateInfoByVid(String vid);

    /**
     * 更新status
     * @param vid
     * @param status
     * @param name
     */
    @Update("update vacate set " +
            "status = #{status} , " +
            "agrees = #{name}" +
            " where id = #{vid}")
    void upDateVacate(String vid, String status, String name);

    @Select("select * from admin where id = #{id} and password = #{password}" )
    Admin getAdminById(String id, String password);

    @Select("select * from grade where   grade = #{grade} and name = #{name}")
    Grade queryGrade(String name, Integer grade);

    @Insert("insert into grade (name,grade) values(#{name},#{grade})")
    @Options(useGeneratedKeys=true, keyProperty="userId", keyColumn="id")
    int insertGrade(String name, String grade);

    @Select("select * from teacher where grade = #{grade} and class_name = #{name}")
    Teacher getTeacherByGrade(Integer grade, String name);

    @Delete("delete from student where grade = #{gid}")
    void delteAllStudentsByGid(Integer gid);

    @Insert("insert into student (sid,password,name,tid,dormNum,grade,gender,email,telephone) " +
            "  values(#{sid},#{password},#{name},#{tid},#{dormNum},#{gid},#{gender}," +
            "#{email},#{telephone})")
    void insertStudent(int sid, String email, String name, String telephone,
                       String dormNum, String password, String gender, Integer gid, int tid);

    @Select("set foreign_key_checks = #{i}")
    void setFKchecks(int i);

    @Insert("insert into teacher(tid,name,password,email,telephone," +
            "office,grade) values(#{tid},#{teacher.name},#{teacher.passWord},#{teacher.email},#{teacher.telephone}," +
            "#{teacher.office},#{teacher.grade})")
    void insertTeacher(int tid,Teacher teacher,String grade);
}
