/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.Projeto2024Alex.ProjetoComDTO.service.impl;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.ClienteDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.dto.EnderecoEntregaDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.dto.EnderecoFaturamentoDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.dto.EnderecoViacepDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.ClienteEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.repository.ClienteRepository;
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
    private final EnderecoServiceImpl enderecoServiceImpl;

    @Autowired
    public ClienteServiceImpl(ModelMapper modelMapper, PasswordEncoder encoder, ClienteRepository repository, EnderecoServiceImpl enderecoServiceImpl) {
        this.modelMapper = modelMapper;
        this.encoder = encoder;
        this.clienteRepository = repository;
        this.enderecoServiceImpl = enderecoServiceImpl;
    }

    @Override
    public String salvarCliente(ClienteEntity clienteEntity, BindingResult result, RedirectAttributes attributes) {
        ClienteDTO clienteDTO = modelMapper.map(clienteEntity, ClienteDTO.class);
        boolean temErros = validacaoCamposCadastro(clienteDTO, result);
        if (temErros){
            return "criar-cliente";
        }else{
            preencherCamposCadastro(clienteDTO);
            ClienteEntity clienteSalvar = modelMapper.map(clienteDTO, ClienteEntity.class);
            clienteRepository.save(clienteSalvar);
            return "redirect:/cliente/";
        }
    }

    private void preencherCamposCadastro(ClienteDTO clienteDto) {
        clienteDto.setDataNascimento(clienteDto.getDataNascimento().replace("-", ""));
        String senhaCriptografada = encoder.encode(clienteDto.getSenha());
        clienteDto.setSenha(senhaCriptografada);
        consultarCep(clienteDto);
    }

    private boolean validacaoCamposCadastro(ClienteDTO clienteDTO, BindingResult result) {
        if (clienteRepository.existsByEmailIgnoreCase(clienteDTO.getEmail())) {
            result.rejectValue("email", "email.exists", "O e-mail já está cadastrado. Por favor, escolha outro.");
        }

        if (clienteRepository.existsByCpf(clienteDTO.getCpf())) {
            result.rejectValue("cpf", "email.cpf", "O cpf já está cadastrado. Por favor, escolha outro.");
        }

        return result.hasErrors();
    }

    @Override
    public void consultarCep(ClienteDTO clienteDTO) {
        EnderecoViacepDTO viacepDTO = new EnderecoViacepDTO();

        viacepDTO.setCep(clienteDTO.getCep());

        EnderecoEntregaDTO enderecoEntregaDTO = enderecoServiceImpl.executaBuscaEnderecoEntrega(viacepDTO);

        EnderecoFaturamentoDTO enderecoFaturamentoDto = modelMapper.map(enderecoEntregaDTO, EnderecoFaturamentoDTO.class);

        enderecoEntregaDTO.setCliente(clienteDTO);
        enderecoFaturamentoDto.setCliente(clienteDTO);

        clienteDTO.setLocalidade(enderecoEntregaDTO.getLocalidade());
        clienteDTO.setUf(enderecoEntregaDTO.getUf());
        clienteDTO.setEnderecoEntregaDto(List.of(enderecoEntregaDTO));
        clienteDTO.setEnderecoFaturamentoDto(List.of(enderecoFaturamentoDto));
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
        return "redirect:/cliente/";
    }

    private String formatarDataNascimento(String dataNascimento) {
        DateTimeFormatter formatterEntrada = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter formatterSaida = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataFormatada = LocalDate.parse(dataNascimento, formatterEntrada);
        return dataFormatada.format(formatterSaida);
    }

    @Override
    public String confirmarAtualizacao(ClienteDTO clienteDTO, BindingResult result, HttpSession session) {
        /*
        TODO AO TENTAR SALVAR, A DATA ESTÁ SENDO PASSADA COM '/', PRECISO REMOVER ELAS AO CONFIRMAR A ATUALIZAÇÃO DO REGISTRO
         */
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
                    return "redirect:/cliente/loja/";
                }
            }else{
                throw new EntityNotFoundException("Cliente não encontrado");
            }
        }else{
            return "redirect:/cliente/";
        }


    }

    private void preencherCamposEditar(ClienteEntity clienteEntity, ClienteDTO clienteDTO) {
        clienteEntity.setNome(clienteDTO.getNome());
        clienteEntity.setDataNascimento(clienteDTO.getDataNascimento());
        clienteEntity.setGenero(clienteDTO.getGenero());
        if (!clienteDTO.getSenha().isEmpty())
            clienteEntity.setSenha(encoder.encode(clienteDTO.getSenha()));
        clienteEntity.setEnderecosEntrega(null);
        clienteEntity.setEnderecosFaturamento(null);
    }
}
