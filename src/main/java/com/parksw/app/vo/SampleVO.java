package com.parksw.app.vo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * SampleVO
 * 
 * @author parksw
 * @Create 2018.02.12
 * @version 1.0
 */
public class SampleVO {
	/** 샘플 번호 */
	private int no;
	/** 샘플 제목 */
	private String title;
	/** 샘플 설명 */
	private String description;

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		String result = "";
		
		try {
			result = new ObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
