package com.vote.voting.dao;

/**
 * @author slyslyme
 * @version 1.0
 * @date 2019/11/17 9:26
 * @package com.vote.voting.dao
 **/

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 统计结果Dao
 */
@Mapper
public interface PollDao {
    /**
     * 统计系统中的用户数
     * @return
     */
    @Select("select distinct count(*) from vote_account")
    public Integer pollAccounts();

    /**
     * 统计系统中投票活动的总数
     * @return
     */
    @Select("select distinct count(*) from vote_detail")
    public Integer pollVoteActivities();

    /**
     * 最近7天的投票人数
     */
    @Select("SELECT count(*) FROM vote_case where DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(voteTime)")
    public Integer poolVoteWeek();

    /**
     * 最近7天的日期
     * @return
     */
    @Select("select a.click_date from(\n" +
            "select CURDATE() as click_date\n" +
            "union all\n" +
            "select DATE_SUB(CURDATE(), INTERVAL 1 DAY) as click_date\n" +
            "union all\n" +
            "select DATE_SUB(CURDATE(), INTERVAL 2 DAY) as click_date\n" +
            "union all\n" +
            "select DATE_SUB(CURDATE(), INTERVAL 3 DAY) as click_date\n" +
            "union all\n" +
            "select DATE_SUB(CURDATE(), INTERVAL 4 DAY) as click_date\n" +
            "union all\n" +
            "select DATE_SUB(CURDATE(), INTERVAL 5 DAY) as click_date\n" +
            "union all\n" +
            "select DATE_SUB(CURDATE(), INTERVAL 6 DAY) as click_date)\n" +
            "a left join (select date(voteTime)as datetime, count(*) as count\n" +
            "from vote_case\n" +
            "group by date(voteTime))\n" +
            "b on a.click_date=b.datetime\n" +
            "ORDER BY click_date ASC\n")
    public List<String> findWeekDate();

    /**
     * 最近7天的每天投票数
     */
    @Select("select ifnull(b.count,0) from(\n" +
            "select CURDATE() as click_date\n" +
            "union all\n" +
            "select DATE_SUB(CURDATE(), INTERVAL 1 DAY) as click_date\n" +
            "union all\n" +
            "select DATE_SUB(CURDATE(), INTERVAL 2 DAY) as click_date\n" +
            "union all\n" +
            "select DATE_SUB(CURDATE(), INTERVAL 3 DAY) as click_date\n" +
            "union all\n" +
            "select DATE_SUB(CURDATE(), INTERVAL 4 DAY) as click_date\n" +
            "union all\n" +
            "select DATE_SUB(CURDATE(), INTERVAL 5 DAY) as click_date\n" +
            "union all\n" +
            "select DATE_SUB(CURDATE(), INTERVAL 6 DAY) as click_date)\n" +
            "a left join (select date(voteTime)as datetime, count(*) as count\n" +
            "from vote_case\n" +
            "group by date(voteTime))\n" +
            "b on a.click_date=b.datetime\n" +
            "ORDER BY click_date ASC\n")
    public List<Integer> findWeekDateNum();
}
