package com.blog.service;

import java.util.List;

import com.blog.payload.UserDto;

public interface UserService {

	UserDto createUser(UserDto userDto);
	UserDto updateUser(UserDto userDto, Integer userid);
	List<UserDto> getAllUser();
	UserDto getUserById(Integer userid);
	void  deleteUser(Integer userid);
}
