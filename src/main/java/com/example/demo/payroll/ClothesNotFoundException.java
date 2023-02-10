package com.example.demo.payroll;

public class ClothesNotFoundException extends RuntimeException {

	public ClothesNotFoundException(Integer id) {

		super("Could not find Book " + id);
	}

}
