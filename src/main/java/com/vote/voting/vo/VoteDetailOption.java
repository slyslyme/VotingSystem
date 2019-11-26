package com.vote.voting.vo;

import com.vote.voting.entity.VoteDetail;
import com.vote.voting.entity.VoteOptions;

import java.util.List;

/**
 * @author slyslyme
 * @version 1.0
 * @date 2019/11/18 23:51
 * @package com.vote.voting.vo
 **/

/**
 * 投票描述+选项
 */
public class VoteDetailOption {
    private int voteStatus = 0;  // 是否已经投票，0代表没有投票，1代表已经投票
    private VoteDetail voteDetail;   // 投票标题描述等内容
    private List<VoteOptions> voteOptions; // 全部的选项
    private List<VoteOptions> Choiced; // 已经选择的选项列表
    private Integer optionsNum;

    public List<VoteOptions> getChoiced() {
        return Choiced;
    }

    public void setChoiced(List<VoteOptions> choiced) {
        Choiced = choiced;
    }

    public int getVoteStatus() {
        return voteStatus;
    }

    public void setVoteStatus(int voteStatus) {
        this.voteStatus = voteStatus;
    }

    public VoteDetail getVoteDetail() {
        return voteDetail;
    }

    public void setVoteDetail(VoteDetail voteDetail) {
        this.voteDetail = voteDetail;
    }

    public List<VoteOptions> getVoteOptions() {
        return voteOptions;
    }

    public void setVoteOptions(List<VoteOptions> voteOptions) {
        this.voteOptions = voteOptions;
    }

    public Integer getOptionsNum() {
        return optionsNum;
    }

    public void setOptionsNum(Integer optionsNum) {
        this.optionsNum = optionsNum;
    }
}
