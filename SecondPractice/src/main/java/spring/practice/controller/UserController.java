package spring.practice.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import spring.practice.dao.UserDao;
import spring.practice.domain.Authentication;
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
	
	@RequestMapping(value="/users", method=RequestMethod.PUT)
	public String modify(@Valid User user, BindingResult bindingResult, Model model) {
		log.info("User : {}", user);
		
		if (bindingResult.getErrorCount() > 0) {
			return "/user/form";
		}

		int affectedRowNum = userDao.update(user);
		
		if (affectedRowNum != 1) {
			model.addAttribute("errorMessage", "예기치 못한 에러가 발생했습니다. 다시 시도해주세요");
			return "/user/form";
		}
		
		return "redirect:/";
	}
	
	@RequestMapping("/users/login/form")
	public String loginForm(Authentication authentication) {
		return "/user/login";
	}
	
	@RequestMapping(value="/users/login", method=RequestMethod.POST)
	public String loginForm(@Valid Authentication authentication, BindingResult bindingResult, Model model, HttpSession session) {
		log.info("Authentication : {}", authentication);
		
		if (bindingResult.getErrorCount() > 0) {
			return "/user/login";
		}

		User selectedUser = userDao.findById(authentication.getUserId());
		
		if (selectedUser == null) {
			model.addAttribute("errorMessage", "존재하지 않는 아이디입니다. 다시한번 확인해 주세요");
			return "/user/login";
		}
		
		if ( selectedUser.isSamePassword(authentication) == false) {
			model.addAttribute("errorMessage", "잘못된 비밀번호입니다. 다시한번 확인해 주세요");
			return "/user/login";
		}
		
		session.setAttribute("userId", authentication.getUserId());
		return "redirect:/";
	}
	
	@RequestMapping("/users/{userId}/form")
	public String modifyForm(@PathVariable String userId, Model model, HttpSession session) {
		
		//TODO HttpSessionUtil과 같은 체크 라이브러리를 사용한다
		Object userObject = session.getAttribute("userId");
		String userIdFromSession = null;
		
		if (userObject == null) {
			return "redirect:/";
		} else {
			try {
				userIdFromSession  = userObject.toString();
			} catch (Exception e) {
				return "redirect:/";
			}
		}
		
		//TODO 현재 세션에 저장된 userId와 다른값이라면
		if (! userIdFromSession.equalsIgnoreCase(userId)) {
			//TODO 에러메세지 출력
			return "redirect:/users/" + userIdFromSession;
		}
		
		//TODO User가 존재하지 않다면
		User selectedUser = userDao.findById(userId);
		if (selectedUser == null) {
			//TODO 에러메세지 출력
			return "redirect:/";
		}
		
		model.addAttribute("user", selectedUser);
		
		return "/user/form";
	}
	
	@RequestMapping("/users/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("userId");
		return "redirect:/";
	}
}
