package spring.practice.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import spring.practice.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContext.xml")
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@Transactional
public class MybatisUserDaoTest {

	@Autowired
	UserDao userDao;
	
	@Test
	public void init() {
		User user = userDao.findById("testUserId");
		assertThat("testUserId", is(user.getUserId()));
		assertThat("testPassword", is(user.getPassword()));
		assertThat("testName", is(user.getName()));
		assertThat("testEmail@naver.com", is(user.getEmail()));
	}
	
	@Test
	public void create() throws Exception {
		String userId = "Yoonsung";
		
		User user = new User(userId, "pass", "JungYoonSung", "estrella@nhnnext.org");
		int affectedRowNum = userDao.create(user);
		assertThat(affectedRowNum, is(affectedRowNum));
		
		User selectedUser = userDao.findById(userId);
		assertThat(user, is(selectedUser));
	}
}