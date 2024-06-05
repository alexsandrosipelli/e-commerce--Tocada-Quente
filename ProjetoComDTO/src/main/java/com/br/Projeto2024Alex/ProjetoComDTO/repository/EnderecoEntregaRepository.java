package com.br.Projeto2024Alex.ProjetoComDTO.repository;

import com.br.Projeto2024Alex.ProjetoComDTO.entity.ClienteEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.EnderecoEntregaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EnderecoEntregaRepository extends JpaRepository<EnderecoEntregaEntity, Long> {

    boolean existsByCepAndCliente(String cep, ClienteEntity cliente);

    @Query("select e from EnderecoEntregaEntity e where e.cliente.id = ?1 and (e.enderecoPrincipal = true)")
    List<EnderecoEntregaEntity> findSecondaryEnderecosByClienteId(Long clienteId);

    @Transactional
    @Modifying
    @Query("delete from EnderecoEntregaEntity e where e.complemento like 'Endereco Teste%'")
    void deleteByComplementoStartingWithEntregaTeste();

    List<EnderecoEntregaEntity> findByCliente_IdAndStatus(Long id, boolean status);

    EnderecoEntregaEntity findByCliente_IdAndEnderecoPrincipalTrue(Long id);
}
