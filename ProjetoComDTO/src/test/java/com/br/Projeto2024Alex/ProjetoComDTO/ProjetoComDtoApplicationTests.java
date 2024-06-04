package com.br.Projeto2024Alex.ProjetoComDTO;

import com.br.Projeto2024Alex.ProjetoComDTO.crudtest.ClienteTest;
import com.br.Projeto2024Alex.ProjetoComDTO.crudtest.LoginTest;
import com.br.Projeto2024Alex.ProjetoComDTO.crudtest.ProdutoTest;
import com.br.Projeto2024Alex.ProjetoComDTO.crudtest.UsuarioTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({UsuarioTest.class, LoginTest.class, ProdutoTest.class, ClienteTest.class})
public class ProjetoComDtoApplicationTests {

}
