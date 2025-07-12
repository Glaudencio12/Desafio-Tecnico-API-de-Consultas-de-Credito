package com.glaudencio12.Consulta_de_Creditos.service;

import com.glaudencio12.Consulta_de_Creditos.dto.CreditoDTO;
import com.glaudencio12.Consulta_de_Creditos.mapper.ObjectMapper;
import com.glaudencio12.Consulta_de_Creditos.model.Credito;
import com.glaudencio12.Consulta_de_Creditos.repository.CreditoRepository;
import com.glaudencio12.Consulta_de_Creditos.utils.HateoasLinks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreditoService {
    private static final Logger logger = LoggerFactory.getLogger(CreditoService.class.getName());
    @Autowired
    private CreditoRepository repository;
    @Autowired
    private HateoasLinks hateoasLinks;

    public CreditoDTO findCreditById(String numeroCredito){
        logger.info("Busca ao crédito constituído");
        List<Credito> creditoList = repository.findAll();
        Credito entidade = null;
        for (Credito credito : creditoList){
            if (credito.getNumeroCredito().equals(numeroCredito)) {
                entidade = credito;
            }
        }
        CreditoDTO dto = ObjectMapper.parseObject(entidade, CreditoDTO.class);
        hateoasLinks.links(dto);
        return dto;
    }

    public List<CreditoDTO> findAllCredit(String numeroNfse){
        logger.info("Busca a todos os créditos");
        List<Credito> creditoList = repository.findAll();
        List<CreditoDTO> creditos = new ArrayList<>();
        for (Credito credito : creditoList){
            if (credito.getNumeroNfse().equals(numeroNfse)) {
                creditos.add(ObjectMapper.parseObject(credito, CreditoDTO.class));
            }
        }
        creditos.forEach(dto -> hateoasLinks.links(dto));
        return creditos;
    }
}
