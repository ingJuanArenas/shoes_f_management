package com.shoes_f_management.Web.Controllers;

import com.shoes_f_management.Domain.DTOs.Requests.ShoeRequestDto;
import com.shoes_f_management.Domain.DTOs.Responses.ShoeResponseDTO;
import com.shoes_f_management.Persistence.Models.Shoe;
import com.shoes_f_management.Persistence.Repositories.ShoeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/shoes")
@RequiredArgsConstructor
@Validated
public class ShoeController {

    private final ShoeRepository shoeRepository;

    @GetMapping
    public List<ShoeResponseDTO> getAllShoes() {
        return shoeRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoeResponseDTO> getShoeById(@PathVariable Long id) {
        return shoeRepository.findById(id)
                .map(shoe -> ResponseEntity.ok(toResponse(shoe)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ShoeResponseDTO> createShoe(@Valid @RequestBody ShoeRequestDto request) {
        Shoe shoe = new Shoe();
        updateEntityFromRequest(shoe, request);
        Shoe saved = shoeRepository.save(shoe);
        return ResponseEntity.created(URI.create("/api/shoes/" + saved.getId()))
                .body(toResponse(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShoeResponseDTO> updateShoe(
            @PathVariable Long id,
            @Valid @RequestBody ShoeRequestDto request
    ) {
        return shoeRepository.findById(id)
                .map(existing -> {
                    updateEntityFromRequest(existing, request);
                    Shoe updated = shoeRepository.save(existing);
                    return ResponseEntity.ok(toResponse(updated));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShoe(@PathVariable Long id) {
        return shoeRepository.findById(id)
                .map(shoe -> {
                    shoeRepository.delete(shoe);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private ShoeResponseDTO toResponse(Shoe shoe) {
        return new ShoeResponseDTO(
                shoe.getId(),
                shoe.getReference(),
                shoe.getModelName(),
                shoe.getColor(),
                shoe.getSize(),
                shoe.getInitialStock(),
                shoe.getCostPrice(),
                shoe.getRetailPrice(),
                shoe.getWholesalePrice()
        );
    }

    private void updateEntityFromRequest(Shoe shoe, ShoeRequestDto request) {
        shoe.setReference(request.reference());
        shoe.setModelName(request.modelName());
        shoe.setColor(request.color());
        shoe.setSize(request.size());
        shoe.setInitialStock(request.initialStock());
        shoe.setCostPrice(request.costPrice());
        shoe.setRetailPrice(request.retailPrice());
        shoe.setWholesalePrice(request.wholesalePrice());
    }
}
