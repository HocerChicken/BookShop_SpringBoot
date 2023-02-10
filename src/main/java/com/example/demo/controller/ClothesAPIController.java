package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Book;
import com.example.demo.payroll.ClothesNotFoundException;
import com.example.demo.repository.BookRepository;

@RestController
@RequestMapping("/clothes/api")
public class ClothesAPIController {

	@Autowired
	private BookRepository clothesRepository;

	@GetMapping("list")
	public List<Book> list() {

		return clothesRepository.findAll();
	}

	@GetMapping("/list/{id}")
	public Book one(@PathVariable Integer id) {

		return clothesRepository.findById(id).orElseThrow(() -> new ClothesNotFoundException(id));
	}

	@GetMapping("/home1")
	public String home() {

		return "home1";
	}
}
