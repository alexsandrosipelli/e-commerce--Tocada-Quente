package ecomerce.pi;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/*desabilitando a funçao de login do Spring Security*/
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})

public class PiApplication {

    public static void main(String[] args) {
        /*O método main() é o ponto de entrada para o aplicativo Java.
SpringApplication.run(PiApplication.class, args); inicia o aplicativo Spring Boot.*/
        SpringApplication.run(PiApplication.class, args);
    }

    @Bean/*@Bean: É uma anotação usada para indicar que o método retornará 
        um objeto que deve ser gerenciado pelo contêiner Spring. No caso,
        o método getPasswordEncoder() é um Bean que retorna um codificador de senha.*/
    public PasswordEncoder getPasswordEncoder() {
        /*BCryptPasswordEncoder: É uma implementação de PasswordEncoder fornecida pelo
   Spring Security para codificar e comparar senhas usando o algoritmo BCrypt.
Ele retorna uma instância de BCryptPasswordEncoder, que é usada para codificar senhas usando o algoritmo BCrypt.
Isso é útil para codificar as senhas dos usuários antes de armazená-las no banco de dados, por exemplo.            
         */
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

}
