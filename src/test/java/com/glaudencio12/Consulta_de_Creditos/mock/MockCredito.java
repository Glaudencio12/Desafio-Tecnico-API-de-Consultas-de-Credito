package com.glaudencio12.Consulta_de_Creditos.mock;
import com.glaudencio12.Consulta_de_Creditos.dto.CreditoDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class MockCredito {

    public List<CreditoDTO> mockCreditoList(){
        return List.of(
                new CreditoDTO(1L,"123456", "7891011", LocalDate.of(2024, 2, 25),
                        new BigDecimal("1500.75"), "ISSQN", true, new BigDecimal("5.0"),
                        new BigDecimal("30000.00"), new BigDecimal("5000.00"), new BigDecimal("25000.00")),

                new CreditoDTO(1L,"789012", "7891011", LocalDate.of(2024, 2, 26),
                        new BigDecimal("1200.50"), "ISSQN", false, new BigDecimal("4.5"),
                        new BigDecimal("25000.00"), new BigDecimal("4000.00"), new BigDecimal("21000.00")),

                new CreditoDTO(1L, "654321", "1122334", LocalDate.of(2024, 1, 15),
                        new BigDecimal("800.50"), "Outros", true, new BigDecimal("3.5"),
                        new BigDecimal("20000.00"), new BigDecimal("3000.00"), new BigDecimal("17000.00")
                )
        );
    }
}
