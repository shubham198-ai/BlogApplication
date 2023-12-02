package com.blog.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer postId;
@Column(length = 1000)
	private String content;
	private String title;
	private String imageName;
	private Date addedDate;
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	@ManyToOne
	private User user;
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "post")
	private List<Comment>comment=new ArrayList<>();
	
	}
	

