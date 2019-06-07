package com.xiaowei.spring.boot.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.xiaowei.spring.boot.blog.domain.User;
import com.xiaowei.spring.boot.blog.domain.es.EsBlog;
import com.xiaowei.spring.boot.blog.service.EsBlogService;
import com.xiaowei.spring.boot.blog.vo.TagVO;

/**
 * Blog 控制器
 * 
 * @author yuexiaowei
 *
 */
@Controller
@RequestMapping("/blogs")
public class BlogController {
	@Autowired
	private EsBlogService esBlogService;

	@GetMapping
	public String listEsBlogs(@RequestParam(value = "order", required = false, defaultValue = "new") String order,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			@RequestParam(value = "async", required = false) boolean async,
			@RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize, Model model) {

		Page<EsBlog> page = null;
		List<EsBlog> list = null;
		boolean isEmpty = true; // 系统初始化时，没有博客数据
		try {
			if (order.equals("hot")) { // 最热查询
				Sort sort = new Sort(Direction.DESC, "readSize", "commentSize", "voteSize", "createTime");
				Pageable pageable = new PageRequest(pageIndex, pageSize, sort);
				page = esBlogService.listHotestEsBlogs(keyword, pageable);
			} else if (order.equals("new")) { // 最新查询
				Sort sort = new Sort(Direction.DESC, "createTime");
				Pageable pageable = new PageRequest(pageIndex, pageSize, sort);
				page = esBlogService.listNewestEsBlogs(keyword, pageable);
			}

			isEmpty = false;
			/*
			这个异常的作用是什么？
			就是说有可能在系统初始化的时候，通过page = esBlogService.listHotestEsBlogs(keyword, pageable);这个方法是找不到
			这个对象的，因为ES里面是空的嘛，这个时候可能会有一些异常，不管这个异常是什么，我们的page不能为空，因为要绑定到页面里面去的，
			所以这个page对象一定是要有返回的，所以加了个异常来处理，让我们页面正常显示
			 */
		} catch (Exception e) {
			Pageable pageable = new PageRequest(pageIndex, pageSize);
			page = esBlogService.listEsBlogs(pageable);
		}

		list = page.getContent(); // 当前所在页面数据列表

		model.addAttribute("order", order);
		model.addAttribute("keyword", keyword);
		model.addAttribute("page", page);
		model.addAttribute("blogList", list);


		/*
		首次访问页面才加载，什么意思呢？
因为这些信息在博客的右侧，但是博客的主页是有分页的，当我们点击第二页的时候
她肯定还会调用这个方法，这时候就没必要刷新右侧的这些数据了，因为这些信息基本不会变
这样的好处就是不必重复加载这些数据
		 */
		if (!async && !isEmpty) {
			List<EsBlog> newest = esBlogService.listTop5NewestEsBlogs();
			model.addAttribute("newest", newest);
			List<EsBlog> hotest = esBlogService.listTop5HotestEsBlogs();
			model.addAttribute("hotest", hotest);
//			List<TagVO> tags = esBlogService.listTop30Tags();
//			model.addAttribute("tags", tags);
			List<TagVO> tags = esBlogService.listTop20Tags();
			model.addAttribute("tags", tags);
			List<User> users = esBlogService.listTop12Users();
			model.addAttribute("users", users);
		}

		//return (async == true ? "/index :: #mainContainerRepleace" : "/index");
		return (async == true ? "index :: #mainContainerRepleace" : "index");
	}
	@GetMapping("/newest")
	public String listNewestEsBlogs(Model model) {
		List<EsBlog> newest = esBlogService.listTop5NewestEsBlogs();
		model.addAttribute("newest", newest);
		return "newest";
	}

	@GetMapping("/hotest")
	public String listHotestEsBlogs(Model model) {
		List<EsBlog> hotest = esBlogService.listTop5HotestEsBlogs();
		model.addAttribute("hotest", hotest);
		return "hotest";
	}
}
