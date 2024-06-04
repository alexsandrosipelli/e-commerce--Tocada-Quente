package com.br.Projeto2024Alex.ProjetoComDTO.repository;

import com.br.Projeto2024Alex.ProjetoComDTO.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

    ClienteEntity findByEmail(String email);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByCpf(String cpf);

    List<ClienteEntity> findByEmailStartingWith(String emailPrefix);
}
