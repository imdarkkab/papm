package com.nichesoft.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nichesoft.api.response.MessagesResponse;
import com.nichesoft.api.response.bean.Message;

@Controller
public class MessageController {

	@RequestMapping(value="/messages",method=RequestMethod.GET)
	@ResponseBody
	public List<MessagesResponse> messages(){
		List<MessagesResponse> res = new ArrayList<MessagesResponse>();
		
		Message en = new Message("en", "title") ;
		Message th = new Message("th", "ชื่อเรื่อง") ;
		List<Message> m1 = new ArrayList<Message>();
		m1.add(en);
		m1.add(th) ;
		MessagesResponse res1 = new MessagesResponse("test.title",m1) ;
		MessagesResponse res2 = new MessagesResponse("test.title",m1) ;
		res.add(res1);
		res.add(res2) ;
		return res ;
	}
}
