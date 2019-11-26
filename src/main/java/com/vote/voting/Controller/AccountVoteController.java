package com.vote.voting.Controller;

import com.vote.voting.entity.VoteAccount;
import com.vote.voting.entity.VoteDetail;
import com.vote.voting.exception.GlobalException;
import com.vote.voting.result.CodeMsg;
import com.vote.voting.result.Result;
import com.vote.voting.service.AccountService;
import com.vote.voting.service.AccountVoteService;
import com.vote.voting.service.DetailService;
import com.vote.voting.service.ManageVoteService;
import com.vote.voting.vo.AccountInsert;
import com.vote.voting.vo.EchartsHistogram;
import com.vote.voting.vo.VoteDetailOption;
import com.vote.voting.vo.VoteInsert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

/**
 * @author slyslyme
 * @version 1.0
 * @date 2019/11/17 21:42
 * @package com.vote.voting.Controller
 **/

@RestController
@RequestMapping("vote")
public class AccountVoteController {

    @Autowired
    DetailService detailService;

    @Autowired
    AccountVoteService accountVoteService;

    @Autowired
    ManageVoteService manageVoteService;

    @Autowired
    AccountService accountService;

    private static Logger logger = LoggerFactory.getLogger(AccountVoteController.class);

    /**
     * 修改密码
     * @param request
     * @param oldPass
     * @param newPass
     * @return
     */
    @RequestMapping(value = "updatePw", method = RequestMethod.POST)
    public Result<Boolean> updatePw(HttpServletRequest request, @RequestParam("oldPass")String oldPass,@RequestParam("newPass")String newPass){
        String user = accountService.getSession(request);
        String pass = accountService.getSessionPass(request);
        if(user == null || !accountVoteService.findPass(user).equals(pass))
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        VoteAccount account = accountService.findAccountByUsername(user);
        return Result.success(accountVoteService.updatePw(oldPass,newPass,account.getId()));
    }

    /**
     * 展示投票列表，不需要登录就可以查看
     * @return
     */
    @RequestMapping("list")
    public Result<List<VoteDetail>> findVoteList(HttpServletRequest request){
        String user = accountService.getSession(request);
        String pass = accountService.getSessionPass(request);
        if(user == null || !accountVoteService.findPass(user).equals(pass))
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        List<VoteDetail> voteList = detailService.findVoteList();
        System.out.println(voteList.get(0).getEnd());
        return Result.success(voteList);
    }


    /**
     * 展示该投票活动内容详情，如果已经投过票了那么显示投票情况
     * 注意：account_id可以变为使用VoteAccount类来进行，在实体类中获取
     * @param voteId
     * @return
     */
    @RequestMapping("detail")
    public Result<VoteDetailOption> findDetailOptions(@RequestParam("id") int voteId, HttpServletRequest request){
        String user = accountService.getSession(request);
        String pass = accountService.getSessionPass(request);
        if(user == null || !accountVoteService.findPass(user).equals(pass))
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        logger.info("data:"+voteId + " " + user);
        VoteAccount account = accountService.findAccountByUsername(user);
        VoteDetailOption voteDetailOption = detailService.findDetailOption(voteId, account.getId());
        return Result.success(voteDetailOption);
    }

    /**
     * 某个投票的详情，没有选项
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("detailOne")
    public Result<VoteDetail> findById(HttpServletRequest request, @RequestParam("id") int id){
        logger.info("data " + id);
        String user = accountService.getSession(request);
        String pass = accountService.getSessionPass(request);
        if(user == null || !accountVoteService.findPass(user).equals(pass))
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        return Result.success(manageVoteService.findById(id));
    }


    /**
     * 用户进行投票，根据是单选题 多选题 进行投票
     * @param insert
     * @return
     */
    @RequestMapping(value = "choice", method = RequestMethod.POST)
    public Result<Boolean> vote(HttpServletRequest request, AccountInsert insert){
        String user = accountService.getSession(request);
        String pass = accountService.getSessionPass(request);
        if(user == null || !accountVoteService.findPass(user).equals(pass))
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        VoteAccount account = accountService.findAccountByUsername(user);
        int vote_id = insert.getId();
        System.out.println(vote_id);
        List<Integer> options = insert.getVoteOptions();
        System.out.println(options.size());
        logger.info(vote_id + "  " + account.getId()+"  " + options.size());
        Boolean flag = accountVoteService.insertChoice(vote_id, account.getId(), options);
        return Result.success(flag);
    }

    /**
     * 柱状图
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("detailHistogram")
    public Result<EchartsHistogram> findHistogram(HttpServletRequest request, @RequestParam("id") int id){
        logger.info("data:"+ id);
        String user = accountService.getSession(request);
        String pass = accountService.getSessionPass(request);
        if(user == null || !accountVoteService.findPass(user).equals(pass))
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        return Result.success(manageVoteService.findHistogram(id));
    }

    /**
     * 退出
     * @return
     */
    @RequestMapping("logout")
    public Result<Boolean> logout(HttpServletRequest request, String username){
        System.out.println(username);
        Enumeration em = request.getSession().getAttributeNames();
        while(em.hasMoreElements()){
            request.getSession().removeAttribute(em.nextElement().toString());
        }
        return Result.success(true);
    }

}
