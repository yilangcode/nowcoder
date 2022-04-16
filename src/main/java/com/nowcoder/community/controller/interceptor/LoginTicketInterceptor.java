package com.nowcoder.community.controller.interceptor;

import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CookieUtil;
import com.nowcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class LoginTicketInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;


    /*
    客户端每次请求到达，拦截器先在controller前通过cookie携带的ticket查询用户
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从cookie中获取凭证（前提是登录状态）
        String ticket = CookieUtil.getValue(request, "ticket");
        if(ticket != null){
            //查询凭证，是否有效，是否超时？
            LoginTicket loginTicket = userService.findLoginTicket(ticket);
            //检查凭证是否有效
            if(loginTicket != null && loginTicket.getStatus() == 0 && loginTicket.getExpired().after(new Date())){
                User user = userService.findUserById(loginTicket.getUserId());
                //在本次请求中持有用户（信息）      （此处并发多线程，线程隔离，数据不互相干扰） ThreadLocal
                //请求处理完成后，响应完服务器，此次线程销毁
                hostHolder.setUser(user);
            }
        }

        return true;
    }

    //将User放入模板
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User user = hostHolder.getUser();
        if(user != null && modelAndView != null){
            modelAndView.addObject("loginUser",user);
        }
    }

    //整个请求结束后，清理数据

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
