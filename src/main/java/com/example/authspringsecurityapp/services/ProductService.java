package com.example.authspringsecurityapp.services;

import com.example.authspringsecurityapp.domain.product.Product;
import com.example.authspringsecurityapp.domain.product.ProductRequestDTO;
import com.example.authspringsecurityapp.domain.product.ProductResponseDTO;
import com.example.authspringsecurityapp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService { // a service retorna uma lista de ProductResponseDTO ou um ProductResponseDTO em vez de retornar uma lista de entidade ou uma entidade para a controller, separando as responsabilidades
    @Autowired
    private ProductRepository productRepository;

    public List<ProductResponseDTO> getAllProducts() {
        
        // O que faz? Pega uma lista de Product e transforma numa lista de ProductDTO
          List<ProductResponseDTO> productResponseDTOList =
                  this.productRepository.findAll() // 1 - this.productRepository.findAll() => pega uma lista de product da repository
                          .stream()  // 2 - .stream() => transforma a lista de produto em um tipo de colecao stream(fluxo)
                          .map(ProductResponseDTO::new) // 3 - .map => mapeia cada produto para um produtoDto, cria um produtoDto baseado nos atributos de produto e usando o construtor personalizado de produtoDto que permite criar dto a partir de um produto
                          .toList(); // coleta os resultados do map em uma lista de produtosDto
          return productResponseDTOList;
    }

    public ProductResponseDTO postProduct(ProductRequestDTO productRequestDTO) {
        Product newProduct = new Product(productRequestDTO);
        ProductResponseDTO productResponseDTO = new ProductResponseDTO(productRepository.save(newProduct));

        return productResponseDTO;
    }
}
