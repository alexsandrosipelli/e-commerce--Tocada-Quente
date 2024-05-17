package com.br.Projeto2024Alex.ProjetoComDTO.crudtest;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.UsuarioDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.UsuarioEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class UsuarioTest {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UsuarioRepository repository;

    @Test
    void testeCadastrarUsuario(){

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        String senha = encoder.encode("1234");

        usuarioDTO.setNome("Teste1");
        usuarioDTO.setEmail("usuarioteste1@gmail.com");
        usuarioDTO.setCPF("99239242899");
        usuarioDTO.setGrupo("ADMINISTRADOR");
        usuarioDTO.setStatus(true);
        usuarioDTO.setSenha(senha);
        usuarioDTO.setConfirmacaoSenha(senha);

        UsuarioEntity entity = usuarioDTO.toEntity();

        repository.save(entity);

        assertNotNull(entity.getId());
    }

    @Test
    void testeBuscarUsuario(){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        String senha = encoder.encode("1234");

        usuarioDTO.setNome("Teste2");
        usuarioDTO.setEmail("usuarioteste2@gmail.com");
        usuarioDTO.setCPF("95596414840");
        usuarioDTO.setGrupo("ADMINISTRADOR");
        usuarioDTO.setStatus(true);
        usuarioDTO.setSenha(senha);
        usuarioDTO.setConfirmacaoSenha(senha);

        UsuarioEntity entity = usuarioDTO.toEntity();

        repository.save(entity);
        UsuarioEntity entityGet = repository.findById(entity.getId()).orElse(null);

        assertEquals(entity, entityGet);
    }

    @Test
    void testeEditarUsuario(){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        String senha = encoder.encode("1234");

        usuarioDTO.setNome("Teste3");
        usuarioDTO.setEmail("usuarioteste3@gmail.com");
        usuarioDTO.setCPF("56958841875");
        usuarioDTO.setGrupo("ESTOQUISTA");
        usuarioDTO.setStatus(true);
        usuarioDTO.setSenha(senha);
        usuarioDTO.setConfirmacaoSenha(senha);

        UsuarioEntity entity = usuarioDTO.toEntity();

        repository.save(entity);

        //Atualizei o valor
        entity.setGrupo("ADMINISTRADOR");

        repository.save(entity);

        UsuarioEntity usuarioAtualizado = repository.findById(entity.getId()).orElse(null);

        assertNotNull(usuarioAtualizado);
        assertEquals("ADMINISTRADOR", usuarioAtualizado.getGrupo());
    }

    @Test
    void testeExcluirUsuario(){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        String senha = encoder.encode("1234");

        usuarioDTO.setNome("Teste4");
        usuarioDTO.setEmail("usuarioteste4@gmail.com");
        usuarioDTO.setCPF("90153002832");
        usuarioDTO.setGrupo("ADMINISTRADOR");
        usuarioDTO.setStatus(true);
        usuarioDTO.setSenha(senha);
        usuarioDTO.setConfirmacaoSenha(senha);

        UsuarioEntity entity = usuarioDTO.toEntity();

        repository.save(entity);

        // Excluir o produto do banco de dados
        repository.delete(entity);

        // Verificar se o produto foi excluído com sucesso
        assertNull(repository.findById(entity.getId()).orElse(null));
    }
}
