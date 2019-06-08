package com.tsu.xjq.service;

import com.tsu.xjq.pojo.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName StudentService
 * @Author Elv1s
 * @Date 2019/1/14 13:34
 * @Description:
 */
@Transactional
public interface StudentService {

    /**
     * 通过id查处一个学生
     * @param id
     * @return
     */
    List<Student> getStu(String id);

    /**
     * 获得所有学生对象(layui)
     * @return List<Student>
     */
    LayuiResult getAllStu(int page, int rows);

    /**
     * 获得所有学生对象, 用于分页(easyui)
     * @param page
     * @param rows
     * @return  EasyUIDataGridResult
     */
    EasyUIDataGridResult getAllStuForPage(int page,int rows);

    /**
     * 删除多个或一个学生对象
     * @param ids
     */
     void deleteStus(String[] ids);

    /**
     * 获得sid这个学生最新的五条请假记录
     * @param sid
     * @return
     */
    List<Vacate> getVacateList(String sid);

    /**
     * 某学生的请假记录,并分页
     * @param id
     * @param page
     * @param limit
     * @return
     */
    LayuiResult getVacates(String id, int page, int limit);

    /**
     * 学生申请请假,等待老师审核
     * @param days 请假天数
     * @param reason  请假原因
     * @param range    请假范围
     * @param sid   学生id
     * @return int
     */
    int addVacate(String days, String reason, String range, String sid);



    /**
     * 通过tid查询教师
     * @param sid
     * @return
     */
    Teacher getTeacherBySid(int sid);

    /**
     * 保存学生对象
     * @param student
     */
    void saveStudent(Student student);
}
