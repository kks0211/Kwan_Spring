package org.kwan.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.kwan.dao.LoginDAO;
import org.kwan.dao.LoginDAOImpl;
import org.kwan.domain.LoginVO;
import org.kwan.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Inject
	private LoginService service;

	@GetMapping(value = "/slipp")
	public void slippGet() throws Exception {
		logger.info("slippPage");

	}

	@GetMapping(value = "/create")
	public void createGet(Model model) throws Exception {
		logger.info("createGet");
		model.addAttribute("loginVO", new LoginVO());

	}

	@PostMapping(value = "/create")
	public String createPost(@Valid LoginVO login, BindingResult bindingResult, RedirectAttributes rttr) throws Exception {
		logger.info("createPost");
		logger.info("User : " + login);

		if (bindingResult.hasErrors()) {
			logger.info("bindingResult error!!");
			List<ObjectError> errors = bindingResult.getAllErrors();
			for (ObjectError error : errors) {
				logger.info("error : {}, {}", error.getCode(), error.getDefaultMessage());
			}
			return "/login/create";
		}

		service.regist(login);

		rttr.addFlashAttribute("result", "success");

		return "redirect:/login/slipp";

	}

	@GetMapping(value = "/login")
	public void loginGet(Model model) throws Exception {
		logger.info("login GET ====> ");
		model.addAttribute("loginVO", new LoginVO());
	}

	@PostMapping(value = "/login")
	public String loginPost(@Valid LoginVO login, BindingResult bindingResult, RedirectAttributes rttr, HttpSession session, Model model)
			throws Exception {
		logger.info("login POST ====> ");

		/*
		 * if(bindingResult.hasErrors()){ logger.info("bindingResult error!!");
		 * List<ObjectError> errors = bindingResult.getAllErrors();
		 * for(ObjectError error: errors) { logger.info("error : {}, {}",
		 * error.getCode(), error.getDefaultMessage()); } return "/login/login";
		 * }
		 */

		if (service.loginCheck(login.getUserId(), login.getPassword(), model)) {

			service.findByUserId(login.getUserId());

			session.setAttribute("userId", login.getUserId());

			rttr.addFlashAttribute("result", "success");

			return "redirect:/login/slipp";
		}

		return "/login/login";
	}

	@GetMapping(value = "/logout")
	public String logoutPost(RedirectAttributes rttr, HttpSession session) throws Exception {
		logger.info("logout ==== > ");

		session.removeAttribute("userId");

		rttr.addFlashAttribute("result", "success");

		return "redirect:/login/slipp";
	}

	@GetMapping(value = "{userId}/update")
	public String updateGet(@PathVariable("userId") String userId, RedirectAttributes rttr, Model model) throws Exception {
		logger.info("updateGet");

		if (userId == null) {
			throw new IllegalArgumentException("사용자 ID가 존재하지 않습니다.");
		}

		LoginVO login = service.findByUserId(userId);

		model.addAttribute("loginVO", login);
		model.addAttribute("isUpdate", true);
		// rttr.addFlashAttribute("isUpdate", true);

		return "/login/create";

	}

}
