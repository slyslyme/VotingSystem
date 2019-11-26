package com.vote.voting.dao;

/**
 * @author slyslyme
 * @version 1.0
 * @date 2019/11/17 9:23
 * @package com.vote.voting.dao
 **/

import com.vote.voting.entity.VoteUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 管理员Dao
 */
@Mapper
public interface AdminDao {
    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    @Select("select * from vote_user where username=#{username}")
    public VoteUser findUserByUsername(String username);

    /**
     * 用户与密码联合查询
     * @param username
     * @param password
     * @return
     */
    @Select("select * from vote_user where username=#{username} and password=#{password}")
    public VoteUser findUser(String username, String password);

    @Select("select * from vote_user where id=#{id}")
    public VoteUser findPass(int id);

    @Select("select * from vote_user where username=#{username}")
    public VoteUser findPass1(String username);

    /**
     * 修改密码
     * @param password
     * @return
     */
    @Update("update vote_user set password=#{password} where id=#{id}")
    public Boolean updatePass(String password, int id);
}
