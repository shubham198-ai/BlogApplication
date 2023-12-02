package com.blog.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.exception.ResourceNotFoundException;
import com.blog.model.Category;
import com.blog.model.Post;
import com.blog.model.User;
import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import com.blog.repo.CategoryRepo;
import com.blog.repo.PostRepo;
import com.blog.repo.UserRepo;
import com.blog.service.PostService;
@Service
public class PostServiceImpl implements PostService {
@Autowired
private PostRepo postRepo;   
@Autowired
private ModelMapper modelMapper;
@Autowired
private UserRepo userRepo;
@Autowired
private CategoryRepo categoryRepo;

//create post
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		User user=this.userRepo.findById(userId).orElseThrow
				(()-> new ResourceNotFoundException("user with userid not found:"+userId));
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category with categoryId not found:"+categoryId));
		Post post=this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post newPost=this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
	}
//update post
	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow
		(()-> new ResourceNotFoundException("post with postid not found:"+postId));
		post.setAddedDate(postDto.getAddedDate());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		post.setTitle(postDto.getTitle());
		Post post1=this.postRepo.save(post);
		return this.modelMapper.map(post1, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post with postId not found:"+postId));
        this.postRepo.deleteById(postId);
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post with postId not found:"+postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user with userId not found:" +userId));
		List<Post>post=this.postRepo.findByUser(user);
		          List<PostDto>postDto=post.stream().map((pos)->this.modelMapper.map(pos, PostDto.class)).collect(Collectors.toList());
		return postDto;
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		Category cate=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category with categoryId not found:"+categoryId));
		   List<Post>post=this.postRepo.findByCategory(cate);
		  List<PostDto> postDto= post.stream().map((pos)->this.modelMapper.map(pos, PostDto.class)).collect(Collectors.toList());
		return postDto;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		  List<Post> post = this.postRepo.findByTitleContaining(keyword);
		  List<PostDto> postDto = post.stream().map((pos)->this.modelMapper.map(pos, PostDto.class)).collect(Collectors.toList());
		return postDto;
	}
	@Override
	public PostResponse getall(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
		Sort sort=(sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending());
		Pageable p=PageRequest.of(pageNumber, pageSize,sort);
		Page<Post>pagePost=this.postRepo.findAll(p);
		List<Post>allPost=pagePost.getContent();
		List<PostDto>postDt=allPost.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postDt);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(true);
		return postResponse;
	}

}
