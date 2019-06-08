package com.tsu.xjq.service.impl;

import com.tsu.xjq.dao.TeacherDao;
import com.tsu.xjq.pojo.*;
import com.tsu.xjq.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TeacherServiceImpl
 * @Author Elv1s
 * @Date 2019/3/3 9:15
 * @Description:
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    TeacherDao teacherDao;

    @Override
    public List<Teacher> getTeacher(String id) {
        return teacherDao.getTeacherById(id);
    }

    @Override
    public List<Student> getStudentByTid(String id) {
        return teacherDao.getStudentsByTid(id);
    }

    @Override
    public List<Integer> getSidBytudent(List<Student> students) {
        List<Integer> ids = new ArrayList<>();
        for (Student student : students) {
            int sid = student.getSid();
            ids.add(sid);
        }
        return ids;
    }


    /**
     * 一位教师下所有学生以及所有请假记录
     * @param students
     * @return
     */
    @Override
    public List<Vacate> getVacatesByStudent(List<Student> students) {
        List<Vacate> vacates = new ArrayList<>();
        for (Student student : students) {
            int sid = student.getSid();
            List<Vacate> vacateList =  teacherDao.getVacateBysid(sid);
            vacates.addAll(vacateList);
        }
        return vacates;
    }

    @Override
    public List<VacateInfo> getVacateIsZero(String tid) {
       List<VacateInfo> vacateInfos = teacherDao.getVacateIsZero(tid);
        return vacateInfos;
    }

    @Override
    public List<VacateInfo> getVacateIsOne(String tid) {
        return teacherDao.getVacateIsOne(tid);
    }

    @Override
    public Assistant getAssistant(String grade) {
        return teacherDao.getAssistant(grade);
    }

    @Override
    public void adopt(String id, String status,String name) {
        teacherDao.adopt(id,status,name);
    }

    @Override
    public Student getStudentByVid(String id) {
        Student student = teacherDao.getStudentByVid(id);
        return student;
    }

    @Override
    public Vacate getVacateByVid(String id) {
        return teacherDao.getVacateByVid(id);
    }

    @Override
    public Teacher getTeacherBysid(int sid) {
        return teacherDao.getTeacherBySid(sid);
    }


}
