package com.enset.exam.jee.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("PERSONNEL")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditPersonnel extends Credit {
    @Column(nullable = false)
    private String motif;
}
