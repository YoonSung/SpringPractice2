package spring.practice.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
	public String form(User user) {
		return "/user/form";
	}
	
	@RequestMapping(value="/users", method=RequestMethod.POST)
	public String create(@Valid User user, BindingResult bindingResult, Model model) {
		log.info("User : {}", user);
		
		if (bindingResult.getErrorCount() > 0) {
			return "/user/form";
		}

		User selectedUser = userDao.findById(user.getUserId());
		if (selectedUser != null) {
			model.addAttribute("errorMessage", "아이디가 이미 존재합니다.");
			return "/user/form";
		}
		
		int affectedRowNum = userDao.create(user);
		
		if (affectedRowNum != 1) {
			model.addAttribute("errorMessage", "예기치 못한 에러가 발생했습니다. 다시 시도해주세요");
			return "/user/form";
		}
		
		return "redirect:/";
	}
}
