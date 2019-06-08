package com.tsu.xjq.utils;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @ClassName AsyncTaskUtil
 * @Author Elv1s
 * @Date 2019/3/10 8:26
 * @Description:
 */

@Component
public class AsyncTaskUtil {


    /**
     * 学生向老师请假的异步调用工作
     *
     * @param teacherEmail
     * @param studentName
     * @param teacherName
     * @param studentId
     * @param reason
     * @param days
     * @param range
     * @param vid
     */
    @Async
    public void EmailToTeacher(String teacherEmail, String studentName, String teacherName,
                               int studentId, String reason, String days, String range, int vid) throws Exception {

        //发送邮件
        new MailUtil().sendMail(teacherEmail,
                "来自" + studentName + "的请假",
                teacherName,
                teacherName + "老师您好，我是:" +
                        studentName + "(学号：" + studentId + ")，" +
                        "因为我：" + reason + "， 所以我想请假" + days + "天(" + range + ")，希望您能批准。-----------"
                        + "<a href = 'http://www.lovelybj.xyz:8081/adopt2/" + vid + "/1'>点击此处同意</a>--------" +
                        "<a href = 'http://www.lovelybj.xyz:8081/adopt2/" + vid + "/2/' >点击此处不同意</a>"
        );
    }

    /**
     * 提醒学生请假通过
     *
     * @param studentEmail
     * @param studentName
     * @param teacherName
     * @throws Exception
     */
    @Async
    public void EmailToStudentIsPass(String studentEmail, String studentName, String teacherName) {
        new MailUtil().sendMail(studentEmail, studentName + "同学,你的请假批准了",
                studentName,
                studentName + "同学你好,你的请假" +
                        teacherName +
                        "老师已经准许,校外注意安全!");
    }

    /**
     * 提醒学生请假未通过
     *
     * @param studentEmail
     * @param studentName
     * @param teacherName
     * @param teacherTele
     */
    @Async
    public void EmailToStudentIsNotPass(String studentEmail, String studentName, String teacherName, String teacherTele) {
        new MailUtil().sendMail(studentEmail, studentName + "同学,你的请假没有通过",
                studentName,
                studentName + "同学你好,很遗憾你的请假没有被老师通过," +
                        "请你主动联系老师说明情况," +
                        teacherName +
                        "老师联系方式:" +
                        teacherTele);

    }

}




