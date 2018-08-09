package com.yao.interceptor;

import com.yao.entity.IP;
import com.yao.repository.IPRepository;
import com.yao.util.IPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private IPRepository ipRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(null != request.getSession(true).getAttribute("access")){
            return true;
        }

        System.out.println("进入了拦截器方法了。。。。。。");
        String ip = IPUtils.getIpAddress(request);

        if(ip.contains(",")){
            ip = ip.split(",")[0];
        }



        List<IP> list = ipRepository.findAll();
        for(IP i : list){
            if(i.getIp().equals(ip)){
                request.getSession(true).setAttribute("access",true);
                return true;
            }
        }
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }
}
