package com.nowcoder.community.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class CommunityUtil {

    //生成一个随机字符串、激活码
    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    //MD5加密
    //hello + 3e4a8 加上随机字符串，防止用户输入的密码太简单
    public static String md5(String key){
        if(StringUtils.isBlank(key)){  //“” “ ” null直接返回null
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }
}
