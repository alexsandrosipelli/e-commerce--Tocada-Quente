package com.br.Projeto2024Alex.ProjetoComDTO.repository;

import com.br.Projeto2024Alex.ProjetoComDTO.entity.EnderecoFaturamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EnderecoFaturamentoRepository extends JpaRepository<EnderecoFaturamentoEntity, Long> {
  @Transactional
  @Modifying
  @Query("delete from EnderecoFaturamentoEntity e where e.complemento like 'Endereco Teste%'")
  void deleteByComplementoStartingWithFaturamentoTeste();
}
