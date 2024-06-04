/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.Projeto2024Alex.ProjetoComDTO.service.impl;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.ClienteDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.dto.EnderecoEntregaDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.dto.EnderecoViacepDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.ClienteEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.EnderecoEntregaEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.EnderecoFaturamentoEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.repository.ClienteRepository;
import com.br.Projeto2024Alex.ProjetoComDTO.repository.EnderecoEntregaRepository;
import com.br.Projeto2024Alex.ProjetoComDTO.repository.EnderecoFaturamentoRepository;
import com.br.Projeto2024Alex.ProjetoComDTO.repository.ProdutoRepository;
import com.br.Projeto2024Alex.ProjetoComDTO.service.ClienteService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author alexs
 */
@Service
public class ClienteServiceImpl implements ClienteService {

    private final ModelMapper modelMapper;
    private final PasswordEncoder encoder;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final EnderecoEntregaRepository enderecoEntregaRepository;
    private final EnderecoFaturamentoRepository enderecoFaturamentoRepository;
    private final EnderecoServiceImpl enderecoServiceImpl;

    @Autowired
    public ClienteServiceImpl(ModelMapper modelMapper, PasswordEncoder encoder, ClienteRepository repository, ProdutoRepository produtoRepository, EnderecoEntregaRepository enderecoEntregaRepository, EnderecoFaturamentoRepository enderecoFaturamentoRepository, EnderecoServiceImpl enderecoServiceImpl) {
        this.modelMapper = modelMapper;
        this.encoder = encoder;
        this.clienteRepository = repository;
        this.produtoRepository = produtoRepository;
        this.enderecoEntregaRepository = enderecoEntregaRepository;
        this.enderecoFaturamentoRepository = enderecoFaturamentoRepository;
        this.enderecoServiceImpl = enderecoServiceImpl;
    }

    @Override
    public String salvarCliente(ClienteEntity clienteEntity, BindingResult result, RedirectAttributes attributes) {
        ClienteDTO clienteDTO = modelMapper.map(clienteEntity, ClienteDTO.class);
        boolean temErros = validacaoCamposCadastro(clienteDTO, result);
        if (temErros){
            return "criar-cliente";
        }else{
            preencherNovoCliente(clienteDTO);
            ClienteEntity clienteSalvar = modelMapper.map(clienteDTO, ClienteEntity.class);
            clienteRepository.save(clienteSalvar);
            EnderecoEntregaEntity enderecoEntrega = preencherNovoEnderecoEntrega(clienteDTO);
            EnderecoFaturamentoEntity enderecoFaturamento = preencherNovoEnderecoFaturamento(clienteDTO);
            enderecoEntregaRepository.save(enderecoEntrega);
            enderecoFaturamentoRepository.save(enderecoFaturamento);
            return "redirect:/site/cliente/";
        }
    }

    private EnderecoFaturamentoEntity preencherNovoEnderecoFaturamento(ClienteDTO clienteDTO) {
        EnderecoEntregaDTO enderecoFaturamentoDto = consultarNovoEndereco(clienteDTO);

        return modelMapper.map(enderecoFaturamentoDto, EnderecoFaturamentoEntity.class);
    }

    private EnderecoEntregaEntity preencherNovoEnderecoEntrega(ClienteDTO clienteDTO) {
        EnderecoEntregaDTO enderecoEntregaDTO = consultarNovoEndereco(clienteDTO);

        return modelMapper.map(enderecoEntregaDTO, EnderecoEntregaEntity.class);
    }

    private EnderecoEntregaDTO consultarNovoEndereco(ClienteDTO clienteDTO) {
        ClienteEntity clienteSalvo = clienteRepository.findByEmail(clienteDTO.getEmail());
        ClienteDTO clienteDTO1 = modelMapper.map(clienteSalvo, ClienteDTO.class);
        EnderecoViacepDTO viacepDTO = new EnderecoViacepDTO();

        viacepDTO.setCep(clienteSalvo.getCep());

        EnderecoEntregaDTO endereco = enderecoServiceImpl.executaBuscaEnderecoEntrega(viacepDTO);

        endereco.setCep(endereco.getCep().replace("-", ""));
        endereco.setNumero(clienteDTO.getNumero());
        endereco.setComplemento(clienteDTO.getComplemento());
        endereco.setEnderecoPrincipal(false);
        endereco.setStatus(true);
        endereco.setCliente(clienteDTO1);
        return endereco;
    }

    private void preencherNovoCliente(ClienteDTO clienteDto) {
        clienteDto.setDataNascimento(clienteDto.getDataNascimento().replace("-", ""));
        String senhaCriptografada = encoder.encode(clienteDto.getSenha());
        clienteDto.setSenha(senhaCriptografada);
    }

