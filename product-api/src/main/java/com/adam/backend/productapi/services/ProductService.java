package com.adam.backend.productapi.services;

import com.adam.backend.productapi.converter.DTOConverter;
import com.adam.backend.productapi.dtos.ProductDTO;
import com.adam.backend.productapi.exceptions.CategoryNotFoundException;
import com.adam.backend.productapi.exceptions.ProductNotFoundException;
import com.adam.backend.productapi.models.Product;
import com.adam.backend.productapi.repositories.CategoryRepository;
import com.adam.backend.productapi.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ProductDTO> getAll() {
        List<Product> products = productRepository.findAll();
        return products
                .stream()
                .map(ProductDTO::convert)
                .toList();
    }

    public List<ProductDTO> getProductByCategoryId(Long categoryId) {
        List<Product> products = productRepository.findByCategory_Id(categoryId);
        return products
                .stream()
                .map(ProductDTO::convert)
                .toList();
    }

    public ProductDTO findByProductIdentifier(
            String productIdentifier) {
        Product product = productRepository.findByProductIdentifier(productIdentifier);
        if (product != null) {
            return DTOConverter.convert(product);
        }
        return null;
    }

    public ProductDTO save(ProductDTO productDTO) {
        boolean existsCategory = categoryRepository.existsById(productDTO.getCategory().getId());
        if (!existsCategory) {
            throw new CategoryNotFoundException();
        }
        Product product = productRepository.save(Product.convert(productDTO));
        return DTOConverter.convert(product);
    }
    public ProductDTO delete(Long id) throws ProductNotFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        // Delete the product from the repository
        productRepository.delete(product);

        // Convert and return the deleted product as a DTO
        return ProductDTO.convert(product);
    }
}
