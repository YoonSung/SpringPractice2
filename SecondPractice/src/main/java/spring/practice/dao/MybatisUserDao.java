package spring.practice.dao;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spring.practice.domain.User;

public class MybatisUserDao implements UserDao {

	private DataSource dataSource;
	private SqlSession sqlSession;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
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
