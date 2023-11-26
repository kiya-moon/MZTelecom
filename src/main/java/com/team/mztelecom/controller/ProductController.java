package com.team.mztelecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.team.mztelecom.domain.Product;
import com.team.mztelecom.service.ProductService;


@RestController
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	public ProductController(ProductService productService) {
        this.productService = productService;
    }
	
	@GetMapping("/search.do")
    public List<Product> searchProducts(@RequestParam("keyword") String keyword) {
        List<Product> products;
        if (keyword != null && !keyword.isEmpty()) {
            products = productService.searchProductsByName(keyword);
        } else {
            products = productService.getAllProducts();
        }
        return products;
    }
}
