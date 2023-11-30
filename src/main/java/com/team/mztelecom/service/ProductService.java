package com.team.mztelecom.service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team.mztelecom.domain.IntmBas;
import com.team.mztelecom.domain.IntmImg;
import com.team.mztelecom.repository.ProductRepository;
import com.team.mztelecom.repository.ImgRepository;

@Service
public class ProductService {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
	
	@Autowired
	ProductRepository productRepository;
	
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    public List<IntmBas> getAllProductsWithImages() {
    	logger.debug("product 서비스"); 
    	
        return productRepository.findAllProductsWithImages();
    }
    
    public IntmBas getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElse(null);
    }
    

}
