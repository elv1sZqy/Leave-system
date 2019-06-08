package com.tsu.xjq.utils;

import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
/**
*@ClassName MailUtil
* @Author Elv1s
* @Date 2019/3/5 16:15
* @Description:为了快速提醒老师以及学生请假的进程
*/
public class MailUtil {
    /**
     *
     * @param receive 收信人邮箱地址
     * @param subject  邮件标题
     * @param receiver  对收信人称呼
     * @param content   邮件内容
     */
    public void sendMail(String receive,String subject,String receiver, String content){
        Properties properties = new Properties() ;
        //协议smtp
        properties.setProperty("mail.transprot.protocol", "smtp");
        //协议地址
        properties.setProperty("mail.smtp.host","smtp.qq.com");
        //协议端口
        properties.setProperty("mail.smtp.port", "465");
        //需要授权
        properties.setProperty("mail.smpt.auth", "true");
        //QQ:SSL安全认证
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
        properties.setProperty("mail.smtp.socketFactory.port", "465");
        //得到session 会话
        Session session = Session.getInstance(properties);
        //开启debug日志
        session.setDebug(true);

        //创建邮件
        try {
            //发送人邮箱,收信人邮箱,收信人的称呼,内容,标题
            MimeMessage message = creatMimeMessage(session,
                    "1120280983@qq.com", receive,receiver,content,subject);
            //rcjuqnheuxqaighf   授权码
            Transport transport = session.getTransport("smtp");
            //连接
            transport.connect("1120280983@qq.com","phiyvuebtvnuheij");
            //发送
            transport.sendMessage(message, message.getAllRecipients());
            //关闭
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    /**
     *
     * @param session
     * @param send  发送人邮箱
     * @param receive   收信人邮箱
     * @param receiver  对收信人的称呼
     * @param content   邮件的内容
     * @param subject   邮件的标题
     * @return
     * @throws Exception
     */
    public MimeMessage creatMimeMessage(Session session,String send,String receive,String receiver,
                                        String content,String subject) throws Exception {
        MimeMessage message = new MimeMessage(session);
        Address address = new InternetAddress(send,"信科请假系统","utf-8");

        //发件人
        message.setFrom(address);
        //邮件标题
        message.setSubject(subject,"utf-8");
        //邮件内容
        message.setContent(content,"text/html;charset=utf-8");
        //邮件类型以及收件人
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receive,receiver,"utf-8") );
        //发送日期
        message.setSentDate(new Date());
        message.saveChanges();

        return message;
    }

}
