package com.nichesoft.api.response;

import java.io.Serializable;
import java.util.HashMap;

import org.apache.commons.collections.map.HashedMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public @Data class LoginResponse /*extends GenericResponse<HashMap<String,String>> implements Serializable */{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4532736326775093267L;
	
//	private HashMap<String,String> data ;
	private String sessionId ;

//	public LoginResponse(int status, HashMap<String,String> data) {
////		super(status, data);
//		this.data = data ;
//	}


	
	
}
