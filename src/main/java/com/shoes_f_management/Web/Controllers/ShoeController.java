package com.shoes_f_management.Web.Controllers;

import com.shoes_f_management.Domain.DTOs.Requests.ShoeRequestDto;
import com.shoes_f_management.Domain.DTOs.Responses.ShoeResponseDTO;
import com.shoes_f_management.Domain.Services.ShoeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shoes")
public class ShoeController {

    private final ShoeService shoeService;

    public ShoeController(ShoeService shoeService) {
        this.shoeService = shoeService;
    }

    @GetMapping
    public ResponseEntity<List<ShoeResponseDTO>> getAllShoes() {
        return ResponseEntity.ok(shoeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoeResponseDTO> getShoeById(@PathVariable Long id) {
        return ResponseEntity.ok(shoeService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ShoeResponseDTO> createShoe(@Valid @RequestBody ShoeRequestDto request) {
        return ResponseEntity.ok(shoeService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShoeResponseDTO> updateShoe( @PathVariable Long id, @Valid @RequestBody ShoeRequestDto request) {
        return ResponseEntity.ok(shoeService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShoe(@PathVariable Long id) {
        shoeService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
