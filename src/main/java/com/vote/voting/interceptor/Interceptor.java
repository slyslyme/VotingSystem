package com.vote.voting.interceptor;

import com.vote.voting.exception.GlobalException;
import com.vote.voting.result.CodeMsg;
import com.vote.voting.result.Result;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author slyslyme
 * @version 1.0
 * @date 2019/11/17 21:23
 * @package com.vote.voting.interceptor
 **/
public class Interceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String user = (String)session.getAttribute("username");
        if(user != null)
            return true;
        else {
            //response.sendRedirect(request.getContextPath()+"/account/login");
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }

}
