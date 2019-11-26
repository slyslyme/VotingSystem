package com.vote.voting.service;

import com.vote.voting.dao.PollDao;
import com.vote.voting.vo.PollVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author slyslyme
 * @version 1.0
 * @date 2019/11/17 9:31
 * @package com.vote.voting.service
 **/

/*
  统计service
 */
@Service
public class PollService {
    @Autowired
    PollDao pollDao;

    /**
     * 统计系统中的一些数据
     * @return
     */
    public PollVo findIndexData(){
        int accounts = pollDao.pollAccounts();
        int votes = pollDao.pollVoteActivities();
        int weeks = pollDao.poolVoteWeek();
        List<String> weekDate = pollDao.findWeekDate();
        List<Integer> weekDateNum = pollDao.findWeekDateNum();
        PollVo vo = new PollVo();
        vo.setPeopleNum(accounts);
        vo.setVoteNum(votes);
        vo.setWeekVoteNum(weeks);
        vo.setWeekDate(weekDate);
        vo.setWeekDateNum(weekDateNum);
        return vo;
    }

}
