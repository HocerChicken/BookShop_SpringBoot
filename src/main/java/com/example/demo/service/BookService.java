package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.BookRepository;
import com.example.demo.model.Book;

@Service
public class BookService {

    @Autowired
    private BookRepository repo;

    public List<Book> listAll(String keyword) {

        if (keyword != null) {
            return repo.search(keyword);
        }

        return repo.findAll();
    }

}
