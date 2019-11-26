package com.vote.voting.entity;

import java.util.Date;

/**
 * @author slyslyme
 * @version 1.0
 * @date 2019/11/17 9:02
 * @package com.vote.voting.entity
 **/
public class VoteCase {
    private int id;
    private int accountId;
    private int detailId;
    private int voteIndex;
    private String voteTime; // 自动时间戳

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public int getVoteIndex() {
        return voteIndex;
    }

    public void setVoteIndex(int voteIndex) {
        this.voteIndex = voteIndex;
    }

    public String getVoteTime() {
        return voteTime;
    }

    public void setVoteTime(String voteTime) {
        this.voteTime = voteTime;
    }

    @Override
    public String toString() {
        return "VoteCase{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", detailId=" + detailId +
                ", voteIndex=" + voteIndex +
                ", voteTime='" + voteTime + '\'' +
                '}';
    }
}
