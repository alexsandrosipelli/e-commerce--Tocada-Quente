package com.br.Projeto2024Alex.ProjetoComDTO.repository;

import com.br.Projeto2024Alex.ProjetoComDTO.entity.UsuarioEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    public boolean existsByEmail(String email);

    public UsuarioEntity findByEmail(String email);

    public List<UsuarioEntity> findByNomeContainingIgnoreCase(String nome);

}
