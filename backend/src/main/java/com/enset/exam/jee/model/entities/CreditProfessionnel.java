package com.enset.exam.jee.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("PROFESSIONNEL")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditProfessionnel extends Credit {
    @Column(nullable = false)
    private String motif;
    @Column(nullable = false)
    private String raisonSociale;
}
