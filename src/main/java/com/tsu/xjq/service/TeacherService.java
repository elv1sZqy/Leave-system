package com.tsu.xjq.service;

import com.tsu.xjq.pojo.*;

import java.util.List;

public interface TeacherService {

    /**
     * 根据tid查老师对象
     * @param id
     * @return
     */
    List<Teacher> getTeacher(String id);

    List<Student> getStudentByTid(String id);

    List<Integer> getSidBytudent(List<Student> students);

    List<Vacate> getVacatesByStudent(List<Student> students);

    /**
     * 查询某老师下 状态是0的请假记录
     * @param tid
     * @return
     */
    List<VacateInfo> getVacateIsZero(String tid);

    /**
     * 查询某老师下 状态是通过的请假记录
     * @param tid
     * @return
     */
    List<VacateInfo> getVacateIsOne(String tid);

    /**
     * 获得本年级的辅导员
     * @param grade
     * @return
     */
    Assistant getAssistant(String grade);

    /**
     * 审批请求, 并说明审批人
     * @param id
     * @param status
     * @param name
     */
    void adopt(String id, String status,String name);

    /**
     * 通过请假id查询请假的学生
     * @param id
     * @return
     */
    Student getStudentByVid(String id);

    /**
     * 通过vid获得vacate
     * @param id
     * @return
     */
    Vacate getVacateByVid(String id);

    /**
     * 通过sid查老师对象
     * @param sid
     * @return
     */
    Teacher getTeacherBysid(int sid);
}
