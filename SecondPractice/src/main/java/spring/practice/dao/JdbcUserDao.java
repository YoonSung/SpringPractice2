package spring.practice.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import spring.practice.domain.User;

public class JdbcUserDao extends JdbcDaoSupport implements UserDao {
	
	private static final Logger log = LoggerFactory.getLogger(JdbcUserDao.class);
	
	@PostConstruct
	public void init() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("user.sql"));
		
		DatabasePopulatorUtils.execute(populator, getDataSource());
		log.info("Database Initialize Success!!");
	}

	/* (non-Javadoc)
	 * @see spring.practice.dao.IUserDao#findById(java.lang.String)
	 */
	@Override
	public User findById(String userId) {
		String sql = "SELECT * FROM tbl_user WHERE userId = ?";
		
		RowMapper<User> rowMapper = new RowMapper<User>() {

			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new User(
						rs.getString("userId"),
						rs.getString("password"),
						rs.getString("name"),
						rs.getString("email")
						);
			}
		};
		
		
		try {
			return getJdbcTemplate().queryForObject(sql, rowMapper, userId);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see spring.practice.dao.IUserDao#create(spring.practice.domain.User)
	 */
	@Override
	public int create(User user) {
		String sql = "INSERT INTO tbl_user(userId, password, name, email) VALUES (?, ?, ?, ?)";
		return getJdbcTemplate().update(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
	}

	/* (non-Javadoc)
	 * @see spring.practice.dao.IUserDao#update(spring.practice.domain.User)
	 */
	@Override
	public int update(User user) {
		String sql = "UPDATE tbl_user SET password = ?, name = ?, email = ? WHERE userId = ?";
		return getJdbcTemplate().update(sql, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
	}
}
