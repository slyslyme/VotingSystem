package com.vote.voting.dao;

import com.vote.voting.entity.VoteAccount;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author slyslyme
 * @version 1.0
 * @date 2019/11/20 1:04
 * @package com.vote.voting.dao
 **/
@Mapper
public interface ManageAccountDao {
    /**
     * 展示系统中全部的用户信息
     * @return
     */
    @Select("select * from vote_account")
    public List<VoteAccount> findAllAccounts();


    /**
     * 根据用户名、手机号、性别、年龄
     * @param username
     * @return
     */
    @Select("select * from vote_account where 1=1 and username like concat('%',#{key},'%')")
    public List<VoteAccount> findByUsername(String username);

    @Select("select * from vote_account where 1=1 and phone like concat('%',#{key},'%')")
    public List<VoteAccount> findByPhoneNumber(String phone);

    @Select("select * from vote_account where gender=#{gender}")
    public List<VoteAccount> findByGender(int gender);

    @Select("select * from vote_account where age=#{age}")
    public List<VoteAccount> findByAge(int age);

    /**
     * 模糊查询
     * @param key
     * @return
     */
    /*@Select("<script> select * from vote_account where 1=1" +
            "<if test=\"phone!=null\">and phone=#{key}</if>" +
            "<if test=\"gander!=null\">and gender=#{key}</if>" +
            "<if test=\"age!=null\">and age=#{key}</if>" +
            "<if test=\"username!=null\">or username like concat('%',#{key},'%')</if>" +
            "</script>")
    public List<VoteAccount> findSomeAccounts(String key);*/

    /**
     * 删除指定用户名的用户
     * @param username
     * @return
     */
    @Delete("delete from vote_account where username=#{username}")
    public Boolean deleteAccount(String username);

    /**
     * 插入用户，默认用户状态时非冻结，不要加入用户状态
     * @param username
     * @param password
     * @param phone
     * @param gender
     * @param age
     * @return
     */
    @Insert("insert into vote_account(username,password,phone,gender,age) values(#{username},#{password}," +
            "#{phone},#{gender},#{age})")
    public Boolean addAccount(String username,String password,String phone,int gender,int age);

    /**
     * 更新用户
     * @param username
     * @param password
     * @param phone
     * @param gender
     * @param age
     * @return
     */
    @Update("update vote_account set username=#{username},password=#{password},phone=#{phone}," +
            "gender=#{gender},age=#{age} where id=#{id}")
    public Boolean updateAccount(int id, String username,String password,String phone,int gender,int age);
}
