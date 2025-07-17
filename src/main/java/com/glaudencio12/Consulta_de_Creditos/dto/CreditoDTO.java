package com.glaudencio12.Consulta_de_Creditos.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreditoDTO extends RepresentationModel<CreditoDTO> {
    private Long id;
    private String numeroCredito;
    private String numeroNfse;
    private LocalDate dataConstituicao;
    private BigDecimal valorIssqn;
    private String tipoCredito;
    private boolean simplesNacional;
    private BigDecimal aliquota;
    private BigDecimal valorFaturado;
    private BigDecimal valorDeducao;
    private BigDecimal baseCalculo;
}
