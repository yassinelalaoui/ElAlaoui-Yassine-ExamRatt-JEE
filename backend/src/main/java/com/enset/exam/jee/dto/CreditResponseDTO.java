package com.enset.exam.jee.dto;

import com.enset.exam.jee.model.enums.CreditStatut;
import com.enset.exam.jee.model.enums.CreditTypeBien;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditResponseDTO {
    private Long id;
    private LocalDate dateDemande;
    private CreditStatut statut;
    private LocalDate dateAcceptation;
    private double montant;
    private int dureeRemboursement;
    private double tauxInteret;
    private Long clientId;
    private String creditType;
    private String motif;
    private CreditTypeBien typeBien;
    private String raisonSociale;
    private double totalPayee;
    private double remainingBalance;
}
