package com.example.shiro;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Map;
@Slf4j
public class MyFilter extends BasicHttpAuthenticationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return super.isAccessAllowed(request, response, mappedValue);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return super.onAccessDenied(request, response);
    }

    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        return super.isLoginAttempt(request, response);
    }

    @Override
    protected boolean isLoginAttempt(String authzHeader) {
        return super.isLoginAttempt(authzHeader);
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        return super.executeLogin(request, response);
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Map map = request.getParameterMap();
        String requestStr = null;
        if(map != null){
            JSONObject json = new JSONObject(map);
            requestStr = json.toJSONString();
        }
        log.info("request start,url:{},params:{}", request.getLocalAddr(), requestStr);
        return true;
    }

    @Override
    protected void postHandle(ServletRequest request, ServletResponse response) throws Exception {
        Map map = request.getParameterMap();
        String requestStr = null;
        if(map != null){
            JSONObject json = new JSONObject(map);
            requestStr = json.toJSONString();
        }
        ShiroHttpServletRequest r = (ShiroHttpServletRequest)request;
        log.info("request finished,url:{},params:{}",r.getRequestURI()+((ShiroHttpServletRequest) request).getHeader("token"), requestStr);
    }

    @Override
    public void afterCompletion(ServletRequest request, ServletResponse response, Exception exception) throws Exception {
        Map  map = request.getParameterMap();
        String requestStr = null;
        if(map != null){
            JSONObject json = new JSONObject(map);
            requestStr = json.toJSONString();
        }
        log.info("request exception,url:{},params:{}", request.getLocalAddr(), requestStr);
    }
}
