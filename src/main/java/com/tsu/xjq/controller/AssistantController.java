package com.tsu.xjq.controller;

import com.tsu.xjq.pojo.*;
import com.tsu.xjq.service.AssistantService;
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
 * @ClassName AssistantController
 * @Author Elv1s
 * @Date 2019/3/6 21:30
 * @Description:
 */
@Controller
public class AssistantController {

    @Autowired
    AssistantService assistantService;
    @Autowired
    AsyncTaskUtil asyncTask;


    /**
     * 登录
     * @param id
     * @param assistant1
     * @param md
     * @param session
     * @return
     */
    @RequestMapping("/login")
    public String toAssistant(String id , Assistant assistant1, Model md, HttpSession session){
        assistant1.setAid(id);
        Assistant assistant = assistantService.getAssistant(assistant1);

        if (assistant != null){
            String grade = assistant.getGrade();
            List<Grade> grades = assistantService.getGrades(grade);

            md.addAttribute("grades", grades);
            session.setAttribute("assistant", assistant);
            return  "admin/assistant";
        }

        md.addAttribute("msg", "账号或者密码错误");
        return "admin/login";
    }

    /**
     * 获得className班级的所有请假记录
     * @param page
     * @param rows
     * @param className
     * @param session
     * @return
     */
    @RequestMapping("/getVacates")
    @ResponseBody
    public EasyUIDataGridResult getVacates(int page,int rows,String className,HttpSession session){
        Assistant assistant = (Assistant) session.getAttribute("assistant");
        String grade = assistant.getGrade();
        EasyUIDataGridResult vacates = assistantService.getVacates(page, rows, className, grade);
        return vacates;
    }

    /**
     * 查询该年级待处理的请假请求
     * @param page
     * @param rows
     * @param session
     * @return
     */
    @RequestMapping("/pending")
    @ResponseBody
    public EasyUIDataGridResult getPending(int page,int rows,HttpSession session){
        Assistant assistant = (Assistant) session.getAttribute("assistant");

        String grade = assistant.getGrade();
        EasyUIDataGridResult pendingVacates = assistantService.getPendingVacates(page, rows, grade);
        return pendingVacates;

    }

    /**
     * 批量处理请假请求
     * @param status
     * @param ids
     * @param session
     * @return
     */
    @RequestMapping("/assistant/adopt/{status}")
    @ResponseBody
    public String adoptVacates(@PathVariable String status,String[] ids,HttpSession session){
        Assistant assistant = (Assistant) session.getAttribute("assistant");
        String name = assistant.getName();
        List<VacateInfo> vacateInfos = assistantService.adoptVacates(status, ids, name);
        //未通过的情况
        if (status.equals("4")){
            //发送邮件提示未通过
            for (VacateInfo vacateInfo : vacateInfos ) {
                asyncTask.EmailToStudentIsNotPass(vacateInfo.getEmail(), vacateInfo.getName(),
                        assistant.getName(), assistant.getTelephone());
            }
            //操作成功
            return "200";
        }
        else {
            /*通过的情况:
                    大于三天小于等于七天:status = 5 并发送邮件
                    大于七天: status = 6  不发送邮件
        * */
            //发送邮件提示通过
            for (VacateInfo vacateInfo : vacateInfos ) {
                if (vacateInfo.getDays() <= 7){
                    asyncTask.EmailToStudentIsPass(vacateInfo.getEmail(), vacateInfo.getName(),
                            assistant.getName());
                    //不用通知admin
                    assistantService.adoptVacate("5", vacateInfo.getId());
                }
                else {
                    //通知admin处理
                    assistantService.adoptVacate("6", vacateInfo.getId());
                }
            }
            //操作成功
            return "200";
        }
    }
}
