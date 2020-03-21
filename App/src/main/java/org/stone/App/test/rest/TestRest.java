package org.stone.app.test.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.stone.app.common.mapper.TemplateMapper;
import org.stone.app.test.po.Student;

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
