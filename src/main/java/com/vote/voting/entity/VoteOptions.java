package com.vote.voting.entity;

/**
 * @author slyslyme
 * @version 1.0
 * @date 2019/11/2 21:57
 * @package com.vote.voting.entity
 **/
public class VoteOptions {
    private Integer id;
    private Integer voteId;
    private Integer optionIndex;
    private String optiondesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVoteId() {
        return voteId;
    }

    public void setVoteId(Integer voteId) {
        this.voteId = voteId;
    }

    public Integer getOptionIndex() {
        return optionIndex;
    }

    public void setOptionIndex(Integer optionIndex) {
        this.optionIndex = optionIndex;
    }

    public String getOptiondesc() {
        return optiondesc;
    }

    public void setOptiondesc(String optiondesc) {
        this.optiondesc = optiondesc;
    }

    @Override
    public String toString() {
        return "VoteOptions{" +
                "id=" + id +
                ", voteId=" + voteId +
                ", optionIndex=" + optionIndex +
                ", option='" + optiondesc + '\'' +
                '}';
    }
}
