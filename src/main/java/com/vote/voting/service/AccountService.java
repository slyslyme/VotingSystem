package com.vote.voting.service;

import com.vote.voting.dao.AccountDao;
import com.vote.voting.entity.VoteAccount;
import com.vote.voting.exception.GlobalException;
import com.vote.voting.result.CodeMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author slyslyme
 * @version 1.0
 * @date 2019/11/17 9:31
 * @package com.vote.voting.service
 **/
@Service
public class AccountService {
    @Autowired
    AccountDao accountDao;

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    public VoteAccount login(String username, String password){
        VoteAccount account = accountDao.findAccount(username);
        if(account == null)
            throw new GlobalException(CodeMsg.USER_NOT_EXIST); // 抛出全局异常，用户名不存在
        account = accountDao.accountLogin(username, password);
        if(account == null)
            throw new GlobalException(CodeMsg.PASSWORD_ERROR); // 密码错误
        return account;
    }

    /**
     * 查找对应用户名的信息
     * @param username
     * @return
     */
    public VoteAccount findAccountByUsername(String username){
        VoteAccount account = accountDao.findAccount(username);
        if(account == null)
            throw new GlobalException(CodeMsg.USER_NOT_EXIST);
        return account;
    }

    /**
     * 注册
     * @param account
     * @return
     */
    public boolean reg(VoteAccount account){
        VoteAccount voteAccount = accountDao.findAccount(account.getUsername());
        if(voteAccount != null)
            throw new GlobalException(CodeMsg.USER_EXIST); // 用户名已经存在
        voteAccount = accountDao.findAccountByPhone(account.getPhone());
        if(voteAccount != null)
            throw new GlobalException(CodeMsg.PHONE_EXIST); // 手机号已经存在
        return accountDao.insertAccount(account);
    }

    /**
     * 验证session
     * @param request
     * @return
     */
    public String getSession(HttpServletRequest request){
        Object obj = request.getSession().getAttribute("username");
        if(obj == null)
            return null;
        String str = obj.toString();
        return str;
    }

    public String getSessionPass(HttpServletRequest request){
        Object obj = request.getSession().getAttribute("password");
        if(obj == null)
            return null;
        String str = obj.toString();
        return str;
    }
}
