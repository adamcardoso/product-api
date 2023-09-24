package com.adam.backend.productapi.api.impl;

import com.adam.backend.productapi.dtos.ProductDTO;
import com.adam.backend.productapi.exceptions.ProductNotFoundException;
import com.adam.backend.productapi.services.interfaces.ProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductControllerImpl {

    private static final Logger logger = LoggerFactory.getLogger(ProductControllerImpl.class);

    private final ProductService productService;

    public ProductControllerImpl(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product")
    public ResponseEntity<Page<ProductDTO>> getProducts(Pageable pageable) {
        Page<ProductDTO> page = productService.getAll(pageable);

        logger.info("Listando todos os produtos");

        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/product/category/{categoryId}")
    public ResponseEntity<ProductDTO> getProductByCategory(@PathVariable("categoryId") Long categoryId) {
        Optional<ProductDTO> productDTO = productService.getProductByCategoryId(categoryId);
        if (productDTO.isPresent()) {
            logger.info("Encontrado produto com ID: {}", categoryId);
            return ResponseEntity.ok(productDTO.get());
        } else {
            logger.error("Produto com ID {} não encontrado", categoryId);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/product/{productIdentifier}")
    public ResponseEntity<ProductDTO> findById(@PathVariable String productIdentifier) {
        Optional<ProductDTO> productDTO = productService.findByProductIdentifier(productIdentifier);

        if (productDTO.isPresent()) {
            logger.info("Encontrado identificador com ID: {}", productIdentifier);
            return ResponseEntity.ok(productDTO.get());
        } else {
            logger.error("Identificador com ID {} não encontrado", productIdentifier);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/product")
    public ResponseEntity<ProductDTO> newProduct(@Valid @RequestBody ProductDTO productDTO) {
        productDTO = productService.save(productDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(productDTO.getId()).toUri();

        logger.info("Inserindo um novo produto");
        return ResponseEntity.created(uri).body(productDTO);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);

        logger.info("Deletando um produto com ID: {}", id);

        return ResponseEntity.noContent().build();
    }
}

