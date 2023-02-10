package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Book;
import com.example.demo.model.User;

@Repository

public interface BookRepository extends JpaRepository<Book, Integer> {

	Page<Book> findByCategoryId(Pageable pageable, int categoryId);

	@Query("SELECT b FROM Book b WHERE CONCAT(b.name, ' ', b.AuthorName) LIKE %?1%")
	public List<Book> search(String keyword);

}
