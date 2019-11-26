package com.vote.voting.service;

import com.vote.voting.dao.AccountVoteDao;
import com.vote.voting.dao.DetailDao;
import com.vote.voting.entity.VoteAccount;
import com.vote.voting.exception.GlobalException;
import com.vote.voting.result.CodeMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

/**
 * @author slyslyme
 * @version 1.0
 * @date 2019/11/17 21:44
 * @package com.vote.voting.service
 **/

@Service
public class AccountVoteService {

    @Autowired
    AccountVoteDao accountVoteDao;

    @Autowired
    DetailService detailService;

    @Autowired
    DetailDao detailDao;

    /**
     * 用户投票 (要求投票返回的格式一定是List类型，默认单选返回的也是list类型)
     * @param vote_id  投票活动id
     * @param account_id 账号id
     * @param options 所选的选项
     * @return
     */
    public boolean insertChoice(int vote_id, int account_id, List<Integer> options){
        int nums = options.size();
        Boolean flag;
        for(int i = 0; i < nums; i++){
            flag = accountVoteDao.accountVote(vote_id, account_id, options.get(i));
            if(flag == false){
                throw new GlobalException(CodeMsg.VOTE_FAIL);
            }
        }
        accountVoteDao.voteNums(vote_id);
        return true;
    }

    public String findStartTime(int id){
        String str = detailDao.findStartTime(id);
        return str;
    }

    /**
     * 修改密码
     * @param oldPass
     * @param newPass
     * @param id
     * @return
     */
    public Boolean updatePw(String oldPass, String newPass, int id){
        VoteAccount account = accountVoteDao.findPass(id);
        if(oldPass.equals(account.getPassword())){
            return accountVoteDao.updatePass(newPass, id);
        }
        throw new GlobalException(CodeMsg.PASS_ERROR);
    }

    /**
     * 获取password
     * @param id
     * @return
     */
    public String password(int id){
        VoteAccount account = accountVoteDao.findPass(id);
        return account.getPassword();
    }

    public String findPass(String password){
        VoteAccount account =  accountVoteDao.findPass1(password);
        return account.getPassword();
    }
}
