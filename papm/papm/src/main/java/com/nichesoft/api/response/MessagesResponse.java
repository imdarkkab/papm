package com.nichesoft.api.response;

import java.io.Serializable;
import java.util.List;

import com.nichesoft.api.response.bean.Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public @Data class MessagesResponse implements Serializable {

	public String key ;
	public List<Message> message ;
	

}
