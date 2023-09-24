package com.adam.backend.productapi.converter;

import com.adam.backend.productapi.dtos.CategoryDTO;
import com.adam.backend.productapi.dtos.ProductDTO;
import com.adam.backend.productapi.models.Category;
import com.adam.backend.productapi.models.Product;

public class DTOConverter {

    private DTOConverter() {
        // Private constructor to prevent instantiation
        throw new UnsupportedOperationException("Utility class - do not instantiate");
    }

    public static CategoryDTO convert(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setNome(category.getNome());
        return categoryDTO;
    }
    public static ProductDTO convert(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setNome(product.getNome());
        productDTO.setPreco(product.getPreco());
        productDTO.setProductIdentifier(product.getProductIdentifier());
        productDTO.setDescricao(product.getDescricao());
        if (product.getCategory() != null) {
            productDTO.setCategory(
                    DTOConverter.convert(product.getCategory()));
        }
        return productDTO;
    }
}
