package com.vote.voting.service;

import com.vote.voting.dao.AdminDao;
import com.vote.voting.entity.VoteUser;
import com.vote.voting.exception.GlobalException;
import com.vote.voting.result.CodeMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author slyslyme
 * @version 1.0
 * @date 2019/11/17 9:32
 * @package com.vote.voting.service
 **/
@Service
public class AdminService {

    @Autowired
    AdminDao adminDao;

    public VoteUser findUser(String username, String password){
        VoteUser name = adminDao.findUserByUsername(username);
        if(name == null)
            throw new GlobalException(CodeMsg.USER_NOT_EXIST);
        VoteUser user = adminDao.findUser(username, password);
        if (user == null)
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        return user;
    }

    public VoteUser findUserByUsername(String username){
        return adminDao.findUserByUsername(username);
    }

    public Boolean updatePw(String oldPass, String newPass, int id){
        VoteUser account = adminDao.findPass(id);
        if(oldPass.equals(account.getPassword())){
            return adminDao.updatePass(newPass, id);
        }
        throw new GlobalException(CodeMsg.PASS_ERROR);
    }
}
