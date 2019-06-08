package com.tsu.xjq.service.impl;

import com.tsu.xjq.dao.StudentDao;
import com.tsu.xjq.pojo.*;
import com.tsu.xjq.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName StudentImpl
 * @Author Elv1s
 * @Date 2019/1/14 14:00
 * @Description:
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentDao studentDao;
    @Autowired
    EasyUIDataGridResult result;
    @Autowired
    LayuiResult layuiResult;


    /**
     * 通过id查学生
     *
     * @param id
     * @return
     */
    @Override
    public List<Student> getStu(String id) {
        return studentDao.getStu(id);
    }

    @Override
    public LayuiResult getAllStu(int page, int rows) {
        List<Student> needStu = new ArrayList<>();
        List<Student> allStu = studentDao.getAllStu();
        //设置总记录数
        layuiResult.setCount(allStu.size());
        //当前页首条对象的index
        int firstIndex = (page - 1) * rows;
        //当前页最后一个index
        int lastIndex = page * rows;
        //防止index>list.size
        if (lastIndex >= allStu.size()) {
            lastIndex = allStu.size();
        }
        for (int i = firstIndex; i < lastIndex; i++) {
            Student student = allStu.get(i);
            needStu.add(student);
        }
        //设置当前页显示的对象
        layuiResult.setData(needStu);
        layuiResult.setCode("0");
        layuiResult.setMsg("");
        return layuiResult;
    }

    @Override
    public LayuiResult getVacates(String id, int page, int limit) {
        List<Vacate> needVacates = new ArrayList<>();
        List<Vacate> allVacates = studentDao.getVacateList(id);
        //设置总记录数
        layuiResult.setCount(allVacates.size());
        //当前页首条对象的index
        int firstIndex = (page - 1) * limit;
        //当前页最后一个index
        int lastIndex = page * limit;
        //防止index>list.size
        if (lastIndex >= allVacates.size()) {
            lastIndex = allVacates.size();
        }
        for (int i = firstIndex; i < lastIndex; i++) {
            Vacate vacate = allVacates.get(i);
            needVacates.add(vacate);
        }
        //设置当前页显示的对象
        layuiResult.setData(needVacates);
        layuiResult.setCode("0");
        layuiResult.setMsg("");
        return layuiResult;
    }

    @Override
    public int addVacate(String days, String reason, String range, String sid) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式   yyyy-MM-dd HH:mm:ss
        String time = df.format(new Date());
        int i = Integer.parseInt(sid);
        //插入数据
        studentDao.addVacate(reason, range, i, time, days);
        int maxId = studentDao.getMaxId();
        return maxId;
    }

    @Override
    public Teacher getTeacherBySid(int sid) {
        return studentDao.getTeacherBySid(sid);
    }

    @Override
    public void saveStudent(Student student) {
        int sid = student.getSid();
        String email = student.getEmail();
        String telephone = student.getTelephone();
        studentDao.saveStudent(sid, email, telephone);
    }

    @Override
    public EasyUIDataGridResult getAllStuForPage(int page, int rows) {
        List<Student> needStu = new ArrayList<>();
        List<Student> allStu = studentDao.getAllStu();
        //设置总记录数
        result.setTotal(allStu.size());
        //当前页首条对象的index
        int firstIndex = (page - 1) * rows;
        //当前页最后一个index
        int lastIndex = page * rows;
        //防止index>list.size
        if (lastIndex >= allStu.size()) {
            lastIndex = allStu.size();
        }
        for (int i = firstIndex; i < lastIndex; i++) {
            Student student = allStu.get(i);
            needStu.add(student);
        }
        //设置当前页显示的对象
        result.setRows(needStu);
        return result;
    }

    /**
     * *通过ID 删除多个学生
     *
     * @param ids
     */
    @Override
    public void deleteStus(String[] ids) {
        for (String id : ids) {
            studentDao.deleteStu(id);
        }
    }

    /**
     * 获得最新的五条记录
     *
     * @param sid
     * @return
     */
    @Override
    public List<Vacate> getVacateList(String sid) {
        List<Vacate> vacateList = studentDao.getVacateList(sid);
        List<Vacate> fiveVacates = new ArrayList();
        if (vacateList.size() < 5) {
            return vacateList;
        }
        //大于5条
        else {
            Vacate vacate = null;
            for (int i = 0; i < 5; i++) {
                 vacate = vacateList.get(i);
                fiveVacates.add(vacate);
            }
            return fiveVacates;
        }
    }


}
