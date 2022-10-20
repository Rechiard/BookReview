package com.tjnu.frame.dto;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class Auth extends Authenticator{
    private String username="";
    private String password ="";

    public Auth(String username,String password)
    {

        this.username = username;
        this.password = password;

    }

    //在哪里调用的这个方法？ 这个方法是必须有的，用于验证身份
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username,password);
    }
}
