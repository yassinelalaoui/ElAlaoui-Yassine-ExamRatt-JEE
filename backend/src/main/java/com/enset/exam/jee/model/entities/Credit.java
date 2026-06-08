package com.enset.exam.jee.model.entities;

import com.enset.exam.jee.model.enums.CreditStatut;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "credits")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "credit_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateDemande;

    @Enumerated(EnumType.STRING)
    private CreditStatut statut;

    private LocalDate dateAcceptation;
    private double montant;
    private int dureeRemboursement;
    private double tauxInteret;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "credit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Remboursement> remboursements = new ArrayList<>();
}
