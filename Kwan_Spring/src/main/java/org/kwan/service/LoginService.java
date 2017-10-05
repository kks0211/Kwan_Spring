package org.kwan.service;

import org.kwan.domain.LoginVO;
import org.springframework.ui.Model;

public interface LoginService {
	
	public void regist(LoginVO login) throws Exception;
	public LoginVO findByUserId(String userId) throws Exception;
	public boolean loginCheck(String userId, String password, Model model) throws Exception;
}
