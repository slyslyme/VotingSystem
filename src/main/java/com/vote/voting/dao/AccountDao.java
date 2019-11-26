package com.vote.voting.dao;

/**
 * @author slyslyme
 * @version 1.0
 * @date 2019/11/17 9:23
 * @package com.vote.voting.dao
 **/

import com.vote.voting.entity.VoteAccount;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 用户Dao
 */
@Mapper
public interface AccountDao {
    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    @Select("select * from vote_account where username=#{username}")
    public VoteAccount findAccount(String username);

    /**
     * 根据手机号查询
     * @param phone
     * @return
     */
    @Select("select * from vote_account where phone=#{phone}")
    public VoteAccount findAccountByPhone(String phone);

    /**
     * 根据用户名和密码联合查询
     * @param username
     * @param password
     * @return
     */
    @Select("select * from vote_account where username=#{username} and password=#{password}")
    public VoteAccount accountLogin(String username, String password);

    /**
     * 注册插入用户
     * @param account
     * @return
     */
    @Insert("insert into vote_account(username,password,phone,gender,age) values(#{username},#{password},#{phone},#{gender},#{age})")
    public Boolean insertAccount(VoteAccount account);

    /**
     * 更新用户状态
     * @param status
     * @param account_id
     * @return
     */
    @Update("update vote_account set status=#{status} where id=#{account_id}")
    public Boolean frozenAccount(int status, int account_id);
}
