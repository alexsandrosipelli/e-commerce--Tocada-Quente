package com.br.Projeto2024Alex.ProjetoComDTO.crudtest;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.ClienteDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.dto.EnderecoEntregaDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.dto.EnderecoFaturamentoDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.ClienteEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ClienteTest {
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ClienteRepository repository;

    @Test
    void testeCadastroCliente(){
        ClienteDTO clienteDTO = new ClienteDTO();
        List<EnderecoEntregaDTO> listaEnderecosEntrega = new ArrayList<>();
        List<EnderecoFaturamentoDTO> listaEnderecosFaturamento = new ArrayList<>();
        String senha = encoder.encode("1234");

        clienteDTO.setNome("Cliente Teste1");
        clienteDTO.setDataNascimento("20240517");
        clienteDTO.setGenero("Masculino");
        clienteDTO.setEmail("clienteteste1@gmail.com");
        clienteDTO.setSenha(senha);
        clienteDTO.setCpf("99296983886");
        clienteDTO.setCep("04696000");
        clienteDTO.setLocalidade("São Paulo");
        clienteDTO.setUf("SP");

        listaEnderecosEntrega.add(new EnderecoEntregaDTO(
                "04696000",
                "Avenida Engenheiro Eusébio Stevaux",
                "823",
                "Endereco Teste Unitario",
                "Jurubatuba",
                "São Paulo",
                clienteDTO,
                "SP"
        ));

        listaEnderecosFaturamento.add(new EnderecoFaturamentoDTO(
                "04696000",
                "Avenida Engenheiro Eusébio Stevaux",
                "823",
                "Endereco Teste Unitario",
                "Jurubatuba",
                "São Paulo",
                clienteDTO,
                "SP"
        ));

        clienteDTO.setEnderecoEntregaDto(listaEnderecosEntrega);
        clienteDTO.setEnderecoFaturamentoDto(listaEnderecosFaturamento);

        ClienteEntity clienteEntity = clienteDTO.toEntity();
        repository.save(clienteEntity);
        assertNotNull(clienteEntity.getId());
    }

    @Test
    void testeBuscarCliente(){
        ClienteDTO clienteDTO = new ClienteDTO();
        List<EnderecoEntregaDTO> listaEnderecosEntrega = new ArrayList<>();
        List<EnderecoFaturamentoDTO> listaEnderecosFaturamento = new ArrayList<>();
        String senha = encoder.encode("1234");

        clienteDTO.setNome("Cliente Teste2");
        clienteDTO.setDataNascimento("20240517");
        clienteDTO.setGenero("Masculino");
        clienteDTO.setEmail("clienteteste2@gmail.com");
        clienteDTO.setSenha(senha);
        clienteDTO.setCpf("02869286805");
        clienteDTO.setCep("04696000");
        clienteDTO.setLocalidade("São Paulo");
        clienteDTO.setUf("SP");

        listaEnderecosEntrega.add(new EnderecoEntregaDTO(
                "04696000",
                "Avenida Engenheiro Eusébio Stevaux",
                "823",
                "Endereco Teste Unitario",
                "Jurubatuba",
                "São Paulo",
                clienteDTO,
                "SP"
        ));

        listaEnderecosFaturamento.add(new EnderecoFaturamentoDTO(
                "04696000",
                "Avenida Engenheiro Eusébio Stevaux",
                "823",
                "Endereco Teste Unitario",
                "Jurubatuba",
                "São Paulo",
                clienteDTO,
                "SP"
        ));

        clienteDTO.setEnderecoEntregaDto(listaEnderecosEntrega);
        clienteDTO.setEnderecoFaturamentoDto(listaEnderecosFaturamento);

        ClienteEntity clienteEntity = clienteDTO.toEntity();
        repository.save(clienteEntity);
        ClienteEntity clienteEntityGet = repository.findById(clienteEntity.getId()).orElse(null);

        assertEquals(clienteEntity, clienteEntityGet);
    }

    @Test
    void testeEditarCliente(){
        ClienteDTO clienteDTO = new ClienteDTO();
        List<EnderecoEntregaDTO> listaEnderecosEntrega = new ArrayList<>();
        List<EnderecoFaturamentoDTO> listaEnderecosFaturamento = new ArrayList<>();
        String senha = encoder.encode("1234");

        clienteDTO.setNome("Cliente Teste3");
        clienteDTO.setDataNascimento("20240517");
        clienteDTO.setGenero("Masculino");
        clienteDTO.setEmail("clienteteste3@gmail.com");
        clienteDTO.setSenha(senha);
        clienteDTO.setCpf("96498848820");
        clienteDTO.setCep("04696000");
        clienteDTO.setLocalidade("São Paulo");
        clienteDTO.setUf("SP");

        listaEnderecosEntrega.add(new EnderecoEntregaDTO(
                "04696000",
                "Avenida Engenheiro Eusébio Stevaux",
                "823",
                "Endereco Teste Unitario",
                "Jurubatuba",
                "São Paulo",
                clienteDTO,
                "SP"
        ));

        listaEnderecosFaturamento.add(new EnderecoFaturamentoDTO(
                "04696000",
                "Avenida Engenheiro Eusébio Stevaux",
                "823",
                "Endereco Teste Unitario",
                "Jurubatuba",
                "São Paulo",
                clienteDTO,
                "SP"
        ));

        clienteDTO.setEnderecoEntregaDto(listaEnderecosEntrega);
        clienteDTO.setEnderecoFaturamentoDto(listaEnderecosFaturamento);

        ClienteEntity clienteEntity = clienteDTO.toEntity();
        repository.save(clienteEntity);

        clienteEntity.setGenero("Feminino");

        repository.save(clienteEntity);

        ClienteEntity clienteAtualizado = repository.findById(clienteEntity.getId()).orElse(null);

        assertNotNull(clienteAtualizado);
        assertEquals("Feminino", clienteAtualizado.getGenero());
    }

    @Test
    void testeExcluirCliente(){
        ClienteDTO clienteDTO = new ClienteDTO();
        List<EnderecoEntregaDTO> listaEnderecosEntrega = new ArrayList<>();
        List<EnderecoFaturamentoDTO> listaEnderecosFaturamento = new ArrayList<>();
        String senha = encoder.encode("1234");

        clienteDTO.setNome("Cliente Teste4");
        clienteDTO.setDataNascimento("20240517");
        clienteDTO.setGenero("Masculino");
        clienteDTO.setEmail("clienteteste4@gmail.com");
        clienteDTO.setSenha(senha);
        clienteDTO.setCpf("58000435829");
        clienteDTO.setCep("04696000");
        clienteDTO.setLocalidade("São Paulo");
        clienteDTO.setUf("SP");

        listaEnderecosEntrega.add(new EnderecoEntregaDTO(
                "04696000",
                "Avenida Engenheiro Eusébio Stevaux",
                "823",
                "Endereco Teste Unitario",
                "Jurubatuba",
                "São Paulo",
                clienteDTO,
                "SP"
        ));

        listaEnderecosFaturamento.add(new EnderecoFaturamentoDTO(
                "04696000",
                "Avenida Engenheiro Eusébio Stevaux",
                "823",
                "Endereco Teste Unitario",
                "Jurubatuba",
                "São Paulo",
                clienteDTO,
                "SP"
        ));

        clienteDTO.setEnderecoEntregaDto(listaEnderecosEntrega);
        clienteDTO.setEnderecoFaturamentoDto(listaEnderecosFaturamento);

        ClienteEntity clienteEntity = clienteDTO.toEntity();
        repository.save(clienteEntity);

        repository.delete(clienteEntity);

        assertNull(repository.findById(clienteEntity.getId()).orElse(null));
    }
}
