package com.enset.exam.jee.service;

import com.enset.exam.jee.dto.*;

import java.util.List;

public interface CreditManagerService {
    ClientDTO createClient(ClientDTO clientDTO);
    ClientDTO updateClient(Long clientId, ClientDTO clientDTO);
    ClientDTO getClient(Long clientId);
    List<ClientDTO> getAllClients();
    void deleteClient(Long clientId);
    CreditResponseDTO saveCredit(CreditRequestDTO creditRequest);
    CreditResponseDTO approveCredit(Long creditId, String statut);
    RemboursementDTO addRemboursement(Long creditId, RemboursementDTO remboursementDTO);
    List<CreditResponseDTO> getCreditsByClient(Long clientId);
    double calculateRemainingBalance(Long creditId);
    double calculateTotalPaid(Long creditId);
}
