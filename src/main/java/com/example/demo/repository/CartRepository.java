package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Book;
import com.example.demo.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {

}
