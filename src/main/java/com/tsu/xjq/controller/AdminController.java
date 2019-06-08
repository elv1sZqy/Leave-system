package com.tsu.xjq.controller;

import com.tsu.xjq.pojo.*;
import com.tsu.xjq.service.AdminService;
import com.tsu.xjq.service.AssistantService;
import com.tsu.xjq.utils.AsyncTaskUtil;
import com.tsu.xjq.utils.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName AdminController
 * @Author Elv1s
 * @Date 2019/1/7 15:57
 * @Description: 主要负责管理员的页面的跳转以及数据处理
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;
    @Autowired
    AssistantService assistantService;
    @Autowired
    AsyncTaskUtil asyncTask;

    /**
     * 去登录页面
     *
     * @return
     */
    @RequestMapping("")
    public String toLogin() {
        return "admin/login";
    }

    /**
     * login to index
     *
     * @return
     */
    @RequestMapping("/login")
    public String loginPage(Model md, String id, String password, HttpSession session) {
        Admin admin = adminService.getAdminById(id, password);
        List<Grade> grades16 = adminService.getGrades(16);
        List<Grade> grades17 = adminService.getGrades(17);
        List<Grade> grades18 = adminService.getGrades(18);
        List<Grade> grades15 = adminService.getGrades(15);

        session.setAttribute("admin", admin);
        md.addAttribute("grades15", grades15);
        md.addAttribute("grades16", grades16);
        md.addAttribute("grades17", grades17);
        md.addAttribute("grades18", grades18);

        return "admin/index";
    }

    /**
     * tabs跳转
     *
     * @param page
     * @return
     */
    @RequestMapping("{page}")
    public String toPage(@PathVariable String page) {
        page = "admin/" + page;
        return page;
    }

    /**
     * 待处理
     *
     * @param page
     * @param rows
     * @param session
     * @return
     */
    @RequestMapping("/pending")
    @ResponseBody
    public EasyUIDataGridResult getPending(int page, int rows, HttpSession session) {

        EasyUIDataGridResult pendingVacates = adminService.getPendingVacates(page, rows);
        return pendingVacates;
    }

    /**
     * 获取className的所有请假记录
     *
     * @param page
     * @param rows
     * @param className
     * @param grade
     * @return
     */
    @RequestMapping("/getVacates")
    @ResponseBody
    public EasyUIDataGridResult getVacates(int page, int rows, String className, String grade) {
        EasyUIDataGridResult vacates = assistantService.getVacates(page, rows, className, grade);
        return vacates;
    }

    @ResponseBody
    @RequestMapping("/adopt/{status}")
    public String adoptVacates(@PathVariable String status, String[] ids, HttpSession session) {
        Admin admin = (Admin) session.getAttribute("admin");
        //不同意的情况
        if (status.equals("8")) {
            //数据库vacate.status=8
            List<VacateInfo> vacateInfos = adminService.adoptVacates(status, ids, admin.getName());
            //发送邮件提示
            for (VacateInfo vacateInfo : vacateInfos) {
                asyncTask.EmailToStudentIsNotPass(vacateInfo.getEmail(),
                        vacateInfo.getName(), admin.getName(), admin.getTele());
            }
            return "200";
        }
        //同意的情况
        //vacate.status=7
        List<VacateInfo> vacateInfos = adminService.adoptVacates(status, ids, admin.getName());
        //发送邮件提示
        for (VacateInfo vacateInfo : vacateInfos) {

            asyncTask.EmailToStudentIsPass(vacateInfo.getEmail(),
                    vacateInfo.getName(), admin.getName());
        }
        return "200";
    }

    @RequestMapping("/addStudents")
    @ResponseBody
    public String addStudents(MultipartFile file ,String gname, String grade,Teacher teacher) throws IOException {
        try {
            //如果格式不正确, 提醒admin
            if (!file.getContentType().equals("application/vnd.ms-excel")){
                return "上传的文件格式不正确,请重新上传";
            }

            //方法封装在了ExcelUtil中
            List<Student> studentList = ExcelUtil.getStudentList(file);
            //todo  写入数据库
            adminService.insertStudents(studentList,gname,grade,teacher);
        }catch (Exception e){
            return "导入异常,请检查是否出现重复学号";
        }

        return "导入成功";
    }

    @GetMapping("checkGrade")
    @ResponseBody
    public String checkGrade(String grade,String gname){
        Grade grade1 = adminService.getGrade(grade,gname);
        if (grade1 == null){
            return "404";
        }
        return "200";
    }


}