package com.br.Projeto2024Alex.ProjetoComDTO;

import com.br.Projeto2024Alex.ProjetoComDTO.crudtest.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@SuiteClasses({UsuarioTest.class, LoginTest.class, ProdutoTest.class})
public class ProjetoComDtoApplicationTests {

}
