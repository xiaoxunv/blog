package com.xiaowei.spring.boot.blog.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.xiaowei.spring.boot.blog.domain.Blog;
import com.xiaowei.spring.boot.blog.domain.Catalog;
import com.xiaowei.spring.boot.blog.domain.Comment;
import com.xiaowei.spring.boot.blog.domain.User;
import com.xiaowei.spring.boot.blog.domain.Vote;
import com.xiaowei.spring.boot.blog.domain.es.EsBlog;
import com.xiaowei.spring.boot.blog.repository.BlogRepository;

/**
 * Blog 服务.
 * 
 * @since 1.0.0 2017年4月7日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
@Service
public class BlogServiceImpl implements BlogService {

	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private EsBlogService esBlogService;

	
	@Transactional
	@Override
	public Blog saveBlog(Blog blog) {
		/*
		 * 除了把博客存储到关系数据库中，还要存储到es的nosql数据库中
		 */
		boolean isNew = (blog.getId() == null);
		EsBlog esBlog = null;
		Blog returnBlog = blogRepository.save(blog);
		/*
		 * 如果是新增，就把blog对象作为参数传给EsBlog进行实例化
		 * 否则的话，就是更新，首先要把EsBlog给找出来，因为EsBlog它里面其实他自己带了一个id的，本身也是有主见的
		 * 把原有的一些信息给找出来，
		 */
		if (isNew) {
			
			esBlog = new EsBlog(returnBlog);
		} else {
			
			esBlog = esBlogService.getEsBlogByBlogId(blog.getId());
			esBlog.update(returnBlog);
		}
		//最后把EsBlog进行保存
		esBlogService.updateEsBlog(esBlog);
		return returnBlog;
	}

	
	@Transactional
	@Override
	public void removeBlog(Long id) {
		blogRepository.delete(id);
		EsBlog esblog = esBlogService.getEsBlogByBlogId(id);
		esBlogService.removeEsBlog(esblog.getId());
	}


	@Transactional
	@Override
	public Blog updateBlog(Blog blog) {
		return blogRepository.save(blog);
	}
	@Override
	public Blog getBlogById(Long id) {
		return blogRepository.findOne(id);
	}

	@Override
	public Page<Blog> listBlogsByTitleLike(User user, String title, Pageable pageable) {
		// 模糊查询
		title = "%" + title + "%";
		Page<Blog> blogs = blogRepository.findByUserAndTitleLikeOrderByCreateTimeDesc(user, title, pageable);
		return blogs;
	}

	@Override
	public Page<Blog> listBlogsByTitleLikeAndSort(User user, String title, Pageable pageable) {
		// 模糊查询
		title = "%" + title + "%";
		Page<Blog> blogs = blogRepository.findByUserAndTitleLike(user, title, pageable);
		return blogs;
	}

	@Override
	public void readingIncrease(Long id) {
		Blog blog = blogRepository.findOne(id);
		blog.setReadSize(blog.getCommentSize()+1);
		//blog.setReading(blog.getReading()+1);
		blogRepository.save(blog);
	}
 
	@Override
	public Blog createComment(Long blogId, String commentContent) {
		Blog originalBlog = blogRepository.findOne(blogId);
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		Comment comment = new Comment(user, commentContent);
		originalBlog.addComment(comment);
		return blogRepository.save(originalBlog);
	}

	@Override
	public void removeComment(Long blogId, Long commentId) {
		Blog originalBlog = blogRepository.findOne(blogId);
		originalBlog.removeComment(commentId);
		blogRepository.save(originalBlog);
	}
	
	@Override
	public Blog createVote(Long blogId) {
		Blog originalBlog = blogRepository.findOne(blogId);
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		Vote vote = new Vote(user);
		boolean isExist = originalBlog.addVote(vote);
		if (isExist) {
			throw new IllegalArgumentException("该用户已经点过赞了");
		}
		return blogRepository.save(originalBlog);
	}

	@Override
	public void removeVote(Long blogId, Long voteId) {
		Blog originalBlog = blogRepository.findOne(blogId);
		originalBlog.removeVote(voteId);
		blogRepository.save(originalBlog);
	}
	
	@Override
	public Page<Blog> listBlogsByCatalog(Catalog catalog, Pageable pageable) {
		Page<Blog> blogs = blogRepository.findByCatalog(catalog, pageable);
		return blogs;
	}

}
