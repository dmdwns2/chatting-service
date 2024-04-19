package com.example.log4j;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class LogInterceptor implements HandlerInterceptor {
    // Request가 들어오고 Controller에 넘어가기 직전에 처리
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("URL : {}", request.getRequestURI());
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    // Controller에서 요청이 다 마무리되고, View로 렌더링하기 전에 처리
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response
            , Object handler, ModelAndView modelAndView) throws Exception {
        log.info("response status: {}", response.getStatus());
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    // Controller에서 요청이 다 마무리되고, View로 렌더링이 다 끝나면 처리
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response
            , Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}
