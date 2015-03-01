package com.nichesoft.api.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.nichesoft.api.response.LoginResponse;
import com.nichesoft.bean.User;
import com.nichesoft.business.service.UserService;

@Log4j
@Controller
public class LoginController  {

	@Resource
	private UserService userService ;
	
	@RequestMapping( value="/login", method = {RequestMethod.GET} )
	//@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public LoginResponse login(HttpServletRequest request , HttpServletResponse response , 
			@RequestParam("username") String userName,
			@RequestParam("password") String password){
		
		User user = userService.findByUserNameAndPassword(userName, password);
		log.debug("user="+user);
		
		User user2 = userService.findByUserNameOrPassword(userName, password);
		log.debug("user2="+user2);
		
		User user3 = userService.findByCorporateId(1);
		log.debug("user3="+user3);
		
		String sessionId = request.getSession().getId() ;
		log.debug("sessionId="+sessionId);
		return new LoginResponse(sessionId) ;
	}

}
