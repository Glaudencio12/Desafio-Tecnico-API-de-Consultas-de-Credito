package com.glaudencio12.Consulta_de_Creditos.repository;

import com.glaudencio12.Consulta_de_Creditos.model.Credito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditoRepository extends JpaRepository<Credito, Long> {}
