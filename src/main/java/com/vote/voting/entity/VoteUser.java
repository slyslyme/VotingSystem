package com.vote.voting.entity;

/**
 * @author slyslyme
 * @version 1.0
 * @date 2019/11/2 21:09
 * @package com.vote.voting.entity
 **/
public class VoteUser {
    private Integer id;
    private String username;
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
}
