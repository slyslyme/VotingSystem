package com.vote.voting.service;

import com.vote.voting.dao.DetailDao;
import com.vote.voting.entity.VoteDetail;
import com.vote.voting.entity.VoteOptions;
import com.vote.voting.exception.GlobalException;
import com.vote.voting.result.CodeMsg;
import com.vote.voting.vo.VoteDetailOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.List;

/**
 * @author slyslyme
 * @version 1.0
 * @date 2019/11/17 9:31
 * @package com.vote.voting.service
 **/

/**
 * 投票活动的内容显示
 */
@Service
public class DetailService {

    @Autowired
    DetailDao detailDao;

    /**
     * 投票列表
     * @return
     */
    public List<VoteDetail> findVoteList(){
        List<VoteDetail> voteList = detailDao.findAll();
        if(voteList == null)
            throw new GlobalException(CodeMsg.NOTHING);

        return voteList;
    }

    /**
     * 前台用户查看投票详情
     * @param vote_id
     * @param account_id
     * @return
     */
    public VoteDetailOption findDetailOption(int vote_id, int account_id){
        List<VoteOptions> voteOptions = detailDao.findVoteOptions(vote_id);
        VoteDetail voteDetail = detailDao.findVoteDetail(vote_id);
        if(voteDetail == null || voteOptions == null)
            throw new GlobalException(CodeMsg.NOTHING);  // 没有找到投票内容
        List<VoteOptions> options = detailDao.choicedOptions(vote_id, account_id);
        VoteDetailOption voteDetailOption = new VoteDetailOption();
        // 已选项的个数为0，说明没有投过票
        if(options.size() == 0)
            voteDetailOption.setVoteStatus(0);
        else {
            voteDetailOption.setVoteStatus(1);
            voteDetailOption.setChoiced(options);
        }
        voteDetailOption.setVoteDetail(voteDetail);
        voteDetailOption.setVoteOptions(voteOptions);
        return voteDetailOption;
    }

    /**
     * 管理员查看投票详情
     * @param vote_id
     * @return
     */
    public VoteDetailOption findDetail(int vote_id){
        List<VoteOptions> voteOptions = detailDao.findVoteOptions(vote_id);
        VoteDetail voteDetail = detailDao.findVoteDetail(vote_id);
        if(voteDetail == null || voteOptions == null)
            throw new GlobalException(CodeMsg.NOTHING);  // 没有找到投票内容
        VoteDetailOption voteDetailOption = new VoteDetailOption();
        // 已选项的个数为0，说明没有投过票
        voteDetailOption.setVoteStatus(0);
        voteDetailOption.setVoteDetail(voteDetail);
        voteDetailOption.setVoteOptions(voteOptions);
        return voteDetailOption;
    }

    public VoteDetail findDetailOptionTest(int vote_id){
        VoteDetail voteDetail = detailDao.findVoteDetail(vote_id);
        if(voteDetail == null)
            throw new GlobalException(CodeMsg.NOTHING);  // 没有找到投票内容
        System.out.println(voteDetail.getStart());
        return voteDetail;
    }


}
