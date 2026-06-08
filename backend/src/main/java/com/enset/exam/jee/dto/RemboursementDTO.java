package com.enset.exam.jee.dto;

import com.enset.exam.jee.model.enums.RemboursementType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemboursementDTO {
    private Long id;
    private LocalDate date;
    private double montant;
    private RemboursementType type;
    private Long creditId;
}
