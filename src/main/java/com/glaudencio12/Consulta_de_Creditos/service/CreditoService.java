package com.glaudencio12.Consulta_de_Creditos.service;

import com.glaudencio12.Consulta_de_Creditos.dto.CreditoDTO;
import com.glaudencio12.Consulta_de_Creditos.mapper.ObjectMapper;
import com.glaudencio12.Consulta_de_Creditos.model.Credito;
import com.glaudencio12.Consulta_de_Creditos.repository.CreditoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditoService {
    private static final Logger logger = LoggerFactory.getLogger(CreditoService.class.getName());
    @Autowired
    private CreditoRepository repository;

    public CreditoDTO findCreditById(String numeroCredito){
        logger.info("Busca ao crédito constituído");
        List<Credito> creditoList = repository.findAll();
        Credito entidade = null;
        for (Credito credito : creditoList){
            if (credito.getNumeroCredito().equals(numeroCredito)) {
                entidade = credito;
            }
        }
        return ObjectMapper.parseObject(entidade, CreditoDTO.class);
    }
}
