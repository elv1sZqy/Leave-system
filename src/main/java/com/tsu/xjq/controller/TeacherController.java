package com.tsu.xjq.controller;

import com.tsu.xjq.pojo.*;
import com.tsu.xjq.service.TeacherService;
import com.tsu.xjq.utils.AsyncTaskUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @ClassName TeacherController
 * @Author Elv1s
 * @Date 2019/3/3 9:14
 * @Description:
 */
@Controller
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @Autowired
    AsyncTaskUtil asyncTask;

    /**
     * 教师登录页面准备
     * @param id
     * @param pwd
     * @param md
     * @param session
     * @return
     */
    @RequestMapping("/loginForTeacher")
    public String login(String id, String pwd, Model md, HttpSession session){
        List<Teacher> teacher = teacherService.getTeacher(id);
        //如果无该老师记录,返回登入页面
        if (teacher.size() == 0 ){
            md.addAttribute("msg", "账号错误");
            return "index";
        }
        //有记录则准备信息返回前端
        if(teacher.get(0).getPassWord().equals(pwd)){
            //老师对象
            Teacher teacher1 = teacher.get(0);
            //老师下属的学生对象
            List<Student> students = teacherService.getStudentByTid(id);
            teacher1.setStudents(students);
            //待处理的请假请求
            List<VacateInfo> vacateIsZero = teacherService.getVacateIsZero(id);
            //全班已经生效的所有请求
            List<VacateInfo> vacateIsPass = teacherService.getVacateIsOne(id);
            //本年级的辅导员对象
            Assistant assistant = teacherService.getAssistant(teacher1.getGrade());
            teacher1.setAssistant(assistant);

            //将sid放在session中方便之后使用
            session.setAttribute("teacher", teacher1);
            //status为0的记录(待处理)
            md.addAttribute("vacateIsZero", vacateIsZero);
            //status为1的记录(已通过)
            md.addAttribute("vacateIsPass", vacateIsPass);

            return "user/teacher";
        }
        md.addAttribute("msg", "密码错误");
        return "index";
    }

    /**
     * 处理请假请求
     * @param id      请假请求的id
     * @param status 请求是否通过的状态
     *               *  小于三天: 0待处理   1 教师通过  2教师未通过
                     * 大于三天小于一周: 0待处理    3教师通过
                     * 大于一周: 0待处理  3教师通过
     * @param session
     * @param md
     * @return
     */
    @RequestMapping("/adopt/{id}/{status}")
    public String adopt(@PathVariable String id, @PathVariable String status,HttpSession session, Model md){
        Student student = teacherService.getStudentByVid(id);
        Teacher teacher = teacherService.getTeacherBysid(student.getSid());
        int tid = teacher.getTid();
        //"2" 为不准假
        if (status.equals("2")){
            //发送邮件提醒学生请假没通过
            asyncTask.EmailToStudentIsNotPass(student.getEmail(), student.getName(),
                    teacher.getName(), teacher.getTelephone());
            teacherService.adopt(id,status,teacher.getName());
        }
        else {
            /*通过了的情况
            请假天数小于等于3:  status = 1
            大余3小于7 :  status = 3
            大于7:   status = 3
        */
            Vacate vacate = teacherService.getVacateByVid(id);

            if (vacate.getDays() <= 3){
                teacherService.adopt(id,"1",teacher.getName());
                //小于等于三天  不需要之后审核, 直接发送邮件
                //发送邮件提醒学生请假通过了
                asyncTask.EmailToStudentIsPass(student.getEmail(),student.getName() ,teacher.getName() );
            }
            else {
                //大于3天 ,需要之后审核,暂时不发送邮件
                teacherService.adopt(id,"3",teacher.getName());
            }
        }

        //待处理的请假请求
        List<VacateInfo> vacateIsZero = teacherService.getVacateIsZero(tid+"");
        //全班已经生效的所有请求
        List<VacateInfo> vacateIsOne = teacherService.getVacateIsOne(tid +"");
        //status为0的记录(待处理)
        md.addAttribute("vacateIsZero", vacateIsZero);
        //status为1的记录(已通过)
        md.addAttribute("vacateIsPass", vacateIsOne);

        return "user/teacher";
    }

    /**
     * 处理请假请求,通过邮件链接
     * @param vid  请假ID
     * @param status 请求是否通过的状态
     *               *  小于三天: 0待处理   1 教师通过  2教师未通过
                     * 大于三天小于一周: 0待处理    3教师通过
                     * 大于一周: 0待处理  3教师通过

     * @return
     */
    @ResponseBody
    @RequestMapping("/adopt2/{vid}/{status}")
    public String adoptFromEmail(@PathVariable String vid, @PathVariable String status){
        //学生
        Student student = teacherService.getStudentByVid(vid);
        //老师
        Teacher teacher = teacherService.getTeacherBysid(student.getSid());

        Vacate vacate = teacherService.getVacateByVid(vid);

        //"2" 为不准假
        if (status.equals("2")){
            //发送邮件提醒学生请假没通过
            asyncTask.EmailToStudentIsNotPass(student.getEmail(), student.getName(),
                    teacher.getName(), teacher.getTelephone());
            teacherService.adopt(vid,status,teacher.getName());
            return "已经拒绝了" + student.getName() + "(" + student.getSid() + ")的请假";
        }
        else {
            /*通过了的情况
            请假天数小于等于3:  status = 1
            大余3小于7 :  status = 3
            大于7:   status = 3
        */
            if (vacate.getDays() <= 3){
                teacherService.adopt(vid,"1",teacher.getName());
                //小于等于三天  不需要之后审核, 直接发送邮件
                //发送邮件提醒学生请假通过了
                asyncTask.EmailToStudentIsPass(student.getEmail(),student.getName() ,teacher.getName() );
            }
            else {
                //大于3天 ,需要之后审核,暂时不发送邮件
                teacherService.adopt(vid,"3",teacher.getName());

            }
            return "已通过" + student.getName() + "(" + student.getSid() + ")的请假";
        }
    }

}
