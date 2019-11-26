package com.vote.voting.vo;

import com.vote.voting.entity.VoteOptions;

import java.sql.Date;
import java.util.List;

/**
 * @author slyslyme
 * @version 1.0
 * @date 2019/11/21 16:37
 * @package com.vote.voting.vo
 **/

public class VoteInsert {
    int id;
    private String title;
    private String detail;
    private int type;
    private String start;
    private String end;
    private List<String> voteOptions; // 全部的选项

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public List<String> getVoteOptions() {
        return voteOptions;
    }

    public void setVoteOptions(List<String> voteOptions) {
        this.voteOptions = voteOptions;
    }

    @Override
    public String toString() {
        return "VoteInsert{" +
                ", title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                ", type=" + type +
                ", start=" + start +
                ", end=" + end +
                ", voteOptions=" + voteOptions +
                '}';
    }
}
