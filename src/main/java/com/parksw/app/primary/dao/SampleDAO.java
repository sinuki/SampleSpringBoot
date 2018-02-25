package com.parksw.app.primary.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.parksw.app.vo.SampleVO;

/**
 * SampleDAO
 * 
 * @author parksw
 * @Create 2018.02.12
 * @version 1.0
 */
@Repository
public interface SampleDAO {

	List<SampleVO> findSamples();
}
