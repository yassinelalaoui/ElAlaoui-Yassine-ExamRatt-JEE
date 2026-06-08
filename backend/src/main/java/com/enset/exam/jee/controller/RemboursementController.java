package com.enset.exam.jee.controller;

import com.enset.exam.jee.dto.RemboursementDTO;
import com.enset.exam.jee.model.entities.Remboursement;
import com.enset.exam.jee.repository.RemboursementRepository;
import com.enset.exam.jee.service.CreditManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/remboursements")
@RequiredArgsConstructor
public class RemboursementController {
    private final CreditManagerService service;
    private final RemboursementRepository remboursementRepository;

    @PostMapping("/credit/{creditId}")
    @PreAuthorize("hasAnyRole('EMPLOYE','ADMIN')")
    public ResponseEntity<RemboursementDTO> addPayment(@PathVariable Long creditId, @RequestBody RemboursementDTO dto) {
        return ResponseEntity.status(201).body(service.addRemboursement(creditId, dto));
    }

    @GetMapping("/credit/{creditId}")
    @PreAuthorize("hasAnyRole('CLIENT','EMPLOYE','ADMIN')")
    public ResponseEntity<List<RemboursementDTO>> getPayments(@PathVariable Long creditId) {
        List<RemboursementDTO> result = remboursementRepository.findByCreditId(creditId).stream()
                .map(r -> new RemboursementDTO(r.getId(), r.getDate(), r.getMontant(), r.getType(), r.getCredit().getId()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
}
