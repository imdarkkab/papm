package com.nichesoft.api.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.nichesoft.bean.User;
import com.sun.servicetag.UnauthorizedAccessException;

@Component
@Log4j
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.debug("AuthenticationInterceptor-preHandle");
		String uri = request.getRequestURI();
		if (!uri.endsWith("/api/login") && !uri.endsWith("/api/logout")) {
			User userData = (User) request.getSession().getAttribute("LOGGEDIN_USER");
			if (userData == null) {
				throw new UnauthorizedAccessException("Unauthorized") ;

			}else{
				return true;
			}
		}else{
			return true;
		}
		
	}
}
