package com.blog.service;

import java.util.List;

import com.blog.model.Post;
import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;

public interface PostService {
	
	//create
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	
	//update
	PostDto updatePost(PostDto postDto, Integer postId);
	
	//delete
	void deletePost(Integer postId);
	
	//getById
	PostDto getPostById(Integer postId);
    
	//get all
	PostResponse getall(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	//get post by user
	List<PostDto>getPostByUser(Integer postId);
	
	//get post by category
	List<PostDto>getPostByCategory(Integer categoryId);
	
	//serach post 
	List<PostDto>searchPosts(String keyword);
}
