package com.nichesoft.api.response.bean;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public @Data class Message implements Serializable {

	private String lang ;
	private String content ;
}
