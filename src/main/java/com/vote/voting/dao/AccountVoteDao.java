package com.vote.voting.dao;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.vote.voting.entity.VoteAccount;
import com.vote.voting.entity.VoteOptions;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

/**
 * @author slyslyme
 * @version 1.0
 * @date 2019/11/17 21:44
 * @package com.vote.voting.dao
 **/

/**
 * 用户投票
 */
@Mapper
public interface AccountVoteDao {

    /**
     * 根据选项内容和投票活动的ID查询这是第几个选项,得到的是index
     * @return
     */
    @Select("select * from vote_options where voteId=#{vote_id} and option=#{option}")
    public VoteOptions findOptions(int vote_id, String option);

    /**
     * 用户投票
     * @param account_id
     * @param detail_id
     * @param vote_index
     * @return
     */
    @Insert("insert into vote_case(accountId, detailId, voteIndex,voteTime) values(#{account_id},#{detail_id},#{vote_index},now())")
    public Boolean accountVote(int detail_id, int account_id, int vote_index);

    /**
     * 更新当前投票活动的投票人数
     * @param vote_id
     * @return
     */
    @Update("update vote_detail set counts=counts+1 where id=#{vote_id}")
    public Boolean voteNums(int vote_id);

    @Select("select * from vote_account where id=#{id}")
    public VoteAccount findPass(int id);

    @Select("select * from vote_account where username=#{username}")
    public VoteAccount findPass1(String username);

    /**
     * 修改密码
     * @param password
     * @return
     */
    @Update("update vote_account set password=#{password} where id=#{id}")
    public Boolean updatePass(String password, int id);
}
