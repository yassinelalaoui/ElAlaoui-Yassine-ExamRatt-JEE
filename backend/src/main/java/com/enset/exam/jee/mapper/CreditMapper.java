package com.enset.exam.jee.mapper;

import com.enset.exam.jee.dto.CreditRequestDTO;
import com.enset.exam.jee.dto.CreditResponseDTO;
import com.enset.exam.jee.model.entities.*;
import com.enset.exam.jee.model.enums.CreditStatut;
import com.enset.exam.jee.model.enums.CreditTypeBien;

import java.util.stream.Collectors;

public class CreditMapper {
    public static CreditResponseDTO toDto(Credit credit) {
        if (credit == null) {
            return null;
        }
        CreditResponseDTO dto = new CreditResponseDTO();
        dto.setId(credit.getId());
        dto.setDateDemande(credit.getDateDemande());
        dto.setStatut(credit.getStatut());
        dto.setDateAcceptation(credit.getDateAcceptation());
        dto.setMontant(credit.getMontant());
        dto.setDureeRemboursement(credit.getDureeRemboursement());
        dto.setTauxInteret(credit.getTauxInteret());
        dto.setClientId(credit.getClient() != null ? credit.getClient().getId() : null);
        dto.setTotalPayee(credit.getRemboursements().stream().mapToDouble(r -> r.getMontant()).sum());
        dto.setRemainingBalance(calculateRemainingBalance(credit));

        if (credit instanceof CreditPersonnel personnel) {
            dto.setCreditType("PERSONNEL");
            dto.setMotif(personnel.getMotif());
        } else if (credit instanceof CreditImmobilier immobilier) {
            dto.setCreditType("IMMOBILIER");
            dto.setTypeBien(immobilier.getTypeBien());
        } else if (credit instanceof CreditProfessionnel professionnel) {
            dto.setCreditType("PROFESSIONNEL");
            dto.setMotif(professionnel.getMotif());
            dto.setRaisonSociale(professionnel.getRaisonSociale());
        }
        return dto;
    }

    public static Credit toEntity(CreditRequestDTO request, Client client) {
        if (request == null || client == null) {
            return null;
        }
        Credit credit;
        var type = request.getCreditType() != null ? request.getCreditType().toUpperCase() : "";
        switch (type) {
            case "IMMOBILIER" -> {
                CreditImmobilier immobilier = new CreditImmobilier();
                immobilier.setTypeBien(request.getTypeBien());
                credit = immobilier;
            }
            case "PROFESSIONNEL" -> {
                CreditProfessionnel professionnel = new CreditProfessionnel();
                professionnel.setMotif(request.getMotif());
                professionnel.setRaisonSociale(request.getRaisonSociale());
                credit = professionnel;
            }
            default -> {
                CreditPersonnel personnel = new CreditPersonnel();
                personnel.setMotif(request.getMotif());
                credit = personnel;
            }
        }
        credit.setClient(client);
        credit.setDateDemande(request.getDateDemande());
        credit.setMontant(request.getMontant());
        credit.setDureeRemboursement(request.getDureeRemboursement());
        credit.setTauxInteret(request.getTauxInteret());
        credit.setStatut(CreditStatut.EN_COURS);
        return credit;
    }

    private static double calculateRemainingBalance(Credit credit) {
        double totalPaid = credit.getRemboursements().stream().mapToDouble(r -> r.getMontant()).sum();
        return Math.max(0, credit.getMontant() - totalPaid);
    }
}
