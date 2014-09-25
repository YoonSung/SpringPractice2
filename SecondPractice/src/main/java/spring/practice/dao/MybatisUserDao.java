package spring.practice.dao;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import spring.practice.domain.User;

public class MybatisUserDao implements UserDao {

	private static final Logger log = LoggerFactory.getLogger(MybatisUserDao.class);
	private DataSource dataSource;
	private SqlSession sqlSession;
	
	@PostConstruct
	public void init() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("user.sql"));
		
		DatabasePopulatorUtils.execute(populator, getDataSource());
		log.info("Database Initialize Success!!");
	}
	
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
