package com.vote.voting.Controller;

import com.vote.voting.entity.VoteAccount;
import com.vote.voting.entity.VoteDetail;
import com.vote.voting.entity.VoteUser;
import com.vote.voting.exception.GlobalException;
import com.vote.voting.result.CodeMsg;
import com.vote.voting.result.Result;
import com.vote.voting.service.*;
import com.vote.voting.vo.EchartsHistogram;
import com.vote.voting.vo.PollVo;
import com.vote.voting.vo.VoteDetailOption;
import com.vote.voting.vo.VoteInsert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author slyslyme
 * @version 1.0
 * @date 2019/11/20 1:18
 * @package com.vote.voting.Controller
 **/
@RestController
@RequestMapping("admin")
public class AdminVoteController {

    @Autowired
    ManageAccountService manageAccountService;
    @Autowired
    DetailService detailService;
    @Autowired
    ManageVoteService manageVoteService;
    @Autowired
    AccountService accountService;
    @Autowired
    PollService pollService;
    @Autowired
    AdminService adminService;

    private static Logger logger = LoggerFactory.getLogger(AccountContoller.class);

    /**
     * 更新密码
     * @param request
     * @param oldPass
     * @param newPass
     * @return
     */
    @RequestMapping(value = "updatePass", method = RequestMethod.POST)
    public Result<Boolean> updatePw(HttpServletRequest request, @RequestParam("oldPass")String oldPass,@RequestParam("newPass")String newPass){
        String user = accountService.getSession(request);
        //String pass = accountService.getSessionPass(request);
        if(user == null)
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        VoteUser voteUser = adminService.findUserByUsername(user);
        return Result.success(adminService.updatePw(oldPass,newPass,voteUser.getId()));
    }

    @RequestMapping("index")
    public Result<PollVo> findIndexData(HttpServletRequest request){
        String user = accountService.getSession(request);
        if(user == null)
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        PollVo vo = pollService.findIndexData();
        return Result.success(vo);
    }

    /**
     * 展示用户列表，包含详情信息
     * @return
     */
    @RequestMapping("accounts")
    public Result<List<VoteAccount>> findAllAccounts(HttpServletRequest request){
        String user = accountService.getSession(request);
        if(user == null)
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        return Result.success(manageAccountService.findAllAccounts());
    }

    /**
     * 模糊查询用户信息
     * @param key
     * @return
     */
    @RequestMapping("someAccounts")
    public Result<List<VoteAccount>> findSomeAccounts(HttpServletRequest request, @RequestParam("type")String type, @RequestParam("key")String key){
        String user = accountService.getSession(request);
        if(user == null)
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        logger.info("data:"+ type + key);
        return Result.success(manageAccountService.findSomeAccounts(type,key));
    }

    /**
     * 添加用户,添加成功后返回当前用户列表
     * @param voteAccount 用户类
     * @return
     */
    @RequestMapping(value = "addAccount", method = RequestMethod.POST)
    public Result<List<VoteAccount>> addAccount(HttpServletRequest request, VoteAccount voteAccount){
        String user = accountService.getSession(request);
        if(user == null)
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        logger.info("data:"+ voteAccount);
        boolean flag = manageAccountService.addAccount(voteAccount);
        return Result.success(manageAccountService.findAllAccounts());
    }

    /**
     * 删除用户--根据用户名
     * @param username
     * @return
     */
    @RequestMapping(value = "deleteAccount", method = RequestMethod.POST)
    public Result<List<VoteAccount>> deleteAccount(HttpServletRequest request, String username){
        String user = accountService.getSession(request);
        if(user == null)
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        logger.info("data:"+ username);
        manageAccountService.deleteAccount(username);
        return Result.success(manageAccountService.findAllAccounts());
    }

    /**
     * 根据用户名查找某个人
     * @param request
     * @param username
     * @return
     */
    @RequestMapping("accountOne")
    public Result<VoteAccount> findByUsername(HttpServletRequest request, String username){
        VoteAccount account = accountService.findAccountByUsername(username);
        return Result.success(account);
    }

    /**
     * 修改用户信息
     * @param voteAccount
     * @return
     */
    @RequestMapping("updateAccount")
    public Result<List<VoteAccount>> updateAccount(HttpServletRequest request, VoteAccount voteAccount){
        logger.info("data:"+ voteAccount);
        String user = accountService.getSession(request);
        if(user == null)
            throw new GlobalException(CodeMsg.SESSION_ERROR);

        manageAccountService.updateAccount(voteAccount);
        return Result.success(manageAccountService.findAllAccounts());
    }

    /**
     * 展示投票活动的列表
     * @return
     */
    @RequestMapping("voteList")
    public Result<List<VoteDetail>> findAllVote(HttpServletRequest request){
        /*String user = accountService.getSession(request);
        if(user == null)
            throw new GlobalException(CodeMsg.SESSION_ERROR);
*/
        return Result.success(detailService.findVoteList());
    }

    /**
     * 模糊查询投票详情内容
     * @param request
     * @param type
     * @param key
     * @return
     */
    @RequestMapping("voteListDim")
    public Result<List<VoteDetail>> findSomeVotes(HttpServletRequest request, @RequestParam("type") String type, @RequestParam("key") String key){
        logger.info("data:"+ type + " " + key);
        String user = accountService.getSession(request);
        if(user == null)
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        List<VoteDetail> details = manageVoteService.findByTitle(type, key);
        return Result.success(details);
    }

    /**
     * 某个投票活动的详情，选项等，管理员专用
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("detailVote")
    public Result<VoteDetailOption> findVoteDetail(HttpServletRequest request, @RequestParam("id") int id){
        logger.info("data:"+ id);
        String user = accountService.getSession(request);
        if(user == null)
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        VoteDetailOption voteDetailOption = detailService.findDetail(id);
        return Result.success(voteDetailOption);
    }

    /**
     * 删除某个投票活动，删除的同时，需要同时删除投票记录·投票中的选项这些
     * @param id
     * @return
     */
    @RequestMapping(value = "deleteVote",method = RequestMethod.POST)
    public Result<List<VoteDetail>> deleteVote(HttpServletRequest request, @RequestParam("id")int id){
        logger.info("data:"+ id);
        String user = accountService.getSession(request);
        if(user == null)
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        return Result.success(manageVoteService.deleteVote(id));
    }

    /**
     * 管理员发起投票
     * @param vote
     * @return
     */
    @RequestMapping(value = "addVote", method = RequestMethod.POST)
    public Result<List<VoteDetail>> addVote(HttpServletRequest request, VoteInsert vote){
        logger.info("data:"+ vote);
        String user = accountService.getSession(request);
        if(user == null)
            throw new GlobalException(CodeMsg.SESSION_ERROR);

        return Result.success(manageVoteService.addOptions(vote));
    }

    /**
     * 更新投票
     * @param request
     * @param vote
     * @return
     */
    @RequestMapping(value = "updateVote", method = RequestMethod.POST)
    public Result<List<VoteDetail>> updateVote(HttpServletRequest request, VoteInsert vote){
        logger.info("data:"+ vote);
        String user = accountService.getSession(request);
        if(user == null)
            throw new GlobalException(CodeMsg.SESSION_ERROR);

        return Result.success(manageVoteService.updateVote(vote));
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
        if(user == null)
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        return Result.success(manageVoteService.findHistogram(id));
    }

}
