package spring.practice.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import spring.practice.domain.User;

public class UserDao extends JdbcDaoSupport {
	
	private static final Logger log = LoggerFactory.getLogger(UserDao.class);
	
	@PostConstruct
	public void init() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator(new ClassPathResource("user.sql"));
		DatabasePopulatorUtils.execute(populator, getDataSource());
		log.info("Database Initialize Success!!");
	}

	public User findById(String userId) {
		String sql = "SELECT * FROM tbl_user WHERE userId = ?";
		
		RowMapper<User> rowMapper = new RowMapper<User>() {

			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				if (rowNum == 0)
					return null;
				
				return new User(
						rs.getString("userId"),
						rs.getString("password"),
						rs.getString("name"),
						rs.getString("email")
				);
			}
		};
		
		return getJdbcTemplate().queryForObject(sql, rowMapper, userId);
	}

	public int create(User user) {
		String sql = "INSERT INTO tbl_user(userId, password, name, email) VALUES (?, ?, ?, ?)";
		return getJdbcTemplate().update(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
	}
}
