package component;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.UsuarioDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.UsuarioEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DataComponent implements ApplicationRunner {

  private static final Logger logger = LoggerFactory.getLogger(DataComponent.class);
  private static final Long ADMIN_USER_ID = 1L;
  private static final String ADMIN_USER_NAME = "Adm. Professor";
  private static final String ADMIN_USER_EMAIL = "professor@gmail.com";
  private static final String ADMIN_USER_CPF = "29642577879";
  private static final String ADMIN_USER_GROUP = "ADMINISTRADOR";
  private static final String ADMIN_USER_PASSWORD = "1234";

  private final UsuarioRepository usuarioRepository;
  private final PasswordEncoder encoder;
  private final ModelMapper modelMapper;

  @Autowired
  public DataComponent(UsuarioRepository usuarioRepository, PasswordEncoder encoder, ModelMapper modelMapper) {
    this.usuarioRepository = usuarioRepository;
    this.encoder = encoder;
    this.modelMapper = modelMapper;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    if (!usuarioRepository.existsById(ADMIN_USER_ID)) {
      cadastrarUsuario();
      logger.info("USUÁRIO ADM CRIADO NA BASE DE DADOS");
    } else {
      logger.info("USUÁRIO ADM JÁ CRIADO NA BASE DE DADOS");
    }
  }

  private void cadastrarUsuario() {
    UsuarioDTO usuarioDTO = new UsuarioDTO();
    usuarioDTO.setNome(ADMIN_USER_NAME);
    usuarioDTO.setEmail(ADMIN_USER_EMAIL);
    usuarioDTO.setCPF(ADMIN_USER_CPF);
    usuarioDTO.setGrupo(ADMIN_USER_GROUP);
    usuarioDTO.setStatus(true);
    String encodedPassword = encoder.encode(ADMIN_USER_PASSWORD);
    usuarioDTO.setSenha(encodedPassword);
    usuarioDTO.setConfirmacaoSenha(encodedPassword);
    UsuarioEntity usuarioEntity = modelMapper.map(usuarioDTO, UsuarioEntity.class);
    usuarioRepository.save(usuarioEntity);
  }
}
