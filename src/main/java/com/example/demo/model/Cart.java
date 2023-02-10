package com.example.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
@Table(name = "cart")
public class Cart implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cid;

	@Column(name = "bookname")
	private String bname;

	@Column(name = "price")
	private int price;

	@Column(name = "quantity")
	private String num;

	@Column(name = "image")
	private String img;

	public Cart() {
		super();
	}

	@Override
	public String toString() {
		return "Cart [cid=" + cid + ", bname=" + bname + ", price=" + price + ", num=" + num + ", img=" + img + "]";
	}

}
