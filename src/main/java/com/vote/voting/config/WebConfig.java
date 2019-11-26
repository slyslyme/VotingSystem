package com.vote.voting.config;

import com.vote.voting.interceptor.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;

import javax.servlet.ServletContext;
import javax.websocket.Session;
import java.util.List;

/**
 * @author slyslyme
 * @version 1.0
 * @date 2019/11/17 21:30
 * @package com.vote.voting.config
 **/

@Configuration
public class WebConfig implements WebMvcConfigurer, ServletContextInitializer {

    /**
     * 允许跨域访问
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 添加映射路径
        registry.addMapping("/**")
                // 放行哪些原始域
                .allowedOrigins("*")
                // 放行哪些请求方式
                .allowedMethods("*")
                // 是否允许携带 cookie
                .allowCredentials(true)
                // 放行哪些头部信息
                .allowedHeaders("*");
    }

    @Override
    public void onStartup(ServletContext servletContext){
        servletContext.setSessionTimeout(20);
        System.out.println("session过期时间是" + servletContext.getSessionTimeout());
    }

    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Interceptor()).addPathPatterns("/**").excludePathPatterns("/account/**","/user/**");
        //registry.addInterceptor(new Interceptor()).excludePathPatterns("/**");
        //registry.addInterceptor(new Interceptor()).addPathPatterns("/");
    }
}
