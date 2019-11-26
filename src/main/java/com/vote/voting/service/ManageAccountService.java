package com.vote.voting.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.vote.voting.dao.AccountDao;
import com.vote.voting.dao.ManageAccountDao;
import com.vote.voting.entity.VoteAccount;
import com.vote.voting.exception.GlobalException;
import com.vote.voting.result.CodeMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author slyslyme
 * @version 1.0
 * @date 2019/11/20 1:07
 * @package com.vote.voting.service
 **/
@Service
public class ManageAccountService {
    @Autowired
    ManageAccountDao manageAccountDao;

    @Autowired
    AccountDao accountDao;

    /**
     * 查询全部用户
     * @return
     */
    public List<VoteAccount> findAllAccounts(){
        List<VoteAccount> accounts = manageAccountDao.findAllAccounts();
        if(accounts == null)
            throw new GlobalException(CodeMsg.NO_ACCOUNT);
        return accounts;
    }

    /**
     * 根据不同的类型进行查询
     * @param type
     * @param key
     * @return
     */
    public List<VoteAccount> findSomeAccounts(String type, String key){
        List<VoteAccount> accounts = new ArrayList<>();
        if("username".equals(type)){
            accounts = manageAccountDao.findByUsername(key.trim());
        } else if("phone".equals(type)){
            accounts = manageAccountDao.findByPhoneNumber(key.trim());
        }else if("gender".equals(type)){
            int gender = -1;
            key = key.trim();
            if(key.equals("男"))
                gender = 0;
            else if(key.equals("女"))
                gender = 1;
            else
                throw new GlobalException(CodeMsg.ERROR_GENDER); // 性别格式出错
            accounts = manageAccountDao.findByGender(gender);
        } else if("age".equals(type)){
            try{
                int x = Integer.valueOf(key.trim());
            } catch (Exception e){
                throw new GlobalException(CodeMsg.ERROR_AGE);
            }
            accounts = manageAccountDao.findByAge(Integer.valueOf(key.trim()));
        }
        return accounts;
    }

    /**
     * 添加用户
     * @param voteAccount
     * @return
     */
    public Boolean addAccount(VoteAccount voteAccount){
        String username = voteAccount.getUsername();
        VoteAccount account = accountDao.findAccount(username);
        if(account != null) {  // 用户名已经存在
            throw new GlobalException(CodeMsg.USER_EXIST);
        }
        String password = voteAccount.getPassword();
        String phone = voteAccount.getPhone();
        account = accountDao.findAccountByPhone(phone);
        if(account != null){  // 手机号已经被使用
            throw new GlobalException(CodeMsg.PHONE_EXIST);
        }
        int gender = voteAccount.getGender();
        int age = voteAccount.getAge();
        Boolean flag = manageAccountDao.addAccount(username,password,phone,gender,age);
        if(flag == false)
            throw new GlobalException(CodeMsg.FAIL_ADD_ACCOUNT); // 添加用户失败
        return true;
    }

    /**
     * 删除用户--根据用户名
     * @param username
     * @return
     */
    public Boolean deleteAccount(String username){
        boolean flag = manageAccountDao.deleteAccount(username);
        if(flag)
            return true;
        else
            throw new GlobalException(CodeMsg.FAIL_DELETE_ACCOUNT);
    }

    /**
     * 修改用户信息
     * @param account
     * @return
     */
    @Transactional
    public Boolean updateAccount(VoteAccount account){
        int id = account.getId();
        String username = account.getUsername();
        String password = account.getPassword();
        if(accountDao.findAccount(username) != null && accountDao.findAccount(username).getId() != id){
            throw new GlobalException(CodeMsg.USER_EXIST);
        }
        String phone = account.getPhone();
        if(accountDao.findAccountByPhone(phone) != null)
            throw new GlobalException(CodeMsg.PHONE_EXIST);
        int gender = account.getGender();
        int age = account.getAge();
        boolean flag =manageAccountDao.updateAccount(id,username,password,phone,gender,age);
        if(flag)
            return true;
        else
            return false;
    }

}
