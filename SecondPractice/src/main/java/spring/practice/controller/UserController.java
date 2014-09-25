package spring.practice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import spring.practice.dao.UserDao;
import spring.practice.domain.User;

@Controller
public class UserController {
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserDao userDao;
	
	@RequestMapping("/users/form")
	public String form() {
		return "/user/form";
	}
	
	@RequestMapping(value="/users", method=RequestMethod.POST)
	public String create(User user, Model model) {
		log.info("User : {}", user);
		
		//TODO Validation Check
		//TODO 데이터베이스에서 동일 아이디 존재하는지 체크
		User selectedUser = userDao.findById(user.getUserId());
		if (selectedUser != null) {
			//TODO Error Message 출력가능하도록 처리, 리다이렉트
			model.addAttribute("errorMessage", "아이디가 이미 존재합니다.");
			return "/user/form";
		}
		
		//TODO 데이터베이스 입력실패시 에러처리
		userDao.create(user);
		
		return "redirect:/";
	}
}
