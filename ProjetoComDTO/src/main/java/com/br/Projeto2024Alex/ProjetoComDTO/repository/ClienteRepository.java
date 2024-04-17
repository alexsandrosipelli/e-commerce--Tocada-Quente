package com.br.Projeto2024Alex.ProjetoComDTO.repository;

import com.br.Projeto2024Alex.ProjetoComDTO.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

}
