package com.parksw.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.parksw.app.service.SampleService;

/**
 * SampleController
 * 
 * @author parksw
 * @Create 2018.02.12
 * @version 1.0
 */
@Controller
public class SampleController {

	@Autowired
	private SampleService sampleService;
	
	@GetMapping("")
	public String index(Model model) {
		model.addAttribute("samples", sampleService.findSamples());
		
		return "index";
	}
}
