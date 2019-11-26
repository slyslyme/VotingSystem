package com.vote.voting.vo;

import java.util.List;

/**
 * @author slyslyme
 * @version 1.0
 * @date 2019/11/24 13:26
 * @package com.vote.voting.vo
 **/
public class AccountInsert {
    int id;
    private List<Integer> voteOptions; // 全部的选项

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getVoteOptions() {
        return voteOptions;
    }

    public void setVoteOptions(List<Integer> voteOptions) {
        this.voteOptions = voteOptions;
    }
}
