package com.blog.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.exception.ResourceNotFoundException;
import com.blog.model.User;
import com.blog.payload.UserDto;
import com.blog.repo.UserRepo;
import com.blog.service.UserService;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userDto) {
		User user=this.dtoToUser(userDto);
		User savedUser=this.userRepo.save(user);
		return this.userTodto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userid) {
		User user=this.userRepo.findById(userid).orElseThrow(()->new ResourceNotFoundException("user with userid not found:"+userid));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User updatedUser=this.userRepo.save(user);
		UserDto userDto1=this.userTodto(updatedUser);
		
		return userDto1;
	}

	@Override
	public List<UserDto> getAllUser() {
		List<User> users=this.userRepo.findAll();
		List<UserDto>userDto=users.stream().map(user->this.userTodto(user))
				.collect(Collectors.toList());
		return userDto;
		
	}

	@Override
	public UserDto getUserById(Integer userid) {
		User user=this.userRepo.findById(userid).orElseThrow
				(()->new ResourceNotFoundException("user with userid not found:"+userid));
UserDto userDto=this.userTodto(user);
		return userDto;
	}

	@Override
	public void deleteUser(Integer userid) {
		User user=this.userRepo.findById(userid).orElseThrow
				(()->new ResourceNotFoundException("user with userid not found:"+userid));
		this.userRepo.delete(user);
		
	}
	public User dtoToUser(UserDto userDto) {
		User user=this.modelMapper.map(userDto, User.class);
		return user;
	}
	public UserDto userTodto(User user) {
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
		return userDto;
	}

}
