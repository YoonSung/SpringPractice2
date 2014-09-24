package spring.practice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import spring.practice.domain.User;

@Controller
public class UserController {
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping("/users/form")
	public String form() {
		return "/user/form";
	}
	
	@RequestMapping(value="/users", method=RequestMethod.POST)
	public String create(User user) {
		log.info("User : {}", user);
		
		//TODO 데이터베이스에서 동일 아이디 존재하는지 체크
		//TODO Database 에 저장
		
		return "redirect:/";
	}
}
