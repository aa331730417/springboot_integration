package com.luqi.demo.utils;

import com.luqi.demo.model.User;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @Author: I_UO_I
 * @Date: 2019/1/21 10:53
 * @Version 1.0
 */
public class PasswordUtil {

    //散列算法:这里使用MD5算法;
    public static String ALGORITHMNAME = "md5";
    //散列的次数，比如散列两次，相当于 md5(md5(""));
    public static int HASHITERATIONS = 2;

    public static void encryptPassword(User user) {
        ByteSource salt = ByteSource.Util.bytes(user.getUserName());
        String newPassword = new SimpleHash(ALGORITHMNAME, user.getPassword(), salt, HASHITERATIONS).toHex();
        user.setPassword(newPassword);
    }
    public static void main(String[] args) {
        User user = new User();
        user.setUserName("zs");
        user.setPassword("123");
        PasswordUtil.encryptPassword(user);
        System.out.println(user);
    }

}
