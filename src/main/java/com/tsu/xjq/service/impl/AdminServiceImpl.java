package com.tsu.xjq.service.impl;

import com.tsu.xjq.dao.AdminDao;
import com.tsu.xjq.pojo.*;
import com.tsu.xjq.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName AdminServiceImpl
 * @Author Elv1s
 * @Date 2019/3/8 14:30
 * @Description:
 */
@Service
public class AdminServiceImpl  implements AdminService {

    @Autowired
    AdminDao adminDao;

    @Override
    public EasyUIDataGridResult getPendingVacates(int page, int rows) {
        List<VacateInfo> needVacates = new ArrayList<>();
        List<VacateInfo> allVacates = adminDao.getPendingVacates();
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        //设置总记录数
        result.setTotal(allVacates.size());
        //当前页首条对象的index
        int firstIndex = (page - 1) * rows;
        //当前页最后一个index
        int lastIndex = page * rows;
        //防止index>list.size
        if (lastIndex >= allVacates.size()) {
            lastIndex = allVacates.size();
        }
        for (int i = firstIndex; i < lastIndex; i++) {
            VacateInfo vacate = allVacates.get(i);
            needVacates.add(vacate);
        }
        //设置当前页显示的对象
        result.setRows(needVacates);
        return result;
    }

    @Override
    public List<Grade> getGrades(int i) {
        return adminDao.getGrades(i);
    }

    @Override
    public List<VacateInfo> adoptVacates(String status, String[] ids,String name) {
        List<VacateInfo> vacateInfos = new ArrayList<>();
        for (String vid: ids ) {

            VacateInfo vacateinfo = adminDao.getVacateInfoByVid(vid);
            String agrees = vacateinfo.getAgrees();
            agrees = agrees + "," + name;
            adminDao.upDateVacate(vid,status,agrees);
            vacateInfos.add(vacateinfo);
        }
        return vacateInfos;
    }

    @Override
    public Admin getAdminById(String id, String password) {
        return adminDao.getAdminById(id,password);
    }

    @Override
    public void insertStudents(List<Student> studentList,String name, String grade,Teacher teacher) {
        int i = Integer.parseInt(grade);
        int gid = 0,tid = 0;
        Grade grade1 = adminDao.queryGrade(name,i);
        //班级不存在的情况
        if (grade1 == null){
            System.out.println(teacher);
            //如果当前班级不存在,则新增一个
            gid = adminDao.insertGrade(name, grade);
            //插入新增的班级的班主任
            int tid1 = teacher.getTid();
            adminDao.insertTeacher(tid1,teacher,grade);
            tid = teacher.getTid();
        }
        //班级存在的情况
        else{
            gid = grade1.getGid();
            Teacher teacher1 = adminDao.getTeacherByGrade(i,name);
            tid = teacher1.getTid();
            //关闭外键关联
            adminDao.setFKchecks(0);
            //删除存在的学生
            adminDao.delteAllStudentsByGid(gid);
            //开启外键关联
            adminDao.setFKchecks(1);
        }

        //插入学生
        for (Student student : studentList ) {
            adminDao.insertStudent(student.getSid(),student.getEmail(),student.getName(),
                    student.getTelephone(),student.getDormNum(),student.getPassword(),
                    student.getGender(),gid,tid);
        }
    }

    @Override
    public Grade getGrade(String grade, String gname) {
        int i = Integer.parseInt(grade);
        return adminDao.queryGrade(gname, i);
    }
}
