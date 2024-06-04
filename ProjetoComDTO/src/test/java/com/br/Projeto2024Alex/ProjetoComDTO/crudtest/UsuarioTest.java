package com.br.Projeto2024Alex.ProjetoComDTO.crudtest;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.UsuarioDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.UsuarioEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.repository.UsuarioRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class UsuarioTest {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UsuarioRepository repository;

    @BeforeEach
    @AfterEach
    void limparDadosDeTeste() {
        List<UsuarioEntity> usuarios = repository.findByEmailStartingWith("usuarioteste");
        repository.deleteAll(usuarios);
    }

    @Test
    void testeCadastrarUsuario(){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        String senha = encoder.encode("1234");

        usuarioDTO.setNome("Usuário Teste1");
        usuarioDTO.setEmail("usuarioteste1@gmail.com");
        usuarioDTO.setSenha(senha);
        usuarioDTO.setGrupo("ADMINISTRADOR");
        usuarioDTO.setCPF("02869286805");
        usuarioDTO.setStatus(false);

        UsuarioEntity usuarioEntity = usuarioDTO.toEntity();
        repository.save(usuarioEntity);
        UsuarioEntity usuarioEntityGet = repository.findById(usuarioEntity.getId()).orElse(null);
        assertNotNull(usuarioEntityGet, "O usuário não foi cadastrado corretamente");
    }

    @Test
    void testeBuscarUsuario(){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        String senha = encoder.encode("1234");

        usuarioDTO.setNome("Usuário Teste2");
        usuarioDTO.setEmail("usuarioteste2@gmail.com");
        usuarioDTO.setCPF("95596414840");
        usuarioDTO.setGrupo("ADMINISTRADOR");
        usuarioDTO.setStatus(true);
        usuarioDTO.setSenha(senha);
        usuarioDTO.setConfirmacaoSenha(senha);

        UsuarioEntity entity = usuarioDTO.toEntity();
        repository.save(entity);
        UsuarioEntity entityGet = repository.findById(entity.getId()).orElse(null);

        assertEquals(entity, entityGet, "Cliente não encontrado na base de dados.");
    }

    @Test
    void testeEditarUsuario() {
        // Configuração do UsuarioDTO
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        String senha = encoder.encode("1234");

        usuarioDTO.setNome("Usuário Teste3");
        usuarioDTO.setEmail("usuarioteste3@gmail.com");
        usuarioDTO.setCPF("56958841875");
        usuarioDTO.setGrupo("ESTOQUISTA");
        usuarioDTO.setStatus(true);
        usuarioDTO.setSenha(senha);
        usuarioDTO.setConfirmacaoSenha(senha);

        // Conversão para UsuarioEntity e salvamento inicial
        UsuarioEntity usuarioEntity = usuarioDTO.toEntity();
        repository.save(usuarioEntity);

        // Verifica se o usuário foi salvo corretamente
        UsuarioEntity usuarioSalvo = repository.findById(usuarioEntity.getId()).orElse(null);
        assertNotNull(usuarioSalvo, "O usuário não foi encontrado após salvar");
        assertEquals("ESTOQUISTA", usuarioSalvo.getGrupo(), "O grupo do usuário não foi salvo corretamente");

        // Atualização do grupo do usuário
        usuarioSalvo.setGrupo("ADMINISTRADOR");

        // Salvamento da atualização
        repository.save(usuarioSalvo);

        // Verificação se o usuário foi atualizado corretamente
        UsuarioEntity usuarioAtualizado = repository.findById(usuarioSalvo.getId()).orElse(null);
        assertNotNull(usuarioAtualizado, "O usuário não foi encontrado após a atualização");
        assertEquals("ADMINISTRADOR", usuarioAtualizado.getGrupo(), "O grupo do usuário não foi atualizado corretamente");
    }


    @Test
    void testeExcluirUsuario(){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        String senha = encoder.encode("1234");

        usuarioDTO.setNome("Usuário Teste4");
        usuarioDTO.setEmail("usuarioteste4@gmail.com");
        usuarioDTO.setCPF("90153002832");
        usuarioDTO.setGrupo("ADMINISTRADOR");
        usuarioDTO.setStatus(true);
        usuarioDTO.setSenha(senha);
        usuarioDTO.setConfirmacaoSenha(senha);

        UsuarioEntity entity = usuarioDTO.toEntity();

        repository.save(entity);

        // Excluir o usuário do banco de dados
        repository.delete(entity);

        // Verificar se o usuário foi excluído com sucesso
        assertNull(repository.findById(entity.getId()).orElse(null), "O usuário não foi excluído corretamente.");
    }
}
