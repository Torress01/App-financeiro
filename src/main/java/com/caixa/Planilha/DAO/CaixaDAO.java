package com.caixa.planilha.DAO;

import com.caixa.planilha.models.Caixa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaixaDAO extends JpaRepository<Caixa, Integer> {
  
}
