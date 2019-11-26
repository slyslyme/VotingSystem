package com.vote.voting.dao;

/**
 * @author slyslyme
 * @version 1.0
 * @date 2019/11/17 9:27
 * @package com.vote.voting.dao
 **/

import com.vote.voting.entity.VoteDetail;
import com.vote.voting.entity.VoteOptions;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * 投票详情Dao
 */
@Mapper
public interface DetailDao {
    /**
     * 展示投票列表
     */
    @Select("select * from vote_detail")
    public List<VoteDetail> findAll();

    /**
     *
     * (1) 投票内容
     */
    @Select("select * from vote_detail where id=#{id}")
    public VoteDetail findVoteDetail(int id);

    /**
     * (2) 投票选项
     */
    @Select("select * from vote_options where voteId=#{vote_id} order by optionIndex ASC")
    public List<VoteOptions> findVoteOptions(int vote_id);


    /**
     * 查询当前登录的用户所投的选项
     */
    @Select("select * from vote_options where voteId=#{voteId} and optionIndex in(\n" +
            "select voteIndex from vote_case where accountId=#{accountId} and detailId=#{voteId}\n" +
            ")")
    public List<VoteOptions> choicedOptions(int voteId, int accountId);

    @Select("select start from vote_detail where id=#{id}")
    public String findStartTime(int id);

}