package com.br.Projeto2024Alex.ProjetoComDTO.crudtest;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.UsuarioDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.ClienteEntity;
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
public class LoginTest {
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
    void testeLogin(){
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

        assertEquals(usuarioEntity, usuarioEntityGet);
        UsuarioEntity entity = repository.findByEmail("usuarioteste1@gmail.com");

        assertTrue(encoder.matches("1234", entity.getSenha()));
        repository.deleteByEmailStartingWithUsuarioTeste();
    }
}
