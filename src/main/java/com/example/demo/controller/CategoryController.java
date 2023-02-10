package com.example.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;

@Controller
@RequestMapping("category")
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;

	@GetMapping
	public String list(ModelMap model, @RequestParam Optional<String> message) {

		Iterable<Category> list = categoryRepository.findAll();

		if (message.isPresent()) {
			model.addAttribute("message", message.get());
		}

		model.addAttribute("category", list);

		return "category/list";
	}

	@GetMapping(value = { "newOrEdit", "newOrEdit/{id}" })
	public String newOrEdit(ModelMap model,
			@PathVariable(name = "id", required = false) Optional<Integer> id) {

		Category category;

		if (id.isPresent()) {
			Optional<Category> existedCate = categoryRepository.findById(id.get());

			category = existedCate.isPresent() ? existedCate.get() : new Category();
		} else {
			category = new Category();
		}

		model.addAttribute("category", category);

		return "category/newOrEdit";
	}

	@PostMapping("saveOrUpdate")
	public String saveOrUpdate(@Valid Category item, BindingResult result, ModelMap model,
			RedirectAttributes attributes) {

		if (result.hasErrors()) {
			model.addAttribute("category", item);

			return "category/newOrEdit";
		}

		categoryRepository.save(item);

		attributes.addAttribute("message", "New Category created");

		return "redirect:/category";
	}

	@GetMapping("paginate")
	public String paginate(ModelMap model, @RequestParam Optional<String> message,
			@PageableDefault(size = 5, sort = "id", direction = Direction.ASC) Pageable pageable) {

		Page<Category> pages = categoryRepository.findAll(pageable);

		if (message.isPresent()) {
			model.addAttribute("message", message.get());
		}

		List<Sort.Order> sortOrders = pages.getSort().stream().collect(Collectors.toList());

		if (sortOrders.size() > 0) {

			Sort.Order order = sortOrders.get(0);

			model.addAttribute("sort",
					order.getProperty() + "," + (order.getDirection() == Sort.Direction.ASC ? "asc" : "desc"));
		} else {
			model.addAttribute("sort", "name");
		}

		model.addAttribute("pages", pages);

		return "category/paginate";
	}

	@GetMapping("delete/{id}")
	public String delete(RedirectAttributes attributes,
			@PathVariable("id") Integer id) {

		categoryRepository.deleteById(id);

		attributes.addAttribute("message", "The category is deleted");

		return "redirect:/category";
	}

}
