package com.enset.exam.jee.service.impl;

import com.enset.exam.jee.dto.*;
import com.enset.exam.jee.exception.BusinessException;
import com.enset.exam.jee.exception.ResourceNotFoundException;
import com.enset.exam.jee.mapper.CreditMapper;
import com.enset.exam.jee.model.entities.*;
import com.enset.exam.jee.model.enums.CreditStatut;
import com.enset.exam.jee.repository.ClientRepository;
import com.enset.exam.jee.repository.CreditRepository;
import com.enset.exam.jee.repository.RemboursementRepository;
import com.enset.exam.jee.service.CreditManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CreditManagerServiceImpl implements CreditManagerService {

    private final ClientRepository clientRepository;
    private final CreditRepository creditRepository;
    private final RemboursementRepository remboursementRepository;

    @Override
    public ClientDTO createClient(ClientDTO clientDTO) {
        if (clientDTO.getEmail() == null || clientDTO.getNom() == null) {
            throw new BusinessException("Le nom et l'email du client sont requis.");
        }
        Client client = new Client();
        client.setNom(clientDTO.getNom());
        client.setEmail(clientDTO.getEmail());
        Client saved = clientRepository.save(client);
        return new ClientDTO(saved.getId(), saved.getNom(), saved.getEmail());
    }

    @Override
    public ClientDTO updateClient(Long clientId, ClientDTO clientDTO) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client non trouvé: " + clientId));
        client.setNom(clientDTO.getNom());
        client.setEmail(clientDTO.getEmail());
        Client saved = clientRepository.save(client);
        return new ClientDTO(saved.getId(), saved.getNom(), saved.getEmail());
    }

    @Override
    public ClientDTO getClient(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client non trouvé: " + clientId));
        return new ClientDTO(client.getId(), client.getNom(), client.getEmail());
    }

    @Override
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream()
                .map(client -> new ClientDTO(client.getId(), client.getNom(), client.getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteClient(Long clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new ResourceNotFoundException("Client non trouvé: " + clientId);
        }
        clientRepository.deleteById(clientId);
    }

    @Override
    public CreditResponseDTO saveCredit(CreditRequestDTO creditRequest) {
        Client client = clientRepository.findById(creditRequest.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client non trouvé: " + creditRequest.getClientId()));
        if (creditRequest.getMontant() <= 0) {
            throw new BusinessException("Le montant du crédit doit être supérieur à zéro.");
        }
        if (creditRequest.getDureeRemboursement() <= 0) {
            throw new BusinessException("La durée du remboursement doit être valide.");
        }
        if (creditRequest.getDateDemande() == null) {
            creditRequest.setDateDemande(LocalDate.now());
        }
        Credit credit = CreditMapper.toEntity(creditRequest, client);
        Credit saved = creditRepository.save(credit);
        return CreditMapper.toDto(saved);
    }

    @Override
    public CreditResponseDTO approveCredit(Long creditId, String statut) {
        Credit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> new ResourceNotFoundException("Crédit non trouvé: " + creditId));
        CreditStatut newStatus;
        try {
            newStatus = CreditStatut.valueOf(statut.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new BusinessException("Statut invalide: " + statut);
        }
        credit.setStatut(newStatus);
        credit.setDateAcceptation(newStatus == CreditStatut.ACCEPTE ? LocalDate.now() : null);
        Credit saved = creditRepository.save(credit);
        return CreditMapper.toDto(saved);
    }

    @Override
    public RemboursementDTO addRemboursement(Long creditId, RemboursementDTO remboursementDTO) {
        Credit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> new ResourceNotFoundException("Crédit non trouvé: " + creditId));
        if (credit.getStatut() != CreditStatut.ACCEPTE) {
            throw new BusinessException("Impossible d'ajouter un remboursement sur un crédit non accepté.");
        }
        Remboursement remboursement = new Remboursement();
        remboursement.setDate(remboursementDTO.getDate() != null ? remboursementDTO.getDate() : LocalDate.now());
        remboursement.setMontant(remboursementDTO.getMontant());
        remboursement.setType(remboursementDTO.getType());
        remboursement.setCredit(credit);
        Remboursement saved = remboursementRepository.save(remboursement);
        credit.getRemboursements().add(saved);
        creditRepository.save(credit);
        return new RemboursementDTO(saved.getId(), saved.getDate(), saved.getMontant(), saved.getType(), creditId);
    }

    @Override
    public List<CreditResponseDTO> getCreditsByClient(Long clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new ResourceNotFoundException("Client non trouvé: " + clientId);
        }
        return creditRepository.findByClientId(clientId).stream()
                .map(CreditMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public double calculateRemainingBalance(Long creditId) {
        Credit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> new ResourceNotFoundException("Crédit non trouvé: " + creditId));
        double totalPaid = credit.getRemboursements().stream().mapToDouble(Remboursement::getMontant).sum();
        return Math.max(0, credit.getMontant() - totalPaid);
    }

    @Override
    public double calculateTotalPaid(Long creditId) {
        Credit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> new ResourceNotFoundException("Crédit non trouvé: " + creditId));
        return credit.getRemboursements().stream().mapToDouble(Remboursement::getMontant).sum();
    }
}
