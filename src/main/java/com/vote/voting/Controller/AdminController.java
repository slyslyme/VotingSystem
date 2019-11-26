package com.vote.voting.Controller;

import com.vote.voting.entity.VoteUser;
import com.vote.voting.result.Result;
import com.vote.voting.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;

/**
 * @author slyslyme
 * @version 1.0
 * @date 2019/11/17 19:21
 * @package com.vote.voting.Controller
 **/
@RequestMapping("user")
@RestController
public class AdminController {
    @Autowired
    AdminService adminService;

    private static Logger logger = LoggerFactory.getLogger(AccountContoller.class);

    @RequestMapping("login")
    public Result<VoteUser> doLogin(HttpServletRequest request, @RequestParam("username")String username, @RequestParam("password")String password){
        logger.info("data:"+username + password);
        request.getSession().setAttribute("username",username);
        VoteUser user = adminService.findUser(username, password);
        return Result.success(user);
    }
}
