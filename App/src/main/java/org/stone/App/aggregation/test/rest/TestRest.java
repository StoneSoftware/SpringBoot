package org.stone.App.aggregation.test.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.stone.App.aggregation.common.mapper.TemplateMapper;
import org.stone.App.aggregation.test.po.Student;

@RestController
@RequestMapping("/test/")
public class TestRest {

	@Autowired
	private TemplateMapper mapper;

	@GetMapping("t")
	public List<Student> test(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		return mapper.getAll();
	}
}
