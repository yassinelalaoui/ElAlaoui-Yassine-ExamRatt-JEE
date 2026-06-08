package com.enset.exam.jee.config;

import com.enset.exam.jee.model.entities.*;
import com.enset.exam.jee.model.enums.ApplicationRole;
import com.enset.exam.jee.model.enums.CreditStatut;
import com.enset.exam.jee.model.enums.CreditTypeBien;
import com.enset.exam.jee.model.enums.RemboursementType;
import com.enset.exam.jee.repository.ClientRepository;
import com.enset.exam.jee.repository.CreditRepository;
import com.enset.exam.jee.repository.RemboursementRepository;
import com.enset.exam.jee.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner seedDatabase(ClientRepository clientRepository,
                                         CreditRepository creditRepository,
                                         RemboursementRepository remboursementRepository,
                                         UserAccountRepository userAccountRepository) {
        return args -> {
            if (userAccountRepository.count() == 0) {
                UserAccount admin = new UserAccount(null, "admin", passwordEncoder.encode("admin123"), ApplicationRole.ROLE_ADMIN);
                UserAccount employe = new UserAccount(null, "employe", passwordEncoder.encode("employe123"), ApplicationRole.ROLE_EMPLOYE);
                UserAccount clientUser = new UserAccount(null, "client1@example.com", passwordEncoder.encode("client123"), ApplicationRole.ROLE_CLIENT);
                userAccountRepository.saveAll(List.of(admin, employe, clientUser));
            }

            if (clientRepository.count() == 0) {
                Client client1 = new Client(null, "Khadija Ait", "client1@example.com", List.of());
                Client client2 = new Client(null, "Youssef Ben", "client2@example.com", List.of());
                clientRepository.saveAll(List.of(client1, client2));

                CreditPersonnel credit1 = new CreditPersonnel();
                credit1.setClient(client1);
                credit1.setDateDemande(LocalDate.now().minusDays(12));
                credit1.setDateAcceptation(LocalDate.now().minusDays(2));
                credit1.setMontant(15000);
                credit1.setDureeRemboursement(24);
                credit1.setTauxInteret(6.5);
                credit1.setStatut(CreditStatut.ACCEPTE);
                credit1.setMotif("Etudes");

                CreditImmobilier credit2 = new CreditImmobilier();
                credit2.setClient(client1);
                credit2.setDateDemande(LocalDate.now().minusDays(20));
                credit2.setMontant(250000);
                credit2.setDureeRemboursement(180);
                credit2.setTauxInteret(4.2);
                credit2.setStatut(CreditStatut.EN_COURS);
                credit2.setTypeBien(CreditTypeBien.APPARTEMENT);

                CreditProfessionnel credit3 = new CreditProfessionnel();
                credit3.setClient(client2);
                credit3.setDateDemande(LocalDate.now().minusDays(5));
                credit3.setMontant(75000);
                credit3.setDureeRemboursement(36);
                credit3.setTauxInteret(7.2);
                credit3.setStatut(CreditStatut.REJETE);
                credit3.setMotif("Achat de materiel");
                credit3.setRaisonSociale("SARL Tech Solutions");

                creditRepository.saveAll(List.of(credit1, credit2, credit3));

                Remboursement remb1 = new Remboursement(null, LocalDate.now().minusDays(1), 650, RemboursementType.MENSUALITE, credit1);
                Remboursement remb2 = new Remboursement(null, LocalDate.now().minusDays(10), 650, RemboursementType.MENSUALITE, credit1);
                remboursementRepository.saveAll(List.of(remb1, remb2));
            }
        };
    }
}
