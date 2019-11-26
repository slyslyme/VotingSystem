package com.vote.voting.Controller;

import com.vote.voting.entity.VoteAccount;
import com.vote.voting.result.Result;
import com.vote.voting.service.AccountService;
import com.vote.voting.service.AccountVoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *
 * @author slyslyme
 * @version 1.0
 * @date 2019/11/17 9:33
 * @package com.vote.voting.Controller
 **/
@RestController
@RequestMapping("account")
public class AccountContoller {

    @Autowired
    AccountService accountService;
    @Autowired
    AccountVoteService accountVoteService;

    private static Logger logger = LoggerFactory.getLogger(AccountContoller.class);

    @RequestMapping("login")
    public Result<VoteAccount> doLogin(@RequestParam("username") String username, @RequestParam("password")String password,
                                       HttpServletRequest request){
        logger.info("data:"+username + " " + password);
        VoteAccount account = accountService.login(username, password);
        String pass = accountVoteService.password(account.getId());
        request.getSession().setAttribute("username",username);
        request.getSession().setAttribute("password",pass);
        return Result.success(account);
    }

    @RequestMapping(value = "reg", method = RequestMethod.POST)
    public Result<Boolean> doReg(VoteAccount account){
        logger.info("data:"+account);
        accountService.reg(account);
        return Result.success(true);
    }
}
