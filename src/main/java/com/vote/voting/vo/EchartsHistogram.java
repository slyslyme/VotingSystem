package com.vote.voting.vo;

import java.util.List;

/**
 * @author slyslyme
 * @version 1.0
 * @date 2019/11/23 15:26
 * @package com.vote.voting.vo
 **/
public class EchartsHistogram {
    List<Integer> nums;
    List<String> options;

    public List<Integer> getNums() {
        return nums;
    }

    public void setNums(List<Integer> nums) {
        this.nums = nums;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "EchartsHistogram{" +
                "nums=" + nums +
                ", options=" + options +
                '}';
    }
}
