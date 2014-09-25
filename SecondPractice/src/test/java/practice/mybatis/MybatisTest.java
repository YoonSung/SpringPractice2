package practice.mybatis;

import java.io.IOException;
import java.io.InputStream;

import javax.sql.DataSource;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spring.practice.domain.User;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class MybatisTest {
	
	private static SqlSession session;
	private static final Logger log = LoggerFactory.getLogger(MybatisTest.class);
	
	User testUser = new User("testUserId2", "testPassword", "testName", "testEmail@naver.com");
	
	@BeforeClass
	public static void init() throws IOException {
		String resource = "mybatis-config-test.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		session = sqlSessionFactory.openSession();
	}
	
	@AfterClass
	public static void destroy() {
		session.close();
	}
	
	@Test
	public void findById() throws Exception {
		User user = session.selectOne("UserMap.findById", "testUserId");
		log.debug("findById : {}", user);
		assertNotNull(user);
		
	}
	
	@Test
	public void create() throws Exception {
		int affectedRow = session.insert("UserMap.create", testUser);
		assertThat(affectedRow, is(1));
		
		User selectedUser = session.selectOne("UserMap.findById", testUser.getUserId());
		
		assertEquals(testUser, selectedUser);
	}
	
	@Test
	public void update() throws Exception {
		
		User user = new User("testUserId", "새로운 비밀번호", "새로운 이름", "estrella@nhnnext.org");
		
		int affectedRow = session.update("UserMap.update", user);
		assertThat(affectedRow, is(1));
		
		User selectedUser = session.selectOne("UserMap.findById", "testUserId");
		
		assertEquals(user, selectedUser);
	}
}
