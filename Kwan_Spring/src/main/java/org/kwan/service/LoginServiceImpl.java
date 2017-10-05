package org.kwan.service;

import javax.inject.Inject;

import org.kwan.dao.LoginDAO;
import org.kwan.domain.LoginVO;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


@Service
public class LoginServiceImpl implements LoginService {
	
	@Inject
	private LoginDAO dao;
	
	@Override
	public void regist(LoginVO login) throws Exception {
		dao.create(login);
	}
	
	@Override
	public LoginVO findByUserId(String userId) throws Exception {
		return (LoginVO) dao.findByUserId(userId);
	}
	
	@Override
	public boolean loginCheck(String userId, String password, Model model) throws Exception {
		LoginVO login = (LoginVO) dao.findByUserId(userId);
		
		if(login == null) {
			model.addAttribute("errorMessage", "존재하지 않는 아이디입니다.");
			return false;
		}
		if(!login.getPassword().equals(password)){
			model.addAttribute("errorMessage", "비밀번호가 틀립니다.");
			return false;
		}
		System.out.println("check success");
		return true;
	}

}
