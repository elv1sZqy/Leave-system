package com.tsu.xjq.service;

import com.tsu.xjq.pojo.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName AdminService
 * @Author Elv1s
 * @Date 2019/3/8 14:29
 * @Description:
 */
@Transactional
public interface AdminService {
    /**
     * 拿到admin待处理请求
     * @param page
     * @param rows
     * @return
     */
    EasyUIDataGridResult getPendingVacates(int page, int rows);

    /**
     * 获得年级下班级信息
     * @param i
     * @return
     */
    List<Grade> getGrades(int i);

    /**
     * 批量处理请求 并获得vacateinfo
     * @param status 7为同意  8拒绝
     * @param ids  vids
     * @return
     */
    List<VacateInfo> adoptVacates(String status, String[] ids,String name);

    /**
     * admin 登录
     * @param id
     * @param password
     * @return
     */
    Admin getAdminById(String id, String password);


    /**
     * 批量插入学生信息
     * @param studentList
     * @param name
     * @param grade
     * @param teac
     */
    void insertStudents(List<Student> studentList, String name, String grade,Teacher teac);

    /**
     * 根据grade 和gname 查询班级是否存在
     * @param grade
     * @param gname
     * @return
     */
    Grade getGrade(String grade, String gname);
}
