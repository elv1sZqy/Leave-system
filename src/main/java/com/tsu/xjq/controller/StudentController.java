package com.tsu.xjq.controller;

import com.google.gson.Gson;
import com.tsu.xjq.pojo.LayuiResult;
import com.tsu.xjq.pojo.Student;
import com.tsu.xjq.pojo.Teacher;
import com.tsu.xjq.pojo.Vacate;
import com.tsu.xjq.service.StudentService;
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
 * @ClassName UserController
 * @Author Elv1s
 * @Date 2019/1/19 12:57
 * @Description:
 *          主要负责学生,教师等页面
 */
@Controller
public class StudentController {

    @Autowired
    StudentService studentService;
    @Autowired
    AsyncTaskUtil asyncTask;

    /**
     * 首页
     * @return
     */
    @RequestMapping("/")
    public String welcomePage(){
        return "index";
    }


    /**
     * 去学生请假详细列表页面
     * @return
     */
    @RequestMapping("/user/list")
    public String toList(){
        return "user/list";
    }

    /**
     * 获得指定sid的请假记录,并分页
     * @param id
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/get/{id}")
    @ResponseBody
    public String getVacates(@PathVariable String id, int page, int limit){
        //查询sid学生下的请假记录,已分页
        LayuiResult vacates = studentService.getVacates(id,page, limit);
        //转成Json串
        String s = new Gson().toJson(vacates);
      return s;
    }


    /**
     * 学生登入
     * @param id
     * @param pwd
     * @param md
     * @param session
     * @return
     */
    @RequestMapping("/loginForStudent")
    public String login(String id, String pwd, Model md, HttpSession session){
        List<Student> stu = studentService.getStu(id);
        //如果无该学生记录,返回登入页面
        if (stu.size() == 0 ){
            md.addAttribute("msg", "账号错误");
            return "index";
        }
        //有记录则准备信息返回前端
        if(stu.get(0).getPassword().equals(pwd)){
            //学生对象
            Student student = stu.get(0);
            //教师对象
            Teacher teacher = studentService.getTeacherBySid(student.getSid());
            student.setTeacher(teacher);
            //获得最新五条记录
            List<Vacate> vacates = studentService.getVacateList(id);

            //学生信息
            md.addAttribute("student", student);
            //请假记录
            md.addAttribute("vacates", vacates);

            //将sid放在session中方便之后使用
            session.setAttribute("sid", stu.get(0).getSid());
            session.setAttribute("student", student);

            return "user/student";
        }
        md.addAttribute("msg", "密码错误");
        return "index";
    }

     /**
     * 申请请假
     * @param days
     * @param reason
     * @param range
     * @param md
     * @param session
     * @return
     */
    @RequestMapping("/apply")
    public String applyVacate(String days, String reason,String range,Model md,HttpSession session) throws Exception {
        int sid = (int) session.getAttribute("sid");
        Teacher teacher = studentService.getTeacherBySid(sid);
        String id = sid + "";
        Student student = studentService.getStu(id).get(0);
        student.setTeacher(teacher);
        //申请的请假的id号
        int vid = studentService.addVacate(days, reason, range, id);

        //调用异步工作 发送邮件
        asyncTask.EmailToTeacher(teacher.getEmail(), student.getName(), teacher.getName(),
                student.getSid(), reason, days, range, vid);

        //该学生下的请假请求
        List<Vacate> vacateList = studentService.getVacateList(id);
        md.addAttribute("student",student);
        md.addAttribute("vacates", vacateList);
        session.setAttribute("student", student);
        return "user/student";
    }

    /**
     * 学生编辑自己的联系方式
     * @param stu
     * @param session
     * @param md
     * @return
     */
    @RequestMapping("/editInfo")
    public String editInfo(Student stu,HttpSession session,Model md){
        int id = (int) session.getAttribute("sid");
        Teacher teacher = studentService.getTeacherBySid(id);
        String sid = id + "";
        Student student = studentService.getStu(sid).get(0);
        student.setTelephone(stu.getTelephone());
        student.setEmail(stu.getEmail());
        studentService.saveStudent(student);
        student.setTeacher(teacher);

        List<Vacate> vacateList = studentService.getVacateList(sid);
        md.addAttribute("student",student );
        md.addAttribute("vacates", vacateList);
        return "user/student";
    }
}
