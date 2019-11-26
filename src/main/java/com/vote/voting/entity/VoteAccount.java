package com.vote.voting.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * @author slyslyme
 * @version 1.0
 * @date 2019/11/2 21:21
 * @package com.vote.voting.entity
 **/
public class VoteAccount {
    private int id;   // 用户ID
    private String username;  // 用户名
    private String password; // 用户密码
    private String phone;  // 用户电话
    private int gender;    // 性别 0男 1女
    private int age;    // 年龄

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
