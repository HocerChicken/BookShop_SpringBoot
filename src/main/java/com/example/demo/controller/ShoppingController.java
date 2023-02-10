package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Book;
import com.example.demo.model.Cart;
import com.example.demo.model.Category;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.BookService;

@Controller
@RequestMapping("/users")
public class ShoppingController {

	@Autowired
	private BookRepository clothesRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CartRepository cartRepository;
	
	@ModelAttribute("categorys")
	public List<Category> getCategorys() {

		return categoryRepository.findAll();
	}

	public Page<Book> listAll(int pageNum) {

		int pageSize = 12;

		Pageable pageable = PageRequest.of(pageNum - 1, pageSize);

		return clothesRepository.findAll(pageable);
	}

	@RequestMapping("/shopping/{pageNum}")
	public String viewPage(Model model,
			@PathVariable(name = "pageNum") int pageNum) {

		Page<Book> page = listAll(pageNum);

		List<Book> listProducts = page.getContent();

		System.out.print(listProducts);

		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("clothes", listProducts);
		int num = 0;
		if(!cartRepository.findAll().isEmpty()) {
			num = cartRepository.findAll().size();
		}
		model.addAttribute("numItemCart", String.valueOf(num));

		return "users/shopping";
	}

	@RequestMapping("/")
	public String viewHomePage(Model model) {

		return viewPage(model, 1);
	}

	@RequestMapping("/shopping-search")
	public String viewHomeSearch(@RequestParam("categoryId") int categoryId, Model model) {

		return viewPageSearch(model, 1, categoryId);
	}

	public Page<Book> listAllByCategory(int pageNum, int categoryId) {

		int pageSize = 12;

		Pageable pageable = PageRequest.of(pageNum - 1, pageSize);

		return clothesRepository.findByCategoryId(pageable, categoryId);
	}

	public String viewPageSearch(Model model,
			@PathVariable(name = "pageNum") int pageNum,
			int categoryId) {

		Page<Book> page = listAllByCategory(pageNum, categoryId);

		List<Book> listProducts = page.getContent();

		System.out.print(listProducts);

		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("clothes", listProducts);

		return "users/shopping";
	}

	@RequestMapping("/homepage-login")
	public String homepage(Model model) {
		int num = 0;
		if(!cartRepository.findAll().isEmpty()) {
			num = cartRepository.findAll().size();
		}
		model.addAttribute("numItemCart", String.valueOf(num));
		return "users/home";
	}
	
	@RequestMapping("/homepage")
	public String homepage_login(Model model) {
		int num = 0;
		if(!cartRepository.findAll().isEmpty()) {
			num = cartRepository.findAll().size();
		}
		model.addAttribute("numItemCart", String.valueOf(num));
		return "users/home-login";
	}

	@RequestMapping("/header")
	public String header() {

		return "users/header";
	}

	@ModelAttribute("clothes")
	public List<Book> getStudents() {

		return clothesRepository.findAll();
	}

	@Autowired
	private BookService service;

	@RequestMapping("/shopping/search")
	public String viewHomePage(Model model, @Param("keyword") String keyword) {

		List<Book> listProducts = service.listAll(keyword);

		model.addAttribute("clothes", listProducts);

		System.out.println(keyword);

		return "users/shopping";
	}

}
