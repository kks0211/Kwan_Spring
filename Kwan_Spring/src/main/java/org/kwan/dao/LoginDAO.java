package org.kwan.dao;

import org.kwan.domain.LoginVO;

public interface LoginDAO {
	
	public void create(LoginVO login) throws Exception;
	public Object findByUserId(String userId) throws Exception;

}
