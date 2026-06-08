package com.enset.exam.jee.controller;

import com.enset.exam.jee.dto.CreditRequestDTO;
import com.enset.exam.jee.dto.CreditResponseDTO;
import com.enset.exam.jee.model.entities.Client;
import com.enset.exam.jee.repository.ClientRepository;
import com.enset.exam.jee.service.CreditManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/credits")
@RequiredArgsConstructor
public class CreditController {
    private final CreditManagerService service;
    private final ClientRepository clientRepository;

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<CreditResponseDTO> applyCredit(@RequestBody CreditRequestDTO request) {
        verifyClientOwnership(request.getClientId());
        return ResponseEntity.status(201).body(service.saveCredit(request));
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreditResponseDTO> approveCredit(@PathVariable Long id, @RequestParam String statut) {
        return ResponseEntity.ok(service.approveCredit(id, statut));
    }

    @GetMapping("/client/{id}")
    @PreAuthorize("hasAnyRole('CLIENT','EMPLOYE','ADMIN')")
    public ResponseEntity<List<CreditResponseDTO>> getCreditsByClient(@PathVariable Long id) {
        if (isCurrentUserClient()) {
            verifyClientOwnership(id);
        }
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

    private void verifyClientOwnership(Long clientId) {
        String username = getCurrentUsername();
        if (username != null) {
            Client client = clientRepository.findById(clientId).orElse(null);
            if (client != null && username.equals(client.getEmail())) {
                return;
            }
            if (isCurrentUserClient()) {
                throw new AccessDeniedException("Accès interdit : vous ne pouvez consulter que vos propres crédits.");
            }
        }
    }

    private boolean isCurrentUserClient() {
        return getCurrentAuthentication().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CLIENT"));
    }

    private String getCurrentUsername() {
        return getCurrentAuthentication().getName();
    }

    private Authentication getCurrentAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
