package spring.practice.dao;

import spring.practice.domain.User;

public interface UserDao {

	public abstract User findById(String userId);

	public abstract int create(User user);

	public abstract int update(User user);

}