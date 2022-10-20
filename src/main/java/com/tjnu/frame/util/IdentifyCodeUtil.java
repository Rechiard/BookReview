package com.tjnu.frame.util;

import java.util.Random;

/**
 * 生成随机验证码
 */
public class IdentifyCodeUtil {

    private static final String RANDOM_NUMS = "0123456789";
    private Random random = new Random();

    private int strNum = 6;

    /**
     * 生成验证码
     * @return
     */
    public String createIdentifyCode(){
        String rand = "";
        for(int i = 1;i <= strNum;i++){
            rand += String.valueOf(getRandomNum(random.nextInt(RANDOM_NUMS.length())));
        }
        return rand;
    }

    /**
     * 获得随机数字
     * @param num
     * @return
     */
    private String getRandomNum(int num){
        return String.valueOf(RANDOM_NUMS.charAt(num));
    }

}
