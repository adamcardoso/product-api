package com.adam.backend.productapi.services;

import com.adam.backend.productapi.dtos.DTOConverter;
import com.adam.backend.productapi.dtos.ProductDTO;
import com.adam.backend.productapi.models.Product;
import com.adam.backend.productapi.repositories.ProductRepository;
import com.adam.backend.productapi.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> getAll() {
        List<Product> products = productRepository.findAll();
        return products
                .stream()
                .map(ProductDTO::convert)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductByCategoryId(
            Long categoryId) {
        List<Product> products =
                productRepository.getProductByCategory(categoryId);
        return products
                .stream()
                .map(ProductDTO::convert)
                .collect(Collectors.toList());
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
        Product product =
                productRepository.save(Product.convert(productDTO));
        return ProductDTO.convert(product);
    }
    public void delete(long ProductId)
            throws ProductNotFoundException {
        Optional<Product> Product =
                productRepository.findById(ProductId);
        Product.ifPresent(productRepository::delete);
        return null;
    }
}