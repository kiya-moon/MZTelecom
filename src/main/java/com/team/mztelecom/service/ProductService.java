package com.team.mztelecom.service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team.mztelecom.domain.IntmBas;
import com.team.mztelecom.repository.ProductRepository;

@Service
public class ProductService {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
	
	@Autowired
	ProductRepository productRepository;
	
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<IntmBas> getAllProducts() {
    	
    	logger.debug("product 서비스");
    	
        return productRepository.findAll();
      
    }

}
