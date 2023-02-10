package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Book;
import com.example.demo.model.Cart;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CartRepository;

@Controller
public class CartController {
	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private BookRepository bookRepository;

	@RequestMapping("/cart")
	public String index(Model model) {
		model.addAttribute("citems", cartRepository.findAll());
		return "cart";
	}

	@RequestMapping("/addcart/{id}")
	public String addCart(@PathVariable(name = "id") int id) {

		Book b = bookRepository.getById(id);

		Cart c = new Cart();

		c.setBname(b.getName());
		c.setImg(b.getImage());
		c.setNum("1");
		c.setPrice(b.getPrice());

		cartRepository.save(c);

		return "redirect:/cart";
	}

	@GetMapping("/editcart")
	public String editCart(@RequestParam(name = "mId") String id, @RequestParam(name = "mNum") int num) {

		System.out.println("id = " + id);
		System.out.println("num = " + num);

		Cart c = cartRepository.getById(Integer.parseInt(id));

		c.setNum("" + num);
		cartRepository.save(c);

		return "redirect:/cart";
	}

	@RequestMapping("/deletecart/{id}")
	public String deleteItem(@PathVariable(name = "id") int id) {

		cartRepository.deleteById(id);

		return "redirect:/cart";
	}
	
	@RequestMapping("/clearcart")
	public String clearCart(){
		cartRepository.deleteAll();
		return "redirect:/cart";
	}
}
