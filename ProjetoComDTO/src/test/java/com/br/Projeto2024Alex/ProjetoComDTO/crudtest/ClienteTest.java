package com.br.Projeto2024Alex.ProjetoComDTO.crudtest;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.ClienteDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.ClienteEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.UsuarioEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.repository.ClienteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ClienteTest {
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ClienteRepository repository;

    @BeforeEach
    @AfterEach
    void limparDadosDeTeste() {
        List<ClienteEntity> clientes = repository.findByEmailStartingWith("clienteteste");
        repository.deleteAll(clientes);
    }

    @Test
    void testeCadastroCliente() {
        ClienteDTO clienteDTO = new ClienteDTO();
        String senha = encoder.encode("1234");

        clienteDTO.setNome("Cliente Teste1");
        clienteDTO.setDataNascimento("20240517");
        clienteDTO.setGenero("Masculino");
        clienteDTO.setComplemento("Teste Crud Cliente");
        clienteDTO.setNumero(777);
        clienteDTO.setEmail("clienteteste1@gmail.com");
        clienteDTO.setSenha(senha);
        clienteDTO.setCpf("99296983886");
        clienteDTO.setCep("04696000");
        clienteDTO.setLocalidade("São Paulo");
        clienteDTO.setUf("SP");

        ClienteEntity clienteEntity = clienteDTO.toEntity();
        repository.save(clienteEntity);
        ClienteEntity clienteEntityGet = repository.findById(clienteEntity.getId()).orElse(null);
        assertNotNull(clienteEntityGet, "Cliente não foi cadastrado corretamente.");
    }

    @Test
    void testeBuscarCliente() {
        ClienteDTO clienteDTO = new ClienteDTO();
        String senha = encoder.encode("1234");

        clienteDTO.setNome("Cliente Teste2");
        clienteDTO.setDataNascimento("20240517");
        clienteDTO.setGenero("Masculino");
        clienteDTO.setEmail("clienteteste2@gmail.com");
        clienteDTO.setSenha(senha);
        clienteDTO.setComplemento("Teste Crud Cliente");
        clienteDTO.setNumero(777);
        clienteDTO.setCpf("02869286805");
        clienteDTO.setCep("04696000");
        clienteDTO.setLocalidade("São Paulo");
        clienteDTO.setUf("SP");

        ClienteEntity clienteEntity = clienteDTO.toEntity();
        repository.save(clienteEntity);
        ClienteEntity clienteEntityGet = repository.findById(clienteEntity.getId()).orElse(null);

        assertEquals(clienteEntity, clienteEntityGet);
    }

    @Test
    void testeEditarCliente() {
        // Criação de um novo ClienteDTO com todos os campos necessários
        ClienteDTO clienteDTO = new ClienteDTO();
        String senha = encoder.encode("1234");

        clienteDTO.setNome("Cliente Teste3");
        clienteDTO.setDataNascimento("20240517");
        clienteDTO.setGenero("Masculino");
        clienteDTO.setEmail("clienteteste3@gmail.com");
        clienteDTO.setSenha(senha);
        clienteDTO.setComplemento("Teste Crud Cliente");
        clienteDTO.setNumero(777);
        clienteDTO.setCpf("96498848820");
        clienteDTO.setCep("04696000");
        clienteDTO.setLocalidade("São Paulo");
        clienteDTO.setUf("SP");

        // Converte o DTO para uma entidade e salva no repositório
        ClienteEntity clienteEntity = clienteDTO.toEntity();
        repository.save(clienteEntity);

        // Busca o cliente salvo
        ClienteEntity clienteSalvo = repository.findById(clienteEntity.getId()).orElse(null);
        assertNotNull(clienteSalvo, "O cliente não foi encontrado após salvar");

        // Atualiza campos da entidade
        clienteSalvo.setGenero("Feminino");

        // Os campos transitórios precisam ser setados novamente
        clienteSalvo.setNumero(777);
        clienteSalvo.setComplemento("Teste Crud Cliente");

        // Salva a atualização
        repository.save(clienteSalvo);

        // Busca novamente o cliente atualizado
        ClienteEntity clienteAtualizadoNovo = repository.findById(clienteSalvo.getId()).orElse(null);
        assertNotNull(clienteAtualizadoNovo, "O cliente não foi encontrado após a atualização");
        assertEquals("Feminino", clienteAtualizadoNovo.getGenero(), "O gênero do cliente não foi atualizado corretamente");
        clienteAtualizadoNovo.setNumero(777);
        clienteAtualizadoNovo.setComplemento("Teste Crud Cliente");
        assertEquals(clienteDTO.getNumero(), clienteAtualizadoNovo.getNumero(), "O número do cliente não foi atualizado corretamente");
        assertEquals(clienteDTO.getComplemento(), clienteAtualizadoNovo.getComplemento(), "O complemento do cliente não foi atualizado corretamente");
    }



    @Test
    void testeExcluirCliente() {
        ClienteDTO clienteDTO = new ClienteDTO();
        String senha = encoder.encode("1234");

        clienteDTO.setNome("Cliente Teste4");
        clienteDTO.setDataNascimento("20240517");
        clienteDTO.setComplemento("Teste Crud Cliente");
        clienteDTO.setNumero(777);
        clienteDTO.setGenero("Masculino");
        clienteDTO.setEmail("clienteteste4@gmail.com");
        clienteDTO.setSenha(senha);
        clienteDTO.setCpf("58000435829");
        clienteDTO.setCep("04696000");
        clienteDTO.setLocalidade("São Paulo");
        clienteDTO.setUf("SP");

        ClienteEntity clienteEntity = clienteDTO.toEntity();
        repository.save(clienteEntity);

        repository.delete(clienteEntity);

        assertNull(repository.findById(clienteEntity.getId()).orElse(null));
    }
}
