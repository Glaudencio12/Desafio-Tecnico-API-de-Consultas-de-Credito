package com.glaudencio12.Consulta_de_Creditos.utils;

import com.glaudencio12.Consulta_de_Creditos.controller.CreditoController;
import com.glaudencio12.Consulta_de_Creditos.dto.CreditoDTO;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class HateoasLinks {
    public void links(CreditoDTO dto){
        dto.add(linkTo(methodOn(CreditoController.class).findById(dto.getNumeroCredito()))
                .withRel("findCreditByNumber")
                .withType("GET")
                .withTitle("Retorna os detalhes de um crédito constituído específico com base no número do crédito constituído.")
        );
        dto.add(linkTo(methodOn(CreditoController.class).findAll(dto.getNumeroNfse()))
                .withRel("findAllCredits")
                .withType("GET")
                .withTitle("Retorna uma lista de créditos constituídos com base no número da NFS-e")
        );
    }
}
