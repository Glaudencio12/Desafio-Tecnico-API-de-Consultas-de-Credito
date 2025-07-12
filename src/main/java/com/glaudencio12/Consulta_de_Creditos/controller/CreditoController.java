package com.glaudencio12.Consulta_de_Creditos.controller;

import com.glaudencio12.Consulta_de_Creditos.dto.CreditoDTO;
import com.glaudencio12.Consulta_de_Creditos.service.CreditoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/creditos/")
public class CreditoController {
    @Autowired
    private CreditoService service;

    @GetMapping(value = "/{numeroCredito}")
    public CreditoDTO findById(@PathVariable("numeroCredito") String numeroCredito){
        return service.findCreditById(numeroCredito);
    }
}
