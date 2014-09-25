package practice.mybatis;

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
import org.junit.Test;

import spring.practice.domain.User;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class MybatisTest {
	@Test
	public void findById() throws Exception {

		String resource = "mybatis-config-test.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

		SqlSession session = sqlSessionFactory.openSession();
		User user = session.selectOne("UserMap.findById", "testUserId");
		
		System.out.println(user.toString());
		assertNotNull(user);
		
		session.close();
	}
}
