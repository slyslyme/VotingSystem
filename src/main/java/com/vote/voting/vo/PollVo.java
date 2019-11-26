package com.vote.voting.vo;

import java.util.List;

/**
 * @author slyslyme
 * @version 1.0
 * @date 2019/11/23 16:43
 * @package com.vote.voting.vo
 **/

/**
 * 首页的数据
 */
public class PollVo {
    private Integer peopleNum; // 当前系统用户数量
    private Integer voteNum;  // 当前系统投票活动数量
    private Integer weekVoteNum; // 最近一周的投票数量
    private List<String> weekDate; // 最近一周的日期
    private List<Integer> weekDateNum;// 最近一周每天的投票数量

    public Integer getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    public Integer getVoteNum() {
        return voteNum;
    }

    public void setVoteNum(Integer voteNum) {
        this.voteNum = voteNum;
    }

    public Integer getWeekVoteNum() {
        return weekVoteNum;
    }

    public void setWeekVoteNum(Integer weekVoteNum) {
        this.weekVoteNum = weekVoteNum;
    }

    public List<String> getWeekDate() {
        return weekDate;
    }

    public void setWeekDate(List<String> weekDate) {
        this.weekDate = weekDate;
    }

    public List<Integer> getWeekDateNum() {
        return weekDateNum;
    }

    public void setWeekDateNum(List<Integer> weekDateNum) {
        this.weekDateNum = weekDateNum;
    }
}
