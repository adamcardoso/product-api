package com.adam.backend.productapi.api.interfaces;

import com.adam.backend.productapi.dtos.ProductDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserController {
    ResponseEntity<Page<ProductDTO>> getProducts(Pageable pageable);

    ResponseEntity<ProductDTO> getProductByCategory(@PathVariable("categoryId") Long categoryId);

    ResponseEntity<ProductDTO> findById(@PathVariable String productIdentifier);

    ResponseEntity<ProductDTO> newProduct(@Valid @RequestBody ProductDTO productDTO);

    ResponseEntity<Void> delete(@PathVariable Long id);
}
