package com.enset.exam.jee.controller;

import com.enset.exam.jee.dto.CreditRequestDTO;
import com.enset.exam.jee.dto.CreditResponseDTO;
import com.enset.exam.jee.service.CreditManagerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/credits")
@RequiredArgsConstructor
public class CreditController {
    private final CreditManagerService service;

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<CreditResponseDTO> applyCredit(@RequestBody CreditRequestDTO request) {
        return ResponseEntity.status(201).body(service.saveCredit(request));
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreditResponseDTO> approveCredit(@PathVariable Long id, @RequestParam String statut) {
        return ResponseEntity.ok(service.approveCredit(id, statut));
    }

    @GetMapping("/client/{id}")
    @PreAuthorize("hasAnyRole('CLIENT','EMPLOYE','ADMIN')")
    public ResponseEntity<List<CreditResponseDTO>> getCreditsByClient(@PathVariable Long id, HttpServletRequest request) {
        String user = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : null;
        return ResponseEntity.ok(service.getCreditsByClient(id));
    }

    @GetMapping("/{id}/balance")
    @PreAuthorize("hasAnyRole('CLIENT','EMPLOYE','ADMIN')")
    public ResponseEntity<Double> getRemainingBalance(@PathVariable Long id) {
        return ResponseEntity.ok(service.calculateRemainingBalance(id));
    }

    @GetMapping("/{id}/paid")
    @PreAuthorize("hasAnyRole('CLIENT','EMPLOYE','ADMIN')")
    public ResponseEntity<Double> getTotalPaid(@PathVariable Long id) {
        return ResponseEntity.ok(service.calculateTotalPaid(id));
    }
}
