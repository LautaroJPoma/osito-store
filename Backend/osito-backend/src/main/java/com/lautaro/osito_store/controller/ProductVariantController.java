package com.lautaro.osito_store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lautaro.osito_store.dto.ProductVariantDTO;
import com.lautaro.osito_store.service.ProductVariantService;

@RestController
@RequestMapping("/api/variants")
public class ProductVariantController {

    @Autowired
    private ProductVariantService productVariantService;


    @GetMapping
    public ResponseEntity<List<ProductVariantDTO>> getAllVariants() {
        List<ProductVariantDTO> variants = productVariantService.getAllProductVariants();
        return ResponseEntity.ok(variants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductVariantDTO> getVariantById(@PathVariable Long id) {
        if (!productVariantService.existsById(id)) {
            throw new RuntimeException("Variante con ID " + id + " no encontrada");
        }
        ProductVariantDTO variant = productVariantService.getProductVariant(id);
        return ResponseEntity.ok(variant);
    }

    @PostMapping
    public ResponseEntity<ProductVariantDTO> createVariant(@RequestBody ProductVariantDTO dto) {
        if (dto.getColor() == null || dto.getColor().isEmpty()) {
            throw new RuntimeException("El color no puede estar vacío");
        }
        if (dto.getSize() == null || dto.getSize().isEmpty()) {
            throw new RuntimeException("El tamaño no puede estar vacío");
        }

        ProductVariantDTO newVariant = productVariantService.createProductVariant(dto);
        return new ResponseEntity<>(newVariant, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductVariantDTO> updateVariant(@PathVariable Long id, @RequestBody ProductVariantDTO dto) {
        if (!productVariantService.existsById(id)) {
            throw new RuntimeException("Variante con ID " + id + " no encontrada");
        }

        ProductVariantDTO updated = productVariantService.updateProductVariant(dto, id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVariant(@PathVariable Long id) {
        if (!productVariantService.existsById(id)) {
            throw new RuntimeException("Variante con ID " + id + " no encontrada");
        }
        productVariantService.deleteProductVariant(id);
        return ResponseEntity.noContent().build();
    }
}