    private boolean validacaoCamposCadastro(ClienteDTO clienteDTO, BindingResult result) {
        String regexCep = "^[0-9]+$";
        if (clienteRepository.existsByEmailIgnoreCase(clienteDTO.getEmail())) {
            result.rejectValue("email", "email.exists", "O e-mail já está cadastrado. Por favor, escolha outro.");
        }

        if (clienteRepository.existsByCpf(clienteDTO.getCpf())) {
            result.rejectValue("cpf", "email.cpf", "O cpf já está cadastrado. Por favor, escolha outro.");
        }

        if (clienteDTO.getNumero() != null && clienteDTO.getNumero().toString().length() > 8){
            result.rejectValue("numero", "numero.max", "O número do endereço deve ter no máximo 8 dígitos.");
        }

        if (clienteDTO.getCep() != null && !clienteDTO.getCep().matches(regexCep)){
            result.rejectValue("cep", "cep.invalid", "O CEP deve conter apenas números.");
        }

        if (clienteDTO.getNumero() == null){
            result.rejectValue("numero", "numero.null", "O campo número não pode ser nulo.");
        }

        if (clienteDTO.getComplemento().isBlank() || clienteDTO.getComplemento().isEmpty()){
            result.rejectValue("complemento", "complemento.empty", "O campo complemento não pode estar em branco.");
        }

        return result.hasErrors();
    }

    @Override
    public String editarCliente(HttpSession session, Model model) {
        ClienteEntity clienteLogado = (ClienteEntity) session.getAttribute("clienteLogado");
        if (clienteLogado != null){
            String dataFormatada = formatarDataNascimento(clienteLogado.getDataNascimento());
            ClienteDTO clienteDTO = modelMapper.map(clienteLogado, ClienteDTO.class);
            clienteDTO.setDataNascimento(dataFormatada);
            clienteDTO.setCep(null);
            clienteDTO.setLocalidade(null);
            clienteDTO.setUf(null);
            model.addAttribute("clienteEditar", clienteDTO);
            return "editar-cliente";
        }
        return "redirect:/site/cliente/";
    }

    private String formatarDataNascimento(String dataNascimento) {
        DateTimeFormatter formatterEntrada = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter formatterSaida = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataFormatada = LocalDate.parse(dataNascimento, formatterEntrada);
        return dataFormatada.format(formatterSaida);
    }

    @Override
    public String confirmarAtualizacao(ClienteDTO clienteDTO, BindingResult result, HttpSession session) {
        ClienteEntity clienteLogado = (ClienteEntity) session.getAttribute("clienteLogado");
        if (clienteLogado != null){
            Optional<ClienteEntity> clienteBase = clienteRepository.findById(clienteLogado.getId());
            if (clienteBase.isPresent()){
                if (result.hasErrors()){
                    return "editar-cliente";
                }else{
                    ClienteEntity clienteEntity = clienteBase.get();
                    preencherCamposEditar(clienteEntity, clienteDTO);
                    clienteRepository.save(clienteEntity);
                    return "redirect:/site/";
                }
            }else{
                throw new EntityNotFoundException("Cliente não encontrado");
            }
        }else{
            return "redirect:/site/cliente/";
        }
    }

    @Override
    public String deslogarCliente(HttpSession session) {
        session.removeAttribute("clienteLogado");
        // Invalidar a sessão (opcional, mas recomendado)
        session.invalidate();

        return "redirect:/site/";
    }

    @Override
    public String listarEnderecos(Model model, HttpSession session) {
        ClienteEntity clienteLogado = (ClienteEntity) session.getAttribute("clienteLogado");
        if (clienteLogado != null){
            List<EnderecoEntregaEntity> enderecos = enderecoEntregaRepository.findByCliente_IdAndStatus(clienteLogado.getId(), true);
            model.addAttribute("enderecos", enderecos);
            return "listar-enderecos";
        }
        return "redirect:/site/cliente/";
    }

    @Override
    public String salvarEndereco(EnderecoEntregaDTO enderecoEntregaDTO, BindingResult result, RedirectAttributes attributes,
                                 HttpSession session) {
        ClienteEntity clienteLogado = (ClienteEntity) session.getAttribute("clienteLogado");
        if (clienteLogado != null){
            ClienteDTO clienteDTO = modelMapper.map(clienteLogado, ClienteDTO.class);
            enderecoEntregaDTO.setCliente(clienteDTO);
            boolean temErros = validacaoCamposEndereco(enderecoEntregaDTO, result);
            if (temErros){
                return "criar-endereco";
            }else{
                enderecoEntregaDTO = preencherCamposEndereco(enderecoEntregaDTO);
                enderecoEntregaDTO.setCliente(clienteDTO);
                EnderecoEntregaEntity enderecoSalvar = modelMapper.map(enderecoEntregaDTO, EnderecoEntregaEntity.class);
                EnderecoFaturamentoEntity enderecoFaturamentoSalvar = modelMapper.map(enderecoEntregaDTO, EnderecoFaturamentoEntity.class);
                enderecoEntregaRepository.save(enderecoSalvar);
                enderecoFaturamentoRepository.save(enderecoFaturamentoSalvar);
                return "redirect:/site/cliente/enderecos";
            }
        }else{
            return "redirect:/site/cliente/";
        }
    }

    @Override
    public String novoEndereco(Model model, HttpSession session) {
        ClienteEntity clienteLogado = (ClienteEntity) session.getAttribute("clienteLogado");
        if (clienteLogado != null) {
            model.addAttribute("enderecos", new EnderecoEntregaEntity());
            return "criar-endereco";
        }
        return "redirect:/site/cliente/";
    }

