package com.enset.exam.jee.model.entities;

import com.enset.exam.jee.model.enums.CreditTypeBien;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("IMMOBILIER")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreditImmobilier extends Credit {
    @Enumerated(EnumType.STRING)
    private CreditTypeBien typeBien;
}
