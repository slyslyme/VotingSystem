package com.vote.voting.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.util.Date;

/**
 * @author slyslyme
 * @version 1.0
 * @date 2019/11/17 9:02
 * @package com.vote.voting.entity
 **/
public class VoteDetail{
    private int id;
    private String title;
    private String detail;
    @Value("#{0}")
    private int counts;
    private int type;
    private String start;
    private String end;

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

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
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


    @Override
    public String toString() {
        return "VoteDetail{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                ", counts=" + counts +
                ", type=" + type +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                '}';
    }
}
