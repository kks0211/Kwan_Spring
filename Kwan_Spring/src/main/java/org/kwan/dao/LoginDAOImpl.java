package org.kwan.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.kwan.domain.LoginVO;
import org.springframework.stereotype.Repository;

@Repository
public class LoginDAOImpl implements LoginDAO {

	@Inject
	private SqlSession session;

	private static String namespace = "org.kwan.mapper.LoginMapper";

	@Override
	public void create(LoginVO login) throws Exception {
		session.insert(namespace + ".create", login);
	}

	@Override
	public Object findByUserId(String userId) throws Exception {
		return session.selectOne(namespace + ".read", userId);

	}

}
