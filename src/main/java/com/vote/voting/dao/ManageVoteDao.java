package com.vote.voting.dao;

import com.vote.voting.entity.VoteAccount;
import com.vote.voting.entity.VoteCase;
import com.vote.voting.entity.VoteDetail;
import com.vote.voting.entity.VoteOptions;
import com.vote.voting.vo.EchartsHistogram;
import com.vote.voting.vo.VoteInsert;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * @author slyslyme
 * @version 1.0
 * @date 2019/11/21 10:55
 * @package com.vote.voting.dao
 **/
@Mapper
public interface ManageVoteDao {

    /**
     * 删除投票活动
     * @param id
     * @return
     */
    @Delete("delete from vote_detail where id=#{id}")
    public Boolean deleteVote(int id);

    /**
     * 删除投票活动的选项
     * @param id
     * @return
     */
    @Delete("delete from vote_options where voteId=#{id}")
    public Boolean deleteVoteOptions(int id);

    /**
     * 删除投票活动的详情
     * @param id
     * @return
     */
    @Delete("delete from vote_case where detailId=#{id}")
    public Boolean deleteVoteCase(int id);

    /**
     * 添加投票活动
     * @param voteDetail
     * @return
     */
    @Insert("insert into vote_detail(title,detail,type,start,end,counts) values(#{title},#{detail},#{type},#{start},#{end},0)")
    public void insertVote(VoteDetail voteDetail);

    @Select("select id from vote_detail where title=#{title}")
    public VoteDetail findByTitle(String title);

    @Select("select * from vote_case where option=#{option} and voteId=#{id}")
    public Boolean findByOption(String option, int id);

    /**
     * 插入单个选项，在代码中插入多个选项需要循环
     * @param voteId
     * @param optionIndex
     * @param optiondesc
     * @return
     */
    @Insert("insert into vote_options(voteId,optionIndex,optiondesc) values(#{voteId},#{optionIndex},#{optiondesc})")
    public Boolean insertVoteOption(@Param("voteId") int voteId, @Param("optionIndex") int optionIndex, @Param("optiondesc") String optiondesc);

    /**
     * 更新投票活动详情内容
     * @param voteInsert
     * @return
     */
    @Update("update vote_detail set title=#{title},detail=#{detail},type=#{type},start=#{start},end=#{end} where id=#{id}")
    public Boolean updateVoteDetail(VoteInsert voteInsert);

    /**
     * 根据id查找对应的投票活动
     * @param id
     * @return
     */
    @Select("select * from vote_detail where id=#{id}")
    public VoteDetail findVoteDetailById(int id);

    /**
     * 删除投票中的选项
     * @param id
     * @return
     */
    @Delete("delete from vote_options where voteId=#{id}")
    public Boolean deleteOption(int id);
    @Select("select * from vote_options where voteId=#{id}")
    public List<VoteOptions> findOption(int id);
    @Select("select * from vote_case where detailId=#{id}")
    public List<VoteCase> findCase(int id);

    /**
     * 根据标题进行模糊查询
     * @param key
     * @return
     */
    @Select("select * from vote_detail where title like concat('%',#{key},'%')")
    public List<VoteDetail> findByTitleDim(String key);

    /**
     * 根据投票的类型进行模糊查询
     * @param key
     * @return
     */
    @Select("select * from vote_detail where type=#{type}")
    public List<VoteDetail> findByTypeDim(int key);

    /**
     * 柱状图的统计--数量
     * @param id
     * @return
     */
    @Select("select num from (select o.optionIndex,o.optiondesc, IFNULL(T.num,0) as num from vote_options o left join \n" +
            "(select optionIndex, count(optionIndex) as num from vote_options a left join vote_case b\n" +
            "on\n" +
            "a.optionIndex=b.voteIndex\n" +
            "where detailId=#{id} and voteId=#{id}\n" +
            "GROUP BY optionIndex) T " +
            "on o.optionIndex=T.optionIndex\n" +
            "where voteId=#{id}\n" +
            "ORDER BY optionIndex ASC)as S")
    public List<Integer> findHistogramNum(int id);

    /**
     * 柱状图的统计--选项
     * @param id
     * @return
     */
    @Select("select s.optiondesc from (select o.optionIndex,o.optiondesc, IFNULL(T.num,0) as num from vote_options o left join \n" +
            "(select optionIndex, count(optionIndex) as num from vote_options a left join vote_case b\n" +
            "on\n" +
            "a.optionIndex=b.voteIndex\n" +
            "where detailId=#{id} and voteId=#{id}\n" +
            "GROUP BY optionIndex) T " +
            "on o.optionIndex=T.optionIndex\n" +
            "where voteId=#{id}\n" +
            "ORDER BY optionIndex ASC)as S")
    public List<String> findHistogramOptions(int id);
}
