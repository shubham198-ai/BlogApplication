package com.blog.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.model.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
