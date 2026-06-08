package com.enset.exam.jee.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("PROFESSIONNEL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreditProfessionnel extends Credit {
    @Column(nullable = false)
    private String motif;
    @Column(nullable = false)
    private String raisonSociale;
}
