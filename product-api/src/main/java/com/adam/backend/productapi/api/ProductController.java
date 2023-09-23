package com.adam.backend.productapi.api;

import com.adam.backend.productapi.dtos.ProductDTO;
import com.adam.backend.productapi.exceptions.ProductNotFoundException;
import com.adam.backend.productapi.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product")
    public List<ProductDTO> getProducts() {
        return productService.getAll();
    }

    @GetMapping("/product/category/{categoryId}")
    public List<ProductDTO> getProductByCategory(@PathVariable Long categoryId) {
        return productService.getProductByCategoryId(categoryId);
    }

    @GetMapping("/product/{productIdentifier}")
    ProductDTO findById(@PathVariable String productIdentifier) {
        return productService
                .findByProductIdentifier(productIdentifier);
    }
    @PostMapping("/product")
    ProductDTO newProduct(@Valid @RequestBody ProductDTO productDTO) {
        return productService.save(productDTO);
    }
    @DeleteMapping("/product/{id}")
    ProductDTO delete(@PathVariable Long id) throws ProductNotFoundException {
        return productService.delete(id);
    }
}

