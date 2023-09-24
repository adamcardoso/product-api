package com.adam.backend.productapi.services.impl;

import com.adam.backend.productapi.converter.DTOConverter;
import com.adam.backend.productapi.dtos.ProductDTO;
import com.adam.backend.productapi.exceptions.CategoryNotFoundException;
import com.adam.backend.productapi.exceptions.DatabaseException;
import com.adam.backend.productapi.exceptions.ProductNotFoundException;
import com.adam.backend.productapi.models.Product;
import com.adam.backend.productapi.repositories.CategoryRepository;
import com.adam.backend.productapi.repositories.ProductRepository;
import com.adam.backend.productapi.services.interfaces.ProductService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> getAll(Pageable pageable) {
        try{
            Page<Product> products = productRepository.findAll(pageable);

            return products.map(ProductDTO::convert);

        } catch (Exception e){
            throw new ProductNotFoundException("Erro ao buscar produtos no banco de dados!", e);
        }
    }

    @Override
    @Transactional
    public Optional<ProductDTO> getProductByCategoryId(Long categoryId) {
        try {
            Optional<Product> products = productRepository.findByCategoryId(categoryId);

            return products.map(ProductDTO::convert);
        }catch (Exception e){
            throw new ProductNotFoundException("Erro ao buscar produtos no banco de dados!", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductDTO> findByProductIdentifier(String productIdentifier) {
        Product product = productRepository.findByProductIdentifier(productIdentifier);
        if (Objects.nonNull(product)) {
            return Optional.of(DTOConverter.convert(product));
        }
        return Optional.empty();
    }

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        try {
            boolean existsCategory = categoryRepository.existsById(productDTO.getCategory().getId());
            if (!existsCategory) {
                throw new CategoryNotFoundException();
            }
            Product product = productRepository.save(Product.convert(productDTO));
            return DTOConverter.convert(product);
        }catch (Exception e) {
            throw new DatabaseException("Erro ao inserir pessoa no banco de dados", e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            productRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e) {
            throw new ProductNotFoundException("Produto com a id " + id + "não encontrado", e);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Violação de integridade no banco de dados", e);
        } catch (Exception e) {
            throw new DatabaseException("Erro ao excluir pessoa do banco de dados", e);
        }
    }
}
