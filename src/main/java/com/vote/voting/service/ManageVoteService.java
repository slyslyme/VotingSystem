package com.vote.voting.service;

import com.vote.voting.dao.ManageVoteDao;
import com.vote.voting.entity.VoteCase;
import com.vote.voting.entity.VoteDetail;
import com.vote.voting.entity.VoteOptions;
import com.vote.voting.exception.GlobalException;
import com.vote.voting.result.CodeMsg;
import com.vote.voting.vo.EchartsHistogram;
import com.vote.voting.vo.VoteDetailOption;
import com.vote.voting.vo.VoteInsert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * @author slyslyme
 * @version 1.0
 * @date 2019/11/21 14:08
 * @package com.vote.voting.service
 **/
@Service
public class ManageVoteService {

    @Autowired
    ManageVoteDao manageVoteDao;

    @Autowired
    DetailService detailService;

    /**
     * 删除投票，包括活动、选项、情况
     * @param id
     * @return
     */
    @Transactional
    public List<VoteDetail> deleteVote(int id){
        boolean flag2=true, flag3=true;
        boolean flag1 = manageVoteDao.deleteVote(id);
        List<VoteOptions> options = manageVoteDao.findOption(id);
        if(!options.isEmpty())
            flag2 = manageVoteDao.deleteVoteOptions(id);
        List<VoteCase> cases = manageVoteDao.findCase(id);
        System.out.println(cases);
        if(!cases.isEmpty())
            flag3 = manageVoteDao.deleteVoteCase(id);
        System.out.println(flag1 +" " + flag2  +" "+ flag3);
        if(flag1 == false || flag2 == false || flag3 == false)
            throw  new GlobalException(CodeMsg.FAIL_DELETE_VOTE);  // 删除投票失败
        return detailService.findVoteList();
    }


    /**
     * 管理员发起投票
     * @param voteInsert
     * @return
     */
    @Transactional
    public List<VoteDetail> addOptions(VoteInsert voteInsert){
        String title = voteInsert.getTitle(); // 判断标题是否存在
        if(manageVoteDao.findByTitle(title) != null)
            throw new GlobalException(CodeMsg.EXIST_TITLE);
        List<String> options = voteInsert.getVoteOptions();
        HashSet<String> set = new HashSet<>();
        set.add(options.get(0));
        for(int i = 1; i < options.size(); i++){
            if(set.contains(options.get((i)))){
                throw new GlobalException(CodeMsg.REPETE_OPTIONS); // 选项重复
            }
            set.add(options.get(i));
        }

        String detail = voteInsert.getDetail();
        int type = voteInsert.getType();
        String start = voteInsert.getStart();
        String end = voteInsert.getEnd();
        VoteDetail voteDetail = new VoteDetail();
        voteDetail.setTitle(title);
        voteDetail.setDetail(detail);
        voteDetail.setType(type);
        voteDetail.setStart(start);
        voteDetail.setEnd(end);
        manageVoteDao.insertVote(voteDetail);
        VoteDetail detail2 = manageVoteDao.findByTitle(title);
        int voteId = detail2.getId();
        System.out.println(voteId);
        System.out.println(options.size());
        for(int i = 0; i < options.size(); i++){
            manageVoteDao.insertVoteOption(voteId, i, options.get(i));
        }
        return detailService.findVoteList();
    }

    /**
     * 对投票活动的修改，未开始的投票所有的内容都可以进行修改，正在进行中的投票和已经结束的投票，不能修改
     * @param voteInsert
     * @return
     */
    public List<VoteDetail> updateVote(VoteInsert voteInsert){
        int id = voteInsert.getId();
        if(id == 0)
            throw new GlobalException(CodeMsg.NO_ID);
        System.out.println(id + "  -----");
        VoteDetail voteDetail = manageVoteDao.findVoteDetailById(id);

        String title = voteInsert.getTitle();
        VoteDetail voteDetail1 = manageVoteDao.findByTitle(title);

        if(voteDetail1 != null && manageVoteDao.findByTitle(title).getId()!=voteInsert.getId())
            throw new GlobalException(CodeMsg.EXIST_TITLE); // 修改后的标题已经存在

        List<String> option = voteInsert.getVoteOptions();
        HashSet<String> set = new HashSet<>();
        set.add(option.get(0));
        for(int i = 1; i < option.size(); i++){
            if(set.contains(option.get((i)))){
                throw new GlobalException(CodeMsg.REPETE_OPTIONS);
            }
            set.add(option.get(i));
        }

        manageVoteDao.updateVoteDetail(voteInsert);
        List<String> options = voteInsert.getVoteOptions();
        manageVoteDao.deleteOption(id);
        for(int i = 0; i < options.size(); i++){
            manageVoteDao.insertVoteOption(id, i, options.get(i));
        }
        return detailService.findVoteList();
    }

    /**
     * 根据id查找投票信息
     * @param id
     * @return
     */
    public VoteDetail findById(int id){
        VoteDetail detail = manageVoteDao.findVoteDetailById(id);
        System.out.println(detail.getStart() + "   " + detail.getEnd());
        return detail;
    }

    /**
     * 根据标题、类型进行模糊匹配查询
     * @param key
     * @return
     */
    public List<VoteDetail> findByTitle(String type, String key){
        List<VoteDetail> list = new ArrayList<>();
        int typeNum = 0;
        key = key.trim();
        if(type.equals("title")) {
            list = manageVoteDao.findByTitleDim(key);
        } else if(type.equals("type")){
            if(key.equals("单选")) typeNum = 0;
            else if(key.equals("多选")) typeNum = 1;
            else throw new GlobalException(CodeMsg.ERROR_OPTION); // 查询的数据不对
            list = manageVoteDao.findByTypeDim(typeNum);
        }
        System.out.println(type + " " + key);
        return list;
    }

    /**
     * 柱状图
     * @param id
     * @return
     */
    public EchartsHistogram findHistogram(int id){
        List<Integer> listNum = manageVoteDao.findHistogramNum(id);
        List<String> listOption = manageVoteDao.findHistogramOptions(id);
        if(listNum == null || listOption==null)
            throw new GlobalException(CodeMsg.NO_DATA);
        EchartsHistogram echartsHistogram = new EchartsHistogram();
        echartsHistogram.setNums(listNum);
        echartsHistogram.setOptions(listOption);
        return echartsHistogram;
    }
}
