package com.adam.backend.productapi.services.interfaces;

import com.adam.backend.productapi.dtos.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {
    Page<ProductDTO> getAll(Pageable pageable);

    Optional<ProductDTO> getProductByCategoryId(Long categoryId);

    Optional<ProductDTO> findByProductIdentifier(String productIdentifier);

    ProductDTO save(ProductDTO productDTO);

    void delete(Long id);
}
