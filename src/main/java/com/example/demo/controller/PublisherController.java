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

import com.example.demo.model.Publisher;
import com.example.demo.repository.PublisherRepository;

@Controller
@RequestMapping("publisher")
public class PublisherController {

	@Autowired
	private PublisherRepository publisherRepository;

	@GetMapping
	public String list(ModelMap model, @RequestParam Optional<String> message) {

		Iterable<Publisher> list = publisherRepository.findAll();

		if (message.isPresent()) {
			model.addAttribute("message", message.get());
		}

		model.addAttribute("publisher", list);

		return "publisher/list";
	}

	@GetMapping(value = { "newOrEdit", "newOrEdit/{id}" })
	public String newOrEdit(ModelMap model,
			@PathVariable(name = "id", required = false) Optional<Integer> id) {

		Publisher publisher;

		if (id.isPresent()) {
			Optional<Publisher> existedCate = publisherRepository.findById(id.get());

			publisher = existedCate.isPresent() ? existedCate.get() : new Publisher();
		} else {
			publisher = new Publisher();
		}

		model.addAttribute("publisher", publisher);

		return "publisher/newOrEdit";
	}

	@PostMapping("saveOrUpdate")
	public String saveOrUpdate(@Valid Publisher item, BindingResult result, ModelMap model,
			RedirectAttributes attributes) {

		if (result.hasErrors()) {
			model.addAttribute("publisher", item);

			return "publisher/newOrEdit";
		}

		publisherRepository.save(item);

		attributes.addAttribute("message", "New Publisher created");

		return "redirect:/publisher";
	}

	@GetMapping("paginate")
	public String paginate(ModelMap model, @RequestParam Optional<String> message,
			@PageableDefault(size = 5, sort = "id", direction = Direction.ASC) Pageable pageable) {

		Page<Publisher> pages = publisherRepository.findAll(pageable);

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

		return "publisher/paginate";
	}

	@GetMapping("delete/{id}")
	public String delete(RedirectAttributes attributes,
			@PathVariable("id") Integer id) {

		publisherRepository.deleteById(id);

		attributes.addAttribute("message", "The publisher is deleted");

		return "redirect:/publisher";
	}

}
