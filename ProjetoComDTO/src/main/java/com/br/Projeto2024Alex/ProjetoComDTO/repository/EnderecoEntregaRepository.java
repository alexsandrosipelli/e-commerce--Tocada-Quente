package com.br.Projeto2024Alex.ProjetoComDTO.repository;

import com.br.Projeto2024Alex.ProjetoComDTO.entity.ClienteEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.EnderecoEntregaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoEntregaRepository extends JpaRepository<EnderecoEntregaEntity, Long> {

    List<EnderecoEntregaEntity> findByCliente(ClienteEntity cliente);

    boolean existsByCepAndCliente(String cep, ClienteEntity cliente);

    @Query("select e from EnderecoEntregaEntity e where e.cliente.id = ?1 and (e.enderecoPrincipal = false or e.enderecoPrincipal is null)")
    List<EnderecoEntregaEntity> findNonPrimaryOrUnsetEnderecosByClienteId(Long clienteId);
}
