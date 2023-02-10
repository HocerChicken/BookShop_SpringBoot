package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Book;
import com.example.demo.model.Category;
import com.example.demo.model.Publisher;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.PublisherRepository;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

@Controller
@RequestMapping(value = "/books")
public class BookController {

	public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\";

	@Autowired
	private BookRepository clothesRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private PublisherRepository publisherRepository;

	@RequestMapping("clothes-list")
	public String list() {

		return "books/clothes-list";
	}

	public Page<Book> listAll(int pageNum) {

		int pageSize = 10;

		Pageable pageable = PageRequest.of(pageNum - 1, pageSize);

		return clothesRepository.findAll(pageable);
	}

	@RequestMapping("/page/{pageNum}")
	public String viewPage(Model model,
			@PathVariable(name = "pageNum") int pageNum) {

		Page<Book> page = listAll(pageNum);

		List<Book> listProducts = page.getContent();

		System.out.print(listProducts);

		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("clothes", listProducts);

		return "books/list-page";
	}

	@RequestMapping("/")
	public String viewHomePage(Model model) {

		return viewPage(model, 1);
	}

	@ModelAttribute("clothes")
	public List<Book> getStudents() {

		return clothesRepository.findAll();
	}

	@ModelAttribute("categorys")
	public List<Category> getCategorys() {

		return categoryRepository.findAll();
	}

	@ModelAttribute("publishers")
	public List<Publisher> getPublishers() {

		return publisherRepository.findAll();
	}

	@GetMapping("detail")
	public String deltail(@RequestParam("clothesId") String clothesId, Model model) {

		System.out.println("Clothes id: " + clothesId);

		Book clothes = clothesRepository.getById(Integer.parseInt(clothesId));

		model.addAttribute("clothes", clothes);

		return "books/detail";
	}

	@RequestMapping("/new")
	public String newStudent(Model model) {

		model.addAttribute("clothes", new Book());
		return "books/new";
	}

	@RequestMapping("/saveOrUpdate")
	public String doSaveCustomer(@ModelAttribute Book clothes,
			Model model,
			@RequestParam("fileToUpload") MultipartFile file,
			@RequestParam("idCategory") int idCategory,
			@RequestParam("idPublisher") int idPublisher,
			RedirectAttributes attributes) {

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		Path path = Paths.get(UPLOAD_DIRECTORY + fileName);

		try {
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}

		clothes.setImage(fileName);

		Category category = categoryRepository.getById(idCategory);

		clothes.setCategory(category);

		Publisher publisher = publisherRepository.getById(idPublisher);

		clothes.setPublisher(publisher);

		System.out.print(clothes);
		clothesRepository.save(clothes);

		return "redirect:/books/";
	}

	@GetMapping("edit")
	public String edit(@RequestParam("clothesId") String id, Model model) {

		Book clothes = clothesRepository.getById(Integer.parseInt(id));

		model.addAttribute("clothes", clothes);

		return "books/edit";
	}

	@GetMapping("delete/{id}")
	public String delete(@PathVariable("id") Integer id, Model model) {

		clothesRepository.deleteById(id);

		return "redirect:/books/";
	}

	@PostMapping("/update")
	public String update(
			@ModelAttribute("Clothes") Book clothes,
			@RequestParam("idCategory") int idCategory,
			@RequestParam("idPublisher") int idPublisher,
			Model model) {

		Optional<Book> clothes2 = clothesRepository.findById(clothes.getId());

		clothes.setImage(clothes2.get().getImage());

		// set category and publisher

		Category category = categoryRepository.getById(idCategory);

		clothes.setCategory(category);

		Publisher publisher = publisherRepository.getById(idPublisher);

		clothes.setPublisher(publisher);

		clothesRepository.save(clothes);

		model.addAttribute("clothes", clothes);

		return "redirect:/books/";
	}

	@GetMapping("/home1")
	public String home() {

		return "home1";
	}

}
