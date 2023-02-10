package com.example.demo.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name = "book")
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "price")
	private int price;

	@Column(name = "description")
	private String description;

	@Column(name = "image")
	private String image;

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "authorname")
	private String AuthorName;

	@Column(name = "publishtime")
	private Date publishTime;

	@ManyToOne
	@JoinColumn(name = "categoryid", nullable = false)
	private Category category;

	@ManyToOne
	@JoinColumn(name = "publisherid", nullable = false)
	private Publisher publisher;

	public Book() {
		super();
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", price=" + price + ", description=" + description + ", image="
				+ image + ", quantity=" + quantity + ", AuthorName=" + AuthorName + "]";
	}

}
