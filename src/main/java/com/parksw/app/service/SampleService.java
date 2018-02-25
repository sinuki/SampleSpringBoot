package com.parksw.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parksw.app.primary.dao.SampleDAO;
import com.parksw.app.vo.SampleVO;

/**
 * SampleService
 * 
 * @author parksw
 * @Create 2018.02.12
 * @version 1.0
 */
@Service
public class SampleService {

	@Autowired
	private SampleDAO sampleDAO;
	
	public List<SampleVO> findSamples() {
		return sampleDAO.findSamples();
	}
}
