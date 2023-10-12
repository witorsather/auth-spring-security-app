package com.example.authspringsecurityapp.controllers;

import com.example.authspringsecurityapp.domain.product.Product;
import com.example.authspringsecurityapp.domain.product.ProductRequestDTO;
import com.example.authspringsecurityapp.domain.product.ProductResponseDTO;
import com.example.authspringsecurityapp.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity getAllProducts() {
        List<ProductResponseDTO> productResponseDTOList = productService.getAllProducts();

        return ResponseEntity.ok(productResponseDTOList);
    }

    // eu poderia retornar apenas ResponseEntity.ok.build() uma instancia da entidade de resposta apenas com o status 200, mas o requisito dessa API pede para retornar as informacoes para o cliente o que inclui o id que ele nao tem
    @PostMapping
    public ResponseEntity postProduct(@RequestBody @Valid ProductRequestDTO body) { // o cliente precisa mandar o json no formato do dto, diferença entre o dto e a entidade e o id, ja que e post o id fica por conta do banco gerar
        ProductResponseDTO productResponseDTO = productService.postProduct(body);
        
        return ResponseEntity.ok(productResponseDTO);
    }
}
