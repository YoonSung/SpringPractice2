package spring.practice.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import spring.practice.domain.User;

@Repository
public class MybatisUserDao implements UserDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public User findById(String userId) {
		return sqlSession.selectOne("UserMap.findById", userId);
	}

	@Override
	public int create(User user) {
		return sqlSession.insert("UserMap.create", user);
	}

	@Override
	public int update(User user) {
		return sqlSession.update("UserMap.update", user);
	}

}
