package com.example.demo.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "category")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(columnDefinition = "nvarchar(50) not null")
	@NotEmpty(message = "Name must be entered")
	private String name;

	@OneToMany(mappedBy = "category")
	private Set<Book> books = new HashSet<>();

	public Category() {

		super();
	}

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public Set<Book> getBooks() {

		return books;
	}

	public void setBooks(Set<Book> books) {

		this.books = books;
	}

	@Override
	public String toString() {

		return "Category [id=" + id + ", name=" + name + ", books=" + books + "]";
	}

}