    @Override
    public String desativarEndereco(Long id, Model model, HttpSession session) {
        Optional<EnderecoEntregaEntity> enderecos = enderecoEntregaRepository.findById(id);
        ClienteEntity clienteLogado = (ClienteEntity) session.getAttribute("clienteLogado");

        if (clienteLogado != null){
            if (enderecos.isPresent()){
                EnderecoEntregaEntity endereco = enderecos.get();
                endereco.setStatus(false);
                endereco.setCliente(clienteLogado);
                enderecoEntregaRepository.save(endereco);
                return "redirect:/site/cliente/enderecos";
            }else{
                throw new EntityNotFoundException("Endereço com o id "+id+" não encontrado na base de dados");
            }
        }else{
            return "redirect:/site/cliente/";
        }
    }

    @Override
    public String alterarEnderecoPrincipal(Long id, HttpSession session) {
        Optional<EnderecoEntregaEntity> enderecoOptional = enderecoEntregaRepository.findById(id);
        ClienteEntity clienteLogado = (ClienteEntity) session.getAttribute("clienteLogado");
        if (clienteLogado != null){
            if (enderecoOptional.isPresent()){
                EnderecoEntregaEntity enderecoPrincipal = enderecoOptional.get();
                enderecoPrincipal.setCliente(clienteLogado);

                if (Boolean.TRUE.equals(enderecoPrincipal.getEnderecoPrincipal())){
                    enderecoPrincipal.setEnderecoPrincipal(false);
                    enderecoEntregaRepository.save(enderecoPrincipal);
                    return "redirect:/site/cliente/enderecos";
                }
                // Desativar todos os outros endereços principais para este cliente
                List<EnderecoEntregaEntity> enderecosCliente = enderecoEntregaRepository
                        .findSecondaryEnderecosByClienteId(enderecoPrincipal.getCliente().getId());

                for (EnderecoEntregaEntity endereco : enderecosCliente) {
                    if (!endereco.getId().equals(id) && Boolean.TRUE.equals(endereco.getEnderecoPrincipal())) {
                        endereco.setEnderecoPrincipal(false);
                        enderecoEntregaRepository.save(endereco);
                    }
                }

                // Definir o endereço especificado como principal
                enderecoPrincipal.setEnderecoPrincipal(true);
                enderecoEntregaRepository.save(enderecoPrincipal);
                return "redirect:/site/cliente/enderecos";
            } else {
                throw new EntityNotFoundException("Endereço com o id " + id + " não encontrado na base de dados");
            }
        }else{
            return "redirect:/site/cliente/";
        }
    }

    private EnderecoEntregaDTO preencherCamposEndereco(EnderecoEntregaDTO enderecoEntregaDTO) {
        enderecoEntregaDTO = consultarCepCadastroEndereco(enderecoEntregaDTO);
        return enderecoEntregaDTO;
    }

    private EnderecoEntregaDTO consultarCepCadastroEndereco(EnderecoEntregaDTO enderecoEntregaDTO) {
        EnderecoViacepDTO viacepDTO = new EnderecoViacepDTO();

        viacepDTO.setCep(enderecoEntregaDTO.getCep());

        EnderecoEntregaDTO endereco = enderecoServiceImpl.executaBuscaEnderecoEntrega(viacepDTO);

        endereco.setCep(enderecoEntregaDTO.getCep());
        endereco.setNumero(enderecoEntregaDTO.getNumero());
        endereco.setComplemento(enderecoEntregaDTO.getComplemento());
        endereco.setEnderecoPrincipal(false);
        endereco.setStatus(true);
        return endereco;
    }

    private boolean validacaoCamposEndereco(EnderecoEntregaDTO enderecoEntregaDTO, BindingResult result) {
        if (enderecoEntregaRepository.existsByCepAndCliente(enderecoEntregaDTO.getCep(), enderecoEntregaDTO.getCliente().toEntity())) {
            result.rejectValue("cep", "cep.exists", "CEP já cadastrado para o cliente logado");
        }

        if (enderecoEntregaDTO.getNumero() == null){
            result.rejectValue("numero", "numero.null", "O campo número não pode ser nulo.");
        }

        if (enderecoEntregaDTO.getComplemento().isBlank() || enderecoEntregaDTO.getComplemento().isEmpty()){
            result.rejectValue("complemento", "complemento.empty", "O campo complemento não pode estar em branco.");
        }

        return result.hasErrors();
    }

    private void preencherCamposEditar(ClienteEntity clienteEntity, ClienteDTO clienteDTO) {
        clienteEntity.setNome(clienteDTO.getNome());
        clienteEntity.setDataNascimento(clienteDTO.getDataNascimento().replace("-", ""));
        clienteEntity.setGenero(clienteDTO.getGenero());
        if (!clienteDTO.getSenha().isEmpty())
            clienteEntity.setSenha(encoder.encode(clienteDTO.getSenha()));
        clienteEntity.setNumero(777);
        clienteEntity.setComplemento("Preenchimento padrão");
    }
}
