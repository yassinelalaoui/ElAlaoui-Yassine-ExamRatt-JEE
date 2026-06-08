package com.enset.exam.jee.dto;

import com.enset.exam.jee.model.enums.CreditTypeBien;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditRequestDTO {
    private Long clientId;
    private LocalDate dateDemande;
    private double montant;
    private int dureeRemboursement;
    private double tauxInteret;
    private String creditType;
    private String motif;
    private CreditTypeBien typeBien;
    private String raisonSociale;
}
