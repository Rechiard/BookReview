package com.tjnu.frame.util;

import com.tjnu.frame.dto.Auth;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMailUtil {

    private Properties props; //系统属性
    private Session mailSession; //邮件会话对象
    private MimeMessage mimeMsg; //MIME邮件对象

    public SendMailUtil() {
        Auth au = new Auth("w838673159@163.com", "XFSGXTJXGAKQHEZL");
        //设置系统属性
        props=java.lang.System.getProperties(); //获得系统属性对象
        props.setProperty("mail.smtp.auth", "true"); //开启认证

        props.put("mail.smtp.host", "smtp.163.com"); //设置SMTP主机
        props.put("mail.smtp.port", "465"); //设置服务端口号
        props.put("mail.smtp.auth", "true"); //同时通过验证

        //	props.setProperty("mail.smtp.timeout", "1000"); //设置连接诶超时
        props.setProperty("mail.smtp.socketFactory.port", "465"); //设置ssl端口
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        //获得邮件会话对象
        mailSession = Session.getInstance(props, au);
    }
    //MailCopyTo抄送人  MailBCopyTo安送人  MailSubject邮件主题  MailBody邮件内容
    public boolean sendingMimeMail(String MailTo,String MailBody) {
        try {
            //创建MIME邮件对象
            mimeMsg=new MimeMessage(mailSession);

            //设置发信人
            mimeMsg.setFrom(new InternetAddress("w838673159@163.com"));
            //设置收信人
            if(MailTo!=null){
                mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(MailTo));
            }

            //设置邮件主题
            mimeMsg.setSubject("万卷书堂给您发送了一个验证码,请勿轻易给别人","utf-8");
            //设置邮件内容，将邮件body部分转化为HTML格式
            String message = "这是万卷书堂给您发送的验证码，请勿轻易发给他人，并且保护自己的隐私安全：\n";
            mimeMsg.setContent(message+MailBody,"text/html;charset=utf-8");
            //发送邮件
            Transport.send(mimeMsg);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
